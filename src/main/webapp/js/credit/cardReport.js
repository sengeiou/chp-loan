// 祥版征信相关操作（信用卡）
//金额验证
function addKeyup(){
	$('input[type=text][money=1]').each(function(index, element) {
		$(element).keyup(function(){
			
			//只输入数字和三位小数
			this.value = this.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
			this.value = this.value.replace(/^\./g,""); //验证第一个字符是数字而不是
			this.value = this.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
			this.value = this.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
			this.value = this.value.replace(/^(\-)*(\d+)\.(\d\d\d).*$/,'$1$2.$3'); //只能输入三位小数
		});
	});
}

$(function(){
	// 初始化数据
	initCardData();
});

// 初始化数据
function initCardData(){
	$("#cardoneTable").find("tbody").html("");
	$("#cardtwoTable").find("tbody").html("");
	$("#card24Table").find("tbody").html("");
	var data = $(window.parent.document).find("form[id='param']").serialize();
	$.ajax({
		url: ctx+"/credit/showCardData",
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
			if( data != null ){
				// 信用卡明细信息
				if(data.creditCardDetailedOneList != null && data.creditCardDetailedOneList[0].id != null){
					for( var i=0; i<data.creditCardDetailedOneList.length; i++ ){
						if( data.creditCardDetailedOneList[0].id != null ) {
							//append selce2难用，用clone
							var vTb=$('#cardoneTable');
							var vTr=$('#cardoneTable_trRow_1');
							var htmInfo=vTr.cloneSelect2(true,true);
							
							htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id","infoTr"+data.creditCardDetailedOneList[i].id);
							htmInfo.find("input[name='id']").val(data.creditCardDetailedOneList[i].id);
							htmInfo.find("input[name='cardType']").val(data.creditCardDetailedOneList[i].cardType);
//							htmInfo.find("select[name='cardType']").select2("val",data.creditCardDetailedOneList[i].cardType);
//							htmInfo.find("select[name='guaranteeType']").select2("val",data.creditCardDetailedOneList[i].guaranteeType);
							htmInfo.find("select[name='currency']").select2("val",data.creditCardDetailedOneList[i].currency);
							

							htmInfo.find("input[name='accountDay']").val(FormatDate(data.creditCardDetailedOneList[i].accountDay));
							htmInfo.find("input[name='cerditLine']").val(formatMoney(data.creditCardDetailedOneList[i].cerditLine));
							htmInfo.find("input[name='shareCreditLine']").val(formatMoney(data.creditCardDetailedOneList[i].shareCreditLine));
							htmInfo.find("input[name='liabilitiesLine']").val(formatMoney(data.creditCardDetailedOneList[i].liabilitiesLine));
							htmInfo.find("input[name='usedAmount']").val(formatMoney(data.creditCardDetailedOneList[i].usedAmount));
							htmInfo.find("input[name='deleteName']").attr("onClick","removeCardData(this,'"+data.creditCardDetailedOneList[i].id+"')");
							htmInfo.find("input[name='saveName']").attr("onClick","saveOneCardData(this,'"+data.creditCardDetailedOneList[i].id+"')");
							htmInfo.find("tr").attr("id","infoTr"+data.creditCardDetailedOneList[i].id);
							
							htmInfo.appendTo(vTb); 
							
							//绑定信息二
							showCardTwo(data.creditCardDetailedOneList[i].id,data.creditCardDetailedTwoList);
	
							// 显示期数
							showQsCard(data.creditCycleRecordExList,data.creditCardDetailedOneList[i].id,data.creditCardDetailedTwoList);
							// 如果信用卡状态是未激活，将几个日期的必填的class去掉
							removeDateRequired( null, data.creditCardDetailedOneList[i].id);
						}
					}
					
					// 显示编号
					sortCard();
				}
			}
		}
	});
}

