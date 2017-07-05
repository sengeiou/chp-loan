$(function(){
	$("select[str='provinceTD']").change(function(){
		var provinceId = $(this).find("option:selected").val();
		var element = $(this).parent("td").next("td").find("select");
		if( provinceId == "" ){
			element.empty();
			element.append("<option value='' selected=true>请选择</option>");
			element.trigger("change");
		}else{
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadCity",
				data: {provinceId: provinceId},
				async: false,
				success : function(data) {
					var resultObj = eval("("+data+")");
					element.empty();
					element.append("<option value=''>请选择</option>");
					var message ="";
					$.each(resultObj,function(i,item){
						message += "<option value="+item.areaCode+">"+item.areaName+"</option>";
					});
					element.append(message);
					element.trigger("change");
					element.attr("disabled", false);
				}
			});
		}
	});
	
	$("select[str='cityTD']").change(function(){
		var cityId = $(this).find("option:selected").val();
		var element = $(this).parent("td").next("td").find("select");
		if( cityId == "" ){
			element.empty();
			element.append("<option value='' selected=true>请选择</option>");
			element.trigger("change");
		}else{
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadDistrict",
				data: {cityId: cityId},
				async: false,
				success : function(data) {
					var resultObj = eval("("+data+")");
					element.empty();
					element.append("<option value=''>请选择</option>");
					var message ="";
					$.each(resultObj,function(i,item){
						message += "<option value="+item.areaCode+">"+item.areaName+"</option>";
					});
					element.append(message);
					element.trigger("change");
					element.attr("disabled", false);
				}
			});
		}
	});
	
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
	
	//初始化省、市、区		
	$("#creditBasicInfoForm").find("select[name='province']").select2("val", $("#provinceHid").val());// 户籍省 								
	$("#creditBasicInfoForm").find("select[name='province']").trigger("change");
	$("#creditBasicInfoForm").find("select[name='city']").select2("val", $("#cityHid").val());// 户籍市								
	$("#creditBasicInfoForm").find("select[name='city']").trigger("change");
	$("#creditBasicInfoForm").find("select[name='area']").select2("val", $("#areaHid").val());// 户籍区								
	
});

//格式化金额
function formatMoney(flag, index, name){
	
	//显示字段
	var inputName = $("#" + flag + "Form").find("input[name='" + name + "Name']");
	//隐藏字段
	var input = $("#" + flag + "Form").find("input[name='" + name + "']");
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
	
	if (flag == 'creditUnclearedBankAcceptance' && name != 'totalBalance') {
		totalMoney(flag, index);
	}
	
}

//金额合计
function totalMoney(flag, index){
	
	var actual30dayBalance = $("#" + flag + "Form").find("input[name='actual30dayBalance']");
	var actual30dayBalanceVal = $(actual30dayBalance[index]).val();
	//alert(actual30dayBalanceVal);
	var actual60dayBalance = $("#" + flag + "Form").find("input[name='actual60dayBalance']");
	var actual60dayBalanceVal = $(actual60dayBalance[index]).val();
	//alert(actual60dayBalanceVal);
	var actual90dayBalance = $("#" + flag + "Form").find("input[name='actual90dayBalance']");
	var actual90dayBalanceVal = $(actual90dayBalance[index]).val();
	//alert(actual90dayBalanceVal);
	var actual91dayBalance = $("#" + flag + "Form").find("input[name='actual91dayBalance']");
	var actual91dayBalanceVal = $(actual91dayBalance[index]).val();
	//alert(actual91dayBalanceVal);
	total = Number(actual30dayBalanceVal) + Number(actual60dayBalanceVal) + Number(actual90dayBalanceVal) + Number(actual91dayBalanceVal);
	
	var totalBalanceName = $("#" + flag + "Form").find("input[name='totalBalanceName']");
	$(totalBalanceName[index]).val(total);
	
	//alert($(totalBalanceName[index]).val());
	formatMoney(flag, index, 'totalBalance');
	
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
	
	var allInfoNum = $("#" + flag + "Table").find("tbody").find("input[name='id']");
	allInfoNum.each(function(i,item){
		$(this).parent("tr").attr("id","infoTr" + (i + 1));
		$(this).parent("tr").find("td a[name='aRemoveInfo']").attr("onClick","removeTr('" + flag + "', '" + (i + 1)+"')");
		//出资人信息 出资金额
		if (flag == "creditInvestorInfo") {
			$(this).parent("tr").find("td input[name='contributionAmountName']").attr("onblur","formatMoney('creditInvestorInfo', '" + i + "', 'contributionAmount')");
		
		} 
		//企业征信_负债历史变化
		if (flag == "creditLiabilityHis") {
			//全部负债余额
			$(this).parent("tr").find("td input[name='allBalanceName']").attr("onblur","formatMoney('creditLiabilityHis', '" + i + "', 'allBalance')");
			//不良负债余额
			$(this).parent("tr").find("td input[name='badnessBalanceName']").attr("onblur","formatMoney('creditLiabilityHis', '" + i + "', 'badnessBalance')");
				
		}
		
		//未结清业务:不良、关注类-贷款信息
		if  (flag.indexOf("unclearLoan")!=-1) {
			$(this).parent("tr").find("td input[name='businessAmountName']").attr("onblur","formatMoney('"+flag+"', '" + i + "', 'businessAmount')");
			$(this).parent("tr").find("td input[name='businessBalanceName']").attr("onblur","formatMoney('"+flag+"', '" + i + "', 'businessBalance')");
		}
		
		//未结清业务:正常 贷款
		if (flag == "creditUnclearedLoan") {
			//借据金额
			$(this).parent("tr").find("td input[name='iousAmountName']").attr("onblur","formatMoney('creditUnclearedLoan', '" + i + "', 'iousAmount')");
			//借据余额
			$(this).parent("tr").find("td input[name='iousBalanceName']").attr("onblur","formatMoney('creditUnclearedLoan', '" + i + "', 'iousBalance')");
		}
		
		//未结清业务:正常 贸易融资
		if (flag == "creditUnclearedTradeFinancing") {
			//借据金额
			$(this).parent("tr").find("td input[name='financingAmountName']").attr("onblur","formatMoney('creditUnclearedTradeFinancingForm', '" + i + "', 'financingAmount')");
			//借据余额
			$(this).parent("tr").find("td input[name='financingBalanceName']").attr("onblur","formatMoney('creditUnclearedTradeFinancingForm', '" + i + "', 'financingBalance')");
		}
		
		//未结清业务:正常 保理
		if (flag == "creditUnclearedFactoring") {
			//借据金额
			$(this).parent("tr").find("td input[name='factoringAmountName']").attr("onblur","formatMoney('creditUnclearedFactoring', '" + i + "', 'factoringAmount')");
			//借据余额
			$(this).parent("tr").find("td input[name='factoringBalanceName']").attr("onblur","formatMoney('creditUnclearedFactoring', '" + i + "', 'factoringBalance')");
		}
		
		//未结清业务:正常 票据贴现
		if (flag == "creditUnclearedNotesDiscounted") {
			//借据金额
			$(this).parent("tr").find("td input[name='balanceName']").attr("onblur","formatMoney('creditUnclearedNotesDiscounted', '" + i + "', 'balance')");
		}
		
		//未结清业务:正常 银行承兑汇票
		if (flag == "creditUnclearedBankAcceptance") {
			//到期日 <30
			$(this).parent("tr").find("td input[name='actual30dayBalanceName']").attr("onblur","formatMoney('creditUnclearedBankAcceptance', '" + i + "', 'actual30dayBalance')");
			//到期日 <60
			$(this).parent("tr").find("td input[name='actual60dayBalanceName']").attr("onblur","formatMoney('creditUnclearedBankAcceptance', '" + i + "', 'actual60dayBalance')");
			//到期日 ≤90
			$(this).parent("tr").find("td input[name='actual90dayBalanceName']").attr("onblur","formatMoney('creditUnclearedBankAcceptance', '" + i + "', 'actual90dayBalance')");
			//到期日 >90
			$(this).parent("tr").find("td input[name='actual91dayBalanceName']").attr("onblur","formatMoney('creditUnclearedBankAcceptance', '" + i + "', 'actual91dayBalance')");
			//合计
			$(this).parent("tr").find("td input[name='totalBalanceName']").attr("onblur","formatMoney('creditUnclearedBankAcceptance', '" + i + "', 'totalBalance')");
										
		}		
		
		//未结清业务:正常 信用证
		if (flag == "creditUnclearedLetterCredit") {
			//金额
			$(this).parent("tr").find("td input[name='balanceName']").attr("onblur","formatMoney('creditUnclearedLetterCredit', '" + i + "', 'balance')");
			//余额
			$(this).parent("tr").find("td input[name='amountName']").attr("onblur","formatMoney('creditUnclearedLetterCredit', '" + i + "', 'amount')");
		}
		
		//未结清业务:正常 保函
		if (flag == "creditUnclearedLetterGuarantee") {
			//金额
			$(this).parent("tr").find("td input[name='balanceName']").attr("onblur","formatMoney('creditUnclearedLetterGuarantee', '" + i + "', 'balance')");
			//余额
			$(this).parent("tr").find("td input[name='amountName']").attr("onblur","formatMoney('creditUnclearedLetterGuarantee', '" + i + "', 'amount')");
		}
		
		//企业征信_已结清贷款
		if (flag.indexOf("paidLoan") != -1) {
			// 业务金额
			$(this).parent("tr").find("td input[name='businessAmountName']").attr("onblur","formatMoney('"+flag+"', '" + i + "', 'businessAmount')");

		}
		
		//企业征信_对外担保记录
		if (flag == "externalGuarantee") {
			// 业务金额
			$(this).parent("tr").find("td input[name='guaranteeAmountName']").attr("onblur","formatMoney('"+flag+"', '" + i + "', 'guaranteeAmount')");

		}
		
		//企业征信_公共记录明细民事判决记录
		if (flag == "civilJudgment") {
			// 业务金额
			$(this).parent("tr").find("td input[name='objectLitigationAmountName']").attr("onblur","formatMoney('"+flag+"', '" + i + "', 'objectLitigationAmount')");

		}
		
		//企业征信_处罚
		if (flag == "creditPunish") {
			// 业务金额
			$(this).parent("tr").find("td input[name='amountName']").attr("onblur","formatMoney('"+flag+"', '" + i + "', 'amount')");

		}
		
	});
}

