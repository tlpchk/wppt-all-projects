<?php
session_start();
require_once("errors.php");
if (!isset($_SESSION['username'])) {
    $_SESSION['msg'] = "You must log in first";
    header('location: login.php');
}

if (isset($_GET['logout'])) {
    session_destroy();
    unset($_SESSION['username']);
    header("location: login.php");
}
?>
<!DOCTYPE html>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
	<div class="header">
		<h2>Home Page</h2>
	</div>
	<div class="content">


		<?php  if (isset($_SESSION['username']) && $_SESSION['user_type'] == "user") {
            ECHO "<a href = 'transfer.php'>Make transfer</a> \n";
            ECHO "<a href = 'history.php'>History</a> \n";
            ECHO getErrors();?>
			<h1>Welcome, <strong><?php echo $_SESSION['username']; ?></strong></h1>
			<p> <a href="index.php?logout='1'" style="color: red;">logout</a> </p>

		<?php }elseif (isset($_SESSION['username']) && $_SESSION['user_type'] == "admin") {
            ECHO "<a href = 'transfer.php'>Admin super power</a> \n";
            ECHO getErrors();?>
            <h1><strong>ADMIN</strong></h1>
            <p> <a href="index.php?logout='1'" style="color: red;">logout</a> </p>
        <?php } ?>
	</div>

</body>
</html>
