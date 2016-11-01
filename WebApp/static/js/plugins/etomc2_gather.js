
var StockData = "";
var talkListInit = 0;

// 全局
var StockCodeName = "";
var straListData = [];
var nowTId = 0;
var selectStraId = 0;

// 全局;
var StockInfoData;
var StockCode = "";
var StockSid = 0;

var DomainUrl="";

var symbols = " !\"#$%&'()*+,-./0123456789:;<=>?@";
var loAZ = "abcdefghijklmnopqrstuvwxyz";
symbols += loAZ.toUpperCase();
symbols += "[\\]^_`";
symbols += loAZ;
symbols += "{|}~";


var StockJsonDBHFQ = "";
var StockMd=0;
var ToolTip=true;
var SliceStock=360;

var StockDescCode = "";

var myChart;
if($('#echart_k').length > 0) {
	myChart = echarts.init(document.getElementById('echart_k'));
}

/*
 *	############################################################################
 *	
 *   arbinfo
 *   
 *	############################################################################
 */



function selling(c) {
	var dataList = 'act=0&code=' + c;
	$.ajax({
		url : DomainUrl+"/arbinfo?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;

			var msg = "平仓成功!";

			BellNotifi(msg);
		},
		error : function(response) {
			// //console.log(response);
		}
	});
}

function showStockDesc(c, n) {
	StockDescCode = c;
	$("#CodeName").html("<i class='fa fa-star'></i> "+c+" "+n);
	showStrategy();
}

function showStrategy() {
	var dataList = 'act=1&code=' + StockDescCode;
	$.ajax({
		url : DomainUrl+"/arbinfo?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			if (option.length < 10) {
				var msg = "数据出错!";

				BellNotifi(msg);
			} else {
				var StockDesc = JSON.parse(option);

				var html = "";

				for (var i = 0; i < StockDesc.length; i++) {
					html += "<tr>" + 
					      "<th >" + (i+1)+ "</th>" +
							"<th scope='row'>" + StockDesc[i]['title']+ "</th>" + 
							"<th>" + StockDesc[i]['name']+ "</th>	" + 
							"</tr>";
				}
				$("#litbody_0").html(html);
				liCurrent(0);
			}
		},
		error : function(response) {
			// //console.log(response);
		}
	});
}

function showSelling(){
	liCurrent(2);
}

function liCurrent(i) {
	for (var n = 0; n < 8; n++) {
		$("#li_" + n).attr("class", "list-group-item");
		$("#lidiv_" + n).attr("class", "col-md-9 hide");
	}
	$("#lidiv_" + i).attr("class", "col-md-9");
	$("#li_" + i).attr("class", "list-group-item current");
}

/*
 *	############################################################################
 *	
 *   bellNotification
 *   
 *	############################################################################
 */

var cnt = 10;

TabbedNotification = function(options) {
	var message = "<div id='ntf"
			+ cnt
			+ "' class='text alert-"
			+ options.type
			+ "' style='display:none'><p><i class='fa fa-bell'></i> "
			+ options.title
			+ "</p><div class='close'><a href='javascript:;' class='notification_close'><i class='fa fa-close'></i></a></div><p>"
			+ options.text + "</p></div>";

	if (!document.getElementById('custom_notifications')) {
		BellNotifi('doesnt exists');
	} else {
		$('#custom_notifications ul.notifications')
				.append(
						"<li><a id='ntlink"
								+ cnt
								+ "' class='alert-"
								+ options.type
								+ "' href='general_elements.html#ntf"
								+ cnt
								+ "'><i class='fa fa-bell animated shake'></i></a></li>");
		$('#custom_notifications #notif-group').append(message);
		cnt++;
		CustomTabs(options);
	}
};

CustomTabs = function(options) {
	$('.tabbed_notifications > div').hide();
	$('.tabbed_notifications > div:first-of-type').show();
	$('#custom_notifications').removeClass('dsp_none');
	$('.notifications a').click(
			function(e) {
				e.preventDefault();
				var $this = $(this), tabbed_notifications = '#'
						+ $this.parents('.notifications').data(
								'tabbed_notifications'), others = $this
						.closest('li').siblings().children('a'), target = $this
						.attr('href');
				others.removeClass('active');
				$this.addClass('active');
				$(tabbed_notifications).children('div').hide();
				$(target).show();
			});
};

CustomTabs();

var tabid = idname = '';

$(document).on('click', '.notification_close', function(e) {
	idname = $(this).parent().parent().attr("id");
	tabid = idname.substr(-2);
	$('#ntf' + tabid).remove();
	$('#ntlink' + tabid).parent().remove();
	$('.notifications a').first().addClass('active');
	$('#notif-group div').first().css('display', 'block');
});

