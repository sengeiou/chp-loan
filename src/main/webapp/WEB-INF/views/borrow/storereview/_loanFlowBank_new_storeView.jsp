<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<jsp:include page="../apply/applyCommon.jsp"/>
<script type="text/javascript">
$(document).ready(function(){

$('#tempSave').bind('click',function(){
	$('#tempSave').attr('disabled',true);
	launch.updateApplyInfo('9', 'bankForm','tempSave');	
   });

loanCard.initCity("bankProvince", "bankCity",null);
 $('#bankSigningPlatform').bind('change',function(){
	$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
 });
 
 areaselectCard.initCity("bankProvince", "bankCity",
		  null, $("#bankCityHidden")
				.val(), null);
 $('#bankIsRareword').bind('click',function(){
	 if($(this).attr('checked') || $(this).attr('checked')=='checked'){
		 $('#bankAccountName').removeAttr("readonly");
	 }else{
		 $('#bankAccountName').attr('readonly', true); 
	 }
 });
 
 loan.getBankList("loanBankbrId","hdloanBankbrId");
});
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
</script>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:launch.getCustomerInfo_storeView('1');">个人基本信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('2');">借款意愿</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('3');">工作信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('4');">联系人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('5');">自然人保证人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('6');">房产信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('8');">证件信息</a></li>
		<li class="active"><a href="javascript:launch.getCustomerInfo_storeView('9');">银行卡信息</a></li>
		<li style="width:auto;float:right;">
			<jsp:include page="./tright.jsp"/>
		</li>
	</ul>

	<form id="custInfoForm" method="post">
	  	<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}" id="customerCode">
		<input type="hidden" name="consultId" value="${workItem.bv.consultId}" id="consultId">
		<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}" id="loanCode" >
		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
		<input type="hidden" name="token" value="${workItem.token}">
		<input type="hidden" name="stepName" value="${workItem.stepName }">
		<input type="hidden" name="flowName" value="${workItem.flowName }">
		<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
	
		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
		<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
	</form>
	<div id="div9" class="content control-group">
	  <form id="bankForm" method="post">
	  	<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
	  	<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}">
		<input type="hidden" name="consultId" value="${workItem.bv.consultId}">
		<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}">
		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
		<input type="hidden" name="token" value="${workItem.token}">
		<input type="hidden" name="stepName" value="${workItem.stepName }">
		<input type="hidden" name="flowName" value="${workItem.flowName }">
		<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
		<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
		<input type="hidden" name="loanCustomer.customerName" value="${applyInfoEx.loanCustomer.customerName}" id="loanCustomer_customerName"/>
		<input type="hidden" name="loanBank.id" value="${applyInfoEx.loanBank.id}"/>
		<input type="hidden" name="loanBank.loanCode" value="${applyInfoEx.loanBank.loanCode }">
		<input type="hidden" name="method" value="bank">	
	    <input type="hidden" name="signingPlatformName" id="signingPlatformName" value="${fns:getDictLabel(applyInfoEx.loanBank.bankSigningPlatform,'jk_deduct_plat','')}"/>
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
               <td>
 				<label class="lab"><span class="red">*</span>银行卡/折 户名：</label>
				<c:choose>
				  <c:when test="${applyInfoEx.loanBank.bankAccountName!=null && applyInfoEx.loanBank.bankAccountName!=''}">
				      <input type="text" name="loanBank.bankAccountName" id="bankAccountName" value="${applyInfoEx.loanBank.bankAccountName}"
				        <c:if test="${applyInfoEx.loanBank.bankIsRareword!=1}">
				           readonly="readonly"
				        </c:if>   
				            class="input_text178 required stringCheck bankAccountNameCheckOne bankAccountNameCheckTwo"/>
				  </c:when>
				  <c:otherwise>
				     <input type="text" name="loanBank.bankAccountName"  id="bankAccountName" value="${workItem.bv.loanInfo.loanCustomerName}" 
				       <c:if test="${applyInfoEx.loanBank.bankIsRareword!=1}">
				           readonly="readonly"
				        </c:if>  
				           class="input_text178 required stringCheck"/>
				   </c:otherwise>
				 </c:choose> 
				 <input type="checkbox" name="loanBank.bankIsRareword" id="bankIsRareword" value="1"
				    <c:if test="${applyInfoEx.loanBank.bankIsRareword==1}">
				       checked = true
				    </c:if>
				 />是否生僻字
				 </td>
				 <td><label class="lab"><span class="red">*</span>授权人：</label>
				 <c:choose><c:when test="${applyInfoEx.loanBank.bankAuthorizer!=null && applyInfoEx.loanBank.bankAuthorizer!=''}">
				<input type="text" name="loanBank.bankAuthorizer" value="${applyInfoEx.loanBank.bankAuthorizer}" readonly="readonly" class="input_text178 required stringCheck"/></c:when><c:otherwise><input type="text" name="loanBank.bankAuthorizer" value="${workItem.bv.loanInfo.loanCustomerName}" readonly="readonly" class="input_text178 required stringCheck"/>
				   </c:otherwise>
				 </c:choose> 
				</td>
				<td><label class="lab"><span class="red">*</span>签约平台：</label>
				<select id="bankSigningPlatform" name="loanBank.bankSigningPlatform" value="${applyInfoEx.loanBank.bankSigningPlatform}" class="select180 required">
                  <option value="">签约平台</option>
                     <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
                      <c:if test="${item.label!='中金' && item.value!='6' && item.value != '7' && item.value != '4'}">
					    <option value="${item.value}"
					      <c:if test="${applyInfoEx.loanBank.bankSigningPlatform==item.value}">
					        selected=true 
					      </c:if> 
					     >${item.label}
					    </option>
					    </c:if>
				     </c:forEach>
				</select>
			 </td>
            </tr>
            <tr>
			 <td><label class="lab"><span class="red">*</span>开户行：</label>
						<select name="loanBank.bankName" id="bankName" value="${applyInfoEx.loanBank.bankName}" class="select180 required">
							<option value="">开户行</option>
							<c:choose>
								<c:when test="${borrowBankList ne null && not empty borrowBankList}">
									<c:forEach items="${borrowBankList}" var="item">
										<option value="${item.bankCode}" <c:if test="${applyInfoEx.loanBank.bankName==item.bankCode}"> selected=true </c:if>>
											${item.bankName}
										</option>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
										<option value="${curItem.value}" <c:if test="${applyInfoEx.loanBank.bankName==curItem.value}"> selected=true </c:if>>
											${curItem.label}
										</option>
									</c:forEach>	
								</c:otherwise>
							</c:choose>
						</select>
					</td>
                  <td><label class="lab"><span class="red">*</span>开户行支行：</label>
                <%--  <input type="hidden" id ='hdloanBankbrId' name="loanBank.bankNo" value="${applyInfoEx.loanBank.bankNo}"/> --%>
                 <input type="text"  name="loanBank.bankBranch" value="${applyInfoEx.loanBank.bankBranch}" class="input_text178 required stringCheck2"/>
                  <!-- <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i> -->
                 </td>
                <td><label class="lab"><span class="red">*</span>开户行省市：</label>
                <select class="select78" id="bankProvince" name="loanBank.bankProvince" value="${applyInfoEx.loanBank.bankProvince}">
                      <option value="">请选择</option>
                      <c:forEach var="item" items="${provinceList}" varStatus="status">
		               <option value="${item.areaCode}" 
		                <c:if test="${applyInfoEx.loanBank.bankProvince==item.areaCode}">
		                   selected=true 
		                </c:if>
		               >${item.areaName}</option>
	                  </c:forEach>
                    </select>-<select class="select78 required" id="bankCity" name="loanBank.bankCity" value="${applyInfoEx.loanBank.bankCity}">
                       <option value="">请选择</option>
                    </select>
                    <input type="hidden" id="bankCityHidden" value="${applyInfoEx.loanBank.bankCity}"/>
                </td>
            </tr>
            <tr>
               <td><label class="lab"><span class="red">*</span>银行卡号：</label>
                 <input type="text" id="firstInputBankAccount" value="${applyInfoEx.loanBank.bankAccount}" class="input_text178 required" name="firstInputBankAccount"/></td>
                 <td><label class="lab"><span class="red">*</span>确认银行卡号：</label>
                <input type="text" id="confirmBankAccount" value="${applyInfoEx.loanBank.bankAccount}" name="loanBank.bankAccount" class="input_text178 required"></td>
               <td></td>
            </tr>
         </table>
       
         <!--流程参数-->
        <div class="tright mr10 mb5" >
            <input type="submit" class="btn btn-primary" id="tempSave" value="保存"></input>
        </div>
         </form>
    </div>
</body>
</html>