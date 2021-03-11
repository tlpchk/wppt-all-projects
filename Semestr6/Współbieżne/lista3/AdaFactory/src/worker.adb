with Constants; use Constants;
with Ada.Text_IO; use Ada.Text_IO;
  
package body Worker is 
   
   Rand_Comp_Indx_Gen : Rand_Comp_Indx.Generator;
   Rand_Patient_Gen : Rand_Patient.Generator;
   
   function initWorkers return WA_Access_Type is
      WA_Ptr : WA_Access_Type;
   begin
      Rand_Comp_Indx.Reset(Rand_Comp_Indx_Gen);
      Rand_Patient.Reset(Rand_Patient_Gen);
      
      WA_Ptr := new WorkerArrayType(1..WORKERS_COUNT);
      for I in WA_Ptr.all'Range loop
         WA_Ptr(I) := new WorkerTask(I);
      end loop;
      return WA_Ptr;
   end initWorkers;
   
   
   task body WorkerTask is    
      patient : Boolean;
      doneGoals : Integer;
      
      gServer : GoalServerAcc;
      prodServer : ProductServerAcc;
      service : ServiceAcc;
      computers : ComputerArrayType(Computer_Range);
      
      g: Goal;
      solution : Integer;
      
      procedure Log(msg: String) is
      begin
         if survMode.all = LIVE_MODE then
            Put_Line("Worker " & id'Image & " :" & msg );
         end if;
      end Log;
      
      function solveOnComputer(g:in out Goal) return Integer is
         compIndx : Positive;
      begin
         compIndx := 1;
         loop
            if(patient = True) then
               compIndx := Rand_Comp_Indx.Random(Rand_Comp_Indx_Gen);
               Log("My comp is #" & compIndx'Image); 
               computers(compIndx).GoalToCompute(g);
               
               if(g.result = Integer'Last) then
                  Log("Comp #" & compIndx'Image & " is broken");
                  service.computerToFix(computers(compIndx));
               else
                  return g.result;
               end if;
            else
               select
                  computers(compIndx).GoalToCompute(g);
                 
                  Log("My comp is #" & compIndx'Image);
                  if(g.result = Integer'Last) then
                     Log("Comp #" & compIndx'Image & " is broken");
                     service.computerToFix(computers(compIndx));
                  else
                     return g.result;
                  end if;
               or
                  delay 0.1;
                  Log("Next computer");
                  compIndx := 1 + (compIndx) mod COMPUTERS_COUNT; 
               end select;    
            end if;
         end loop;
         
      end solveOnComputer;
      
   begin
      accept Construct (gs : in GoalServerAcc; ps : in ProductServerAcc; comps : in ComputerArrayType; serv : in ServiceAcc) do
         gServer := gs;
         prodServer := ps;
         service := serv;
         computers := comps;
         patient := Rand_Patient.Random(Rand_Patient_Gen);
         doneGoals := 0;
      end Construct;
      
      loop
         gServer.getGoal(g);
         Log("Wow, have a new task");
      
         solution := solveOnComputer(g);
         Log(g.arg1'Image & " " & g.op'Image & " " & g.arg2'Image & " = "  & solution'Image);
         doneGoals := doneGoals + 1;
         prodServer.addProduct(solution);
         
         select 
            accept Statistic(goals: out Integer; isPatient : out Boolean) do
               goals := doneGoals;
               isPatient := patient;
            end Statistic;
         or
            delay WORKER_BREAK_TIME;
         end select;
         
         exit when exit_state.all;
      end loop;
   end WorkerTask;
end Worker;
