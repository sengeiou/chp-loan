<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	
	<c:forEach items="${list}" var="ls">
		<input type="hidden" name="id" value="${ls.id }" />
		<tr>
			<td>${ls.templateName}</td>
			<td>${ls.templateDescription }</td>
			<td>${ls.templateContent }</td>
			<td>${ls.templateStatus }</td>
			<td>${ls.createTime }</td>
			<td><input type="submit" value="修正" onclick="updateGo('${ls.id }')" /></td>
		</tr>
	</c:forEach>