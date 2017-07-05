<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackDoneDetails.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
</head>
<body>

 <div id="confirmModal" class="dialogbox">
	   <div style="width: 1000px;">
					<table class="table table-hover table-bordered table-condensed" id="confirmTab" style="margin:0">
					  <thead>
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
							<td>${payback.loanInfo.loanStoreOrgId}</td>
							<td>${payback.loanInfo.dictLoanStatusLabel }</td>
							<td><fmt:formatNumber value='${payback.contract.contractAmount}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${payback.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00" ></fmt:formatNumber></td>
							<td><fmt:formatNumber value='${payback.loanGrant.grantAmount}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${payback.contract.contractMonths}' pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value='${payback.paybackMonthAmount}' pattern="#,##0.00" /></td>
					        <td><fmt:formatNumber value='${payback.paybackMonth.monthsAomuntPaybacked}' pattern="#,##0.00" /></td>
					        <td>${payback.paybackMaxOverduedays}</td>
					        <td><fmt:formatNumber value='${payback.paybackMonth.penaltyInterest}' pattern="#,##0.00" /></td>
					        <td><fmt:formatNumber value='${payback.paybackMonth.overDueAmontPaybackedSum}' pattern="#,##0.00" /></td>
							</td>
						</tr>
					</thead>
				</table>
						<table  class="mt20"
							cellspacing="0">
							<tr>
								<td><label class="lab">累计减免金额：</label>
								<input type="text" class="input_text178" 
							     value="<fmt:formatNumber value='${payback.paybackMonth.creditAmount}' pattern="#,##0.00" />"readonly/>
								 </td>
								<td><label class="lab">减免人：</label> <input
									type="text" id="remissionBy" value="${ payback.remissionBy }"
									class="input_text178" readonly/></td>
								<td><label class="lab">蓝补金额：</label> 
								<input type="text" class="input_text178" 
							     value="<fmt:formatNumber value='${payback.paybackBuleAmount}' pattern="#,##0.00" />" readonly/>
								</td>
								</td>
							</tr>
							<tr>
								<td><label class="lab"> 是否确认结清：</label>
								<c:if test="${payback.dictPayStatus eq 2}"> 
						                         是
					            </c:if>
					            <c:if test="${payback.dictPayStatus ne 2}"> 
						                         否
					            </c:if>	
								</td>
									
								<td id="isTopCashBack"><label class="lab"> 是否返款：</label> 
								<c:if test="${payback.dictPayStatus ne 2 || payback.urgeServicesMoney.urgeDecuteMoeny le 0 || payback.paybackMaxOverduedays gt 30}">
								    否
								</c:if>
								<c:if test="${payback.dictPayStatus eq 2 && payback.paybackMaxOverduedays le 30 && payback.urgeServicesMoney.urgeDecuteMoeny gt 0}">
								   是
								</c:if>
								</td>
								<c:if test="${payback.dictPayStatus eq 2 && payback.urgeServicesMoney.urgeDecuteMoeny gt 0}">
								<td id="isSettleMoney"><label class="lab">返款金额：</label>
								<input  class="input_text178"  value="<fmt:formatNumber value='${payback.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00" />"
							     type="text" size="20"  readonly>
								</td>
								</c:if>
							</tr>
							<tr>
								<td><label class="lab">审核意见：</label>
								<input class="input_text178" value="${payback.remarks}" readonly="true"></input>
								</td>
							</tr>
						</table>
			</div>
		</div>
 
</body>
</html>