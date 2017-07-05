<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/doStoreInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/jquery-barcode-1.3.3.js"></script>
</head>
<body>
	<form id="doStoreForm" enctype="multipart/form-data" action="${ctx}/borrow/payback/doStore/save" method="post" class="form-horizontal">
		<input id="id" name="id" type="hidden" value="${paybackApply.id }" />
		<input id ="loanBankId" name="loanBank.id" type="hidden" value="${paybackApply.loanBank.id }" />
		<input id ="loanBankNewId" name="loanBank.newId" type="hidden"  />
		<input type="hidden" id="paybackMonthId" value="${paybackApply.paybackMonth.id}" />
		<input id="loanCode" name="loanBank.loanCode" type="hidden" value="${paybackApply.loanBank.loanCode }">
		<input id="applyPaybackStatus" name="dictPaybackStatus" type="hidden">
		<h2 class="f14 pl10">基本信息</h2>
		<div class="box2">
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
				   <input type="radio" id="paymentsPosCard" name="dictRepayMethod" value="2"<c:if test="${paybackApply.dictRepayMethod=='2'}"> checked</c:if> disabled/>POS机刷卡

					</td>
				</tr>
			</table>
		</div>
		
		<!-- POS刷卡 开始  -->
		<div class="box2 mt20" id="qishu_div5" style="display: none">
			<table class="f14 table1" cellpadding="0" cellspacing="0" border="0" width="100%">
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
			</c:when>
		</c:choose>
		<div class="tright pt10 pr30" >
			<input class="btn btn-primary" type="button" onclick="history.go(-1)" value="返回">
		</div>
	</form>
</body>
</html>
