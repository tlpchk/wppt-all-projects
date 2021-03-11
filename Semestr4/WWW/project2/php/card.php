<?php
require_once(__DIR__."/tags.php");

$CARDGROUP=<<<EOT
<section class="container card-group">
<div>
	<h2 class="rb">{{HEADER}}</h2>
</div>
{{CARDS}}
</section>
EOT;

$CARDCONTENT=<<<EOT
<div class="cardsect {{GRID}}">
	<a href="{{HREF}}"><div class="card rb">
		<h2 class="rtb {{TYPE}}">{{TITLE}}</h2>
		<img src="{{IMAGE}}" class="img-card" alt="image">
		<p class="rbb description">
			{{DESCRIPTION}}
		</p>
	</div></a>
</div>
EOT;
	

$CARDLIST=<<<EOT
<div class="cardsect {{GRID}}">
	<div class="card rb">
		<h2 class="rtb {{TYPE}}">{{TITLE}}</h2>
		<ul class="rbb">
{{ITEMS}}
		</ul>
	</div>
</div>
EOT;

$ABOUTME=<<<EOT
<div id="aboutme" class="col-4">
	<p>W 2006-2015 uczyłem się w szkole średniej w małym wsi Michałkowe,Zachodnia Ukraina.</p>
	<p>W 2015-2016 skończyłem szkołe średnią w Kamieńcu-Podolskim, gdzie nauczyłem się polskiego.</p>
	<p>Od 2016 po chwilę obecną jestem studentem informatyki Politechnice Wrocławskiej.</p>
</div>
EOT;

function aboutMe(){
	global $ABOUTME;
	return $ABOUTME."\n";
}

class CardContent{
	private $gridSize = "";
	private $href = "";
	private $type = "";
	private $title = "";
	private $image = "";
	private $description = "";
	public function __construct($gridSize="col-12",$href="",$type="",$title=""){
		$this->gridSize = $gridSize ;
		$this->href = $href ;
		$this->type = $type;
		$this->title = $title ;
	}
	public function setImage($image){
		$this->image=$image;	
	}
	
	public function setDescription($description){
		$this->description = $description;
	}
	
	public function create(){
		global $CARDCONTENT;
		$card = str_replace(["{{GRID}}","{{HREF}}","{{TYPE}}","{{TITLE}}","{{IMAGE}}", "{{DESCRIPTION}}"], [$this->gridSize,$this->href,$this->type,$this->title,	$this->image, $this->description], $CARDCONTENT);
		return preg_replace('/^\h*\v+/m', '', $card);
	}
}

class CardList{
	private $gridSize = "";
	private $type = "";
	private $title = "";
	private $items = [];
	public function __construct($gridSize,$title,$type=""){
		$this->gridSize = $gridSize ;
		$this->type = $type;
		$this->title = $title ;
	}
	
	public function addItem($name,$href=""){
		if ($href != ""){
			$this->items []= array($name,$href);
		}
		else{
			$this->items []=  $name;
		}
	}
	
	public function create(){
		global $CARDLIST;
		$card = str_replace(["{{GRID}}","{{TYPE}}","{{TITLE}}",], [$this->gridSize,$this->type,$this->title], $CARDLIST);
		$X = [];
		$C = $this->items;
		$P1 = '			<li>{{NAME}}</li>'. "\n";
		$P2 = '<li><a class="rb" href="{{HREF}}" target="_blank">{{NAME}}</a></li>'. "\n";
		foreach ($C as $item){
			if (is_string($item)){
				$X []= (string) str_replace("{{NAME}}",$item, $P1);
			}
			else{
				$X []= "			".(string) str_replace(["{{HREF}}", "{{NAME}}"], [$item[1], $item[0] ], $P2);
			}
		} 
		$card = str_replace("{{ITEMS}}", join("\n",$X), $card);
		return preg_replace('/^\h*\v+/m', '', $card)."\n";
		}
}

class CardGroup{
	private $cards = [];
	private $header = "";
	
	public function create(){
		global $CARDGROUP;
		$sect = (string)str_replace(["{{HEADER}}","{{CARDS}}"],[$this->header,$this->createCards()],$CARDGROUP);
		return preg_replace('/^\h*\v+/m', '', $sect)."\n";
	}
		
	public function setHeader($header){
		$this->header = $header;
	}
	
	public function addCard($card){
		$this->cards []= $card;
	}
	
	function createCards(){
		$result = "";
		foreach($this->cards as $card){
			$result .= $card->create()."\n";
		}
		return $result;
	}
}
?>
