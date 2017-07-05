<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title></title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src='${context}/js/validate.js'></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendContractHandle.js"></script>
<script type="text/javascript" src='${context}/js/car/contract/contractAudit.js'></script>
<script type="text/javascript">
	$(function() {
		$("#interestRateId").trigger("change");
	});
</script>
</head>
<body>
	<form id="param" class="hide">
		<input type="text" name="loanCode" id="rateLoanCodeId" value="${workItem.bv.carLoanInfo.loanCode }" />
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
	</form>
	<form id="workParam" class="hide">
		<input type="text" value="${workItem.flowName}" name="flowName"></input>
		<input type="text" value="${workItem.flowId}" name="flowId"></input>
		<input type="text" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="text" value="${workItem.stepName}" name="stepName"></input>
		<input type="text" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="text" value="${workItem.token}" name="token"></input>
	</form>
	<div class="tright pt5 pb5 pr10 ">
			
			<c:if test="${workItem.bv.isLargeAmount == 1}">
				<button class="btn btn-small jkcj_carExtendInterestRateForm_large_view" applyId="${workItem.bv.carLoanInfo.applyId}" loanCode="${workItem.bv.carLoanInfo.loanCode}" id="largeAmountBtn">大额查看</button>
			</c:if>
			<input type="button" class="btn btn-small jkcj_carExtendInterestRateForm_back" onclick="backShow()" value="退回"></input>
	</div>
	<h2 class="f14 pl10">历史展期信息</h2>
	<div class="box2">
		<table class="table3" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<thead>
				<tr><th>合同编号</th><th>合同金额(元)</th><th>借款期限(天)</th><th>合同期限</th><th>降额(元)</th></tr>
			</thead>
			<c:forEach var="extendContract" items="${workItem.bv.carContracts }">
				<tr>
					<td>${extendContract.contractCode}</td>
					<td><fmt:formatNumber value="${extendContract.auditAmount}" pattern="0.00" /></td>
					<td>${extendContract.contractMonths}</td>
					<td><fmt:formatDate value='${extendContract.contractFactDay }' type='date' pattern="yyyy-MM-dd" /> ~ <fmt:formatDate value='${extendContract.contractEndDay }' type='date' pattern="yyyy-MM-dd" /></td>
					<td><c:choose>
						<c:when test="${extendContract.derate == null}">
							0.00
						</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${extendContract.derate}" pattern="0.00" />
						</c:otherwise>
					</c:choose></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<h2 class="f14 pl10 mt20">借款人信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<%-- <td><label class="lab">账户名称：</label>${workItem.bv.carCustomerBankInfo.bankAccountName}</td> --%>
			<tr>
				<td><label class="lab">借款人姓名：</label>${workItem.bv.carCustomer.customerName}</td>
				<td><label class="lab">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
				<td><label class="lab">门店名称：</label>${workItem.bv.carLoanInfo.storeName}</td>
			</tr>
			<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
					<td><label class="lab">共借人身份证号：</label>${cobos.certNum}</td>
					<td></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<h2 class="f14 pl10 mt20">车辆信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
				<td><label class="lab">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
			</tr>
		</table>
	</div>
	<h2 class="f14 pl10 mt20">汇诚审批信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">批借产品：</label>${workItem.bv.carContract.productType}</td>
				<td><label class="lab">批借金额：</label><fmt:formatNumber value="${workItem.bv.carContract.auditAmount}" pattern="0.00" />元</td>
				<td><label class="lab">批借期限：</label><label id="auditMonthsId">${workItem.bv.carContract.contractMonths}</label>天</td>
			</tr>
			<tr>
				<td><label class="lab">总费率：</label><label id="totalRate"><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.000" /></label>%</td>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<h2 class="f14 pl10 mt20">借款信息</h2>
	<form id="saveHandleCheckRateInfoId">
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td style="width: 32%"><label class="lab">展期合同编号：</label>${workItem.bv.carContract.contractCode }</td>
					<td></td>
					<td></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">合同金额：</label><fmt:formatNumber value="${workItem.bv.carContract.contractAmount }" pattern="0.00" />元</td>
					<td><label class="lab"><span style="color: #ff0000"></span>降额：</label>
						<fmt:formatNumber value="${workItem.bv.carContract.derate }" pattern="0.00" />元</td>
					<td><label class="lab">总费率：</label><fmt:formatNumber value="${workItem.bv.carContract.grossRate }" pattern="0.000" />%</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">利息率：</label> <select
						name="carCheckRate.interestRate" id="interestRateId"
						class="required select180">
							<option value="">请选择</option>
							<option value="0.5670">0.5670</option>
							<option value="0.4830">0.4830</option>
							<option value="0.6670">0.6670</option>
					</select>%</td>
					<td><label class="lab">月利息：</label> <input
						name="carCheckRate.monthlyInterest" id="monthlyInterestId"
						class="required input_text178" type="text" readonly="readonly" />元</td>
					<td style="width: 32%"><label class="lab">中间人：</label> <select
						name="centerUser" class="select180 required">
						    <option value="400000000029989" 
									<c:if test="${workItem.bv.carLoanInfo.mortgagee eq '400000000029989'}">
										selected=true
									</c:if>
								>夏靖</option>
								<option value="400000000029992" 
									<c:if test="${workItem.bv.carLoanInfo.mortgagee eq '400000000029992'}">
										selected=true
									</c:if>
								>寇振红</option>
					</select></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">展期综合服务费：</label> <input
						name="carCheckRate.comprehensiveServiceFee"
						class="required input_text178" type="text" readonly="readonly"
						id="mulMoney" />元/月</td>
					<td><label class="lab">审核费：</label> <input
						name="carCheckRate.auditFee" class="required input_text178"
						type="text" readonly="readonly" id="auditMoney" />元/月</td>
					<td><label class="lab">咨询费：</label> <input
						name="carCheckRate.consultingFee" class="required input_text178"
						type="text" readonly="readonly" id="consultMoney" />元/月</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">居间服务费：</label> <input
						name="carCheckRate.intermediaryServiceFee"
						class="required input_text178" type="text" readonly="readonly"
						id="mediacyMoney" />元/月</td>
					<td><label class="lab"></span>信息服务费：</label> <input
						name="carCheckRate.informationServiceCharge"
						class="required input_text178" type="text" readonly="readonly"
						id="infoMoney" />元/月</td>
					<c:choose>
						 <c:when test="${workItem.bv.carContract.productType == 'GPS'}">
							<td><label class="lab">平台及流量费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.flowFee == null ? 0 : workItem.bv.carLoanInfo.flowFee }" pattern="#,##0.00" />元</td>
						 </c:when>
						<c:otherwise>
							<td><label class="lab">停车费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.parkingFee }" pattern="#,##0.00" />元</td>
						</c:otherwise> 
					</c:choose>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">展期费用：</label><input
						name="extensionFee"
						class="required input_text178" type="text" readonly="readonly"
						id="extendMoney" />元/月</td>
					<td><label class="lab">展期应还总金额：</label><input
						name="carCheckRate.extendPayAmount" class="required input_text178" type="text" readonly="readonly"
						id="extendPayAmountId" />元</td>
				<c:if test="${workItem.bv.carLoanInfo.deviceUsedFee != null}">
				    <td><label class="lab">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee}" pattern="#,##0.00" />元</td>
				</c:if>
				</tr>
			</table>
			<input type="hidden" value="TO_SIGN" name="checkResult">
			<input type="hidden" name="contractAmount" value="${workItem.bv.carContract.contractAmount }" />
			<input type="hidden" name="contractCode" value="${workItem.bv.carContract.contractCode}">
			<input type="hidden" name="contractVersion" value="${workItem.bv.carContract.contractVersion}">
			<input type="hidden" name="derate" value="${workItem.bv.carContract.derate}">
			<input type="hidden" name="carCheckRate.grantAmount" id="carGrantAmountId" />
		</div>
	</form>
	<%-- <h2 class="f14 pl10 mt20">客户银行信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">授权人：</label>${workItem.bv.carCustomer.customerName}</td>
				<td><label class="lab">银行卡户名：</label>${workItem.bv.carCustomerBankInfo.bankAccountName} <input type="checkbox" onclick="return false;" <c:if test="${workItem.bv.carCustomerBankInfo.israre == '1'}"> checked="checked" </c:if> /><lable>是否生僻字</lable></td>
				<td><label class="lab">开户行：</label>${workItem.bv.carCustomerBankInfo.cardBank}</td>
			</tr>
			<tr>
				<td><label class="lab">开户支行：</label>${workItem.bv.carCustomerBankInfo.applyBankName}</td>
				<td><label class="lab">银行账号：</label>${workItem.bv.carCustomerBankInfo.bankCardNo}</td>
				<td></td>
			</tr>
		</table>
	</div> --%>
	<div class="tright pt10 pr30" style="margin-bottom: 10px;">
			<input type="button" class="btn btn-primary" id="handleCheckRateInfo" value="提交"></input>
			<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
				onclick="JavaScript:history.go(-1);"></input> 
		</div>
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
				<form id="backFormId">
					<div class="modal-body">
						<table class="table4" cellpadding="0" cellspacing="0"
							border="0" width="100%" id="tpTable">
							<tr>
								<td><lable class="lab" style="width:80px;"><span style="color: red;">*</span>退回原因：</lable>
										<textarea style="width: 400px; height: 150px;"
											name="backReason" id="backReason" class="required"></textarea>
										<input type="hidden" value="BACK_END_AUDIT" name="checkResult">
										<span class="textareP">剩余<var style="color:#C00">--</var>字符</span>
								</td>
							</tr>
						</table>
					</div>
				</form>
				<div class="modal-footer">
					<button id="backSave" class="btn btn-primary"
						aria-hidden="true">确定</button>
					<button id="backCancel" class="btn btn-primary" data-dismiss="modal"
						aria-hidden="true">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>