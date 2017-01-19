
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
            "isExt": "0",　　//  ０　下面副图区，１主图区
            "db": "list",　　　　 // 数组
            "date": "list",  // 时间数组，可为空
            "name": "db name" //　名字
        },
        {
             "format": "bar",　　//  图像 bar 线
             "yIndex": "0",　　//  刻度方位，０　左边，１右边
             "isExt": "0",　　//  ０　下面副图区，１主图区
            "db": "list",　　　　 // 数组
            "date": "list",  // 时间数组，可为空
            "name": "db name" //　名字
        },
        {
            "format": "markPoint",　　//  在k 线上面标识
            "yIndex": "0",　　//  刻度方位，０　左边，１右边
            "isExt": "1",　　//  ０　下面副图区，１主图区
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
			//console.log("codeJson:" + option);
			if (option.length > 0) {
			
				var codeJson = JSON.parse(option);
						
				//ShowResult(codeJson);
				//
				var rawma = codeJson["rawma"];
				var data = codeJson["data"];
				var table = "";

				InitStockData(StockJsonDBHFQ['data'], code);
				if (rawma == 1){
					AddmaData(code);
					
				}
				
				var i = 0;
				
				for(k in data){
					
					format = data[k]["format"];
					if(format == "table"){
						//console.log("codeJson1:" + data[k]["db"]);
						table += data[k]["name"]
						table += showTable(data[k]["db"]);
						table += "<br />";
					}else if(format == "markPoint"){
						//{name : 'Buy', value : 20.61, xAxis: '2016-12-12', yAxis: 20.61}
						var mpList = []
						
						mdata = data[k]["db"];
						console.log("cdata:" + mdata);
						var mData = JSON.parse(mdata);
						var col = mData["columns"]
						for(var k in mData["index"]){
							var mpdict = {}
							mpdict["name"] = "BUY";
							mpdict["value"] = mData["data"][k][0];
							mpdict["xAxis"] = mData["data"][k][1];
							mpdict["yAxis"] = mData["data"][k][0];
							mpList.push(mpdict)	
							
							var selldict = {}
							selldict["name"] = "SELL";
							selldict["value"] = mData["data"][k][2];
							selldict["xAxis"] = mData["data"][k][3];
							selldict["yAxis"] = mData["data"][k][2];
							mpList.push(selldict)
							
						}
						markPointAction(mpList);
						console.log("cdata:" + JSON.stringify(mpList));
					}
					else {
						//console.log("isExt:" + data[k]["isExt"]);
						if(i == 0 && data[k]["isExt"] == 0){
							ClearExtChart();
							i = 1;
						}
												
						var name = data[k]["name"];
						cdata = data[k]["db"];
						cdata = cdata.replace(/NaN/g, 0);
						
						var cData = JSON.parse(cdata);
						// 这儿有一个 bug
						if(data[k]["isExt"] == 0){
							//console.log("codeJson2:" + name);
							ExtChart(name, cData, format, data[k]["yIndex"]);
						}else{
							addChartLineData(0, name, cData, format);
						}
						
					}
					
				}
				if (table.length > 0){
					addTables(table);	
				}
						
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
	var th = "", td="";
	
	if(codeJson['columns'] != undefined){
		var columns = codeJson['columns'];	
		for(var k in columns){
			th += "<th>"+columns[k]+"</th>";
		}
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