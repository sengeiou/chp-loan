<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>集中数据推送页面</title>
<script type="text/javascript" src="${context}/js/payback/paybacksplit.js"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script src="../../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<link media="screen" rel="stylesheet" href="../../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
function centerReductApply(){
	
	        art.dialog.confirm('确认推送？', 
			    function () {
			    	 var dialog = art.dialog({
					      content: '推送中。。。',
					      cancel:false,
					      lock:true
				 	 });
				     $("#CenterapplyPayForm").attr("action",ctx+"/borrow/pushdata/pushRun");
				     $("#CenterapplyPayForm").submit();
			  }, 
			    function () {
			      // art.dialog.tips('');
			  });
}
$(function(){
	   $("#queryCount").click(function(){
		   
		      var  yearReplayDay = $("#search_yearReplayDay").val();
		      var   tlSign  = $("#searchtlSign").val();
			  $.ajax({  
				    type : "POST",
				    data : {'monthPayDay':yearReplayDay},
					url :ctx+"/borrow/pushdata/queryCount",
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
		            <tr>
						 <td><label class="lab">还款日（年月日）：</label><input name="monthPayDay" id="search_yearReplayDay"  type="text" readonly="readonly" maxlength="40" class="input_text70 Wdate"
					       value="<fmt:formatDate value="${paramMap.monthPayDay}" pattern="yyyy-MM-dd"/>"
					       onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,maxDate:'#F{$dp.$D(\'search_yearReplayDay\')}'});" style="cursor: pointer"/>
					     </td>
		            </tr>
		        </table>
          </form>
          <div class="tright pr30 pb5 ">
					<button class="btn btn-primary" onclick="centerReductApply()">提交</button>
					<button class="btn btn-primary"  id ="queryCount">查询条数</button>
	     </div>
	 </div>
	 <div id ='querycountId' ><div>
</body>
</html>