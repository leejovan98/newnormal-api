CREATE SCHEMA IF NOT EXISTS new_normal;

CREATE TABLE IF NOT EXISTS USER(
	id int primary key auto_increment,
    username varchar(50), 
    password varchar(50),
    email varchar(50),
    role varchar(10),
    verified char(1)
);


CREATE TABLE IF NOT EXISTS VERIFICATION(
	verification_code varchar(50) primary key,
    user_id int,
    foreign key (user_id) references user(id) on delete cascade
);