<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
  * a{
    cursor:pointer;
  }
</style>
<script src="${context}/static/flash/flexpaper/jquery.js" type="text/javascript"></script>
<script src="${context}/static/flash/flexpaper/flexpaper_flash.js" type="text/javascript"></script>
<script src="${context}/static/flash/flexpaper/flexpaper_flash_debug.js" type="text/javascript"></script> 
<script type="text/javascript">
    $(function(){
    	 var docId=$('#docId').val();
    	 var file=$('#file').val();
    	 file = file.substring(file.indexOf('_'));
    	$.ajax({
    		url:'${ctx}/apply/contractAudit/getSwfByDocId?docId='+docId+"&fileName="+file,
    		async:false,
    		type:'get',
    		success:function(result){
    		 	
    		 
    		},
    		error:function(){
    			alert('请求出错');
    		}
    	}); 
    	$('#downLoadBtn${docId}').bind('click',function(){
			 window.location.href=ctx+"/apply/contractAudit/downLoadContract?docId=${docId}";
	    });
           var url='${context}/static/flash/flexpaper/'+file+".swf";
           var fp = new FlexPaperViewer(    
           		 '${context}/static/flash/flexpaper/FlexPaperViewer',
					 'viewerPlaceHolder${docId}', { config : {
					 SwfFile : url,
					 Scale : 0.6, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5,
					 ZoomInterval : 0.2,
					 FitPageOnLoad : false,
					 FitWidthOnLoad : false,
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : false,
					 MinZoomSize : 0.2,
					 MaxZoomSize : 5,
					 SearchMatchAll : false,
					 InitViewMode : 'Portrait',
				/* 	              :${downloadEnabled}, */
					 ViewModeToolsVisible : true,
					 ZoomToolsVisible : true,
					 NavToolsVisible : true,
					 CursorToolsVisible : true,
					 SearchToolsVisible : true,
						
						 localeChain: 'en_US'
					 }});
    });
</script>
</head>
<body>
 
 <div style="position:absolute;left:10px;top:10px;">
  <input type="hidden" id="docId" value="${docId}"/>
  <input type="hidden" id="file" value="${fileName}"/>
  <a id="viewerPlaceHolder${docId}" name="right" style="width:860px;height:480px;display:block"></a>         
 </div>
</body>
</html>