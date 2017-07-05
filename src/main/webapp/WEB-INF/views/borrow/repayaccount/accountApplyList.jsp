<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<meta name="decorator" content="default"/>
<script type="text/javascript">
	var emailTimer = new Object();
	var emailFlag = "";
	//手机号码验证    
 	jQuery.validator.addMethod("isMobile", function(value, element) {    
    	var length = value.length;    
		return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(17[0-9]{1}))+\d{8})$/.test(value));    
    }, "<font color='red'>请正确填写手机号码</font>");
	//邮箱验证
	jQuery.validator.addMethod("isEmail", function(value, element) {    
	var length = value.length;    
	return this.optional(element) || (/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(value));    
	}, "<font color='red'>请正确填写邮箱地址</font>");
	$.validator.addMethod("isEmailModify", function(value, element) {  
		if(value == $('#oldEmail').val()){$("#emailBtn").attr("disabled", "true");  return false;}else{$("#emailBtn").removeAttr("disabled")}
		return true;
	}, "新邮箱地址不可与旧邮箱地址一致");
	$.validator.addMethod("isConfirmEmail", function(value, element) {  
		if(emailFlag == ""){return false;}
		return true;
	}, "邮箱未验证成功，请验证后确认提交");
	
	$(document).ready(function(){
		//初始化省市
		loanCard.initCity("provinceId","cityId");
		loanCard.initCity("pMsgProvinceId","pMsgCityId");
		// 初始化开户行支行
		loan.getBankList("bankBranch","hdloanBankbrId");
		// 初始化开户行支行
		loan.getBankList("bankBranchNew","hdloanBankbrIdNew","","selectStoresBtn1");
		//验证修改账号表单
		$("#editAccountMessage").validate({
			rules : {
				"account" : {
					required : true,
					maxlength : 19,
					number : true,
					isModify:true
				},
				"bankBranch" : {
					required : true
				},
				"file" : {
					required : true
				},
				"deductPlatName" : {
					required : true
				}
			},
			messages : {
				"account" : {
					required : "<font color='red'>划扣账号不得为空</font>",
					maxlength : "<font color='red'>划扣账号不得超过19位</font>",
					number : "<font color='red'>划扣账号只能是数字</font>"
				},
				"bankBranch" : {
					required : "<font color='red'>开户支行不能为空</font>"
				},
				"file" : {
					required : "<font color='red'>请选择上传文件</font>"
				},
				"deductPlatName" : {
					required : "<font color='red'>请选择划扣平台</font>"
				}
			}
		});
		
		//验证修改手机号表单
		$("#editPhoneMessage").validate({
			rules : {
				"customerPhone" : {
					isMobile : true,
					required : true,
					isPhoneModify : true
				},
				"file" : {
					required : true
				}
			},
			messages : {
				"file" : {
					required : "<font color='red'>请选择上传文件</font>"
				},
				"customerPhone" : {
					required : "<font color='red'>手机号不得为空</font>"
				}
			}
		});
		
		//验证修改邮箱表单
		$("#editEmailMessage").validate({
			rules : {
				"updatecontent" : {
					isEmail : true,
					required : true,
					isEmailModify : true
				},
				"file" : {
					required : true
				},
				"emailvalidator":{
					isConfirmEmail:true
				}
			},
			messages : {
				"file" : {
					required : "<font color='red'>请选择上传文件</font>"
				},
				"updatecontent" : {
					required : "<font color='red'>邮箱不得为空</font>"
				}
			}
		});
		$.validator.addMethod("isPhoneModify", function(value, element) {  
			if(value == $('#isPhoneModify').val()){return false;}
			return true;
		}, "请修改手机号码");
		
		//验证添加账号表单
		$("#addAccountMessage").validate({
			rules : {
				"accountName" : {
					required : true
				},
				"provinceId" : {
					required : true
				},
				"cityId" : {
					required : true
				},
				"deductPlat" : {
					required : true
				},
				"account" : {
					required : true,
					maxlength : 19,
					number : true
				},
				"bankName" : {
					required : true
				},
				"bankBranch" : {
					required : true
				},
				"file" : {
					required : true
				}
			},
			messages : {
				"accountName" : {
					required : "<font color='red'>账号姓名不得为空</font>"
				},
				"provinceId" : {
					required : "<font color='red'>请选择省份</font>"
				},
				"cityId" : {
					required : "<font color='red'>请选择城市</font>"
				},
				"deductPlat" : {
					required : "<font color='red'>请选择划扣平台</font>"
				},
				"account" : {
					required : "<font color='red'>划扣账号不得为空</font>",
					maxlength : "<font color='red'>划扣账号不得超过19位</font>",
					number : "<font color='red'>划扣账号只能是数字</font>"
				},
				"bankName" : {
					required : "<font color='red'>请选择开户行</font>"
				},
				"bankBranch" : {
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
		
		//验证添加账号表单
		$("#editGoldAccountMessage").validate({
			rules : {
				"provinceId" : {
					required : true
				},
				"cityId" : {
					required : true
				},
				"deductPlat" : {
					required : true
				},
				"pMsgAccount" : {
					required : true,
					maxlength : 19,
					number : true
				},
				"bankName" : {
					required : true
				},
				"bankBranch" : {
					required : true
				},
				"file" : {
					required : true
				},
				"pMsgCustomerPhone" : {
					required : true,
					isMobile : true,
					number : true
				}
			},
			messages : {
				"provinceId" : {
					required : "<font color='red'>请选择省份</font>"
				},
				"cityId" : {
					required : "<font color='red'>请选择城市</font>"
				},
				"deductPlat" : {
					required : "<font color='red'>请选择划扣平台</font>"
				},
				"pMsgAccount" : {
					required : "<font color='red'>划扣账号不得为空</font>",
					maxlength : "<font color='red'>划扣账号不得超过19位</font>",
					number : "<font color='red'>划扣账号只能是数字</font>"
				},
				"bankName" : {
					required : "<font color='red'>请选择开户行</font>"
				},
				"bankBranch" : {
					required : "<font color='red'>开户支行不能为空</font>"
				},
				"file" : {
					required : "<font color='red'>请选择上传文件</font>"
				},
				"pMsgCustomerPhone" : {
					required : "<font color='red'>手机号不能为空</font>",
					number : "<font color='red'>手机号只能是数字</font>"
				}
			}
		});
		$.validator.addMethod("isGoldPhoneModify", function(value, element) {  
			if(value == $('#isGoldPhoneModify').val()){return false;}
			return true;
		}, "请修改客户手机号");
		
		$.validator.addMethod("isGoldAccountModify", function(value, element) {  
			if(value == $('#isGoldAccountModify').val()){return false;}
			return true;
		}, "请修改划扣账号");
	});
	
	//点击清除按钮调用的的方法
	function resets(){
		// 清除text	
		$(":text").val('');
		// 清除select	
		$("select").val("");
		$("#accountApplyForm").submit();
	}
	
	function newAccount(contractCode){
		var url = "${ctx}/borrow/account/repayaccount/newAccount?contractCode=" + contractCode;
		$.ajax({
			type : "GET",
			url : url,
			success : function(data){
				$("#flag").val(data.flag);
				$("#loanCode").val(data.loanCode);
				$("#addCustomerName").val(data.customerName);
      			$("#addContractCode").val(data.contractCode);
      			$("#addCustomerCard").val(data.customerCard);
      			$("#addCustomerPhone").val(data.customerPhone);
      			$("#model").val(data.model);
      			$("#jx_add").css('display','none'); 
      			$("#cf_add").css('display','none');
      			$("#xt_add").css('display','none');
      			$("#tg_add").css('display','none');
      			
      			if(data.model=='1')
      			{
      				$("#tg_add").css('display','block');
      			}
      			else if(data.flag=='0')
      			{
      				$("#jx_add").css('display','block'); 
      			}
      			else if(data.flag=='2' || data.flag=='1')
      			{
      				$("#cf_add").css('display','block'); 
      			}
      			else if(data.flag=='4')
      			{
      				$("#xt_add").css('display','block'); 
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
      			    	var srcFormParam = $('#addAccountMessage');
      					srcFormParam.submit();
      					return false;
      			    },
      			    cancel: true
      			});
			}
		});
	}
	
	function editAccount(id,flag){
		$("#jx").css('display','none'); 
		$("#cf").css('display','none');
		$("#xt").css('display','none');
		
		if(flag=='0')
		{
			$("#jx").css('display','block'); 
		}
		if(flag=='2' || flag=='1')
		{
			$("#cf").css('display','block'); 
		}
		if(flag=='4')
		{
			$("#xt").css('display','block'); 
		}
		var url = "${ctx}/borrow/account/repayaccount/editAccount?id=" + id;
		$.ajax({
      		type : "GET",
      		url : url,
      		success : function(data) {
      			$("#accountId").val(data.id);
      			$("#refId").val(id);
      			$("#editLoanCode").val(data.loanCode);
      			$("#editProvinceId").val(data.provinceId);
      			$("#editCityId").val(data.cityId);
      			$("#editDeductPlat").val(data.deductPlat);
      			$("#editBankName").val(data.bankName)
      			$("#editCustomerName").val(data.customerName);
      			$("#editContractCode").val(data.contractCode);
      			$("#editCustomerCard").val(data.customerCard);
      			$("#editCustomerPhone").val(data.customerPhone);
      			$("#editBankBranch").val(data.bankBranch);
      			$("#accountName").val(data.accountName);
      			$("#provinceName").val(data.provinceName);
      			$("#cityName").val(data.cityName);
      			var deductPlatCode = "";
      			$('#deductPlatName option').each(function(){
      				 if($(this).text().trim()==data.deductPlatName){
						  $(this).attr('checked',true); 
						  deductPlatCode = $(this).val();
					  }
				   });
      			$('#deductPlatName').val(deductPlatCode).trigger("change");
      			$("#account").val(data.account);
      			$("#editOldAccount").val(data.account);
      			$("#isModify").val(data.account); // 是否修改
      			$("#editOpenBankName").val(data.openBankName);
      			$("#bankBranch").val(data.bankBranch);
      			$("#hdloanBankbrId").val(data.hdloanBankbrId);
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
	
	function editMobilePhone(id,flag){
		var url = "${ctx}/borrow/account/repayaccount/editMobilePhone?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				$("#phoneId").val(data.id);
				$("#phoneLoanCode").val(data.loanCode);
      			$("#phoneAccountName").val(data.accountName);
      			$("#phoneProvinceId").val(data.provinceId);
      			$("#phoneProvinceName").val(data.provinceName);
      			$("#phoneCityId").val(data.cityId);
      			$("#phoneCityName").val(data.cityName);
      			$("#phoneDeductPlat").val(data.deductPlat);
      			$("#phoneAccount").val(data.account);
      			$("#phoneBankName").val(data.bankName);
      			$("#phoneBankBranch").val(data.bankBranch);
      			$("#customerId").val(data.customerId);
      			
      			$("#phoneCustomerName").val(data.customerName);
      			$("#phoneContractCode").val(data.contractCode);
      			$("#phoneCustomerCard").val(data.customerCard);
      			$("#phoneCustomerPhone").val(data.customerPhone);
      			$("#isPhoneModify").val(data.customerPhone);
      			
      			$("#jx1").css('display','none'); 
      			$("#cf1").css('display','none');
      			$("#xt1").css('display','none');
      			
      			if(flag=='0')
   				{
      				$("#jx1").css('display','block'); 
   				}
      			if(flag=='2' || flag=='1')
   				{
      				$("#cf1").css('display','block'); 
   				}
      			if(flag=='4')
   				{
      				$("#xt1").css('display','block'); 
   				}
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
      			    	var srcFormParam = $('#editPhoneMessage');
      					srcFormParam.submit();
      					return false;
      			    },
      			    cancel: true
      			});
			}
		});
	}
	
	function editCustomerEmail(id){
		emailFlag = "";
		var url = "${ctx}/borrow/account/repayaccount/editCustomerEmail?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				$("#emailId").val(data.id);
      			$("#emailCustomerName").val(data.customerName);
      			$("#emailContractCode").val(data.contractCode);
      			$("#emailCustomerCard").val(data.customerCard);
      			$("#oldEmail").val(data.customerEmail);
      			//$("#emailLoanCode").val(data.loanCode);
      			art.dialog({
      			    content: document.getElementById("editEmail"),
      			    title:'修改邮箱',
      			    fixed: true,
      			    lock:true,
      			    width:450,
      			    height:250,
      			    id: 'confirm',
      			    okVal: '确认',
      			    ok: function () {
      			    	 $("#newEmail").removeAttr("disabled");
      			    	var srcFormParam = $('#editEmailMessage');
      					srcFormParam.submit();
      					return false;
      			    },
      			    cancel: function(){
      			    	clearInterval(emailTimer); 
      		            $("#emailBtn").removeAttr("disabled");//启用按钮
      		            $("#newEmail").removeAttr("disabled");
      		            $("#emailBtn").val("邮箱验证");
      		            $("#confirmEmailResult").text("")
      			    	 return true;
      			    }
      			});
			}
		});
	}

	function updatePhone() {
		$("#showAccountMsg").css('display','none'); 
		$("#showPhoneMsg").css('display','block'); 
	}
	function updateAccount(){
		$("#showAccountMsg").css('display','block'); 
		$("#showPhoneMsg").css('display','none'); 
	}
	function editGoldAccount(id){
		var url = "${ctx}/borrow/account/repayaccount/editGoldAccount?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			async: false,
			success : function(data){
				$("#goldLoanCode").val(data.loanCode);
				$("#goldAccountId").val(data.id);
				$("#goldOldAccount").val(data.account);
      			$("#goldCustomerName").val(data.customerName);
      			$("#goldContractCode").val(data.contractCode);
      			$("#goldCustomerCard").val(data.customerCard);
      			$("#goldAccountName").val(data.accountName);
      			$("#goldCustomerId").val(data.customerId);
      			$("#r").val(data.bankBranch);
      			var cityStr = "<option value=''>请选择</option>";
      			if (data.cityList != null){
	     			for(var i=0; i<data.cityList.length; i++){
	     				if (data.cityList[i].areaCode == data.cityId){
	     					cityStr += "<option selected='selected' value='" + data.cityList[i].areaCode + "'>" + data.cityList[i].areaName + "</option>";
	     				} else {
	     					cityStr += "<option value='" + data.cityList[i].areaCode + "'>" + data.cityList[i].areaName + "</option>";
	     				}
	     			}
      			}
      			var bankStr = "<option value=''>请选择</option>";
      			if (data.bankList != null){
	     			for(var i=0; i<data.bankList.length; i++){
	     				if (data.bankList[i].value == data.bankName){
	     					bankStr += "<option selected='selected' value='" + data.bankList[i].value + "'>" + data.bankList[i].label + "</option>";
	     				} else {
	     					bankStr += "<option value='" + data.bankList[i].value + "'>" + data.bankList[i].label + "</option>";
	     				}
	     			}
      			}
      			$("#goldProvinceId").val(data.provinceId);
      			$("#goldCityId").val(data.cityId);
      			$("#goldBankId").val(data.bankName);
      			$("#goldProvinceName").val(data.provinceName);
      			$("#goldCityName").val(data.cityName);
      			$("#goldBankName").val(data.openBankName);
      			$("#goldBankBranch").val(data.bankBranch);
      			$("#pMsgBankBranch").val(data.bankBranch);
      			$("#pMsgBankName").append(bankStr);
      			$("#pMsgAccount").val(data.account);
      			$("#isGoldAccountModify").val(data.account);
      			$("#pMsgCustomerPhone").val(data.customerPhone);
      			$("#isGoldPhoneModify").val(data.customerPhone);
      			$("#pMsgProvinceId").val(data.provinceId);
      			$("#goldDeductPlatName").val(data.deductPlatName);
      			$("#goldPDeductPlatName").val(data.deductPlatName);
      			$("#deductPlat").val(data.deductPlat);
      			$("#pMsgCityId").append(cityStr);
      			$("#pMsgProvinceId").trigger("change");
      			$("#goldProvinceId").trigger("change");
      			$("#goldCityId").trigger("change");
      			$("#pMsgCityId").trigger("change");
      			$("#goldBankName").trigger("change");
      			$("#pMsgBankName").trigger("change");
      			art.dialog({
      			    content: document.getElementById("editGoldDiv"),
      			    title:'修改金账户卡号',
      			    fixed: true,
      			    lock:true,
      			    width:600,
      			    height:400,
      			    id: 'confirm',
      			    okVal: '确认',
      			    ok: function () {
      			    	var srcFormParam = $('#editGoldAccountMessage');
      					srcFormParam.submit();
      					return false;
      			    },
      			    cancel: true
      			});
			}
		});
	}
	
	function updateMobilePhone(id){
		var url = "${ctx}/borrow/account/repayaccount/editMobilePhone?id=" + id;
		$.ajax({
			type : "POST",
			url : url,
			success : function(data){
				$("#phoneId").val(data.id);
				$("#phoneLoanCode").val(data.loanCode);
      			$("#phoneAccountName").val(data.accountName);
      			$("#phoneProvinceId").val(data.provinceId);
      			$("#phoneProvinceName").val(data.provinceName);
      			$("#phoneCityId").val(data.cityId);
      			$("#phoneCityName").val(data.cityName);
      			$("#phoneDeductPlat").val(data.deductPlat);
      			$("#phoneAccount").val(data.account);
      			$("#phoneBankName").val(data.bankName);
      			$("#phoneBankBranch").val(data.bankBranch);
      			
      			$("#phoneCustomerName").val(data.customerName);
      			$("#phoneContractCode").val(data.contractCode);
      			$("#phoneCustomerCard").val(data.customerCard);
      			$("#phoneCustomerPhone").val(data.customerPhone);
      			art.dialog({
      			    content: document.getElementById("updatePhoneDiv"),
      			    title:'修改金账户手机号及卡号(手机号页面)',
      			    fixed: true,
      			    lock:true,
      			    width:450,
      			    height:280,
      			    id: 'confirm',
      			    okVal: '确认',
      			    ok: function () {
      			    	var srcFormParam = $('#updatePhoneMessage');
      					srcFormParam.submit();
      					return false;
      			    },
      			    cancel: true
      			});
			}
		});
	}
	
	function setTop(id, contractCode, loanCode){
		var url = "${ctx}/borrow/account/repayaccount/setTop";
		$.ajax({
			type : "POST",
			url : url,
			dataType : "json",
			data : {"id":id, "contractCode":contractCode, "loanCode":loanCode},
			success : function(data){
				art.dialog.alert("置顶成功");
			}
		});
	}
	
	function download(filetype)
	{
		art.dialog.tips("文件下载中...");
		window.location.href = ctx
				+ "/borrow/account/repayaccount/fileDownLoad?fileType="+filetype;
	}
	
	//倒计时
	function countDown(maxtime,obj )  
	{   
	   var countNum = 0;
	   emailTimer = setInterval(function()  
	   {  
	       if(maxtime>0){     
	             --maxtime;
	             ++countNum;
	             $(obj).val("邮箱验证(" + maxtime+")" );
	             if(countNum == 40){
	            	 countNum = 0;
	            	confirmEmail(obj);
	             }
	        }     
	       else
	       {     
	            clearInterval(emailTimer); 
	            $(obj).removeAttr("disabled");//启用按钮
	            $("#newEmail").removeAttr("disabled");
	            $(obj).val("邮箱验证");
	            $("#confirmEmailResult").text("验证失败")
	            art.dialog.alert("邮箱验证失败，请修改邮箱或重新验证!");
	        }     
	    }, 1000);
	}
	
	function sendEmail(obj){
		 if(!(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test($("#newEmail").val()))){
			 art.dialog.alert("请正确填写邮箱地址");
			 return;
		 }
		 $("jx1").focus();
		 $(obj).attr("disabled", "true");	
		 $("#newEmail").attr("disabled", "true");
		 $.ajax({
			   type: "POST",
			   url : ctx + "/borrow/account/repayaccount/sendEmail",
		 	   data: {
		 		  	  contractCode: $("#emailContractCode").val(),
		 		   	  customerName:$("#emailCustomerName").val(),
		 		   	  customerEmail:$("#newEmail").val(),
		 		   	  id:$("#emailId").val()
		 		   	 },	
			   success: function(data){
				   if(data!=""){
						countDown(150,obj);
					    art.dialog.alert("验证邮件发送成功，请及时验证!");
					    $("#confirmEmailResult").text("验证中");
				   }else{
					   art.dialog.alert("验证邮件发送失败！");
					   $(obj).removeAttr("disabled");
					   $("#confirmEmailResult").text("验证失败");
					   $("#newEmail").removeAttr("disabled");
				   }
			  }
		});
	}
	
	function confirmEmail(obj){
		 $.ajax({
			   type: "POST",
			   url : ctx + "/borrow/account/repayaccount/confirmEmail",
		 	   data: {
		 		  	id:$("#emailId").val()
		 		   	 },	
			   success: function(data){
				   if(data!=""){
					   $("#confirmEmailResult").text("验证成功")
					   clearInterval(emailTimer); 
					   emailFlag = "through";
					   $("#emailvalidator").blur();
					   $(obj).val("邮箱验证");
					   art.dialog.alert("邮箱已验证成功，请继续操作");
				   }else{
					  // art.dialog.alert("验证邮件发送失败！");
				   }
			  }
		});
	}
	
	
