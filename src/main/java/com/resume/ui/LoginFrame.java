package com.resume.ui;

import com.resume.service.AuthenticationService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Экран входа в систему.
 * Обеспечивает аутентификацию пользователя перед доступом к приложению.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class LoginFrame extends JFrame {
    
    private static final Color PRIMARY_COLOR = new Color(102, 126, 234);
    private static final Color SECONDARY_COLOR = new Color(118, 75, 162);
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color WHITE = Color.WHITE;
    private static final Color DARK_GRAY = new Color(51, 51, 51);
    private static final Color LIGHT_GRAY = new Color(128, 128, 128);
    
    private AuthenticationService authService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private MainFrame mainFrame;

    /**
     * Конструктор экрана входа.
     * 
     * @param authService сервис аутентификации
     */
    public LoginFrame(AuthenticationService authService) {
        this.authService = authService;
        initializeUI();
        setupEventHandlers();
    }

    /**
     * Инициализировать пользовательский интерфейс.
     */
    private void initializeUI() {
        setTitle("Resume Generator - Вход в систему");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Основная панель с градиентом
        JPanel mainPanel = createGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Создаем центральную панель для формы
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Добавляем нижнюю панель с информацией
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }

    /**
     * Создать панель с градиентным фоном.
     */
    private JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = new GradientPaint(
                    0, 0, PRIMARY_COLOR,
                    getWidth(), getHeight(), SECONDARY_COLOR
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    /**
     * Создать центральную панель с формой входа.
     */
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));
        
        // Заголовок
        JLabel titleLabel = new JLabel("Resume Generator");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel subtitleLabel = new JLabel("Создайте профессиональное резюме");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(WHITE);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Иконка
        JLabel iconLabel = createIconLabel();
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Панель формы
        JPanel formPanel = createFormPanel();
        
        centerPanel.add(iconLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(titleLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(subtitleLabel);
        centerPanel.add(Box.createVerticalStrut(40));
        centerPanel.add(formPanel);
        
        return centerPanel;
    }

    /**
     * Создать иконку приложения.
     */
    private JLabel createIconLabel() {
        JLabel iconLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Рисуем иконку резюме
                g2d.setColor(WHITE);
                g2d.fillRoundRect(10, 5, 40, 50, 5, 5);
                
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(10, 5, 40, 50, 5, 5);
                
                // Линии текста
                g2d.drawLine(15, 15, 45, 15);
                g2d.drawLine(15, 20, 35, 20);
                g2d.drawLine(15, 30, 45, 30);
                g2d.drawLine(15, 35, 40, 35);
                g2d.drawLine(15, 45, 35, 45);
            }
        };
        iconLabel.setPreferredSize(new Dimension(60, 60));
        return iconLabel;
    }

    /**
     * Создать панель с формой входа.
     */
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setOpaque(false);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setMaximumSize(new Dimension(300, 250));
        
        // Панель входа
        JPanel loginPanel = createLoginInputPanel();
        
        // Статус
        statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.YELLOW);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        formPanel.add(loginPanel);
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(statusLabel);
        
        return formPanel;
    }

    /**
     * Создать панель с полями ввода.
     */
    private JPanel createLoginInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        
        // Поле имени пользователя
        JLabel userLabel = new JLabel("Имя пользователя:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        userLabel.setForeground(WHITE);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        usernameField = new JTextField();
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHITE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Поле пароля
        JLabel passLabel = new JLabel("Пароль:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        passLabel.setForeground(WHITE);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(WHITE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Кнопка входа
        loginButton = createLoginButton();
        
        inputPanel.add(userLabel);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(usernameField);
        inputPanel.add(Box.createVerticalStrut(15));
        inputPanel.add(passLabel);
        inputPanel.add(Box.createVerticalStrut(5));
        inputPanel.add(passwordField);
        inputPanel.add(Box.createVerticalStrut(20));
        inputPanel.add(loginButton);
        
        return inputPanel;
    }

    /**
     * Создать кнопку входа.
     */
    private JButton createLoginButton() {
        JButton button = new JButton("Войти");
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(PRIMARY_COLOR);
        button.setBackground(WHITE);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Эффект при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BACKGROUND_COLOR);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(WHITE);
            }
        });
        
        return button;
    }

    /**
     * Создать нижнюю панель с информацией.
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        
        JLabel helpLabel = new JLabel("Учетные данные по умолчанию:");
        helpLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        helpLabel.setForeground(WHITE);
        helpLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel credentialsLabel = new JLabel("Пользователь: newton | Пароль: newton");
        credentialsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        credentialsLabel.setForeground(WHITE);
        credentialsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        bottomPanel.add(helpLabel);
        bottomPanel.add(Box.createVerticalStrut(5));
        bottomPanel.add(credentialsLabel);
        
        return bottomPanel;
    }

    /**
     * Настроить обработчики событий.
     */
    private void setupEventHandlers() {
        loginButton.addActionListener(new LoginActionListener());
        
        // Обработка Enter в полях
        KeyListener enterKeyListener = new EnterKeyListener();
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    /**
     * Выполнить вход в систему.
     */
    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            showStatus("Пожалуйста, заполните все поля", Color.YELLOW);
            return;
        }
        
        boolean authenticated = authService.authenticate(username, password);
        
        if (authenticated) {
            showStatus("Вход выполнен успешно!", Color.GREEN);
            
            // Небольшая задержка для показа сообщения
            Timer timer = new Timer(1000, e -> openMainApplication());
            timer.setRepeats(false);
            timer.start();
        } else {
            showStatus("Неверные учетные данные!", Color.YELLOW);
            passwordField.setText("");
            passwordField.requestFocus();
        }
    }

    /**
     * Показать статусное сообщение.
     */
    private void showStatus(String message, Color color) {
        statusLabel.setText(message);
        statusLabel.setForeground(color);
    }

    /**
     * Открыть основное приложение.
     */
    private void openMainApplication() {
        if (mainFrame == null) {
            mainFrame = new MainFrame(authService);
        }
        mainFrame.setVisible(true);
        this.setVisible(false);
    }

    /**
     * Обработчик кнопки входа.
     */
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            performLogin();
        }
    }

    /**
     * Обработчик клавиши Enter.
     */
    private class EnterKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                performLogin();
            }
        }
        
        @Override
        public void keyTyped(KeyEvent e) {}
        
        @Override
        public void keyReleased(KeyEvent e) {}
    }
}