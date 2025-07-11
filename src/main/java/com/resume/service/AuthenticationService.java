package com.resume.service;

import com.resume.model.User;

/**
 * Сервис аутентификации пользователей.
 * Обрабатывает вход в систему и проверку учетных данных.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class AuthenticationService {
    
    private static final String VALID_USERNAME = "newton";
    private static final String VALID_PASSWORD = "newton";
    
    private User currentUser;

    /**
     * Конструктор по умолчанию.
     */
    public AuthenticationService() {
        this.currentUser = null;
    }

    /**
     * Выполнить аутентификацию пользователя.
     * 
     * @param username имя пользователя
     * @param password пароль
     * @return true если аутентификация успешна, иначе false
     */
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        
        boolean isValid = VALID_USERNAME.equals(username.trim()) && 
                         VALID_PASSWORD.equals(password.trim());
        
        if (isValid) {
            currentUser = new User(username, password);
        } else {
            currentUser = null;
        }
        
        return isValid;
    }

    /**
     * Проверить, вошел ли пользователь в систему.
     * 
     * @return true если пользователь аутентифицирован, иначе false
     */
    public boolean isAuthenticated() {
        return currentUser != null;
    }

    /**
     * Получить текущего аутентифицированного пользователя.
     * 
     * @return текущий пользователь или null если не аутентифицирован
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Выполнить выход из системы.
     */
    public void logout() {
        currentUser = null;
    }

    /**
     * Проверить валидность учетных данных без входа в систему.
     * 
     * @param username имя пользователя
     * @param password пароль
     * @return true если учетные данные валидны, иначе false
     */
    public boolean validateCredentials(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        
        return VALID_USERNAME.equals(username.trim()) && 
               VALID_PASSWORD.equals(password.trim());
    }

    /**
     * Получить имя текущего пользователя.
     * 
     * @return имя пользователя или null если не аутентифицирован
     */
    public String getCurrentUsername() {
        return isAuthenticated() ? currentUser.getUsername() : null;
    }
}