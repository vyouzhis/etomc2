/*
 *	############################################################################
 *	
 *   Tactics
 *   
 *	############################################################################
 */

function Tactics() {
	var code = $("#code_num").val();
	if (code.length == 0) {
		alert("请填写代码");
		return;
	}
		
	$.ajax({
		url : "/etomc2/ptactics?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',

		data : {
			"code" : code,
			"id" : straListData[nowTId]['id'],
		},
		success : function(result) {
			// console.log("success" + result);
		},
		error : function(request, textStatus, errorThrown) {
			// alert(textStatus);
		},
		complete : function(request, textStatus) { // for additional info
			var option = request.responseText;

			if (option.length > 0) {
			
				var codeJson = JSON.parse(option);
								
				ShowResult(codeJson);
				tacticsChart(codeJson);
			}
		}
	});
}


function tacticsChart(codeJson){
	
	var cReturn = codeJson['returns'];
	var cData = [];
	for ( var k in cReturn) {
		// console.log("hs300 k:" + cReturn[k]['time']);

		cData.push(cReturn[k]['item']);
	}
	
	var name = straListData[nowTId]['title'];
	
	addChartLineData(1, name, cData);
}

function ShowResult(cJ){
	var profit = cJ["profit"];
	$("#iprofit").html(profit + "<span>年化收益(%)</span>");
	var sharpe = cJ["sharpe"];
	$("#isharpe").html(sharpe + "<span>夏普比率</span>");
	var maxDraw = cJ['maxDraw'];
	$("#iMaxDrawDown").html(maxDraw + "<span>最大回撤(%)</span>");

	var beta = cJ['alpha']['beta'];
	$("#ibeta").html(beta + "<span>贝塔</span>");

	var alpha = cJ['alpha']['alpha'];
	$("#ialpha").html(alpha + "<span>阿尔法(%)</span>");
	
	$("#resultVal").attr("class", "row");
}