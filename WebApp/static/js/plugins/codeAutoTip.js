/*
 *	############################################################################
 *	
 *   showChart
 *   
 *	############################################################################
 */
function CodeAuto(){
$("#code_num")
		.keypress(
				function() {
					var code = $("#code_num").val();
					code = code.replace(/_/g, "");
					$
							.ajax({
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
								complete : function(request, textStatus) { // for
																			// additional
																			// info
									var option = request.responseText;
									// console.log("option:" + option);
									if (option.length > 0) {
										var JsonList = JSON.parse(option);

										var html = " "
										for (var i = 0; i < JsonList.length; i++) {
											html += "<li><a href='javascript:void(0)' onclick=\"addCode('"
													+ JsonList[i]['code']
													+ "')\">"
													+ JsonList[i]['code']
													+ " "
													+ JsonList[i]['name']
													+ "</a></li>";
										}
										$("#codeMenu").html(html);
									}
								}
							});
				});
}