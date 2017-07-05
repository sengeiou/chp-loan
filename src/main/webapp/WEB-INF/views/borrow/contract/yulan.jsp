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
    	 var loanCode=$('#load').val();
    	 var file=$('#file').val();
    	
    	$.ajax({
    		url:'${ctx}/apply/contractAudit/getSwf?loanCode='+loanCode,
    		datatype:'json',
    		async:false,
    		type:'GET',
    		success:function(data){
    			var f=data.file;
    			file=f;
    		},
    		error:function(){
    			alert('请求出错');
    		}
    	}); 
   
           var url='${context}/static/flash/flexpaper/'+file;
           var fp = new FlexPaperViewer(    
           		 '${context}/static/flash/flexpaper/FlexPaperViewer',
					 'viewerPlaceHolder', { config : {
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
        <input type="hidden" id="load" value="${loanCode}"/>
        <input type="hidden" id="file" value=""/>
            <a id="viewerPlaceHolder" name="right" style="width:660px;height:480px;display:block"></a>         
           
        </div>   
</body>
</html>