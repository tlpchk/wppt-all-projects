USE  world;
SELECT city.District, city.Name, country.Name
FROM country JOIN city ON country.Code=city.CountryCode
WHERE city.Population > 10000
ORDER BY city.District;