/*
 *	############################################################################
 *	
 *   talk
 *   
 *	############################################################################
 */

var symbols = " !\"#$%&'()*+,-./0123456789:;<=>?@";
var loAZ = "abcdefghijklmnopqrstuvwxyz";
symbols += loAZ.toUpperCase();
symbols += "[\\]^_`";
symbols += loAZ;
symbols += "{|}~";



function talkSelect(i, sid){
	var ariaCode = true;		
	var activeCode = "active";
	
	var ariaStra = false;
	var activeStra = "";
	
	StockCode = $("#code_num").val();
	StockSid = 0;
		
	if(i==1){
		// stra
		ariaCode = false;
		activeCode = "";
		
		ariaStra = true;
		activeStra = "active";		
		
		StockCode = "";
		StockSid = sid;
	}
	
	$("#talkNameCode").attr("aria-expanded",ariaCode);
	$("#listtab2").attr("class", activeCode);
	$("#chapter2").attr("class","tab-pane fade "+activeCode+" in");
	
	
	$("#talkNameStra").attr("aria-expanded", ariaStra);			
	$("#listtab1").attr("class",activeStra);				
	$("#chapter1").attr("class","tab-pane fade "+activeStra+" in");
	
}


function toAscii(valueStr) {

	valueStr = valueStr.toLowerCase();
	var hex = "0123456789abcdef";
	var text = "";
	var i = 0;

	for (i = 0; i < valueStr.length; i = i + 2) {
		var char1 = valueStr.charAt(i);
		if (char1 == ':') {
			i++;
			char1 = valueStr.charAt(i);
		}
		var char2 = valueStr.charAt(i + 1);
		var num1 = hex.indexOf(char1);
		var num2 = hex.indexOf(char2);
		var value = num1 << 4;
		value = value | num2;

		var valueInt = parseInt(value);
		var symbolIndex = valueInt - 32;
		var ch = '?';
		if (symbolIndex >= 0 && value <= 126) {
			ch = symbols.charAt(symbolIndex)
		}
		text += ch;
	}

	// document.form1.ascii.value = text;
	return text;
}

function saveTalk() {
	// var icore = getCookie("iCore");

	var msg = $("#comment-text").val();

	var dataList = 'msg=' + msg + "&sid=" + StockSid + "&code=" + StockCode + "&pid=" + 0;
	//console.log(dataList);

	$.ajax({
		url : "/etomc2/italk?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			//console.log("option:" + option);
		},
		error : function(response) {
			// console.log(response);
		}
	});

	listTalk(0, StockSid, StockCode);
	$("#comment-text").val("");
}

function getTalkPage(p){
	
	listTalk(p, StockSid, StockCode);
}

