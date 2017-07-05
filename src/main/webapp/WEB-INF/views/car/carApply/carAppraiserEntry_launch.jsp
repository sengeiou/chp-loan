<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title>评估师录入</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="")">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">

//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号有误");
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
//车架号验证
jQuery.validator.addMethod("frameNumberCheck", function(value, element) {
    var length = value.length;
    var fremeNum = /^[a-zA-Z0-9\u4e00-\u9fa5]{17}$/;
    return this.optional(element) || (length == 17 && fremeNum.test(value));
}, "只能输入17位的中文、英文和数字！");
//表征里程验证
jQuery.validator.addMethod("mileageCheck", function(value, element) {
    var length = value.length;
    var mileage = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
    var brand = null;
    if(value>200000){
    	brand=false;
    }else{
    	brand=true;
    }
    return this.optional(element) || (mileage.test(value)&&brand);
}, "只能输入小于200000数字！");

jQuery.validator.addMethod("colourCheck", function(value, element) {
    return this.optional(element) || /^[\u4e00-\u9fa5]{2,5}$/.test(value);
}, "颜色只能为2-5个汉字");
//排气量数字验证
jQuery.validator.addMethod("displacemintCheck", function(value, element) {
    var length = value.length;
    var displacemint = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
    return this.optional(element) ||displacemint.test(value);
}, "只能输入0-9的数字！");

jQuery.validator.addMethod(
		"numberEnglish",function(b,a){
			return this.optional(a)||/^[a-zA-Z0-9]*$/.test(b)
		},"只能输入数字和英文");
jQuery.validator.addMethod(
		"chinese",function(b,a){
			return this.optional(a)||/^[\u4e00-\u9fa5]*$/.test(b)
		},"只能输入汉字");
jQuery.validator.addMethod(
		"color",function(b,a){
			return this.optional(a)||/^[a-zA-Z\u4e00-\u9fa5]*$/.test(b)
		},"只能输入汉字或英文");
jQuery.validator.addMethod(
		"licenseNumber",function(b,a){
			return this.optional(a)||/^[a-zA-Z0-9\u4e00-\u9fa5]*$/.test(b)
		},"只能输入汉字,数字,英文");
jQuery.validator.addMethod(
		"brandModel",function(b,a){
			var brand = null;
			if(/^[0-9]*$/.test(b) || /^[a-zA-Z]*$/.test(b)){
				brand = false;
			}else{
				brand = true;
			}
			return this.optional(a)||(/^[a-zA-Z0-9]*$/.test(b) && brand)
		},"只能数字,英文混合输入");
jQuery.validator.addMethod(
		"moreZero",function(value,element,params){
			if(value > 0){
				return true;
			}else{
				return false;
			}
		},"输入的数字要大于零");
