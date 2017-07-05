<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/grant/grantSureDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
<script type="text/javascript">
$(document).ready(function() {
			
	// 点击返回
	 $("#goBackBtn1").click(function(){
		 window.location.href=ctx+"/channel/goldcredit/grantSure/fetchTaskItems";
	 })	
	// 点击金信取消
		$("#cancleJxBtn1").click(function(){
			var sure=confirm("确定对单子进行金信取消么？");
			var loanMarking=' ';
			if(sure){
	          updFlag(loanMarking);
	          $(this).prop("type",'hidden');
	          $("#addP2PBtn").prop("type",'button');
			  $("#addJxBtn").prop("type",'button');
			  window.location.href=ctx+"/channel/goldcredit/grantSure/fetchTaskItems";
				
			}else{
				return;
			}
		});	
	//驳回申请
		$("#btnRefuseApply").click(function () {
			var $param = $("#param").serialize();
			art.dialog({
				title : '消息',
				content : '确认驳回申请？',
				opacity : .1,
				lock : true,
				ok : function() {
					$.ajax({
						type : 'post',
						url : ctx+'/channel/goldcredit/grantSure/grantSureRejectBack',
						data :$param,
						cache : false,
						dataType : 'json',
						async : false,
						success : function(result) {
							if(result){
								art.dialog.alert("驳回申请成功");
								// 跳转到列表页面
							}else{
								art.dialog.alert("驳回申请失败");
								// 跳转到列表页面
							}
							window.location.href=ctx+'/channel/goldcredit/grantSure/fetchTaskItems';
						},
						error : function() {
							art.dialog.alert('请求异常');
						}
					});
				},
				cancel : true
			});
			
		});	

		// 点击退回
		$("#grantBackBtn").click(function(){	
		    $(this).attr("data-target","#sureBack");
		});
		// 点击放款确认
		$("#grantSureBtn1").click(function(){
			var response="TO_DELIVERY_CARD";
			var grant=confirm("确定对单子进行放款确认？");
			var param=$("#param").serialize();
			if(param!=null && param!=undefined){
				param+='&response='+response;
			}
			if(grant){
				// 放款确认，更改单子的借款状态以及对表进行查询，同时对历史表进行插入，
				$.ajax({
	    			type : 'post',
	    			url : ctx + '/channel/goldcredit/grantSure/dispatchFlowStatus',
	    			data :param,
	    			cache : false,
	    			dataType : 'json',
	    			async : false,
	    			success : function(result) {
	    				var message = '${message}';
	    				/* if (result)
	    					art.dialog.alert("放款确认成功！");
	    				else 
	    					art.dialog.alert("放款确认失败！"); */
		    		   if(!result){
		    		    	if (message) {
			    				art.dialog.alert(message);
			    			}
		    		    }else{
		    		    	art.dialog.alert("放款确认成功！");
		    		    }
		    		    url=ctx + '/channel/goldcredit/grantSure/fetchTaskItems';
		    		    window.location.href=url;
	    			},
	    			error : function() {
	    				 art.dialog.alert('请求异常，放款确认失败！');
	    			}
	    			 
	    		});
			}
			else{
				return;
			}
		});
		// 点击确认退回原因,需要指定退回的节点
		$("#GCgrantSureBackBtn").click(function(){
			// 获得填写的退回原因，调用流程退回的处理方法，更新单子的状态以及合同表的待放款确认退回原因
			var grantSureBackReason= $("#sel").find("option:selected").text();
			// 要退回的流程节点,合同审核
			var response="TO_CONTRACT_CHECK";
			var param=$("#param").serialize();
			if(grantSureBackReason=="其他"){
				grantSureBackReason=$("textarea").text();
				if(grantSureBackReason==null||grantSureBackReason==""){
					art.dialog.alert("退回原因不能为空！");
					return;
				}
			}
			if(param!=null && param!=undefined){
				param+='&response='+response;
			}
			grantSureBack(param,grantSureBackReason);
		});
		 var frozenFlag = '${workItem.bv.frozenFlag}';
		 if (!frozenFlag) {
			 $("#btnRefuseApply").hide();
		 }
		});
		
		
