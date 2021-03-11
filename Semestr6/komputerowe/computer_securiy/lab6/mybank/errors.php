<?php

function getErrors(){
    global $errors;
    $result = "";
    if (count($errors) > 0) {
        $result .='<div class="error">'."\n";
        foreach ($errors as $error) {
            $result .= "<p>".$error."</p>"."\n";
        }
        $result .= '</div>'."\n";
    }
    return $result;
}
?>