$(document).ready(function(){
	//刚进入页面前是否长期的日期判断
	if ($('#longTerm').attr('checked') == true
	|| $('#longTerm').attr('checked') == 'checked') {
	$('#idEndDay').val('');
	$('#idEndDay').attr('disabled',
	true);
	} else {
	$('#idEndDay').attr('disabled',false);
	$('#idEndDay').removeAttr('disabled');
	}
	//点击是否长期后是否长期的日期判断
	$('#longTerm')
	.bind(
	'click',
	function() {
	if ($(this).attr('checked') == true
	|| $(this).attr('checked') == 'checked') {
	$('#idEndDay').val('');
	$('#idEndDay').attr('disabled',
	true);
	$("#idStartDay").addClass("required");
	} else {
	$('#idEndDay').attr('disabled',false);
	$('#idEndDay').removeAttr('disabled');
	$('#idStartDay').removeClass('required');
	}
	});
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		if($("#stepName").val() == "@launch"){
		var url = ctx+"/car/appraiserEntry/giveUpBeforeLanuch";					
		}else{
		var url = ctx+"/car/appraiserEntry/giveUp";
		}
		art.dialog.confirm("是否客户放弃?",function(){
			$("#giveUpForm").attr('action',url);
			$("#giveUpForm").submit();
		});
	});
	
	
	//验证JS
	$("#inputForm_1").validate({
		onclick: true, 
		rules:{
			customerMobilePhone:{
				isMobile:true
			},
			customerCertNum:{
				card:true
			},
		},
		messages : {
			customerEmail:{
				email:"请输入正确邮箱格式"
			},
			customerMobilePhone:{
				isMobile:"请正确填写您的手机号码"
			}
		},
		errorPlacement : function(error, element) {
			if(element.attr("type")=="radio")
			{
				error.appendTo($("#"+element.attr("name")+"Span"));
			}else{
				error.insertAfter(element);
			}
		}
	});		
	
	//提交验证
	

	$("#submitConsultBtn").bind('click',function(){
		 $("#submitConsultBtn").attr("disabled", true);
		if(($("#idStartDay").val() != null && $("#idStartDay").val() != "" ) && ($('#longTerm').attr('checked') != true
				|| $('#longTerm').attr('checked') != 'checked')){
			$("#idEndDay").addClass("required");
		}else{
			$("#idEndDay").removeClass("required");
		}
		//将金额中的","去除
		var suggestLoanAmount = $("#suggestLoanAmount").val();
		var storeAssessAmount = $("#storeAssessAmount").val();
		var similarMarketPrice = $("#similarMarketPrice").val();
		var reg=new RegExp(",","g");
		if(suggestLoanAmount != null){
			$("#suggestLoanAmount").val(suggestLoanAmount.replace(reg, ""));
			$("#suggestLoanAmount2").val(suggestLoanAmount);
		}
		if(storeAssessAmount != null){
			$("#storeAssessAmount").val(storeAssessAmount.replace(reg, ""));
			$("#storeAssessAmount2").val(storeAssessAmount);
		}
		if(similarMarketPrice != null){
			$("#similarMarketPrice").val(similarMarketPrice.replace(reg, ""));
			$("#similarMarketPrice2").val(similarMarketPrice);
		}
		var flag1 = true;
		var flag2 = true;
		var flag3 = true;
		var customerCertNum = $("#customerCertNum").val()
		if(customerCertNum != null && customerCertNum != ''){
			var hasFirst ='0';
			if(customerCertNum!="${workItem.bv.customerCertNum}"){
				hasFirst ='1';
			}
		    $.ajax({
			   type: "POST",
			   url: ctx+"/car/appraiserEntry/vehicleCeiling",
			   data: {'customerCertNum':customerCertNum,'hasFirst':hasFirst},
			   async: false,
			   success: function(data){
				   if(data != "success"){
					   $.jBox.error(data, '提示信息');
					   flag1 = false;
				   }
			   }
			});
		}
		
	var flag  =	$("#inputForm_1").validate({
			rules : {
				customerName:{
					realName:true
					},
				customerCertNum:{
					card:true,
				},
				customerMobilePhone:{
					isMobile:true
				},
				vehiclePlantModel:{
					brandModel:true,
					maxlength:30
				},
				vehicleBrandModel:{
					licenseNumber:true,
					maxlength:30
				},
				 plateNumbers:{
					remote:{
						type : "POST",
						url :  ctx + "/car/appraiserEntry/notRepeatSubmit",
						data:{
							plateNumbers :$("#plateNumbers").val()
						}
					}
				},
				storeAssessAmount:{
					moreZero:true,
					number:true,
					maxlength:8
				},
				suggestLoanAmount:{
					moreZero:true,
					number:true,
					maxlength:8
					},
				similarMarketPrice:{
					number:true,
			
					maxlength:8
				    },
				mileage:{
				    number:true,
				    maxlength:9,
				    max:200000
				    },
				displacemint:{
					number:true,
					 max:9,
					 range:[1,9] 
					},
				changeNum:{
					number:true,
					maxlength:2
				},
				carBodyColor:{
					color:true,
					maxlength:5
				},
				variator:{
					chinese:true,
					maxlength:8
				},
				engineNumber:{
					numberEnglish:true,
					maxlength:20
				},
				ownershipCertificateNumber:{
					numberEnglish:true,
					maxlength:20
				},
				modifiedSituation:{
					maxlength:100
				},
				outerInspection:{
					maxlength:100
				},
				illegalAccident:{
					maxlength:100
				},
				vehicleAssessment:{
					maxlength:100
				},
				remark:{
					maxlength:100
				}
				
			},
			messages : {
				customerName:{
					realName:"姓名只能为2-30个汉字"
				},
				customerCertNum:{
					card:"请确认身份证号录入是否正确"
				},
				customerMobilePhone:{
					isMobile:"请正确填写您的手机号码"
				},
				 plateNumbers:{
					remote:"该车正在车借中使用，请勿重复"
				}, 
				 frameNumber:{
				    licenseNumber:'只能输入汉字,数字,英文',
					maxlength:"车架号必须20位"
				 },
				 engineNumber:{maxlength:"发动机号不能超过20位"},
				suggestLoanAmount:{
					moreZero:'输入的数字要大于零',
					number:"请输入合法的数字",
					maxlength:"金额位数不能超过8位"
						},
				storeAssessAmount:{
					moreZero:'输入的数字要大于零',
					number:"请输入合法的数字",
					maxlength:"金额位数不能超过8位"
				},
				similarMarketPrice:{
					number:"请输入合法的数字",
					maxlength:"金额位数不能超过8位"
						},
				mileage:{
					number:"必须输入合法的数字",
					maxlength:"输入的数字不能大于8位",
					max:"小于20w里程"
					},
				displacemint:{
					number:"请输入合法的数字",
					max:"输入的数字在1~9之间",
					 range:"输入的数字在1~9之间"
						},
				carBodyColor:{
					maxlength:"输入的长度不能大于5",
					color:'请输入中文或英文'
						},
				changeNum:{
					number:"请输入合法的数字",
					maxlength:"请输入数字不能大于10"
						},
				ownershipCertificateNumber:{
					maxlength:"最长可输入20个字符"
				},
				variator:{
					maxlength:"最长可输入8个字符"
				},
				vehiclePlantModel:{
					maxlength:"最长可输入30个字符"
				},
				vehicleBrandModel:{
				  licenseNumber:'只能输入汉字,数字,英文',
					maxlength:'最长可输入30个字符'
				},
				modifiedSituation:{
					maxlength:"100字以内"
				},
				outerInspection:{
					maxlength:"100字以内"
				},
				illegalAccident:{
					maxlength:"100字以内"
				},
				vehicleAssessment:{
					maxlength:"20字以内"
				},
				remark:{
					maxlength:"100字以内"
				}
			}
		}).form();
		if(!flag){
			$("#submitConsultBtn").attr("disabled", false);
			return;
		}
		//建议借款金额必须小于评估金额的判断
		var a = $("#storeAssessAmount").val();
		var b = $("#suggestLoanAmount").val();
		if(a != null && b != null){
			a.replace(",", "");
			b.replace(",", "");
		if(eval(b) > eval(a)){
			$.jBox.error('建议借款金额必须小于等于评估金额', '提示信息');
			flag2 = false;
			}
		}
		//证件有效期的结束日期不可以小于开始日期
		var startDate = $("#idStartDay").val();
		var endDate = $("#idEndDay").val();
		if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){
				art.dialog.alert("证件有效期开始日期不能大于结束日期!",function(){
					$("#idStartDay").val("");
					$("#idEndDay").val("");
				});
				flag3 = false;     
			}
			};
		 if(flag == true && flag1 == true && flag2 == true && flag3 == true){
			//$("#inputForm_1").submit();
			 $.ajax({
				 url:ctx + "/car/appraiserEntry/launchFlow",
				 type:'post',
				 data: $("#inputForm_1").serialize(),
				 success:function(data){
					 windowLocationHref(ctx + "/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog");
				 }
			 });
		} else {
			$("#submitConsultBtn").attr("disabled", false);
			if(suggestLoanAmount != null){
				$("#suggestLoanAmount").val($("#suggestLoanAmount2").val());
			}
			if(storeAssessAmount != null){
				$("#storeAssessAmount").val($("#storeAssessAmount2").val());
			}
			if(similarMarketPrice != null){
				$("#similarMarketPrice").val($("#similarMarketPrice2").val());
			}
		};
	});
	 //输入数字时有千位分割符
	/* $("#suggestLoanAmount,#storeAssessAmount,#similarMarketPrice").bind('keyup',function(){
		var s = $(this).val();
		$(this).val(cc(s));
	}); */
	 $("#customerCertNum").blur(function(){
		 if($(this).val()!=""){
			var hasFirst ='0';
			if($(this).val()!="${workItem.bv.customerCertNum}"){
				hasFirst ='1';
			}
		 	$.ajax({
			   type: "POST",
			   url: ctx+"/car/appraiserEntry/vehicleCeiling",
			   data: {'customerCertNum':$(this).val(),'hasFirst':hasFirst},
			   async: false,
			   success: function(data){
				   if(data != "success"){
					   $.jBox.error(data, '提示信息');
				   }
			   }
			});
		 }
	 });
});
//输入数字时有千位分隔符
function cc(number){
	number = number.replace(/,/g, "");
    var digit = number.indexOf("."); // 取得小数点的位置
    var int = number.substr(0, digit); // 取得小数中的整数部分
    var i;
    var mag = new Array();
    var word;
    if (number.indexOf(".") == -1) { // 整数时
        i = number.length; // 整数的个数
        while (i > 0) {
            word = number.substring(i, i - 3); // 每隔3位截取一组数字
            i -= 3;
            mag.unshift(word); // 分别将截取的数字压入数组
        }
        number = mag;
    } else { // 小数时
        i = int.length; // 除小数外，整数部分的个数
        while (i > 0) {
            word = int.substring(i, i - 3); // 每隔3位截取一组数字
            i -= 3;
            mag.unshift(word);
        }
        number = mag + number.substring(digit);
    }
    return number; 
	} 

