$(document).ready(function(){
		// 初始化table
	$('#auditedTab').bootstrapTable('destroy');
	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height() + 220,
			striped: true,
			pageSize: 20,
			pageNumber:1
		});
	
	// 导出Excel数据表
	$("button[name='exportExcel']").click(function() {
		var contractCode = $(this).val();
		window.location.href=ctx+"/borrow/payback/manualOperation/exportMonthExcel?contractCode="+contractCode;
	});
});