</script>
</head>
<body>
	<div class="control-group">
		<form id="accountApplyForm" action="${ctx}/borrow/account/repayaccount/accountApplyList" method="post">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">合同编号：</label><input type="text" class="input-medium" name="contractCode" value="${repayAccountApplyView.contractCode}"/></td>
					<td><label class="lab">客户姓名：</label><input type="text" class="input-medium" name="customerName" value="${repayAccountApplyView.customerName}"/></td>
					<td>
						<label class="lab">合同版本号：</label> 
		                 <select id="version" name="version" class="version">
									<option value="">全部</option>
		                     <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="item">
							   <option value="${item.value}"
							   		<c:if test="${repayAccountApplyView.version==item.value}">
							     		 selected=true 
							   		</c:if> 
							 	 >${item.label}
							   </option>
						     </c:forEach>
						</select>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" onclick="resets()" value="清除">
		</div>
	</div>
	
	<c:if test="${repayAccountApplyView.newAccountFlag == '1' && applyList != null && fn:length(applyList) > 0 && model ne 1}">
			<c:if test="${repayAccountApplyView.maintainStatus == '1' && repayAccountApplyView.flag != 'TG'}">
				<p class="mb5">
					<input type="button" class="btn btn-small" onclick="newAccount('${repayAccountApplyView.contractCode}')" value="新建">
				</p>
			</c:if>
	</c:if>
	
	<div class="box5">
		<table class="table  table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th>操作日期</th>
					<th>合同编号</th>
					<th>账号姓名</th>
					<th>账号</th>
					<th>开户行</th>
					<th>附件</th>
					<th>模式</th>
					<th>渠道</th>
					<th>维护状态</th>
					<th>合同版本号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${rasList}" var="item">
					<tr>
						<td>
							<c:if test="${item.maintainStatus ne '2'}">
								<fmt:formatDate value="${item.applyTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
							<c:if test="${item.maintainStatus eq '2'}">
								<fmt:formatDate value="${item.maintainTime}" type="date" pattern="yyyy-MM-dd"/>
							</c:if>
						</td>
						<td>${item.contractCode}</td>
						<td>${item.accountName}</td>
						<td>${item.account}</td>
						<td>${item.bankNames}</td>
						<td>
							<c:if test="${item.fileName ne null && item.fileName ne ''}">
								${item.fileName}
							</c:if>
							<c:if test="${item.fileName eq null || item.fileName eq ''}">
								${item.phoneFileName}
							</c:if>
						</td>
						<td><c:if test="${item.model == '1'}">TG</c:if></td>
						<td>${item.flagName}</td>
						<td>${item.maintainStatusName}</td>
						<td>${item.versionLabel}</td>
						<td>
							<c:if test="${repayAccountApplyView.newAccountFlag == '1' && repayAccountApplyView.maintainStatus == '1'}">
								<c:if test="${item.model != '1'}">
									<button class="btn btn_edit" onclick="editAccount('${item.id}','${item.flag}')">修改账号</button>
									<button class="btn btn_edit" onclick="editMobilePhone('${item.id}','${item.flag}')">修改手机号</button>
								</c:if>
								<c:if test="${item.model == '1'}">
									<button class="btn btn_edit" onclick="editGoldAccount('${item.id}')">修改金账户手机号及卡号</button>
								</c:if>
								<button class="btn btn_edit" onclick="editCustomerEmail('${item.id}')">修改邮箱</button>
							</c:if>
							<c:if test="${(item.maintainStatus eq '2' || item.maintainStatus==null || item.maintainStatus=='') && item.topFlag !=1}">
								<button class="btn btn_edit" onclick="setTop('${item.id}','${item.contractCode}','${item.loanCode}')">置顶</button>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	
	<!-- 修改账号div -->
	<div id="editAccountDiv" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="editAccountMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveAccountMessage" method="post" enctype="multipart/form-data">
			<input type="hidden" id="editLoanCode" name="loanCode"/>
			<input type="hidden" id="accountId" name="oldAccountId"/>
			<input type="hidden" id="editProvinceId" name="provinceId">
			<input type="hidden" id="editCityId" name="cityId"/>
			<input type="hidden" id="editDeductPlat" name="deductPlat"/>
			<input type="hidden" id="editBankName" name="bankName"/>
			<input type="hidden" id="editOldAccount" name="oldAccount"/>
			<input type="hidden" name="phone" id="phone" value="1"/>
			<input type="hidden" id="refId" name="refId"/>
			<input name="uptedaType" type="hidden" value="1" />
			<input name="repayAccountTokenId" type="hidden" value="${repayAccountApplyView.repayAccountTokenId}" />
			<input name="repayAccountToken" type="hidden" value="${repayAccountApplyView.repayAccountToken}" />
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="editCustomerName" name="customerName" readonly="readonly"/></td>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="editContractCode" name="contractCode" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="editCustomerCard" readonly="readonly"/></td>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium" id="editCustomerPhone" name="customerPhone" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">账号姓名：</label></td>
						<td><input type="text" class="input-medium" id="accountName" name="accountName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">省份：</label></td>
						<td><input type="text" class="input-medium" id="provinceName" name="provinceName" readonly="readonly"/></td>
						<td><label class="lab">城市：</label></td>
						<td><input type="text" class="input-medium" id="cityName" name="cityName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">划扣平台：</label></td>
						<td>
					 	<select id="deductPlatName" onchange="setDeductPlat(this.value);" name="deductPlatName" class="select180">
                 		 <option value="">签约平台</option>
                   		  <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
                    		  <c:if test="${item.label!='中金' && item.value!='6' && item.value != '7'}">
					  		  <option value="${item.value}">${item.label}
					   		 </option>
					    	</c:if>
				     </c:forEach>
				</select>
						</td>
						<td><label class="lab">划扣账号：</label></td>
						<td><input type="text" class="input-medium isModify" id="account" name="account"/></td>
						<td><input type="hidden" id="isModify"/></td>
					</tr>
					<tr>
						<td><label class="lab">开户行名称：</label></td>
						<td><input type="text" class="input-medium" id="editOpenBankName" name="openBankName" readonly="readonly"/></td>
						<td><label class="lab">开户行支行：</label></td>
						<td><!-- <input type="hidden" id ='hdloanBankbrId' name="hdloanBankbrId" /> -->
                 		<input type="text" id="editBankBranch"  name="bankBranch"  class="input-medium isChineseChar required"/>
                  		<!--  <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i> --></td> 
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
						<td><label class="lab">下载附件：</label></td>
						<td><input id="jx" class="jx btn_edit" value="金信卡号手机号变更模板 " onclick="download('3');"> <input
							id="cf" class="cf btn_edit" value="财富&P2P卡号手机号变更模板" onclick="download('2');"> <input class="xt btn_edit"
							id="xt" value="信托卡号手机号变更模板" onclick="download('5');"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 修改手机号div -->
	<div id="editPhoneDiv" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="editPhoneMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveAccountMessage" method="post" enctype="multipart/form-data">
			<input type="hidden" id="phoneId" name="oldAccountId"/>
			<input type="hidden" id="phoneLoanCode" name="loanCode"/>
			<input type="hidden" id="phoneAccountName" name="accountName"/>
			<input type="hidden" id="phoneProvinceId" name="provinceId"/>
			<input type="hidden" id="phoneProvinceName" name="provinceName"/>
			<input type="hidden" id="phoneCityId" name="cityId"/>
			<input type="hidden" id="phoneCityName" name="cityName"/>
			<input type="hidden" id="phoneDeductPlat" name="deductPlat"/>
			<input type="hidden" id="phoneAccount" name="account">
			<input type="hidden" id="phoneBankName" name="bankName"/>
			<input type="hidden" id="phoneBankBranch" name="bankBranch"/>
			<input type="hidden" id="customerId" name="customerId"/>
			<input type="hidden" name="phone" id="phone" value="0"/>
			<input type="hidden" id="refId" name="refId"/>
			<input name="uptedaType" type="hidden" value="0" />
			<input name="repayAccountTokenId" type="hidden" value="${repayAccountApplyView.repayAccountTokenId}" />
			<input name="repayAccountToken" type="hidden" value="${repayAccountApplyView.repayAccountToken}" />
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="phoneCustomerName" name="customerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="phoneContractCode" name="contractCode" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="phoneCustomerCard" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium isPhoneModify" id="phoneCustomerPhone" name="customerPhone"/>
						<input type="hidden" id="isPhoneModify"/></td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
					</tr>
					<tr>
					<td><label class="lab">下载附件：</label></td>
						<td><input id="jx1" class="jx btn_edit" value="金信卡号手机号变更模板 " onclick="download('3');"> <input
							id="cf1" class="cf btn_edit" value="财富&P2P卡号手机号变更模板" onclick="download('2');"> <input class="xt btn_edit"
							id="xt1" value="信托卡号手机号变更模板" onclick="download('5');"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 修改邮箱 -->
	<div id="editEmail" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="editEmailMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveAccountMessage" method="post" enctype="multipart/form-data">
			<input name="repayAccountTokenId" type="hidden" value="${repayAccountApplyView.repayAccountTokenId}" />
			<input name="repayAccountToken" type="hidden" value="${repayAccountApplyView.repayAccountToken}" />
			<input name="oldAccountId" type="hidden" id="emailId"/>
			<input name="uptedaType" type="hidden" value="2" />
			<input name="loanCode" type="hidden" id="emailLoanCode"/>
			
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="emailCustomerName" name="customerName" readonly="readonly"/></td>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="emailContractCode" name="contractCode" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="emailCustomerCard" name="customerCard" readonly="readonly"/></td>
						<td><label class="lab">邮箱地址(旧)：</label></td>
						<td><input type="text" class="input-medium" id="oldEmail" name="customerEmail" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">邮箱地址(新)：</label></td>
						<td><input type="text" class="input-medium" name="updatecontent" id="newEmail" /></td>
						<td><input class="btn btn-small" type="button" id="emailBtn" value="邮箱验证" name="emailBtn" onclick="sendEmail(this)"/>
							<input name = "emailvalidator" id="emailvalidator" style= "background-color:transparent;width:1px;border:none;"></input>
						</td>
						<td><label class="lab" style="color:red;text-align:left;" id="confirmEmailResult"></label></td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file"
							onchange="fileChange()" /></td>
						<td><label class="lab">下载附件：</label></td>
						<td><input id="jx1" class="jx btn_edit" value="借款人客户信息变更申请书 "
							onclick="download('6');"></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
	<!-- 新建div -->
	<div id="addDiv" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="addAccountMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveAccountMessage" method="post" enctype="multipart/form-data">
			<input type="hidden" id="loanCode" name="loanCode"/>
			<input type="hidden" id="flag" name="flag"/>
			<input name="repayAccountTokenId" type="hidden" value="${repayAccountApplyView.repayAccountTokenId}" />
			<input name="repayAccountToken" type="hidden" value="${repayAccountApplyView.repayAccountToken}" />
			<table class="table  table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="addCustomerName" name="customerName" readonly="readonly"/></td>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="addContractCode" name="contractCode" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="addCustomerCard" readonly="readonly"/></td>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium" id="addCustomerPhone" name="customerPhone" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">账号姓名：</label></td>
						<td><input type="text" class="input-medium isChineseChar" name="accountName"/></td>
					</tr>
					<tr>
						<td><label class="lab">省份：</label></td>
						<td>
							<select class="select180" id="provinceId" name="provinceId">
								<option value="">请选择</option>
								<c:forEach items="${provinceList}" var="province">
									<option value="${province.areaCode}">${province.areaName}</option>
								</c:forEach>
							</select>
						</td>
						<td><label class="lab">城市：</label></td>
						<td>
							<select class="select180" id="cityId" name="cityId">
							</select>
						</td>
					</tr>
					<tr>
						<td><label class="lab">划扣平台：</label></td>
						<td>
							<select class="select180" name="deductPlat">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="plat">
									<c:if test="${plat.label != '中金' &&  plat.value!='6' && plat.value!='7'}">
										<option value="${plat.value}">${plat.label}</option>
									</c:if>
								</c:forEach>
							</select>
						</td>	
						<td><label class="lab">划扣账号：</label></td>
						<td><input type="text" class="input-medium" name="account"/></td>
					</tr>
					<tr>
						<td><label class="lab">开户行名称：</label></td>
						<td>
							<select class="select180" name="bankName">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_open_bank')}" var="bank">
									<option value="${bank.value}">${bank.label}</option>
								</c:forEach>
							</select>
						</td>
						<td><label class="lab">开户行支行：</label></td>
						<td><!-- <input type="hidden" id ='hdloanBankbrIdNew' name="hdloanBankbrId" /> -->
                 		<input type="text" id="bankBranchNew" name="bankBranch"  class="input-medium isChineseChar required"/>
                  		<!-- <i id="selectStoresBtn1" class="icon-search" style="cursor: pointer;"></i> --></td>
					</tr>
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
						<td><label class="lab">下载附件：</label></td>
						<td><input id="jx_add" class="jx btn_edit" value="金信卡号手机号变更模板 " onclick="download('3');"> 
							<input id="cf_add" class="cf btn_edit" value="财富&P2P卡号手机号变更模板" onclick="download('2');"> 
							<input id="xt_add"  class="xt btn_edit" value="信托卡号手机号变更模板" onclick="download('5');">
							<input id="tg_add" class="jx btn_edit" value="金账户（TG)卡号手机号变更模板 " onclick="download('4');">
						</td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
	
		<!-- 修改金账户手机号及卡号DIV(卡号页面) -->
	<div id="editGoldDiv" style="display:none">
		<form id="editGoldAccountMessage" class="validate" action="${ctx}/borrow/account/repayaccount/saveGoldAccount" method="post" enctype="multipart/form-data">
			 <input type="hidden" name="token" value="${token}">
			<input type="hidden" id="goldLoanCode" name="loanCode"/>
			<input type="hidden" id="goldAccountId" name="oldAccountId"/>
			<input type="hidden" id="goldOldAccount" name="oldAccount"/>
			<input type="hidden" id="goldBankId" name="bankName"/>
			<input type="hidden" id="goldBankBranch" name="bankBranch"/>
			<input type="hidden" id="goldProvinceId" name="provinceId">
			<input type="hidden" id="goldCityId" name="cityId"/>
			<input type="hidden" id="deductPlat" name="deductPlat"/>
			<input type="hidden" id="changeType" name="changeType" value="1"/>
			<input type="hidden" id="goldCustomerId" name="customerId" value="1"/>
			<input name="repayAccountTokenId" type="hidden" value="${repayAccountApplyView.repayAccountTokenId}" />
			<input name="repayAccountToken" type="hidden" value="${repayAccountApplyView.repayAccountToken}" />
			<table class="table  table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="goldCustomerName" name="customerName" readonly="readonly"/></td>
						<td><label class="lab">客户身份证号：</label></td>
						<td><input type="text" class="input-medium" id="goldCustomerCard" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">账号姓名：</label></td>
						<td><input type="text" class="input-medium" id="goldAccountName" name="accountName"  readonly="readonly"/></td>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="goldContractCode" name="contractCode" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab"><font color="red">变更类型：</font></label></td>
						<td colspan="3">
							<input type="radio" name="phone" value="0" checked="checked" onclick="updatePhone();">变更手机号</input>
							<input type="radio" name="phone" value="1" onclick="updateAccount();">变更银行卡号</input>
						</td>
					</tr>
				</tbody>
			</table>
			
			<!-- 修改金账户手机号及卡号DIV(卡号页面) -->
			<div id="showAccountMsg"  style="display:none">
				<table class="table  table-bordered table-condensed table-hover ">
						<tr>
							<td><label class="lab">省份：</label></td>
							<td>
								<select class="select180" id="pMsgProvinceId" name="pMsgProvinceId">
									<option value="">请选择</option>
									<c:forEach items="${provinceList}" var="province">
										<option value="${province.areaCode}">${province.areaName}</option>
									</c:forEach>
								</select>
							</td>
							<td><label class="lab">城市：</label></td>
							<td>
								<select class="select180" id="pMsgCityId" name="pMsgCityId">
								</select>
							</td>
						</tr>
						<tr>
							<td><label class="lab">划扣平台：</label></td>
							<td><input type="text" class="input-medium" id="goldDeductPlatName"  readonly="readonly"/></td>
							<td><label class="lab">划扣账号：</label></td>
							<td><input type="text" class="input-medium isGoldAccountModify" id="pMsgAccount" name="pMsgAccount"/>
								<input type="hidden" id="isGoldAccountModify">
							</td>
						</tr>
						<tr>
							<td><label class="lab">开户行名称：</label></td>
							<td>
								<select class="select180" id="pMsgBankName" name="pMsgBankName">
								</select>
							</td>
							<td><label class="lab">开户行支行：</label></td>
							<td><input type="text" class="input-medium isChineseChar" id="pMsgBankBranch" name="pMsgBankBranch"/></td>
						</tr>
				</table>
			</div>
				
			<!-- 修改金账户手机号及卡号DIV(手机号页面) -->
			<div id="showPhoneMsg" >
				<table class="table  table-bordered table-condensed table-hover ">
					<tr>
						<td><label class="lab">客户手机号：</label></td>
						<td><input type="text" class="input-medium isGoldPhoneModify" id="pMsgCustomerPhone" name="pMsgCustomerPhone"/>
							<input type="hidden" id="isGoldPhoneModify"/>
						</td>
					</tr>
					<tr>
						<td><label class="lab">省份：</label></td>
						<td><input type="text" class="input-medium" id="goldProvinceName" name="goldProvinceName" readonly="readonly"/></td>
						<td><label class="lab">城市：</label></td>
						<td><input type="text" class="input-medium" id="goldCityName" name="goldCityName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">划扣平台：</label></td>
						<td><input type="text" class="input-medium" id="goldPDeductPlatName"  readonly="readonly"/></td>
						<td><label class="lab">开户行名称：</label></td>
						<td><input type="text" class="input-medium" id="goldBankName" name="goldBankName" readonly="readonly"/></td>
					</tr>
				</table>
			</div>
			
			<div id="showFile">
				<table class="table  table-bordered table-condensed table-hover ">
					<tr>
						<td><label class="lab">上传附件：</label></td>
						<td><input type="file" class="input-medium" name="file" onchange="fileChange()"/></td>
					</tr>
					<tr>
						<td><label class="lab">下载附件：</label></td>
						<td><input id="tg" class="jx btn_edit" value="金账户（TG)卡号手机号变更模板 " onclick="download('4');"></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>