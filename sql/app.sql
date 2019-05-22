CREATE TABLE en_cliente (
  id_cliente INTEGER NOT NULL PRIMARY KEY,
  nome       TEXT    NOT NULL
);

CREATE TABLE en_filme (
  id_filme        INTEGER NOT NULL PRIMARY KEY,
  data_lancamento DATE    NOT NULL,
  nome            TEXT    NOT NULL,
  descricao       TEXT    NOT NULL
);

CREATE TABLE en_aluguel (
  id_aluguel   INTEGER NOT NULL PRIMARY KEY,
  id_cliente   INTEGER NOT NULL REFERENCES en_cliente (id_cliente),

  data_aluguel DATE    NOT NULL,
  valor        FLOAT   NOT NULL
);

CREATE TABLE re_aluguel_filme (
  id_filme   INTEGER NOT NULL  REFERENCES en_filme (id_filme),
  id_aluguel INTEGER NOT NULL REFERENCES en_aluguel (id_aluguel),
  PRIMARY KEY (id_filme, id_aluguel)
);


CREATE SEQUENCE "seq_en_cliente" START 1;
CREATE SEQUENCE "seq_en_filme" START 1;
CREATE SEQUENCE "seq_en_aluguel" START 1;


INSERT INTO en_filme (id_filme, data_lancamento, nome, descricao) VALUES (nextval('seq_en_filme'), '1980-06-20', 'Lagoa Azul', 'Drama/Filme para adolescentes');
INSERT INTO en_cliente (id_cliente, nome) VALUES (nextval('seq_en_cliente'), 'Ana');
INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), now(), 8.90);

INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));

INSERT INTO en_filme (id_filme, data_lancamento, nome, descricao) VALUES (nextval('seq_en_filme'), '2008-01-18', 'Eu Sou a Lenda', 'Thriller/Drama');
INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), now(), 6.90);
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));

INSERT INTO en_cliente (id_cliente, nome) VALUES (nextval('seq_en_cliente'), 'João');
INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), now(), 4.90);
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));

INSERT INTO en_cliente (id_cliente, nome) VALUES (nextval('seq_en_cliente'), 'Maria');

INSERT INTO en_cliente (id_cliente, nome) VALUES (nextval('seq_en_cliente'), 'Felippe');

INSERT INTO en_filme (id_filme, data_lancamento, nome, descricao) VALUES (nextval('seq_en_filme'), '2018-04-26', 'Vingadores: Guerra Infinita', 'Fantasia/Filme de ficção científica');
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));

INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), now(), 15.55);
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));

INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), now(), 7.99);
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));
INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), '2000-05-09', 5.99);
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));
INSERT INTO en_aluguel(id_aluguel, id_cliente, data_aluguel, valor) VALUES (nextval('seq_en_aluguel'), currval('seq_en_cliente'), '2015-05-09', 1.99);
INSERT INTO re_aluguel_filme(id_filme, id_aluguel) VALUES (currval('seq_en_filme'), currval('seq_en_aluguel'));

