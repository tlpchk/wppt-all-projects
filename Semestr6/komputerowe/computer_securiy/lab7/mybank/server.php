<?php
	session_start();

	$errors = array();
	$db = new PDO("mysql:host=localhost;dbname=mybank","root","");


	// REGISTER USER
	if (isset($_POST['reg_user'])) {
	    $username = $_POST['username'];
		$email = $_POST['email'];
		$password_1 = $_POST['password_1'];
		$password_2 = $_POST['password_2'];
        $amount = 100.0;


		// form validation: ensure that the form is correctly filled
		if (empty($username)) { array_push($errors, "Username is required"); }
		if (empty($email)) { array_push($errors, "Email is required"); }
		if (empty($password_1) || empty($password_2)) { array_push($errors, "Password is required"); }

		if ($password_1 != $password_2) {
			array_push($errors, "The two passwords do not match");
		}

        $stmt = $db->prepare("SELECT username FROM users Where ?");
        $stmt->bindParam(1,$username);
        $stmt->execute();
        $results = $stmt->fetch();

        if($results->username != null){
            array_push($errors, "User with that username exist");
        }

		// register user if there are no errors in the form
		if (count($errors) == 0) {
			$password = password_hash($password_1, PASSWORD_ARGON2I);//encrypt the password before saving in the database

            $stmt = $db->prepare("INSERT INTO users (username, email, password,amount)VALUES(?, ?, ?, ?)");
            $stmt->bindParam(1,$username);
            $stmt->bindParam(2,$email);
            $stmt->bindParam(3,$password);
            $stmt->bindParam(4,$amount);


            if($stmt->execute()) {
                $_SESSION['username'] = $username;
                $_SESSION['user_type'] = "user";
                header('location: index.php');
            }else{
                array_push($errors, "Registration error");
            }
		}
	}

	// ...

	// LOGIN USER
	if (isset($_POST['login_user'])) {
        $username = $_POST['username'];
        $password = $_POST['password'];

        if (empty($username)) {
            array_push($errors, "Username is required");
        }
        if (empty($password)) {
            array_push($errors, "Password is required");
        }

        if (count($errors) == 0) {
            #$stmt = $db->prepare("SELECT * FROM users WHERE username=?");
            #$stmt->bindParam(1, $username);

            $query = "SELECT * FROM users WHERE username='$username'";
            $stmt = $db->prepare($query);
            $stmt->execute();

            $data = $stmt->fetch();

            $user = $data['username'];
            $email = $data['email'];
            $hashpass = $data['password'];
            $usertype = $data['user_type'];
            $verify = password_verify($password, $hashpass);
            if ($verify) {
                $_SESSION['username'] = $user;

                if ($usertype == "user") {
                    $_SESSION['user_type'] = "user";
                } elseif ($usertype == "admin") {
                    $_SESSION['user_type'] = "admin";
                }
                header('location: index.php');
            } else {
                array_push($errors, $query);
                array_push($errors, "Wrong username/password combination");
            }
        }
    }



    // TRANSFER
    if (isset($_POST['transfer']) && isset($_SESSION['username'])) {
        $sender = $_SESSION['username'];
        $receiver=$_POST['receiver'];
        $title = $_POST['title'];
        $amount = $_POST['amount'];
        $password =  $_POST['password'];


        $password = password_hash($password_1, PASSWORD_ARGON2I);
        $result = getColumnsByUsername($db, $sender, ["amount"])[0];

        $stmt = $db->prepare("call transfer(?,?,?,?,?)");
        $stmt->bindParam(1,$sender);
        $stmt->bindParam(2,$receiver);
        $stmt->bindParam(3,$title);
        $stmt->bindParam(4,$amount);
        $stmt->bindParam(5,$password);

        $stmt->execute();

        $result = $result - getColumnsByUsername($db, $sender, ["amount"])[0];

        if ($result == 0){
            array_push($errors, "Transakcja nie powiodła się");
        }
    }

    function getHistory(){
        global $HISTORY,$TR,$db;

        if ($db->connect_error) {
            die("Connection failed: " . $db->connect_error);
        }

        $transfers = "";
        $username = $_SESSION['username'];

        if(!empty($username)){

            $stmt = $db->prepare("select * from transfers where sender = ? OR receiver = ? order by date DESC LIMIT 30");
            $stmt->bindParam(1,$username);
            $stmt->bindParam(2,$username);

            $stmt->execute();


            while($row = $stmt->fetch()) {

                $transfers.= (string)str_replace(["{{SENDER}}","{{RECEIVER}}","{{TITLE}}","{{DATE}}","{{AMOUNT}}"],
                                                        [$row["sender"],$row["receiver"],$row["title"],$row["date"],$row["amount"]],$TR);
            }

            return (string)str_replace("{{TRANSFERS}}",$transfers,$HISTORY);
        }
    }

    function getColumnsByUsername($db, $username, $columns)
    {
        $data = array();

        $stmt = $db->prepare("SELECT * FROM users WHERE username = ?");
        $stmt->bindParam(1, $username);

        $stmt->execute();

        $result = $stmt->fetch(PDO::FETCH_ASSOC);

        if ($result['username'] == $username) {

            foreach ($columns as $column) {
                $data [] = $result["$column"];
            }
        }
        return $data;
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


?>
