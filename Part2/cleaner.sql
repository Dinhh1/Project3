SELECT 'Movies without any star.';

SELECT * FROM movies AS m LEFT JOIN stars_in_movies AS s ON m.id = s.movie_id WHERE s.movie_id is NULL;

SELECT 'Stars without any movie.';

SELECT * FROM stars AS s LEFT JOIN stars_in_movies AS m ON m.star_id = s.id WHERE m.star_id is NULL;

SELECT 'Genres without any movies.';

SELECT * FROM genres AS g LEFT JOIN genres_in_movies AS m ON m.genre_id = g.id WHERE m.movie_id is NULL;

SELECT 'Movies without any genres.';

SELECT * FROM movies AS m LEFT JOIN genres_in_movies AS g ON g.movie_id = m.id WHERE g.genre_id is NULL;

SELECT 'Stars with no first name or last name.';

SELECT * FROM stars WHERE first_name is NULL OR first_name = '' OR last_name is NULL OR last_name ='';

SELECT 'Expired customer credit card. Report expired credit cards only if they belong to existing customers';

SELECT * FROM creditcards WHERE expiration < CURDATE() AND id IN (SELECT cc_id FROM customers);

SELECT 'Movie/star/genres that are the same. Make sure you group the IDs of the duplicates in your report. Two movies are considered the same if (name, year) match, and two stars if (first name, last name, dob) match.';

SELECT 'Genres that are the same.';

SELECT name, count(name) FROM genres GROUP BY name HAVING count(name) > 1;

SELECT 'Movies(name, year) that are the same.';

SELECT title, year FROM movies GROUP BY title, year HAVING count(*) > 1;

SELECT 'Stars(first name, last name, dob) that are the same.';

SELECT first_name, last_name, dob FROM stars GROUP BY first_name, last_name, dob HAVING count(*) > 1;

SELECT 'Birth date > today or year < ~1900.';

SELECT * FROM stars WHERE dob > CURDATE() OR dob < '1900-1-10';

SELECT 'Customer email has no sign';

SELECT * FROM customers WHERE email not LIKE '%@%';