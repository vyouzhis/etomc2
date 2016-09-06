/*
 *	############################################################################
 *	
 *   showChart
 *   
 *	############################################################################
 */


var myChart = echarts.init(document.getElementById('echart_k'));

function showChart(dt) {
	if (dt.length > 2) {
		var JsonList = JSON.parse(dt);
		
		console.log("JsonList:" + JsonList );
		var DateList = [];
		var legendName = [];
		var bool = 0;
		var seriesList = [];
		for ( var cn in JsonList) {
			console.log("cn:" + cn );
			if(cn == "_id") continue;
			var Json = JsonList[cn];
			console.log("Json:" + Json );
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

		myChart.clear();
		myChart.setOption(JsonOpton, true);
		myChart.refresh();

	}
}

function getChartOption(){
	return myChart.getOption();
}

function ChartRefresh(o){
	myChart.clear();
	myChart.setOption(o, true);
	myChart.refresh();
}