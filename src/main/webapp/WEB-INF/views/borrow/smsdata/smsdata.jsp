<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>短信发送页面</title>
<script type="text/javascript" src="${context}/js/payback/paybacksplit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function centerReductApply(){
	
	        art.dialog.confirm('确认发送？', 
			    function () {
			    	 var dialog = art.dialog({
					      content: '推送中。。。',
					      cancel:false,
					      lock:true
				 	 });
				     $("#CenterapplyPayForm").attr("action",ctx+"/borrow/smsdata/loanSmsBatchSend");
				     $("#CenterapplyPayForm").submit();
			  }, 
			    function () {
			      // art.dialog.tips('');
			  });
}
$(function(){
	   $("#queryCount").click(function(){
		   
			  $.ajax({  
				    type : "POST",
					url :ctx+"/borrow/smsdata/queryCount",
					datatype : "json",
					async: false,
					success : function(data){
						if(data){
							 $("#querycountId").html("<p >数据条数："+data+"</p>")
						 }
					 },
					error: function(){
						art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
					}
			  });
	      })
	})

	
	function stopOrStart(flag){
	  $.ajax({  
		    type : "POST",
			url :ctx+"/borrow/smsdata/stopSms",
			datatype : "json",
			data : {"flag":flag},
			async: false,
			success : function(data){
				if(data){
					if(flag=='1'){
						art.dialog.alert("停止成功！！！");
					}
					if(flag=='2'){
						art.dialog.alert("启动成功！！！");
					}
				
				 }
			 },
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
	  });
}
</script>
<style>
body{TEXT-ALIGN: center;}
#querycountId{ MARGIN-RIGHT: auto;
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
          <form method="post" action="${ctx}/borrow/pushdata/ChangJieSignService" id="CenterapplyPayForm">
		      <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		       <td><label class="lab">发送条数：</label>
		                   <input type="text" class="input_text178"  name="quantity" value="${paramMap.quantity}"></td>
					    </td>
		       </table>
          </form>
          <div class="tright pr30 pb5 ">
					<button class="btn btn-primary" onclick="centerReductApply()">提交</button>
					<button class="btn btn-primary"  id ="queryCount">查询条数</button>
					<button class="btn btn-primary"  onclick = "stopOrStart('1')">停止发送</button>
					<button class="btn btn-primary"  onclick = "stopOrStart('2')">开始发送</button>
	     </div>
	 </div>
	 <div id ='querycountId' ><div>
</body>
</html>