<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<jsp:include page="../apply/applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/launch/manager.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//A005老板借      A006小微企业借时，均为必填项
	if("A005" == "${applyInfoEx.loanInfo.productType}" || "A006" == "${applyInfoEx.loanInfo.productType}"){
		$("input[class*='input_text178']").addClass("required");
		$("input[type$='checkbox']").addClass("required");
		$("#longTerm").removeClass("required");
		$("#creditCode").removeClass("required");
		$("#orgCode").removeClass("required");
		$("#creditCode").addClass("creditCodeAndOrgCodeNotEmpty");
		$("#orgCode").addClass("creditCodeAndOrgCodeNotEmpty");
		//alert(!parseInt("${applyInfoEx.loanInfo.loanApplyAmount}")>30*10000);
		//申请金额大于30万时必须填写
		var loanApplyAmount="${applyInfoEx.loanInfo.loanApplyAmount}";
		if(typeof loanApplyAmount!= 'undefined' && ''!=loanApplyAmount && parseFloat(loanApplyAmount) <= 30*10000){
			$("#corporateCertNum").removeClass("required coboCertCheckCopmany");
			$("#corporateRepresent").removeClass("required");
			$("#corporateRepresentMobile").removeClass("required");
			$("#compEmail").removeClass("required");
			$("#creditCode").removeClass("creditCodeAndOrgCodeNotEmpty");
			$("#orgCode").removeClass("creditCodeAndOrgCodeNotEmpty");
			$("#longTerm").removeClass("effectiveCertificate");
			
			$("#corporateCertNumRed").remove();
			$("#corporateRepresentRed").remove(); 
			$("#corporateRepresentMobileRed").remove();
			$("#compEmailRed").remove(); 
			$(".idValidityDayRed").hide();
		}
		$("#creditCodeRed").remove(); 
		$("#orgCodeRed").remove(); 
		$("select").addClass("required");
	}else{
		$(".red").remove();
		$("#longTerm").removeClass("effectiveCertificate");
	}
	
	//经营场所判断
	if("${applyInfoEx.loanCompManage.managePlace}".indexOf("1") != -1){
		$("#possession").append("<span id='appendRent'><label id='monthRentMoneyTitle' class='lab'><span class='red'>*</span>月租金(元)：</label>"+
		"<input type='text' style='width: 100px;' maxlength='100' class='required number numCheck positiveNumCheck' min='0' max='999999999.00' id='monthRentMoney' name='loanCompManage.monthRentMoney' value='${applyInfoEx.loanCompManage.monthRentMoney}'></span>");
	}
	if("${applyInfoEx.loanCompManage.managePlace}".indexOf("2") != -1){
		$("#possession").append("<span id='appendPay'><label id='monthPayMoneyTitle' class='lab'><span class='red'>*</span>月还款(元)：</label>"+
		"<input type='text' style='width: 100px;' maxlength='100' class='required number numCheck positiveNumCheck' min='0' max='999999999.00' id='monthPayMoney'  name='loanCompManage.monthPayMoney' value='${applyInfoEx.loanCompManage.monthPayMoney}'></span>");
	}
	
	$('#manageNextBtn').bind('click',function(){
		$(this).attr('disabled',true);
	 	launch.updateApplyInfo('7','manageForm','manageNextBtn'); 
	});
	loan.initCity("manageProvince", "manageCity", "manageArea");
	areaselect.initCity("manageProvince", "manageCity", "manageArea", $("#manageCityHidden").val(),$("#manageArerHidden").val());
	
	manager.init();
	
	//如果不是老板借和小微企业借则全部输入项置灰，不允许输入
	if("A005" != "${applyInfoEx.loanInfo.productType}" && "A006" != "${applyInfoEx.loanInfo.productType}"){
		$("#manageForm *").attr("disabled", "disabled");
	}
});
	//字段验证（与必填和非必填没关系）
	function certNumBlur(){
		var value=$("#corporateCertNum").val();
		if(value.length > 0){
			launch.corporateCertNum(value);
		    $("#corporateCertNum").addClass("coboCertCheckCopmany");
		}else{
			$("#corporateCertNum").removeClass("coboCertCheckCopmany");
		}
	} 
	//信用代码验证
   function creditCodeBlur(){
  		var value=$("#creditCode").val();
		if(value.length > 0){
		    $("#creditCode").addClass("Length18");
		    $("#creditCode").addClass("stringNumber");
		}else{
			$("#creditCode").removeClass("Length18");
			$("#creditCode").removeClass("stringNumber");
		}
	} 
   //组织机构码验证
   function orgCodeBlur(){
  		var value=$("#orgCode").val();
		if(value.length > 0){
		    $("#orgCode").addClass("Length10");
		    $("#orgCode").addClass("stringNumberOther");
		}else{
			$("#orgCode").removeClass("Length10");
			$("#orgCode").removeClass("stringNumberOther");
		}
	} 
	function managePlaceHandler(mark){	
		//选中租用显示月租金
		if(mark.value == '1' && mark.checked == true){
			$("#possession").append("<span id='appendRent'><label id='monthRentMoneyTitle' class='lab'><span class='red'>*</span>月租金(元)：</label>"+
					"<input type='text' id='monthRentMoney' class='required number numCheck positiveNumCheck' min='0' max='999999999.00' name='loanCompManage.monthRentMoney' value='${applyInfoEx.loanCompManage.monthRentMoney}'></span>");
		}else if(mark.value == '1' && mark.checked == false){
			$("#appendRent").remove();
		}
		//选中按揭显示月还款
		else if(mark.value == '2' && mark.checked == true){
			$("#possession").append("<span id='appendPay'><label id='monthPayMoneyTitle' class='lab'><span class='red'>*</span>月还款(元)：</label>"+
			"<input type='text' id='monthPayMoney' class='required number numCheck positiveNumCheck' min='0' max='999999999.00' name='loanCompManage.monthPayMoney' value='${applyInfoEx.loanCompManage.monthPayMoney}'></span>");
		}
		else if(mark.value == '2' && mark.checked == false){
			$("#appendPay").remove();
		} 
		/* if("A005" != "${applyInfoEx.loanInfo.productType}" && "A006" != "${applyInfoEx.loanInfo.productType}"){
			$(".red").remove();
			$("#monthRentMoney").removeClass("required");
			$("#monthPayMoney").removeClass("required");
		} */
	}

	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
