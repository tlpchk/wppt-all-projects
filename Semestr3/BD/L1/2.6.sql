USE world;

SELECT DISTINCT 
	 country.Name
FROM country JOIN countrylanguage
		ON countrylanguage.CountryCode=country.Code
WHERE
	(SELECT COUNT(*)
    FROM countrylanguage
    WHERE country.Code=countrylanguage.CountryCode AND countrylanguage.IsOfficial='F')>=2
;