<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>放款历史</title>
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
	        <th>回盘时间</th>
	        <th>操作人</th>
	        <th>成功金额</th>
	        <th>放款方式</th>
	        <th>回盘结果</th>
	        <th>失败原因</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ page!=null && fn:length(page.list)>0}">
          <c:forEach items="${page.list}" var="item">
             <tr>
             <td> <fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>   </td>
             <td>${item.createBy}</td>
             <td><fmt:formatNumber value="${item.successAmount}" pattern="#,#00.00" /></td>
             <td>${item.grantDeductFlag}</td>
             <td>${item.grantRecepicResult}</td>
             <td>${item.grantFailResult} </td>
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
	
	$("#searchForm").attr("action", "${ctx}/borrow/grant/grantAudit/getGrantHis");
	$("#searchForm").submit();
	return false;
}
</script>
</body>
</html>