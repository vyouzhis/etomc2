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

jQuery(function($) {
	
	
	getStockData("hs300");

	getPage(1);

	CodeAuto();
	
	ListBookMarksCode(1, StockCid);

	$("#listtab1").click(function() {
		StockCode = "";
		StockSid = straListData[nowTId]['id'];
	});

	$("#listtab2").click(function() {
		StockCode = $("#code_num").val();
		StockSid = 0;
	});
});

