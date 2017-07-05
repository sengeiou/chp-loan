$(function(){
	
	//金额验证
	function addKeyup(){
		$('input[type=text][money=1]').each(function(index, element) {
			$(element).keyup(function(){
				//只输入数字和两位小数
				this.value = this.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
				this.value = this.value.replace(/^\./g,""); //验证第一个字符是数字而不是
				this.value = this.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
				this.value = this.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
				this.value = this.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
			});
		});
	}	
	
	//金额验证
	addKeyup();
	
	//贷款信息
	loanInit();
	
	//信用卡信息
	cardInit();
	
	// 返回按钮
	$("#backBtn").click(function(){
		window.location = ctx + "/borrow/borrowlist/fetchTaskItems";
	});	
	
	// 如果贷款账户状态是结清，去除截止日期等的必填校验		
});

//格式化金额
function formatMoney1(flag, index, name){
	//显示字段
	var inputName = $("#" + flag + "InfoForm").find("input[name='" + name + "Name']");
	//隐藏字段
	var input = $("#" + flag + "InfoForm").find("input[name='" + name + "']");
	//显示值
	var wd = $(inputName[index]).val();
	
	wd = wd.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
	wd = wd.replace(/^\./g,""); //验证第一个字符是数字而不是
	wd = wd.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
	wd = wd.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
	wd = wd.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
	
	var s = Number(wd.replace(/,/g,''));
	//设置隐藏值
	$(input[index]).val(s)
	if(s != null && s != "" && s != 0){
		s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(2) + ""; 
		var l = s.split(".")[0].split("").reverse(),r = s.split(".")[1]; 
		var t = ""; 
		for (var i = 0; i < l.length; i++) { 
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : ""); 
		}
		if(r != "00"){
			$(inputName[index]).val(t.split("").reverse().join("") + "." + r);
		}else{
			$(inputName[index]).val(t.split("").reverse().join(""));
		}
	}	
}


function loanInit() {
	// 去除必填
	$("#loanInfoForm").find('select[name="accountStatus"]').each(function(index) {
		removeDateRequired(this);
	});
	
	$("#loanInfoForm").find('select[name="isOverdue"]').each(function(index) {
		clearOverDueCount(this);
	});
}

//去除必填
function cardInit() {
	$("#cardInfoForm").find('select[name="isOverdue"]').each(function(index) {
		clearOverDueCount(this);
	});	
}

//添加信息
function addTr(flag){
	
	// 添加信息tr
	var trInfo = $("#" + flag + "TableHide").find("tbody");
	var htmInfo = trInfo.cloneSelect2(true,true);
	$("#" + flag + "Table").append(htmInfo);
	// 调整序号
	setNum(flag);
}

