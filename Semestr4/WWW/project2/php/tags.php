<?php
	function openTag($name,$class = "",$id = "",$other = array()){
		$tag= "<$name ";
		if(strlen($id) != 0){ $tag.= "id = \"$id\" ";}
		if(strlen($class) != 0){ $tag.= "class = \"$class\" ";}
		foreach ($other as $atribute) {
			$tag.= "$atribute[0]=\"$atribute[1]\" ";
		}
		$tag.= ">\n";
		return $tag;
	}
							 
	function closeTag($name){
		return "</$name>\n";
	}

	function openSection($class="", $id=""){
		return openTag("section",$class,$id);
	}

	function closeSection(){
		return closeTag("section");
	}

	function openDiv($class="", $id=""){
		return openTag("div",$class,$id);
	}

	function closeDiv(){
		return closeTag("div");
	}


	function openBody(){
		return openTag("body");
	}

	function closeBody(){
		return closeTag("body");
	}

	function openHTML($lang = "pl"){
		return openTag("html","","",array(array("lang",$lang)));
	}

	function closeHTML(){
		return closeTag("html");
	}


	function openContainer(){
		return '<div class="container">'."\n";
	}

	function closeContainer(){
		return closeDiv();
	}

	function closeNav(){
		return closeTag("nav");
	}
							 
?>