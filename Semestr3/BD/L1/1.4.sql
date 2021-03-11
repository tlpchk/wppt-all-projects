USE world;
SELECT country.Continent,count(*)
FROM country
GROUP BY country.Continent;