//显示信息二
function showCardTwo(id,creditCardDetailedTwoList){
	for( var n=0;n<creditCardDetailedTwoList.length; n++ ){
		if( creditCardDetailedTwoList[0].id != null && creditCardDetailedTwoList[n].relationId == id){
			var vTb=$('#cardtwoTable');
			var vTr=$('#cardtwoTable_trRow_1');
			var htmInfo=vTr.cloneSelect2(true,true);
		
			htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id","infoTr2"+id);
			htmInfo.find("input[name='id']").val(creditCardDetailedTwoList[n].id);
			htmInfo.find("input[name='relationId']").val(creditCardDetailedTwoList[n].relationId);
			
			htmInfo.find("select[name='accountStatus']").select2("val",creditCardDetailedTwoList[n].accountStatus);
			htmInfo.find("select[name='accountStatus']").attr("onchange","setCardUnOpen(this,'"+id+"')");
		 
			htmInfo.find("input[name='shouldRepayAmount']").val(formatMoney(creditCardDetailedTwoList[n].shouldRepayAmount));
			htmInfo.find("input[name='realRepayAmount']").val(formatMoney(creditCardDetailedTwoList[n].realRepayAmount));
			htmInfo.find("input[name='realRepayDay']").val(FormatDate(creditCardDetailedTwoList[n].realRepayDay));
			htmInfo.find("input[name='currentOverdue']").val(creditCardDetailedTwoList[n].currentOverdue);			
			htmInfo.find("input[name='currentOverdueTotal']").val(formatMoney(creditCardDetailedTwoList[n].currentOverdueTotal));
			htmInfo.find("input[name='overdraftBalance']").val(formatMoney(creditCardDetailedTwoList[n].overdraftBalance));
			htmInfo.find("input[name='repaymentNo']").val(creditCardDetailedTwoList[n].repaymentNo);
			htmInfo.find("input[name='getinfoTime']").val(FormatDate(creditCardDetailedTwoList[n].getinfoTime));
			
			htmInfo.appendTo(vTb); 
		
			}
	}
}


// 显示期数
function showQsCard(QSData , id,CardDetailedTwoList){

	if(QSData != null && QSData.length > 0 && QSData[0].relationId != null){
		for( var s = 0; s < QSData.length; s++  ){
			if(QSData[s].relationId == id){
				// 最近24个月缴交状态
				var trPeriods = $("#periodscardHide").find("tbody").html();
				var htmPeriods = $(trPeriods);
				htmPeriods.find("input[name='num']").parent("td").parent("tr").attr("id","periodsTr"+id);
				htmPeriods.find("input[name='qs1']").val(QSData[s].qs1);
				htmPeriods.find("input[name='qs2']").val(QSData[s].qs2);
				htmPeriods.find("input[name='qs3']").val(QSData[s].qs3);
				htmPeriods.find("input[name='qs4']").val(QSData[s].qs4);
				htmPeriods.find("input[name='qs5']").val(QSData[s].qs5);
				htmPeriods.find("input[name='qs6']").val(QSData[s].qs6);
				htmPeriods.find("input[name='qs7']").val(QSData[s].qs7);
				htmPeriods.find("input[name='qs8']").val(QSData[s].qs8);
				htmPeriods.find("input[name='qs9']").val(QSData[s].qs9);
				htmPeriods.find("input[name='qs10']").val(QSData[s].qs10);
				htmPeriods.find("input[name='qs11']").val(QSData[s].qs11);
				htmPeriods.find("input[name='qs12']").val(QSData[s].qs12);
				htmPeriods.find("input[name='qs13']").val(QSData[s].qs13);
				htmPeriods.find("input[name='qs14']").val(QSData[s].qs14);
				htmPeriods.find("input[name='qs15']").val(QSData[s].qs15);
				htmPeriods.find("input[name='qs16']").val(QSData[s].qs16);
				htmPeriods.find("input[name='qs17']").val(QSData[s].qs17);
				htmPeriods.find("input[name='qs18']").val(QSData[s].qs18);
				htmPeriods.find("input[name='qs19']").val(QSData[s].qs19);
				htmPeriods.find("input[name='qs20']").val(QSData[s].qs20);
				htmPeriods.find("input[name='qs21']").val(QSData[s].qs21);
				htmPeriods.find("input[name='qs22']").val(QSData[s].qs22);
				htmPeriods.find("input[name='qs23']").val(QSData[s].qs23);
				htmPeriods.find("input[name='qs24']").val(QSData[s].qs24);
				
				//显示结算年月
				for( var i=0; i<CardDetailedTwoList.length; i++ ){
				if(CardDetailedTwoList[i]!=null && CardDetailedTwoList[i]!=undefined){
					if(QSData[s].relationId==CardDetailedTwoList[i].relationId){
						htmPeriods.find("input[name='balanceTime']").val(FormatDate1(CardDetailedTwoList[i].clearingDay));
					}
					
				  }
					
				}
				
				$("#card24Table").find("tbody").append(htmPeriods);
			}
		}
	}
}

