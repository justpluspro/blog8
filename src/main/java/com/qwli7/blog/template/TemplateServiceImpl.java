package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import com.qwli7.blog.entity.vo.TemplateQueryParam;
import com.qwli7.blog.exception.LogicException;
import com.qwli7.blog.mapper.TemplateMapper;
import com.qwli7.blog.util.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.*;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 模板业务类
 * @author liqiwen
 * @since 2.0
 */
@Service
public class TemplateServiceImpl implements TemplateService, HandlerMapping, InitializingBean, Ordered {

    private static final String DEFAULT_TEMPLATE_PATH = "defaultTemplate";

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    /**
     * TemplateMapper
     */
    private final TemplateMapper templateMapper;

    private List<String> urlPatterns = new CopyOnWriteArrayList<>();

    private final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    public TemplateServiceImpl(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    @Override
    public HandlerExecutionChain getHandler(HttpServletRequest request) throws Exception {
        final String method = request.getMethod();
        // skip non method get
        if(!HttpMethod.GET.name().equals(method)) {
            return null;
        }
//        DispatcherServlet
        final String lookupPath = urlPathHelper.getLookupPathForRequest(request);


        return getHandlerExecutionChain(request, lookupPath);
    }

    private HandlerExecutionChain getHandlerExecutionChain(HttpServletRequest request, String lookupPath) {
        for(String pattern : urlPatterns) {
            if(antPathMatcher.match(pattern, lookupPath)) {
                final Map<String, String[]> parameterMap = request.getParameterMap();
                final Map<String, String> variablesMap = antPathMatcher.extractUriTemplateVariables(pattern, lookupPath);
                for(String key: parameterMap.keySet()) {
                    variablesMap.put(key, parameterMap.get(key)[0]);
                }
                request.setAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE, variablesMap);
                // /  /article/ewqew  /moments  /moment/2021
                TemplateQueryParam queryParam = new TemplateQueryParam();
                queryParam.setEnable(true);
                final List<Template> allTemplates = getAllTemplates(queryParam);
                if(!CollectionUtils.isEmpty(allTemplates)) {
                    final Optional<Template> templateOp = allTemplates.stream().filter(e -> e.getPattern().equals(pattern)).findAny();
                    if(templateOp.isPresent()) {
                        return new HandlerExecutionChain(templateOp.get().getName());
                    }
                }
            }
        }
        return null;
    }


    @Transactional(readOnly = true)
    @Override
    public Template findTemplate(String templateName) {
        return templateMapper.findByName(templateName).orElseThrow(()
                -> new LogicException("template.notExists", "模板不存在"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Template> getAllTemplates(TemplateQueryParam queryParam) {
        return templateMapper.findAll(queryParam);
    }

    @Override
    public void registerTemplate(Template template) {
        final String pattern = template.getPattern();
        if(!antPathMatcher.isPattern(pattern)) {
            throw new LogicException("invalid.pattern", "无效的 pattern");
        }
        Optional<Template> templateOp = templateMapper.findByName(template.getName());
        if(templateOp.isPresent()) {
            throw new LogicException("template.exists", "模板已经存在了");
        }
        templateOp = templateMapper.findByPattern(pattern);
        if(templateOp.isPresent()) {
            throw new LogicException("template.pattern.exists", "模板pattern已经存在了");
        }
        template.setCreateAt(LocalDateTime.now());
        template.setModifyAt(LocalDateTime.now());
        templateMapper.insert(template);
        urlPatterns.add(pattern);
    }

    @Override
    public List<String> getAllUrlPatterns() {
        return urlPatterns;
    }

    @Override
    public void registerAllTemplate(Template template) {

    }

    @Override
    public void deleteTemplate(int id) {
        Template template = templateMapper.findById(id).orElseThrow(()
                -> new LogicException("template.notExists", "模板不存在"));
        templateMapper.deleteById(id);
        urlPatterns.remove(template.getPattern());
    }

    @Override
    public Optional<Template> findByName(String templateName) {
        return templateMapper.findByName(templateName);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        TemplateQueryParam queryParam = new TemplateQueryParam();
        queryParam.setEnable(true);
        final List<Template> templates = templateMapper.findAll(queryParam);
        if(CollectionUtils.isEmpty(templates)) {
            // register local templates

            ClassPathResource classPathResource = new ClassPathResource(Paths.get(DEFAULT_TEMPLATE_PATH).toString());
            final InputStream is = classPathResource.getInputStream();

            final File file = classPathResource.getFile();
            if(!file.exists() && !file.isDirectory()) {
                throw new RuntimeException("不存在默认的模板");
            }
            final File[] defaultTemplates = file.listFiles(pathname -> {
                final String filenameExtension = StringUtils.getFilenameExtension(pathname.getName());
                return !StringUtils.isEmpty(filenameExtension) && filenameExtension.endsWith("html");
            });
            if(defaultTemplates != null && defaultTemplates.length > 0) {
                List<Template> defaultTemplateList = new ArrayList<>();
                Arrays.stream(defaultTemplates).forEach(e -> {
                    final String name = e.getName();
                    Template template = new Template();
                    String nameWithoutExt = StringUtils.getFilename(name).substring(0, name.lastIndexOf("."));
                    final Optional<TemplateNameEnum> templateNameEnumOp = TemplateNameEnum.getTemplateNameEnumByName(nameWithoutExt);
                    if(templateNameEnumOp.isPresent()) {
                        final TemplateNameEnum templateNameEnum = templateNameEnumOp.get();
                        template.setName(nameWithoutExt);
                        StreamUtils.readFileToString(e).ifPresent(content -> {
                            template.setContent(content);
                            template.setEnable(true);
                            template.setAllowComment(true);
                            template.setCreateAt(LocalDateTime.now());
                            template.setModifyAt(LocalDateTime.now());
                            template.setPattern(templateNameEnum.pattern);
                            defaultTemplateList.add(template);
                        });
                    }
                });
                if(defaultTemplateList.isEmpty()) {
                    throw new RuntimeException("默认模板加载失败，请确认默认模板是否存在");
                }
                templateMapper.batchInsert(defaultTemplateList);
                urlPatterns = defaultTemplateList.stream().map(Template::getPattern).collect(Collectors.toList());
            }
        } else {
            urlPatterns = templates.stream().map(Template::getPattern).collect(Collectors.toList());
        }
    }


    public static void main(String[] args) {
        String path = "/{path}";
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        System.out.println(antPathMatcher.match(path, "/")); // true
        System.out.println(antPathMatcher.match(path, "/index"));

        String detailPath = "/article/{id}";
        System.out.println(antPathMatcher.match(detailPath, "/article/123")); // true
        System.out.println(antPathMatcher.match(detailPath, "/article/use-default")); //true
        final String filename = StringUtils.getFilename("index.html");
        System.out.println(filename.substring(0, filename.lastIndexOf(".")));

    }
}