// 删除信息
function removeTr(flag, index){
	// 数据库删除
	var id = $("#" + flag + "Form").find("input[name='id']"); // 主键
	var i = index - 1;
	var idVal = $(id[i]).val();
	// 后台数据库删除
	if (idVal != "") {
		  
		if (confirm("确认要删除吗？")) {
			var url = "";
			// 删除信用卡url
			if (flag == "creditInvestorInfo") {
				url = ctx + "/credit/enterpriseCredit/deleteCreditInvestorInfoById";
			} else if (flag == "creditExecutiveInfo") {
				//删除高管人员信息
				url = ctx + "/credit/enterpriseCredit/deleteCreditExecutiveInfoById";
			} else if (flag == "creditPunish") {
				// 删除处罚信息
				url = ctx + "/credit/enterpriseCredit/deletePunishById";
			} else if (flag == "creditGrade") {
				// 删除评级信息
				url = ctx + "/credit/enterpriseCredit/deleteGradeById";
			} else if (flag == "creditLoanCard") {
				// 删除贷款卡信息
				url = ctx + "/credit/enterpriseCredit/deleteLoanCardById";
			} else if (flag == "civilJudgment") {
				// 删除民事判决信息
				url = ctx + "/credit/enterpriseCredit/deleteJudgmentById";
			} else if (flag == "externalGuarantee") {
				// 删除外部担保信息
				url = ctx + "/credit/enterpriseCredit/deleteGuaranteeById";
			} else if (flag.indexOf("paidLoan") != -1) {
				// 删除已结清负债明细
				url = ctx + "/credit/enterpriseCredit/deletePaidLoanById";
			} else if (flag == "creditAffiliatedEnterprise") {
				//删除直接关联企业
				url = ctx + "/credit/enterpriseCredit/deleteCreditAffiliatedEnterpriseById";
			} else if (flag == "creditLiabilityHis") {
				//负债历史变化
				url = ctx + "/credit/enterpriseCredit/deleteCreditLiabilityHisById";
			} else if (flag == "creditUnclearedLoan") {
				//企业征信_未结清贷款
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedLoanById";
			} else if (flag == "creditUnclearedTradeFinancing") {
				//企业征信_未结清贸易融资
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedTradeFinancingById";
			} else if (flag == "creditUnclearedFactoring") {
				//企业征信_未结清保理
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedFactoringById";
			} else if (flag == "creditUnclearedNotesDiscounted") {
				//企业征信_未结清保理
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedNotesDiscountedById";
			} else if (flag == "creditUnclearedBankAcceptance") {
				//企业征信_未结清银行承兑汇票
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedBankAcceptanceById";
			} else if (flag == "creditUnclearedLetterCredit") {
				//企业征信_未结清信用证
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedLetterCreditById";
			} else if (flag == "creditUnclearedLetterGuarantee") {
				//企业征信_未结清保函
				url = ctx + "/credit/enterpriseCredit/deleteCreditUnclearedLetterGuaranteeById";
			}
			else if (flag.indexOf("unclearLoan") != -1) {
				// 删除未结清业务:不良、关注类-贷款信息
				url = ctx + "/credit/enterpriseCredit/deleteUnclearedImproperLoanById";
			}
			
			
			$.ajax({
				url:url,
				data: { id : idVal },
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

//拼接出资人信息json数据
function makeParamCreditInvestorInfo() {
	// 出资人信息
	var id = $("#creditInvestorInfoForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditInvestorInfoForm").find("input[name='loanCode']"); // 外键
	
	var contributionAmount = $("#creditInvestorInfoForm").find("input[name='contributionAmount']"); // 出资金额
	var investorName = $("#creditInvestorInfoForm").find("input[name='investorName']"); // 出资方名称
	var dictCertType = $("#creditInvestorInfoForm").find("select[name='dictCertType']"); // 证件类型
	var customerCertNum = $("#creditInvestorInfoForm").find("input[name='customerCertNum']"); // 证件号码
	var dictCurrency = $("#creditInvestorInfoForm").find("select[name='dictCurrency']"); // 币种
	var contributionProportion = $("#creditInvestorInfoForm").find("input[name='contributionProportion']"); // 出资占比

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditInvestorInfoList[" + i + "].id=" + $(id[i]).val();
		param += "&creditInvestorInfoList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditInvestorInfoList[" + i + "].contributionAmount=" + $(contributionAmount[i]).val();
		param += "&creditInvestorInfoList[" + i + "].investorName=" + $(investorName[i]).val();
		param += "&creditInvestorInfoList[" + i + "].dictCertType=" + $(dictCertType[i]).val();
		param += "&creditInvestorInfoList[" + i + "].customerCertNum=" + $(customerCertNum[i]).val();		
		param += "&creditInvestorInfoList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&creditInvestorInfoList[" + i + "].contributionProportion=" + $(contributionProportion[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//拼接处罚信息json数据
function makeCreditPunishParam() {
	// 企业征信-处罚信息
	var id = $("#creditPunishForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditPunishForm").find("input[name='loanCode']"); // 外键
	
	var gradeTime = $("#creditPunishForm").find("input[name='gradeTime']"); // 处罚时间
	var orgName = $("#creditPunishForm").find("input[name='orgName']"); // 机构
	var item = $("#creditPunishForm").find("select[name='item']"); // 项目
	var amount = $("#creditPunishForm").find("input[name='amount']"); // 金额
	var addDay = $("#creditPunishForm").find("input[name='addDay']"); // 添加日期
		
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditPunishList[" + i + "].id=" + $(id[i]).val();
		param += "&creditPunishList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditPunishList[" + i + "].gradeTime=" + $(gradeTime[i]).val();
		param += "&creditPunishList[" + i + "].orgName=" + $(orgName[i]).val();
		param += "&creditPunishList[" + i + "].item=" + $(item[i]).val();
		param += "&creditPunishList[" + i + "].amount=" + $(amount[i]).val();
		param += "&creditPunishList[" + i + "].addDay=" + $(addDay[i]).val();		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
}

// 评级信息
function makeCreditGradeParam() {
	// 企业征信-评级信息
	var id = $("#creditGradeForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditGradeForm").find("input[name='loanCode']"); // 外键
	
	var gradeTime = $("#creditGradeForm").find("input[name='gradeTime']"); // 评级时间
	var dictOrgName = $("#creditGradeForm").find("select[name='dictOrgName']"); // 机构
	var dictRank = $("#creditGradeForm").find("select[name='dictRank']"); // 等级
	var addDay = $("#creditGradeForm").find("input[name='addDay']"); // 添加日期
		
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditGradeList[" + i + "].id=" + $(id[i]).val();
		param += "&creditGradeList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditGradeList[" + i + "].gradeTime=" + $(gradeTime[i]).val();
		param += "&creditGradeList[" + i + "].dictOrgName=" + $(dictOrgName[i]).val();
		param += "&creditGradeList[" + i + "].dictRank=" + $(dictRank[i]).val();
		param += "&creditGradeList[" + i + "].addDay=" + $(addDay[i]).val();		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
}

//拼接贷款卡信息
function makeCreditLoanCardParam() {
	// 企业征信-贷款卡信息
	var id = $("#creditLoanCardForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditLoanCardForm").find("input[name='loanCode']"); // 外键
	
	var throughYear = $("#creditLoanCardForm").find("input[name='throughYear']"); // 通过年份
	var orgName = $("#creditLoanCardForm").find("input[name='orgName']"); // 机构
	var addDay = $("#creditLoanCardForm").find("input[name='addDay']"); // 添加日期
		
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditLoanCardList[" + i + "].id=" + $(id[i]).val();
		param += "&creditLoanCardList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditLoanCardList[" + i + "].throughYear=" + $(throughYear[i]).val();
		param += "&creditLoanCardList[" + i + "].orgName=" + $(orgName[i]).val();
		param += "&creditLoanCardList[" + i + "].addDay=" + $(addDay[i]).val();		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
}

//拼接民事判决信息
function makeCivilJudgmentParam() {
	// 企业征信-诉讼信息
	var id = $("#civilJudgmentForm").find("input[name='id']"); // 主键
	var loanCode = $("#civilJudgmentForm").find("input[name='loanCode']"); // 外键
	
	var filingCourt = $("#civilJudgmentForm").find("input[name='filingCourt']"); //
	var matterCase = $("#civilJudgmentForm").find("input[name='matterCase']"); // 
	var caseReference = $("#civilJudgmentForm").find("input[name='caseReference']"); // 
	var objectLitigation = $("#civilJudgmentForm").find("input[name='objectLitigation']"); // 
	var dictClosedManner = $("#civilJudgmentForm").find("select[name='dictClosedManner']"); // 
	var filingDay = $("#civilJudgmentForm").find("input[name='filingDay']"); // 
	var dictLawsuitPosition = $("#civilJudgmentForm").find("select[name='dictLawsuitPosition']"); // 
	var trialProcedure = $("#civilJudgmentForm").find("input[name='trialProcedure']"); // 
	var objectLitigationAmount = $("#civilJudgmentForm").find("input[name='objectLitigationAmount']"); //
	var effectiveDay = $("#civilJudgmentForm").find("input[name='effectiveDay']"); //
	
		
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&civilJudgmentList[" + i + "].id=" + $(id[i]).val();
		param += "&civilJudgmentList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&civilJudgmentList[" + i + "].filingCourt=" + $(filingCourt[i]).val();
		param += "&civilJudgmentList[" + i + "].matterCase=" + $(matterCase[i]).val();
		param += "&civilJudgmentList[" + i + "].caseReference=" + $(caseReference[i]).val();
		param += "&civilJudgmentList[" + i + "].objectLitigation=" + $(objectLitigation[i]).val();
		param += "&civilJudgmentList[" + i + "].dictClosedManner=" + $(dictClosedManner[i]).val();
		param += "&civilJudgmentList[" + i + "].filingDay=" + $(filingDay[i]).val();
		param += "&civilJudgmentList[" + i + "].dictLawsuitPosition=" + $(dictLawsuitPosition[i]).val();
		param += "&civilJudgmentList[" + i + "].trialProcedure=" + $(trialProcedure[i]).val();
		param += "&civilJudgmentList[" + i + "].objectLitigationAmount=" + $(objectLitigationAmount[i]).val();
		param += "&civilJudgmentList[" + i + "].effectiveDay=" + $(effectiveDay[i]).val();
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
}

//拼接对外担保信息
function makeExternalGuaranteeParam() {
	// 企业征信-对外担保信息
	var id = $("#externalGuaranteeForm").find("input[name='id']"); // 主键
	var loanCode = $("#externalGuaranteeForm").find("input[name='loanCode']"); // 外键
	
	var dictGuaranteeType = $("#externalGuaranteeForm").find("select[name='dictGuaranteeType']"); //
	var warrantee = $("#externalGuaranteeForm").find("input[name='warrantee']"); // 
	var dictCertType = $("#externalGuaranteeForm").find("select[name='dictCertType']"); // 
	var customerCertNum = $("#externalGuaranteeForm").find("input[name='customerCertNum']"); // 
	var dictCurrency = $("#externalGuaranteeForm").find("select[name='dictCurrency']"); // 
	var guaranteeAmount = $("#externalGuaranteeForm").find("input[name='guaranteeAmount']"); // 
	var dictGuaranteeForm = $("#externalGuaranteeForm").find("select[name='dictGuaranteeForm']"); // 
	
		
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&externalGuaranteeList[" + i + "].id=" + $(id[i]).val();
		param += "&externalGuaranteeList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&externalGuaranteeList[" + i + "].dictGuaranteeType=" + $(dictGuaranteeType[i]).val();
		param += "&externalGuaranteeList[" + i + "].warrantee=" + $(warrantee[i]).val();
		param += "&externalGuaranteeList[" + i + "].dictCertType=" + $(dictCertType[i]).val();
		param += "&externalGuaranteeList[" + i + "].customerCertNum=" + $(customerCertNum[i]).val();
		param += "&externalGuaranteeList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&externalGuaranteeList[" + i + "].guaranteeAmount=" + $(guaranteeAmount[i]).val();
		param += "&externalGuaranteeList[" + i + "].dictGuaranteeForm=" + $(dictGuaranteeForm[i]).val();
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
}

//拼接对外担保信息
function makePaidLoanParam(flag) {
	var form = $("#"+flag+"Form");
	// 企业征信-对外担保信息
	var id = form.find("input[name='id']"); // 主键
	var loanCode = form.find("input[name='loanCode']"); // 外键
	var businessType = form.find("input[name='businessType']"); //
	
	var loanOrg = form.find("input[name='loanOrg']"); //
	var dictCurrency = form.find("select[name='dictCurrency']"); //
	var businessAmount = form.find("input[name='businessAmount']"); //
	var businessDay = form.find("input[name='businessDay']"); //
	var actualDay = form.find("input[name='actualDay']"); //
	var paidDay = form.find("input[name='paidDay']"); //
	var dictRepayMethod = form.find("input[name='dictRepayMethod']"); //
	var dictLevelClass = form.find("select[name='dictLevelClass']"); //
	var dictLoanType = form.find("select[name='dictLoanType']"); //
	var dictGuarantee = form.find("select[name='dictGuarantee']"); //
	var dictExhibition = form.find("select[name='dictExhibition']"); //
	var makeAdvances = form.find("select[name='makeAdvances']"); //
	//保证金比例 
	if(flag == "paidLoanBank" || flag == "paidLoanCredit" || flag == "paidLoanGuarantee") {
		var marginLevel = form.find("input[name='marginLevel']"); //
	}
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&paidLoanList[" + i + "].id=" + $(id[i]).val();
		param += "&paidLoanList[" + i + "].loanCode=" + $(loanCode[i]).val();
		param += "&paidLoanList[" + i + "].businessType=" + $(businessType[i]).val();
		
		param += "&paidLoanList[" + i + "].loanOrg=" + $(loanOrg[i]).val();
		param += "&paidLoanList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&paidLoanList[" + i + "].businessAmount=" + $(businessAmount[i]).val();
		param += "&paidLoanList[" + i + "].businessDay=" + $(businessDay[i]).val();
		param += "&paidLoanList[" + i + "].actualDay=" + $(actualDay[i]).val();
		param += "&paidLoanList[" + i + "].paidDay=" + $(paidDay[i]).val();
		param += "&paidLoanList[" + i + "].dictRepayMethod=" + $(dictRepayMethod[i]).val();
		param += "&paidLoanList[" + i + "].dictLevelClass=" + $(dictLevelClass[i]).val();
		param += "&paidLoanList[" + i + "].dictLoanType=" + $(dictLoanType[i]).val();
		param += "&paidLoanList[" + i + "].dictGuarantee=" + $(dictGuarantee[i]).val();
		param += "&paidLoanList[" + i + "].dictExhibition=" + $(dictExhibition[i]).val();
		param += "&paidLoanList[" + i + "].makeAdvances=" + $(makeAdvances[i]).val();
		//保证金比例 
		if(flag == "paidLoanBank" || flag == "paidLoanCredit" || flag == "paidLoanGuarantee") {
			param += "&paidLoanList[" + i + "].marginLevel=" + $(marginLevel[i]).val();

		}
	}	
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	if( id.length==0 || id.length==undefined){
		param += "&businessType=" + $("#"+flag+"TableHide").find("input[name='businessType']").val();	
	}
	return param;
}

var paidLoanNUmber = 0;
//拼接对外担保信息
function makePaidLoanParamSave(flag, number) {
	
	var form = $("#"+flag+"Form");
	// 企业征信-对外担保信息
	var id = form.find("input[name='id']"); // 主键
	var loanCode = form.find("input[name='loanCode']"); // 外键
	var businessType = form.find("input[name='businessType']"); //
	
	var loanOrg = form.find("input[name='loanOrg']"); //
	var dictCurrency = form.find("select[name='dictCurrency']"); //
	var businessAmount = form.find("input[name='businessAmount']"); //
	var businessDay = form.find("input[name='businessDay']"); //
	var actualDay = form.find("input[name='actualDay']"); //
	var paidDay = form.find("input[name='paidDay']"); //
	var dictRepayMethod = form.find("input[name='dictRepayMethod']"); //
	var dictLevelClass = form.find("select[name='dictLevelClass']"); //
	var dictLoanType = form.find("select[name='dictLoanType']"); //
	var dictGuarantee = form.find("select[name='dictGuarantee']"); //
	var dictExhibition = form.find("select[name='dictExhibition']"); //
	var makeAdvances = form.find("select[name='makeAdvances']"); //
	//保证金比例 
	if(flag == "paidLoanBank" || flag == "paidLoanCredit" || flag == "paidLoanGuarantee") {
		var marginLevel = form.find("input[name='marginLevel']"); //
	}
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		paidLoanNUmber++;
		param += "&paidLoanList[" + paidLoanNUmber + "].id=" + $(id[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].loanCode=" + $(loanCode[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].businessType=" + $(businessType[i]).val();
		
		param += "&paidLoanList[" + paidLoanNUmber + "].loanOrg=" + $(loanOrg[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].businessAmount=" + $(businessAmount[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].businessDay=" + $(businessDay[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].actualDay=" + $(actualDay[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].paidDay=" + $(paidDay[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].dictRepayMethod=" + $(dictRepayMethod[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].dictLevelClass=" + $(dictLevelClass[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].dictLoanType=" + $(dictLoanType[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].dictGuarantee=" + $(dictGuarantee[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].dictExhibition=" + $(dictExhibition[i]).val();
		param += "&paidLoanList[" + paidLoanNUmber + "].makeAdvances=" + $(makeAdvances[i]).val();
		//保证金比例 
		if(flag == "paidLoanBank" || flag == "paidLoanCredit" || flag == "paidLoanGuarantee") {
			param += "&paidLoanList[" + paidLoanNUmber + "].marginLevel=" + $(marginLevel[i]).val();
		}
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	
	return param;
}


//高管人员信息
function makeParamCreditExecutiveInfo() {
	// 高管人员信息
	var id = $("#creditExecutiveInfoForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditExecutiveInfoForm").find("input[name='loanCode']"); // 外键
	
	var dictCompPost = $("#creditExecutiveInfoForm").find("select[name='dictCompPost']"); // 职务
	var name = $("#creditExecutiveInfoForm").find("input[name='name']"); // 姓名
	var dictCertType = $("#creditExecutiveInfoForm").find("select[name='dictCertType']"); // 证件类型
	var customerCertNum = $("#creditExecutiveInfoForm").find("input[name='customerCertNum']"); // 证件号码

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditExecutiveInfoList[" + i + "].id=" + $(id[i]).val();
		param += "&creditExecutiveInfoList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditExecutiveInfoList[" + i + "].dictCompPost=" + $(dictCompPost[i]).val();
		param += "&creditExecutiveInfoList[" + i + "].name=" + $(name[i]).val();		
		param += "&creditExecutiveInfoList[" + i + "].dictCertType=" + $(dictCertType[i]).val();
		param += "&creditExecutiveInfoList[" + i + "].customerCertNum=" + $(customerCertNum[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	
	return param;
	
}

//直接关联企业
function makeParamCreditAffiliatedEnterprise() {
	// 高管人员信息
	var id = $("#creditAffiliatedEnterpriseForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditAffiliatedEnterpriseForm").find("input[name='loanCode']"); // 外键
	
	var name = $("#creditAffiliatedEnterpriseForm").find("input[name='name']"); // 名称
	var loanCardCode = $("#creditAffiliatedEnterpriseForm").find("input[name='loanCardCode']"); // 贷款卡编号
	var dictRepeatRelation = $("#creditAffiliatedEnterpriseForm").find("select[name='dictRepeatRelation']"); // 关系

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditAffiliatedEnterpriseList[" + i + "].id=" + $(id[i]).val();
		param += "&creditAffiliatedEnterpriseList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditAffiliatedEnterpriseList[" + i + "].name=" + $(name[i]).val();		
		param += "&creditAffiliatedEnterpriseList[" + i + "].loanCardCode=" + $(loanCardCode[i]).val();
		param += "&creditAffiliatedEnterpriseList[" + i + "].dictRepeatRelation=" + $(dictRepeatRelation[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	
	return param;
	
}


//基础信息
function makeParamCreditBasicInfo() {
	// 高管人员信息
	var id = $("#creditBasicInfoForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditBasicInfoForm").find("input[name='loanCode']"); // 外键
	
	var creditName = $("#creditBasicInfoForm").find("input[name='creditName']"); // 名称
	var registrationNumber = $("#creditBasicInfoForm").find("input[name='registrationNumber']"); // 登记注册号
	var registrationType = $("#creditBasicInfoForm").find("input[name='registrationType']"); // 登记注册类型
	var taxRegistrationNumber = $("#creditBasicInfoForm").find("input[name='taxRegistrationNumber']"); // 国税登记号
	var dictLoanCardState = $("#creditBasicInfoForm").find("select[name='dictLoanCardState']"); // 贷款卡状态
	var landTaxRegistrationNumber = $("#creditBasicInfoForm").find("input[name='landTaxRegistrationNumber']"); // 地税登记号
	var registrationDate = $("#creditBasicInfoForm").find("input[name='registrationDate']"); // 登记注册日期
	var expireDate = $("#creditBasicInfoForm").find("input[name='expireDate']"); // 有效截至日期
	var province = $("#creditBasicInfoForm").find("select[name='province']"); // 登记注册类型
	var city = $("#creditBasicInfoForm").find("select[name='city']"); // 登记注册类型
	var area = $("#creditBasicInfoForm").find("select[name='area']"); // 登记注册类型
	var address = $("#creditBasicInfoForm").find("input[name='address']"); // 有效截至日期
	
	var param = "";
	var i = 0;
	param += "&creditBasicInfo.id=" + $(id[i]).val();
	param += "&creditBasicInfo.loanCode=" + $(loanCode[i]).val();
	
	param += "&creditBasicInfo.creditName=" + $(creditName[i]).val();		
	param += "&creditBasicInfo.registrationNumber=" + $(registrationNumber[i]).val();
	param += "&creditBasicInfo.registrationType=" + $(registrationType[i]).val();
	param += "&creditBasicInfo.taxRegistrationNumber=" + $(taxRegistrationNumber[i]).val();		
	param += "&creditBasicInfo.dictLoanCardState=" + $(dictLoanCardState[i]).val();
	param += "&creditBasicInfo.landTaxRegistrationNumber=" + $(landTaxRegistrationNumber[i]).val();	
	param += "&creditBasicInfo.registrationDate=" + $(registrationDate[i]).val();
	param += "&creditBasicInfo.expireDate=" + $(expireDate[i]).val();	
	param += "&creditBasicInfo.province=" + $(province[i]).val();
	param += "&creditBasicInfo.city=" + $(city[i]).val();
	param += "&creditBasicInfo.area=" + $(area[i]).val();
	param += "&creditBasicInfo.address=" + $(address[i]).val();
	return param;
	
}


//企业信息
function makeParamEnterpriseCredit() {
	// 企业信息
	var loanCode = $("#enterpriseCreditForm").find("input[name='loanCode']"); // 主键
	
	var creditVersion = $("#enterpriseCreditForm").find("select[name='creditVersion']"); // 信用报告版本
	var loanCardCode = $("#enterpriseCreditForm").find("input[name='loanCardCode']"); // 贷款卡编号
	var reportDate = $("#enterpriseCreditForm").find("input[name='reportDate']"); // 报告日期
	
	var param = "";
	var i = 0;
	param += "&loanCode=" + $(loanCode[i]).val();
	
	param += "&creditVersion=" + $(creditVersion[i]).val();		
	param += "&loanCardCode=" + $(loanCardCode[i]).val();
	param += "&reportDate=" + $(reportDate[i]).val();
	return param;
	
}

//当前负债信息概要
function makeParamCreditCurrentLiabilityInfo() {
	// 当前负债信息概要
	var id = $("#creditCurrentLiabilityInfoForm").find("input[name='id']"); // 主键
	
	var transactionCount = $("#creditCurrentLiabilityInfoForm").find("input[name='transactionCount']"); // 笔数
	var balance = $("#creditCurrentLiabilityInfoForm").find("input[name='balance']"); // 余额
	var completionDate = $("#creditCurrentLiabilityInfoForm").find("input[name='completionDate']"); // 最近一次处置完成日期

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditCurrentLiabilityInfoList[" + i + "].id=" + $(id[i]).val();
		
		param += "&creditCurrentLiabilityInfoList[" + i + "].transactionCount=" + $(transactionCount[i]).val();		
		param += "&creditCurrentLiabilityInfoList[" + i + "].balance=" + $(balance[i]).val();
		param += "&creditCurrentLiabilityInfoList[" + i + "].completionDate=" + $(completionDate[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//当前负债信息明细
function makeParamCreditCurrentLiabilityDetail() {
	// 当前负债信息明细
	var id = $("#creditCurrentLiabilityDetailForm").find("input[name='id']"); // 主键
	
	var normalTransactionCount = $("#creditCurrentLiabilityDetailForm").find("input[name='normalTransactionCount']"); // 正常笔数
	var normalBalance = $("#creditCurrentLiabilityDetailForm").find("input[name='normalBalance']"); // 正常余额
	var concernTransactionCount = $("#creditCurrentLiabilityDetailForm").find("input[name='concernTransactionCount']"); // 关注笔数
	var concernBalance = $("#creditCurrentLiabilityDetailForm").find("input[name='concernBalance']"); // 关注余额
	var badnessTransactionCount = $("#creditCurrentLiabilityDetailForm").find("input[name='badnessTransactionCount']"); // 不良笔数
	var badnessBalance = $("#creditCurrentLiabilityDetailForm").find("input[name='badnessBalance']"); // 不良余额

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditCurrentLiabilityDetailList[" + i + "].id=" + $(id[i]).val();
		
		param += "&creditCurrentLiabilityDetailList[" + i + "].normalTransactionCount=" + $(normalTransactionCount[i]).val();		
		param += "&creditCurrentLiabilityDetailList[" + i + "].normalBalance=" + $(normalBalance[i]).val();
		param += "&creditCurrentLiabilityDetailList[" + i + "].concernTransactionCount=" + $(concernTransactionCount[i]).val();		
		param += "&creditCurrentLiabilityDetailList[" + i + "].concernBalance=" + $(concernBalance[i]).val();
		param += "&creditCurrentLiabilityDetailList[" + i + "].badnessTransactionCount=" + $(badnessTransactionCount[i]).val();		
		param += "&creditCurrentLiabilityDetailList[" + i + "].badnessBalance=" + $(badnessBalance[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//对外担保信息概要
function makeParamCreditExternalSecurityInfo() {
	// 对外担保信息概要
	var id = $("#creditExternalSecurityInfoForm").find("input[name='id']"); // 主键
	
	var transactionCount = $("#creditExternalSecurityInfoForm").find("input[name='transactionCount']"); // 笔数
	var balance = $("#creditExternalSecurityInfoForm").find("input[name='balance']"); // 担保金额
	var normalBalance = $("#creditExternalSecurityInfoForm").find("input[name='normalBalance']"); // 正常余额
	var concernBalance = $("#creditExternalSecurityInfoForm").find("input[name='concernBalance']"); // 关注余额
	var badnessBalance = $("#creditExternalSecurityInfoForm").find("input[name='badnessBalance']"); // 不良余额

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditExternalSecurityInfoList[" + i + "].id=" + $(id[i]).val();
		
		param += "&creditExternalSecurityInfoList[" + i + "].transactionCount=" + $(transactionCount[i]).val();		
		param += "&creditExternalSecurityInfoList[" + i + "].balance=" + $(balance[i]).val();
		param += "&creditExternalSecurityInfoList[" + i + "].normalBalance=" + $(normalBalance[i]).val();		
		param += "&creditExternalSecurityInfoList[" + i + "].concernBalance=" + $(concernBalance[i]).val();
		param += "&creditExternalSecurityInfoList[" + i + "].badnessBalance=" + $(badnessBalance[i]).val();		

	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_已结清信贷信息
function makeParamCreditCreditClearedInfo() {
	// 企业征信_已结清信贷信息
	var id = $("#creditCreditClearedInfoForm").find("input[name='id']"); // 主键
	
	var transactionCount = $("#creditCreditClearedInfoForm").find("input[name='transactionCount']"); // 笔数
	var balance = $("#creditCreditClearedInfoForm").find("input[name='balance']"); // 担保金额
	var completionDate = $("#creditCreditClearedInfoForm").find("input[name='completionDate']"); // 正常余额

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditCreditClearedInfoList[" + i + "].id=" + $(id[i]).val();
		
		param += "&creditCreditClearedInfoList[" + i + "].transactionCount=" + $(transactionCount[i]).val();		
		param += "&creditCreditClearedInfoList[" + i + "].balance=" + $(balance[i]).val();
		param += "&creditCreditClearedInfoList[" + i + "].completionDate=" + $(completionDate[i]).val();		

	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_已结清信贷明细
function makeParamCreditCreditClearedDetail() {
	// 企业征信_已结清信贷明细
	var id = $("#creditCreditClearedDetailForm").find("input[name='id']"); // 主键
	
	var loan = $("#creditCreditClearedDetailForm").find("input[name='loan']"); // 笔数
	var tradeFinancing = $("#creditCreditClearedDetailForm").find("input[name='tradeFinancing']"); // 担保金额
	var factoring = $("#creditCreditClearedDetailForm").find("input[name='factoring']"); // 正常余额
	var notesDiscounted = $("#creditCreditClearedDetailForm").find("input[name='notesDiscounted']"); // 笔数
	var bankAcceptance = $("#creditCreditClearedDetailForm").find("input[name='bankAcceptance']"); // 担保金额
	var letterCredit = $("#creditCreditClearedDetailForm").find("input[name='letterCredit']"); // 正常余额
	var letterGuarantee = $("#creditCreditClearedDetailForm").find("input[name='letterGuarantee']"); // 正常余额
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditCreditClearedDetailList[" + i + "].id=" + $(id[i]).val();
		
		param += "&creditCreditClearedDetailList[" + i + "].loan=" + $(loan[i]).val();		
		param += "&creditCreditClearedDetailList[" + i + "].tradeFinancing=" + $(tradeFinancing[i]).val();
		param += "&creditCreditClearedDetailList[" + i + "].factoring=" + $(factoring[i]).val();		
		param += "&creditCreditClearedDetailList[" + i + "].notesDiscounted=" + $(notesDiscounted[i]).val();		
		param += "&creditCreditClearedDetailList[" + i + "].bankAcceptance=" + $(bankAcceptance[i]).val();
		param += "&creditCreditClearedDetailList[" + i + "].letterCredit=" + $(letterCredit[i]).val();		
		param += "&creditCreditClearedDetailList[" + i + "].letterGuarantee=" + $(letterGuarantee[i]).val();		
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_负债历史变化
function makeParamCreditLiabilityHis() {
	// 企业征信_负债历史变化
	var id = $("#creditLiabilityHisForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditLiabilityHisForm").find("input[name='loanCode']"); // 外键
	
	var liabilityHisTime = $("#creditLiabilityHisForm").find("input[name='liabilityHisTime']"); // 笔数
	var allBalance = $("#creditLiabilityHisForm").find("input[name='allBalance']"); // 担保金额
	var badnessBalance = $("#creditLiabilityHisForm").find("input[name='badnessBalance']"); // 正常余额

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditLiabilityHisList[" + i + "].id=" + $(id[i]).val();
		param += "&creditLiabilityHisList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditLiabilityHisList[" + i + "].liabilityHisTime=" + $(liabilityHisTime[i]).val();		
		param += "&creditLiabilityHisList[" + i + "].allBalance=" + $(allBalance[i]).val();
		param += "&creditLiabilityHisList[" + i + "].badnessBalance=" + $(badnessBalance[i]).val();		
	
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清贷款
function makeParamCreditUnclearedLoan() {
	
	// 企业征信_未结清贷款
	var id = $("#creditUnclearedLoanForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedLoanForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedLoanForm").find("input[name='loanOrg']"); // 授信机构
	var dictCurrency = $("#creditUnclearedLoanForm").find("select[name='dictCurrency']"); // 币种
	var iousAmount = $("#creditUnclearedLoanForm").find("input[name='iousAmount']"); // 借据金额
	var iousBalance = $("#creditUnclearedLoanForm").find("input[name='iousBalance']"); // 借据余额
	var lendingDay = $("#creditUnclearedLoanForm").find("input[name='lendingDay']"); // 放款日期
	var actualDay = $("#creditUnclearedLoanForm").find("input[name='actualDay']"); // 到期日期
	var dictLoanType = $("#creditUnclearedLoanForm").find("select[name='dictLoanType']"); // 贷款形式
	var dictGuarantee = $("#creditUnclearedLoanForm").find("select[name='dictGuarantee']"); // 担保
	var dictExhibition = $("#creditUnclearedLoanForm").find("select[name='dictExhibition']"); // 展期
	var dictLevelClass = $("#creditUnclearedLoanForm").find("select[name='dictLevelClass']"); // 展期

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedLoanList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedLoanList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedLoanList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedLoanList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&creditUnclearedLoanList[" + i + "].iousAmount=" + $(iousAmount[i]).val();		
		param += "&creditUnclearedLoanList[" + i + "].iousBalance=" + $(iousBalance[i]).val();		
		param += "&creditUnclearedLoanList[" + i + "].lendingDay=" + $(lendingDay[i]).val();
		param += "&creditUnclearedLoanList[" + i + "].actualDay=" + $(actualDay[i]).val();		
		param += "&creditUnclearedLoanList[" + i + "].dictLoanType=" + $(dictLoanType[i]).val();		
		param += "&creditUnclearedLoanList[" + i + "].dictGuarantee=" + $(dictGuarantee[i]).val();
		param += "&creditUnclearedLoanList[" + i + "].dictExhibition=" + $(dictExhibition[i]).val();		
		param += "&creditUnclearedLoanList[" + i + "].dictLevelClass=" + $(dictLevelClass[i]).val();		
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清贸易融资
function makeParamCreditUnclearedTradeFinancing() {
	
	// 企业征信_未结清贸易融资
	var id = $("#creditUnclearedTradeFinancingForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedTradeFinancingForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedTradeFinancingForm").find("input[name='loanOrg']"); // 授信机构
	var dictCurrency = $("#creditUnclearedTradeFinancingForm").find("select[name='dictCurrency']"); // 币种
	var financingAmount = $("#creditUnclearedTradeFinancingForm").find("input[name='financingAmount']"); // 融资金额
	var financingBalance = $("#creditUnclearedTradeFinancingForm").find("input[name='financingBalance']"); // 融资余额
	var lendingDay = $("#creditUnclearedTradeFinancingForm").find("input[name='lendingDay']"); // 放款日期
	var actualDay = $("#creditUnclearedTradeFinancingForm").find("input[name='actualDay']"); // 到期日期
	var dictGuarantee = $("#creditUnclearedTradeFinancingForm").find("select[name='dictGuarantee']"); // 担保
	var dictExhibition = $("#creditUnclearedTradeFinancingForm").find("select[name='dictExhibition']"); // 展期
	var dictLevelClass = $("#creditUnclearedTradeFinancingForm").find("select[name='dictLevelClass']"); // 展期

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedTradeFinancingList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedTradeFinancingList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedTradeFinancingList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedTradeFinancingList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&creditUnclearedTradeFinancingList[" + i + "].financingAmount=" + $(financingAmount[i]).val();		
		param += "&creditUnclearedTradeFinancingList[" + i + "].financingBalance=" + $(financingBalance[i]).val();		
		param += "&creditUnclearedTradeFinancingList[" + i + "].lendingDay=" + $(lendingDay[i]).val();
		param += "&creditUnclearedTradeFinancingList[" + i + "].actualDay=" + $(actualDay[i]).val();		
		param += "&creditUnclearedTradeFinancingList[" + i + "].dictGuarantee=" + $(dictGuarantee[i]).val();
		param += "&creditUnclearedTradeFinancingList[" + i + "].dictExhibition=" + $(dictExhibition[i]).val();		
		param += "&creditUnclearedTradeFinancingList[" + i + "].dictLevelClass=" + $(dictLevelClass[i]).val();		
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清保理
function makeParamCreditUnclearedFactoring() {
	
	// 企业征信_未结清保理
	var id = $("#creditUnclearedFactoringForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedFactoringForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedFactoringForm").find("input[name='loanOrg']"); // 授信机构
	var dictCurrency = $("#creditUnclearedFactoringForm").find("select[name='dictCurrency']"); // 币种
	var factoringAmount = $("#creditUnclearedFactoringForm").find("input[name='factoringAmount']"); // 融资金额
	var factoringBalance = $("#creditUnclearedFactoringForm").find("input[name='factoringBalance']"); // 融资余额
	var factoringDay = $("#creditUnclearedFactoringForm").find("input[name='factoringDay']"); // 到期日期
	var dictGuarantee = $("#creditUnclearedFactoringForm").find("select[name='dictGuarantee']"); // 担保
	var makeAdvances = $("#creditUnclearedFactoringForm").find("select[name='makeAdvances']"); // 展期
	var lendingDay = $("#creditUnclearedFactoringForm").find("input[name='lendingDay']"); // 到期日期
	var dictLevelClass = $("#creditUnclearedFactoringForm").find("select[name='dictLevelClass']"); // 担保
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedFactoringList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedFactoringList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedFactoringList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedFactoringList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&creditUnclearedFactoringList[" + i + "].factoringAmount=" + $(factoringAmount[i]).val();		
		param += "&creditUnclearedFactoringList[" + i + "].factoringBalance=" + $(factoringBalance[i]).val();		
		param += "&creditUnclearedFactoringList[" + i + "].factoringDay=" + $(factoringDay[i]).val();		
		param += "&creditUnclearedFactoringList[" + i + "].dictGuarantee=" + $(dictGuarantee[i]).val();
		param += "&creditUnclearedFactoringList[" + i + "].makeAdvances=" + $(makeAdvances[i]).val();		
		param += "&creditUnclearedFactoringList[" + i + "].lendingDay=" + $(lendingDay[i]).val();		
		param += "&creditUnclearedFactoringList[" + i + "].dictLevelClass=" + $(dictLevelClass[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清票据贴现
function makeParamCreditUnclearedNotesDiscounted() {
	
	// 企业征信_未结清票据贴现
	var id = $("#creditUnclearedNotesDiscountedForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedNotesDiscountedForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedNotesDiscountedForm").find("input[name='loanOrg']"); // 授信机构
	var transactionCount = $("#creditUnclearedNotesDiscountedForm").find("input[name='transactionCount']"); // 币种
	var balance = $("#creditUnclearedNotesDiscountedForm").find("input[name='balance']"); // 融资金额

	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedNotesDiscountedList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedNotesDiscountedList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedNotesDiscountedList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedNotesDiscountedList[" + i + "].transactionCount=" + $(transactionCount[i]).val();
		param += "&creditUnclearedNotesDiscountedList[" + i + "].balance=" + $(balance[i]).val();		

	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清银行承兑汇票
function makeParamCreditUnclearedBankAcceptance() {
	
	// 企业征信_未结清银行承兑汇票
	var id = $("#creditUnclearedBankAcceptanceForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedBankAcceptanceForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedBankAcceptanceForm").find("input[name='loanOrg']"); // 授信机构
	var transactionCount = $("#creditUnclearedBankAcceptanceForm").find("input[name='transactionCount']"); // 融资金额
	var actual30dayBalance = $("#creditUnclearedBankAcceptanceForm").find("input[name='actual30dayBalance']"); //到期小于30余额
	var actual60dayBalance = $("#creditUnclearedBankAcceptanceForm").find("input[name='actual60dayBalance']"); //到期小于60余额
	var actual90dayBalance = $("#creditUnclearedBankAcceptanceForm").find("input[name='actual90dayBalance']"); //到期小于90余额
	var actual91dayBalance = $("#creditUnclearedBankAcceptanceForm").find("input[name='actual91dayBalance']"); //到期大于90余额
	var totalBalance = $("#creditUnclearedBankAcceptanceForm").find("input[name='totalBalance']"); //余额合计
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedBankAcceptanceList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedBankAcceptanceList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedBankAcceptanceList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedBankAcceptanceList[" + i + "].transactionCount=" + $(transactionCount[i]).val();
		param += "&creditUnclearedBankAcceptanceList[" + i + "].actual30dayBalance=" + $(actual30dayBalance[i]).val();		
		param += "&creditUnclearedBankAcceptanceList[" + i + "].actual60dayBalance=" + $(actual60dayBalance[i]).val();		
		param += "&creditUnclearedBankAcceptanceList[" + i + "].actual90dayBalance=" + $(actual90dayBalance[i]).val();
		param += "&creditUnclearedBankAcceptanceList[" + i + "].actual91dayBalance=" + $(actual91dayBalance[i]).val();		
		param += "&creditUnclearedBankAcceptanceList[" + i + "].totalBalance=" + $(totalBalance[i]).val();
		
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清信用证
function makeParamCreditUnclearedLetterCredit() {
	
	// 企业征信_未结清信用证
	var id = $("#creditUnclearedLetterCreditForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedLetterCreditForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedLetterCreditForm").find("input[name='loanOrg']"); // 授信机构
	var transactionCount = $("#creditUnclearedLetterCreditForm").find("input[name='transactionCount']"); // 笔数
	var balance = $("#creditUnclearedLetterCreditForm").find("input[name='balance']"); // 金额
	var amount = $("#creditUnclearedLetterCreditForm").find("input[name='amount']"); // 余额
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedLetterCreditList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedLetterCreditList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedLetterCreditList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedLetterCreditList[" + i + "].transactionCount=" + $(transactionCount[i]).val();
		param += "&creditUnclearedLetterCreditList[" + i + "].balance=" + $(balance[i]).val();		
		param += "&creditUnclearedLetterCreditList[" + i + "].amount=" + $(amount[i]).val();		

	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	return param;
	
}

//企业征信_未结清保函
function makeParamCreditUnclearedLetterGuarantee() {
	
	// 企业征信_未结清保函
	var id = $("#creditUnclearedLetterGuaranteeForm").find("input[name='id']"); // 主键
	var loanCode = $("#creditUnclearedLetterGuaranteeForm").find("input[name='loanCode']"); // 外键
	
	var loanOrg = $("#creditUnclearedLetterGuaranteeForm").find("input[name='loanOrg']"); // 授信机构
	var transactionCount = $("#creditUnclearedLetterGuaranteeForm").find("input[name='transactionCount']"); // 笔数
	var balance = $("#creditUnclearedLetterGuaranteeForm").find("input[name='balance']"); // 金额
	var amount = $("#creditUnclearedLetterGuaranteeForm").find("input[name='amount']"); // 余额
	
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedLetterGuaranteeList[" + i + "].id=" + $(id[i]).val();
		param += "&creditUnclearedLetterGuaranteeList[" + i + "].loanCode=" + $(loanCode[i]).val();
		
		param += "&creditUnclearedLetterGuaranteeList[" + i + "].loanOrg=" + $(loanOrg[i]).val();		
		param += "&creditUnclearedLetterGuaranteeList[" + i + "].transactionCount=" + $(transactionCount[i]).val();
		param += "&creditUnclearedLetterGuaranteeList[" + i + "].balance=" + $(balance[i]).val();		
		param += "&creditUnclearedLetterGuaranteeList[" + i + "].amount=" + $(amount[i]).val();		

	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	
	return param;
	
}

//拼接未结清业务:不良、关注类-贷款信息
function makeUnclearedImproperLoanParam(flag) {
	var form = $("#"+flag+"Form");
	
	// 企业征信-对外担保信息
	var id = form.find("input[name='id']"); // 主键
	var loanCode = form.find("input[name='loanCode']"); // 外键
	var businessType = form.find("input[name='businessType']"); //
	
	var loanOrg = form.find("input[name='loanOrg']"); //
	var dictCurrency = form.find("select[name='dictCurrency']"); //
	var businessAmount = form.find("input[name='businessAmount']"); //
	var businessBalance = form.find("input[name='businessBalance']"); //业务余额
	var businessDay = form.find("input[name='businessDay']"); //
	var actualDay = form.find("input[name='actualDay']"); //
	var dictLevelClass = form.find("select[name='dictLevelClass']"); //
	var dictLoanType = form.find("select[name='dictLoanType']"); //
	var dictGuarantee = form.find("select[name='dictGuarantee']"); //
	var dictExhibition = form.find("select[name='dictExhibition']"); //
	var makeAdvances = form.find("select[name='makeAdvances']"); //
	var marginLevel= form.find("input[name='marginLevel']");//保证金比例
	var loanCode=$("#enterpriseCreditLoanCode").val();
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedImproperLoanList[" + i + "].id=" + $(id[i]).val();
		
		if($(loanCode[i]).val()!=undefined && $(loanCode[i]).val()!=''){
			loanCode=$(loanCode[i]).val();
		}
		param += "&creditUnclearedImproperLoanList[" + i + "].loanCode=" +loanCode;
		param += "&creditUnclearedImproperLoanList[" + i + "].businessType=" + $(businessType[i]).val();
		param += "&creditUnclearedImproperLoanList[" + i + "].loanOrg=" + $(loanOrg[i]).val();
		param += "&creditUnclearedImproperLoanList[" + i + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&creditUnclearedImproperLoanList[" + i + "].businessAmount=" + $(businessAmount[i]).val();
		param += "&creditUnclearedImproperLoanList[" + i + "].businessBalance=" + (($(businessBalance[i]).val()==undefined)?"":$(businessBalance[i]).val());
		param += "&creditUnclearedImproperLoanList[" + i + "].businessDay=" + $(businessDay[i]).val();		
		param += "&creditUnclearedImproperLoanList[" + i + "].actualDay=" + $(actualDay[i]).val();
		param += "&creditUnclearedImproperLoanList[" + i + "].dictLevelClass=" + $(dictLevelClass[i]).val();
		param += "&creditUnclearedImproperLoanList[" + i + "].marginLevel=" + $(marginLevel[i]).val();
		
		param += "&creditUnclearedImproperLoanList[" + i + "].dictLoanType=" + (($(dictLoanType[i]).val()==undefined)?"":$(dictLoanType[i]).val());		
		param += "&creditUnclearedImproperLoanList[" + i + "].dictGuarantee=" + (($(dictGuarantee[i]).val()==undefined)?"":$(dictGuarantee[i]).val());
		
		param += "&creditUnclearedImproperLoanList[" + i + "].dictExhibition=" + (($(dictExhibition[i]).val()==undefined)?"":$(dictExhibition[i]).val());
		param += "&creditUnclearedImproperLoanList[" + i + "].makeAdvances=" + (($(makeAdvances[i]).val()==undefined)?"":$(makeAdvances[i]).val());
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();

	if( id.length==0 || id.length==undefined){
		param += "&businessType=" + $("#"+flag+"TableHide").find("input[name='businessType']").val();	
	}
	return param;
}
var unclearedImproperLoanNumber = 0;
//拼接未结清业务:不良、关注类-贷款信息
function makeUnclearedImproperLoanParamSave(flag, number) {
	
	var form = $("#"+flag+"Form");
	
	// 企业征信-对外担保信息
	var id = form.find("input[name='id']"); // 主键
	var loanCode = form.find("input[name='loanCode']"); // 外键
	var businessType = form.find("input[name='businessType']"); //
	
	var loanOrg = form.find("input[name='loanOrg']"); //
	var dictCurrency = form.find("select[name='dictCurrency']"); //
	var businessAmount = form.find("input[name='businessAmount']"); //
	var businessBalance = form.find("input[name='businessBalance']"); //业务余额
	var businessDay = form.find("input[name='businessDay']"); //
	var actualDay = form.find("input[name='actualDay']"); //
	var dictLevelClass = form.find("select[name='dictLevelClass']"); //
	var dictLoanType = form.find("select[name='dictLoanType']"); //
	var dictGuarantee = form.find("select[name='dictGuarantee']"); //
	var dictExhibition = form.find("select[name='dictExhibition']"); //
	var makeAdvances = form.find("select[name='makeAdvances']"); //
	var marginLevel= form.find("input[name='marginLevel']");//保证金比例
	var loanCode=$("#enterpriseCreditLoanCode").val();
	var param = "";
	for(var i = 0; i < id.length; i++ ){
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].id=" + $(id[i]).val();
		
		if($(loanCode[i]).val()!=undefined && $(loanCode[i]).val()!=''){
			loanCode=$(loanCode[i]).val();
		}
		unclearedImproperLoanNumber++;
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].loanCode=" +loanCode;
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].businessType=" + $(businessType[i]).val();
		
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].loanOrg=" + $(loanOrg[i]).val();
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].dictCurrency=" + $(dictCurrency[i]).val();
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].businessAmount=" + $(businessAmount[i]).val();
		
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].businessBalance=" + (($(businessBalance[i]).val()==undefined)?"":$(businessBalance[i]).val());
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].businessDay=" + $(businessDay[i]).val();		
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].actualDay=" + $(actualDay[i]).val();
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].dictLevelClass=" + $(dictLevelClass[i]).val();
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].marginLevel=" + $(marginLevel[i]).val();
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].dictLoanType=" + (($(dictLoanType[i]).val()==undefined)?"":$(dictLoanType[i]).val());		
		
		
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].dictGuarantee=" + (($(dictGuarantee[i]).val()==undefined)?"":$(dictGuarantee[i]).val());
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].dictExhibition=" + (($(dictExhibition[i]).val()==undefined)?"":$(dictExhibition[i]).val());
		param += "&creditUnclearedImproperLoanList[" + unclearedImproperLoanNumber + "].makeAdvances=" + (($(makeAdvances[i]).val()==undefined)?"":$(makeAdvances[i]).val());
	}
	// 关联id
	param += "&loanCode=" + $("#enterpriseCreditLoanCode").val();
	
	return param;
}



// 保存信息
function saveTr(flag){
	// 根据企业信用报告的版本，判断数据库中是否已经存在企业征信报告
	// 如果没有，要求点击页面底部保存按钮，新增企业征信报告
	var creditExists = $("#enterpriseCreditForm").find("input[name='enterpriseCreditExists']").val();
	//验证表单
	if (creditExists == ""
			|| creditExists == null
			|| creditExists == undefined
			|| creditExists =="0") {
		art.dialog.tips("请在页面最上方选择信用报告版本，并保存！");
		return;
	}
		
	//验证表单
	if (!checkForm($("#" + flag + "Form"))) {
		return;
	}
	 
	if (flag == "unclearLoanLoan") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期时间需要晚于发放日期")) {
			return;
		}
		
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessAmountName","businessBalanceName",
				"借据金额需大于借据余额")) {
			return;
		}
	} else if (flag == "unclearLoanTrade") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期时间需要晚于发放日期")) {
			return;
		}
		
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessAmountName","businessBalanceName",
				"融资金额需大于等于融资余额")) {
			return;
		}
		
	} else if (flag == "unclearLoanFactor") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期时间需要晚于叙做日期")) {
			return;
		}		
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessAmountName","businessBalanceName",
				"叙做金额需大于等于叙做余额")) {
			return;
		}
	} else if (flag == "unclearLoanBank") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期时间需要晚于承兑日期")) {
			return;
		}	
	} else if (flag == "unclearLoanGuarantee") {
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessAmountName","businessBalanceName",
				"开证金额需大于等于可用余额")) {
			return;
		}	
	} else if (flag == "unclearLoanLetter") {
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessAmountName","businessBalanceName",
				"开立金额需大于等于可用余额")) {
			return;
		}	
	} else if (flag == "creditUnclearedLoan") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"lendingDay","actualDay",
				"到期时间需要晚于发放日期")) {
			return;
		}		
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"iousAmountName","iousBalanceName",
				"借据金额需大于等于借据余额")) {
			return;
		}
	} else if (flag == "creditUnclearedTradeFinancing") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"lendingDay","actualDay",
				"到期时间需要晚于发放日期")) {
			return;
		}
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"financingAmountName","financingBalanceName",
				"融资金额需大于等于融资余额")) {
			return;
		}		
		
	} else if (flag == "creditUnclearedFactoring") {
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"factoringAmountName","factoringBalanceName",
				"叙做金额需大于等于叙做余额")) {
			return;
		}	
	} else if (flag == "creditUnclearedLetterCredit") {
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"balanceName","amountName",
				"信用证金额需大于等于余额")) {
			return;
		}	
	} else if (flag == "creditUnclearedLetterGuarantee") {
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"balanceName","amountName",
				"保函金额需大于等于余额")) {
			return;
		}	
	} else if (flag == "paidLoanLoan") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期日期需要晚于放款日期")) {
			return;
		} 
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","paidDay",
				"结清日期需要晚于放款日期")) {
			return;
		}
	} else if (flag == "paidLoanTrade") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期日期需要晚于放款日期")) {
			return;
		}	
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","paidDay",
				"结清日期需要晚于放款日期")) {
			return;
		}				
	} else if (flag == "paidLoanFactor") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期日期需要晚于叙做日期")) {
			return;
		}	
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","paidDay",
				"结清日期需要晚于叙做日期")) {
			return;
		}
	} else if (flag == "paidLoanNotes") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期日期需要晚于贴现日期")) {
			return;
		}	
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","paidDay",
				"结清日期需要晚于贴现日期")) {
			return;
		}
	} else if (flag == "paidLoanBank") {
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","actualDay",
				"到期日期需要晚于承兑日期")) {
			return;
		}	
		if (!checkDate($("#" + flag + "Form"),$("#" + flag + "Form"),
				"businessDay","paidDay",
				"结清日期需要晚于承兑日期")) {
			return;
		}
	} else if (flag == "creditLiabilityHis") {
		if (!checkDataBig($("#" + flag + "Form"),$("#" + flag + "Form"),
				"allBalanceName","badnessBalanceName",
				"全部负债余额需大于等于不良负债余额")) {
			return;
		}		
	}
		
	var data = "";
	var url = "";
	// 拼接信用卡json
	if (flag == "creditInvestorInfo") {
		data = encodeURI(makeParamCreditInvestorInfo());
		url = ctx + "/credit/enterpriseCredit/saveCreditInvestorInfo";
		
	} else if (flag == "creditPunish") {
		// 处罚信息
		data = encodeURI(makeCreditPunishParam());
		url = ctx + "/credit/enterpriseCredit/saveCreditPunish";		
	} else if (flag == "creditGrade") {
		// 评级信息
		data = makeCreditGradeParam();
		url = ctx + "/credit/enterpriseCredit/saveCreditGrade";		
	} else if (flag == "creditLoanCard") {
		// 贷款卡信息
		data = encodeURI(makeCreditLoanCardParam());
		url = ctx + "/credit/enterpriseCredit/saveCreditLoanCard";		
	} else if (flag == "civilJudgment") {
		// 民事判决信息拼接查询信息卡
		data = encodeURI(makeCivilJudgmentParam());
		url = ctx + "/credit/enterpriseCredit/saveCivilJudgment";		
	} else if (flag == "externalGuarantee") {
		// 外部担保信息
		data = encodeURI(makeExternalGuaranteeParam());
		url = ctx + "/credit/enterpriseCredit/saveExternalGuarantee";	
		
	} else if (flag.indexOf("paidLoan")!=-1) {
		// 已结清信贷-贷款信息
		data = encodeURI(makePaidLoanParam(flag));
		url = ctx + "/credit/enterpriseCredit/savePaidLoan";	
		
	} else if(flag == "creditExecutiveInfo") {
		//高管人员信息
		data = encodeURI(makeParamCreditExecutiveInfo());
		url = ctx + "/credit/enterpriseCredit/saveCreditExecutiveInfo";

	} else if(flag == "creditAffiliatedEnterprise") {
		//直接关联企业
		data = encodeURI(makeParamCreditAffiliatedEnterprise());
		url = ctx + "/credit/enterpriseCredit/saveCreditAffiliatedEnterprise";

	} else if(flag == "creditLiabilityHis") {
		//企业征信_负债历史变化
		data = makeParamCreditLiabilityHis();
		url = ctx + "/credit/enterpriseCredit/saveCreditLiabilityHis";

	} else if(flag == "creditUnclearedLoan") {
		//企业征信_未结清贷款
		data = encodeURI(makeParamCreditUnclearedLoan());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedLoan";

	} else if(flag == "creditUnclearedTradeFinancing") {
		//企业征信_未结清贸易融资
		data = encodeURI(makeParamCreditUnclearedTradeFinancing());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedTradeFinancing";

	} else if(flag == "creditUnclearedFactoring") {
		//企业征信_未结清保理
		data = encodeURI(makeParamCreditUnclearedFactoring());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedFactoring";

	} else if(flag == "creditUnclearedNotesDiscounted") {
		//企业征信_未结清票据贴现
		data = encodeURI(makeParamCreditUnclearedNotesDiscounted());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedNotesDiscounted";

	} else if(flag == "creditUnclearedBankAcceptance") {
		//企业征信_未结清银行承兑汇票
		data = encodeURI(makeParamCreditUnclearedBankAcceptance());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedBankAcceptance";

	} else if(flag == "creditUnclearedLetterCredit") {
		//企业征信_未结清信用证
		data = encodeURI(makeParamCreditUnclearedLetterCredit());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedLetterCredit";

	} else if(flag == "creditUnclearedLetterGuarantee") {
		//企业征信_未结清保函
		data = encodeURI(makeParamCreditUnclearedLetterGuarantee());
		url = ctx + "/credit/enterpriseCredit/saveCreditUnclearedLetterGuarantee";

	}
	else if (flag.indexOf("unclearLoan")!=-1) {
		// 未结清业务:不良、关注类-贷款信息
	
		data = encodeURI(makeUnclearedImproperLoanParam(flag));
		url = ctx + "/credit/enterpriseCredit/saveUnclearedImproperLoan";	
	}
	
	// 简版贷记卡负债信息
	$.ajax({
		url: url,
		data: data,
		type: "post",
		dataType: 'json',
		success:function(data){
			if(data) {
				art.dialog.tips('保存成功!');
				// 刷新信用卡div
				if (flag == "creditInvestorInfo") {
					$("#creditInvestorInfoTab").load(ctx+"/credit/enterpriseCredit/initCreditInvestorInfo",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditExecutiveInfo") {
					//高管人员信息
					$("#creditExecutiveInfoTab").load(ctx+"/credit/enterpriseCredit/initCreditExecutiveInfo",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditPunish") {
					// 处罚信息
					$("#creditPunishTab").load(ctx+"/credit/enterpriseCredit/initCreditPunish",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditGrade") {
					// 评级信息
					$("#creditGradeTab").load(ctx+"/credit/enterpriseCredit/initCreditGrade",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditLoanCard") {
					// 贷款卡信息
					$("#creditLoanCardTab").load(ctx+"/credit/enterpriseCredit/initCreditLoanCard",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "civilJudgment") {
					// 民事判决信息
					$("#civilJudgmentTab").load(ctx+"/credit/enterpriseCredit/initCivilJudgment",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "externalGuarantee") {
					// 外部担保信息
					$("#externalGuaranteeTab").load(ctx+"/credit/enterpriseCredit/initExternalGuarantee",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag.indexOf("paidLoan")!=-1) {
					// 已结清负债信息
					$("#"+flag+"Tab").load(ctx+"/credit/enterpriseCredit/initPaidLoan",{"loanCode":$("#enterpriseCreditLoanCode").val(),"businessType":data},function(){});
				} else if (flag == "creditAffiliatedEnterprise") {
					// 直接关联企业
					$("#creditAffiliatedEnterpriseTab").load(ctx+"/credit/enterpriseCredit/initCreditAffiliatedEnterprise",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditLiabilityHis") {
					// 直接关联企业
					$("#creditLiabilityHisTab").load(ctx+"/credit/enterpriseCredit/initCreditLiabilityHis",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedLoan") {
					// 企业征信_未结清贷款
					$("#creditUnclearedLoanTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedLoan",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedTradeFinancing") {
					// 企业征信_未结清贸易融资
					$("#creditUnclearedTradeFinancingTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedTradeFinancing",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedFactoring") {
					// 企业征信_未结清贸易融资
					$("#creditUnclearedFactoringTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedFactoring",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedNotesDiscounted") {
					// 企业征信_未结清票据贴现
					$("#creditUnclearedNotesDiscountedTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedNotesDiscounted",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedBankAcceptance") {
					// 企业征信_未结清银行承兑汇票
					$("#creditUnclearedBankAcceptanceTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedBankAcceptance",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedLetterCredit") {
					// 企业征信_未结清信用证
					$("#creditUnclearedLetterCreditTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedLetterCredit",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				} else if (flag == "creditUnclearedLetterGuarantee") {
					// 企业征信_未结清保函
					$("#creditUnclearedLetterGuaranteeTab").load(ctx+"/credit/enterpriseCredit/initCreditUnclearedLetterGuarantee",{"loanCode":$("#enterpriseCreditLoanCode").val()},function(){});
				}else if (flag.indexOf("unclearLoan")!=-1) {
					// 未结清业务:不良、关注类-贷款信息
					$("#"+flag+"Tab").load(ctx+"/credit/enterpriseCredit/initUnclearedImproperLoan",{"loanCode":$("#enterpriseCreditLoanCode").val(),"businessType":data},function(){});
				} 
				
			} else {
				art.dialog.tips('保存失败!');
			}
		}
	});
}

//保存企业征信
function saveEnterpriseCredit(){
	//验证表单
	if (!$("#enterpriseCreditForm").validate().form()) {
		return;
	}	
	
	//基础信息
	if (!$("#creditBasicInfoForm").validate().form()) {
		return;
	}
	
	if (!checkDate($("#creditBasicInfoForm"),$("#creditBasicInfoForm"),
			"registrationDate","expireDate",
			"有效截止日期需要晚于登记注册日期")) {
		return;
	}
	
	//验证表单 出资人信息
	if (!checkForm($("#creditInvestorInfoForm"))) {
		return;
	}
	
	//验证表单 高管人员信息
	if (!checkForm($("#creditExecutiveInfoForm"))) {
		return;
	}
	
	//验证表单 有直接关联的其他企业
	if (!checkForm($("#creditAffiliatedEnterpriseForm"))) {
		return;
	}
	
	//验证表单 当前负债信息概要
	if (!checkForm($("#creditCurrentLiabilityInfoForm"))) {
		return;
	}
	
	//验证表单 当前负债信息概要
	if (!checkForm($("#creditCurrentLiabilityDetailForm"))) {
		return;
	}
	
	//验证表单 对外担保信息概要
	if (!checkForm($("#creditExternalSecurityInfoForm"))) {
		return;
	}
	
	//验证表单 已结清信贷信息概要
	if (!checkForm($("#creditCreditClearedInfoForm"))) {
		return;
	}
	
	//验证表单 企业征信_已结清信贷明细
	if (!checkForm($("#creditCreditClearedDetailForm"))) {
		return;
	}	
	
	//验证表单 企业征信_负债历史变化
	if (!checkForm($("#creditLiabilityHisForm"))) {
		return;
	}
	
	if (!checkDataBig($("#creditLiabilityHisForm"),$("#creditLiabilityHisForm"),
			"allBalanceName","badnessBalanceName",
			"全部负债余额需大于等于不良负债余额")) {
		return;
	}
	
	//验证表单 未结清业务:不良、关注类 贷款
	if (!checkForm($("#unclearLoanLoanForm"))) {
		return;
	}
	
	if (!checkDate($("#unclearLoanLoanForm"),$("#unclearLoanLoanForm"),
			"businessDay","actualDay",
			"到期时间需要晚于发放日期")) {
		return;
	}
	
	if (!checkDataBig($("#unclearLoanLoanForm"),$("#unclearLoanLoanForm"),
			"businessAmountName","businessBalanceName",
			"借据金额需大于借据余额")) {
		return;
	}	
	
	//验证表单 未结清业务:不良、关注类 贸易融资
	if (!checkForm($("#unclearLoanTradeForm"))) {
		return;
	}
	
	if (!checkDate($("#unclearLoanTradeForm"),$("#unclearLoanTradeForm"),
			"businessDay","actualDay",
			"到期时间需要晚于发放日期")) {
		return;
	}
	
	if (!checkDataBig($("#unclearLoanTradeForm"),$("#unclearLoanTradeForm"),
			"businessAmountName","businessBalanceName",
			"融资金额需大于等于融资余额")) {
		return;
	}
	
	//验证表单 未结清业务:不良、关注类 保理
	if (!checkForm($("#unclearLoanFactorForm"))) {
		return;
	}	
	
	if (!checkDate($("#unclearLoanFactorForm"),$("#unclearLoanFactorForm"),
			"businessDay","actualDay",
			"到期时间需要晚于叙做日期")) {
		return;
	}
	
	if (!checkDataBig($("#unclearLoanTradeForm"),$("#unclearLoanTradeForm"),
			"businessAmountName","businessBalanceName",
			"叙做金额需大于等于叙做余额")) {
		return;
	}
	
	//验证表单 未结清业务:不良、关注类 票据贴现
	if (!checkForm($("#unclearLoanNotesForm"))) {
		return;
	}
	
	//验证表单 未结清业务:不良、关注类 银行承兑汇票
	if (!checkForm($("#unclearLoanBankForm"))) {
		return;
	}
	
	if (!checkDate($("#unclearLoanBankForm"),$("#unclearLoanBankForm"),
			"businessDay","actualDay",
			"到期时间需要晚于承兑日期")) {
		return;
	}

	//验证表单 未结清业务:不良、关注类 信用证
	if (!checkForm($("#unclearLoanGuaranteeForm"))) {
		return;
	}

	if (!checkDataBig($("#unclearLoanGuaranteeForm"),$("#unclearLoanGuaranteeForm"),
			"businessAmountName","businessBalanceName",
			"开证金额需大于等于可用余额")) {
		return;
	}
	//验证表单 未结清业务:不良、关注类 保函
	if (!checkForm($("#unclearLoanLetterForm"))) {
		return;
	}
	if (!checkDataBig($("#unclearLoanLetterForm"),$("#unclearLoanLetterForm"),
			"businessAmountName","businessBalanceName",
			"开立金额需大于等于可用余额")) {
		return;
	}
	
	//验证表单 未结清业务:正常 贷款
	if (!checkForm($("#creditUnclearedLoanForm"))) {
		return;
	}
	
	if (!checkDate($("#creditUnclearedLoanForm"),$("#creditUnclearedLoanForm"),
			"lendingDay","actualDay",
			"到期时间需晚于发放日期")) {
		return;
	}
	
	if (!checkDataBig($("#creditUnclearedLoanForm"),$("#creditUnclearedLoanForm"),
			"iousAmountName","iousBalanceName",
			"借据金额需大于等于借据余额")) {
		return;
	}
	
	//验证表单 未结清业务:正常 贸易融资
	if (!checkForm($("#creditUnclearedTradeFinancingForm"))) {
		return;
	}
	
	if (!checkDate($("#creditUnclearedTradeFinancingForm"),$("#creditUnclearedTradeFinancingForm"),
			"lendingDay","actualDay",
			"到期时间需要晚于发放日期")) {
		return;
	}
	
	if (!checkDataBig($("#creditUnclearedTradeFinancingForm"),$("#creditUnclearedTradeFinancingForm"),
			"financingAmountName","financingBalanceName",
			"融资金额需大于等于融资余额")) {
		return;
	}
	
	//验证表单 未结清业务:正常 保理
	if (!checkForm($("#creditUnclearedFactoringForm"))) {
		return;
	}
	
	if (!checkDataBig($("#creditUnclearedFactoringForm"),$("#creditUnclearedFactoringForm"),
			"factoringAmountName","factoringBalanceName",
			"叙做金额需大于等于叙做余额")) {
		return;
	}
	
	//验证表单 未结清业务:正常 票据贴现
	if (!checkForm($("#creditUnclearedNotesDiscountedForm"))) {
		return;
	}	
	
	//验证表单 未结清业务:正常 银行承兑汇票
	if (!checkForm($("#creditUnclearedBankAcceptanceForm"))) {
		return;
	}
	
	//验证表单 未结清业务:正常 信用证
	if (!checkForm($("#creditUnclearedLetterCreditForm"))) {
		return;
	}	
	if (!checkDataBig($("#creditUnclearedLetterCreditForm"),$("#creditUnclearedLetterCreditForm"),
			"balanceName","amountName",
			"信用证金额需大于等于余额")) {
		return;
	}
	
	
	//验证表单 未结清业务:正常 保函
	if (!checkForm($("#creditUnclearedLetterGuaranteeForm"))) {
		return;
	}
	if (!checkDataBig($("#creditUnclearedLetterGuaranteeForm"),$("#creditUnclearedLetterGuaranteeForm"),
			"balanceName","amountName",
			"保函金额需大于等于余额")) {
		return;
	}
	
	//验证表单 已还清债务-贷款
	if (!checkForm($("#paidLoanLoanForm"))) {
		return;
	}	
	if (!checkDate($("#paidLoanLoanForm"),$("#paidLoanLoanForm"),
			"businessDay","actualDay",
			"到期日期需要晚于放款日期")) {
		return;
	}
	if (!checkDate($("#paidLoanLoanForm"),$("#paidLoanLoanForm"),
			"businessDay","paidDay",
			"结清日期需要晚于放款日期")) {
		return;
	}
	
	//验证表单 已还清债务-贸易融资
	if (!checkForm($("#paidLoanTradeForm"))) {
		return;
	}	
	if (!checkDate($("#paidLoanTradeForm"),$("#paidLoanTradeForm"),
			"businessDay","actualDay",
			"到期日期需要晚于放款日期")) {
		return;
	}
	if (!checkDate($("#paidLoanTradeForm"),$("#paidLoanTradeForm"),
			"businessDay","paidDay",
			"结清日期需要晚于放款日期")) {
		return;
	}
	
	//验证表单 已还清债务-保理
	if (!checkForm($("#paidLoanFactorForm"))) {
		return;
	}		
	if (!checkDate($("#paidLoanFactorForm"),$("#paidLoanFactorForm"),
			"businessDay","actualDay",
			"到期日期需要晚于叙做日期")) {
		return;
	}
	if (!checkDate($("#paidLoanFactorForm"),$("#paidLoanFactorForm"),
			"businessDay","paidDay",
			"结清日期需要晚于叙做日期")) {
		return;
	}
	
	//验证表单 已还清债务-票据贴现
	if (!checkForm($("#paidLoanNotesForm"))) {
		return;
	}
	if (!checkDate($("#paidLoanNotesForm"),$("#paidLoanNotesForm"),
			"businessDay","actualDay",
			"到期日期需要晚于贴现日期")) {
		return;
	}	
	if (!checkDate($("#paidLoanNotesForm"),$("#paidLoanNotesForm"),
			"businessDay","paidDay",
			"结清日期需要晚于贴现日期")) {
		return;
	}
	
	//验证表单 已还清债务-银行承兑汇票
	if (!checkForm($("#paidLoanBankForm"))) {
		return;
	}
	if (!checkDate($("#paidLoanBankForm"),$("#paidLoanBankForm"),
			"businessDay","actualDay",
			"到期日期需要晚于承兑日期")) {
		return;
	}
	if (!checkDate($("#paidLoanBankForm"),$("#paidLoanBankForm"),
			"businessDay","paidDay",
			"结清日期需要晚于承兑日期")) {
		return;
	}
	
	//验证表单 已还清债务-信用证
	if (!checkForm($("#paidLoanCreditForm"))) {
		return;
	}
	
	//验证表单 已还清债务-保函
	if (!checkForm($("#paidLoanGuaranteeForm"))) {
		return;
	}	
	
	var data = "";
	//企业信息
	data += makeParamEnterpriseCredit();
	//基础信息
	data += makeParamCreditBasicInfo();
	//当前负债信息概要
	data += makeParamCreditCurrentLiabilityInfo();
	//当前负债信息明细
	data += makeParamCreditCurrentLiabilityDetail();
	//对外担保信息概要
	data += makeParamCreditExternalSecurityInfo();
	//企业征信_已结清信贷信息
	data += makeParamCreditCreditClearedInfo();
	//企业征信_已结清信贷明细
	data += makeParamCreditCreditClearedDetail();
	
	// 出资人信息
	data += makeParamCreditInvestorInfo();
	// 处罚信息
	data += makeCreditPunishParam();
	// 评级信息
	data += makeCreditGradeParam();
	// 贷款卡信息
	data += makeCreditLoanCardParam();
	// 民事判决信息拼接查询信息卡
	data += makeCivilJudgmentParam();
	// 外部担保信息
	data += makeExternalGuaranteeParam();
	
	// 已结清信贷-贷款信息
	paidLoanNUmber = 0;
	data += makePaidLoanParamSave("paidLoanBank", 1);
	data += makePaidLoanParamSave("paidLoanCredit", 2);
	data += makePaidLoanParamSave("paidLoanFactor", 3);
	data += makePaidLoanParamSave("paidLoanGuarantee", 4);
	data += makePaidLoanParamSave("paidLoanLoan", 5);
	data += makePaidLoanParamSave("paidLoanNotes", 6);
	data += makePaidLoanParamSave("paidLoanTrade", 7);
	paidLoanNUmber = 0;
	
	//高管人员信息
	data += makeParamCreditExecutiveInfo();
	//直接关联企业
	data += makeParamCreditAffiliatedEnterprise();
	//企业征信_负债历史变化
	data += makeParamCreditLiabilityHis();
	//企业征信_未结清贷款
	data += makeParamCreditUnclearedLoan();
	//企业征信_未结清贸易融资
	data += makeParamCreditUnclearedTradeFinancing();
	//企业征信_未结清贸易融资
	data += makeParamCreditUnclearedFactoring();
	//企业征信_未结清票据贴现
	data += makeParamCreditUnclearedNotesDiscounted();
	//企业征信_未结清银行承兑汇票
	data += makeParamCreditUnclearedBankAcceptance();
	//企业征信_未结清信用证
	data += makeParamCreditUnclearedLetterCredit();
	//企业征信_未结清保函
	data += makeParamCreditUnclearedLetterGuarantee();	
	
	
	//企业征信_未结清信用证
	// 未结清业务:不良、关注类-贷款信息
	unclearedImproperLoanNumber = 0;
	data += makeUnclearedImproperLoanParamSave("unclearLoanBank", 1);
	data += makeUnclearedImproperLoanParamSave("unclearLoanFactor", 2);
	data += makeUnclearedImproperLoanParamSave("unclearLoanGuarantee", 3);
	data += makeUnclearedImproperLoanParamSave("unclearLoanLetter", 4);
	data += makeUnclearedImproperLoanParamSave("unclearLoanLoan", 5);
	data += makeUnclearedImproperLoanParamSave("unclearLoanNotes", 6);
	data += makeUnclearedImproperLoanParamSave("unclearLoanTrade", 7);
	unclearedImproperLoanNumber = 0;
	data = encodeURI(data);
	
	var url = ctx + "/credit/enterpriseCredit/saveEnterpriseCredit";
	
	$.ajax({
		url: url,
		data: data,
		type: "post",
		async: false,
		dataType: 'json',
		success:function(data){
			if(data) {
				art.dialog.tips('保存成功!');
				//页面跳转
				initEnterpriseCredit();
			} else {
				art.dialog.tips('保存失败!');
			}
		}
	});
	
}

//跳转dao企业征信
function initEnterpriseCredit(){
	window.location.href = ctx + "/credit/enterpriseCredit/form?loanCode=" + $("#enterpriseCreditLoanCode").val();
}


function checkIdNum(obj){
	var isOK= true;
	var num = $(obj).val();
	var certType = $(obj).parents("tr:first").find("select[name='dictCertType']:first").val();
	if(certType == "1") {
		isOK= checkIdcard(num);
	}
	return isOK;
	
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

function formatMoneyEx(money){
	var tpMoney=money;
	if(money!=undefined && money!=null){
		 tpMoney = money.toString().replace(/\,/g, '');
	}
	if(tpMoney==""){
		tpMoney=0;
	}
	return tpMoney;
}