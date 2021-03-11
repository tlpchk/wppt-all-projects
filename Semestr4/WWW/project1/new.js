function myFunction(){
	var headers = document.getElementsByTagName("header");
	var i;
	for(i=0; i < headers.length; i++){
		headers[i].style.display = "None";
	}	
}

window.onload = myFunction();

