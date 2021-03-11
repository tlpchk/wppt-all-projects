USE  world;
SELECT city.Name
FROM country JOIN city ON country.Capital=city.ID
WHERE country.Continent='Asia';
