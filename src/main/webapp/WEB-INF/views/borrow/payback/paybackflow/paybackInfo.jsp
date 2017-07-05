\<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context }/static/jQueryRotate.js"></script>
<style type="text/css">
.pagination {
	background: none;
	position: static;
	width: auto
}
</style>
</head>
<body>
	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>
	<form id="paybackInfoForm" action="${ctx}/borrow/payback/dealPayback/save" method="post" class="form-horizontal">
		<input type="hidden" id = "paybackId" name="payback.id" value="${paybackApply.payback.id}" />
		<input type="hidden" name="id" value="${paybackApply.id}" />
		<input type="hidden" id="infoId"/>
		<input type="hidden" id="msg" value="${message}">
		<input type="hidden" name="manualMatchingTokenId" value="${paybackApply.manualMatchingTokenId}"/>
		<input type="hidden" name="manualMatchingToken" value="${paybackApply.manualMatchingToken}"/>
		<input type="hidden" id = "reqTime" value="${paybackApply.reqTime}"/>
		<input type="hidden" id="zhcz" name = "zhcz" value="${zhcz}"/>
		<h2 class="f14 pl10 ">基本信息&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${paybackApply.contractCode }" readonly/>
					</td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178"  name="loanCustomer.customerCertNum" value="${paybackApply.loanCustomer.customerCertNum }" readonly>
					</td>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178"  name="loanCustomer.customerName" value="${paybackApply.loanCustomer.customerName }" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="text" class="input_text178" name="contract.productType" value="${paybackApply.jkProducts.productName }" readonly/>
					</td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackApply.contract.contractAmount }' pattern="#,##0.00"/>" readonly>
						<input type="hidden"class="input_text178" name="contract.contractAmount" value="<fmt:formatNumber value='${paybackApply.contract.contractAmount }' pattern="0.00"/>" readonly>
					</td>
					<td><label class="lab">期供金额（元）：</label> 
						<input type="text"class="input_text178" value="<fmt:formatNumber value='${paybackApply.payback.paybackMonthAmount }' pattern="#,##0.00"/>" readonly>
						<input type="hidden"class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value='${paybackApply.payback.paybackMonthAmount }' pattern="0.00"/>" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> 
						<input type="text" class="input_text178" name="contract.contractMonths" value="${paybackApply.contract.contractMonths }" readonly>
					</td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" name="loanInfo.dictLoanStatus" value="${paybackApply.loanInfo.dictLoanStatusLabel}" readonly>
					</td>
					<td><label class="lab">门店：</label> 
						<input type="text" class="input_text178" value="${paybackApply.loanInfo.loanStoreOrgName}" readonly>
					</td>
				</tr>
			</table>
		</div>
		<h2 class="f14 pl10 mt20">还款信息</h2>
		<div class="box2 mb10">
		 <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td><label class="lab">蓝补金额：</label>
					<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackApply.payback.paybackBuleAmount }' pattern="#,##0.00"/>" readonly />
					<input type="hidden" id="paybackBuleAmount" name="payback.paybackBuleAmount"  value="<fmt:formatNumber value='${paybackApply.payback.paybackBuleAmount }' pattern="0.00"/>"/>
				</td>
				<td><label class="lab"> 还款方式：</label> 
					<input type="radio" style="margin-top: 5px" id="paymentsManual" name="dictRepayMethod" value="1" disabled="disabled"
					<c:if test="${paybackApply.dictRepayMethod=='1'}"> checked</c:if>/>汇款/转账
					<input type="radio" id="paymentsAuto" name="dictRepayMethod" value="0" disabled="disabled"
					<c:if test="${paybackApply.dictRepayMethod=='0'}" > checked</c:if> readonly/>划扣
				</td>
			</tr>
			</table>
		</div>
		<c:choose>
			<c:when test="${paybackApply.dictRepayMethod=='1'}">
				 <h2 class="f14 pl10 mt20">汇款账户&nbsp;&nbsp;&nbsp;</h2>
				<div class="box2" id="qishu_div1">
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab">实际到账总额：</label> 
							<input type="text" id="applyReallyAmountFormat" class="input_text178" value="<fmt:formatNumber value='${paybackApply.applyReallyAmount }' pattern="#,##0.00"/>" readonly/></td>
							<input type="hidden" id="applyReallyAmount" name="applyReallyAmount" value="<fmt:formatNumber value='${paybackApply.applyReallyAmount }' pattern="0.00"/>"/></td>
							<input type="hidden" name="applyAmount" value="<fmt:formatNumber value='${paybackApply.applyAmount }' pattern="0.00"/>"/>
							<td><label class="lab">存入账户：</label> 
								<select class="select180" id="storesInAccount" name="paybackTransferInfo.storesInAccount" disabled>
									<option>请选择</option>
									<c:forEach var="item" items="${middlePersonList }">
										<option value="${item.bankCardNo }" <c:if test="${paybackApply.dictDepositAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
									</c:forEach>
								</select>
							</td>
							<td><label class="lab">还款申请日期：</label> 
								<input type="text" name="applyPayDay" class="input_text178"
								value="<fmt:formatDate value='${paybackApply.applyPayDay}' type='date' pattern="yyyy-MM-dd" />" readonly/>
							</td>
						</tr>
					</table>
				</div>
				<div id="paybackTransferInfo">
					<table id='appendTab' class="table table-bordered table-condensed" cellpadding=" 0" cellspacing="0" border="0" width="100%">
						<tr>
							<td>存款方式</td>
							<td>存款支行</td>
							<td>存款时间</td>
							<td>存款时间点</td>
							<td>实际到账金额</td>
							<td>实际存款人</td>
							<td>存款凭条</td>
							<td>上传人</td>
							<td>上传时间</td>
							<td>操作</td>
						</tr>
						<c:forEach items="${paybackTransferInfoList}" var="item" varStatus="status">
							<tr>
								<td>
									<input type="hidden" class="input-mini"name="paybackTransferInfo.dictDeposit" />${item.dictDeposit }
								</td>
								<td>
									<input type="hidden" class="input-mini"name="paybackTransferInfo.accountBranch" />${item.accountBranch }
								</td>
								<td>
									<input type="hidden" name="paybackTransferInfo.tranDepositTime" />
									<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />
								</td>
								<td>
									<input type="hidden" name="paybackTransferInfo.applyTime" />
									<fmt:formatDate value='${item.applyTime}' type='date' pattern="HH:mm" />
								</td>
								<td>
									<input type="hidden" class="input-mini" /><fmt:formatNumber value='${item.reallyAmount }' pattern="#,##0.00"/>
								</td>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.depositName" />${item.depositName }
								</td>
								<td>
									<input type="button" class="btn_edit" name = "previewPngBtn" value="${item.uploadFilename }" docId = ${item.uploadPath }>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.uploadFilename" />&nbsp;${item.uploadName }
								</td>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.uploadDate" readOnly />
									<fmt:formatDate value='${item.uploadDate }' type="both"/>
								</td>
								<td>
								<c:choose>
									<c:when test="${item.auditStatus==2}">
									<input type="button" class="btn_edit" value="已匹配"  disabled/>
									</c:when>
									<c:otherwise>
									<input type="button" class="btn_edit" data-toggle="modal" data-target="#matchingPayMal" id="matchingPayback_${status.index}" name="matchingPayback" value="匹配"
									infoId = "${item.id }" tranDepositTime="<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />" reallyAmount="${item.reallyAmount }">
									</c:otherwise>
								</c:choose>
									<input type="button" class="btn_edit" id ="downPng" name="downPng" docId = ${item.uploadPath } fileName="${item.uploadFilename }" value="下载凭证" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="box2" id="qishu_div1">
					<tr>
						<td><label class="lab">匹配结果：</label>&nbsp; 
							<input type="radio" style="margin-top: 5px" id ="dictPayResultGo" name="dictPayResult"  value="0" required> 匹配成功 
							<input type="radio" style="margin-top: 5px" id ="dictPayResultBack" name="dictPayResult" value="1" required> 匹配退回
						</td>
					</tr>
				</div>
				
				<div id="shhy"  class="box2" style="display: none">
					<label class="lab">失败原因：</label>
					<select class="select180" id="failReason" name="failReason">
						<option>请核实实际存款人姓名</option>
						<option>请核实存入银行</option>
						<option>请核实到账时间</option>
						<option>请核实到账金额</option>
						<option>其它</option>
					</select>
					<div id="backMesDiv" style="display: none">
						<label class="lab">退回原因：</label>
						<textarea class="form-control" id="applyBackMes" name="applyBackMes" ></textarea>
					</div>
				</div>
				
				<div class="pull-right mt10 pr30" id="qishu_div4">
					<input class="btn btn-primary" type="submit" id="applyPaySaveBtn" value="确认"/>
					<input  class="btn btn-primary" type="button" id="btnCancel" value="返 回"  />
				</div>
			</c:when>
			<c:otherwise>
				<div class="box2" id="qishu_div2">
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab"></span>划扣金额：</label>
								<input type="hidden" class="input_text178"  name="applyDeductAmount" /><fmt:formatNumber value='${paybackApply.applyAmount }' pattern="#,##0.00"/>
							</td>
							<td><label class="lab">划扣平台：</label> 
								<input type="hidden" class="input_text178" name="dictDealType" />${paybackApply.dictDealTypeLabel}
							</td>
							<td><label class="lab">还款申请日期：</label> 
							<input type="hidden" class="date" [pattern="yyyy-MM-dd" ] [yearstart="-80" ] [yearend="5"] >
								<fmt:formatDate value='${paybackApply.applyPayDay}' type='date' pattern="yyyy-MM-dd" />
							</td>
						</tr>
						<tr>
							<td><label class="lab"></span>账号姓名：</label>
								<input type="hidden" class="input_text178" id="bankAccountName" name="loanBank.bankAccountName" readonly />${paybackApply.loanBank.bankAccountName }
							<td><label class="lab"></span>划扣账号：</label>
								<input type="hidden" class="input_text178" id="bankAccount" name="loanBank.bankAccount" readonly />${paybackApply.loanBank.bankAccount }
							</td>
							<td><label class="lab"></span>开户行全称：</label>
								<input type="hidden" class="input_text178" id="bankName" name="loanBank.bankName" readonly />${paybackApply.loanBank.bankNameLabel}-${paybackApply.loanBank.bankBranch}
							</td>
						</tr>
					</table>
				</div>
				<div class="pull-right mt10 pr30" id="qishu_div4">
					<input class="btn btn-primary" type="button" id="dhkbtnCancel" value="返 回" onclick="JavaScript:window.location='${ctx}/borrow/payback/deduct/list'" />
				</div>
			</c:otherwise>
		</c:choose>
	</form>

	<div class="modal fade" id="matchingPayMal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">手动匹配</h4>
				</div>
				<table class="table" id="matchingTable" data-toggle="table"  data-pagination="true">
					<thead>
						<tr>
							<th data-field="orderNumber">序号</th>
							<th data-field="outDepositTime" data-formatter="dataFormatter">存款日期</th>
							<th data-field="outReallyAmount" data-formatter="numberFormatter">实际到账金额</th>
							<th data-field="midBankName">存入银行</th>
							<th data-field="outEnterBankAccount" class="hidden">存入账号</th>
							<th data-field="outDepositName">存款人</th>
							<th data-field="outRemark">备注</th>
							<th data-radio="true">操作</th>
						</tr>
					</thead>
				</table>
			<div class="modal-footer">
				<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
				<a type="button" class="btn btn-primary" data-dismiss="modal" id="matchingSemiautomatic">提交更改</a>
			</div>
		</div>
</body>
</html>
