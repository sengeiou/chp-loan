var outside = {};
taskParam={
		taskBtnName:'outside',
		taskStep:'外访分配代办',
		taskView:'loanflow_outside_workItems'
   } 
   reloadParam={
		curForm:'outvisitSearch',
		queueName:'HJ_SUB_MANAGER',
		childPage:'loanflow_outside_workItems'   
   }
   kvParam={
	  key:'loanFlag',
	  value:' '
   }
  var cancelFlagRetVal = "0";

outside.initMethod = function() {
	//全选绑定
	$('#checkAll').bind('click',function() {
		var checked = false;
		if ($('#checkAll').attr('checked') == 'checked'|| $('#checkAll').attr('checked')) 
		{
			checked = true;
		}
		$("input[name='taskId']").each(function() {
			$(this).attr('checked', checked);
		});
	});
	
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
		var queue="queue=HJ_SUB_MANAGER&viewName=loanflow_outsideUp_workItems";
		srcFormParam.action = ctx+'/loan/workFlow/getTaskItems';
		srcFormParam.submit();
	}
	
	//清除按钮绑定
	$('#rateAuditFormClrBtn').bind('click', function() {
		var queue="queue=HJ_SUB_MANAGER&viewName=loanflow_outside_workItems";
		window.location.href =ctx+'/loan/workFlow/getTaskItems?queue=HJ_SUB_MANAGER&viewName=loanflow_outside_workItems';
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
		         width:800,
		     	 height:360
		     },false);
		 }else{
			 art.dialog.alert("请先选择外访任务!",'提示');
		 }
	});
	
}
//外访列表门店选择框
loan.getstorelsitByDepartmentId = function(inputText,hiddenText,isSingle){
	var url="/loan/workFlow/selectOrgListByDepartmentId";
	if(isSingle!=""&isSingle!="undefined"&isSingle!=undefined){
		url=url+"?isSingle=1"
	}
	$("#selectStoresBtnByDepartmentId").click(function(){
		art.dialog.open(ctx + url, 
				{
			title:"选择门店", 
			width:660, 
			height:350, 
			lock:true,
			opacity: .1,
			okVal: '确定', 
			ok:function(){
				var iframe = this.iframe.contentWindow;
				var str="";
				var strname="";
				$("input[name='orgIds']:checked",iframe.document).each(function(){ 
					if($(this).attr("checked")){
						str += $(this).attr("id")+",";
						strname += $(this).attr("sname")+",";
					}
				});
				
				str = str.replace(/,$/g,"");
				strname = strname.replace(/,$/g,"");
				
				$("#"+inputText).val(strname);
				$("#"+hiddenText).val(str);
			}
				},false);
	})
};
//查看外访任务
function showOutVisitTaskList(loanCode){
	var url=ctx+"/loan/workFlow/loadOutVisitTaskList?loanCode="+loanCode;
	art.dialog.open(url,{
		title:"外访任务信息",
		width:700,
		height:400,
		lock:true
	},false);
}