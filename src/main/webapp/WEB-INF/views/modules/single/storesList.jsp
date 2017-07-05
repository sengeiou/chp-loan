<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default">
<script type="text/javascript">

	$(document).ready(function() {
		
		 $("#all").click(function() {
			$('input[name="orgIds"]').prop("checked",this.checked); 
			//全选按钮存储或移除数据
			$('input[name="orgIds"]').each(function(i){
				addOrRemoveCache(this.id,stores);
			}); 
		});
		
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
		$("#searchForm").attr("action", "${ctx}/sys/org/${queryURL }");
		$("#searchForm").submit();
		return false;
	}
</script>
<title>门店</title>
</head>
<body>
	<form:form id="searchForm" action="${ctx}/sys/org/${queryURL }"
		method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden"
			value="${page.pageSize}" />
		<div class="rows">
			<label>门店名称：</label><input name="name" value="${org.name}"
				class="txt date input_text178" style="width: 260px;" title="查询多个门店用   , 隔开"/> <label>归属区域：</label><input
				name="area.name" value="${org.area.name }" class="txt date input_text178"
				style="width: 90px;" /> &nbsp;<input id="btnSubmit"
				class="btn btn-primary" type="submit" value="查询" />
		</div>
	</form:form>
	<sys:message content="${message}" />
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed" style="margin-bottom:50px">
		<tr>
			<th width="30"> <input type="checkbox" id="all"/>全选 </th>
			<th>机构名称</th>
			<th>归属区域</th>
		</tr>
		<c:forEach items="${page.list }" var="org">
			<tr>
				<td width="30"><input name="orgIds" type="checkbox"
					id="${org.id}" sname="${org.name}" /></td>
				<td>${org.name }</td>
				<td>${org.area }</td>
			</tr>
		</c:forEach>
	</table>
	<div>${page}</div>
</body>
</html>