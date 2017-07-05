<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>

<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet"
	href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/grant/disCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script language="javascript">
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action",
				"${ctx}/borrow/grant/disCard/showDisPerson");
		$("#searchForm").submit();
		return false;
	}
	$(function (){
		var win = window.parent.art.dialog.open.origin;
		var userCode = $("#userCode",win.document).val();
		$("input[name='SelectEl'][userCode='"+userCode+"']").prop("checked",true);
	});
</script>
</head>
<body>
	<!--分配外访弹出框表-->
	<div class="dialogbox">
		<div class="diabox_cell">
			<div class="diabox">
				<div class="mb10 control-group">
					<form id="searchForm" method="post"
						action="${ctx}/borrow/grant/disCard/showDisPerson">
						<label class="lab">编号：</label> <input type="text"
							class="input_text140 mr10" id="userCode" name="userCode"
							value="${userInfo.userCode }"> <label class="lab">姓名：</label>
						<input type="text" class="input_text140 mr10" id="name"
							name="name" value="${userInfo.name }"> <input id="pageNo"
							name="pageNo" type="hidden" value="${page.pageNo}" /> <input
							id="pageSize" name="pageSize" type="hidden"
							value="${page.pageSize}" />
						<button class="btn btn-small">检索</button>
					</form>
				</div>
				<table class="table  table-bordered table-condensed table-hover ">
					<thead>
						<tr>
							<th>序号</th>
							<th>员工编号</th>
							<th>姓名</th>
							<th>选择</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${ user!=null && fn:length(user.list)>0}">
							<c:set var="index" value="0" />
							<c:forEach items="${user.list}" var="item">
								<c:set var="index" value="${index+1}" />
								<tr>
									<td>${index}</td>
									<td>${item.userCode}</td>
									<td>${item.name}</td>
									<td><input type="radio" name="SelectEl"
										userCode="${item.id}"/></td>
								</tr>
							</c:forEach>
						</c:if>
						<c:if test="${ user==null || fn:length(user.list)==0}">
							<tr>
								<td colspan="4" style="text-align: center;">没有待办数据</td>
							</tr>
						</c:if>
					</tbody>
				</table>
				<br>
				<br>
				<div class="pagination">${user}</div>
			</div>
		</div>
	</div>
</body>
</html>