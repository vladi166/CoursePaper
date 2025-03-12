# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»
## Документация

1. [Текст задания](https://github.com/netology-code/aqa-qamid-diplom/blob/main/README.md)
2. [План автоматизации](https://github.com/vladi166/CoursePaper/blob/main/docs/Plan.md)

## **Инструкция для запуска автотестов**

1. Клонировать проект: https://github.com/vladi166/CoursePaper
2. Открыть проект в IntelliJ IDEA
3. Запустить Docker Desktop

### Подключение SUT к MySQL

1. В терминале 1 в корне проекта запустить контейнеры: `docker-compose up -d`
2. В терминале 2 запустить приложение: `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3366/app`
3. Проверить, что приложение успешно запустилось (ввести URL в браузере Сhrome: `http://localhost/8080`)
4. В Windows запуск тестов `./gradlew clean test "-Dselenide.headless=true" "-Ddatasource.url=jdbc:mysql://localhost:3366/app_db"`
5. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
6. Закрыть отчёт в терминале 3:   `CTRL + C --> y --> Enter`
7. Остановить приложение в терминале 2: `CTRL + C`
8. Остановить контейнеры в терминале 1:`docker-compose down`
