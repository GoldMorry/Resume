# Resume Generator - Настройка для IntelliJ IDEA

## ✅ Полная совместимость с IDEA!

Проект полностью совместим с IntelliJ IDEA и будет работать без проблем.

## 🚀 Импорт проекта в IDEA

### 1. Открытие проекта
```
File → Open → Выберите папку проекта (где находится pom.xml)
```

### 2. Настройка SDK
- IDEA автоматически определит проект как **Maven**
- Убедитесь, что выбран **Java 8+** SDK
- `File → Project Structure → Project → Project SDK`

### 3. Автоматическая индексация
- IDEA автоматически:
  - Загрузит зависимости Maven
  - Проиндексирует исходный код
  - Настроит пути к исходникам

## ▶️ Запуск в IDEA

### Способ 1: Через конфигурацию запуска
1. `Run → Edit Configurations`
2. `+ → Application`
3. **Main class**: `com.resume.ResumeGeneratorApplication`
4. **Module**: `resume-generator`
5. **JRE**: Use module SDK
6. `Apply → OK`

### Способ 2: Быстрый запуск
- Откройте `ResumeGeneratorApplication.java`
- Нажмите зеленую стрелку рядом с `main()` методом
- Или `Ctrl+Shift+F10` (Windows/Linux) / `Cmd+Shift+R` (macOS)

### Способ 3: Maven
- Откройте Maven панель (справа)
- `Plugins → exec → exec:java`

## 🧪 Запуск тестов

### Unit тесты:
- Откройте тестовый класс в `src/test/java/`
- Нажмите зеленую стрелку у класса/метода
- Или через Maven: `test → test`

### Интеграционные тесты:
- Откройте `TestApp.java`
- Запустите как обычное приложение

## ⚙️ Настройки IDEA (опционально)

### Кодировка:
```
File → Settings → Editor → File Encodings
- Global Encoding: UTF-8
- Project Encoding: UTF-8
```

### Java версия:
```
File → Project Structure → Project
- Project language level: 8 или выше
```

### Maven:
```
File → Settings → Build → Build Tools → Maven
- Maven home directory: (автоматически)
- User settings file: (автоматически)
```

## 🎨 Swing UI в IDEA

### Отображение UI:
- ✅ **Swing полностью поддерживается** в IDEA
- ✅ **Look and Feel** настроен автоматически
- ✅ **Градиенты и эффекты** отображаются корректно

### Возможные проблемы:
- **На Linux**: Может потребоваться установка `libxss1`
- **На macOS**: UI может выглядеть чуть иначе (нормально)
- **Retina дисплеи**: IDEA автоматически масштабирует

## 🛠️ Возможные проблемы и решения

### 1. "Cannot resolve symbol" ошибки:
```
File → Invalidate Caches and Restart → Invalidate and Restart
```

### 2. Maven зависимости не загружаются:
```
Maven панель → Refresh (круглая стрелка)
или
File → Reload Maven Projects
```

### 3. Не запускается main класс:
- Проверьте, что выбран правильный SDK
- Убедитесь, что проект скомпилирован: `Build → Build Project`

### 4. Swing UI не отображается на Linux:
```bash
# Установите необходимые библиотеки
sudo apt-get install libxss1 libgconf-2-4 libxrandr2 libatk1.0-0 libgtk-3-0
```

## 📁 Структура в IDEA

```
resume-generator
├── 📁 src/main/java
│   └── 📁 com.resume
│       ├── 📁 model (4 класса)
│       ├── 📁 service (4 сервиса)
│       ├── 📁 ui (2 UI класса)
│       └── 📄 ResumeGeneratorApplication
├── 📁 src/test/java
│   └── 📁 com.resume.service (2 теста)
├── 📁 target (автогенерация Maven)
├── 📄 pom.xml
└── 📄 README.md, DEMO.md, и т.д.
```

## 🎯 Отладка в IDEA

### Точки останова:
- Поставьте breakpoint в любом методе
- Запустите в режиме Debug (`Shift+F9`)
- Используйте панель Variables для просмотра состояния

### Рекомендуемые точки для отладки:
- `AuthenticationService.authenticate()` - проверка входа
- `HtmlGeneratorService.generateHtml()` - генерация HTML
- `MainFrame` конструкторы - инициализация UI

## 🚀 Производительность в IDEA

### Heap память:
```
Help → Change Memory Settings
Рекомендуется: 2048MB для комфортной работы
```

### Индексация:
- Первый запуск может занять 1-2 минуты
- Последующие запуски мгновенные

## ✨ Дополнительные возможности IDEA

### Code completion:
- ✅ **Автодополнение** работает на 100%
- ✅ **Import suggestions** для всех классов
- ✅ **JavaDoc** отображается при наведении

### Рефакторинг:
- `Shift+F6` - переименование
- `Ctrl+Alt+M` - выделение в метод
- `F6` - перемещение классов

### Git integration:
```
VCS → Import into Version Control → Create Git Repository
```

## 🎉 Заключение

**Проект готов к работе в IDEA без дополнительных настроек!**

- ✅ Maven автоматически настроится
- ✅ Зависимости загрузятся автоматически  
- ✅ Swing UI будет отображаться корректно
- ✅ Все функции IDEA (отладка, рефакторинг) работают
- ✅ Горячие клавиши и автодополнение активны

**Просто откройте папку проекта в IDEA и все заработает!** 🚀