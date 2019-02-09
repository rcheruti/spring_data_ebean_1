create table car (
  id                            integer auto_increment not null,
  name                          varchar(255),
  date                          date,
  color                         varchar(255),
  person_id                     integer,
  created                       timestamp not null,
  modified                      timestamp not null,
  constraint pk_car primary key (id)
);

create table person (
  id                            integer auto_increment not null,
  name                          varchar(255),
  birthdate                     date,
  sex                           boolean default false not null,
  version                       timestamp not null,
  created                       timestamp not null,
  modified                      timestamp not null,
  constraint pk_person primary key (id)
);

create index ix_car_person_id on car (person_id);
alter table car add constraint fk_car_person_id foreign key (person_id) references person (id) on delete restrict on update restrict;

