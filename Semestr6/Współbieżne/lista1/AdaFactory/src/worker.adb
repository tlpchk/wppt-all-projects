with Constants; use Constants;
with Ada.Text_IO; use Ada.Text_IO;
  
package body Worker is 
   
   function initWorkers return WA_Access_Type is
      WA_Ptr : WA_Access_Type;
   begin
      WA_Ptr := new WorkerArrayType(1..WORKERS_COUNT);
      for I in WA_Ptr.all'Range loop
         WA_Ptr(I) := new WorkerTask(I);
      end loop;
      return WA_Ptr;
   end initWorkers;
   
   function computeGoal(g: Goal) return Integer is
   begin
      case g.op is
         when ADD => return g.arg1 + g.arg2;
         when SUB => return g.arg1 - g.arg2;
         when MUL => return g.arg1 * g.arg2;
         when DIV => return g.arg1 / g.arg2;
      end case;
   end computeGoal;
   
   task body WorkerTask is
      procedure Log(msg: String) is
      begin
         if survMode.all = LIVE_MODE then
            Put_Line("Worker " & id'Image & " :" & msg );
         end if;
      end Log;
      
      g: Goal;
      p : Integer;
      gServer : GoalServerAcc;
      prodServer : ProductServerAcc;
   begin
      accept Construct (gs : in GoalServerAcc; ps : in ProductServerAcc) do
         gServer := gs;
         prodServer := ps;
      end Construct;
      
      loop
         gServer.getGoal(g);
         Log("Wow, have a new task");
         p := computeGoal(g);
         Log(g.arg1'Image & " " & g.op'Image & " " & g.arg2'Image & " = "  & p'Image);
         prodServer.addProduct(p);
         delay Duration(WORKER_BREAK_TIME);
         exit when exit_state.all;
      end loop;
   end WorkerTask;
end Worker;
