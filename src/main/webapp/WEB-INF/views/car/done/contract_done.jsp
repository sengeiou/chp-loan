<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>已制作合同</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="")">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script>
imageUrl='${imageurl}'
$(document).ready(function(){
$("#ImageData").click(function(){
	art.dialog.open(imageUrl/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111' */, {
	title: "客户影像资料",	
	top: 80,
	lock: true,
	    width: 1000,
	    height: 450,
	},false);	
	});
});

$(function(){
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
})
</script>
</head>
<body>	
 <div class="wrap2">
    	<div class="pt10 pb10 tright pr30 title">
    	<input type="hidden" value="${carLoanInfo.loanCode}" id="loanCode"/>
    	<input type="hidden" value="${carContract.contractCode }" id="contractCode"/>
		<button class="btn btn-small jkcj_contractProDoneList_formShowcontract" id="xyckBtn">协议查看</button>
		
	</div>
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
				<td><label class="lab">总费率：</label><fmt:formatNumber value="${carContract.grossRate}" pattern="0.0000" />%</td>
				<td><label class="lab">批借金额：</label><fmt:formatNumber value="${carContract.auditAmount == null ? 0 : carContract.auditAmount }" pattern="#,##0.00" />
   					<c:if test="${carContract.auditAmount != null}">元</c:if></td>
				<td><label class="lab">批借期限：</label>${carContract.contractMonths }天</td>
			</tr>
			<sys:carProductShow facilityCharge="${carLoanInfo.facilityCharge }" parkingFee="${carLoanInfo.parkingFee }" 
			flowFee="${carLoanInfo.flowFee }" dictGpsRemaining="${carLoanInfo.dictGpsRemaining }"
            dictIsGatherFlowFee="${carLoanInfo.dictIsGatherFlowFee }"  dictSettleRelend="${carLoanInfo.dictSettleRelend }" 
			productType="${carContract.productTypeName}" ></sys:carProductShow>
			<tr>
				<td><label class="lab">首期服务费率：</label><fmt:formatNumber value="${carCheckRate.firstServiceTariffingRate}" pattern="0.00" />%</td>
				<td>
					<c:if test="${carAuditResult.outVisitDistance != null && carAuditResult.outVisitDistance>0}">
						<label class="lab" >外访距离：</label><fmt:formatNumber  value="${carAuditResult.outVisitDistance}"  pattern="0.00"  />公里
					</c:if>
				</td>
				<td>
					<c:if test="${carContract.productTypeName=='GPS'}">
						<label class="lab">设备使用费：</label><fmt:formatNumber value="${carLoanInfo.deviceUsedFee == '' ? 0 : carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元
					</c:if>
				</td>
			</tr>
		</table>
		</div>
		<h2 class="f14 pl10">借款信息</h2>
		<form id="saveContractProFormId">
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td><label class="lab">合同编号：</label>${carContract.contractCode }</td>
				<td><label class="lab">车牌号码：</label>${carVehicleInfo.plateNumbers }</td>
			</tr>
			<tr>
					<td style="width: 32%"><label class="lab">利息率：</label><fmt:formatNumber value="${carCheckRate.interestRate }" pattern="0.0000" />%</td>
					<td><label class="lab">月利息：</label><fmt:formatNumber value="${carCheckRate.monthlyInterest  == null ? 0 : carCheckRate.monthlyInterest  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.monthlyInterest  != null}">元</c:if></td>
					<td></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000">*</span>中间人：</label> ${middleman }</td>
					<td><label class="lab">首期服务费：</label><fmt:formatNumber value="${carCheckRate.firstServiceTariffing  == null ? 0 : carCheckRate.firstServiceTariffing  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.firstServiceTariffing  != null}">元</c:if></td>
					<td>
						<c:if test="${carCheckRate.outVisitFee != null && carCheckRate.outVisitFee>0}">
							<label class="lab" >外访费：</label><fmt:formatNumber  value="${carCheckRate.outVisitFee}"  pattern="0.00"  />元
						</c:if>
					</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">合同金额：</label><fmt:formatNumber value="${carCheckRate.contractAmount  == null ? 0 : carCheckRate.contractAmount  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.contractAmount  != null}">元</c:if></td>
					<td><label class="lab">实放金额：</label><fmt:formatNumber value="${carCheckRate.feePaymentAmount  == null ? 0 : carCheckRate.feePaymentAmount  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.feePaymentAmount  != null}">元</c:if></td>
					<td><label class="lab">月还金额：</label><fmt:formatNumber value="${carCheckRate.monthRepayAmount  == null ? 0 : carCheckRate.monthRepayAmount  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.monthRepayAmount  != null}">元</c:if></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">综合服务费：</label><fmt:formatNumber value="${carCheckRate.comprehensiveServiceFee  == null ? 0 : carCheckRate.comprehensiveServiceFee  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.comprehensiveServiceFee  != null}">元</c:if></td>
					<td><label class="lab">审核费：</label><fmt:formatNumber value="${carCheckRate.auditFee  == null ? 0 : carCheckRate.auditFee  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.auditFee  != null}">元</c:if></td>
					<td><label class="lab">咨询费：</label><fmt:formatNumber value="${carCheckRate.consultingFee  == null ? 0 : carCheckRate.consultingFee  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.consultingFee  != null}">元</c:if></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">居间服务费：</label><fmt:formatNumber value="${carCheckRate.intermediaryServiceFee  == null ? 0 : carCheckRate.intermediaryServiceFee  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.intermediaryServiceFee != null}">元</c:if></td>
					<td><label class="lab">信息服务费：</label><fmt:formatNumber value="${carCheckRate.informationServiceCharge  == null ? 0 : carCheckRate.informationServiceCharge  }" pattern="#,##0.00" />
   					<c:if test="${carCheckRate.informationServiceCharge != null}">元</c:if></td>
					<td><label class="lab">标识：</label>${carLoanInfo.loanFlag }</td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">首期还款日：</label><fmt:formatDate value='${carContract.contractReplayDay}' pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">合同签订日期：</label><fmt:formatDate value='${carContract.contractDueDay}' pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">还款付息方式：</label>${carContract.dictRepayMethod}</td>
				</tr>
				<tr>
					<td style="width: 32%" class="hidden"><label class="lab">还款付息方式：</label>${carContract.dictRepayMethod}</td>
					<td></td>
					<td></td>
				</tr>
		</table>
		</div>
		<h2 class="f14 pl10">银行信息</h2>
		<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			<tr>
				<td style="width: 32%"><label class="lab">账户姓名：</label>${carCustomerBankInfo.bankAccountName}&nbsp;
				<span>
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
		<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="返回"
		onclick="JavaScript:history.go(-1);"></input> 
</body>
</html>
</html>