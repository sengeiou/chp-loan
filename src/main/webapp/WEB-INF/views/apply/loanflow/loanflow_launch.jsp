<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信借流程发起</title>
</head>
<body>
  
   <p>${workItem.bv.customerName}</p>
   <form:form id="loanapplyForm" action="${ctx}/apply/loanFlow/launchFlow" method="post">
     <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
     <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
     <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
    <p><span>客户姓名：</span><span><input type="text" name="customerName" value="${workItem.bv.customerName}"></input></span>
    </p>
   <input type="submit" name="流程发起" value="流程发起"></input>
   </form:form>
</body>
</html>