
/*
 *	############################################################################
 *	
 *   arbinfo
 *   
 *	############################################################################
 */



function selling(c) {
	var dataList = 'act=0&code=' + c;
	$.ajax({
		url : DomainUrl+"/arbinfo?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;

			var msg = "平仓成功!";

			BellNotifi(msg);
		},
		error : function(response) {
			// //console.log(response);
		}
	});
}

function showStockDesc(c, n) {
	StockDescCode = c;
	$("#CodeName").html("<i class='fa fa-star'></i> "+c+" "+n);
	showStrategy();
}

function showStrategy() {
	var dataList = 'act=1&code=' + StockDescCode;
	$.ajax({
		url : DomainUrl+"/arbinfo?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			if (option.length < 10) {
				var msg = "数据出错!";

				BellNotifi(msg);
			} else {
				var StockDesc = JSON.parse(option);

				var html = "";

				for (var i = 0; i < StockDesc.length; i++) {
					html += "<tr>" + 
					      "<th >" + (i+1)+ "</th>" +
							"<th scope='row'>" + StockDesc[i]['title']+ "</th>" + 
							"<th>" + StockDesc[i]['name']+ "</th>	" + 
							"</tr>";
				}
				$("#litbody_0").html(html);
				liCurrent(0);
			}
		},
		error : function(response) {
			// //console.log(response);
		}
	});
}

function showSelling(){
	liCurrent(2);
}

function liCurrent(i) {
	for (var n = 0; n < 8; n++) {
		$("#li_" + n).attr("class", "list-group-item");
		$("#lidiv_" + n).attr("class", "col-md-9 hide");
	}
	$("#lidiv_" + i).attr("class", "col-md-9");
	$("#li_" + i).attr("class", "list-group-item current");
}
