<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default"/>
<title>Insert title here</title>
</head>
<body>
	<div class="control-group" >
		<p class="pop_title">提示</p>
		<div class="pop_content">
			<form action="#" method="post">
				<label class="lab">金账户上限额度：</label>
				
				<c:if test="${ceiling == '0.00000' }" var = "f">
				<input type="text"
					name="ceiling" id="ceiling" class="input_text178" value="">
					<input type="hidden" name="ceilingHidden" id="ceilingHidden"
					class="input_text178" value="">
				</c:if>
				<c:if test="${!f }">
				<input type="text"
					name="ceiling" id="ceiling" class="input_text178" value="<fmt:formatNumber value='${ceiling }' pattern="#00.00" />">
				<input type="hidden" name="ceilingHidden" id="ceilingHidden"
					class="input_text178" value="${ceiling }">
				</c:if>
				
			</form>
		</div>
	</div>
</body>
</html>