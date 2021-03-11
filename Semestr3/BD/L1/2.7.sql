USE world;

SELECT
	country.Name
FROM
	country
WHERE
	country.LifeExpectancy>=(SELECT AVG(country.LifeExpectancy)
							 FROM country);