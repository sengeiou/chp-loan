$(document).ready(function(){
	
	/**
	 * @function 初始化tab
	 */
	$('#applypaybackUseTab').bootstrapTable('destroy');
	$("#applypaybackUseTab").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#applypaybackUseTab').bootstrapTable({
		method: 'get',
		cache: false,
		height: $(window).height() - 230,
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
		$("#orgId").val('');
		$("#paybackDate").val('');
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
	
	//初始化门店下拉框
	loan.getstorelsit("orgName","orgId");
});

function doOpenTransfer(paybackId, certNum) {
	var url = ctx + '/borrow/blue/PaybackBlue/openTransferBlue?paybackId='+paybackId+'&certNum='+certNum;
		
	art.dialog.open(url, {
		id:"openTransferBlue",
		title : '蓝补转账',
		width : 800,
		lock:true,
		height : 460,
		close:function()
		{
			$("form:first").submit();
		}
	});
}

//doOpenRefund
function doOpenRefund(paybackId, certNum) {
	var url = ctx + '/borrow/blue/PaybackBlue/openRefundBlue?paybackId='+paybackId+'&certNum='+certNum;
		
	art.dialog.open(url, {
		id:"openRefundBlue",
		title : '蓝补退款',
		width : 800,
		lock:true,
		height : 500,
		close:function()
		{
			$("form:first").submit();
		}
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
	$("#applyPayBackUseForm").attr("action",  ctx + "/borrow/blue/PaybackBlue/list?dictPaybackStatus=2");
	$("#applyPayBackUseForm").submit();
	return false;
}

function updatePaybackBlue(paybackId, certNum){
	var url = ctx + '/borrow/blue/PaybackBlue/updateBluePage?paybackId='+paybackId+'&certNum='+certNum;
	
	art.dialog.open(url, {
		id:"updateBlue",
		title : '蓝补金额修改',
		width : 400,
		lock:true,
		height : 260,
		close:function()
		{
			$("form:first").submit();
		}
	});
}

function openView(contractCode,surplusBuleAmount){
	var url = ctx + '/borrow/blue/PaybackBlue/listBlue?contractCode='+contractCode+'&surplusBuleAmount='+surplusBuleAmount;
	art.dialog.open(url, {
		id:"view",
		title : '蓝补交易明细',
		width : 1024,
		lock:true,
		height : 400
	});
}