</script>
</head>
<body>
<ul class="nav nav-tabs">
	   <li><a href="javascript:launch.getCustomerInfo_storeView('1');">个人基本信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('2');">借款意愿</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('3');">工作信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('4');">联系人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('5');">自然人保证人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('6');">房产信息</a></li>
		<li class="active"><a href="javascript:launch.getCustomerInfo_storeView('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('8');">证件信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('9');">银行卡信息</a></li>
		<li style="width:auto;float:right;">
			<jsp:include page="./tright.jsp"/>
		</li>
	</ul>

	<form id="custInfoForm" method="post" >
    	<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}" id="customerCode">
		<input type="hidden" name="consultId" value="${workItem.bv.consultId}" id="consultId">
		<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}" id="loanCode" >
  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
 		<input type="hidden" name="token" value="${workItem.token}">
 		<input type="hidden" name="stepName" value="${workItem.stepName }">
 		<input type="hidden" name="flowName" value="${workItem.flowName }">
 		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
 		<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
 		<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
 		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
 	</form>
	<div id="div7" class="content control-group">
		<form id="manageForm" method="post">
			<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
			<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}">
			<input type="hidden" name="consultId" value="${workItem.bv.consultId}">
			<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}">
	  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
	 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
	  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	 		<input type="hidden" name="token" value="${workItem.token}">
	 		<input type="hidden" name="stepName" value="${workItem.stepName }">
	 		<input type="hidden" name="flowName" value="${workItem.flowName }">
 			<input type="hidden" name="method" value="manager">
 			
 			<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}" />
 			<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
 			<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
 			<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
		   	  <div id="loanManageArea">
			      <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<input type="hidden" name="loanCompManage.id" value="${applyInfoEx.loanCompManage.id}"/>
							<label class="lab"><span class="red">*</span>营业执照注册号：</label>
							<input type="text" name="loanCompManage.businessLicenseRegisterNum" class="input_text178 stringNum" maxlength="50" value="${applyInfoEx.loanCompManage.businessLicenseRegisterNum}"/>
						</td>
						<td>
							<label class="lab"><span class="red">*</span>成立日期：</label> <input id="compCreateDate" class="Wdate input_text178" name="loanCompManage.compCreateDate"
								value="<fmt:formatDate value='${applyInfoEx.loanCompManage.compCreateDate}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
						</td>
						<td>
							<label class="lab"><span class="red">*</span>企业类型：</label>
							<select class="select180" name="loanCompManage.compType">
								<option value="">请选择</option>
								<c:forEach items="${fns:getNewDictList('jk_enterprise_type')}" var="item">
									<option value="${item.value}" <c:if test="${applyInfoEx.loanCompManage.compType==item.value}">selected=true</c:if>>
										${item.label}
									</option>
								</c:forEach>
							</select>
							<input type="text" name="loanCompManage.compTypeRemark" value="${applyInfoEx.loanCompManage.compTypeRemark}" class="input_text178 <c:if test='${empty applyInfoEx.loanCompManage.compTypeRemark}'> collapse </c:if>" maxlength="50"/>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class="lab"><span class="red">*</span>平均月营业额(万元)：</label>
						    <input type="text" name="loanCompManage.averageMonthTurnover" class="input_text178 numCheck positiveNumCheck" min="0" max="999999999.00" value="${applyInfoEx.loanCompManage.averageMonthTurnover}"/>
						</td>
						<td>
							<label class="lab"><span class="red">*</span>主营业务：</label>
							<input type="text" name="loanCompManage.managerBusiness" class="input_text178" maxlength="200" value="${applyInfoEx.loanCompManage.managerBusiness}"/>
						</td>
						<td>
							<label class="lab"><span class="red">*</span>企业注册资本(万元)：</label>
							<input type="text" name="loanCompManage.compRegisterCapital" pattern="0" class="input_text178 positiveInteger" min="0" max="999999999" value="${applyInfoEx.loanCompManage.compRegisterCapital}"/>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class="lab"><span class="red">*</span>申请人占股比例(%)：</label>
							<input type="text" name="loanCompManage.customerRatioComp" class="input_text178 number numCheck positiveNumCheck" min="0" max="999999999.00"" value="${applyInfoEx.loanCompManage.customerRatioComp}"/>
						</td>
						<td>
							<label class="lab"><span class="red" id="corporateRepresentRed">*</span>法定代表人姓名：</label>
							<input type="text" name="loanCompManage.corporateRepresent" id="corporateRepresent" class="input_text178 maxLength50" value="${applyInfoEx.loanCompManage.corporateRepresent}"/>
						</td>
						<td>
							<label class="lab"><span id="corporateCertNumRed" class="red">*</span>法定代表人身份证号码：</label>
							<input type="text" name="loanCompManage.certNum" id="corporateCertNum" class="input_text178" onblur="certNumBlur()" value="${applyInfoEx.loanCompManage.certNum}"/>
						</td>
					</tr>
					
					<tr>
						<td>
							<label class="lab">
								<span class="red idValidityDayRed">*</span>
								证件有效期：
							</label>
							<span>
								<input id="idStartDay" name="loanCompManage.idStartDay" value="<fmt:formatDate value='${applyInfoEx.loanCompManage.idStartDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" />
								-
								<input id="idEndDay" name="loanCompManage.idEndDay" value="<fmt:formatDate value='${applyInfoEx.loanCompManage.idEndDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10" <c:if test="${applyInfoEx.loanCompManage.idStartDay!=null && applyInfoEx.loanCompManage.idEndDay==null}"> readOnly=true style='display:none' </c:if> onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" />
								<input type="checkbox" id="longTerm" name="longTermManage" value="1" class="effectiveCertificate" onclick="launch.idEffectiveDay(this,'idEndDay');" <c:if test="${applyInfoEx.loanCompManage.idStartDay!=null && applyInfoEx.loanCompManage.idEndDay==null}">checked=true</c:if>>
								长期
								</input>
							</span>
						</td>
						<td>
							<label class="lab"><span id="corporateRepresentMobileRed" class="red">*</span>法定代表人手机号：</label>
							<input type="text" name="loanCompManage.corporateRepresentMobile" id="corporateRepresentMobile" class="input_text178 isMobile" value="${applyInfoEx.loanCompManage.corporateRepresentMobile}"/>
						</td>
						<td>
							<label class="lab"><span id="compEmailRed" class="red">*</span>企业邮箱：</label>
							<input type="text" name="loanCompManage.compEmail" id="compEmail" class="input_text178 isEmail maxLength20" value="${applyInfoEx.loanCompManage.compEmail}"/>
						</td>
					</tr>
					<tr>
						<td>
							<label class="lab"><span class="red">*</span>营业面积(平方米)：</label>
							<input type="text" name="loanCompManage.businessArea" pattern="0" class="input_text178 numCheck positiveNumCheck" min="0" max="999999999.00" value="${applyInfoEx.loanCompManage.businessArea}"/>
						</td>
						<td>
							<label class="lab"><span id="creditCodeRed" class="red">*</span>信用代码：</label>
							<input type="text" name="loanCompManage.creditCode" id="creditCode" class="input_text178" onblur="creditCodeBlur()" value="${applyInfoEx.loanCompManage.creditCode}"/>
						</td>
						<td>
							<label class="lab"><span id="orgCodeRed" class="red">*</span>组织机构码：</label>
							<input type="text" name="loanCompManage.orgCode" id="orgCode" class="input_text178" onblur="orgCodeBlur()" value="${applyInfoEx.loanCompManage.orgCode}"/>
						</td>
					</tr>  
					<tr>
						<td colspan="3">
							<label class="lab"><span class="red">*</span>经营场所：</label>
							<span>
							<c:forEach items="${fns:getNewDictList('jk_comp_manage_area')}" var="item">
						      <input name="loanCompManage.managePlace" type="checkbox" id="managePlace${item.value}" onclick="managePlaceHandler(this);"
							  <c:if test="${fn:contains(applyInfoEx.loanCompManage.managePlace,item.value)}">checked</c:if> value="${item.value}">${item.label}
							</c:forEach>
							</span>
							<label id="possession"></label>
							<%-- <label id='monthRentMoneyTitle' class='lab'><span class='red'>*</span>月租金：</label>
							<input type='text' id='monthRentMoney' name='loanCompManage.monthRentMoney' value='${loanCompManage.monthRentMoney}'>元 --%>
						</td>
					</tr>
					
					<tr>
						<td colspan="2"><label class="lab"><span class="red">*</span>经营地址：</label>
							<select class="select78 mr10" id="manageProvince"
								name="loanCompManage.manageAddressProvince" value="${applyInfoEx.loanCompManage.manageAddressProvince}">
									<option value="">请选择</option>
									<c:forEach var="item" items="${provinceList}" varStatus="status">
										<option value="${item.areaCode}"
											<c:if test="${applyInfoEx.loanCompManage.manageAddressProvince==item.areaCode}">selected=true</c:if>>${item.areaName}
										</option>
									</c:forEach>
							</select>
							-
							<select class="select78 mr10" id="manageCity" name="loanCompManage.manageAddressCity" value="${applyInfoEx.loanCompManage.manageAddressCity}">
									<option value="">请选择</option>
							</select>
							- 
							<select class="select78 mr10" id="manageArea" name="loanCompManage.manageAddressArea" value="${applyInfoEx.loanCompManage.manageAddressArea}">
									<option value="">请选择</option>
							</select> 
							<input type="hidden" id="manageCityHidden" value="${applyInfoEx.loanCompManage.manageAddressCity}" />
							<input type="hidden" id="manageArerHidden" value="${applyInfoEx.loanCompManage.manageAddressArea}" />
							<input name="loanCompManage.manageAddress" type="text" value="${applyInfoEx.loanCompManage.manageAddress}" class="input_text178" style="width:250px">
						</td>
					</tr>
				</table>
			  </div>
		<!-- <input type="button" class="btn btn-small" onclick="house.format();" value="序列化"/> -->
		<div class="tright mr10 mb5" >
			<input type="submit" id="manageNextBtn" class="btn btn-primary" value="保存" />
		</div>
		</form>
	</div>
</body>
</html>