use librarydrome;

insert into `user` (email, password) values
        ("peaveybryan03@gmail.com", "1216985786"),
        ("rylee@gmail.com", "1216985786");

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