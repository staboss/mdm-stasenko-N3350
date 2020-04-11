# Формирование счета на оплату услуг

### Основные требования для запуска проекта:
- [Python 3.+](https://www.python.org/downloads/) для запуска скрипта
- [docx2pdf](https://pypi.org/project/docx2pdf/) парсинг .docx в .pdf
- [docxtpl](https://pypi.org/project/docxtpl/) парсинг в шаблон .docx
- [Java 8+](https://www.java.com/en/download/) для запуска jar-файлов

Для создания счета нужно запустить скрипт: `./script.sh`

Можно сформировать другой счет на оплату, изменив внутри скрипта `[ARGS]` в `java -jar JAR_FILE [ARGS]`

**(!) Следует придерживаться указанного формата передаваемых данных (!)**
