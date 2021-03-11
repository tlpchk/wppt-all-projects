with Constants; use Constants;
with Computer; use Computer;

package ServiceServer is 
   
   task type ServiceTask is
      entry computerToFix(compToFix : in ComputerAcc);
   end ServiceTask;
   
   type ServiceAcc is access all ServiceTask;
   type ScheduleType is array (1..SERVICE_WORKERS_COUNT) of Integer;
 
   --------------------------------------
   
   task type ServiceWorkerTask(id: Positive) is
      entry Request(compToFix: in ComputerAcc);
      entry Free;
   end ServiceWorkerTask;
   
   type ServiceWorkerAcc is access ServiceWorkerTask;   
   type SW_ArrayType is array (1..SERVICE_WORKERS_COUNT) of ServiceWorkerAcc ;
   type SW_Arr_Acc is access SW_ArrayType;
   
   
end ServiceServer;
