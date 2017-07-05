<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/outside/outside.js?version=2" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>外访待办</title>
<script language="javascript">
	$(document).ready(function() {
		loan.getstorelsitByDepartmentId("storeName", "storeOrgId", null); //门店查询项
		outside.initMethod();
		//隐藏/显示绑定
	    $('#showMore').bind('click', function() {
			show();
		});
		
		var msg = "${message}";
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	 
		/* 外访清单下载测试用 
		$("#download").bind('click',
			function() {
				art.dialog.tips("文件下载中...");
				window.location.href = ctx+ "/loan/workFlow/downLoadVisitTask?loanCode=JK2016061800000012";
		}); */
	});

	function callback_checkOutPeople(userCode) {
		var url = ctx + "/loan/workFlow/allocationOutTask?applyIds="+ getCheckedCheckBoxValue('taskId') + "&outSideUserCode="+ userCode;
		window.location.href = url;
	}
	function checkBoxAll(){
		var checked = false;
		if ($('#checkBoxAll').attr('checked') == 'checked'|| $('#checkBoxAll').attr('checked')) 
		{
			checked = true;
		}
		$("input[name='taskId']").each(function() {
			$(this).attr('checked', checked);
		});
	}
</script>
</head>
<body>
	<div class="control-group">
		<form id="outvisitSearch" action="${ctx}/loan/workFlow/getTaskItems" method="post">
			<!-- 工作流队列 和view名 -->
			<input type="hidden" name="queue" value="${queue }">
			<input type="hidden" name="viewName" value="${viewName }">
			<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">借款编号：</label>
						<form:input id="loanCode" path="queryParam.loanCode" class="input_text178" />
					</td>
					<td>
					    <label class="lab">客户姓名：</label> 
					    <form:input id="custmername" path="queryParam.customerName" class="input_text178" />
					</td>
					<td>
						<label class="lab">门店：</label>
						<form:input id="storeName" path="queryParam.storeName" class="input_text178" readonly="true"/>
						<i id="selectStoresBtnByDepartmentId" class="icon-search" style="cursor: pointer;"></i>
						<form:input type="hidden" id="storeOrgId" path="queryParam.storeOrgId"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">身份证号：</label> 
						<form:input path="queryParam.identityCode" class="input_text178" />
					</td>
					<td>
						<label class="lab">产品：</label> 
						<form:select class="select180" path="queryParam.applyProductCode">
							<form:option value="">请选择</form:option>
							<form:options items="${prdList}" itemValue="productCode" itemLabel="productName" />
						</form:select>
					</td>
					<td>
						<label class="lab">团队经理：</label> 
						<form:input path="queryParam.teamManagerName" class="input_text178" />
					</td>
				</tr>
				<tr id="T1" style="display: none;">
					<td>
						<label class="lab">客户经理：</label> 
						<form:input path="queryParam.customerManagerName" class="input_text178" />
					</td>
					<td>
						<label class="lab">标识：</label> 
						<form:select class="select180" path="queryParam.channelCode">
							<form:option value="">请选择</form:option>
							<form:options items="${fns:getDictList('jk_channel_flag')}" itemValue="value" itemLabel="label" />
						</form:select>
					</td>
					<td>
						<label class="lab">是否加急：</label> 
						<form:radiobuttons path="queryParam.urgentFlag" items="${fns:getDictList('jk_urgent_flag')}" itemValue="value" itemLabel="label" />
					</td>
				</tr>
				<tr id="T2" style="display: none;">
					<td>
						<label class="lab">是否电销：</label> 
						<form:radiobuttons path="queryParam.telesalesFlag" items="${fns:getDictList('jk_telemarketing')}" itemValue="value" itemLabel="label" />
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input> 
			<input type="button" id="rateAuditFormClrBtn" class="btn btn-primary" value="清除"></input>
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<!-- <img src="../../../static/images/up.png" id="showMore" onclick="javascript:show();"></img> -->
				<img src="../../../static/images/up.png" id="showMore"></img>
			</div>
		</div>
	</div>
	<div id="auditList">
		<p class="mb5">
			<button class="btn btn-small" id="allotTaskBtn">分配外访任务</button>
		</p>
		<!-- <input id="download" type="button" class="btn btn-small" value="外访清单下载" /> -->
		<sys:columnCtrl pageToken="outside"></sys:columnCtrl>
		<div class="box5">
			<table id="tableList" class="table  table-bordered table-condensed table-hover data-list-table">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkBoxAll" onclick="checkBoxAll();"></input></th>
						<th>借款编号</th>
						<th>客户姓名</th>
						<th>门店</th>
						<th>产品</th>
						<th>状态</th>
						<th>批复金额</th>
						<th>批复分期</th>
						<th>加急标识</th>
						<th>是否电销</th>
						<th>团队经理</th>
						<th>客户经理</th>
						<th>进件时间</th>
						<th>外访发起时间</th>
						<th>渠道</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="prepareListBody">
					<c:if test="${ workItems!=null && fn:length(workItems)>0}">
						<c:set var="index" value="0" />
						<c:forEach items="${workItems}" var="item">
							<c:set var="index" value="${index+1}"/>
							<tr>
								<td>
								<input type="checkbox" name="taskId" value='${item.wobNum};${item.applyId};${item.token};${item.loanCode}'/>
								</td>
								<td>${item.loanCode}</td>
								<td>${item.customerName}</td>
								<td>${item.storeName}</td>
								<td>${item.applyProductName}</td>
								<td>${item.loanStatusName}</td>
								<td><fmt:formatNumber value="${item.replyMoney}" type="number" pattern="#,##0.00" /></td>
								<td>${item.replyMonth}</td>
								<td><c:if test="${item.urgentFlag == '1'}"><span class="red">加急</span></c:if></td>
								<td><c:if test="${item.telesalesFlag == '1'}">是</c:if></td>
								<td>${item.teamManagerName}</td>
								<td>${item.customerManagerName}</td>
								<td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd" /></td>
								<td>${item.visitStartTime}</td>
								<td>${item.channelCodeLabel}</td>
								<td>
									<input type="button" value="查看" class="btn_edit" onclick="showOutVisitTaskList('${item.loanCode}');"/>
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${ workItems==null || fn:length(workItems)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/javascript">
		$("#tableList").wrap(
				'<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method : 'get',
			cache : false,
			height : $(window).height() - 260,

			pageSize : 20,
			pageNumber : 1
		});
		$(window).resize(function() {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>