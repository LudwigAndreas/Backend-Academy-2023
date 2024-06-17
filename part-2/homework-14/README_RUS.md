# Tinkoff Backend Academy homework 14

Интеграция Keycloak и Spring Boot.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)
3. [Links](#links)

## Information

**Название проекта:** Домашнее задание Tinkoff Backend Academy 14 - Интеграция Keycloak и Spring Boot

**Описание:**
В ходе выполнения домашнего задания необходимо реализовать интеграцию Keycloak и Spring Boot. Keycloak реализует большинство стандартов авторизации и имеет широкое использование. Частично обусловленно тем что он используется в aws iam. В ходе решения домашней работы нужно получить docker-compose файл с конфигурациями keyclock и приложение которое позволяет реализовывать поведение сервиса акаунтов из предыдущего домашнего задания.

**Используемые технологии:**

- **Язык программирования:** Java 17
- **Инструменты разработки:** IntelliJ IDEA, Maven, Docker, Spring Boot, Keycloak
- **Контроль версий:** Git

**Ключевые особенности:**

- **Интеграция Keycloak и Spring Boot** - В ходе выполнения домашнего задания необходимо реализовать интеграцию Keycloak и Spring Boot.
- **Docker** - Для упрощения запуска окружения и тестов необходимо добавить файл `docker-compose.yml` и тестовые контейнеры для интеграционных тестов.
- **Авторизация** - Авторизация должна быть реализована с использованием Keycloak.
- **API** - Приложение должно предоставлять API для работы с пользователями.

**Детали реализации:**

- **Keycloak** - Для упрощения запуска окружения и тестов необходимо добавить файл `docker-compose.yml` и тестовые контейнеры для интеграционных тестов.
- **Spring Boot** - Приложение должно предоставлять API для работы с пользователями.
- **Keycloak** конфигурации в `docker/keycloak/import/realm-export.json`

**Инструкции по использованию:**

- Docker и docker-compose должны быть установлены на вашем компьютере
- Клонируйте репозиторий
- Перейдите в директорию `accounting/docker`
- Запустите следующую команду

```bash
docker-compose up
```

или

```bash
docker-compose -f docker-compose.yml up
```

- Откройте браузер и перейдите по адресу `http://localhost:8282`

- Вы должны увидеть страницу входа
- Используйте следующие учетные данные для входа
  - Имя пользователя: admin
  - Пароль: admin

- Вы должны увидеть панель управления
- Теперь вы можете запустить приложение Spring Boot
- Перейдите в директорию `accounting`
- Запустите следующую команду

```bash
mvn spring-boot:run
```

- Теперь вы можете получить доступ к приложению по адресу `http://localhost:8080/api/`

**Цели проекта:**

- Изучить интеграцию Keycloak и Spring Boot.
- Изучить принципы Docker и использовать Docker для упрощения запуска окружения и тестов.
- Изучить работу с Keycloak и Spring Boot.
- Изучить принципы авторизации и аутентификации.

## Part 1. Implementation

В ходе решения домашней работы нужно получить docker-compose файл с конфигурациями keyclock и приложение которое позволяет реализовывать поведение сервиса акаунтов из предыдущего домашнего задания. Позволяющий регистрировать пользователя в базе пользователей и кейклоке. Получать токен. Давать защищенный доступ к аккаунтам пользователей. Не защищенный для регистрации. Пользователь может редактировать только свой аккаунт.

## Links

[Хороший цикл статей](https://habr.com/ru/articles/716232/), которые могут быть полезны. Так же рекомендую разобраться чем клиентская авторизация отличается от авторизации пользователей и базовые поля в токенах авторизации.
Чтобы можно было сохранить настройки посмотрите [статью](https://www.keycloak.org/server/importExport).
[На посмотреть](https://www.baeldung.com/spring-boot-keycloak) и если возникли трудности с созданием пользователя через api [статья](https://www.appsdeveloperblog.com/keycloak-rest-api-create-a-new-user/) может помочь.