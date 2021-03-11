with Constants; use Constants;
with ProductServer; use ProductServer;
with Ada.Text_IO; use Ada.Text_IO;

package body Client is

   function initClients return CA_Access_Type is
      CA_Ptr : CA_Access_Type;
   begin
      CA_Ptr := new ClientArrayType(1..Clients_COUNT);
      for I in CA_Ptr.all'Range loop
         CA_Ptr(I) := new ClientTask(I);
      end loop;
      return CA_Ptr;
   end initClients;
   
   task body ClientTask is  
      p: Integer;
      server : ProductServerAcc;
      
      procedure Log(msg: String) is
      begin
         if survMode.all = LIVE_MODE then
            Put_Line("Client " & id'Image & " :" & msg );
         end if;
      end Log;
      
   begin
      accept Construct (ps : in ProductServerAcc) do
         server := ps;
      end Construct;
      loop
         server.getProduct(p);
         Log("Thank you for " & p'Image);
         delay Duration(CLIENT_BREAK_TIME);
         exit when exit_state.all;
      end loop;
   end ClientTask;

end Client;
