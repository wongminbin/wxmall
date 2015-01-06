var name = "";
var ps = "";
function signIn(){
	
	if(!checkAdmin())
		return false;
	
	rsaAdmin();
	
	return true;
}

function registeIn(){
	if(!checkAdmin())
		return false;
	
	rsaAdmin();
	
	return true;
}

function checkAdmin(){
	name = $("#login_name").val();
	ps = $("#login_password").val();
	if(name == "" || ps == "")
		return false;
	return true;
}

function rsaAdmin(){
	name = encodeURIComponent(name);
	ps = encodeURIComponent(ps);
	
	var key = RSAUtils.getKeyPair(exponent, "", modulus);
	
	name = RSAUtils.encryptedString(key, name);
	ps = RSAUtils.encryptedString(key, ps);
	
	$("#login_name").val(name);
	$("#login_password").val(ps);
}