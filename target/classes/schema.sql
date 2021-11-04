CREATE TABLE IF NOT EXISTS NEW_NORMAL.USER(
	id int primary key auto_increment,
    username varchar(50), 
    password varchar(200),
    email varchar(50),
    authorities varchar(50),
    verified char(1),
    vaccinated char(1),
    vaccination_date date
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.VERIFICATION(
	verification_code varchar(50) primary key,
    user_id int,
    foreign key (user_id) references user(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.EVENT(
	id int primary key auto_increment,
	organizer_id int,
	title varchar(50),
	description varchar(200),
	visibility varchar(10),
	start_datetime datetime,
	stop_datetime datetime,
	max_subscribers int,
	num_subscribers int,
	invite_code varchar(50),
	location varchar(50),
	vaccination_required char(1),
	insert_ts timestamp,
	foreign key (organizer_id) references user(id) on delete cascade
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.SUBSCRIPTION(
	event_id int,
	user_id int,
	primary key(event_id, user_id),
	foreign key (event_id) references event(id),
	foreign key (user_id) references user(id)
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.ADMIN_CONFIG(
	id int auto_increment,
	property varchar(50),
	value varchar(50),
	update_ts timestamp,
	primary key (id)
);

INSERT INTO  NEW_NORMAL.ADMIN_CONFIG (property, value)
SELECT * FROM (SELECT 'allow adjacent booking', 'Y') AS temp
WHERE NOT EXISTS (select property from NEW_NORMAL.ADMIN_CONFIG where property='allow adjacent booking')
LIMIT 1;

INSERT INTO  NEW_NORMAL.ADMIN_CONFIG (property, value)
SELECT * FROM (SELECT 'max capacity', '1.0') AS temp
WHERE NOT EXISTS (select property from NEW_NORMAL.ADMIN_CONFIG where property='max capacity')
LIMIT 1;

CREATE TABLE IF NOT EXISTS NEW_NORMAL.VENUETYPEINFO(
	venue_name varchar(50),
	capacity int,
	primary key (venue_name)
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.VENUE(
	id int primary key auto_increment,
	building varchar(50),
	venue_type varchar(50),
	venue_level int,
	room_number int,
	foreign key (venue_type) references venuetypeinfo(venue_name)
);