</script>

</head>
<body>
<form id="giveUpForm" method="post">
	<input type="hidden" value="${workItem.token}" name="token"></input>
	<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
	<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	<input id="stepName" type="hidden" value="${workItem.stepName}" name="stepName"></input>
	<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	<input id="customerCode" name="customerCode" type="hidden" value="${workItem.bv.customerCode}"/>
	<input id="loanCode" name="loanCode" type="hidden" value="${workItem.bv.loanCode}"/>
</form>
 <form id="inputForm_1" class="form-horizontal" name="inputForm_1" action="${pageContext.request.contextPath}${fns:getAdminPath()}/car/appraiserEntry/launchFlow" method="post" onSubmit="disabledSubmitBtn('submitConsultBtn')">
 <div>
	<input type="hidden" value="${workItem.token}" name="token"></input>
	<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
	<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
 	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	<input type="hidden" name="menuId" value="${param.menuId}"/>
	<input type="hidden" value="${workItem.bv.consTelesalesFlag}" name="consTelesalesFlag" ></input>
	<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
	<input type="hidden" id="suggestLoanAmount2" ></input>
	<input type="hidden" id="storeAssessAmount2" ></input>
	<input type="hidden" id="similarMarketPrice2" ></input>
	<%-- <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId" ></input>
	<input type="hidden" value="${workItem.bv.defToken}" name="defToken" ></input> --%>
 </div>
 <div class="tright pr10 pt5 pb5" >
     <input type="button" onclick="showCarLoanHis('${workItem.bv.loanCode}')" class="btn btn-small" value="历史">
     <input type="button" id="giveUpBtn" class="btn btn-small"  value="客户放弃"></input></div>
 <h2 class=" f14 pl10 ">客户基本信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
