DROP database IF EXISTS librarydrome;
CREATE database librarydrome;
use librarydrome;

create table user (
	user_id int primary key auto_increment,
	email varchar(256) not null,
	password varchar(256) not null
);

create table movie (
	movie_id int primary key auto_increment,
	title varchar(256) not null,
	year int not null,
	availability enum('none', 'dvd', 'blu-ray', 'both') not null,
	poster_url text null
);

create table list (
	list_id int primary key auto_increment,
	title varchar(256) not null,
	user_id int,
	constraint fk_list_user_id
		foreign key (user_id)
		references user(user_id),
	constraint uq_list_title_user
		unique (title, user_id)
);

create table movie_is_on_list (
	movie_id int,
	list_id int,
	constraint fk_moil_movie_id
		foreign key (movie_id)
		references movie(movie_id),
	constraint fk_moil_list_id
		foreign key (list_id)
		references list(list_id),
	constraint pk_moil_movie_list
		primary key (movie_id, list_id)
);

create table genre (
	genre_id int primary key auto_increment,
	name varchar(256) not null unique
);

create table movie_has_genre (
	movie_id int,
	genre_id int,
	constraint fk_mhg_movie_id
		foreign key (movie_id)
		references movie(movie_id),
	constraint fk_mhg_genre_id
		foreign key (genre_id)
		references genre(genre_id),
	constraint pk_mhg_movie_genre
		primary key (movie_id, genre_id)
);

create table director (
	director_id int primary key auto_increment,
	name varchar(256) not null unique
);

create table movie_directed_by (
	movie_id int,
	director_id int,
	constraint fk_mdb_movie_id
		foreign key (movie_id)
		references movie(movie_id),
	constraint fk_mdb_director_id
		foreign key (director_id)
		references director(director_id),
	constraint pk_mdb_movie_director
		primary key (movie_id, director_id)
);

create table physical (
	physical_id int primary key auto_increment,
	movie_id int,
	format enum('dvd', 'blu-ray'),
	year int not null,
	constraint fk_physical_movie_id
		foreign key (movie_id)
		references movie(movie_id),
	constraint uq_physical_movie_format_year
		unique (movie_id, format, year)
);

create table rating (
	rating_id int primary key auto_increment,
	value int not null check (value >= 1 and value <= 5),
	notes text null,
	user_id int,
	physical_id int,
	constraint fk_rating_user_id
		foreign key (user_id)
		references user(user_id),
	constraint fk_rating_physical_id
		foreign key (physical_id)
		references physical(physical_id),
	constraint uq_rating_user_physical
		unique (user_id, physical_id)
);

