<?php
if(isset($_POST['reset_pass']))
{
  $db = mysql_connect('127.0.0.1','root','', 'mybank');

  $email = mysqli_real_escape_string($db, $_POST['email']);

  if (empty($email)) { array_push($errors, "Email is required"); }

	if (count($errors) == 0) {
		$email = md5($email);

		$query = mysql_query("SELECT email,password FROM user WHERE email='$email'");
    while($row = mysql_fetch_array($query))
    {
      $email=md5($row['email']);
      $pass=md5($row['password']);
    }
    $link="<a href='www.samplewebsite.com/reset.php?key=".$email."&reset=".$pass."'>Click To Reset password</a>";
    require_once('phpmail/PHPMailerAutoload.php');
    $mail = new PHPMailer();
    $mail->CharSet =  "utf-8";
    $mail->IsSMTP();
    // enable SMTP authentication
    $mail->SMTPAuth = true;
    // GMAIL username
    $mail->Username = "236723@studetn.pwr.edu.pl";
    // GMAIL password
    $mail->Password = "sH31Z3nb3rgt1";
    $mail->SMTPSecure = "ssl";
    // sets GMAIL as the SMTP server
    $mail->Host = "smtp.gmail.com";
    // set the SMTP port for the GMAIL server
    $mail->Port = "465";
    $mail->From='your_gmail_id@gmail.com';
    $mail->FromName='your_name';
    $mail->AddAddress('reciever_email_id', 'reciever_name');
    $mail->Subject  =  'Reset Password';
    $mail->IsHTML(true);
    $mail->Body    = 'Click On This Link to Reset Password '.$pass.'';
    if($mail->Send())
    {
      echo "Check Your Email and Click on the link sent to your email";
    }
    else
    {
      echo "Mail Error - >".$mail->ErrorInfo;
    }
  }
}
?>
