<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>畅捷实名认证页面</title>
<script type="text/javascript" src="${context}/js/payback/paybacksplit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function centerReductApply(){
	
	        art.dialog.confirm('确认实名认证？', 
			    function () {
			    	 var dialog = art.dialog({
					      content: '认证中。。。',
					      cancel:false,
					      lock:true
				 	 });
				     $("#CenterapplyPayForm").attr("action",ctx+"/borrow/certification/ChangJieSignByContractCode");
				     $("#CenterapplyPayForm").submit();
			  }, 
			    function () {
			      // art.dialog.tips('');
			  });
}	
</script>
<style>
body{TEXT-ALIGN: center;}
#backDiv{ MARGIN-RIGHT: auto;
MARGIN-LEFT: auto;
height:200px;
width:400px;
vertical-align:middle;
line-height:200px;
}
</style>
</head>
<body>
	<div class="control-group" >
          <form method="post" action="${ctx}/borrow/certification/ChangJieSignByContractCode" id="CenterapplyPayForm">
		     <div id="backDiv">
			 <p><span class="red">*</span>合同号：
				 <textarea   name="search_contractCode"></textarea>
			 <p><span class="red">*</span>标志：
				 <input type ='text' name ='search_flag'/>
             </div>
             
          </form>
          <div class="tright pr30 pb5 ">
					<button class="btn btn-primary" onclick="centerReductApply()">提交</button>
	     </div>
	 </div>
	 <div id ='querycountId' ><div>
</body>
</html>