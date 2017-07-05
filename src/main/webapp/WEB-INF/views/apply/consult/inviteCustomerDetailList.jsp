<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
<title>邀请客户管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<table style="width: 99%; margin: 0 auto" class="table table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th>备注时间</th>
				<th>备注记录</th>
			</tr>
		</thead>
		<c:forEach items="${inviteCustomerDetailList}" var="item">
			<tr>
				<td>
					<fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td>${item.remarks}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>