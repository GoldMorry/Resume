package com.resume.service;

import com.resume.model.Resume;
import com.resume.model.Education;
import com.resume.model.WorkExperience;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit тесты для ResumeService.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class ResumeServiceTest {
    
    private ResumeService resumeService;

    @Before
    public void setUp() {
        resumeService = new ResumeService();
    }

    @Test
    public void testCreateDefaultResume() {
        // Тест создания резюме по умолчанию
        Resume resume = resumeService.createDefaultResume();
        
        assertNotNull("Резюме не должно быть null", resume);
        assertNotNull("Полное имя должно быть заполнено", resume.getFullName());
        assertNotNull("Email должен быть заполнен", resume.getEmail());
        assertNotNull("Тема должна быть установлена", resume.getTheme());
        assertEquals("По умолчанию должна быть современная тема", "modern", resume.getTheme());
        
        // Проверяем, что коллекции инициализированы
        assertNotNull("Список образования не должен быть null", resume.getEducation());
        assertNotNull("Список опыта работы не должен быть null", resume.getWorkExperience());
        assertNotNull("Список навыков не должен быть null", resume.getSkills());
        assertNotNull("Список языков не должен быть null", resume.getLanguages());
        assertNotNull("Список сертификатов не должен быть null", resume.getCertifications());
        
        // Проверяем, что есть примерные данные
        assertFalse("Должно быть хотя бы одно образование", resume.getEducation().isEmpty());
        assertFalse("Должен быть хотя бы один опыт работы", resume.getWorkExperience().isEmpty());
        assertFalse("Должны быть навыки", resume.getSkills().isEmpty());
    }

    @Test
    public void testValidateResumeSuccess() {
        // Тест успешной валидации резюме
        Resume resume = new Resume();
        resume.setFullName("Иван Иванов");
        resume.setEmail("ivan@example.com");
        
        String validationResult = resumeService.validateResume(resume);
        assertNull("Валидация должна пройти успешно", validationResult);
    }

    @Test
    public void testValidateResumeNullResume() {
        // Тест валидации null резюме
        String validationResult = resumeService.validateResume(null);
        assertNotNull("Должна быть ошибка валидации", validationResult);
        assertTrue("Сообщение должно содержать информацию о пустом резюме", 
                  validationResult.contains("пустым"));
    }

    @Test
    public void testValidateResumeEmptyName() {
        // Тест валидации резюме с пустым именем
        Resume resume = new Resume();
        resume.setFullName("");
        resume.setEmail("ivan@example.com");
        
        String validationResult = resumeService.validateResume(resume);
        assertNotNull("Должна быть ошибка валидации", validationResult);
        assertTrue("Сообщение должно содержать информацию об имени", 
                  validationResult.contains("имя"));
    }

    @Test
    public void testValidateResumeEmptyEmail() {
        // Тест валидации резюме с пустым email
        Resume resume = new Resume();
        resume.setFullName("Иван Иванов");
        resume.setEmail("");
        
        String validationResult = resumeService.validateResume(resume);
        assertNotNull("Должна быть ошибка валидации", validationResult);
        assertTrue("Сообщение должно содержать информацию об email", 
                  validationResult.contains("email"));
    }

    @Test
    public void testValidateResumeInvalidEmail() {
        // Тест валидации резюме с некорректным email
        Resume resume = new Resume();
        resume.setFullName("Иван Иванов");
        resume.setEmail("invalid-email");
        
        String validationResult = resumeService.validateResume(resume);
        assertNotNull("Должна быть ошибка валидации", validationResult);
        assertTrue("Сообщение должно содержать информацию о формате email", 
                  validationResult.contains("формат"));
    }

    @Test
    public void testUpdateTheme() {
        // Тест обновления темы
        Resume resume = new Resume();
        resume.setTheme("modern");
        
        resumeService.updateTheme(resume, "classic");
        assertEquals("Тема должна быть обновлена", "classic", resume.getTheme());
        
        // Тест с null значениями
        resumeService.updateTheme(null, "modern");
        resumeService.updateTheme(resume, null);
        assertEquals("Тема не должна измениться при null значениях", "classic", resume.getTheme());
    }

    @Test
    public void testGetAvailableThemes() {
        // Тест получения доступных тем
        String[] themes = resumeService.getAvailableThemes();
        
        assertNotNull("Массив тем не должен быть null", themes);
        assertEquals("Должно быть две темы", 2, themes.length);
        
        boolean hasModern = false;
        boolean hasClassic = false;
        
        for (String theme : themes) {
            if ("modern".equals(theme)) hasModern = true;
            if ("classic".equals(theme)) hasClassic = true;
        }
        
        assertTrue("Должна быть современная тема", hasModern);
        assertTrue("Должна быть классическая тема", hasClassic);
    }

    @Test
    public void testGetThemeDisplayName() {
        // Тест получения отображаемых названий тем
        assertEquals("Название современной темы", "Современная", 
                    resumeService.getThemeDisplayName("modern"));
        assertEquals("Название классической темы", "Классическая", 
                    resumeService.getThemeDisplayName("classic"));
        assertEquals("Неизвестная тема", "Неизвестная", 
                    resumeService.getThemeDisplayName("unknown"));
    }

    @Test
    public void testClearResume() {
        // Тест очистки резюме
        Resume resume = resumeService.createDefaultResume();
        
        // Убеждаемся, что резюме заполнено
        assertNotNull("Имя должно быть заполнено", resume.getFullName());
        assertFalse("Образование должно быть заполнено", resume.getEducation().isEmpty());
        
        resumeService.clearResume(resume);
        
        // Проверяем, что поля очищены
        assertEquals("Имя должно быть пустым", "", resume.getFullName());
        assertEquals("Email должен быть пустым", "", resume.getEmail());
        assertTrue("Образование должно быть очищено", resume.getEducation().isEmpty());
        assertTrue("Опыт работы должен быть очищен", resume.getWorkExperience().isEmpty());
        assertTrue("Навыки должны быть очищены", resume.getSkills().isEmpty());
        
        // Тест с null резюме
        resumeService.clearResume(null);
        // Не должно быть исключений
    }

    @Test
    public void testAddEducation() {
        // Тест добавления образования
        Resume resume = new Resume();
        int initialCount = resume.getEducation().size();
        
        Education education = resumeService.addEducation(resume);
        
        assertNotNull("Образование не должно быть null", education);
        assertEquals("Количество записей должно увеличиться", 
                    initialCount + 1, resume.getEducation().size());
        assertTrue("Новое образование должно быть в списке", 
                  resume.getEducation().contains(education));
        
        // Тест с null резюме
        Education nullResult = resumeService.addEducation(null);
        assertNull("Результат должен быть null для null резюме", nullResult);
    }

    @Test
    public void testAddWorkExperience() {
        // Тест добавления опыта работы
        Resume resume = new Resume();
        int initialCount = resume.getWorkExperience().size();
        
        WorkExperience work = resumeService.addWorkExperience(resume);
        
        assertNotNull("Опыт работы не должен быть null", work);
        assertEquals("Количество записей должно увеличиться", 
                    initialCount + 1, resume.getWorkExperience().size());
        assertTrue("Новый опыт работы должен быть в списке", 
                  resume.getWorkExperience().contains(work));
        assertFalse("По умолчанию не должно быть текущей работой", work.isCurrentJob());
        
        // Тест с null резюме
        WorkExperience nullResult = resumeService.addWorkExperience(null);
        assertNull("Результат должен быть null для null резюме", nullResult);
    }

    @Test
    public void testEmailValidation() {
        // Тест валидации email адресов
        Resume resume = new Resume();
        resume.setFullName("Test User");
        
        // Валидные email адреса
        String[] validEmails = {
            "user@example.com",
            "test.email@domain.org",
            "user123@test-domain.co.uk",
            "first.last+tag@example.com"
        };
        
        for (String email : validEmails) {
            resume.setEmail(email);
            String result = resumeService.validateResume(resume);
            assertNull("Email '" + email + "' должен быть валидным", result);
        }
        
        // Невалидные email адреса
        String[] invalidEmails = {
            "invalid-email",
            "@domain.com",
            "user@",
            "user@domain",
            "user.domain.com",
            ""
        };
        
        for (String email : invalidEmails) {
            resume.setEmail(email);
            String result = resumeService.validateResume(resume);
            assertNotNull("Email '" + email + "' должен быть невалидным", result);
        }
    }
}