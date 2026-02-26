# TechZad (TehZad)

Тестовое задание — небольшой REST API для управления проектами и задачами с аутентификацией и ролевым доступом.

## Стек технологий

| Компонент | Технология |
|-----------|------------|
| **Язык** | Java 17 |
| **Фреймворк** | Spring Boot 3.2.2 |
| **Сборка** | Maven |
| **База данных** | H2 (in-memory) |
| **ORM** | Spring Data JPA |
| **Безопасность** | Spring Security (BCrypt, DaoAuthenticationProvider) |
| **Валидация** | Jakarta Validation |
| **Дополнительно** | Lombok |

## Функциональность

- **Пользователи** — создание, удаление, поиск, просмотр профиля
- **Проекты** — иерархия (дерево) родительских и дочерних проектов, CRUD
- **Задачи** — CRUD, статусы (NEW, IN_PROGRESS, DONE), типы (MANAGER, ENGINEER)

**Роли:** MANAGER (только чтение), ADMIN (чтение и изменение).

## Запуск

```bash
mvn spring-boot:run
```

Приложение будет доступно на `http://localhost:8080`.

**Тестовые учётные записи** (из `data.sql`):
- `admin` — права ADMIN
- `manager` — права MANAGER

## API

| Метод | Endpoint | Описание |
|-------|----------|----------|
| **Пользователи** | | |
| POST | `/api/v1/users` | Создать пользователя |
| GET | `/api/v1/users` | Список пользователей |
| GET | `/api/v1/users/{id}` | Получить пользователя |
| GET | `/api/v1/users/myprofile` | Текущий пользователь |
| GET | `/api/v1/users/search?text=` | Поиск пользователей |
| DELETE | `/api/v1/users/{id}` | Удалить пользователя |
| **Проекты** | | |
| GET | `/api/v1/projects` | Список проектов |
| GET | `/api/v1/projects/{id}` | Получить проект |
| GET | `/api/v1/projects/parents` | Корневые проекты |
| POST | `/api/v1/projects` | Создать проект |
| PATCH | `/api/v1/projects/{id}` | Обновить проект |
| DELETE | `/api/v1/projects/{id}` | Удалить проект |
| **Задачи** | | |
| GET | `/api/v1/tasks/{id}` | Получить задачу |
| POST | `/api/v1/tasks` | Создать задачу |
| PATCH | `/api/v1/tasks/{id}` | Обновить задачу |
| DELETE | `/api/v1/tasks/{id}` | Удалить задачу |
| POST | `/api/v1/tasks/{id}/newStatus?status=` | Изменить статус |

Аутентификация: form login или HTTP Basic.

## Структура проекта

```
src/main/java/com/example/TehZad/
├── config/           # SecurityConfig
├── exceptions/       # ErrorHandler, NotFoundException
├── user/             # UserController, UserService, UserRepository, DTO
├── project/          # ProjectController, ProjectService, ProjectRepository, DTO
├── task/             # TaskController, TaskService, TaskRepository, DTO
└── validation/       # Validation groups (CreationInfo, UpdationInfo)
```

## База данных

H2 in-memory (`jdbc:h2:mem:techZad`). Схема и тестовые данные загружаются при старте из `schema.sql` и `data.sql`.

Консоль H2 (если включена в профиле): `http://localhost:8080/h2-console`
