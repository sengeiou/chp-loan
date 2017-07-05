<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/applyPaybackInfo.js"></script>
<script type="text/javascript">
function download(filetype)
{
	art.dialog.tips("文件下载中...");
	window.location.href = ctx
			+ "/borrow/payback/applyPaybackUse/fileDownLoad?fileType="+filetype;
}
</script>
</head>
<body>
	<p class="tright pt5 mb5 pr10"><span id="applyPaybackMonthBth" class="btn btn-small " >还款明细</span></p>
	<form id="applyPayinfoForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/applyPaybackUse/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${payback.id}" />
		<input type="hidden" id="dictPayUse" name="paybackApply.dictPayUse" value="${payback.paybackApply.dictPayUse}" />
		<input type="hidden" name="paybackCurrentMonth" value="${payback.paybackCurrentMonth}" />
		<input type="hidden" name="contract.contractMonths" value="${payback.contract.contractMonths}" />
		<input type="hidden" name="dictPayStatus" value="${payback.dictPayStatus}" />
		<input type="hidden" id="paybackMonthId" value="${payback.paybackMonth.id}" />
		<input type="hidden" id="contractVersion" name="contract.contractVersion" value="${payback.contract.contractVersion }"/>
		<input type="hidden" id="loanCode" value="${payback.loanInfo.id }" name="loanInfo.id"/>
		<input type="hidden" id="paybackDay" value="${payback.paybackDay }" name="paybackDay"/>
		<input type="hidden" id="dictLoanStatus" value="${payback.loanInfo.dictLoanStatus }"/>
		<input type="hidden" id="msg" name="msg" value="${message}">
		<input name="finishTokenId" type="hidden" value="${payback.finishTokenId}" />
		<input name="finishToken" type="hidden" value="${payback.finishToken}" />
		<h2 class="f14 pl10 ">基本信息&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab" style="width:155px">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${payback.contractCode}" readonly/></td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" name="loanCustomer.customerCertNum" value="${payback.loanCustomer.customerCertNum}" readonly/>
					</td>
					<td><label class="lab" style="width:155px">客户姓名：</label> 
						<input type="text" class="input_text178" name="loanCustomer.customerName" value="${payback.loanCustomer.customerName}" readonly/>
					</td>
				</tr>
				<tr>
					<td><label class="lab" style="width:155px">产品类型：</label> 
						<input type="text" class="input_text178" name="jkProducts.productName" value="${payback.jkProducts.productName }" readonly/></td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" name="contract.contractAmount" value="<fmt:formatNumber value="${payback.contract.contractAmount }" pattern='0.00'/>" readonly/>
					</td>
					<td><label class="lab" style="width:155px">期供金额（元）：</label> 
						<input type="text" class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value="${payback.paybackMonthAmount }" pattern='0.00'/>" readOnly/></td>
				</tr>
				<tr>
					<td><label class="lab" style="width:155px">借款期限：</label> 
						<input type="text" class="input_text178" name="contract.contractMonths" value="${payback.contract.contractMonths }" readonly/></td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" name="loanInfo.dictLoanStatus" value="${payback.loanInfo.dictLoanStatusLabel}" readonly/>
					</td>
					<c:choose>
						<c:when test='${payback.paybackApply.dictPayUse=="5" }'>
						<td></td>
						</c:when>
						<c:otherwise>
							<td><label class="lab" style="width:155px">提前结清资料下载：</label> 
							<input type="input" onclick="download('1');" class="btn_edit" name="settled_btn" value="提前还款申请书模板"/></td>
						</c:otherwise>
					</c:choose>
				</tr>
			</table>
		</div>
			<h2 class ="f14 pl10 mt20">还款信息&nbsp;&nbsp;&nbsp;</h2>
		<c:choose>
			<c:when test='${payback.paybackApply.dictPayUse=="5" }'>
				<div class="box2">
					<table class="" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="lab">蓝补金额：</label>&nbsp; <input
								type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab" style="width:155px">逾期原因：</label>
								<select name="paybackCharge.monthOverdueMes" style="width:160px">
										<option>请选择</option>
										<option>卡号错误</option>
										<option>客户外省出差</option>
										<option>资金周转困难</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><label class="lab">逾期未还期供总金额：</label>&nbsp; <input
								type="text" class="input_text178" id="monthAmountSum" name="paybackMonth.monthAmountSum" value="<fmt:formatNumber value="${payback.paybackMonth.monthAmountSum }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab" style="width:155px">未还违约金(滞纳金)总金额：</label>&nbsp; <input
								type="text" class="input_text178" id="monthPenaltySum" name="paybackMonth.monthPenaltySum" value="<fmt:formatNumber value="${payback.paybackMonth.monthPenaltySum }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">未还罚息总金额：</label>&nbsp; <input
								type="text" class="input_text178" id="monthInterestPunishSum" name="paybackMonth.monthInterestPunishSum" value="<fmt:formatNumber value="${payback.paybackMonth.monthInterestPunishSum }" pattern='0.00'/>" readOnly/>
							</td>
						</tr>
						<tr>
							<td><label class="lab">还款冲抵日期：</label>&nbsp; 
								<input type="text" class="input_text178" name="paybackCharge.createTime" value="<fmt:formatDate value='${payback.modifyTime}' type='date' pattern="yyyy-MM-dd"/>" readOnly/>
								
								<!-- <label class="lab">申请还期供总额：</label>&nbsp; -->
							<input type="hidden" class="input_text178" id="monthBlusAmount" name="paybackCharge.applyAmountPayback" readOnly/> 
							</td>
							<td><label class="lab" style="width:155px">申请还违约金(滞纳金)总额：</label>&nbsp; 
								<input type="text" class="input_text178" id="monthBlusAmount" name="paybackCharge.applyAmountViolate" />
							</td>
							<td><label class="lab">申请还罚息总额：</label>&nbsp; <input
								type="text" class="input_text178" id="monthBlusAmount" name="paybackCharge.applyAmountPunish" />
							</td>
						</tr>
						<tr>
							<td><label class="lab">本次蓝补冲抵金额：</label>&nbsp; <input
								type="text" class="input_text178" id ="offsetBlusAmount" name="paybackApply.offsetBlusAmount" readOnly/>
							</td>
							<td><label class="lab" style="width:155px">蓝补冲抵余额：</label>&nbsp; <input
								type="text" class="input_text178" id="bulsbalanceAmount" name="paybackApply.bulsbalanceAmount" readOnly/>
							</td>
							<td>
							</td>
						</tr>
					</table>
				</div>
			</c:when>
			<c:otherwise>
				<div class="box2">
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab" style="width:185px">未还违约金(滞纳金)及罚息总金额：</label>
							<input type="text" class="input_text178" name="paybackCharge.penaltyTotalAmount" value="<fmt:formatNumber value="${payback.paybackMonth.penaltyInterest }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab">提前结清金额：</label> <input
								type="text" class="input_text178" name="paybackMonth.monthBeforeFinishAmount" value="<fmt:formatNumber value="${payback.paybackMonth.monthBeforeFinishAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab" style="width:155px">提前结清应还总金额：</label><input
								type="text" class="input_text178" name="paybackCharge.settleTotalAmount" value="<fmt:formatNumber value="${payback.paybackMonth.shoudBackAmount }" pattern='0.00'/>" readOnly/>
							</td>
						</tr>
						<tr>
							<td><label class="lab" style="width:185px">蓝补金额：</label><input
								type="text" class="input_text178" name="paybackCharge.applyBuleAmount" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab" >减免金额：</label><input
								type="text" class="input_text178"  value="<fmt:formatNumber value="${payback.paybackMonth.creditAmount }" pattern='0.00'/>" readOnly/>
							</td>
							<td><label class="lab" style="width:155px">提前结清申请资料：</label>
							<input  id="files" type="file" name="files">
							</td>
						</tr>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="pull-right mt10 pr30" id="qishu_div4">
			<input class="btn btn-primary" type="button" id="applyPayInfoSaveBtn" value="确认"/>
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" />
		</div>
	</form>
</body>
</html>
