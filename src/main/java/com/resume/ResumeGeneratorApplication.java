package com.resume;

import com.resume.service.AuthenticationService;
import com.resume.ui.LoginFrame;

import javax.swing.*;
import javax.swing.UIManager;

/**
 * Главный класс приложения Resume Generator.
 * Точка входа в приложение, инициализирует сервисы и запускает UI.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class ResumeGeneratorApplication {

    /**
     * Главный метод приложения.
     * 
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        // Устанавливаем Look and Feel для лучшего внешнего вида
        try {
            // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            System.err.println("Не удалось установить системный Look and Feel: " + e.getMessage());
            // Продолжаем работу с дефолтным Look and Feel
        }
        
        // Запускаем приложение в EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowApplication();
            } catch (Exception e) {
                System.err.println("Ошибка при запуске приложения: " + e.getMessage());
                e.printStackTrace();
                
                // Показываем пользователю сообщение об ошибке
                JOptionPane.showMessageDialog(
                    null,
                    "Произошла ошибка при запуске приложения:\n" + e.getMessage(),
                    "Ошибка запуска",
                    JOptionPane.ERROR_MESSAGE
                );
                
                System.exit(1);
            }
        });
    }

    /**
     * Создать и показать главное окно приложения.
     */
    private static void createAndShowApplication() {
        System.out.println("Запуск Resume Generator v1.0");
        System.out.println("=================================");
        
        // Инициализируем сервисы
        AuthenticationService authService = new AuthenticationService();
        
        // Создаем и показываем экран входа
        LoginFrame loginFrame = new LoginFrame(authService);
        loginFrame.setVisible(true);
        
        System.out.println("Приложение запущено успешно!");
        System.out.println("Для входа используйте: newton/newton");
        
        // Выводим информацию о системе
        printSystemInfo();
    }

    /**
     * Вывести информацию о системе в консоль.
     */
    private static void printSystemInfo() {
        System.out.println("\nИнформация о системе:");
        System.out.println("- Java версия: " + System.getProperty("java.version"));
        System.out.println("- Java vendor: " + System.getProperty("java.vendor"));
        System.out.println("- ОС: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.out.println("- Архитектура: " + System.getProperty("os.arch"));
        System.out.println("- Пользователь: " + System.getProperty("user.name"));
        System.out.println("- Рабочая директория: " + System.getProperty("user.dir"));
        
        // Проверяем поддержку Desktop API
        if (java.awt.Desktop.isDesktopSupported()) {
            System.out.println("- Desktop API поддерживается");
        } else {
            System.out.println("- Desktop API НЕ поддерживается");
        }
        
        System.out.println("=================================\n");
    }
}