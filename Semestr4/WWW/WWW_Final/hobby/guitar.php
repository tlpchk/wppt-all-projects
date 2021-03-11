<?php
$root = "../";
require_once($root."php/tags.php");
require_once($root."php/card.php");
require_once($root."php/page.php");

$title  = "Gitara";
$root = "../";
$description = "Tutorialy gry na gitarze";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Gra na gitarze";

$Page->SetDescription($description);
$Page->addStyles($styles);
$js = array("localstorage.js");
$Page->addJS($js);

$href = "https://www.youtube.com/channel/UCmj81L0jHz-sGab2_EesV_w";
$imgsrc = $root."img/gergely.jpg";
	
$content ="Peter Gergely is a 20 year old Hungarian guitarist who specializes in acoustic arrangements
of popular music, similar to artists Kelly Valleau and Igor Presnyakov.";

$guitar = new CardContent($gridSize="col-4", $href,$type="c2",$title="Peter Gergely");
$guitar->setDescription($content);
$guitar->setImage($imgsrc);

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo openContainer();
echo $guitar->create();
echo closeContainer();
echo $Page->End();
?>