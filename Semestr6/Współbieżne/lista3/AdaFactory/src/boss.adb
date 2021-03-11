with GoalServer; use GoalServer;
with ProductServer; use ProductServer;
with Constants; use Constants;
with Ada.Text_IO; use Ada.Text_IO;
with Ada.Numerics.Discrete_Random;

package body Boss is
   
   Arg_Gen : Rand_Int.Generator;
   Op_Gen : Rand_Op.Generator;
   
   procedure Log(msg: String) is
   begin
      if (survMode.all = LIVE_MODE and LISTA_3 = False) then
         Put_Line("Boss: " & msg);
      end if;
   end Log;

    
   function genGoal return Goal is
      arg1 : Integer;
      arg2 : Integer;
      op : Operator;
   begin
      arg1 := Rand_Int.Random(Arg_Gen);
      arg2 := Rand_Int.Random(Arg_Gen);
      op := Rand_Op.Random(Op_Gen);
      Log("New goal - " & arg1'Image & " " & op'Image & " " & arg2'Image);
      return Goal'(arg1 => arg1,
                   arg2 => arg2,
                   op => op,
                   result => Integer'Last
                  );
   end genGoal;
   
   task body BossTask is
      g: Goal;
      server: GoalServerAcc;
   begin
      Rand_Int.Reset(Arg_Gen);
      Rand_Op.Reset(Op_Gen);
      
      accept Construct(gs: GoalServerAcc) do
         server := gs;
      end Construct;
         
      loop
         g := genGoal;
         server.addGoal(g);
         delay BOSS_BREAK_TIME;
         exit when exit_state.all ;
      end loop;
   end BossTask;

end Boss;
