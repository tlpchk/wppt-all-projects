with GoalServer; use GoalServer;
with ProductServer; use ProductServer;
with Computer; use Computer;
with Ada.Numerics.Discrete_Random;

package Worker is
   
   task type WorkerTask(id: Positive) is
      entry Construct(gs : GoalServerAcc ; ps: ProductServerAcc; comps: in CompArr_Acc);
      entry Statistic(goals: out Integer; isPatient : out Boolean);
   end WorkerTask;
   
   type WorkerArrayType is array (Positive range <>) of access WorkerTask;
   type WA_Access_Type is access WorkerArrayType;
   
   function initWorkers return WA_Access_Type;
   
end Worker;
