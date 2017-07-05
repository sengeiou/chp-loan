var dataParam = "";
var workItemParam = "";
$(function() {
	dataParam = $("#param").serialize();
	workItemParam = $("#workParam").serialize();
	/**
	 * 公共表单提交方法
	 * <br>参数说明：
	 * submitForm 需要提交的form
	 * ajaxUrl 请求处理url
	 * jumpUrl 请求完成后跳转的url，用于刷新页面
	 * <br>
	 * 页面上需定义:
	 * id为param的表单，用于传递额外参数；id为workParam的表单，用于工作流
	 */
	function commonAjax(submitForm, ajaxUrl, jumpUrl) {
		$.ajax({
			type : "POST",
			data: submitForm.serialize() + "&" + dataParam + "&" + workItemParam,
			url: ctx + ajaxUrl,
			beforeSend : function(){
				waitingDialog.show();
			},
			success : function(data) {
				waitingDialog.hide();
				if (data == "true") {
					alert("操作成功！");
					windowLocationHref(ctx + jumpUrl);
				} else if (data == "noSubmit") {
					alert("已超有效期，不能进行提交！");
				} else if (data == "false") {
					alert("操作失败！请重新操作");
				}
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				waitingDialog.hide();
				alert("操作失败，请联系管理员");
			},
			complete:function (XMLHttpRequest, textStatus) {
				waitingDialog.hide();
				if (data == "true") {
					alert("操作成功！");
					windowLocationHref(ctx + jumpUrl);
				} else {
					alert("操作失败！请重新操作");
				}
			}
		});
	}
	
	// 审核费率 通过 保存事件
	$("#handleCheckRateInfo").click(function() {
		if(!$("#saveHandleCheckRateInfoId").validate().form()){
			return;
		}
		var submitForm = $(this).parents("form:first");
//		if (!checkForm(submitForm)) {
//			return false;
//		}
		commonAjax(submitForm, "/car/carContract/checkRate/carContractHandle", "/car/carLoanWorkItems/fetchTaskItems/rateCheck");
	});
	// 审核费率 退回 保存事件
	$('#backSave').click(function() {
		var submitForm = $("#backFormId");
		if (!checkForm(submitForm)) {
			return false;
		}
		commonAjax(submitForm, "/car/carContract/checkRate/carContractHandle", "/car/carLoanWorkItems/fetchTaskItems/rateCheck");
	});
	// 合同制作 通过 保存事件
	$("#handleMakeContract").click(function() {
		/**
		 * 校验中间人是否填写
		 */
		if(!$('#saveContractProFormId').validate().form()){
			return;
		}
		
		var submitForm = $('#saveContractProFormId');
//		if (!checkForm(submitForm)) {
//			return false;
//		}
		commonAjax(submitForm, "/car/carContract/checkRate/carContractHandle", "/car/carLoanWorkItems/fetchTaskItems/contractCommissioner");
	});
	//合同制作 退回 保存事件
	$('#backContractProSave111').click(function() {
		var submitForm = $("#backFormId111");
		var check = $("#backFormId111").validate().form();
		if(check == true){
			$.ajax({
				type : "POST",
				data: submitForm.serialize() + "&" + dataParam + "&" + workItemParam,
				url: ctx + "/car/carContract/checkRate/carContractHandle",
				dataType:"text",
				beforeSend : function(){
					waitingDialog.show();
				},
				success : function(data) {
					waitingDialog.hide();
					if (data == "true") {
						alert("操作成功！");
						windowLocationHref(ctx + "/car/carLoanWorkItems/fetchTaskItems/contractCommissioner");
					} else {
						alert("操作失败！请重新操作");
					}
				},
				error: function(XMLHttpRequest, textStatus, errorThrown) {
					waitingDialog.hide();
					alert("操作失败，请联系管理员");
				}
			});
		} else {
			return false;
		}
		
		//var check = $("#backFormId").validate().form();
		/*if(check == true){
			commonAjax(submitForm, "/car/carContract/checkRate/carContractHandle", "/car/carLoanWorkItems/fetchTaskItems/contractCommissioner");
		} else {
			return false;
		}*/
	});
	// 合同签订上传 通过 保存事件
	$("#handleUploadContract").click(function() {
		var submitForm = $(this).parents("form:first");
//		if (!checkForm(submitForm)) {
//			return false;
//		}
		
		if($(".pc1Image").attr("src")=="/loan/static/images/image-1.png"||$(".pc2Image").attr("src")=="/loan/static/images/image-2.png"){
			alert("请上传照片再提交！");
			return false;
		}if($(".coboimage1").attr("src")=="/loan/static/images/image-1.png"||$(".coboimage2").attr("src")=="/loan/static/images/image-2.png"){
			alert("请上传共借人照片再提交！");
			return false;
		}
		var bankSigningPlatform = $('#bankSigningPlatform option:selected').val();
		if(bankSigningPlatform=='' || bankSigningPlatform==null){
		  $('#bankSigningPlatformMsg').html("请选择签约平台");
		  return false;
		}else{
		  $('#bankSigningPlatformHid').val(bankSigningPlatform);
		  var bankSigningPlatformName = $('#bankSigningPlatform option:selected').val();
		 
		  $('#signingPlatformName').val(bankSigningPlatformName);
		  $('#bankSigningPlatformMsg').html("");	
		}
		 var  result = validateID();
		 if(!result){
			 art.dialog.alert("身份未验证通过，禁止提交！"); 
			 return result;
		 }
		if (confirm("你确认要提交吗？")){
			commonAjax(submitForm, "/car/carContract/checkRate/carContractHandle", "/car/carApplyTask/fetchTaskItems");
		}
		
	});
	
	function validateID(){
		var result = true;
    	if($('#mainBorrower').attr('validResult')!='1'){
    		result = false;
    	}
    	if($("input[id^='coboIDValidBtn']").length>0){
    		if(result){
    		   $("input[id^='coboIDValidBtn']").each(function(){
    			   if($(this).attr('validResult')!='1'){
    		    		result = false;
    		    	}
    		   }); 
    		}
    	}
    	return result;
	}
	// 合同审核  保存事件
	$("#handleContractCheck").click(function() {
		var submitForm = $("#carContractForm");
		if (!checkForm(submitForm)) {
			return false;
		}
		commonAjax(submitForm, "/car/carContract/checkRate/carContractHandle", "/car/carLoanWorkItems/fetchTaskItems/contractCheck");
	});
	// 历史
	$('#historyBtn').click(function() {
		showCarLoanHis($(this).attr('loanCode'));
	});
	// 查看
	$('#showView').click(function() {
		showCarLoanInfo($(this).attr('loanCode'));
	});
	//客户放弃
	$('#custGiveUp').bind('click', function() {
		art.dialog.confirm('是否确定执行放弃操作', function() {
			$('#dictOperateResult').val("3");
			dispatchFlow('', 'CUSTOMER_GIVE_UP', REDIRECT_URL);
			var dialog = art.dialog({
				title : '请等待..',
				lock : true
			});
		}, function() {
			
		});
	});
	// 协议查看
	$('#protclBtn').bind('click', function() {
		protclShow($(this).attr('applyId'), $(this).attr('loanCode'));
	});
	// 大额查看
	$('#largeAmountBtn').click(function(){
		largeAmountShow($(this).attr('applyId'), $(this).attr('loanCode'));
	});
	// 利息率改变事件
	$("#interestRateId").change(function() {
		
		var rate = $(this).val();
		var loanCode = $("#rateLoanCodeId").val();
		var outVisitFee = $("#outVisitFee").val();
		$.ajax({
			type : "POST",
			data: {
				loanCode : loanCode,
				rate : rate,
				outVisitFee:outVisitFee
			},
			url: ctx + "/car/carContract/checkRate/calculateLoanEveryAmount",
			beforeSend: function(XMLHttpRequest){
	        	waitingDialog.show();
	        },
			success : function(data) {
				$("#monthlyInterestId").val(data.monthRa.toFixed(2));
				$("#monthlyInterestIdHidden").val(data.monthRa.toFixed(5));
				
				$("#actualPayMoney").val(data.autualAmount.toFixed(2));
				$("#actualPayMoneyHidden").val(data.autualAmount.toFixed(5));
				
				$("#firstServerMoneyId").val(data.firstServerAmount.toFixed(2));
				$("#firstServerMoneyIdHidden").val(data.firstServerAmount.toFixed(5));
				
				$("#carGrantAmountId").val(data.grantAmount.toFixed(5));
				$("#grantAmountId").val(data.grantAmount.toFixed(5));
				$("#deductAmountId").val(data.deductAmount.toFixed(5));
				$("#deductAmount").val(data.deductAmount.toFixed(5));
				
				$("#monthBackAmount").val(data.monthPayAmount.toFixed(2));
				$("#monthBackAmountHidden").val(data.monthPayAmount.toFixed(5));
				
				$("#mulMoney").val(data.mulAmount.toFixed(2));//实放金额
				$("#mulMoneyHidden").val(data.mulAmount.toFixed(5));//实放金额
				
				$("#auditMoney").val(data.checkAmount.toFixed(2));
				$("#auditMoneyHidden").val(data.checkAmount.toFixed(5));
				
				$("#consultMoney").val(data.consultAmount.toFixed(2));
				$("#consultMoneyHidden").val(data.consultAmount.toFixed(5));
				
				$("#mediacyMoney").val(data.mediacyAmount.toFixed(2));
				$("#mediacyMoneyHidden").val(data.mediacyAmount.toFixed(5));
				
				$("#infoMoney").val(data.infoAmount.toFixed(2));
				$("#infoMoneyHidden").val(data.infoAmount.toFixed(5));
				waitingDialog.hide();
			},
			error: function() {
				waitingDialog.hide();
				alert("操作失败，请联系管理员");
			}
		});
	});
});
/**
 * 退回弹出框
 */
function backShow(){
	$('#modal_Back').modal('show');
}