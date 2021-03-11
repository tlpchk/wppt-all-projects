-- ALTER TABLE world.country DROP COLUMN GNPgrowth;

ALTER TABLE world.country ADD GNPgrowth integer AFTER GNPOld;
UPDATE world.country SET GNPgrowth = GNP-GNPOld;
UPDATE world.country SET GNPgrowth = GNP
WHERE GNPgrowth is null;


SELECT * FROM world.country;