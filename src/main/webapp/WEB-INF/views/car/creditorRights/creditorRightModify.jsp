<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>债权信息列表</title>
<meta name="decorator" content="default" />
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/car/creditorRights/creditorRights.js'></script>
<script type="text/javascript">
$(document).ready(function(){
	creditorRights.validate();
});
</script>
</head>
<body>
 <form:form id="inputForm" modelAttribute="creditorRight" action="${ctx}/car/creditorRight/modify" method="post" class="form-horizontal">
 <h2 class="pt5 f14 pl10">基本信息</h2>
 <input type="hidden" name="id" value="${creditRight.id }"/>
 <div class="box2 mb10">
     <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <tr>
             <td><label class="lab"><span class="red">*</span>借款人姓名：</label>
                <input type="text" name="loanCustomerName" class="input_text178" value="${creditRight.loanCustomerName }"></td>
             <td><label class="lab"><span class="red">*</span>证件类型：</label>
               <select class="select180" name="certType" >
             	<option  value="">请选择</option>
			    <c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
				<option value="${item.value}" <c:if test="${creditRight.certType==item.value}">
			      selected = true 
			     </c:if> >${item.label}</option>
				</c:forEach>
             </select></td>
         </tr>
         <tr>
             <td><label class="lab"><span class="red">*</span>证件号码：</label>
             <input type="text" name="customerCertNum" value="${creditRight.customerCertNum }" class="input_text178"></td>
             <td><label class="lab"><span class="red">*</span>车牌号码：</label>
             <input type="text" name="plateNumbers" value="${creditRight.plateNumbers }"  class="input_text178"></td>
             
         </tr>
     </table>
 </div>
<h2 class="pt5 f14 pl10 mt20">车借信息</h2>
    <div class="box2 mb10">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td width="32%"><label class="lab">合同编号：</label>
                <input type="text" name="contractCode" value="${creditRight.contractCode }"   class="input_text178"></td>
                <td width="32%"><label class="lab">借款产品类型：</label>
                	<select class="select180" name="productType">
                	 		 <option value="">请选择</option>
		                    <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
		                      <option value="${product_type.productCode}"
		                      <c:if test="${creditRight.productType==product_type.productName}">
							      selected = true 
							     </c:if>>${product_type.productName}</option>
		                    </c:forEach>  
                      </select>
				</td>
                <td><label class="lab">职业情况：</label>
                		<select class="select180" name="occupationCase">
               				<option value="">请选择</option>
		                     <c:forEach items="${fns:getDictList('car_occupation_case')}" var="item">
							<option value="${item.label}"  
								<c:if test="${creditRight.job==item.label}">
							      selected = true 
							     </c:if>>${item.label}</option>
							</c:forEach>
						</select>
				</td>
            </tr>
            <tr>
                <td><label class="lab">车借借款总额：</label>
                <input type="text" name="loanAmount" value="${creditRight.loanAmount }"  class="input_text178"></td>
                <td><label class="lab">借款用途：</label>
                	<select class="select180" name="usageOfLoan" >
                			<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
								<option value="${item.label}" <c:if test="${creditRight.usageOfLoan==item.label}">
							      selected = true 
							     </c:if>>${item.label}</option>
							</c:forEach>
					</select>
				</td>
                <td><label class="lab">借款月利率（%）：</label>
               		<select class="select180" name="monthlyInterestRate">
               				<option value="0">请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_month_interest_ratio')}" var="item">
								<option value="${item.label}" 	<c:if test="${creditRight.monthlyInterestRate ==item.label}">
							      selected = true 
							     </c:if>>${item.label}</option>
							</c:forEach>
					</select>	
				</td>
            </tr>
            <tr>
                <td><label class="lab">合同签约日期：</label>
                 	<form:input path="contractDay" value="${creditRight.contractDay }"   class="input_text178" 
				 		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
                </td>
                <td><label class="lab">首次还款日：</label>
                	<form:input path="downPaymentDay" value="${creditRight.downPaymentDay }"   class="input_text178" 
				 		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
                </td>
				<td><label class="lab">合同截止日期：</label>
					<form:input path="contractEndDay"   value="${creditRight.contractEndDay }"  class="input_text178" 
				 		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
				</td>
            </tr>
			<tr>
                <td><label class="lab">债权人：</label>
					<select id="creditor" name="creditor" class="select180">
                       <option value="">请选择</option>
	                     <c:forEach items="${fns:getDictList('car_creditor_rights')}" var="item">
						     <option value="${item.label}" 
							     <c:if test="${creditRight.creditor==item.label}">
							      selected = true 
						     </c:if>>${item.label}</option>
	                    </c:forEach>  
                   </select>
				</td>
                <td><label class="lab">借款状态：</label>
					<form:select id="loanStatus" path="loanStatus" class="select180">
                       <option value="">请选择</option>
					    <c:forEach items="${fns:getDictLists('33,36,37,38','jk_car_loan_status')}" var="item">
						     <option value="${item.value}" 
							     <c:if test="${creditRight.loanStatus==item.value}">
							      selected = true 
							     </c:if> >${item.label}</option>
	                    </c:forEach>
                   </form:select>
				</td>
				<td><label class="lab"><span class="red">*</span>渠道：</label>
					 <form:select id="channelType" path="channelType" class="select180 required">
                       <option value="">请选择</option>
                       <option value="1">CHP</option>
                       <option value="2">P2P</option>
                     
                   </form:select>
				</td>
            </tr>
        </table>
    </div>
    <div class="tright mt10 pr10">
	    <input type="submit"  class="btn btn-primary" id="btnSubmit" value="提交" />
		<input type="button"  class="btn btn-primary" onclick="history.go(-1)" value="取消" />
	</div>
  </form:form>
</body>
</html>