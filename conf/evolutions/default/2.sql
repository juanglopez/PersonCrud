# --- Sample dataset

# --- !Ups

insert into person (id,name,surname) values (  1,'Apple Inc.','Apple Inc.');
insert into person (id,name,surname) values (  2,'Apple Inc.2','Apple Inc.');
insert into person (id,name,surname) values (  3,'Apple Inc.3','Apple Inc.');
insert into person (id,name,surname) values (  4,'Apple Inc.4','Apple Inc.');
insert into person (id,name,surname) values (  5,'Apple Inc.5','Apple Inc.');
# --- !Downs

delete from person;
