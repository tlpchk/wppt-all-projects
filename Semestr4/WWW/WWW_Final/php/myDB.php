<?php
/*.
  require_module 'standard';
  require_module 'mysqli';
.*/

/**
*  konstruktor połączenia z bazą danych
*  @return void
*/

class portaldb extends mysqli {

  public function __construct() {
    parent::__construct('localhost', 'root', '1234', 'project3');
  
    if (mysqli_connect_errno() != 0) {
      die('Connect Error (' . mysqli_connect_errno() . ') ' . mysqli_connect_error());
    }
    parent::query("SET NAMES utf8");
    parent::query("SET CHARACTER SET utf8");
    parent::query("SET collation_connection = utf16_polish_ci");
  }
}

$sessionKEY = "key";
$sessionVAL = "3.141";

$shortcuts = [
	"S1" => "Semestr I",
	"S2" => "Semestr II",
	"S3" => "Semestr III",
	"S4" => "Semestr IV",
	"Semestr I" => ["AM1" => "Analiza Matematyczna 1",
					"AGA" => "Algebra z Geometrią Analityczną",
					"LSF" => "Logika i Struktury Formalne",
					"WIP" => "Wstęp do Informatyki i Programowania"],
	"Semestr II" => ["AM2" => "Analiza Matematyczna 2",
					"MD" => "Logika i Struktury Formalne",
					"AAK" => "Algebra Abstrakcyjna i Kodowanie",
					"KP" => "Kurs Programowania",
					"PPI" => "Problemy prawne Informatyki",
					"FZK" => "Fizyka"],
	"Semestr III" => ["MP" => "Metody Probabilistyczne",
					"TP" => "Technologia Programowania",
					"BD" => "Bazy Danych",
					"AKSO" => "Architektura Komputerów i Systemy Operacyjne"],
	"Semestr IV" => ["ASD" => "Algorytmy i Stuktury Danych",
					"TS" => "Technologie Siecowe",
					"TS" => "Technologie Siecowe",
					"WWW" => "Nowoczesne technologie WWW",
					"GK" => "Grafika Komputerowa",
					"WTG" => "Wprowadzenie do Teorji Grafów",
					"PSB" => "Przedsiębiorczość",
					"PN" => "Podstawy negocjacji"]
];

function Kody2Napisy($kod) {
  global $skroty;
  return (string) isset($skroty[$kod])?$skroty[$kod]:$kod;
}


function ZalozTabeleWpisy() {
  $s =<<<EOT
CREATE TABLE `wpisy` (
  `ID` bigint(20) UNSIGNED NOT NULL,
  `NICK` varchar(15) COLLATE utf16_polish_ci NOT NULL DEFAULT 'anonim',
  `Date` datetime NOT NULL,
  `Subject` varchar(10) COLLATE utf16_polish_ci DEFAULT NULL,
  `Info` text COLLATE utf16_polish_ci,
  `Display` tinyint(1) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_polish_ci;

ALTER TABLE `wpisy`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE  KEY `ID` (`ID`);

ALTER TABLE `wpisy`
  MODIFY `ID` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
EOT;
  return $s;
}

function DodajWpis() {
  $s = <<<EOT
INSERT INTO `wpisy` (`NICK`, `Date`, `Subject`, `Info`, `Display`) VALUES
('JCI', '2018-05-21 16:19:00', 'ANA-01', 'To jest mój pierwszy wpis dotyczący Analizy Matematycznej I', 1),
('PZI', '2018-05-20 16:24:00', 'ALG-01', 'To jest komentarz Pawła na temat Algebry I', 1);
EOT;
  return $s;
}

?>
