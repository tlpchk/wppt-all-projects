USE world;

SELECT DISTINCT cntl.Language
FROM countrylanguage AS cntl JOIN country ON cntl.CountryCode=country.Code AND cntl.IsOfficial='T'
GROUP BY cntl.Language
HAVING COUNT(*)>=4
