<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
</script>
</head>
<body>
 <ul class="nav nav-tabs">
	  <li><a href="javascript:launch.getCustomerInfo('1');">个人资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('2');">配偶资料</a></li>
	  <li class="active"><a href="javascript:launch.getCustomerInfo('3');">申请信息</a></li>

	    <c:if test="${workItem.bv.oneedition==-1}"> 
			<li ><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li ><a href="javascript:launch.getCustomerInfo('4');">自然人保证人</a></li>	
        </c:if>
	  <li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">
	   <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	   <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	   <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	</form>
<div id="div3" class="content control-group">
<form id="applyInfoForm" method="post">
     <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	 <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	 <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
      <input type="hidden" value="${applyInfoEx.loanInfo.id}" name="loanInfo.id" id="applyInfoId"/>
      <input type="hidden" id="limitLower"/>
      <input type="hidden" id="limitUpper"/>
 <table class=" table1" cellpadding="0" cellspacing="0" border="0"
		width="100%">
		<tr>
		   <td ><label class="lab" ><span class="red">*</span>产品类型：</label>
				<select id="productId" name="loanInfo.productType"
				value="${applyInfoEx.loanInfo.productType}" class="select180 required">
					<option value="">请选择</option>
					<c:forEach var="item" items="${productList}" varStatus="status">
					  <option value="${item.productCode }"
					    <c:if test="${item.productCode==applyInfoEx.loanInfo.productType}">
					      selected=true 
					    </c:if>
					  >${item.productName}</option>
					</c:forEach>
     			</select>
     		</td>
			<td ><label class="lab" ><span class="red">*</span>申请额度（元）：</label>
				<input type="text" name="loanInfo.loanApplyAmount"
				value="<fmt:formatNumber value='${applyInfoEx.loanInfo.loanApplyAmount}' pattern="##0.00"/>" class="input_text178 required numCheck positiveNumCheck limitCheck" /></td>
			<td><label class="lab" ><span class="red">*</span>借款期限：</label> <select
				id="monthId" name="loanInfo.loanMonths"
				value="${applyInfoEx.loanInfo.loanMonths}" class="select180 required">
					<option value="">请选择</option>
				</select>
				<input type="hidden" id="loanMonths" value="${applyInfoEx.loanInfo.loanMonths}"/>
			</td>
		</tr>
		<tr>
			<td><label class="lab" ><span class="red">*</span>申请日期：</label> <input class="input_text178 required"
				id="d4311" name="loanInfo.loanApplyTime" value="<fmt:formatDate value='${applyInfoEx.loanInfo.loanApplyTime}' pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" class="Wdate"
				size="10" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="cursor: pointer" /></td>
			<td><label class="lab" ><span class="red">*</span>借款用途：</label> <select id="cardType"
				name="loanInfo.dictLoanUse" value="${applyInfoEx.loanInfo.dictLoanUse}"
				class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
						<option value="${item.value}" 
						<c:if test="${applyInfoEx.loanInfo.dictLoanUse==item.value}"> 
						    selected=true 
						  </c:if> 
						>${item.label}</option>
					</c:forEach>
			</select></td>
			<td><label class="lab"><span class="red">*</span>具体用途：</label>
			<input type="text"
				value="${applyInfoEx.loanInfo.realyUse}" name="loanInfo.realyUse"
				class="input_text178 required" maxlength='50' /></td>
		</tr>
		<tr>
		   <td><label class="lab"><span class="red">*</span>客户职业类型：</label> 
			      <span>
			      <select class="select78 mr34 required" name="loanInfo.dictProfType">
			          <option value="">请选择</option>
			       		 <c:forEach items="${fns:getDictList('jk_prof_type')}" var="item">
					 	  <option value="${item.value}" 
							<c:if test="${item.value==applyInfoEx.loanInfo.dictProfType}">
                          		selected=true 
                      		  </c:if> >${item.label}</option>
                   		 </c:forEach>
                    </select>
                   </span>
            </td> 
			<td><label class="lab"><span class="red">*</span>是否加急：</label> 
			      <span>
			      <c:forEach
					items="${fns:getDictList('yes_no')}" var="item">
					<input type="radio" class="required" name="loanInfo.loanUrgentFlag" value="${item.value}" 
						<c:if test="${item.value==applyInfoEx.loanInfo.loanUrgentFlag}">
                        checked=true 
                      </c:if> >${item.label}</input>
                   </c:forEach>
                   </span>
            </td>
           <td><label class="lab">录入人：</label>
               <input type="text" name="loanInfo.loanCustomerServiceName" disabled="disabled" value="${applyInfoEx.loanInfo.loanCustomerServiceName}" class="input_text178"/>
               <input type="hidden" name="loanInfo.loanCustomerService" value="${applyInfoEx.loanInfo.loanCustomerService}"/>
            </td>
 		</tr>
		<tr> 
	       <td><label class="lab">客户经理：</label>
               <input type="text" name="loanInfo.loanManagerName" disabled="disabled" value="${applyInfoEx.loanInfo.loanManagerName}" class="input_text178"/>
               <input type="hidden" name="loanInfo.loanManagerCode" value="${applyInfoEx.loanInfo.loanManagerCode}"/>
            </td>
			<td colspan="1"><label class="lab" >管辖城市：</label> 
			 <select class="select78 mr34" disabled="disabled" id="storeProvice">
				  <option value="">请选择</option>
				  <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode }"
		             <c:if test="${applyInfoEx.loanInfo.storeProviceCode==item.areaCode}">
		               selected=true 
		             </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
			  </select>
			  <select class="select78" disabled="disabled" id="storeCity"><option value="">请选择</option>
			  </select>
			  <input type="hidden" value="${applyInfoEx.loanInfo.storeCityCode}" id="storeCityHidden"/>
		   </td>
		   <td>
               <input type="hidden" name="loanInfo.loanStoreOrgId" value="${applyInfoEx.loanInfo.loanStoreOrgId}"/>
            </td>
		</tr>
		<tr>
	     <td colspan="3"> 
	       <label class="lab">备注：</label>
	       <input type="hidden" name="loanRemark.id" value="${applyInfoEx.loanRemark.id}"/>
	       <textarea rows="" cols="" id="remarks" name="loanRemark.remark" maxlength="450" class="textarea_refuse">${applyInfoEx.loanRemark.remark}</textarea>
		 </td>
	  </tr>
	</table>
	<div class="tright mb5 mr10" >
		<input type="submit" id="applyInfoNextBtn" class="btn btn-primary" value="下一步" />
	</div>
	</form>
</div>
<script type="text/javascript">
$(document).ready(
		function() {
			$('#applyInfoNextBtn').bind('click',function(){
				$(this).attr('disabled',true);
				launch.saveApplyInfo('3','applyInfoForm','applyInfoNextBtn');
			});
			loan.initCity("storeProvice", "storeCity",null);
			loan.initProductWithLimit("productId", "monthId","limitLower","limitUpper",'<fmt:formatDate value="${consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>');
			loan.selectedProductWithLimit("productId", "monthId",$('#loanMonths').val(),"limitLower","limitUpper",'<fmt:formatDate value="${consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>');
			areaselect.initCity("storeProvice", "storeCity",
					  null, $("#storeCityHidden").val(), null);
			$('#storeCity').attr('disabled',true);
			//loan.selectedProductRisk("productId", "monthId",$('#loanMonths').val(),'<fmt:formatDate value="${consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>');
 });
</script>
</body>
</html>