
create table permissao (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     permissao varchar(50) not null UNIQUE
);

create table usuario (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     nome varchar(50) not null,
     email varchar(100) not null UNIQUE,
     senha varchar(255) not null
);

create table permissao_usuario (
     id int(4) AUTO_INCREMENT PRIMARY KEY,
     usuario_id int(4) not null,
     permissao_id int(4) not null,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id),
    FOREIGN KEY (permissao_id) REFERENCES permissao(id)
);

INSERT INTO usuario (nome, email, senha) VALUES
     ('Super Admin', 'sansyro@email.com', '55f190954d1e6cf2d16d931bb37abeac2505f79106a9c02e44bbcafeedd6c384'),
     ('Visitante', 'email@email.com', '55f190954d1e6cf2d16d931bb37abeac2505f79106a9c02e44bbcafeedd6c384');

INSERT INTO permissao (permissao) VALUES
     ('PADRAO'),
     ('USUARIO');

INSERT INTO permissao_usuario (usuario_id, permissao_id) VALUES
  ((SELECT u.id FROM usuario u WHERE u.email = 'sansyro@email.com' ), (SELECT p.id FROM permissao p WHERE p.permissao = 'PADRAO')),
  ((SELECT u.id FROM usuario u WHERE u.email = 'email@email.com' ), (SELECT p.id FROM permissao p WHERE p.permissao = 'PADRAO'));

