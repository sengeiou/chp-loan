<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>已收记录页面</title>
	<meta name="decorator" content="default"/> 
</head>
<body>
<div class="done">
    <h3 class="pt10 pb10">已收记录页面</h3>
    <table cellspacing="0" cellpadding="0" border="0"  class="table2" width="100%">
    <thead>
	    <tr>
	        <th>合同编号</th>
	        <th>客户姓名</th>
	        <th>证件号码</th>
	        <th>金额</th>
	        <th>账户</th>
	        <th>日期</th>
	        <th>回盘结果</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${grantUrgeList!=null && fn:length(grantUrgeList)>0}">
          <c:forEach items="${grantUrgeList}" var="item">
             <tr>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>${item.customerCertNum}</td>
             <td>${item.deductAmount}</td>
             <td>${item.midName}</td>
             <td><fmt:formatDate value="${item.deductDate}" pattern="yyyy/MM/dd HH:mm:ss"/></td>
             <td>${item.splitBackResult}</td>
         </tr> 
         </c:forEach>            
         </c:if>
         <c:if test="${ grantUrgeList==null || fn:length(grantUrgeList)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
       </tbody> 
</table>
</div>
</body>
</html>