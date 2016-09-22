/*
 *	############################################################################
 *	
 *   showChart
 *   
 *	############################################################################
 */
function CodeAuto() {
	
	$("#code_num")
			.keyup(
					function(e) {
						var key = (e.keyCode ? e.keyCode : e.which);
						var keyOK = 0;
						var code = $("#code_num").val();
						code = code.replace(/_/g, "");
						
						if (key == 13 && code.length == 6) {
							keyOK = 1;
						}

						$.ajax({
									url : "/etomc2/stockcode?jsoncallback=?",
									contentType : 'text/html;charset=utf-8',

									data : {
										"codeext" : code,
									},
									success : function(result) {
										// console.log("success" + result);
									},
									error : function(request, textStatus,
											errorThrown) {
										// alert(textStatus);
									},
									complete : function(request, textStatus) {
										var option = request.responseText;
										// console.log("option:" + option);
										if (option.length > 2) {
											var JsonList = JSON.parse(option);

											var html = " "
											for (var i = 0; i < JsonList.length; i++) {
												html += "<li><a href='javascript:void(0)' onclick=\"addCode('"
														+ JsonList[i]['code']
														+ "'"
														+ ", '"
														+ JsonList[i]['name']
														+ "')\">"
														+ JsonList[i]['code']
														+ " "
														+ JsonList[i]['name']
														+ "</a></li>";
												if(keyOK == 1 && JsonList[i]['code'] == code){
													$("#StockStradiv").attr("class", "panel-heading");
													addCode(code, JsonList[i]['name']);
													
													KChart();
												}
											}
											
											$("#codeMenu").html(html);
										} else {
											console.log("option:" + option);
										}
									}
								});

					});

}

function addCode(o, n) {
	$("#code_num").val(o);

	$("#talkNameCode").html("<i class='fa fa-bar-chart-o'></i>" + o + " " + n);
	$("#talkNameCode").attr("class", "");

	$("#talkNameCode").focus();

	//console.log("code:" + o);

	// 获取股票的讨论内容
	listTalk(0, 0, o);

	talkSelect(0);
	StockCode = o
	StockCodeName = n;		
}
