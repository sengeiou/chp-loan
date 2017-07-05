<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>集中划扣已拆分页面历史</title>
<script type="text/javascript" src="${context}/js/payback/centerdeducthirstory.js"></script>
</head>
<body>
   
    <h3 class="pt10 pb10">历史记录</h3>
    <table class="table table-hover table-bordered table-condensed">
    <thead> 
    <tr>
        <th>操作时间</th>
        <th>操作人</th>
        <th>操作步骤</th>
        <th>操作结果</th>
        <th>备注</th>
    </tr>
    </thead>
    <c:forEach items="${listOpe.list}" var="packbackope">
     <tr>
        <input type="hidden" value="${packbackope.id }"/>
        <td>${packbackope.operatTime }</td>
        <td>${packbackope.operator }</td>
        <td>${packbackope.dictLoanStatus }</td>
        <td>${packbackope.operateResult }</td>
        <td>${packbackope.remark }</td>
    </tr>
    </c:forEach>
</table>
    <div class="pagination">${listOpe}</div>

</body>
</html>