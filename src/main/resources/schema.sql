CREATE TABLE IF NOT EXISTS t_users
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(200) unique,
    password VARCHAR(200),
    role     VARCHAR(200)
);
CREATE TABLE IF NOT EXISTS t_project
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name           VARCHAR(200) unique,
    description    VARCHAR(200),
    created        date,
    status_changed date,
    status         VARCHAR(200),
    creator        BIGINT REFERENCES t_users (id) ON DELETE SET NULL
);
CREATE TABLE IF NOT EXISTS t_task
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name           VARCHAR(200) unique,
    description    VARCHAR(200),
    created        DATE,
    STATUS_CHANGED DATE,
    status         VARCHAR(200),
    Task_Type      VARCHAR(200),
    project_id     BIGINT REFERENCES t_project (id) ON DELETE CASCADE,
    creator        BIGINT REFERENCES t_users (id) ON DELETE SET NULL
);

