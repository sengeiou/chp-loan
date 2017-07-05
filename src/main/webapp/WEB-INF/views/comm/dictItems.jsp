<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>字典查询</title>
<script type="text/javascript">
$(document).ready(function(){
	$('#all').click(function(){
		var checked = $(this).attr('checked');
		if(checked || checked=='checked'){
			checked = true;
		}else{
			checked=false;
		}
		$("input[name='dict']").each(function(){
			$(this).attr('checked',checked);
		});
	});
});
</script>
</head>
<body>
	<table id="contentTable"
		class="table table-striped table-bordered table-condensed">
		<tr>
			<th width="30"><input type="checkbox" id="all"/>全选</th>
			<th>${typeName}</th>
		</tr>
		<c:forEach items="${dicts}" var="item">
			<tr>
				<td width="30"><input name="dict" type="checkbox"
					id="${item.id}" svalue="${item.midBankName}" /></td>
				<td>${item.midBankName}</td>
			</tr>
		</c:forEach>
	</table>	
</body>
</html>