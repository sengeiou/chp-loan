<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${ctxStatic}/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<title>合同预览</title>
<script type="text/javascript">
$(document).ready(function(){  
    $(document).bind("contextmenu",function(e){   
          return false;   
    });
});
</script>
</head>
<body>
<img width="98%" src="${pageContext.request.contextPath}/servlet/showPdfImgServlet?docId=${param.docId}"/><br/>
</body>
</html>