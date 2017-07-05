<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/applyPaybackInfo.js"></script>
</head>
<body>
	<form id="applyPayLaunchForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/applyPaybackUse/saveElectricApplyPaybackUse" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${payback.id}" />
		<input type="hidden" name="paybackApply.dictPayUse" value="${payback.paybackApply.dictPayUse}" />
		<input type="hidden" name="paybackCurrentMonth" value="${payback.paybackCurrentMonth}" />
		<div class="control-group">
			<table class="" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${payback.contractCode}" readonly/></td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" id="customerCertNum" name="loanCustomer.customerCertNum" value="${payback.loanCustomer.customerCertNum}" readonly/>
					</td>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" id="customerName" name="loanCustomer.customerName" value="${payback.loanCustomer.customerName}" readonly/>
					</td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="text" class="input_text178" id="productType" name="jkProducts.productName" value="${payback.jkProducts.productName }" readonly/></td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" name="contract.contractAmount" value="<fmt:formatNumber value="${payback.contract.contractAmount }" pattern='0.00'/>" readonly/>
					</td>
					<td><label class="lab">期供金额（元）：</label> 
						<input type="text" class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value="${payback.paybackMonthAmount }" pattern='0.00'/>" readOnly/></td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> 
						<input type="text" class="input_text178" name="contract.contractMonths" value="${payback.contract.contractMonths }" readonly/></td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" id="dictLoanStatus" name="loanInfo.dictLoanStatus" value="${payback.loanInfo.dictLoanStatus }" readonly/>
					</td>
					<td><label class="lab">渠道：</label> 
                        <input type="text" class="input_text178" id="loanFlag" name="loanInfo.loanFlag" value="${payback.loanInfo.loanFlag }" readonly/>
                    </td>
				</tr>
			</table>
		</div>
			<h2 class ="table1">还款信息&nbsp;&nbsp;&nbsp;</h2>
		<c:choose>
			<c:when test='${payback.paybackApply.dictPayUse=="1" }'>
				<div class="box2">
					<table class="" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="lab">蓝补金额:</label>&nbsp; <input
								type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">冲抵方式:</label>
                                <select name="paybackCharge.dictOffsetType">
                                        <option>请选择</option>
                                        <option>冲抵期供</option>
                                        <option>冲抵违约金罚息总额</option>
                                </select>
                            </td>
							<td><label class="lab">逾期原因:</label>
								<select name="paybackCharge.monthOverdueMes">
										<option>请选择</option>
										<option>卡号错误</option>
										<option>客户外省出差</option>
										<option>资金周转困难</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label class="lab">逾期未还期供总金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackMonth.monthAmountSum" value="<fmt:formatNumber value="${payback.paybackMonth.monthAmountSum }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">未还违约金总金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackMonth.monthPenaltySum" value="<fmt:formatNumber value="${payback.paybackMonth.monthPenaltySum }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">未还罚息总金额:</label>&nbsp; <input
								type="text" class="input_text178" name = "paybackMonth.monthInterestPunishSum" value="<fmt:formatNumber value="${payback.paybackMonth.monthInterestPunishSum }" pattern='0.00'/>" readOnly/>
							</td>
						</tr>
						<tr>
							<td><label class="lab">申请还期供总额:</label>&nbsp; 
							<input type="text" class="input_text178" id="monthBlusAmount" name="paybackCharge.applyAmountPayback" />
							</td>
							<td><label class="lab">申请还违约金总额:</label>&nbsp; 
								<input type="text" class="input_text178" id="monthBlusAmount" name="paybackCharge.applyAmountViolate" />
							</td>
							<td><label class="lab">申请还罚息总额:</label>&nbsp; <input
								type="text" class="input_text178" id="monthBlusAmount" name="paybackCharge.applyAmountPunish" />
							</td>
						</tr>
						<tr>
							<td><label class="lab">本次蓝补冲抵金额:</label>&nbsp; <input
								type="text" class="input_text178" id ="offsetBlusAmount" name="paybackApply.offsetBlusAmount" readOnly/>
							</td>
							<td><label class="lab">蓝补冲抵余额:</label>&nbsp; <input
								type="text" class="input_text178" id="bulsbalanceAmount" name="paybackApply.bulsbalanceAmount" readOnly/>
							</td>
							<td><label class="lab">还款冲抵日期:</label>&nbsp; 
								<input type="text" class="input_text178" name="modifyTime" value="<fmt:formatDate value='${payback.modifyTime}' type='date' pattern="yyyy-MM-dd"/>" readOnly/>
							</td>
						</tr>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="box2">
					<table class="" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="lab">未还违约金及罚息总金额:</label>&nbsp;<input
								type="text" class="input_text178" name="paybackCharge.penaltyTotalAmount" value="<fmt:formatNumber value="${payback.paybackMonth.penaltyInterest }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">提前结清金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackMonth.monthBeforeFinishAmount" value="<fmt:formatNumber value="${payback.paybackMonth.monthBeforeFinishAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">提前结清应还总金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackCharge.settleTotalAmount" value="<fmt:formatNumber value="${payback.paybackMonth.shoudBackAmount }" pattern='0.00'/>" readOnly/>
							</td>
						</tr>
						<tr>
							<td><label class="lab">蓝补金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackBuleAmount" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">提前结清申请资料:</label>&nbsp; 
								<!-- <a id ="" class="btn" >上传</a>--> 
							<input name="files" type="file" >
							</td>
							<td><label class="lab">减免金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackMonth.creditAmount" value="<fmt:formatNumber value="${payback.paybackMonth.creditAmount }" pattern='0.00'/>" readOnly/>
							</td>
						</tr>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="pull-right" id="qishu_div4" float:right">
			<button class="btn btn-primary" class="submit" type="submit" id="applyPaySaveBtn">确认</button>
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
		</div>
	</form>
</body>
</html>