//退回方法声明
function grantSureBack(param,grantSureBackReason){
	if(param!=null && param!=undefined){
		param+='&grantSureBackReason='+grantSureBackReason;
	}
	$.ajax({
		type : 'post',
		url : ctx+'/channel/goldcredit/grantSure/grantSureBack',
		data :param,
		cache : false,
		dataType : 'json',
		async : false,
		success : function(result) {
			if(result){
				art.dialog.alert("单子退回成功");
			}else{
				art.dialog.alert("单子退回失败");
			}
			// 跳转到列表页面
			window.location.href=ctx+'/channel/goldcredit/grantSure/fetchTaskItems';
		},
		error : function() {
			art.dialog.alert('请求异常');
		}
	});
}
</script>
</head>
<body>

   
	<div class="title mt5">
	 <table class="table table-striped table-bordered table-condensed" >
      <tr>
	   <td>客户名称：
	  ${workItem.bv.customerName}</td>
       <td>合同编号：
       ${workItem.bv.contractCode}</td>
	   <td>证件号码：
	  ${workItem.bv.customerCertNum}</td>
	   <input type="hidden" value="${workItem.bv.loanFlag}" name="flag">
	 </tr>
	 </table>
	</div>
    <div class="title">
		<div class="pt5 f14 pl10" style="float:left">合同相关文件</div>
		<div style="float:right"class="mb5 mt5">
		<form id="param">
			<input type="hidden"  name="applyId" value='${workItem.bv.applyId}'></input>
			<input type="hidden"  name="wobNum" value='${workItem.wobNum}'></input>
			<input type="hidden"  name="flowName" value='${workItem.flowName}'></input>
			<input type="hidden"  name="token" value='${workItem.token}'></input>
			<input type="hidden"  name="stepName" value='${workItem.stepName}'></input>
			<input type="hidden" id="flowParam" name = "contractCode" contractCode='${workItem.bv.contractCode}' value = "${workItem.bv.contractCode}"></input>
			<%-- <input type="hidden"  name="channelCode" value='${workItem.bv.loanFlag}'></input> --%>
			<input type = "hidden" name = "urgentFlag" value = "${workItem.bv.urgentFlag }"/>
		</form>
		<button class="btn btn-small" id="btnRefuseApply">驳回申请</button>
		
        <button  class="btn btn-small" id="grantSureBtn1">放款确认</button>   
        <input type="button" class="btn btn-small" id="cancleJxBtn1" value="金信取消"></input>
       
        <input type="button" class="btn btn-small" onclick="showLoanHis('${workItem.bv.applyId}')"  value="历史"></input> 
        <button  id="grantBackBtn" role="button" class="btn btn-small"  data-toggle="modal">退回</button> 
    </div>
    </div>
    <div style="height:100%">
        <ul class="ul01" style="height:100%">
            <li class="li01"><a href="#" name="lis">借款协议</a></li>
        	<li class="li01"><a href="#" name="lis">委托和授权书</a></li>
        	<li class="li01"><a href="#" name="lis">信用咨询及管理服务协议</a></li>
        	<li class="li01"><a href="#" name="lis">信合还款事项客户通知书</a></li>
        	<li class="li01"><a href="#" name="lis">还款管理服务说明确认书</a></li>
        </ul>
    </div>
	<div class="btn_next">
	<button class="btn btn-primary"  style="float:right" id="goBackBtn1">返回</button>
	</div>
    
    
     <div class='modal fade'  id="sureBack" style="width:90%;height:90%">
       <div class="modal-dialog" role="document">
	       <div class="modal-content">
           <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="grantSureBack">待放款确认退回</h4>
		    </div>
       <div class="modal-body">
			<table class="table table-striped table-bordered table-condensed">
                <tr>
                    <td align="left"><label>退回原因</label></td>
                    <td align="left">
                      <select id="sel">
                        <option>风险客户</option>
                        <option>客户原因放弃</option>
                        <option>其他</option>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label></td>
                    <td align="left"><textarea  rows="" cols="" style='font-family:"Microsoft YaHei";' >请填写其他退回原因！</textarea></td>
                </tr>
            </table>
		</div>
            <div class="modal-footer"><button class="btn btn-primary" id="GCgrantSureBackBtn">确认</button>
            <button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('sureBack')">取消</button></div>
		</div>
		</div>
		</div>
</body>
</html>