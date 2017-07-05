<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="detailAddress" type="java.lang.String" required="true" description="详细地址"%>
<c:choose>
<c:when test="${fn:contains(detailAddress,'北京市')
	                       ||fn:contains(detailAddress,'天津市')
	                       ||fn:contains(detailAddress,'上海市')
	                       ||fn:contains(detailAddress,'重庆市')}">
				          	${fn:substring(detailAddress,3,fn:length(detailAddress))}</c:when>
<c:otherwise>${detailAddress}</c:otherwise>
</c:choose>
