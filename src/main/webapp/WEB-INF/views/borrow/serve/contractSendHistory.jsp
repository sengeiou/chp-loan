<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#historyForm").attr("action", "${ctx }/borrow/serve/customerServe/historyList");
		$("#historyForm").submit();
		return false;
	}
</script>
</head>
<body>
	<form id="historyForm" action="${ctx }/borrow/serve/customerServe/historyList" method="post">
		<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
		<input id="pageSize" name="pageSize" type="hidden" />
		<input id="applyId" name="applyId" type="hidden" value="${applyId}">
	</form>
	<div class="history">
    	<h3 class="pt10 pb10">历史状态</h3>
		<table  class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>操作时间</th>
					<th>操作人</th>
					<th>操作步骤</th>
					<th>操作结果</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${page.list != null && fn:length(page.list) > 0}">
					<c:forEach items="${page.list}" var="item">
						<tr>
							<td><fmt:formatDate value="${item.operateTime}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
							<td>${item.operator}</td>
							<td>${item.operateStep}</td>
							<td>${item.operateResult}</td>
							<th>${item.remark}</th>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${page.list == null || fn:length(page.list) == 0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有历史数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<c:if test="${page.list != null && fn:length(page.list) > 0}">
			<div class="pagination">${page }</div>
		</c:if>
	</div>
</body>
</html>