# Resume Generator - Генератор резюме

Профессиональное Java приложение для создания красивых резюме в HTML формате с поддержкой различных тем оформления.

## Особенности

- 🔐 Система аутентификации (newton/newton)
- 🎨 Две красивые темы оформления (Современная и Классическая)
- 📝 Полный редактор резюме с всеми необходимыми секциями
- 🌐 Экспорт в HTML с автооткрытием в браузере
- 💼 Профессиональный UI дизайн
- 🏗️ Чистая архитектура (Model-Service-UI)
- ✅ Совместимость с Java 8+

## Системные требования

- Java 8 или выше
- Maven 3.6+
- Операционная система: Windows, macOS, Linux

## Быстрый старт

### 1. Клонирование и сборка

```bash
git clone <repository-url>
cd resume-generator
mvn clean compile
```

### 2. Запуск приложения

```bash
mvn exec:java -Dexec.mainClass="com.resume.ResumeGeneratorApplication"
```

Или создайте JAR файл:

```bash
mvn clean package
java -jar target/resume-generator-1.0.0.jar
```

### 3. Использование

1. **Вход в систему**: Используйте учетные данные `newton/newton`
2. **Редактирование**: Заполните все необходимые поля резюме
3. **Выбор темы**: Выберите одну из двух доступных тем
4. **Генерация**: Нажмите кнопку "Отправить" для создания HTML
5. **Просмотр**: Резюме автоматически откроется в браузере

## Структура приложения

```
src/
├── main/java/com/resume/
│   ├── ResumeGeneratorApplication.java    # Главный класс
│   ├── model/                             # Модели данных
│   │   ├── User.java                      # Модель пользователя
│   │   ├── Resume.java                    # Модель резюме
│   │   ├── Education.java                 # Модель образования
│   │   └── WorkExperience.java            # Модель опыта работы
│   ├── service/                           # Бизнес-логика
│   │   ├── AuthenticationService.java     # Аутентификация
│   │   ├── ResumeService.java             # Управление резюме
│   │   ├── HtmlGeneratorService.java      # Генерация HTML
│   │   └── BrowserService.java            # Работа с браузером
│   └── ui/                                # Пользовательский интерфейс
│       ├── LoginFrame.java                # Экран входа
│       └── MainFrame.java                 # Основное окно
```

## Архитектурные принципы

### Model-Service-UI разделение
- **Model**: Чистые POJO классы для данных
- **Service**: Бизнес-логика и обработка данных
- **UI**: Swing компоненты для пользовательского интерфейса

### SOLID принципы
- **S** - Каждый класс имеет одну ответственность
- **O** - Классы открыты для расширения, закрыты для модификации
- **L** - Подклассы могут заменять базовые классы
- **I** - Интерфейсы специфичны для клиентов
- **D** - Зависимость от абстракций, а не конкретных классов

## Функциональность

### Персональная информация
- Полное имя
- Email адрес
- Номер телефона
- Адрес проживания
- LinkedIn профиль
- GitHub профиль

### Профессиональная информация
- Карьерная цель
- Образование (с возможностью добавления нескольких записей)
- Опыт работы (с поддержкой текущего места работы)
- Навыки (через запятую)
- Языки программирования/общения
- Сертификаты и достижения

### Темы оформления

#### Современная тема
- Градиентный фон
- Современная типографика (Segoe UI)
- Карточный дизайн
- Цветовые акценты
- Адаптивная верстка

#### Классическая тема
- Строгий черно-белый дизайн
- Классическая типографика (Times New Roman)
- Формальное оформление
- Подходит для консервативных сфер

## Особенности реализации

### Валидация данных
- Проверка обязательных полей
- Валидация email адресов
- Информативные сообщения об ошибках

### Генерация HTML
- Встроенные CSS стили
- Семантическая HTML разметка
- Поддержка UTF-8 кодировки
- Адаптивный дизайн

### Автооткрытие в браузере
- Использование Desktop API
- Fallback через системные команды
- Поддержка Windows, macOS, Linux

## Сборка и развертывание

### Создание исполняемого JAR

```bash
mvn clean package
```

Результат: `target/resume-generator-1.0.0.jar`

### Запуск JAR файла

```bash
java -jar target/resume-generator-1.0.0.jar
```

### Системные зависимости
Приложение использует только стандартные библиотеки Java и не требует дополнительных зависимостей времени выполнения.

## Конфигурация

### Учетные данные
По умолчанию настроены учетные данные:
- **Пользователь**: `newton`
- **Пароль**: `newton`

Для изменения отредактируйте `AuthenticationService.java`

### Темы оформления
Для добавления новых тем:
1. Добавьте новый CSS в `HtmlGeneratorService.java`
2. Обновите `ResumeService.getAvailableThemes()`
3. Добавьте отображаемое название в `getThemeDisplayName()`

### Папка для сохранения
HTML файлы сохраняются в папку `generated-resumes` в рабочей директории.

## Отладка и диагностика

### Логирование
Приложение выводит диагностическую информацию в консоль:
- Информация о запуске
- Системные характеристики
- Статус операций

### Обработка ошибок
- Валидация пользовательского ввода
- Обработка ошибок файловой системы
- Graceful fallback для браузера

## Известные ограничения

1. **Аутентификация**: Статичные учетные данные (для демонстрации)
2. **Персистентность**: Данные не сохраняются между сессиями
3. **Многопользовательность**: Поддерживается только один пользователь
4. **Форматы экспорта**: Только HTML (можно расширить)

## Возможные улучшения

- [ ] Сохранение резюме в JSON/XML
- [ ] Экспорт в PDF
- [ ] Больше тем оформления
- [ ] База данных для пользователей
- [ ] Многопользовательский режим
- [ ] Предварительный просмотр в приложении
- [ ] Импорт данных из LinkedIn

## Лицензия

MIT License - свободное использование и модификация.

## Автор

Resume Generator Team  
Version 1.0.0

---

**Приятного использования! 🎉**