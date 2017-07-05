
//手机号码验证    
jQuery.validator.addMethod("isMobile", function(value, element) {    
	var length = value.length;    
	return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/.test(value));    
}, "<font color='red'>请正确填写手机号码</font>");
//邮箱地址验证验证    
jQuery.validator.addMethod("isEmail", function(value, element) {    
	var length = value.length;    
	return this.optional(element) || (/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value));    
}, "<font color='red'>请正确填写邮箱地址</font>");
	
 	
$(document).ready(function(){ 
	//初始化省市
	loanCard.initCity("provinceId","cityId");
	loanCard.initCity("pMsgProvinceId","pMsgCityId");
	//验证修改账号表单
	$("#editAccountMessage").validate({
		rules : {
			"bankCardNo" : {
				required : true,
				maxlength : 19,
				number : true,
				isModify:true
			},
			"applyBankName" : {
				required : true
			},
			"file" : {
				required : true
			},
			"bankSigningPlatform" : {
				required : true
			}
		},
		messages : {
			"bankCardNo" : {
				required : "<font color='red'>划扣账号不得为空</font>",
				maxlength : "<font color='red'>划扣账号不得超过19位</font>",
				number : "<font color='red'>划扣账号只能是数字</font>"
			},
			"applyBankName" : {
				required : "<font color='red'>开户支行不能为空</font>"
			},
			"file" : {
				required : "<font color='red'>请选择上传文件</font>"
			},
			"bankSigningPlatform" : {
				required : "<font color='red'>请选择划扣平台</font>"
			}
		}
	});
	
	//修改邮箱
	$("#editEmailMessage").validate({
		rules : {
			"newEmail" : {
				isEmail : true,
				emailCompare:true,
				required : true
			},
			"coboEmail" : {
				emailCompareA:true,
				isEmail : true
			},
			"file" : {
				required : true
			}
		},
		messages : {
			"newEmail" : {
				required : "<font color='red'>邮箱地址不能为空</font>"
			},
			"file" : {
				required : "<font color='red'>请选择上传文件</font>"
			}
		}
	});
//	$.validator.addMethod("isEmailModify", function(value, element) {  
//		if(value == $('#isEmailModify').val()){return false;}
//		return true;
//	}, "请修改邮箱地址");

	$.validator.addMethod("emailCompare", function(value, element) {  
		if(value == $('#emailCoboEmail').val()){return false;}
		return true;
	}, "借款人与共借人邮箱地址不能相同");
	
	$.validator.addMethod("emailCompareA", function(value, element) {  
		if(value == $('#mailCustomerEmail').val()){return false;}
		return true;
	}, "借款人与共借人邮箱地址不能相同");
	
	//验证修改手机号表单
	$("#editPhoneMessage").validate({
		rules : {
			"newCustomerPhone" : {
				isMobile : true,
				required : true,
//				isPhoneModify : true,
				phoneCompare:true,
				minlength:11,
				maxlength : 11,
				number : true
			},
			"coboMobile" : {
				isMobile : true,
				phoneCompareA:true,
				minlength:11,
				maxlength : 11,
				number : true
			},
			"file" : {
				required : true
			}
		},
		messages : {
			"file" : {
				required : "<font color='red'>请选择上传文件</font>"
			},
			"newCustomerPhone" : {
				required : "<font color='red'>手机号不得为空</font>",
				minlength : "<font color='red'>手机号不得少于11位</font>",
				maxlength : "<font color='red'>手机号不得超过11位</font>",
				number : "<font color='red'>手机号只能是数字</font>"
			},
			"coboMobile" : {
				minlength : "<font color='red'>手机号不得少于11位</font>",
				maxlength : "<font color='red'>手机号不得超过11位</font>",
				number : "<font color='red'>手机号只能是数字</font>"
			}
		}
	});
//	$.validator.addMethod("isPhoneModify", function(value, element) {  
//		if(value == $('#isPhoneModify').val()){return false;}
//		return true;
//	}, "请修改手机号码");

	$.validator.addMethod("phoneCompare", function(value, element) {  
		if(value == $('#phoneCoboMobile').val()){return false;}
		return true;
	}, "借款人与共借人手机号不能相同");
	
	$.validator.addMethod("phoneCompareA", function(value, element) {  
		if(value == $('#phoneCustomerPhone').val()){return false;}
		return true;
	}, "借款人与共借人手机号不能相同");
	
	
	//验证添加账号表单
	$("#addAccountMessage").validate({
		rules : {
			"bankAccountName" : {
				required : true
			},
			"bankProvince" : {
				required : true
			},
			"bankProvinceCity" : {
				required : true
			},
			"bankSigningPlatform" : {
				required : true
			},
			"bankCardNo" : {
				required : true,
				minlength:16,
				maxlength : 19,
				number : true
			},
			"cardBank" : {
				required : true
			},
			"applyBankName" : {
				required : true
			},
			"file" : {
				required : true
			}
		},
		messages : {
			"bankAccountName" : {
				required : "<font color='red'>账号姓名不得为空</font>"
			},
			"bankProvince" : {
				required : "<font color='red'>请选择省份</font>"
			},
			"bankProvinceCity" : {
				required : "<font color='red'>请选择城市</font>"
			},
			"bankSigningPlatform" : {
				required : "<font color='red'>请选择划扣平台</font>"
			},
			"bankCardNo" : {
				required : "<font color='red'>划扣账号不得为空</font>",
				minlength : "<font color='red'>划扣账号不得少于16位</font>",
				maxlength : "<font color='red'>划扣账号不得超过19位</font>",
				number : "<font color='red'>划扣账号只能是数字</font>"
			},
			"cardBank" : {
				required : "<font color='red'>请选择开户行</font>"
			},
			"applyBankName" : {
				required : "<font color='red'>开户支行不能为空</font>"
			},
			"file" : {
				required : "<font color='red'>请选择上传文件</font>"
			}
		}
	});
	$.validator.addMethod("isModify", function(value, element) {  
		if(value == $('#isModify').val()){return false;}
		return true;
	}, "请修改划扣账号");
	
	$.validator.addMethod("isChineseChar", function(value, element) {       
         return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
    }, "匹配中文(包括汉字和字符) "); 
});	


