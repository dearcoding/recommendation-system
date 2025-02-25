CREATE TABLE movie (
    id bigint PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genres TEXT NOT NULL
);

CREATE TABLE "user" (
    id bigint PRIMARY KEY,
    username VARCHAR(100) NOT NULL
);

CREATE TABLE rating (
    user_id bigint,
    movie_id bigint,
    rating INT NULL,
    view_percentage INT NULL,
    PRIMARY KEY (user_id, movie_id),
    FOREIGN KEY (user_id) REFERENCES "user"(id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE CASCADE
);
