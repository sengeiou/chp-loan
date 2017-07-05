<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html ><head>
<title>个人征信报告简版</title>
<script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
<script type="text/javascript" src="${context}/js/validateCredit.js"></script>
<script type="text/javascript" src="${context}/js/credit/creditReportSimpleForm.js"></script>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">


</script>
</head>
<body>
	<div class="pt5 pr30 mb5">
		<div class="tright">
			<button class="btn btn-small" onclick="initEnterpriseCreditWebLoad();" >征信登录</button>
			<button class="btn btn-small"  onclick="initHtmlUrl();" >静态网页版</button>
			<!-- <button id="backBtn" class="btn btn-small" >返回</button> -->
			<button onclick="history.go(-1);" class="btn btn-small" >返回</button>
			<input type="hidden" id="saveLock" value="0">
		</div>
	</div>
	<div>
	<form id="creditReportSimpleForm">
		<input type="hidden" id="creditReportSimpleId" value="${creditReportSimple.id}" />
		<input type="hidden" id="loanCode" value="${loanCode}" />
		<input type="hidden" id="rId" value="${rId}" />
		<input type="hidden" id="dictCustomerType" value="${dictCustomerType}"/>
		<input type="hidden" id="applyCertNum" value="${applyCertNum}"/>
		<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
			<tr>
				<td >
					<b class="pl10">基本信息(数据来源：<c:if test="${creditReportSimple.creditSource eq '1'}">非</c:if>手工录入) &nbsp;</b>

				</td>
			</tr>
			<tr>
				<td >
					<lable class="lab"><font color="red">*</font>查询时间：</lable>
					<input type="text" id="queryTime" class="select75 Wdate required" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" value='<fmt:formatDate value="${creditReportSimple.queryTime}" pattern="yyyy-MM-dd"/>' />
				</td>
				
				<td >
					<lable class="lab"><font color="red">*</font>姓名：</lable>
					<input type="text" maxlength="20" id="name" class="input_text178 required" value="${creditReportSimple.name}" />
				</td>
				
				
			</tr>
			<tr>
				<td >
					<lable class="lab"><font color="red">*</font>身份证号：</lable>
					<input type="text" id="certNo" readonly="readonly" class="required card" value="${applyCertNum}"/>
				</td>
				<td >
					<lable class="lab"><font color="red">*</font>婚姻状况：</lable>
					<select name="marryStatus" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_marriage')}" var="item" >
							<option <c:if test="${creditReportSimple.marryStatus == item.value}">selected</c:if> value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="button" value="保存" class="btn btn-small" onClick="saveQueryTime();"/>
				</td>
				<%-- <td >
					<lable class="lab"><font color="red">*</font>最高学历：</lable>
					<select name="highestEducation" class="select180 ">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_degree')}" var="item" >
							<option <c:if test="${creditReportSimple.highestEducation == item.value}">selected</c:if> value="${item.value}">${item.label}</option>
						</c:forEach>
					</select>
				</td> --%>
				
			</tr>
		</table>
	</form>
	</div>
	<br>
	<div>
		<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
			<tr>
				<td style="text-align:left" >
					<b class="pl10">保证人代偿信息</b>
				</td>
				
				<td style="text-align:right" >
					<input type="button" class="btn btn-small" value="新增保证人代偿信息" onclick="addTr('payback');"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-small" value="保存保证人代偿信息" onclick="saveTr('payback',this);"/>
				</td>
			</tr>
		</table>
	</div>
	
	<%@ include file="/WEB-INF/views/credit/creditReportSimplePaybackInfoForm.jsp"%>
	<br>
	<br>
	<div>
		<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
			<tr>
				<td style="text-align:left" >
					<b class="pl10">信用卡明细信息</b>
				</td>
				
				<td style="text-align:right" >
					<input type="button" class="btn btn-small" value="新增信用卡" onclick="addTr('card');"/>
					<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-small" value="保存信用卡" onclick="saveTr('card');"/> -->
				</td>
			</tr>
		</table>
	</div>
	
	<%@ include file="/WEB-INF/views/credit/creditReportSimpleCardInfoForm.jsp"%>
	<br>
	<br>
	<div>
		<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
			<tr>
				<td style="text-align:left" >
					<b class="pl10">贷款明细信息</b>
				</td>
				
				<td style="text-align:right" >
					<input type="button" class="btn btn-small" value="新增贷款" onclick="addTr('loan');" />
					<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-small" value="保存贷款" onclick="saveTr('loan');" /> -->
				</td>
			</tr>
		</table>
	</div>

	<%@ include file="/WEB-INF/views/credit/creditReportSimpleLoanInfoForm.jsp"%>
     <br>
	<br>
	<div>
		<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
			<tr>
				<td style="text-align:left" >
					<b class="pl10">查询信息</b>
				</td>
				
				<td style="text-align:right" >
					<input type="button" class="btn btn-small" value="新增查询记录" onclick="addTr('query');"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" class="btn btn-small" value="保存查询记录" onclick="saveTr('query',this);"/>
				</td>
			</tr>
		</table>
	</div>
	
	<%@ include file="/WEB-INF/views/credit/creditReportSimpleQueryInfoForm.jsp"%>
	
</body>
</html>
