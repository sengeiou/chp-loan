
$(document).ready(function(){
	// 清除按钮
	$("#clearBtn").click(function(){	
		$(':input').val('');
		$("#searchForm").submit();
	});
	
	$('#idStartDay').on('blur',function(){
			var zjhm=$('#certNum').val();
			var startDay=$(this).val();
			if(zjhm !=null && startDay != null){
			var st=parseInt(startDay.replace(/-/g,''));
			var zj=parseInt(zjhm.substring(6,14));
			if(st < zj){
				art.dialog.alert('证件有效值输入起始时间有误，请检查!');
			  }
			}
		else{
			art.dialog.alert('请先输入证件号码或者证件有效期时间!');
		    }
	})
		
	$('#longTerm').bind(
				'click',
				function() {
					if ($(this).attr('checked') == true
							|| $(this).attr('checked') == 'checked') {
						$('#idEndDay').val('');
						$('#idEndDay').attr('readOnly', true);
						$('#idEndDay').hide();
					} else {
						$('#idEndDay').removeAttr('readOnly');
						$('#idEndDay').show();
					}
	})
	
	$('#submitConsultBtn').bind('click',function(){
				consult.datavalidate();
	})
});

function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);  
	$("#searchForm").attr("action", ctx + "/apply/consultOcr/list");
	$("#searchForm").submit();
	return false;
}





var consult = {};


//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return  (length == 11 && mobile.test(value));
}, "手机号有误");
//两位小数校验
jQuery.validator.addMethod("positiveNumCheck", function(value, element) {
	var isOK = true;
	var num = parseFloat(value);
	if(num<=0){
	
		isOK = false;
    }
	return isOK;
}, "输入的数值不能小于0");
//根据证件类型对证件号码检测（共借人）
jQuery.validator.addMethod("isCertificateCobo", function(value, element) {
	var isOK= true;
	var cardType = $("#dictCertType option:selected").val();
	var cardTypeName= $("#dictCertType option:selected").text();
	
	if(cardTypeName == "身份证" || cardTypeName == "临时身份证"){ // 身份证检测
			isOK = checkIdcard(value);
	}else if(cardTypeName=="户口薄"){ // 户口检测
		    isOK = checkNodeIdNum(value);
	}else if(cardTypeName=="军官证" || cardTypeName=="士兵证"){ // 军官证检测
		    isOK = checkOfficersNum(value);
	}else if(cardTypeName=="护照"){ // 护照检测
		    isOK = checkPassport(value);
	}else if(cardTypeName=="港澳居民来往内地通行证" || cardTypeName=="台湾同胞来往内地通行证"){ // 港澳通行证检测
		    isOK = checkGatepassNum(value);
	}else if(cardTypeName=="外国人居留证"){ // 外国人居留证
		  //  isOK = checkCerpacNum(value);
	}else if(cardTypeName=="警官证"){
		    isOK = checkPoliceNum(value);
	}
   return  isOK;
}, "证件号码输入有误");
//身份证
jQuery.validator.addMethod("isCertificate", function(value, element) {
	var isOK = true;
	var cardType = $("#cardType option:selected").val();
	if(cardType != "0"){
			isOK = false;
	}
    var length = value.length;
    return isOK;
}, "咨询时，证件类型只能是身份证");
//身份证号校验
jQuery.validator.addMethod("certNumCheck", function(value, element) {
	var isOK = true;
	var cardType = $("#cardType option:selected").val();
	if(cardType != "0"){
			isOK = false;
	}else{
		isOK = checkIdcard(value);
	}
    var length = value.length;
    return isOK;
}, "请检查证件号码的正确性");
// 中英文校验
jQuery.validator.addMethod("stringCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5 .·]{1,20}$/;
		isOK = regex.test(value);
    }
	return isOK;
}, "只能输入中文、英文、空格、点号");
// 两位小数校验
jQuery.validator.addMethod("numCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex1=/^\d{1,10}\.\d{1,2}$/;
		var regex2=/^\d{1,10}$/;
		isOK = regex1.test(value)||regex2.test(value);
    }
	return isOK;
}, "整数部分不能超过10位，小数部分不能超过2位");
jQuery.validator.addMethod("effectiveCertificate", function(value, element) {
	   var isOK= true;
	   var startDay = $('#idStartDay').val();
	   var endDay = $('#idEndDay').val();
	   var checked = $('#longTerm').prop('checked');
	   if(startDay=='' || startDay==null){
		   isOK = false;
		   jQuery.validator.messages.effectiveCertificate = "请填写证件有效期";
	   }else{
		   if((endDay==''|| endDay==null) && (checked==false || checked==undefined)){
	    	isOK = false;
	    	jQuery.validator.messages.effectiveCertificate = "证件有效期输入有误";
		   }
	   }
     return isOK;
}, "");
jQuery.validator.addMethod("telNoCheck", function(value, element) {
	   var isOK= true;
	   var areaNo = $('#areaNo').val();
	   var telephoneNo = $('#telephoneNo').val();
	   if((areaNo=='' && telephoneNo!='')||(areaNo!='' && telephoneNo=='')){
		   isOK = false;
	   }
       return isOK;
}, "请完整填写固定电话");

