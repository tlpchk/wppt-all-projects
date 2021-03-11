SELECT country.Region,SUM(country.Population)
FROM country
GROUP BY country.Region;