<td style="width:32%">
  <label class="lab"><span class="red">*</span>客户姓名：</label>
  <input id="customerName" name="customerName" class="required input_text178" maxLength="30" type="text" value="${workItem.bv.customerName}"/>
  <input id="customerCode" name="customerCode" type="hidden" value="${workItem.bv.customerCode}"/>
  <input type="hidden" id="flag" />
  <input type="hidden" id="message"/>
</td>
<td style="width:32%">
  <label class="lab"><span class="red">*</span>证件类型：</label>
<%--   <select id="dictCertType" name="dictCertType"   class="required select180">
    <option  value="">请选择</option>
    <c:forEach items="${fns:getDictLists('0','com_certificate_type')}" var="item">
	<option value="${item.value}" <c:if test="${workItem.bv.dictCertType==item.label}">
      selected = true 
     </c:if> >${item.label}</option>
	</c:forEach>
   </select> --%>
   身份证
</td>
<td style="width:32%">
   <label class="lab"><span class="red">*</span>证件号码：</label>
   <input id="customerCertNum" name="customerCertNum" class="input_text178 required card" type="text" value="${workItem.bv.customerCertNum}"/>
   <span id="blackTip" style="color: red;"></span>
</td>
   </tr>
   <tr>
<td style="width:32%">
   <label class="lab"><span class="red">*</span>手机号码：</label>
   <input id="customerMobilePhone" name="customerMobilePhone" class="input_text178 required isMobile" type="text" value="${workItem.bv.customerMobilePhone}"/>
   <span id="blackTip" style="color: red;"></span>