//调整序号
function setNum(flag) {
	
	var allInfoNum = $("#" + flag + "Table").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
		$(this).parent("td").parent("tr").attr("id","infoTr" + (i + 1));
		$(this).parent("td").parent("tr").find("td a[name='aRemoveInfo']").attr("onClick","removeTr('" + flag + "', '" + (i + 1)+"')");
		//信用卡信息增加金额转换
		if (flag == "card") {
			$(this).parent("td").parent("tr").find("td select[name='accountStatus']").attr("onchange","changeCardStatus(this)");
			$(this).parent("td").parent("tr").find("td input[name='limitName']").attr("onblur","formatMoney1('card', '" + i + "', 'limit')");
			$(this).parent("td").parent("tr").find("td input[name='usedLimitName']").attr("onblur","formatMoney1('card', '" + i + "', 'usedLimit')");
			$(this).parent("td").parent("tr").find("td input[name='overdueAmountName']").attr("onblur","formatMoney1('card', '" + i + "', 'overdueAmount')");
			$(this).parent("td").parent("tr").find("td select[name='isOverdue']").attr("onchange","clearOverDueCount(this')");
			
		}
		//贷款明细信息增加金额转换
		if (flag == "loan") {
			var accountStat = $(this).parent("td").parent("tr").find("td select[name='accountStatus']");
			removeDateRequired(accountStat);
			accountStat.attr("onchange","changeLoanStatus(this)");
			
			$(this).parent("td").parent("tr").find("td input[name='conteactAmountName']").attr("onblur","formatMoney1('loan', '" + i + "', 'conteactAmount')");
			$(this).parent("td").parent("tr").find("td input[name='loanBalanceName']").attr("onblur","formatMoney1('loan', '" + i + "', 'loanBalance')");
			$(this).parent("td").parent("tr").find("td input[name='overdueAmountName']").attr("onblur","formatMoney1('loan', '" + i + "', 'overdueAmount')");
			$(this).parent("td").parent("tr").find("td select[name='isOverdue']").attr("onchange","clearOverDueCount(this')");			
		}
		//保证人代偿信息增加金额转换
		if (flag == "payback") {
			$(this).parent("td").parent("tr").find("td input[name='totalPaybackAmountName']").attr("onblur","formatMoney1('payback', '" + i + "', 'totalPaybackAmount')");
			$(this).parent("td").parent("tr").find("td input[name='residualAmountName']").attr("onblur","formatMoney1('payback', '" + i + "', 'residualAmount')");
		}
		
	});
}

// 删除信息
function removeTr(flag, index){
	// 数据库删除
	var id = $("#" + flag + "InfoForm").find("input[name='id']"); // 主键
	var i = index - 1;
	var idVal = $(id[i]).val();
	// 后台数据库删除
	if (idVal != "") {
		
		if (confirm("确认要删除吗？")) {
			var url = "";
			// 删除信用卡url
			if (flag == "card") {
				url = ctx + "/credit/creditReportSimple/deleteCardInfoById";
			} else if (flag == "loan") {
				// 删除贷款url
				url = ctx + "/credit/creditReportSimple/deleteLoanInfoById";
			} else if (flag == "query") {
				// 删除查询信息卡url
				url = ctx + "/credit/creditReportSimple/deleteQueryInfoById";
			} else if (flag == "payback") {
				// 删除保证人代偿信息卡url
				url = ctx + "/credit/creditReportSimple/deletePaybackInfoById";
			}
			
			$.ajax({
				url:url,
				data : {
					id : idVal
				},
				type: "post",
				dataType:'json',
				success:function(data){
					if(data) {
						art.dialog.tips('删除成功!');
					} else {
						art.dialog.tips('删除失败!');
					}
				}
			});
	
		} else {
			return;
		}
	}
	
	// 移除该行
	$("#" + flag + "Table").find("#infoTr" + index).remove();
	// 调整序号
	setNum(flag);
}


