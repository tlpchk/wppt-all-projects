with GoalServer; use GoalServer;

package Boss is

   task type BossTask is
      entry Construct(gs: GoalServerAcc);
   end BossTask;

end Boss;
