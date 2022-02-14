CREATE TABLE IF NOT EXISTS client
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(64) NOT NULL,
    last_name       VARCHAR(64) NOT NULL,
    login           VARCHAR(64) NOT NULL UNIQUE,
    password        VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS presentation
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(64) NOT NULL,
    description     TEXT,
    creation_time   timestamp with time zone not null default now(),
    start_time      timestamp with time zone,
    user_id         BIGSERIAL REFERENCES client (id)
);