// 拼接信用卡信息json数据
function makeParamCardInfo(t) {
	// 信用卡信息
	
	var tr = $(t).parents("tr");
	
	var id = tr.find("input[name='id']"); // 主键
	var relationId = tr.find("input[name='relationId']"); // 外键
	
	var accountStatus = tr.find("select[name='accountStatus']"); // 账户状态
	var currency = tr.find("select[name='currency']"); // 币种
	var isOverdue = tr.find("select[name='isOverdue']"); // 是否发生过逾期
	var issueDay = tr.find("input[name='issueDay']"); // 发放日期
	var abortDay = tr.find("input[name='abortDay']"); // 截至年月
	var limit = tr.find("input[name='limit']"); // 额度
	var usedLimit = tr.find("input[name='usedLimit']"); // 已使用额度
	var overdueAmount = tr.find("input[name='overdueAmount']"); // 逾期金额
	var overdueNo = tr.find("input[name='overdueNo']"); // 最近5年逾期次数
	var overdueForNo = tr.find("input[name='overdueForNo']"); // 最近五年90天以上逾期次数
	var cancellationDay = tr.find("input[name='cancellationDay']"); // 销户年月
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditCardInfoList[" + i + "].id=" + $(id[i]).val();
		param += "&creditCardInfoList[" + i + "].relationId=" + $(relationId[i]).val();
		
		param += "&creditCardInfoList[" + i + "].accountStatus=" + $(accountStatus[i]).val();
		param += "&creditCardInfoList[" + i + "].currency=" + $(currency[i]).val();
		param += "&creditCardInfoList[" + i + "].isOverdue=" + $(isOverdue[i]).val();
		param += "&creditCardInfoList[" + i + "].issueDay=" + $(issueDay[i]).val();
		param += "&creditCardInfoList[" + i + "].abortDay=" + $(abortDay[i]).val();
		param += "&creditCardInfoList[" + i + "].limit=" + $(limit[i]).val();
		param += "&creditCardInfoList[" + i + "].usedLimit=" + $(usedLimit[i]).val();
		param += "&creditCardInfoList[" + i + "].overdueAmount=" + $(overdueAmount[i]).val();
		param += "&creditCardInfoList[" + i + "].overdueNo=" + $(overdueNo[i]).val();
		param += "&creditCardInfoList[" + i + "].overdueForNo=" + $(overdueForNo[i]).val();
		param += "&creditCardInfoList[" + i + "].cancellationDay=" + $(cancellationDay[i]).val();
		
	}
	
	// 关联id
	param += "&id=" + $("#creditReportSimpleId").val();
	
	return param;
}

//拼接保证人代偿信息json数据
function makeParamPaybackInfo() {
	// 保证人代偿信息
	var id = $("#paybackInfoForm").find("input[name='id']"); // 主键
	var relationId = $("#paybackInfoForm").find("input[name='relationId']"); // 外键
	
	var recentlyPaybackTime = $("#paybackInfoForm").find("input[name='recentlyPaybackTime']"); // 发放日期
	var paybackOrg = $("#paybackInfoForm").find("input[name='paybackOrg']"); // 额度
	var totalPaybackAmount = $("#paybackInfoForm").find("input[name='totalPaybackAmount']"); // 已使用额度
	var lastPaybackDate = $("#paybackInfoForm").find("input[name='lastPaybackDate']"); // 逾期金额
	var residualAmount = $("#paybackInfoForm").find("input[name='residualAmount']"); // 最近5年逾期次数
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditPaybackInfoList[" + i + "].id=" + $(id[i]).val();
		param += "&creditPaybackInfoList[" + i + "].relationId=" + $(relationId[i]).val();
		
		param += "&creditPaybackInfoList[" + i + "].recentlyPaybackTime=" + $(recentlyPaybackTime[i]).val();
		param += "&creditPaybackInfoList[" + i + "].paybackOrg=" + $(paybackOrg[i]).val();
		param += "&creditPaybackInfoList[" + i + "].totalPaybackAmount=" + $(totalPaybackAmount[i]).val();
		param += "&creditPaybackInfoList[" + i + "].lastPaybackDate=" + $(lastPaybackDate[i]).val();
		param += "&creditPaybackInfoList[" + i + "].residualAmount=" + $(residualAmount[i]).val();
		
	}
	// 关联id
	param += "&id=" + $("#creditReportSimpleId").val();
	
	return param;
}

