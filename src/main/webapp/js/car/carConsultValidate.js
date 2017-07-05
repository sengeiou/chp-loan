var consult = {};


//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号有误");

//身份证
jQuery.validator.addMethod("isCertificate", function(value, element) {
	var isOK= checkIdcard(value);
	var cardType = $("#cardType option:selected").val();
	if(cardType != "0")
		{
			isOK=true;
		}
    var length = value.length;
    return this.optional(element) || (isOK);
}, "身份证有误");
// 中英文校验
jQuery.validator.addMethod("stringCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5 ]{1,20}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入中文、英文、空格");
// 两位小数校验
jQuery.validator.addMethod("numCheck", function(value, element) {
	var isOK = true;
	if(value!='' && value.indexOf(".")!=-1){
		var regex=/\d{1,}\.\d{2}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "最多输入两位小数");
jQuery.validator.addMethod("effectiveCertificate", function(value, element) {
	   var isOK= true;
	   var endDay = $('#idEndDay').val();
	   var checked = $('#longTerm').prop('checked');
    if((endDay==''|| endDay==null) && (checked==false || checked==undefined)){
	    	isOK = false;
	    }
 return this.optional(element) || (isOK);
}, "证件有效期输入有误");
consult.datavalidate = function() {
	$("#inputForm").validate(
			{
				onkeyup: false, 
				rules : {
					'customerBaseInfo.customerName' : {
						required : true,
						stringCheck:true
					},
                   'loanApplyMoney':{
                	   max:999999999,
                	   number:true   
                   },
                   'customerBaseInfo.customerMobilePhone':{
                	   isMobile:true
                   },
                   'customerBaseInfo.mateCertNum': {
                	   isCertificate:true
            	   },
            	   'customerBaseInfo.idStartDay':{
            		   effectiveCertificate:true
            	   },
            	   'customerBaseInfo.areaNo':{
            		   maxlength:4,
            		   minlength:3,
            		   digits:true
                   },
            	   'customerBaseInfo.telephoneNo':{
            		   maxlength:8,
            		   minlength:7,
            		   digits:true
                   }
				},
				messages : {
					'loanApplyMoney':{
						max:"输入金额有误",
						number : "金额只能是数字"	
					},
	            	'customerBaseInfo.areaNo':{
	            		   maxlength:"区号最多为4位",
	            		   minlength:"区号最少为3位",
	            		   digits:"区号只能是数字类型"
	                   },
	            	   'customerBaseInfo.telephoneNo':{
	            		   maxlength:"直播号码最多为8位",
	            		   minlength:"直播号码最少为7位",
	            		   digits:"直播号码只能是数字类型"
	                   }
				},
				submitHandler : function(form) {
					loading('正在提交，请稍等...');
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
}