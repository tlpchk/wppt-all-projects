with Constants; use Constants;

package Computer is
   
   task type ComputerTask(id : Integer) is
      entry GoalToCompute(g : in out Goal);
      entry BackDoor;
   end ComputerTask;
   
   type ComputerAcc is access ComputerTask;
   type ComputerArrayType is array (Integer range <>) of ComputerAcc;  
   type CompArr_Acc is access ComputerArrayType;

   function initComputers return CompArr_Acc;
end Computer;
