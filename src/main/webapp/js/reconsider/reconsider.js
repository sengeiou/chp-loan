var consider={};
	/**
	 异步加载列表数据，数据加载后将整个视图嵌入列表
	 @Parameter url  申请资料查看请求路径
	 @Parameter applyId 流程ID
	 */
consider.openform = function(url,applyId,teleFlag){
	window.location=url+"&applyId="+applyId+"&teleFlag="+teleFlag;
};
consider.retTaskItemsView =function(returnParam,teleFlag){
  if(teleFlag=='0'){
	  window.location = ctx + "/apply/reconsiderApply/queryReconsiderList";
  }else if(teleFlag=='1'){
	  window.location = ctx + "/apply/reconsiderApply/queryTelesalesReconsiderList";
  }
}; 
consider.dispatchTasks = function(formId){
  $("#"+formId).validate({
	  onkeyup: false,
	  rules : {
		'reconsiderApply.secondReconsiderMsg':{
			maxlength:500
		}  
	  },
	  messages : {
		'reconsiderApply.secondReconsiderMsg': {
			maxlength:$.validator.format("请输入一个长度最多是 {0} 的字符串")
		} 
	  },
	  submitHandler : function(form) {
		    $('#submitBtn').attr('disabled',true);
		    var url = ctx + "/apply/reconsiderApply/launchFlow";
			form.action = url;
			form.submit(); 
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
consider.autoMatch = function(inputId,hiddenId){
	$('#' + inputId).autocomplete({
		source : function(query, process) {
			var matchCount = this.options.items;// 返回结果集最大数量
			$.post(ctx + "/common/userinfo/userdata", {
				"userName" : query,
				"matchCount" : matchCount
			}, function(respData) {
				return process(respData);
			});
		},
		formatItem : function(item) {
			return item["name"] + "(" + item["userCode"] + ")";
		},
		setValue : function(item) {
			$('#' + hiddenId).val(item["id"]);
			return {
				'data-value' : item["name"],
				'real-value' : item["userCode"]
			};
		}
	});
};