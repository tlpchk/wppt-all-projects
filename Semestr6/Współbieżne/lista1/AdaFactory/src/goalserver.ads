with Constants; use Constants;

package GoalServer is
   
   task type GoalServerTask is
      entry addGoal(g : in Goal);
      entry getGoal(g : out Goal);
      entry getLength(l : out Positive);
   end GoalServerTask;
   
   type GoalServerAcc is access GoalServerTask;
   
end GoalServer;
