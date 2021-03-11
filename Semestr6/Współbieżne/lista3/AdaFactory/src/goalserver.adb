with Constants; use Constants;
with Ada.Text_IO; use Ada.Text_IO;

package body GoalServer is
   procedure Log(msg: String) is
   begin
      if (survMode.all = LIVE_MODE and LISTA_3 = False) then
         Put_Line("Goals: " & msg);
      end if;
   end Log;

   
   task body GoalServerTask is
      goals : array (1 .. GOALS_CAPACITY) of Goal;
      length : Natural range 0 .. GOALS_CAPACITY := 0;
      head, tail : Natural := 1;
      
      function isFull return Boolean is (length = GOALS_CAPACITY);
      function isEmpty return Boolean is (length = 0);
   begin
      loop
         select
            when (isFull = False) =>
               accept addGoal (g : in Goal) do  
                  goals(tail) := g;
                  Log("Goal added - " & g.arg1'Image & " " & g.op'Image & " " & g.arg2'Image);
                  tail := tail mod GOALS_CAPACITY + 1;
                  length := length + 1;
               end addGoal;
         or
            when (isEmpty = False) =>
               accept getGoal(g : out Goal) do
                  g := goals(head);
                  head := head mod GOALS_CAPACITY + 1;
                  length := length - 1;
               end getGoal;
         or
            accept getLength(l : out Positive) do
               l := length;
            end getLength;
         or
                
         terminate;
      end select;
      exit when exit_state.all;
   end loop;
end GoalServerTask;

end GoalServer;
