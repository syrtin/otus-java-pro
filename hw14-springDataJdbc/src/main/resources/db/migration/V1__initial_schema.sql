drop table if exists client;
drop table if exists address;
drop table if exists phone;

create table client
(
    id              bigserial   not null primary key,
    name            varchar(50) not null
);

create table address
(
    id              bigserial    not null primary key,
    street          varchar(100) not null ,
    client_id       bigint       not null references  client
);

create table phone
(
    id              bigserial   not null primary key,
    phone_number    varchar(50) not null,
    client_id       bigint      not null,
    foreign key (client_id) references client (id) on delete cascade
);
