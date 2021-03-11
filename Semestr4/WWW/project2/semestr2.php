<?php
require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");
require_once(__DIR__."/php/menu.php");

$title  = "Semestr II";
$root = "";
$description = "WPPT, Semestr 2";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Semestr II";

$Page->SetDescription($description);
$Page->addStyles($styles);
//---------------------------
$am2Header = "Analiza Matematyczna II";
	
$am2_1 = new CardList("col-6 orange","Czego się nauczyłem");
$am2_1->addItem("Całki, całki, całeczki!!");
$am2_1->addItem("Podciągi!");
$am2_1->addItem("Ciagłosć funkcji wielu zmiennych");
$am2_1->addItem("Rózniczkowanie");
$am2_1->addItem("Ekstrema funkcji");
$am2_1->addItem("Całkowanie funkcji wielu zmiennych");
$am2_1->addItem("Całki powierzchniowe");

$am2_2 = new CardList("col-6","Materiały");
$am2_2->addItem("AM2 Definicje(Gewert,Skoczylas)","files/Analiza Matematyczna 2/AM2 Definicje.pdf");
$am2_2->addItem("AM2. Powtórka","files/Analiza Matematyczna 2/am2-zagadnienia.pdf");
$am2_2->addItem("AM2 Przyklady.pdf(Gewert,Skoczylas)","files/Analiza Matematyczna 2/AM2 Przyklady.pdf");
				
$am2 = new CardGroup();
$am2->setHeader($am2Header);
$am2->addCard($am2_1);
$am2->addCard($am2_2);
//--------------------------

$algebraHeader = "Algebra Abstrakcyjna i Kodowanie";
	
$algebra_1 = new CardList("col-6 orange","Czego się nauczyłem");
$algebra_1->addItem("Grupa,rząd grupy,rząd elementu");
$algebra_1->addItem("Grupa ilorazowa");
$algebra_1->addItem("Homomorfizm,Izomorfizm");
$algebra_1->addItem("Grupa permutacji ");
$algebra_1->addItem("Chinskie twierdzenie o resztach");
$algebra_1->addItem("RSA");

$algebra_2 = new CardList("col-6","Materiały");
$algebra_2->addItem("Działania na macierzach","http://www.matmana6.pl/tablice_matematyczne/studia/macierze_i_wyznaczniki/122-dzialania_na_macierzach");
$algebra_2->addItem("Układy równa","https://www.matemaks.pl/uklady-rownan.html");
$algebra_2->addItem("Liczby Zespolone","files/semestr1/Algebra z Geometrią Analityczną/liczby_zespolone.pdf");
$algebra_2->addItem("Ciała","files/semestr1/Algebra z Geometrią Analityczną/ciala.pdf");
$algebra_2->addItem("Kolokwia","files/semestr2/Algebra/Algebra_2_2016.pdf" );
$algebra_2->addItem("Przykładowe rozwiązania","files/semestr2/algebra-przykladowe.pdf" );
					
$algebra = new CardGroup();
$algebra->setHeader($algebraHeader);
$algebra->addCard($algebra_1);
$algebra->addCard($algebra_2);

//-----------------------------------------

$mdHeader = "Matematyka Dyskretna";
	
$md_1 = new CardList("col-6 orange","Czego się nauczyłem");
$md_1->addItem("Symbol Newtona");
$md_1->addItem("Permutacje");
$md_1->addItem("Zmienne losowe");
$md_1->addItem("Klasy kombinatoryczne,funkcje tworzące");
$md_1->addItem("Drzewa");
$md_1->addItem("Multizbiory, cykle, rozbicia");
$md_1->addItem("Grafy");
$md_1->addItem("Liczby Stirlinga ");

$md_2 = new CardList("col-6","Materiały");
$md_2->addItem("Przykładowe rozwiązania zanań","files/semestr2/MD/MD_rozwiązania.pdf");
$md_2->addItem("MD dla informatyków(kordecki)","files/semestr2/MD/MD dla informatyków(kordecki).pdf");

