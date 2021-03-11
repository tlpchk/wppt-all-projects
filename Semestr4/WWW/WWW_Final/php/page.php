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
{{SCRIPTS}}
	<script type="text/x-mathjax-config">
    MathJax.Hub.Config({
      tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}, 
      CommonHTML: { linebreaks: { automatic: true } }, 
      "HTML-CSS": { linebreaks: { automatic: true } }, 
      SVG: { linebreaks: { automatic: true } } 
    })
  </script>
  <script async src="https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.4/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
</head>
<body>
<div id="main">
EOT;


$PAGE_HEADER =<<<EOT
<header class="container" id="main-header">
	<div id="pwr-logo">
		<img id="imgpwr" src="{{ROOT}}img/pwr-logo.png" alt="Logotyp Politechniki Wrocławskiej"/>
	</div>
	<a href="{{ROOT}}index.php"><div id="mylogo" >
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

function generateMainHeader($root){
	$header = '<header id = "main-header" class="container" >'."\n";
	$header.= '	<div id="pwr-logo">'."\n";
	$header.= '		<img id="imgpwr" src="'.$root.'img/pwr-logo.png" alt="Logotyp Politechniki Wrocławskiej"/>'."\n";
	$header.= '	</div>'."\n";
	$header.= '	<a href="'.$root.'index.php"><div id="mylogo">'."\n";
	$header.= '		<h1>Maksym Telepchuk</h1>'."\n";
	$header.= '		<p>Student Informatyki na WPPT, PWr</p>'."\n";
	$header.= '	</div></a>'."\n";
	$header.= '</header>'."\n";

	return $header;
}



class Page {
	private $title        = "";
	private $description  = "";
	private $root;
	private $cssfiles   = [] ;
	private $scripts = [] ;

	public function SetDescription($s) {
		$this->description = $s;
	}
	
	public function setRoot($root){
		$this->root = $root;
	}

	public function __construct($title = "",$root = "") {
		$this->title = $title;
		$this->setRoot($root);
	}
	
	public function addStyles($styles){
		foreach ($styles as $s){
			$this->cssfiles []= $s;
		}
	}
	public function addJS($scripts){
		foreach ($scripts as $s){
			$this->scripts []= $s;
		}
	}
	
	public function linkStyleSheet($name){
		$link = "<link rel=\"stylesheet\" href=\"{{R}}css/$name.css\">";
		$link =  str_replace('{{R}}',$this->root,$link);
		return $link;
	}
	 
	public function scriptJS($src){
		$script = '<script src="'.$this->root.'js/'.$src.'"></script>';
		return $script;
	}
	 
	public function Begin() {
		global $HEADER;
		$s = str_replace(["{{TITLE}}", "{{DESCRIPTION}}"], [$this->title, $this->description], $HEADER);

		$X = [];
		$CSS = $this->cssfiles;
		$JS = $this->scripts;
		/*foreach ($CSS as $link) {
				$X []= "	".$this->linkStyleSheet($link);
			}*/
		$X []="	".$this->linkStyleSheet("myStyle");
		$s = str_replace("{{STYLES}}", join("\n",$X), $s);
		
		foreach ($JS as $link) {
				$X []= "	".$this->scriptJS($link);
			}
		$s = str_replace("{{SCRIPTS}}", join("\n",$X), $s);

		return preg_replace('/^\h*\v+/m', '', $s)."\n";
	}

	
	public function PageHeader($subheader){
    	global $PAGE_HEADER;
    	return str_replace(["{{SUBHEADER}}","{{ROOT}}"],[$subheader,$this->root], $PAGE_HEADER)."\n";
 	 }
   
  public function End() {
    global $FOOTER;
    return $FOOTER."\n";    
  }  

} 

?>
