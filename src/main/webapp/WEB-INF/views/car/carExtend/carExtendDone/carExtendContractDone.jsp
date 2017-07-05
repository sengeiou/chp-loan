<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借合同已办查看</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script>
$(document).ready(function(){
	  $("#xyckBtn").click(function(){
		  var loanCode=$('#loanCode').val();
		  var contractCode=$('#contractCode').val();
		  var url='${ctx}/car/carContract/checkRate/xieyiList?loanCode='+loanCode+'&contractCode='+contractCode;
		  art.dialog.open(url, {  
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
		});
});
</script>
</head>
<body>	
 <div class="wrap2">
    
	<div class="pt10 pb10 tright pr30 title">
	    <input type="hidden" value="${carLoanInfo.loanCode}" id="loanCode"/>
    	<input type="hidden" value="${carContract.contractCode }" id="contractCode"/>
    	<button class="btn btn-small jkcj_carExtendcontractProDoneList_downloan" id="xyckBtn" >协议查看下载</button>&nbsp;
    	<input type="button" class="btn btn-small" value="返回" onclick="JavaScript:history.go(-1);"></input> 
	</div>
 <h2 class="f14 pl10">历史展期信息</h2>
		<div class="box2">
         <table class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<th>合同编号</th>
					<th>合同金额（元）</th>
					<th>借款期限（天）</th>
					<th>合同期限</th>
					<th>降额（元）</th>
				</tr>
				<c:if test="${carContracts!=null && fn:length(carContracts)>0}">
				<c:forEach items="${carContracts}" var="carContracts">
					<tr>
						<td>${carContracts.contractCode}</td>
						<td><fmt:formatNumber value="${carContracts.contractAmount}" pattern="0.00" />元</td>
						<td>${carContracts.contractMonths}天</td>
						<td><fmt:formatDate value='${carContracts.contractFactDay}' pattern="yyyy-MM-dd"/>~<fmt:formatDate value='${carContracts.contractEndDay}' pattern="yyyy-MM-dd"/></td>
							<td>
								 <c:choose>
									<c:when test="${carContracts.derate == null}">
										0.00
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${carContracts.derate}"
											pattern="0.00" />
									</c:otherwise>
								</c:choose>元
							</td>
						</tr>
				</c:forEach>
				</c:if>
			</table>
           <h2 class="f14 pl10">客户信息</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td style="width: 32%"><label class="lab">门店名称：</label>${carLoanInfo.storeName}</td>
			</tr>
			<tr>
				<td style="width: 32%"><label class="lab">客户姓名：</label>${carCustomer.customerName}</td>
				<td><label class="lab">身份证号：</label>${carCustomer.customerCertNum}</td>
				<td style="width: 32%"><label class="lab">客户邮箱：</label>${carCustomer.customerEmail}</td>
			</tr>
			<c:forEach items="${carLoanCoborrowers }" var="cobos">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
					<td><label class="lab">共借人身份证号：</label>${cobos.certNum}</td>
					<td><label class="lab">共借人邮箱：</label>${cobos.email}</td>
				</tr>
			</c:forEach>
		</table>
		</div>
		<h2 class="f14 pl10">汇诚审批结果</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td><label class="lab">批借产品：</label>${carContract.productTypeName}</td>
				<td><label class="lab">批借金额：</label><fmt:formatNumber value="${carContract.auditAmount == null ? 0 : carContract.auditAmount }" pattern="#,##0.00" />
   					<c:if test="${carContract.auditAmount != null}">元</c:if></td>
				<td><label class="lab">批借期限：</label>${carContract.contractMonths }天</td>
			</tr>
			<tr>
				<td><label class="lab">总费率：</label><fmt:formatNumber value="${carContract.grossRate == null ? 0 : carContract.grossRate}" pattern="#,##0.000" />%</td>
				<!-- 
				首期服务费率：</label><fmt:formatNumber value="${carAuditResult.firstServiceTariffing}" pattern="0.00" />% --></td>
			</tr>
			<%-- <tr>
				<td id="flowFee">
				<c:if test="${carAuditResult.productTypeName eq 'GPS'}">
					<fmt:formatNumber value="${carLoanInfo.flowFee  == null ? 0 : carLoanInfo.flowFee  }" pattern="#,##0.00" />
   					<c:if test="${carLoanInfo.flowFee  != null}">元 </c:if></c:if></td>
				<td></td>
			</tr> --%>
		</table>
		</div>
		<h2 class="f14 pl10">借款信息</h2>
		<form id="saveContractProFormId">
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td><label class="lab">展期合同编号：</label>${carContract.contractCode }</td>
			</tr>
			<tr>
				<td><label class="lab">合同金额：</label><fmt:formatNumber value="${carContract.contractAmount  == null ? 0 : carContract.contractAmount }" pattern="#,##0.00" />元</td>
                <td><label class="lab">降额：</label><fmt:formatNumber value="${carContract.derate  == null ? 0 : carContract.derate }" pattern="#,##0.00" />元</td>
               <td><label class="lab">总费率：</label><fmt:formatNumber value="${carContract.grossRate == null ? 0 : carContract.grossRate}" pattern="#,##0.0000" />%</td>
            </tr>
			<tr>
				 <td><label class="lab">利息率：</label><fmt:formatNumber value="${carCheckRate.interestRate  == null ? 0 : carCheckRate.interestRate }" pattern="#,##0.0000" />%</td>
                <td><label class="lab">月利息：</label><fmt:formatNumber value="${carCheckRate.monthlyInterest == null ? 0 : carCheckRate.monthlyInterest}" pattern="#,##0.00" />元</td>
                <td><label class="lab">中间人：</label>${middleman }</td>
            </tr>
            <tr>
				<td><label class="lab">展期综合服务费：</label><fmt:formatNumber value="${carCheckRate.comprehensiveServiceFee == null ? 0 : carCheckRate.comprehensiveServiceFee}" pattern="#,##0.00" />元</td>
                <td><label class="lab">审核费：</label><fmt:formatNumber value="${carCheckRate.auditFee == null ? 0 : carCheckRate.auditFee}" pattern="#,##0.00" />元</td>
                <td><label class="lab">咨询费：</label><fmt:formatNumber value="${carCheckRate.consultingFee  == null ? 0 : carCheckRate.consultingFee }" pattern="#,##0.00" />元</td>
            </tr>
            <tr>
				<td><label class="lab">居间服务费：</label><fmt:formatNumber value="${carCheckRate.intermediaryServiceFee == null ? 0 : carCheckRate.intermediaryServiceFee}" pattern="#,##0.00" />元</td>
                <td><label class="lab">信息服务费：</label><fmt:formatNumber value="${carCheckRate.informationServiceCharge  == null ? 0 : carCheckRate.informationServiceCharge }" pattern="#,##0.00" />元</td>
                <td><label class="lab">展期费用：</label><fmt:formatNumber value="${carContract.extensionFee== null ? 0 : carContract.extensionFee}" pattern="#,##0.00" />元</td>
            </tr>
            <tr>
            	<sys:carExtendProductShow facilityCharge="${carLoanInfo.facilityCharge }" parkingFee="${carLoanInfo.parkingFee }" 
					flowFee="${carLoanInfo.flowFee }" dictGpsRemaining="${carLoanInfo.dictGpsRemaining }"
            		dictIsGatherFlowFee="${carLoanInfo.dictIsGatherFlowFee }"  dictSettleRelend="${carLoanInfo.dictSettleRelend }" 
					productType="${carContract.productType}"></sys:carExtendProductShow>
                <td><label class="lab">合同签订日期：</label><fmt:formatDate value='${carContract.contractFactDay }' type='date' pattern="yyyy-MM-dd" /></td>
                <td><label class="lab">展期应还总金额：</label><fmt:formatNumber value="${carCheckRate.extendPayAmount}" pattern="0.00" />元</td>
            </tr>
           
            <c:if test="${carLoanInfo.dictProductType=='CJ01'&&carLoanInfo.deviceUsedFee!= null}">
		    <tr>
		    	<td>
		    		<label class="lab">设备使用费：</label><fmt:formatNumber value="${carLoanInfo.deviceUsedFee == null ? 0 : carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元
		    	</td>
		    </tr>
			</c:if>
		</table>
		</div>
		<h2 class="f14 pl10">银行信息</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td style="width: 32%"><label class="lab">账户姓名：</label>${carCustomerBankInfo.bankAccountName}&nbsp;
				 <span id="rareword">
				<input type="checkbox" id="isRareword" <c:if test="${carCustomerBankInfo.israre == '1'}">checked='checked'</c:if> disabled="disabled" value="${carCustomerBankInfo.israre}" /><lable>是否生僻字</lable>
				</span> 
				</td>
				<td><label class="lab">开户行：</label>${carCustomerBankInfo.cardBank}</td>
				<td><label class="lab">开卡行支行名称：</label>${carCustomerBankInfo.applyBankName}</td>
			</tr>
			<tr>
				<td><label class="lab">银行账号：</label>${carCustomerBankInfo.bankCardNo}</td>
				<td></td>
				<td></td>
			</tr>
		</table>
		</div>
  </div>
  <div class="tright pr10 pt5 pb5 mb10" >
	<input type="button" onclick="history.go(-1);" class="btn btn-primary" extendContractDone id="button" value="返回" />
</body>
</html>