/*
 *	############################################################################
 *	
 *   etomc2 gather
 *   
 *	############################################################################
 */
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

//多重指标 0 单 1 多
var MutileQuant = 0;
//保存主的option
var MutileMainOption = "";
//保存副的option
var MutileExtOption = "";

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

var StockDescCode = "";

var showExt = 1;
// 定义是否要放缩
var isDZ = 0; 
var myChart;
var myChart_ext;
var MainChartLengendName=[];
if($('#echart_k').length > 0) {
	myChart = echarts.init(document.getElementById('echart_k'));
	
	if($('#echart_ext').length > 0) {
		myChart_ext = echarts.init(document.getElementById('echart_ext'));
		myChart.connect([myChart_ext]);
		myChart_ext.connect([myChart]);
		
		setTimeout(function (){
		    window.onresize = function () {
		        myChart.resize();
		        myChart_ext.resize();
		        
		    }
		},200);
	}
}