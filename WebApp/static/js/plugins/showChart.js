
/*
 *	############################################################################
 *	
 *   showChart
 *   
 *	############################################################################
 */


function showChart(dt, code) {
	if (dt.length > 10) {
		// //console.log("JsonList:" + dt );
		StockJsonDBHFQ = JSON.parse(dt);
		InitChart(code);		
	}
}

function InitChart(code){
	InitStockData(StockJsonDBHFQ['data'], code);
	
	if(showExt == 1){
		AddmaData(code);
		initExtChart();
	}
}

function InitStockData(JsonData, cn) {
	var DateList = [];
	var legendName = [];
	var bool = 0;
	var seriesList = [];
	var NameList = {};

	NameList["hs300"] = "沪深300指数";
	NameList["sh"] = "上证指数";
	NameList["sz"] = "深证指数";
	NameList["zx"] = "中小板指数";
	NameList["cy"] = "创业板指数";
	
	//console.log("NameList:" + NameList[cn] );
	if(NameList[cn] == undefined){
		legendName.push(cn);
		
	}else{
		legendName.push(NameList[cn]);
	}
	var seriesData = {};
	var shList = [];
	
	if(NameList[cn] == undefined){		
		seriesData['name'] = cn;		
	}else{
		seriesData['name'] = NameList[cn];
		//isDZ = 1;		
	}
	
	seriesData['type'] = "k";
			
	for ( var k in JsonData) {
		sdate = JsonData[k]['date'];

		if (bool == 0) {

			DateList.push(sdate);
		}		
		var datalist = [];
		// 开盘，收盘，最低，最高
		datalist.push(JsonData[k]['open']);
		datalist.push(JsonData[k]['close']);
		datalist.push(JsonData[k]['low']);
		datalist.push(JsonData[k]['high']);
		shList.push(datalist);
	}
	bool = 1;
	seriesData['data'] = shList;
	seriesList.push(seriesData);

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
					type : 'dashed',
					
				},
				
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
				
				restore : {
					show : ToolTip
				}

			}
		},
		
		 grid: {
		        x: 80,
		        y: 40,
		        x2:40,
		        y2:25
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

	//"dataZoom" 
	 
   if(isDZ == 1){
	   var dz =  {
	        show : true,
	        realtime: true,
	        start : 95,
	        end : 100
		    }
	   JsonOpton["dataZoom"] = dz;
	   JsonOpton["grid"] = {
		        x: 0,
		        y: 0,
		        x2:0,
		        y2:0
		    };
	 }
	//console.log("JsonOpton:" +JSON.stringify(JsonOpton) );

	ChartRefresh(JsonOpton);
}

function hfqStockData(dtype) {
	if (StockJsonDBHFQ[dtype] == undefined) {
		//console.log("StockJsonDBHFQ null !"+dtype);
		return;
	}

	var JsonData = StockJsonDBHFQ[dtype];

	var oldOption = getChartOption();
	var xDate = oldOption['xAxis'][0]['data'];
	
	var shList = [];

	for ( var k in JsonData) {
		var datalist = [];
		
		//console.log(xDate[k] +"---"+ JsonData[k]['date']);
		
		if (xDate[k] == JsonData[k]['date']){
			// 开盘，收盘，最低，最高
			datalist.push(JsonData[k]['open']);
			datalist.push(JsonData[k]['close']);
			datalist.push(JsonData[k]['low']);
			datalist.push(JsonData[k]['high']);
		}else{
			// 开盘，收盘，最低，最高
			datalist.push('-');
			datalist.push('-');
			datalist.push('-');
			datalist.push('-');
		}
				
		shList.push(datalist);
	}
	
	for (var m = 0; m < oldOption['series'].length; m++) {
		if (oldOption['series'][m]['type'] == "k") {
			oldOption['series'][m]['data'] = shList;
			break;
		}
	}

	// //console.log("oldOption:" + JSON.stringify(oldOption));

	ChartRefresh(oldOption);
}

