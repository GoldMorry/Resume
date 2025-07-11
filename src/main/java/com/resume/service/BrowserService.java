package com.resume.service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;

/**
 * Сервис для работы с браузером.
 * Обеспечивает открытие HTML файлов в системном браузере.
 * 
 * @author Resume Generator Team
 * @version 1.0
 */
public class BrowserService {

    /**
     * Открыть HTML файл в браузере по умолчанию.
     * 
     * @param filePath путь к HTML файлу
     * @return true если файл успешно открыт, false в противном случае
     */
    public boolean openInBrowser(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            System.err.println("Путь к файлу не может быть пустым");
            return false;
        }
        
        try {
            File htmlFile = new File(filePath);
            
            if (!htmlFile.exists()) {
                System.err.println("Файл не найден: " + filePath);
                return false;
            }
            
            if (!Desktop.isDesktopSupported()) {
                System.err.println("Desktop API не поддерживается на данной системе");
                return false;
            }
            
            Desktop desktop = Desktop.getDesktop();
            
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                System.err.println("Браузер не поддерживается на данной системе");
                return false;
            }
            
            URI uri = htmlFile.toURI();
            desktop.browse(uri);
            
            System.out.println("Файл успешно открыт в браузере: " + filePath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Ошибка при открытии файла в браузере: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Неожиданная ошибка: " + e.getMessage());
            return false;
        }
    }

    /**
     * Проверить, поддерживается ли открытие браузера на данной системе.
     * 
     * @return true если поддерживается, false в противном случае
     */
    public boolean isBrowserSupported() {
        return Desktop.isDesktopSupported() && 
               Desktop.getDesktop().isSupported(Desktop.Action.BROWSE);
    }

    /**
     * Получить информацию о поддержке Desktop API.
     * 
     * @return строка с информацией о поддержке
     */
    public String getDesktopSupportInfo() {
        StringBuilder info = new StringBuilder();
        
        info.append("Desktop API поддерживается: ").append(Desktop.isDesktopSupported()).append("\n");
        
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            info.append("Поддержка открытия браузера: ").append(desktop.isSupported(Desktop.Action.BROWSE)).append("\n");
            info.append("Поддержка открытия файлов: ").append(desktop.isSupported(Desktop.Action.OPEN)).append("\n");
            info.append("Поддержка редактирования: ").append(desktop.isSupported(Desktop.Action.EDIT)).append("\n");
            info.append("Поддержка печати: ").append(desktop.isSupported(Desktop.Action.PRINT)).append("\n");
        }
        
        return info.toString();
    }

    /**
     * Попытаться открыть файл альтернативным способом (через системную команду).
     * Используется как fallback если Desktop API не работает.
     * 
     * @param filePath путь к файлу
     * @return true если команда выполнена успешно
     */
    public boolean openWithSystemCommand(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }
        
        try {
            String os = System.getProperty("os.name").toLowerCase();
            ProcessBuilder pb;
            
            if (os.contains("win")) {
                // Windows
                pb = new ProcessBuilder("rundll32", "url.dll,FileProtocolHandler", filePath);
            } else if (os.contains("mac")) {
                // macOS
                pb = new ProcessBuilder("open", filePath);
            } else {
                // Linux и другие Unix-подобные системы
                pb = new ProcessBuilder("xdg-open", filePath);
            }
            
            Process process = pb.start();
            int exitCode = process.waitFor();
            
            if (exitCode == 0) {
                System.out.println("Файл открыт через системную команду: " + filePath);
                return true;
            } else {
                System.err.println("Системная команда завершилась с кодом: " + exitCode);
                return false;
            }
            
        } catch (IOException e) {
            System.err.println("Ошибка выполнения системной команды: " + e.getMessage());
            return false;
        } catch (InterruptedException e) {
            System.err.println("Команда была прервана: " + e.getMessage());
            Thread.currentThread().interrupt();
            return false;
        }
    }

    /**
     * Открыть файл в браузере с автоматическим выбором метода.
     * Сначала пытается использовать Desktop API, при неудаче - системную команду.
     * 
     * @param filePath путь к файлу
     * @return true если файл успешно открыт любым из способов
     */
    public boolean openInBrowserWithFallback(String filePath) {
        // Сначала пытаемся стандартным способом
        if (openInBrowser(filePath)) {
            return true;
        }
        
        System.out.println("Desktop API не сработал, пробуем системную команду...");
        
        // Если не получилось, пытаемся через системную команду
        return openWithSystemCommand(filePath);
    }
}