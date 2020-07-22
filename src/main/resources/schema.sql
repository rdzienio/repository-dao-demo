create table SDA_USER
(
    pesel varchar(20),
    name varchar(50),
    assigned_course varchar(120),
    price double,
    payed boolean,
    constraint SDA_USER_pk
        primary key (pesel)
);