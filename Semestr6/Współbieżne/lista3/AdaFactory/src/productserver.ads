package ProductServer is

   task type ProductServerTask is
      entry addProduct(p : Integer);
      entry getProduct(p : out Integer);
      entry getLength(l : out Positive);
   end ProductServerTask;
   
   type ProductServerAcc is access ProductServerTask;

end ProductServer;
