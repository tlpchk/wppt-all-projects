USE world;

SELECT country.Name,country.IndepYear, c.Name,cntl.Language,
	   country.SurfaceArea,country.Region,country.Continent
FROM country JOIN city AS c ON country.Capital=c.ID 
			 JOIN countrylanguage AS cntl ON cntl.CountryCode=country.Code AND cntl.IsOfficial='T'

WHERE country.IndepYear is not null
GROUP BY country.Name
ORDER BY country.IndepYear ASC
LIMIT 5

