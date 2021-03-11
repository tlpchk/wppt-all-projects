<?php

require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");
require_once(__DIR__."/php/menu.php");

$title  = "Maksym Telepchuk";
$root = "";
$description = "Strona glówna Maksyma Telepchuka";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Strona główna";

$Page->SetDescription($description);
$Page->addStyles($styles);


$semestr = new CardList("col-6","Edukacja");
$semestr->addItem("Semestr I","semestr1.php");
$semestr->addItem("Semestr II","semestr2.php");
$semestr->addItem("Semestr III","semestr3.php");
$semestr->addItem("Semestr IV","semestr4.php");

$hobby =  new CardList("col-6 orange","Hobby","orange");
$hobby->addItem("Gitara","guitar.php");
$hobby->addItem("Angielski","english.php");
$hobby->addItem("Pop Science","popscience.php");
$hobby->addItem("Kursy programowania","programming.php");

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
	
