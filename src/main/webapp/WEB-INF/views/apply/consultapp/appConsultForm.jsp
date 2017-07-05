<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript"
	src="${context}/js/consultapp/consultValidateApp.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				 $('#submitConsultBtn').bind('click',function(){
					if($('#flag').val()=='0' || $('#flag').val()=='2'){
						art.dialog.alert($('#message').val());
					  	return false;
					}else{
						consult.datavalidate();
					}
				}); 
			});
	
			var msg = "${message}";
			if (msg != "" && msg != null) {
				art.dialog.alert(msg);
			};
			
</script>
</head>
<body>
 <form id="consultForm" action="${ctx}/apply/consultapp/saveConsult" method="post"
			class="form-horizontal">
 <h2 class=" f14 pl10 mt20">基本信息</h2>
  <div class="box2 ">
  
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	  <tr>
		<td style="width:32%">
		  <input type="hidden" name="id" value="${appCustomerInfo.id}"/>
		  <input type="hidden" name="birthday" value="${appCustomerInfo.birthday}"/>
		  <input type="hidden" name="communicateDate" value="<fmt:formatDate value='${appCustomerInfo.communicateDate}' />"/>
		  <input type="hidden" name="storeId" value="${appCustomerInfo.storeId}"/>
		  <input type="hidden" name="loanUse" value="${appCustomerInfo.loanUse }">
		  <input type="hidden" name="industry" value="${appCustomerInfo.industry }">
		  <input type="hidden" name="teamOrgId" value="${appCustomerInfo.teamOrgId}">
		  <input type="hidden" name="sex" value="${appCustomerInfo.sex}">
		  <label class="lab"><span style="color: #ff0000">*</span>客户姓名：</label>
		  <input name="customerName" id="customerName" value="${appCustomerInfo.customerName}" class="required input_text178" readonly="readonly"/>
		  <input type="hidden" id="message"/>
		</td>
		<td style="width:32%">
		  <label class="lab"><span style="color: #ff0000">*</span>手机号码：</label>
		  <input name="mobilephone" value="${appCustomerInfo.mobilephone}" class="required input_text178" id="mobilephone" readonly="readonly"/>
		</td>
		<td style="width:32%">
		  <label class="lab">固定电话：</label>
		  <span>
		  <input name="areaNo" id="areaNo" class="input_text178" />-
		  <input name="telephoneNo" id="telephoneNo" class="input_text178 areaNoCheck telNoCheck" />
		  </span>
		</td>
	   </tr>
	   <tr>
		<td style="width:32%">
		  <label class="lab"><span style="color: #ff0000">*</span>证件类型：</label>
		  <select id="certType" name="certType" readonly="readonly" disabled="true" class="required select180">
			<option value="0">身份证</option>
		   </select>
		</td>
		<td style="width:32%">
		   <label class="lab"><span style="color: #ff0000">*</span>证件号码：</label>
		   <input name="certNum" value="${appCustomerInfo.certNum}" class="required input_text178" id="certNum" readonly="readonly" />
		   <span id="blackTip" style="color: red;"></span>
		</td>
		<td style="width:32%">
		    <label class="lab"><span style="color: #ff0000">*</span>证件有效期：</label>
		     <input id="idStartDate" name="idStartDate" value="${appCustomerInfo.idStartDate}" type="text" class="input_text178" size="10" readonly="readonly"/>
		      - 
		      <c:choose>
                 <c:when test="${appCustomerInfo.idEndDate==''|| appCustomerInfo.idEndDate==null}">
                   	<label><input type="checkbox" disabled="disabled" name="longTerm" id="longTerm" value="1" checked="checked" readonly="readonly"/>长期</label>
                 </c:when>
                 <c:otherwise>
                     <input id="idEndDate" name="idEndDate" value="${appCustomerInfo.idEndDate}" type="text" class="input_text178" size="10" readonly="readonly"/>
                 </c:otherwise>
               </c:choose>
	     </td>
	 </tr>
	 <tr>
	   <td rowspan="2">
	    <label class="lab"><span style="color: #ff0000">*</span>行业类型：</label>
	      <select id="industryName" name="industryName" class="required select180" disabled="true" readonly="readonly">
			<option value="${appCustomerInfo.industryName}">${appCustomerInfo.industryName}</option>
		   </select>
		</td>
		<td style="width:32%">
		   <label class="lab"><span style="color: #ff0000">*</span>性别：</label>
		   <c:if test="${appCustomerInfo.sex=='0'}">
		   <input type="radio" id="man" name="sex" checked="true" disabled="true"/>男
		   <input type="radio" id="women" name="sex" disabled="true"/>女
		   </c:if>
		   <c:if test="${appCustomerInfo.sex=='1'}">
		   <input type="radio" id="man" name="sex" disabled="true"/>男
		   <input type="radio" id="women" name="sex" checked="true" disabled="true"/>女
		   </c:if>
        </td>
      </tr>
 </table>
</div>
   <h2 class=" f14 pl10 mt20">信借信息</h2>
  <div class="box2 ">
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	 <tr>
	   <td style="width:32%">
	    <label class="lab"><span style="color: #ff0000">*</span>团队经理：</label>
	    <input name="teamEmpName" value="${appCustomerInfo.teamEmpName}" class="input_text178" readonly="readonly"/>
		<input type="hidden" name="teamEmpcode" value="${appCustomerInfo.teamEmpcode}"/>
	   </td>
	   <td style="width:32%" >
	    <label class="lab">客户经理：</label>
	    <input name="financingName" value="${appCustomerInfo.financingName}" readonly="readonly" class="input_text178" />
		<input type="hidden" name="financingId" value="${appCustomerInfo.financingId}"/>
	    </td>
		<td style="width:32%">
		  <label class="lab"><span style="color: #ff0000">*</span>信借类型：</label>
		  <input type="radio" name="loanType" value="1" checked="true" />个人信用借款
		</td>
	</tr>
	<tr>
	  <td style="width:32%">
	    <label class="lab"><span style="color: #ff0000">*</span>借款用途：</label>
	    <select id="loanUseName" name="loanUseName" class="required select180" disabled="true" readonly="readonly">
			<option value = "${appCustomerInfo.loanUseName}">${appCustomerInfo.loanUseName}</option>
		</select>
      </td>
	  <td colspan="2">
	     <label class="lab">计划借款金额：</label>
	    <input name="loanPosition" value="${appCustomerInfo.loanPosition}" id="loanPosition" class="input_text178" readonly="readonly"/>
	   </td>
	</tr>
 </table>
</div>
 <h2 class=" f14 pl10 mt20">沟通记录及下一步操作</h2>
 <div class="box2 ">
   <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	 <tr>
	  <td style="width:32%">
	    <label class="lab">沟通记录：</label>
	    <textarea  name="loanRecord" class="textarea_refuse" maxlength="490" rows="10" cols="50"></textarea>
	  </td>
	  <td style="width:32%">
	    <label class="lab"><span style="color: #ff0000">*</span>下一步：</label>
	    <select id="loanStatus" name="loanStatus" class="required select180">
		  <option value="">请选择</option>
		  <c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
		    <c:if test="${item.label != '已进件' && item.label != '待申请'}">
		      <option value="${item.value}">${item.label}</option>
		     </c:if>
		  </c:forEach>
		</select>
	  </td>
	  <td style="width:32%"></td>
   </tr>
 </table>
</div>
<div class="tright mt10 mr34" ><input type="submit" id="submitConsultBtn" class="btn btn-primary" value="提交"></input></div>
</form>
</body>
</html>