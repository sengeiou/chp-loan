<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default">
<script type="text/javascript">

	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/common/bank/${queryURL }");
		$("#searchForm").submit();
		return false;
	}
</script>
<title>门店</title>
</head>
<body>
	<form:form id="searchForm" action="${ctx}/common/bank/${queryURL }"
		method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<div class="rows">
			<label>支行名称：</label><input name="bankName" value="${bank.bankName}"
				class="txt date input_text178" style="width: 260px;"/> 
			<label>支行编码：</label><input
				name="bankCode" value="${bank.bankCode}" class="txt date input_text178"
				style="width: 90px;" /> &nbsp;<input id="btnSubmit"
				class="btn btn-primary" type="submit" value="查询" />
		</div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed" style="margin-bottom:50px">
		<tr>
			<th width="30"> <input type="radio" id="all"/></th>
			<th>支行名称</th>
			<th>支行编码</th>
		</tr>
		<c:forEach items="${page.list}" var="bank">
			<tr>
				<td width="30"><input name="bankCode" type="radio"
					id="${bank.bankCode}" sname="${bank.bankName}" /></td>
				<td>${bank.bankName }</td>
				<td>${bank.bankCode }</td>
			</tr>
		</c:forEach>
	</table>
	<div>${page}</div>
</body>
</html>