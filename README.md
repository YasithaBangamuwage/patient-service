# patient-service

Use docker file to config postgress with pgadmin

Run below SQL scritps before running the app

create table if not exists patientservice.patient(
	id serial not null primary key,
	name varchar(255) not null,
	age int not null,
	gender int not null
);


insert into patientservice.patient(name, age, gender) values
('yasitha', 33,1),
('Owain', 3,1),
('Saman', 45,1),
('Devi', 60,0),
('Lakmali', 30,0)
