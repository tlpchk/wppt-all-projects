use `db-electronics`;

create or replace  view view2 as
select prod.Nazwa,prod.Siedziba,l.Model
from laptopy l join producenci prod on prod.ID=l.Producent;

use `db-electronics`;
delete l from laptopy l join producenci prod on l.Producent=prod.ID where prod.Siedziba = "Taiwan";