function listTalk(p, sid, code) {
	StockCode = code;
	StockSid = sid;
	$.ajax({
				url : "/etomc2/listtalk?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"sid" : sid,
					"code" : code,
					"p" : p
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
					// console.log("option:" + option);
					var Json = JSON.parse(option);
					StockInfoData = Json["StockInfo"];
				
					// var talkData = Json['data'];

					var ListHtml = "";

					for (var i = 0; i < Json['data'].length; i++) {
						var reply = "<p><a href='javascript:void(0)' onclick='tReplay("+JSON.stringify(Json['data'][i])+")'><i class='fa fa-reply'></i> 转发</a></p>"
						if (Json['data'][i]['reply'] == 1) {
							reply = "";
						}
						var date = new Date(
								parseInt(Json['data'][i]['ctime']) * 1000)
								.toLocaleString().substr(0, 17);
						var msg = Json['data'][i]['msg'];
						
						msg = msg.replace(/@/g, "<a class='product-title' href='#'>@");
						msg = msg.replace(/:/g, ":</a>");
						
						ListHtml += "<li class='media shopping-cart-table'>"
								+ "<a class='media-left' href='javascript:void(0)'> <span class='avatar anonymous'><i class='fa fa-user'></i></span>"
								+ "</a><div class='media-body'>"
								+ "	<h4 class='media-heading comment-author'>"
								+ "		<a href='#'>"
								+ Json['data'][i]['nickname'] + "</a>"
								+ "	</h4>"
								+ "	<span class='timestamp text-muted'>" + date
								+ "</span>" + "	<p>" + msg
								+ "</p>" + reply + "	<hr>" + "</div>" + "</li>";
					}

					if (sid != 0) {
						var strDesc =  "";
						if(p==0 || p ==1){
							strDesc = "<li class='media'>"
							+ "<a class='media-left' href='javascript:void(0)'> <span class='avatar anonymous'><i class='fa fa-user'></i></span>"
							+ "</a><div class='media-body'>"
							+ "	<h4 class='media-heading comment-author'>"
							+ "		<a href='#'>站长</a>"
							+ "	</h4>"
							+ "	<span class='timestamp text-muted'>" 
							+ "</span>" + "	<p>" + straListData[nowTId]['sdesc']
							+ "</p>	<hr>" + "</div>" + "</li>"; 													
						}
						
						$("#talkStra").html(strDesc+ListHtml);
					} else {
						var infoList = sInfo();
						var liHtml = "";
						var td = "";
						for ( var k in infoList) {
							var n = k.split("_");
							if (n.length == 2) {

								liHtml += "<li>"
										+ "<a data-toggle='tab' tabindex='-1' onclick='infoshow(\""
										+ n[0] + "\", \"" + code
										+ "\")' href='javascript:void(0)'>"
										+ infoList[k] + "</a></li>";
							}
						}

						var table = "<table class='table table-striped'>"
								+ "<thead>"
								+ "<tr>"
								+ "<th id='tdName' name='tdName'>基本面(属性)</th>"
								+ "<th><ul><li class='dropdown'>"
								+ "<a data-toggle='dropdown' class='dropdown-toggle' id='myTabDrop1' href='ui-tabs-accordion.html#' aria-expanded='false'>上市公司情况 <b class='caret'>"
								+ "</b>"
								+ "</a>"
								+ "<ul aria-labelledby='myTabDrop1' role='menu' class='dropdown-menu'>"
								+ liHtml + "</ul></li></ul>" + "</th>"
								+ "</tr>" + "</thead>"
								+ "<tbody id='infoshowtd' name='infoshowtd'>"
								+ td + "</tbody>" + "</table>";

						var codeHtml = "<li class='media'>"
								+ "<a class='media-left' href='blog-single.html#'> <span class='avatar anonymous'><i class='fa fa-user'></i></span>"
								+ "</a>" + "<div class='media-body'>"
								+ "	<h4 class='media-heading comment-author'>"
								+ "		<a href='#'>站长</a>" + "	</h4>"
								+ "	<span class='timestamp text-muted'>"
								+ "</span>" + "	<p>" + table + "</p>	<hr>"
								+ "</div>" + "</li>";

						$("#talkCode").html(codeHtml + ListHtml);
						infoshow("basics", code);
						$("#comment-code").val(code);						
					}
					
					//console.log("page:" + Json['page']);
					if(sid != 0){
						$("#talkListPageStra").html(Json['page']);
					}else{
						$("#talkListPageCode").html(Json['page']);
					}
						
					
				}
			});
}

function tReplay(o){
	console.log("replay:" + JSON.stringify(o));
	var val = "@"+o['nickname']+":"+o['msg'];
	$("#comment-text").val(val);
}

function infoshow(k, c) {
	var infoList = sInfo();

	$("#tdName").html(infoList[k + "_name"] + " (属性)");

	var td = "";

	for ( var ik in infoList[k]) {

		var db = "--";

		td += "<tr>" + "<th scope='row'>" + infoList[k][ik] + "</th>" + "<td>"
				+ db
		"</td>" + "</tr>";
	}

	for (var i = 0; i < StockInfoData.length; i++) {

		if (StockInfoData[i] === null) {
			continue;
		}

		if (StockInfoData[i][k] != undefined) {
			td = "";
			for ( var ik in infoList[k]) {

				var db = "";
				if (k == "basics") {
					db = StockInfoData[i][k][c][ik];
				} else {
					db = StockInfoData[i][k][0][ik];
				}
				if (db === undefined) {
					db = "--";
				}
				td += "<tr>" + "<th scope='row'>" + infoList[k][ik] + "</th>"
						+ "<td>" + db
				"</td>" + "</tr>";
			}
			break;
		}
	}

	$("#infoshowtd").html(td);
}

