<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>蓝补退款</title>

<script type="text/javascript" src="${context }/js/refund/paybackBlueRefund.js"></script>
<script type="text/javascript" src="${context }/js/common.js"></script>
<meta name="decorator" content="default"/>
<script type="text/javascript">
$(document).ready(function(){
	loan.initCity("incomeCity", "incomeCounty", null);
	// 触发下计算 退款后蓝补余额 
	$("#paybackBuleAmount").blur();
}); 
</script>
</head>
<body>
<h2 class=" f14 pl10 ">退款</h2>
 <div class="box2 mb10">
  <form id="inputForm" >
  
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input type="hidden" id="tkMoneyHidden" value="<fmt:formatNumber value="${tkMoney}" pattern='0.00'/>">
                <input type="hidden" id="refundId" name="refundId" value="${refundId}">
                <input type="hidden" id="appType" name="appType" value="${refund.appType}">
                <input type="hidden" id="mt" name="mt" value="${payback.modifytime}">
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
                <td><label class="lab">蓝补余额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount1" name="paybackBuleAmount1" value="<fmt:formatNumber value="${payback.paybackbuleamount }" pattern='0.00'/>" readOnly/>
                </td>
                <td><label class="lab">期供余额：</label>
                  <input type="text" class="input_text178" name="paybackMonthAmount" value="<fmt:formatNumber value="${payback.paybackmonthamount }" pattern='0.00'/>" readOnly/>
                </td>
             </tr>
       
            <tr>
                <td><label class="lab">退款金额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmount" name="paybackBuleAmount" 
               	value="<fmt:formatNumber value="${tkMoney }" pattern='0.00'/>" 
               	onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"/>
                </td>
                <td>
                	<label class="lab">未还违约金及罚息总额：</label>
                  	<input type="text" class="input_text178" value="<fmt:formatNumber value="${payback.notpenaltypunishshouldsum }" pattern='0.00'/>" readOnly/>
                </td>
             </tr>
			   <tr>
                <td cols='2'><label class="lab">退款后蓝补余额：</label>
               <input type="text" class="input_text178" id = "paybackBuleAmountLast" name="paybackBuleAmountLast" value="<fmt:formatNumber value="0" pattern='0.00'/>" readonly/><%-- ${payback.paybackBuleAmount } --%>
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款账户：</label>
               <input type="text" class="input_text178 required" id="incomeAccount" name="incomeAccount" value="<c:if test="${refundId==''}">${payback.bankaccount }</c:if><c:if test="${refundId!=''}">${refund.incomeAccount }</c:if>" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" readonly/> 
                </td>
                <td><label class="lab">收款户名：</label>
               <input type="text" class="input_text178 required"  id="incomeName" name="incomeName" value="<c:if test="${refundId==''}">${payback.bankaccountname }</c:if><c:if test="${refundId!=''}">${refund.incomeName }</c:if>" maxlength="10" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" readonly/>
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款银行：</label>
              <%--  <input type="text" class="input_text178 required" id="incomeBank" name="incomeBank" value="${refund.incomeBank }" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')"/> --%>
                <select class="select180 required" id="incomeBank" name="incomeBank">
                	<c:if test="${refundId==''}">
                    	<c:forEach items="${fns:getDictList('jk_open_bank')}" var="applyBankName">
                    		<c:if test="${applyBankName.value eq payback.bankname}">
                            <option value="${applyBankName.label }" selected>${applyBankName.label }</option>
                            </c:if>
                        </c:forEach>
                	</c:if>
                	<c:if test="${refundId!=''}">
                    	<option value="${refund.incomeBank }" selected>${refund.incomeBank }</option>
                	</c:if>
                	<%-- <option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_open_bank')}" var="applyBankName">
                            	<option value="${applyBankName.label }" <c:if test="${applyBankName.label eq refund.incomeBank}">selected</c:if>>${applyBankName.label }</option>
                            </c:forEach> --%>
				</select>
                </td>
                <td><label class="lab">收款支行：</label>
               <input type="text" class="input_text178" id="incomeBranch" name="incomeBranch" value="<c:if test="${refundId==''}">${payback.bankbranch }</c:if><c:if test="${refundId!=''}">${refund.incomeBranch }</c:if>" maxlength="50" readonly/><!-- onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')" -->
                </td>
             </tr>
             <tr>
                <td><label class="lab">收款省直辖市：</label>
               <%-- <input type="text" class="input_text178 required" id="incomeCity" name="incomeCity" value="${refund.incomeCity }" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')"/> --%>
                <select class="select180" id="incomeCity" name="incomeCity" >
                	<c:if test="${refundId==''}">
                    	<option value="${payback.bankprovince }" selected>${payback.bankprovincename }</option>
                	</c:if>
                	<c:if test="${refundId!=''}">
                    	<option value="${refund.incomeCity }" selected>${refund.incomeCityName }</option>
                	</c:if>
                	
            		<%-- <option value="" selected="selected">选择省份</option>
                	<c:forEach var="item" items="${provinceList}" >
		             	<option value="${item.areaCode }" <c:if test="${item.areaCode eq refund.incomeCity}">selected</c:if>>${item.areaName}</option>
	            	</c:forEach> --%>
            	</select>
                </td>
                <td><label class="lab">收款市县：</label>
               <%-- <input type="text" class="input_text178 required" id="incomeCounty" name="incomeCounty" value="${refund.incomeCounty }" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')"/> --%>
                <select class="select180" id="incomeCounty" name="incomeCounty" >
                	<c:if test="${refundId==''}">
                    	<option value="${payback.bankcity }" selected>${payback.bankcityname }</option>
                	</c:if>
                	<c:if test="${refundId!=''}">
                    	<option value="${refund.incomeCounty }" selected>${refund.incomeCountyName }</option>
                	</c:if>
                	
                <%-- <option value="">请选择</option>
                <c:forEach var="item" items="${cityList}" >
		             <option value="${item.areaCode }" <c:if test="${item.areaCode eq refund.incomeCounty}">selected</c:if>>${item.areaName}</option>
	            </c:forEach> --%>
                </select>
                </td>
             </tr> 
			 <tr>
                <td cols='2'><label class="lab">退款原因：</label>
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" name="reson" value="${refund.remark}">${refund.remark}</textarea>
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
