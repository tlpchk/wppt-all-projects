<?php

require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");
require_once(__DIR__."/php/menu.php");

$title  = "Semestr I";
$root = "";
$description = "WPPT, Semestr 1";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Semestr I";

$Page->SetDescription($description);
$Page->addStyles($styles);
//---------------------------
$am1Header = "Analiza Matematyczna 1";
	
$am1_1 = new CardList("col-6 orange","Czego się nauczyłem");
$am1_1->addItem("Granica ciągu,granica funkcji");
$am1_1->addItem("Ciągłość funkcji");
$am1_1->addItem("Pochodne i całki jednej zmiennej");
$am1_1->addItem("Funkcje jednej zmiennej");

$am1_2 = new CardList("col-6","Materiały");
$am1_2->addItem("Ściąga ze wzorami","files/semestr1/Analiza Matematyczna/Analiza1.pdf");
$am1_2->addItem("AM w zadaniach, Tom1, T. Krysicki","files/semestr1/Analiza Matematyczna/AM w zadaniach, Tom1, T. Krysicki.pdf");
$am1_2->addItem("Darmowy eTrapez","files/semestr1/Analiza Matematyczna/Analiza1.pdf");
$am1_2->addItem("AM Kuratowski","files/semestr1/Analiza Matematyczna/AM Kuratowski.pdf");

$am1 = new CardGroup();
$am1->setHeader($am1Header);
$am1->addCard($am1_1);
$am1->addCard($am1_2);
//--------------------------

$wipHeader = "Wstęp do Informatyki i Programowania";
	
$wip_1 = new CardList("col-6 orange","Czego się nauczyłem");
$wip_1->addItem("Pojęcie algorytmu");
$wip_1->addItem("Rekurencja,programowanie dynamiczn");
$wip_1->addItem("Dziel i zwycięża");
$wip_1->addItem("Stos,Drzewo Binarne");
$wip_1->addItem("Język programowania C");

$wip_2 = new CardList("col-6","Materiały");
$wip_2->addItem("Język ANSI C, B.Kiernigan, D. Ritchie","files/semestr1/Wstęp do Informatyki i Programowania/WIP Język ANSI C.pdf");
$wip_2->addItem("Wstęp do Informatyki(Cichoń,Przemko)","files/semestr1/Wstęp do Informatyki i Programowania/cichon_wstep_do_informatyki.pdf");
$wip_2->addItem("Projekty","files/semestr1/Wstęp do Informatyki i Programowania/Projects");

$wip = new CardGroup();
$wip->setHeader($wipHeader);
$wip->addCard($wip_1);
$wip->addCard($wip_2);

//--------------------------

$algebraHeader = "Algebra z Geometrią Analityczną";
	
$algebra_1 = new CardList("col-6 orange","Czego się nauczyłem");
$algebra_1->addItem("Grupy, pierscienie i ciała");
$algebra_1->addItem("Wielomiany");
$algebra_1->addItem("Macierze");
$algebra_1->addItem("Układy równan liniowych");
$algebra_1->addItem("Przekształcenia liniowe");

$algebra_2 = new CardList("col-6","Materiały");
$algebra_2->addItem("Działania na macierzach","http://www.matmana6.pl/tablice_matematyczne/studia/macierze_i_wyznaczniki/122-dzialania_na_macierzach");
$algebra_2->addItem("Układy równa","https://www.matemaks.pl/uklady-rownan.html");
$algebra_2->addItem("Liczby Zespolone","files/semestr1/Algebra z Geometrią Analityczną/liczby_zespolone.pdf");
$algebra_2->addItem("Ciała","files/semestr1/Algebra z Geometrią Analityczną/ciala.pdf");

$algebra = new CardGroup();
$algebra->setHeader($algebraHeader);
$algebra->addCard($algebra_1);
$algebra->addCard($algebra_2);

//-----------------------------------------
$logikaHeader = "Logika i Struktury Formalne";
	
$logika_1 = new CardList("col-6 orange","Czego się nauczyłem");
$logika_1->addItem("Zbiory");
$logika_1->addItem("Kwantyfikatory");
$logika_1->addItem("Relacje");
$logika_1->addItem("Indukcja matematyczna");
$logika_1->addItem("Równolicznosć");
$logika_1->addItem("Dobre porządki ");
$logika_1->addItem("Grafy");

$logika_2 = new CardList("col-6","Materiały");
$logika_2->addItem("Logika(Cichoń)","files/semestr1/Logika i struktury formalne/LG Wstep do Matematyki(Cicoń).pdf");
$logika_2->addItem("Notatki. Teoria Modeli","files/semestr1/Logika i struktury formalne/teoria modeli.pdf");
$logika_2->addItem("Egzamin","files/semestr1/Logika i struktury formalne/Egzamin.jpg");

$logika = new CardGroup();
$logika->setHeader($logikaHeader);
$logika->addCard($logika_1);
$logika->addCard($logika_2);
//----------------------------------------

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo  createMeuForEducation();

echo $am1->create();
echo $wip->create();
echo $algebra->create();
echo $logika->create();
echo $Page->End();
?>
