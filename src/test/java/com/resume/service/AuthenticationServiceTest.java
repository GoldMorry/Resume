package com.resume.service;

import com.resume.model.User;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit тесты для AuthenticationService.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class AuthenticationServiceTest {
    
    private AuthenticationService authService;

    @Before
    public void setUp() {
        authService = new AuthenticationService();
    }

    @Test
    public void testSuccessfulAuthentication() {
        // Тест успешной аутентификации
        boolean result = authService.authenticate("newton", "newton");
        assertTrue("Аутентификация должна быть успешной", result);
        assertTrue("Пользователь должен быть аутентифицирован", authService.isAuthenticated());
        assertEquals("Имя пользователя должно совпадать", "newton", authService.getCurrentUsername());
    }

    @Test
    public void testFailedAuthenticationWrongUsername() {
        // Тест неудачной аутентификации с неверным именем пользователя
        boolean result = authService.authenticate("wronguser", "newton");
        assertFalse("Аутентификация должна быть неудачной", result);
        assertFalse("Пользователь не должен быть аутентифицирован", authService.isAuthenticated());
        assertNull("Имя пользователя должно быть null", authService.getCurrentUsername());
    }

    @Test
    public void testFailedAuthenticationWrongPassword() {
        // Тест неудачной аутентификации с неверным паролем
        boolean result = authService.authenticate("newton", "wrongpassword");
        assertFalse("Аутентификация должна быть неудачной", result);
        assertFalse("Пользователь не должен быть аутентифицирован", authService.isAuthenticated());
        assertNull("Имя пользователя должно быть null", authService.getCurrentUsername());
    }

    @Test
    public void testAuthenticationWithNullValues() {
        // Тест аутентификации с null значениями
        boolean result1 = authService.authenticate(null, "newton");
        assertFalse("Аутентификация с null username должна быть неудачной", result1);
        
        boolean result2 = authService.authenticate("newton", null);
        assertFalse("Аутентификация с null password должна быть неудачной", result2);
        
        boolean result3 = authService.authenticate(null, null);
        assertFalse("Аутентификация с null значениями должна быть неудачной", result3);
    }

    @Test
    public void testAuthenticationWithEmptyValues() {
        // Тест аутентификации с пустыми значениями
        boolean result1 = authService.authenticate("", "newton");
        assertFalse("Аутентификация с пустым username должна быть неудачной", result1);
        
        boolean result2 = authService.authenticate("newton", "");
        assertFalse("Аутентификация с пустым password должна быть неудачной", result2);
        
        boolean result3 = authService.authenticate("  ", "  ");
        assertFalse("Аутентификация с пробелами должна быть неудачной", result3);
    }

    @Test
    public void testLogout() {
        // Тест выхода из системы
        authService.authenticate("newton", "newton");
        assertTrue("Пользователь должен быть аутентифицирован", authService.isAuthenticated());
        
        authService.logout();
        assertFalse("После выхода пользователь не должен быть аутентифицирован", authService.isAuthenticated());
        assertNull("После выхода имя пользователя должно быть null", authService.getCurrentUsername());
    }

    @Test
    public void testValidateCredentials() {
        // Тест валидации учетных данных без входа
        assertTrue("Корректные учетные данные должны быть валидными", 
                  authService.validateCredentials("newton", "newton"));
        
        assertFalse("Некорректные учетные данные не должны быть валидными", 
                   authService.validateCredentials("wrong", "wrong"));
        
        assertFalse("Пользователь не должен быть аутентифицирован после валидации", 
                   authService.isAuthenticated());
    }

    @Test
    public void testGetCurrentUser() {
        // Тест получения текущего пользователя
        assertNull("Изначально текущий пользователь должен быть null", 
                  authService.getCurrentUser());
        
        authService.authenticate("newton", "newton");
        User currentUser = authService.getCurrentUser();
        assertNotNull("После аутентификации пользователь не должен быть null", currentUser);
        assertEquals("Имя пользователя должно совпадать", "newton", currentUser.getUsername());
    }

    @Test
    public void testCaseSensitivity() {
        // Тест чувствительности к регистру
        assertFalse("Аутентификация должна быть чувствительна к регистру", 
                   authService.authenticate("NEWTON", "newton"));
        
        assertFalse("Аутентификация должна быть чувствительна к регистру пароля", 
                   authService.authenticate("newton", "NEWTON"));
    }

    @Test
    public void testWhitespaceHandling() {
        // Тест обработки пробелов
        assertTrue("Пробелы должны обрезаться", 
                  authService.authenticate("  newton  ", "  newton  "));
        
        assertTrue("Пользователь должен быть аутентифицирован", authService.isAuthenticated());
    }
}