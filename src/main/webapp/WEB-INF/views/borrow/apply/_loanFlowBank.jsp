<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
var formConfig = {button: null};
$(document).ready(function(){

$('#launchFlow').bind('click',function(){
	formConfig.button = "launchFlow";
	$('#launchFlow').attr('disabled',true);
	$('#tempSave').attr('disabled',true);
	launch.launchFlow('9','bankForm','launchFlow');
	
});
$('#tempSave').bind('click',function(){
	formConfig.button = "tempSave";
	$('#launchFlow').attr('disabled',true);
	$('#tempSave').attr('disabled',true);
	launch.launchFlow('9','bankForm','tempSave');
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
		 $('#bankAccountName').removeAttr("readOnly");
	 }else{
		 $('#bankAccountName').attr('readOnly', true); 
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
	  <li><a href="javascript:launch.getCustomerInfo('1');">个人资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('2');">配偶资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('3');">申请信息</a></li>
	  
	    <c:if test="${workItem.bv.oneedition==-1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">自然人/保证人</a></li>	
        </c:if>
      
	  <li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
      <li class="active"><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">
	  <input type="hidden" value="${workItem.bv.oneedition}" name="oneedition"  id="oneedition"/>
	  <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	  <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode1" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId1" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode1" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName1" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId1"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	</form>
<div id="div9" class="content control-group">
	  <form id="bankForm" method="post">
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
	    <input type="hidden" name="signingPlatformName" id="signingPlatformName"/>
        <input type="hidden" value="${applyInfoEx.loanBank.id}" name="loanBank.id" id="loanBankId"/>
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
				            class="input_text178 required stringCheck"/>
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
                      <c:if test="${item.label!='中金' && item.value!='6' && item.value != '7' && item.value != '4'
                      }">
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
			 <td><label class="lab"><span class="red">*</span>开户行名称：</label>
			 <select name="loanBank.bankName" id="bankName" value="${applyInfoEx.loanBank.bankName}" class="select180 required">
                    <option value="">开户行</option>
                    <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${applyInfoEx.loanBank.bankName==curItem.value}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach>
				</select>
                </td>
                  <td><label class="lab"><span class="red">*</span>开户支行：</label>
                <%--  <input type="hidden" id ='hdloanBankbrId' name="loanBank.bankNo" value="${applyInfoEx.loanBank.bankNo}"/> --%>
                 <input type="text"  name="loanBank.bankBranch" value="${applyInfoEx.loanBank.bankBranch}" class="input_text178 required stringCheck2"/>
                  <!-- <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i> -->
                 </td>
                <td><label class="lab"><span class="red">*</span>开户省市：</label>
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
            <input type="submit" class="btn btn-primary" id="launchFlow" value="提交"></input>
             <input type="submit" class="btn btn-primary" id="tempSave" value="保存"></input>
        </div>
         </form>
    </div>
</body>
</html>