//显示编号
function sortCard(){
	// 循环信用卡信息(进行排序)
	var allInfoNum = $("#cardoneTable").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
	// 循环信用卡二信息(进行排序)
	var allInfoNum = $("#cardtwoTable").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
	// 循环期数(进行排序)
	var allPeriodsNum = $("#card24Table").find("tbody").find("input[name='num']");
	allPeriodsNum.each(function(n,item){
		$(this).val(n+1);
	});
}

//添加信息
function addCardData(){
	var data = new Date().getTime()+"";
	var num = Math.floor(Math.random()*100+1)+"";
	var randomNum = data+num;
	// 信用卡一信息
	var vTr=$('#cardoneTable_trRow_1');
	var htmInfo1=vTr.cloneSelect2(true,true);
	htmInfo1.find("input[name='deleteName']").attr("onClick","removeCardData(this,'"+randomNum+"')");
	htmInfo1.find("input[name='saveName']").attr("onClick","saveOneCardData(this,'"+randomNum+"')");
	htmInfo1.find("input").val("");
	// 卡类型，贷记卡
	htmInfo1.find("input[name='cardType']").val("1");
	htmInfo1.find("input[name='deleteName']").val("删除");
	htmInfo1.find("input[name='saveName']").val("保存");
	htmInfo1.find("input[name='num']").parent("td").parent("tr").attr("id","infoTr"+randomNum);	
	$("#cardoneTable").find("tbody").append(htmInfo1);
	// 信用卡二信息
	//var trInfo2 = $("#cardtwoHide").find("tbody").html();
	var vTr2=$('#cardtwoTable_trRow_1');
	var htmInfo2 = vTr2.cloneSelect2(true,true);
	htmInfo2.find("input[name='num']").parent("td").parent("tr").attr("id","infoTr2"+randomNum);
	htmInfo2.find("select[name='accountStatus']").attr("onChange","setCardUnOpen(this,'"+randomNum+"')");
	$("#cardtwoTable").find("tbody").append(htmInfo2);
	// 最近24个月缴交状态
	var trPeriods = $("#periodscardHide").find("tbody").html();
	var htmPeriods = $(trPeriods);
	htmPeriods.find("input[name='num']").parent("td").parent("tr").attr("id","periodsTr"+randomNum);
	$("#card24Table").find("tbody").append(htmPeriods);
	// 显示编号
	sortCard();
}


function saveOneCardData(t,id){
	
	//验证表单
	if (!checkForm($("#cardoneForm"))) {
		return;
	}
	//验证表单
	if (!checkForm($("#cardtwoForm"))) {
		return;
	}
	//periodsForm
	if (!checkForm($("#periodscardForm"))) {
		return;
	}
	//验证数据
	if (!checkDate($("#cardoneForm"),$("#cardtwoForm"),
			"accountDay","realRepayDay",
			"最近一次实际还款日期需要晚于开户日期")) {
		return;
	}
	var param = makeOneParamCard(t,id);
	 
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	// 信用卡
	$.ajax({
		//url:ctx+'/creditdetailed/accumulation/saveData',
		url: ctx+"/credit/saveCardData",
		data:data,
		type: "post",
		dataType:'json',
		async: false,
		success:function(data){
			if(data != null){
				
				var OneId = data.relationId;
				var TwoId = data.id;
				
				// 信用卡一信息
				var CardForm1=$(t).parents("tr");
				var num = CardForm1.find("input[name='num']").val(); 
				CardForm1.find("input[name='id']").val(OneId);
				CardForm1.find("input[name='deleteName']").attr("onClick","removeCardData(this,'"+OneId+"')");
				CardForm1.find("input[name='saveName']").attr("onClick","saveOneCardData(this,'"+OneId+"')");
				
				// 贷款二信息
				var CardForm2=$("#cardtwoForm").find("table tbody tr").eq(num-1);
				CardForm2.find("input[name='id']").val(TwoId);
				CardForm2.find("input[name='relationId']").val(OneId);
				
				art.dialog.tips("操作成功");
			}else{
				art.dialog.tips("操作失败");
			}
		}
	});
}

