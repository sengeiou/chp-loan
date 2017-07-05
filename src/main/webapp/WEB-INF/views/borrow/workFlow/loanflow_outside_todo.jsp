<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${context}/js/outside/outsidetodo.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<title>外访待办</title>
<script language="javascript">
	$(document).ready(function() {
		var imageUrl = "${imageUrl}";
		outsidetodo.fileUpload(imageUrl);
		outsidetodo.completeVisit();
		outsidetodo.outsidetodovalidate('outvisittodo');
		outsidetodo.giveup();
		outsidetodo.downloadload();
		outsidetodo.reject();
	});
</script>
</head>
<body>
	<div style="display: none;" id="boxy">
		<form id="outvisittodo" action="${ctx}/loan/workFlow/doOutTask">
			<table id="distance" class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<c:if test="${not empty outsideTaskList}">
					<c:forEach var="item" items="${outsideTaskList}" varStatus="status">
						<tr>
							<td>
								<label class="lab">第 ${status.index+1} 次外访距离(km)：</label> 
								${item.itemDistance}
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<tr>
					<td>
						<label class="lab">外访距离(km)：</label> 
						<input type="text" id="itemDistance" name="itemDistance" class="input_text178 required" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">确认距离(km)：</label> 
						<input type="text" id="coitemDistance" name="coitemDistance" class="input_text178" />
					</td>
				</tr>
			</table>
			<table id="rejectRes" class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr valign="top">
					<td>
						<label >拒绝理由：</label>
						<textarea id="remark" name="remark" cols="55" class="textarea_big1" ></textarea>
					</td>
				</tr>
			</table>
			<!-- 工作流队列 和view名 -->
			<input type="hidden" name="applyId" value="${applyId }"> 
			<input type="hidden" name="wobNum" value="${wobNum }">
			<input type="hidden" name="token" value="${token }"> 
			<input type="hidden" name="queue" value="${queue }"> 
			<input type="hidden" name="viewName" value="${viewName }"> 
			<input type="hidden" name="loanCode" id="loanCode" value="${loanCode }">
			<input type="hidden" name="response" id="response" value="${loanCode }">
		</form>
	</div>
	
	<div class="tright pt10 mb10">
		<input id="download" type="button" class="btn btn-small" value="外访清单下载" />
		<%-- <button class="btn btn-small" id="${loanCode }"  onclick="wfHistoryBtn(this.id)">外访明细</button> --%>
		<input id="uploadbtn" type="button" class="btn btn-small" value="上传外访资料" /> 
		<input id="confirmInfo" type="button" class="btn btn-small" value="外访资料确认上传" /> 
		<input id="${bview.loanCustomer.applyId }" class="btn btn-small" onclick="showLoanHis('${applyId }');" type="button" value="历史" /> 
		<input id="giveUpBtn" type="button" class="btn btn-small" value="客户放弃" />
		<input id="rejectBtn" type="button" class="btn btn-small" value="门店拒绝" />
		<input onclick="history.go(-1);" type="button" class="btn btn-small" value="返回" />
	</div>

	<h2 class="f14 pl10">客户信息</h2>
	<div class="box2">
		<c:if test="${ customerList!=null && fn:length(customerList)>0}">
			<c:forEach items="${customerList}" var="item">
				<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">客户编号：</label>${item.customerCode }</td>
						<td><label class="lab">姓名：</label>${item.customerName }</td>
						<td><label class="lab">性别：</label>${item.customerSexLabel}</td>
					</tr>
					<tr>
						<td><label class="lab">证件类型：</label>${item.dictCertTypeLabel}</td>
						<td><label class="lab">证件号码：</label>${item.customerCertNum }</td>
						<td><%-- <label class="lab">出生日期：</label> <fmt:formatDate
								value="${item.customerBirthday }" pattern="yyyy-MM-dd" /> --%></td>
					</tr>
					<tr id="T1" style="display: none;">
						<td><label class="lab">客户类型：</label>${item.dictCustomerLoanTypeLabel}</td>
						<td><label class="lab">学历：</label>${item.dictEducationLabel}</td>
						<td><label class="lab">婚姻状况：</label>${item.dictMarryLabel}</td>
					</tr>
				</table>
			</c:forEach>
		</c:if>
	</div>
	
	<h2 class="f14 pl10 mt20">借款信息</h2>
	<div class="box2">
		<c:if test="${ loanList!=null && fn:length(loanList)>0}">
			<c:forEach items="${loanList}" var="item">
				<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">产品类别：</label>${item.productType }</td>
						<td>
							<label class="lab">申请额度(元)：</label> 
							<fmt:formatNumber value="${item.loanApplyAmount }" pattern="#,##0.00" />
						</td>
						<td><label class="lab">申请期限(月)：</label>${item.loanMonths }</td>
					</tr>
					<tr>
						<td>
							<label class="lab">申请日期：</label> 
							<fmt:formatDate value="${item.loanApplyTime }" pattern="yyyy-MM-dd" />
						</td>
						<c:if test="${item.loanInfoOldOrNewFlag eq 0}">
							<td><label class="lab">借款用途： </label>${item.dictLoanUserLabel}</td>
							<td><label class="lab">具体用途：</label>${item.realyUse }</td>
						</c:if>
						<c:if test="${item.loanInfoOldOrNewFlag eq 1}">
							<td><label class="lab">主要借款用途： </label>${item.dictLoanUserLabel}</td>
							<td><label class="lab">具体用途：</label>${item.dictLoanUseNewOther }</td>
						</c:if>
					</tr>
					<tr id="T1" style="display: none;">
						<td><label class="lab">是否加急：</label>${item.loanUrgentFlagLabel}</td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</c:forEach>
		</c:if>
	</div>

	<h2 class="f14 pl10 mt20">
		<c:if test="${loanInfoOldOrNewFlag eq 0}">
			共同借款人
		</c:if>
		<c:if test="${loanInfoOldOrNewFlag eq 1}">
			自然人保证人
		</c:if>
	</h2>
	<div class="box2">
		<c:if test="${ cobList!=null && fn:length(cobList)>0}">
			<c:forEach items="${cobList}" var="item">
				<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">姓名：</label>${item.coboName }</td>
						<td><label class="lab">性别：</label>${item.coboSexName}</td>
						<td><label class="lab">常用手机号：</label>${item.coboMobile }</td>
					</tr>
					<tr>
						<td><label class="lab">证件类型：</label>${item.dictCertTypeName}</td>
						<td><label class="lab">证件号码：</label>${item.coboCertNum }</td>
						<td></td>
					</tr>
				</table>
			</c:forEach>
		</c:if>
		<c:if test="${ cobList==null || fn:length(cobList)==0}">
			<table>
				<tr>
					<td colspan="3" align="center" style="text-align: center;">
						<c:if test="${loanInfoOldOrNewFlag eq 0}">
							<h2 class="f14 pl10 mt20">没有共借人信息</h2>
						</c:if>
						<c:if test="${loanInfoOldOrNewFlag eq 1}">
							<h2 class="f14 pl10 mt20">没有自然人保证人信息</h2>
						</c:if>
					</td>
				</tr>
			</table>
		</c:if>
	</div>
</body>
</html>