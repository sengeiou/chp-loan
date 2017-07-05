var manager = {}

manager.init = function(){
	//企业类型
	$("select[name$='compType']").each(function(){
		manager.initCompType($(this));
		$(this).bind("change", function(){
			manager.initCompType($(this));
		});
	});	
}

manager.initCompType = function(ele){
	var value = ele.val();
	var remark = ele.siblings("input[name$='compTypeRemark']");
	if(value == 4){
		remark.show();
		remark.addClass("required");
	}else{
		remark.hide();
		remark.val("");
		remark.removeClass("required");
	}
}