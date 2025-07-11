import com.resume.model.*;
import com.resume.service.*;

/**
 * Простой тест для демонстрации работы Resume Generator без GUI.
 */
public class TestApp {
    public static void main(String[] args) {
        System.out.println("=== Resume Generator - Тестирование ===\n");
        
        // Тест аутентификации
        testAuthentication();
        
        // Тест создания резюме
        testResumeCreation();
        
        // Тест генерации HTML
        testHtmlGeneration();
        
        System.out.println("=== Все тесты завершены ===");
    }
    
    private static void testAuthentication() {
        System.out.println("1. Тестирование аутентификации:");
        
        AuthenticationService authService = new AuthenticationService();
        
        // Успешная аутентификация
        boolean success = authService.authenticate("newton", "newton");
        System.out.println("   ✅ Успешный вход (newton/newton): " + success);
        System.out.println("   ✅ Пользователь аутентифицирован: " + authService.isAuthenticated());
        System.out.println("   ✅ Текущий пользователь: " + authService.getCurrentUsername());
        
        // Неуспешная аутентификация
        boolean failed = authService.authenticate("wrong", "wrong");
        System.out.println("   ❌ Неверные данные: " + failed);
        
        authService.authenticate("newton", "newton"); // Восстанавливаем вход
        System.out.println();
    }
    
    private static void testResumeCreation() {
        System.out.println("2. Тестирование создания резюме:");
        
        ResumeService resumeService = new ResumeService();
        Resume resume = resumeService.createDefaultResume();
        
        System.out.println("   ✅ Резюме создано: " + (resume != null));
        System.out.println("   ✅ Имя: " + resume.getFullName());
        System.out.println("   ✅ Email: " + resume.getEmail());
        System.out.println("   ✅ Тема: " + resume.getTheme());
        System.out.println("   ✅ Образований: " + resume.getEducation().size());
        System.out.println("   ✅ Опыт работы: " + resume.getWorkExperience().size());
        System.out.println("   ✅ Навыков: " + resume.getSkills().size());
        
        // Тест валидации
        String validation = resumeService.validateResume(resume);
        System.out.println("   ✅ Валидация прошла: " + (validation == null));
        
        // Тест смены темы
        resumeService.updateTheme(resume, "classic");
        System.out.println("   ✅ Тема изменена на: " + resumeService.getThemeDisplayName(resume.getTheme()));
        
        System.out.println();
    }
    
    private static void testHtmlGeneration() {
        System.out.println("3. Тестирование генерации HTML:");
        
        try {
            ResumeService resumeService = new ResumeService();
            HtmlGeneratorService htmlService = new HtmlGeneratorService();
            
            Resume resume = resumeService.createDefaultResume();
            
            // Тест современной темы
            resumeService.updateTheme(resume, "modern");
            String modernPath = htmlService.generateHtml(resume, "test-modern.html");
            System.out.println("   ✅ Современная тема: " + modernPath);
            
            // Тест классической темы
            resumeService.updateTheme(resume, "classic");
            String classicPath = htmlService.generateHtml(resume, "test-classic.html");
            System.out.println("   ✅ Классическая тема: " + classicPath);
            
            // Проверяем, что файлы созданы
            java.io.File modernFile = new java.io.File(modernPath);
            java.io.File classicFile = new java.io.File(classicPath);
            
            System.out.println("   ✅ Файл современной темы существует: " + modernFile.exists());
            System.out.println("   ✅ Файл классической темы существует: " + classicFile.exists());
            
            if (modernFile.exists()) {
                System.out.println("   ✅ Размер файла современной темы: " + modernFile.length() + " байт");
            }
            if (classicFile.exists()) {
                System.out.println("   ✅ Размер файла классической темы: " + classicFile.length() + " байт");
            }
            
        } catch (Exception e) {
            System.out.println("   ❌ Ошибка генерации HTML: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println();
    }
}