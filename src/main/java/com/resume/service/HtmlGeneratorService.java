package com.resume.service;

import com.resume.model.Resume;
import com.resume.model.Education;
import com.resume.model.WorkExperience;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Сервис генерации HTML резюме.
 * Создает HTML файлы с красивым оформлением в различных темах.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class HtmlGeneratorService {
    
    private static final String OUTPUT_DIR = "generated-resumes";
    private static final String DEFAULT_FILENAME = "resume.html";

    /**
     * Сгенерировать HTML резюме.
     * 
     * @param resume данные резюме
     * @return путь к созданному файлу
     * @throws IOException если произошла ошибка при записи файла
     */
    public String generateHtml(Resume resume) throws IOException {
        String htmlContent = buildHtmlContent(resume);
        return saveHtmlToFile(htmlContent, DEFAULT_FILENAME);
    }

    /**
     * Сгенерировать HTML резюме с заданным именем файла.
     * 
     * @param resume данные резюме
     * @param filename имя файла
     * @return путь к созданному файлу
     * @throws IOException если произошла ошибка при записи файла
     */
    public String generateHtml(Resume resume, String filename) throws IOException {
        String htmlContent = buildHtmlContent(resume);
        return saveHtmlToFile(htmlContent, filename);
    }

    /**
     * Построить HTML контент резюме.
     * 
     * @param resume данные резюме
     * @return HTML строка
     */
    private String buildHtmlContent(Resume resume) {
        StringBuilder html = new StringBuilder();
        
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"ru\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>").append(escapeHtml(resume.getFullName())).append(" - Резюме</title>\n");
        html.append("    <style>\n");
        html.append(getCssForTheme(resume.getTheme()));
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        
        // Заголовок с персональной информацией
        html.append(buildPersonalInfoSection(resume));
        
        // Карьерная цель
        if (resume.getObjective() != null && !resume.getObjective().trim().isEmpty()) {
            html.append(buildObjectiveSection(resume));
        }
        
        // Опыт работы
        if (!resume.getWorkExperience().isEmpty()) {
            html.append(buildWorkExperienceSection(resume));
        }
        
        // Образование
        if (!resume.getEducation().isEmpty()) {
            html.append(buildEducationSection(resume));
        }
        
        // Навыки
        if (!resume.getSkills().isEmpty()) {
            html.append(buildSkillsSection(resume));
        }
        
        // Языки
        if (!resume.getLanguages().isEmpty()) {
            html.append(buildLanguagesSection(resume));
        }
        
        // Сертификаты
        if (!resume.getCertifications().isEmpty()) {
            html.append(buildCertificationsSection(resume));
        }
        
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>");
        
        return html.toString();
    }

    /**
     * Построить секцию персональной информации.
     */
    private String buildPersonalInfoSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <header class=\"header\">\n");
        section.append("            <h1>").append(escapeHtml(resume.getFullName())).append("</h1>\n");
        section.append("            <div class=\"contact-info\">\n");
        
        if (resume.getEmail() != null && !resume.getEmail().trim().isEmpty()) {
            section.append("                <div class=\"contact-item\">\n");
            section.append("                    <span class=\"contact-label\">Email:</span>\n");
            section.append("                    <a href=\"mailto:").append(escapeHtml(resume.getEmail())).append("\">");
            section.append(escapeHtml(resume.getEmail())).append("</a>\n");
            section.append("                </div>\n");
        }
        
        if (resume.getPhone() != null && !resume.getPhone().trim().isEmpty()) {
            section.append("                <div class=\"contact-item\">\n");
            section.append("                    <span class=\"contact-label\">Телефон:</span>\n");
            section.append("                    <span>").append(escapeHtml(resume.getPhone())).append("</span>\n");
            section.append("                </div>\n");
        }
        
        if (resume.getAddress() != null && !resume.getAddress().trim().isEmpty()) {
            section.append("                <div class=\"contact-item\">\n");
            section.append("                    <span class=\"contact-label\">Адрес:</span>\n");
            section.append("                    <span>").append(escapeHtml(resume.getAddress())).append("</span>\n");
            section.append("                </div>\n");
        }
        
        if (resume.getLinkedIn() != null && !resume.getLinkedIn().trim().isEmpty()) {
            section.append("                <div class=\"contact-item\">\n");
            section.append("                    <span class=\"contact-label\">LinkedIn:</span>\n");
            section.append("                    <a href=\"").append(escapeHtml(resume.getLinkedIn())).append("\" target=\"_blank\">");
            section.append(escapeHtml(resume.getLinkedIn())).append("</a>\n");
            section.append("                </div>\n");
        }
        
        if (resume.getGithub() != null && !resume.getGithub().trim().isEmpty()) {
            section.append("                <div class=\"contact-item\">\n");
            section.append("                    <span class=\"contact-label\">GitHub:</span>\n");
            section.append("                    <a href=\"").append(escapeHtml(resume.getGithub())).append("\" target=\"_blank\">");
            section.append(escapeHtml(resume.getGithub())).append("</a>\n");
            section.append("                </div>\n");
        }
        
        section.append("            </div>\n");
        section.append("        </header>\n\n");
        
        return section.toString();
    }

    /**
     * Построить секцию карьерной цели.
     */
    private String buildObjectiveSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"section\">\n");
        section.append("            <h2>Карьерная цель</h2>\n");
        section.append("            <p>").append(escapeHtml(resume.getObjective())).append("</p>\n");
        section.append("        </section>\n\n");
        
        return section.toString();
    }

    /**
     * Построить секцию опыта работы.
     */
    private String buildWorkExperienceSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"section\">\n");
        section.append("            <h2>Опыт работы</h2>\n");
        
        for (WorkExperience work : resume.getWorkExperience()) {
            section.append("            <div class=\"work-item\">\n");
            section.append("                <h3>").append(escapeHtml(work.getPosition())).append("</h3>\n");
            section.append("                <h4>").append(escapeHtml(work.getCompany())).append("</h4>\n");
            
            String period = escapeHtml(work.getStartDate());
            if (work.isCurrentJob()) {
                period += " - Настоящее время";
            } else if (work.getEndDate() != null && !work.getEndDate().trim().isEmpty()) {
                period += " - " + escapeHtml(work.getEndDate());
            }
            section.append("                <p class=\"period\">").append(period).append("</p>\n");
            
            if (work.getDescription() != null && !work.getDescription().trim().isEmpty()) {
                section.append("                <p class=\"description\">").append(escapeHtml(work.getDescription())).append("</p>\n");
            }
            section.append("            </div>\n");
        }
        
        section.append("        </section>\n\n");
        
        return section.toString();
    }

    /**
     * Построить секцию образования.
     */
    private String buildEducationSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"section\">\n");
        section.append("            <h2>Образование</h2>\n");
        
        for (Education edu : resume.getEducation()) {
            section.append("            <div class=\"education-item\">\n");
            section.append("                <h3>").append(escapeHtml(edu.getDegree())).append("</h3>\n");
            section.append("                <h4>").append(escapeHtml(edu.getInstitution())).append("</h4>\n");
            
            if (edu.getMajor() != null && !edu.getMajor().trim().isEmpty()) {
                section.append("                <p class=\"major\">Специальность: ").append(escapeHtml(edu.getMajor())).append("</p>\n");
            }
            
            String period = "";
            if (edu.getStartYear() != null && !edu.getStartYear().trim().isEmpty()) {
                period += escapeHtml(edu.getStartYear());
                if (edu.getEndYear() != null && !edu.getEndYear().trim().isEmpty()) {
                    period += " - " + escapeHtml(edu.getEndYear());
                }
            }
            if (!period.isEmpty()) {
                section.append("                <p class=\"period\">").append(period).append("</p>\n");
            }
            
            if (edu.getGpa() != null && !edu.getGpa().trim().isEmpty()) {
                section.append("                <p class=\"gpa\">Средний балл: ").append(escapeHtml(edu.getGpa())).append("</p>\n");
            }
            
            section.append("            </div>\n");
        }
        
        section.append("        </section>\n\n");
        
        return section.toString();
    }

    /**
     * Построить секцию навыков.
     */
    private String buildSkillsSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"section\">\n");
        section.append("            <h2>Навыки</h2>\n");
        section.append("            <div class=\"skills-list\">\n");
        
        for (String skill : resume.getSkills()) {
            if (skill != null && !skill.trim().isEmpty()) {
                section.append("                <span class=\"skill-item\">").append(escapeHtml(skill)).append("</span>\n");
            }
        }
        
        section.append("            </div>\n");
        section.append("        </section>\n\n");
        
        return section.toString();
    }

    /**
     * Построить секцию языков.
     */
    private String buildLanguagesSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"section\">\n");
        section.append("            <h2>Языки</h2>\n");
        section.append("            <ul class=\"languages-list\">\n");
        
        for (String language : resume.getLanguages()) {
            if (language != null && !language.trim().isEmpty()) {
                section.append("                <li>").append(escapeHtml(language)).append("</li>\n");
            }
        }
        
        section.append("            </ul>\n");
        section.append("        </section>\n\n");
        
        return section.toString();
    }

    /**
     * Построить секцию сертификатов.
     */
    private String buildCertificationsSection(Resume resume) {
        StringBuilder section = new StringBuilder();
        
        section.append("        <section class=\"section\">\n");
        section.append("            <h2>Сертификаты</h2>\n");
        section.append("            <ul class=\"certifications-list\">\n");
        
        for (String cert : resume.getCertifications()) {
            if (cert != null && !cert.trim().isEmpty()) {
                section.append("                <li>").append(escapeHtml(cert)).append("</li>\n");
            }
        }
        
        section.append("            </ul>\n");
        section.append("        </section>\n\n");
        
        return section.toString();
    }

    /**
     * Получить CSS стили для выбранной темы.
     */
    private String getCssForTheme(String theme) {
        if ("classic".equals(theme)) {
            return getClassicThemeCss();
        } else {
            return getModernThemeCss();
        }
    }

    /**
     * Современная тема CSS.
     */
    private String getModernThemeCss() {
        return """
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            
            body {
                font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                line-height: 1.6;
                color: #333;
                background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                min-height: 100vh;
                padding: 20px;
            }
            
            .container {
                max-width: 800px;
                margin: 0 auto;
                background: white;
                border-radius: 15px;
                box-shadow: 0 20px 40px rgba(0,0,0,0.1);
                overflow: hidden;
            }
            
            .header {
                background: linear-gradient(135deg, #2c3e50, #34495e);
                color: white;
                padding: 40px;
                text-align: center;
            }
            
            .header h1 {
                font-size: 2.5em;
                margin-bottom: 20px;
                font-weight: 300;
            }
            
            .contact-info {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
            }
            
            .contact-item {
                display: flex;
                align-items: center;
                gap: 8px;
            }
            
            .contact-label {
                font-weight: 600;
                opacity: 0.8;
            }
            
            .contact-item a {
                color: #74b9ff;
                text-decoration: none;
            }
            
            .contact-item a:hover {
                text-decoration: underline;
            }
            
            .section {
                padding: 30px 40px;
                border-bottom: 1px solid #ecf0f1;
            }
            
            .section:last-child {
                border-bottom: none;
            }
            
            .section h2 {
                color: #2c3e50;
                font-size: 1.8em;
                margin-bottom: 20px;
                position: relative;
                padding-bottom: 10px;
            }
            
            .section h2::after {
                content: '';
                position: absolute;
                bottom: 0;
                left: 0;
                width: 50px;
                height: 3px;
                background: linear-gradient(135deg, #667eea, #764ba2);
                border-radius: 2px;
            }
            
            .work-item, .education-item {
                margin-bottom: 25px;
                padding: 20px;
                background: #f8f9fa;
                border-radius: 10px;
                border-left: 4px solid #667eea;
            }
            
            .work-item h3, .education-item h3 {
                color: #2c3e50;
                font-size: 1.3em;
                margin-bottom: 5px;
            }
            
            .work-item h4, .education-item h4 {
                color: #667eea;
                font-size: 1.1em;
                margin-bottom: 10px;
            }
            
            .period {
                color: #7f8c8d;
                font-style: italic;
                margin-bottom: 10px;
            }
            
            .description {
                color: #555;
                line-height: 1.7;
            }
            
            .major, .gpa {
                color: #555;
                margin-bottom: 5px;
            }
            
            .skills-list {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }
            
            .skill-item {
                background: linear-gradient(135deg, #667eea, #764ba2);
                color: white;
                padding: 8px 15px;
                border-radius: 25px;
                font-size: 0.9em;
                font-weight: 500;
            }
            
            .languages-list, .certifications-list {
                list-style: none;
                display: grid;
                grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
                gap: 10px;
            }
            
            .languages-list li, .certifications-list li {
                background: #ecf0f1;
                padding: 10px 15px;
                border-radius: 8px;
                border-left: 3px solid #667eea;
            }
            
            @media (max-width: 768px) {
                body {
                    padding: 10px;
                }
                
                .header {
                    padding: 30px 20px;
                }
                
                .header h1 {
                    font-size: 2em;
                }
                
                .contact-info {
                    flex-direction: column;
                    align-items: center;
                }
                
                .section {
                    padding: 20px;
                }
                
                .skills-list {
                    justify-content: center;
                }
            }
            """;
    }

    /**
     * Классическая тема CSS.
     */
    private String getClassicThemeCss() {
        return """
            * {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            
            body {
                font-family: 'Times New Roman', serif;
                line-height: 1.6;
                color: #333;
                background: #f5f5f5;
                padding: 20px;
            }
            
            .container {
                max-width: 800px;
                margin: 0 auto;
                background: white;
                border: 2px solid #333;
                box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            }
            
            .header {
                background: #333;
                color: white;
                padding: 30px;
                text-align: center;
                border-bottom: 3px solid #666;
            }
            
            .header h1 {
                font-size: 2.5em;
                margin-bottom: 15px;
                font-weight: bold;
                text-transform: uppercase;
                letter-spacing: 2px;
            }
            
            .contact-info {
                display: flex;
                flex-wrap: wrap;
                justify-content: center;
                gap: 20px;
                font-family: Arial, sans-serif;
                font-size: 0.9em;
            }
            
            .contact-item {
                display: flex;
                align-items: center;
                gap: 8px;
            }
            
            .contact-label {
                font-weight: bold;
            }
            
            .contact-item a {
                color: #ccc;
                text-decoration: underline;
            }
            
            .contact-item a:hover {
                color: white;
            }
            
            .section {
                padding: 25px 30px;
                border-bottom: 1px solid #ddd;
            }
            
            .section:last-child {
                border-bottom: none;
            }
            
            .section h2 {
                color: #333;
                font-size: 1.6em;
                margin-bottom: 20px;
                text-transform: uppercase;
                font-weight: bold;
                border-bottom: 2px solid #333;
                padding-bottom: 5px;
                letter-spacing: 1px;
            }
            
            .work-item, .education-item {
                margin-bottom: 20px;
                padding: 15px 0;
                border-bottom: 1px solid #eee;
            }
            
            .work-item:last-child, .education-item:last-child {
                border-bottom: none;
            }
            
            .work-item h3, .education-item h3 {
                color: #333;
                font-size: 1.2em;
                margin-bottom: 5px;
                font-weight: bold;
            }
            
            .work-item h4, .education-item h4 {
                color: #666;
                font-size: 1em;
                margin-bottom: 8px;
                font-style: italic;
            }
            
            .period {
                color: #888;
                font-size: 0.9em;
                margin-bottom: 10px;
                font-weight: bold;
            }
            
            .description {
                color: #555;
                line-height: 1.7;
                text-align: justify;
            }
            
            .major, .gpa {
                color: #666;
                margin-bottom: 5px;
                font-style: italic;
            }
            
            .skills-list {
                display: flex;
                flex-wrap: wrap;
                gap: 8px;
            }
            
            .skill-item {
                background: #333;
                color: white;
                padding: 6px 12px;
                border: 1px solid #333;
                font-size: 0.9em;
                font-weight: bold;
            }
            
            .languages-list, .certifications-list {
                list-style: none;
                columns: 2;
                column-gap: 30px;
            }
            
            .languages-list li, .certifications-list li {
                padding: 8px 0;
                border-bottom: 1px dotted #ccc;
                break-inside: avoid;
            }
            
            @media (max-width: 768px) {
                body {
                    padding: 10px;
                }
                
                .header {
                    padding: 20px;
                }
                
                .header h1 {
                    font-size: 2em;
                }
                
                .contact-info {
                    flex-direction: column;
                    align-items: center;
                }
                
                .section {
                    padding: 20px;
                }
                
                .languages-list, .certifications-list {
                    columns: 1;
                }
            }
            """;
    }

    /**
     * Сохранить HTML контент в файл.
     */
    private String saveHtmlToFile(String htmlContent, String filename) throws IOException {
        File outputDir = new File(OUTPUT_DIR);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        
        File htmlFile = new File(outputDir, filename);
        
        try (FileWriter writer = new FileWriter(htmlFile)) {
            writer.write(htmlContent);
        }
        
        return htmlFile.getAbsolutePath();
    }

    /**
     * Экранировать HTML символы.
     */
    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#39;");
    }
}