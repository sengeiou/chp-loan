<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<jsp:include page="./applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/launch/certificateInfo.js?version=3"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#certificateNextBtn').bind('click', function() {
			$(this).attr('disabled', true);
			launch.saveApplyInfo('8', 'certificateForm', 'certificateNextBtn');
		});
		var isBorrow=$("#isBorrow").val();
		//如果是借么的单子则按照借么APP的要求 户主页地址置灰为空
		if(isBorrow != 1){
			loan.initCity("masterAddressProvince", "masterAddressCity", "masterAddressArea");
			areaselect.initCity("masterAddressProvince", "masterAddressCity", "masterAddressArea", $("#masterAddressCityHidden").val(), $("#masterAddressAreaHidden").val());
		}

		/* var weddingTime = $("#weddingTime");
		var licenseIssuingAgency = $("#licenseIssuingAgency")
		if(${applyInfoEx.loanCustomer ne null && "1" eq applyInfoEx.loanCustomer.getDictMarry()}){
			weddingTime.addClass("required");
			weddingTime.siblings("label").children("span").show();
			licenseIssuingAgency.addClass("required");
			licenseIssuingAgency.siblings("label").children("span").show();
		}else{
			weddingTime.removeClass("required");
			weddingTime.siblings("label").children("span").hide();
			licenseIssuingAgency.removeClass("required");
			licenseIssuingAgency.siblings("label").children("span").hide();
		} */
		//初始化
		personalCertificate.init();
	});
	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li>
			<a href="javascript:launch.getCustomerInfo('1');">个人基本信息</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('2');">借款意愿</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('3');">工作信息</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('4');">联系人信息</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('5');">自然人保证人信息</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('6');">房产信息</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('7');">经营信息</a>
		</li>
		<li class="active">
			<a href="javascript:launch.getCustomerInfo('8');">证件信息</a>
		</li>
		<li>
			<a href="javascript:launch.getCustomerInfo('9');">银行卡信息</a>
		</li>
	</ul>
	<form id="custInfoForm" method="post">
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
	</form>
	<div id="div8" class="content control-group">
		<form id="certificateForm" method="post">
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
			<input type="hidden" name="loanPersonalCertificate.id" value="${applyInfoEx.loanPersonalCertificate.id}" id="loanPersonalCertificateId" />
			<input type="hidden" name="loanPersonalCertificate.customerAddressProvince" value="${applyInfoEx.loanPersonalCertificate.customerAddressProvince}" id="customerAddressProvince" />
			<input type="hidden" name="loanPersonalCertificate.customerAddressCity" value="${applyInfoEx.loanPersonalCertificate.customerAddressCity}" id="customerAddressCity" />
			<input type="hidden" name="loanPersonalCertificate.customerAddressArea" value="${applyInfoEx.loanPersonalCertificate.customerAddressArea}" id="customerAddressArea" />
			<input type="hidden" name="loanPersonalCertificate.customerAddress" value="${applyInfoEx.loanPersonalCertificate.customerAddress}" id="customerAddress" />
			<input type="hidden" name="loanPersonalCertificate.customerName" value="${applyInfoEx.loanCustomer.customerName}" id="certCustomerName" />
			<input type="hidden" name="loanPersonalCertificate.customerCertNum" value="${applyInfoEx.loanCustomer.customerCertNum}" id="customerCertNum" />
			<input type="hidden" name="loanPersonalCertificate.dictMarry" value="${applyInfoEx.loanCustomer.dictMarry}" id="dictMarry" />
			<h2 class=" pl10 f14 ">
				<b>户口本信息</b>
			</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="33%">
						<label class="lab">
							<span class="red"></span>
							申请人与户主关系：
						</label>
						<select name="loanPersonalCertificate.customerRelMaster" id="personalCertificate" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_apply_rel_master')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.loanPersonalCertificate.customerRelMaster==item.value}"> selected=true </c:if>>${item.label}</option>
							</c:forEach>
						</select>
						<input type="text" id="customerRelMasterRemark" name="loanPersonalCertificate.customerRelMasterRemark" value="${applyInfoEx.loanPersonalCertificate.customerRelMasterRemark}" class="input_text178 chineseCheck" maxlength="180" />
					</td>
					<td width="33%">
						<label class="lab">
							<span class="red" style="display:none;">*</span>
							户主姓名：
						</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.masterName}" id="masterName" name="loanPersonalCertificate.masterName" class="input_text178 stringCheck" maxlength='20' />
					</td>
					<td>
						<label class="lab">
							<span class="red" style="display:none;">*</span>
							户主身份证号码：
						</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.masterCertNum}" id="masterCertNum" name="loanPersonalCertificate.masterCertNum" class="input_text178 coboCertCheckCopmany " maxlength='50' />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">子女姓名：</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.childrenName}" id="childrenName" name="loanPersonalCertificate.childrenName" class="input_text178 chineseCheck childrenNameEmptyAndCertNumNotEmpty" maxlength='50' />
					</td>
					<td>
						<label class="lab">子女身份证号码：</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.childrenCertNum}" id="childrenCertNum" name="loanPersonalCertificate.childrenCertNum" class="input_text178 childrenNameNotEmptyAndCertNumEmpty" maxlength='50' />
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label class="lab">
							<span class="red"></span>
							户主页住址：
						</label>
						<select class="select78 mr10" id="masterAddressProvince" name="loanPersonalCertificate.masterAddressProvince" value="${applyInfoEx.loanPersonalCertificate.masterAddressProvince}">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}" varStatus="status">
								<option value="${item.areaCode}" <c:if test="${applyInfoEx.loanPersonalCertificate.masterAddressProvince==item.areaCode}"> selected=true </c:if>>${item.areaName}</option>
							</c:forEach>
						</select>
						-
						<select class="select78 mr10" id="masterAddressCity" name="loanPersonalCertificate.masterAddressCity" value="${applyInfoEx.loanPersonalCertificate.masterAddressCity}">
							<option value="">请选择</option>
						</select>
						-
						<select class="select78 mr10" id="masterAddressArea" name="loanPersonalCertificate.masterAddressArea" value="${applyInfoEx.loanPersonalCertificate.masterAddressArea}">
							<option value="">请选择</option>
						</select>
						<input name="loanPersonalCertificate.masterAddress" id="masterAddress" type="text" value="${applyInfoEx.loanPersonalCertificate.masterAddress}" class="input_text178" style="width: 250px">
						<input type="hidden" id="masterAddressCityHidden" value="${applyInfoEx.loanPersonalCertificate.masterAddressCity}" />
						<input type="hidden" id="masterAddressAreaHidden" value="${applyInfoEx.loanPersonalCertificate.masterAddressArea}" />
					</td>
				</tr>
			</table>
			
			<h2 class=" pl10 f14 ">
				<b>学历信息</b>
			</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="33%">
						<label class="lab">学历证书类型：</label>
						<select name="loanPersonalCertificate.educationalCertificateType" id="personalCertificateType" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_academic_certificate_type')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.loanPersonalCertificate.educationalCertificateType==item.value}"> selected=true </c:if>>${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td width="33%">
						<label class="lab">毕业院校：</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.graduationSchool}" name="loanPersonalCertificate.graduationSchool" id="graduationSchool" class="input_text178" maxlength='50' />
					</td>
					<td>
						<label class="lab">学历证书编号：</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.educationalCertificateNum}" name="loanPersonalCertificate.educationalCertificateNum" id="educationalCertificateNum" class="input_text178 certificateNum " maxlength='50' />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">学历证书取得时间：</label>
						<input class="input_text178 Wdate" id="educationalCertificateTime" name="loanPersonalCertificate.educationalCertificateTime" value="<fmt:formatDate value='${applyInfoEx.loanPersonalCertificate.educationalCertificateTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
				</tr>
			</table>
			<h2 class=" pl10 f14 ">
				<b>结婚证信息</b>
			</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td width="33%">
						<label class="lab"><span class="red"></span>结婚日期：</label>
						<input id="weddingTime" class="input_text178 Wdate" name="loanPersonalCertificate.weddingTime" value="<fmt:formatDate value='${applyInfoEx.loanPersonalCertificate.weddingTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td>
						<label class="lab"><span class="red"></span>发证机构：</label>
						<input type="text" value="${applyInfoEx.loanPersonalCertificate.licenseIssuingAgency}" id="licenseIssuingAgency" name="loanPersonalCertificate.licenseIssuingAgency" class="input_text178 chineseCheck" maxlength='50' />
					</td>
					<td></td>
				</tr>
			</table>
			<div class="tright mb5 mr10">
				<input type="submit" id="certificateNextBtn" class="btn btn-primary" value="下一步" />
			</div>
		</form>
	</div>
</body>
</html>