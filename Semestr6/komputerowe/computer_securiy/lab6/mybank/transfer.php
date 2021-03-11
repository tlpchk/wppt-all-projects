<?php
    session_start();

    require_once("server.php");
    require_once("patterns.php");
    require_once('db_get.php');
    require_once('errors.php');


    function transfer_form(){
        $db = mysqli_connect('127.0.0.1', 'root', '','mybank');
        global $TRANSFER_FORM;

        $data = getColumnsByUsername($db, $_SESSION['username'],["username","amount"]);
        $account = $data[0];
        $amount = sprintf("%.2f", floatval($data[1]));

        $db->close();

        return (string)str_replace(["{{ERRORS}}","{{ACCOUNT}}","{{AMOUNT}}"],[getErrors(),$account,$amount],$TRANSFER_FORM);
    }

    function verification(){
        global $VERIFICATION;

        return (string)str_replace(["{{SENDER}}","{{RECEIVER}}","{{TITLE}}","{{AMOUNT}}"],
                                   [$_SESSION['username'], $_POST['receiver'],$_POST['title'],$_POST['amount']],
                                    $VERIFICATION);
    }

    function confirmation(){
        global $CONFIRMATION,$COMPLETE_TRANSACTION,$errors;

        if (count($errors) > 0){
            $CONFIRMATION = (string)str_replace("{{MSG}}",getErrors(),$CONFIRMATION);
        }
        else{
            $CONFIRMATION = (string)str_replace("{{MSG}}",$COMPLETE_TRANSACTION,$CONFIRMATION);
        }


        return (string)str_replace(["{{SENDER}}","{{RECEIVER}}","{{TITLE}}","{{AMOUNT}}"],
                                   [$_SESSION['username'], $_POST['receiver'],$_POST['title'],$_POST['amount']],
                                    $CONFIRMATION);
    }


?>

<!DOCTYPE html>
<html>
<head>
	<title>Przelew</title>
	<link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

<?php

if (isset($_POST['verify'])){
    ECHO verification();
    unset($_POST['verify']);
}
else if(isset($_POST['transfer'])){

    ECHO confirmation();
    unset($_POST['transfer']);
}
else if(isset($_SESSION['username'])){
    ECHO transfer_form();
}else{
    ECHO "PLEASE, LOG IN";
}
?>

</body>
</html>
