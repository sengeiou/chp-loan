<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/payback/common.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript"
	src="${context}/js/consult/consultOcrList.js"></script>
<meta name="decorator" content="default" />
</head>
<body>
 <form:form id="inputForm"  action="${ctx}/apply/consultOcr/saveConsult" method="post"
			class="form-horizontal">
 <h2 class="f14 pl10 mt20">基本信息</h2>
  <div class="box2 ">
  
	<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	  <tr>
		<td style="width:32%">
		  <label class="lab"><span style="color: #ff0000">*</span>客户姓名：</label>
		  <input type="text" value="${appConsult.customerName}"  name="customerName" id="customerName" class="input_text178"  />
		  <input type="hidden" name="communicateDate" value="${appConsult.communicateDate }"/>
		  <input type="hidden" name="teamOrgId" value="${appConsult.teamOrgId }"/>
		  <input type="hidden" name="isPhone" value="${appConsult.isPhone }"/>
		  <input type="hidden" name="phoneSource" value="${appConsult.phoneSource }"/>
		  <input type="hidden" name="storeId" value="${appConsult.storeId }"/>
		  <input type="hidden" name="id" value="${appConsult.id }"/>
		  <input type="hidden" name="industry" value="${appConsult.industry }">
		  <input type="hidden" name="loanUse" value="${appConsult.loanUse }" />
		  <input type="hidden" id="flag" />
		  <input type="hidden" id="message"/>
		</td>
		<td style="width:32%">
		  <label class="lab"><span style="color: #ff0000">*</span>手机号码：</label>
		  <input type="text" value="${appConsult.mobilephone }" class="required input_text178" name="mobilephone"  id="mobilephone"/>
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
		  <select id="cardType" name="certType" readonly="readonly" class="required select180">
		    <option value="0">身份证</option>
		   </select>
		</td>
		<td style="width:32%">
		   <label class="lab"><span style="color: #ff0000">*</span>证件号码：</label>
		   <input type="text" value="${appConsult.certNum }" class="required input_text178" id="certNum" name="certNum"/>
		</td>
		<td style="width:32%">
		    <label class="lab"><span style="color: #ff0000">*</span>证件有效期：</label>
		      <input id="idStartDay" name="idStartDate" type="text" readonly="readonly" maxlength="40"
		             class="input-mini Wdate" value="${appConsult.idStartDate }" onclick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" type="text" class="input_text178" size="10"/>
		      -
		       <c:choose>
                 <c:when test="${appConsult.idEndDate==''|| appConsult.idEndDate==null}">
                   	<label><input type="checkbox" disabled="disabled" name="idEndDate" id="longTerm" value="" checked="checked" readonly="readonly"/>长期</label>
                 </c:when>
                 <c:otherwise>
                     <input id="idEndDay" name="idEndDate" type="text" readonly="readonly" maxlength="40" class="input-mini Wdate" value="${appConsult.idEndDate}"
							 onclick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" type="text" class="input_text178" size="10" />
                 </c:otherwise>
               </c:choose>
	     </td>
	 </tr>
	 <tr>
	   <td rowspan="2">
	    <label class="lab"><span style="color: #ff0000">*</span>行业类型：</label>
	      <select id="industryName" class="required select180" >
	      <c:forEach items="${fns:getDictList('jk_industry_type')}" var="item">
		    <option value="${item.value}" <c:if test="${appConsult.industry == item.value }">selected</c:if>>${item.label}</option>
		  </c:forEach>
		   </select>
		</td>
		<td style="width:32%">
		   <label class="lab"><span style="color: #ff0000">*</span>性别：</label>
		    <c:if test="${appConsult.sex == 0}">
		   <input type="radio" id="man" name="sex" value='0' checked="true" />男
		   <input type="radio" id="women" name="sex" value='1' disabled="true"/>女
		   </c:if>
		   <c:if test="${appConsult.sex == 1}">
		   <input type="radio" id="man" name="sex" value='0' disabled="true"/>男
		   <input type="radio" id="women" name="sex" value='1' checked="true" />女
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
	     <input name="teamName" value="${appConsult.teamName}" class="input_text178" readonly="readonly"/>
		<input type="hidden" name="teamEmpcode" value="${appConsult.teamEmpcode}"/>
	   </td>
	   <td style="width:32%" >
	    <label class="lab">客户经理：</label>
	     <input name="financingName" value="${appConsult.financingName}" readonly="readonly" class="input_text178" />
		<input type="hidden" name="financingId" value="${appConsult.financingId}"/>
	    </td>
		<td style="width:32%">
		 <label class="lab"><span style="color: #ff0000">*</span>信借类型：</label>
		  <input type="radio" name="loanType" value="1" checked="true" />个人信用借款
		</td>
	</tr>
	<tr>
	  <td style="width:32%">
	    <label class="lab"><span style="color: #ff0000">*</span>借款用途：</label>
	    <select id="industryName" class="required select180" >
	    <c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
		    <option value="${item.value}" <c:if test="${appConsult.loanUse == item.value }">selected</c:if>>${item.label}</option>
		  </c:forEach>
		  </select>
      </td>
	  <td colspan="2">
	     <label class="lab">计划借款金额：</label>
	      <input name="loanPosition" value="<fmt:formatNumber value='${appConsult.loanPosition }' pattern="0.00" />" 
	      id="loanPosition" class="input_text178"/>
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
		  <c:if test="${not (item.value eq 3 || item.value eq 5) }">
		    <option value="${item.value}" <c:if test="${appConsult.loanStatus == item.value }">selected</c:if>>${item.label}</option>
		  </c:if>
		  </c:forEach>
		</select>
	  </td>
	  <td style="width:32%"></td>
   </tr>
 </table>
</div>
<div class="tright mt10 mr34">
   <input type="submit" id="submitConsultBtn" class="btn btn-primary" value="提交"/>
   <button class="btn btn-primary" type="button"
             onclick="JavaScript:window.location='${ctx}/apply/consultOcr/list'" >返回</button>
</div>
</form:form>
</body>
</html>