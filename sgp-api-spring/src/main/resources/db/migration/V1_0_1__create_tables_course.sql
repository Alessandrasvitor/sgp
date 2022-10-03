
create table instituition (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     name varchar(50) not null,
     address varchar(100) not null UNIQUE,
     quantity decimal(2,1)
);

create table course (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     instituition_id int(4) not null,
     user_id int(4) not null,
     name varchar(50) not null,
     description text not null,
     notation decimal(3,1),
     status varchar(50) not null,
     category varchar(50) not null,
     priority int not null,
     start_date datetime,
     end_date datetime,
     finished boolean,
    FOREIGN KEY (user_id) REFERENCES user(id),
    FOREIGN KEY (instituition_id) REFERENCES instituition(id)
);

INSERT INTO functionality_user (user_id, functionality) VALUES
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'INSTITUITION'),
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'COURSE');