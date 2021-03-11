<?php

$HEADER =<<<EOT
<!DOCTYPE html>
<html lang="pl">
<head>
	<meta charset="utf-8">
	<title>{{TITLE}}</title> 
	<meta name="description" content= "{{DESCRIPTION}}">
	<meta name="author" content="Maksym Telepchuk">
	<meta name="viewport" content = "width=device-width, initial-scale=1.0"/>
{{STYLES}}
</head>
<body>
<div id="main">
EOT;

$PAGE_HEADER =<<<EOT
<header class="container" id="main-header">
	<div id="pwr-logo">
		<img src="img/pwr-logo.png" alt="Logotyp Politechniki Wrocławskiej"/>
	</div>
	<a href="index.php"><div id="mylogo" >
		<h1>Maksym Telepchuk</h1>
		<span class="subheader">{{SUBHEADER}}</span>
	</div></a>
	
</header >
EOT;

$FOOTER =<<<EOT
</div>
</body>
</html>   
EOT;


class Page {
	private $title        = "";
	private $description  = "";
	private $root         = "";
	private $cssfiles   = [] ;

	public function SetDescription($s) {
		$this->description = $s;
	}
	
	public function setRoot($root){
		$this->root = $root;
	}

	public function __construc($title = "") {
		$this->title = $title;
	}
	
	public function addStyles($styles){
		foreach ($styles as $s){
			$this->cssfiles []= $s;
		}
	}
	
	public function linkStyleSheet($name){
		$link = "<link rel=\"stylesheet\" href=\"{{R}}css/$name.css\">";
		$link = (string) str_replace("{{R}}",$this->root,$link);
		return $link;
	}
	 
	public function Begin() {
		global $HEADER;
		$s = str_replace(["{{TITLE}}", "{{DESCRIPTION}}"], [$this->title, $this->description], $HEADER);

		$X = [];
		$C = $this->cssfiles;
		foreach ($C as $link) {
				$X []= "	".$this->linkStyleSheet($link);
			}
		$s= str_replace("{{STYLES}}", join("\n",$X), $s);

		return preg_replace('/^\h*\v+/m', '', $s)."\n";
		// \h : dowolny poziomy pusty znak
		// \v : dowolny pionowy pusty znak
		// /m : multiline
	}

	
	public function PageHeader($subheader){
    	global $PAGE_HEADER;
    	return str_replace("{{SUBHEADER}}",$subheader, $PAGE_HEADER)."\n";
 	 }
   
  public function End() {
    global $FOOTER;
    return $FOOTER."\n";    
  }  

} 

?>
