# --- First database schema

# --- !Ups

create table person (
  id                        bigint not null,
  name                      varchar(255),
  surName                   varchar(255),
  email                     varchar(255),
  birthday                  varchar(255),

  constraint pk_computer primary key (id))
;

create sequence person_seq start with 1000;

# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists person;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists person_seq;

