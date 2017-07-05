<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript"
	src="${context }/js/payback/paybackBlueTrans.js"></script>
<meta name="decorator" content="default" />
</head>
<body>
	<div class="control-group">
		<form id="inputForm" action="${ctx}/borrow/blue/PaybackBlue/saveTransData">
			<h2 class=" f14 pl10 ">付款方信息</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="95%">
				<tr>
					<td><label class="lab">客户姓名：</label> <input type="text"
						class="input_text178" value="${payback.loanCustomer.customerName}"
						readonly /></td>
					<td><label class="lab">证件号码：</label> <input type="text"
						class="input_text178"
						value="${payback.loanCustomer.customerCertNum}" readonly /></td>
				</tr>
				<tr>
					<td><label class="lab">合同编号：</label> <input type="text"
						class="input_text178" id="contractCodeLast"
						name="contractCodeLast" value="${payback.contractCode}"
						readonly="readonly" /></td>
					<td><label class="lab">借款状态：</label> <input type="text"
						class="input_text178" value="${payback.loanInfo.dictLoanStatus }"
						readonly /></td>
				</tr>
				<tr>
					<td><label class="lab">蓝补余额：</label> <input type="text"
						class="input_text178" id="paybackBuleAmountLast"
						name="paybackBuleAmountLast" value="0.00" readOnly /> <input
						type="hidden" class="input_text178" id="paybackBuleAmounts"
						value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>"
						readOnly /></td>
					<td><label class="lab">期供余额：</label> <input type="text"
						class="input_text178"
						value="<fmt:formatNumber value="${payback.paybackMonthAmount }" pattern='0.00'/>"
						readOnly /></td>
				</tr>
			</table>
			<c:if test="${ paybackOther!=null}">
				<h2 class=" f14 pl10 ">收款方信息</h2>
				<table class=" table1" cellpadding="0" cellspacing="0" border="0"
					width="95%">
					<tr>
						<td><label class="lab">客户姓名：</label> <input type="text"
							class="input_text178" id="customerName"
							name="loanCustomer.customerName" value="" readonly /></td>
						<td><label class="lab">证件号码：</label> <input type="text"
							class="input_text178" id="customerCertNum"
							name="loanCustomer.customerCertNum" value="" readonly /></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label> <input type="text"
							class="input_text178" id="contractCode" name="contractCode"
							readonly="readonly" /></td>
						<td><label class="lab">借款状态：</label> <input type="text"
							class="input_text178" id="dictLoanStatus"
							name="loanInfo.dictLoanStatus" readonly /></td>
					</tr>
					<tr>
						<td><label class="lab">转账金额：</label> <input type="text"
							id="transmitBuleAmount" name="transmitBuleAmount"
							class="input_text178 required"
							onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"
							value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" />
							<input type="hidden" id="paybackBuleAmount"
							name="paybackBuleAmount"></td>
						<td><label class="lab">转账用途：</label> <input type="text"
							id="transAmountFor" name="transAmountFor"
							class="input_text178 required" /></td>
					</tr>
				</table>
				<table id="tableList">
					<thead>
						<tr>
							<th>客户姓名</th>
							<th>证件号码</th>
							<th>合同编号</th>
							<th>借款状态</th>
							<th>蓝补余额</th>
							<th>期供金额</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:set var="index" value="0" />
						<c:forEach items="${paybackOther}" var="item">
							<c:set var="index" value="${index+1}" />
							<tr>
								<td>${item.loanCustomer.customerName }</td>
								<td>${item.loanCustomer.customerCertNum}</td>
								<td>${item.contractCode }</td>
								<td>${item.loanInfo.dictLoanStatus}</td>
								<td><fmt:formatNumber value='${item.paybackBuleAmount}'
										pattern="#,#00.00" /></td>
								<td><fmt:formatNumber value='${item.paybackMonthAmount}'
										pattern="#,#00.00" /></td>
								<td><input type="radio"
									onclick="changeSele('${item.loanCustomer.customerName }','${item.loanCustomer.customerCertNum}','${item.contractCode }','${item.loanInfo.dictLoanStatus}','${item.paybackBuleAmount}')" /></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<br>
			<div class="tright pr30">
				<c:if test="${paybackOther!=null}">
					<input type="button" id="submitBtn" value="提交" onclick="saveData()"
						class="btn btn-primary" />
				</c:if>
				<input type="button" value="关闭" onclick="art.dialog.close();"
					class="btn btn-primary" />
			</div>
		</form>
	</div>
</body>
</html>
