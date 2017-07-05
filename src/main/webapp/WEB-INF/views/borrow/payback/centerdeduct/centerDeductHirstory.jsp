<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>集中划扣已拆分页面历史</title>
<script type="text/javascript" src="${context}/js/payback/centerdeducthirstory.js"></script>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	
	$("#searchForm").attr("action", "${ctx}/borrow/payback/payBackSplitapply/getHirstory");
	$("#searchForm").submit();
	return false;
}
</script>
</head>
<body>
	<form id="searchForm"> 
		 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="mainId" name="applyId" type="hidden" value="${applyId}" />
	</form>
   <div class="body_r">
    <h3 class="pt10 pb10">历史记录</h3>
    <table cellspacing="0" cellpadding="0" border="0"  class="table2" width="99.9%">
    <tr>
        <th>操作时间</th>
        <th>操作人</th>
        <th>操作步骤</th>
        <th>操作结果</th>
        <th>备注</th>
    </tr>
    <c:if test="${ listOpe.list!=null && fn:length(listOpe.list)>0}">
    <c:forEach items="${listOpe.list }" var="packbackope">
     <tr>
        <input type="hidden" value="${packbackope.id }"/>
        <td>${packbackope.operatTime }</td>
        <td>${packbackope.operator }</td>
        <td>${packbackope.dictLoanStatus }</td>
        <td>${packbackope.operateResult }</td>
        <td>${packbackope.remark }</td>
    </tr>
    </c:forEach>
   </c:if>
     <c:if test="${ listOpe.list==null || fn:length(listOpe.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
     </c:if>
</table>
    <div class="pagination">${listOpe}</div>
</div>
</body>
</html>