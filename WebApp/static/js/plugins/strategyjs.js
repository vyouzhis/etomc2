/*
 *	############################################################################
 *	
 *   strategyjs
 *   
 *	############################################################################
 */

function istrSelect(i) {
	for (n = 0; n < 5; n++) {
		$("#select_" + n).attr("class", "fa fa-close");
	}

	$("#select_" + i).attr("class", "fa fa-check");
	
	for (n = 0; n < 3; n++) {
		$("#bmslist_" + n).attr("class", "fa fa-close");
	}

	selectStraId = straListData[i]['id'];
	var name = straListData[i]['title'];
	nowTId = i;
	$("#strategy_name").html(name);

	$("#talkNameStra").html("<i class='fa fa-flask'></i>" + name);

	$("#comment-sid").val(straListData[i]['id']);
	//console.log("sid:" + straListData[i]['id']);

	// 获取策略的讨论内容
	listTalk(0, straListData[i]['id'], "");

	talkSelect(1, straListData[nowTId]['id']);
}


function istrSelect2(name, sid, i) {
	for (n = 0; n < 5; n++) {
		$("#select_" + n).attr("class", "fa fa-close");
	}
	
	for (n = 0; n < 3; n++) {
		$("#bmslist_" + n).attr("class", "fa fa-close");
	}
	$("#bmslist_" + i).attr("class", "fa fa-check");
	
	selectStraId = sid;
	
	$("#strategy_name").html(name);

	$("#talkNameStra").html("<i class='fa fa-flask'></i>" + name);

	$("#comment-sid").val(sid);
	//console.log("sid:" + straListData[i]['id']);

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
					//console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					//alert(textStatus);
				},
				complete : function(request, textStatus) { //for additional info
					var option = request.responseText;
					//console.log("option:" + option);
					var Json = JSON.parse(option);
					$("#straPage").html(Json['page']);
					//console.log("option:" + Json['data']);
																						
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

						ListStrHtml += "<tr>"
								+ "<th scope='row'>"
								+ (i + 1)
								+ "</th>"
								+ "<td>"
								+ Json['data'][i]['title']
								+ "</td>"
								+ "<td>"
								+ Json['data'][i]['follow']
								+ "</td>"
								//+ "<td>"
								//	+ Json['data'][i]['income']
								//	+ "</td>"
								+ "<td>"
								+ insval
								+ "</td>"
								+ "<td>"
								+ Json['data'][i]['nickname']
								+ "</td>"
								+ "<td><button title='Success' class='btn btn-outline btn-success' type='button' onclick='istrSelect("
								+ i
								+ ")'>"
								+ "		<i class='fa "+fa+"' id='select_"+i+"' name='select_"+i+"'></i> <span class='sr-only'>Success</span>"
								+ "	</button></td>" + "</tr>";
					}

					$("#straList").html(ListStrHtml);
					//console.log("talkListInit:" + talkListInit);
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