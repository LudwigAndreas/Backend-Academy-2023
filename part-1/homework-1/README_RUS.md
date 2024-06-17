# Tinkoff Backend Academy homework 1

Простая консольная CRUD база данных магазина.

## Contents

1. [Information](#information)
2. [Part 1](#part-1-implementation)

## Information

**Название проекта**: Домашнее задание Tinkoff Backend Academy 1 - Простая база данных в памяти

**Описание**:
Этот проект представляет собой простую CRUD (Создание, Чтение, Обновление, Удаление) базу данных в памяти для магазина, реализованную как консольное приложение. Оно позволяет пользователям управлять базовой информацией о продуктах, включая SKU, название, цену и количество. Приложение предназначено для работы в командной строке, ожидая пользовательских команд и выполняя их соответствующим образом.

**Используемые технологии**:

**Язык программирования**: Java 17
**Инструменты разработки**: IntelliJ IDEA
**Контроль версий**: Git

**Ключевые особенности**:

**Создание продукта**: Добавление новых продуктов с уникальным SKU.
**Чтение продуктов**: Отображение списка всех продуктов в табличном формате.
Обновление продукта: Изменение деталей существующего продукта по его SKU.
**Удаление продукта**: Удаление продукта из базы данных по его SKU.
**Команда выхода**: Корректное завершение работы приложения.

**Детали реализации**:
**Хранение данных**: Вся информация о продуктах хранится в памяти (внешние базы данных не используются).
**Пользовательский ввод**: Команды считываются из консольного ввода.
**Валидация**: Обеспечивает уникальность SKU и проверяет, что он состоит только из цифр и заглавных латинских букв.

**Инструкции по использованию**:
Запустите приложение, запустив программу на Java.
Введите команды, как описано в разделе реализации.
Следуйте структуре команд для выполнения различных операций.
Завершите работу программы, введя exit.

**Цели проекта**:
Изучить и реализовать CRUD операции в консольном приложении.
Понять и эффективно управлять хранением данных в памяти.
Практиковать валидацию ввода и обработку ошибок в Java.

## Part 1. Implementation

Простая CRUD база данных магазина:
Написать консольную программу, которая хранит и извлекает основную информацию о товарах: артикул, название, цена и количество.

Данные хранить в памяти.

Программа ждёт, пока пользователь введет в консоли команду.
После выполнения одной команды, ждёт следующую.
Если пользоваетль ввёл exit, программа завершается.

Пользователь может ввести 5 команд:

1) ```create $артикул $название $цена $количество```
    - добавить новый товар

2) ```read```
    - Вывести на экран список всех ранее добавленных товаров
      (всю информацию в виде таблицы)

3) ```update $артикул $название $цена $количество```
    - обновить информацию о товаре с заданным артикулом

4) ```delete $артикул```
    - удалить заданный товар из БД

5) ```exit```
    - завершить программу

Обязательные требования:

- Артикул должен быть уникальным. Можно добавить только один товар с одним артикулом.
- Артикул может содержать только цифры и латинские буквы в Upper Case.

Опционально:

- Название может состоять из нескольких слов (через пробел) в кавычках.