function AddmaData(cn) {
	var JsonData = StockJsonDBHFQ['data'];

	var ln = {};
	ln["ma5"] = "5日均价";
	ln["ma10"] = "10日均价";
	ln["ma30"] = "30日均价";

	for ( var lk in ln) {
		var hsData = [];

		var shList = [];
		
		for ( var k in JsonData) {				
			shList.push(JsonData[k][lk]);
		}
		addChartLineData(0, ln[lk], shList, "line");
	}
}


function addChartLineData(isSelect, lname, hsData, types) {
	var oldOption = "";
	
	if(MutileQuant == 0){
		oldOption = getChartOption();	
	}else{
		oldOption = JSON.parse(MutileMainOption);	
	}
		
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
	seriesData['type'] = types;
	seriesData['data'] = hsData;
	seriesData['yAxisIndex'] = 1;
	seriesList.push(seriesData);

	oldOption['legend']['data'] = legendName;
	oldOption['series'] = seriesList;

	//console.log("oldOption:" + JSON.stringify(oldOption));

	ChartRefresh(oldOption);
}

function getChartOption() {
	return myChart.getOption();
}

function ChartRefresh(o) {
	$('input:radio[name=inline-radio][value=0]').attr('checked', true);
	myChart.clear();
	myChart.setOption(o, true);
	myChart.refresh();
}

function KChart() {
	var code = $("#code_num").val();
	if (code.length == 0) {
		BellNotifi("请填写代码");
		return;
	}
	$("#icode").html(code);
	getStockData(code);	
	
	$('input:radio[id=stockRadio0]').attr('checked', true);
}

function getStockData(code) {
	$.ajax({
		url : DomainUrl+"/stockdata?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',

		data : {
			"code" : code,
		},
		success : function(result) {
			// //console.log("success" + result);
		},
		error : function(request, textStatus, errorThrown) {
			// alert(textStatus);
		},
		complete : function(request, textStatus) { // for additional info
			var option = request.responseText;
			//console.log("option:" + option);
			StockData = option;
			showChart(StockData, code);
		}
	});
}

function getExtOldOption(){
	var oldOption = myChart_ext.getOption();
	return oldOption;
}

function ClearExtChart(){
	var oldOption = getExtOldOption();
	
	oldOption['series'] = [];
	
	myChart_ext.clear();
	myChart_ext.setOption(oldOption, true);
	myChart_ext.refresh();
}

function ExtChart(lname, hsData, types, yIndex){
	
	var shList = [];
	
	addChartLineData(0, lname, shList, types);
	var MainChartOption = "";
	var oldOption = "";
	
	if(MutileQuant == 0){
		 MainChartOption = getChartOption();	
		 oldOption = getExtOldOption();
	}else{
		 MainChartOption = JSON.parse(MutileMainOption);	
		 oldOption = JSON.parse(MutileExtOption);
		 console.log("MutileMainOption  --- :" + JSON.stringify(MainChartOption));
	}
		
	var legendName = MainChartOption['legend']['data'];
		
	var seriesList = oldOption['series'];
	
	var seriesData = {};
	seriesData['name'] = lname;
	seriesData['type'] = types;
	seriesData['yAxisIndex'] = yIndex;
	seriesData['data'] = hsData;
	seriesList.push(seriesData);

	oldOption['legend']['data'] = legendName;
	oldOption['series'] = seriesList;
	
	myChart_ext.clear();
	myChart_ext.setOption(oldOption, true);
	myChart_ext.refresh();
	
}

function markPointAction(dataList){	
	var Option = getChartOption();
	
	var markPoint = {
	        symbol: 'emptyPin',  
	        symbolSize:5,
	        itemStyle:{
	            normal:{color:"#3c6699",label:{position:'top'}}
	        },
	        data : dataList
	    };
	for (var m=0; m< Option['series'].length; m++){
		
		if (Option['series'][m]['type'] == "k"){
			//console.log("markPoint ..." );
			Option['series'][m]["markPoint"] = markPoint;
			//console.log("oldOption:" + JSON.stringify(Option));
			ChartRefresh(Option);
			break;
		}
	}
	
	
}

