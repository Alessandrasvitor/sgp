
alter table user
    ADD fl_ativo boolean default false not null;

alter table user
    ADD checker_code varchar(10) default '0123456789' not null;