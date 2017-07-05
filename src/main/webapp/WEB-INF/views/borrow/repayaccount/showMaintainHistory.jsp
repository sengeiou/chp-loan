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
					<th>操作时间</th>
					<th>操作人</th>
					<th>操作步骤</th>
					<th>原数据</th>
					<th>新数据</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="item">
					<tr>
						<td><fmt:formatDate value="${item.operateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						<td>${item.operator}</td>
						<td>${item.operateStep}</td>
						<td style="text-align:left">${item.oldData}</td>
						<td style="text-align:left">${item.newData}</td>
						<th>${item.remarks}</th>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>