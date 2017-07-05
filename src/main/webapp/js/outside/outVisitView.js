$(function(){
	// 主借人/共借人 tab
	$(".miss").each(function(index,element) {
		$(this).click(function(){
			$(this).addClass("active").siblings("li").removeClass("active");
			$(".body").eq(index).removeClass("hide").siblings("div").addClass("hide");
		})
	});
	
	// 外访清单 一级tab
	$("span[mark='tab']").each(function(index,element) {
		$(this).click(function(){
			$(this).addClass("click").siblings("span").removeClass("click");
			$("div[mark='content']").eq(index).removeClass("hide").siblings(".content").addClass("hide");
		})
	});
	
	// 选中复选框
	var jsons = $(".body").find("input[mark='json']");
	for(var i = 0; i < jsons.length; i++){
		var json = $(jsons[i]);
		var checks = json.val().split(",");
		for(var j = 0; j < checks.length; j++){
			json.parents(".body").find("input[mark='"+checks[j]+"']").attr("checked",true);
		}
	}
})