//点击清除按钮调用的的方法
function resets(){
	// 清除text	
	$(":text").val('');
	// 清除select	
	$("#accountApplyForm").submit();
}

//修改按钮
function update(id,loanCode,dictMaintainType,updateType,coboFlag){
	//功能说明：
	//如果维护类型是新建，点击修改按钮，弹出新建页面，并显示新建后的数据(新建-审核-拒绝的数据)，可以修改
	//如果维护类型是修改，点击修改按钮，根据修改类型弹出'修改邮箱地址、修改手机号、修改银行账号'相应页面并显示数据，可修改
	//调用各方法，变量type有2种方式：0：第一次操作 1:一次以上操作
	//第一次操作：点击'新建、修改还款手机号、修改还款账号、修改邮箱地址'
	//一次以上操作：点击修改按钮
	if(dictMaintainType == '0'){
		newAccount(id,loanCode,'1',coboFlag);
	}else if(dictMaintainType == '1'){
		if(updateType == '1'){
			editPhone(id,coboFlag);
		}else if (updateType == '2'){
			editAccount(id,coboFlag);
		}else if(updateType == '3'){
			editEmail(id,coboFlag);
		}
	}
}

//新建银行账号
function newAccount(id,loanCode,type,coboFlag){
	$('#provinceId').val('').trigger("change");
	$('#cityId').val('').trigger("change");
	$('#phoneBankSigningPlatform').val('').trigger("change");
	$('#phoneCardBank').val('').trigger("change");
	$('#addAccountMessage')[0].reset();
	$("#coboTitle").hide();
	$("#coboMsg").hide();
	var validator = $("#addAccountMessage").validate({  
        submitHandler: function (form) {  
            form.submit();  
        }  
    });  
    validator.resetForm();  
	var url = ctx+"/car/carBankInfo/carCustomerBankInfo/editGoldAccount?id=" + id;
	$.ajax({
		type : "GET",
		url : url,
		success : function(data){
  			$("#newOldBankAccountId").val(id);
			$("#loanCode").val(data.loanCode);
			$("#addCustomerName").val(data.customerName);
  			$("#addContractCode").val(data.contractCode);
  			$("#addCustomerCard").val(data.customerCertNum);
  			$("#addCustomerPhone").val(data.customerPhoneFirst);
			$("#addContractVersion").val(data.contractVersion);
			$("#phoneBankAccountName").val(data.customerName);
  			if(data.israre == '1')
			{
				$("#israre").attr("checked",true)
				$("#israre").val(data.israre);
				$("#phoneBankAccountName").val(data.bankAccountName);
			}
			if(coboFlag == '1'){
				$("#coboTitle").show();
				$("#coboMsg").show();
				$("#addCoboId").val(data.coboId);
				$("#addCoboName").val(data.coboName);
				$("#addCoboCertNum").val(data.coboCertNum);
			}
			if(type == '1'){
      			$("#phoneApplyBankName").val(data.applyBankName);
      			$("#phoneBankCardNo").val(data.bankCardNo);
      			
      			//省份
      			var province = "";
      			$('#provinceId option').each(function(){
      				 if($(this).val().trim()==data.bankProvince){
						  $(this).attr('checked',true); 
						  province = $(this).val();
					  }
				   });
      			$('#provinceId').val(province).trigger("change");
      			
      			//市
      			var city = "";
      			$('#cityId option').each(function(){
      				 if($(this).val().trim()==data.bankProvinceCity){
						  $(this).attr('checked',true); 
						  city = $(this).val();
					  }
				   });
      			$('#cityId').val(city).trigger("change");
      			
				//划扣平台
      			var deductPlatCode = "";
      			$('#phoneBankSigningPlatform option').each(function(){
      				 if($(this).val().trim()==data.bankSigningPlatform){
						  $(this).attr('checked',true); 
						  deductPlatCode = $(this).val();
					  }
				   });
      			$('#phoneBankSigningPlatform').val(deductPlatCode).trigger("change");
      			//开户行名称
      			var bankName = "";
      			$('#phoneCardBank option').each(function(){
      				 if($(this).val().trim()==data.cardBank){
						  $(this).attr('checked',true); 
						  bankName = $(this).val();
					  }
				   });
      			$('#phoneCardBank').val(bankName).trigger("change");
			}
  			art.dialog({
  			    content: document.getElementById("addDiv"),
  			    title:'添加还款账号',
  			    fixed: true,
  			    lock:true,
  			    width:500,
  			    height:300,
  			    id: 'confirm',
  			    okVal: '确认',
  			    ok: function () {
  			    	var r = $('#addAccountMessage').valid();
  			    	if(r){
      					this.DOM.buttons[0].childNodes[0].disabled = true;
  			    	}
  			    	var srcFormParam = $('#addAccountMessage');
  					srcFormParam.submit();
  					return false;
  			    },
  			    cancel: true
  			});
		}
	});
}

