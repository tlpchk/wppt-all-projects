<?php
require_once("server.php");
require_once("errors.php");


$LOGIN_FORM=<<<EOT
 <div class="header">
        <h2>Your bank</h2>
    </div>
    
<form method="post" action="login.php">
   
    {{ERRORS}}
    
    <div class="input-group">
        <label>Username</label>
        <input type="text" name="username" >
    </div>
    <div class="input-group">
        <label>Password</label>
        <input type="password" name="password">
    </div>
    <div class="input-group">
        <button type="submit" class="btn" name="login_user">Login</button>
    </div>
    <p>
        Not yet a member? <a href="register.php">Sign up</a>
    </p>
</form>
EOT;

function login_form(){
    global $LOGIN_FORM;
    return (string)str_replace(["{{ERRORS}}"],[getErrors()],$LOGIN_FORM);
}
?>


<!DOCTYPE html>
<html>
<head>
    <title>Log In</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>


<?php ECHO login_form()?>


</body>
</html>
