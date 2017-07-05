
$(document).ready(function(){
	
	// 为下载提前结清申请资料按钮绑定事件
//	$("#downZip").click(function(){
//		window.location.href = ctx+"/borrow/payback/earlySettlement/downZip?docId="+$(this).attr('docId')+"&fileName="+$(this).attr('fileName');
//	});
	
	/**
	 * @function 结清表单提交
	 */
	$('#submitBtn').click(function() {
			var files = $('#files').val()
			if (files.length > 0) {
				files = files.substring(files.lastIndexOf("."));
				if (!(".zip" == files)) {
					artDialog.alert('请上传后缀为.zip的压缩文件!');
					return false;
				} else{
					$('#doAdvanceSettledInfoForm').submit();
				}
			} else {
				artDialog.alert('请上传提前结清资料!');
				return false;
			}
	})
	
	$("#giveUpDoStoreBtn").click(function(){
		var chargeId = $("#chargeId").val();
		var contractCode = $("#contractCode").val();
		var paybackId = $("#paybackId").val();
		art.dialog.confirm("是否确定放弃？", function(){
			$.ajax({  
				   type : "POST",
				   data:{
					   "chargeId":chargeId,
					   "contractCode":contractCode,
					   "paybackId":paybackId
				   },
					url : ctx+"/borrow/payback/doAdvanceSettled/giveUpStatus",
					datatype : "json",
					success : function(data){
						if(data == true){
							artDialog.alert("操作成功！");
						}
						setTimeout(function(){
							window.location.href=ctx+"/borrow/payback/doAdvanceSettled/list";
						},3000);
					},
					error: function(){
							artDialog.alert("服务器没有返回数据，可能服务器忙，请重试");
						}
				});
		});
		
	});
});