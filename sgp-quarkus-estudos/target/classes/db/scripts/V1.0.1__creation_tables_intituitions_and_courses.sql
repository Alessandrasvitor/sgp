CREATE TABLE instituition (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     name VARCHAR(50) NOT NULL,
     address VARCHAR(100) NOT NULL UNIQUE,
     quantity DECIMAL(2,1)
);

CREATE TABLE course (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     instituition_id BIGINT NOT NULL,
     user_id BIGINT NOT NULL DEFAULT 0,
     name VARCHAR(50) NOT NULL,
     description text NOT NULL,
     notation DECIMAL(3,1),
     status VARCHAR(50) NOT NULL,
     category VARCHAR(50) NOT NULL,
     priority INT NOT NULL,
     start_date datetime,
     end_date datetime,
     finished boolean,
    FOREIGN KEY (instituition_id) REFERENCES instituition(id)
);