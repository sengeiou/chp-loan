<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default" />
<title>债权确认列表</title>
<script type="text/javascript" src = "${context}/js/channel/finance/creditorConfirmList.js">
</script>
<script type="text/javascript">
	$(function () {
		$("#stratContractAmount,#endContractAmount").blur(function () {
			var stratContractAmount = parseFloat($("#stratContractAmount").val(),10),
			endContractAmount = parseFloat($("#endContractAmount").val(),10);
			if (isNaN($(this).val())){//如果isNaN非数字的话返回的是true
				 art.dialog.alert("输入的债权金额必须为数字！");
				 $(this).val("").focus();
			} else if (stratContractAmount - endContractAmount > 0){
				art.dialog.alert("起始债权金额不能够大于债权终止债权金额！");
				$(this).focus().val("");
			}
		});
	});
	
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		
		$("#confirmForm").attr("action", "${ctx }/channel/financial/confirm/init");
		$("#confirmForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="control-group">
		<form:form method="post" modelAttribute="creditorComfirmParam"
			id="confirmForm" action="${ctx }/channel/financial/confirm/init">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		 	<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">借款ID：</label>
					<form:input type="text" class="input_text178" path="loanCode"></form:input></td>
					<td><label class="lab">债权金额：</label> <form:input type="text"
							class="input_text70" path="stratContractAmount" id = "stratContractAmount"></form:input>-<form:input type="text" class="input_text70"
							path="endContractAmount" id = "endContractAmount"></form:input>&nbsp;元</td>
				</tr>
				<tr>
					<td><label class="lab">原始借款开始日期：</label> <input
						name=loanStartDate id  = "loanStartDate"
						value="<fmt:formatDate value='${creditorComfirmParam.loanStartDate}' pattern='yyyy-MM-dd'/>"
						type="text" class="Wdate input_text70" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'loanEndDate\')}'})" style="cursor: pointer" />-<input name="loanEndDate" id = "loanEndDate"
						value="<fmt:formatDate value='${creditorComfirmParam.loanEndDate}' pattern='yyyy-MM-dd'/>"
						type="text" class="Wdate input_text70" size="10"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'loanStartDate\')}'})" style="cursor: pointer" />
					</td>
					<td><label class="lab">债权导出日期：</label> <input name="stratCreditExportDate" id = "stratCreditExportDate"
						value="<fmt:formatDate value='${creditorComfirmParam.stratCreditExportDate}' pattern='yyyy-MM-dd'/>"
						type="text" class="Wdate input_text70" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endCreditExportDate\')}'})" style="cursor: pointer">-<input
						name="endCreditExportDate" id = "endCreditExportDate"
						value="<fmt:formatDate value='${creditorComfirmParam.endCreditExportDate}' pattern='yyyy-MM-dd'/>"
						type="text" class="Wdate input_text70" size="10"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'stratCreditExportDate\')}'})" style="cursor: pointer">
					</td>
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
		<button class="btn btn-small" id="dao">导出excel</button>
		<button class="btn btn-small" id="confirm">确认</button>
		&nbsp;&nbsp;<label class="lab1"><span class="red">债权合计金额：</span></label><label id = "amount"
			class="red"> <fmt:formatNumber value='${amount}'
				pattern="#,##0.00" /></label><label class="red">元</label> <input
			type="hidden" id="hiddenDeduct" value="0.00" /> <input type="hidden"
			id="deduct" value="${amount}"> &nbsp;&nbsp;<label
			class="lab1"><span class="red">总笔数：</span></label> <label class="red" id = "count">${creditorConfirm.count }</label><label
			class="red">笔</label> <input type="hidden" id="num" value="${creditorConfirm.count }">
		<input type="hidden" id="hiddenNum" value="0" />
	</p>
	<sys:columnCtrl pageToken="creditorw"></sys:columnCtrl>
	<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id = "checkAll"/></th>
					<th>借款ID</th>
					<th>债务人</th>
					<th>身份证号</th>
					<th>借款用途</th>
					<th>借款类型</th>
					<th>原始期限（月）</th>
					<th>原始借款开始日期</th>
					<th>原始借款到期日期</th>
					<th>还款方式</th>
					<th>还款日</th>
					<th>还款金额</th>
					<th>债权金额（元）</th>
					<th>债权月利率（%）</th>
					<th>债权转入金额（元）</th>
					<th>债权转入期限（月）</th>
					<th>债权转出日期</th>
					<th>债权人</th>
					<th>债权导出日期</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${creditorListShow!=null && fn:length(creditorListShow)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${creditorConfirm.list}" var="item">
						<c:set var="index" value="${index+1}" />
						<tr>
							<td>
								<input type="checkbox" name="checkBoxItem"
								deductAmount='${item.contractAmount}'
								cid='${item.loanCode }' />
							</td>
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
							<td>${item.classType}</td>
							<td>${item.loanMonths}</td>
							<td><fmt:formatDate value="${item.loanStartDate}"
									pattern='yyyy-MM-dd' /></td>
							<td><fmt:formatDate value="${item.loanEndDate}"
									pattern='yyyy-MM-dd' /></td>
							<td>等额本息</td> 
							<td>${item.replayDay}</td>
							<td><fmt:formatNumber value='${item.contractMonthRepayAmount}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.contractAmount}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.feeMonthRate}'
									pattern="#,#0.00" /></td>
							<td><fmt:formatNumber
									value='${item.contractAmount}' pattern="#,#00.00" /></td>
							<td>${item.loanMonths}</td>
							<td><fmt:formatDate value="${item.loanEndDate}"
									pattern='yyyy-MM-dd' /></td>
							<td>${item.creditor}</td>
							<td><fmt:formatDate value="${item.creditorExportDate}"
									pattern='yyyy-MM-dd' /></td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if
					test="${ creditorConfirm==null || fn:length(creditorConfirm.list)==0}">
					<tr>
						<td colspan="19" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
		</table>
	</div>
	</div>
	<div class="pagination">${creditorConfirm}</div>
</body>
</html>