//拼接信用卡信息json数据
function makeParamLoanInfo(t) {
	
	var tr = $(t).parents("tr");
	
	// 信用卡信息
	var id = tr.find("input[name='id']"); // 主键
	var relationId = tr.find("input[name='relationId']"); // 外键
	
	var accountStatus = tr.find("select[name='accountStatus']"); // 账户状态
	var currency = tr.find("select[name='currency']"); // 贷款种类
	var isOverdue = tr.find("select[name='isOverdue']"); // 是否发生过逾期
	var issueDay = tr.find("input[name='issueDay']"); // 发放日期
	var abortDay = tr.find("input[name='abortDay']"); // 到期日期
	var actualDay = tr.find("input[name='actualDay']"); // 截至年月
	var conteactAmount = tr.find("input[name='conteactAmount']"); // 贷款合同金额
	var loanBalance = tr.find("input[name='loanBalance']"); // 贷款余额
	var overdueAmount = tr.find("input[name='overdueAmount']"); // 逾期金额
	var overdueNo = tr.find("input[name='overdueNo']"); // 最近5年逾期次数
	var overdueForNo = tr.find("input[name='overdueForNo']"); // 最近五年90天以上逾期次数
	var settleDay = tr.find("input[name='settleDay']"); // 结清年月
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditLoanInfoList[" + i + "].id=" + $(id[i]).val();
		param += "&creditLoanInfoList[" + i + "].relationId=" + $(relationId[i]).val();
		
		param += "&creditLoanInfoList[" + i + "].accountStatus=" + $(accountStatus[i]).val();
		param += "&creditLoanInfoList[" + i + "].currency=" + $(currency[i]).val();
		param += "&creditLoanInfoList[" + i + "].isOverdue=" + $(isOverdue[i]).val();
		param += "&creditLoanInfoList[" + i + "].issueDay=" + $(issueDay[i]).val();
		param += "&creditLoanInfoList[" + i + "].abortDay=" + $(abortDay[i]).val();
		param += "&creditLoanInfoList[" + i + "].actualDay=" + $(actualDay[i]).val();
		param += "&creditLoanInfoList[" + i + "].conteactAmount=" + $(conteactAmount[i]).val();
		param += "&creditLoanInfoList[" + i + "].loanBalance=" + $(loanBalance[i]).val();
		param += "&creditLoanInfoList[" + i + "].overdueAmount=" + $(overdueAmount[i]).val();
		param += "&creditLoanInfoList[" + i + "].overdueNo=" + $(overdueNo[i]).val();
		param += "&creditLoanInfoList[" + i + "].overdueForNo=" + $(overdueForNo[i]).val();
		param += "&creditLoanInfoList[" + i + "].settleDay=" + $(settleDay[i]).val();
	}
	// 关联id
	param += "&id=" + $("#creditReportSimpleId").val();
	
	return param;
}

//拼接信用卡信息json数据
function makeParamQueryInfo() {
	// 信用卡信息
	var id = $("#queryInfoForm").find("input[name='id']"); // 主键
	var relationId = $("#queryInfoForm").find("input[name='relationId']"); // 外键
	
	var queryDay = $("#queryInfoForm").find("input[name='queryDay']"); // 查询日期
	var queryType = $("#queryInfoForm").find("select[name='queryType']"); // 查询原因
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditQueryRecordList[" + i + "].id=" + $(id[i]).val();
		param += "&creditQueryRecordList[" + i + "].relationId=" + $(relationId[i]).val();
		
		param += "&creditQueryRecordList[" + i + "].queryDay=" + $(queryDay[i]).val();
		param += "&creditQueryRecordList[" + i + "].queryType=" + $(queryType[i]).val();
	}
	// 关联id
	param += "&id=" + $("#creditReportSimpleId").val();
	
	return param;
}

