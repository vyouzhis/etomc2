/*
 *	############################################################################
 *	
 *   bookmark
 *   
 *	############################################################################
 */




function bookmarks(i, c, a, t) {
	var sid = parseInt(i);
	var code = c;
	
	if(sid ==0 || code.length ==0){
		console.log("error ");
		return;
	}
	
	$.ajax({
				url : "/etomc2/listtalk?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"sid" : sid,
					"code" : code,
					"action": a,
					"cid": t
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
					
				}
			});
}