<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<META http-equiv="Content-Type" content="application/adobe-pdf ">
<meta name="decorator" content="default" />

<script type="text/javascript">
$(document).ready(function(){
	$("#downPdfBtn").hide();
	$('#myTab a').click(function (e) {
		var downloadFlag = $(this).attr("downloadFlag");
        //e.preventDefault();//阻止a链接的跳转行为 
        //$(this).tab('show');//显示当前选中的链接及关联的content 
        if($.trim($(this).text())=="还款管理服务说明书" || downloadFlag==1){
        	$("#downPdfVal").val($(this).attr("docId"));
        	$("#downPdfBtn").show();
        }else{
        	$("#downPdfBtn").hide();
        }
    });
});
function downLoadPdf(){
	window.location="${ctx}/car/carContract/checkRate/contractDownloadOne?docId="+$("#downPdfVal").val();
}
</script>
<title>合同预览</title>
</head>
<body ><inut type="hidden" id="downPdfVal"/>
 <div id="myTabContent" class="tab-content" style="padding-top:5px">
   <ul id="myTab" class="nav nav-tabs">
  	  	<c:forEach items="${files}" var="file" varStatus="status">
    	  <li 
    	   <c:if test="${status.index==0}">
    	    class="active"
    	   </c:if>>
       		<a href="#${file.id}" data-toggle="tab" docId="${file.docId}" downloadFlag="${file.downloadFlag}">
         		${file.contractFileName} 
       		</a>
          </li>
        </c:forEach>
        <input type="button" style="float: right" id="downPdfBtn"
			onclick="downLoadPdf()" class="btn btn-success"
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
     		<iframe height="500px" width="100%" margin-left="50%" scrolling="auto" src="${ctx}/car/carContract/checkRate/contractPreviewOneShow?docId=${file.docId}" ></iframe>
   		 </div>
   		 
 	 </c:forEach> 
 	
 </div>

</body>
</html>