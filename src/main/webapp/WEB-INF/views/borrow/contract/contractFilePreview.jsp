<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">
#print-button {
	display:none;
}
</style>
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
	$(".ttttt").each(function(i,v){ 
		var mainheight = window.screen.height -280; 
		if(mainheight<500){
			$(v).height(500); 
		}else{
			$(v).height(mainheight); 
		}		
	}); 
}); 
function downLoadPdf(){
	window.location="${ctx}/apply/contractAudit/downLoadContractsingle?docId="+$("#downPdfVal").val()+"&fileName="+$("#downPdfValName").val();
}
</script>
<style type="text/css">
.print{
	display:none;
}
</style>
</head>
<body>
<inut type="hidden" id="downPdfVal"/>
<inut type="hidden" id="downPdfValName"/>
 <div id="myTabContent" class="tab-content" style="padding-top:5px">
   <ul id="myTab" class="nav nav-tabs">
  	  	<c:forEach items="${files}" var="file" varStatus="status">
  	  	 <c:if test="${file.docId!=null && file.docId!='' }">
	    	  <li 
	    	   <c:if test="${status.index==0}">
	    	    class="active"
	    	   </c:if>
	    	    >
	    	    <c:choose>
	   		       <c:when test="${signed=='1'}">
	   		         <c:if test="${file.signDocId!=null && fn:length(file.signDocId)>0}">
	       		   		 <a href="#${file.id}" data-toggle="tab" docId="${file.signDocId}" fileName="${file.contractFileName} ">
	         		 		 ${file.contractFileName} 
	       		    	 </a>
	       		     </c:if>
	   		       </c:when>
	   		       <c:otherwise>
	   		             <a href="#${file.id}" data-toggle="tab">
	         		 		 ${file.contractFileName} 
	       		    	 </a>
	   		       </c:otherwise>
	   		    </c:choose>
	          </li>
          </c:if>
        </c:forEach>
       <c:if test="${role eq '1'}">
	        <input type="button" style="float: right" id="downPdfBtn"
				onclick="downLoadPdf()" class="btn btn-success"
				value="下载">
	       
       </c:if>
       </ul>
       <%
          int i=0;
       %>
       <c:forEach items="${files}" var="file" varStatus="status">   
       <c:if test="${file.docId!=null && file.docId!='' }">
	       <div 
	   		   <% if(i==0){ %>
	   		      class="tab-pane fade in active"
	   		  <%}else{ %>
	   		      class="tab-pane fade in"
	   		    <%} %>
	   		     id="${file.id}" >		     
					<c:choose>
						<c:when test="${signed=='1'}">
							<c:if
								test="${file.signDocId!=null && fn:length(file.signDocId)>0}">						
						   		<iframe  class="ttttt" height="500px" width="100%" margin-left="50%"
									scrolling="auto"
									src="${ctx}/apply/contractAudit/contractFilePreviewShow?docId=${file.signDocId}&toolbar=false">
								</iframe>
								<%i++; %>
							</c:if>
						</c:when>
						<c:otherwise>
							<iframe height="500px" class="ttttt" width="100%" margin-left="50%"
								scrolling="auto"
								src="${ctx}/apply/contractAudit/contractFilePreviewShow?docId=${file.docId}&toolbar=false">
							</iframe>
							<%i++; %>
						</c:otherwise>
					</c:choose>
				</div>
			</c:if>
 	 </c:forEach> 
 </div>
</body>
</html>