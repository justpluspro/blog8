package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import com.qwli7.blog.entity.vo.TemplateQueryParam;
import com.qwli7.blog.mapper.TemplateMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 模板业务类
 * @author liqiwen
 * @since 2.0
 */
@Service
public class TemplateServiceImpl implements TemplateService, HandlerMapping, InitializingBean, Ordered {

    /**
     * TemplateMapper
     */
    private final TemplateMapper templateMapper;

    private List<String> urlPatterns = new ArrayList<>();

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

        if(urlPatterns.contains(lookupPath)) {

            return new HandlerExecutionChain(lookupPath);
        }
        return null;
    }


    @Override
    public Template findTemplate(String templateName) {
        Template template = new Template();
        template.setContent("");
        template.setName("index");
        return template;
    }

    @Override
    public List<Template> getAllTemplates(TemplateQueryParam queryParam) {
        return templateMapper.listAll(queryParam);
    }

    @Override
    public void registerTemplate(Template template) {
        final String pattern = template.getPattern();
//        templateMapper.findByPattern(pattern);
    }


    @Override
    public void registerAllTemplate(Template template) {

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
        final List<Template> templates = templateMapper.listAll(queryParam);
        if(templates == null || templates.size() == 0) {
            // register local templates

            urlPatterns.add("/");
            urlPatterns.add("/moments");

        }  else {
            templates.forEach(e -> {
                final String pattern = e.getPattern();
                urlPatterns.add(pattern);
            });
        }
    }
}
