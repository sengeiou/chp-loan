$(function(){
	// 客户资料上传确认
	$("#subBtn").click(function(){
		var url = ctx + "/borrow/borrowlist/dispatchFlow";		
		art.dialog.confirm("是否确认提交?",function(){
			$('#response').val('TO_STORECHECK');
			$("#loanApplyForm").attr('action',url);
			$("#loanApplyForm").submit();
		});
	});
	// 客户放弃	
	if($('#giveUpBtn').length>0){
		$("#giveUpBtn").bind('click',function(){
			var url = ctx + "/borrow/borrowlist/dispatchFlow";		
			art.dialog.confirm("是否放弃客户?",function(){
				$('#response').val('TO_GIVEUP');
				$("#loanApplyForm").attr('action',url);
				$("#loanApplyForm").submit();
			});
		});
	}
});