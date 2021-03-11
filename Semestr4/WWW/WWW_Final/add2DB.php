<?php
	$root = "./";
	require_once($root."php/myDB.php");
	require_once($root."php/tags.php");
	require_once($root."php/page.php");
	require_once($root."php/opinions.php");

	$OK = isset($_POST["NICK"]) && isset($_POST["SUBJ"]) && isset($_POST["CONTENT"]) && isset($_POST["CAPTCHA"]) &&
		   ($_SESSION["captcha"] == $_POST["CAPTCHA"]) ;

	if (!$OK){
		setcookie("error_captcha","1");
		setcookie("content",$_POST["CONTENT"]);
		setcookie("nick",$_POST["NICK"]);
		setcookie("subject",$_POST["SUBJ"]);
		
		header("location: addOpinion.php");
	}else{
		//UNSETCOOKIE
		setcookie("error_captcha","",1);
		setcookie("nick","",1);
		setcookie("content","",1);
		setcookie("subject","",1);
		//CREATE AND EXECUTE COMMAND
		$NICK = substr(trim($_POST["NICK"]),0,15);
		$SUBJ = substr($_POST["SUBJ"],0,10);
		$CONTENT = strip_tags(trim($_POST["CONTENT"]),"<p><strong>");

		$db = new portaldb();
		$command = $db->prepare("INSERT INTO opinie (NICK, Subject, Content) VALUES (?, ?, ?)");
		$command->bind_param("sss", $NICK, $SUBJ, $CONTENT);
		$command->execute();
		//CLOSE
		$command->close();
		$db->close();
		$_SESSION = [];
		session_destroy();   
		session_unset();
		header("location: opinions.php");
	}
?>