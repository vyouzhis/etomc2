
/*
 *	############################################################################
 *	
 *   strategyjs
 *   
 *	############################################################################
 */

function istrSelect(i) {
	for (n = 0; n < 5; n++) {
		$("#id_" + n).attr("class", "");
	}

	$("#id_" + i).attr("class", "text-primary leadtab");
	
	for (n = 0; n < 3; n++) {
		$("#bmslist_" + n).attr("class", "fa fa-close");
	}
	
	selectStraId = straListData[i]['id'];
	var name = straListData[i]['title'];
	nowTId = i;
	$("#strategy_name").html(name);

	$("#talkNameStra").html("<i class='fa fa-flask'></i>" + name);

	$("#comment-sid").val(straListData[i]['id']);
	////console.log("sid:" + straListData[i]['id']);

	// 获取策略的讨论内容
	listTalk(0, straListData[i]['id'], "");

	talkSelect(1, straListData[nowTId]['id']);
}


function istrSelect2(name, sid, i) {
	for (n = 0; n < 5; n++) {
		$("#select_" + n).attr("class", "text-primary");
	}
	
	for (n = 0; n < 3; n++) {
		$("#bm_" + n).attr("class", "");
	}
	$("#bm_" + i).attr("class", "text-primary leadtab");
	
	selectStraId = sid;
	
	$("#strategy_name").html(name);

	$("#talkNameStra").html("<i class='fa fa-flask'></i>" + name);

	$("#comment-sid").val(sid);
	////console.log("sid:" + straListData[i]['id']);

	// 获取策略的讨论内容
	listTalk(0, sid, "");

	talkSelect(1, sid);
}

function getPage(p) {
	
	$.ajax({
				url : DomainUrl+"/strapage?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',

				data : {
					"cid" : StockCid,
					"p" : p
				},
				success : function(result) {
					////console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					//alert(textStatus);
				},
				complete : function(request, textStatus) { //for additional info
					var option = request.responseText;
					////console.log("option:" + option);
					var Json = JSON.parse(option);
					$("#straPage").html(Json['page']);
					////console.log("option:" + Json['data']);
																						
					var ListStrHtml = "";
					for (i = 0; i < Json['data'].length; i++) {
						var fa = "fa-close";
																				
						if(selectStraId == Json['data'][i]['id']){
							fa = "fa-check";
						}
													
						var inte = Json['data'][i]['integral'];
						var insval = "";
						if (inte == 0) {
							insval = '免费';
						} else {
							insval = inte + ' 积分';
						}
						
						var cidVal = Json['data'][i]['cid'];
						var cidName
						if (cidVal == 1) {
							cidName = '选股';
						}else if (cidVal == 2){
							cidName = "交易";
						}
						 else {
							 cidName = '风控';
						}
						
						
						ListStrHtml += "<tr id=\"id_"+i+"\" onclick='istrSelect("
								+ i
								+ ")'>"
								+ "<th scope='row'>"
								+ (i + 1)
								+ "</th>"
								+ "<td>"
								+ Json['data'][i]['title']
								+ "</td>"
								+ "<td>"
								+ Json['data'][i]['follow']
								+ "</td>"
							   + "<td>"
								+ cidName
								+ "</td>"
								+ "<td>"
								+ insval
								+ "</td>"
								+ "<td>"
								+ Json['data'][i]['nickname']
								+ "</td>"
								+ "</tr>";
					}

					$("#straList").html(ListStrHtml);
					////console.log("talkListInit:" + talkListInit);
					straListData = Json['data'];
					var name = straListData[0]['title'];
					
					if (talkListInit == 0) {
						listTalk(0, straListData[0]['id'], "");
						talkListInit = 1;
						istrSelect(0);
						$("#talkNameStra").html(
								"<i class='fa fa-flask'></i>" + name);
					}
					
				}
			});
}
