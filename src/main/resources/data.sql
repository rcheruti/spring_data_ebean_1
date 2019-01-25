
INSERT INTO person (name, birthdate, sex, created, modified, version) VALUES
  ('Rafael Cheruti',      '1990-12-10', 1, now(), now(), '1970-12-01' ) ,
  ('Amanda Somerville',   '1979-03-07', 0, now(), now(), '1970-12-01' ) ,
  ('Simone Simons',       '1985-01-17', 0, now(), now(), '1970-12-01' ) ,
  ('Floor Jansen',        '1981-02-21', 0, now(), now(), '1970-12-01' ) ,
  ('Tarja Turunen',       '1977-08-17', 0, now(), now(), '1970-12-01' ) ,
  ('Elize Ryd',           '1984-10-15', 0, now(), now(), '1970-12-01' ) ,
  ('Anette Olzon',        '1971-06-21', 0, now(), now(), '1970-12-01' ) ,
  ('Angela Gossow',       '1974-11-05', 0, now(), now(), '1970-12-01' ) ,
  ('Alissa White-Gluz',   '1985-07-31', 0, now(), now(), '1970-12-01' ) 
  ;

INSERT INTO car (name, date, person_id, created, modified) VALUES
  ('Car 01', '1990-12-10', 1, now(), now() ) ,
  ('Car 02', '1979-03-07', 1, now(), now() ) ,
  ('Car 03', '1985-01-17', 2, now(), now() ) ,
  ('Car 04', '1981-02-21', 3, now(), now() ) ,
  ('Car 05', '1977-08-17', 4, now(), now() ) ,
  ('Car 06', '1984-10-15', 4, now(), now() ) ,
  ('Car 07', '1971-06-21', 6, now(), now() ) ,
  ('Car 08', '1974-11-05', 6, now(), now() ) ,
  ('Car 09', '1985-07-31', 6, now(), now() ) 
  ;
