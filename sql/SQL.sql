use traveldatabase;

-- Create Queries

create table admin(
admin_email varchar(30) primary key,
admin_password varchar(20),
admin_fname varchar(30),
admin_lname varchar(30),
join_date datetime);

create table user(
user_email varchar(30) primary key,
user_password varchar(20),
user_fname varchar(30),
user_lname varchar(30),
join_date datetime);

create table file(
file_name varchar(50) not null,
file_desc varchar(500),
file_url varchar(500),
version_no varchar(50),
upload_date varchar(100),
update_date varchar(100),
primary key (file_name),
user_email varchar(60) references user(user_email)
);

create table tag (
    tag_id varchar(50) NOT NULL,
    tag_name varchar(50) NOT NULL,
    primary key (tag_id)
);

create table file_tag (
    file_name varchar(50),
    tag_id varchar(50),
    primary key (file_name, tag_id),
    FOREIGN KEY (file_name) REFERENCES file(file_name),
    FOREIGN KEY (tag_id) REFERENCES tag(tag_id)
);


-- Select Queries

SELECT * from traveldatabase.admin;
SELECT * from traveldatabase.user;
SELECT * from traveldatabase.file;
SELECT * from traveldatabase.tag;
SELECT * from traveldatabase.file_tag;

-- Drop Queries

drop table traveldatabase.admin;
drop table traveldatabase.user;
drop table traveldatabase.file;
drop table traveldatabase.tag;
drop table traveldatabase.file_tag;
