with Ada.Numerics.Discrete_Random;
package Constants is
   type Operator is (ADD,SUB,MUL,DIV);
   type ModeType is (LIVE_MODE,INTERACTIVE_MODE);
   
   MAX_ARG_VALUE  : constant Integer := 1000;
   MIN_ARG_VALUE  : constant Integer :=  1;
   
   subtype Rand_Range is Integer range MIN_ARG_VALUE..MAX_ARG_VALUE;
   package Rand_Int is new Ada.Numerics.Discrete_Random(Rand_Range);
   package Rand_Op is new Ada.Numerics.Discrete_Random(Operator);
   
   type Goal is record
      arg1 : Integer;
      arg2 : Integer;
      op : Operator;
   end record;
   
   
   BOSS_BREAK_TIME : constant Float := 1.0;
     
   WORKER_BREAK_TIME : constant Float :=  3.0;
   CLIENT_BREAK_TIME  : constant Integer :=  1000;
   
   GOALS_CAPACITY  : constant Integer :=  10;
   WAREHOUSE_CAPACITY  : constant Integer :=  10;

   WORKERS_COUNT  : constant Integer :=  2;
   CLIENTS_COUNT        : constant Integer :=  3;
   
   survMode : access ModeType := new ModeType'(LIVE_MODE);
   exit_state: access Boolean := new Boolean'(False);
end Constants;

