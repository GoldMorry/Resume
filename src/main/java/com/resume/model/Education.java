package com.resume.model;

/**
 * Модель образования, представляющая информацию об учебном заведении.
 * Содержит данные об учреждении, степени, специальности и периоде обучения.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class Education {
    private String institution;
    private String degree;
    private String major;
    private String startYear;
    private String endYear;
    private String gpa;

    /**
     * Конструктор по умолчанию.
     */
    public Education() {
    }

    /**
     * Конструктор с параметрами.
     * 
     * @param institution учебное заведение
     * @param degree степень/квалификация
     * @param major специальность
     * @param startYear год начала обучения
     * @param endYear год окончания обучения
     */
    public Education(String institution, String degree, String major, 
                    String startYear, String endYear) {
        this.institution = institution;
        this.degree = degree;
        this.major = major;
        this.startYear = startYear;
        this.endYear = endYear;
    }

    /**
     * Получить название учебного заведения.
     * 
     * @return название учебного заведения
     */
    public String getInstitution() {
        return institution;
    }

    /**
     * Установить название учебного заведения.
     * 
     * @param institution название учебного заведения
     */
    public void setInstitution(String institution) {
        this.institution = institution;
    }

    /**
     * Получить степень/квалификацию.
     * 
     * @return степень/квалификация
     */
    public String getDegree() {
        return degree;
    }

    /**
     * Установить степень/квалификацию.
     * 
     * @param degree степень/квалификация
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * Получить специальность.
     * 
     * @return специальность
     */
    public String getMajor() {
        return major;
    }

    /**
     * Установить специальность.
     * 
     * @param major специальность
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Получить год начала обучения.
     * 
     * @return год начала обучения
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * Установить год начала обучения.
     * 
     * @param startYear год начала обучения
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    /**
     * Получить год окончания обучения.
     * 
     * @return год окончания обучения
     */
    public String getEndYear() {
        return endYear;
    }

    /**
     * Установить год окончания обучения.
     * 
     * @param endYear год окончания обучения
     */
    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    /**
     * Получить средний балл.
     * 
     * @return средний балл
     */
    public String getGpa() {
        return gpa;
    }

    /**
     * Установить средний балл.
     * 
     * @param gpa средний балл
     */
    public void setGpa(String gpa) {
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "Education{" +
                "institution='" + institution + '\'' +
                ", degree='" + degree + '\'' +
                ", major='" + major + '\'' +
                ", startYear='" + startYear + '\'' +
                ", endYear='" + endYear + '\'' +
                '}';
    }
}