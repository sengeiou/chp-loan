//加载邮箱验证js
document.write('<script src="'+context+'/js/consult/emailvalidate.js"><\/script>');
$(function(){
	updateEmailIfConfirm();
	// 查看影像资料
	$("#storereviews").click(function(){
		var imageUrl = $("#imageUrl").val();
		art.dialog.open(imageUrl, {
			title: "客户影像资料",	
			top: 80,
			lock: true,
		    width: 1000,
		    height: 450,
		},false);	
	});	
	$('#backDetailBtn').click(function(){
		var url = ctx + "/apply/storeReviewController/getBackDetail?loanCode="+$(this).attr("loanCode");
		art.dialog.open(url, {
			title: "汇诚回退原因",	
			top: 80,
			lock: true,
		    width: 1000,
		    height: 450,
		},false);
		
	});
});	

// 提交门店复核流程节点
$(function(){
	$("#subBtn").click(function(){
		var tabLength=$('#contactArea tr').length;
		var url = ctx + "/borrow/borrowlist/dispatchFlow";
		if(tabLength<3){
			 art.dialog.alert("联系人至少要3个");
			 return false;
		 }else{
			 $("#subBtn").prop('disabled','disabled');
			 $("#subForm").attr("action",url);
			 $("#subForm").submit();			
		 }
	});
	if($("#giveUp").length>0){
		$("#giveUp").click(function(){
			var url = ctx + "/borrow/borrowlist/dispatchFlow";		
			art.dialog.confirm("是否放弃客户?",function(){
				$('#response').val('TO_GIVEUP');
				$("#subBtn").prop('disabled','disabled');
				$("#subForm").attr("action",url);
				$("#subForm").submit();
			});
		});
	}
	if($("#refuse").length>0){
		$("#refuse").click(function(){
			var url = ctx + "/borrow/borrowlist/dispatchFlow";	
			art.dialog({
				content: document.getElementById("refuseMod"),
				title:'门店拒绝',
				fixed: true,
				lock:true,
				okVal: '确认拒绝',
				ok: function () {
					$("#remark").attr("value",$("#rejectReason").val());
					if ($("#remark").val() == null || $("#remark").val()=='') {
						art.dialog.alert("请输入拒绝原因!");
					}else{					
						$('#response').val('TO_GIVEUP');
						$("#subBtn").prop('disabled','disabled');
						$("#subForm").attr("action",url);
						$("#subForm").submit();
					}
					return false;
				},
				cancel: true
			});
		});
	}
});

