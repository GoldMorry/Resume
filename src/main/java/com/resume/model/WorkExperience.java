package com.resume.model;

/**
 * Модель опыта работы, представляющая информацию о трудовой деятельности.
 * Содержит данные о компании, должности, периоде работы и обязанностях.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class WorkExperience {
    private String company;
    private String position;
    private String startDate;
    private String endDate;
    private String description;
    private boolean currentJob;

    /**
     * Конструктор по умолчанию.
     */
    public WorkExperience() {
        this.currentJob = false;
    }

    /**
     * Конструктор с параметрами.
     * 
     * @param company название компании
     * @param position должность
     * @param startDate дата начала работы
     * @param endDate дата окончания работы
     * @param description описание обязанностей
     */
    public WorkExperience(String company, String position, String startDate, 
                         String endDate, String description) {
        this.company = company;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.currentJob = false;
    }

    /**
     * Получить название компании.
     * 
     * @return название компании
     */
    public String getCompany() {
        return company;
    }

    /**
     * Установить название компании.
     * 
     * @param company название компании
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Получить должность.
     * 
     * @return должность
     */
    public String getPosition() {
        return position;
    }

    /**
     * Установить должность.
     * 
     * @param position должность
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Получить дату начала работы.
     * 
     * @return дата начала работы
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Установить дату начала работы.
     * 
     * @param startDate дата начала работы
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Получить дату окончания работы.
     * 
     * @return дата окончания работы
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Установить дату окончания работы.
     * 
     * @param endDate дата окончания работы
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Получить описание обязанностей.
     * 
     * @return описание обязанностей
     */
    public String getDescription() {
        return description;
    }

    /**
     * Установить описание обязанностей.
     * 
     * @param description описание обязанностей
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Проверить, является ли работа текущей.
     * 
     * @return true если текущая работа, иначе false
     */
    public boolean isCurrentJob() {
        return currentJob;
    }

    /**
     * Установить флаг текущей работы.
     * 
     * @param currentJob флаг текущей работы
     */
    public void setCurrentJob(boolean currentJob) {
        this.currentJob = currentJob;
    }

    @Override
    public String toString() {
        return "WorkExperience{" +
                "company='" + company + '\'' +
                ", position='" + position + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", currentJob=" + currentJob +
                '}';
    }
}