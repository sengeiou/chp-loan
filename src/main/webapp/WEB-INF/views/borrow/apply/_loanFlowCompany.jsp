<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript"> 
$(document).ready(function(){
	$('#companyNextBtn').bind('click',function(){
		$(this).attr('disabled',true);
		//产品类型为老板借 或小微企业借 且申请额度在30W以上（不含） 法人代表姓名，法人代表身份证号，法人代表手机号，企业邮箱， 必填   // 其他可不填
	 	launch.saveApplyInfo('6','companyForm','companyNextBtn'); 
	});
	loan.initCity("compProvince", "compCity","compArer");
	areaselect.initCity("compProvince", "compCity",
			 "compArer", $("#compCityHidden").val(),$("#compArerHidden").val());
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
      <li class="active"><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
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
 <div id="div6" class="content control-group">
    <form id="companyForm" method="post">
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
	  	<input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
	  	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  	<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	 	<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
        <input type="hidden" name="customerLoanCompany.id" value="${applyInfoEx.customerLoanCompany.id}" id="custCompId"/>
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td  width="31%" >
                  <label class="lab"><span class="red">*</span>公司名称：</label>
                  <input type="text" name="customerLoanCompany.compName" value="${applyInfoEx.customerLoanCompany.compName}" class="input_text178 required compNameCheck"/></td>
                <td  width="31%" ><label class="lab"><span class="red">*</span>单位类型：</label>
                <select name="customerLoanCompany.dictCompType" value="${applyInfoEx.customerLoanCompany.dictCompType}" class="select180 required">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_unit_type')}" var="item">
					  <option value="${item.value}"
					    <c:if test="${applyInfoEx.customerLoanCompany.dictCompType==item.value}">
					       selected=true
					    </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
                </td>
				<td><label class="lab"><span class="red">*</span>单位电话：</label>
				   <input type="text" name="customerLoanCompany.compTel" value="${applyInfoEx.customerLoanCompany.compTel}" class="input_text178 isTel required"/>
				 </td>
            </tr>
            
            <tr>
                <td><label class="lab">月收入(元)：</label>
                     <input type="text" name="customerLoanCompany.compSalary" value="<fmt:formatNumber value='${applyInfoEx.customerLoanCompany.compSalary}' pattern="##0.00"/>"  class="input_text178 numCheck positiveNumCheck"/>
                </td>
                <td><label class="lab">每月支薪日：</label>
                     <input type="text" name="customerLoanCompany.compSalaryDay" value="${applyInfoEx.customerLoanCompany.compSalaryDay}" class="input_text178"/>
                </td>
                <td><label class="lab"><span style="color:red">*</span>行业类型：</label>
                <select class="select180 required" name="customerLoanCompany.dictCompIndustry" value="${applyInfoEx.customerLoanCompany.dictCompIndustry}">
                     <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_industry_type')}" var="item">
					  <option value="${item.value}"
					    <c:if test="${applyInfoEx.customerLoanCompany.dictCompIndustry==item.value}">
					       selected=true 
					    </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
                </td>
            </tr>
            <tr>
                <td><label class="lab">入职时间：</label>
                <input id="d4311" name="customerLoanCompany.compEntryDay" value="<fmt:formatDate value='${applyInfoEx.customerLoanCompany.compEntryDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                   onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
                </td> 
				<td><label class="lab">职务：</label>
				  <select id="compPost" name="customerLoanCompany.compPost" value="${applyInfoEx.customerLoanCompany.compPost}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_job_type')}" var="item">
					  <option value="${item.value}"
					    <c:if test="${applyInfoEx.customerLoanCompany.compPost==item.value}">
					       selected=true 
					    </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
	    		</td> 
				<td><label class="lab"><span style="color:red">*</span>累积工作年限：</label>
				    <input type="text" name="customerLoanCompany.compWorkExperience"  value="<fmt:formatNumber value='${applyInfoEx.customerLoanCompany.compWorkExperience}' pattern="0.0" />" class="input_text178"/>
				</td> 
            </tr>
        <c:if test="${workItem.bv.oneedition==1}"> 
  <!-- 法人信息  开始 -->          
           <tr>
                <td><label class="lab">法人姓名：</label>
                     <input type="text" id="comLegalMan" name="customerLoanCompany.comLegalMan" value="${applyInfoEx.customerLoanCompany.comLegalMan}" class="input_text178 comLegalMan"/>
                </td>
                
                <td><label class="lab">法人身份证号：</label>
                     <input type="text" id="comLegalManNum" name="customerLoanCompany.comLegalManNum"  onblur="javascript:launch.certifacte(this.value,this);"  value="${applyInfoEx.customerLoanCompany.comLegalManNum}" class="input_text178 comLegalManNum coboCertCheckCopmany"/>
                </td>
                
                <td><label class="lab">法人手机号：</label>
                     <input type="text"  id="comLegalManMoblie" name="customerLoanCompany.comLegalManMoblie" value="${applyInfoEx.customerLoanCompany.comLegalManMoblie}" class="input_text178 comLegalManMoblie isMobile"/>
                </td>
            </tr>
            
            <tr>
                 <td><label class="lab">企业邮箱：</label>
                     <input type="text" id="comEmail" name="customerLoanCompany.comEmail" value="${applyInfoEx.customerLoanCompany.comEmail}" class="input_text178 comLegalManComEmail email"/>
                </td>
                
           </tr>
  <!-- 法人信息结束  -->    
        </c:if>       
            <tr>
                <td colspan="3" style="text-align:left;"><label class="lab"><span class="red">*</span>地址：</label>
                   <select class="select78 mr10 required" id="compProvince" name="customerLoanCompany.compProvince" value="${applyInfoEx.customerLoanCompany.compProvince}">
                      <option value="">请选择</option>
                      <c:forEach var="item" items="${provinceList}" varStatus="status">
		               <option value="${item.areaCode}" 
		                <c:if test="${applyInfoEx.customerLoanCompany.compProvince==item.areaCode}">
		                   selected = true
		                </c:if>
		               >${item.areaName}</option>
	                  </c:forEach>
                   </select>
                   <select class="select78 mr10 required" id="compCity" name="customerLoanCompany.compCity" value="${applyInfoEx.customerLoanCompany.compCity}">
                      <option value="">请选择</option>
                   </select>
                   <select class="select78 mr10 required" id="compArer" name="customerLoanCompany.compArer" value="${applyInfoEx.customerLoanCompany.compArer}">
                      <option value="">请选择</option>
                   </select>
                   <input type="hidden" id="compCityHidden" value="${applyInfoEx.customerLoanCompany.compCity}"/>
                   <input type="hidden" id="compArerHidden" value="${applyInfoEx.customerLoanCompany.compArer}"/>
                   <input type="text" name="customerLoanCompany.compAddress" value="${applyInfoEx.customerLoanCompany.compAddress}" class="input_text178 required" style="width:250px">
                </td>
            </tr>
        </table>
        <div class=" tright mr10 mb5" ><input type="submit" id="companyNextBtn" class="btn btn-primary" value="下一步"/></div>
     </form>
    </div>
</body>
</html>