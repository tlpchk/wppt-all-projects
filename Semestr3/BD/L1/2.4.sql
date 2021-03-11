USE  world ;
SELECT city.Name AS 'Miasto', country.Name AS 'Kraj', 100*city.Population/country.Population AS 'Procent od populacji kraju'
FROM city JOIN country ON city.CountryCode = country.Code 
WHERE country.Continent='Europe';