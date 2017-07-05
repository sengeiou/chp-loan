
$(document).ready(function(){
	/**
	 * @function 初始化tab
	 */
	$("#doAdvanceTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#doAdvanceTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: 400,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	/**
	 * 初始化门店下拉框
	 */
	loan.getstorelsit("txtStore","hdStore");

	/**
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showSearchConfirmPaybackBtn').click(function() {
		if($("#searchConfirmPayback").css('display')=='none'){
			$("#showSearchConfirmPaybackBtn").attr('src','../../../../static/images/down.png');
			$("#searchConfirmPayback").show();
		}else{
			$("#showSearchConfirmPaybackBtn").attr("src",'../../../../static/images/up.png');
			$("#searchConfirmPayback").hide();
		}
	});
	
	/**
	 * @function 清除按钮绑定
	 */
	$('#clearBtn').bind('click', function() {
		$(':input','#confirmPaybackForm') 
		.not(':button, :submit, :reset, :hidden') 
		.val('') 
		.removeAttr('selected');
		// 清除select			
		$("select").val("");
		$("select").trigger("change");	
	});
	
	/**
	 * @function 页面办理按钮事件
	 */
	$(":input[name='doAdvanceBtn']").each(function(){
		$(this).bind('click',function(){
			$('#doAdvanceId').val($(this).attr('doAdvanceId'));
			$('#doAdvanceDictOffsetType').val($(this).attr('doAdvanceDictOffsetType'));
			$('#doAdvanceChargeStatus').val($(this).attr('doAdvanceChargeStatus'));
			$('#doAdvanceContractCode').val($(this).attr('doAdvanceContractCode'));
			$("#doAdvanceForm").submit();
		});
	});
	
	/**
	 *@function 还款日验证
	 */
	$("#paybackDayNum").blur(function(){
		var da = $("#paybackDayNum").val();
		if (da != null && "" != da) {
			var dar = eval(da);
			if (dar>31 || dar<1 ) {
				artDialog.alert('请输入1~31之间的数字!');
				$("#paybackDayNum").focus();
				return;
			}
		}
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
	$("#confirmPaybackForm").attr("action",  ctx + "/borrow/payback/doAdvanceSettled/list");
	$("#confirmPaybackForm").submit();
	return false;
}