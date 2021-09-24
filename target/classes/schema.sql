CREATE SCHEMA IF NOT EXISTS new_normal;

CREATE TABLE IF NOT EXISTS NEW_NORMAL.USER(
	id int primary key auto_increment,
    username varchar(50), 
    password varchar(50),
    email varchar(50),
    authorities varchar(50),
    verified char(1)
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.VERIFICATION(
	verification_code varchar(50) primary key,
    user_id int,
    foreign key (user_id) references user(id) on delete cascade
);