<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<!--车借审核利率-->
<title>审核费率</title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src='${context}/js/validate.js'></script>
<script type="text/javascript" src="${context}/js/car/contract/carContractHandle.js"></script>
<script type="text/javascript" src='${context}/js/car/contract/contractAudit.js'></script>
<script type="text/javascript">
	
$(function() {
	
	$("#interestRateId").trigger("change");
	var deviceFee = 100*(month/30);
	$("#deviceUsedFee").val(deviceFee);
	
});
</script>
<style type="text/css">
	.lab {
		width: 130px;
	}
</style>
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
		<input type="text" value="${workItem.flowProperties.timeoutCheckStage}" name="timeoutCheckStage"></input>
		<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
	</form>
	<div class="tright pt5 pb5 pr10 ">
			
			<input type="button" class="btn btn-small jkcj_lendCarPendingReview_refunded" onclick="backShow()" value="退回"></input>
			<c:if test="${workItem.bv.isLargeAmount == 1}">
				<button class="btn btn-small jkcj_lendCarPendingReview_Large view" applyId="${workItem.bv.carLoanInfo.applyId}" loanCode="${workItem.bv.carLoanInfo.loanCode}" id="largeAmountBtn">大额查看</button>
			</c:if>
	</div>
	<h2 class="f14 pl10">客户信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">门店名称：</label>${workItem.bv.carLoanInfo.storeName}</td>
				<td><label class="lab">合同编号：</label>${workItem.bv.carContract.contractCode}</td>
				<td></td>
			</tr>
			<tr>
				<td><label class="lab">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
				<td><label class="lab">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
			    <c:if test="${workItem.bv.carAuditResult.productTypeName =='GPS'}">
				<c:if test="${workItem.bv.carLoanInfo.deviceUsedFee != null}">
				<td><label class="lab">设备使用费：</label><fmt:formatNumber  value="${workItem.bv.carLoanInfo.deviceUsedFee}" type="hidden" pattern="0.00"  /></td>
				</c:if>
				</c:if>
			</tr>
			<tr>
				<td><label class="lab">借款人姓名：</label>${workItem.bv.carCustomer.customerName}</td>
				<td><label class="lab">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
				<td><label class="lab">年龄：</label>${workItem.bv.customerAge}</td>
			</tr>
			<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
					<td><label class="lab">共借人身份证号：</label>${cobos.certNum}</td>
					<td></td>
				</tr>
			</c:forEach>
			<sys:carProductShow parkingFee="${workItem.bv.carLoanInfo.parkingFee}" flowFee="${workItem.bv.carLoanInfo.flowFee}" 
            dictGpsRemaining="${workItem.bv.carLoanInfo.dictGpsRemaining}" facilityCharge="${workItem.bv.carLoanInfo.facilityCharge}" 
            dictIsGatherFlowFee="${workItem.bv.carLoanInfo.dictIsGatherFlowFee}" 
            dictSettleRelend="${workItem.bv.carLoanInfo.dictSettleRelendName}" productType="${workItem.bv.carAuditResult.productTypeName}"></sys:carProductShow>
		</table>
	</div>
	
	<h2 class="f14 pl10 mt20">汇诚审批结果</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">批借产品：</label>${workItem.bv.carAuditResult.productTypeName}</td>
				<td><label class="lab">批借金额：</label><fmt:formatNumber value="${workItem.bv.carAuditResult.auditAmount}" pattern="0.00" />元</td>
				<td><label class="lab">批借期限：</label><label id="auditMonthsId">${workItem.bv.carAuditResult.dictAuditMonths}</label>天</td>
			</tr>
			<tr>
				<td><label class="lab">总费率：</label><label id="totalRate"><fmt:formatNumber value="${workItem.bv.carAuditResult.grossRate}" pattern="0.00" /></label>%</td>
				<td>
				<label class="lab" >首期服务费率：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.firstServiceTariffingRate}" pattern="0.00" />%</td>
				<c:if test="${workItem.bv.carAuditResult.outVisitDistance != null and workItem.bv.carAuditResult.outVisitDistance > 0 }">
				<td><label class="lab" >外访距离：</label><fmt:formatNumber  value="${workItem.bv.carAuditResult.outVisitDistance}"  pattern="0.00"  />km</td>
			    </c:if>
			</tr>
		</table>
	</div>
	
	<h2 class=" f14 pl10 mt20">审核费率</h2>
	<form id="saveHandleCheckRateInfoId">
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000">*</span>利息率：</label> <select
						name="carCheckRate.interestRate" id="interestRateId" class="required select180 select2Req">
							<option value="">请选择</option>
							<option value="0.5670">0.5670</option>
							<option value="0.4830">0.4830</option>
							<option value="0.6670">0.6670</option>
					</select>%</td>
					<td><label class="lab"><span style="color: #ff0000"></span>月利息：</label>
						<input id="monthlyInterestId"
						class="required input_text178" type="text" readonly="readonly"/>
						<input name="carCheckRate.monthlyInterest" id="monthlyInterestIdHidden"
						class="required input_text178" type="hidden"/>元</td>
					<td><label class="lab">渠道：</label>${workItem.bv.carLoanInfo.loanFlag }</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000">*</span>中间人：</label> <select name="centerUser"
						class="select180 required">
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
					<td><label class="lab"><span style="color: #ff0000"></span>首期服务费：</label>
						<input class="required input_text178" type="text" readonly="readonly" id="firstServerMoneyId" />
						<input name="carCheckRate.firstServiceTariffing"
						class="required input_text178" type="hidden" id="firstServerMoneyIdHidden" />元
						<input name="carCheckRate.firstServiceTariffingRate"
						class="required input_text178" type="hidden" value="${workItem.bv.carLoanInfo.firstServiceTariffingRate}" />
						</td>
					
					<td >
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance == null}">
						<input name="carCheckRate.outVisitFee" class="required input_text178 number" value ="${workItem.bv.carAuditResult.outVisitDistance}"  type="hidden" readonly="readonly" id="outVisitFee" pattern="0.00" />
					  </c:if>
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance != null and workItem.bv.carAuditResult.outVisitDistance > 0}">
					  <label class="lab"><span style="color: #ff0000"></span>外访费：</label>
					  </c:if>
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance == 0}">
						<input name="carCheckRate.outVisitFee" class="required input_text178 number" value ="0.00" type="text" readonly="readonly" id="outVisitFee" pattern="0.00" />元
					  </c:if>
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance > 0 and workItem.bv.carAuditResult.outVisitDistance <= 50 }">
						<input name="carCheckRate.outVisitFee" class="required input_text178 number" value ="200.00" type="text" readonly="readonly" id="outVisitFee" pattern="0.00" />元
					  </c:if>
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance > 50 and workItem.bv.carAuditResult.outVisitDistance <= 100 }">
						<input name="carCheckRate.outVisitFee" class="required input_text178 number" value ="300.00" type="text" readonly="readonly" id="outVisitFee" pattern="0.00" />元
					  </c:if>
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance > 100 and workItem.bv.carAuditResult.outVisitDistance <= 150 }">
						<input name="carCheckRate.outVisitFee" class="required input_text178 number" value ="400.00" type="text" readonly="readonly" id="outVisitFee" pattern="0.00" />元
					  </c:if>
					  <c:if test="${workItem.bv.carAuditResult.outVisitDistance > 150 }">
						<input name="carCheckRate.outVisitFee" class="required input_text178 number" value ="500.00" type="text" readonly="readonly" id="outVisitFee" pattern="0.00" />元
					  </c:if>
					</td>
					
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000"></span>合同金额：</label> 
						<input name="carCheckRate.contractAmount" class="required input_text178" id="contractMoneyId"
						type="text" value='<fmt:formatNumber value="${workItem.bv.carAuditResult.auditAmount}" pattern="0.00" />' readonly="readonly"/>元</td>
					<td><label class="lab"><span style="color: #ff0000"></span>实放金额：</label>
						<input class="required input_text178" type="text" readonly="readonly" id="actualPayMoney"/>
						<input name="carCheckRate.feePaymentAmount" class="required input_text178" type="hidden" id="actualPayMoneyHidden"/>元
						<input type="hidden" name="carCheckRate.grantAmount" id="carGrantAmountId"/>
						<input type="hidden" name="grantAmount" id="grantAmountId"/>
						<input type="hidden" name="deductsAmount" id="deductAmount"/>
						<input type="hidden" name="carCheckRate.deductAmount" id="deductAmountId"/>
						</td>
					<td><label class="lab"><span style="color: #ff0000"></span>月还金额：</label>
						<input class="required input_text178 number" type="text" readonly="readonly" id="monthBackAmount"/>
						<input name="carCheckRate.monthRepayAmount"
						class="required input_text178 number" type="hidden" id="monthBackAmountHidden"/>元</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000"></span>综合服务费：</label> 
						<input class="required input_text178" type="text" readonly="readonly" id="mulMoney"/>
						<input name="carCheckRate.comprehensiveServiceFee"
						class="required input_text178" type="hidden" id="mulMoneyHidden"/>元</td>
					<td><label class="lab"><span style="color: #ff0000"></span>审核费：</label>
						<input class="required input_text178"
						type="text" readonly="readonly" id="auditMoney"/>
						<input name="carCheckRate.auditFee" class="required input_text178"
						type="hidden" id="auditMoneyHidden"/>元</td>
					<td><label class="lab"><span style="color: #ff0000"></span>咨询费：</label>
						<input class="required input_text178" type="text" readonly="readonly" id="consultMoney"/>
						<input name="carCheckRate.consultingFee" type="hidden" id="consultMoneyHidden"/>元</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000"></span>居间服务费：</label> 
							<input class="required input_text178" type="text" readonly="readonly" id="mediacyMoney"/>
						<input name="carCheckRate.intermediaryServiceFee" type="hidden" id="mediacyMoneyHidden"/>元</td>
					<td><label class="lab"><span style="color: #ff0000"></span>信息服务费：</label>
						<input class="required input_text178" type="text" readonly="readonly" id="infoMoney"/>
						<input name="carCheckRate.informationServiceCharge" type="hidden" id="infoMoneyHidden"/>元</td>
					<td></td>
				</tr>
			</table>
			<input type="hidden" value="TO_SIGN" name="checkResult">
			<input type="hidden" value="${workItem.bv.carContract.contractCode}" name="contractCode">
		</div>
		<div class="tright pt10 pr30" style="position: fixed;bottom:20px;right:1px;">
			<input type="button" class="btn btn-primary" id="handleCheckRateInfo" value="提交"></input>
			
			<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
			onclick="JavaScript:history.go(-1);"></input>
			
			
		</div>
	</form>
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
								<td><lable class="lab"><span style="color: red;">*</span>退回原因：</lable>
										<textarea style="width: 400px; height: 150px;"
											name="backReason" id="backReason" class="required"></textarea>
										<input type="hidden" value="BACK_END_AUDIT" name="checkResult">
								</td>
							</tr>
						</table>
					</div>
				</form>
				<div class="modal-footer" >
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