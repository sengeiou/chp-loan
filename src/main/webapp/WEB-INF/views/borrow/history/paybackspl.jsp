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
		<input id="applyId" name="applyId" type="hidden" value="${info.applyId}" />
		<input id="rPaybackId" name="rPaybackId" type="hidden" value="${rPaybackId}" />
		<input id="payBackApplyId" name="payBackApplyId" type="hidden" value="${payBackApplyId}" />
		<input id="payBackApplyId" name="lisi" type="hidden" value="${lisi}" />
	</form>
<div class="history">
    <table class="table  table-bordered table-condensed table-hover " >
    <thead>
	    <tr>
	        <th>回盘时间</th>
	        <th>操作人</th>
	        <th>划扣金额</th>
	        <th>划扣平台</th>
	        <th>回盘结果</th>
	        <th>失败原因</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ page!=null && fn:length(page.list)>0}">
          <c:forEach items="${page.list}" var="item">
             <tr>
             <td> <fmt:formatDate value="${item.splitBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/>   </td>
             <td>${item.createBy}</td>
             <td><fmt:formatNumber value="${item.splitAmount}" pattern="#,#00.00" /></td>
             <td>${item.dictDealType}</td>
             <td>${item.splitBackResult}</td>
             <td>${item.splitFailResult} </td>
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
	
	$("#searchForm").attr("action", "${ctx}/common/history/showPayBackHis");
	$("#searchForm").submit();
	return false;
}
</script>
</body>
</html>