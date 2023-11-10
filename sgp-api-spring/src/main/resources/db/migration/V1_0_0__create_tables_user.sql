
create table IF NOT EXISTS user (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     name varchar(50) not null,
     email varchar(100) not null UNIQUE,
     password varchar(255) not null,
     user_hash_code varchar(255) not null,
     start_view varchar(50) not null,
     token  varchar(255),
     phone_number varchar(20) not null,
     checker_code varchar(10) default '0123456789' not null,
     fl_active boolean default false not null
);

create table IF NOT EXISTS functionality_user (
     user_id int(4) not null,
     functionality varchar(50) not null,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO user (id, name, email, password, user_hash_code, start_view, phone_number) VALUES
     (9999999, 'Super Admin', 'sansyro@email.com', '123456', '123456', 'instituition', '0123456789'),
     (9999998,'Visitante', 'email@email.com', '123456', '123456', 'home', '0123456789');

INSERT INTO functionality_user (user_id, functionality) VALUES
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'HOME'),
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'USER'),
  ((SELECT u.id FROM user u WHERE u.email = 'email@email.com' ), 'HOME');