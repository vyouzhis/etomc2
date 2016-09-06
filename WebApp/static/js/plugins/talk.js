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

function saveTalk() {
	// var icore = getCookie("iCore");

	var msg = $("#comment-text").val();
	var sid = $("#comment-sid").val();
	var code = $("#comment-code").val();

	

	$.ajax({
		url : "/etomc2/italk?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : 'msg=' + msg + "&sid=" + sid + "&code=" + code + "&pid=" + 0,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			 console.log("option:" + option);
		},
		error : function(response) {
			// console.log(response);
		}
	});

	listTalk(0, sid, code);
	$("#comment-text").val("");
}

function listTalk(p, sid, code) {
	$
			.ajax({
				url : "/etomc2/listtalk?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"sid" : sid,
					"code" : code,
					"p" : p
				},
				success : function(result) {
					// console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					// alert(textStatus);
				},
				complete : function(request, textStatus) { // for additional
															// info
					var option = request.responseText;
					// console.log("option:" + option);
					var Json = JSON.parse(option);
					$("#talkPage").html(Json['page']);

					var talkData = Json['data'];
					console.log("talkData:" + talkData);
					var ListHtml = "";

					for (i = 0; i < Json['data'].length; i++) {
						var reply = "<p><a href='blog-single.html#'><i class='fa fa-reply'></i> Reply</a></p>"
						if (Json['data'][i]['reply'] == 1) {
							reply = "";
						}
						var date = new Date(
								parseInt(Json['data'][i]['ctime']) * 1000)
								.toLocaleString().substr(0, 17);

						ListHtml += "<li class='media'>"
								+ "<a class='media-left' href='blog-single.html#'> <span class='avatar anonymous'><i class='fa fa-user'></i></span>"
								+ "</a>" + "<div class='media-body'>"
								+ "	<h4 class='media-heading comment-author'>"
								+ "		<a href='#'>"
								+ Json['data'][i]['nickname'] + "</a>"
								+ "	</h4>"
								+ "	<span class='timestamp text-muted'>" + date
								+ "</span>" + "	<p>" + Json['data'][i]['msg']
								+ "</p>" + reply + "	<hr>" + "</div>" + "</li>";
					}
					if (sid != 0) {
						$("#talkStra").html(ListHtml);
					} else {
						$("#talkCode").html(ListHtml);
					}
				}
			});
}
