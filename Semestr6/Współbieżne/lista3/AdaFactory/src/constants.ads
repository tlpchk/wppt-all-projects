with Ada.Numerics.Discrete_Random;
package Constants is
   type Operator is (ADD,MUL);
   type ModeType is (LIVE_MODE,INTERACTIVE_MODE);
   
   LISTA_3 : constant Boolean := True;
   
   MAX_ARG_VALUE  : constant Integer := 1000;
   MIN_ARG_VALUE  : constant Integer :=  1;
   
   BOSS_BREAK_TIME : constant Duration := Duration(1.0);
   WORKER_BREAK_TIME : constant Duration :=  Duration(3.0);
   IMPATIENT_WORKER_WAITING_TIME : constant Duration := Duration(0.1);
   CLIENT_BREAK_TIME  : constant Duration :=  Duration(1.0);
  
   COMPUTER_COMPUTATION_TIME : constant Duration := Duration(1.0);
   COMPUTER_FIXING_TIME  : constant Duration := Duration(10.0);
   PROBABILITY_OF_BREAKING : constant Float := 0.3;
   
   GOALS_CAPACITY  : constant Integer :=  2;
   WAREHOUSE_CAPACITY  : constant Integer :=  10;

   
   SERVICE_WORKERS_COUNT  : constant Integer :=  2;
   WORKERS_COUNT  : constant Integer :=  10;
   CLIENTS_COUNT        : constant Integer := 1;
   COMPUTERS_COUNT: constant Integer := 2;
   
   subtype Computer_Range is Integer range 1..COMPUTERS_COUNT;
   subtype Arg_Range is Integer range MIN_ARG_VALUE..MAX_ARG_VALUE;
   package Rand_Int is new Ada.Numerics.Discrete_Random(Arg_Range);
   package Rand_Op is new Ada.Numerics.Discrete_Random(Operator);
   package Rand_Comp_Indx is new Ada.Numerics.Discrete_Random(Computer_Range);
   package Rand_Patient is new Ada.Numerics.Discrete_Random(Boolean);
   
   type Goal is record
      arg1 : Integer;
      arg2 : Integer;
      op : Operator;
      result : Integer;
   end record;
   
   
   survMode : access ModeType := new ModeType'(LIVE_MODE);
   exit_state: access Boolean := new Boolean'(False);
end Constants;

