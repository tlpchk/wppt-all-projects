var myimg;

function store(){
    var imgCanvas = document.createElement("canvas"),
        imgContext = imgCanvas.getContext("2d");

    imgCanvas.width = myimg.width;
    imgCanvas.height = myimg.height;

    imgContext.drawImage(myimg, 0, 0, myimg.width, myimg.height);

    var imgAsDataURL = imgCanvas.toDataURL("image/png");
	
	try {
		localStorage.setItem("imgpwr", imgAsDataURL);
	}
	catch (e) {
		console.log("Storage failed: " + e);
	}
	console.log('store image to localStorage');
}; 

function load(){
	myimg.setAttribute("src", localStorage.getItem("imgpwr"));
	console.log('load image from localStorage');
}

function cleanLocalStorage(){
	localStorage.removeItem("imgpwr");
	console.log('removed image form localStorage');
}
window.addEventListener("load",function(){
	myimg = document.getElementById("imgpwr");
	myimg.addEventListener('click',cleanLocalStorage);
	if(localStorage.getItem("imgpwr") == null){
		store();
	}else{
		load();
	}
},false);
