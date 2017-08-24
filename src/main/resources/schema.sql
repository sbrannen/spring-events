drop table event if exists;
drop sequence if exists hibernate_sequence;

-- -----------------------------------------------------------------------------

-- 13 = 12 (number of entries in data.sql) + 1
create sequence hibernate_sequence start with 13 increment by 1;

create table event (
	id bigint not null,
	name varchar(30) not null,
	event_date date not null,
	location varchar(30) not null,
	primary key (id)
);
