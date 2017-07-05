<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/confirmPaybackDetails.js"></script>
<script type="text/javascript" src="${context}/js/payback/historicalRecords.js"></script>
<script type="text/javascript" src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
</head>
<body>
 <div id="confirmModal" class="dialogbox" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true">
	 <form id="confirmInfoForm" action="${ctx}/borrow/payback/confirmPayback/save" method="post">
			<input type="hidden" id="id" name="id" class="input_text178" value="${payback.id }"/>
			 <input type="hidden" id="contractCode" name="contractCode" class="input_text178" value="${payback.contractCode}"/>
			  <input type="hidden" id="loanCodeId" name="loanInfo.id" class="input_text178" value="${ payback.loanInfo.id}"/>
			  <input type="hidden" id="dictSourceType" value="${payback.loanInfo.dictSourceType}"/>
			<table class="table table-hover table-bordered table-condensed" id="confirmTab" style="margin:0">
						<tr>
							        <th>合同编号</th>
									<th >客户姓名</th>
									<th >门店名称</th>
									<th >借款状态</th>
									<th >合同金额</th>
									<th >已收催收服务费金额</th>
									<th >放款金额</th>
									<th >借款期数</th>
									<th >期供金额</th>
									<th >已还期供金额</th>
									<th >最长逾期天数</th>
									<th >未还违约金(滞纳金)及罚息金额</th>
									<th >已还违约金(滞纳金)及罚息金额</th>
						</tr>
						  <tr>
							<td>${payback.contractCode }</td>
							<td>${payback.loanCustomer.customerName }</td>
							<td>${payback.loanInfo.loanStoreOrgName}</td>
							<td>${payback.loanInfo.dictLoanStatusLabel }</td>
							<td><fmt:formatNumber value='${payback.contract.contractAmount}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${payback.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00" ></fmt:formatNumber></td>
							<td><fmt:formatNumber value='${payback.loanGrant.grantAmount}' pattern="#,##0.00" /></td>
							<td>${payback.contract.contractMonths}</td>
							<td><fmt:formatNumber value='${payback.paybackMonthAmount}' pattern="#,##0.00" /></td>
					        <td><fmt:formatNumber value='${payback.paybackMonth.monthsAomuntPaybacked}' pattern="#,##0.00" /></td>
					        <td>${payback.paybackMaxOverduedays}</td>
					        <td><fmt:formatNumber value='${payback.paybackMonth.penaltyInterest}' pattern="#,##0.00" /></td>
					        <td><fmt:formatNumber value='${payback.paybackMonth.overDueAmontPaybackedSum}' pattern="#,##0.00" /></td>
						</tr>
					
				</table>
				<div class="modal-body">
					<table class="table1" cellpadding="0"
						cellspacing="0">
						<tr>
							<td>
							    <label class="lab">累计减免金额：</label> 
							   	<input type="hidden" id="actualAmount" value='${payback.paybackMonth.actualAmount}'/> 
								<input type="hidden" id="moneyAmount" value='${payback.paybackMonth.moneyAmount}'/> 
								<input type="hidden" id="creditAmount" value='${payback.paybackMonth.creditAmount }'/>
								<input type="text" class="input_text178" 
							     value="<fmt:formatNumber value='${payback.paybackMonth.creditAmount }' pattern="#,##0.00" />" readonly/>
							</td>
							<td>
							   <label class="lab">减免人：</label> 
							   <input type="text" id="remissionBy" name="paybackMonth.modifyBy"
								class="input_text178" value="${payback.remissionBy }" readonly />
							</td>
							<td><label class="lab">蓝补金额：</label> 
							    <input type="hidden" id="paybackBuleAmount" name="paybackBuleAmount"
								class="input_text178" value="${payback.paybackBuleAmount }" />
								
								<input type="text" class="input_text178" 
							     value="<fmt:formatNumber value='${payback.paybackBuleAmount }' pattern="#,##0.00" />" readonly/>
							</td>
						</tr>
						<tr>
							<td><label class="lab">是否确认结清:</label> 
								<input type="radio" name="isConfirm" value="0" checked="checked">是 
								<input type="radio" name="isConfirm" value="1">否</td>
							<td id="isTopCashBack"><label class="lab"> 是否返款:</label> 
								<input type="radio" id="isTopCashBack" name="isTopCashBack" value="0" <c:if test="${payback.paybackMaxOverduedays le 30 && payback.urgeServicesMoney.urgeDecuteMoeny gt 0}"> checked</c:if>>是
								<input type="radio" id="isTopCashBackNot" name="isTopCashBack"  <c:if test="${payback.urgeServicesMoney.urgeDecuteMoeny le 0 || payback.paybackMaxOverduedays gt 30}"> checked</c:if> value="1">否
							</td>
							<td id="isSettleMoney" class="lab"><label style="width:200px">返款金额：</label> 
									<input	class="input_text178 isNeUrgeMoney" id="paybackBackAmountBak" name="UrgeServicesMoney.urgeMoeny" type="text" size="20"
									value="<fmt:formatNumber value='${payback.urgeServicesMoney.urgeDecuteMoeny }' pattern="0.00" />"> 
		  							<input type="hidden" id="paybackBackAmount"  value="${payback.urgeServicesMoney.urgeMoeny }">
							</td>
						</tr>
						<tr>
							<td><label class="lab">审核意见:</label> 
								<textarea name="returnReason" id="returnReason" class="form-control" rows="3" style="line-height: 40px; height: 55px; width: 175px;"></textarea></td>
						</tr>
					</table>
				</div>
			 <div class="tright pr30 pt10">
                 <button  class="btn btn-primary" id="submitConfirmBtn">确定</button>
                 <button class="btn btn-primary"  id="closeBtn">关闭</button>
              </div>
		</form>
 </div>
</body>
</html>