</td>
<td style="width:32%">
  <label class="lab">学历：</label>
  <select id="dictEducation" name="dictEducation"  class="select180">
    <option value="">请选择</option>
    <c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
    	<option value="${curEdu.value}"<c:if test="${workItem.bv.dictEducation==curEdu.label}">
    	selected = true</c:if> >${curEdu.label}</option>
    </c:forEach>
   </select>
</td>
<td style="width:32%">
  <label class="lab">婚姻状况：</label>
  <select id="dictMarryStatus" name="dictMarryStatus" class="select180">
    <option value="">请选择</option>
	<c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
  		<option value="${item.value}"
   		<c:if test="${workItem.bv.dictMarryStatus==item.label}"> 
    		selected=true 
   		</c:if> >${item.label}</option>
     </c:forEach>
   </select>
</td>
 </tr>
 <tr>
 	<td style="width:32%">
    	  <label class="lab"><span class="red"></span>证件有效期：</label>
    <input id="idStartDay" name="idStartDay"  class="Wdate input_text70" type="text" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" value="<fmt:formatDate 
      value='${workItem.bv.idStartDay}' pattern="yyyy-MM-dd"/>"  />
      -<input id="idEndDay" name="idEndDay"  class="Wdate input_text70" type="text" size="10" 
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" 
  	value="<fmt:formatDate value='${workItem.bv.idEndDay}' pattern="yyyy-MM-dd"/>"  />
   <input type="checkbox" path="longTerm" name="isLongTerm" id="longTerm" <c:if test="${workItem.bv.isLongTerm == 1 }"> checked="checked" </c:if>   value="1"/>长期
     </td>
      </tr>
      <tr>
      	<td>
      		<label class="lab">预计借款金额：</label>
      			<fmt:formatNumber value="${workItem.bv.consLoanAmount == null ? 0 : workItem.bv.consLoanAmount }" />元
      			<input type="hidden" name="loanApplyAmount"  maxLength="8" value="${workItem.bv.consLoanAmount}"/>
      	</td>
      </tr>
 </table>
