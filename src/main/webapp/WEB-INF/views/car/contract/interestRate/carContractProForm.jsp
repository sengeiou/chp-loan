<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title>合同制作待办提交</title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${context}/js/car/contract/carContractHandle.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript">
$(function(){
	$('#historyBtn').bind('click',function(){
		  showCarLoanHis($(this).attr('loanCode'));
	});  
	//查看详细信息
	$('#showView').bind('click',function(){
		  showCarLoanInfo($(this).attr('loanCode'));
	});
}
);
$(function(){
	var text = $("#textarea").val();
	var counter = text.length;
	$(".textareP").find("var").text(200-counter);
	
	$("#textarea").keyup(function() {
		var text = $("#textarea").val();
		var counter = text.length;
		$(".textareP").find("var").text(200-counter);
	});
});

$(document).ready(function(){
	var isRare = $("#isRareword").val();
	if(isRare == '1'){
		$("#rareword").show();
	}else {
		$("#rareword").hide();
	}
	var isRare_two = $("#isRareword_two").val();
	if(isRare_two == '1'){
		$("#rareword_two").show();
	}else {
		$("#rareword_two").hide();
	}
});

//停车费和设备费根据批借产品互斥显示；平台流量费只针对GPS类产品，移交与质押不收取，不可默认带出
$(document).ready(function(){
		var productTypeName = $("#productTypeName").val();
		if(productTypeName=='GPS'){
			if ($("#GPSchai").val() == '2' && $("#getFee").val() == '1' ) {
				$("#flowFee").show();
			}else{
				$("#flowFee").hide();
			}
			$("#parkingFee").hide();
			$("#facilityCharge").show();
		}else {
			$("#parkingFee").show();
			$("#facilityCharge").hide();
		}
	});

function validateContractVersion(){
	var contractNo='<c:forEach items="${fns:getDictList('jk_car_contract_version')}" var="dict" varStatus="status"><c:if test="${status.last}">${dict.label }</c:if></c:forEach>';
	if("${workItem.bv.carContract.contractVersion}"!=contractNo){
		return true;
	}else{
		return false;
	}
}


