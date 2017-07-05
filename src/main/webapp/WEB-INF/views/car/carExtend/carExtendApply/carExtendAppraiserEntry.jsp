<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<title>展期评估师录入</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendDetailsCheck.js"></script>
<script src="${context}/js/car/carExtend/carExtendDetailsCheck.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<meta name="decorator" content="default" />
<script type="text/javascript">
function queryuploadForm(){
	$("#submitConsultBtn").removeAttr("disabled");
}
jQuery.validator.addMethod(
		"moreZero",function(value,element,params){
			if(value > 0){
				return true;
			}else{
				return false;
			}
		},"输入的数字要大于零");
		
//车架号验证
jQuery.validator.addMethod("frameNumberCheck", function(value, element) {
    var length = value.length;
    var fremeNum = /^[a-zA-Z0-9\u4e00-\u9fa5]{17}$/;
    return this.optional(element) || (length == 17 && fremeNum.test(value));
}, "只能输入17位的中文、英文和数字！");
//车牌号码的验证
jQuery.validator.addMethod("plateNum", function(value, element) {
    var length = value.length;
    var plateNumber = /^[A-Z]{1}[A-Z0-9]{5}$/;
    var flag = false;
    if (length == 7) {
    	var str1 = new Array("京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "学");
    	for (var i = 0; i < str1.length; i++) {
	    	if (value.substr(0, 1) == str1[i]) {
	    		flag = true;
	    		value = value.substr(1, length);
	    		break;
	    	} else {
	    		continue;
	    	}
	    }
    }
    return this.optional(element) || (flag && plateNumber.test(value));
}, "车牌格式，例：京A88888");
		//clivtaHideOrShow
$(document).ready(function(){
	if($("#productType").val() == "CJO2" || $("#productType").val() == "CJO3" ){
		$("#commericialHideOrShow").hide();
	} else {
		$("#commericialHideOrShow").show();
	}
	//展期评估金额的验证
	/* $("#extensionAssessAmount").bind('blur',function(){
		$.ajax({
			   type: "POST",
			   url: ctx+"/car/carExtend/carExtendAppraiser/amountCheck",
			   data: {'extensionAssessAmount':$("#extensionAssessAmount").val(),
				   'applyId':$("#applyId").val() 
			   },
			   success: function(data){
				   if(data != "true"){
					   $.jBox.error(data, '提示信息');
				   }
			   }
			});
	}); */
	
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		var url = ctx+"/car/carExtend/carExtendAppraiser/appraiserGiveUp";					
		art.dialog.confirm("是否展期放弃?",function(){
			$("#giveUpForm").attr('action',url);
			$("#giveUpForm").submit();
		});
	});
	
});

