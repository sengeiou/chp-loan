$(function(){
	$("#checkAllTab").bind('click',function(){
		//验证JS
	var f=	$("#carBank").validate({
					onclick: true, 
					rules : {
						bankCardNo : {
							equalTo:"#firstInputBankAccount",
							digits:true,
							min:0,
							maxlength:19,
							minlength:16
						}
					},
					messages : {
						bankCardNo:{
							equalTo: "输入银行卡号不一致",
							digits:  "银行卡号必须为数字类型",
							min:"输入的数据不合法",
							maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
							minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
						}
					}
		}).form();
	
    	if (!checkForm($(this).parents("form"))){
    		return false;
    	}
    	 if(f==true){
			$.ajax({
				url:ctx+"/car/carApplyTask/checkAllTab",
				type:'post',
				data: $("#carBank").serialize(),
				success:function(data){
					if(data == "" ){
							//$("#carBank").submit();
						$.ajax({
							url: ctx + "/car/carApplyTask/carLoanFlowBank",
							type:'post',
							data: $("#carBank").serialize(),
							 success:function(data){
								 windowLocationHref(ctx + "/car/carApplyTask/fetchTaskItems");
							 }
						});
						$("#checkAllTab").prop("disabled",true);
					}else{
						art.dialog.alert(data);
					}
				},
				error:function(){
					art.dialog.alert('服务器异常！');
					return false;
				}
			});
    	 }
	});
});

