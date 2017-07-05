<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js?v=1"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#idStartDay').on('blur', function() {
			var zjhm = $('#mateCertNum').val();
			var startDay = $(this).val();
			if (zjhm != null && startDay != null) {
				var st = parseInt(startDay.replace(/-/g, ''));
				var zj = parseInt(zjhm.substring(6, 14));
				if (st < zj) {
					art.dialog.alert('证件有效值输入起始时间有误，请检查!');
				}
			} else {
				art.dialog.alert('请先输入证件号码或者证件有效期时间!');
			}
		});

		$('#mateCertNum').bind('blur', function() {
			$('#flag').val('');
			$('#blackTip').html('');
			$('#message').val('');
			var mateCertNum = $('#mateCertNum').val();
			var cardType = $('#cardType option:selected').val();
			var cardTypeName = $('#cardType option:selected').text();
			consult.certifacteFormat('cardType', 'mateCertNum'); // 身份尾号X格式化
			queryBlackLog(cardType, mateCertNum);
			if (mateCertNum != '' && cardType != '' && cardType == '0') {
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
		$('#submitConsultBtn').bind('click', function() {
			if ($('#flag').val() == '0' || $('#flag').val() == '2') {
				art.dialog.alert($('#message').val());
				return false;
			} else {
				consult.datavalidate();
			}
		});

		// 身份证ORC BY ZHANGFENG
		$('#cardType').bind('change', function() {
			if ($("#cardType").find("option:selected").text() == "身份证") {
				$('#ocr').show();
			} else {
				$('#ocr').hide();
			}
		});
		// 身份证ORC
		$('#file').bind('change', function() {
			var formData = new FormData($('#inputForm')[0]);
			contents_getJsonForFileUpload(this, ctx + '/apply/consult/updateFile', formData, function(result) {
				if (result.errorCode != '' && result.errorCode != 'undefined' && result.errorCode != undefined) {
					art.dialog.alert(result.errorCode);
					return;
				}
				if (result.validdate != null && result.validdate != '' && result.validdate != 'undefined' && result.validdate != undefined) {
					var idStartDay = result.validdate.split("-")[0];
					var idEndDay = result.validdate.split("-")[1];
					$('#idStartDay').val(idStartDay.substr(0, 4) + '-' + idStartDay.substr(4, 2) + '-' + idStartDay.substr(6, 2));
					if (idEndDay == '' || idEndDay == 'undefined' || idEndDay == undefined) {
						$("#longTerm").checked = true;
					} else {
						$('#idEndDay').val(idEndDay.substr(0, 4) + '-' + idEndDay.substr(4, 2) + '-' + idEndDay.substr(6, 2));
					}
				}
				$('#mateCertNum').val(result.cardnum);
				$('#customerName').val(result.name);
				var sexCode;
				if (result.sex == '男') {
					sexCode = 0;
				} else {
					sexCode = 1
				}
				$("[name='customerBaseInfo.customerSex']").each(function() {
					if ($(this).val() == sexCode) {
						$(this).attr('checked', true);
					}
				})
			}, null, false);
		});

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
	<form:form id="inputForm" modelAttribute="consult" action="${ctx}/apply/consult/saveConsult" method="post" class="form-horizontal">
		<h2 class=" f14 pl10 mt20">基本信息</h2>
		<div class="box2 ">

			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							姓名：
						</label>
						<form:input path="customerBaseInfo.customerName" id="customerName" class="input_text178" />
						<form:hidden path="customerBaseInfo.customerCode" id="customerCode" />
						<input type="hidden" id="flag" />
						<input type="hidden" id="message" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							常用手机号：
						</label>
						<form:input path="customerBaseInfo.customerMobilePhone" class="required input_text178" id="customerMobilePhone" />
					</td>
					<td style="width: 32%">
						<label class="lab">宅电：</label>
						<span>
							<form:input path="customerBaseInfo.areaNo" id="areaNo" class="input_text50" />
							-
							<form:input path="customerBaseInfo.telephoneNo" id="telephoneNo" class="input_text178 areaNoCheck telNoCheck" />
						</span>
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
							<c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
								<option value="${item.value}" <c:if test="${item.label=='身份证'}"> selected=true </c:if>>
			     					${item.label}
			     				</option>
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
						<input id="file" name="file" type="file" style="display: none">
						<a class="btn" id="ocr" onclick="$('input[id=file]').click();">OCR</a>
						<span id="blackTip" style="color: red;"></span>
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							证件有效期：
						</label>
						<span>
							<input id="idStartDay" name="customerBaseInfo.idStartDay" type="text" class="input_text70 Wdate" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" />
							-
							<input id="idEndDay" name="customerBaseInfo.idEndDay" type="text" class="input_text70 Wdate" size="10" onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" />
							<form:checkbox path="longTerm" id="longTerm" value="1" />
							长期
						</span>
					</td>
				</tr>
				<tr>
					<td rowspan="2">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							所属行业：
						</label>
						<form:select id="dictCompIndustry" path="customerBaseInfo.dictCompIndustry" class="required select180">
							<option value="">请选择</option>
							<form:options items="${fns:getNewDictList('jk_industry_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							性别：
						</label>
						<form:radiobuttons id="sex" path="customerBaseInfo.customerSex" cssClass="customerSex required" items="${fns:getDictList('jk_sex')}" itemLabel="label" itemValue="value" delimiter="&nbsp;" />
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
							团队经理：
						</label>
						<form:input path="consTeamEmpName" disabled="true" class="input_text178" />
						<form:hidden path="loanTeamEmpcode" />
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							客户经理：
						</label>
						<c:choose>
							<c:when test="${storeManager ne null}">
								<select id="customerManager" name="managerCode" class="select180 required" disabled="disabled">
									<option value="${storeManager.userCode}">${storeManager.name}</option>
								</select>
								<input type="hidden" name="managerCode" value="${storeManager.userCode}">
							</c:when>
							<c:otherwise>
								<form:select id="customerManager" path="managerCode" class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${customerManagers}" var="item">		
										<option value="${item.id}">${item.name}</option>
									</c:forEach>
								</form:select>	
							</c:otherwise>
						</c:choose>
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
							主要借款用途：
						</label>
						<form:select id="dictLoanUse" path="dictLoanUse" class="select180 required">
							<option value="">请选择</option>
							<form:options items="${fns:getNewDictList('jk_loan_use')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<form:input path="dictLoanUseRemark" id="dictLoanUseRemark" htmlEscape="false" maxlength="100" class="input_text178 collapse" />
					</td>
					<td colspan="2">
						<label class="lab">计划借款金额：</label>
						<form:input path="loanApplyMoney" id="loanApplyMoney" htmlEscape="false" maxlength="50" class="input_text178 numCheck positiveNumCheck" />
					</td>
				</tr>
			</table>
		</div>
		<h2 class=" f14 pl10 mt20">沟通记录及下一步操作</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td style="width: 32%">
						<label class="lab">沟通记录：</label>
						<form:textarea path="consultRecord.consLoanRecord" class="textarea_refuse" maxlength="490" rows="10" cols="50" />
						<%--  <form:input path="consultRecord.consLoanRecord" htmlEscape="false"  class="input_text178" /> --%>
					</td>
					<td style="width: 32%">
						<label class="lab">
							<span style="color: #ff0000">*</span>
							下一步：
						</label>
						<form:select id="dictOperStatus" path="consultRecord.consOperStatus" class="required select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
								<c:if test="${item.label!='已进件' && item.label!='待申请'}">
									<option value="${item.value}">${item.label}</option>
								</c:if>
							</c:forEach>
						</form:select>
					</td>
					<td style="width: 32%"></td>
				</tr>
			</table>
		</div>
		<div class="tright mt10 mr34">
			<input type="submit" id="submitConsultBtn" class="btn btn-primary" value="提交"></input>
		</div>
	</form:form>
</body>
</html>