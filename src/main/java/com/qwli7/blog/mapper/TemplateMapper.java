package com.qwli7.blog.mapper;

import com.qwli7.blog.entity.Template;
import com.qwli7.blog.entity.vo.TemplateQueryParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * 模板 Mapper
 * @author liqiwen
 * @since 1.2
 */
@Mapper
public interface TemplateMapper {

    /**
     * 插入模板
     * @param template template
     */
    void insert(Template template);

    /**
     * 根据 id 查询一个模板
     * @param id id
     * @return Template
     */
    Optional<Template> findById(int id);

    /**
     * 根据名称查询一个模板
     * @param name name
     * @return Template
     */
    Optional<Template> findByName(String name);

    /**
     * 更新模板
     * @param template template
     */
    void update(Template template);

    /**
     * 删除一个模板
     * @param id id
     */
    void delete(int id);

    /**
     * 查询所有的模板
     * @param queryParam queryParam
     * @return List
     */
    List<Template> listAll(TemplateQueryParam queryParam);
}
