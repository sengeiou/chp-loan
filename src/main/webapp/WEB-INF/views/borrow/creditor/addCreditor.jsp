<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>债权录入</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script src="${context}/js/creditor/addPage.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
</head>
<body>
<form:form id="inputForm" name="inputForm" modelAttribute="consult"	action="${ctx}/borrow/creditor/save" method="post"
			class="form-horizontal">
 <h2 class=" f14 pl10 mt20">基本信息</h2>
  <div class="box2 ">
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	  <tr>
		<td style="width:50%">
		  <label class="lab"><span style="color: #ff0000">*</span>借款人姓名：</label>
		  <input id="loanName" name="loanName" type="text" class="input_text178"  maxlength="10" onkeyup="this.value=this.value.replace(/[^\u4e00-\u9fa5]/g,'')"/>
		</td>
		<td style="width:50%">
		  <label class="lab">证件类型：</label>
		  <select class="required select180" id="cerType" name="cerType">
		  	<c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
						<option value="${item.value}" <c:if test="${item.value == 0}">selected</c:if>>${item.label}</option>
					</c:forEach>
		  </select>
		</td>
	   </tr>
	   <tr>
		<td>
		  <label class="lab"><span style="color: #ff0000">*</span>证件号码：</label>
		  <input class="required input_text178" type="text" id="cerNum" name="cerNum" onblur="getName(this);" maxlength="20">
		</td>
	 </tr>
 </table>
</div>
   <h2 class=" f14 pl10 mt20">贷款相关信息</h2>
  <div class="box2 ">
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	 <tr>
	   <td style="width:50%">
	      <label class="lab"><span style="color: #ff0000">*</span>借款编号：</label>
	      <input class="required input_text178" type="text" id="loanCode" name="loanCode" maxlength="16">
		</td>
		<td style="width:50%">
		   <label class="lab">借款人借款用途：</label>
		   <select class="required select180" id="loanPurpose" name="loanPurpose">
		   	<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
						<option value="${item.value}" <c:if test="${item.value == 1}">selected</c:if>>${item.label}</option>
					</c:forEach>
		   </select>
        </td>
      </tr>
      <tr>
	   <td>
	      <label class="lab">债权人：</label>
	      <select class="required select180" id="creditor" name="creditor">
	      	<option value="0">夏靖</option>
	      	<!-- <option value="1">魏永华</option> -->
	      </select>
		</td>
		<td>
		   <label class="lab"><span style="color: #ff0000">*</span>初始借款金额：</label>
		   <input class="required input_text178" type="text" id="initMoney" name="initMoney" maxlength="12">
        </td>
      </tr>
      <tr>
	   <td>
	      <label class="lab"><span style="color: #ff0000">*</span>月账户管理费：</label>
	      <input class="required input_text178" type="text" id="manageMoney" name="manageMoney" value="0" maxlength="12">
		</td>
		<td>
		   <label class="lab"><span style="color: #ff0000">*</span>初始借款时间：</label>
		   <input id="initLoanDate" name="initLoanDate" value="<fmt:formatDate value='${cpcn.createTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" readonly>
        </td>
      </tr>
      <td>
	      <label class="lab"><span style="color: #ff0000">*</span>还款期限：</label>
	      <input class="required input_text178" type="text" maxlength="3" id="repaymentDate" name="repaymentDate" style="width:75px;" onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" onblur="getLastsMonths();" >
	      &nbsp;&nbsp;
	      <select id="repaymentType" name="repaymentType" class="required select70" >
	      	<option value="M">月</option>
	      	<!-- <option value="1">年</option> -->
	      </select>
		</td>
		<td>
		   <label class="lab"><span style="color: #ff0000">*</span>起始还款日期：</label>
		   <input id="startDate" type="text" name="startDate" value="<fmt:formatDate value='${cpcn.createTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" onblur="getLastsMonths();" readonly>
        </td>
      </tr>
      <tr>
	   <td>
	      <label class="lab"><span style="color: #ff0000">*</span>借款利率%：</label>
	      <input class="required input_text178" type="text" id="interestRate" name="interestRate" maxlength="5">
		</td>
		<td>
		   <label class="lab">借款人状态：</label>
		   <select class="required select180" id="loanStatus" name="loanStatus">
		   	<option value="0">还款中</option>
		   	<option value="1">待还款</option>
		   </select>
        </td>
      </tr>
      <tr>
	   <td>
	      <label class="lab">产品类型：</label>
	      <select class="required select180" id="type" name="type" onchange="getOccupation(this);">
	      	<option value="">请选择</option>
	      	<c:forEach items="${typeList}" var="item">
	      		<c:if test="${item.value ne 'A007' and item.value ne 'A008' }">
						<option value="${item.value}">${item.label}</option>
				</c:if>
			</c:forEach>
	      	<!-- <option value=""0>质押</option>
	      	<option value="1">房易借</option>
	      	<option value="2">老板借</option>
	      	<option value="3">精英借</option>
	      	<option value="4">薪水借</option>
	      	<option value="5">抵押物</option>
	      	<option value="6">楼易借</option>
	      	<option value="7">小微企业借</option>
	      	<option value="8">大企业借</option> -->
	      </select>
		</td>
		<td>
		   <label class="lab">借款人职业情况：</label>
		   <input class="required input_text178" type="text" id="occupationTmp" name="occupationTmp" readonly>
		   <input type="hidden" id="occupation" name="occupation">
		   <!-- <select class="required input_text178" id="occupation" name="occupation" >
		   	<option value="">请选择</option>
		   </select> -->
        </td>
      </tr>
      <tr>
	   <td>
	      <label class="lab">剩余期数：</label>
	      <input class="required input_text178" type="text" id="surplusDate" name="surplusDate" readonly ><!-- onkeyup="this.value=this.value.replace(/[^0-9]/g,'')" -->
		</td>

      </tr>
 </table>
</div>


<div class="tright mt10 mr34" ><input type="button" id="submitConsultBtn" class="btn btn-primary" value="提交" onclick="sub();"></input>
<a type="button" id="submitConsultBtn" class="btn btn-primary" href="${ctx}/borrow/creditor/getCreditorlist">取消</a></div>
</form:form>
</body>
</html>