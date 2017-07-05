<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>合同下载和查看</title>
	<meta name="decorator" content="default"/> 
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
</head>
<body>
<div class="history">
    <h3 class="pt10 pb10">合同下载和查看</h3>
    <table class="table  table-bordered table-condensed table-hover " >
    <thead>
	    <tr>
	    	<!-- <th><input type="checkbox" id="checkAll" /></th> -->
	        <th>操作时间</th>
	        <th>文件名称</th>
	        <th>下载和查看</th>
	    </tr>
	  </thead>
        <tbody>
         <c:if test="${ docs!=null && fn:length(docs)>0}">
          <c:forEach items="${docs}" var="item">
             <tr>
             <!-- <td><input type="checkbox" name="checks" class="checks" /></td> -->
             <%-- <td> <fmt:formatDate value="${item.createdDate}" pattern="yyyy/MM/dd HH:mm:ss"/>   </td> --%>
             <td> <fmt:formatDate value="${item.modifyTime}" pattern="yyyy/MM/dd HH:mm:ss"/>   </td>
             <%-- <td>${item.docTitle}</td> --%>
             <td>${item.contractFileName}</td>
             <td>
             	<c:if test="${item.downloadFlag==1}">
             		<input type="button" value="下载" class="btn_edit" name="download" onclick="JavaScript:window.location='${ctx}/car/carContract/checkRate/contractDownloadOne?docId=${item.docId }'" ></input>
             		<input type="button" value="预览" class="btn_edit" onclick="contractPreview('${item.docId}')"></input>
             	</c:if>
             	<c:if test="${item.downloadFlag==0}">
             		<c:if test="${item.contractFileName=='还款管理服务说明书'}">
             		   <input type="button" value="下载" class="btn_edit" name="download" onclick="JavaScript:window.location='${ctx}/car/carContract/checkRate/contractDownloadOne?docId=${item.docId }'" ></input>
             		</c:if>
             		<input type="button" value="预览" class="btn_edit" onclick="contractPreview('${item.docId}')"></input>
             	</c:if>
             </td>
         </tr> 
         </c:forEach>            
         </c:if>
         <c:if test="${ docs==null || fn:length(docs)==0}">
           <tr><td colspan="18" style="text-align:center;">没有可操作合同</td></tr>
         </c:if>
       </tbody> 
</table>
<div><input type="hidden" id ="oneDocId"  /></div>
</div>
<script type="text/javascript">
/**
 * 预览合同
 */
function contractPreview(docId){
 		var url=ctx + "/car/carContract/checkRate/contractPreviewOne?docId="+docId;
	    art.dialog.open(url, {  
		   id: 'contract',
		   title: '合同预览',
		   lock:true,
		   width:700,
		   height:500
		},false);  
}
</script>
</body>
</html>