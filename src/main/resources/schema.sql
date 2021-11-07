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
  
DROP PROCEDURE IF EXISTS NEW_NORMAL.insertVenueData;
DROP PROCEDURE IF EXISTS NEW_NORMAL.insertVenueTypeInfoData;

CREATE PROCEDURE insertVenueTypeInfoData(in inVenueType varchar(50), in inCapacity int)
	INSERT INTO NEW_NORMAL.VENUE_TYPE_INFO(venue_type, capacity)
	SELECT inVenueType, inCapacity 
	FROM NEW_NORMAL.VENUE_TYPE_INFO
	WHERE NOT EXISTS (SELECT * FROM NEW_NORMAL.VENUE_TYPE_INFO WHERE venue_type=inVenueType)
    LIMIT 1;

CREATE PROCEDURE insertVenueData(in inBuilding varchar(50), in inVenueType varchar(50), in inVenueLevel int, in inRoomNumber int)
	INSERT INTO NEW_NORMAL.VENUE(building, venue_type, level, room_number)
	SELECT inBuilding, inVenueType, inVenueLevel, inRoomNumber
	FROM NEW_NORMAL.VENUE
	WHERE NOT EXISTS (SELECT 1 FROM NEW_NORMAL.VENUE WHERE building=inBuilding AND venue_type=inVenueType AND level=inVenueLevel AND room_number=inRoomNumber)
    LIMIT 1;

CALL insertVenueTypeInfoData('SR','50');
CALL insertVenueTypeInfoData('GSR','5');

call insertVenueData('SCIS','SR','1','1');
call insertVenueData('SCIS','SR','1','2');
call insertVenueData('SCIS','SR','1','3');
call insertVenueData('SCIS','SR','1','4');
call insertVenueData('SCIS','SR','1','5');
call insertVenueData('SCIS','GSR','1','6');
call insertVenueData('SCIS','GSR','1','7');
call insertVenueData('SCIS','GSR','1','8');
call insertVenueData('SCIS','GSR','1','9');
call insertVenueData('SCIS','GSR','1','10');

call insertVenueData('SCIS','SR','2','1');
call insertVenueData('SCIS','SR','2','2');
call insertVenueData('SCIS','SR','2','3');
call insertVenueData('SCIS','SR','2','4');
call insertVenueData('SCIS','SR','2','5');
call insertVenueData('SCIS','GSR','2','6');
call insertVenueData('SCIS','GSR','2','7');
call insertVenueData('SCIS','GSR','2','8');
call insertVenueData('SCIS','GSR','2','9');
call insertVenueData('SCIS','GSR','2','10');

call insertVenueData('SCIS','SR','3','1');
call insertVenueData('SCIS','SR','3','2');
call insertVenueData('SCIS','SR','3','3');
call insertVenueData('SCIS','SR','3','4');
call insertVenueData('SCIS','SR','3','5');
call insertVenueData('SCIS','GSR','3','6');
call insertVenueData('SCIS','GSR','3','7');
call insertVenueData('SCIS','GSR','3','8');
call insertVenueData('SCIS','GSR','3','9');
call insertVenueData('SCIS','GSR','3','10');

call insertVenueData('SCIS','SR','4','1');
call insertVenueData('SCIS','SR','4','2');
call insertVenueData('SCIS','SR','4','3');
call insertVenueData('SCIS','SR','4','4');
call insertVenueData('SCIS','SR','4','5');
call insertVenueData('SCIS','GSR','4','6');
call insertVenueData('SCIS','GSR','4','7');
call insertVenueData('SCIS','GSR','4','8');
call insertVenueData('SCIS','GSR','4','9');
call insertVenueData('SCIS','GSR','4','10');

call insertVenueData('SOE','SR','1','1');
call insertVenueData('SOE','SR','1','2');
call insertVenueData('SOE','SR','1','3');
call insertVenueData('SOE','SR','1','4');
call insertVenueData('SOE','SR','1','5');
call insertVenueData('SOE','GSR','1','6');
call insertVenueData('SOE','GSR','1','7');
call insertVenueData('SOE','GSR','1','8');
call insertVenueData('SOE','GSR','1','9');
call insertVenueData('SOE','GSR','1','10');

call insertVenueData('SOE','SR','2','1');
call insertVenueData('SOE','SR','2','2');
call insertVenueData('SOE','SR','2','3');
call insertVenueData('SOE','SR','2','4');
call insertVenueData('SOE','SR','2','5');
call insertVenueData('SOE','GSR','2','6');
call insertVenueData('SOE','GSR','2','7');
call insertVenueData('SOE','GSR','2','8');
call insertVenueData('SOE','GSR','2','9');
call insertVenueData('SOE','GSR','2','10');

call insertVenueData('SOE','SR','3','1');
call insertVenueData('SOE','SR','3','2');
call insertVenueData('SOE','SR','3','3');
call insertVenueData('SOE','SR','3','4');
call insertVenueData('SOE','SR','3','5');
call insertVenueData('SOE','GSR','3','6');
call insertVenueData('SOE','GSR','3','7');
call insertVenueData('SOE','GSR','3','8');
call insertVenueData('SOE','GSR','3','9');
call insertVenueData('SOE','GSR','3','10');

call insertVenueData('SOE','SR','4','1');
call insertVenueData('SOE','SR','4','2');
call insertVenueData('SOE','SR','4','3');
call insertVenueData('SOE','SR','4','4');
call insertVenueData('SOE','SR','4','5');
call insertVenueData('SOE','GSR','4','6');
call insertVenueData('SOE','GSR','4','7');
call insertVenueData('SOE','GSR','4','8');
call insertVenueData('SOE','GSR','4','9');
call insertVenueData('SOE','GSR','4','10');

