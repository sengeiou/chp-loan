// 祥版征信相关操作

$(function(){
	// 初始化数据
	initData();
	//金额验证
	addKeyup();
	//校验
	//$("input[name='realRepayAmount']").attr("onblur","validateNumEx($(this));");
	$("input[name^='qs']").attr("onblur","validate24Ex($(this));");
});



// 初始化数据
function initData(){
	$("#loanoneTable").find("tbody").html("");
	$("#loantwoTable").find("tbody").html("");
	$("#loan24Table").find("tbody").html("");
	var data = $(window.parent.document).find("form[id='param']").serialize();
	$.ajax({
		url: ctx+"/credit/showData",
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
			
			if( data != null ){
				// 贷款明细信息
				if(data.creditLoanDetailedOneList != null && data.creditLoanDetailedOneList[0].id != null){
					for( var i=0; i<data.creditLoanDetailedOneList.length; i++ ){
						if( data.creditLoanDetailedOneList[0].id != null ){
							//append selce2难用，用clone
							var vTb=$('#loanoneTable');
							var vTr=$('#loanoneTable_trRow_1');
							var htmInfo=vTr.cloneSelect2(true,true);
							
							htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id","infoTr"+data.creditLoanDetailedOneList[i].id);
							htmInfo.find("input[name='id']").val(data.creditLoanDetailedOneList[i].id);
							htmInfo.find("select[name='loanType']").select2("val",data.creditLoanDetailedOneList[i].loanType);
							htmInfo.find("select[name='guaranteeType']").select2("val",data.creditLoanDetailedOneList[i].guaranteeType);
							htmInfo.find("select[name='currency']").select2("val",data.creditLoanDetailedOneList[i].currency);
							
							
							htmInfo.find("select[name='accountStatu']").select2("val",data.creditLoanDetailedOneList[i].accountStatu);
							htmInfo.find("select[name='repayFrequency']").select2("val",data.creditLoanDetailedOneList[i].repayFrequency);
							htmInfo.find("select[name='levelClass']").select2("val",data.creditLoanDetailedOneList[i].levelClass);
							htmInfo.find("input[name='repayMonths']").val(data.creditLoanDetailedOneList[i].repayMonths);
							htmInfo.find("input[name='releaseDay']").val(FormatDate(data.creditLoanDetailedOneList[i].releaseDay));
							htmInfo.find("input[name='actualDay']").val(FormatDate(data.creditLoanDetailedOneList[i].actualDay));
							htmInfo.find("input[name='conteactAmount']").val(formatMoney(data.creditLoanDetailedOneList[i].conteactAmount,2));
							htmInfo.find("input[name='loanBalance']").val(formatMoney(data.creditLoanDetailedOneList[i].loanBalance,2));
							htmInfo.find("input[name='getingTime']").val(FormatDate(data.creditLoanDetailedOneList[i].getingTime));
							htmInfo.find("input[name='deleteName']").attr("onClick","removeData(this,'"+data.creditLoanDetailedOneList[i].id+"')");
							htmInfo.find("input[name='saveName']").attr("onClick","saveOneData(this,'"+data.creditLoanDetailedOneList[i].id+"')");
							htmInfo.find("tr").attr("id","infoTr"+data.creditLoanDetailedOneList[i].id);
							
							htmInfo.appendTo(vTb); 
							//显示信息二
							showLoanTwo(data.creditLoanDetailedOneList[i].id,data.creditLoanDetailedTwoList);
							// 显示期数
							showQs(data.creditCycleRecordExList,data.creditLoanDetailedOneList[i].id,data.creditLoanDetailedTwoList);
						}
					}
					
					// 显示编号
					sort();
				}
			}
		}
	});
}

