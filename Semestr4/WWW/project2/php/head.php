<?php
	function generateHead($title,$author,$links){
		$head = "<head>\n"; 
		$head.=	"	<meta charset=\"utf-8\">\n";
		$head.= "	<title>$title</title>\n";
		$head.= "	<meta name=\"author\" content=\"$author\">\n";
		$head.= "	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n";
		
		foreach ($links as $link) {
			$head.= "	".linkStyleSheet($link)."\n";
		}

		$head.= "</head>\n" ;
		return $head;
	}

	function linkStyleSheet($name){
		$link = "<link rel=\"stylesheet\" href=\"./css/$name.css\">";
		return $link;
	}
?>