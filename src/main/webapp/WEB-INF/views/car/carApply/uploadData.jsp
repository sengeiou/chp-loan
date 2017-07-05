<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资料上传</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/car/apply/carUploadInformation.js"></script>
<style type="text/css">
.textarea_refuse {
    width: 370px;
}
</style>
<script type="text/javascript">

$(document).ready(function(){
	// 查看影像资料
	$("#carUpLoad").click(function(){
		art.dialog.open(${workItem.bv.imageUrl}/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111',  */{
		title: "上传影像资料",	
		top: 80,
		lock: true,
		    width: 1000,
		    height: 450,
		},false);	
	});
	
	var text = $("#remark").val();
	var counter = text.length;
	$(".textareP").find("var").text(200-counter);
	
	$("#remark").keyup(function() {
		var text = $("#remark").val();
		var counter = text.length;
		$(".textareP").find("var").text(200-counter);
	});
	
	//必填验证
	$("#sendBack").bind('click',function(){
		$("#loanApplyForm").validate({
			onclick: true, 
			rules:{
				backNode: {required:true}
			},
			messages: {
				backNode: {required:"请选择退回节点"}
			}
		});
	})
	
})
</script>
</head>

<body>
	<div style=" height:100%" >
	<c:set var="bview" value="${workItem.bv}" />
<div class="control-group r" style="width:39%; height:500px;overflow:auto;">
		<div class="tright">			
			<input id="subBtn" type="button" class="btn btn-small" value="客户资料上传确认" />
			<input id="giveUpBtn" type="button" class="btn btn-small" value="客户放弃" />
			<input  class="btn btn-small" onclick="showCarLoanHis('${workItem.bv.loanCode}')"  type="button" value="历史" />
			<input onclick="showCarLoanInfo('${workItem.bv.loanCode}')" type="button" class="btn btn-small" value="查看" />
			<button  class="btn btn-small"  data-target="#back_div" data-toggle="modal" name="back" id="sendBack"  />退回</button>
			<input type="button" class="btn btn-small" id="handleCheckRateInfoCancel" value="返回"
			onclick="JavaScript:history.go(-1);"></input> 
		</div>
		<h2 class="f14 pl10 mt20">客户信息</h2>
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td olspan="2"><label class="labl">客户编号：</label>${workItem.bv.customerCode}</td>
			    </tr>	
			    <tr>
			    	<td><label class="labl">客户姓名：</label>${workItem.bv.customerName}</td>	
					<td><label class="labl">性别：</label>${workItem.bv.customerSex}</td>
			   	</tr>
			   	<tr>	
			   	    <td><label class="labl">证件类型：</label>${workItem.bv.dictCertType}</td>
					<td><label class="labl">证件号码：</label>${workItem.bv.customerCertNum}</td>
				</tr>
				<tr>
					<td colspan="2">
						<label class="labl">证件有效期：</label>
						<fmt:formatDate value='${workItem.bv.idStartDay}' pattern="yyyy-MM-dd"/>~
						<c:if test="${workItem.bv.isLongTerm==1 }">长期</c:if>
						<c:if test="${workItem.bv.isLongTerm==null && workItem.bv.idEndDay!=null}">
							<fmt:formatDate value='${workItem.bv.idEndDay}' pattern="yyyy-MM-dd"/></c:if>
					</td>
				</tr>
				<tr>
					<td><label class="labl">学历：</label>${workItem.bv.dictEducation}</td>
					<td><label class="labl">婚姻状况：</label>${workItem.bv.dictMarryStatus}</td>					
				</tr>
				
				       <!-- 共借人添加 Wangj 2016-05-21 -->
						<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
							<tr>
								<td><label class="labl">共借人姓名：</label>${cobos.coboName}</td>
								<td><label class="labl">共借人身份证号：</label>${cobos.certNum}</td>
							</tr>
							<tr>
								<td><label class="labl">共借人地址：</label><sys:carAddressShow detailAddress="${cobos.dictHouseholdView}"></sys:carAddressShow></td>
								<td></td>
							</tr>

						</c:forEach>				
								
			</table>
		</div>

	<!-- 影像插件 -->
	<div class="l" style="width:60%">
		<!-- src="http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111" -->
		<iframe src="${workItem.bv.imageUrl}" width="100%" height="500">
		</iframe>
	</div>
	</div>
	<!-- 工作流提交表单 -->
	<form id="loanApplyForm" method="post">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input name="menuId" type="hidden" value="${param.menuId}" />
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
		<input type="hidden" value="${workItem.bv.loanCode }" name="loanCode"></input>
		<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
	
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
    		<td><lable class="labl"><span style="color: red;">*</span>退回至流程节点：</lable>
    		  <select id="backNode" name="backNode" class="required">
	<%--  <c:forEach items="${fns:getDictList('jk_chk_back_reason')}" var="card_type">
	<option value="${card_type.value}">${card_type.label}</option>
 		 </c:forEach> --%>
 		 <option value="">请选择退回节点</option>
 		 <option value="0">评估师录入</option>
 		 <option value="1">车借申请</option>
 		 </select>
 	
 		 </td>
   	 </tr>
        	  	  <tr>
	<td><lable class="labl"><span style="color: red;">*</span>退回原因：</lable>
	<textarea rows="" cols="" class="textarea_refuse required" id="remark" name="remark" maxlength="200"></textarea>
	<span class="textareP">剩余<var style="color:#C00">--</var>字符</span></td>
	</td>
	</tr>
      	  </table>
 	</div>
 	<div class="modal-footer">
 	<button id="backSure" class="btn btn-primary" aria-hidden="true" >确定</button>
 	<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
 	</div>
	</div>
	</div>

	</div>
	</form>
</body>
</html>