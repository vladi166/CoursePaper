# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»
## Документация

1. [Текст задания](https://github.com/netology-code/aqa-qamid-diplom/blob/main/README.md)
2. [План автоматизации](https://github.com/vladi166/CoursePaper/blob/main/docs/Plan.md)
3. [Отчет о проведенном тестировании](https://github.com/vladi166/CoursePaper/blob/main/docs/Report.md)
4. [Отчет о проведенной автоматизации](https://github.com/vladi166/CoursePaper/blob/main/docs/Summary.md)

## **Инструкция для запуска автотестов**

1. Клонировать проект: https://github.com/vladi166/CoursePaper
2. Запустить Docker Desktop
3. Открыть проект в IntelliJ IDEA

### Подключение SUT к MySQL

1. Перейти во вкладку **Services**, запустить Docker, нажав на кнопку play
2. Запустить файл [‎docker-compose.yml](%E2%80%8Edocker-compose.yml), так же запуск возможен в терминале, командой `docker start coursepaper-mysql-1`
2. В терминале 2 запустить приложение: `java -jar .\artifacts\aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3366/app`
3. Проверить, что приложение успешно запустилось (ввести URL в браузере Сhrome: `http://localhost/8080`)
4. В Windows запуск тестов `./gradlew clean test "-Dselenide.headless=true" "-Ddatasource.url=jdbc:mysql://localhost:3366/app_db"`
5. Создать отчёт Allure и открыть в браузере `.\gradlew allureServe`
6. Закрыть отчёт в терминале 3:   `CTRL + C --> y --> Enter`
7. Остановить приложение в терминале 2: `CTRL + C`
8. Остановить контейнеры в терминале 1:`docker stop coursepaper-mysql-1`
