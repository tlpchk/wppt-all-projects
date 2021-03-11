<?php
require_once("server.php");
require_once("errors.php");

$REGISTER_FORM=<<<EOT
<form method="post" action="register.php">
    
    {{ERRORS}}
    
    <div class="input-group">
        <label>Username</label>
        <input type="text" name="username" value="{{USERNAME}}">
    </div>
    <div class="input-group">
        <label>Email</label>
        <input type="email" name="email" value="{{EMAIL}}">
    </div>
    <div class="input-group">
        <label>Password</label>
        <input type="password" name="password_1">
    </div>
    <div class="input-group">
        <label>Confirm password</label>
        <input type="password" name="password_2">
    </div>
    <div class="input-group">
        <button type="submit" class="btn" name="reg_user">Register</button>
    </div>
    <p>
        Already a member? <a href="login.php">Sign in</a>
    </p>
</form>
EOT;

function register_form(){
    global $REGISTER_FORM, $username,$email;
    return (string)str_replace(["{{ERRORS}}", "{{USERNAME}}","{{EMAIL}}"],[getErrors(),$username,$email],$REGISTER_FORM);
}
?>

<!DOCTYPE html>
<html>
<head>
	<title>RegistrationL</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div class="header">
		<h2>Registration</h2>
	</div>

    <?php ECHO register_form(); ?>

</body>
</html>
