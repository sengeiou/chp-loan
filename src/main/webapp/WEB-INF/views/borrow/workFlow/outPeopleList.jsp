<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>操作历史</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script language="javascript">
	$(document).ready(function() {
		var msg = "${message }"
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	});

	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/loan/workFlow/getOutPeopleList");
		$("#searchForm").submit();
		return false;

	}
</script>
</head>
<body>
	<form id="searchForm" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<table cellspacing="0" cellpadding="0" border="0" class="table2" width="100%">
			<thead>
				<tr>
					<th>员工编号:<input class="input_text178" name="userCode" value="${user.userCode }">
					</th>
					<th>员工姓名:<input class="input_text178" name="name" value="${user.name }">
					</th>
					<th>门店:<input class="input_text178" name="departmentName" value="${user.departmentName }">
					</th>
					<th><input class="btn btn-small" type="submit" value="查询">
					</th>
				</tr>
			</thead>
		</table>
	</form>
	<br>
	<div class="history">
		<table class="table table-hover table-bordered table-condensed" id="outPerpleTable">
			<thead>
				<tr>
					<th>序号</th>
					<th>员工编号</th>
					<th>员工姓名</th>
					<th>门店</th>
					<th>选择</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ page!=null && fn:length(page.list)>0}">
					<c:forEach items="${page.list}" var="item" varStatus="varIndex">
						<tr>
							<td>${varIndex.index+1 }</td>
							<td>${item.userCode}</td>
							<td>${item.name}</td>
							<td>${item.departmentName}</td>
							<td><input type="radio" name="checkMe" value="${item.id}">
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${ page==null || fn:length(page.list)==0}">
					<tr>
						<td colspan="4" style="text-align: center;">没有数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="pagination">${page}</div>
	<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<thead>
			<tr>
				<th><input class="btn btn-primary r" type="button" value="确认" onclick="submitJob()"></th>
			</tr>
		</thead>
	</table>
	</div>
	<br>
	<br>
	<br>

	<script type="text/javascript">
		function submitJob() {
			//var userCode = getCheckedCheckBoxValue('checkMe');
			var userCode = $('#outPerpleTable input[name="checkMe"]:checked ').val();
			if (userCode) {
				var parent = art.dialog.open.origin;//来源页面
				parent.callback_checkOutPeople(userCode);
				art.dialog.close();
			} else {
				art.dialog.alert("请选择外访人员!", '提示');
			}

		}
	</script>
</body>
</html>