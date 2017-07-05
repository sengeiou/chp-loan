<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/statistics/returnRateStats.js"></script>

<title>退回率统计报表</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		returnRateStats.init();
	});
	
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#returnRateSearchForm").attr("action", "${ctx}/borrow/statistics/returnRateStatsList");
		$("#returnRateSearchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="control-group">
		<form id="returnRateSearchForm" method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="export" name="exportId" type="hidden" value="" />
			<table class=" table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">员工姓名：</label>
						<input type="text" name="userName" class="input_text178" value="${returnRateStatsParams.userName }"/>
					</td>
					<td>
						<label class="lab">业务部：</label>
						<select name="businessDepartmentId" style="width: 180px">
							<option value="">全部</option>
							<c:forEach var="item" items="${businessDepartmentList}">
								<option value="${item.id}" <c:if test="${item.id == returnRateStatsParams.businessDepartmentId}">selected='selected'</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">城支：</label>
						<select name="cityBranchId" style="width: 180px">
							<option value="">全部</option>
							<c:forEach var="item" items="${cityBranchList}">
								<option value="${item.id}" <c:if test="${item.id == returnRateStatsParams.cityBranchId}">selected='selected'</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">时间：</label>
						<input type="text" class="input_text178 Wdate" name="month" value="<fmt:formatDate value="${returnRateStatsParams.month }" pattern="yyyy-MM"/>" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:true, maxDate:'%y-%M'});" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">员工岗位：</label>
						<select name="roleId" class="select180">
							<option value="">全部</option>
							<option value="6230000004" <c:if test="${returnRateStatsParams.roleId == '6230000004'}">selected='selected'</c:if>>汇金门店-客服</option>
							<option value="6230000003" <c:if test="${returnRateStatsParams.roleId == '6230000003'}">selected='selected'</c:if>>汇金门店-门店副理</option>
						</select>
					</td>
					<td>
						<label class="lab">省分：</label>
						<select name="provinceBranchId" style="width: 180px">
							<option value="">全部</option>
							<c:forEach var="item" items="${provinceBranchList}">
								<option value="${item.id}" <c:if test="${item.id == returnRateStatsParams.provinceBranchId}">selected='selected'</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">门店：</label>
						<select name="storeId" style="width: 180px">
							<option value="">全部</option>
							<c:forEach var="item" items="${storeList}">
								<option value="${item.id}" <c:if test="${item.id == returnRateStatsParams.storeId}">selected='selected'</c:if>>${item.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" id="clearBtn" value="清空"></input>
				<input type="button" class="btn btn-primary" id="searchBtn" value="查询"></input>
				<input type="button" class="btn btn-primary" id="exportBtn" value="导出Excel"></input>
			</div>
		</form>
	</div>

	<div class="box5">
		<table class="table table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll">全选</th>
					<th>序号</th>
					<th>业务部</th>
					<th>省分</th>
					<th>城支</th>
					<th>门店</th>
					<th>员工岗位</th>
					<th>员工姓名</th>
					<th>时间</th>
					<th>退回次数</th>
					<th>退回率</th>
				</tr>
			</thead>
			<c:choose>
				<c:when test="${returnRateStatsPage.list ne null && not empty returnRateStatsPage.list}">
					<c:set var="index" value="0" />
					<c:forEach var="item" items="${returnRateStatsPage.list}" varStatus="status">
						<tr>
							<td><input type="checkbox" name="id" value="${item.id}"></td>
							<td>${status.count}</td>
							<td>${item.businessDepartmentName}</td>
							<td>${item.provinceBranchName}</td>
							<td>${item.cityBranchName}</td>
							<td>${item.storeName}</td>
							<td>${item.roleName}</td>
							<td>${item.userName}</td>
							<td><fmt:formatDate value="${item.statsDate}" pattern="yyyy-MM"/></td>
							<td>${item.returnTimes}</td>
							<td><fmt:formatNumber type="percent" maxIntegerDigits="5" value="${item.returnRate}" /></td>
						</tr>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr>
						<td colspan="18" style="text-align: center;">没有数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</div>
	<div class="pagination">${returnRateStatsPage}</div>
</body>
</html>