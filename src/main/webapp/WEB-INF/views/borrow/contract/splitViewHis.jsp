<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>占比历史</title>
</head>
<body>
	<div>
		<div id="auditList">
			<div class="box5">
				<table id="tableList"
					class="table  table-bordered table-condensed table-hover ">
					<thead>
						<tr>
							<th>大金融（%）</th>
							<th>金信（%）</th>
							<th>生效日期</th>
						</tr>
					</thead>
					<tbody id="prepareListBody">
						<c:forEach items="${page.list }" var="ps" varStatus="xh">
							<c:set var="index" value="${index+1}" />
							<tr>
								<td>${ps.zcj}</td>
								<td>${ps.jinxin }</td>
								<td><fmt:formatDate value="${ps.createTime }" pattern="yyyy-MM-dd"/></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="pagination">${page}</div>
			</div>
		</div>
	</div>
</body>
</html>