<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!-- 历史展期信息-->
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carHistoryExtendAdd.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a>历史展期信息</a></li>
		<c:choose>
			<c:when test="${newLoanCode == null}">
				<li><a>借款信息</a></li>
				<li><a>个人资料</a></li>
				<li><a>工作信息</a></li>
				<li><a>联系人资料</a></li>
				<li><a>共同借款人</a></li>
				<li><a>客户开户信息</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendInfo?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">借款信息</a></li>
				<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowCustomer?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">个人资料</a></li>
				<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendCompany?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">工作信息</a></li>
				<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendContact?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">联系人资料</a></li>
				<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toExtendCoborrower?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">共同借款人</a></li>
				<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowBank?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
	<jsp:include page="frameFlowFormExtend.jsp"></jsp:include>
	<!-- 历史展期div -->
	<div id="div1" class="content control-group">
		<form id="cna">
				<input type="hidden" id="newLoanCodeId" value="${newLoanCode}">
				<input type="hidden" id="loanCodeId" value="${loanCode}">
				<input type="hidden" id="sumCountId" value="${sumCount}">
				<input type="hidden" id="extendCountId" value="${extendCount}">
				<input type="button" class="btn btn-small" id="ExtendAdd" value="增加历史展期"></input>
				<input type="button" class="btn btn-small" id="ExtendDelete" value="删除最后一行"></input>
        <table id="contactAreaExtend" class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
           	<tr>
           		<th>合同编号</th>
	           	<th>合同金额(元)</th>
	           	<th>借款期限(天)</th>
	           	<th>合同期限</th>
	           	<th>降额(元)</th>
           	</tr>
           	<c:forEach items="${carContracts}" var="carCon" varStatus="stat">
           		<tr <c:if test="${carCon.loanApplyAmount == null}"> mark="saveDataMark" </c:if> >
	           		<td class="hidden"><input type="text" mark="id" value="${carCon.id}" /></td>
	           		<td class="hidden"><input type="text" mark="loanCode" value="${carCon.loanCode}" /></td>
	           		<td class="hidden"><input type="text" mark="loanInfoId" value="${carCon.loanInfoId}" /></td>
	           		<td><input type="text" style="width:300px;" mark="contractCode" readonly value="${carCon.contractCode}" class="input_text178"/>-<input type="text" style="width:40px;" readonly mark="numCount" value="${carCon.numCount}"></td>
	           		<td><input type="text" id="lastValStr" mark="contractAmount" name="contractAmount" class="input_text178 number"	           			<c:choose>
	           				<c:when test="${carCon.loanApplyAmount == null}">
	           					onBlur="changeTrDerate(this);"
	           				</c:when>
	           				<c:otherwise>
	           					readonly
	           				</c:otherwise>
	           			</c:choose>
	           			value="<fmt:formatNumber value="${carCon.contractAmount}" pattern="##0.00"/>" maxlength="8"/>
	           		</td>
	           		<td>
	           			<c:choose>
		           			<c:when test="${carCon.loanApplyAmount == null && stat.index == 0}">
		           				<select name="contractMonths" mark="contractMonths" style="width:80px;" class="required" id="carMonthChange">
				           			<option value="">请选择</option>
				           			<option value="30" <c:if test="${carCon.contractMonths == '30'}">selected</c:if> >30</option>
				           			<option value="90" <c:if test="${carCon.contractMonths == '90'}">selected</c:if> >90</option>
				           		</select>
		           			</c:when>
		           			<c:otherwise>
		           				<label>${carCon.contractMonths }<input type="hidden" mark="contractMonths" value="${carCon.contractMonths }"/></label>
		           			</c:otherwise>
		           		</c:choose>
	           		</td>
	           		<td><input type="text" name="contractFactDay" mark="contractFactDay" 
		           		<c:choose>
		           			<c:when test="${carCon.loanApplyAmount == null && stat.index == 0}">
			           			onClick="WdatePicker({skin:'whyGreen'})" id="carFactDayChange"
		           			</c:when>
		           			<c:otherwise>
		           				readonly
		           			</c:otherwise>
		           		</c:choose>
	           			value="<fmt:formatDate value='${carCon.contractFactDay}' pattern="yyyy-MM-dd"/>" 
	           			class="Wdate input_text178" size="10" style="cursor: pointer" />-
	           		<input type="text" readonly name="contractEndDay" mark="contractEndDay"
	           			value="<fmt:formatDate value='${carCon.contractEndDay}'   pattern="yyyy-MM-dd"/>"
	           			class="Wdate input_text178" size="10" style="cursor: pointer" /></td>
	           		<td><input type="text" name="derate" mark="derate" readonly value="<fmt:formatNumber value="${carCon.derate == null ? 0 : carCon.derate }" pattern="##0.00" />"/></td>
	           	</tr>
           	</c:forEach>
			</table>
			<div class="tright mr10 mb5">
				<input type="button" id="extendNextBtn" class="btn btn-primary" value="下一步"/>
			</div>
		</form>
	</div>
	
	<div class="hide">
			<table id="myExtendCopy" class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
           	<tr mark="saveDataMark">
				<td class="hidden"><input type="text" mark="id"/></td>
				<td class="hidden"><input type="text" mark="loanCode"/></td>
	           	<td class="hidden"><input type="text" mark="loanInfoId"/></td>
           		<td><input type="text" style="width:300px;" mark="contractCode" readonly name="contractCode" class="input_text178 required" value="${contractCode }"/>-<input type="text" style="width:40px;" readonly name="numCount" mark="numCount"></td>
           		<td><input type="text" mark="contractAmount" name="contractAmount" maxlength="8" class="input_text178 required number" onBlur="changeTrDerate(this);"/>
           		</td>
           		<td><label>30<input type="hidden" mark="contractMonths" value="30"/></label></td>
           		<td><input type="text" mark="contractFactDay" name="contractFactDay" class="Wdate input_text178 required" size="10"
						readonly style="cursor: pointer" />-<input type="text" mark="contractEndDay" name="contractEndDay" 
						class="Wdate input_text178 required" size="10" readonly style="cursor: pointer" /></td>
           		<td><input type="text" class="required" mark="derate" name="derate" readonly value="<fmt:formatNumber value="${carCon.derate == null ? 0 : carCon.derate }" pattern="##0.00" />"/></td>
           	</tr>
			</table>
	</div>
	<div class="hide">
			<table id="myExtendCopyFirst" class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
           	<tr mark="saveDataMark">
           		<td class="hidden"><input type="text" mark="id"/></td>
				<td class="hidden"><input type="text" mark="loanCode"/></td>
	           	<td class="hidden"><input type="text" mark="loanInfoId"/></td>
           		<td><input type="text" style="width:300px;" mark="contractCode" readonly name="contractCode" class="input_text178 required" value="${contractCode }"/>-<input type="text" style="width:40px;" readonly name="numCount" mark="numCount"></td>
           		<td><input type="text" mark="contractAmount" name="contractAmount" maxlength="8" class="input_text178 required number" onBlur="changeTrDerate(this);"/>
           		</td>
           		<td><select name="contractMonths" mark="contractMonths" style="width:80px;" class="required" id="carMonthChange">
           			<option value="">请选择</option>
           			<option value="30">30</option>
           			<option value="90">90</option>
           		</select></td>
           		<td><input id="carFactDayChange" type="text" mark="contractFactDay" name="contractFactDay" class="Wdate input_text178 required" size="10" readonly
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer;background-color:white;" />-<input type="text" mark="contractEndDay" name="contractEndDay" 
						class="Wdate input_text178 required" size="10" readonly style="cursor: pointer" /></td>
           		<td><input type="text" mark="derate" class="required" name="derate" readonly value="<fmt:formatNumber value="${carCon.derate == null ? 0 : carCon.derate }" pattern="##0.00" />"/></td>
           	</tr>
			</table>
	</div>
</body>
</html>