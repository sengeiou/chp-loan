var bankmain = {};	

/**
 * 
 */
bankmain.batchChuli = function() {
	$("#batchmodify").bind('click', function() {
		art.dialog({
			content: document.getElementById("batchmodifyDiv"),
			title:'选择划扣方式',
			fixed: true,
			lock:true,
			id: 'onlinConfirm',
			okVal: '确定',
			ok: function () {
				batchmodify();
				return false;
			},
			cancel: true
		});
	});
}	
/**
	 * @function 页面分页
	 * @param n
	 * @param s
	 * @returns {Boolean}
	 */
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#auditForm").attr("action",  ctx + "/borrow/payback/deductsDue/deductsDueMain");
		$("#auditForm").submit();
		return false;
	}

$(function(){
		// 为全选和反选绑定事件
		$("#checkAll").click(function() {
			$("input:checkbox").attr("checked", this.checked);
		});
		
		// 为修改连接绑定事件
		$(".btn_edit").click(function(){
			var method=$(this).next().next().val();
			var id=$(this).next().val();
			var methodStr="";
			var flag="1";
			if(method == 1){
				var dialog=confirm("该操作将由批量划扣修改为实时划扣，是否继续？");
				flag="0";
			}else{
				var dialog=confirm("该操作将由实时划扣修改为批量划扣，是否继续？");
			}
				   if(dialog){
					   $.ajax({  
						   type : "POST",
						   data:{
							   "id":id,
							   "flag":flag
						   },
							url : ctx+"/borrow/payback/deductsDue/realBatch",
							datatype : "json",
							success : function(data){
								//window.location.reload(true);
								window.location.href=ctx+"/borrow/payback/deductsDue/deductsDueMain";
							},
							error: function(){
									alert("服务器没有返回数据，可能服务器忙，请重试");
								}
						});
				   }else{
					   return;
				   }
		});
		//清除按钮绑定事件
		$("#reset").click(function(){
			// 清除text	
			$(":input").val('');
			$("#auditForm").attr("action",ctx + "/borrow/payback/deductsDue/deductsDueMain");
			$("#auditForm").submit();
		});
				
});

function batchmodify(){
	
	   var id="";
	   var dictDealType="";// 选择的划扣平台
	   var deductMethod=$("input:radio:checked").val();
	   $("input:checkbox[name='checkBox']:checked").each(function(){
		   id +=","+$(this).val();
	   });
	  if(!id){
		  art.dialog.alert("请选择要操作的数据！");
		  return false;
	  }
	
	  $.ajax({  
		   type : "POST",
		   data:{
			   "id":id,
			   "flag":deductMethod
		   },
			url : ctx+"/borrow/payback/deductsDue/batchUpdate",
			datatype : "json",
			success : function(data){
				if(data=="success"){
				  art.dialog(
						    {
						    	content: "处理成功",
							    title:'选择划扣方式',
							    fixed: true,
							    lock:true,
							    id: 'oflineconfirm',
							    okVal: '确认',
							    ok: function () {
							    	window.location.reload(true);
									return false;
							    }
						     }		
						    );
				}
				
			},
			error: function(){
					alert("服务器没有返回数据，可能服务器忙，请重试");
				}
		});
}

