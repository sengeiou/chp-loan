<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已拆分历史历史</title>
	<meta name="decorator" content="default"/> 
</head>
<body>
	<form id="searchForm"> 
		 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="rId" name="rId"rPaybackId" type="hidden" value="${PaybackSplit.rId}" />
	</form>
<div class="history">
    <table class="table table-hover table-bordered table-condensed">
    <thead>
	    <tr>
	        <th>操作时间</th>
	        <th>操作人</th>
	        <th>划扣平台</th>
	        <th>操作结果</th>
	        <th>备注</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ waitPage.list !=null && fn:length(waitPage.list)>0}">
         <c:forEach items="${waitPage.list}" var="item">
	             <tr>
	             <td><fmt:formatDate value="${item.createTime}" pattern="yyyy/MM/dd HH:mm:ss"/> </td>
	             <td>${item.createBy}</td>
	             <td>${item.dictDealTypeLabel}</td>
	             <td>${item.splitBackResultLabel} </td>
	             <td>${item.splitFailResult} </td>
	         </tr> 
	         </c:forEach>            
         </c:if>
         <c:if test="${ waitPage.list==null || fn:length(waitPage.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody> 
</table>
	<div class="pagination">${waitPage}</div>
</div>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	
	$("#searchForm").attr("action", "${ctx}/borrow/payback/historicalRecords/allSplitHis");
	$("#searchForm").submit();
	return false;
}
</script>
</body>
</html>