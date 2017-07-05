var launch = {};
var checkResult=false;


//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号有误");
//中英文校验
jQuery.validator.addMethod("stringCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5 ]{1,20}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入中文、英文、空格");
//两位小数校验
jQuery.validator.addMethod("numCheck", function(value, element) {
	var isOK = true;
	if(value!='' && value.indexOf(".")!=-1){
		var regex=/\d{1,}\.\d{2}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "最多输入两位小数");
// 电话校验
jQuery.validator.addMethod("isTel", function(value, element) {
	//var telRegx = /^\d{3}-\d{8}|\d{4}-\d{7}$/;
	var telRegx =/^(\d{4}|\d{3})-(\d{7,8})$/;
    var result=true;
 	if(value!=null && value!=''){
 		 if(!telRegx.test(value)){
 			result = false;
         }
	}
    return this.optional(element) || (result);
}, "号码格式：XXX(X)-XXXXXXX(X)");
// 身份证
jQuery.validator.addMethod("isCertificate", function(value, element) {
	var isOK= checkIdcard(value);
	var cardType = $("#mateCardType option:selected").val();
	if(cardType != "0")
		{
			isOK=true;
		}
    var length = value.length;
    return this.optional(element) || (isOK);
}, "身份证有误");
jQuery.validator.addMethod("effectiveCertificate", function(value, element) {
	   var isOK= true;
	   var endDay = $('#idEndDay').val();
	   var checked = $('#longTerm').prop('checked');
       if((endDay==''|| endDay==null) && (checked==false || checked==undefined)){
	    	isOK = false;
	    }
    return this.optional(element) || (isOK);
}, "证件有效期输入有误");
launch.saveApplyInfo =function(num,busiForm) {
	$("#"+busiForm).validate({
		onkeyup: false, 
		rules : {
			'loanCustomer.customerEmail' : {
				email:true
			},
			'loanCustomer.customerPhoneSecond':{
				isMobile:true
			},
			'loanMate.mateCertNum':{
				isCertificate:true
			},
			'loanMate.mateTel':{
				isMobile:true
			},
			'loanInfo.loanApplyAmount':{
				required : true,
          	   	max:999999999,
          	   	number:true 
			},
     	   'customerLoanCompany.compWorkExperience' : {
    		   required : true,
        	   max:100,
        	   min:0.5,
        	   number:true 
    		},
    		'customerLoanCompany.compSalaryDay' :{
          	   max:31,
          	   digits:true,
          	   min:1
    		},
    		'customerLoanCompany.compSalary' :{
    		   max:999999999,
    		   min:100,
           	   number:true 
    		},
    		'customerLoanCompany.compTel':{
    		   isTel:true
    		},
    		'loanCustomer.idStartDay':{
    		   effectiveCertificate:true
    		}
		},
		messages : {
			'loanCustomer.customerEmail':{
				email: "邮件格式有误"	
			},
			'loanInfo.loanApplyAmount':{
				max:"输入金额有误",
				number : "金额只能是数字",
				min:"输入最小为{0}的数值"
			},
			'customerLoanCompany.compSalary' :{
				max:"收入金额有误",
				min:"输入最小{0}的数值"
			},
			'customerLoanCompany.compWorkExperience':{
				max:"工作年限有误",
				min:"输入最小为{0}的数值"
			},
			'customerLoanCompany.compSalaryDay' :{
          	   	max:"输入最大为{0}的数值",
          	   	min:"输入最小为{0}的数值"
    		}
		},
		submitHandler : function(form) {
			var url = ctx + "/apply/dataEntry/saveApplyInfo?" + "flag=" + num;
			form.action = url;
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
