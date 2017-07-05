var dataParam = "";
var workItemParam = "";
$(function() {
	var text = $("#backReason").val();
	var counter = 0;
	if (text != undefined) {
		counter = text.length;
	}
	$(".textareP").find("var").text(1000-counter);
	$("#backReason").keyup(function() {
		var text = $("#backReason").val();
		var counter = text.length;
		$(".textareP").find("var").text(1000-counter);
	});
	
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
				alert("操作成功！");
				if (data == "true") {
					 windowLocationHref(ctx + jumpUrl);
				} else {
					alert("操作失败！请重新操作");
				}
			},
			error: function() {
				waitingDialog.hide();
				alert("操作失败，请联系管理员");
			}
		});
	}
	
	// 审核费率 通过 保存事件
	$("#handleCheckRateInfo").click(function() {
		if(!$("#saveHandleCheckRateInfoId").validate().form()){
			return;
		}
		var submitForm = $("#saveHandleCheckRateInfoId");
//		if (!checkForm(submitForm)) {
//			return false;
//		}
		commonAjax(submitForm, "/car/carExtendContract/carContractHandle", "/car/carExtendWorkItems/fetchTaskItems/extendContractCommissionerRateCheck");
	});
	// 审核费率 退回 保存事件
	$('#backSave').click(function() {
		var submitForm = $("#backFormId");
		if (!checkForm(submitForm)) {
			return;
		}
		$('#modal_Back').modal('hide');
		commonAjax(submitForm, "/car/carExtendContract/carContractHandle", "/car/carExtendWorkItems/fetchTaskItems/extendContractCommissionerRateCheck");
	});
	// 合同制作 通过 保存事件
	$("#handleMakeContract").click(function() {
		/**
		 * 校验中间人是否填写
		 */
		if(!$('#saveContractProFormId').validate().form()){
			return;
		}
		
		var submitForm = $(this).parents("form:first");
//		if (!checkForm(submitForm)) {
//			return false;
//		}
		commonAjax(submitForm, "/car/carExtendContract/carContractHandle", "/car/carExtendWorkItems/fetchTaskItems/extendContractCommissioner");
	});
	//合同制作 退回 保存事件
	$('#backContractProSave').click(function() {
		var submitForm = $("#backFormId");
		if (!checkForm(submitForm)) {
			return false;
		}
		commonAjax(submitForm, "/car/carExtendContract/carContractHandle", "/car/carExtendWorkItems/fetchTaskItems/extendContractCommissioner");
	});
	// 合同签订上传 通过 保存事件
	$("#handleUploadContract").click(function() {
		var submitForm = $(this).parents("form:first");
//		if (!checkForm(submitForm)) {
//			return false;
//		}
		if($(".pc1Image").attr("src")=="/loan/static/images/image-1.png"||$(".pc2Image").attr("src")=="/loan/static/images/image-1.png"){
			alert("请上传照片再提交！");
			return false;
		}if($(".coboimage1").attr("src")=="/loan/static/images/image-2.png"||$(".coboimage2").attr("src")=="/loan/static/images/image-2.png"){
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
		commonAjax(submitForm, "/car/carExtendContract/carContractHandle", "/car/carExtendWorkItems/fetchTaskItems/extendReceived");
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
	// 合同审核 退回  保存事件
	$("#handleContractCheck").click(function() {
		var submitForm = $(this).parents("form:first");
		if (!checkForm(submitForm)) {
			return false;
		}
		commonAjax(submitForm, "/car/carExtendContract/carContractHandle", "/car/carExtendWorkItems/fetchTaskItems/extendContractCheck");
	});
	// 历史
	$('#historyBtn').click(function() {
		showCarLoanHis($(this).attr('loanCode'));
	});
	// 查看
	$('#showView').click(function() {
		showExtendLoanInfo($(this).attr('loanCode'));
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
		$.ajax({
			type : "POST",
			data: {
				loanCode : loanCode,
				rate : rate
			},
			url: ctx + "/car/carContract/checkRate/calculateExtendEveryAmount",
			beforeSend: function(XMLHttpRequest){
	        	waitingDialog.show();
	        },
			success : function(data) {
				$("#monthlyInterestId").val(data.monthRa.toFixed(2));
				$("#carGrantAmountId").val(data.grantAmount);
				$("#mulMoney").val(data.mulAmount.toFixed(2));
				$("#auditMoney").val(data.checkAmount.toFixed(2));
				$("#consultMoney").val(data.consultAmount.toFixed(2));
				$("#mediacyMoney").val(data.mediacyAmount.toFixed(2));
				$("#infoMoney").val(data.infoAmount.toFixed(2));
				$("#extendMoney").val(data.extendAmount.toFixed(2));
				$("#extendPayAmountId").val(data.extendPayTotalAmount.toFixed(2));
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
/**
 * 合同审核 审批结果
 */
function auditClick(self) {
	var changeFlag = $(self).val();
	if (changeFlag == 'TO_GRANT_CONFIRM') {
		$("#checkStar").hide();
		$('#checkSuggess').removeClass("required");
		$('#backNodeSelect').removeClass("required");
		$(self).parents("tr").next("tr").hide();
	} else {
		$("#checkStar").show();
		$(self).parents("tr").next("tr").show();
		$('#checkSuggess').addClass("required");
		$('#backNodeSelect').addClass("required");
	}
}
/**
 * 利率审核 取消按钮
 */
function cancelInterestBtn() {
	window.location = ctx + "/car/carExtendWorkItems/fetchTaskItems/extendContractCommissionerRateCheck";
}