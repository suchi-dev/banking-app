package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.TemplateRequest;
import org.example.dto.TemplateResponse;
import org.example.service.TemplateService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

   private final TemplateService templateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTemplate(@RequestBody TemplateRequest templateRequest){
        templateService.createTemplate(templateRequest);
    }

    @GetMapping
    @ResponseBody
    public List<TemplateResponse> getAllTemplates(){
        return templateService.getAllTemplates();
    }

    @GetMapping("/{templateName}")
    @ResponseBody
    public TemplateResponse getByNameAndLocale(@PathVariable String templateName,
                                           @RequestParam String locale){
        return templateService.getTemplateByNameAndLocale(templateName, locale);
    }

}
