<?
$MENU=<<<EOT
<nav id="navbar">
	<ul>
		<li class="home"><a href="index.php">Głowna</a></li>
		{{ITEMS}}
	</ul>
</nav>
EOT;
	function createMenu($items){
		global $MENU;
		$links = [];
		foreach($items as $item){
			$links []= "	".'<li><a href="'.$item[1].'">'.$item[0].'</a></li>';
		}
		
		return str_replace("{{ITEMS}}", join("\n",$links), $MENU);
	}

	function createMeuForEducation(){
		return createMenu([["Semestr I","semestr1.php"],["Semestr II","semestr2.php"],["Semestr III","semestr3.php"],["Semestr IV","semestr4.php"]]);
	}
?>