//修改银行卡号
function editAccount(id,coboFlag){
	$('#phoneBankSigningPlatform').val('').trigger("change");
	$('#editAccountMessage')[0].reset();
	var validator = $("#editAccountMessage").validate({  
        submitHandler: function (form) {  
            form.submit();  
        }  
    });  
    validator.resetForm();  
	var url = ctx+"/car/carBankInfo/carCustomerBankInfo/editGoldAccount?id=" + id;
	$.ajax({
  		type : "GET",
  		url : url,
  		success : function(data) {
			$("#editCoboTitle").hide();
			$("#editCoboMsg").hide();
  			$("#oldBankAccountId").val(data.id);
  			$("#editCustomerName").val(data.customerName);
  			$("#editContractCode").val(data.contractCode);
  			$("#editCustomerCard").val(data.customerCertNum);
  			$("#editCustomerPhone").val(data.customerPhoneFirst);
  			$("#editContractVersion").val(data.contractVersion);
  			$("#accountName").val(data.bankAccountName);
  			$("#editOpenBankName").val(data.cardBankName);
  			$("#bankBranch").val(data.applyBankName);
  			$("#provinceName").val(data.provinceName);
  			$("#cityName").val(data.cityName);
  			$("#account").val(data.bankCardNo);
			if(coboFlag == '1'){
				$("#editCoboTitle").show();
				$("#editCoboMsg").show();
				$("#editCoboId").val(data.coboId);
				$("#editCoboName").val(data.coboName);
				$("#editCoboCertNum").val(data.coboCertNum);
			}
  			var deductPlatCode = "";
  			$('#deductPlatName option').each(function(){
  				 if($(this).val().trim()==data.bankSigningPlatform){
					  $(this).attr('checked',true); 
					  deductPlatCode = $(this).val();
				  }
			   });
  			$('#deductPlatName').val(deductPlatCode).trigger("change");
  			$("#isModify").val(data.bankCardNo); // 是否修改
  			art.dialog({
  			    content: document.getElementById("editAccountDiv"),
  			    title:'修改还款账号',
  			    fixed: true,
  			    lock:true,
  			    width:500,
  			    height:200,
  			    id: 'confirm',
  			    okVal: '确认',
  			    ok: function () {
  			    	var r = $('#editAccountMessage').valid();
  			    	if(r){
      					this.DOM.buttons[0].childNodes[0].disabled = true;
  			    	}
  			    	var srcFormParam = $('#editAccountMessage');
  					srcFormParam.submit();
  					return false;
  			    },
  			    cancel: true
  			});
  		}
  	});
}

