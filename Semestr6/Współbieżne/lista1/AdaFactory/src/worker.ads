with GoalServer; use GoalServer;
with ProductServer; use ProductServer;

package Worker is
 
   
   task type WorkerTask(id: Positive) is
      entry Construct(gs : GoalServerAcc ; ps: ProductServerAcc);
   end WorkerTask;
   
   type WorkerArrayType is array (Positive range <>) of access WorkerTask;
   type WA_Access_Type is access WorkerArrayType;
   
   function initWorkers return WA_Access_Type;
   
end Worker;
