
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
				url : DomainUrl+"/bookmark?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"act" : act,
					"cid":cid
				},
				success : function(result) {
					// //console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					// alert(textStatus);
				},
				complete : function(request, textStatus) { // for additional
															// info
					var option = request.responseText;
					if(option.length < 5) return;
					////console.log("option:" + option);
					var Json = JSON.parse(option);
					var html="";
					for(var i=0; i<Json.length; i++){
						var price = Json[i]['price'];
						var nprice = Json[i]['nowprice'];
						var profit = (nprice - price)/price * 100;
						profit = Math.round(profit*Math.pow(10, 2))/Math.pow(10, 2);  
						html += "<tr id='bmlist_"+i+"' name='bmlist_"+i+"' onclick='ListBookMarksStra(\""+Json[i]['code']+"\",\""+Json[i]['name']+"\", "+cid+", "+i+")'>"+
									"<th scope='row'>"+(i+1)+"</th>"+
									"<td>"+"<a href=''><code>"+
												"<i class='fa fa-star'></i>"+Json[i]['code']+" "+Json[i]['name']+
											"</code>"+
									"</a></td>"+
									"<td>"+(price*1000)+"</td>"+
									"<td>"+price+"</td>"+
									"<td>"+nprice+"</td>"+
									"<td>"+profit+"%</td>"+
									
								"</tr>";
					}
										
					$("#bookMarkCodeList").html(html);
					ListBookMarksStra(Json[0]['code'], Json[0]['name'], cid, 0);
				}
			});
}

function ListBookMarksStra(code, name, cid, i){		
	
	for (n = 0; n < 3; n++) {
		$("#bmlist_" + n).attr("class", "");
	}

	$("#bmlist_" + i).attr("class", "text-primary leadtab");
	
	$("#code_num").val(code);
	
	$("#CodeAndName").html(code+" "+name);
	
	addCode(code, name);
		
	$.ajax({
		url : DomainUrl+"/bookmark?jsoncallback=?",
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
				 htmlList += "<tr id='bm_"+i+"' onclick='istrSelect2(\""+Json[i]['title']+"\", \""+Json[i]['id']+"\", "+i+")'>"+
								"<th scope='row'>"+(i+1)+"</th>"+
								"<td>"+Json[i]['title']+"</td>"+
								"<td>10%</td>"+
								"<td>5.6</td>"+
								
							"</tr>";
			}
//			$("#bmlist_"+i).attr("class", "fa fa-check");
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
		url : DomainUrl+"/bookmark?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			////console.log("option:" + option);
			var i = parseInt(option);
			var msg = "添加成功!";
			if(i==-1){
				msg = "参数有问题";
			}else if(i==-2){
				msg = "策略已达组合最大值";
			}else if(i==-3){
				msg = "策略已存在，不必再次添加";
			}else if(i==-4){
				msg = "股票数量已达组合最大值";
			}
									
			BellNotifi(msg);
			
			ListBookMarksCode(1, StockCid);
		},
		error : function(response) {
			// //console.log(response);
		}
	});
}
