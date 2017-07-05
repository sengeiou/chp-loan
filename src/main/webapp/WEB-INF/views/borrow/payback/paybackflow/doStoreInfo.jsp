<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/doStoreInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/jquery-barcode-1.3.3.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" ></script>
<script type="text/javascript" src="${context }/static/jQueryRotate.js"></script>
</head>
<body>
	<form id="doStoreForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/doStore/save" method="post" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${paybackApply.id }" /> <!--还款申请表ID   -->
		<input id="pId" name="payback.id" type="hidden" value="${paybackApply.payback.id }" /> <!--还款主表ID   -->
		<input id ="loanBankId" name="loanBank.id" type="hidden" value="${paybackApply.loanBank.id }" /> <!--还款借款账户信息表ID   -->
		<input id ="loanBankNewId" name="loanBank.newId" type="hidden" value="${paybackApply.loanBank.id }" /> <!--还款借款账户信息表 临时ID作为更新使用   -->
		<input type="hidden" id="paybackMonthId" value="${paybackApply.paybackMonth.id}" /> <!--期供表ID   -->
		<input id="loanCode" name="loanBank.loanCode" type="hidden" value="${paybackApply.loanBank.loanCode }"> <!--借款编号   -->
		<input id="applyPaybackStatus" name="dictPaybackStatus" type="hidden"> <!--还款申请状态  -->
		<input type="hidden" id="confrimFlag" name="confrimFlag" value="0">
		<input id = "doStoreTokenId" name="doStoreTokenId" type="hidden" value="${paybackApply.doStoreTokenId}" />
		<input id = "doStoreToken" name="doStoreToken" type="hidden" value="${paybackApply.doStoreToken}" />
		<h2 class="f14 pl10">基本信息</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
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
						<input type="text" class="input_text178" name="jkProducts.productName" value="${paybackApply.jkProducts.productName }" readonly></td>
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
		<h2 class="f14 pl10 mt20">还款信息</h2>
		<div class="box2" id="qishu_div3">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">蓝补金额：</label> 
						<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackApply.payback.paybackBuleAmount }' pattern="#,##0.00"/>" readonly />
						<input type="hidden" id="paybackBuleAmount" name="payback.paybackBuleAmount"  value="<fmt:formatNumber value='${paybackApply.payback.paybackBuleAmount }' pattern="0.00"/>"/>
				</tr>
				<tr>
					<td><label class="lab"> 还款方式：</label> 
						<input type="radio" style="margin-top:5px" id="paymentsManual" name="dictRepayMethod" value="1"<c:if test="${paybackApply.dictRepayMethod=='1'}"> checked</c:if>/>汇款/转账
						<input type="radio" style="margin-top:5px" id="paymentsAuto" name="dictRepayMethod" value="0"<c:if test="${paybackApply.dictRepayMethod=='0'}"> checked</c:if>/>划扣
		
						<input type="radio" style="margin-top:5px" id="paymentsPosCard" name="dictRepayMethod" value="2"<c:if test="${paybackApply.dictRepayMethod=='2'}"> checked</c:if> disabled/>POS机刷卡
						<input type="radio" style="margin-top:5px" id="paymentsPosAudit" name="dictRepayMethod" value="3"<c:if test="${paybackApply.dictRepayMethod=='3'}"> checked</c:if> disabled/>POS机刷卡查账
					</td>
				</tr>
				<tr>
					<td> <label class="lab"> 中和公司查账：</label> 
						<input type="checkbox" style="margin-top:5px" id="operateRole" name="operateRole" value="1" 
							<c:if test="${paybackApply.operateRole==1 }">checked</c:if>
						/>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- POS刷卡 开始  -->
		<div class="box2" id="qishu_div5" style="display: none">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></span>申请刷卡金额：</label> 
						<input type="text" class="input_text178"  type="nummber" id="deductAmount" value="<fmt:formatNumber value='${paybackApply.deductAmountPosCard }' pattern="#,##0.00"/>"  readonly/>
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
		<div class = "box2" id="qishu_div1" style="border-bottom:0">
		<h2 class="f14 pl10 mt20">账户信息&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" class="input_text178" id="transferAmount" name="transferAmount" value="<fmt:formatNumber value='${paybackApply.applyAmount }' pattern="0.00"/>" readonly/></td>
					<td><label class="lab">存入账户：</label> 
						<input id="storesInAccountname" name="paybackTransferInfo.storesInAccountname" type="hidden">
						<select class="select180" id="storesInAccount" name="paybackTransferInfo.storesInAccount">
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<c:if test="${(paybackApply.operateRole ==null || paybackApply.operateRole !=1) 
									&& item.midBankName!='中和-中国工商银行' && item.midBankName!='中和-招商银行'
								}">
								<option value="${item.bankCardNo }" <c:if test="${paybackApply.dictDepositAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
								</c:if>
								<c:if test="${paybackApply.operateRole==1 }">
									<option value="${item.bankCardNo }" <c:if test="${paybackApply.dictDepositAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
								</c:if>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label>&nbsp; 
						<input type="text" class="input_text178" id="applyPayDay"name="applyPayDay"
						value="<fmt:formatDate value='${paybackApply.applyPayDay}' type='date' pattern="yyyy-MM-dd" />" readonly></td>
				</tr>
			</table>
		</div>
		
		<!--汇款   -->
		<div id="qishu_div7">
			<table id='doStoreAppendTab' class="table table-bordered table-condensed" cellpadding=" 0" cellspacing="0" border="0" width="100%">
				<tr>
					<th colspan="8">
						<h1 class="f14 ml10">汇款账号&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a onclick="appendRow()" style="background-size:100%;width:14px;height:14px" ><img src="${context }/static/images/jiahao.png"/></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a onclick="deleteRow()" style="background-size:100%;width:14px;height:14px"><img src="${context }/static/images/jianhao.png"/></a>
						</h1>&nbsp;
					</th>
				</tr>
				<tr>
					<td>存款方式</td>
					<td>存款时间</td>
					<td>实际到账金额</td>
					<td>实际存款人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
				</tr>
				<c:forEach items="${paybackTransferInfoList}" var="item">
					<tr id='appendId'>
						<td><input name="paybackTransferInfo.id" value="${item.id }" type="hidden"  />
							<input type="hidden" name="paybackTransferInfo.uploadPath" value="${item.uploadPath }">
							<input type="hidden" name="paybackTransferInfo.uploadFilename" value="${item.uploadFilename }">
							<input type="text" class="input-mini" id="dictDeposit" name="paybackTransferInfo.dictDeposit" value="${item.dictDeposit }" /></td>
						<td><input type="text" name="paybackTransferInfo.tranDepositTimeStr" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${item.tranDepositTime}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%yyyy-%MM-%dd'});"/></td>
						<td><input type="text" id="applyAmountCopy" class="input-mini" name="paybackTransferInfo.reallyAmountStr" value="<fmt:formatNumber value='${item.reallyAmount }' pattern="0.00"/>" /></td>
						<td><input type="text" class="input-mini" id="depositName"" name="paybackTransferInfo.depositName" value="${item.depositName }" /></td>
						<td><input name="files" type="file">
							<c:if test="${paybackApply.dictSourceType eq 3}">
								<input type="hidden" name="dictSourceType" value="${paybackApply.dictSourceType }"></input>
								<input type="button" class="btn_edit" name = "doPreviewPngBtn" value="${item.uploadFilename }" docId = ${item.uploadPath }>
							</c:if>
						</td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadName" />&nbsp;${item.uploadName }</td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadDate" readOnly /> <fmt:formatDate value='${item.uploadDate }' type='date' pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		
		<!--划扣   -->
		<div class="box2" id="qishu_div2" style="display: none">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></span>划扣金额：</label> 
						<input type="text" class="input_text178" id="deductAmountPayback" name="deductAmount" value="${paybackApply.applyAmount }" /></td>
					<td><label class="lab">划扣平台：</label> 
						<select class="select178" id="dictDealType" name="dictDealType">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="dictDealType">
									<option value="${dictDealType.value }" <c:if test="${paybackApply.dictDealType == dictDealType.value }">selected</c:if>>${dictDealType.label }</option>
								</c:forEach>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" id="applyPayDay" name="applyPayDay" value="<fmt:formatDate value='${paybackApply.applyPayDay}' type='date' pattern="yyyy-MM-dd" />" readonly /></td>
				</tr>
				<tr>
					<td><label class="lab"></span>账号姓名：</label> 
						<input type="text" class="input_text178" id="bankAccountName" name="loanBank.bankAccountName" value="${paybackApply.loanBank.bankAccountName }" readonly />&nbsp;
						<a id="customerMal" role="button" class="btn" data-toggle="customerMal">查询</a>
					<td><label class="lab"></span>划扣账号：</label> <input type="text" class="input_text178" id="bankAccount" name="loanBank.bankAccount" value="${paybackApply.loanBank.bankAccount }" readonly /></td>
					<td><label class="lab"></span>开户行全称：</label> 
						<input type="text" class="input_text178" id="bankName" name="loanBank.bankName" value="${paybackApply.loanBank.bankNameLabel }" readonly /></td>
						
				</tr>
			</table>
		</div>
			</c:when>
		</c:choose>
		<div class="tcenter mt20" style="float:right">
		<c:if test="${paybackApply.dictRepayMethod!='2'}"> 
			<input class="btn btn-primary" type="button" id="applyPayLaunchBtn" value="提交">
		</c:if>	
			<input class="btn btn-primary" type="button" id="giveUpDoStoreBtn" value="放弃">
			<input class="btn btn-primary" type="button" onclick="JavaScript:window.location='${ctx}/borrow/payback/doStore/list'" value="返回">
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
