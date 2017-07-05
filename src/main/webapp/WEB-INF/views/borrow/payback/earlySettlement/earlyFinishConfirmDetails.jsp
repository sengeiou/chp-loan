<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/payback/earlyFinishConfirmDetails.js"></script>
<script type="text/javascript" src="${context}/js/payback/historicalRecords.js"></script>
</head>
<body>
   <div id="earlyFinishModal" class="dialogbox" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true">
		<form id="confirmInfoForm">
			<input type="hidden" id="chargeId" name="id" class="input_text178" value="${paybackCharge.id }" />
			<input type="hidden" id="id" name="payback.id" class="input_text178" value="${paybackCharge.payback.id }" />
			<input type="hidden" id="contractCode" name="contractCode" class="input_text178"  value="${paybackCharge.contractCode }" />
			<input type="hidden" id="paybackContractCode" name="payback.contractCode" class="input_text178"  value="${paybackCharge.contractCode }" />
			<input type="hidden" id="paybackCurrentMonth" name="payback.paybackCurrentMonth" class="input_text178" value="${paybackCharge.payback.paybackCurrentMonth}" />
			<input type="hidden" id="contractVersion" name="contract.contractVersion" class="input_text178" value="${paybackCharge.contract.contractVersion }"/>
			<input type="hidden" id="id" name="paybackMonth.id" class="input_text178" value="${paybackCharge.paybackMonth.id }"/>	
			<input type="hidden" id="contractMonth" name="contract.contractMonths" class="input_text178" value="${paybackCharge.contract.contractMonths }"/>	
		    <input type="hidden" id="dictLoanStatus" name="loanInfo.dictLoanStatus" class="input_text178" value="${paybackCharge.loanInfo.dictLoanStatus }"/>
		    <input type="hidden" id="dictPayStatus" name="payback.dictPayStatus" class="input_text178" value="${paybackCharge.payback.dictPayStatus }"/>
		    <input type="hidden" id="applyBuleAmount" name="applyBuleAmount" class="input_text178" value="${paybackCharge.applyBuleAmount }"/>	
			<input type="hidden" id="loanInfoId" name="payback.loanInfo.id" class="input_text178" value="${paybackCharge.loanInfo.id }"/>		
			<input type="hidden" id="loanFlag" name="payback.loanInfo.loanFlag" class="input_text178" value="${paybackCharge.loanInfo.loanFlag }"/>		
			<input type="hidden" id="loanCode" name="contract.loanCode" class="input_text178" value="${paybackCharge.contract.loanCode }"/>
		<div>
			<table class="table table-bordered table-condensed" id="confirmTab" style="margin:0">
				<tr style="background-color: #EEEEE0;">
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>门店名称</th>
					<th>合同金额</th>
					<th>放款金额</th>
					<th>借款状态</th>
					<th>期供金额</th>
				</tr>
				<tr style="background-color: #ffffff;">
					<td>${paybackCharge.contractCode }</td>
					<td>${paybackCharge.loanCustomer.customerName }</td>
					<td>${paybackCharge.loanInfo.loanStoreOrgName}</td>
					<td><fmt:formatNumber value='${paybackCharge.contract.contractAmount}' pattern="#,##0.00" /></td>
					<td><fmt:formatNumber value='${paybackCharge.loanGrant.grantAmount}' pattern="#,##0.00" /></td>
					<td>${paybackCharge.loanInfo.dictLoanStatusLabel}</td>
					<td><fmt:formatNumber value='${paybackCharge.payback.paybackMonthAmount}' pattern="#,##0.00" /></td>
				</tr>
				<tr style="background-color: #EEEEE0;">
					<th>已还期供金额</th>
					<th>已还催收服务费</th>
					<th>最长逾期天数</th>
					<th>未还违约金(滞纳金)及罚息金额</th>
					<th>已还违约金(滞纳金)及罚息金额</th>
					<th colspan="2">申请提前结清日期</th>
				</tr>
				<tr style="background-color: #ffffff;">
					<td><fmt:formatNumber value='${paybackCharge.paybackMonth.monthsAomuntPaybacked}' pattern="#,##0.00" /></td>
			        <td><fmt:formatNumber value='${paybackCharge.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00"/></td>
			        <td>${paybackCharge.payback.paybackMaxOverduedays}</td> 
			        <td><fmt:formatNumber value='${paybackCharge.paybackMonth.penaltyInterest}' pattern="#,##0.00" /></td>
			        <td><fmt:formatNumber value='${paybackCharge.paybackMonth.overDueAmontPaybackedSum}' pattern="#,##0.00" /></td>
					<td colspan="2"><fmt:formatDate value="${paybackCharge.createTime }" type="date" /></td>
				</tr>
			</table>
		</div>
		<div class="modal-body">
			<table class="table1" cellpadding="0" cellspacing="0" style="width: 100%;">
			    <tr>
					<td style="width: 33%;">
					   <label class="lab">减免提前结清金额：</label> 
						<input type="text"  class="input_text178" 
						value="<fmt:formatNumber value='${paybackCharge.paybackMonth.monthBeforeReductionAmount}' pattern="#,##0.00" />" readonly/>
					
					    <input type="hidden" id = "monthBeforeReductionAmount" name="paybackMonth.monthBeforeReductionAmount"  
					    value="${paybackCharge.paybackMonth.monthBeforeReductionAmount }" readonly />
					</td>
					<td style="width: 33%;"><label class="lab" style="width:200px">累计减免违约金(滞纳金)及罚息金额：</label> 
						<input type="text" class="input_text178" 
						value="<fmt:formatNumber value='${paybackCharge.paybackMonth.creditAmount}' pattern="#,##0.00" />" readonly/>
					</td>
					<td style="width: 34%;" align="center"><label class="lab" style="width:155px">提前结清应还款总额：</label> 
						<input type="text" class="input_text178" 
						value="<fmt:formatNumber value='${paybackCharge.settleTotalAmount}' pattern="#,##0.00" />" readonly/>
					    <input type="hidden" id = "settleTotalAmount" name="settleTotalAmount" class="input_text178" 
						value= "${paybackCharge.settleTotalAmount}" />
					</td>
				</tr>
				<tr>
					<td><label class="lab">申请还款金额：</label> 
						<input type="text" id = "reductionBy"  class="input_text178" 
						value="<fmt:formatNumber value='${paybackCharge.applyBuleAmount}' pattern="#,##0.00" />" readonly/>
					</td>
					<td><label class="lab" style="width:200px">蓝补余额：</label> 
						<input type="text" class="input_text178" 
						value="<fmt:formatNumber value='${paybackCharge.payback.paybackBuleAmount}' pattern="#,##0.00" />" readonly/>
					
					    <input type="hidden" id="paybackBuleAmount" name="payback.paybackBuleAmount" class="input_text178" 
						value="${paybackCharge.payback.paybackBuleAmount}"/>
					</td>
					<td style="width: 34%;" align="center"><label class="lab" style="width:155px">一次性提前结清应还款金额：</label> 
						<input type="text"  class="input_text178"
						value="<fmt:formatNumber value='${paybackCharge.paybackMonth.monthBeforeFinishAmount}' pattern="#,##0.00" />" readonly/>
						
						<input type="hidden" class="input_text178" id = "monthBeforeFinishAmount" name="paybackMonth.monthBeforeFinishAmount"
						value="${paybackCharge.paybackMonth.monthBeforeFinishAmount}" />
					</td>
				</tr>
				<tr>
					<td><label class="lab">是否确认结清：</label> 
						<input type="radio" name="isContfirm" value="0" checked="checked">是 
						<input type="radio" name="isContfirm" value="1">否
					</td>
					<td id="isTopCashBack"><label class="lab" style="width:200px"> 是否返款：</label> 
						<input type="radio" name="isTopCashBack" value="0" <c:if test="${paybackCharge.payback.paybackMaxOverduedays le 30 && paybackCharge.urgeServicesMoney.urgeDecuteMoeny gt 0}"> checked</c:if>>是
						<input type="radio" name="isTopCashBack" value="1" <c:if test="${paybackCharge.urgeServicesMoney.urgeDecuteMoeny le 0 || paybackCharge.payback.paybackMaxOverduedays gt 30}"> checked</c:if>>否
					</td>
					<td id="isSettleMoney" style="width: 34%;" align="center"><label class="lab" style="width:155px">返款金额：</label>
			  			<input type="text" id="paybackBak"  name="UrgeServicesBackMoney.paybackBackMoney" value="<fmt:formatNumber value='${paybackCharge.urgeServicesMoney.urgeDecuteMoeny}' pattern="###0.00" />"/>
						<input class="input_text178" type="hidden" id="paybackBackAmount" type="text" size="20" value="${paybackCharge.urgeServicesMoney.urgeDecuteMoeny}">
					</td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab" style="vertical-align: top;">审核意见：</label>    
						<textarea name="applyBackMes" id="returnReason" class="form-control" rows="3" style="line-height: 0px; height: 60px; width: 85%;"></textarea>
					</td>
				</tr>
			</table>
		</div>
	 	<div class="tright pr30 pt10">
	         <button class="btn btn-primary" id="submitConfirmBtn">确定</button>
	         <button class="btn btn-primary"  id="closeBtn">关闭</button>
       	</div>
		</form>
	</div>
	<div id="backDiv" style="display: none">
		<p><span class="red">是否确认提前结清确认不通过？</span></p>
    </div>
</body>
</html>