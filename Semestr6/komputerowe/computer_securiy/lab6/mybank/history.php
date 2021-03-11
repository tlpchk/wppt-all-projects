<?php
session_start();

require_once('server.php');

?>

<!DOCTYPE html>
<html>
<head>
    <title>History</title>
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

<div class="header">
    <h2>History</h2>
</div>

<div class="content">

<?php ECHO getHistory()?>
</div>

</body>
</html>

