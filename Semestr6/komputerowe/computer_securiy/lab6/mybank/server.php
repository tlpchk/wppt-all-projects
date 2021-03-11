<?php
	session_start();

	require_once("db_get.php");

	$username = "";
	$email    = "";
	$errors = array();

	// connect to database
	$db = mysqli_connect('127.0.0.1', 'root', '','mybank');

    if ($db->connect_error) {
        die("Connection failed: " . $db->connect_error);
    }


	// REGISTER USER
	if (isset($_POST['reg_user'])) {

		// receive all input values from the form
		$username = mysqli_real_escape_string($db, $_POST['username']);
		$email = mysqli_real_escape_string($db, $_POST['email']);
		$password_1 = mysqli_real_escape_string($db, $_POST['password_1']);
		$password_2 = mysqli_real_escape_string($db, $_POST['password_2']);
        $amount = 100.0;

		// form validation: ensure that the form is correctly filled
		if (empty($username)) { array_push($errors, "Username is required"); }
		if (empty($email)) { array_push($errors, "Email is required"); }
		if (empty($password_1)) { array_push($errors, "Password is required"); }

		if ($password_1 != $password_2) {
			array_push($errors, "The two passwords do not match");
		}

		// register user if there are no errors in the form
		if (count($errors) == 0) {


			$password = md5($password_1);//encrypt the password before saving in the database

            $stmt = $db->prepare("INSERT INTO users (username, email, password,amount)VALUES(?, ?, ?,?)");
            $stmt->bind_param('sssd',$username,$email,$password,$amount);

            $stmt->execute();


			$_SESSION['username'] = $username;
			$_SESSION['success'] = "1";
			header('location: index.php');
		}

	}

	// ...

	// LOGIN USER
	if (isset($_POST['login_user'])) {
		$username = mysqli_real_escape_string($db, $_POST['username']);
		$password = mysqli_real_escape_string($db, $_POST['password']);

		if (empty($username)) {
			array_push($errors, "Username is required");
		}
		if (empty($password)) {
			array_push($errors, "Password is required");
		}

		if (count($errors) == 0) {
			$password = md5($password);

            $stmt = $db->prepare("SELECT * FROM users WHERE username=? AND password=?");
            $stmt->bind_param('ss',$username,$password);

            $stmt->execute();

			$results = $stmt->get_result();

			if (mysqli_num_rows($results) == 1) {
				$_SESSION['username'] = $username;
				$_SESSION['success'] = "1";
				header('location: index.php');
			}else {
				array_push($errors, "Wrong username/password combination");
			}
		}
	}


    // TRANSFER

    if (isset($_POST['transfer'])) {
        $sender = $_SESSION['username'];
        $receiver= mysqli_real_escape_string($db, $_POST['receiver']);
        $title = mysqli_real_escape_string($db, $_POST['title']);
        $amount = mysqli_real_escape_string($db, $_POST['amount']);
        $password = mysqli_real_escape_string($db, $_POST['password']);



        $password = md5($password);
        $result = getColumnsByUsername($db, $sender, ["amount"])[0];

        $stmt = $db->prepare("call transfer(?,?,?,?,?)");
        $stmt->bind_param('sssds',$sender,$receiver,$title,$amount,$password);

        $stmt->execute();

        $result = $result - getColumnsByUsername($db, $sender, ["amount"])[0];

        if ($result == 0){
            array_push($errors, "Transakcja nie powiodła się");
        }
    }

    function getHistory(){

        global $HISTORY,$TR;


        $db = mysqli_connect('127.0.0.1', 'root', '','mybank');

        if ($db->connect_error) {
            die("Connection failed: " . $db->connect_error);
        }

        $transfers = "";
        $username = $_SESSION['username'];

        if(!empty($username)){

            $stmt = $db->prepare("select * from transfers where sender = ? OR receiver = ? order by date DESC LIMIT 30");
            $stmt->bind_param('ss',$username,$username);

            $stmt->execute();

            $result = $stmt->get_result();

            if ($result->num_rows > 0) {
                // output data of each row
                while($row = $result->fetch_assoc()) {
                    $transfers.= (string)str_replace(["{{SENDER}}","{{RECEIVER}}","{{TITLE}}","{{DATE}}","{{AMOUNT}}"],
                                                        [$row["sender"],$row["receiver"],$row["title"],$row["date"],$row["amount"]],$TR);
                }
            }else{
                ECHO "NO RESULTS";
            }

            return (string)str_replace("{{TRANSFERS}}",$transfers,$HISTORY);

        }
        $db->close();
    }

$HISTORY=<<<END
<div class="of">
<table>
	<thead>
		<tr>
			<th>Sender</th>
			<th>Receiver</th>
			<th>Title</th>
			<th>Date</th>
			<th>Amount</th>
		</tr>
	</thead>
	</tbody>
	{{TRANSFERS}}
	</tbody>
</table>
</div>
END;

$TR = "<tr><td>{{SENDER}}</td><td>{{RECEIVER}}</td><td>{{TITLE}}</td><td>{{DATE}}</td><td>{{AMOUNT}}</td></tr>\n	";


$db->close();
?>