function sInfo() {
	var stockInfo = {};
	var basics = {};
	basics["industry"] = "所属行业";
	basics["area"] = "地区";
	basics["pe"] = "市盈率";
	basics["outstanding"] = "流通股本";
	basics["totals"] = "总股本(万)";
	basics["totalAssets"] = "总资产(万)";
	basics["liquidAssets"] = "流动资产";
	basics["fixedAssets"] = "固定资产";
	basics["reserved"] = "公积金";
	basics["reservedPerShare"] = "每股公积金";
	basics["eps"] = "每股收益";
	basics["bvps"] = "每股净资";
	basics["pb"] = "市净率";
	basics["timeToMarket"] = "上市日期";
	stockInfo["basics"] = basics;
	stockInfo["basics_name"] = "基本面";

	var report = {};
	report["eps"] = "每股收益";
	report["eps_yoy"] = "每股收益同比(%)";
	report["bvps"] = "每股净资产";
	report["roe"] = "净资产收益率(%)";
	report["epcf"] = "每股现金流量(元)";
	report["net_profits"] = "净利润(万元)";
	report["profits_yoy"] = "净利润同比(%)";
	report["distrib"] = "分配方案";
	report["report_date"] = "发布日期";
	stockInfo["report"] = report;
	stockInfo["report_name"] = "业绩报告";

	var profit = {};
	profit["roe"] = "净资产收益率(%)";
	profit["net_profit_ratio"] = "净利率(%)";
	profit["gross_profit_rate"] = "毛利率(%)";
	profit["net_profits"] = "净利润(万元)";
	profit["eps"] = "每股收益";
	profit["business_income"] = "营业收入(百万元)";
	profit["bips"] = "每股主营业务收入(元)";
	stockInfo["profit"] = profit;
	stockInfo["profit_name"] = "盈利能力";

	var operation = {};
	operation["arturnover"] = "应收账款周转率(次)";
	operation["arturndays"] = "应收账款周转天数(天)";
	operation["inventory_turnover"] = "存货周转率(次)";
	operation["inventory_days"] = "存货周转天数(天)";
	operation["currentasset_turnover"] = "流动资产周转率(次)";
	operation["currentasset_days"] = "流动资产周转天数(天)";
	stockInfo["operation"] = operation;
	stockInfo["operation_name"] = "营运能力";

	var growth = {};
	growth["mbrg"] = "主营业务收入增长率(%)";
	growth["nprg"] = "净利润增长率(%)";
	growth["nav"] = "净资产增长率";
	growth["targ"] = "总资产增长率";
	growth["epsg"] = "每股收益增长率";
	growth["seg"] = "股东权益增长率";
	stockInfo["growth"] = growth;
	stockInfo["growth_name"] = "成长能力";

	var debtpaying = {};
	debtpaying["currentratio"] = "流动比率";
	debtpaying["quickratio"] = "速动比率";
	debtpaying["cashratio"] = "现金比率";
	debtpaying["icratio"] = "利息支付倍数";
	debtpaying["sheqratio"] = "股东权益比率";
	debtpaying["adratio"] = "股东权益增长率";
	stockInfo["debtpaying"] = debtpaying;
	stockInfo["debtpaying_name"] = "偿债能力";

	var cashflow = {};
	cashflow["cf_sales"] = "经营现金净流量对销售收入比率";
	cashflow["rateofreturn"] = "资产的经营现金流量回报率";
	cashflow["cf_nm"] = "经营现金净流量与净利润的比率";
	cashflow["cf_liabilities"] = "经营现金净流量对负债比率";
	cashflow["cashflowratio"] = "现金流量比率";
	stockInfo["cashflow"] = cashflow;
	stockInfo["cashflow_name"] = "现金流量";

	return stockInfo;
}
