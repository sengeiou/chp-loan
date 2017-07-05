function backSubmitMes(){
	window.parent.window.jBox.warning("是否执行退回操作？", "提示", function(v, h, f){
		if(v=='yes'){
			window.parent.window.jBox.tip('已提交','success');
		}
		if(v=='no'){
			window.parent.window.jBox.tip('取消');
		}
		
	});
}