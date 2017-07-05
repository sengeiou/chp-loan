<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<jsp:include page="./applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/launch/company.js"></script>
<script type="text/javascript"> 
$(document).ready(function(){
	$('#companyNextBtn').bind('click',function(){
		$(this).attr('disabled',true);
	 	launch.saveApplyInfo('3','companyForm','companyNextBtn'); 
	});
	company.init();
	//为农信借时，字段为非必填
	var productCode=$("#customerProductType").val();
	if(productCode!=null && productCode!=''){
		if(productCode=='A021'){
			//alert(productCode);
			$(".red").hide(); 
			$("input[name='customerLoanCompany.compName']").removeClass("required");
			$("input[name='customerLoanCompany.compTel']").removeClass("required");
			$("select[name='customerLoanCompany.dictCompIndustry']").removeClass("required");
			$("select[name='customerLoanCompany.compProvince']").removeClass("required");
			$("select[name='customerLoanCompany.compCity']").removeClass("required");
			$("select[name='customerLoanCompany.compArer']").removeClass("required");
			$("input[name='customerLoanCompany.compAddress']").removeClass("required");
			$("input[name='customerLoanCompany.previousCompName']").removeClass("required");
			$("select[name='customerLoanCompany.dictCompType']").removeClass("required");
			$("select[name='customerLoanCompany.compPostLevel']").removeClass("required");
			$("input[name='customerLoanCompany.compEntryDay']").removeClass("required");
			$("input[name='customerLoanCompany.compUnitScale']").removeClass("required");
			$("input[name='customerLoanCompany.compDepartment']").removeClass("required");
			$("input[name='customerLoanCompany.compPost']").removeClass("required");
			$("input[name='customerLoanCompany.compSalaryDay']").removeClass("required day");
			$("select[name='customerLoanCompany.dictSalaryPay']").removeClass("required");
		}
	}
});
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
	  	<li><a href="javascript:launch.getCustomerInfo('1');">个人基本信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('2');">借款意愿</a></li>
		<li class="active"><a href="javascript:launch.getCustomerInfo('3');">工作信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('4');">联系人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('5');">自然人保证人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('6');">房产信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('8');">证件信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('9');">银行卡信息</a></li>
	</ul>

	<form id="custInfoForm" method="post">
	  <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	  <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}" name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}" name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	</form>
	<div id="div6" class="content control-group">
		<form id="companyForm" method="post">
			<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
			<input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId" />
			<input type="hidden" value="${workItem.bv.defToken}" name="defToken" />
			<input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanInfo.loanCode" id="loanCode" />
			<input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanCode" />
			<input type="hidden" value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id" id="custId" />
			<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" />
			<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode" />
			<input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}" name="loanInfo.loanCustomerName" id="loanCustomerName" />
			<input type="hidden" value="${workItem.bv.loanCustomer.customerName}" name="loanCustomer.customerName" />
			<input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId" />
			<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
			<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
			<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
			<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
			
			<input type="hidden" value="${applyInfoEx.loanInfo.productType}" id="customerProductType"/>
			<input type="hidden" name="customerLoanCompany.id" value="${applyInfoEx.customerLoanCompany.id}" id="custCompId" />
			
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">
							<span class="red">*</span>
							单位名称：
						</label>
						<input type="text" name="customerLoanCompany.compName" value="${applyInfoEx.customerLoanCompany.compName}" class="input_text178 required" />
					</td>
					<td>
						<label class="lab">
							<span class="red">*</span>
							单位电话：
						</label>
						<input type="text" name="customerLoanCompany.compTel" value="${applyInfoEx.customerLoanCompany.compTel}" class="input_text178 isTel required" />
						-
						<input style="width: 50px" type="text" max="9999.00" name="customerLoanCompany.compTelExtension" value="${applyInfoEx.customerLoanCompany.compTelExtension}" class="input_text178 positiveInteger" />
					</td>
					<td>
						<label class="lab">
							<span style="color: red" class="red">*</span>
							所属行业：
						</label>
						<select class="select180 required" name="customerLoanCompany.dictCompIndustry" value="${applyInfoEx.customerLoanCompany.dictCompIndustry}">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_industry_type')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.customerLoanCompany.dictCompIndustry==item.value}"> selected=true </c:if>>
									${item.label}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td colspan="2" style="text-align: left;">
						<label class="lab">
							<span class="red">*</span>
							单位地址：
						</label>
						<select class="select78 mr10 required" id="compProvince" name="customerLoanCompany.compProvince" value="${applyInfoEx.customerLoanCompany.compProvince}">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}" varStatus="status">
								<option value="${item.areaCode}" <c:if test="${applyInfoEx.customerLoanCompany.compProvince==item.areaCode}">selected = true</c:if>>
					                ${item.areaName}
					            </option>
							</c:forEach>
						</select>
						-
						<select class="select78 mr10 required" id="compCity" name="customerLoanCompany.compCity" value="${applyInfoEx.customerLoanCompany.compCity}">
							<option value="">请选择</option>
						</select>
						-
						<select class="select78 mr10 required" id="compArer" name="customerLoanCompany.compArer" value="${applyInfoEx.customerLoanCompany.compArer}">
							<option value="">请选择</option>
						</select>
						<input type="hidden" id="compCityHidden" value="${applyInfoEx.customerLoanCompany.compCity}" />
						<input type="hidden" id="compArerHidden" value="${applyInfoEx.customerLoanCompany.compArer}" />
						<input type="text" name="customerLoanCompany.compAddress" value="${applyInfoEx.customerLoanCompany.compAddress}" class="input_text178 required maxLength100" style="width: 150px">
					</td>
					<td colspan="3">
						<label class="lab">
							<span style="color: red" class="red">*</span>
							前单位名称：
						</label>
						<input type="text" id="previousCompName" name="customerLoanCompany.previousCompName" value="${applyInfoEx.customerLoanCompany.previousCompName}" class="input_text178 required" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">
							<span class="red">*</span>
							单位性质：
						</label>
						<select name="customerLoanCompany.dictCompType" value="${applyInfoEx.customerLoanCompany.dictCompType}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_unit_type')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.customerLoanCompany.dictCompType==item.value}"> selected=true </c:if>>
									${item.label}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">
							<span class="red">*</span>
							职务级别：
						</label>
						<select id="compPostLevel" name="customerLoanCompany.compPostLevel" value="${applyInfoEx.customerLoanCompany.compPostLevel}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_job_type')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.customerLoanCompany.compPostLevel==item.value}">selected=true </c:if>>
									${item.label}
								</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">
							<span class="red">*</span>
							入职时间：
						</label>
						<input id="d4311" name="customerLoanCompany.compEntryDay" value="<fmt:formatDate value='${applyInfoEx.customerLoanCompany.compEntryDay}' pattern="yyyy-MM"/>" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d', dateFmt:'yyyy-MM' })" style="cursor: pointer" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">
							<span style="color: red" class="red">*</span>
							员工数量(人)：
						</label>
						<input type="text" id="compUnitScale" name="customerLoanCompany.compUnitScale" value="${applyInfoEx.customerLoanCompany.compUnitScale}" class="input_text178 required positiveInteger" max="999999999.00"/>
					</td>
					<td>
						<label class="lab">
							<span style="color: red" class="red">*</span>
							部门：
						</label>
						<input type="text" id="department" name="customerLoanCompany.compDepartment" value="${applyInfoEx.customerLoanCompany.compDepartment}" class="input_text178 required maxLength20" />
					</td>
					<td>
						<label class="lab">
							<span class="red">*</span>
							职务：
						</label>
						<input type="text" id="compPost" name="customerLoanCompany.compPost" value="${applyInfoEx.customerLoanCompany.compPost}" class="input_text178 required maxLength20" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">
							<span class="red">*</span>
							月税后工资(元)：
						</label>
						<input type="text" name="customerLoanCompany.compSalary" value="<fmt:formatNumber value='${applyInfoEx.customerLoanCompany.compSalary}' pattern="##0.00"/>" class="input_text178 numCheck positiveNumCheck" min="0.00" max="999999999.00"/>
					</td>
					<td>
						<label class="lab">
							<span class="red">*</span>
							每月发薪日期(日)：
						</label>
						<input type="text" name="customerLoanCompany.compSalaryDay" value="${applyInfoEx.customerLoanCompany.compSalaryDay}" class="input_text178 required day" />
					</td>
					<td>
						<label class="lab">
							<span class="red">*</span>
							主要发薪方式：
						</label>
						<select id="dictSalaryPay" name="customerLoanCompany.dictSalaryPay" value="${applyInfoEx.customerLoanCompany.dictSalaryPay}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_paysalary_way')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.customerLoanCompany.dictSalaryPay==item.value}">selected=true</c:if>>
									${item.label}
								</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class=" tright mr10 mb5">
				<input type="submit" id="companyNextBtn" class="btn btn-primary" value="下一步" />
			</div>
		</form>
	</div>
</body>
</html>