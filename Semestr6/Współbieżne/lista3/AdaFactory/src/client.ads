with ProductServer; use ProductServer;

package Client is

   task type ClientTask(id: Positive) is
      entry Construct(ps: ProductServerAcc);
   end ClientTask;
  
   
   type ClientArrayType is array (Positive range <>) of access ClientTask;
   type CA_Access_Type is access ClientArrayType;
   
   function initClients return CA_Access_Type;
end Client;
