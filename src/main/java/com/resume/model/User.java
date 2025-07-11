package com.resume.model;

/**
 * Модель пользователя для системы аутентификации.
 * Представляет пользователя с логином и паролем.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class User {
    private String username;
    private String password;

    /**
     * Конструктор по умолчанию.
     */
    public User() {
    }

    /**
     * Конструктор с параметрами.
     * 
     * @param username имя пользователя
     * @param password пароль пользователя
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Получить имя пользователя.
     * 
     * @return имя пользователя
     */
    public String getUsername() {
        return username;
    }

    /**
     * Установить имя пользователя.
     * 
     * @param username имя пользователя
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Получить пароль пользователя.
     * 
     * @return пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Установить пароль пользователя.
     * 
     * @param password пароль пользователя
     */
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        User user = (User) obj;
        return username != null ? username.equals(user.username) : user.username == null;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "'}";
    }
}