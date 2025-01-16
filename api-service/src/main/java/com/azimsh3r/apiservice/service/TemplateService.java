package com.azimsh3r.apiservice.service;

import com.azimsh3r.apiservice.model.Template;
import com.azimsh3r.apiservice.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TemplateService {
    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Template createTemplate(Template template) {
        template.setCreatedAt(LocalDateTime.now());
        template.setUpdatedAt(LocalDateTime.now());
        return templateRepository.save(template);
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Template getTemplateById(Integer id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with id " + id));
    }

    public Template updateTemplate(Integer id, Template updatedTemplate) {
        Template existingTemplate = getTemplateById(id);
        existingTemplate.setName(updatedTemplate.getName());
        existingTemplate.setContent(updatedTemplate.getContent());
        existingTemplate.setUpdatedAt(LocalDateTime.now());
        existingTemplate.setStatus(updatedTemplate.getStatus());
        existingTemplate.setVariables(updatedTemplate.getVariables());
        return templateRepository.save(existingTemplate);
    }

    public void deleteTemplate(Integer id) {
        templateRepository.deleteById(id);
    }
}
