<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript">
</script>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function(){
	$('#myTab a').click(function (e) {
        $("#downPdfVal").val($(this).attr("docId"));
    });
	$('#myTab a:first').click();
});
function downLoadPdf(){
	window.location="${ctx}/car/carContract/checkRate/contractDownloadOne?docId="+$("#downPdfVal").val();
}
</script>
<title>合同预览</title>
</head>
<body><inut type="hidden" id="downPdfVal"/>
 <div id="myTabContent" class="tab-content" style="padding-top:5px">
   <ul id="myTab" class="nav nav-tabs">
  	  	<c:forEach items="${files}" var="file" varStatus="status">
    	  <li 
    	   <c:if test="${status.index==0}">
    	    class="active"
    	   </c:if>
    	    >
    	    <c:choose>
   		       <c:when test="${signed=='1'}">
   		         <c:if test="${file.signDocId!=null && fn:length(file.signDocId)>0}">
       		   		 <a href="#${file.id}" data-toggle="tab" docId="${file.signDocId}">
         		 		 ${file.contractFileName}
       		    	 </a>
       		     </c:if>
   		       </c:when>
   		       <c:otherwise>
   		             <a href="#${file.id}" data-toggle="tab" docId="${file.docId}">
         		 		 ${file.contractFileName}
       		    	 </a>
   		       </c:otherwise>
   		    </c:choose>
          </li>
        </c:forEach>
        <input type="button" style="float: right" id="downPdfBtn"
			onclick="downLoadPdf()" class="btn btn-success jkcj_lendCarProduction_contract_DownLoad"
			value="下载">
       </ul>
       <c:forEach items="${files}" var="file" varStatus="status">
   		 <div 
   		   <c:if test="${status.index==0}">
   		      class="tab-pane fade in active"
   		   </c:if>
   		    <c:if test="${status.index!=0}">
   		    class="tab-pane fade in"
   		    </c:if>
   		     id="${file.id}" >
   		     <c:choose>
   		       <c:when test="${signed=='1'}">
   		          <c:if test="${file.signDocId!=null && fn:length(file.signDocId)>0}">
   		           <iframe id="docIframe${status.index}" name="docIframe${status.index}" height="500px" width="100%" margin-left="50%" onload="testIframe()" scrolling="auto" src="${ctx}/car/carContract/checkRate/contractPreviewOneShow?docId=${file.signDocId}">
     		   
     		       </iframe>
     		       </c:if>
   		       </c:when>
   		       <c:otherwise>
   		          <iframe height="500px" width="100%" margin-left="50%" scrolling="auto" src="${ctx}/car/carContract/checkRate/contractPreviewOneShow?docId=${file.docId}">
     		   
     		      </iframe>
   		       </c:otherwise>
   		     </c:choose>
   		 </div>
 	 </c:forEach> 
 </div>
</body>
</html>