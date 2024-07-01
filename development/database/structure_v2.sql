drop database if exists informes_backboard;
create database informes_backboard;
use informes_backboard;

create table user_platform_roles (
    id int not null auto_increment primary key,
    name varchar(50) unique not null,
    description varchar(255)
);

create table user_platform (
    id int not null auto_increment primary key,
    name varchar(255) not null,
    username varchar(255) unique not null,
    password varchar(255) not null,
    email varchar(255),
    image_url varchar(500),
    user_platform_roles_id int not null,
    foreign key (user_platform_roles_id) references user_platform_roles(id)
);

create table campus (
    id int not null auto_increment primary key,
    name varchar(255) unique not null,
    description varchar(500)
);

create table campus_codes (
    id int not null auto_increment primary key,
    code int unique not null,
    campus_id int not null,
    foreign key (campus_id) references campus(id)
);

create table grades (
    id int not null auto_increment primary key,
    name varchar(255) unique not null
);

create table modality (
    id int not null auto_increment primary key,
    name varchar(10) unique not null,
    description varchar(255)
);

create table roles (
    id int not null auto_increment primary key,
    name varchar(255) unique not null
);

create table storage_history (
    id int not null auto_increment primary key,
    created_at date unique not null,
    value float not null
);

create table courses (
    id int unsigned not null auto_increment primary key,
    keyword varchar(20) not null,
    name varchar(800) not null,
    periods int not null,
    modality_id int not null,
    campus_code_id int not null,
    grades_id int not null,
    hash_code varchar(500) not null,
    foreign key (modality_id) references modality(id),
    foreign key (campus_code_id) references campus_codes(id),
    foreign key (grades_id) references grades(id)
);

create table users (
    id int unsigned not null auto_increment primary key,
    keyword varchar(2000) not null,
    periods int not null,
    keyword_course varchar(50) not null,
    roles_id int not null,
    modality_id int not null,
    campus_code_id int not null,
    grades_id int not null,
    hash_code varchar(500) unique not null,
    foreign key (roles_id) references roles(id),
    foreign key (modality_id) references modality(id),
    foreign key (campus_code_id) references campus_codes(id),
    foreign key (grades_id) references grades(id)
);

# =============================================================================
# === Default Data
# =============================================================================

insert into user_platform_roles (id, name, description) VALUES (1, 'Admin', 'Administrator');
insert into user_platform (name, username, password, user_platform_roles_id) values ('Administrator', 'admin', 'password', 1);

insert into campus (id, name) VALUES (1, 'Mexicali');
insert into campus (id, name) VALUES (2, 'Ensenada');
insert into campus (id, name) VALUES (3, 'Tijuana');

insert into grades (id, name) VALUES (1, 'Licenciatura');
insert into grades (id, name) VALUES (2, 'Posgrado');

insert into roles (id, name) values (1, 'Estudiantes');
insert into roles (id, name) values (2, 'Docentes');

insert into modality (id, name, description) VALUES (1, 'D', 'Distancia');
insert into modality (id, name, description) VALUES (2, 'S', 'Semipresencial');
insert into modality (id, name, description) VALUES (3, 'E', 'Presencial');

insert into campus_codes (campus_id, code) VALUES (1, 1);
insert into campus_codes (campus_id, code) VALUES (1, 2);
insert into campus_codes (campus_id, code) VALUES (1, 10);
insert into campus_codes (campus_id, code) VALUES (1, 40);
insert into campus_codes (campus_id, code) VALUES (1, 80);
insert into campus_codes (campus_id, code) VALUES (1, 90);
insert into campus_codes (campus_id, code) VALUES (1, 110);
insert into campus_codes (campus_id, code) VALUES (1, 111);
insert into campus_codes (campus_id, code) VALUES (1, 123);
insert into campus_codes (campus_id, code) VALUES (1, 124);
insert into campus_codes (campus_id, code) VALUES (1, 125);
insert into campus_codes (campus_id, code) VALUES (1, 126);
insert into campus_codes (campus_id, code) VALUES (1, 140);
insert into campus_codes (campus_id, code) VALUES (1, 160);
insert into campus_codes (campus_id, code) VALUES (1, 200);
insert into campus_codes (campus_id, code) VALUES (1, 220);
insert into campus_codes (campus_id, code) VALUES (1, 222);
insert into campus_codes (campus_id, code) VALUES (1, 260);
insert into campus_codes (campus_id, code) VALUES (1, 300);
insert into campus_codes (campus_id, code) VALUES (1, 310);
insert into campus_codes (campus_id, code) VALUES (1, 335);
insert into campus_codes (campus_id, code) VALUES (1, 337);
insert into campus_codes (campus_id, code) VALUES (1, 605);
insert into campus_codes (campus_id, code) VALUES (1, 625);

insert into campus_codes (campus_id, code) VALUES (2, 20);
insert into campus_codes (campus_id, code) VALUES (2, 30);
insert into campus_codes (campus_id, code) VALUES (2, 50);
insert into campus_codes (campus_id, code) VALUES (2, 170);
insert into campus_codes (campus_id, code) VALUES (2, 290);
insert into campus_codes (campus_id, code) VALUES (2, 312);
insert into campus_codes (campus_id, code) VALUES (2, 320);
insert into campus_codes (campus_id, code) VALUES (2, 330);
insert into campus_codes (campus_id, code) VALUES (2, 615);
insert into campus_codes (campus_id, code) VALUES (2, 620);
insert into campus_codes (campus_id, code) VALUES (2, 700);
insert into campus_codes (campus_id, code) VALUES (2, 795);

insert into campus_codes (campus_id, code) VALUES (3, 70);
insert into campus_codes (campus_id, code) VALUES (3, 100);
insert into campus_codes (campus_id, code) VALUES (3, 120);
insert into campus_codes (campus_id, code) VALUES (3, 130);
insert into campus_codes (campus_id, code) VALUES (3, 150);
insert into campus_codes (campus_id, code) VALUES (3, 180);
insert into campus_codes (campus_id, code) VALUES (3, 190);
insert into campus_codes (campus_id, code) VALUES (3, 240);
insert into campus_codes (campus_id, code) VALUES (3, 280);
insert into campus_codes (campus_id, code) VALUES (3, 311);
insert into campus_codes (campus_id, code) VALUES (3, 313);
insert into campus_codes (campus_id, code) VALUES (3, 332);
insert into campus_codes (campus_id, code) VALUES (3, 334);
insert into campus_codes (campus_id, code) VALUES (3, 336);
insert into campus_codes (campus_id, code) VALUES (3, 400);
insert into campus_codes (campus_id, code) VALUES (3, 500);
insert into campus_codes (campus_id, code) VALUES (3, 626);
insert into campus_codes (campus_id, code) VALUES (3, 790);