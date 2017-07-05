<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<!-- 借款信息 -->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendTermCustomer.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('input:radio[name="dictIsGatherFlowFee"]').change(function() {
		var dictIsGatherFlowFee = $('input:radio[name="dictIsGatherFlowFee"]:checked ').val();
		if(dictIsGatherFlowFee == '1'){
			$("#flowFeeSpan").show();
			$("#flowFee").val(200);
		}else if(dictIsGatherFlowFee == '2'){
			$("#flowFee").val('');
			$("#flowFeeSpan").hide();
		} else {
			$("#isDictIsGatherFlowFee").addClass("required");
		}
	});
});
$(function(){
	$('input:radio[name="dictIsGatherFlowFee"]').trigger("change");
	var cVal = $("#infoContractCode")[0].innerHTML.substr(0,1);
	var pVal = null;
	var pLab = null;
	if (cVal == '车') {
		pVal = 'CJ02';
		pLab = '移交';
		$("#parkingFeeTd").show();
		$('input:radio[name="dictIsGatherFlowFee"]').attr("disabled", false);
		$('#flowFee').attr("disabled", false);
		$("#flowFeeTr").hide();
	} else if (cVal == 'G') {
		pVal = 'CJ01';
		pLab = 'GPS';
		$("#parkingFee").val("");
		$("#parkingFeeTd").hide();
		$("#flowFeeTr").show();
		$('input:radio[name="dictIsGatherFlowFee"]').css("disabled", true);
		$('#flowFee').css("disabled", true);
	} else if (cVal == '质') {
		pVal = 'CJ03';
		pLab = '质押';
		$("#parkingFeeTd").show();
		$('input:radio[name="dictIsGatherFlowFee"]').css("disabled", false);
		$('#flowFee').css("disabled", false);
		$("#flowFeeTr").hide();
	}
	$("#dictProductType").val(pLab);
	$("#dictProductTypeHidden").val(pVal);
	//验证JS
	$("#carLoanFlowInfo").validate({
		onclick: true, 
		rules:{
			parkingFee :{
				checkParkingAmount : true
			},
			loanApplyAmount :{
				lessThenLastAmount : true
			}
		}
	});

	jQuery.validator.addMethod("checkParkingAmount", function(b, a) {
		var check = true;
		var parkingAmount = $("#parkingFee").val();
		
		if (parkingAmount != null && parkingAmount != '') {
			if (parkingAmount > 1000) {
				check = false;
			}
		}
		return this.optional(a) || check
	}, "停车费应小于1000");
	jQuery.validator.addMethod("lessThenLastAmount", function(b, a) {
		var check = false;
		var lastAmount = $("#auditAmount").val();
		var loanAmount = $("#loanApplyAmount").val();
		if (loanAmount != null && loanAmount != '' && lastAmount != null && lastAmount != '') {
			if (parseFloat(lastAmount) >= parseFloat(loanAmount)) {
				check = true;
			}
		}
		return this.optional(a) || check
	}, "借款金额应不能大于上次借款金额！");
});
</script>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/queryHistoryExtend?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li class="active"><a>借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowCustomer?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendCompany?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendContact?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toExtendCoborrower?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowBank?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
	</ul>