function setDeductPlat(platCode){
	$("#editDeductPlat").val(platCode);
}
function fileChange(){
	var filepath=$("input[name='file']").val();
    var extStart=filepath.lastIndexOf(".");
    var ext=filepath.substring(extStart,filepath.length).toUpperCase();
    if(ext!=".ZIP" && ext!=".RAR"){
    	art.dialog.alert("仅限于上传rar或zip文件");
    	$("input[name='file']").val('');
    }
}

//修改手机号
function editPhone(id,coboFlag){
	$('#editPhoneMessage')[0].reset();
	var validator = $("#editPhoneMessage").validate({  
        submitHandler: function (form) {  
            form.submit();  
        }  
    });  
    validator.resetForm();  
	var url = ctx+"/car/carBankInfo/carCustomerBankInfo/editGoldAccount?id=" + id;
	$.ajax({
		type : "POST",
		url : url,
		success : function(data){
			//先隐藏共借人信息，如果有共借人信息才显示
			$("#pCoboTitle").hide();
			$("#pCoboName").hide();
			$("#pCoboCertNum").hide();
			$("#pCoboMobile").hide();
			$("#phoneBankAccountId").val(data.id);
  			$("#phoneCustomerName").val(data.customerName);
  			$("#phoneContractCode").val(data.contractCode);
  			$("#phoneCustomerCard").val(data.customerCertNum);
			//如果有共借人信息显示
			if(coboFlag == '1'){
				$("#pCoboTitle").show();
				$("#pCoboName").show();
				$("#pCoboCertNum").show();
				$("#pCoboMobile").show();
				$("#phoneCoboId").val(data.coboId);
				$("#phoneCoboName").val(data.coboName);
				$("#phoneCoboCertNum").val(data.coboCertNum);
				$("#phoneCoboMobile").val(data.coboMobile);
			}
			$("#phoneCustomerPhone").val(data.customerPhoneFirst);
  			$("#customerPhoneFirst").val(data.customerPhoneFirst);
//  			$("#isPhoneModify").val(data.customerPhoneFirst);
  			art.dialog({
  			    content: document.getElementById("editPhoneDiv"),
  			    title:'修改还款手机号',
  			    fixed: true,
  			    lock:true,
  			    width:450,
  			    height:280,
  			    id: 'confirm',
  			    okVal: '确认',
  			    ok: function () {
  			    	var r = $('#editPhoneMessage').valid();
  			    	if(r){
      					this.DOM.buttons[0].childNodes[0].disabled = true;
  			    	}
  			    	var srcFormParam = $('#editPhoneMessage');
  					srcFormParam.submit();
  					return false;
  			    },
  			    cancel: true
  			});
		}
	});
}

