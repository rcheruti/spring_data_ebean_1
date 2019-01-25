alter table car drop constraint if exists fk_car_person_id;
drop index if exists ix_car_person_id;

drop table if exists car;

drop table if exists person;

