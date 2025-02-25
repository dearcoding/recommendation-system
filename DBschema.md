# Database Schema

## Tables

### movie
Stores information about movies

| Column | Type        | Constraints | Description                                             |
|--------|-------------|-------------|---------------------------------------------------------|
| id     | bigint      | PRIMARY KEY | Unique identifier for the movie                         |
| title  | VARCHAR(255)| NOT NULL    | Name of the movie                                       |
| genres | TEXT        | NOT NULL    | List or categories of movie genres concatenated by '\|' |

### user
Stores user account information

| Column    | Type        | Constraints | Description |
|-----------|-------------|-------------|-------------|
| id        | bigint      | PRIMARY KEY | Unique identifier for the user |
| username  | VARCHAR(100)| NOT NULL    | User's account name |

### rating
Tracks user ratings and viewing information for movies

| Column          | Type    | Constraints | Description |
|-----------------|---------|-------------|-------------|
| user_id         | bigint  | PRIMARY KEY, FOREIGN KEY | References user's ID |
| movie_id        | bigint  | PRIMARY KEY, FOREIGN KEY | References movie's ID |
| rating          | INT     | NULL        | Numeric rating given by user |
| view_percentage | INT     | NULL        | Percentage of movie watched |

## Relationships
- `rating` table has composite primary key (user_id, movie_id)
- `rating` has foreign key constraints to `user` and `movie` tables
- ON DELETE CASCADE ensures related ratings are removed if user or movie is deleted