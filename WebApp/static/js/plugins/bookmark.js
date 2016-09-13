/*
 *	############################################################################
 *	
 *   bookmark
 *   
 *	############################################################################
 */

function ListBookMarksCode(act, cid) {
	
	$.ajax({
				url : "/etomc2/bookmark?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"act" : act,
					"cid":cid
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


function addBookMark(i, cid) {
	
	var code = $("#code_num").val();
	if(StockCode.length != 0){
		code = StockCode;
	}
		
	var dataList = 'act=' + i+"&sid=" + straListData[nowTId]['id'] + "&code=" + code +"&name="+StockCodeName+"&cid="+cid ;
		
	$.ajax({
		url : "/etomc2/bookmark?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
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
}