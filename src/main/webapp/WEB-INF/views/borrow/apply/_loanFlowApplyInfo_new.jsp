<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<jsp:include page="./applyCommon.jsp"/>
<script type="text/javascript">
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
</script>
</head>
<body>
 <ul class="nav nav-tabs">
  	<li><a href="javascript:launch.getCustomerInfo('1');">个人基本信息</a></li>
	<li class="active"><a href="javascript:launch.getCustomerInfo('2');">借款意愿</a></li>
	<li><a href="javascript:launch.getCustomerInfo('3');">工作信息</a></li>
	<li><a href="javascript:launch.getCustomerInfo('4');">联系人信息</a></li>
	<li><a href="javascript:launch.getCustomerInfo('5');">自然人保证人信息</a></li>
	<li><a href="javascript:launch.getCustomerInfo('6');">房产信息</a></li>
	<li><a href="javascript:launch.getCustomerInfo('7');">经营信息</a></li>
	<li><a href="javascript:launch.getCustomerInfo('8');">证件信息</a></li>
	<li><a href="javascript:launch.getCustomerInfo('9');">银行卡信息</a></li>
</ul>
<form id="custInfoForm" method="post">
   <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
   <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
   <input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanInfo.loanCode" id="loanCode" />
   <input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanCode"/>
   <input type="hidden" value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id" id="custId" /> 
   <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" /> 
   <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode"/> 
   <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}" name="loanInfo.loanCustomerName" id="loanCustomerName" />
   <input type="hidden" value="${workItem.bv.loanCustomer.customerName}" name="loanCustomer.customerName" />
   <input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
   <input type="hidden" value="${workItem.flowCode}" name="flowCode"/>
   <input type="hidden" value="${workItem.flowName}" name="flowName"/>
   <input type="hidden" value="${workItem.stepName}" name="stepName"/>
   <input type="hidden" value="${workItem.flowType}" name="flowType"/>
