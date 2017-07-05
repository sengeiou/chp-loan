<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/doAdvanceSettledInfo.js"></script>
</head>
<body>
	<form id="doAdvanceSettledInfoForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/doAdvanceSettled/save" method="post" class="form-horizontal">
			<h2 class ="f14 pl10 mt20">基本信息&nbsp;&nbsp;&nbsp;</h2>
			<input type="hidden" name="id" value="${paybackCharge.id}" id="chargeId">
			<input type="hidden" name="applyBuleAmount" value="${paybackCharge.payback.paybackBuleAmount}"/>
		    <input type="hidden" name="payback.id" value="${paybackCharge.payback.id }" id="paybackId"/>
		<div class="box2">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab" style="width:155px">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${paybackCharge.contractCode}" id="contractCode" readonly/></td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" value="${paybackCharge.loanCustomer.customerCertNum}" readonly/>
					</td>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" value="${paybackCharge.loanCustomer.customerName}" readonly/>
					</td>
				</tr>
				<tr>
					<td><label class="lab" style="width:155px">产品类型：</label> 
						<input type="text" class="input_text178" value="${paybackCharge.jkProducts.productName }" readonly/></td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" value="<fmt:formatNumber value="${paybackCharge.contract.contractAmount }" pattern='#,##0.00'/>" readonly/>
					</td>
					<td><label class="lab">期供金额（元）：</label> 
						<input type="text" class="input_text178"  value="<fmt:formatNumber value="${paybackCharge.payback.paybackMonthAmount }" pattern='#,##0.00'/>" readOnly/>
						</td>
				</tr>
				<tr>
					<td><label class="lab" style="width:155px">借款期限：</label> 
						<input type="text" class="input_text178" value="${paybackCharge.contract.contractMonths }" readonly/></td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" value="${paybackCharge.loanInfo.dictLoanStatusLabel}" readonly/>
					</td>
				</tr>
			</table>
		</div>
			<h2 class ="f14 pl10 mt20">还款信息&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2">
			<table class="" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab" style="width:155px">未还违约金(滞纳金)及罚息总金额：</label>
						<input type="text" class="input_text178" value="<fmt:formatNumber value="${paybackCharge.penaltyTotalAmount}" pattern='#,##0.00'/>" readOnly/>
					</td>
					<td><label class="lab">提前结清金额：</label>
						<input type="text" class="input_text178" value="<fmt:formatNumber value="${paybackCharge.paybackMonth.monthBeforeFinishAmount }" pattern='#,##0.00'/>" readOnly/>
					</td>
					<td><label class="lab" style="width:120px">提前结清应还总金额：</label>
						<input type="text" class="input_text178" value="<fmt:formatNumber value="${paybackCharge.settleTotalAmount }" pattern='#,##0.00'/>" readOnly/>
					</td>
				</tr>
				<tr>
					<td><label class="lab" style="width:155px">蓝补金额：</label> 
						<input type="text" class="input_text178" value="<fmt:formatNumber value="${paybackCharge.payback.paybackBuleAmount }" pattern='#,##0.00'/>" readOnly/>
					</td>
					<td><label class="lab">提前结清申请资料：</label>
						<input id="files" name="files" type="file">
					<!--  	<input name=uploadPath type="hidden"  value="${paybackCharge.uploadPath }">
						<input type="button" class="btn_edit" id ="downZip"  docId = "${paybackCharge.uploadPath }" fileName="${paybackCharge.uploadFilename }" value="${paybackCharge.uploadFilename }"/>
					-->
					</td>
					<td><label class="lab" style="width:120px">减免金额：</label>
						<input type="text" class="input_text178" value="<fmt:formatNumber value="${paybackCharge.creditAmount }" pattern='#,##0.00'/>" readOnly/>
					</td>
				</tr>
			</table>
		</div>
		<div class="pull-right mt10 pr30" id="qishu_div4"">
			<button class="btn btn-primary" class="btn btn-primary" type="button" id="submitBtn" >确认</button>
			<input class="btn btn-primary" type="button" id="giveUpDoStoreBtn" value="放弃">
			<input id="btnCancel" class="btn btn-primary" type="button" value="返 回" onclick="JavaScript:window.location='${ctx}/borrow/payback/doAdvanceSettled/list'" />
		</div>
	</form>
</body>
</html>
