use `db-electronics`;

create or replace  view view1 as
select l.Model,l.Typ,proc.Rdzenie,proc.Czestotliwosc,m.Przekatna,m.sRGB
from laptopy l join procesory proc on proc.ID=l.Procesor
			   join matryce m on l.Matryca=m.ID
               join producenci prod on prod.ID=l.Producent
where prod.Siedziba <> "USA";