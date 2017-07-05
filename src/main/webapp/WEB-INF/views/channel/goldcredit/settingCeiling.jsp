<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default"/>
<title>Insert title here</title>
<script src="${context}/js/channel/goldcredit/onlyNumber.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function (){
		$('#ceiling','form').onlyFloat();
	});
</script>
</head>
<body>

	<div class="control-group" >
		<p class="pop_title">提示</p>
		<div class="pop_content">
			<form action="#" method="post">
				<label class="lab">金信上限额度：</label>
				
				<c:if test="${ceiling.kinnobu_quota_limit == '0.00000' }" var = "f">
				<input type="text"
					name="ceiling" id="ceiling" class="input_text178" value="">
					<input type="hidden" name="ceilingHidden" id="ceilingHidden"
					class="input_text178" value="" style="ime-mode:disabled">
				</c:if>
				<c:if test="${!f}">
				<input type="text"
					name="ceiling" id="ceiling" class="input_text178" value="<fmt:formatNumber value='${ceiling.kinnobu_quota_limit }' pattern="###.00" />" style="ime-mode:disabled">
				<input type="hidden" name="ceilingHidden" id="ceilingHidden"
					class="input_text178" value="${ceiling.kinnobu_quota_limit }">
				</c:if>
			</form>
		</div>
		
	</div>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<span style="color:red;" class= 'span7 text-center'>(备注：输入的验证规则，只能输入数字)</span>
</body>
</html>