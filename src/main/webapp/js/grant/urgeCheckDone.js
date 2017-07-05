$(document).ready(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	
	/**
	 * 清除按钮，
	 */
	$("#clearBtn").click(function(){			
		$(':input','#urgeCheckDoneForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#urgeCheckDoneForm").submit();
	});
	
	/**
	 * 全选
	 */
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
	
	/**
	 * 金额区间值的校验
	 */
	$("#urgeReallyAmountMin").live("blur",function(){  //金额输入框失去焦点时，进行格式的验证
		var maxMoney = $("#urgeReallyAmountMin").val();
		var index = maxMoney.indexOf(".");
	    if(index>0 && maxMoney.substring(index).length>3) {
	    	art.dialog.alert("请输入有效的金额;，小数位最多为两位")
	    	$(this).val("");
	        return false;
	    }
	    if(isNaN(maxMoney)) { //不是数字isNaN返回true
	    	art.dialog.alert("请输入有效的金额格式！");
	    	$(this).val("");
	        return false;
	    }
		if((maxMoney!=" "||maxMoney!=null)&& maxMoney < 0){
			art.dialog.alert("输入金额要大于0");
			$(this).val("");
			return false;
		}
	});
	
	$("#urgeReallyAmountMax").live("blur",function(){//金额输入框失去焦点时，进行格式的验证
		var maxMoney = $("#urgeReallyAmountMax").val();
		var maxMoneyStart = $("#urgeReallyAmountMin").val();
		var index = maxMoney.indexOf(".");
	    if(index>0 && maxMoney.substring(index).length>3) {
	    	art.dialog.alert("请输入有效的金额，小数位最多为两位");
	    	$(this).val("");
	        return false;
	    }
	    if(isNaN(maxMoney)) {//不是数字isNaN返回true
	    	art.dialog.alert("请输入有效的金额格式！");
	    	$(this).val("");
	        return false;
	    }
		if(maxMoney!=""&& maxMoney <= 0){
			art.dialog.alert("输入金额要大于0");
			$(this).val("");
			return false;
		}
		if(maxMoney!="" && maxMoneyStart!="" && parseFloat(maxMoney) < parseFloat(maxMoneyStart)){
			art.dialog.alert("起始金额不大于终止金额！");
	    	return false;
		}
	});
	
	/**
	 * 页面点击查看按钮事件，合同编号，申请表的id
	 */
	$(":input[name='doneInfoBtn']").each(function(){
		$(this).bind('click',function(){
			$('#applyId').val($(this).attr('matchingId'));
			$('#matchingContractCode').val($(this).attr('contractCode'));
			$("#matchingInfoForm").submit();
		});
	});
	
	/**
	 * 导出excel，如果有选中的，将选中的导出，如果没有，默认导出查询条件下的全部
	 */
	$('#daoBtn').bind('click', function() {
		var urgeCheck = $("#urgeCheckDoneForm").serialize();
		var checkApplyId = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(checkApplyId!="")
        		{
        			checkApplyId+=","+$(this).val();
        		}else{
        			checkApplyId=$(this).val();
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/urgeCheckDone/urgeCheckDoneExl?checkApplyId="+checkApplyId+"&"+urgeCheck;
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
	$("#urgeCheckDoneForm").attr("action",  ctx + "/borrow/grant/urgeCheckDone/urgeCheckDoneInfo");
	$("#urgeCheckDoneForm").submit();
	return false;
}