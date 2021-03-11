with Constants; use Constants;

package body Computer is
   
   function initComputers return CompArr_Acc is
      Ptr : CompArr_Acc;
   begin
      Ptr := new ComputerArray;
      for I in Ptr.all'Range loop
         Ptr(I) := new ComputerTask;
      end loop;
      return Ptr;
   end initComputers;
   
   function computeGoal(g: Goal) return Integer is
   begin
      if(g.op = ADD) then
         return g.arg1 + g.arg2;
      elsif (g.op = MUL) then
         return g.arg1 * g.arg2;
      else
         return Integer'First;
      end if;
   end computeGoal;
   
   task body ComputerTask is
   begin
      loop
         accept GoalToCompute(g:in out Goal) do
            delay COMPUTER_TIME;
            g.result := computeGoal(g);
         end GoalToCompute;
      end loop;
   end ComputerTask;
   

end Computer;
