create table cep (
  id                            integer auto_increment not null,
  bairro                        varchar(255),
  cep                           varchar(255),
  cidade                        varchar(255),
  complemento                   varchar(255),
  complemento2                  varchar(255),
  endereco                      varchar(255),
  uf                            varchar(255),
  constraint pk_cep primary key (id)
);

create table person (
  id                            integer auto_increment not null,
  name                          varchar(255),
  birthdate                     date,
  sex                           boolean default false not null,
  constraint pk_person primary key (id)
);