//显示信息二
function showLoanTwo(id,creditLoanDetailedTwoList){
	
	for( var i=0; i<creditLoanDetailedTwoList.length; i++ ){
		if( creditLoanDetailedTwoList[0].id != null &&
				 creditLoanDetailedTwoList[0].relationId==id){
			var vTb=$('#loantwoTable');
			var vTr=$('#loantwoTable_trRow_1');
			var htmInfo=vTr.cloneSelect2(true,true);
			htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id","infoTr2"+id);
			htmInfo.find("input[name='id']").val(creditLoanDetailedTwoList[i].id);
			htmInfo.find("input[name='relationId']").val(creditLoanDetailedTwoList[i].relationId);
			
			htmInfo.find("input[name='repayMonths']").val(creditLoanDetailedTwoList[i].repayMonths);
			htmInfo.find("input[name='realRepayDay']").val(FormatDate(creditLoanDetailedTwoList[i].realRepayDay));
			htmInfo.find("input[name='shouldRepayAmount']").val(formatMoney(creditLoanDetailedTwoList[i].shouldRepayAmount));
			htmInfo.find("input[name='realRepayAmount']").val(formatMoney(creditLoanDetailedTwoList[i].realRepayAmount));
			htmInfo.find("input[name='currentOverdue']").val(creditLoanDetailedTwoList[i].currentOverdue);
			htmInfo.find("input[name='currentOverdueTotal']").val(formatMoney(creditLoanDetailedTwoList[i].currentOverdueTotal));
			
			htmInfo.find("input[name='overdueNoTotal']").val(creditLoanDetailedTwoList[i].overdueNoTotal);
			htmInfo.find("input[name='overdueNoHighest']").val(creditLoanDetailedTwoList[i].overdueNoHighest);
			htmInfo.find("input[name='overduePrincipalLevel1']").val(formatMoney(creditLoanDetailedTwoList[i].overduePrincipalLevel1));
			htmInfo.find("input[name='overduePrincipalLevel2']").val(formatMoney(creditLoanDetailedTwoList[i].overduePrincipalLevel2));
			htmInfo.find("input[name='overduePrincipalLevel3']").val(formatMoney(creditLoanDetailedTwoList[i].overduePrincipalLevel3));
			htmInfo.find("input[name='overduePrincipalLevel4']").val(formatMoney(creditLoanDetailedTwoList[i].overduePrincipalLevel4));
			
			//htmInfo.find("tr").attr("id","infoTr2"+creditLoanDetailedTwoList[i].id);
			//$("#loantwoTable").find("tbody").append(htmInfo);
			htmInfo.appendTo(vTb); 
			}
	}
}

// 显示期数
function showQs(QSData , id,LoanDetailedTwoList){
	
	if(QSData != null && QSData.length > 0 && QSData[0].relationId != null){
		for( var s = 0; s < QSData.length; s++  ){
			if(QSData[s].relationId == id){
				// 最近24个月缴交状态
				var trPeriods = $("#periodsHide").find("tbody").html();
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
				for( var i=0; i<LoanDetailedTwoList.length; i++ ){
				if(LoanDetailedTwoList[i]!=null && LoanDetailedTwoList[i]!=undefined){
					if(QSData[s].relationId==LoanDetailedTwoList[i].relationId){
						htmPeriods.find("input[name='balanceTime']").val(FormatDate1(LoanDetailedTwoList[i].clearingDay));
					}
					
				}
					
				}
				
				$("#loan24Table").find("tbody").append(htmPeriods);
			}
		}
	}
}

//显示编号
function sort(){
	// 循环贷款信息(进行排序)
	var allInfoNum = $("#loanoneTable").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
	// 循环贷款二信息(进行排序)
	var allInfoNum = $("#loantwoTable").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
	// 循环期数(进行排序)
	var allPeriodsNum = $("#loan24Table").find("tbody").find("input[name='num']");
	allPeriodsNum.each(function(n,item){
		$(this).val(n+1);
	});
}