</div>
 <h2 class=" f14 pl10 mt20">车辆信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
 <tr>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>车辆品牌与型号：</label>
    <input id="vehicleBrandModel" maxlength="30" name="vehicleBrandModel" class="required input_text178" type="text" value="${workItem.bv.vehicleBrandModel}"/>
	<input id="loanCode" name="loanCode" type="hidden" value="${workItem.bv.loanCode}"/>
   </td>
      <td style="width:32%">
    <label class="lab"><span class="red">*</span>车牌号码：</label>
    <input id="plateNumbers" name="plateNumbers" class="required input_text178 plateNum" maxLength="10" type="text"  value="${workItem.bv.plateNumbers}"/>
   </td>
 	<td style="width:32%">
    <label class="lab"><span class="red">*</span>建议借款金额：</label>
    <input id="suggestLoanAmount" name="suggestLoanAmount" class="required input_text178" maxLength="8" type="text" value="<fmt:formatNumber value="${workItem.bv.suggestLoanAmount}" />" maxlength=""/>
    <label>元</label>
   </td>
 </tr>
 <tr>

   <td style="width:32%">
    <label class="lab"><span class="red">*</span>评估师姓名：</label>
    <input id="appraiserName" name="appraiserName" class="required input_text178" type="text" value="${workItem.bv.appraiserName}" readonly/>
   </td>
  <td style="width:32%">
    <label class="lab"><span class="red">*</span>评估金额：</label>
    <input id="storeAssessAmount" name="storeAssessAmount" class="required input_text178" maxLength="8" type="text" value="<fmt:formatNumber value="${workItem.bv.storeAssessAmount }" />"/>
    <label>元</label>
   </td>
   <td style="width:32%">
    <label class="lab"><span class="red">*</span>同类市场价格：</label>
    <input id="similarMarketPrice" name="similarMarketPrice" class="required input_text178" maxLength="8" type="text" value="<fmt:formatNumber value="${workItem.bv.similarMarketPrice }"/>"/>
    <label>元</label>
   </td>
