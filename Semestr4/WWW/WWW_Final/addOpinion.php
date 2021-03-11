<?php
	$root = "./";
	require_once($root."php/tags.php");
	require_once($root."php/page.php");
	require_once($root."php/myDB.php");
	require_once($root."php/opinions.php");

	global $OPINIONFORM;

	$root = "./";
	$title  = "Twoja opinia";
	$description = "Opinie";
	$styles = array("reset","style","grid","form");
	$Page = new Page($title,$root);
	$subheader = "Twoja opinia";

	$Page->SetDescription($description);
	$Page->addStyles($styles);
	$js = array("localstorage.js");
	$Page->addJS($js);

	echo $Page->Begin();
	echo $Page->PageHeader($subheader);
	echo openContainer();
	echo generateOpinionForm();
	echo closeContainer();
	echo $Page->End();
?>
