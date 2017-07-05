<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/doStoreInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/jquery-barcode-1.3.3.js"></script>
<style type="text/css">
#file {
	border-radius: 8px;
	width:70%;
	height:70%;
	margin：auto;
}
</style>
</head>
<body>
	<form id="doStoreForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/doStore/saveEelert" method="post" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${paybackApply.id }" />
		<input id ="loanBankId" name="loanBank.id" type="hidden" value="${paybackApply.loanBank.id }" />
		<input id ="loanBankNewId" name="loanBank.newId" type="hidden"  />
		<input id="loanCode" name="loanBank.loanCode" type="hidden" value="${paybackApply.loanBank.loanCode }">
		<input id="applyPaybackStatus" name="dictPaybackStatus" type="hidden">
		<div class="box1 mb10">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" id='contarctCode' name="contractCode" value="${paybackApply.contractCode }" readonly/>&nbsp;</td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" id="customerCertNum" name="loanCustomer.customerCertNum" value="${paybackApply.loanCustomer.customerCertNum }" readonly>
					</td>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" id="customerName" name="loanCustomer.customerName" value="${paybackApply.loanCustomer.customerName }" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="text" class="input_text178" id="productType" name="contract.productType" value="${paybackApply.contract.productType }" readonly></td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" id="contractAmount" name="contract.contractAmount" value="${paybackApply.contract.contractAmount }" readonly>
					</td>
					<td><label class="lab">期供金额（元）：</label> 
						<input type="text" class="input_text178" id="paybackMonthAmount" name="paybackMonthAmount" value="${paybackApply.payback.paybackMonthAmount }" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> 
						<input type="text" class="input_text178" id="contractMonths" name="contract.contractMonths" value="${paybackApply.contract.contractMonths }" readonly>
					</td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" id="dictLoanStatus" name="loanInfo.dictLoanStatus" value="${paybackApply.loanInfo.dictLoanStatusLabel}" readonly>
					</td>
				</tr>
			</table>
		</div>
		<c:choose>
			<c:when test='${paybackApply.dictPayUse!="3" }'>
		<div id="qishu_div3">
			<h1 class="f14 ml10">还款信息</h1>
			<table class="" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">蓝补金额：</label> 
						<input type="text" class="input_text178" id="paybackBuleAmount" name="paybackBuleAmount" value="${paybackApply.payback.paybackBuleAmount }" readonly></td>
				</tr>
				<tr>
					<td><label class="lab"> 还款方式：</label> 
				<input type="radio" id="paymentsManual" name="dictRepayMethod" value="1"<c:if test="${paybackApply.dictRepayMethod=='1'}"> checked</c:if> disabled/>汇款/转账
				<input type="radio" id="paymentsAuto" name="dictRepayMethod" value="0"<c:if test="${paybackApply.dictRepayMethod=='0'}"> checked</c:if> disabled/>划扣
					</td>
				</tr>
			</table>
		</div>
		<!-- POS刷卡 开始  -->
		<div id="qishu_div5" style="display: none">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></span>申请刷卡金额：</label> 
						<input type="text" class="input_text178"  type="nummber" id="deductAmount"   value="${paybackApply.deductAmountPosCard}" readonly/>
					</td>
					<td><label class="lab">POS机平台：</label> 
						<select class="select178" id="dictPosType" name="paybackApply.dictPosType" disabled>${paybackApply.dictPosType}
							<option>请选择</option>
							<c:forEach items="${fns:getDictList('jk_pos')}" var="item">
								<option value="${item.value }" <c:if test="${paybackApply.dictPosType==item.value }">selected</c:if>>${item.label }</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label>&nbsp; 
					<input type="text" class="input_text178" id="applyPayDay"name="applyPayDay"
					value="<fmt:formatDate value='${paybackApply.applyPayDayPos}' type='date' pattern="yyyy-MM-dd" />" readonly></td>
				</tr>
			    <tr>
				<td>
					<label class="lab">POS机订单号：</label> 
					<input type="text" class="input_text178" id="src" value="${paybackApply.posBillCode}" name="paybackApply.posBillCode" readonly />
			    </td>
               	</tr>
               	<tr>
               	 <td>
			      <label class="lab">条形码：</label> 
			      <div id="bcTarget" style="margin-left:auto;margin-left:100px;"></div>
			    </td>
               	</tr>
			</table>
		</div>
	
	   <!-- POS刷卡 结束-->
				<!-- POS刷卡查账 开始-->
	    <div id="qishu_div6" style="display: none">
			<table class="" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" class="input_text178" id="transferAmount" name="paybackApply.transferAmount" readonly/>
					</td>
					<td><label class="lab">存入账户：</label> 
						<select class="select78_24" id="storesInAccount" name="paybackTransferInfo.storesInAccount">
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<option value="${item.midBankName }">${item.midBankName}</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" id="applyPayDay" name="paybackApply.applyPayDay" readonly>
					</td>
				</tr>
			</table>
			<table id='appendTabPos' class="table table-bordered">
				<tr>
					<th colspan="8">
						<h1 class="f14 ml10">汇款账号&nbsp;&nbsp;&nbsp; 
							<a onclick="appendRowPos()"><img src="${context }/static/images/jiahao.png" />&nbsp;&nbsp;&nbsp;</a>
							<a onclick="deleteRowPos()"><img src="${context }/static/images/jianhao.png" /></a>
						</h1>
					</th>
				</tr>
				<tr>
					<td>存款方式</td>
					<td>存款时间</td>
					<td>实际到账金额</td>
					<td>实际存入人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
				</tr>
				<tr id='appendIdPos'>
					<td><input type="text" class="input_text178" id="dictDeposit" name="paybackTransferInfo.dictDeposit" /></td>
					<td><input class="input_text178" name="paybackTransferInfo.tranDepositTimeStrPos" type="text" /></td>
					<td><input type="text" class="input_text178" id="applyAmountCopy" name="paybackTransferInfo.reallyAmountStr" /></td>
					<td><input type="text" class="input_text178" id="depositName" name="paybackTransferInfo.depositName" /></td>
					<td><input type="text" class="input_text178" id="uploadFilename" name="paybackTransferInfo.uploadFilename" />&nbsp; 
						<a type="button" class="btn" onclick="return alert('上传影像');" />上传</a></td>
					<td><input type="text" class="input_text178" id="uploadName" name="paybackTransferInfo.uploadName" / readonly />&nbsp;</td>
					<td><input type="text" class="input_text178" id="uploadDate" name="paybackTransferInfo.uploadDate" readOnly /></td>
				</tr>
			</table>
		</div>
		<!-- POS刷卡查账 结束-->	
		<div id="qishu_div1" style="display: none">
			<table class="" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" id="transferAmount" class="input_text78" id="transferAmount" name="transferAmount" value="${paybackApply.applyAmount }" readonly></td>
					<td><label class="lab">存入账户：</label> 
						<select class="select78_24" id="storesInAccount" name="paybackTransferInfo.storesInAccount">
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<option value="${item.bankCardNo }" <c:if test="${paybackApply.paybackTransferInfo.storesInAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label>&nbsp; 
						<input type="text" class="input_text178" id="applyPayDay"name="applyPayDay"
						value="<fmt:formatDate value='${paybackApply.applyPayDay}' type='date' pattern="yyyy-MM-dd" />" readonly></td>
				</tr>
			</table>
			<table id='doStoreAppendTab' class="table table-bordered table-condensed" cellpadding=" 0" cellspacing="0" border="0" width="100%">
				<tr>
					<th colspan="8">
						<h1 class="f14 ml10">汇款账号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="appendRow()" style="background:url(${context }/static/images/jiahao.png);background-size:100%" >&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" onclick="deleteRow()" style="background:url(${context }/static/images/jianhao.png);background-size:100%">
						</h1>&nbsp;
					</th>
				</tr>
				<tr>
					<td>存款方式</td>
					<td>存款时间</td>
					<td>实际到账金额</td>
					<td>实际存入人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
				</tr>
				<c:forEach items="${paybackTransferInfoList}" var="item">
					<tr id='appendId'>
						<td><input name="paybackTransferInfo.id" value="${item.id }" type="hidden"  />
							<input type="text" class="input-mini" name="paybackTransferInfo.dictDeposit" value="${item.dictDeposit }" /></td>
						<td><input type="text" class="input-mini" name="paybackTransferInfo.tranDepositTimeStr" value="<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />"/></td>
						<td><input type="text" id="applyAmountCopy" name="paybackTransferInfo.reallyAmountStr" class="input-mini" value="${item.reallyAmount }" /></td>
						<td><input type="text" class="input-mini" name="paybackTransferInfo.depositName" value="${item.depositName }" /></td>
						<td><input name="files" type="file">
							<input type="button" class="btn_edit" name = "doPreviewPngBtn" value="${item.uploadFilename }" docId = ${item.uploadPath }></td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadFilename" />&nbsp;${item.uploadName }</td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadDate" readOnly /> <fmt:formatDate value='${item.uploadDate }' type='date' pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="box2 mb10" id="qishu_div2" style="display: none">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></span>划扣金额：</label> 
						<input type="text" class="input_text178" id="deductAmount" name="deductAmount" value="${paybackApply.applyAmount }" /></td>
					<td><label class="lab">划扣平台：</label> <select class="select178"
						id="dictDealType" name="dictDealType">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
								<option value="${item.value}">${item.label}
							</c:forEach>
					</select></td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" id="applyPayDay" name="applyPayDay" value="<fmt:formatDate value='${paybackApply.applyPayDay}' type='date' pattern="yyyy-MM-dd" />" readonly /></td>
				</tr>
				<tr>
					<td><label class="lab"></span>账号姓名：</label> 
						<input type="text" class="input_text178" id="bankAccountName" name="loanBank.bankAccountName" value="${paybackApply.loanBank.bankAccountName }" readonly />&nbsp;
						<a id="customerMal" role="button" class="btn" data-toggle="customerMal">查询</a>
					<td><label class="lab"></span>划扣账号:</label> <input type="text" class="input_text178" id="bankAccount" name="loanBank.bankAccount" value="${paybackApply.loanBank.bankAccount }" readonly /></td>
					<td><label class="lab"></span>开户行全称：</label> 
						<input type="text" class="input_text178" id="bankName" name="loanBank.bankName" value="${paybackApply.loanBank.bankName }" readonly /></td>
				</tr>
			</table>
		</div>
			</c:when>
			<c:otherwise>
				<div class="control-group">
					<table class="" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="lab">未还违约金及罚息总金额:</label>&nbsp;<input
								type="text" class="input_text178" name="paybackCharge.penaltyTotalAmount" value="${paybackCharge.penaltyTotalAmount }"/>
							</td>
							<td><label class="lab">提前结清金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackMonth.monthBeforeFinishAmount" value="${paybackApply.paybackMonth.monthBeforeFinishAmount }"/>
							</td>
							<td><label class="lab">提前结清应还总金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackCharge.settleTotalAmount" value="${paybackCharge.settleTotalAmount}"/>
							</td>
						</tr>
						<tr>
							<td><label class="lab">蓝补金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackBuleAmount" value="${paybackApply.payback.paybackBuleAmount }"/>
							</td>
							<td><label class="lab">提前结清申请资料:</label>&nbsp; <input
								type="text" class="input_text178" name="" value=""/>
								<a id ="" class="btn" >上传</a> 
							</td>
							<td><label class="lab">减免金额:</label>&nbsp; <input
								type="text" class="input_text178" name="paybackMonth.monthPunishReduction" value="${paybackApply.paybackMonth.monthPunishReduction }"/>
							</td>
						</tr>
					</table>
				</div>
			</c:otherwise>
		</c:choose>
		<div class="tcenter mt20" style="float:right">
		<c:if test="${paybackApply.dictRepayMethod!='2'}"> 
			<input class="btn btn-primary" class="submit" type="submit" id="applyPayLaunchBtn" value="提交">
		</c:if>	
			<input class="btn btn-primary" type="button"  id="giveUpDoStoreBtn" value="放弃">
			<input class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回">
		</div>
	</form>
	<div id="customerModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-body">
			<table class="table table-striped" id="customerTab">
				<thead>
					<tr>
						<th data-field="bankAccountName">账号姓名</th>
						<th data-field="bankAccount">划扣账号</th>
						<th data-field="bankAccountOpen">开户行全称</th>
						<th data-radio="true">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true" id="selectBankAccountBtn">选中</button>
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		</div>
	</div>
</body>
</html>
