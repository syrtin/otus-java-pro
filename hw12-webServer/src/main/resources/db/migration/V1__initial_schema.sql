drop table if exists client;
drop table if exists address;
drop table if exists phone;

-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence address_seq start with 1 increment by 1;
create sequence client_seq start with 1 increment by 1;
create sequence phone_seq start with 1 increment by 1;

create table address
(
    id  bigint not null primary key,
    street  varchar(100),
    client_id bigint
);

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id  bigint,
    foreign key (address_id) references address (id)
);

create table phone
(
    id  bigint not null primary key,
    phone_number varchar(50),
    client_id bigint,
    foreign key (client_id) references client (id)
);
