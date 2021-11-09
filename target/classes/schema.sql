use new_normal;

create table if not exists user(
    id int primary key auto_increment,
    username varchar(50), 
    password varchar(200),
    email varchar(50),
    authorities varchar(50),
    verified char(1),
    vaccinated char(1),
    vaccination_date date
);

create table if not exists verification(
    verification_code varchar(50) primary key,
    user_id int,
    foreign key (user_id) references user(id) on delete cascade
);

create table if not exists venue_type_info(
    venue_type varchar(50),
    capacity int,
    primary key (venue_type)
);

create table if not exists venue(
    id int primary key auto_increment,
    building varchar(50),
    venue_type varchar(50),
    level int,
    room_number int,
    foreign key (venue_type) references venue_type_info(venue_type) on delete cascade
);

create table if not exists event(
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

create table if not exists subscription(
    event_id int,
    user_id int,
    primary key(event_id, user_id),
    foreign key (event_id) references event(id),
    foreign key (user_id) references user(id)
);

create table if not exists admin_config(
    id int auto_increment,
    property varchar(50),
    value varchar(50),
    update_ts timestamp,
    primary key (id)
);

insert into  admin_config (property, value)
select * from (select 'allow adjacent booking', 'Y') as temp
where not exists (select property from admin_config where property='allow adjacent booking')
limit 1;

insert into  admin_config (property, value)
select * from (select 'max capacity', '1.0') as temp
where not exists (select property from admin_config where property='max capacity')
limit 1;
  
drop procedure if exists insertvenuedata;
drop procedure if exists insertvenuetypeinfodata;

create procedure insertvenuetypeinfodata (in invenuetype varchar(50), in incapacity int)
insert into venue_type_info (venue_type, capacity)
select * from ( select invenuetype , incapacity) as temp
where not exists (select * from venue_type_info where venue_type=invenuetype)
limit 1;

create procedure insertvenuedata (in inbuilding varchar(50), in invenuetype varchar(50), in invenuelevel int, in inroomnumber int)
    insert into venue (building, venue_type, level, room_number)
    select * from ( select inbuilding, invenuetype , invenuelevel, inroomnumber) as temp
    where not exists (select * from venue where building=inbuilding and venue_type=invenuetype and level=invenuelevel and room_number=inroomnumber)
    limit 1;

CALL INSERTVENUETYPEINFODATA('SR','50');
CALL INSERTVENUETYPEINFODATA('GSR','5');

call insertvenuedata('SCIS','SR','1','1');
call insertvenuedata('SCIS','SR','1','2');
call insertvenuedata('SCIS','SR','1','3');
call insertvenuedata('SCIS','SR','1','4');
call insertvenuedata('SCIS','SR','1','5');
call insertvenuedata('SCIS','GSR','1','6');
call insertvenuedata('SCIS','GSR','1','7');
call insertvenuedata('SCIS','GSR','1','8');
call insertvenuedata('SCIS','GSR','1','9');
call insertvenuedata('SCIS','GSR','1','10');

call insertvenuedata('SCIS','SR','2','1');
call insertvenuedata('SCIS','SR','2','2');
call insertvenuedata('SCIS','SR','2','3');
call insertvenuedata('SCIS','SR','2','4');
call insertvenuedata('SCIS','SR','2','5');
call insertvenuedata('SCIS','GSR','2','6');
call insertvenuedata('SCIS','GSR','2','7');
call insertvenuedata('SCIS','GSR','2','8');
call insertvenuedata('SCIS','GSR','2','9');
call insertvenuedata('SCIS','GSR','2','10');

call insertvenuedata('SCIS','SR','3','1');
call insertvenuedata('SCIS','SR','3','2');
call insertvenuedata('SCIS','SR','3','3');
call insertvenuedata('SCIS','SR','3','4');
call insertvenuedata('SCIS','SR','3','5');
call insertvenuedata('SCIS','GSR','3','6');
call insertvenuedata('SCIS','GSR','3','7');
call insertvenuedata('SCIS','GSR','3','8');
call insertvenuedata('SCIS','GSR','3','9');
call insertvenuedata('SCIS','GSR','3','10');

call insertvenuedata('SCIS','SR','4','1');
call insertvenuedata('SCIS','SR','4','2');
call insertvenuedata('SCIS','SR','4','3');
call insertvenuedata('SCIS','SR','4','4');
call insertvenuedata('SCIS','SR','4','5');
call insertvenuedata('SCIS','GSR','4','6');
call insertvenuedata('SCIS','GSR','4','7');
call insertvenuedata('SCIS','GSR','4','8');
call insertvenuedata('SCIS','GSR','4','9');
call insertvenuedata('SCIS','GSR','4','10');

call insertvenuedata('SOE','SR','1','1');
call insertvenuedata('SOE','SR','1','2');
call insertvenuedata('SOE','SR','1','3');
call insertvenuedata('SOE','SR','1','4');
call insertvenuedata('SOE','SR','1','5');
call insertvenuedata('SOE','GSR','1','6');
call insertvenuedata('SOE','GSR','1','7');
call insertvenuedata('SOE','GSR','1','8');
call insertvenuedata('SOE','GSR','1','9');
call insertvenuedata('SOE','GSR','1','10');

call insertvenuedata('SOE','SR','2','1');
call insertvenuedata('SOE','SR','2','2');
call insertvenuedata('SOE','SR','2','3');
call insertvenuedata('SOE','SR','2','4');
call insertvenuedata('SOE','SR','2','5');
call insertvenuedata('SOE','GSR','2','6');
call insertvenuedata('SOE','GSR','2','7');
call insertvenuedata('SOE','GSR','2','8');
call insertvenuedata('SOE','GSR','2','9');
call insertvenuedata('SOE','GSR','2','10');

call insertvenuedata('SOE','SR','3','1');
call insertvenuedata('SOE','SR','3','2');
call insertvenuedata('SOE','SR','3','3');
call insertvenuedata('SOE','SR','3','4');
call insertvenuedata('SOE','SR','3','5');
call insertvenuedata('SOE','GSR','3','6');
call insertvenuedata('SOE','GSR','3','7');
call insertvenuedata('SOE','GSR','3','8');
call insertvenuedata('SOE','GSR','3','9');
call insertvenuedata('SOE','GSR','3','10');

call insertvenuedata('SOE','SR','4','1');
call insertvenuedata('SOE','SR','4','2');
call insertvenuedata('SOE','SR','4','3');
call insertvenuedata('SOE','SR','4','4');
call insertvenuedata('SOE','SR','4','5');
call insertvenuedata('SOE','GSR','4','6');
call insertvenuedata('SOE','GSR','4','7');
call insertvenuedata('SOE','GSR','4','8');
call insertvenuedata('SOE','GSR','4','9');
call insertvenuedata('SOE','GSR','4','10');