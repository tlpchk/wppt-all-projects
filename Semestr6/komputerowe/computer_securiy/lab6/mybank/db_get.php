<?php
function validate_data($data){
    $data = trim($data);
    $data = stripslashes($data);
    $data = strip_tags($data);
    $data = htmlspecialchars($data);
    $data = mysqli_real_escape_string($data);
    return $data;
}

function getColumnsByUsername($db, $username, $columns){
    $data = array();

    $stmt = $db->prepare("SELECT * FROM users WHERE username = ?");
    $stmt->bind_param('s',$username);

    $stmt->execute();

    $result = $stmt->get_result();

    if (mysqli_num_rows($result) == 1) {
        $row = $result->fetch_assoc();

        foreach($columns as $column) {
            $data []= $row["$column"];
        }
    }
    return $data;
}
?>