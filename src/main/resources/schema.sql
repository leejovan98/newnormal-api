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

CREATE TABLE IF NOT EXISTS NEW_NORMAL.VENUE_TYPE_INFO(
	venue_type varchar(50),
	capacity int,
	primary key (venue_type)
);

CREATE TABLE IF NOT EXISTS NEW_NORMAL.VENUE(
	id int primary key auto_increment,
	building varchar(50),
	venue_type varchar(50),
	level int,
	room_number int,
	foreign key (venue_type) references venue_type_info(venue_type) on delete cascade
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
	venue_id int,
	vaccination_required char(1),
	insert_ts timestamp,
	foreign key (organizer_id) references user(id) on delete cascade,
	foreign key (venue_id) references venue(id) on delete cascade
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
  

DELETE FROM NEW_NORMAL.VENUE_TYPE_INFO;
DELETE FROM NEW_NORMAL.VENUE;
ALTER TABLE NEW_NORMAL.VENUE_TYPE_INFO AUTO_INCREMENT = 1;
ALTER TABLE NEW_NORMAL.VENUE AUTO_INCREMENT = 1;

INSERT INTO NEW_NORMAL.VENUE_TYPE_INFO(venue_type, capacity) VALUES
('SR','50'),
('GSR','5');

INSERT INTO NEW_NORMAL.VENUE(building, venue_type, level, room_number) VALUES
('SCIS','SR','1','1'),
('SCIS','SR','1','2'),
('SCIS','SR','1','3'),
('SCIS','SR','1','4'),
('SCIS','SR','1','5'),
('SCIS','GSR','1','6'),
('SCIS','GSR','1','7'),
('SCIS','GSR','1','8'),
('SCIS','GSR','1','9'),
('SCIS','GSR','1','10'),

('SCIS','SR','2','1'),
('SCIS','SR','2','2'),
('SCIS','SR','2','3'),
('SCIS','SR','2','4'),
('SCIS','SR','2','5'),
('SCIS','GSR','2','6'),
('SCIS','GSR','2','7'),
('SCIS','GSR','2','8'),
('SCIS','GSR','2','9'),
('SCIS','GSR','2','10'),

('SCIS','SR','3','1'),
('SCIS','SR','3','2'),
('SCIS','SR','3','3'),
('SCIS','SR','3','4'),
('SCIS','SR','3','5'),
('SCIS','GSR','3','6'),
('SCIS','GSR','3','7'),
('SCIS','GSR','3','8'),
('SCIS','GSR','3','9'),
('SCIS','GSR','3','10'),

('SCIS','SR','4','1'),
('SCIS','SR','4','2'),
('SCIS','SR','4','3'),
('SCIS','SR','4','4'),
('SCIS','SR','4','5'),
('SCIS','GSR','4','6'),
('SCIS','GSR','4','7'),
('SCIS','GSR','4','8'),
('SCIS','GSR','4','9'),
('SCIS','GSR','4','10'),

('SOE','SR','1','1'),
('SOE','SR','1','2'),
('SOE','SR','1','3'),
('SOE','SR','1','4'),
('SOE','SR','1','5'),
('SOE','GSR','1','6'),
('SOE','GSR','1','7'),
('SOE','GSR','1','8'),
('SOE','GSR','1','9'),
('SOE','GSR','1','10'),

('SOE','SR','2','1'),
('SOE','SR','2','2'),
('SOE','SR','2','3'),
('SOE','SR','2','4'),
('SOE','SR','2','5'),
('SOE','GSR','2','6'),
('SOE','GSR','2','7'),
('SOE','GSR','2','8'),
('SOE','GSR','2','9'),
('SOE','GSR','2','10'),

('SOE','SR','3','1'),
('SOE','SR','3','2'),
('SOE','SR','3','3'),
('SOE','SR','3','4'),
('SOE','SR','3','5'),
('SOE','GSR','3','6'),
('SOE','GSR','3','7'),
('SOE','GSR','3','8'),
('SOE','GSR','3','9'),
('SOE','GSR','3','10'),

('SOE','SR','4','1'),
('SOE','SR','4','2'),
('SOE','SR','4','3'),
('SOE','SR','4','4'),
('SOE','SR','4','5'),
('SOE','GSR','4','6'),
('SOE','GSR','4','7'),
('SOE','GSR','4','8'),
('SOE','GSR','4','9'),
('SOE','GSR','4','10');

