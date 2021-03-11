<?php
require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");

$title  = "Angielski";
$root = "img/";
$description = "Materiały do Angielskiego";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "English";

$Page->SetDescription($description);
$Page->addStyles($styles);

$href = "http://lelang.ru/english/druzya-1-sezon-1-seriya/";
$imgsrc = $root."Friends-logo.jpg";
	
$content ="Wszystkie sezony \"Friends\" w oryginale z podwójnymi napisami.";

$englisg = new CardContent($gridSize="col-4", $href,$type="orange",$title="Friends");
$englisg->setDescription($content);
$englisg->setImage($imgsrc);

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo openContainer();
echo $englisg->create();
echo closeContainer();
echo $Page->End();
?>