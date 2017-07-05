$(document).ready(function(){
	
	
	
	//全选和反选
	$("#checkAll").click(function() {
		$("input[name='checkBox']").attr("checked", this.checked);
		var totalMoney = 0;
		var totalNum = 0;
		if (this.checked) {
			$("[name='checkBox']").each(function() {
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).next().val()?$(this).next().val():0);
			});
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
		} else {
			$("#total_money").text(0);
			$("#total_num").text(0);
		}
	});
	//为每一条记录的复选框绑定事件
	$("[name='checkBox']").click(function() {
		var totalMoney = 0.0;
		var totalNum = 0;
		$("[name='checkBox']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).next().val());
				totalNum = totalNum + 1;
			}
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
		});
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
    })
	
	
	
	
	
	
	// 加载弹出框
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
	
	// 导出已匹配成功列表
	$("#daoBtnListPosMacth").click(function(){
		var idVal = "";
		 $("input:checkbox[name='checkBoxItem']:checked").each(function(){
			 idVal+=","+$(this).next().next().val();
			});		
		$("#ids").val(idVal);
		$("#deductInfoFormPos").attr("action",ctx+"/borrow/poscard/posBacktageList/exportPosMachingBackList");
		$("#deductInfoFormPos").submit();
		$("#deductInfoFormPos").attr("action",ctx+"/borrow/poscard/posBacktageList/seeStoresAlreadyDo");
	
	});
	
	/**
	 * 页面办理按钮事件
	 */
	$(":input[name='deductInfoBtnPos']").each(function(){
		$(this).bind('click',function(){
			$('#deductContractCode').val($(this).attr('deductContractCode'));
			$('#matchingId').val($(this).attr('applyPaybackId'));
			$('#chargeId').val($(this).attr('chargeId'));
			$("#deductInfoFormPos").submit();
		});
	});
	
	//全选和反选 导出总条数
	$("#checkAll").click(function() {
		$("input:checkbox").attr("checked", this.checked);
		totalNum = 0;
		if (this.checked) {
			$("[name='checkBoxItem']").each(function() {
				totalNum = totalNum + 1;
			});
			$("#totalNum").text(totalNum);
		} else {
			$("#totalNum").text(0);
		}
	});
	
	$(".checkBoxItem").click(function(){
			totalNum = 0;
			$(".checkBoxItem:checked").each(function() {
				totalNum = totalNum + 1;
			});
			if(totalNum==$(".checkBoxItem").length){
				$("#checkAll").attr("checked",true);
			}else{
				$("#checkAll").attr("checked",false);
			}
			$("#totalNum").text(totalNum);
	})
	
	// 初始化tab
	$("#doStoreTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#doStoreTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: 350,
		striped: true,
		pageSize: 20,
		pageNumber:1
	});
	
	// 搜索条件显示隐藏事件
	$('#showSearchDeductBtn').click(function() {
		if($("#searchDeduct").css('display')=='none'){
			$("#showSearchDeductBtn").attr('src','../../../static/images/down.png');
			$("#searchDeduct").show();
		}else{
			$("#showSearchDeductBtn").attr("src",'../../../static/images/up.png');
			$("#searchDeduct").hide();
		}
	});
	
	// 清除搜索表单数据
	$('#clearDeductBtn').click(function() {
		$(':input','#auditForm') 
		.not(':button, :submit, :reset, :hidden') 
		.val('') 
		.removeAttr('selected');
	});
	
	// 页面办理按钮事件
	$(":input[name='deductInfoBtn']").each(function(){
		$(this).bind('click',function(){
			$('#deductContractCode').val($(this).attr('deductContractCode'));
			$('#matchingId').val($(this).attr('applyPaybackId'));
			$('#chargeId').val($(this).attr('chargeId'));
			$("#deductInfoForm").submit();
		});
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

});

/**
 * @function 
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#deductInfoForm").attr("action",  ctx + "/borrow/payback/doStore/list");
	$("#deductInfoForm").submit();
	return false;
}