</form>
<div id="div3" class="content control-group">
<form id="applyInfoForm" method="post">
	 <input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
     <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	 <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	 <input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanInfo.loanCode" id="loanCode" />
	 <input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanCode"/>
	 <input type="hidden" value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id" id="custId" /> 
	 <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" /> 
	 <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode"/> 
	 <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}" name="loanInfo.loanCustomerName" id="loanCustomerName" />
	 <input type="hidden" value="${workItem.bv.loanCustomer.customerName}" name="loanCustomer.customerName" />
	 <input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
	 <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	 <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	 <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	 <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
     <input type="hidden" value="${applyInfoEx.loanInfo.id}" name="loanInfo.id" id="applyInfoId"/>
	 <input type="hidden" name="loanInfo.loanCustomerService" value="${applyInfoEx.loanInfo.loanCustomerService}"/>
     <input type="hidden" name="loanInfo.loanManagerCode" value="${applyInfoEx.loanInfo.loanManagerCode}"/>
	 <input type="hidden" value="${applyInfoEx.loanInfo.storeCityCode}" id="storeCityHidden"/>
	 <input type="hidden" name="loanInfo.loanStoreOrgId" value="${applyInfoEx.loanInfo.loanStoreOrgId}"/>
     <input type="hidden" id="limitLower"/>
     <input type="hidden" id="limitUpper"/>
 	 <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
		    <td>
		   		<label class="lab"><span class="red">*</span>申请额度(元)：</label>
				<input type="text" name="loanInfo.loanApplyAmount" value="<fmt:formatNumber value='${applyInfoEx.loanInfo.loanApplyAmount}' pattern="##0.00"/>" class="input_text178 required numCheck positiveNumCheck limitCheck" />
		    </td>
		   	<td>
		   		<label class="lab" ><span class="red">*</span>产品类别：</label>
				<select id="productId" name="loanInfo.productType" value="${applyInfoEx.loanInfo.productType}" class="select180 required">
					<option value="">请选择</option>
					<c:forEach var="item" items="${productList}" varStatus="status">
					  <option value="${item.productCode }"
					    <c:if test="${item.productCode==applyInfoEx.loanInfo.productType}">
					      selected=true 
					    </c:if>
					  >${item.productName}</option>
					</c:forEach>
     			</select>
     			<c:if test="${applyInfoEx.loanInfo.consTelesalesFlag ne 1}">
	     			<span id="loanCustomerSourceSpan" style="display:none;">
		     			<label>客户来源：</label>
		     			<select id="loanCustomerSource" name="loanInfo.loanCustomerSource" class="select180">
		     				<option value="">请选择</option>
		     				<c:forEach items="${fns:getNewDictList('jk_loan_customer_source')}" var="item">
								<option value="${item.value}" <c:if test="${applyInfoEx.loanInfo.loanCustomerSource==item.value}">  selected=true  </c:if>>
									${item.label}
								</option>
							</c:forEach>
		     			</select>
	     			</span>
     			</c:if>
     		</td>
			<td>
				<label class="lab" ><span class="red">*</span>申请期限：</label> 
				<select id="monthId" name="loanInfo.loanMonths" value="${applyInfoEx.loanInfo.loanMonths}" class="select180 required">
					<option value="">请选择</option>
				</select>
				<input type="hidden" id="loanMonths" value="${applyInfoEx.loanInfo.loanMonths}"/>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab" ><span class="red">*</span>主要借款用途：</label> 
				<select id="dictLoanUse" name="loanInfo.dictLoanUse" value="${applyInfoEx.loanInfo.dictLoanUse}" onchange="dictLoanUseNewChange(this);" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getNewDictList('jk_loan_use')}" var="item">
							<option value="${item.value}" <c:if test="${applyInfoEx.loanInfo.dictLoanUse==item.value}">  selected=true  </c:if>>
								${item.label}
							</option>
						</c:forEach>
				</select>
				<input type="text" value="${applyInfoEx.loanInfo.dictLoanUseNewOther}" name="loanInfo.dictLoanUseNewOther" id="dictLoanUseNewOther" class="input_text178 required" maxlength='100' 
						style="width:100px;<c:if test="${applyInfoEx.loanInfo.dictLoanUse ne 12}">display:none</c:if>"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>最高可承受月还(元)：</label> 
				<input type="text" name="loanInfo.highPaybackMonthMoney" id="highPaybackMonthMoney" value="<fmt:formatNumber value='${applyInfoEx.loanInfo.highPaybackMonthMoney}' pattern="##0.00"/>"  class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>主要还款来源：</label> 
				<select id="dictLoanSource" name="loanInfo.dictLoanSource" value="${applyInfoEx.loanInfo.dictLoanSource}" onchange="dictLoanSourceChange(this);" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getNewDictList('jk_repay_source_new')}" var="item">
							<option value="${item.value}" 
							<c:if test="${applyInfoEx.loanInfo.dictLoanSource==item.value}"> 
								selected=true 
							  </c:if> 
							>${item.label}</option>
						</c:forEach>
				</select>
				<input type="text" value="${applyInfoEx.loanInfo.dictLoanSourceOther}" name="loanInfo.dictLoanSourceOther" id="dictLoanSourceOther" class="input_text178 required" maxlength='100' 
						style="width:100px;<c:if test="${applyInfoEx.loanInfo.dictLoanSource ne 7}">display:none</c:if>"/>
			</td>
		</tr>
		<tr>
		    <td colspan="3">
		    	<label class="lab"><span class="red productTypeSpan" style="display:none;">*</span>其他收入来源：</label> 
		    	<span>
			  		<c:forEach items="${fns:getNewDictList('jk_repay_source_new_else')}" var="item">
			  			<input type="checkbox" class="productTypeRe" name="loanInfo.dictLoanSourceElse" value="${item.value}" <c:if test="${fn:contains(applyInfoEx.loanInfo.dictLoanSourceElse,item.value)}">checked</c:if> onchange="dictLoanSourceElseChange(this);">${item.label}</input>
			  		</c:forEach>
				  	<input type="text" value="${applyInfoEx.loanInfo.dictLoanSourceElseOther}" name="loanInfo.dictLoanSourceElseOther" id="dictLoanSourceElseOther" class="input_text178 required" maxlength="100" 
				  			style="width:100px;<c:if test="${!fn:contains(applyInfoEx.loanInfo.dictLoanSourceElse,'5')}">display:none</c:if>"/>
			  	</span>
            </td> 
 		</tr>
		<tr>
			<td>
				<label class="lab"><span class="red">*</span>其它月收入(元)：</label>
				<input type="text" name="loanInfo.otherMonthIncome" id="otherMonthIncome" value="<fmt:formatNumber value='${applyInfoEx.loanInfo.otherMonthIncome}' pattern="##0.00"/>" class="input_text178 number numCheck positiveNumCheck required" min="0" max="999999999.00"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>同业在还借款总笔数：</label>
				<input type="text" name="loanInfo.otherCompanyPaybackCount" id="otherCompanyPaybackCount" value="${applyInfoEx.loanInfo.otherCompanyPaybackCount}" class="input_text178 required digits" maxlength="3"/> 
			</td> 
			<td>
				<label class="lab"><span class="red productTypeSpan" style="display:none;">*</span>月还款总额(元)：</label>
				<input type="text" name="loanInfo.otherCompanyPaybackTotalmoney" id="otherCompanyPaybackTotalmoney" value="<fmt:formatNumber value='${applyInfoEx.loanInfo.otherCompanyPaybackTotalmoney}' pattern="##0.00"/>" class="input_text178 productTypeRe number numCheck positiveNumCheck otherCompanyCompare" min="0" max="999999999.00"/>
			</td>
 		</tr>
 		<tr>
			<td>
				<label class="lab" ><span class="red">*</span>申请日期：</label> 
				<input class="input_text178 required" id="d4311" name="loanInfo.loanApplyTime" value="<fmt:formatDate value='${applyInfoEx.loanInfo.loanApplyTime}' pattern="yyyy-MM-dd HH:mm:ss"/>" type="text" class="Wdate"
						size="10" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: pointer" />
			</td>
			<td>
				<label class="lab"><span class="red">*</span>是否加急：</label> 
			      <span>
				      <c:forEach items="${fns:getNewDictList('yes_no')}" var="item">
						<input type="radio" class="required" name="loanInfo.loanUrgentFlag" value="${item.value}" 
							<c:if test="${item.value==applyInfoEx.loanInfo.loanUrgentFlag}">
	                        checked=true 
	                      </c:if> >${item.label}</input>
	                  </c:forEach>
                  </span>
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
			launch.saveApplyInfo('2','applyInfoForm','applyInfoNextBtn');
		});
		//loan.initCity("storeProvice", "storeCity",null);
		var createTime='<fmt:formatDate value="${consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>';
		loan.initProductWithLimitNew("productId", "monthId","limitLower","limitUpper",createTime);
		loan.selectedProductWithLimit("productId", "monthId",$('#loanMonths').val(),"limitLower","limitUpper",createTime,"loanCustomerSource");
		//客服来源change
		loan.initCustomerSourceWithLimit("productId","monthId",createTime,"loanCustomerSource");
		//areaselect.initCity("storeProvice", "storeCity",null, $("#storeCityHidden").val(), null);
		//$('#storeCity').attr('disabled',true);
		//loan.selectedProductRisk("productId", "monthId",$('#loanMonths').val(),'<fmt:formatDate value="${consultTime}" pattern="yyyy-MM-dd HH:mm:ss"/>');
		
		//根据选择的产品决定字段是否必填
		var productCode='${applyInfoEx.loanInfo.productType}';
		if(productCode!=null && productCode!=''){
			var productCodeArr = ['A007','A008','A014','A003']; //优卡借、优房借、精英借、薪水借
			if(productCodeArr.indexOf(productCode)!=-1){
				$(".productTypeSpan").show(); 
				$(".productTypeRe").addClass("required");
			}else{
				$(".productTypeSpan").hide();
				$(".productTypeRe").removeClass("required");
			}
		}
		//如果是老板借则客户来源字段显示
		if(productCode!=null && productCode!=''){
			if(productCode == "A005"){
				$("#loanCustomerSourceSpan").show(); 
			}else{
				$("#loanCustomerSourceSpan").hide(); 
				$("#loanCustomerSource").val("").trigger("change");
			}
		}
 	});
 	//主要借款用途下拉框改变时调用
 	function dictLoanUseNewChange(obj){
 		var value=obj.value;
		if(value==12){ //选择其他时显示文本框
			$("#dictLoanUseNewOther").show();
		}else{
			$("#dictLoanUseNewOther").hide();
		}
 	}
 	//主要还款来源下拉框改变时调用
 	function dictLoanSourceChange(obj){
 		var value=obj.value;
		if(value==7){ //选择其他时显示文本框
			$("#dictLoanSourceOther").show();
		}else{
			$("#dictLoanSourceOther").hide();
		}
 	}
 	//其他收入来源复选框改变时调用
 	function dictLoanSourceElseChange(obj){
 		var value=obj.value;
		if($("input[name='loanInfo.dictLoanSourceElse'][value='5']").is(':checked')){ //选择其他时显示文本框
			$("#dictLoanSourceElseOther").show();
		}else{
			$("#dictLoanSourceElseOther").hide();
		}
 	}
</script>
</body>
</html>