$(function(){
	//提交验证
	$("#submitConsultBtn,#uploadSubmit").bind('click',function(){
	 var flag  = $("#inputForm_1").validate({
			onfocusout: true, 
			rules : {
				extensionAssessAmount:{
					number:true,
					moreZero:true,
					max:9999999999999
					},
				extensionSuggestAmount:{
					number:true,
					moreZero:true,
					max:9999999999999
					},
				mileage:{
				    number:true,
				    max:200000
				    },
				modifiedSituation:{
					maxlength:200
				},
				outerInspection:{
					maxlength:200
				},
				illegalAccident:{
					maxlength:200
				},
				vehicleAssessment:{
					maxlength:200
				}
			},
			messages : {
				extensionAssessAmount:{
					number:"请输入合法的数字",
					max:"金额位数不能超过13位"
						},
				extensionSuggestAmount:{
					number:"必须输入合法的数字",
					max:"金额位数不能超过13位"
						},
				mileage:{number:"必须输入合法的数字",
						 max:"小于200000的数字"
						},
						modifiedSituation:{
							maxlength:"200字以内"
						},
						outerInspection:{
							maxlength:"200字以内"
						},
						illegalAccident:{
							maxlength:"200字以内"
						},
						vehicleAssessment:{
							maxlength:"200字以内"
						}
			}
		}).form();
	//展期建议借款金额必须小于上次合同金额的判断
	var flag1 = false;
		var a = $("#extensionAssessAmount").val();
		var b = $("#extensionSuggestAmount").val();
		var d = $("#auditAmount").val(); 
	    if(b != null && d != null){
			if(eval(b) > eval(d)){
				$.jBox.error('建议借款金额必须小于等于上次合同金额', '提示信息');
				flag1 = false;
				return;
			} else {
				flag1 = true;
			}
		}; 
		/* if(c != null && d != null){
			if(eval(c) > eval(d)){
				$.jBox.error('展期评估金额必须小于等于上次合同金额', '提示信息');
				flag1 = false;
			} else {
				flag1 = true;
			}
		} */
		//将金额中的","去除
		var suggestLoanAmount = $("#suggestLoanAmount").val();
		var similarMarketPrice = $("#similarMarketPrice").val();
		var reg=new RegExp(",","g");
		if(suggestLoanAmount != null){
			$("#suggestLoanAmount").val(suggestLoanAmount.replace(reg, ""));
		}
		if(similarMarketPrice != null){
			$("#similarMarketPrice").val(similarMarketPrice.replace(reg, ""));
		}
		 if(flag && flag1){
			 art.dialog.confirm("是否确认提交?",function(){
				$("#inputForm_1").submit();
			});
		} 
	});
});
//上传影像资料插件
imageUrl = '${workItem.bv.imageUrl}';
$(document).ready(function(){
	$("#ImageData").click(function(){
		art.dialog.open(imageUrl,{/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', { */
		title: "客户影像资料",	
		top: 80,
		lock: true,
		    width: 1000,
		    height: 450,
		},false);	
	});
	$("#uploadSure").click(function(){
		art.dialog.confirm("是否已确认上传?",function(){
			$("#inputForm_1").submit();
		});
	});
});
</script>

</head>
<body>
<input type="hidden" value="0" id="hasUpload"></input>
<form id="giveUpForm" method="post">
	<input type="hidden" value="${workItem.token}" name="token"></input>
	<input name="menuId" type="hidden" value="${param.menuId}" />
	<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
	<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	<input type="hidden" value="${workItem.bv.dictProductType}" id="productType"></input>
	<input id="stepName" type="hidden" value="${workItem.stepName}" name="stepName"></input>
	<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	<input id="applyId" name="applyId" type="hidden" value="${workItem.bv.applyId}"/>
</form>
 <form id="inputForm_1" class="form-horizontal" name="inputForm_1" action="${pageContext.request.contextPath}${fns:getAdminPath()}/car/carExtend/carExtendAppraiser/appraiserSubmit" method="post" >
 <div>
	<input type="hidden" value="${workItem.token}" name="token"></input>
	<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
	<input name="menuId" type="hidden" value="${param.menuId}" />
	<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
 	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
 </div>
 <div class="tright pr10 pt5 pb5" >
 	 <input type="button" class="btn btn-small" id="ImageData" value="上传资料">
 	 <input  type="button" class="btn btn-small" onclick="queryuploadForm()"  value="确认上传" />
 	 <input onclick="showExtendLoanInfo('${workItem.bv.applyId}')" type="button" class="btn btn-small" value="查看" />
     <input type="button" onclick="showCarLoanHis('${workItem.bv.loanCode}')" class="btn btn-small" value="历史">
     <input type="button" id="giveUpBtn" class="btn btn-small"  value="展期放弃"></input></div>
 <h2 class=" f14 pl10 ">客户基本信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
<td style="width:32%">
  <label class="lab">客户姓名：</label>${workItem.bv.customerName}
</td>
<td style="width:32%">
  <label class="lab">证件类型：</label>身份证<%-- ${workItem.bv.dictCertType} --%>
</td>
<td style="width:32%">
   <label class="lab">证件号码：</label>${workItem.bv.customerCertNum}
   <span id="blackTip" style="color: red;"></span>
</td>
   </tr>
 </table>