//修改邮箱
function editEmail(id,coboFlag){
	$('#editEmailMessage')[0].reset();
	var validator = $("#editEmailMessage").validate({  
        submitHandler: function (form) {  
            form.submit();  
        }  
    });  
    validator.resetForm();  
	var url = ctx+"/car/carBankInfo/carCustomerBankInfo/editGoldAccount?id=" + id;
	$.ajax({
		type : "POST",
		url : url,
		success : function(data){
			//先隐藏共借人信息，如果有共借人信息才会显示
			$("#emailCoboTitle").hide();
			$("#eCoboTitle").hide();
			$("#eCoboName").hide();
			$("#eCoboCertNum").hide();
			$("#eCoboEmail").hide();
			$("#emailOldBankAccountId").val(data.id);
  			$("#mailCustomerName").val(data.customerName);
  			$("#mailContractCode").val(data.contractCode);
  			$("#mailCustomerCard").val(data.customerCertNum);
			//如果有共借人信息显示
			if(coboFlag == '1'){
				$("#emailCoboTitle").show();
				$("#eCoboName").show();
				$("#eCoboCertNum").show();
				$("#eCoboEmail").show();
				$("#emailCoboId").val(data.coboId);
				$("#emailCoboName").val(data.coboName);
				$("#emailCoboCertNum").val(data.coboCertNum);
				$("#emailCoboEmail").val(data.coboEmail);
			}
  			$("#emailCustomerEmail").val(data.customerEmail);
  			$("#mailCustomerEmail").val(data.customerEmail);
//  			$("#isEmailModify").val(data.customerEmail);
  			art.dialog({
  			    content: document.getElementById("editEmailDiv"),
  			    title:'修改邮箱',
  			    fixed: true,
  			    lock:true,
  			    width:450,
  			    height:280,
  			    id: 'confirm',
  			    okVal: '确认',
  			    ok: function () {
  			    	var r = $('#editEmailMessage').valid();
  			    	if(r){
      					this.DOM.buttons[0].childNodes[0].disabled = true;
  			    	}
  			    	var srcFormParam = $('#editEmailMessage');
  					srcFormParam.submit();
  					return false;
  			    },
  			    cancel: true
  			});
		}
	});
}

function setTop(id, loanCode){
	var url = ctx+"/car/carBankInfo/carCustomerBankInfo/setTop";
	$.ajax({
		type : "POST",
		url : url,
		dataType : "json",
		data : {"id":id, "loanCode":loanCode},
		success : function(data){
		    	var srcFormParam = $('#accountApplyForm');
				srcFormParam.submit();
			art.dialog.alert("置顶成功");
		}
	});
}

function download(filetype)
{
	art.dialog.tips("文件下载中...");
	window.location.href = ctx
			+ "/car/carBankInfo/carCustomerBankInfo/fileDownLoad?fileType="+filetype;
}
var bankNoName = "";
function selectIsrare(){
	if($("input[type='checkbox']").is(':checked')){
		$("#israre").val("1");
		bankNoName = $("#phoneBankAccountName").val();
		$("#phoneBankAccountName").val("");
		$("#phoneBankAccountName").removeAttr("readonly");
	}else{
		$("#israre").val("");
		$("#phoneBankAccountName").val(bankNoName);
		$("#phoneBankAccountName").attr("readonly","readonly");
	}
}