<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>债权信息列表</title>
<meta name="decorator" content="default" />
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript" src="${context}/js/car/creditorRights/creditorRights.js"></script>
<script type="text/javascript">
function page(n,s){
	if(n) $("#pageNo").val(n);
	if(s) $("#pageSize").val(s);
	$("#inputForm").attr("action","${ctx}/car/creditorRight/list");
	$("#inputForm").submit();
	return false;
}

</script>
</head>
<body>
 <div class="control-group">
    <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th>操作信息</th>
		<th>操作人</th>
		<th>操作时间</th>
      </tr>
      </thead>
      	<c:if test="${ page.list!=null && fn:length(page.list)>0}">
	       	<c:forEach items="${page.list}" var="item" varStatus="status"> 
	        <tr>
	          <td>${item.operMsg}</td>
	          <td> ${item.operPeople} </td>
	          <td><fmt:formatDate value='${item.operTime}' pattern="yyyy-MM-dd HH:mm:ss"/></td>
	      </tr>
	      </c:forEach>       
      </c:if>
      <c:if test="${ page.list==null || fn:length(page.list)==0}">
        <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
      </c:if>
     </table>
   </div>
<div class="pagination">${page}</div>
</body>
</html>