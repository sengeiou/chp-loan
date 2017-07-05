$(document).ready(function(){
	//查询按钮事件
	$("#queryButton").click(function() {
		//校验
		var contarctCode = $('#contractCode').val();
		if(/^[\u4e00-\u9fa5]+$/.test(contarctCode)){
			alert("不能输入汉字")
			return false;
			};
		$({  
		   type : "POST",
		   data:{
			   custName:$('#custName').val(),
			   contractCode:$('#contractCode').val(),
			   startTime:$('#startTime').val(),
			   endTime:$('#endTime').val(),
			   strote:$('#strote').val(),
			   teamManager:$('#teamManager').val(),
			   manager:$('#manager').val(),
			   newStrote:$('#newStrote').val(),
			   newTeamManager:$('#newTeamManager').val(),
			   newManager:$('#newManager').val()
		   },
			url : ctx+"/borrow/taskDeliveryInfo/queryTaskDelivery?",
			datatype : "json"
			
		});
	});
	
	//重置查询条件
	$("#clearButton").click(function() {
		$("input").val(''); 
	});
	
	
});