function BellNotifi(msg) {
	new TabbedNotification({
		title : '当前信息提示',
		text : "当前提示信息内容：<strong>"+msg+"</strong>",
		type : 'info',
		sound : false
	});
}


/*
 *	############################################################################
 *	
 *   bookmark
 *   
 *	############################################################################
 */

// act 1 code 2 stra
function ListBookMarksCode(act, cid) {
	
	$.ajax({
				url : DomainUrl+"/bookmark?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"act" : act,
					"cid":cid
				},
				success : function(result) {
					// //console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					// alert(textStatus);
				},
				complete : function(request, textStatus) { // for additional
															// info
					var option = request.responseText;
					if(option.length < 5) return;
					////console.log("option:" + option);
					var Json = JSON.parse(option);
					var html="";
					for(var i=0; i<Json.length; i++){
						html += "<tr>"+
									"<th scope='row'>"+(i+1)+"</th>"+
									"<td>"+"<a href=''><code>"+
												"<i class='fa fa-star'></i>"+Json[i]['code']+" "+Json[i]['name']+
											"</code>"+
									"</a></td>"+
									"<td>dd</td>"+
									"<td>dd</td>"+
									"<td><button title='Success' class='btn btn-outline btn-success' type='button'" +
											" onclick='ListBookMarksStra(\""+Json[i]['code']+"\",\""+Json[i]['name']+"\", "+cid+", "+i+")'>"+
											"<i class='fa fa-close'  id='bmlist_"+i+"' id='bmlist_"+i+"'></i><span class='sr-only'>Success</span>"+
										"</button></td>"+
								"</tr>";
					}
										
					$("#bookMarkCodeList").html(html);
					ListBookMarksStra(Json[0]['code'], Json[0]['name'], cid, 0);
				}
			});
}

function ListBookMarksStra(code, name, cid, i){		
	
	for (n = 0; n < 3; n++) {
		$("#bmlist_" + n).attr("class", "fa fa-close");
	}

	$("#bmlist_" + i).attr("class", "fa fa-check");
	
	$("#code_num").val(code);
	
	$("#CodeAndName").html(code+" "+name);
	
	addCode(code, name);
		
	$.ajax({
		url : DomainUrl+"/bookmark?jsoncallback=?",
		contentType : 'text/html;charset=utf-8',
		data : {
			"act" : 2,
			"cid": cid,
			"code": code
		},
		success : function(result) {
			// //console.log("success" + result);
		},
		error : function(request, textStatus, errorThrown) {
			// alert(textStatus);
		},
		complete : function(request, textStatus) { // for additional
													// info
			var option = request.responseText;
			////console.log("option:" + option);
			var Json = JSON.parse(option);
			var htmlList="";
			for(var i=0; i<Json.length; i++){
				 htmlList += "<tr>"+
								"<th scope='row'></th>"+
								"<td>"+Json[i]['title']+"</td>"+
								"<td>10%</td>"+
								"<td>5.6</td>"+
								"<td><button title='Success' class='btn btn-outline btn-success' type='button' onclick='istrSelect2(\""+Json[i]['title']+"\", \""+Json[i]['id']+"\", "+i+")'>"+
								"<i class='fa fa-close' id='bmslist_"+i+"' id='bmslist_"+i+"'></i><span class='sr-only'>Success</span>"+
								"</button></td>"+
							"</tr>";
			}
//			$("#bmlist_"+i).attr("class", "fa fa-check");
			$("#nowCodeStraList").html(htmlList);						
		}
	});
	
}


function addBookMark(i, cid) {
	
	var code = $("#code_num").val();
	if(StockCode.length != 0){
		code = StockCode;
	}
		
	var dataList = 'act=' + i+"&sid=" + straListData[nowTId]['id'] + "&code=" + code +"&name="+StockCodeName+"&cid="+cid ;
		
	$.ajax({
		url : DomainUrl+"/bookmark?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			////console.log("option:" + option);
			var i = parseInt(option);
			var msg = "添加成功!";
			if(i==-1){
				msg = "参数有问题";
			}else if(i==-2){
				msg = "策略已达组合最大值";
			}else if(i==-3){
				msg = "策略已存在，不必再次添加";
			}else if(i==-4){
				msg = "股票数量已达组合最大值";
			}
									
			BellNotifi(msg);
		},
		error : function(response) {
			// //console.log(response);
		}
	});
}

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
									url : DomainUrl+"/stockcode?jsoncallback=?",
									contentType : 'text/html;charset=utf-8',

									data : {
										"codeext" : code,
									},
									success : function(result) {
										// //console.log("success" + result);
									},
									error : function(request, textStatus,
											errorThrown) {
										// alert(textStatus);
									},
									complete : function(request, textStatus) {
										var option = request.responseText;
										// //console.log("option:" + option);
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
											//console.log("option:" + option);
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

	////console.log("code:" + o);

	// 获取股票的讨论内容
	listTalk(0, 0, o);

	talkSelect(0);
	StockCode = o
	StockCodeName = n;		
}

