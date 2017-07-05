$(document).ready(function(){
	
	/**
	 * @function 初始化tab
	 */
	$("#applypaybackUseTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#applypaybackUseTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: 350,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
	
	
	
	/**
	 * @function 清除搜索表单数据
	 */
	$('#applyPayBackUseClearBtn').click(function() {
		$(':input','#applyPayBackUseForm') 
		.not(':button, :submit, :reset, :hidden') 
		.val('') 
		.removeAttr('selected');
	});
	
	
	
	/**
	 * @function 页面弹出框事件
	 */
	$('#applyPayBackUsebtn').click(function() {
		$("#appplyPayBackUseForm").submit();
	});
	
	// 清除按钮
	$("#applyPayBackUseClearBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除number
		$("#repaymentDate").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio			
		$(":radio").attr("checked", false);
		// 清除select			
		$("select").val("");
		$("select").trigger("change");	
	});
	
	// Excel列表导出
	$("#dao").click(function(){
		 var cid = "";
		 var urgeMoney = $("#applyPayBackUseForm").serialize();
		 window.location.href=ctx+"/borrow/blue/PaybackBlue/listBlueimp?cid="+cid+"&"+urgeMoney;
	})
	
	// 返回按钮
	$("#backBtn").click(function(){
		window.location = ctx + "/borrow/blue/PaybackBlue/list";
	});	
});



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
	$("#applyPayBackUseForm").attr("action",  ctx + "/borrow/blue/PaybackBlue/listBlue");
	$("#applyPayBackUseForm").submit();
	return false;
}