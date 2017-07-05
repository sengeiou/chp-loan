<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>待还款划扣已办查看页面</title>
</head>
<body>
<div class="body_r">
    <div class="box2 mb10">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">合同编号：</label>${paybackApply.contractCode}&nbsp;
				
				</td>
                <td><label class="lab">证件号码：</label>${paybackApply.customerCertNum}</td>
                <td><label class="lab">客户姓名：</label>${paybackApply.customerName}</td>
            </tr>
            <tr>
                <td><label class="lab">产品类型：</label>${paybackApply.productType}</td>
                <td><label class="lab">合同金额（元）：</label><fmt:formatNumber value='${paybackApply.contractAmount}' pattern="0.00"/></td>
                <td><label class="lab">期供金额（元）：</label><fmt:formatNumber value='${paybackApply.payback.paybackMonthAmount}' pattern="0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">借款期限：</label>${paybackApply.contractMonths}</td>
                <td><label class="lab">借款状态：</label>${paybackApply.dictLoanStatusLabel}</td>
            </tr>
        </table>
    </div>
		<h2 class="f14 pl10">还款信息&nbsp;&nbsp;&nbsp;</h2>
    <div class="box2 mb10">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td ><label class="lab">蓝补金额：</label><fmt:formatNumber value='${paybackApply.paybackBuleAmount}' pattern="0.00"/>&nbsp;</td>
            </tr>
           <tr>
		     <td>
		      <label class="lab"> 还款方式：</label><label>划扣</label>
		  </td>
		</tr>
        </table>
    </div>
  
  <div class="box2 mb10" id="qishu_div2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	    <tr >
		  <td ><label class="lab">申请划扣金额：</label><fmt:formatNumber value='${paybackApply.applyDeductAmount}' pattern="0.00"/></td>
		  <td ><label class="lab">划扣平台：</label>${paybackApply.dictDealTypeLabel}</td>
		  <td ><label class="lab">还款申请日期：</label><fmt:formatDate value="${paybackApply.applyPayDay}" type="date"/></td>
		</tr>	
        <tr>
          <td><label class="lab">账号姓名：</label>${paybackApply.applyAccountName}</td>
          <td><label class="lab">划扣账号:</label>${paybackApply.applyDeductAccount}</td>
          <td><label class="lab">开户行全称：</label>${paybackApply.applyBankNameLabel}</td>
        </tr>    
			 </table>		
  </div>

</body>
</html>