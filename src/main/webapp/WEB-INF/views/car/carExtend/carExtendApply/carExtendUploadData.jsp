<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资料上传</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/car/carExtend/carExtendDetailsCheck.js" type="text/javascript"></script>
<script type="text/javascript">
imageUrl='${workItem.bv.imageUrl}';
$(document).ready(function(){
	// 查看影像资料
	$("#carUpLoad").click(function(){
	art.dialog.open(imageUrl/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111' */, {
	title: "上传影像资料",	
	top: 80,
	lock: true,
	    width: 1000,
	    height: 450,
	},false);	
	});
})
//业务操作js
$(function(){
	// 客户资料上传确认
	$("#subBtn").click(function(){
		var url = ctx + "/car/carExtend/carExtendUpload/uploadData";		
		art.dialog.confirm("是否确认上传?",function(){
			$("#loanApplyForm").attr('action',url);
			$("#loanApplyForm").submit();
		});
	});
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		var url = ctx + "/car/carExtend/carExtendUpload/giveUp";		
		art.dialog.confirm("是否放弃上传?",function(){
			$("#loanApplyForm").attr('action',url);
			$("#loanApplyForm").submit();
		});
	});
	//退回节点
	$("#backSure").bind('click',function(){
		var url = ctx + "/car/carExtend/carExtendUpload/sendBack";
		$("#loanApplyForm").attr('action',url);
		$("#loanApplyForm").submit();
	})
});
function extendReview(loanCode,applyId){
	window.location.href=ctx + "/car/carExtend/carExtendUpload/toCarContract?loanCode=${workItem.bv.loanCode}&applyId=${workItem.bv.applyId}&wobNum=${param.wobNum}&dealType=${param.dealType}&token=${param.token}";
}
</script>
</head>

<body>
	<div style=" height:100%" >
	<c:set var="bview" value="${workItem.bv}" />
<div class="control-group r" style="width:39%; height:500px">
		<div class="tright">			
			<input id="subBtn" type="button" class="btn btn-small" value="确认上传" />
			<input onclick="showExtendLoanInfo('${workItem.bv.applyId}')" type="button" class="btn btn-small" value="查看" />
			<input  class="btn btn-small" onclick="showCarLoanHis('${workItem.bv.loanCode}')"  type="button" value="历史" />
			<button  class="btn btn-small"  data-target="#back_div" data-toggle="modal" name="back" id="sendBack"  />退回</button>
			<input id="giveUpBtn" type="button" class="btn btn-small" value="展期放弃" />
			<input onclick="extendReview()" type="button" class="btn btn-small" value="复核" />
		</div>
		<div class="control-group">
		<h2 class="f14 pl10 mt20">客户基本信息</h2>
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td colspan="2"><label class="labl">合同编号：</label>${workItem.bv.contractCode}</td>
			    </tr>
			    <tr>
					<td><label class="labl">主借人姓名：</label>${workItem.bv.customerName}</td>
					<td><label class="labl">性别：</label>${workItem.bv.customerSex}</td>
			    </tr>
			    <tr>
					<td><label class="labl">身份证号：</label>${workItem.bv.customerCertNum}</td>
			    </tr>
			    <c:forEach items="${workItem.bv.carLoanCoborrowers}" var="cobo">
			    <tr>	
					<td><label class="labl">共借人：</label>${cobo.coboName}</td>
					<td><label class="labl">身份证号：</label>${cobo.certNum}</td>
			   	</tr>
			   	</c:forEach>
			</table>
			<div class="tright">
				<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="返回"
				onclick="JavaScript:history.go(-1);"></input> 
			</div>
		</div>

	</div>
	<!-- 影像插件 -->
	<div class="l" style="width:60%">
		<iframe src="${workItem.bv.imageUrl}"
			width="100%" height="500">
		</iframe>
	</div>
	<!-- 工作流提交表单 -->
	<form id="loanApplyForm" method="post">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input name="menuId" type="hidden" value="${param.menuId}" />
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
		<input type="hidden" value="${workItem.bv.loanCode }" name="loanCode"></input>
	
	<!--退回弹框  -->
    <div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
	<div class="modal-dialog" role="document">
	<div class="modal-content">
	<div class="modal-header">
   	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>

	<div class="l">
         		 <h4 class="pop_title">退回</h4>
       	 	</div>
 	</div>
 	<div class="modal-body">
  	  <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
        	  	  <tr>
	<td><lable class="labl">退回原因：</lable>
	<textarea rows="" cols="" class="textarea_refuse" id="remark" name="remark"></textarea>
	</td>
	</tr>
      	  </table>
 	</div>
 	<div class="modal-footer">
 	<button id="backSure" class="btn btn-primary" data-dismiss="modal" aria-hidden="true" >确定</button>
 	<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
 	</div>
	</div>
	</div>
	</div>
	</form>
</body>
</html>