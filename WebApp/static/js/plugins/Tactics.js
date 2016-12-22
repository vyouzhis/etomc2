
/*
 *	############################################################################
 *	
 *   Tactics
 *   
 *   
 *  
 {
    "RawMa": "0",  // 0 关闭原来的MA5 MA10 MA20, 1 保留
    "data": [
        {
            "format": "line",　　//  图像 line 线
            "yIndex": "0",　　//  刻度方位，０　左边，１右边
            "db": "list",　　　　 // 数组
            "date": "list",  // 时间数组，可为空
            "name": "db name" //　名字
        },
        {
             "format": "bar",　　//  图像 bar 线
             "yIndex": "0",　　//  刻度方位，０　左边，１右边
            "db": "list",　　　　 // 数组
            "date": "list",  // 时间数组，可为空
            "name": "db name" //　名字
        },
        {
            "format": "table", // table 显示，主要在讨论区
            "db": "json",　//  pandas 格式的json 
            "name": "db name"　// 名字
        }
    ]
}
 *   
 *   
 *	############################################################################
 */

function Tactics() {
	$("#looping").attr("class","fa fa-cog fa-spin fa-fw");
	$("#developing").attr("class","fa fa-connectdevelop fa-spin");
	var code = $("#code_num").val();
	if (code.length == 0) {
		BellNotifi("请填写代码");
		return;
	}
	
	$.ajax({
		url : DomainUrl+"/ptactics?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',

		data : {
			"code" : code,
			"id" : straListData[nowTId]['id'],
		},
		success : function(result) {
			// //console.log("success" + result);
		},
		error : function(request, textStatus, errorThrown) {
			// alert(textStatus);
			$("#looping").attr("class","fa fa-cog fa-fw");
			$("#developing").attr("class","fa fa-connectdevelop");
			talkSelect(4, straListData[nowTId]['id']);
		},
		complete : function(request, textStatus) { // for additional info
			var option = request.responseText;
			console.log("codeJson:" + option);
			if (option.length > 0) {
			
				var codeJson = JSON.parse(option);
						
				//ShowResult(codeJson);
				//
				var rawma = codeJson["rawma"];
				var data = codeJson["data"];
				var table = "";

				InitStockData(StockJsonDBHFQ['data'], code);
				AddmaData(code);
				var i = 0;
				
				for(k in data){
					
					format = data[k]["format"];
					if(format == "table"){
						//console.log("codeJson1:" + data[k]["db"]);
						table += showTable(data[k]["db"]);
						table += "<br />";
					}else {
						//console.log("codeJson2:" + data[k]["db"]);
						if(i == 0){
							ClearExtChart();
							i = 1;
						}
												
						var name = data[k]["name"];
						cdata = data[k]["db"];
						cdata = cdata.replace(/NaN/g, 0);
						
						var cData = JSON.parse(cdata);
						
						ExtChart(rawma, name, cData, format, data[k]["yIndex"]);
					}
					
				}
				addTables(table);				
			}
			$("#looping").attr("class","fa fa-cog fa-fw");
			$("#developing").attr("class","fa fa-connectdevelop");
			talkSelect(4, straListData[nowTId]['id']);
		}
	});
}

function showTable(o){
	$("#ResNList").attr("class", "");
	var codeJson = JSON.parse(o);
	var columns = codeJson['columns'];
	var th = "", td="";
	for(var k in columns){
		th += "<th>"+columns[k]+"</th>";
	}
	var data = codeJson['data'];
	for(var d in data){
		td+="<tr>";
		for(var m in data[d]){
			td += "<td>"+data[d][m]+"</td>";
		}
		td+="</tr>";
	}
	
	var table = "<table class='table table-striped'>"
		+ "<thead>"
		+ "<tr>"
		+ th
		+ "</tr>" + "</thead>"
		+ "<tbody id='infoshowtd' name='infoshowtd'>"
		+ td + "</tbody>" + "</table>";
	
	return table;
}

function addTables(table){
	var codeHtml = "<li class='media'>"
			+ "<a class='media-left' href='blog-single.html#'> <span class='avatar'>" +
			"<img alt='Avatar' class='img-circle' height='42' width='42' src='Data/UserLogo/etomc2_144x144.png'></span>"
			+ "</a><div class='media-body'>"
			+ "<h4 class='media-heading comment-author'>"
			+ "<a href='#'>站长</a></h4>"
			+ table + "<hr>"
			+ "</div>" + "</li>";
	
	// 只要当前的结果
	//var html = $("#ResLists").html()
	var html = "";
	$("#ResLists").html(codeHtml+html);
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