// 门店复核编辑窗口
$(function(){
	$("#creditMod").submit(function(){
		$("#creditNextBtn").prop('disabled','disabled');		
	});
	$("#houseMod").submit(function(){
		$("#houseNextBtn").prop('disabled','disabled');		
	});
	$("#contactMod").submit(function(){
		$("#contactNextBtn").prop('disabled','disabled');		
	});
	// 编辑客户信息
	$("#customerEditBtn").click(function(){		
		art.dialog({
			title: '客户信息编辑',
			padding: 0,
			content: document.getElementById('customerMod'),
			lock: true,
		},false);
	});	
	
	// 客户信息提交验证
	$('#customerForm').validate({
		onkeyup: false,
		rules : {
			'loanCustomer.customerEmail' : {
				isEmail:true,
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
			}
		},
		message:{
			'loanCustomer.customerEmail':{
				email: "邮箱格式有误"	
			},
			'loanCustomer.customerPhoneSecond':{
				isMobile:"手机号格式不对"
			},
		},
		submitHandler : function(form) {
			var sendEmailFlag = $("#sendEmailFlag").val();
			var email = $("input[name='loanCustomer.customerEmail']").val();
			var oldEmailFlag = $("#oldEmailFlag").val();
			//如果填写邮箱不是之前保存过的邮箱，并且不是验证过的邮箱则进行验证提示
			if(sendEmailFlag!=email && email!=oldEmailFlag){
				top.$.jBox.tip('请进行邮箱验证处理！');
				return;
			}
			$.ajax({
				  url:ctx+"/borrow/borrowlist/checkIfEmailConfirm",
				  type:"post",
				  data:{'customerCode':$("#bvCustomerCode").val(),'loanCode':$("#loanCode").val(),'customerEmail':email},
				  async:false,
				  dataType:'json',
				  success:function(data){
					  if(data=='1'){
						  top.$.jBox.tip('正在保存数据...');
					      $("#customerNextBtn").attr('disabled',true);
						  form.submit();
					  }else{
						  top.$.jBox.tip('请进行邮箱验证处理！');
					  }
				  },error:function(data){
					  top.$.jBox.tip('业务处理错误');
				  }
		   });
		}
	});
	
	// 配偶信息编辑
	$("#mateEditBtn").click(function(){
		art.dialog({
			title: '配偶资料编辑',
			padding: 0,
			content: document.getElementById('mateMod'),
			lock: true,
		},false);				
	});
	// 配偶信息添加
	$("#mateAddBtn").click(function(){
		art.dialog({
			title: '配偶资料编辑',
			padding: 0,
			content: document.getElementById('mateMod'),
			lock: true,
		},false);				
	});
	// 配偶资料提交验证
	$('#mateForm').validate({
		onkeyup: false,
		rules:{
			'loanMate.mateCertNum':{
				isCertificate:true
		}
		}
	});
	
	// 信借申请信息编辑
	$("#applyEditBtn").click(function(){
		art.dialog({
			title: '信借申请资料编辑',
			padding: 0,
			content: document.getElementById('applyMod'),
			lock: true,
		},false);
	});
	
	// 信借信息提交验证
	$('#applyForm').validate({
		onkeyup: false
	});
	
	// 共借人信息编辑
	$("#coboEditBtn").click(function(){
		
	var oneedition=	$("#oneedition").val();
		if(oneedition==1){
			art.dialog({
				title: '自然人保证人信息编辑',
				padding: 0,
				content: document.getElementById('coboMod'),
				lock: true,
			},false);
		}else {
			art.dialog({
				title: '共借人信息编辑',
				padding: 0,
				content: document.getElementById('coboMod'),
				lock: true,
			},false);
		}
		
	});
	
	// 共借人信息提交验证
	$('#coboForm').validate({
		onkeyup: false
	});
	
	// 信用资料信息编辑
	$("#creditEditBtn").click(function(){		
		art.dialog({
			title: '信用资料信息编辑',
			padding: 0,
			content: document.getElementById('creditMod'),
			lock: true,
		},false);	
	});
	
	// 信用资料提交验证
	$('#creditForm').validate({
		onkeyup: false
	});
	
	// 职业信息/公司资料编辑
	$("#companyEditBtn").click(function(){
		art.dialog({
			title: '职业信息/公司资料编辑',
			padding: 0,
			content: document.getElementById('companyMod'),
			lock: true,
		},false);	
	});
	
	// 职业信息/公司资料提交验证
	$('#companyForm').validate({
		onkeyup: false,
		rules : {
			'customerLoanCompany.compWorkExperience' : {
				required : true,
				max:65,
				min:0.5,
				number:true,
				compWorkExperience : true  
			},
			'customerLoanCompany.compSalaryDay' :{
	          	   max:31,
	          	   digits:true,
	          	   min:1
	    	}
		},
		messages : {
			'customerLoanCompany.compWorkExperience':{
				max:"工作年限有误",
				min:"输入最小为{0}的数值"
			},
			'customerLoanCompany.compSalaryDay' :{
          	   	max:"输入最大为{0}的数值",
          	   	min:"输入最小为{0}的数值"
    		}
		}
	});
	
	// 房产资料编辑
	$("#houseEditBtn").click(function(){
		art.dialog({
			title: '房产资料编辑',
			padding: 0,
			content: document.getElementById('houseMod'),
			lock: true,
		},false);
	});
	
	// 房产资料资料提交验证
	$('#houseForm').validate({
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
        	   max:65,
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
    		},
    		'loanBank.bankAccount' : {
				equalTo:"#firstInputBankAccount",
				digits:true,
				min:0,
				maxlength:19,
				minlength:16
			},
			'loanRemark.remark':{
				maxlength:500,
			}
		},
		messages : {
			'loanCustomer.customerEmail':{
				email: "邮箱格式有误"	
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
    		},
    		'loanBank.bankAccount':{
				equalTo: "输入银行卡号不一致",
				digits:  "银行卡号必须为数字类型",
				min:"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
				minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
			},
			'loanRemark.remark':{
				maxlength:"输入的字符串长度最长为{0}字",
			}
		}
	});
	
	// 联系人资料编辑
	$("#contactEditBtn").click(function(){
		art.dialog({
			title: '联系人资料编辑',
			padding: 0,
			content: document.getElementById('contactMod'),
			lock: true,
		},false);	
	});
	
	// 联系人资料提交验证
	$('#contactForm').validate({
		onkeyup: false
	});
		
	// 银行卡资料编辑
	$("#bankEditBtn").click(function(){
		art.dialog({
			title: '银行卡资料编辑',
			padding: 0,
			content: document.getElementById('bankMod'),
			lock: true,
		},false);
	});	

	// 银行卡资料提交验证
	$('#bankForm').validate({
		onkeyup: false,
		rules : {
			'loanBank.bankAccount' : {
				equalTo:"#firstInputBankAccount",
				digits:true,
				min:0,
				maxlength:19,
				minlength:16
			},
			'firstInputBankAccount' : {
				minlength:16
			}
		},
		messages : {
			'loanBank.bankAccount':{
				equalTo: "输入银行卡号不一致",
				digits:  "银行卡号必须为数字类型",
				min:"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
				minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
			},
			'firstInputBankAccount' : {
				minlength:$.validator.format("请输入一个长度最少是{0}的数字字符串")
			}
		}
	});
	
});