// 保存信息
function saveTr(flag,t){
	
	/*//验证表单
	if (!$("#creditReportSimpleForm").validate().form()) {
		return;
	}*/
	var saveQuery = false;
	var ss = /^[\u4E00-\u9FA5\(\)a-zA-Z·]{0,}$/;
	var name = $("#name").val();// 姓名
	if(!ss.test(name)){
		art.dialog.tips('请正确输入姓名');
		return;
	}
	if(!checkForm($("#creditReportSimpleForm"))){
		art.dialog.tips('请正确填写基本信息');
		return;
	}else{
		if($("#saveLock").val() != '1'){
			$("#saveLock").val("1");
		
			$.ajax({
				url : ctx + "/credit/creditReportSimple/saveQueryTime",
				async : false,
				type : "POST",
				data : {
					id : $("#creditReportSimpleId").val(),
					queryTime : $("#queryTime").val(),
					name:$("#name").val(),
					certNo:$("#certNo").val(),
					marryStatus:$("select[name='marryStatus']").val(),
					highestEducation:$("select[name='highestEducation']").val(),
					loanCode:$("#loanCode").val(),
					rCustomerCoborrowerId:$("#rId").val(),
					dictCustomerType:$("#dictCustomerType").val()
				},
				dataType:'json',
				success:function(data){
					if(data){
						//art.dialog.tips('保存成功');
						$("#saveLock").val("0");
					}
				}
			});
		}
	}
	
	
	//验证表单
	if (!checkForm($("#" + flag + "InfoForm"))) {
		
		return;
	}
	
	var today = new Date();
	var queryDay = new Date($("input[name='queryDay']").val().replace(/-/g,"/"));
	if(today - queryDay < 0) {
		art.dialog.tips('查询日期不能晚于今天!');
		return;
	}
	if (flag == "card") {
		if (!checkDate($("#cardInfoForm"),$("#cardInfoForm"),
				"issueDay","abortDay",
				"截至年月要晚于发放日期")) {
			return;
		}
		if (!checkDate($("#cardInfoForm"),$("#cardInfoForm"),
				"issueDay","cancellationDay",
				"销户年月要晚于发放日期")) {
			return;
		}					
		if (!checkDataBig($("#cardInfoForm"),$("#cardInfoForm"),
				"limitName","overdueAmountName",
				"额度需大于等于逾期金额")) {
			return;
		}
		if (!checkDataBig($("#cardInfoForm"),$("#cardInfoForm"),
				"overdueNo","overdueForNo",
				"五年内逾期次数需大于等于五年内90天以上逾期次数")) {
			return;
		}
	}
	if (flag == "loan") {
		if (!checkDate($("#loanInfoForm"),$("#loanInfoForm"),
				"issueDay","abortDay",
				"到期日期要晚于发放日期")) {
			return;
		}
		if (!checkDate($("#loanInfoForm"),$("#loanInfoForm"),
				"issueDay","actualDay",
				"截至年月要晚于发放日期")) {
			return;
		}
		if (!checkDate($("#loanInfoForm"),$("#loanInfoForm"),
				"issueDay","settleDay",
				"结清年月要晚于发放日期")) {
			return;
		}				
		if (!checkDataBig($("#loanInfoForm"),$("#loanInfoForm"),
				"conteactAmountName","loanBalanceName",
				"贷款合同金额需大于等于贷款余额")) {
			return;
		}
		if (!checkDataBig($("#loanInfoForm"),$("#loanInfoForm"),
				"conteactAmountName","overdueAmountName",
				"贷款合同金额需大于等于逾期金额")) {
			return;
		}
		if (!checkDataBig($("#loanInfoForm"),$("#loanInfoForm"),
				"overdueNo","overdueForNo",
				"五年内逾期次数需大于等于五年内90天以上逾期次数")) {
			return;
		}
		
	}
	
	var data = "";
	var url = "";
	// 拼接信用卡json
	if (flag == "card") {
		data = encodeURI(makeParamCardInfo(t));
		url = ctx + "/credit/creditReportSimple/saveCardInfo";
	} else if (flag == "loan") {
		// 拼接贷款json
		data = encodeURI(makeParamLoanInfo(t));
		url = ctx + "/credit/creditReportSimple/saveLoanInfo";		
	} else if (flag == "query") {
		// 拼接查询信息卡json
		data = encodeURI(makeParamQueryInfo());
		url = ctx + "/credit/creditReportSimple/saveQueryInfo";
	} else if (flag == "payback") {
		// 拼接保证人代偿信息json
		data = encodeURI(makeParamPaybackInfo());
		url = ctx + "/credit/creditReportSimple/savePaybackInfo";
	}
	// 简版贷记卡负债信息
	$.ajax({
		url: url,
		data: data,
		type: "post",
		dataType: 'json',
		async : false,
		success:function(data){
			if(data != null) {
				art.dialog.tips('保存成功!');
				// 刷新信用卡div
				if (flag == "card") {
					$(t).parents("tr").find("input[name='id']").val(data.id);
				} else if (flag == "loan") {
					$(t).parents("tr").find("input[name='id']").val(data.id);
				} else if (flag == "query") {
					// 刷新查询信息卡div
					$("#queryInfoTab").load(ctx+"/credit/creditReportSimple/initQueryInfo",{"id":$("#creditReportSimpleId").val()},function(){});
				} else if (flag == "payback") {
					// 刷新保证人代偿信息div
					$("#paybackInfoTab").load(ctx+"/credit/creditReportSimple/initCreditPaybackInfo",{"id":$("#creditReportSimpleId").val()},function(){});
				}
				
			} else {
				art.dialog.tips('保存失败!');
			}
		}
	});
}

