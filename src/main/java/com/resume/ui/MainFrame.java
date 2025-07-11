package com.resume.ui;

import com.resume.model.Resume;
import com.resume.model.Education;
import com.resume.model.WorkExperience;
import com.resume.service.AuthenticationService;
import com.resume.service.ResumeService;
import com.resume.service.HtmlGeneratorService;
import com.resume.service.BrowserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;

/**
 * Основное окно приложения для редактирования резюме.
 * Содержит форму для ввода всех данных резюме и возможность выбора темы.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class MainFrame extends JFrame {
    
    private static final Color PRIMARY_COLOR = new Color(102, 126, 234);
    private static final Color SECONDARY_COLOR = new Color(118, 75, 162);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color WHITE = Color.WHITE;
    private static final Color DARK_GRAY = new Color(51, 51, 51);
    
    private AuthenticationService authService;
    private ResumeService resumeService;
    private HtmlGeneratorService htmlGeneratorService;
    private BrowserService browserService;
    private Resume currentResume;
    
    // UI компоненты
    private JComboBox<String> themeComboBox;
    private JTextField fullNameField;
    private JTextField emailField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField linkedInField;
    private JTextField githubField;
    private JTextArea objectiveArea;
    private JTextArea skillsArea;
    private JTextArea languagesArea;
    private JTextArea certificationsArea;
    
    // Панели для динамических секций
    private JPanel educationPanel;
    private JPanel workExperiencePanel;
    
    private JLabel statusLabel;

    /**
     * Конструктор основного окна.
     * 
     * @param authService сервис аутентификации
     */
    public MainFrame(AuthenticationService authService) {
        this.authService = authService;
        this.resumeService = new ResumeService();
        this.htmlGeneratorService = new HtmlGeneratorService();
        this.browserService = new BrowserService();
        this.currentResume = resumeService.createDefaultResume();
        
        initializeUI();
        setupEventHandlers();
        loadResumeData();
    }

    /**
     * Инициализировать пользовательский интерфейс.
     */
    private void initializeUI() {
        setTitle("Resume Generator - Редактор резюме");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        
        // Создаем основную панель
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Заголовок
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Основная область с прокруткой
        JScrollPane scrollPane = createScrollableContent();
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Нижняя панель с кнопками
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    /**
     * Создать панель заголовка.
     */
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Левая часть с заголовком
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("Редактор резюме");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(WHITE);
        
        JLabel userLabel = new JLabel("Пользователь: " + authService.getCurrentUsername());
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(WHITE);
        
        leftPanel.add(titleLabel);
        
        // Правая часть с выбором темы
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        
        JLabel themeLabel = new JLabel("Тема:");
        themeLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        themeLabel.setForeground(WHITE);
        
        String[] themes = resumeService.getAvailableThemes();
        String[] themeDisplayNames = new String[themes.length];
        for (int i = 0; i < themes.length; i++) {
            themeDisplayNames[i] = resumeService.getThemeDisplayName(themes[i]);
        }
        
        themeComboBox = new JComboBox<>(themeDisplayNames);
        themeComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        themeComboBox.setPreferredSize(new Dimension(150, 30));
        
        rightPanel.add(themeLabel);
        rightPanel.add(Box.createHorizontalStrut(10));
        rightPanel.add(themeComboBox);
        rightPanel.add(Box.createHorizontalStrut(20));
        rightPanel.add(userLabel);
        
        headerPanel.add(leftPanel, BorderLayout.WEST);
        headerPanel.add(rightPanel, BorderLayout.EAST);
        
        return headerPanel;
    }

    /**
     * Создать прокручиваемый контент.
     */
    private JScrollPane createScrollableContent() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(BACKGROUND_COLOR);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Персональная информация
        contentPanel.add(createPersonalInfoSection());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Карьерная цель
        contentPanel.add(createObjectiveSection());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Образование
        contentPanel.add(createEducationSection());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Опыт работы
        contentPanel.add(createWorkExperienceSection());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Навыки
        contentPanel.add(createSkillsSection());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Языки
        contentPanel.add(createLanguagesSection());
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Сертификаты
        contentPanel.add(createCertificationsSection());
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        return scrollPane;
    }

    /**
     * Создать секцию персональной информации.
     */
    private JPanel createPersonalInfoSection() {
        JPanel section = createSectionPanel("Персональная информация");
        
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Полное имя
        gbc.gridx = 0; gbc.gridy = 0;
        fieldsPanel.add(new JLabel("Полное имя:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        fullNameField = createTextField();
        fieldsPanel.add(fullNameField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        emailField = createTextField();
        fieldsPanel.add(emailField, gbc);
        
        // Телефон
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Телефон:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        phoneField = createTextField();
        fieldsPanel.add(phoneField, gbc);
        
        // Адрес
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("Адрес:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        addressField = createTextField();
        fieldsPanel.add(addressField, gbc);
        
        // LinkedIn
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("LinkedIn:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        linkedInField = createTextField();
        fieldsPanel.add(linkedInField, gbc);
        
        // GitHub
        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        fieldsPanel.add(new JLabel("GitHub:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        githubField = createTextField();
        fieldsPanel.add(githubField, gbc);
        
        section.add(fieldsPanel);
        return section;
    }

    /**
     * Создать секцию карьерной цели.
     */
    private JPanel createObjectiveSection() {
        JPanel section = createSectionPanel("Карьерная цель");
        
        objectiveArea = createTextArea(4);
        JScrollPane scrollPane = new JScrollPane(objectiveArea);
        scrollPane.setPreferredSize(new Dimension(0, 100));
        
        section.add(scrollPane);
        return section;
    }

    /**
     * Создать секцию образования.
     */
    private JPanel createEducationSection() {
        JPanel section = createSectionPanel("Образование");
        
        educationPanel = new JPanel();
        educationPanel.setLayout(new BoxLayout(educationPanel, BoxLayout.Y_AXIS));
        educationPanel.setOpaque(false);
        
        JButton addEducationButton = createAddButton("Добавить образование");
        addEducationButton.addActionListener(e -> addEducationEntry());
        
        section.add(educationPanel);
        section.add(Box.createVerticalStrut(10));
        section.add(addEducationButton);
        
        return section;
    }

    /**
     * Создать секцию опыта работы.
     */
    private JPanel createWorkExperienceSection() {
        JPanel section = createSectionPanel("Опыт работы");
        
        workExperiencePanel = new JPanel();
        workExperiencePanel.setLayout(new BoxLayout(workExperiencePanel, BoxLayout.Y_AXIS));
        workExperiencePanel.setOpaque(false);
        
        JButton addWorkButton = createAddButton("Добавить опыт работы");
        addWorkButton.addActionListener(e -> addWorkExperienceEntry());
        
        section.add(workExperiencePanel);
        section.add(Box.createVerticalStrut(10));
        section.add(addWorkButton);
        
        return section;
    }

    /**
     * Создать секцию навыков.
     */
    private JPanel createSkillsSection() {
        JPanel section = createSectionPanel("Навыки");
        
        JLabel helpLabel = new JLabel("Введите навыки через запятую:");
        helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        helpLabel.setForeground(Color.GRAY);
        
        skillsArea = createTextArea(3);
        JScrollPane scrollPane = new JScrollPane(skillsArea);
        scrollPane.setPreferredSize(new Dimension(0, 80));
        
        section.add(helpLabel);
        section.add(Box.createVerticalStrut(5));
        section.add(scrollPane);
        
        return section;
    }

    /**
     * Создать секцию языков.
     */
    private JPanel createLanguagesSection() {
        JPanel section = createSectionPanel("Языки");
        
        JLabel helpLabel = new JLabel("Введите языки через запятую:");
        helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        helpLabel.setForeground(Color.GRAY);
        
        languagesArea = createTextArea(2);
        JScrollPane scrollPane = new JScrollPane(languagesArea);
        scrollPane.setPreferredSize(new Dimension(0, 60));
        
        section.add(helpLabel);
        section.add(Box.createVerticalStrut(5));
        section.add(scrollPane);
        
        return section;
    }

    /**
     * Создать секцию сертификатов.
     */
    private JPanel createCertificationsSection() {
        JPanel section = createSectionPanel("Сертификаты");
        
        JLabel helpLabel = new JLabel("Введите сертификаты через запятую:");
        helpLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        helpLabel.setForeground(Color.GRAY);
        
        certificationsArea = createTextArea(3);
        JScrollPane scrollPane = new JScrollPane(certificationsArea);
        scrollPane.setPreferredSize(new Dimension(0, 80));
        
        section.add(helpLabel);
        section.add(Box.createVerticalStrut(5));
        section.add(scrollPane);
        
        return section;
    }

    /**
     * Создать нижнюю панель с кнопками.
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        
        // Статус
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        
        JButton clearButton = createButton("Очистить", Color.GRAY);
        JButton submitButton = createButton("Отправить", PRIMARY_COLOR);
        
        clearButton.addActionListener(e -> clearForm());
        submitButton.addActionListener(e -> submitResume());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(submitButton);
        
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        
        return bottomPanel;
    }

    /**
     * Создать базовую панель секции.
     */
    private JPanel createSectionPanel(String title) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setBackground(WHITE);
        section.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(DARK_GRAY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        section.add(titleLabel);
        section.add(Box.createVerticalStrut(10));
        
        return section;
    }

    /**
     * Создать текстовое поле.
     */
    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(0, 30));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    /**
     * Создать текстовую область.
     */
    private JTextArea createTextArea(int rows) {
        JTextArea area = new JTextArea(rows, 0);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        return area;
    }

    /**
     * Создать кнопку добавления.
     */
    private JButton createAddButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setForeground(PRIMARY_COLOR);
        button.setBackground(WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
    }

    /**
     * Создать кнопку.
     */
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(WHITE);
        button.setBackground(color);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    /**
     * Настроить обработчики событий.
     */
    private void setupEventHandlers() {
        themeComboBox.addActionListener(e -> updateTheme());
    }

    /**
     * Загрузить данные резюме в форму.
     */
    private void loadResumeData() {
        if (currentResume == null) return;
        
        fullNameField.setText(currentResume.getFullName());
        emailField.setText(currentResume.getEmail());
        phoneField.setText(currentResume.getPhone());
        addressField.setText(currentResume.getAddress());
        linkedInField.setText(currentResume.getLinkedIn());
        githubField.setText(currentResume.getGithub());
        objectiveArea.setText(currentResume.getObjective());
        
        // Навыки
        if (!currentResume.getSkills().isEmpty()) {
            skillsArea.setText(String.join(", ", currentResume.getSkills()));
        }
        
        // Языки
        if (!currentResume.getLanguages().isEmpty()) {
            languagesArea.setText(String.join(", ", currentResume.getLanguages()));
        }
        
        // Сертификаты
        if (!currentResume.getCertifications().isEmpty()) {
            certificationsArea.setText(String.join(", ", currentResume.getCertifications()));
        }
        
        // Загружаем образование и опыт работы
        loadEducationEntries();
        loadWorkExperienceEntries();
        
        // Устанавливаем тему
        String[] themes = resumeService.getAvailableThemes();
        for (int i = 0; i < themes.length; i++) {
            if (themes[i].equals(currentResume.getTheme())) {
                themeComboBox.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * Загрузить записи об образовании.
     */
    private void loadEducationEntries() {
        educationPanel.removeAll();
        for (Education education : currentResume.getEducation()) {
            addEducationEntry(education);
        }
        educationPanel.revalidate();
        educationPanel.repaint();
    }

    /**
     * Загрузить записи об опыте работы.
     */
    private void loadWorkExperienceEntries() {
        workExperiencePanel.removeAll();
        for (WorkExperience work : currentResume.getWorkExperience()) {
            addWorkExperienceEntry(work);
        }
        workExperiencePanel.revalidate();
        workExperiencePanel.repaint();
    }

    /**
     * Добавить новую запись об образовании.
     */
    private void addEducationEntry() {
        Education education = resumeService.addEducation(currentResume);
        addEducationEntry(education);
        educationPanel.revalidate();
        educationPanel.repaint();
    }

    /**
     * Добавить компонент для редактирования образования.
     */
    private void addEducationEntry(Education education) {
        JPanel entryPanel = new JPanel(new GridBagLayout());
        entryPanel.setOpaque(false);
        entryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Поля образования
        JTextField institutionField = createTextField();
        JTextField degreeField = createTextField();
        JTextField majorField = createTextField();
        JTextField startYearField = createTextField();
        JTextField endYearField = createTextField();
        JTextField gpaField = createTextField();
        
        institutionField.setText(education.getInstitution());
        degreeField.setText(education.getDegree());
        majorField.setText(education.getMajor());
        startYearField.setText(education.getStartYear());
        endYearField.setText(education.getEndYear());
        gpaField.setText(education.getGpa());
        
        // Размещение полей
        gbc.gridx = 0; gbc.gridy = 0;
        entryPanel.add(new JLabel("Учреждение:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(institutionField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Степень:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(degreeField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Специальность:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(majorField, gbc);
        
        // Годы обучения в одной строке
        JPanel yearsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        yearsPanel.setOpaque(false);
        startYearField.setPreferredSize(new Dimension(80, 30));
        endYearField.setPreferredSize(new Dimension(80, 30));
        yearsPanel.add(startYearField);
        yearsPanel.add(new JLabel(" - "));
        yearsPanel.add(endYearField);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Годы:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(yearsPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Средний балл:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        gpaField.setPreferredSize(new Dimension(100, 30));
        entryPanel.add(gpaField, gbc);
        
        // Кнопка удаления
        JButton removeButton = new JButton("Удалить");
        removeButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        removeButton.setForeground(Color.RED);
        removeButton.setBackground(WHITE);
        removeButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> {
            currentResume.getEducation().remove(education);
            educationPanel.remove(entryPanel);
            educationPanel.revalidate();
            educationPanel.repaint();
        });
        
        gbc.gridx = 2; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(removeButton, gbc);
        
        // Сохранение изменений в модель
        Runnable saveChanges = () -> {
            education.setInstitution(institutionField.getText());
            education.setDegree(degreeField.getText());
            education.setMajor(majorField.getText());
            education.setStartYear(startYearField.getText());
            education.setEndYear(endYearField.getText());
            education.setGpa(gpaField.getText());
        };
        
        institutionField.addActionListener(e -> saveChanges.run());
        degreeField.addActionListener(e -> saveChanges.run());
        majorField.addActionListener(e -> saveChanges.run());
        startYearField.addActionListener(e -> saveChanges.run());
        endYearField.addActionListener(e -> saveChanges.run());
        gpaField.addActionListener(e -> saveChanges.run());
        
        educationPanel.add(entryPanel);
        educationPanel.add(Box.createVerticalStrut(10));
    }

    /**
     * Добавить новую запись об опыте работы.
     */
    private void addWorkExperienceEntry() {
        WorkExperience work = resumeService.addWorkExperience(currentResume);
        addWorkExperienceEntry(work);
        workExperiencePanel.revalidate();
        workExperiencePanel.repaint();
    }

    /**
     * Добавить компонент для редактирования опыта работы.
     */
    private void addWorkExperienceEntry(WorkExperience work) {
        JPanel entryPanel = new JPanel(new GridBagLayout());
        entryPanel.setOpaque(false);
        entryPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Поля опыта работы
        JTextField companyField = createTextField();
        JTextField positionField = createTextField();
        JTextField startDateField = createTextField();
        JTextField endDateField = createTextField();
        JTextArea descriptionArea = createTextArea(3);
        JCheckBox currentJobCheckbox = new JCheckBox("Текущая работа");
        
        companyField.setText(work.getCompany());
        positionField.setText(work.getPosition());
        startDateField.setText(work.getStartDate());
        endDateField.setText(work.getEndDate());
        descriptionArea.setText(work.getDescription());
        currentJobCheckbox.setSelected(work.isCurrentJob());
        
        // Размещение полей
        gbc.gridx = 0; gbc.gridy = 0;
        entryPanel.add(new JLabel("Компания:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(companyField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Должность:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(positionField, gbc);
        
        // Даты в одной строке
        JPanel datesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        datesPanel.setOpaque(false);
        startDateField.setPreferredSize(new Dimension(100, 30));
        endDateField.setPreferredSize(new Dimension(100, 30));
        datesPanel.add(startDateField);
        datesPanel.add(new JLabel(" - "));
        datesPanel.add(endDateField);
        datesPanel.add(Box.createHorizontalStrut(10));
        datesPanel.add(currentJobCheckbox);
        
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Период:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        entryPanel.add(datesPanel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        entryPanel.add(new JLabel("Описание:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH; gbc.weightx = 1.0; gbc.weighty = 1.0;
        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setPreferredSize(new Dimension(0, 80));
        entryPanel.add(descScrollPane, gbc);
        
        // Кнопка удаления
        JButton removeButton = new JButton("Удалить");
        removeButton.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        removeButton.setForeground(Color.RED);
        removeButton.setBackground(WHITE);
        removeButton.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        removeButton.setFocusPainted(false);
        removeButton.addActionListener(e -> {
            currentResume.getWorkExperience().remove(work);
            workExperiencePanel.remove(entryPanel);
            workExperiencePanel.revalidate();
            workExperiencePanel.repaint();
        });
        
        gbc.gridx = 2; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0; gbc.weighty = 0;
        entryPanel.add(removeButton, gbc);
        
        // Обработчик для checkbox
        currentJobCheckbox.addActionListener(e -> {
            if (currentJobCheckbox.isSelected()) {
                endDateField.setText("");
                endDateField.setEnabled(false);
            } else {
                endDateField.setEnabled(true);
            }
            work.setCurrentJob(currentJobCheckbox.isSelected());
        });
        
        // Изначальное состояние поля даты окончания
        endDateField.setEnabled(!work.isCurrentJob());
        
        // Сохранение изменений в модель
        Runnable saveChanges = () -> {
            work.setCompany(companyField.getText());
            work.setPosition(positionField.getText());
            work.setStartDate(startDateField.getText());
            work.setEndDate(endDateField.getText());
            work.setDescription(descriptionArea.getText());
        };
        
        companyField.addActionListener(e -> saveChanges.run());
        positionField.addActionListener(e -> saveChanges.run());
        startDateField.addActionListener(e -> saveChanges.run());
        endDateField.addActionListener(e -> saveChanges.run());
        
        workExperiencePanel.add(entryPanel);
        workExperiencePanel.add(Box.createVerticalStrut(10));
    }

    /**
     * Обновить тему резюме.
     */
    private void updateTheme() {
        int selectedIndex = themeComboBox.getSelectedIndex();
        String[] themes = resumeService.getAvailableThemes();
        if (selectedIndex >= 0 && selectedIndex < themes.length) {
            resumeService.updateTheme(currentResume, themes[selectedIndex]);
            showStatus("Тема изменена на: " + resumeService.getThemeDisplayName(themes[selectedIndex]));
        }
    }

    /**
     * Очистить форму.
     */
    private void clearForm() {
        int result = JOptionPane.showConfirmDialog(
            this,
            "Вы уверены, что хотите очистить все данные?",
            "Подтверждение",
            JOptionPane.YES_NO_OPTION
        );
        
        if (result == JOptionPane.YES_OPTION) {
            resumeService.clearResume(currentResume);
            loadResumeData();
            showStatus("Форма очищена");
        }
    }

    /**
     * Отправить резюме (сгенерировать HTML).
     */
    private void submitResume() {
        try {
            // Сохраняем данные из формы
            saveFormData();
            
            // Валидируем резюме
            String validationError = resumeService.validateResume(currentResume);
            if (validationError != null) {
                showStatus("Ошибка: " + validationError);
                JOptionPane.showMessageDialog(this, validationError, "Ошибка валидации", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            showStatus("Генерируется HTML...");
            
            // Генерируем HTML
            String htmlFilePath = htmlGeneratorService.generateHtml(currentResume);
            
            showStatus("HTML резюме сохранено: " + htmlFilePath);
            
            // Показываем диалог предварительного просмотра
            showPreviewDialog(htmlFilePath);
            
        } catch (IOException e) {
            String errorMessage = "Ошибка при генерации HTML: " + e.getMessage();
            showStatus(errorMessage);
            JOptionPane.showMessageDialog(this, errorMessage, "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Сохранить данные из формы в модель.
     */
    private void saveFormData() {
        currentResume.setFullName(fullNameField.getText());
        currentResume.setEmail(emailField.getText());
        currentResume.setPhone(phoneField.getText());
        currentResume.setAddress(addressField.getText());
        currentResume.setLinkedIn(linkedInField.getText());
        currentResume.setGithub(githubField.getText());
        currentResume.setObjective(objectiveArea.getText());
        
        // Навыки
        String skillsText = skillsArea.getText();
        if (!skillsText.trim().isEmpty()) {
            currentResume.setSkills(Arrays.asList(skillsText.split("\\s*,\\s*")));
        } else {
            currentResume.setSkills(new ArrayList<>());
        }
        
        // Языки
        String languagesText = languagesArea.getText();
        if (!languagesText.trim().isEmpty()) {
            currentResume.setLanguages(Arrays.asList(languagesText.split("\\s*,\\s*")));
        } else {
            currentResume.setLanguages(new ArrayList<>());
        }
        
        // Сертификаты
        String certificationsText = certificationsArea.getText();
        if (!certificationsText.trim().isEmpty()) {
            currentResume.setCertifications(Arrays.asList(certificationsText.split("\\s*,\\s*")));
        } else {
            currentResume.setCertifications(new ArrayList<>());
        }
    }

    /**
     * Показать диалог предварительного просмотра.
     */
    private void showPreviewDialog(String htmlFilePath) {
        int result = JOptionPane.showConfirmDialog(
            this,
            "HTML резюме успешно создано!\n\nХотите открыть его в браузере?",
            "Предварительный просмотр",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.INFORMATION_MESSAGE
        );
        
        if (result == JOptionPane.YES_OPTION) {
            boolean opened = browserService.openInBrowserWithFallback(htmlFilePath);
            if (!opened) {
                JOptionPane.showMessageDialog(
                    this,
                    "Не удалось автоматически открыть браузер.\nФайл сохранен по пути: " + htmlFilePath,
                    "Информация",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }

    /**
     * Показать статусное сообщение.
     */
    private void showStatus(String message) {
        statusLabel.setText(message);
        
        // Автоматически очищаем статус через 5 секунд
        Timer timer = new Timer(5000, e -> statusLabel.setText(" "));
        timer.setRepeats(false);
        timer.start();
    }
}