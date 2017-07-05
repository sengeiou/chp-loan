var infos = {};
  $(document).ready(function(){
	  infos.datavalidate();
  });
function updateRefund(formId) {
	
	$.ajax({
		url : ctx + '/borrow/blue/PaybackBlue/updateBlue',
		type : 'post',
		data : $('#' + formId).serialize(),
		dataType : 'text',
		success : function(data) {
			if (data) {
 				// 如果父页面重载或者关闭其子对话框全部会关闭
				var win = art.dialog.open.origin;//来源页面
				art.dialog.close();
				art.dialog.alert("修改成功！");
			} else {
				art.dialog.alert("修改失败,请重试！");
			}
		},
		error : function() {
			art.dialog.alert("服务器异常,请重试！");
		},
		async : false

	});
}

infos.datavalidate = function() {
	$("#inputForm").validate(
			{
				onkeyup : false,
				submitHandler : function(form) {
					updateRefund("inputForm");
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