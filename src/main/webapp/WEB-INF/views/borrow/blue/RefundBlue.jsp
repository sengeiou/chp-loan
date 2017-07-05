<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>


<script type="text/javascript" src="${context }/js/payback/paybackBlueRefund.js"></script>
<meta name="decorator" content="default"/>

</head>
<body>
  <h2 class=" f14 pl10 ">退款</h2>
 <div class="box2 mb10">
  <form id="inputForm" >
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
            <tr>
                <td><label class="lab">客户姓名：</label>
               <input type="text" class="input_text178" id="customerName" name="loanCustomer.customerName" value="${payback.loanCustomer.customerName}" readonly/>
                </td>
                <td><label class="lab">证件号码：</label>
                   <input type="text" class="input_text178" id="customerCertNum" name="loanCustomer.customerCertNum" value="${payback.loanCustomer.customerCertNum}" readonly/>
                </td>
             </tr>
			  <tr>
                <td><label class="lab">合同编号：</label>
                <input type="text" class="input_text178" name="contractCode" value="${payback.contractCode}" readonly="readonly"/>
                </td>
                <td><label class="lab">借款状态：</label>
                   <input type="text" class="input_text178" id="dictLoanStatus" name="loanInfo.dictLoanStatus" value="${payback.loanInfo.dictLoanStatus }" readonly/>
                </td>
             </tr>
			   <tr>
                <td><label class="lab">蓝补余额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount1" name="paybackBuleAmount1" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" readOnly/>
                </td>
                <td><label class="lab">期供余额：</label>
                  <input type="text" class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value="${payback.paybackMonthAmount }" pattern='0.00'/>" readOnly/>
                </td>
             </tr>
       
            <tr>
                <td><label class="lab">退款金额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" value="<fmt:formatNumber value="${payback.paybackBuleAmount }" pattern='0.00'/>" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>
                </td>
                <td>
                  
                </td>
             </tr>
			   <tr>
                <td cols='2'><label class="lab">退款后蓝补余额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmountLast" name="paybackBuleAmountLast" value="<fmt:formatNumber value="0" pattern='0.00'/>" readonly/><%-- ${payback.paybackBuleAmount } --%>
                </td>
             </tr>
			 <tr>
                <td cols='2'><label class="lab">退款原因：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="paybackBuleReson"></textarea>
                </td>
             </tr>  
        </table>
        
        <div class="tright pr30">
       <input type="submit" id="submitBtn" value="提交"  class="btn btn-primary"/> 
        
    
        </div>
 </form>  
</div>
</body>
</html>