//添加贷款信息
function addData(){
	var data = new Date().getTime()+"";
	var num = Math.floor(Math.random()*100+1)+"";
	var randomNum = data+num;
	// 贷款一信息
	var vTr=$('#loanoneTable_trRow_1');
	var htmInfo1=vTr.cloneSelect2(true,true);
	htmInfo1.find("input[name='deleteName']").attr("onClick","removeData(this,'"+randomNum+"')");
	htmInfo1.find("input[name='saveName']").attr("onClick","saveOneData(this,'')");
	htmInfo1.find("input").val("");
	htmInfo1.find("input[name='deleteName']").val("删除");
	htmInfo1.find("input[name='saveName']").val("保存");
	$("#loanoneTable").find("tbody").append(htmInfo1);
	// 贷款二信息
	var vTr2=$('#loantwoTable_trRow_1');
	var htmInfo2 = vTr2.cloneSelect2(true,true);
	htmInfo2.find("input[name='num']").parent("td").parent("tr").attr("id","infoTr2"+randomNum);
	$("#loantwoTable").find("tbody").append(htmInfo2);
	// 最近24个月缴交状态
	var trPeriods = $("#periodsHide").find("tbody").html();
	var htmPeriods = $(trPeriods);
	htmPeriods.find("input[name='num']").parent("td").parent("tr").attr("id","periodsTr"+randomNum);
	$("#loan24Table").find("tbody").append(htmPeriods);
	// 显示编号
	sort();
}
//flag 在validate.js中
//对比大小
function checkDataBig(t1,t2,name1,name2,msg){
  flag =true;
  var pre=$(t1).find("input[name='"+name1+"']");
  var next=$(t2).find("input[name='"+name2+"']");
  for(var i = 0; i < pre.length; i++ ){
	  var p=$(pre[i]);
	  var n=$(next[i]);  
      if(eval(formatMoneyEx(p.val()))<eval(formatMoneyEx(n.val())) ){
	    art.dialog.tips(msg);
	    addMyValidateCss(p);
	    addMyValidateCss(n);
	    flag = false;
	    break;
     }
  }
	return flag;
}
//对比日期前后
function checkDate(t1,t2,name1,name2,msg){
  flag =true;
  var pre=$(t1).find("input[name='"+name1+"']");
  var next=$(t2).find("input[name='"+name2+"']");

  for(var i = 0; i < pre.length; i++ ){

  var p=$(pre[i]);
  var n=$(next[i]);
  if(p.val()!="" && n.val()!=""){
	  var begin=new Date(p.val().replace(/-/g,"/"));
	  var end=new Date(n.val().replace(/-/g,"/"));
	  if(begin-end>0 ){
		  art.dialog.tips(msg);
		  addMyValidateCss(p);
		  addMyValidateCss(n);
		  flag = false;
		  break;
	  }
  }
    }
  
	return flag;
}

function saveOneData(t,id){
	//验证表单
	if (!checkForm($("#loanoneForm"))) {
		return;
	}
	//验证表单
	if (!checkForm($("#loantwoForm"))) {
		return;
	}
	//periodsForm
	if (!checkForm($("#periodsForm"))) {
		return;
	}
	//periodsForm
	if (!checkForm($("#guaranteeoneForm"))) {
		return;
	}
	
	
	//数据校验
	if (!checkDataBig($("#loanoneForm"),$("#loantwoForm"),
			"repayMonths","repayMonths",
			"总还款月数大于等于剩余还款月数")) {
		return;
	}
	if (!checkDate($("#loanoneForm"),$("#loanoneForm"),
			"releaseDay","actualDay",
			"到期日期需要晚于发放日期")) {
		return;
	}
	if (!checkDataBig($("#loanoneForm"),$("#loanoneForm"),
			"conteactAmount","loanBalance",
			"合同金额需要大于等于贷款余额")) {
		return;
	}
	if (!checkDate($("#loanoneForm"),$("#loanoneForm"),
			"releaseDay","getingTime",
			"信息获取时间需要晚于发放日期")) {
		return;
	}
	
	var param = makeOneParam(t,id);
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	data+="&relationType=1&oneLoanRelationId=1"; //oneLoanRelationId 要动态传递
	
	// 贷款
	$.ajax({
		//url:ctx+'/creditdetailed/accumulation/saveData',
		url: ctx+"/credit/saveData",
		data:data,
		type: "post",
		dataType:'json',
		async: false,
		success:function(data){
			if(data != null){
				
				var OneId = data.relationId;
				var TwoId = data.id;
				
				// 贷款一信息
				var loanForm1=$(t).parents("tr");
				var num = loanForm1.find("input[name='num']").val(); 
				loanForm1.find("input[name='id']").val(OneId);
				loanForm1.find("input[name='deleteName']").attr("onClick","removeData(this,'"+OneId+"')");
				loanForm1.find("input[name='saveName']").attr("onClick","saveOneData(this,'"+OneId+"')");
				
				// 贷款二信息
				var loanForm2=$("#loantwoForm").find("table tbody tr").eq(num-1);
				loanForm2.find("input[name='id']").val(TwoId);
				loanForm2.find("input[name='relationId']").val(OneId);
				
				art.dialog.tips("操作成功");
			}else{
				art.dialog.tips("操作失败");
			}
		
			//initData();
		}
	});
	
	
	
}