//保存查询日期
function saveQueryTime() {

	//验证表单
	/*if (!$("#creditReportSimpleForm").validate().form()) {
		return;
	}*/
	
	if(!checkForm($("#creditReportSimpleForm"))){
		return;
	}
	
	var ss = /^[\u4E00-\u9FA5\(\)a-zA-Z·]{0,}$/;
	var name = $("#name").val();// 姓名
	if(!ss.test(name)){
		art.dialog.tips('请正确输入姓名');
		return;
	}
	
	var today = new Date();
	var queryDay = new Date($("#queryTime").val().replace(/-/g,"/"));
	if(today - queryDay < 0) {
		art.dialog.tips('查询日期不能晚于今天!');
		return;
	}
	if($("#saveLock").val() != '1'){
		$("#saveLock").val("1");
		$.ajax({
			url : ctx + "/credit/creditReportSimple/saveQueryTime",
			async : false,
			type : "POST",
			data : {
				id : $("#creditReportSimpleId").val(),
				queryTime : $("#queryTime").val(),
				name:$("#name").val(),
				certNo:$("#certNo").val(),
				marryStatus:$("select[name='marryStatus']").val(),
				highestEducation:$("select[name='highestEducation']").val(),
				loanCode:$("#loanCode").val(),
				rCustomerCoborrowerId:$("#rId").val(),
				dictCustomerType:$("#dictCustomerType").val()
			},
			dataType:'json',
			success:function(data){
				if(data){
					art.dialog.tips('保存成功');
				}else{
					art.dialog.tips('保存失败');
				}
				$("#saveLock").val("0");
			}
		});
	}
} 

//跳转到网络征信登录界面
function initEnterpriseCreditWebLoad(){
	
	var param = "id="+$("#creditReportSimpleId").val()+"&loanCode="+$("#loanCode").val()
	+"&rCustomerCoborrowerId="+$("#rId").val()+"&dictCustomerType="+$("#dictCustomerType").val()
	+"&applyCertNum="+$("#applyCertNum").val();
	
	window.location.href = ctx + "/credit/creditReportSimple/initWeb?"+param;
}


//跳转到静态网页
function initHtmlUrl(){
	OpenWindow = window.open("initHtmlUrl?id=" + $("#creditReportSimpleId").val(), "height=400,toolbar=no, width=600,top=200, left=300, scrollbars=yes, menubar=no, status=no, titlebar=no, toolbar=no, location=no");

}

