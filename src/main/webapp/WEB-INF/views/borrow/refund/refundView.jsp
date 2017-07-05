<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>催收服务费退款查看</title>

<meta name="decorator" content="default"/>
<script type="text/javascript">
function openDetails(chargeId){
	var url = ctx+'/borrow/grant/grantDeductsBack/urgeDone?backDoneId='+chargeId;
	var title  = "已收记录";
	art.dialog.open(url, {  
		   title: title,
		   lock:true,
		   width:800,
		   height:450
		},false);
}
</script>
</head>
<body>
<h2 class=" f14 pl10 ">退款</h2>
 <div class="box2 mb10">
  <form id="inputForm" >
  
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
            <tr>
                <td><label class="lab">客户姓名：</label>
               <input type="text" class="input_text178" id="customerName" name="loanCustomer.customerName" value="${payback.customername}" readonly/>
                </td>
                <td><label class="lab">证件号码：</label>
                   <input type="text" class="input_text178" id="customerCertNum" name="loanCustomer.customerCertNum" value="${payback.customercertnum}" readonly/>
                </td>
             </tr>
			  <tr>
                <td><label class="lab">合同编号：</label>
                <input type="text" class="input_text178" name="contractCode" value="${payback.contractcode}" readonly="readonly"/>
                </td>
                <td><label class="lab">借款状态：</label>
                   <input type="text" class="input_text178" id="dictLoanStatus" name="loanInfo.dictLoanStatus" value="${payback.dictloanstatus }" readonly/>
                </td>
             </tr>
			   <tr>
                <td><label class="lab">放款金额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount1" name="paybackBuleAmount1" value="<fmt:formatNumber value="${payback.grantamount }" pattern='0.00'/>" readOnly/>
                </td>
                <td><label class="lab">催收服务费：</label>
                  <input type="text" class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value="${payback.urgemoeny }" pattern='0.00'/>" readOnly/><a onclick="openDetails('${refund.chargeId}');">已收记录详情</a>
                </td>
             </tr>
       
            <tr>
                <td><label class="lab">退款金额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${refund.refundMoney }" pattern='0.00'/>" readOnly/>
                </td>
                <td><label class="lab">申请日期：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatDate value="${refund.createTimes}" type="date" />" readOnly/>
                </td>
             </tr>
			<tr>
                <td><label class="lab">收款账户：</label>
               <input type="text" class="input_text178 required"  value="${refund.incomeAccount }" readonly/> 
                </td>
                <td><label class="lab">收款户名：</label>
               <input type="text" class="input_text178 required"  value="${refund.incomeName }" readonly/>
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款银行：</label>
                <input type="text" class="input_text178 required"  value="${refund.incomeBank }" readonly/>
                </td>
                <td><label class="lab">收款支行：</label>
               <input type="text" class="input_text178 required" value="${refund.incomeBranch }" readonly/>
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款省直辖市：</label>
                <input type="text" class="input_text178 required" value="${refund.incomeCityName }" readonly/>
                </td>
                <td><label class="lab">收款市县：</label>
                <input type="text" class="input_text178 required" value="${refund.incomeCountyName }" readonly/>
                </td>
             </tr>
             <tr>
                <td cols='2'><label class="lab">退款原因：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="paybackBuleReson" readonly>${refund.remark }</textarea>
                </td>
             </tr> 
             <tr><td colspan="2"><h1></h1></td></tr>
             <tr>
                <td><label class="lab">初审结果：</label>
               <input type="radio" class="input_text18" id="fkResult" name="fkResult" value="1" disabled <c:if test="${refund.fkResult=='1' }">checked</c:if> />通过
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
               <input type="radio" class="input_text18" id="fkResult" name="fkResult" value="0" disabled <c:if test="${refund.fkResult=='0' }">checked</c:if> />退回
               &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </td>
                <td><label class="lab">终审结果：</label>
                   <input type="radio" class="input_text18" id="zcResult" name="zcResult" value="1" disabled <c:if test="${refund.zcResult=='1' }">checked</c:if> />通过
               <input type="radio" class="input_text18" id="zcResult" name="zcResult" value="0" disabled <c:if test="${refund.zcResult=='0' }">checked</c:if> />退回
                </td>
             </tr>
             <tr>
                <td><label class="lab">退款原因：</label>
               <input type="radio" class="input_text18" id="fkReason" name="fkReason" value="1" disabled <c:if test="${refund.fkReason=='1' }">checked</c:if> />系统原因
               <input type="radio" class="input_text18" id="fkReason" name="fkReason" value="2" disabled <c:if test="${refund.fkReason=='2' }">checked</c:if> />门店操作错误
               <input type="radio" class="input_text18" id="fkReason" name="fkReason" value="3" disabled <c:if test="${refund.fkReason=='3' }">checked</c:if> />客户自身原因
                </td>
                <td></td>
             </tr>
			  <tr>
                <td><label class="lab">审批意见：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="fkRemark" readonly>${refund.fkRemark }</textarea>
                </td>
                <td><label class="lab">审批意见：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="zcemark" readonly>${refund.zcRemark }</textarea>
                </td>
             </tr> 
             <tr>
                <td cols='2'><label class="lab">申请状态：</label>
            		${refund.appStatusName}<c:if test="${refund.appStatus==null }">待退款</c:if>
                </td>
               
             </tr>
        </table>
        
        <div class="tright pr30">
       
        
    
        </div>
 </form>  
</div>
</body>
</html>