<jsp:include page="frameFlowFormExtend.jsp"></jsp:include>
<div id="div3" class="content control-group">
<form id="carLoanFlowInfo" method="post" action="${ctx}/car/carExtendHistory/carExtendLoanFlowInfo">
	  <input type="hidden" value="${carLoanInfo.customerCode}" name="customerCode"/>
      <input type="hidden" value="${loanCode}" name="loanCode"/>
      <input type="hidden" value="${newLoanCode}" name="newLoanCode"/>
      <input type="hidden" value="${auditAmount}" id="auditAmount"/>
      <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td><label class="lab">合同编号：<span class="lab" id="infoContractCode">${contractCode}</span></label></td>
		</tr>
		<tr>
            <td><label class="lab"><span class="red">*</span>申请额度（元）：</label>
				<input type="text" name="loanApplyAmount" id='loanApplyAmount'
				value="<fmt:formatNumber value='${carLoanInfo.loanApplyAmount}' pattern="##0"/>" class="input_text178 numCheck positiveNumCheck required lessThenLastAmount"  maxlength="8" /></td>
                      
                      <td><label class="lab"><span class="red">*</span>产品类型：</label>
				<input id="dictProductType" type="text" class="input_text178 required" readonly="readonly"/>
				<input name="dictProductType" id="dictProductTypeHidden" type="hidden" class="input_text178 required" />
				<input name="loanApplyTime" value="<fmt:formatDate value='${carLoanInfo.loanApplyTime}' pattern="yyyy-MM-dd"/>"
						type="hidden" class="Wdate input_text70 required" size="10" style="cursor: pointer" />
     		</td>
                      <td><label class="lab"><span class="red">*</span>申请借款期限(天数)：</label>
                      <input class="input_text178 required" name="loanMonths" value="30" readonly="readonly"/>
			</td>
          </tr>
			<tr>
				<td><label class="lab"><span class="red">*</span>客户姓名：</label>
				<input type="text" name="loanCustomerName" value="${carLoanInfo.loanCustomerName == null ? carCustomer.customerName : carLoanInfo.loanCustomerName}" class="required realName"  maxlength="10"/>
				</td>
				<td>
				<c:choose>
				<c:when	test="${carLoanInfo.dictLoanCommonRepaymentFlag != null}">
					<label class="lab"><span class="red">*</span>共借人：</label>
					<c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
						<input type="radio" onclick="return false" name="dictLoanCommonRepaymentFlag" class="required" value="${item.value}"
							<c:if test="${item.value == carLoanInfo.dictLoanCommonRepaymentFlag}">
	                  			checked='true'
	               			</c:if> />${item.label}
	               	</c:forEach>
	              </c:when>
	              <c:otherwise>
	              	<label class="lab"><span class="red">*</span>共借人：</label>
					<c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
						<input type="radio" name="dictLoanCommonRepaymentFlag" class="required" value="${item.value}"
							<c:if test="${item.value == carLoanInfo.dictLoanCommonRepaymentFlag}">
	                  			checked='true'
	               			</c:if> />${item.label}
	               	</c:forEach>
	              </c:otherwise>
	            </c:choose>
				</td>
				<td id="parkingFeeTd"><label class="lab"><span style="color: red">*</span>停车费：</label>
                <input type="text" value="<fmt:formatNumber value='${carLoanInfo.parkingFee}' pattern="##0"/>" id="parkingFee" maxlength="8" name="parkingFee" class="numCheck required positiveNumCheck">元
                </td>
			</tr>
			<tr id="flowFeeTr">
				<td><label class="lab"><span style="color: red">*</span>是否收取平台流量费：</label>
					<input type="radio" id="isDictIsGatherFlowFee" name="dictIsGatherFlowFee" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '1'}">
					checked </c:if> value="1"/>是&nbsp;&nbsp;
					<input type="radio"  name="dictIsGatherFlowFee" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '2'}">
					checked </c:if> value="2"/>否&nbsp;&nbsp;
					<span id="flowFeeSpan" style="display:none;"><input id="flowFee" name="flowFee" type="text" value="200.00" readonly="readonly"/>元</span>
				</td>
			</tr> 
			<tr>
                <td><label class="lab"><span style="color: red">*</span>团队经理：</label>
						<select id="termManagerId" name="consTeamManagercode" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${termManagers}" var="item">
							 	<option value="${item.id}"
								<c:if test="${carLoanInfo.consTeamManagercode == item.id}">selected</c:if>
								>${item.name}</option>
							</c:forEach>
						</select></td>
					<td><label class="lab"><span style="color: red">*</span>客户经理：</label>
						<select id="custManagerId" name="managerCode" class="select180 required">
							<option value="">请选择</option>
					</select><input id="customerManagerHidden" type="hidden" value="${carLoanInfo.managerCode }" /></td>
                 <td><label class="lab">门店名称：</label>${storeName}</td>
                  <input type="hidden" value="${storeName}" name="storeName">
                 <input type="hidden" value="${storeCode}" name="storeCode">
			</tr>
			<tr>
				<td><label class="lab">管辖城市：</label>${cityName}</td>
				<td><label class="lab"><span class="red">*</span>客户人法查询结果：</label>
					<input type="text" class="input_text178 required" maxlength="20" name="queryResult" value="${carApplicationInterviewInfo.queryResult}"></td>
			</tr>
			<tr>
				<td><label class="lab"><span class="red">*</span>展期原因：</label><select
					name="dictLoanUse" class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
						<option value="${item.value}" 
						<c:if test="${carLoanInfo.dictLoanUse == item.value}"> 
						    selected
						  </c:if> 
						>${item.label}</option>
					</c:forEach>
				</select></td>
				<td><label class="lab"><span class="red">*</span>详细用途：</label>
					<input type="text" class="input_text178 required" maxlength="50" name="loanUseDetail" value="${carLoanInfo.loanUseDetail}"></td>
			</tr>
        </table>
	<div class="tright mb5 mr10" >
		<input type="submit" id="applyInfoNextBtn" class="btn btn-primary" value="下一步" />
	</div>
	</form>
</div>
</body>
</html>