</div>
 <h2 class=" f14 pl10 mt20">车辆信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
 <tr>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>车辆品牌与型号：</label><input name="vehicleBrandModel" class="required input_text178" 
    	type="text" value="${workItem.bv.vehicleBrandModel}" maxlength="20"
    	<c:if test="${workItem.bv.vehicleBrandModel != null}">readonly</c:if> />
	<input id="applyId" name="applyId" type="hidden" value="${workItem.bv.applyId}"/>
   </td>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>车牌号码：</label><input name="plateNumbers" class="required input_text178 plateNum" 
    	type="text" value="${workItem.bv.plateNumbers}" 
    	<c:if test="${workItem.bv.plateNumbers != null}">readonly</c:if> />
   </td>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>权属证书编号：</label><input name="ownershipCertificateNumber" class="required input_text178" 
    	type="text" value="${workItem.bv.ownershipCertificateNumber}" 
    	<c:if test="${workItem.bv.ownershipCertificateNumber != null}">readonly</c:if> />
    </td>
 </tr>
 <tr>
 	<td style="width:32%">
    <label class="lab"><span class="red">*</span>前次建议借款金额：</label><input id="suggestLoanAmount" name="suggestLoanAmount" class="required input_text178" 
    	type="text" value='<fmt:formatNumber value="${workItem.bv.auditAmount == null ? 0 : workItem.bv.auditAmount }" pattern="#,##0.00" />' 
    	<c:if test="${workItem.bv.auditAmount != null}">readonly</c:if> />元
   </td>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>同类市场价格：</label><input id="similarMarketPrice" name="similarMarketPrice" class="required input_text178" 
    	type="text" value='<fmt:formatNumber value="${workItem.bv.similarMarketPrice == null ? null : workItem.bv.similarMarketPrice }" pattern="#,##0.00" />' 
    	 />元
   </td>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>评估师姓名：</label><input name="appraiserName" class="required input_text178" 
    	type="text" value="${fns:getUser().name}" readonly />
   </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>车架号：</label><input name="frameNumber" class="required input_text178 frameNumberCheck" 
    	type="text" value="${workItem.bv.frameNumber}" maxlength="17"
    	<c:if test="${workItem.bv.frameNumber != null}">readonly</c:if> />
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>车辆厂牌型号：</label><input name="vehiclePlantModel" class="required input_text178" 
    	type="text" value="${workItem.bv.vehiclePlantModel}" 
    	<c:if test="${workItem.bv.vehiclePlantModel != null}">readonly</c:if> />
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>首次登记日期：</label><input id="firstRegistrationDate" name="firstRegistrationDate" type="text" class="required input_text70 Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" value="<fmt:formatDate value='${workItem.bv.firstRegistrationDate}' pattern="yyyy-MM-dd"/>"
       <c:if test="${workItem.bv.firstRegistrationDate != null}">readonly</c:if> />
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>出厂日期：</label><input id="factoryDate" name="factoryDate" type="text" class="required input_text70 Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" value="<fmt:formatDate value='${workItem.bv.factoryDate}' pattern="yyyy-MM-dd"/>"
       <c:if test="${workItem.bv.factoryDate != null}">readonly</c:if> />
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>排气量：</label><input name="displacemint" class="required input_text178" 
    	type="text" value="${workItem.bv.displacemint}" 
    	 />升
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>车身颜色：</label><input name="carBodyColor" class="required input_text178" 
    	type="text" value="${workItem.bv.carBodyColor}" 
    	<c:if test="${workItem.bv.carBodyColor != null}">readonly</c:if> />
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>变速器：</label><input name="variator" class="required input_text178" 
    	type="text" value="${workItem.bv.variator}" 
    	<c:if test="${workItem.bv.variator != null}">readonly</c:if> />
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>发动机号：</label><input name="engineNumber" class="required input_text178" 
    	type="text" value="${workItem.bv.engineNumber}" 
    	<c:if test="${workItem.bv.engineNumber != null}">readonly</c:if> />
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>过户次数：</label><input name="changeNum" class="required input_text178" 
    	type="text" value="${workItem.bv.changeNum}" 
    	<c:if test="${workItem.bv.changeNum != null}">readonly</c:if> />次
    </td>
