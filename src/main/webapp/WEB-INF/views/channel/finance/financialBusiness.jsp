<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default"/>
<title>大金融业务列表</title>
<script src="${context}/js/channel/goldcredit/onlyNumber.js" type="text/javascript"></script>
<script type="text/javascript">
$(function () {
	$("#clearBtn").click(function (){
		$(':input','#financesForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
	});
	$(".onlyFloat").onlyFloat();
});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	
	$("#financesForm").attr("action", "${ctx}/channel/financial/business/init");
	$("#financesForm").submit();
	return false;
}
</script>
</head>
<body>
	<div class="control-group">
		<form:form method="post" modelAttribute="params" action="${ctx}/channel/financial/business/init"
			id="financesForm">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		 	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">借款ID：</label>
					<form:input type="text" class="input_text178" path="loanCode"></form:input></td>
					<td><label class="lab">债权金额：</label> 
					<form:input type="text" class="input_text70 onlyFloat" path="stratContractAmount" style="ime-mode:disabled"></form:input>-<form:input type="text" class="input_text70 onlyFloat" path="endContractAmount" style="ime-mode:disabled"></form:input>&nbsp;元
					</td>
					<td><label class="lab">债权状态：</label>
					<form:select
							class="select180" path="creditType">
							<form:option value="">全部</form:option>
							<form:option value="4">结清己确认</form:option>
							<form:option value="2">债权己确认</form:option>
							<form:option value="3">提前结清</form:option>
							<form:option value="1">己放款</form:option>
						</form:select></td>
				</tr>
				<tr>
					<td><label class="lab">债权确认时间：</label>
					<input type="text" class="input_text70 Wdate" name="stratCreditorConfirmDate" id = "stratCreditorConfirmDate"
					value="<fmt:formatDate value='${params.stratCreditorConfirmDate}' pattern='yyyy-MM-dd'/>"  
					onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endCreditorConfirmDate\')}'})" style="cursor: pointer">-<input type="text" class="input_text70 Wdate" 
					name="endCreditorConfirmDate" id = "endCreditorConfirmDate" value="<fmt:formatDate value='${params.endCreditorConfirmDate}' pattern='yyyy-MM-dd'/>" 
					onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'stratCreditorConfirmDate\')}'})" style="cursor: pointer"></td>
				</tr>
			</table>

			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary"
					onclick="document.forms[0].submit();" value="搜索">
				<button class="btn btn-primary" id="clearBtn">清除</button>
			</div>
		</form:form>
	</div>
	<p class="mb5">
		&nbsp;&nbsp;<label class="lab1"><span class="red"> 债权合计金额:</span></label><label
			class="red"> <fmt:formatNumber value='${amount }'
				pattern="#,##0.00" /></label><label class = "red">元</label> <input type="hidden" id="hiddenDeduct"
			value="0.00" />  &nbsp;&nbsp;<label class="lab1"><span
			class="red">总笔数:</span></label> <label class="red">${financialBusiness.count }</label><label class = "red">&nbsp;笔</label> <input
			type="hidden" id="num" value="${totalNum }"> <input
			type="hidden" id="hiddenNum" value="0" />
	</p>
	<sys:columnCtrl pageToken="financial"></sys:columnCtrl>
	<div class="box5" style="overflow-x:auto;">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>序号</th>
	                <th>借款ID</th>
					<th>债务人</th>
					<th>身份证号</th>
					<th>借款用途</th>
					<th>借款类型</th>
					<th>原始期限（月）</th>
					<th>原始借款开始日期</th>
					<th>原始借款到期日期</th>
					<th>还款日</th>
					<th>还款金额</th>
					<th>债权金额（元）</th>
					<th>债权月利率（%）</th>
					<th>债权确认时间</th>
					<th>结清确认时间</th>
					<th>提前结清日期</th>
					<th>债权状态</th>		
				</tr>
			</thead>
			<tbody>
				<c:if test="${ financeListShow!=null && fn:length(financeListShow)>0}">
					<c:forEach items="${financeListShow}" var="item" varStatus="index">
						<tr>
							<td>${index.count}</td>
							<td>${item.loanCode}</td>
							<td>${item.loanCustomerName}</td>
							<td>${item.customerCertNum}</td>
							<td>
								<c:forEach items="${fns:getDictList('jk_loan_use')}" var="loan_use">
									<c:if test="${loan_use.value == item.dictLoanUse }">
										${loan_use.label}
									</c:if>
						 		</c:forEach>
							</td>
							<td>${item.classType }</td>
							<td>${item.loanMonths }</td>
							<td><fmt:formatDate value='${item.loanStartDate}' pattern='yyyy-MM-dd'/></td>
							<td><fmt:formatDate value='${item.loanEndDate}' pattern='yyyy-MM-dd'/></td>
							<td><fmt:formatDate value='${item.replayDay}' pattern='d'/></td>
							<td><fmt:formatNumber value='${item.contractMonthRepayAmount}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.contractAmount}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.feeMonthRate}'
									pattern="#,#0.00" /></td>
							<td><fmt:formatDate value="${item.creditConfirmDate}" pattern = "yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value="${item.settleConfirmDate}" pattern = "yyyy-MM-dd HH:mm:ss" /></td>
							<td><fmt:formatDate value = "${item.settleDate }" pattern = "yyyy-MM-dd HH:mm:ss"/></td>
							<td>
								<c:if test="${item.creditType == '4' }">结清己确认</c:if>
								<c:if test="${item.creditType == '3' }">提前结清</c:if>
								<c:if test="${item.creditType == '2' }">债权己确认</c:if>
								<c:if test="${item.creditType == '1' }">己放款</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${ financialBusiness==null || fn:length(financialBusiness.list)==0}">
					<tr>
						<td colspan="17" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>

		</table>
	</div>
	</div>
	<div class="pagination">${financialBusiness}</div>
</body>
</html>