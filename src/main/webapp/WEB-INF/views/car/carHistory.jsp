<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>操作历史</title>
	<meta name="decorator" content="default"/> 
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
</head>
<body>
<!--  调用 历史弹出框 公用方法
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<a href="javascript:showCarLoanHis('CJ20160122')">历史</a>
 -->
	<form id="searchForm"> 
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="loanCode" name="loanCode" type="hidden" value="${info.loanCode}" />
	</form>
<div class="history">
    <h3 class="pt10 pb10">历史状态</h3>
    <table class="table  table-bordered table-condensed table-hover " >
    <thead>
	    <tr>
	        <th>操作时间</th>
	        <th>操作人</th>
	        <th>操作步骤</th>
	        <th>操作结果</th>
	        <th>备注</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ page!=null && fn:length(page.list)>0}">
          <c:forEach items="${page.list}" var="item">
             <tr>
             <td> <fmt:formatDate value="${item.operateTime}" pattern="yyyy/MM/dd HH:mm:ss"/>   </td>
             <td>${item.operator}</td>
             <td>${item.operateStep}</td>
             <td>${item.operateResult} </td>
             <td>${item.remark} </td>
         </tr> 
         </c:forEach>            
         </c:if>
         <c:if test="${ page==null || fn:length(page.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody> 
</table>
<div class="tright pr10 pt5 pb5" >
                   <input type="button" class="btn btn-primary" id="backToGrossSpreadList" value="返回"
			onclick="JavaScript:window.parent.window.art.dialog({ id: 'his' }).close();"></input>
                       </div>
</div>
<div class="pagination">${page}</div>
<script type="text/javascript">
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	
	$("#searchForm").attr("action", "${ctx}/common/carHistory/showLoanHisByLoanCode");
	$("#searchForm").submit();
	return false;
}
</script>
</body>
</html>