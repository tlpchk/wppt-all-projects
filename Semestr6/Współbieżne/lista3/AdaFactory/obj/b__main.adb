pragma Warnings (Off);
pragma Ada_95;
pragma Source_File_Name (ada_main, Spec_File_Name => "b__main.ads");
pragma Source_File_Name (ada_main, Body_File_Name => "b__main.adb");
pragma Suppress (Overflow_Check);

with System.Restrictions;
with Ada.Exceptions;

package body ada_main is

   E075 : Short_Integer; pragma Import (Ada, E075, "system__os_lib_E");
   E013 : Short_Integer; pragma Import (Ada, E013, "system__soft_links_E");
   E025 : Short_Integer; pragma Import (Ada, E025, "system__exception_table_E");
   E070 : Short_Integer; pragma Import (Ada, E070, "ada__io_exceptions_E");
   E055 : Short_Integer; pragma Import (Ada, E055, "ada__strings_E");
   E040 : Short_Integer; pragma Import (Ada, E040, "ada__containers_E");
   E027 : Short_Integer; pragma Import (Ada, E027, "system__exceptions_E");
   E057 : Short_Integer; pragma Import (Ada, E057, "ada__strings__maps_E");
   E061 : Short_Integer; pragma Import (Ada, E061, "ada__strings__maps__constants_E");
   E045 : Short_Integer; pragma Import (Ada, E045, "interfaces__c_E");
   E021 : Short_Integer; pragma Import (Ada, E021, "system__soft_links__initialize_E");
   E081 : Short_Integer; pragma Import (Ada, E081, "system__object_reader_E");
   E050 : Short_Integer; pragma Import (Ada, E050, "system__dwarf_lines_E");
   E039 : Short_Integer; pragma Import (Ada, E039, "system__traceback__symbolic_E");
   E122 : Short_Integer; pragma Import (Ada, E122, "ada__numerics_E");
   E006 : Short_Integer; pragma Import (Ada, E006, "ada__tags_E");
   E105 : Short_Integer; pragma Import (Ada, E105, "ada__streams_E");
   E113 : Short_Integer; pragma Import (Ada, E113, "system__file_control_block_E");
   E112 : Short_Integer; pragma Import (Ada, E112, "system__finalization_root_E");
   E110 : Short_Integer; pragma Import (Ada, E110, "ada__finalization_E");
   E109 : Short_Integer; pragma Import (Ada, E109, "system__file_io_E");
   E158 : Short_Integer; pragma Import (Ada, E158, "system__task_info_E");
   E152 : Short_Integer; pragma Import (Ada, E152, "system__task_primitives__operations_E");
   E119 : Short_Integer; pragma Import (Ada, E119, "ada__calendar_E");
   E117 : Short_Integer; pragma Import (Ada, E117, "ada__calendar__delays_E");
   E192 : Short_Integer; pragma Import (Ada, E192, "ada__real_time_E");
   E103 : Short_Integer; pragma Import (Ada, E103, "ada__text_io_E");
   E127 : Short_Integer; pragma Import (Ada, E127, "system__random_seed_E");
   E172 : Short_Integer; pragma Import (Ada, E172, "system__tasking__initialization_E");
   E180 : Short_Integer; pragma Import (Ada, E180, "system__tasking__protected_objects_E");
   E182 : Short_Integer; pragma Import (Ada, E182, "system__tasking__protected_objects__entries_E");
   E186 : Short_Integer; pragma Import (Ada, E186, "system__tasking__queuing_E");
   E190 : Short_Integer; pragma Import (Ada, E190, "system__tasking__stages_E");
   E123 : Short_Integer; pragma Import (Ada, E123, "constants_E");
   E198 : Short_Integer; pragma Import (Ada, E198, "computer_E");
   E131 : Short_Integer; pragma Import (Ada, E131, "goalserver_E");
   E194 : Short_Integer; pragma Import (Ada, E194, "productserver_E");
   E115 : Short_Integer; pragma Import (Ada, E115, "boss_E");
   E196 : Short_Integer; pragma Import (Ada, E196, "client_E");
   E202 : Short_Integer; pragma Import (Ada, E202, "serviceserver_E");
   E208 : Short_Integer; pragma Import (Ada, E208, "worker_E");

   Sec_Default_Sized_Stacks : array (1 .. 1) of aliased System.Secondary_Stack.SS_Stack (System.Parameters.Runtime_Default_Sec_Stack_Size);

   Local_Priority_Specific_Dispatching : constant String := "";
   Local_Interrupt_States : constant String := "";

   Is_Elaborated : Boolean := False;

   procedure finalize_library is
   begin
      E182 := E182 - 1;
      declare
         procedure F1;
         pragma Import (Ada, F1, "system__tasking__protected_objects__entries__finalize_spec");
      begin
         F1;
      end;
      E103 := E103 - 1;
      declare
         procedure F2;
         pragma Import (Ada, F2, "ada__text_io__finalize_spec");
      begin
         F2;
      end;
      declare
         procedure F3;
         pragma Import (Ada, F3, "system__file_io__finalize_body");
      begin
         E109 := E109 - 1;
         F3;
      end;
      declare
         procedure Reraise_Library_Exception_If_Any;
            pragma Import (Ada, Reraise_Library_Exception_If_Any, "__gnat_reraise_library_exception_if_any");
      begin
         Reraise_Library_Exception_If_Any;
      end;
   end finalize_library;

   procedure adafinal is
      procedure s_stalib_adafinal;
      pragma Import (C, s_stalib_adafinal, "system__standard_library__adafinal");

      procedure Runtime_Finalize;
      pragma Import (C, Runtime_Finalize, "__gnat_runtime_finalize");

   begin
      if not Is_Elaborated then
         return;
      end if;
      Is_Elaborated := False;
      Runtime_Finalize;
      s_stalib_adafinal;
   end adafinal;

   type No_Param_Proc is access procedure;

   procedure adainit is
      Main_Priority : Integer;
      pragma Import (C, Main_Priority, "__gl_main_priority");
      Time_Slice_Value : Integer;
      pragma Import (C, Time_Slice_Value, "__gl_time_slice_val");
      WC_Encoding : Character;
      pragma Import (C, WC_Encoding, "__gl_wc_encoding");
      Locking_Policy : Character;
      pragma Import (C, Locking_Policy, "__gl_locking_policy");
      Queuing_Policy : Character;
      pragma Import (C, Queuing_Policy, "__gl_queuing_policy");
      Task_Dispatching_Policy : Character;
      pragma Import (C, Task_Dispatching_Policy, "__gl_task_dispatching_policy");
      Priority_Specific_Dispatching : System.Address;
      pragma Import (C, Priority_Specific_Dispatching, "__gl_priority_specific_dispatching");
      Num_Specific_Dispatching : Integer;
      pragma Import (C, Num_Specific_Dispatching, "__gl_num_specific_dispatching");
      Main_CPU : Integer;
      pragma Import (C, Main_CPU, "__gl_main_cpu");
      Interrupt_States : System.Address;
      pragma Import (C, Interrupt_States, "__gl_interrupt_states");
      Num_Interrupt_States : Integer;
      pragma Import (C, Num_Interrupt_States, "__gl_num_interrupt_states");
      Unreserve_All_Interrupts : Integer;
      pragma Import (C, Unreserve_All_Interrupts, "__gl_unreserve_all_interrupts");
      Detect_Blocking : Integer;
      pragma Import (C, Detect_Blocking, "__gl_detect_blocking");
      Default_Stack_Size : Integer;
      pragma Import (C, Default_Stack_Size, "__gl_default_stack_size");
      Default_Secondary_Stack_Size : System.Parameters.Size_Type;
      pragma Import (C, Default_Secondary_Stack_Size, "__gnat_default_ss_size");
      Leap_Seconds_Support : Integer;
      pragma Import (C, Leap_Seconds_Support, "__gl_leap_seconds_support");
      Bind_Env_Addr : System.Address;
      pragma Import (C, Bind_Env_Addr, "__gl_bind_env_addr");

      procedure Runtime_Initialize (Install_Handler : Integer);
      pragma Import (C, Runtime_Initialize, "__gnat_runtime_initialize");

      Finalize_Library_Objects : No_Param_Proc;
      pragma Import (C, Finalize_Library_Objects, "__gnat_finalize_library_objects");
      Binder_Sec_Stacks_Count : Natural;
      pragma Import (Ada, Binder_Sec_Stacks_Count, "__gnat_binder_ss_count");
      Default_Sized_SS_Pool : System.Address;
      pragma Import (Ada, Default_Sized_SS_Pool, "__gnat_default_ss_pool");

   begin
      if Is_Elaborated then
         return;
      end if;
      Is_Elaborated := True;
      Main_Priority := -1;
      Time_Slice_Value := -1;
      WC_Encoding := 'b';
      Locking_Policy := ' ';
      Queuing_Policy := ' ';
      Task_Dispatching_Policy := ' ';
      System.Restrictions.Run_Time_Restrictions :=
        (Set =>
          (False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False, False, True, False, False, 
           False, False, False, False, False, False, False, False, 
           False, False, False, False),
         Value => (0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
         Violated =>
          (False, False, False, False, True, True, True, False, 
           True, False, False, True, True, True, True, False, 
           False, False, False, False, True, True, False, True, 
           True, False, True, True, True, True, False, False, 
           False, False, False, True, False, False, True, False, 
           False, False, False, True, False, True, False, True, 
           True, False, True, True, False, False, True, False, 
           False, True, False, True, True, True, True, True, 
           False, False, True, False, True, True, True, False, 
           True, True, False, True, True, True, True, False, 
           False, True, False, False, False, False, True, True, 
           True, False, False, False),
         Count => (0, 0, 0, 0, 4, 3, 5, 0, 0, 0),
         Unknown => (False, False, False, False, False, False, True, False, False, False));
      Priority_Specific_Dispatching :=
        Local_Priority_Specific_Dispatching'Address;
      Num_Specific_Dispatching := 0;
      Main_CPU := -1;
      Interrupt_States := Local_Interrupt_States'Address;
      Num_Interrupt_States := 0;
      Unreserve_All_Interrupts := 0;
      Detect_Blocking := 0;
      Default_Stack_Size := -1;
      Leap_Seconds_Support := 0;

      ada_main'Elab_Body;
      Default_Secondary_Stack_Size := System.Parameters.Runtime_Default_Sec_Stack_Size;
      Binder_Sec_Stacks_Count := 1;
      Default_Sized_SS_Pool := Sec_Default_Sized_Stacks'Address;

      Runtime_Initialize (1);

      Finalize_Library_Objects := finalize_library'access;

      System.Soft_Links'Elab_Spec;
      System.Exception_Table'Elab_Body;
      E025 := E025 + 1;
      Ada.Io_Exceptions'Elab_Spec;
      E070 := E070 + 1;
      Ada.Strings'Elab_Spec;
      E055 := E055 + 1;
      Ada.Containers'Elab_Spec;
      E040 := E040 + 1;
      System.Exceptions'Elab_Spec;
      E027 := E027 + 1;
      System.Os_Lib'Elab_Body;
      E075 := E075 + 1;
      Ada.Strings.Maps'Elab_Spec;
      Ada.Strings.Maps.Constants'Elab_Spec;
      E061 := E061 + 1;
      Interfaces.C'Elab_Spec;
      System.Soft_Links.Initialize'Elab_Body;
      E021 := E021 + 1;
      E013 := E013 + 1;
      E057 := E057 + 1;
      E045 := E045 + 1;
      System.Object_Reader'Elab_Spec;
      System.Dwarf_Lines'Elab_Spec;
      E050 := E050 + 1;
      System.Traceback.Symbolic'Elab_Body;
      E039 := E039 + 1;
      E081 := E081 + 1;
      Ada.Numerics'Elab_Spec;
      E122 := E122 + 1;
      Ada.Tags'Elab_Spec;
      Ada.Tags'Elab_Body;
      E006 := E006 + 1;
      Ada.Streams'Elab_Spec;
      E105 := E105 + 1;
      System.File_Control_Block'Elab_Spec;
      E113 := E113 + 1;
      System.Finalization_Root'Elab_Spec;
      E112 := E112 + 1;
      Ada.Finalization'Elab_Spec;
      E110 := E110 + 1;
      System.File_Io'Elab_Body;
      E109 := E109 + 1;
      System.Task_Info'Elab_Spec;
      E158 := E158 + 1;
      System.Task_Primitives.Operations'Elab_Body;
      E152 := E152 + 1;
      Ada.Calendar'Elab_Spec;
      Ada.Calendar'Elab_Body;
      E119 := E119 + 1;
      Ada.Calendar.Delays'Elab_Body;
      E117 := E117 + 1;
      Ada.Real_Time'Elab_Spec;
      Ada.Real_Time'Elab_Body;
      E192 := E192 + 1;
      Ada.Text_Io'Elab_Spec;
      Ada.Text_Io'Elab_Body;
      E103 := E103 + 1;
      System.Random_Seed'Elab_Body;
      E127 := E127 + 1;
      System.Tasking.Initialization'Elab_Body;
      E172 := E172 + 1;
      System.Tasking.Protected_Objects'Elab_Body;
      E180 := E180 + 1;
      System.Tasking.Protected_Objects.Entries'Elab_Spec;
      E182 := E182 + 1;
      System.Tasking.Queuing'Elab_Body;
      E186 := E186 + 1;
      System.Tasking.Stages'Elab_Body;
      E190 := E190 + 1;
      Constants'Elab_Spec;
      E123 := E123 + 1;
      Computer'Elab_Spec;
      Computer'Elab_Body;
      E198 := E198 + 1;
      Goalserver'Elab_Spec;
      Goalserver'Elab_Body;
      E131 := E131 + 1;
      Productserver'Elab_Spec;
      Productserver'Elab_Body;
      E194 := E194 + 1;
      Boss'Elab_Body;
      E115 := E115 + 1;
      Client'Elab_Spec;
      Client'Elab_Body;
      E196 := E196 + 1;
      Serviceserver'Elab_Spec;
      Serviceserver'Elab_Body;
      E202 := E202 + 1;
      Worker'Elab_Spec;
      Worker'Elab_Body;
      E208 := E208 + 1;
   end adainit;

   procedure Ada_Main_Program;
   pragma Import (Ada, Ada_Main_Program, "_ada_main");

   function main
     (argc : Integer;
      argv : System.Address;
      envp : System.Address)
      return Integer
   is
      procedure Initialize (Addr : System.Address);
      pragma Import (C, Initialize, "__gnat_initialize");

      procedure Finalize;
      pragma Import (C, Finalize, "__gnat_finalize");
      SEH : aliased array (1 .. 2) of Integer;

      Ensure_Reference : aliased System.Address := Ada_Main_Program_Name'Address;
      pragma Volatile (Ensure_Reference);

   begin
      gnat_argc := argc;
      gnat_argv := argv;
      gnat_envp := envp;

      Initialize (SEH'Address);
      adainit;
      Ada_Main_Program;
      adafinal;
      Finalize;
      return (gnat_exit_status);
   end;

--  BEGIN Object file/option list
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/constants.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/computer.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/goalserver.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/productserver.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/boss.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/client.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/serviceserver.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/worker.o
   --   /home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/main.o
   --   -L/home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/
   --   -L/home/telepchuk/Documents/6 semestr/Współbieżne/236723/Współbieżne/lista3/AdaFactory/obj/
   --   -L/opt/GNAT/2018/lib/gcc/x86_64-pc-linux-gnu/7.3.1/adalib/
   --   -static
   --   -lgnarl
   --   -lgnat
   --   -lpthread
   --   -lrt
   --   -ldl
--  END Object file/option list   

end ada_main;