function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays*24*60*60*1000));
    var expires = "expires="+d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
 
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

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
		InitStockData(StockJsonDBHFQ['data'], code);
		if(StockMd==0){
			AddmaData(code);
		}
	}
}

function InitStockData(JsonData, cn) {
	var DateList = [];
	var legendName = [];
	var bool = 0;
	var seriesList = [];

	 //console.log(JSON.stringify(JsonData));
	// var JsonData = JsonList;
	//for ( var cn in JsonData) {
		////console.log("cn:" + cn);
	////	if (cn == "_id")
		//	continue;
		
		var Json = JsonData;
		// //console.log("Json:" + Json );
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
			// //console.log("sdate:" +sdate );
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
	//}

	var JsonOpton = {
		title : {
			text : ''
		},
		tooltip : {
			trigger : 'axis',
			formatter : function(params) {
				////console.log("params:" +JSON.stringify(params) );
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

	// //console.log("JsonOpton:" +JSON.stringify(JsonOpton) );

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
		console.log("StockJsonDBHFQ null !"+dtype);
		return;
	}

	var JsonData = StockJsonDBHFQ[dtype];

	var oldOption = getChartOption();

	//for ( var cn in JsonData) {
	//	//console.log("cn:" + cn);
		//if (cn == "_id")
		//	continue;
		var Json = JsonData;

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
	//}

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
	ln["ma20"] = "20日均价";

	for ( var lk in ln) {
		var hsData = [];

	//	for ( var cn in JsonData) {
			////console.log("cn:" + cn);
		//	if (cn == "_id")
		//		continue;
			var Json = JsonData;

			var shList = [];
			var start = Json.length-SliceStock;
			
			var Json_Slice = Json.slice(start);
			for ( var k in Json_Slice) {				
				shList.push(Json_Slice[k][lk]);
			}
	//	}

		addChartLineData(0, ln[lk], shList);
	}
}

function addChartLineData(isSelect, lname, hsData) {
	var oldOption = getChartOption();
	
	////console.log("== oldOption:" + JSON.stringify(oldOption));
	
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

	// //console.log("oldOption:" + JSON.stringify(oldOption));

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
		BellNotifi("请填写代码");
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
			// //console.log("success" + result);
		},
		error : function(request, textStatus, errorThrown) {
			// alert(textStatus);
		},
		complete : function(request, textStatus) { // for additional info
			var option = request.responseText;
			// //console.log("option:" + option);
			StockData = option;
			showChart(StockData, code);
		}
	});
}

/*
 *	############################################################################
 *	
 *   strategyjs
 *   
 *	############################################################################
 */

function istrSelect(i) {
	for (n = 0; n < 5; n++) {
		$("#select_" + n).attr("class", "fa fa-close");
	}

	$("#select_" + i).attr("class", "fa fa-check");
	
	for (n = 0; n < 3; n++) {
		$("#bmslist_" + n).attr("class", "fa fa-close");
	}

	selectStraId = straListData[i]['id'];
	var name = straListData[i]['title'];
	nowTId = i;
	$("#strategy_name").html(name);

	$("#talkNameStra").html("<i class='fa fa-flask'></i>" + name);

	$("#comment-sid").val(straListData[i]['id']);
	////console.log("sid:" + straListData[i]['id']);

	// 获取策略的讨论内容
	listTalk(0, straListData[i]['id'], "");

	talkSelect(1, straListData[nowTId]['id']);
}


function istrSelect2(name, sid, i) {
	for (n = 0; n < 5; n++) {
		$("#select_" + n).attr("class", "fa fa-close");
	}
	
	for (n = 0; n < 3; n++) {
		$("#bmslist_" + n).attr("class", "fa fa-close");
	}
	$("#bmslist_" + i).attr("class", "fa fa-check");
	
	selectStraId = sid;
	
	$("#strategy_name").html(name);

	$("#talkNameStra").html("<i class='fa fa-flask'></i>" + name);

	$("#comment-sid").val(sid);
	////console.log("sid:" + straListData[i]['id']);

	// 获取策略的讨论内容
	listTalk(0, sid, "");

	talkSelect(1, sid);
}

