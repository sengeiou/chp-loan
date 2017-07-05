<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${context}/js/contract/rateList.js" type="text/javascript"></script>
<title>利率列表</title>
<script type="text/javascript">
$(document).ready(function(){
	rateObj.checkAll('selectAll');
});
</script>
</head>
<body>
  <table id="contentTable" class="table table-striped table-bordered table-condensed">
       <tr>
         <th><input type="checkbox" id="selectAll">全选</input></th>
         <th>利率</th>
       </tr>
       <c:forEach items="${rateInfoList}" var="item" varStatus="status">
         <tr>
            <td><input type="checkbox" name="monthRate" value="${item.rate}"></input></td>
            <td>
              <span>${item.rate}%</span>
            </td>
         </tr>
       </c:forEach>
 </table>
</body>
</html>