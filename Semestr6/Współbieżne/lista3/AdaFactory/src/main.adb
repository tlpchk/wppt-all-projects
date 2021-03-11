with Constants; use Constants;
with Ada.Text_IO; use Ada.Text_IO;
with Boss; use Boss;
with Worker; use Worker;
with Client; use Client;
with ProductServer; use ProductServer;
with GoalServer; use GoalServer;
with Computer; use Computer;
with ServiceServer; use ServiceServer;

procedure Main is
   procedure printStartMenu is
   begin
      Put_Line("This is AdaFactory");
      Put_Line("Press mode:");
      Put_Line("  1.Live (see all operations immediatly)");
      Put_Line("  2.Interactive (see status of AdaFactory on demand)");
      Put("Your choice: ");
   end printStartMenu;

   procedure printInteractiveMenu is
   begin
      New_Line;
      Put_Line("1. See actual number of tasks");
      Put_Line("2. See actual number of items");
      Put_Line("3. Worker statistics");
      Put_Line("3. Exit");
      Put("Your choice: ");
   end printInteractiveMenu;


   goals : GoalServerAcc := new GoalServerTask;
   products : ProductServerAcc := new ProductServerTask;
   service : ServiceAcc := new ServiceTask;

   bigBoss : BossTask;
   workers : WorkerArrayType := initWorkers.all;
   clients : ClientArrayType := initClients.all;
   computers : ComputerArrayType := initComputers.all;

   modeInput : String(1..1);
   length : Positive;
   isPatient : Boolean;
begin
   printStartMenu;

   get(modeInput);
   case modeInput(1) is
      when '1' => survMode := new ModeType'(LIVE_MODE);
      when others => survMode := new ModeType'(INTERACTIVE_MODE);
   end case;

   if (survMode.all = LIVE_MODE) then
      Put_Line("Press 'exit' when you get bored");
   end if;


   bigBoss.Construct(goals);

   for I in workers'Range loop
      workers(I).all.Construct(goals,products,computers,service);
   end loop;

   for I in clients'Range loop
      clients(I).all.Construct(products);
   end loop;


   if(survMode.all = INTERACTIVE_MODE) then
      loop
         printInteractiveMenu;
         get(modeInput);
         case modeInput(1) is
         when '1' =>  goals.getLength(length);

         when '2' =>  products.getLength(length);

         when '3' =>
            for I in workers'Range loop
               workers(I).Statistic(goals => length, isPatient => isPatient);
               Put_Line("Worker " & I'Image & " (isPatient=" & isPatient'Image & ") has done " & length'Image & " goals");
            end loop;
         when others => exit;
         end case;
      end loop;
   else
      get(modeInput);
   end if;
   Put_Line("Exit");
   exit_state := new Boolean'(True);
end Main;
