SELECT world.country.Name, world.city.Name
FROM world.country, world.city
WHERE world.country.Capital = world.city.ID;
;
