<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<title>业务办理</title>
</head>
<body>
  
  <form:form id="taskHandleForm" action="${ctx}/borrow/borrowlist/dispatchFlow">
      <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
      <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
      <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
      <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
      <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
      <input type="hidden" value="${workItem.token}" name="token"></input>
      <input type="hidden" name="queue" value="HJ_CUSTOMER_AGENT"/>
       <input type="hidden" name="viewName" value="loanflow_borrowlist_workItems"/>
       <c:if test="${workItem.stepName=='门店复核'}">
         <input type="hidden" value="TO_RULEENGIN" name="response"/>
       </c:if>
      <input type="submit" name="提交" value='提交'></input>
  </form:form>
</body>
</html>