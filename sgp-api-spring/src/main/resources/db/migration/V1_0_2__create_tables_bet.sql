
create table IF NOT EXISTS lottery (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     user_id int(4) not null,
     type varchar(100) not null,
     bet varchar(100) not null UNIQUE,
     lottery_date varchar(10),
     result_date varchar(10),
     result varchar(100),
     paid_out double,
     won double,
     FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO functionality_user (user_id, functionality) VALUES
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'LOTTERY');
