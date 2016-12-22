
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


var StockDescCode = "";

var myChart;
var myChart_ext;
var MainChartLengendName=[];
if($('#echart_k').length > 0) {
	myChart = echarts.init(document.getElementById('echart_k'));
	myChart_ext = echarts.init(document.getElementById('echart_ext'));
	
	myChart.connect([myChart_ext]);
	myChart_ext.connect([myChart]);
	
	setTimeout(function (){
	    window.onresize = function () {
	        myChart.resize();
	        myChart_ext.resize();
	        
	    }
	},200)
}