//对比日期前后
function checkDate(t1,t2,name1,name2,msg){
  flag =true;
  var pre=$(t1).find("input[name='"+name1+"']");
  var next=$(t2).find("input[name='"+name2+"']");

  for(var i = 0; i < pre.length; i++ ){

  var p = $(pre[i]);
  var n = $(next[i]);
  
  if(p.val()!="" && n.val()!=""){
	  var begin = getThisDate(p.val()); 
	  var end = getThisDate(n.val()); 
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

function getThisDate(str){
	var dat;
	if(str.length == 10){
		dat = new Date(str.replace(/-/g,"/"));
	} else if(str.length == 7) {		
		dat = new Date(str.substring(0,4), parseInt(str.substring(5,7)) , 0);
	}
	return dat;
}

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

function formatMoneyEx(money){
	var tpMoney=money;
	if(money!=undefined && money!=null){
		 tpMoney = money.toString().replace(/\,/g, '');
	}
	return tpMoney;
}

function clearOverDueCount(obj) {
	var isOverdue = $(obj).val();
	if (isOverdue == "2") {
		// 逾期金额等设置为0
		setOverDueZero(obj);
		// 逾期金额等不能输入
		$(obj).parents("tr").find("input[name='overdueAmountName']").attr("readonly","readonly");
		$(obj).parents("tr").find("input[name='overdueNo']").attr("readonly","readonly");
		$(obj).parents("tr").find("input[name='overdueForNo']").attr("readonly","readonly");
	} else {
		// 逾期金额等可以输入
		$(obj).parents("tr").find("input[name='overdueAmountName']").removeAttr("readonly");
		$(obj).parents("tr").find("input[name='overdueNo']").removeAttr("readonly");
		$(obj).parents("tr").find("input[name='overdueForNo']").removeAttr("readonly");
	}
}

// 信用卡账户状态变化
function changeCardStatus(t) {
	// 信用卡明细信息中的【销户年月】在账户状态选择销户时为必填
	if($(t).val() == "2"){
		$(t).parents("tr").find("input[name='cancellationDay']").attr("class","select75 Wdate required");
	}else{
		$(t).parents("tr").find("input[name='cancellationDay']").attr("class","select75 Wdate ");
	}
	// 账户状态选择未激活，逾期输入项与已使用额度置为0
	if($(t).val() == "3") {
		setOverDueZero(t);
		$(t).parents("tr").find("input[name='usedLimitName']").val('0');
		$(t).parents("tr").find("input[name='usedLimit']").val('0');		
	} 
}

// 贷款账户状态变化
function changeLoanStatus(t) {
	var tr = $(t).parents("tr");
	// 贷款明细信息中
	if($(t).val() == "1") {
		// 【结清年月】在账户状态为结清是为必填
		if (!tr.find("input[name='settleDay']").hasClass("required")) {
			tr.find("input[name='settleDay']").addClass("required");
		}
		// 【到期日期、截至日期】在账户状态为结清时，非必填
		removeDateRequired(t);
		
		// 贷款账户状态选择结清，贷款余额、逾期输入项置为0
		tr.find("input[name='loanBalanceName']").val('0');
		tr.find("input[name='loanBalance']").val('0');	
		setOverDueZero(t);
	}else{
		tr.find("input[name='settleDay']").removeClass("required");
		
		if (!tr.find("input[name='abortDay']").hasClass("required")) {
			tr.find("input[name='abortDay']").addClass("required");
		}
		if (!tr.find("input[name='actualDay']").hasClass("required")) {
			tr.find("input[name='actualDay']").addClass("required");
		}		
	}
}

// 将逾期信息更新为0
function setOverDueZero(t){
	$(t).parents("tr").find("input[name='overdueAmountName']").val('0');
	$(t).parents("tr").find("input[name='overdueAmount']").val('0');
	$(t).parents("tr").find("input[name='overdueNo']").val('0');
	$(t).parents("tr").find("input[name='overdueForNo']").val('0');
}

/**
 * 如果贷款账户态是结清，移除到期日期、截至日期等的必填校验 * 
 * */
function removeDateRequired(t) {
	if($(t).val() == "1") {
		$(t).parents("tr").find("input[name='abortDay']").removeClass("required");
		$(t).parents("tr").find("input[name='actualDay']").removeClass("required");
	}		
}
