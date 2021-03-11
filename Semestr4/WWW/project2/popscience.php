<?php
require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");

$title  = "Pop Science";
$root = "img/";
$description = "Pop Science";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Pop Science";

$Page->SetDescription($description);
$Page->addStyles($styles);

//-------------------------------------------------------
$href = "http://zserials.cc/dokumentalnye/mind-field.php";
$imgsrc = $root."Mind_Field_title_card.png";
	
$content ="Autorzy serialu dokumentacyjno -badawczego \"Mind Field\"
						oferują swoim widzom zanurzenie się w niesamowitym spektaklu.
						Ten program ma na celu zrozumienie i zrozumienie niezwykłych 
						i prawie niewiarygodnych zjawisk i cech naszego życia.";

$mf= new CardContent($gridSize="col-4", $href,$type="orange",$title="Mind Field");
$mf->setDescription($content);
$mf->setImage($imgsrc);

//-------------------------------------------------------
$href = "http://hdrezka.ag/series/detective/11586-razrushiteli-legend.html#t:22-s:14-e:1p";
$imgsrc = $root."Mythbusters-logo.jpg";
	
$content ="Adam Savage i Jamie Heinemann to nie tylko najlepsi specjaliści od efektów specjalnych,
						którzy dysponują niezbędnymi umiejętnościami i doświadczeniem w tym zakresie, ale są
						także prawdziwymi ekstremami, gotowymi przejść niemal każdy krok, aby osiągnąć cel.
					";

$mythbusters= new CardContent($gridSize="col-4", $href,$type="orange",$title="Mythbusters");
$mythbusters->setDescription($content);
$mythbusters->setImage($imgsrc);

//-------------------------------------------------------
$href = "https://www.ted.com/talks";
$imgsrc = $root."Ted-logo.jpg";
	
$content ="TED Talks to wpływowe filmy od ekspertów z dziedziny edukacji,
						biznesu, nauki, techniki i kreatywności, z napisami w ponad 100 językach.
						Pomysły są bezpłatne do przesyłania i pobierania.";

$ted= new CardContent($gridSize="col-4", $href,$type="orange",$title="TED Talks");
$ted->setDescription($content);
$ted->setImage($imgsrc);

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo openContainer();
echo $mf->create();
echo $mythbusters->create();
echo $ted->create();
echo closeContainer();
echo $Page->End();
?>