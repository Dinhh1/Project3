USE moviedb;
DROP procedure IF EXISTS AddMovie;

DELIMITER $$
USE moviedb $$
CREATE DEFINER='root'@'localhost' PROCEDURE AddMovie(IN t varchar(200), 
                             IN y int, 
                             IN d varchar(200), 
                             IN b_url varchar(200),
                             IN t_url varchar(200),
                             IN star_fname varchar(200),
                             IN star_lname varchar(200),
                             IN genre varchar(200),
                             OUT output varchar(200))
proc:BEGIN
    
    DECLARE star_id int default -1;
    DECLARE genre_id int default -1;
    DECLARE movie_id int default -1;
    
    
    START TRANSACTION;
    
    -- query for star id 
    SELECT id into star_id from stars where first_name = star_fname and last_name = star_lname;
    
    -- if star_id cannot be found then create a star entry
    IF star_id = -1 THEN        
        INSERT INTO stars(first_name, last_name) VALUES(star_fname, star_lname);
        SET star_id = LAST_INSERT_ID();
    END IF;
    
    IF star_id = -1 THEN
        SET output = 'Something went wrong when retrieving star id';
        ROLLBACK;
        LEAVE proc;
    END IF;
    
    -- query for genre id
    SELECT id into genre_id from genres where name = genre;
    
    -- if genre does not exist create a genre entry
    IF genre_id = -1 THEN
        INSERT INTO genres(name) VALUES(genre);
        SET genre_id = LAST_INSERT_ID();
    END IF;
    
    IF genre_id = -1 THEN
        SET output = 'Something went wrong when retrieving genre id';
        ROLLBACK;
        LEAVE proc;
    END IF;
    
    -- query for movie id
    SELECT id into movie_id from movies where title = t and year = y and director = d;
    
    -- create a movie only if it does not exist
    IF movie_id = -1 THEN
        INSERT INTO movies(title, year, director, banner_url, trailer_url) VALUES(t, y, d, b_url, t_url);
        SET movie_id = LAST_INSERT_ID();
    END IF;
    
    
    IF movie_id = -1 THEN
        SET OUTPUT = 'could not find id for movie';
        ROLLBACK;
        LEAVE proc;
    END IF;
    
    INSERT INTO genres_in_movies(genre_id, movie_id) VALUES(genre_id, movie_id);
    INSERT INTO stars_in_movies(star_id, movie_id) VALUES(star_id, movie_id);
    SET OUTPUT = 'Movies successfully inserted';
    
    COMMIT;
    
END$$

DELIMITER ;

