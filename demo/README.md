# Spring Security Demo

## Запуск

1. Создать базу данных:

```sql
CREATE DATABASE testdb;
```

2. Добавить в настройку запуска IntelliJ переменные окружения:

```text
DB_USERNAME=ваш_логин_MySQL
DB_PASSWORD=ваш_пароль_MySQL
```

3. Запустить класс `DemoApplication`.

Приложение будет доступно по адресу:

```text
http://localhost:8080
```

## Тестовые пользователи

При запуске `DataInitializer` проверяет базу. Если ролей и пользователей нет, он создаёт:

```text
admin / 12345678 / ROLE_ADMIN
user / 12345678 / ROLE_USER
```

Существующие пользователи не изменяются.