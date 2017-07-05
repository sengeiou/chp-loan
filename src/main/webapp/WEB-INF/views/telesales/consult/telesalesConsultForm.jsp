<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js?v=1"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#mateCertNum').bind('blur', function() {
			$('#flag').val('');
			$('#blackTip').html('');
			$('#message').val('');
			var mateCertNum = $('#mateCertNum').val();
			var cardType = $('#cardType option:selected').val();
			var cardTypeName = $('#cardType option:selected').text();
			consult.certifacteFormat('cardType', 'mateCertNum'); // 身份尾号X格式化
			queryBlackLog(cardType, mateCertNum);
			if (mateCertNum != '' && cardType != '' && cardTypeName == '身份证') {
				consult.setSexByIdentityCode(mateCertNum);
			}
			// 表明这个是白名单
			if ($('#flag').val() == '1' && mateCertNum != '' && cardType != '') {
				queryCustomerBaseByCertNum(cardType, mateCertNum);
			}
		});
		$('#longTerm').bind('click', function() {
			if ($(this).attr('checked') == true || $(this).attr('checked') == 'checked') {
				$('#idEndDay').val('');
				$('#idEndDay').attr('readOnly', true);
				$('#idEndDay').hide();
			} else {
				$('#idEndDay').removeAttr('readOnly');
				$('#idEndDay').show();
			}
		});
		$('#submitTelesalesBtn').bind('click', function() {
			if ($('#flag').val() == '0' || $('#flag').val() == '2') {
				art.dialog.alert($('#message').val());
				return false;
			} else {
				consult.datavalidate();
			}
		});
		loan.getstorelsit("storeName", "storeCode", "1");
		$('#idStartDay').bind('blur', function() {
			if ($('#mateCertNum').val() != null && $('#mateCertNum').val() != '') {
				var birthday = parseInt($('#mateCertNum').val().substring(6, 14));
				var idStartDay = parseInt($('#idStartDay').val().replace('-', '').replace('-', ''));
				if (idStartDay < birthday) {
					art.dialog.alert("证件有效期的起始时间在出生年月之前，请重新选择");
				}
			}
		})
		
		//初始化
		var dictLoanUseVal = $("#dictLoanUse").val();
		var remark = $("#dictLoanUseRemark");
		if (dictLoanUseVal == '12') {
			remark.show();
			remark.addClass("required");
		} else {
			remark.hide();
			remark.val("");
			remark.removeClass("required");
		}
		
		//借款用途 其他选择，备注必须填写
		$("#dictLoanUse").bind("change", function() {
			var remark = $("#dictLoanUseRemark");
			if ($(this).val() == '12') {
				remark.show();
				remark.addClass("required");
			} else {
				remark.hide();
				remark.val("");
				remark.removeClass("required");
			}
		});
	});
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="consult" action="${ctx}/telesales/consult/saveTelesalesInfo" method="post" class="form-horizontal">
		<h2 class=" f14 pl10 ">基本信息</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							姓名：
						</label>
						<form:input path="customerBaseInfo.customerName" id="customerName" maxlength="30" class="input_text178" />
						<form:hidden path="customerBaseInfo.customerCode" id="customerCode" />
						<input type="hidden" id="flag" />
						<input type="hidden" id="message" />
					</td>
					<td>
						<label class="lab">
							<span style="color: #ff0000">*</span>
							行业类型：
						</label>
						<form:select id="dictCompIndustry" path="customerBaseInfo.dictCompIndustry" class="required select180">
							<option value="">请选择</option>
							<form:options items="${fns:getNewDictList('jk_industry_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<%-- <td style="width: 32%"><label class="lab"><span
							style="color: #ff0000">*</span>手机号码：</label> <form:input
							path="customerBaseInfo.customerMobilePhone"
							class="required input_text178" id="customerMobilePhone" maxlength="11"/></td> --%>
					<td style="width: 32%">
						<label class="lab">宅电：</label>
						<form:input path="customerBaseInfo.areaNo" id="areaNo" class="input_text50" maxlength="4" />
						-
						<form:input path="customerBaseInfo.telephoneNo" id="telephoneNo" class="input_text178" maxlength="8" />
					</td>
				</tr>
				<tr>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							证件类型：
						</label>
						<select id="cardType" readonly="readonly" class="required select180" disabled="disabled">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_certificate_type')}" var="item">
								<option value="${item.value}" <c:if test="${item.label=='身份证'}">
								     selected=true
								  </c:if>>${item.label}</option>
							</c:forEach>
						</select>
						<!-- 此处写死，0表示身份证 -->
						<input type="hidden" name="customerBaseInfo.dictCertType" value="0" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							证件号码：
						</label>
						<form:input path="customerBaseInfo.mateCertNum" class="required input_text178" id="mateCertNum" />
						<span id="blackTip" style="color: red;"></span>
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							证件有效期：
						</label>
						<input id="idStartDay" name="customerBaseInfo.idStartDay" type="text" class="input_text70 required Wdate" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" required />
						-
						<input id="idEndDay" name="customerBaseInfo.idEndDay" type="text" class="input_text70 Wdate" size="10" onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" required />
						<form:checkbox path="longTerm" id="longTerm" value="1" />
						长期
					</td>
				</tr>
				<tr>
					<%-- <td rowspan="2"><label class="lab"><span
							style="color: #ff0000">*</span>行业类型：</label> <form:select
							id="dictCompIndustry" path="customerBaseInfo.dictCompIndustry"
							class="required select180">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('jk_industry_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td> --%>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							性别：
						</label>
						<form:radiobuttons path="customerBaseInfo.customerSex" id="sex" cssClass="customerSex required" items="${fns:getDictList('jk_sex')}" itemLabel="label" itemValue="value" delimiter="&nbsp;" />
					</td>
				</tr>
			</table>
		</div>
		<h2 class=" f14 pl10 mt20">信借信息</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							电销专员：
						</label>
						<form:input path="managerName" disabled="true" class="input_text178 required" />
						<form:hidden path="managerCode" />
						<form:hidden path="teleSalesOrgid" />
						<form:hidden path="loanTeamEmpcode" />
						<form:hidden path="sceneManagerCode" />
						<form:hidden path="loanTeamOrgId" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							门店：
						</label>
						<form:input path="storeName" id="storeName" class="input_text178" cssClass="required" readonly="true" value="${secret.store}" />
						<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<form:hidden path="storeCode" id="storeCode" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							信借类型：
						</label>
						<form:radiobutton path="dictLoanType" value="1" cssClass="required" />
						个人信用借款
					</td>
				</tr>
				<tr>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							电销来源：
						</label>
						<form:select id="consTelesalesSource" path="consTelesalesSource" class="select180 required">
							<option value="">请选择</option>
							<form:options items="${fns:getDictList('jk_rs_src')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<td>
						<label class="lab">计划借款金额：</label>
						<form:input path="loanApplyMoney" id="loanApplyMoney" htmlEscape="false" maxlength="50" class="input_text178" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							主要借款用途：
						</label>
						<form:select id="dictLoanUse" path="dictLoanUse" class="select180 required">
							<option value="">请选择</option>
							<form:options items="${fns:getNewDictList('jk_loan_use')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<form:input path="dictLoanUseRemark" id="dictLoanUseRemark" htmlEscape="false" maxlength="100" class="input_text178 collapse" />
					</td>
				</tr>
				<tr>
					<td style="width: 32%">
						<label class="lab">借款来源：</label>
						<form:checkbox path="isBorrow" id="isBorrow" value="1" />
						借么APP
					</td>
				</tr>
			</table>
		</div>
		<h2 class=" f14 pl10 mt20">沟通记录及下一步操作</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<%-- <td style="width: 32%"><label class="lab">沟通记录：</label> <form:input
							id="consLoanRecord"  path="consultRecord.consLoanRecord" class="textarea_refuse" maxlength="490" rows="10" cols="50"/></td>
					--%>
					<td style="width: 32%">
						<label class="lab">沟通记录：</label>
						<form:textarea id="consLoanRecord" path="consultRecord.consLoanRecord" class="textarea_refuse" maxlength="490" rows="10" cols="50" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							下一步：
						</label>
						<form:select id="dictOperStatus" path="consultRecord.consOperStatus" class="required select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_rs_status')}" var="item">
								<c:if test="${item.value=='0' || item.value=='1' || item.value=='2' || item.value=='5'}">
									<option value="${item.value}">${item.label}</option>
								</c:if>
							</c:forEach>
						</form:select>
					</td>
					<td style="width: 32%"></td>
				</tr>
			</table>
		</div>
		<div class="tright mt10 pr30">
			<input type="submit" id="submitTelesalesBtn" class="btn btn-primary" value="提交"></input>
		</div>
	</form:form>
</body>
</html>