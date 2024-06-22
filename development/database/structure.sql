drop database if exists boilerplate_db;
create database boilerplate_db;
use boilerplate_db;

create table users (
    id int not null auto_increment primary key,
    name varchar(255) not null,
    username varchar(255) not null,
    password varchar(255) not null,
    email varchar(255)
);

# -----------------------------------------------------------------------------

insert into users(name, username, password, email) values ('Administrator', 'admin', 'password', 'admin@test.com');