package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.TemplateRequest;
import org.example.dto.TemplateResponse;
import org.example.model.Template;
import org.example.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;
    public List<TemplateResponse> getAllTemplates() {
        List<Template> templates = templateRepository.findAll();
        return templates.stream()
                .map(this:: mapToTemplateResp)
                .toList();
    }

    public TemplateResponse mapToTemplateResp(Template template){
        return TemplateResponse.builder()
                .templateId(template.getTemplateId())
                .templateName(template.getTemplateName())
                .content(template.getContent())
                .locale(template.getLocale())
                .build();
    }

    public TemplateResponse getTemplateByNameAndLocale(String templateName, String locale) {
        Template template = templateRepository.findByTemplateNameAndLocale(templateName,locale);
        return mapToTemplateResp(template);
    }

    public void createTemplate(TemplateRequest templateRequest) {
        Template template = Template.builder()
                .locale(templateRequest.getLocale())
                .templateName(templateRequest.getTemplateName())
                .content(templateRequest.getContents())
                .build();

        templateRepository.save(template);
    }
}

