# Tinkoff Backend Academy homework 2

Проект на Java с использованием сборщиков Maven и Gradle.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)
3. [Links](#links)

## Information

**Название проекта:** Домашнее задание Tinkoff Backend Academy 2 - Проект на Java с использованием сборщиков Maven и Gradle

**Описание:**
Этот проект представляет собой простое приложение на Java, созданное с использованием сборщиков Maven и Gradle. Он демонстрирует базовую настройку и структуру проекта на Java с использованием сборщиков Maven и Gradle, включая создание контроллера, сервиса и тестовых классов. Проект также включает плагины для генерации отчетов о покрытии кода и документации Javadoc во время процесса сборки.

**Используемые технологии:**

- **Язык программирования:** Java 17
- **Инструменты разработки:** IntelliJ IDEA, Maven, Gradle
- **Контроль версий:** Git

**Ключевые особенности:**

- **Maven Project:** Java проект, созданный с использованием сборщика Maven.
- **Gradle Project:** Java проект, созданный с использованием сборщика Gradle.

**Детали реализации:**

- **Controller:** Содержит REST-конечные точки для обработки HTTP-запросов.
- **Service:** Содержит бизнес-логику и методы обработки данных.
- **Test Classes:** Включает модульные тесты для класса сервиса.

**Инструкции по использованию:**

1. **Собрать Maven проект:** Выполните `mvn clean install` в директории `maven-project`.
2. **Собрать Gradle проект:** Выполните `gradlew myBuild` в директории `gradle-project`.

**Цели проекта:**

- Изучить и создать Java проекты с использованием сборщиков Maven и Gradle.
- Понять структуру Java проекта с использованием сборщиков Maven и Gradle.
- Практиковать использование плагинов для отчетов о покрытии кода и генерации документации Javadoc.

## Part 1. Implementation

1. Создать 2 проекта на mvn и gradle через start.spring.io
2. Добавить код с уже написанным контроллером, сервисом и тестом.
3. Добавить плагины jacoco-maven-plugin и maven-javadoc-plugin чтобы собирались страницы на момент сборки приложения.
4. В сервисе добавить вместо возвращаемой строки описание какой сборщик оказался проще для такой задачи и почему

## Links

1. [Почитать про BOM](https://reflectoring.io/maven-bom/)
2. [Документация по Gradle](https://docs.gradle.org/current/userguide/userguide.html)
3. [Mvn Central](https://mvnrepository.com/repos/central)
4. [Репозитории на Нексус](https://habr.com/ru/articles/339902/)
5. [Пример туториала на Gradle](https://www.baeldung.com/gradle)
6. [Пример туториала на Maven](https://www.baeldung.com/maven)
7. [Что такое JAVADOC](https://www.baeldung.com/javadoc)
8. [Пример создания собственного плагина и сравнение](https://youtu.be/21qdRgFsTy0)
