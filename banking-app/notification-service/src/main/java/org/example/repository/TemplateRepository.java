package org.example.repository;

import org.example.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {
public Template findByTemplateNameAndLocale(String templateName, String locale);


}
