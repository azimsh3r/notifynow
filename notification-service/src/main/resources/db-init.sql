create table if not exists "user"
(
    id                bigint generated always as identity
    primary key,
    username          varchar not null,
    email             varchar not null,
    password          varchar not null,
    organization_name varchar,
    role              varchar,
    status            varchar,
    created_at        timestamp,
    updated_at        timestamp
);

create table if not exists contact
(
    id           bigint generated always as identity
    primary key,
    name         varchar not null,
    email        varchar not null,
    phone_number varchar not null,
    "group"      varchar,
    status       varchar,
    created_at   timestamp,
    updated_at   timestamp,
    extra_info   varchar,
    user_id      bigint
    references "user"
);

create table if not exists template
(
    id         bigint generated always as identity
    primary key,
    name       varchar not null,
    content    varchar not null,
    created_at timestamp,
    updated_at timestamp,
    status     varchar,
    variables  varchar,
    user_id    bigint
    references "user"
);

create table if not exists notification
(
    id          bigint generated always as identity,
    receiver_id bigint
    references contact,
    template_id bigint
    references template,
    status      varchar not null
);
