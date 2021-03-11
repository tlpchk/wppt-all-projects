<?php

require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");
require_once(__DIR__."/php/menu.php");

$title  = "Maksym Telepchuk";
$root = "./";
$description = "Strona glówna Maksyma Telepchuka";
$styles = array("myStyle.css");
$Page = new Page($title,$root);
$subheader = "Strona główna";

$Page->SetDescription($description);
$Page->addStyles($styles);
$js = array("localstorage.js");
$Page->addJS($js);

$semestr = new CardList("col-6","Edukacja","c1");
$semestr->addItem("Semestr I",$root."semesters/semestr1.php");
$semestr->addItem("Semestr II",$root."semesters/semestr2.php");
$semestr->addItem("Semestr III",$root."semesters/semestr3.php");
$semestr->addItem("Semestr IV",$root."semesters/semestr4.php");

$hobby =  new CardList("col-6","Hobby","c2");
$hobby->addItem("Gitara",$root."hobby/guitar.php");
$hobby->addItem("Angielski",$root."hobby/english.php");
$hobby->addItem("Pop Science",$root."hobby/popscience.php");
$hobby->addItem("Kursy programowania",$root."hobby/programming.php");

echo $Page->Begin();
echo $Page->PageHeader($subheader);

echo openContainer();
echo aboutMe();
echo openTag("nav","cards container col-8");
echo $semestr->create();
echo $hobby->create();
echo closeNav();
echo closeContainer();
echo $Page->End();
	
?>
	