package com.resume.service;

import com.resume.model.Resume;
import com.resume.model.Education;
import com.resume.model.WorkExperience;
import java.util.Arrays;

/**
 * Сервис для управления резюме.
 * Предоставляет методы для создания, обновления и валидации резюме.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class ResumeService {

    /**
     * Создать новое резюме с данными по умолчанию.
     * 
     * @return новое резюме с примерными данными
     */
    public Resume createDefaultResume() {
        Resume resume = new Resume();
        
        // Устанавливаем пример данных
        resume.setFullName("Иван Иванов");
        resume.setEmail("ivan.ivanov@example.com");
        resume.setPhone("+7 (999) 123-45-67");
        resume.setAddress("Москва, Россия");
        resume.setLinkedIn("https://linkedin.com/in/ivan-ivanov");
        resume.setGithub("https://github.com/ivan-ivanov");
        resume.setObjective("Опытный разработчик программного обеспечения с 5+ годами опыта в создании веб-приложений и мобильных решений. Стремлюсь применить свои навыки в инновационной компании.");
        
        // Добавляем примеры образования
        Education education1 = new Education();
        education1.setInstitution("Московский Государственный Университет");
        education1.setDegree("Магистр");
        education1.setMajor("Компьютерные науки");
        education1.setStartYear("2015");
        education1.setEndYear("2017");
        education1.setGpa("4.8");
        resume.getEducation().add(education1);
        
        // Добавляем примеры опыта работы
        WorkExperience work1 = new WorkExperience();
        work1.setCompany("ООО 'Инновационные Технологии'");
        work1.setPosition("Senior Java Developer");
        work1.setStartDate("2020");
        work1.setEndDate("");
        work1.setCurrentJob(true);
        work1.setDescription("Разработка корпоративных приложений на Java, ведение проектов, менторство младших разработчиков");
        resume.getWorkExperience().add(work1);
        
        WorkExperience work2 = new WorkExperience();
        work2.setCompany("ИТ Консалтинг");
        work2.setPosition("Java Developer");
        work2.setStartDate("2017");
        work2.setEndDate("2020");
        work2.setCurrentJob(false);
        work2.setDescription("Участие в разработке веб-приложений, интеграция с внешними системами");
        resume.getWorkExperience().add(work2);
        
        // Добавляем навыки
        resume.setSkills(Arrays.asList(
            "Java", "Spring Framework", "Hibernate", "MySQL", "PostgreSQL",
            "JavaScript", "React", "HTML/CSS", "Git", "Maven", "Docker"
        ));
        
        // Добавляем языки
        resume.setLanguages(Arrays.asList(
            "Русский (родной)", "Английский (продвинутый)", "Немецкий (базовый)"
        ));
        
        // Добавляем сертификаты
        resume.setCertifications(Arrays.asList(
            "Oracle Certified Professional, Java SE 11",
            "Spring Professional Certification",
            "AWS Certified Developer"
        ));
        
        return resume;
    }

    /**
     * Валидировать резюме на корректность данных.
     * 
     * @param resume резюме для валидации
     * @return сообщение об ошибке или null если все корректно
     */
    public String validateResume(Resume resume) {
        if (resume == null) {
            return "Резюме не может быть пустым";
        }
        
        if (isEmpty(resume.getFullName())) {
            return "Необходимо указать полное имя";
        }
        
        if (isEmpty(resume.getEmail())) {
            return "Необходимо указать email";
        }
        
        if (!isValidEmail(resume.getEmail())) {
            return "Некорректный формат email";
        }
        
        return null; // Валидация пройдена
    }

    /**
     * Обновить тему резюме.
     * 
     * @param resume резюме для обновления
     * @param theme новая тема
     */
    public void updateTheme(Resume resume, String theme) {
        if (resume != null && theme != null) {
            resume.setTheme(theme);
        }
    }

    /**
     * Получить список доступных тем.
     * 
     * @return массив названий тем
     */
    public String[] getAvailableThemes() {
        return new String[]{"modern", "classic"};
    }

    /**
     * Получить отображаемое название темы.
     * 
     * @param theme внутреннее название темы
     * @return отображаемое название
     */
    public String getThemeDisplayName(String theme) {
        switch (theme) {
            case "modern":
                return "Современная";
            case "classic":
                return "Классическая";
            default:
                return "Неизвестная";
        }
    }

    /**
     * Очистить резюме, оставив только базовую структуру.
     * 
     * @param resume резюме для очистки
     */
    public void clearResume(Resume resume) {
        if (resume == null) return;
        
        resume.setFullName("");
        resume.setEmail("");
        resume.setPhone("");
        resume.setAddress("");
        resume.setLinkedIn("");
        resume.setGithub("");
        resume.setObjective("");
        
        resume.getEducation().clear();
        resume.getWorkExperience().clear();
        resume.getSkills().clear();
        resume.getLanguages().clear();
        resume.getCertifications().clear();
    }

    /**
     * Добавить новую запись об образовании.
     * 
     * @param resume резюме
     * @return новая запись об образовании
     */
    public Education addEducation(Resume resume) {
        if (resume == null) return null;
        
        Education education = new Education();
        education.setInstitution("");
        education.setDegree("");
        education.setMajor("");
        education.setStartYear("");
        education.setEndYear("");
        education.setGpa("");
        
        resume.getEducation().add(education);
        return education;
    }

    /**
     * Добавить новую запись об опыте работы.
     * 
     * @param resume резюме
     * @return новая запись об опыте работы
     */
    public WorkExperience addWorkExperience(Resume resume) {
        if (resume == null) return null;
        
        WorkExperience work = new WorkExperience();
        work.setCompany("");
        work.setPosition("");
        work.setStartDate("");
        work.setEndDate("");
        work.setDescription("");
        work.setCurrentJob(false);
        
        resume.getWorkExperience().add(work);
        return work;
    }

    /**
     * Проверить, является ли строка пустой.
     * 
     * @param str строка для проверки
     * @return true если строка пустая или null
     */
    private boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Проверить корректность email адреса.
     * 
     * @param email email для проверки
     * @return true если email корректный
     */
    private boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}