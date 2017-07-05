<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>渠道标识</title>
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
			<th>渠道</th>
		</tr>
		<c:forEach items="${dicts}" var="item">
			<tr>
				<td width="30">
				<input name="dict" type="checkbox" sname="${item.label}" value="${item.value }"/></td>
				<td>${item.label}</td>
			</tr>
		</c:forEach>
	</table>	
</body>
</html>