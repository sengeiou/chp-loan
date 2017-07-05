<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户咨询</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/apply/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/apply/consult/findConsultList">客户咨询列表</a></li>
		<shiro:hasPermission name="apply:consult:edit">
			<li><a href="${ctx}/apply/consult/openConsultForm">新增客户咨询</a></li>
		</shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="consult" action="${ctx}/apply/consult/findConsultList" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>客户姓名：</label>
			<form:input path="customerName" htmlEscape="false" maxlength="50" class="input-medium" /></li>
			<li class="btns">
			  <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>客户姓名</th>
				<th>手机号</th>
				<th class="sort-column cardType">证件类型</th>
				<th class="sort-column cardNo">证件号码</th>
				<shiro:hasPermission name="apply:consult:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td><a href="${ctx}/apply/consult/openConsultForm?id=${item.id}">${item.customerName}</a></td>
				<td>${item.mobileNo}</td>
				<td>${item.cardType}</a></td>
				<td>${item.cardNo}</td>
				<shiro:hasPermission name="apply:consult:edit"><td>
    				<a href="${ctx}/apply/consult/openConsultForm?id=${item.id}">修改</a>
					<a href="${ctx}/apply/consult/delete?id=${item.id}" onclick="return confirmx('确认要删除该客户咨询吗？', this.href)">删除</a>
					<!--
				          进入流程发起页面，直接调用flowController的openLaunchForm方法即可，flowCode跟flowInfoDefinition.xml中定义的flowCode同，
				           同时需要在taskDispatch.xml相关的steps下配置业务发起页面所在位置
				 -->
					<a href="${ctx}/bpm/flowController/openLaunchForm?flowCode=code123">流程发起</a>
				   
				    <a href="${ctx}/apply/loanFlow/fetchTaskItems?custId=1111">流程待办列表</a>
				
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>