</script>
</head>
<body>
    <input id="contractVersion" type="hidden" value="${workItem.bv.carContract.contractVersion}"/>
	<form id="param" class="hide">
		<input type="text" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
	</form>
	<form id="workParam" class="hide">
		<input type="text" value="${workItem.flowName}" name="flowName"></input>
		<input type="text" value="${workItem.flowId}" name="flowId"></input>
		<input type="text" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="text" value="${workItem.stepName}" name="stepName"></input>
		<input type="text" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="text" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
		<input type="hidden" value="${workItem.bv.carCustomerBankInfo.israre}" name="israre" ></input>
		<input type="hidden" id="GPSchai" value="${workItem.bv.carLoanInfo.dictGpsRemaining }"/>
        <input type="hidden" id="getFee" value="${workItem.bv.carLoanInfo.dictIsGatherFlowFee }"/>
	</form>
	<div class="tright pt5 pr10 pb5 ">
			<input type="button" class="btn btn-small jkcj_carContractProForm_refunded" onclick="backShow()" value="退回">
			
			<%-- <input type="button" class="btn btn-small" id="showView" loanCode="${workItem.bv.carLoanInfo.loanCode}"  value="查看" > --%>
	</div>
		<h2 class="f14 pl10">客户信息</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td colspan="3"><label class="lab">合同编号：</label>${workItem.bv.carContract.contractCode}</td>
			</tr>
			<tr>
				<td style="width: 32%"><label class="lab">门店名称：</label>${workItem.bv.carLoanInfo.storeName}</td>
			</tr>
			<tr>
				<td style="width: 32%"><label class="lab">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
				<td><label class="lab">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
				<td style="width: 32%"><label class="lab">客户邮箱：</label>${workItem.bv.carCustomer.customerEmail}</td>
			</tr>
			<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
					<td><label class="lab">共借人身份证号：</label>${cobos.certNum}</td>
					<td><label class="lab">共借人邮箱：</label>${cobos.email}</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<h2 class="f14 pl10">汇诚审批结果</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td><input type="hidden" id ="productTypeName" value="${workItem.bv.carContract.productTypeName}"/><label class="lab">总费率：</label><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.0000" />%</td>
				<td><label class="lab">批借金额：</label><fmt:formatNumber value="${workItem.bv.carContract.auditAmount}" pattern="0.00" />元</td>
				<td><label class="lab">批借期限：</label>${workItem.bv.carContract.contractMonths}天</td>
			</tr>
			<sys:carProductShow parkingFee="${workItem.bv.carLoanInfo.parkingFee}" flowFee="${workItem.bv.carLoanInfo.flowFee}" 
            dictGpsRemaining="${workItem.bv.carLoanInfo.dictGpsRemaining}" facilityCharge="${workItem.bv.carLoanInfo.facilityCharge}" 
            dictIsGatherFlowFee="${workItem.bv.carLoanInfo.dictIsGatherFlowFee}" 
            dictSettleRelend="${workItem.bv.carLoanInfo.dictSettleRelendName}" productType="${workItem.bv.carContract.productTypeName}"></sys:carProductShow>
			<tr>
				<td><label class="lab">首期服务费率：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.firstServiceTariffingRate}" pattern="0.00" />%</td>
				<td><c:if test="${workItem.bv.carAuditResult.outVisitDistance != null && workItem.bv.carAuditResult.outVisitDistance>0}">
						<label class="lab" >外访距离：</label><fmt:formatNumber  value="${workItem.bv.carAuditResult.outVisitDistance}"  pattern="0.00"  />公里
					</c:if>
				</td>
				<td>
					<c:if test="${workItem.bv.carContract.productTypeName=='GPS'}">
						<label class="lab">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee == '' ? 0 : workItem.bv.carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元
					</c:if>
				</td>
			</tr>
		</table>
		</div>
		<h2 class="f14 pl10">借款信息</h2>
		<form id="saveContractProFormId">
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
				<tr>
					<td style="width: 32%"><label class="lab">利息率：</label><fmt:formatNumber type="number" value="${workItem.bv.carCheckRate.interestRate}" pattern="0.0000"/>%</td>
					<td><label class="lab">月利息：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.monthlyInterest }" pattern="0.00" />元</td>
					<td></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000">*</span>中间人：</label> <select name="centerUser"
						class="select180 required">
							<option value="400000000029992"
									<c:if test="${workItem.bv.carContract.midId eq '400000000029992'}"> selected </c:if>>寇振红</option>
							<option value="400000000029989"
									<c:if test="${workItem.bv.carContract.midId eq '400000000029989'}"> selected </c:if>>夏靖</option>
					</select></td>
					<td><label class="lab">首期服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.firstServiceTariffing }" pattern="0.00" />元</td>
					<td><label class="lab">渠道：</label>${workItem.bv.carLoanInfo.loanFlag }</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">合同金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.contractAmount}" pattern="0.00" />元</td>
					<td><label class="lab">实放金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.feePaymentAmount}" pattern="0.00" />元</td>
					<td><label class="lab">月还金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.monthRepayAmount}" pattern="0.00" />元</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">综合服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.comprehensiveServiceFee}" pattern="0.00" />元</td>
					<td><label class="lab">审核费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.auditFee}" pattern="0.00" />元</td>
					<td><label class="lab">咨询费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.consultingFee}" pattern="0.00" />元</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">居间服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.intermediaryServiceFee}" pattern="0.00" />元</td>
					<td><label class="lab">信息服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.informationServiceCharge}" pattern="0.00" />元</td>
					<td>
						<c:if test="${workItem.bv.carCheckRate.outVisitFee != null && workItem.bv.carAuditResult.outVisitDistance>0}">
							<label class="lab" >外访费：</label><fmt:formatNumber  value="${workItem.bv.carCheckRate.outVisitFee}"  pattern="0.00"  />元
						</c:if>
					</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">合同签订日期：</label><fmt:formatDate value='${workItem.bv.carContract.contractDueDay}' pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">还款付息方式：</label>${workItem.bv.carContract.dictRepayMethod}</td>
					<td><label class="lab">首期还款日：</label><fmt:formatDate value='${workItem.bv.carContract.contractReplayDay}' pattern="yyyy-MM-dd"/></td>
				</tr>
		</table>
		</div>
		<h2 class="f14 pl10">银行信息</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td style="width: 32%"><label class="lab">账户姓名：</label>${workItem.bv.carCustomerBankInfo.bankAccountName}&nbsp;&nbsp;&nbsp;
				 <span>
				<input type="checkbox" <c:if test="${workItem.bv.carCustomerBankInfo.israre == '1'}">checked='checked' </c:if> disabled="disabled" /><lable>是否生僻字</lable>
				</span> 
				</td>
				<td><label class="lab">开户行：</label>${workItem.bv.carCustomerBankInfo.cardBank}</td>
				<td><label class="lab">开卡行支行名称：</label>${workItem.bv.carCustomerBankInfo.applyBankName}</td>
			</tr>
			<tr>
				<td><label class="lab">银行账号：</label>${workItem.bv.carCustomerBankInfo.bankCardNo}</td>
				<td><label class="lab">签约平台：</label>
							<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="curItem">
		                     <c:if test="${curItem.label!='中金' && curItem.value!='6'}">
							    <c:if test="${workItem.bv.carCustomerBankInfo.bankSigningPlatform==curItem.value}">
							    	${curItem.label}
							    </c:if>
							  </c:if>
						    </c:forEach> 
					</td>
				<td></td>
			</tr>
			<tr>
				
			</tr>
			</table>
		</div>
		<input type="hidden" value="TO_UPLOAD_CONTRACT" name="checkResult">
		<div class="tright mt10 pr10 mb10" style="position: fixed;bottom:20px;right:1px;">
			<input type="button" id="handleMakeContract" class="btn btn-primary" value="提交"></input>
			<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
			onclick="JavaScript:history.go(-1);"></input> 
		</div>
	</form>
	</table>

	<!--退回弹框  -->
	<div id="modal_Back" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role" document="">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<div class="l">
						<h4 class="pop_title">退回</h4>
					</div>
				</div>
				<form id="backFormId111">
					<div class="modal-body">
						<table class="table4" cellpadding="0" cellspacing="0"
							border="0" width="100%" id="tpTable">
							<tr>
								<td><div><lable class="lab"><span style="color: red;">*</span>退回至流程节点：</lable><select
									name="checkResult" class="required">
										<option value="">请选择退回节点</option>
										<c:forEach
											items="${fns:getDictLists('14,16', 'jk_car_loan_status')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select></div></td>
							</tr>
							<tr>
								<td valign="top"><lable class="lab"><span style="color: red;">*</span>退回原因：</lable><textarea
										style="width: 370px; height: 150px;vertical-align:top" name="backReason"
										class="required" id="textarea" maxlength="200"></textarea>
										<span class="textareP">剩余<var style="color:#C00">--</var>字符</span>
										</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<button id="backContractProSave111" class="btn btn-primary"  data-dismiss="modal"
							aria-hidden="true">确定</button>
						<button class="btn btn-primary" data-dismiss="modal"
							aria-hidden="true">取消</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>