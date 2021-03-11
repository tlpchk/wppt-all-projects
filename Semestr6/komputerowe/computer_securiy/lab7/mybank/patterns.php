<?php

$TRANSFER_FORM= <<<EOT
<div class="header">
    <h2>Send transfer</h2>
</div>

<form method="post" action="transfer.php">

    {{ERRORS}}

    <div class="input-group">
        <label>Your balance</label>
        <p>{{ACCOUNT}} : {{AMOUNT}} PLN</p>
    </div>
    <div class="input-group">
        <label>Receiver</label>
        <input type="text" name="receiver" required>
    </div>
    <div class="input-group">
        <label>Title</label>
        <input type="text" name="title" required>
    </div>

    <div class="input-group">
        <label>Amount</label>
        <input type="number" name="amount" step="0.01" placeholder= "0.00" required>
    </div>


    <div class="input-group">
        <button type="submit" class="btn" name="verify">Verify</button>
    </div>
</form>
EOT;


$VERIFICATION=<<<EOT
<div class="header">
    <h2>Verification</h2>
</div>


<form name="hidden_form"  method="post"   action="transfer.php">
    <p>From: {{SENDER}}</p>
    <p>To: {{RECEIVER}}</p>
    <p>Title: {{TITLE}}</p>
    <p>Amount: {{AMOUNT}}</p>

    <input type="hidden"  name="receiver" value = "{{RECEIVER}}" id = "weakness">
    <input type="hidden"  name="title"    value = "{{TITLE}}">
    <input type="hidden"  name="amount"   value = "{{AMOUNT}}">
    
    <div class="input-group">
        <label>Verify your password</label>
        <input type="password" name="password" required>
    </div>
    
    <div class="input-group">
        <button type="submit" class="btn" name="transfer">Send</button>
    </div>
</form>
EOT;

$CONFIRMATION=<<<EOT
<div class="header">
    <h2>Confirmation</h2>
</div>

<div class="content">

    {{MSG}}

    <p>Sender: {{SENDER}}</p>
    <p>Receiver: {{RECEIVER}}</p>
    <p>Title: {{TITLE}}</p>
    <p>Amount: {{AMOUNT}} PLN</p>
</div>
EOT;


$COMPLETE_TRANSACTION=<<<EOT
<div class="success">
    <p>Success! Your money in my pocket :)</p>
</div>
EOT;

?>