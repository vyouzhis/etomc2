/*
 *	############################################################################
 *	
 *   talk
 *   
 *	############################################################################
 */

var symbols = " !\"#$%&'()*+,-./0123456789:;<=>?@";
var loAZ = "abcdefghijklmnopqrstuvwxyz";
symbols += loAZ.toUpperCase();
symbols += "[\\]^_`";
symbols += loAZ;
symbols += "{|}~";

function toAscii(valueStr) {

	valueStr = valueStr.toLowerCase();
	var hex = "0123456789abcdef";
	var text = "";
	var i = 0;

	for (i = 0; i < valueStr.length; i = i + 2) {
		var char1 = valueStr.charAt(i);
		if (char1 == ':') {
			i++;
			char1 = valueStr.charAt(i);
		}
		var char2 = valueStr.charAt(i + 1);
		var num1 = hex.indexOf(char1);
		var num2 = hex.indexOf(char2);
		var value = num1 << 4;
		value = value | num2;

		var valueInt = parseInt(value);
		var symbolIndex = valueInt - 32;
		var ch = '?';
		if (symbolIndex >= 0 && value <= 126) {
			ch = symbols.charAt(symbolIndex)
		}
		text += ch;
	}

	// document.form1.ascii.value = text;
	return text;
}

function saveTalk(i) {
	// var icore = getCookie("iCore");

	var msg = $("#comment-text").val();
	console.log("msg:" + msg);
	$.post("/etomc2/italk", {
		"msg" : msg,
		"sid" : i,
		"pid" : 0
	}, function(data) {

		console.log("data:"+data); // 2pm
	}, "json");

}