$md = new CardGroup();
$md->setHeader($mdHeader);
$md->addCard($md_1);
$md->addCard($md_2);

//--------------------------
$kpHeader = "Kurs Programowania";
	
$kp_1 = new CardList("col-6 orange","Czego się nauczyłem");

$kp_1->addItem("Języki obiektowe");
$kp_1->addItem("Aplety");
$kp_1->addItem("GUI");
$kp_1->addItem("Wątki");
$kp_1->addItem("UML");
$kp_1->addItem("Java, C++");

$kp_2 = new CardList("col-6","Materiały");
$kp_2->addItem("HeadFirst JAVA","files/semestr2/kurs programowania/HeadFirst JAVA.djvu");
$kp_2->addItem("Thinking Java","files/semestr2/kurs programowania/Философия Java.djvu ");
$kp_2->addItem("C++(Kennig)","files/semestr2/kurs programowania/С++. Эффективное программирование Э.Кениг.djvu");
$kp_2->addItem("Projekty","files/semestr2/kurs programowania/projects");

$kp = new CardGroup();
$kp->setHeader($kpHeader);
$kp->addCard($kp_1);
$kp->addCard($kp_2);
//----------------------------------------
$fizykaHeader = "Fizyka";
	
$fizyka_1 = new CardList("col-6 orange","Czego się nauczyłem");
$fizyka_1->addItem("Kinematyka");
$fizyka_1->addItem("Praca i energia kinetyczna");
$fizyka_1->addItem("Zasada zachowania energii mechanicznej ");
$fizyka_1->addItem("Ruch obrotowy");
$fizyka_1->addItem("Fale elektromagnetyczne");
$fizyka_1->addItem("Optyka geometryczna");
$fizyka_1->addItem("Zasada zachowania momentu pędu");

$fizyka_2 = new CardList("col-6","Materiały");
$fizyka_2->addItem("Podstawy Fizyki 2","files/semestr2/fizyka/TOM 2.pdf");
$fizyka_2->addItem("Podstawy Fizyki 3","files/semestr2/fizyka/TOM 3.pdf");
$fizyka_2->addItem("Recensja.Prawo Gaussa","files/semestr2/fizyka/Recensja.Prawo Gaussa.docx");
$fizyka_2->addItem("Wzorki","files/semestr2/fizyka/wzory_1.pdf");
$fizyka_2->addItem("Jeszcze więcej wzorków","files/semestr2/fizyka/wzory_2.pdf");
$fizyka_2->addItem("Wzorków nigdy nie jest dużo","files/semestr2/fizyka/wzory_3.pdf");

$fizyka = new CardGroup();
$fizyka->setHeader($fizykaHeader);
$fizyka->addCard($fizyka_1);
$fizyka->addCard($fizyka_2);

//--------------------------
$problemyHeader = "Problemy prawne Informatyki";
	
$problemy_1 = new CardList("col-6 orange","Czego się nauczyłem");
$problemy_1->addItem("Nawyk ciągłego szukania brudnych sztuczek w umowach");


$problemy_2 = new CardList("col-6","Materiały");
$problemy_2->addItem("Umowa audytu zewnętrznego","files/semestr2/problemy prawne/Kolokwium 4.Maksym Telepchuk,236723.pdf");
$problemy_2->addItem("Umowa licencyjna","files/semestr2/problemy prawne/Соглашение.docx");
$problemy_2->addItem("Kodeks karny","files/semestr2/problemy prawne/D19970553Lj.pdf");

$problemy = new CardGroup();
$problemy->setHeader($problemyHeader);
$problemy->addCard($problemy_1);
$problemy->addCard($problemy_2);
//----------------------------------------

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo  createMeuForEducation();

echo $am2->create();
echo $md->create();
echo $algebra->create();
echo $kp->create();
echo $fizyka->create();
echo $problemy->create();
echo $Page->End();
?>
