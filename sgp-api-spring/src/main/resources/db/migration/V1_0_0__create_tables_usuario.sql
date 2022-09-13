
create table user (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     name varchar(50) not null,
     email varchar(100) not null UNIQUE,
     password varchar(255) not null,
     user_hash_code varchar(255) not null,
     init varchar(50),
     token varchar(255)
);

create table functionality_user (
     user_id int(4) not null,
     functionality varchar(50) not null,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO user (name, email, password, user_hash_code) VALUES
     ('Super Admin', 'sansyro@email.com', '123456', '123456'),
     ('Visitante', 'email@email.com', '123456', '123456');

INSERT INTO functionality_user (user_id, functionality) VALUES
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'BASIC'),
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'USER'),
  ((SELECT u.id FROM user u WHERE u.email = 'email@email.com' ), 'BASIC');