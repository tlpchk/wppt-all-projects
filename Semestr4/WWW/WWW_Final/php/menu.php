<?
$MENU=<<<EOT
<nav id="navbar">
	<ul>
{{ITEMS}}
	</ul>
</nav>
EOT;
	function createMenu($items){
		global $MENU;
		$links = [];
		foreach($items as $item){
			$links []= str_replace(["{{VALUE}}","{{HREF}}","{{CLASS}}"],$item,'<li class="{{CLASS}}"><a href="{{HREF}}">{{VALUE}}</a></li>');
		}
		
		return str_replace("{{ITEMS}}", join("\n",$links), $MENU);
	}

	function createMeuForEducation($root){
		return createMenu([
			["Głowna",$root."index.php","home"],
			["Semestr I",$root."semesters/"."semestr1.php",""],
			["Semestr II",$root."semesters/"."semestr2.php",""],
			["Semestr III",$root."semesters/"."semestr3.php",""],
			["Semestr IV",$root."semesters/"."semestr4.php",""],
			["Opinie",$root."opinions.php","opinion"],
			["Dodaj opinie",$root."addOpinion.php","opinion"]]);
	}
?>