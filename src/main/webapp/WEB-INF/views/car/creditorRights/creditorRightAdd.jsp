<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>增加债权信息</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carConsultForm.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/car/creditorRights/creditorRights.js'></script>
<script type="text/javascript">
$(document).ready(function(){
	//编辑-显示 申请借款期限 
	if('${creditRight.borrowingDays}'!=''){
		getLoanMonths($("#productType").val(),'${creditRight.borrowingDays}');
	}
    
	//creditorRights.validate();
	$("#dictLoanCommonRepaymentFlag").click(function(){
		/* if ($(this).val() == 0) {
			$("tr[class='myhide']").hide();  
		}else{
			$("tr[class='myhide']").show(); 
		} */
	});
	
	$("#monthlyInterestRate").val("${creditRight.monthlyInterestRate}");
	$('input:radio[name="customerCobo"]').change(function() {
		var isOtherRevenue = $('input:radio[name="customerCobo"]:checked ').val();
		if(isOtherRevenue){
			if(isOtherRevenue == '0'){
				$("#coborrowAdd").hide();
			}else{
				$("#coborrowAdd").show();
			}
		}
	});
	$('input:radio[name="customerCobo"]').trigger("change");
});
function delRows(nowTr){
	$(nowTr).parent().parent().remove(); 
}
</script>
</head>
<body>
<form:form id="creditorRightsForm" modelAttribute="creditorRight" action="${ctx}/car/creditorRight/add" method="post" class="form-horizontal">
<input type="hidden" name="id" value="${creditRight.id }"/>
<input type="hidden" name="loanCode" value="${creditRight.loanCode }"/>
<input type="hidden" name="rightsType" value="${creditRight.rightsType }"/>
<input type="hidden" name="issendWealth" value="${creditRight.status }"/>
<h2 class="pt5 f14 pl10">基本信息</h2>
 <div class="box2 mb10">
     <table id="coborrowArea" class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <tr>
             <td><label class="lab"><span class="red">*</span>借款人姓名：</label><input type="text" id="loanCustomerName" name="loanCustomerName" class="input_text178 required realName" maxlength="10" value="${creditRight.loanCustomerName }"></td>
             <td><label class="lab"><span class="red">*</span>证件类型：</label>
             <select  name="certType" class="select180 required">
             	<option  value="">请选择</option>
			    <c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
				<option value="${item.value}" <c:if test="${creditRight.certType==item.value}">
			      selected = true 
			     </c:if> >${item.label}</option>
				</c:forEach>
             </select></td>
         </tr>
         <tr>
             <td><label class="lab"><span class="red">*</span>证件号码：</label><input type="text" name="customerCertNum" class="input_text178 required card" value="${creditRight.customerCertNum }"  maxlength="18"></td>
             <td><label class="lab"><span style="color: #ff0000">*</span>车牌号码：</label>
             		<input id="plateNumbers" name="plateNumbers" class="required input_text178 plateNum" type="text" value="${creditRight.plateNumbers }" maxlength="8"/>
             		<span id="blackTip" style="color: red;"></span>
             </td>
         </tr>
         <tr>
             <td>
             	<label class="lab"><span class="red">*</span>共同借款人：</label>
             	<c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
					<input type="radio" id="dictLoanCommonRepaymentFlag" name="customerCobo" class="required" 
					  <c:if test="${item.value == hasCobo }"> checked='true'</c:if>value="${item.value}" />${item.label}
                </c:forEach>
                <input type="button" class="btn btn-small" id="coborrowAdd" value="增加共同借款人"></input>
            </td>
         </tr>
         <c:forEach var="cobo" items="${coborrowers}">
         	<tr>
		             <td>
		             	<label class="lab"><span class="red">*</span>共借人姓名：</label>
		             	<input type="text" maxlength="10" class="input_text178 required realName" name="coborrowName3" mark="coborrowName" value="${cobo.loanCustomerName}">
		             </td>
		             <td>
		             	<label class="lab"><span style="color: #ff0000">*</span>共借人证件号码：</label>
		             	<input type="text" class="input_text178 required" name="coborrowCertNum3" mark="coborrowCertNum" value="${cobo.customerCertNum}">
		             	<input type="button" value="删除" class="btn_edit" onclick="delRows(this);">
		             </td>
		         </tr>
		 </c:forEach>
     </table>
 </div>
