<?php

$root = "../";

require_once($root."php/tags.php");
require_once($root."php/card.php");
require_once($root."php/page.php");
require_once($root."php/menu.php");
$root = "../";

$title  = "Semestr III";
$description = "WPPT, Semestr 3";
$styles = array("reset","style","grid","cards");
$js = array("localstorage.js","accordion.js");
$Page = new Page($title,$root);
$subheader = "Semestr III";

$Page->SetDescription($description);
$Page->addStyles($styles);
$Page->addJS($js);
//---------------------------
$mpisHeader = "Metody Probabilistyczne";
	
$mpis_1 = new CardList("col-6","Czego się nauczyłem","c1");

$mpis_1->addItem("Przestrzenie probabilistycznE");
$mpis_1->addItem("Prawdopodobienstwo warunkowe, niezależność zdarzeń.");
$mpis_1->addItem("Zmienne losowe");
$mpis_1->addItem("Rozkład jednostajny i geometryczny");
$mpis_1->addItem("Wartości oczekiwane");
$mpis_1->addItem("Wariacja zm. losowej");

$mpis_2 = new CardList("col-6","Materiały","c2");
$mpis_2->addItem("Zagadnienia,((Powtórka))","files/semestr3/mpis/metody-skrypt.pdf");
$mpis_2->addItem("Rachunek prawdopodobieństwa,((Cichoń))","files/semestr3/mpis/cichon_probabilistyka.pdf");
$mpis_2->addItem("Ściąga ze wzorami,((Cichoń))","files/semestr3/mpis/Probability.pdf");
$mpis_2->addItem('$P(A)=\frac{|A|}{|\Omega|}$');
$mpis_2->addItem('$P(A|B)=\frac{P(A\cap B)}{P(B)}$');

$mpis = new CardGroup();
$mpis->setHeader($mpisHeader);
$mpis->addCard($mpis_1);
$mpis->addCard($mpis_2);
//--------------------------

$tpHeader = "Technologia Programowania";
	
$tp_1 = new CardList("col-6","Czego się nauczyłem","c1");
$tp_1->addItem("UML");
$tp_1->addItem("GoF Patterns");
$tp_1->addItem("Testowanie");
$tp_1->addItem("XML");
$tp_1->addItem("GRASP,SOLID");
$tp_1->addItem("Regex");
$tp_1->addItem("Overload, override");


$tp_2 = new CardList("col-6","Materiały","c2");
$tp_2->addItem("Wykłady","files/semestr3/tp/Wykłady");
$tp_2->addItem("Projekty","files/semestr3/tp/projects");
$tp_2->addItem("Regex","files/semestr3/tp/tp_regex_mpietrek.pdf");
$tp_2->addItem("Ściąga na Kolokwium","files/semestr3/tp/tp_sciaga_mpietrek_v38.pdf");
$tp_2->addItem("Wzorce projektowe, ((Powtórka))","https://habrahabr.ru/post/210288/");

$tp = new CardGroup();
$tp->setHeader($tpHeader);
$tp->addCard($tp_1);
$tp->addCard($tp_2);
//--------------------------
$bdHeader = "Bazy Danych";
	
$bd_1 = new CardList("col-6","Czego się nauczyłem","c1");
$bd_1->addItem("SQL");
$bd_1->addItem("Algebra relacji");
$bd_1->addItem("Normalizacja baz danych");


$bd_2 = new CardList("col-6","Materiały","c2");
$bd_2->addItem("Normalizacja Baz Danych ((mimuw))","http://wazniak.mimuw.edu.pl/index.php?title=Bazy_danych/Wyk%C5%82ad_5");
$bd_2->addItem("Postaci normalne i algebra relacji","files/semestr3/bd/bazy_notatki_v8.pdf");
$bd_2->addItem("Rozwiązania przykładowych kolokwium","files/semestr3/bd/bazy_przykładowe.pdf");
$bd_2->addItem("Rozwiązania list","files/semestr3/bd/bazy_zadania.pdf");
$bd_2->addItem("SQL ((W3Schools))","https://www.w3schools.com/sql/default.asp");

$bd = new CardGroup();
$bd->setHeader($bdHeader);
$bd->addCard($bd_1);
$bd->addCard($bd_2);
//--------------------------
$akisoHeader = "Architektura Komputerów i Systemy Operacyjne";
	
$akiso_1 = new CardList("col-6","Czego się nauczyłem","c1");

$akiso_1->addItem("Reprezentacja danych");
$akiso_1->addItem("Wątki, procesy,potoki, przekierowania");
$akiso_1->addItem("Podstawy organizacji wejscia i wyjścia,selektory");
$akiso_1->addItem("Linux");
$akiso_1->addItem("Zarządzanie pamięcią");
$akiso_1->addItem("Asembler MARIE, 6502, ARM, x86");
$akiso_1->addItem("Client-server");
$akiso_1->addItem("Powłoki(Shell)");
$akiso_1->addItem("Sygnały");

$akiso_2 = new CardList("col-6","Materiały","c2");
$akiso_2->addItem("Wykłady Zawady","https://drive.google.com/drive/folders/1_sarelhE29ZHFY1-0UI7eRMmQTW9zSsK");
$akiso_2->addItem("Tutorial - Write a Shell in C","https://brennan.io/2015/01/16/write-a-shell-in-c/");
$akiso_2->addItem("Projecty","files/semestr3/akiso/Lab");

$akiso = new CardGroup();
$akiso->setHeader($akisoHeader);
$akiso->addCard($akiso_1);
$akiso->addCard($akiso_2);
//--------------------------

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo createMeuForEducation($root);
echo $mpis->create();
echo $tp->create();
echo $akiso->create();
echo $bd->create();

echo $Page->End();
?>
