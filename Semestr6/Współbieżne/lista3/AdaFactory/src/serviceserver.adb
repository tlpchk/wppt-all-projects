with Ada.Text_IO; use Ada.Text_IO;

package body ServiceServer is

   -- SERVICE WORKER --
   task body ServiceWorkerTask is  

      procedure Log(msg: String) is
      begin
         if (survMode.all = LIVE_MODE) then
            Put_Line("ServiceWorker " & id'Image & " :" & msg );
         end if;
      end Log;
      
   begin
      loop
         select 
            accept Request(compToFix: in ComputerAcc) do
               Log("Going to fix computer #" & id'Image);
               delay COMPUTER_FIXING_TIME;
               compToFix.Backdoor;
               Log("Computer #" & id'Image & " is fixed");
            end Request;
         or
            accept Free do
               null;
            end Free;    
         end select;
         
         exit when exit_state.all;
      end loop;
   end ServiceWorkerTask;
   
   -- SERVICE --
   
   task body ServiceTask is
      -- ServiceWorker ID -> Computer ID
      serviceWorkers : SW_Arr_Acc;
      schedule : ScheduleType;
      idx : Integer;
      
      procedure Log(msg: String) is
      begin
         if (survMode.all = LIVE_MODE) then
            Put_Line("Service: " & msg );
         end if;
      end Log;
      
      function computerIsFixed(compId : Integer) return Boolean is
      begin
         for I in schedule'Range loop
            if( schedule(I) = compId ) then
               return True;
            end if;
         end loop;
         return False;
      end computerIsFixed;
      
      procedure refreshSchedule is
      begin
         for workerId in serviceWorkers'Range loop
            select 
               serviceWorkers(workerId).Free;
               if schedule(workerId) /= 0 then 
                  schedule(workerId) := 0;
                  Log("Good job, service worker #" & workerId'Image);
               end if;
            else
               null;
            end select;
         end loop;
      end refreshSchedule;
      
   begin
      serviceWorkers := new SW_ArrayType;
      
      -- INIT WORKERS
      for I in serviceWorkers'Range loop
         serviceWorkers(I) := new ServiceWorkerTask(I);
      end loop;
      
      -- INIT SCHEDULE
      for I in schedule'Range loop
         schedule(I) := 0;
      end loop;
     
      idx := 1;
      loop
         accept computerToFix(compToFix : ComputerAcc) do
            Log("Comp #" & compToFix.id'Image & " is broken?");     
            refreshSchedule;
            if (computerIsFixed(compToFix.id) = False) then
               Lookup_Loop :
               loop
                  select
                     serviceWorkers(idx).Request(compToFix => compToFix);
          
                     Log("Service Worker #" & idx'Image & " will fix it.");  
                     schedule(idx) := compToFix.id;
                     exit Lookup_Loop;
                  else
                     idx := 1 + idx mod SERVICE_WORKERS_COUNT;
                  end select;  
               end loop Lookup_Loop;
            else
               Log("We know about issue");
            end if;
            
         end computerToFix;           
  
      end loop;
   
   end ServiceTask;
   
end ServiceServer;
