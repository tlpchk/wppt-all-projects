<?php
require_once(__DIR__."/php/tags.php");
require_once(__DIR__."/php/card.php");
require_once(__DIR__."/php/page.php");

$title  = "Programming";
$root = "img/";
$description = "Kursy programowania";
$styles = array("reset","style","grid","cards");
$Page = new Page($title,$root);
$subheader = "Programming";

$Page->SetDescription($description);
$Page->addStyles($styles);

$href = "https://www.codecademy.com/catalog/subject/web-development";
$imgsrc = $root."codecademy-logo.jpg";
	
$content ="Codecademy is an online freemium interactive platform that offers free coding classes in 12 different programming languages including Python, Java,
						JavaScript (jQuery, AngularJS, React.js), Ruby, SQL, and Sass, as well as markup languages HTML and CSS.
					";

$codeacademy = new CardContent($gridSize="col-4", $href,$type="orange",$title="CodeAcademy");
$codeacademy->setDescription($content);
$codeacademy->setImage($imgsrc);
//--------------------------------------------
$href = "https://projecteuler.net";
$imgsrc = $root."euler-logo.jpg";
	
$content ="Project Euler is a series of challenging mathematical/computer programming problems that will require more than just mathematical insights to solve.	
					";

$euler = new CardContent($gridSize="col-4", $href,$type="orange",$title="Project Euler");
$euler->setDescription($content);
$euler->setImage($imgsrc);

echo $Page->Begin();
echo $Page->PageHeader($subheader);
echo openContainer();
echo $codeacademy->create();
echo $euler->create();
echo closeContainer();
echo $Page->End();
?>