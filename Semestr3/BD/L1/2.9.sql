USE world;

SELECT a.name AS 'a', b.name AS 'b'
FROM country AS a JOIN country AS b
	 ON a.Region = b.Region AND a.name < b.name;

