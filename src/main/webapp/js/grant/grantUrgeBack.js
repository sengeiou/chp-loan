$(document).ready(function(){
	
	// 点击清除，清除搜索页面中的数据
	$("#clearBtn").click(function(){		
		$(':input','#urgeForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected'); 
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
			});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
	});
	
	// 放款划扣退回导出excel，根据催收退回表中的关联id进行导出
	$("#daoBtn").click(function(){
		 var urgeId = "";
		 var backForm = $("#urgeForm").serialize(); 
			if($(":input[name='checkBoxItem']:checked").length>0){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(urgeId!="")
	        		{
	        			urgeId+=","+$(this).attr("urgeId");
	        		}else{
	        			urgeId=$(this).attr("urgeId");
	        		}
	        	});
			}
			window.location.href=ctx+"/borrow/grant/grantDeductsBack/importBack?urgeId="+urgeId+"&"+backForm;
	});
	
	// 点击查看，对催收成功的数据进行详细的查看，只有退回成功的有这个按钮
		$(":input[name='seeBtn']").each(function(){
			$(this).bind('click',function(){
				var urgeId = $(this).attr("sid");
				window.location.href = ctx+'/borrow/grant/grantDeductsBack/toDeal?urgeId='+urgeId;
			});
		});
		
	// 查看单子的全部历史，需要查询两张表，借款状态变更表，还款操作流水表
		$(":input[name='historyBtn']").each(function(){
			$(this).bind('click',function(){
				// 获得单子的催收id
				var contractCode = $(this).attr("contractCode");
				var url = ctx+'/borrow/grant/grantDeductsBack/history?contractCode='+contractCode;
				var title = "历史";
				jump(url,title);
			});
		});
		
		// 已收记录页面查看
		$(":input[name='detailBtn']").each(function(){
			$(this).bind('click',function(){
				var backDoneId = $(this).attr("detailId");
				var url = ctx+'/borrow/grant/grantDeductsBack/urgeDone?backDoneId='+backDoneId;
				var title  = "已收记录";
				jump(url,title);
			});
		});
	
	// 对选中的单子进行放款确认
	$("#backSureBtn").click(function(){
		var backId = "";
		if($(":input[name='checkBoxItem']:checked").length == 0){
			var flag = confirm("您确定将所有的待退款的单子进行确认退款？");
			if(flag){
				sureDeal(backId);
			}else{
				return false;
			}
		}else{
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(backId!="")
        		{
        			backId+=","+$(this).attr("urgeId");
        		}else{
        			backId=$(this).attr("urgeId");
        		}
        	});
			// 对urgeId进行处理
			sureDeal(backId);
		}
	});
	
	// 确认退款，点击确定
	$("#dateSure").click(function(){
		// 首先判断中间人和确认日期是否为空
		var win = art.dialog.open.origin;//来源页面
		var backId = $("#urgeId",win.document).val();
		var middleCode = $(":input[name='SelectBank']:checked").val();
		var backDate = $("#backDate").val();
		if(middleCode==null && backDate==""){
			art.dialog.alert("请选择相应信息");
			return false;
		}else if(middleCode==null && backDate!= null){
			art.dialog.alert("请选择中间人信息");
			return false;
		}else if(middleCode!=null && backDate == ""){
			art.dialog.alert("请选择退款日期");
			return false;
		}else{
			// 获得中间人和退款日期，更新退回表
			backSure(middleCode,backDate,backId);
		}
	});
	
	
});	

//退回的处理，点击确定
function backSure(middleCode,backDate,backId){
	$.ajax({
		type : 'post',
		url : ctx+'/borrow/grant/grantDeductsBack/grantUrgeSure',
		data:{
			'backId':backId,
			'middleCode':middleCode,
			'backDate':backDate
		},
		success:function(data){
			if(data){
				alert("确认退款成功");
				// 重定向到列表页面
				art.dialog.open.origin.location.href = ctx+"/borrow/grant/grantDeductsBack/grantDeductsBackInfo";
			}else{
				alert("确认退款失败");
				art.dialog.close();
			}
		}
	});
}

// 确认退款处理
function sureDeal(backId){
	var backForm = $("#urgeForm").serialize(); 
	$.ajax({
		type : 'post',
		url : ctx+'/borrow/grant/grantDeductsBack/sureDeal',
		data:{
			'backId':backId,
			'backForm':backForm
		},
		success:function(data){
			if(data!="null"){
				// 暂时存放urgeId
				$("#urgeId").val(data);
				// 弹框进行中间人的选中，以及退款日期的确定
				var url = ctx+'/borrow/grant/grantDeductsBack/backSure';
				var title = "确认退款";
				jump(url,title);
			}else{
				art.dialog.alert("没有要进行退款的数据");
			}
		}
	});
}

// 样式控制
function jump(url,title){
	art.dialog.open(url, {  
		   title: title,
		   lock:true,
		   width:800,
		   height:450
		},false);
}