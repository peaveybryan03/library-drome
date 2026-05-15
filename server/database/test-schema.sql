DROP database IF EXISTS librarydrome_test;
CREATE database librarydrome_test;
use librarydrome_test;

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

create table film_list (
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
		references movie(movie_id)
		on delete cascade,
	constraint fk_moil_list_id
		foreign key (list_id)
		references film_list(list_id)
		on delete cascade,
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

delimiter //
create procedure set_known_good_state()
begin
	delete from movie;
	alter table movie auto_increment = 1;
	delete from film_list;
	alter table film_list auto_increment = 1;
	delete from `user`;
    alter table `user` auto_increment = 1;

    insert into `user` (email, password) values
        ("a@a.com", "128"),
        ("b@b.com", "129");
    
    insert into film_list (title, user_id) values
    	("list 1", 1),
    	("list 2", 2);
    
    insert into movie (title, `year`, availability, poster_url) values
    	("The Doom Generation", 1995, "none", "https://media.themoviedb.org/t/p/w220_and_h330_face/cRzRj2UBvIH8ryWhu5PNL2PzV7j.jpg"),
    	("Lady Bird", 2017, "both", "https://media.themoviedb.org/t/p/w220_and_h330_face/gl66K7zRdtNYGrxyS2YDUP5ASZd.jpg"),
    	("Bones and All", 2022, "both", "https://media.themoviedb.org/t/p/w220_and_h330_face/ayfr4iL0jVV9mquN7SKvjOidvRH.jpg"),
    	("Women on the Verge of a Nervous Breakdown", 1988, "dvd", "https://media.themoviedb.org/t/p/w220_and_h330_face/8C5FJlUo96pj1xAs2BKnB58PYzi.jpg");
    
    insert into movie_is_on_list (movie_id, list_id) values 
    	(1, 1),
    	(2, 1),
    	(3, 1), 
    	(4, 1);


end //
delimiter ;
