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
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
  function backCarLoanHis(){
	  location.href="${ctx}/bpm/flowController/openForm?applyId=${param.applyId}&loanCode=${loanCode}&wobNum=${param.wobNum}&dealType=${param.dealType}&token=${param.token}";
  }
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarContract');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendInfo');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowCustomer');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendCompany');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendContact');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toExtendCoborrower');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowBank');">客户开户信息</a></li>
		<input type="button" style="float: right"
			onclick="backCarLoanHis()" class="btn btn-small"
			value="返回合同审核主页">
	</ul>
<c:set var="param" value="${workItem.bv}" />
	<!-- 历史展期div -->
	<div id="div1" class="content control-group ">
		<form id="frameFlowForm" method="post">
			<input type="hidden" name="loanCode" value="${loanCode}" />
			<input type="hidden" name="applyId" value="${param.applyId}" />
			<input type="hidden" name="wobNum" value="${param.wobNum}" />
			<input type="hidden" name="dealType" value="${param.dealType}" />
			<input type="hidden" name="token" value="${param.token}" />
		</form>
        <table class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
           	<tr>
           		<th>合同编号</th>
	           	<th>合同金额(元)</th>
	           	<th>借款期限(天)</th>
	           	<th>合同期限</th>
	           	<th>降额(元)</th>
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
           		</c:choose></td>
           	</tr>
           	</c:forEach>
		</c:if>
		<c:if test="${ carContract==null || fn:length(carContract)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
			</table>
	</div>
	
	
</body>
</html>