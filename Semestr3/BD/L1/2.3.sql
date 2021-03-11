USE  world ;
SELECT city.Name, country.Continent
FROM city JOIN country ON city.CountryCode = country.Code
ORDER BY country.Continent ASC, city.Name DESC;
