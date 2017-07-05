<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资料上传</title>
<meta name="decorator" content="default" />
 <script src="../../../js/common.js" type="text/javascript"></script>
<!--   模态弹窗 文件引入 Statr -->
<!-- <script src="../../../static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script> -->
<script src="../../../static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="../../../static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">
  function uploadbox(){
	  
	  $('#single').modal('toggle').css({
			width : '50%',
			'margin-left' : function() {
				return -($(this).width() / 2);
			}
		});	
  }
  
  function checked(){
	  var wfjl1= $('#wfjl1').val();// document.getElementById("wfjl1").value;
	  var wfjl2=  $('#wfjl2').val();//document.getElementById("wfjl2").value;
	
	  if(wfjl1 == ''){
		  top.$.jBox.alert("请输入外访距离",'提示');
		  return ;
	  }
	  if(wfjl1 != wfjl2){
		  top.$.jBox.alert("您两次输入的距离不一致，请检查!",'提示');
		  return ;
	  }
	  $('#itemDistance').val(wfjl1);
	//  document.getElementById("itemDistance").value = wfjl1 ;
	   $('#loanApplyForm').submit();
  }
  
  function giveUp(){
		 var result= confirm("是否确认放弃");
		 if(result){
		    $('#response').val('TO_REJECT');
		    $('#loanApplyForm').submit();
		 }
	  }
</script>
</head>
<body>

	<div class="body_r">
		<div class="pt10 pb10 tright pr30 title" style="text-align: right;">
		<form id="loanApplyForm" action="${ctx}/loan/apply/outTaskUpload" method="post">
          <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
          <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
          <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
          <input type="hidden" value="${workItem.token}" name="token"></input>
          <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
          <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
          <input type="hidden" value="${loanInfo.applyId}" name="applyId"></input>
          <input type="hidden" id="itemDistance" name="itemDistance"></input>
          
        <!--   <input type="hidden" name="response" id="response"/> -->
          </form> 
          	<button class="btn btn-small" onclick="giveUp();">客户放弃</button>
          	<button class="btn btn-small" onclick="alert('下载外访清单');">下载外访清单</button>
			<button class="btn btn-small" onclick="uploadbox();" >外访资料上传确认</button>
			<button class="btn btn-small" onclick="showLoanHis(${loanInfo.applyId})" >历史</button>
			<button class="btn btn-small" onclick="history.go(-1);">返回</button>
		</div>
		<c:set var="bview" value="${workItem.bv}"/>
		<div class="control-group">
			<h2 class="pt5 f14 pl10">客户信息</h2>
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
			
				<tr>
					<td><label class="lab">客户编号：</label>${loanInfo.loanCode}</td>
					<td><label class="lab">客户姓名：</label>${loanInfo.loanCustomerName}</td>
<%-- 					<td><label class="lab">性别：</label>${loanInfo.customerSex}</td> --%>
				</tr>
			</table>
		</div>
		
		<!-- 外访距离输入弹出层 -->
    	<div id="single" class="modal hide fade"  >
    		<table class="table  table-bordered table-condensed table-hover">
                <tr>
                    <td width="200">外访距离:</td>
                    <td><input id="wfjl1">公里</td>
                </tr>
				<tr>
		            <td>确认外访距离:</td>
                    <td><input id="wfjl2">公里</td>
				</tr>
            </table>
            <div>
				<button id="sub" onclick="checked()">确认</button>
			</div>
		</div>		
		<div>
		<iframe src="http://211.157.161.202:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111" width="100%" height="800"></iframe>
		</div>
		<!-- <div class="tcenter mt20" style="text-align: right;">
			<a href="#" class="more">更多>></a>
		</div> -->

	</div>
	 
	
</body>
</html>