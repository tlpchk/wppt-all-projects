<?php
$CAPTCHA = '\begin{bmatrix}{{A}} & {{B}} \\\\ {{C}} & {{D}} \end{bmatrix}';

function answer($a,$b,$c,$d){
	return (int)(($a*$d)-($b*$c));
}

function generateCaptcha($min,$max){
	global $CAPTCHA;
	$a = rand($min,$max);
	$b = rand($min,$max);
	$c = rand($min,$max);
	$d = rand($min,$max);
	
	$captcha = str_replace(["{{A}}","{{B}}","{{C}}","{{D}}"],[$a,$b,$c,$d],$CAPTCHA);
	return ["captcha" => $captcha,
			"key" => answer($a,$b,$c,$d)];
}
?>