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
	$("#auditForm").attr("action",  ctx + "/borrow/payback/remindAgency/remindRepaymentAgencyList");
	$("#auditForm").submit();
	return false;
};


$(document).ready(function() {	
	//还款日验证
	$("#repaymentDate").blur(function(){
		var da = $("#repaymentDate").val();
		if (da != null && "" != da) {
			var dar = eval(da);
			if (dar>31 || dar<1 ) {
				artDialog.alert('请输入1~31之间的数字!');
				$("#repaymentDate").focus();
				return;
			}
		}
	});
	
	// 清除按钮
	$("#clearBtn").click(function(){	
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
});