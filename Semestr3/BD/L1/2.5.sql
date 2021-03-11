USE  world ;
SELECT city.District As 'Dystrykt', COUNT(*) AS 'Liczba miast'
FROM city
GROUP BY city.District;