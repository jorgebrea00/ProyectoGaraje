create schema motowama;

use motowama;

create table cliente (
	id int primary key not null auto_increment,
	dni VARCHAR (10) not null,
	email varchar(20) unique,
	nombre varchar(20),
	apellido varchar(20),
	telefono int
);

create table autenticacion (
	id int primary key not null auto_increment,
	id_cliente int not null,
	email varchar(30) not null,
	pass_hash varchar(20),
	foreign key (email) references cliente(email) ON UPDATE CASCADE ON DELETE CASCADE,
	foreign key (id_cliente) references cliente(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table sesion (
	id int primary key not null auto_increment,
	inicio_sesion datetime,
	fin_sesion datetime,
	id_cliente int not null,
	foreign key (id_cliente) references cliente(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table vehiculo (
	id int primary key not null auto_increment,
	id_cliente int not null,
	matricula varchar (15) unique not null, 
	tipo_vehiculo varchar(20),	
	marca varchar(20),
	modelo varchar(30),
	año int,
	foreign key (id_cliente) references cliente(id) ON UPDATE CASCADE ON DELETE CASCADE
);

create table cita (
	id int primary key not null auto_increment,
	id_cliente int,
	fecha_hora datetime,
	foreign key (id_cliente) references cliente(id) ON UPDATE CASCADE ON DELETE CASCADE
);





