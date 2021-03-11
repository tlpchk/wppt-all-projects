with Constants; use Constants;

package Computer is
   
   task type ComputerTask is
      entry GoalToCompute(g : in out Goal);
   end ComputerTask;
   
   type ComputerArray is array (Computer_Range) of access ComputerTask;  
   type CompArr_Acc is access ComputerArray;
   
   function initComputers return CompArr_Acc;
end Computer;
