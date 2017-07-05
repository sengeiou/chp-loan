<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/electApplyPayback.js"></script>
<script type="text/javascript" src="${context }/js/payback/jquery-barcode-1.3.3.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" ></script>
</head>
<body>
	<p class="tright pt5 mb5 pr10"><span id="applyPaybackMonthBth" class="btn btn-small " >还款明细</span></p>
	<form id="applyPayLaunchForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/applyPayback/saveApplyEletr" method="post">
		<input type="hidden" id ="id" name="id" value="${payback.id }" />
		<input type="hidden" id ="loanBankId" name="loanBank.id" value="${payback.loanBank.id }" />
		<input type="hidden" id="paybackMonthId" value="${payback.paybackMonth.id}" />
		<input type="hidden" id ="loanBankNewId" name="loanBank.newId"/>
		<input type="hidden" id="loanCode" name="loanBank.loanCode" />
		<input type="hidden" id="paybackDay"  name="paybackDay" />
		<input type="hidden" id="storesInAccountname" name="paybackTransferInfo.storesInAccountname" />
 		<input type="hidden" id="confrimFlag" name="confrimFlag"  value="0" ><!-- 保存汇款提交重复标识  -->
		<sys:message content="${message}"/>
		<h2 class="f14 pl10">基本信息&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">合同编号111：</label> 
						<input type="text" class="input_text178" id='contarctCode' name="contractCode"/>&nbsp;
						<input type="button" class="btn btn-primary" id="searchApplyPayBtn" value="查询">
					</td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" id="customerCertNum" name="loanCustomer.customerCertNum" readonly>
					</td>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" id="customerName" name="loanCustomer.customerName" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="hidden" class="input_text178" id="productType" name="contract.productType">
						<input type="text" class="input_text178" id="productName" name="jkProducts.productName" readonly>
					</td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" id="contractAmount" name="contract.contractAmount" readonly>
					</td>
					<td><label class="lab">期供金额（元）：</label> 
						<input type="text" class="input_text178" id="paybackMonthAmount" name="paybackMonthAmount" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> 
						<input type="text" class="input_text178" id="contractMonths" name="contract.contractMonths" readonly>
					</td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" id="dictLoanStatus" name="loanInfo.dictLoanStatus" readonly>
					</td>
				</tr>
			</table>
		</div>
		<div id="qishu_div3" class="box2 mb10" style="border-top:0">
		<h2 class="f14 pl10 mt20">还款信息</h2>
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">蓝补金额：</label> 
						<input type="text" class="input_text178" id="paybackBuleAmount" name="paybackBuleAmount" readonly>
					</td>
					<td><label class="lab">委托充值状态：</label> 
						<input type="text" class="input_text178" id="trustRecharge" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab"> 还款方式：</label> 
						<input type="radio" style="margin-top:5px" id="paymentsManual" name="paybackApply.dictRepayMethod" value="1">汇款/转账
						<input type="radio" style="margin-top:5px" id="paymentsAuto" name="paybackApply.dictRepayMethod" value="0">划扣
						<input type="hidden" style="margin-top:5px" id="paymentsPosCard" name="" value="2">
						<input type="hidden" style="margin-top:5px" id="paymentsPosAudit" name="" value="3">
					</td>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
		
		<div id="qishu_div1" class="box2 mb10" style="display: none;border-top:0;border-bottom:0">
		<h2 class="f14 pl10 mt20">汇款账户&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" class="input_text178" id="transferAmount" name="paybackApply.transferAmount" readonly/>
					</td>
					<td><label class="lab">存入账户：</label> 
						<select class="select78_24" id="storesInAccount" name="paybackTransferInfo.storesInAccount">
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<option value="${item.bankCardNo }">${item.midBankName}</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" id="applyPayDay" name="paybackApply.applyPayDay" readonly>
					</td>
				</tr>
			</table>
			<table id='appendTab' class="table table-bordered table-condensed">
				<tr>
					<th colspan="8">
						<h1 class="f14 ml10">汇款账号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="appendRow()" style="background-size:100% ;height:14px;width:14px" ><img  src="${context }/static/images/jiahao.png"></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="deleteRow()" style="background-size:100%;height:14px;width:14px"><img alt="" src="${context }/static/images/jianhao.png"></a>
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
				<tr id='appendId'>
					<td><input type="text" class="input_text178" id="dictDeposit" name="paybackTransferInfo.dictDeposit"  maxlength="14"   onblur="checkDictDeposit(this)" /></td>
					<td><input type="text" id="tranDepositTimeStr" name="paybackTransferInfo.tranDepositTimeStr" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${paybackApply.applyPayDay}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" onblur="checkTimeStr(this)" /></td>
					<td><input type="text" class="input_text178" id="applyAmountCopy" name="paybackTransferInfo.reallyAmountStr"  maxlength="8"  onblur="checkCardStr(this)" /></td>
					<td><input type="text" class="input_text178" id="depositName" name="paybackTransferInfo.depositName" maxlength="8"  onblur="checkDictDepositName(this)" /></td>
					<td><input name="files" id="filesssss" type="file"></td>
					<td><input type="text" class="input_text178" id="uploadName" name="paybackTransferInfo.uploadName"  readonly />&nbsp;</td>
					<td><input type="text" class="input_text178" id="uploadDate" name="paybackTransferInfo.uploadDate" readOnly /></td>
				</tr>
			</table>
		</div>
		
		<div id="qishu_div2"  class="box2 mb10" style="border-top:0">
		<h2 class="f14 pl10 mt20">划扣信息&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></span>划扣金额：</label> 
						<input type="text" class="input_text178" type="nummber" id="deductAmount" name="paybackApply.deductAmount"/>
					</td>
					<td><label class="lab">划扣平台：</label> 
						<select class="select178" id="dictDealType" name="paybackApply.dictDealType" required>
							<option>请选择</option>
							<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
								<option value="${item.value}">${item.label}
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" id="applyPayDay" name="paybackApply.applyPayDay" readonly />
					</td>
				</tr>
				<tr>
					<td><label class="lab"></span>账号姓名：</label> 
						<input type="text" class="input_text178" id="bankAccountName" name="paybackApply.applyAccountName" readonly /> 
						<a type="button" class="btn btn-primary" id="customerBtn">查询</a>
					</td>
					<td><label class="lab"></span>划扣账号：</label> 
						<input type="text" class="input_text178" id="bankAccount" name="paybackApply.applyDeductAccount" readonly />
					</td>
					<td><label class="lab"></span>开户行全称：</label> 
						<input type="text" class="input_text178" id="bankName" readonly />
						<input type="hidden" class="input_text178" id="bankCode" name="paybackApply.applyBankName" readonly />
					</td>
				</tr>
			</table>
		</div>
		
		<!-- POS刷卡 开始  -->
		<div class="box2 mb10" id="qishu_div5" style="display: none">
			<table class="f14 table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></span>申请刷卡金额：</label> 
						<input type="text" class="input_text178"  type="nummber" id="deductAmountPosCard" name="paybackApply.deductAmountPosCard" required/>
					</td>
					<td><label class="lab">POS机平台：</label> 
						<select class="select178" id="dictPosType" name="paybackApply.dictPosType" required>
							<option>请选择</option>
							<c:forEach items="${fns:getDictList('jk_pos')}" var="item">
								<option value="${item.value}">${item.label}
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" id="applyPayDay" name="paybackApply.applyPayDayPos" readonly />
					</td>
				</tr>
			</table>
		</div>
		<!-- POS刷卡 结束-->
			
		<!-- POS刷卡查账 开始-->
		<div class="box2 mb10" id="qishu_div6" style="display: none">
			<table class="" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" class="input_text178" id="transferAmountPosCard" name="paybackApply.transferAmountPosCard" readonly/>
					</td>
					<td><label class="lab">存入账户：</label> 
						<select class="select78_24" id="posAccount" name="paybackApply.posAccount">
							<option>请选择</option>
							<c:forEach items="${fns:getDictList('jk_account')}" var="item">
								<option value="${item.value}">${item.label}
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" id="applyPayDay" name="paybackApply.applyPayDayPosCard" readonly>
					</td>
				</tr>
			</table>
			<table id='appendTabPos' class="table table-bordered">
				<tr>
					<th colspan="8">
						<h1 class="f14 ml10">POS机刷卡查账账号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="append()" style="background-size:100% ;height:14px;width:14px" ><img  src="${context }/static/images/jiahao.png"></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="deleteR()" style="background-size:100%;height:14px;width:14px"><img alt="" src="${context }/static/images/jianhao.png"></a>
						</h1>
					</th>
				</tr>
				<tr>
					<td>存款方式</td>
					<td>到账日期</td>
					<td>实际到账金额</td>
					<td>参考号</td>
					<td>订单号</td>
					<td>POS小票凭证</td>
					<td>上传人</td>
					<td>上传时间</td>
				</tr>
				<tr id='appendIdPos'>
					<td>
						<select class="select78_24" id="posAccount" name="posCardInfo.dictDepositPosCard"  >
							<option value ='0'>请选择</option>
							<c:forEach items="${fns:getDictList('jk_account')}" var="item">
								<option value="${item.value}">${item.label}
							</c:forEach>
						</select>
					</td>
					<td><input type="text" id="checkDateCard"  name="posCardInfo.paybackDateStr" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${posCardInfo.paybackDateStr}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"    onblur="checkDateStr(this)"  readOnly  /></td>
					<td><input type="text" class="input_text178" id="checkApplyAmountCopyPosCard" name="posCardInfo.applyReallyAmountPosCard"  maxlength="8" onblur="checkCardStr(this)" /></td>
					<td><input type="text" class="input_text178" id="depositNamereferCode" name="posCardInfo.referCode"  maxlength="14"   onblur="checkReferCode(this)" /></td> <!-- insertAfter -->
					<td><input type="text" class="input_text178" id="depositNameOrderNumber" name="posCardInfo.posOrderNumber"  maxlength="24"   onblur="checkNumber(this)"  /></td>
					<td><input id="filess"   name="files" type="file"></td>
					<td><input type="text" class="input_text178" id="uploadNamePosCard" name="posCardInfo.uploadNamePosCard" / readonly />&nbsp;</td>
					<td><input type="text" class="input_text178" id="uploadDatePosCard" name="posCardInfo.uploadDatePosCard" readOnly /></td>
				</tr>
			</table>
		</div>
		
		<!-- POS刷卡查账 结束-->
		
		<div id="qishu_div4" class="pull-right mt10 pr30">
			<input type="button" class="btn btn-primary" id="applyPayLaunchBtn" value="确认" />
		</div>
		
	</form>
	
	<!--更改划扣账户   -->
	<div class="modal fade" id="customerMal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">更改划扣账户</h4>
				</div>
				<table class="table" id="customerTab">
					<thead>
						<tr>
							<th data-field="bankAccountName">账号姓名</th>
							<th data-field="bankAccount">划扣账号</th>
							<th data-field="bankName">开户行全称</th>
							<th data-radio="true">操作</th>
						</tr>
					</thead>
				</table>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal" id="selectBankAccountBtn">提交更改</a>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>
