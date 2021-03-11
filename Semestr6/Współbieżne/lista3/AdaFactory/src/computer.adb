with Constants; use Constants;
with Ada.Numerics.Float_Random; use Ada.Numerics.Float_Random;

package body Computer is
   broken : Boolean;
   solution : Integer;
   G : Generator;
   
   function initComputers return CompArr_Acc is
      Ptr : CompArr_Acc;
   begin
      Ptr := new ComputerArrayType(Computer_Range);
      for I in Ptr'Range loop
         Ptr(I) := new ComputerTask(I);
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
   
   procedure enduranceTest is
      RandValue : Uniformly_Distributed;
      begin
         RandValue := Random(G);
         if RandValue < PROBABILITY_OF_BREAKING then
            broken := True;
         end if;
      end enduranceTest; 
   
      task body ComputerTask is
      begin
         broken := False;
         Reset (G);
         loop
            select
               accept GoalToCompute(g:in out Goal) do
                  delay COMPUTER_COMPUTATION_TIME;
                  solution := computeGoal(g);
                  enduranceTest;
                  if broken then 
                     g.result := Integer'Last;
                  else
                     g.result := solution;
                  end if;
               end GoalToCompute;
            or
               accept BackDoor  do
                  broken := False;
               end BackDoor;
            end select;
         end loop;
      end ComputerTask;

   end Computer;
