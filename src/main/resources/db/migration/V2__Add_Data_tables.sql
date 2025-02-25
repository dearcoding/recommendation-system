INSERT INTO movie (id, title, genres) VALUES
(1, 'Toy Story', 'Adventure|Animation|Children|Comedy|Fantasy'),
(2, 'Grumpier Old Men', 'Comedy|Romance'),
(3, 'Die Hard', 'Action|Thriller'),
(4, 'Star Wars: Return of the Jedi', 'Action|Adventure|Fantasy|Sci-Fi'),
(5, 'The Lion King', 'Adventure|Animation|Children|Drama|Musical'),
(6, 'Pulp Fiction', 'Crime|Drama|Thriller'),
(7, 'Forrest Gump', 'Comedy|Drama|Romance'),
(8, 'The Matrix', 'Action|Sci-Fi'),
(9, 'Goodfellas', 'Biography|Crime|Drama'),
(10, 'Jurassic Park', 'Adventure|Sci-Fi|Thriller');

INSERT INTO "user" (id, username) VALUES
(1, 'Alice'),
(2, 'Bob'),
(3, 'Charlie');

INSERT INTO rating (user_id, movie_id, rating, view_percentage) VALUES
(1, 1, 4, 85),
(1, 2, 5, NULL),
(2, 1, NULL, 90),
(2, 3, 3, NULL),
(3, 4, NULL, 70),
(3, 2, 2, NULL);