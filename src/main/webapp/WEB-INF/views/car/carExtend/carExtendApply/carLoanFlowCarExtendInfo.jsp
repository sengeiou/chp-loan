<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!-- 历史展期信息-->
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewExtendMeetApply.js"></script>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarContract?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendInfo?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowCustomer?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendCompany?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendContact?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toExtendCoborrower?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowBank?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
</ul>
	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
	<!-- 历史展期div -->
	<div id="div1" class="content control-group ">
		<form id="customerForm" action="${ctx}/car/carExtendApply/toCarExtendInfo" method="post">
				<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
				<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
				<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
				<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
				<input type="hidden" value="${workItem.token}" name="token"></input>
				<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
				<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
				<input type="hidden" name="loanCode" value="${loanCode}">
				<input type="hidden" value="${newLoanCode}" name="newLoanCode">
        <table class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
           	<tr>
           		<th>合同编号</th>
	           	<th>合同金额（元）</th>
	           	<th>借款期限（天）</th>
	           	<th>合同期限</th>
	           	<th>降额（元）</th>
           	</tr>
        <c:if test="${carContract!=null && fn:length(carContract)>0}">
           	<c:forEach items="${carContract}" var="ct"> 
           	<tr>
           		<td>${ct.contractCode}</td>
           		<td><fmt:formatNumber value="${ct.contractAmount}" pattern="0.00" /></td>
           		<td>${ct.contractMonths}</td>
           		<td><fmt:formatDate value='${ct.contractFactDay}' pattern="yyyy-MM-dd"/>~<fmt:formatDate value='${ct.contractEndDay}' pattern="yyyy-MM-dd"/></td>
           		<td><c:choose>
           			<c:when test="${ct.derate == null}">
           				0.00
           			</c:when>
           			<c:otherwise>
           				<fmt:formatNumber value="${ct.derate}" pattern="0.00" />
           			</c:otherwise>
           		</c:choose>
           		</td>
           	</tr>
           	</c:forEach>
		</c:if>
		<c:if test="${ carContract==null || fn:length(carContract)==0}">
           <tr><td colspan="18" style="text-align:center;">没有历史展期数据</td></tr>
         </c:if>
			</table>

			<div class="tright mr10 mb5">
				<input type="submit" class="btn btn-primary" id="customerNextBtn"
					value="下一步">
			</div>
		</form>
	</div>
	
	
</body>
</html>