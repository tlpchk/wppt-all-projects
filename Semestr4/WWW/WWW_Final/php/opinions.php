<?php
session_start();

require_once("captcha.php");
require_once("myDB.php");
$root="../";

$OPINIONFORM=<<<EOT
<form id="opinion" method = "post" action = "add2DB.php">
	<div>
		<label>NICK: </label>
		<input name="NICK" maxlength="15" value="{{NICK}}" autofocus required><br/>
	</div>
	<div>
		<label>Temat: </label>
		{{SELECT}}
	</div>
	<div>
		<label>Opinia: </label>
		<textarea name="CONTENT" maxlength="300" required>{{CONTENT}}</textarea>
	</div>
	<div id="captcha">
		<p>Oblicz wyznacznik macierzy</p>
		<p class="matrix">
		$
			{{WZOR}}
		$
		</p><br/>
		<input type="number" name="CAPTCHA" maxlength="5" required><br/>
		<p class="info">{{INFO}}</p>
	</div>
	<input type="submit" value="Wyślij"/>
</form>
EOT;

function generateOpinionForm(){
	global $OPINIONFORM;
	
	$myCaptcha = generateCaptcha(-10,10);
	$_SESSION["captcha"] = $myCaptcha["key"];
	$info = "";
	if (isset($_COOKIE["error_captcha"]) && $_COOKIE["error_captcha"] == "1") {
    	$info = "Naprawdę studjujesz na pepecie?";
  	}
	$form = str_replace(["{{SELECT}}","{{WZOR}}","{{INFO}}"],[generateSelect(),$myCaptcha["captcha"],$info],$OPINIONFORM);
	
	$nick = "";
	$content = "";
	$subject = "";
	if(isset($_COOKIE["nick"])) {
		$nick = $_COOKIE["nick"];
	}
   	if(isset($_COOKIE['subject'])){
		$subject = $_COOKIE['subject'];
	}
	if(isset($_COOKIE['content'])){
		$content = $_COOKIE['content'];
	}
	return str_replace(["{{NICK}}","{{SUBJ}}","{{CONTENT}}"],[$nick,$subject,$content],$form);
}


function generateOpinionTable(){
	$OPINIONTABLE=<<<END
<div class="of">
<table>
	<thead>
		<tr>
			<th>NICK</th>
			<th>Data</th>
			<th>Temat</th>
			<th>Treść</th>
		</tr>
	</thead>
	</tbody>
	{{OPINIONS}}
	</tbody>
</table>
</div>
END;
	
	$TR = "<tr><td>{{NICK}}</td><td>{{DATE}}</td><td>{{SUBJ}}</td><td><div class=\"content\">{{CONTENT}}</div></td></tr>\n	";
	
	$opinions ="";
	$portaldb = new portaldb();
	$query    = "select * from opinie where Display = 1 order by Date DESC LIMIT 30;";
	$data   = $portaldb->query($query);

	while (($row = $data->fetch_assoc()) !== null) {
		$opinion= str_replace(
			["{{NICK}}",  "{{DATE}}",       "{{SUBJ}}",    "{{CONTENT}}"],
		[$row['NICK'],$row['Date'],Kody2Napisy($row['Subject']), $row['Content']], 
		$TR);
		$opinions .= $opinion;
	}
	$data->close();
	$portaldb->close();
	return str_replace('{{OPINIONS}}',$opinions,$OPINIONTABLE);
}


function generateSelect(){
	$result = '<select name="SUBJ" value="{{SUBJ}}">'."\n";
	$result .= '	'.geterateOptgroup('S1')."\n";
	$result .= '	'.geterateOptgroup('S2')."\n";
	$result .= '	'.geterateOptgroup('S3')."\n";
	$result .= '	'.geterateOptgroup('S4')."\n";
	$result .= '</select><br>'; 
	return $result;
}
	
function geterateOptgroup($label){
	global $shortcuts;
	$OPTGROUP=<<<END
	<optgroup label="{{LABEL}}">
		{{OPTIONS}}</optgroup>
		
END;
	$OPTION = '<option value="{{VALUE}}">{{NAME}}</option>';
	$options = "";
	$shortcutslocal = $shortcuts[$shortcuts[$label]];
	foreach($shortcutslocal as $key=>$value){
		$options .= str_replace(['{{VALUE}}','{{NAME}}'],[$label."-".$key,$value],$OPTION)."\n		";
	}
	return str_replace(['{{LABEL}}','{{OPTIONS}}'],[$shortcuts[$label],$options],$OPTGROUP);
}
?>