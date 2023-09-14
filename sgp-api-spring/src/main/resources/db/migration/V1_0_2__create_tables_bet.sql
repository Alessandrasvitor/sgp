
create table bet (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     user_id int(4) not null,
     type varchar(100) not null,
     bet varchar(100) not null UNIQUE,
     bet_date datetime,
     result_date datetime,
     result varchar(100),
     paid_out double,
     lucre double,
     FOREIGN KEY (user_id) REFERENCES user(id)
);

INSERT INTO functionality_user (user_id, functionality) VALUES
  ((SELECT u.id FROM user u WHERE u.email = 'sansyro@email.com' ), 'BET');