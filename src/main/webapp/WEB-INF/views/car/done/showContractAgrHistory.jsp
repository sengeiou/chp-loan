<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="history">
		<table  class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>合同编号</th>
					<th>操作时间</th>
					<th>操作人</th>
					<th>操作步骤</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="item">
					<tr>
						<td>${item.contractCode}</td>
						<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${item.createBy}</td>
						<td>${item.operateStep}</td>
						<th>${item.remark}</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>