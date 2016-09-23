/*
 *	############################################################################
 *	
 *   showChart
 *   
 *	############################################################################
 */

var myChart = echarts.init(document.getElementById('echart_k'));
var StockJsonDBHFQ = "";
var StockMd=0;
var ToolTip=true;
var SliceStock=60;

function showChart(dt) {
	if (dt.length > 10) {
		// console.log("JsonList:" + dt );
		StockJsonDBHFQ = JSON.parse(dt);
		InitStockData(StockJsonDBHFQ['data'][0]);
		if(StockMd==0){
			AddmaData();
		}
	}
}

function InitStockData(JsonData) {
	var DateList = [];
	var legendName = [];
	var bool = 0;
	var seriesList = [];

	// console.log(JSON.stringify(JsonList));
	// var JsonData = JsonList;
	for ( var cn in JsonData) {
		//console.log("cn:" + cn);
		if (cn == "_id")
			continue;
		var Json = JsonData[cn];
		// console.log("Json:" + Json );
		if(cn=="hs300"){
			legendName.push("沪深300指数");
		}else{
			legendName.push(cn);
		}
		var seriesData = {};
		var shList = [];
		if(cn=="hs300"){			
			seriesData['name'] = "沪深300指数";
		}else{
			seriesData['name'] = cn;
		}
		
		seriesData['type'] = "k";
		var start = Json.length-SliceStock;
		
		var Json_Slice = Json.slice(start);
				
		for ( var k in Json_Slice) {
			sdate = Json_Slice[k]['date'].replace(/ 00:00:00/g, "");

			if (bool == 0) {

				DateList.push(sdate);
			}
			// console.log("sdate:" +sdate );
			var datalist = [];
			// 开盘，收盘，最低，最高
			datalist.push(Json_Slice[k]['open']);
			datalist.push(Json_Slice[k]['close']);
			datalist.push(Json_Slice[k]['low']);
			datalist.push(Json_Slice[k]['high']);
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
				//console.log("params:" +JSON.stringify(params) );
				var res = "";
				for (var m = 0; m < params.length; m++) {
					if (params[m]['series']['type'] == "k") {
						res += params[0].name + "<br />"+params[0].seriesName +":";
						res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : '
								+ params[0].value[3];
						res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : '
								+ params[0].value[2];
					} else {
						res += "<br />"+params[m].seriesName + ":" + params[m].value;
					}
				}
				return res;
			},
			axisPointer : {
				type : 'cross',
				crossStyle : {
					color : '#c7254e',
					width : 1,
					type : 'dashed'
				}
			}, 
		},
		legend : {
			data : legendName
		},
		toolbox : {
			show : ToolTip,
			feature : {
				mark : {
					show : ToolTip
				},
				dataZoom : {
					show : ToolTip
				},
				magicType : {
					show : ToolTip,
					type : [ 'line', 'bar' ]
				},
				restore : {
					show : ToolTip
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

	// console.log("JsonOpton:" +JSON.stringify(JsonOpton) );

	ChartRefresh(JsonOpton);
}

$(function() {
	$("input[name='inline-radio']").change(function() {
		var v = $("input[name='inline-radio']:checked").val();
		if (parseInt(v) == 0) {
			hfqStockData("data");
		} else {
			hfqStockData("hfq");
		}
	});
});

function hfqStockData(dtype) {
	if (StockJsonDBHFQ[dtype] == undefined) {
		console.log("StockJsonDBHFQ null !");
		return;
	}

	var JsonData = StockJsonDBHFQ[dtype][0];

	var oldOption = getChartOption();

	for ( var cn in JsonData) {
	//	console.log("cn:" + cn);
		if (cn == "_id")
			continue;
		var Json = JsonData[cn];

		var shList = [];
		var start = Json.length-SliceStock;
		
		var Json_Slice = Json.slice(start);
		for ( var k in Json_Slice) {

			var datalist = [];
			// 开盘，收盘，最低，最高
			datalist.push(Json_Slice[k]['open']);
			datalist.push(Json_Slice[k]['close']);
			datalist.push(Json_Slice[k]['low']);
			datalist.push(Json_Slice[k]['high']);
			shList.push(datalist);
		}
	}

	for (var m = 0; m < oldOption['series'].length; m++) {
		if (oldOption['series'][m]['type'] == "k") {
			oldOption['series'][m]['data'] = shList;
			break;
		}
	}

	// console.log("oldOption:" + JSON.stringify(oldOption));

	ChartRefresh(oldOption);
}

function AddmaData() {
	var JsonData = StockJsonDBHFQ['data'][0];

	var ln = {};
	ln["ma5"] = "5日均价";
	ln["ma10"] = "10日均价";
	ln["ma20"] = "20日均价";

	for ( var lk in ln) {
		var hsData = [];

		for ( var cn in JsonData) {
			//console.log("cn:" + cn);
			if (cn == "_id")
				continue;
			var Json = JsonData[cn];

			var shList = [];
			var start = Json.length-SliceStock;
			
			var Json_Slice = Json.slice(start);
			for ( var k in Json_Slice) {				
				shList.push(Json_Slice[k][lk]);
			}
		}

		addChartLineData(0, ln[lk], shList);
	}
}

function addChartLineData(isSelect, lname, hsData) {
	var oldOption = getChartOption();

	var legendName = oldOption['legend']['data'];

	if (isSelect == 1) {
		var selected = {};
		for (var n = 0; n < legendName.length; n++) {
			selected[legendName[n]] = false;
		}
		oldOption['legend']['selected'] = selected;
	}

	var seriesList = oldOption['series'];

	legendName.push(lname);

	var seriesData = {};
	seriesData['name'] = lname;
	seriesData['type'] = "line";
	seriesData['data'] = hsData;
	seriesData['yAxisIndex'] = 1;
	seriesList.push(seriesData);

	oldOption['legend']['data'] = legendName;
	oldOption['series'] = seriesList;

	// console.log("oldOption:" + JSON.stringify(oldOption));

	ChartRefresh(oldOption);
}

function getChartOption() {
	return myChart.getOption();
}

function ChartRefresh(o) {
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
		url : DomainUrl+"/stockdata?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',

		data : {
			"code" : code,
		},
		success : function(result) {
			// console.log("success" + result);
		},
		error : function(request, textStatus, errorThrown) {
			// alert(textStatus);
		},
		complete : function(request, textStatus) { // for additional info
			var option = request.responseText;
			// console.log("option:" + option);
			StockData = option;
			showChart(StockData);
		}
	});
}