jQuery.validator.addMethod("areaNoCheck", function(value, element) {
	   var isOK= true;
	   var areaNoRegx =/^(0\d{2,3})$/;
	   var areaNo = $('#areaNo').val();
	   if(areaNo!=null && areaNo!=''){
		   isOK = areaNoRegx.test(areaNo);
	   }
	 jQuery.validator.messages.areaNoCheck = "区号不对" ;
    return isOK;
},"");
// onchange 通过证件类型更新证件号码
consult.certifacteFormat = function(certTypeId,certNumId){
	var certTypeValue = $('#'+certTypeId+' option:selected').val();
	var certValue = $('#'+certNumId).val(); 
	if(certValue!=''&& certValue!=null && certTypeValue=='0'){
		if(certValue.indexOf("x")!=-1){
				certValue = certValue.replace(/x/g,"X");
				$('#'+certNumId).val(certValue);
			}
	}
};
consult.datavalidate = function() {
	$("#inputForm").validate(
			{
				onkeyup: false, 
				rules : {
					'customerName' : {
						required : true,
						stringCheck:true
					},
                   'loanPosition':{
                	   max:999999999,
                	   number:true,
                	   numCheck:true
                   },
                   'mobilephone':{
                	   isMobile:true
                   },  
                   'certNum': {
                	   certNumCheck:true
               	   },
                   'certType': {
                	   isCertificate:true
            	   },
            	   'longTerm':{
            		   effectiveCertificate:true
            	   },
            	   'telephoneNo':{
            		   maxlength:8,
            		   minlength:7,
            		   digits:true
                   }
				},
				messages : {
					'loanPosition':{
						max:"输入金额有误",
						number : "金额只能是数字"	
					},
	            	'telephoneNo':{
	            	    maxlength:"直播号码最多为8位",
	            	    minlength:"直播号码最少为7位",
	            	    digits:"直播号码只能是数字类型"
	                }
				},
				submitHandler : function(form) {
					$('#submitConsultBtn').attr('disabled',true);
					form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
				    $("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio")
							|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
	                }
				}
  });
};
consult.findCustomerInfo = function(mateCertNum){
	$.ajax({
		url:ctx+"/apply/consult/asynFindByCertNum",
		type:'post',
		dataType:'json',
		data:{mateCertNum:mateCertNum},
		success:function(data){
		},
		error:function(){
		}
	});
};
/**
 *身份证性别设置
 * 
 * 
 */
consult.setSexByIdentityCode = function(identityCode){
	var num = "";
	if(identityCode.length==18){
	  num = identityCode.charAt(identityCode.length-2);
	}
	if(identityCode.length==15){
	  num = identityCode.charAt(identityCode.length-1);
	}
	var remainder = parseInt(num)%2;
	$('.customerSex').each(function(){
	  if($(this).val()=='1' && remainder==0){
		  $(this).attr('checked',true);
	  }
	  if($(this).val()=='0' && remainder==1){
		  $(this).attr('checked',true); 
	   }
	});
};