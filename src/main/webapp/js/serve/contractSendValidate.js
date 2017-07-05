var contract = {};
//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号有误");

$.validator.addMethod("isChineseChar", function(value, element) {       
    return this.optional(element) || /^[\u0391-\uFFE5·]+$/.test(value);       
}, "匹配中文(包括汉字和字符) "); 

//信息验证
contract.datavalidate = function() {
	$("#inputForm").validate(
			{
				rules : {
                   'receiverPhone':{
                	   isMobile:true
                   } 
				},
				submitHandler : function() {
					//loading('正在提交，请稍等...');
					submitAjaxForm();
				}
        });
};

//数据提交后回传
function submitAjaxForm(){
	var url = ctx + "/borrow/serve/customerServe/saveContractSendInfo";
	$.ajax({
		type : "post",
		url :url,
		dataType : "text",
		data :$("#inputForm").serialize(),
		async : false,
		success : function(msg){
			if (msg == "1"){
				art.dialog.close();
				art.dialog.alert("申请成功");
			} else {
				art.dialog.close();
				art.dialog.alert(msg);
			}
		},
		error : function(msg){
			art.dialog.close();
			art.dialog.alert("申请失败");
		}
	});
}
