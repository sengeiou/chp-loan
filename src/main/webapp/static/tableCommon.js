$(document).ready(function(){
	$("#reportTable").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#reportTable').bootstrapTable({
		method: 'get',
		cache: false,
		height: 400,
		
		pageSize: 20,
		pageNumber:1
	});
	$(window).resize(function () {
		$('#reportTable').bootstrapTable('resetView');
	});
});