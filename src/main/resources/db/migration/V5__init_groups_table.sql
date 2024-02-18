create table groups
(
    id          int primary key auto_increment,
    description varchar(100) not null,
    done        bit
);
alter table tasks add column group_id int null;
alter table tasks add foreign key (group_id) references groups (id);