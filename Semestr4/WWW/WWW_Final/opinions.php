<?php
	$root = "./";
	require_once($root."php/myDB.php");
	require_once($root."php/tags.php");
	require_once($root."php/page.php");
	require_once($root."php/opinions.php");

	$title  = "Zostaw opinie";
	$description = "Opinie";
	$styles = array("reset","style","grid","form");
	$Page = new Page($title,"");
	$subheader = "Opinie";

	$Page->SetDescription($description);
	$Page->addStyles($styles);

	echo $Page->Begin();
	echo $Page->PageHeader($subheader);
	echo openContainer();
	echo generateOpinionTable();
	echo createLink("addOpinion.php","ownOpinion","Dodaj opinie");
	echo closeContainer();
	echo $Page->End();
?>