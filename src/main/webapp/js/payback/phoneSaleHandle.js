/**
 * 提醒标示
 */
$(function(){
	$("#remindBtn").bind('click', function() {
		art.dialog({
			content: document.getElementById("remindDiv"),
			title:'标记提醒',
			fixed: true,
			lock:true,
			width:400,
			id: 'remindBtn',
			okVal: '确认',
			ok: function () {
				signRemind();
				art.dialog.close();
			},
			cancel: true
		});
		
	});

})

//全选和反选复选框   划扣金额和划扣笔数的显示
$(function(){
	$("#qishu_div3").css("display","none");
	//全选和反选
	$("#checkAll").click(function() {
		$("input[name='checkBox']").attr("checked", this.checked);
	});
	//为每一条记录的复选框绑定事件
	$("[name='checkBox']").click(function() {
		var checkBox = $("input:checkbox[name='checkBox']").length;
		var checkBoxchecked = $("input:checkbox[name='checkBox']:checked").length
		/*if(checkBoxchecked==0){
			$("#checkAll").attr("checked", false);
		}*/
		if(checkBox==checkBoxchecked){
			$("#checkAll").attr("checked", true);
		}else{
			$("#checkAll").attr("checked", false);
		}
	});

});
	
// 显示历史弹框事件
function showHirstory(applyId){
	var url="ctx + borrow/payback/payBackSplitapply/getHirstory?applyId="+applyId;	
		  $.colorbox({      
	         	href:url,
		        iframe:true,
		        width:"900",
		        height:"500"
		 });
	
}

//点击清除按钮调用的的方法
function resets(){
	// 清除text	
	$(":text").val('');
	// 清除checkbox	
	$(":checkbox").attr("checked", false);
	// 清除radio			
	$(":radio").attr("checked", false);
	// 清除select			
	$("select").val("");
	$("#CenterapplyPayForm").submit();
}

function signRemind(){
	
	if(!$("#remindStatusId").val()){
		art.dialog.alert("请选择提醒状态");
		return false;
	}
	var checkBoxchecked = $("input:checkbox[name='checkBox']:checked").length
	if(checkBoxchecked == 0){
		 art.dialog.confirm('确认全部标记？', 
				    function () {
			              signRemindupdate();
				    }, 
				    function () {
				      // art.dialog.tips('');
				});
		
	 }else{
		 var checkBoxchecked = $("input:checkbox[name='checkBox']:checked");
		 var ids = "";
		 checkBoxchecked.each(function(){
			 ids += $(this).val(); +',';
		 });
		 
		 signRemindupdate(ids);
	 }

	
	
}

function signRemindupdate(ids){
	
	  $("#remindStatusFlag").val($("#remindStatusId").val());
	  $("#ids").val(ids);
		$.ajax({  
		    type : "POST",
		    data:$("#CenterapplyPayForm").serialize(),
			url :ctx+"/borrow/payback/phonesale/signRemindupdate",
			datatype : "json",
			async: false,
			success : function(data){
				 if(data.success == true){
					 art.dialog.alert("标记成功",function(){
						 document.forms[0].submit();
					 });
				 }else{
					 art.dialog.alert("标记失败",function(){
						 document.forms[0].submit();
					 });
				 }							 
			},
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
		});
		
}

//单选弹出框
function remindBtn(id){
	art.dialog({
		content: document.getElementById("remindDiv"),
		title:'标记提醒',
		fixed: true,
		lock:true,
		width:400,
		id: 'remindBtn',
		okVal: '确认',
		ok: function () {
			remindupdate(id);
			art.dialog.close();
		},
		cancel: true
	});
	
}

function remindupdate(ids){
	
	 if(!$("#remindStatusId").val()){
		art.dialog.alert("请选择提醒状态");
		return false;
	 }
	
	  $("#remindStatusFlag").val($("#remindStatusId").val());
	  $("#ids").val(ids);
		$.ajax({  
		    type : "POST",
		    data:$("#CenterapplyPayForm").serialize(),
			url :ctx+"/borrow/payback/phonesale/signRemindupdate",
			datatype : "json",
			async: false,
			success : function(data){
				 if(data.success == true){
					 art.dialog.alert("标记成功",function(){
						 document.forms[0].submit();
					 });
				 }else{
					 art.dialog.alert("标记失败",function(){
						 document.forms[0].submit();
					 });
				 }							 
			},
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
		});
		
}

/**
 * 弹出历史界面
 * id ：提醒id
 */
function showHis(id) {
	var url = ctx + "/borrow/payback/phonesale/showPayBackHis?id=" + id;
	art.dialog.open(url, {
		id : 'his',
		title : '历史',
		lock : true,
		width : 700,
		height : 350
	}, false);
}
