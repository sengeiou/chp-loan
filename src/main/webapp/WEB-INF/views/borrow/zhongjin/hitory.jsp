<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>历史</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<meta name="decorator" content="default"/>
</head>
<body>

	<div class="control-group">

		<table class="table table-bordered table-condensed table-hover "
			style="width: 99%; margin: 0 auto">
			<thead>
				<tr>
					<th style="width: 200px;">操作时间</th>
					<th style="width: 200px;">操作人</th>
					<th style="width: 200px;">操作步骤</th>
					<th style="width: 200px;">操作结果</th>
					<th style="width: 600px;">操作说明</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ items!=null && fn:length(items)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${items}" var="item">
						<c:set var="index" value="${index+1}" />
							<tr>
								<td>${item.createTime}</td>
								<td>${item.createBy}</td>
								<td>${item.operName}</td>
								<td>${item.operResult}</td>
								<td>${item.operNotes}</td>
							</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>

	</div>
		
</body>
</html>