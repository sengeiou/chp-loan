$(document).ready(function(){
	//全选和反选 导出总条数
	$("#checkAll").click(function() {
		$("input:checkbox").attr("checked", this.checked);
	});
	
	
	//为每一条记录的复选框绑定事件
	$("[name='checkBoxItem']").click(function() {
		$("[name='checkBoxItem']").each(function() {
		});
		var checkBox = $("input:checkbox[name='checkBoxItem']").length;
		var checkBoxchecked = $("input:checkbox[name='checkBoxItem']:checked").length
	
		if(checkBox==checkBoxchecked){
			$("#checkAll").attr("checked", true);
		}else{
			$("#checkAll").attr("checked", false);
		}
	});
	
	
	// 导出電催后台数据列表
	$("#daoBtnList").click(function(){
		var checkVal = "";
			if($(":input[name='checkBoxItem']:checked").length>0){
				$(":input[name='checkBoxItem']:checked").each(function(){
	        		if(checkVal!="")
	        		{
	        			checkVal+=","+$(this).attr("val");
	        		}else{
	        			checkVal=$(this).attr("val");
	        		}
	        	});
			}
			window.location.href=ctx+"/borrow/payback/applyPaybackUse/exportElectricList?checkVal="+checkVal;
	});
	
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
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showSearchApplyUseBtn').click(function() {
		if($("#searchApplyUse").css('display')=='none'){
			$("#showSearchApplyUseBtn").attr('src','../../../../static/images/down.png');
			$("#searchApplyUse").show();
		}else{
			$("#showSearchApplyUseBtn").attr("src",'../../../../static/images/up.png');
			$("#searchApplyUse").hide();
		}
	});
	/**
	 * @function 日期
	 */
	$('input[name="contractEndDay"]').daterangepicker({ 
		 startDate: moment().subtract('days')
	})
	
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
	 * @function 页面办理按钮事件
	 */
	$(":input[name='applyPayBackUse']").each(function(){
		$(this).bind('click',function(){
			$("#applyPayBackContractCode").val($(this).attr('payBackUseContractCode'));
			$("#id").val($(this).attr('applyPaybackId'));
			if($(this).attr('dictMonthStatus')=='1'){
				$('#monthBeforefinish').hide();
			}else{
				$('#monthBeforefinish').show();
			}
		});
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
	$("#applyPayBackUseForm").attr("action",  ctx + "/borrow/payback/applyPaybackUse/goElectricApplyPaybackUseList?dictPaybackStatus=2");
	$("#applyPayBackUseForm").submit();
	return false;
}