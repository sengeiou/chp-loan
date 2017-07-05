<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<jsp:include page="../apply/applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/launch/coborrowerFormatNew.js"></script>
<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#coborrowNextBtn').bind('click',function(){
		 $(this).attr('disabled',true);
	     coborrower.format();
		 launch.updateApplyInfo('5','coborrowForm','coborrowNextBtn');
	}); 
	$('#addCoborrowBtn').bind('click',function(){
		var indexStr = $('#parentPanelIndex').val();
		var index = parseInt(indexStr)+1;
		launch.additionItem('contactPanel','_loanFlowNaturalGuarantorItem',index,'0',null);
		$('#parentPanelIndex').val(index);
	});
	//自然人保证人初始化
	coborrower.naturalGuarantorInit("storeReview");
});
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
		<li class="active"><a href="javascript:launch.getCustomerInfo_storeView('5');">自然人保证人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('6');">房产信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('8');">证件信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('9');">银行卡信息</a></li>
		<li style="width:auto;float:right;">
			<jsp:include page="./tright.jsp"/>
		</li>
	</ul>

	<form id="custInfoForm" method="post">
		<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}" id="customerCode">
		<input type="hidden" name="consultId" value="${workItem.bv.consultId}" id="consultId">
		<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}" id="loanCode" >
  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}" id = "coboApplyId">
 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }" id="response">
  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
 		<input type="hidden" name="token" value="${workItem.token}">
 		<input type="hidden" name="stepName" value="${workItem.stepName }">
 		<input type="hidden" name="flowName" value="${workItem.flowName }">
		<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
 		
 		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
 		<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
 		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
	</form>

	<div id="div4" class="content control-group ">
		<form id="coborrowForm" method="post">
			<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
			<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}">
			<input type="hidden" name="consultId" value="${workItem.bv.consultId}">
			<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}">
	  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
	  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	 		<input type="hidden" name="token" value="${workItem.token}">
	 		<input type="hidden" name="stepName" value="${workItem.stepName }">
	 		<input type="hidden" name="flowName" value="${workItem.flowName }">
			<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
	 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse}">
  			<input type="hidden" name="method" value="cobo">
  			<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
  			
  			<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"  id="loanCode" />
			<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"  id="custId" />
			
			<input type="hidden" value="${applyInfoEx.loanInfo.productType}" id="customerProductType"/>
			<!-- js校验时，区分申请和代办 -->
			<input type="hidden" id = "page" value="storeReview">
			
			<c:if test="${applyInfoEx.loanCoborrower!=null &&fn:length(applyInfoEx.loanCoborrower)>0}">
				<input type="hidden" value="${fn:length(applyInfoEx.loanCoborrower)}" id="parentPanelIndex" />
			</c:if>
			<c:if test="${applyInfoEx.loanCoborrower==null || fn:length(applyInfoEx.loanCoborrower)==0}">
				<input type="hidden" value="0" id="parentPanelIndex" />
			</c:if>
			<div style="text-align: left;">
				<input type="button" id="addCoborrowBtn" class="btn btn-small" value="增加自然人保证人" />
			</div>
			
			<c:if test="${applyInfoEx.loanCoborrower!=null && not empty applyInfoEx.loanCoborrower}">
				<div id="contactPanel">
					<c:forEach items="${applyInfoEx.loanCoborrower}" var="currCoborro" varStatus="coboStatus">
						<div class="contactPanel" id="contactPanel_${coboStatus.index}" index='${coboStatus.index}'>
							<div style="text-align: left;"  class="mt20">
								<input type="button" name="delCoborrower" index="${coboStatus.index}" coboId="${currCoborro.id}" value="删除自然人保证人" class="btn btn-small"></input>
							</div>
							<input type="hidden" class="coboId" name="loanCoborrower[${coboStatus.index}].id" value="${currCoborro.id}" />
							
							<h5 class="mt20">借款意愿</h5>
							<hr class="hr"/>
							<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td width="33%">
										<label class="lab">
											<span class="red">*</span>
											申请额度(元)：
										</label>
										<input type="hidden" id="limitLower_${coboStatus.index}"/>
      									<input type="hidden" id="limitUpper_${coboStatus.index}"/>	
      									<input type="hidden" id="consultTime" value="<fmt:formatDate value="${consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
										<input type="hidden" value="${currCoborro.loanInfoCoborrower.id}" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.id"/>
										<input type="text" value="<fmt:formatNumber value='${currCoborro.loanInfoCoborrower.loanApplyAmount}' pattern="##0.00"/>" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.loanApplyAmount" class="input_text178 required numCheck positiveNumCheck limitCheckCobo" />
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											产品类别：
										</label>
										<select id="productType_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.productType" class="select180 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${productList}" varStatus="status">
					  							<option value="${item.productCode }" <c:if test="${item.productCode==currCoborro.loanInfoCoborrower.productType}">selected=true</c:if>>
					  								${item.productName}
					  							</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											申请期限：
										</label>
										<span>
											<select id="loanMonths_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.loanMonths" value="${currCoborro.loanInfoCoborrower.loanMonths}" class="select180 required">
												<option value="">请选择</option>
											</select>
											<input type="hidden" id="loanMonthsRecord_${coboStatus.index}" value="${currCoborro.loanInfoCoborrower.loanMonths}"/>
										</span>
									</td>
								</tr>
								<tr>
									<td width="33%">
										<label class="lab">
											<span class="red">*</span>
											主要借款用途：
										</label>
										<select id="loanApplyAmount_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.borrowingPurposes" class="select2-container select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_loan_use')}" var="item">
												<option value="${item.value}" <c:if test="${currCoborro.loanInfoCoborrower.borrowingPurposes==item.value}">selected=true</c:if>>
													${item.label}
												</option>
											</c:forEach>
										</select>
										<input type="text" value="${currCoborro.loanInfoCoborrower.borrowingPurposesRemark}" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.borrowingPurposesRemark" class="input_text178 <c:if test="${ empty currCoborro.loanInfoCoborrower.borrowingPurposesRemark}"> collapse</c:if>"  maxlength="100"/>
									</td>
									<td colspan="2">
										<label class="lab">
											<span class="red">*</span>
											主要还款来源：
										</label>
										<select id="mainPaybackResource_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.mainPaybackResource" class="select2-container select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_repay_source_new')}" var="item">
												<option value="${item.value}" <c:if test="${currCoborro.loanInfoCoborrower.mainPaybackResource==item.value}">selected=true</c:if>>
													${item.label}
												</option>
											</c:forEach>
										</select>
										<input name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.mainPaybackResourceRemark" value="${currCoborro.loanInfoCoborrower.mainPaybackResourceRemark}" type="text" class="input_text178 <c:if test="${empty currCoborro.loanInfoCoborrower.mainPaybackResourceRemark}"> collapse </c:if>" maxlength="100"/>
									</td>
								</tr>
								<tr >
									<td rowspan="1">
										<label class="lab">
											<span class="red">*</span>
											最高可承受月还(元)：
										</label>
										<input name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.highPaybackMonthMoney" value="<fmt:formatNumber value='${currCoborro.loanInfoCoborrower.highPaybackMonthMoney}' pattern="##0.00"/>" type="text" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
									</td>
									<td colspan="2" rowspan="3">
										<label class="lab">
											备注：
										</label>
										<textarea class="textarea_refuse" name="loanCoborrower[${coboStatus.index}].loanInfoCoborrower.remarks" maxlength="450">${currCoborro.loanInfoCoborrower.remarks }</textarea>
									</td>
								</tr>
							</table>
							
							<h5 class="mt20">个人基本信息</h5>
							<hr class="hr"/>
							<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td width="33%">
										<label class="lab">
											<span class="red">*</span>
											姓名：
										</label>
										<input type="text" value="${currCoborro.coboName}" name="loanCoborrower[${coboStatus.index}].coboName" class="input_text178 required stringCheck" />
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											身份证号码：
										</label>
										<!--证件类型，默认为身份证 -->
										<input name="loanCoborrower[${coboStatus.index}].dictCertType" type="hidden" value="0"/>
										<input name="loanCoborrower[${coboStatus.index}].coboCertNum" onblur="javascript:launch.certifacteFormatByCertNum(this.value,this);" value="${currCoborro.coboCertNum}" type="text" class="input_text178 required coboCertCheckCopmany coboCertNumCheck2 currentPageDuplicateCertNumCheck" />
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											证件有效期：
										</label>
										<span>
											<input id="idStartDay_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].idStartDay" value="<fmt:formatDate value='${currCoborro.idStartDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay_${coboStatus.index}\')}'})" style="cursor: pointer" />
											-
											<input id="idEndDay_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].idEndDay" value="<fmt:formatDate value='${currCoborro.idEndDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10" <c:if test="${currCoborro.idStartDay!=null && currCoborro.idEndDay==null}"> readOnly=true style='display:none' </c:if> onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay_${coboStatus.index}\')}'})" style="cursor: pointer" />
											<input type="checkbox" id="longTerm_${coboStatus.index}" name="longTerm_${coboStatus.index}" value="1" class="coboEffeCertificate" onclick="launch.idEffectiveDay(this,'idEndDay_${coboStatus.index}');" <c:if test="${currCoborro.idStartDay!=null && currCoborro.idEndDay==null}">checked=true</c:if>>
											长期
											</input>
										</span>
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab">
											<span class="red">*</span>
											性别：
										</label>
										<span>
											<c:forEach items="${fns:getNewDictList('jk_sex')}" var="item">
												<input type="radio" name="loanCoborrower[${coboStatus.index}].coboSex" class="required" <c:if test="${currCoborro.coboSex==item.value}">checked=true</c:if> value="${item.value}">${item.label}</input>
											</c:forEach>
										</span>
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											教育程度：
										</label>
										<select id="dictEducation_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].dictEducation" class="select2-container select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_degree')}" var="curEdu">
												<option value="${curEdu.value}" <c:if test="${currCoborro.dictEducation==curEdu.value}"> selected = true </c:if>>${curEdu.label}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											婚姻状况：
										</label>
										<select name="loanCoborrower[${coboStatus.index}].dictMarry" class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_marriage')}" var="item">
												<c:if test="${item.label!='空'}">
													<option value="${item.value}" <c:if test="${currCoborro.dictMarry==item.value}">selected=true</c:if>>${item.label}</option>
												</c:if>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab"><span class="red">*</span>子女人数(人)：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].childrenNum" value="${currCoborro.childrenNum}" class="input_text178 required positiveInteger" maxlength="3"/>
									</td>
									<td>
										<label class="lab"><span class="red">*</span>供养人数(人)：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].supportNum" value="${currCoborro.supportNum}" class="input_text178 required positiveInteger" maxlength="3"/>
									</td>
									<td>
										<label class="lab"><span class="red">*</span>个人年收入(万元)：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].personalYearIncome" value="${currCoborro.personalYearIncome}" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab"><span class="red">*</span>家庭月收入(元)：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].homeMonthIncome" value="${currCoborro.homeMonthIncome}" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
									</td>
									<td>
										<label class="lab"><span class="red">*</span>家庭月支出(元)：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].homeMonthPay" value="${currCoborro.homeMonthPay}" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
									</td>
									<td>
										<label class="lab"><span class="red">*</span>家庭总负债(万元)：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].homeTotalDebt" value="${currCoborro.homeTotalDebt}" class="input_text178 required number numCheck positiveNumCheck " min="0" max="999999999.00"/>
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab"><span class="red">*</span>初来本市时间：</label>
										<input id="firstArriveYear_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].customerFirtArriveYear" value="${currCoborro.customerFirtArriveYear}" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
									</td>
									<td>
										<label class="lab"><span class="red">*</span>现住宅起始居住日期：</label>
										<input id="customerFirtLivingDay_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].customerFirstLivingDay" value="<fmt:formatDate value='${currCoborro.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
									</td>
									<td>
										<label class="lab"><span class="red"></span>宅电：</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].coboFamilyTel" value="${currCoborro.coboFamilyTel}" class="input_text178 isTel" />
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<label class="lab">
											<span class="red">*</span>
											现居住地址：
										</label>
										<select class="select78  required coboLiveingProvince" id="coboLiveingProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingProvince">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}" varStatus="status">
												<option value="${item.areaCode}" <c:if test="${item.areaCode==currCoborro.coboLiveingProvince}"> selected=true </c:if>>${item.areaName}</option>
											</c:forEach>
										</select>
										-
										<select class="select78  required coboLiveingCity" id="coboLiveingCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingCity">
											<option value="">请选择</option>
										</select>
										-
										<select class="select78  required coboLiveingArea" id="coboLiveingArea_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingArea">
											<option value="">请选择</option>
										</select>
										<input type="hidden" id="coboLiveingCityHidden_${coboStatus.index}" value="${currCoborro.coboLiveingCity}" />
										<input type="hidden" id="coboLiveingAreaHidden_${coboStatus.index}" value="${currCoborro.coboLiveingArea}" />
										<input name="loanCoborrower[${coboStatus.index}].coboNowAddress" value="${currCoborro.coboNowAddress}" type="text" class="input_text178 required" maxlength="100" style="width: 250px" />
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											住宅类别：
										</label>
										<select name="loanCoborrower[${coboStatus.index}].customerHouseHoldProperty" value="${currCoborro.customerHouseHoldProperty}" class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_house_nature')}" var="item">
												<option value="${item.value}" <c:if test="${item.value==currCoborro.customerHouseHoldProperty}">selected=true</c:if>>${item.label}</option>
											</c:forEach>
										</select>
										<input type="text" class="input_text178 <c:if test='${empty currCoborro.residentialCategoryRemark}'> collapse </c:if>" name="loanCoborrower[${coboStatus.index}].residentialCategoryRemark" value="${currCoborro.residentialCategoryRemark}" maxlength="100"/>
									</td>
									<td>
										<label class="lab">
											社保卡号：
										</label>
										<input type="text" class="input_text178 isSocial" name="loanCoborrower[${coboStatus.index}].socialSecurityNumber" value="${currCoborro.socialSecurityNumber}" maxlength="20"/>
									</td>
									<td>
										<label class="lab">
											<span class="red">*</span>
											常用手机号：
										</label>
										<input name="loanCoborrower[${coboStatus.index}].coboMobile" value="${currCoborro.coboMobile}" type="text" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2" />
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<label class="lab">
											<span class="red">*</span>
											户籍地址：
										</label>
										<select class="select78 required coboHouseholdProvince" id="coboHouseholdProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdProvince">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}" varStatus="status">
												<option value="${item.areaCode }" <c:if test="${item.areaCode==currCoborro.coboHouseholdProvince}">selected = true</c:if>>${item.areaName}</option>
											</c:forEach>
										</select>
										-
										<select class="select78  required coboHouseholdCity" id="coboHouseholdCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdCity">
											<option value="">请选择</option>
										</select>
										-
										<select class="select78 required coboHouseholdArea" id="coboHouseholdArea_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdArea">
											<option value="">请选择</option>
										</select>
										<input type="hidden" id="coboHouseholdCityHidden_${coboStatus.index}" value="${currCoborro.coboHouseholdCity}" />
										<input type="hidden" id="coboHouseholdAreaHidden_${coboStatus.index}" value="${currCoborro.coboHouseholdArea}" />
										<input name="loanCoborrower[${coboStatus.index}].coboHouseholdAddress" value="${currCoborro.coboHouseholdAddress}" type="text" class="input_text178 required" style="width: 250px" maxlength="100"/>
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											户籍性质：
										</label>
										<select name="loanCoborrower[${coboStatus.index}].registerProperty" class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_register_property')}" var="item">
												<option value="${item.value}" <c:if test="${item.value==currCoborro.registerProperty}">selected=true</c:if>>${item.label}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											邮箱：
										</label>
										<input type="text" class="input_text178 required isEmail" name="loanCoborrower[${coboStatus.index}].coboEmail" value="${currCoborro.coboEmail}" />
									</td>
									<td class="loanCoborrower_issuingAuthority" style="display:none;">
										<label class="lab"><span style="color: red">*</span>签发机关：</label>
										<input name="loanCoborrower[${coboStatus.index}].issuingAuthority" type="text" value="${currCoborro.issuingAuthority}" class="input_text178 required" maxlength="100"/>
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab">微博：</label>
										<input name="loanCoborrower[${coboStatus.index}].coboWeibo" value="${currCoborro.coboWeibo}" type="text" class="input_text178 maxLength50" />
									</td>
									<td>
										<label class="lab">QQ：</label>
										<input name="loanCoborrower[${coboStatus.index}].coboQq" value="${currCoborro.coboQq}" type="text" class="input_text178 isQQ maxLength20" />
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab">
											征信用户名：
										</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].creditUserName" value="${currCoborro.creditUserName}" class="input_text178 maxLength20" />
									</td>
									<td>
										<label class="lab">
											密码：
										</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].creditPassword" value="${currCoborro.creditPassword}" class="input_text178 maxLength20" />
									</td>
									<td>
										<label class="lab">
											身份验证码：
										</label>
										<input type="text" name="loanCoborrower[${coboStatus.index}].creditAuthCode" value="${currCoborro.creditAuthCode}" class="input_text178 maxLength20" />
									</td>
								</tr>
							</table>
							
							<h5 class="mt20">工作信息</h5>
							<hr class="hr"/>	
							<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
								<tr>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											单位名称：
										</label>
										<input name="loanCoborrower[${coboStatus.index}].coboCompany.id" value="${currCoborro.coboCompany.id}" type="hidden"/>
										<input name="loanCoborrower[${coboStatus.index}].coboCompany.compName" value="${currCoborro.coboCompany.compName}" type="text" class="input_text178 required"/>
									</td>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											单位电话：
										</label>
										<input name="loanCoborrower[${coboStatus.index}].coboCompany.compTel" value="${currCoborro.coboCompany.compTel}" type="text" class="input_text178 required isTel" />
										-
										<input style="width: 50px" type="text" max="9999.00" name="loanCoborrower[${coboStatus.index}].coboCompany.compTelExtension" value="${currCoborro.coboCompany.compTelExtension}" class="input_text178 positiveInteger" />
									</td>
								</tr>
								<tr>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											月税后工资(元)：
										</label>
										<input name="loanCoborrower[${coboStatus.index}].coboCompany.compSalary" value="<fmt:formatNumber value='${currCoborro.coboCompany.compSalary}' pattern="##0.00"/>" type="text" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
									</td>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											每月发薪日期(日)：
										</label>
										<input name="loanCoborrower[${coboStatus.index}].coboCompany.compSalaryDay" value="${currCoborro.coboCompany.compSalaryDay}" type="text" class="input_text178 required day" />
									</td>
									<td>
										<label class="lab">
											<span style="color: red">*</span>
											主要发薪方式：
										</label>
										<select name="loanCoborrower[${coboStatus.index}].coboCompany.dictSalaryPay" class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getNewDictList('jk_paysalary_way')}" var="item">
												<option value="${item.value}" <c:if test="${item.value==currCoborro.coboCompany.dictSalaryPay}">selected=true</c:if>>
													${item.label}
												</option>
											</c:forEach>
										</select>
									</td>
								</tr>
								<tr>
									<td colspan="3">
										<label class="lab">
											<span class="red">*</span>
											单位地址：
										</label>
										<select class="select78  required coboCompProvince" id="coboCompProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompany.compProvince">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}" varStatus="status">
												<option value="${item.areaCode}" <c:if test="${item.areaCode==currCoborro.coboCompany.compProvince}">selected=true</c:if>>${item.areaName}</option>
											</c:forEach>
										</select>
										-
										<select class="select78  required coboCompCity" id="coboCompCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompany.compCity">
											<option value="">请选择</option>
										</select>
										-
										<select class="select78  required coboCompArer" id="coboCompArer_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompany.compArer">
											<option value="">请选择</option>
										</select>
										<input type="hidden" id="compCityHidden_${coboStatus.index}" value="${currCoborro.coboCompany.compCity}" />
										<input type="hidden" id="compArerHidden_${coboStatus.index}" value="${currCoborro.coboCompany.compArer}" />
										<input name="loanCoborrower[${coboStatus.index}].coboCompany.compAddress" value="${currCoborro.coboCompany.compAddress}" type="text" class="input_text178 required"  maxlength="100"  style="width: 250px" />
									</td>
								</tr>
							</table>
							<h5 class="mt20">联系人信息</h5>
							<hr class="hr"/>
							
							<div class="mt20 ml48">
								<h5 class="l mt5">亲属</h5>
								<input type="button" class="btn btn-small ml20" value="增加" name="addCobContactBtn" tableId='relatives_contact_table_${coboStatus.index}' parentIndex='${coboStatus.index}' />
							</div> 
							<table currIndex="${fn:length(currCoborro.relativesContactList) == 0 ? '0' : fn:length(currCoborro.relativesContactList) }" id="relatives_contact_table_${coboStatus.index}" class="table  table-bordered table-condensed  mt20 ml48" style="width: 90%">
								<thead>
									<tr>
										<th><span class="red">*</span>姓名</th>
										<th><span class="red">*</span>关系</th>
										<th>身份证号码</th>
										<th>单位名称</th>
										<th>宅电</th>
										<th>手机号码</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty currCoborro.relativesContactList }">
										<c:forEach items="${currCoborro.relativesContactList}" var="relativesContact" varStatus="relatives">
											<tr>
												<td>
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" value="0" />
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id" value="${relativesContact.id}" />
													<input id = "relativesContact_${coboStatus.index}_contactName_${relatives.index}"  type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" value="${relativesContact.contactName}" class="input_text70 required stringCheck contactCheck" />
												</td>
												<td>
													<select id="relativesContact_${coboStatus.index}_contactRelation_${relatives.index}" index="1${coboStatus.index}${relatives.index}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
														<option value="">请选择</option>
														<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
															<option value="${item.value}" <c:if test="${relativesContact.contactRelation==item.value}">selected=true</c:if> class="${item.id}">${item.label}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<input id = "relativesContact_${coboStatus.index}_certNum_${relatives.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.certNum" value="${relativesContact.certNum}" class="input_text178 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
												</td>
												<td>
													<input id = "relativesContact_${coboStatus.index}_contactSex_${relatives.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactSex" value="${relativesContact.contactSex}" class="input_text178" />
												</td>
												<td>
													<input id = "relativesContact_${coboStatus.index}_homeTel_${relatives.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.homeTel" value="${relativesContact.homeTel}" class="input_text178 isTel mobileAndTelNeedOne" />
												</td>
												<td>
													<input id = "relativesContact_${coboStatus.index}_contactMobile_${relatives.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" value="${relativesContact.contactMobile}" class="input_text178 isMobile contactMobileDiff1 mobileAndTelNeedOne" />
												</td>
												<td class="tcenter">
													<c:if test="${relatives.index>0}">
														<input type="button" class="btn_edit" value="删除" onclick="contact.delConCoboByReturn(this,'relatives_contact_table_${coboStatus.index}','CONTACT')" style="width: 50px;" />
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty currCoborro.relativesContactList }">
										<tr>
											<td>
												<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" value="0" />
												<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id" />
												<input id = "relativesContact_${coboStatus.index}_contactName_0" type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
											</td>
											<td>
												<select id="relativesContact_${coboStatus.index}_contactRelation_0" index="1${coboStatus.index}0" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
													<option value="">请选择</option>
													<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
														<option value="${item.value}" class="${item.id}">${item.label}</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<input id="relativesContact_${coboStatus.index}_certNum_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.certNum" class="input_text178 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
											</td>
											<td>
												<input id="relativesContact_${coboStatus.index}_contactSex_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactSex" class="input_text178" />
											</td>
											<td>
												<input id="relativesContact_${coboStatus.index}_homeTel_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.homeTel" class="input_text178 isTel mobileAndTelNeedOne" />
											</td>
											<td>
												<input id="relativesContact_${coboStatus.index}_contactMobile_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" class="input_text178 isMobile mobileAndTelNeedOne contactMobileDiff1" />
											</td>
											<td class="tcenter"></td>
										</tr>
									</c:if>
								</tbody>
							</table>
							
							<div class="mt20 ml48">
								<h5 class="l mt5">工作联系人</h5>
								<input type="button" class="btn btn-small ml20" value="增加" name="addCobContactBtn" tableId='worktogether_contact_table_${coboStatus.index}' parentIndex='${coboStatus.index}' />
							</div> 
							<table currIndex="${fn:length(currCoborro.workTogetherContactList) == 0 ? '0' : fn:length(currCoborro.workTogetherContactList) }" id="worktogether_contact_table_${coboStatus.index}" class="table  table-bordered table-condensed  mt20 ml48" style="width: 90%">
								<thead>
									<tr>
										<th><span class="red">*</span>姓名</th>
										<th><span class="red">*</span>部门</th>
										<th><span class="red">*</span>手机号码</th>
										<th><span class="red">*</span>职务</th>
										<th><span class="red">*</span>关系</th>
										<th>备注</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty currCoborro.workTogetherContactList }">
										<c:forEach items="${currCoborro.workTogetherContactList}" var="workTogetherContact" varStatus="workTogether">
											<tr>
												<td>
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" value="1" />
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id" value="${workTogetherContact.id}"></input>
													<input id="workTogetherContact_${coboStatus.index}_contactName_${workTogether.index}" type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" value="${workTogetherContact.contactName}" class="input_text70 required stringCheck contactCheck" />
												</td>
												<td>
													<input id="workTogetherContact_${coboStatus.index}_department_${workTogether.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.department" value="${workTogetherContact.department}" class="input_text178 required" maxlength="20"/>
												</td>
												<td>
													<input id="workTogetherContact_${coboStatus.index}_contactMobile_${workTogether.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" value="${workTogetherContact.contactMobile}" class="input_text178 required isMobile contactMobileDiff1" />
												</td>
												<td>
													<input id="workTogetherContact_${coboStatus.index}_post_${workTogether.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.post" value="${workTogetherContact.post}" class="input_text178 required" maxlength="20"/>
												</td>
												
												<td>
													<select id="workTogetherContact_${coboStatus.index}_contactRelation_${workTogether.index}" index="2${coboStatus.index}${workTogether.index}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
														<option value="">请选择</option>
														<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
															<option value="${item.value}" <c:if test="${workTogetherContact.contactRelation==item.value}">selected=true</c:if> class="${item.id}">${item.label}</option>
														</c:forEach>
													</select>
												</td>
												<td>
													<input id="workTogetherContact_${coboStatus.index}_remarks_${workTogether.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.remarks" value="${workTogetherContact.remarks}" class="input_text178" maxlength="100"/>
												</td>
												<td class="tcenter">
													<c:if test="${workTogether.index>0}">
														<input type="button" class="btn_edit" value="删除" onclick="contact.delConCoboByReturn(this,'worktogether_contact_table_${coboStatus.index}','CONTACT')" style="width: 50px;" />
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty currCoborro.workTogetherContactList }">
										<tr>
											<td>
												<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" value="1" />
												<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id"></input>
												<input id="workTogetherContact_${coboStatus.index}_contactName_0" type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
											</td>
											<td>
												<input id="workTogetherContact_${coboStatus.index}_department_0"  type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.department" class="input_text178 required" maxlength="20"/>
											</td>
											<td>
												<input id="workTogetherContact_${coboStatus.index}_contactMobile_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" class="input_text178 required isMobile contactMobileDiff1" />
											</td>
											<td>
												<input id="workTogetherContact_${coboStatus.index}_post_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.post" class="input_text178 required" maxlength="20"/>
											</td>
											
											<td>
												<select id="workTogetherContact_${coboStatus.index}_contactRelation_0" index="2${coboStatus.index}0" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
													<option value="">请选择</option>
													<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
														<option value="${item.value}" class="${item.id}">${item.label}</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<input id="workTogetherContact_${coboStatus.index}_remarks_0" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.remarks" class="input_text178" maxlength="100"/>
											</td>
											<td class="tcenter"></td>
										</tr>
									</c:if>
								</tbody>
							</table>
							
							<div class="mt20 ml48">
								<h5 class="l mt5">其他</h5>
								<input type="button" class="btn btn-small ml20" value="增加" name="addCobContactBtn" tableId='other_contact_table_${coboStatus.index}' parentIndex='${coboStatus.index}' />
							</div> 
							<table currIndex="${fn:length(currCoborro.otherContactList) == 0 ? '0' : fn:length(currCoborro.otherContactList) }" id="other_contact_table_${coboStatus.index}" class="table  table-bordered table-condensed  mt20 ml48" style="width: 90%">
								<thead>
									<tr>
										<th><span class="red">*</span>姓名</th>
										<th><span class="red">*</span>关系</th>
										<th>宅电</th>
										<th><span class="red">*</span>手机号码</th>
										<th>操作</th>
									</tr>
								</thead>
								<tbody>
									<c:if test="${not empty currCoborro.otherContactList }">
										<c:forEach items="${currCoborro.otherContactList}" var="otherContact" varStatus="other">
											<tr>
												<td>
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" value="2" />
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id" value="${otherContact.id}"></input>
													<input id="otherContact_${coboStatus.index}_contactName_${other.index}" type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" value="${otherContact.contactName}" class="input_text70 required stringCheck contactCheck" />
												</td>
												<td>
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" value="3">
													<input id="otherContact_${coboStatus.index}_remarks_${other.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.remarks" value="${otherContact.remarks}" class="input_text178 required chineseCheck2" maxlength="100"/>
												</td>
												<td>
													<input id="otherContact_${coboStatus.index}_homeTel_${other.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.homeTel" value="${otherContact.homeTel}" class="input_text178 isTel" />
												</td>
												<td>
													<input id="otherContact_${coboStatus.index}_contactMobile_${other.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" value="${otherContact.contactMobile}" class="input_text178 required isMobile contactMobileDiff1" />
												</td>
												<td class="tcenter">
													<c:if test="${workItem.bv.isBorrow ne 1}">
														<c:set var="otherContactDelRow" value="1"/>
													</c:if>
													<c:if test="${workItem.bv.isBorrow eq 1}">
														<c:set var="otherContactDelRow" value="0"/>
													</c:if>
													<c:if test="${other.index>otherContactDelRow}">
														<input type="button" class="btn_edit" value="删除" onclick="contact.delConCoboByReturn(this,'other_contact_table_${coboStatus.index}','CONTACT')" style="width: 50px;" />
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
									<c:if test="${empty currCoborro.otherContactList }">
										<c:if test="${workItem.bv.isBorrow ne 1}">
											<c:set var="otherContactEnd" value="1"/>
										</c:if>
										<c:if test="${workItem.bv.isBorrow eq 1}">
											<c:set var="otherContactEnd" value="0"/>
										</c:if>
										<c:forEach  varStatus="other" begin="${fn:length(currCoborro.otherContactList)}" end="${otherContactEnd}">
											<tr>
												<td>
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" value="2" />
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id"></input>
													<input id="otherContact_${coboStatus.index}_contactName_${other.index}" type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
												</td>
												<td>
													<input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" value="3">
													<input id="otherContact_${coboStatus.index}_remarks_${other.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.remarks" class="input_text178 required chineseCheck2" maxlength="100"/>
												</td>
												<td>
													<input id="otherContact_${coboStatus.index}_homeTel_${other.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.homeTel" class="input_text178 isTel" />
												</td>
												<td>
													<input id="otherContact_${coboStatus.index}_contactMobile_${other.index}" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" class="input_text178 required isMobile contactMobileDiff1" />
												</td>
												<td class="tcenter"></td>
											</tr>
										</c:forEach>	
									</c:if>
							</table>
							<table class="table1 mt20 ml48" style="width: 90%">
								<tr>
									<td colspan="3">
										以上可知晓本次借款的联系人&nbsp;&nbsp;
										<span>
											<c:forEach items="${fns:getNewDictList('jk_who_can_know_the_borrowing')}" var="item">
												<c:if test="${item.value != '1'}">
													<input type="checkbox" name="loanCoborrower[${coboStatus.index}].whoCanKnowTheBorrowing" value="${item.value}" <c:if test="${fn:contains(currCoborro.whoCanKnowTheBorrowing, item.value)}">checked = 'true'</c:if> class="required"/>${item.label}
												</c:if>
											</c:forEach>
										</span>
										<input type="text" name="loanCoborrower[${coboStatus.index}].whoCanKnowTheBorrowingRemark" value="${currCoborro.whoCanKnowTheBorrowingRemark }" class="input_text178 chineseCheck2 <c:if test='${empty currCoborro.whoCanKnowTheBorrowingRemark}'> collapse </c:if>" maxlength="100"/>
									</td>
								</tr>
							</table>
						</div>
					</c:forEach>
				</div>
			</c:if>
			<div class="tright mr10 mb5">
				<input type="submit" id="coborrowNextBtn" class="btn btn-primary" value="保存" />
			</div>
		</form>	
	</div>
</body>
</html>