function makeOneParamCard(t,id){
	// 信用卡一信息
	var CardForm1=$(t).parents("tr");
	
	var num = CardForm1.find("input[name='num']").val(); 
	
	var CardType = CardForm1.find("input[name='cardType']");
	var currency = CardForm1.find("select[name='currency']"); 
	var accountDay = CardForm1.find("input[name='accountDay']"); 

	var cerditLine = CardForm1.find("input[name='cerditLine']");  
	var shareCreditLine = CardForm1.find("input[name='shareCreditLine']");  
	var liabilitiesLine = CardForm1.find("input[name='liabilitiesLine']");  
	var usedAmount = CardForm1.find("input[name='usedAmount']");  

	var id = CardForm1.find("input[name='id']"); 
	var param = "";
	for(var i = 0; i < CardType.length; i++ ){
		param+="&creditCardDetailedOneList["+i+"].cardType="+$(CardType[i]).val();
		param+="&creditCardDetailedOneList["+i+"].currency="+$(currency[i]).val();
		param+="&creditCardDetailedOneList["+i+"].accountDay="+$(accountDay[i]).val();
		param+="&creditCardDetailedOneList["+i+"].cerditLine="+formatMoneyEx($(cerditLine[i]).val());
		param+="&creditCardDetailedOneList["+i+"].shareCreditLine="+formatMoneyEx($(shareCreditLine[i]).val());
		param+="&creditCardDetailedOneList["+i+"].liabilitiesLine="+formatMoneyEx($(liabilitiesLine[i]).val());
		param+="&creditCardDetailedOneList["+i+"].usedAmount="+formatMoneyEx($(usedAmount[i]).val());
		param+="&creditCardDetailedOneList["+i+"].id="+$(id[i]).val();
	}
	
	// 信用卡二信息
	var CardForm2=$("#cardtwoForm").find("table tbody tr").eq(num-1);;
	var relationId = CardForm2.find("input[name='relationId']");  
	var accountStatus = CardForm2.find("select[name='accountStatus']");  
	var shouldRepayAmount = CardForm2.find("input[name='shouldRepayAmount']");  
	var realRepayAmount = CardForm2.find("input[name='realRepayAmount']");  
	var realRepayDay = CardForm2.find("input[name='realRepayDay']"); 
	var currentOverdue = CardForm2.find("input[name='currentOverdue']"); 
	
	var currentOverdueTotal = CardForm2.find("input[name='currentOverdueTotal']");  
	var overdraftBalance = CardForm2.find("input[name='overdraftBalance']");  
	var repaymentNo = CardForm2.find("input[name='repaymentNo']");  
	var getinfoTime = CardForm2.find("input[name='getinfoTime']");  
	//var clearingDay = CardForm2.find("input[name='clearingDay']");  
  
	var id = CardForm2.find("input[name='id']"); 
	
	for(var i = 0; i < relationId.length; i++ ){
		param+="&creditCardDetailedTwoList["+i+"].relationId="+$(relationId[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].accountStatus="+$(accountStatus[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].shouldRepayAmount="+formatMoneyEx($(shouldRepayAmount[i]).val());
		
		param+="&creditCardDetailedTwoList["+i+"].realRepayAmount="+formatMoneyEx($(realRepayAmount[i]).val());
		param+="&creditCardDetailedTwoList["+i+"].realRepayDay="+$(realRepayDay[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].currentOverdue="+$(currentOverdue[i]).val();
		
		param+="&creditCardDetailedTwoList["+i+"].currentOverdueTotal="+formatMoneyEx($(currentOverdueTotal[i]).val());
		param+="&creditCardDetailedTwoList["+i+"].overdraftBalance="+formatMoneyEx($(overdraftBalance[i]).val());
		param+="&creditCardDetailedTwoList["+i+"].repaymentNo="+$(repaymentNo[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].getinfoTime="+$(getinfoTime[i]).val();
		
		param+="&creditCardDetailedTwoList["+i+"].id="+$(id[i]).val();
		
		//结算年月
		var balanceTime=$($("#periodscardForm").find("table tbody tr").eq(num-1).find("input[name='balanceTime']")[i]).val();
		
		if(balanceTime!=null && balanceTime!=undefined  ){
			param+="&creditCardDetailedTwoList["+i+"].clearingDay="+balanceTime;
		}
		
	}
	
	
	// 拼接期数
	var tArray = new Array();  
	var relationId = $("#periodscardForm").find("table tbody tr").eq(num-1).find("input[name='relationId']");
	for( var f = 0; f < 24; f++ ){
		tArray[f+1]=new Array();
		for( var g = 0; g < relationId.length; g++ ){
		tArray[f+1][g] = $("#periodscardForm").find("table tbody tr").eq(num-1).find("input[name=qs"+(f+1)+"]")[g];
		param+= "&creditCycleRecordExList["+g+"].qs"+(f+1)+"="+$(tArray[f+1][g]).val();
		}
	}
	
	return param;
}


//*******************************************
//保存数据
function saveCardData(){
	
	//验证表单
	if (!checkForm($("#cardoneForm"))) {
		return;
	}
	//验证表单
	if (!checkForm($("#cardtwoForm"))) {
		return;
	}
	//periodsForm
	if (!checkForm($("#periodscardForm"))) {
		return;
	}
	//验证数据
	if (!checkDate($("#cardoneForm"),$("#cardtwoForm"),
			"accountDay","realRepayDay",
			"最近一次实际还款日期需要晚于开户日期")) {
		return;
	}
	var param = makeParamCard();
	 
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	// 信用卡
	$.ajax({
		//url:ctx+'/creditdetailed/accumulation/saveData',
		url: ctx+"/credit/saveCardData",
		data:data,
		type: "post",
		dataType:'json',
		async: false,
		success:function(data){
			if(data){
				art.dialog.tips("操作成功");
			}
			else{
				art.dialog.tips("操作失败");
			}
			initCardData();
		}
	});
}

// 做好保存参数
function makeParamCard(){
	// 信用卡一信息
	var CardForm1=$("#cardoneForm");
	var CardType = CardForm1.find("input[name='cardType']");
//	var CardType = CardForm1.find("select[name='cardType']"); 
//	var guaranteeType = CardForm1.find("select[name='guaranteeType']"); 
	var currency = CardForm1.find("select[name='currency']"); 
	var accountDay = CardForm1.find("input[name='accountDay']"); 

	var cerditLine = CardForm1.find("input[name='cerditLine']");  
	var shareCreditLine = CardForm1.find("input[name='shareCreditLine']");  
	var liabilitiesLine = CardForm1.find("input[name='liabilitiesLine']");  
	var usedAmount = CardForm1.find("input[name='usedAmount']");  

	var id = CardForm1.find("input[name='id']"); 
	var param = "";
	for(var i = 0; i < CardType.length; i++ ){
		param+="&creditCardDetailedOneList["+i+"].cardType="+$(CardType[i]).val();
//		param+="&creditCardDetailedOneList["+i+"].guaranteeType="+$(guaranteeType[i]).val();
		param+="&creditCardDetailedOneList["+i+"].currency="+$(currency[i]).val();
		param+="&creditCardDetailedOneList["+i+"].accountDay="+$(accountDay[i]).val();
		param+="&creditCardDetailedOneList["+i+"].cerditLine="+formatMoneyEx($(cerditLine[i]).val());
		param+="&creditCardDetailedOneList["+i+"].shareCreditLine="+formatMoneyEx($(shareCreditLine[i]).val());
		param+="&creditCardDetailedOneList["+i+"].liabilitiesLine="+formatMoneyEx($(liabilitiesLine[i]).val());
		param+="&creditCardDetailedOneList["+i+"].usedAmount="+formatMoneyEx($(usedAmount[i]).val());
		param+="&creditCardDetailedOneList["+i+"].id="+$(id[i]).val();
	}
	
	// 信用卡二信息
	var CardForm2=$("#cardtwoForm");
	var relationId = CardForm2.find("input[name='relationId']");  
	var accountStatus = CardForm2.find("select[name='accountStatus']");  
	var shouldRepayAmount = CardForm2.find("input[name='shouldRepayAmount']");  
	var realRepayAmount = CardForm2.find("input[name='realRepayAmount']");  
	var realRepayDay = CardForm2.find("input[name='realRepayDay']"); 
	var currentOverdue = CardForm2.find("input[name='currentOverdue']"); 
	
	var currentOverdueTotal = CardForm2.find("input[name='currentOverdueTotal']");  
	var overdraftBalance = CardForm2.find("input[name='overdraftBalance']");  
	var repaymentNo = CardForm2.find("input[name='repaymentNo']");  
	var getinfoTime = CardForm2.find("input[name='getinfoTime']");  
	//var clearingDay = CardForm2.find("input[name='clearingDay']");  
  
	var id = CardForm2.find("input[name='id']"); 
	
	for(var i = 0; i < relationId.length; i++ ){
		param+="&creditCardDetailedTwoList["+i+"].relationId="+$(relationId[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].accountStatus="+$(accountStatus[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].shouldRepayAmount="+formatMoneyEx($(shouldRepayAmount[i]).val());
		
		param+="&creditCardDetailedTwoList["+i+"].realRepayAmount="+formatMoneyEx($(realRepayAmount[i]).val());
		param+="&creditCardDetailedTwoList["+i+"].realRepayDay="+$(realRepayDay[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].currentOverdue="+$(currentOverdue[i]).val();
		
		param+="&creditCardDetailedTwoList["+i+"].currentOverdueTotal="+formatMoneyEx($(currentOverdueTotal[i]).val());
		param+="&creditCardDetailedTwoList["+i+"].overdraftBalance="+formatMoneyEx($(overdraftBalance[i]).val());
		param+="&creditCardDetailedTwoList["+i+"].repaymentNo="+$(repaymentNo[i]).val();
		param+="&creditCardDetailedTwoList["+i+"].getinfoTime="+$(getinfoTime[i]).val();
		
		param+="&creditCardDetailedTwoList["+i+"].id="+$(id[i]).val();
		
		//结算年月
		var balanceTime=$($("#periodscardForm").find("input[name='balanceTime']")[i]).val();
		
		if(balanceTime!=null && balanceTime!=undefined  ){
			param+="&creditCardDetailedTwoList["+i+"].clearingDay="+balanceTime;
		}
		
	}
	
	
	// 拼接期数
	var tArray = new Array();  
	var relationId = $("#periodscardForm").find("input[name='relationId']");
	for( var f = 0; f < 24; f++ ){
		tArray[f+1]=new Array();
		for( var g = 0; g < relationId.length; g++ ){
		tArray[f+1][g] = $("#periodscardForm").find("input[name=qs"+(f+1)+"]")[g];
		param+= "&creditCycleRecordExList["+g+"].qs"+(f+1)+"="+$(tArray[f+1][g]).val();
		}
	}
	
	return param;
}
//*********************************************
//删除信用卡信息
function removeCardData(t,id){
	
	if(id != "" && id.length>20){
		
		if(confirm("该数据已在数据库中存在,确定要删除吗？")){
			$.ajax({
				
				url: ctx+"/credit/deleteCardData",
				data:"id="+id,
				type: "post",
				dataType:'json',
				success:function(data){
					if(data != "0"){
						art.dialog.tips("删除成功");
						var num = $(t).parents("tr").find("input[name='num']").val();
						$(t).parents("tr").remove();
						// 删除第二表格
						$("#cardtwoForm").find("table tbody tr").eq(num-1).remove();
						// 删除第三表格
						$("#periodscardForm").find("table tbody tr").eq(num-1).remove();
						// 显示编号
						sortCard();
					}else{
						art.dialog.tips("删除失败")
					}
				}
			});
		}
	}else{
		$(t).parent("td").parent("tr").remove();
		$("#infoTr2"+id).remove();
		$("#periodsTr"+id).remove();
		// 显示编号
		sortCard();
	}
}
function FormatDate1 (strTime) {
	if(strTime==undefined || strTime==null){
		return "";
	}
    //var date = new Date(strTime);
    
    return formatTime(strTime)
    //return date.getFullYear()+"-"+(date.getMonth()+1+"-");
}

//格式化时间为 YYYY-MM-DD
function formatTime( param ){
	var result = "";
	if( param != null && typeof(param) != "undefined"){
		var tim = new Date(param);
		var month = (tim.getMonth()+1);
		if(tim.getMonth() < 9){
			month="0"+(tim.getMonth()+1);
		}
		var day = tim.getDate();
		if(tim.getDate() < 10){
			day="0"+tim.getDate()
		}
		result = tim.getFullYear()+"-"+month+"-"+day;
	}
	return result;
}


function FormatDate (strTime) {
	if(strTime==undefined || strTime==null){
		return "";
	}
    var date = new Date(strTime);
    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate();
}
function validateNumEx(t){
	var msg=validateNumber(t.val());
	if(msg!='true'){
		t.focus();
		art.dialog.tips(msg);
	}
}
function validate24Ex(t){
	if(t==undefined || t==null || t.val()==''){
		return false;
	}
	var msg = validate24(t.val());		
	if(msg!='true'){
		addMyValidateCss(t);
		t.val('');
		art.dialog.tips(msg);
	}
}

function formatMoneyEx(money){
	var tpMoney=money;
	if(money!=undefined && money!=null){
		 tpMoney = money.toString().replace(/\,/g, '');
	}
	return tpMoney;
}

function setCardUnOpen(t, id) {
	// 信用卡一 行
	var cardOne = $("#infoTr"+id);
	// 信用卡二 行
	var cardTwo = $(t).parent("td").parent("tr");
	// 24期分期行
	var htmPeriods = $("#periodsTr"+id);
	if ($(t).val() == '3') {
		removeDateRequired('3', id);
		
		cardTwo.find("input[name='shouldRepayAmount']").val('0');
		cardTwo.find("input[name='realRepayAmount']").val('0');
		cardTwo.find("input[name='currentOverdue']").val('0');
		cardTwo.find("input[name='currentOverdueTotal']").val('0');
		cardTwo.find("input[name='overdraftBalance']").val('0');
		cardTwo.find("input[name='repaymentNo']").val('0');					
				
		htmPeriods.find("input[name='qs1']").val('/');
		htmPeriods.find("input[name='qs2']").val('/');
		htmPeriods.find("input[name='qs3']").val('/');
		htmPeriods.find("input[name='qs4']").val('/');
		htmPeriods.find("input[name='qs5']").val('/');
		htmPeriods.find("input[name='qs6']").val('/');
		htmPeriods.find("input[name='qs7']").val('/');
		htmPeriods.find("input[name='qs8']").val('/');
		htmPeriods.find("input[name='qs9']").val('/');
		htmPeriods.find("input[name='qs10']").val('/');
		htmPeriods.find("input[name='qs11']").val('/');
		htmPeriods.find("input[name='qs12']").val('/');
		htmPeriods.find("input[name='qs13']").val('/');
		htmPeriods.find("input[name='qs14']").val('/');
		htmPeriods.find("input[name='qs15']").val('/');
		htmPeriods.find("input[name='qs16']").val('/');
		htmPeriods.find("input[name='qs17']").val('/');
		htmPeriods.find("input[name='qs18']").val('/');
		htmPeriods.find("input[name='qs19']").val('/');
		htmPeriods.find("input[name='qs20']").val('/');
		htmPeriods.find("input[name='qs21']").val('/');
		htmPeriods.find("input[name='qs22']").val('/');
		htmPeriods.find("input[name='qs23']").val('/');
		htmPeriods.find("input[name='qs24']").val('/');
	} else {
		if (!cardOne.find("input[name='accountDay']").hasClass("required")) {
			cardOne.find("input[name='accountDay']").addClass("required");
		}
		if (!cardTwo.find("input[name='realRepayDay']").hasClass("required")) {
			cardTwo.find("input[name='realRepayDay']").addClass("required");
		}
		if (!htmPeriods.find("input[name='balanceTime']").hasClass("required")) {
			htmPeriods.find("input[name='balanceTime']").addClass("required");
		}		
	} 
	
}

/**
 * 如果信用卡状态是未激活，移除开户日期等的必填校验
 * 
 * */
function removeDateRequired(stat, id) {
	// 信用卡一 行
	var cardOne = $("#infoTr"+id);
	// 信用卡二 行
	var cardTwo = $("#infoTr2"+id);
	// 24期分期行
	var htmPeriods = $("#periodsTr"+id);
	
	if (stat == null || stat==undefined || stat=='') {
		stat = cardTwo.find("select[name='accountStatus']").val();
	}
	
	if (stat == '3') {
		cardOne.find("input[name='accountDay']").removeClass("required");
		cardTwo.find("input[name='realRepayDay']").removeClass("required");
		htmPeriods.find("input[name='balanceTime']").removeClass("required");
	}
}

