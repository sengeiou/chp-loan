<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<body>

<head>
<title>个人征信报告简版</title>
<script type="text/javascript">	
	$(function(){
		var tex = $("#tex").val();
		if(tex==''||tex==undefined||tex==null) {
			alert("请登录征信系统！");
			var win = top.window; 
			if (win.opener) win.opener.location.reload();
			window.parent.close();
		} else {
			document.write('${htmlUrl}');
		}
	});
	
</script>
</head>
<!--  <input type="hidden" id ="tex" value="${htmlUrl}"> --> 
	<!-- 简版选择主借人、共借人 -->
</body> 
</html>