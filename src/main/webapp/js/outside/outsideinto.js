var outside = {};

outside.initMethod = function() {
	//搜索绑定
	$('#searchBtn').bind('click',function() {
		searchList("outvisitSearch");
	});
	
	/**
	 异步加载列表数据，数据加载后将整个视图嵌入列表
	 @Parameter srcForm  查询参数表单
	 @Parameter queue    队列集合名称
	 @Parameter viewName 需要返回的视图名称
	 */
	function searchList(srcForm) {
		var srcFormParam = $('#' + srcForm);
		var queue="queue=HJ_VISIT_COMMISSIONER&viewName=loanflow_outsideUp_workItems";
		srcFormParam.action = ctx+'/loan/workFlow/getTaskItems';
		srcFormParam.submit();
	}
	
	//清除按钮绑定
	$('#rateAuditFormClrBtn').bind('click', function() {
		var queue="queue=HJ_VISIT_COMMISSIONER&viewName=loanflow_outsideUp_workItems";
		window.location.href =ctx+'/loan/workFlow/getTaskItems?queue=HJ_VISIT_COMMISSIONER&viewName=loanflow_outsideUp_workItems';
	});
	
	//分配外访任务绑定
	$("#allotTaskBtn").bind('click',function(){
		 var taskIdlist = getCheckedCheckBoxValue('taskId');
		 if(taskIdlist != ''){
			 var url=ctx + "/loan/workFlow/getOutPeopleList";
			 art.dialog.open(url, {  
		         id: 'outvisitor',
		         title: '外访人员',
		         lock:true,
		         width:500,
		     	 height:250
		     },false);
		 }else{
			 art.dialog.alert("请先选择外访任务!",'提示');
		 }
	});
}