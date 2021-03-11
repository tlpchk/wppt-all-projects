<?php
require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");
require_once(__DIR__."/php/menu.php");

$title  = "Semestr IV";
$root = "";
$description = "WPPT, Semestr 4";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Semestr IV";

$Page->SetDescription($description);
$Page->addStyles($styles);
//---------------------------
$aisdHeader = "Algorytmy i Stuktury Danych";
	
$aisd_1 = new CardList("col-6 orange","Czego się nauczyłem");

$aisd_1->addItem("Notacja asymptotycza");
$aisd_1->addItem("Algorytmy sortowania");

$aisd_2 = new CardList("col-6","Materiały");
$aisd_2->addItem("Algorytmy_RU(Kormen)","files/semestr4/Algorytmy i Struktury Danych/Алгоритмы.Построение и анализ Кормен.djvu");
				
$aisd = new CardGroup();
$aisd->setHeader($aisdHeader);
$aisd->addCard($aisd_1);
$aisd->addCard($aisd_2);
//--------------------------

$tsHeader = "Technologie Siecowe";
	
$ts_1 = new CardList("col-6 orange","Czego się nauczyłem");
$ts_1->addItem("Ping, Traceroute, WireShark");
$ts_1->addItem("CSDA/CM, rozpychanie bitów");

$ts_2 = new CardList("col-6 orange","Materiały");
$ts_2->addItem("Działania na macierzach","http://www.matmana6.pl/tablice_matematyczne/studia/macierze_i_wyznaczniki/122-dzialania_na_macierzach");
$ts_2->addItem("Sprawozdanie 1","files/semestr4/TS/TS1.txt");

$ts = new CardGroup();
$ts->setHeader($tsHeader);
$ts->addCard($ts_1);
$ts->addCard($ts_2);

//-----------------------------------------

$wwwHeader = "Nowoczesne technologie WWW";
	
$www_1 = new CardList("col-6 orange","Czego się nauczyłem");
$www_1->addItem("HTML");
$www_1->addItem("CSS");
$www_1->addItem("Java Script");
$www_1->addItem("PHP");
$www_1->addItem("Grid");


$www_2 = new CardList("col-6","Materiały");
$www_2->addItem("HTML(W3Schools","https://www.w3schools.com/html/default.asp");
$www_2->addItem("CSS(W3Schools)","https://www.w3schools.com/css/default.asp");
$www_2->addItem("CSS Responsive(W3Schools)","https://www.w3schools.com/css/css_rwd_intro.asp");
$www_2->addItem("Kolory","http://cs.pwr.edu.pl/cichon/2017_18_b/WWW/kolory.html");

$www = new CardGroup();
$www->setHeader($wwwHeader);
$www->addCard($www_1);
$www->addCard($www_2);

//--------------------------
$grafikaHeader = "Grafika Komputerowa";
	
$grafika_1 = new CardList("col-6 orange","Czego się nauczyłem");

$grafika_1->addItem("JavaScript");
$grafika_1->addItem("SVG");
$grafika_1->addItem("Canvas");


$grafika_2 = new CardList("col-6","Materiały");
$grafika_2->addItem("OpenGL tutorial","http://www.opengl-tutorial.org/beginners-tutorials/tutorial-1-opening-a-window/#introduction");


$grafika = new CardGroup();
$grafika->setHeader($grafikaHeader);
$grafika->addCard($grafika_1);
$grafika->addCard($grafika_2);
//----------------------------------------
$grafyHeader = "Wprowadzenie do Teorji Grafów";
	
$grafy_1 = new CardList("col-6 orange","Czego się nauczyłem");
$grafy_1->addItem("Proszę wpisać sobie plusa na liście aktwności");


$grafy_2 = new CardList("col-6","Materiały");

$grafy = new CardGroup();
$grafy->setHeader($grafyHeader);
$grafy->addCard($grafy_1);
$grafy->addCard($grafy_2);

//--------------------------
$byznesHeader = "Przedsiębiorczość";
	
$byznes_1 = new CardList("col-6 orange","Czego się nauczyłem");
$byznes_1->addItem("Współka partnerska nie dla informatyków");
$byznes_1->addItem("Muszę zmienić hasło");


$byznes_2 = new CardList("col-6","Materiały");
$byznes_2->addItem("Projekt","files/semestr4/Przedsiębiorczość/A._Dereń_J._Skonieczny_PełnyNowy_standard_1 .doc");

$byznes = new CardGroup();
$byznes->setHeader($byznesHeader);
$byznes->addCard($byznes_1);
$byznes->addCard($byznes_2);
//----------------------------------------

$negocjacjeHeader = "Podstawy negocjacji";
	
$negocjacje_1 = new CardList("col-6 orange","Czego się nauczyłem");
$negocjacje_1->addItem("Pan może mieć swoje zdanie, ale pan się myli");


$negocjacje_2 = new CardList("col-6","Materiały");

$negocjacje = new CardGroup();
$negocjacje->setHeader($negocjacjeHeader);
$negocjacje->addCard($negocjacje_1);
$negocjacje->addCard($negocjacje_2);
//----------------------------------------

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo  createMeuForEducation();

echo $aisd->create();
echo $www->create();
echo $ts->create();
echo $grafika->create();
echo $grafy->create();
echo $byznes->create();
echo $negocjacje->create();
echo $Page->End();
?>