</tr>
<tr>
  <td style="width:32%">
  <input id="auditAmount" type="hidden" value="${workItem.bv.auditAmount}"/>
    <label class="lab"><span class="red">*</span>展期评估金额：</label>
    <input id="extensionAssessAmount" name="extensionAssessAmount" class="required input_text178 moreZero" 
    	type="text" maxLength="8" value="<fmt:formatNumber value="0" pattern="#,##0.00" />" />元
   </td>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>展期建议借款金额：</label>
    <input id="extensionSuggestAmount" name="extensionSuggestAmount" class="required input_text178 moreZero" 
    	type="text" maxLength="8" value="<fmt:formatNumber value="0" pattern="#,##0.00" />" />元
   </td>
   	<td style="width:32%">
    <label class="lab"><span class="red">*</span>车年检到期日：</label>
    <input id="annualCheckDate" name="annualCheckDate" type="text" class="required input_text70 Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.annualCheckDate}' pattern="yyyy-MM-dd"/>"/>
    </td>

</tr>
	<tr id="clivtaHideOrShow">
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>交强险到期日：</label>
    <input id="strongRiskMaturityDate" name="strongRiskMaturityDate" type="text" class="required input_text70 Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.strongRiskMaturityDate}' pattern="yyyy-MM-dd"/>"/>
    </td>
	 <td style="width:32%">
	    <label class="lab"><span class="red">*</span>交强险保险公司：</label>
	    <input  name="clivtaCompany" type="text" class="required input_text178" size="30" value="${workItem.bv.clivtaCompany}" />
	    </td>
	    <td style="width:32%">
	    <label class="lab"><span class="red">*</span>交强险单号：</label>
	    <input  name="clivtaNum" type="text" class="required input_text178" size="30" value="${workItem.bv.clivtaNum}" />
	  </td>
    </tr>
    <c:if test="${workItem.bv.dictProductType != null&&workItem.bv.dictProductType=='CJ01'}">
    <tr id="commericialHideOrShow">
  	<td style="width:32%">
    <label class="lab"><span class="red">*</span>商业险到期日：</label>
    <input id="commercialMaturityDate" name="commercialMaturityDate" type="text" class="required input_text70 Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" value="<fmt:formatDate value='${workItem.bv.commercialMaturityDate}' pattern="yyyy-MM-dd"/>"/>
   </td>
	    <td style="width:32%">
	    <label class="lab"><span class="red">*</span>商业险保险公司：</label>
	    <input  name="commericialCompany" type="text" class="required input_text178" size="30" value="${workItem.bv.commericialCompany}" />
	    </td>
	    <td style="width:32%">
	    <label class="lab"><span class="red">*</span>商业险单号：</label>
	    <input  name="commericialNum" type="text" class="required input_text178" size="30" value="${workItem.bv.commericialNum}" />
	    </td>
   </tr>
   </c:if>
   <tr>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>表征里程：</label>
    <input id="mileage" name="mileage" class="required input_text178" type="text"  maxLength="9" value="${workItem.bv.mileage}"/>公里
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>改装情况：</label> 
    <textarea id="modifiedSituation" name="modifiedSituation" class="required input_text178" style="width:350px;height:50px;">${workItem.bv.modifiedSituation}</textarea>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>外观检测：</label>
    <textarea id="outerInspection" name="outerInspection"  class="required input_text178" style="width:350px;height:50px;">${workItem.bv.outerInspection}</textarea>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>违章及事故情况：</label>
    <textarea id="illegalAccident" name="illegalAccident" class="required input_text178" style="width:350px;height:50px;">${workItem.bv.illegalAccident}</textarea>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>车辆评估意见：</label>
    <textarea id="vehicleAssessment" name="vehicleAssessment" class="required input_text178" style="width:350px;height:50px;">${workItem.bv.vehicleAssessment}</textarea>
    </td>
</tr>
 </table>
</div>
<div class="tright" style="margin-top: 10px;margin-right: 10px;margin-bottom: 10px;">
	<input type="button" id="submitConsultBtn" disabled="disabled" class="btn btn-primary" value="提交"></input>
	<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
	onclick="JavaScript:history.go(-1);"></input> </div>
</form>
</body>
</html>