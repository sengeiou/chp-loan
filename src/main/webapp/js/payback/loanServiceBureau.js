$(document).ready(function(){
	
		// 初始化门店下拉框
		loan.getstorelsit("txtStore","hdStore");
	
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
		
		// 集中划扣申请已办清除按钮
		$("#clearBtn").click(function(){	
			$(':input','#auditForm') 
			.not(':submit, :reset, :hidden') 
			.val('') 
			.removeAttr('selected');
			$("#auditForm").attr("action",  ctx + "/borrow/payback/loanServices/centerApplyHaveToList");
			$("#auditForm").submit();
		});
		
		// 待提前结清已办列表页面清除按钮
		$("#clearBtnEarlyExam").click(function(){	
			// 清除text	
			$(':input','#auditForm') 
			.not(':submit, :reset, :hidden') 
			.val('') 
			.removeAttr('selected');
			$("#auditForm").attr("action",  ctx + "/borrow/payback/loanServices/earlyExamHavetoList");
			$("#auditForm").submit();
		});
});