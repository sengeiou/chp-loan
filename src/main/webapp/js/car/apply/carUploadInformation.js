$(function(){
	// 客户资料上传确认
	$("#subBtn").click(function(){
		var url = ctx + "/car/uploadDataController/uploadDataConfirm";		
		art.dialog.confirm("是否确认上传?",function(){
			$("#loanApplyForm").attr('action',url);
			$("#loanApplyForm").submit();
		});
	});
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		var url = ctx + "/car/uploadDataController/giveUp";		
		art.dialog.confirm("是否放弃客户?",function(){
			$("#loanApplyForm").attr('action',url);
			$("#loanApplyForm").submit();
		});
	});
	//退回节点
	$("#backSure").bind('click',function(){
		var url = ctx + "/car/uploadDataController/sendBack";
		$("#loanApplyForm").attr('action',url);
		$("#loanApplyForm").submit();
	})
});