function getPage(p) {
	
	$.ajax({
				url : DomainUrl+"/strapage?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',

				data : {
					"cid" : StockCid,
					"p" : p
				},
				success : function(result) {
					////console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					//alert(textStatus);
				},
				complete : function(request, textStatus) { //for additional info
					var option = request.responseText;
					////console.log("option:" + option);
					var Json = JSON.parse(option);
					$("#straPage").html(Json['page']);
					////console.log("option:" + Json['data']);
																						
					var ListStrHtml = "";
					for (i = 0; i < Json['data'].length; i++) {
						var fa = "fa-close";
																				
						if(selectStraId == Json['data'][i]['id']){
							fa = "fa-check";
						}
													
						var inte = Json['data'][i]['integral'];
						var insval = "";
						if (inte == 0) {
							insval = '免费';
						} else {
							insval = inte + ' 积分';
						}

						ListStrHtml += "<tr>"
								+ "<th scope='row'>"
								+ (i + 1)
								+ "</th>"
								+ "<td>"
								+ Json['data'][i]['title']
								+ "</td>"
								+ "<td>"
								+ Json['data'][i]['follow']
								+ "</td>"
								//+ "<td>"
								//	+ Json['data'][i]['income']
								//	+ "</td>"
								+ "<td>"
								+ insval
								+ "</td>"
								+ "<td>"
								+ Json['data'][i]['nickname']
								+ "</td>"
								+ "<td><button title='Success' class='btn btn-outline btn-success' type='button' onclick='istrSelect("
								+ i
								+ ")'>"
								+ "		<i class='fa "+fa+"' id='select_"+i+"' name='select_"+i+"'></i> <span class='sr-only'>Success</span>"
								+ "	</button></td>" + "</tr>";
					}

					$("#straList").html(ListStrHtml);
					////console.log("talkListInit:" + talkListInit);
					straListData = Json['data'];
					var name = straListData[0]['title'];
					
					if (talkListInit == 0) {
						listTalk(0, straListData[0]['id'], "");
						talkListInit = 1;
						istrSelect(0);
						$("#talkNameStra").html(
								"<i class='fa fa-flask'></i>" + name);
					}
					
				}
			});
}

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
		// //console.log("hs300 k:" + cReturn[k]['time']);

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

/*
 *	############################################################################
 *	
 *   talk
 *   
 *	############################################################################
 */




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

function URLencode(sStr){  
    return sStr.replace(/%/g, '%25');  
} 

function saveTalk() {

	var msg = $("#comment-text").val();
	
	var dataList = 'msg=' + URLencode(msg) + "&sid=" + StockSid + "&code=" + StockCode + "&pid=" + 0;

	$.ajax({
		url : DomainUrl+"/italk?jsoncallback=?",
		contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
		data : dataList,
		type : 'POST',
		dataType : 'json',
		success : function(response) {
			// //console.log(response.status);

		},
		complete : function(request, textStatus) {
			var option = request.responseText;
			////console.log("option:" + option);
		},
		error : function(response) {
			// //console.log(response);
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
				url : DomainUrl+"/listtalk?jsoncallback=?",
				contentType : 'text/html;charset=utf-8',
				data : {
					"sid" : sid,
					"code" : code,
					"p" : p
				},
				success : function(result) {
					// //console.log("success" + result);
				},
				error : function(request, textStatus, errorThrown) {
					// alert(textStatus);
				},
				complete : function(request, textStatus) { // for additional
					// info
					var option = request.responseText;
					// //console.log("option:" + option);
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
								+ "<a class='media-left' href='javascript:void(0)'> " +
										"<span class='avatar '><img alt='Avatar' class='img-circle' height='42' width='42' src=\"Data/"+Json['data'][i]['logo']+"\"></span>"
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
					
					////console.log("page:" + Json['page']);
					if(sid != 0){
						$("#talkListPageStra").html(Json['page']);
					}else{
						$("#talkListPageCode").html(Json['page']);
					}
						
					
				}
			});
}

function tReplay(o){
	//console.log("replay:" + JSON.stringify(o));
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
				+ db+"</td>" + "</tr>";
	}

	for (var i = 0; i < StockInfoData.length; i++) {

		if (StockInfoData[i] === null) {
			continue;
		}
		if ( StockInfoData[i]['Info'][k] === undefined) {
			continue;
		}
				
		InfoCode =  StockInfoData[i]['Info'][k][c];
		
		if (InfoCode != undefined) {
			td = "";
			for ( var ik in infoList[k]) {

				var db = "";
				
				db = InfoCode[ik];
				
				if (db === undefined) {
					db = "--";
				}
				td += "<tr>" + "<th scope='row'>" + infoList[k][ik] + "</th>"
						+ "<td>" + db+
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

