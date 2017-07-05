<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default">
<script type="text/javascript">

	$(document).ready(function() {
		 /* $("#all").click(function() {
			$('input[name="userIds"]').prop("checked",this.checked); 
			//全选按钮存储或移除数据
			$('input[name="userIds"]').each(function(i){
				addOrRemoveCache(this.id,stores);
			}); 
		}); */
		
		/*  $('input[name="orgIds"]').click(function(){
			$(this).prop('checked',this.checked);
			$('input[name="orgIds"]:checked').not($(this)).attr('checked',false);
		});  */
			
		
	});
	
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#searchForm").attr("action", "${ctx}/sys/user/${queryURL }");
		$("#searchForm").submit();
		return false;
	}
</script>
<title>门店</title>
</head>
<body>
	<form:form id="searchForm" action="${ctx}/sys/user/${queryURL }"
		method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<div class="rows">
			<label>员工编号：</label><input name="loginName" value="${user.loginName}"
				class="txt date input_text178" style="width: 100px;"/> &nbsp;
			<label>员工姓名：</label><input name="name" value="${user.name}"
				class="txt date input_text178" style="width: 100px;"/> &nbsp;<input id="btnSubmit"
				class="btn btn-primary" type="submit" value="查询" />
		</div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed" style="margin-bottom:50px">
		<tr>
			<th width="30"> 单选 </th>
			<th>员工编号</th>
			<th>员工姓名</th>
		</tr>
		<c:forEach items="${page.list }" var="user">
			<tr>
				<td width="30"><input name="userIds" type="radio"
					id="${user.loginName}" sname="${user.name}" /></td>
				<td>${user.loginName }</td>
				<td>${user.name }</td>
			</tr>
		</c:forEach>
	</table>
	<div>${page}</div>
</body>
</html>