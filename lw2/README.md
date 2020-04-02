# Обработка и тарификация трафика NetFlow

### Основные требования для запуска проекта:
- [Java](https://www.java.com/en/download/) для работы приложения
- [Gradle](https://docs.gradle.org/current/userguide/installation.html#installing_with_a_package_manager) для сборки проекта

(!) На всякий случай уже собран исполняемый `.jar-файл` в папке `/build/libs/lw2.jar`

Чтобы его запустить, нужно всего лишь вбить следующие команды в терминале:
- `cd build/libs/`
- `java -jar lw2.jar`

Можно попробовать собрать проект самому с помощью `gradle`:
- `gradle build`
- `gradle jar`
- запускаем исполняемый `.jar-файл` как было показано выше

### Пример работы приложения:
![Example NetFlow](https://github.com/staboss/mdm-stasenko-N3350/blob/master/lw2/src/main/resources/example.png)

### График зависимости объема трафика от времени
![Plot](https://github.com/staboss/mdm-stasenko-N3350/blob/master/lw2/src/main/resources/plot.png)
