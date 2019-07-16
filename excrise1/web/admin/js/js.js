function form_submit(){
	document.getElementById("login").submit();
	if(event.KEYCODE==13){
	    var toclick = document.getElementById("keybtn");
	    toclick.click();
    }
}
function form_reset(){
	document.getElementById("login").reset();
}
function reloadcode(){
    var verify=document.getElementById('safecode');
    verify.setAttribute('src','code.php?'+Math.random());
}