<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已拆分历史</title>
	<meta name="decorator" content="default"/> 
</head>
<body>
	<form id="searchForm"> 
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input id="contractCode" name="contractCode" type="hidden" value="${contractCode}" />
	</form>
<div class="history">
    <table class="table  table-bordered table-condensed table-hover " >
    <thead>
	    <tr>
	        <th>合同编号</th>
	        <th>操作名称</th>
	        <th>操作结果</th>
	        <th>操作说明</th>
	        <th>创建人</th>
	        <th>创建时间</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ page!=null && fn:length(page.list)>0}">
          <c:forEach items="${page.list}" var="item">
             <tr>   
             <td>${item.contractCode}</td>
             <td>${item.operName}</td>
             <td>${item.operResult}</td>
             <td>${item.operNotes}</td>
             <td>${item.createBy}</td>
             <td> <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd"/>
         </tr> 
         </c:forEach>            
         </c:if>
         <c:if test="${ page==null || fn:length(page.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有历史数据</td></tr>
         </c:if>
       </tbody> 
</table>
	<div class="pagination">${page}</div>
</div>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	
	$("#searchForm").attr("action", "${ctx}/common/history/showPaybackHistory");
	$("#searchForm").submit();
	return false;
}
</script>
</body>
</html>