function initExtChart(){
	var MainChartOption = getChartOption();

	var ln = ['成交金额(元)','成交量'];	
	var shList = [];
	var lnl = ['line', 'bar']
	for(var n in ln){		
		addChartLineData(0, ln[n], shList, lnl[n]);
	}
	
	var legendName = MainChartOption['legend']['data'];
	
	var Jsonhfq = StockJsonDBHFQ['hfq'];
	
	var Json = StockJsonDBHFQ['data'];
	
	var Volume = [];
	var Amount = []
	var axisData = [];

	for ( var k in Json) {	 
		
		Volume.push( Json[k]['volume']/10000);
		
		if(Jsonhfq != undefined && Jsonhfq[k] != undefined){
			
			Amount.push(Jsonhfq[k]['amount']/10000);			
		}else{
			Amount.push(0);
		}
		axisData.push(Json[k]['date']);
		
	}
	
	var optionExt = {
		    tooltip : {
		        trigger: 'axis',
		        showDelay: 0             // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
		    },
		    legend: {
		        y : -30,
		        data:legendName
		    },		  
		    dataZoom : {
		        show : true,
		        realtime: true,
		        start : 80,
		        end : 100
		    },
		    grid: {
		        x: 80,
		        y:5,
		        x2:40,
		        y2:40
		    },
		    xAxis : [
		        {
		            type : 'category',
		            position:'top',
		            boundaryGap : true,
		            axisLabel:{show:false},
		            axisTick: {onGap:false},
		            splitLine: {show:false},
		            data : axisData
		        }
		    ],
		    yAxis : [ {
				type : 'value',
				scale : true,
				boundaryGap : [ 0.01, 0.01 ]
			}, {
				"scale" : true,
				"type" : "value",
				"name" : ""
			} ],
		    series : [
		        {
		            name:'成交金额(元)',
		            type:'line',	
		            yAxisIndex:1,	 		            
		            data: Amount,
		           
		        },
		        {
		            name:'成交量',
		            type:'bar',		            
		            data: Volume,
		          
		        }
		    ]
		};
	
	//console.log("oldOption:" + JSON.stringify(optionExt));
	
	myChart_ext.clear();
	myChart_ext.setOption(optionExt, true);
	myChart_ext.refresh();
}


function radarFun(){

	option = {
	    title : {
	        
	    },
	    tooltip : {
	        trigger: 'axis'
	    },
	    legend: {
	        orient : 'vertical',
	        x : 'right',
	        y : 'bottom',
	        data:['预算分配','实际开销']
	    },
	    toolbox: {
	       
	    },
	  
	    polar : [
	       {
	           indicator : [
	               { text: '盈利能力', max: 6000},
	               { text: '累计收益', max: 16000},
	               { text: '最大回撤', max: 30000},
	               { text: '夏普比率', max: 38000},
	               { text: '夏普比率', max: 52000},
	               { text: '收益波动率', max: 25000}
	            ]
	        }
	    ],
	    calculable : true,
	    series : [
	        {
	            name: '预算 vs 开销（Budget vs spending）',
	            type: 'radar',
	            data : [
	                {
	                    value : [4300, 10000, 28000, 35000, 50000, 19000],
	                    name : '预算分配'
	                },
	                 {
	                    value : [5000, 14000, 28000, 31000, 42000, 21000],
	                    name : '实际开销'
	                }
	            ]
	        }
	    ]
	};
	                    
	if($('#echart_radar').length > 0) {
		radarChart = echarts.init(document.getElementById('echart_radar'));
		radarChart.setOption(option, true);
		radarChart.refresh();
	}
}
