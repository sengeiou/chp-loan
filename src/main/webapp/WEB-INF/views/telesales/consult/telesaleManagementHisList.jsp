<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/popUpWindows.js"></script>
<title>客户管理</title>
<meta name="decorator" content="default" />
</head>
<body>
		<table style="width:99%;margin: 0 auto" class="table table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>客户编号</th>
					<th>操作人</th>
					<th>沟通时间</th>
					<th>沟通记录</th>
				</tr>
			</thead>
			<c:forEach items="${page.list}" var="item">
				<tr>
					<td>${item.customerCode}</td>
					<td>${item.createName}</td>  
					<td><fmt:formatDate value="${item.consCommunicateDate}"
							pattern="yyyy/MM/dd HH:mm:ss" /></td>
					<td>${item.consLoanRecord}</td>
					</td>
				</tr>
			</c:forEach>
		</table>
</body>
</html>