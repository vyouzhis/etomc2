/*
 *	############################################################################
 *	
 *   showChart
 *   
 *	############################################################################
 */


var myChart = echarts.init(document.getElementById('echart_k'));

function showChart(dt) {
	if (dt.length > 10) {
		//console.log("JsonList:" + dt );
		var JsonDataList = JSON.parse(dt);
						
		NewData(JsonDataList['data']);
		if(JsonDataList['hfq'] != undefined){
			AddNewData(JsonDataList['hfq'], "k");
		}
	}
}

function NewData(JsonList){
	var DateList = [];
	var legendName = [];
	var bool = 0;
	var seriesList = [];
	
	//console.log(JSON.stringify(JsonList));
	var JsonData = JsonList[0];
	for ( var cn in JsonData) {
		console.log("cn:" + cn );
		if(cn == "_id") continue;
		var Json = JsonData[cn];
		//console.log("Json:" + Json );
		legendName.push(cn + "_k线");
		var seriesData = {};
		var shList = [];
		seriesData['name'] = cn + "_k线";
		seriesData['type'] = "k";

		for ( var k in Json) {
			sdate = Json[k]['date'].replace(/ 00:00:00/g, "");
			
			if (bool == 0) {
				
				DateList.push(sdate);
			}
			//console.log("sdate:" +sdate );
			var datalist = [];
			// 开盘，收盘，最低，最高
			datalist.push(Json[k]['open']);
			datalist.push(Json[k]['close']);
			datalist.push(Json[k]['low']);
			datalist.push(Json[k]['high']);
			shList.push(datalist);
		}
		bool = 1;
		seriesData['data'] = shList;
		seriesList.push(seriesData);
	}

	var JsonOpton = {
		title : {
			text : ''
		},
		tooltip : {
			trigger : 'axis',
			formatter : function(params) {
				//console.log("params:" +JSON.stringify(params)  );
				var res = "";
				for (var m = 0; m < params.length; m++) {
					if (params[m]['series']['type'] == "k") {
						res = params[0].seriesName + ' '
								+ params[0].name;
						res += '<br/>  开盘 : ' + params[0].value[0]
								+ '  最高 : ' + params[0].value[3];
						res += '<br/>  收盘 : ' + params[0].value[1]
								+ '  最低 : ' + params[0].value[2];
					} else {
						res += params[0].seriesName + ":"
								+ params[0].value;
					}
				}
				return res;
			}
		},
		legend : {
			data : legendName
		},
		toolbox : {
			show : true,
			feature : {
				mark : {
					show : true
				},
				dataZoom : {
					show : true
				},
				magicType : {
					show : true,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : true
				}

			}
		},
		dataZoom : {
			show : true,
			realtime : true,
			start : 50,
			end : 100
		},
		xAxis : [

		{
			type : 'category',
			boundaryGap : true,
			axisTick : {
				onGap : false
			},
			splitLine : {
				show : false
			},
			data : DateList
		} ],
		yAxis : [ {
			type : 'value',
			scale : true,
			boundaryGap : [ 0.01, 0.01 ]
		}, {
			"scale" : true,
			"type" : "value",
			"name" : ""
		} ],
		series : seriesList
	};

	//console.log("JsonOpton:" +JSON.stringify(JsonOpton)  );

	ChartRefresh(JsonOpton);
}

function AddNewData(JsonList, ctype){
	
	var JsonData = JsonList[0];
	
	var oldOption = getChartOption();
	
	var legendName = oldOption['legend']['data'];
	var selected = {};
	for (var n = 0; n < legendName.length; n++) {
		selected[legendName[n]] = false;
	}
	oldOption['legend']['selected'] = selected;

	var seriesList = oldOption['series'];
			
	for ( var cn in JsonData) {
		console.log("cn:" + cn );
		if(cn == "_id") continue;
		var Json = JsonData[cn];
		//console.log("Json:" + Json );
		legendName.push(cn);
		var seriesData = {};
		var shList = [];
		seriesData['name'] = cn;
		seriesData['type'] = ctype;

		for ( var k in Json) {			
			
			var datalist = [];
			// 开盘，收盘，最低，最高
			datalist.push(Json[k]['open']);
			datalist.push(Json[k]['close']);
			datalist.push(Json[k]['low']);
			datalist.push(Json[k]['high']);
			shList.push(datalist);
		}
		
		seriesData['data'] = shList;
						
		seriesList.push(seriesData);
	}
	
	oldOption['legend']['data'] = legendName;
	oldOption['series'] = seriesList;
	
	console.log("oldOption:" + JSON.stringify(oldOption)  );
	
	ChartRefresh(oldOption);
}

function getChartOption(){
	return myChart.getOption();
}

function ChartRefresh(o){
	myChart.clear();
	myChart.setOption(o, true);
	myChart.refresh();
}

function KChart() {
	var code = $("#code_num").val();
	if (code.length == 0) {
		alert("请填写代码");
		return;
	}
	$("#icode").html(code);
	getStockData(code);
}

function getStockData(code) {
	$.ajax({
		url : "/etomc2/stockdata?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',

		data : {
			"code" : code,
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
			StockData = option;
			showChart(StockData);
		}
	});
}