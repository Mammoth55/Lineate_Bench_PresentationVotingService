drop table if exists public.client cascade;
drop table if exists public.presentation cascade;
drop table if exists public.vote cascade;

CREATE TABLE IF NOT EXISTS public.client
(
    id              BIGSERIAL PRIMARY KEY,
    first_name      VARCHAR(64) NOT NULL,
    last_name       VARCHAR(64),
    login           VARCHAR(64) NOT NULL UNIQUE,
    password        VARCHAR(64) NOT NULL default '123456'
);

CREATE TABLE IF NOT EXISTS public.presentation
(
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(64) NOT NULL UNIQUE,
    description     TEXT,
    status          VARCHAR(64) not null default 'CREATED',
    creation_time   timestamp with time zone not null default now(),
    start_time      timestamp with time zone,
    client_id       BIGSERIAL NOT NULL REFERENCES public.client(id)
);

CREATE TABLE IF NOT EXISTS public.vote
(
    id              BIGSERIAL PRIMARY KEY,
    vote_status     VARCHAR(64) NOT NULL default 'NOT_INTERESTED',
    vote_time       timestamp with time zone not null default now(),
    client_id       BIGSERIAL NOT NULL REFERENCES public.client(id),
    presentation_id BIGSERIAL NOT NULL REFERENCES public.presentation(id)
);
