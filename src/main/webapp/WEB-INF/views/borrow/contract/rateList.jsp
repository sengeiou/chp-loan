<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>利率列表</title>
<script type="text/javascript">
</script>
</head>
<body>
 <form id="rateForm">
  <table id="contentTable" class="table table-striped table-bordered table-condensed">
     <thead>
       <tr><th>利率</th>
           <th>有效期</th>
       </tr>
     </thead>
     <tbody>
       <c:forEach items="${rateInfoList}" var="item" varStatus="status">
         <tr>
            <td>
              <input type="hidden" name="rateInfoList[${status.index}].id" value="${item.id}"/>
              <input type="checkbox" name="rateInfoList[${status.index}].effectiveFlag"
              <c:if test="${item.effectiveFlag=='1'}">
                checked=true 
              </c:if>
               value="1"/>${item.rate}%
            </td>
            <td>
               <input id="d4311${status.index}" class="Wdate input_text70 required" name="rateInfoList[${status.index}].startDate" value="${item.startDate}"
                 type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312${status.index}\')}',dateFmt:'HH:mm:ss'})"/>
               <input id="d4312${status.index}" class="Wdate input_text70 required" name="rateInfoList[${status.index}].endDate" value="${item.endDate}"
                 type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311${status.index}\')}',dateFmt:'HH:mm:ss'})"/>
            </td>
         </tr>
       </c:forEach>
     </tbody> 
     </table>
 </form>
</body>
</html>