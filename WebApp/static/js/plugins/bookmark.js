/*
 *	############################################################################
 *	
 *   bookmark
 *   
 *	############################################################################
 */

// act 1 code 2 stra
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
					if(option.length < 5) return;
					console.log("option:" + option);
					var Json = JSON.parse(option);
					var html="";
					for(var i=0; i<Json.length; i++){
						html += "<tr>"+
									"<th scope='row'>"+(i+1)+"</th>"+
									"<td>"+"<a href=''><code>"+
												"<i class='fa fa-star'></i>"+Json[i]['code']+" "+Json[i]['name']+
											"</code>"+
									"</a></td>"+
									"<td>dd</td>"+
									"<td>dd</td>"+
									"<td><button title='Success' class='btn btn-outline btn-success' type='button'" +
											" onclick='ListBookMarksStra(\""+Json[i]['code']+"\",\""+Json[i]['name']+"\", "+cid+", "+i+")'>"+
											"<i class='fa fa-close'  id='bmlist_"+i+"' id='bmlist_"+i+"'></i><span class='sr-only'>Success</span>"+
										"</button></td>"+
								"</tr>";
					}
										
					$("#bookMarkCodeList").html(html);
					ListBookMarksStra(Json[0]['code'], Json[0]['name'], cid, 0);
				}
			});
}

function ListBookMarksStra(code, name, cid, i){		
	
	for (n = 0; n < 3; n++) {
		$("#bmlist_" + n).attr("class", "fa fa-close");
	}

	$("#bmlist_" + i).attr("class", "fa fa-check");
	
	$("#code_num").val(code);
	
	$("#CodeAndName").html(code+" "+name);
	
	addCode(code, name);
		
	$.ajax({
		url : "/etomc2/bookmark?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',
		data : {
			"act" : 2,
			"cid": cid,
			"code": code
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
			//console.log("option:" + option);
			var Json = JSON.parse(option);
			var htmlList="";
			for(var i=0; i<Json.length; i++){
				 htmlList += "<tr>"+
								"<th scope='row'></th>"+
								"<td>"+Json[i]['title']+"</td>"+
								"<td>10%</td>"+
								"<td>5.6</td>"+
								"<td><button title='Success' class='btn btn-outline btn-success' type='button' onclick='istrSelect2(\""+Json[i]['title']+"\", \""+Json[i]['id']+"\", "+i+")'>"+
								"<i class='fa fa-close' id='bmslist_"+i+"' id='bmslist_"+i+"'></i><span class='sr-only'>Success</span>"+
								"</button></td>"+
							"</tr>";
			}
			$("#bmlist_"+i).attr("class", "btn btn-outline btn-success");
			$("#nowCodeStraList").html(htmlList);						
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