</tr>
<tr>
  <td style="width:32%">
    <label class="lab">商业险到期日：</label>
    <input id="commercialMaturityDate" name="commercialMaturityDate" type="text" class="input_text178 Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.commercialMaturityDate}' pattern="yyyy-MM-dd"/>"/>
   </td>
  <td style="width:32%">
    <label class="lab">商业险保险公司：</label>
    <input id="commericialCompany" name="commericialCompany" value="${workItem.bv.commericialCompany}" type="text" class="input_text178 " maxlength="50"/>

   </td>
  <td style="width:32%">
    <label class="lab">商业险单号：</label>
    <input id="commericialNum" name="commericialNum" value="${workItem.bv.commericialNum}" type="text" class="input_text178" maxlength="50"/>
   </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>交强险到期日：</label>
    <input id="strongRiskMaturityDate" name="strongRiskMaturityDate" type="text" class="input_text178 required Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.strongRiskMaturityDate}' pattern="yyyy-MM-dd"/>"/>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>交强险保险公司：</label>
    <input id="clivtaCompany" name="clivtaCompany" value="${workItem.bv.clivtaCompany}" class="required input_text178" maxLength="50" type="text" value="${workItem.bv.ownershipCertificateNumber}"/>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>交强险单号：</label>
    <input id="clivtaNum" name="clivtaNum" value="${workItem.bv.clivtaNum}" class="required input_text178" maxLength="50" type="text" value="${workItem.bv.ownershipCertificateNumber}"/>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>车架号：</label>
    <input id="frameNumber" name="frameNumber" class="required input_text178  frameNumberCheck" maxLength="17" type="text" value="${workItem.bv.frameNumber}"/>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>车辆厂牌型号：</label>
    <input id="vehiclePlantModel" name="vehiclePlantModel" class="required input_text178" maxLength="30" type="text" value="${workItem.bv.vehiclePlantModel}"/>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>首次登记日期：</label>
    <input id="firstRegistrationDate" name="firstRegistrationDate" type="text" class="input_text178 required Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.firstRegistrationDate}' pattern="yyyy-MM-dd"/>"/>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>表征里程：</label>
    <input id="mileage" name="mileage" class="required input_text178 mileageCheck" maxLength="9"  type="text" value="${workItem.bv.mileage}"/>
    <label>公里</label>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>排气量：</label>
    <input id="displacemint" name="displacemint" class="required input_text178 displacemintCheck"  maxLength="3"  type="text" value="<fmt:formatNumber value='${workItem.bv.displacemint}' pattern='0.0'/>"/>
    <label>升</label>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>车身颜色：</label>
    <input id="carBodyColor" name="carBodyColor" class="required input_text178 colourCheck" maxLength="5" type="text" value="${workItem.bv.carBodyColor}"/>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>变速器：</label>
    <input id="variator" name="variator" class="required input_text178" maxLength="8" type="text" value="${workItem.bv.variator}"/>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>发动机号：</label>
    <input id="engineNumber" name="engineNumber" class="required input_text178" maxLength="20" type="text" value="${workItem.bv.engineNumber}"/>
    </td>
    <td style="width:32%">
    <label class="lab"><span class="red">*</span>过户次数：</label>
    <input id="changeNum" name="changeNum" class="required input_text178 mileageCheck" maxlength="1" type="text" value="${workItem.bv.changeNum}"/>
    <label>次</label>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>权属证书编号：</label>
    <input id="ownershipCertificateNumber" name="ownershipCertificateNumber" maxlength="20" class="required input_text178" type="text" value="${workItem.bv.ownershipCertificateNumber}"/>
    </td>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>出厂日期：</label>
    <input id="factoryDate" name="factoryDate" type="text" class="input_text178 required Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.factoryDate}' pattern="yyyy-MM-dd"/>"/>
    </td>
	<td style="width:32%">
    <label class="lab"><span class="red">*</span>车年到期日：</label>
    <input id="annualCheckDate" name="annualCheckDate" type="text" class="input_text178 required Wdate" size="10"  
      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${workItem.bv.annualCheckDate}' pattern="yyyy-MM-dd"/>"  />
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>改装情况：</label> 
    <textarea id="modifiedSituation" name="modifiedSituation" maxLength="100" class="required input_text178" style="width:350px;height:50px;vertical-align:top">${workItem.bv.modifiedSituation}</textarea>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>外观检测：</label>
    <textarea id="outerInspection" name="outerInspection"  maxLength="100" class="required input_text178" style="width:350px;height:50px;vertical-align:top">${workItem.bv.outerInspection}</textarea>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>违章及事故情况：</label>
    <textarea id="illegalAccident" name="illegalAccident" maxLength="100" class="required input_text178" style="width:350px;height:50px;vertical-align:top">${workItem.bv.illegalAccident}</textarea>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab"><span class="red">*</span>车辆评估意见：</label>
    <textarea id="vehicleAssessment" name="vehicleAssessment" maxLength="100" class="required input_text178" style="width:350px;height:50px;vertical-align:top">${workItem.bv.vehicleAssessment}</textarea>
    </td>
</tr>
<tr>
	<td>
    <label class="lab"><span class="red">*</span>下一步：</label>
    <select id="dictOperStatus" name="dictOperStatus" class="required select180 select2Req">
    <option value="">请选择</option>
		<c:forEach items="${fns:getDictLists('1,2,5','jk_next_step')}" var="item">
		      <option value="${item.value}">${item.label}</option>
		 </c:forEach>
   </select>
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab">备注：</label>
    <textarea id="remark" name="remark" maxLength="50" class="input_text178" style="width:350px;height:50px;vertical-align:top">${workItem.bv.remark}</textarea>
    </td>
</tr>
 </table>
</div>
<div class="tright pr10 pt5 pb5 mb10" >
	<input type="button" id="submitConsultBtn" class="btn btn-primary" value="提交"></input>
	<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
    onclick="JavaScript:history.go(-1);"></input>
</div>
</form>
</body>
</html>