function makeOneParam(t,id){
	// 贷款一信息
	var loanForm1=$(t).parents("tr");
	
	var num = loanForm1.find("input[name='num']").val(); 
	
	var loanType = loanForm1.find("select[name='loanType']"); 
	var guaranteeType = loanForm1.find("select[name='guaranteeType']"); 
	var currency = loanForm1.find("select[name='currency']"); 
	var accountStatu = loanForm1.find("select[name='accountStatu']"); 
	var repayFrequency = loanForm1.find("select[name='repayFrequency']");  
	var levelClass = loanForm1.find("select[name='levelClass']");  

	var repayMonths = loanForm1.find("input[name='repayMonths']");  
	var releaseDay = loanForm1.find("input[name='releaseDay']");  
	var actualDay = loanForm1.find("input[name='actualDay']");  
	var conteactAmount = loanForm1.find("input[name='conteactAmount']");  
	var loanBalance = loanForm1.find("input[name='loanBalance']"); 
	var getingTime = loanForm1.find("input[name='getingTime']"); 
	var id = loanForm1.find("input[name='id']"); 
	
	var param = "";
	for(var i = 0; i < loanType.length; i++ ){
		param+="&creditLoanDetailedOneList["+i+"].loanType="+$(loanType[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].guaranteeType="+$(guaranteeType[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].currency="+$(currency[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].accountStatu="+$(accountStatu[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].repayFrequency="+$(repayFrequency[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].levelClass="+$(levelClass[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].repayMonths="+$(repayMonths[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].releaseDay="+$(releaseDay[i]).val();
		
		param+="&creditLoanDetailedOneList["+i+"].actualDay="+$(actualDay[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].conteactAmount="+formatMoneyEx($(conteactAmount[i]).val());
		param+="&creditLoanDetailedOneList["+i+"].loanBalance="+formatMoneyEx($(loanBalance[i]).val());
		param+="&creditLoanDetailedOneList["+i+"].getingTime="+$(getingTime[i]).val();
	
		param+="&creditLoanDetailedOneList["+i+"].id="+$(id[i]).val();
	}
	
	// 贷款二信息
	var loanForm2=$("#loantwoForm").find("table tbody tr").eq(num-1);
	var relationId = loanForm2.find("input[name='relationId']");  
	var repayMonths = loanForm2.find("input[name='repayMonths']");  
	var realRepayDay = loanForm2.find("input[name='realRepayDay']");  
	var shouldRepayAmount = loanForm2.find("input[name='shouldRepayAmount']");  
	var realRepayAmount = loanForm2.find("input[name='realRepayAmount']"); 
	var currentOverdue = loanForm2.find("input[name='currentOverdue']"); 
	
	var currentOverdueTotal = loanForm2.find("input[name='currentOverdueTotal']");  
	var overdueNoTotal = loanForm2.find("input[name='overdueNoTotal']");  
	var overdueNoHighest = loanForm2.find("input[name='overdueNoHighest']");  
	var overduePrincipalLevel1 = loanForm2.find("input[name='overduePrincipalLevel1']");  
	var overduePrincipalLevel2 = loanForm2.find("input[name='overduePrincipalLevel2']");  
	var overduePrincipalLevel3 = loanForm2.find("input[name='overduePrincipalLevel3']");  
	var overduePrincipalLevel4 = loanForm2.find("input[name='overduePrincipalLevel4']");  
	var id = loanForm2.find("input[name='id']"); 
	
	for(var i = 0; i < relationId.length; i++ ){
		param+="&creditLoanDetailedTwoList["+i+"].relationId="+$(relationId[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].repayMonths="+$(repayMonths[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].realRepayDay="+$(realRepayDay[i]).val();
		
		param+="&creditLoanDetailedTwoList["+i+"].shouldRepayAmount="+formatMoneyEx($(shouldRepayAmount[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].realRepayAmount="+formatMoneyEx($(realRepayAmount[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].currentOverdue="+$(currentOverdue[i]).val();
		
		param+="&creditLoanDetailedTwoList["+i+"].currentOverdueTotal="+formatMoneyEx($(currentOverdueTotal[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overdueNoTotal="+$(overdueNoTotal[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].overdueNoHighest="+$(overdueNoHighest[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel1="+formatMoneyEx($(overduePrincipalLevel1[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel2="+formatMoneyEx($(overduePrincipalLevel2[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel3="+formatMoneyEx($(overduePrincipalLevel3[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel4="+formatMoneyEx($(overduePrincipalLevel4[i]).val());
	
		param+="&creditLoanDetailedTwoList["+i+"].id="+$(id[i]).val();
		
		//结算年月
		var balanceTime=$($("#periodsForm").find("table tbody tr").eq(num-1).find("input[name='balanceTime']")[i]).val();
		
		if(balanceTime!=null && balanceTime!=undefined  ){
			param+="&creditLoanDetailedTwoList["+i+"].clearingDay="+balanceTime;
		}
		
	}
	
	
	// 拼接期数
	var tArray = new Array();  
	var relationId = $("#periodsForm").find("table tbody tr").eq(num-1).find("input[name='relationId']");
	for( var f = 0; f < 24; f++ ){
		tArray[f+1]=new Array();
		for( var g = 0; g < relationId.length; g++ ){
		tArray[f+1][g] = $("#periodsForm").find("table tbody tr").eq(num-1).find("input[name=qs"+(f+1)+"]")[g];
		param+= "&creditCycleRecordExList["+g+"].qs"+(f+1)+"="+$(tArray[f+1][g]).val();
		}
	}
	
	return param;
	
	
}


//**************************************************

//保存数据
function saveData(){
	//验证表单
	if (!checkForm($("#loanoneForm"))) {
		return;
	}
	//验证表单
	if (!checkForm($("#loantwoForm"))) {
		return;
	}
	//periodsForm
	if (!checkForm($("#periodsForm"))) {
		return;
	}
	//periodsForm
	if (!checkForm($("#guaranteeoneForm"))) {
		return;
	}
	
	
	//数据校验
	if (!checkDataBig($("#loanoneForm"),$("#loantwoForm"),
			"repayMonths","repayMonths",
			"总还款月数大于等于剩余还款月数")) {
		return;
	}
	if (!checkDate($("#loanoneForm"),$("#loanoneForm"),
			"releaseDay","actualDay",
			"到期日期需要晚于发放日期")) {
		return;
	}
	if (!checkDataBig($("#loanoneForm"),$("#loanoneForm"),
			"conteactAmount","loanBalance",
			"合同金额需要大于等于贷款余额")) {
		return;
	}
	if (!checkDate($("#loanoneForm"),$("#loanoneForm"),
			"releaseDay","getingTime",
			"信息获取时间需要晚于发放日期")) {
		return;
	}
	
	var param = makeParam();
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	data+="&relationType=1&oneLoanRelationId=1"; //oneLoanRelationId 要动态传递
	// 贷款
	$.ajax({
		//url:ctx+'/creditdetailed/accumulation/saveData',
		url: ctx+"/credit/saveData",
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
		
			initData();
		}
	});
}

// 做好保存参数
function makeParam(){
	// 贷款一信息
	var loanForm1=$("#loanoneForm");
	var loanType = loanForm1.find("select[name='loanType']"); 
	var guaranteeType = loanForm1.find("select[name='guaranteeType']"); 
	var currency = loanForm1.find("select[name='currency']"); 
	var accountStatu = loanForm1.find("select[name='accountStatu']"); 
	var repayFrequency = loanForm1.find("select[name='repayFrequency']");  
	var levelClass = loanForm1.find("select[name='levelClass']");  

	var repayMonths = loanForm1.find("input[name='repayMonths']");  
	var releaseDay = loanForm1.find("input[name='releaseDay']");  
	var actualDay = loanForm1.find("input[name='actualDay']");  
	var conteactAmount = loanForm1.find("input[name='conteactAmount']");  
	var loanBalance = loanForm1.find("input[name='loanBalance']"); 
	var getingTime = loanForm1.find("input[name='getingTime']"); 
	var id = loanForm1.find("input[name='id']"); 
	
	var param = "";
	for(var i = 0; i < loanType.length; i++ ){
		param+="&creditLoanDetailedOneList["+i+"].loanType="+$(loanType[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].guaranteeType="+$(guaranteeType[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].currency="+$(currency[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].accountStatu="+$(accountStatu[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].repayFrequency="+$(repayFrequency[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].levelClass="+$(levelClass[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].repayMonths="+$(repayMonths[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].releaseDay="+$(releaseDay[i]).val();
		
		param+="&creditLoanDetailedOneList["+i+"].actualDay="+$(actualDay[i]).val();
		param+="&creditLoanDetailedOneList["+i+"].conteactAmount="+formatMoneyEx($(conteactAmount[i]).val());
		param+="&creditLoanDetailedOneList["+i+"].loanBalance="+formatMoneyEx($(loanBalance[i]).val());
		param+="&creditLoanDetailedOneList["+i+"].getingTime="+$(getingTime[i]).val();
	
		param+="&creditLoanDetailedOneList["+i+"].id="+$(id[i]).val();
	}
	
	// 贷款二信息
	var loanForm2=$("#loantwoForm");
	var relationId = loanForm2.find("input[name='relationId']");  
	var repayMonths = loanForm2.find("input[name='repayMonths']");  
	var realRepayDay = loanForm2.find("input[name='realRepayDay']");  
	var shouldRepayAmount = loanForm2.find("input[name='shouldRepayAmount']");  
	var realRepayAmount = loanForm2.find("input[name='realRepayAmount']"); 
	var currentOverdue = loanForm2.find("input[name='currentOverdue']"); 
	
	var currentOverdueTotal = loanForm2.find("input[name='currentOverdueTotal']");  
	var overdueNoTotal = loanForm2.find("input[name='overdueNoTotal']");  
	var overdueNoHighest = loanForm2.find("input[name='overdueNoHighest']");  
	var overduePrincipalLevel1 = loanForm2.find("input[name='overduePrincipalLevel1']");  
	var overduePrincipalLevel2 = loanForm2.find("input[name='overduePrincipalLevel2']");  
	var overduePrincipalLevel3 = loanForm2.find("input[name='overduePrincipalLevel3']");  
	var overduePrincipalLevel4 = loanForm2.find("input[name='overduePrincipalLevel4']");  
	var id = loanForm2.find("input[name='id']"); 
	
	for(var i = 0; i < relationId.length; i++ ){
		param+="&creditLoanDetailedTwoList["+i+"].relationId="+$(relationId[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].repayMonths="+$(repayMonths[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].realRepayDay="+$(realRepayDay[i]).val();
		
		param+="&creditLoanDetailedTwoList["+i+"].shouldRepayAmount="+formatMoneyEx($(shouldRepayAmount[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].realRepayAmount="+formatMoneyEx($(realRepayAmount[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].currentOverdue="+$(currentOverdue[i]).val();
		
		param+="&creditLoanDetailedTwoList["+i+"].currentOverdueTotal="+formatMoneyEx($(currentOverdueTotal[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overdueNoTotal="+$(overdueNoTotal[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].overdueNoHighest="+$(overdueNoHighest[i]).val();
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel1="+formatMoneyEx($(overduePrincipalLevel1[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel2="+formatMoneyEx($(overduePrincipalLevel2[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel3="+formatMoneyEx($(overduePrincipalLevel3[i]).val());
		param+="&creditLoanDetailedTwoList["+i+"].overduePrincipalLevel4="+formatMoneyEx($(overduePrincipalLevel4[i]).val());
	
		param+="&creditLoanDetailedTwoList["+i+"].id="+$(id[i]).val();
		
		//结算年月
		var balanceTime=$($("#periodsForm").find("input[name='balanceTime']")[i]).val();
		
		if(balanceTime!=null && balanceTime!=undefined  ){
			param+="&creditLoanDetailedTwoList["+i+"].clearingDay="+balanceTime;
		}
		
	}
	
	
	// 拼接期数
	var tArray = new Array();  
	var relationId = $("#periodsForm").find("input[name='relationId']");
	for( var f = 0; f < 24; f++ ){
		tArray[f+1]=new Array();
		for( var g = 0; g < relationId.length; g++ ){
		tArray[f+1][g] = $("#periodsForm").find("input[name=qs"+(f+1)+"]")[g];
		param+= "&creditCycleRecordExList["+g+"].qs"+(f+1)+"="+$(tArray[f+1][g]).val();
		}
	}
	
	return param;
}

//***********************************************************************

//删除贷款信息
function removeData(t,id){
	if(id != "" && id.length>20){
		
		if(confirm("该数据已在数据库中存在,确定要删除吗？")){
			$.ajax({
				
				url: ctx+"/credit/deleteData",
				data:"id="+id,
				type: "post",
				dataType:'json',
				success:function(data){
					if(data != "0"){
						art.dialog.tips("删除成功");
						var num = $(t).parents("tr").find("input[name='num']").val();
						$(t).parents("tr").remove();
						// 删除第二表格
						$("#loantwoForm").find("table tbody tr").eq(num-1).remove();
						// 删除第三表格
						$("#periodsForm").find("table tbody tr").eq(num-1).remove();
						// 显示编号
						sort();
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
		sort();
	}
}