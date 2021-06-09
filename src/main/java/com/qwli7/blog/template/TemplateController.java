package com.qwli7.blog.template;

import com.qwli7.blog.entity.Template;
import com.qwli7.blog.entity.vo.TemplateQueryParam;
import com.qwli7.blog.exception.TemplateNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class TemplateController {


    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping("templates")
    public List<Template> findAll() {
        TemplateQueryParam queryParam = new TemplateQueryParam();
        return templateService.getAllTemplates(queryParam);
    }

    @GetMapping("{id}/template")
    public Template findById(@PathVariable("id") int id) {
        return templateService.findById(id).orElseThrow(()
                -> new TemplateNotFoundException("template.notFound"));
    }


    @PostMapping("template")
    public ResponseEntity<Void> save(@RequestBody Template template) {
        templateService.save(template);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("{id}/template")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}/template")
    public ResponseEntity<Void> update(@RequestBody Template template, @PathVariable("id") int id) {
        template.setId(id);
        templateService.update(template);
        return ResponseEntity.noContent().build();
    }

}
