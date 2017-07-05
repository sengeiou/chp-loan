<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<META http-equiv="Content-Type" content="application/adobe-pdf ">

<script type="text/javascript">
</script>
<meta name="decorator" content="default" />
<title>合同预览</title>
<script type="text/javascript">
$(document).ready(function(){ 
	$("#downPdfVal").val($("#myTab a").attr("docId"));
	$("#downPdfValName").val($("#myTab a").attr("fileName"));
	$('#myTab a').click(function (e) {
		$("#downPdfVal").val($(this).attr("docId"));
		$("#downPdfValName").val($(this).attr("fileName"));
	}); 
}); 
function downLoadPdf(){
	window.location="${ctx}/apply/contractAudit/downLoadContractsingle?docId="+$("#downPdfVal").val()+"&fileName="+$("#downPdfValName").val();
}
</script>
</head>
<body >        
<inut type="hidden" id="downPdfVal"/>
<inut type="hidden" id="downPdfValName"/> 
 <div id="myTabContent" class="tab-content" style="padding-top:5px">
   <ul id="myTab" class="nav nav-tabs">
  	  	<c:forEach items="${files}" var="file" varStatus="status">
	  	  	<c:if test="${file.docId!=null && file.docId!=''}">
		    	  <li 
		    	   <c:if test="${status.index==0}">
		    	    class="active"
		    	   </c:if>
		    	    >
		       		<a href="#${file.id}" data-toggle="tab" docId="${file.docId}" fileName="${file.contractFileName} ">
		         		${file.contractFileName} 
		       		</a>
		          </li>
	          </c:if>
        </c:forEach>
         <c:if test="${role eq '1'}">
	        <input type="button" style="float: right" id="downPdfBtn"
				onclick="downLoadPdf()" class="btn btn-success"
				value="下载">
	       
       </c:if>
       </ul>
       <c:forEach items="${files}" var="file" varStatus="status">
	       <c:if test="${file.docId!=null && file.docId!=''}">
		   		 <div 
		   		   <c:if test="${status.index==0}">
		   		      class="tab-pane fade in active"
		   		   </c:if>
		   		    <c:if test="${status.index!=0}">
		   		    class="tab-pane fade in"
		   		    </c:if>
		   		     id="${file.id}" >
		     		<iframe height="500px" width="100%" margin-left="50%" scrolling="auto" src="${ctx}/apply/contractAudit/contractFilePreviewShow?docId=${file.docId}" ></iframe>
		   		 </div>
	   		 </c:if>
 	 </c:forEach> 
 	
 </div>

</body>
</html>