<h2 class="pt5 f14 pl10 mt20">车借信息</h2>
    <div class="box2 mb10">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td width="32%"><label class="lab"><span class="red">*</span>合同编号：</label>
                <input style="width: 242px;" type="text" name="contractCode" class="input_text178 required" maxlength="40" value="${creditRight.contractCode }"></td>
                <td width="32%"><label class="lab"><span class="red">*</span>借款产品类型：</label>
                	<select class="select180 required" id="productType" name="productType" onclick="getLoanMonths(this.value);">
                	 		 <option value="">请选择</option>
		                    <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
		                      <option value="${product_type.productCode}" <c:if test="${creditRight.productType==product_type.productCode}">
							      selected = true 
							     </c:if> >${product_type.productName}</option>
		                    </c:forEach>  
                      </select>
				</td>
                <td><label class="lab"><span class="red">*</span>职业情况：</label>
                		<select class="select180 required" name="occupationCase">
               				<option value="">请选择</option>
		                     <c:forEach items="${fns:getDictList('car_occupation_case')}" var="item">
							<option value="${item.value}"  
								<c:if test="${creditRight.job==item.value}">
							      selected = true 
							     </c:if>>${item.label}</option>
							</c:forEach>
						</select>
				</td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>车借借款总额：</label>
                <input type="text" name="primevalWorth" class="required input_text178" value='<fmt:formatNumber value='${creditRight.primevalWorth}' pattern="#00.00"/>'></td>
                <td><label class="lab"><span class="red">*</span>借款用途：</label>
                	<select class="select180 required" name="usageOfLoan" >
                			<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
								<option value="${item.value}" <c:if test="${creditRight.usageOfLoan==item.value}">
							      selected = true 
							     </c:if>>${item.label}</option>
							</c:forEach>
					</select>
				</td>  
                <td><label class="lab"><span class="red">*</span>借款月利率（%）：</label>
               		<select class="select180 required" name="monthlyInterestRate" id="monthlyInterestRate">
               				<option value="">请选择</option>
							<option value="0.567" <c:if test="${creditRight.monthlyInterestRate =='0.567'}">selected = true </c:if>>0.5670</option>
							<option value="0.483" <c:if test="${creditRight.monthlyInterestRate =='0.483'}">selected = true </c:if>>0.4830</option>
							<option value="0.667" <c:if test="${creditRight.monthlyInterestRate =='0.667'}">selected = true </c:if>>0.6670</option>
					</select>	
				</td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>合同签约日期：</label>
                 	<input name="contractDay" class="input_text178 required"    
                 	value=" <fmt:formatDate value='${creditRight.contractDay}' pattern="yyyy-MM-dd"/>" onchange="changeContractDay()"
				 		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
                </td>
                <td><label class="lab"><span class="red">*</span>申请借款期限：</label>
                   <select id="monthId" name="loanDays" class="select180 required" onchange="setContractDate(this.value)">
					</select>
                </td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>首次还款日：</label>
                	<input name="downPaymentDay"  class="input_text178 required"  
                	 value=" <fmt:formatDate value='${creditRight.downPaymentDay }' pattern="yyyy-MM-dd"/>"
				 		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
                </td>
				<td><label class="lab"><span class="red">*</span>合同截止日期：</label>
					<input name="contractEndDay"  class="input_text178 required" 
					 value=" <fmt:formatDate value='${creditRight.contractEndDay }' pattern="yyyy-MM-dd"/>"
				 		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer"/>
				</td>
            </tr>
			<tr>
                <td><label class="lab"><span class="red">*</span>债权人：</label>
					<form:select id="creditor" path="creditor" class="select180 required">
								<option value="">请选择</option>
								<option value="夏靖" selected>夏靖</option>
								<option value="寇振红">寇振红</option>
                   </form:select>
						
				</td>
				
                <td><label class="lab">借款状态：</label>
					 <select id="loanStatus" path="loanStatus" class="select180" name="loanStatus">
                       <option value="">请选择</option>
                       <c:forEach items="${fns:getDictLists('33,36,37,38','jk_car_loan_status')}" var="dict_loan_status">
                       <option value="${dict_loan_status.value}"  
                       <c:if test="${creditRight.loanStatus==dict_loan_status.value}">
                       selected = true
                       </c:if>>${dict_loan_status.label } </option>
                      </c:forEach>
                   </select>
				</td>
				<td><label class="lab"><span class="red">*</span>渠道：</label>
					<select id="channelType" name="channelType" class="select180">
		                   <option value="">请选择</option>
			                    <c:forEach items="${fns:getDictLists('2,3','jk_car_throuth_flag')}" var="loan_Flag">
			                   		<option value="${loan_Flag.value}" <c:if test="${creditRight.channelType==loan_Flag.value}">
			                   		selected = true
			                   		</c:if>>${loan_Flag.label}</option>
			             		 </c:forEach>
				         </select>
				</td>
            </tr>
            
			<tr>
                	 <td><label class="lab"><span class="red">*</span>还款方式：</label>
					<select id="contractReplayWay" name="contractReplayWay" class="select180 required">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictLists('0,1','jk_car_repay_interest_way')}" var="repay_way">
								<option value="${repay_way.value}"  <c:if test="${creditRight.contractReplayWay== repay_way.value}">
								   selected = true
								</c:if>>${repay_way.label}</option>
						</c:forEach>
					</select>
						
				</td>
                <td>
                	<label class="lab"><span class="red">*</span>还款金额：</label>
					<input type="text" name="contractReplayAmount" class="required input_text178" value="<fmt:formatNumber value='${creditRight.contractReplayAmount}' pattern="#00.00"/>"></td>
				</td>
				<td></td>
            </tr>            
        </table>
    </div>
    <div class="tright mt10 pr30">
	    <input type="button"  class="btn btn-primary" id="creditorRightSubmit" value="保存" />
		<input type="button"  class="btn btn-primary" onclick="history.go(-1)" value="取消" />
	</div>
  </form:form>
	<div  class="hide" >
			<table id="mycoborrowCopy" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table03" >
				<tbody>
		         <tr class="myhide">
		             <td>
		             	<label class="lab"><span class="red">*</span>共借人姓名：</label>
		             	<input type="text" mark="coborrowName" name="coborrowName" class="input_text178 required realName" maxlength="10">
		             </td>
		             <td>
		             	<label class="lab"><span style="color: #ff0000">*</span>共借人证件号码：</label>
		             	<input type="text" mark="coborrowCertNum" name="coborrowCertNum" class="input_text178 required  card" maxlength="18">
		             	<input type="button" onclick="delRows(this);" class="btn_edit" value="删除"></input>
		             </td>
		         </tr>
              </tbody>
            </table>
	</div>  
</body>
</html>