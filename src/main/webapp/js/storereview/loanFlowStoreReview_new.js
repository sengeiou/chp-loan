$(function(){
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
	if($("#giveUp").length>0){
		$("#giveUp").click(function(){
			var url = ctx + "/borrow/borrowlist/dispatchFlow";		
			art.dialog.confirm("是否放弃客户?",function(){
				$('#response').val('TO_GIVEUP');
				$("#storeViewSubmitFlow").prop('disabled','disabled');
				$("#storeViewSubmitForm").attr("action",url);
				$("#storeViewSubmitForm").submit();
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
						$("#storeViewSubmitFlow").prop('disabled','disabled');
						$("#storeViewSubmitForm").attr("action",url);
						$("#storeViewSubmitForm").submit();
					}
					return false;
				},
				cancel: true
			});
		});
	}
});


