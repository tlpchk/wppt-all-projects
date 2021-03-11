with Constants; use Constants;

package body ProductServer is

   task body ProductServerTask is
      products : array (1 .. WAREHOUSE_CAPACITY) of Integer;
      length : Natural range 0 .. WAREHOUSE_CAPACITY := 0;
      head, tail : Natural := 1;
      
      function isFull return Boolean is (length = WAREHOUSE_CAPACITY);
      function isEmpty return Boolean is (length = 0);
   begin
      loop
         select
            when (isFull = False) =>
               accept addProduct (p : in Integer) do  
                  products(tail) := p;
                  tail := tail mod WAREHOUSE_CAPACITY + 1;
                  length := length + 1;
               end addProduct;
         or
            when (isEmpty = False) =>
               accept getProduct(p : out Integer) do
                  p := products(head);
                  head := head mod WAREHOUSE_CAPACITY + 1;
                  length := length - 1;
               end getProduct;
         or
              
            accept getLength(l : out Positive) do
               l := length;
            end getLength;
        
         or
            
            terminate;
         end select;
         exit when exit_state.all;
      end loop;
   end ProductServerTask;
   

end ProductServer;
