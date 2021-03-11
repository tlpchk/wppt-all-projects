<?php
	function generateMainHeader(){
		$header = '<header id = "main-header" class="container" >'."\n";
		$header.= '	<div id="pwr-logo">'."\n";
		$header.= '		<img src="./img/pwr-logo.png" alt="Logotyp Politechniki Wrocławskiej"/>'."\n";
		$header.= '	</div>'."\n";
		$header.= '	<a href="index.php"><div id="mylogo">'."\n";
		$header.= '		<h1>Maksym Telepchuk</h1>'."\n";
		$header.= '		<p>Student Informatyki na WPPT, PWr</p>'."\n";
		$header.= '	</div></a>'."\n";
		$header.= '</header>'."\n";
		
		return $header;
	}
?>