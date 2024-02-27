create table if not exists resource
(
    id         serial    not null
        constraint "Resource_pk"
            primary key,
    area       varchar   not null,
    name       varchar,
    quota      integer   not null,
    start_date timestamp not null,
    end_date   timestamp not null
);

create table if not exists request
(
    id           serial    not null
        constraint "Request_pk"
            primary key,
    name         varchar   not null,
    sure_name    varchar   not null,
    patronymic   varchar   not null,
    type         varchar   not null,
    issue_date   timestamp not null,
    series       integer   not null,
    number       integer   not null,
    request_date timestamp not null,
    status       varchar
);

create table if not exists asking_resource
(
    id          serial
        constraint asking_resource_pk
            primary key,
    resource_id integer not null
        constraint asking_resource_resource_id_fk
            references resource
            on update cascade on delete cascade,
    count       integer not null,
    status      varchar,
    request_id  integer
        constraint asking_resource_request_id_fk
            references request
            on update cascade on delete cascade
);