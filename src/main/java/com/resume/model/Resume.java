package com.resume.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель резюме, содержащая всю информацию о пользователе.
 * Включает персональные данные, образование, опыт работы и навыки.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class Resume {
    
    // Персональная информация
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String linkedIn;
    private String github;
    private String objective;
    
    // Профессиональная информация
    private List<Education> education;
    private List<WorkExperience> workExperience;
    private List<String> skills;
    private List<String> languages;
    private List<String> certifications;
    
    // Настройки темы
    private String theme;

    /**
     * Конструктор по умолчанию.
     */
    public Resume() {
        this.education = new ArrayList<>();
        this.workExperience = new ArrayList<>();
        this.skills = new ArrayList<>();
        this.languages = new ArrayList<>();
        this.certifications = new ArrayList<>();
        this.theme = "modern";
    }

    // Геттеры и сеттеры для персональной информации

    /**
     * Получить полное имя.
     * 
     * @return полное имя
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Установить полное имя.
     * 
     * @param fullName полное имя
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Получить email.
     * 
     * @return email адрес
     */
    public String getEmail() {
        return email;
    }

    /**
     * Установить email.
     * 
     * @param email email адрес
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Получить номер телефона.
     * 
     * @return номер телефона
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Установить номер телефона.
     * 
     * @param phone номер телефона
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Получить адрес.
     * 
     * @return адрес
     */
    public String getAddress() {
        return address;
    }

    /**
     * Установить адрес.
     * 
     * @param address адрес
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Получить LinkedIn профиль.
     * 
     * @return LinkedIn URL
     */
    public String getLinkedIn() {
        return linkedIn;
    }

    /**
     * Установить LinkedIn профиль.
     * 
     * @param linkedIn LinkedIn URL
     */
    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    /**
     * Получить GitHub профиль.
     * 
     * @return GitHub URL
     */
    public String getGithub() {
        return github;
    }

    /**
     * Установить GitHub профиль.
     * 
     * @param github GitHub URL
     */
    public void setGithub(String github) {
        this.github = github;
    }

    /**
     * Получить карьерную цель.
     * 
     * @return карьерная цель
     */
    public String getObjective() {
        return objective;
    }

    /**
     * Установить карьерную цель.
     * 
     * @param objective карьерная цель
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    // Геттеры и сеттеры для профессиональной информации

    /**
     * Получить список образования.
     * 
     * @return список образования
     */
    public List<Education> getEducation() {
        return education;
    }

    /**
     * Установить список образования.
     * 
     * @param education список образования
     */
    public void setEducation(List<Education> education) {
        this.education = education;
    }

    /**
     * Получить список опыта работы.
     * 
     * @return список опыта работы
     */
    public List<WorkExperience> getWorkExperience() {
        return workExperience;
    }

    /**
     * Установить список опыта работы.
     * 
     * @param workExperience список опыта работы
     */
    public void setWorkExperience(List<WorkExperience> workExperience) {
        this.workExperience = workExperience;
    }

    /**
     * Получить список навыков.
     * 
     * @return список навыков
     */
    public List<String> getSkills() {
        return skills;
    }

    /**
     * Установить список навыков.
     * 
     * @param skills список навыков
     */
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    /**
     * Получить список языков.
     * 
     * @return список языков
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Установить список языков.
     * 
     * @param languages список языков
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    /**
     * Получить список сертификатов.
     * 
     * @return список сертификатов
     */
    public List<String> getCertifications() {
        return certifications;
    }

    /**
     * Установить список сертификатов.
     * 
     * @param certifications список сертификатов
     */
    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }

    /**
     * Получить тему оформления.
     * 
     * @return тема оформления
     */
    public String getTheme() {
        return theme;
    }

    /**
     * Установить тему оформления.
     * 
     * @param theme тема оформления
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", theme='" + theme + '\'' +
                '}';
    }
}