var overduemanage = {};
/**
 * 批量减免
 */
overduemanage.overduemanage = function() {
	$("#centerApply").bind('click', function() {
		art.dialog({
			content: document.getElementById("backDiv"),
			title:'批量减免',
			fixed: true,
			lock:true,
			width:400,
  			id: 'backDivid',
			okVal: '确认',
			ok: function () {
				var pmId = "";
				var batchReson = $("#batchReson").val();
				if(batchReson == '' || batchReson == null){
					artDialog.alert('请输入减免原因！');
					return;
				}
				$("input:checkbox[name='checkBox']:checked").each(function(){
					if($(this).next().val()>3){
						artDialog.alert('选择的数据中有逾期天数超过3天的，请重新选择！');
						return false;
					}else{
						if(pmId == "" || pmId == null){
							pmId = $(this).val();
						}else{
							pmId = pmId + "," + $(this).val();
						}
					}
				});
				if(pmId == "" || pmId == null){
					return false;
				}
				$.ajax({  
					   type : "POST",
					   data:{
						   "pmId":pmId,
						   "batchReson":batchReson
					   },
						url : ctx+"/borrow/payback/overduemanage/batchRedce",
						datatype : "json",
						success : function(data){
							if("1" == data){
								artDialog.alert("减免成功！");
							}else{
								artDialog.alert("减免失败！");
							}
							window.location.href=ctx+"/borrow/payback/overduemanage/allOverdueManageList";
						},
						error: function(){
								artDialog.alert("服务器没有返回数据，可能服务器忙，请重试");
							}
					});
			},
			 cancelVal: '取消',
			 cancel: true
		});
	});
}
/**
 * @function 全选按钮事件
 */
function selectAll(obj){
		$("input[name='checkBox']").each(function(){
			  this.checked = !this.checked;
		  });
}

$(document).ready(function(){
	
	// 初始化tab
	$('#overDueTab').bootstrapTable('destroy');
	$('#overDueTab').bootstrapTable({
		method: 'get',
		cache: true,
		height: $(window).height() - 230,
		pageSize: 20,
		striped: true,
		pageNumber:1
	});
	
	/**
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showMore').click(function() {
		if($("#T1").css('display')=='none'){
			$("#searchHidden").show();
		}else{
			$("#searchHidden").hide();
		}
	});
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	}
	
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	// 减免按钮事件
	$("button[name='reductionAmount']").click(function(){
		$.ajax({  
			   type : "POST",
			   data:{
				   "id":$(this).val()
			   },
				url : ctx+"/borrow/payback/overduemanage/queryOverdueManage",
				datatype : "json",
				success : function(data){
					if(data != null || data != ""){
					var jsonData = eval("("+data+")");
					// 未还违约金罚息金额
					$("#noPenaltyInterest").val(jsonData.noPenaltyInterest.toFixed(2));
					$("#reductionBys").val(jsonData.reductionBy);
					$("#monthOverdueDayss").val(jsonData.monthOverdueDays);
					$("#ids").val(jsonData.id);
					$("#months").val(jsonData.months);
					$("#rPaybackIds").val(jsonData.rPaybackId);
					$("#monthReductionDays").val(jsonData.monthReductionDay);
					$("#bankNames").val(jsonData.bankName);
					$("#bankAccounts").val(jsonData.bankAccount);
					$("#bankBranchs").val(jsonData.bankBranch);
					$("#customerCertNums").val(jsonData.customerCertNum);
					$("#customerNames").val(jsonData.customerName);
					$("#contractCode").val(jsonData.contractCode);
					$("#loanCodes").val(jsonData.loanCode);
					$("#dateStrings").val(jsonData.dateString);
					$("#contractMonthRepayAmountLate").val(jsonData.contractMonthRepayAmountLate);
					// 违约金(滞纳金)及罚息
					$("#penaltyAndShoulds").val(jsonData.penaltyAndShould.toFixed(2));
					// 应还滞纳金
					$("#monthLateFee").val(jsonData.monthLateFee.toFixed(2));
					// 应还罚息
					$("#monthInterestPunishshould").val(jsonData.monthInterestPunishshould.toFixed(2));
					$("#alsoPenaltyInterest").val(jsonData.alsoPenaltyInterest.toFixed(2));
					// 减免滞纳金  
					$("#monthLateFeeReduction").val(jsonData.monthLateFeeReduction.toFixed(2));
					// 减免罚息
					$("#monthPunishReduction").val(jsonData.monthPunishReduction.toFixed(2));
					// 实还滞纳金
					$("#actualMonthLateFee").val(jsonData.actualMonthLateFee.toFixed(2));
					// 实还利息
					// $("#monthInterestPayactual").val(jsonData.monthInterestPayactual);
					// 实还罚息
					$("#monthInterestPunishactual").val(jsonData.monthInterestPunishactual.toFixed(2));
					// 应还违约金
					$("#monthPenaltyShould").val(jsonData.monthPenaltyShould.toFixed(2));
					// 实还违约金
					$("#monthPenaltyActual").val(jsonData.monthPenaltyActual.toFixed(2));
					// 减免违约金
					$("#monthPenaltyReduction").val(jsonData.monthPenaltyReduction.toFixed(2));
					$("#contractVersion").val(jsonData.contractVersion);
					$('#myModals').modal('toggle').css({
						width : '100%',
						"margin-left" : (($(document).width() -  $("#myModals").css("width").replace("px", "")) / 2),
						"margin-top" : (($(document).height() -  $("#myModals").css("height").replace("px", "")) / 2)
					});
					}else{
						return;
					}
				},
				error: function(){
						artDialog.alert('服务器没有返回数据，可能服务器忙，请重试!');
						return;
					}
			});
	});
	
	// 绑定减免金额确定按钮事件
	$("#confirms").click(function(){
		var rPaybackId=$("#rPaybackIds").val();
		var id=$("#ids").val(); 
		var reductionReson = $("#reductionReson").val();
		var contractCode = $("#contractCode").val();
		var reductionAmount=$("#reductionAmounts").val();//减免金额
		var monthLateFee=$("#monthLateFee").val(); //应还滞纳金
		var monthInterestPunishshould=$("#monthInterestPunishshould").val(); //应还罚息
		var monthLateFeeReduction = $("#monthLateFeeReduction").val();// 减免滞纳金
		var monthPunishReduction = $("#monthPunishReduction").val();// 减免罚息
		var noPenaltyInterest = $("#noPenaltyInterest").val();//未还违约金(滞纳金)及罚息
		var actualMonthLateFee = $("#actualMonthLateFee").val();// 实还滞纳金
		//var monthInterestPayactual = $("#monthInterestPayactual").val();// 实还利息
		var monthInterestPunishactual = $("#monthInterestPunishactual").val();// 实还罚息
		var monthPenaltyShould = $("#monthPenaltyShould").val();// 应还违约金
		var monthPenaltyActual = $("#monthPenaltyActual").val();// 实还违约金
		var monthPenaltyReduction = $("#monthPenaltyReduction").val();// 减免违约金
		var contractVersion = $("#contractVersion").val();
		var months = $("#months").val();
		
		if(!(/^\d+(?=\.{0,1}\d+$|$)/.test(reductionAmount))){
			artDialog.alert('输入有误，只能输入整数或小数！');
			return;
		}
		if(reductionReson == '' || reductionReson == null){
			artDialog.alert('请输入减免原因！');
			return;
		}
		if(reductionAmount == null || reductionAmount == "" || reductionAmount == '0' ){
			artDialog.alert('减免金额不能为0或不能为空！');
			return;
		} else if(parseFloat(reductionAmount) > parseFloat(noPenaltyInterest)){
			artDialog.alert('减免金额不能比未还的违约金(滞纳金)罚息大！');
			return;
		}
		else{
			$.ajax({  
				   type : "POST",
				   data:{
					   "rPaybackId":rPaybackId,
					   "id":id,
					   "contractCode":contractCode,
					   "reductionAmount":reductionAmount,
					   "monthLateFee":monthLateFee, //应还滞纳金
					   "monthInterestPunishshould":monthInterestPunishshould,// 应还罚息
					   "monthLateFeeReduction":monthLateFeeReduction,// 减免滞纳金
					   "monthPunishReduction":monthPunishReduction,// 减免罚息
					   "actualMonthLateFee":actualMonthLateFee,// 实还滞纳金
					   "monthInterestPunishactual":monthInterestPunishactual,// 实还罚息
					   // "monthInterestPayactual":monthInterestPayactual,// 实还利息
					   "monthPenaltyShould":monthPenaltyShould,// 应还违约金
					   "monthPenaltyActual":monthPenaltyActual,// 实还违约金
					   "monthPenaltyReduction":monthPenaltyReduction,// 减免违约金
					   "months":months,
					   "contractVersion":contractVersion,
					   "reductionReson":reductionReson
				   },
					url : ctx+"/borrow/payback/overduemanage/updateReductionAmount",
					datatype : "json",
					success : function(data){
						if(data == "1"){
							artDialog.alert('修改成功');
						}else if(data == "2"){
							artDialog.alert('减免成功，添加修改记录失败');
						}else{
							artDialog.alert('修改失败');
						}
						window.location.href =ctx + '/borrow/payback/overduemanage/allOverdueManageList';
					},
					error: function(){
							artDialog.alert('服务器没有返回数据，可能服务器忙，请重试');
							window.location.reload();
							return;
						}
				});
		}
	});
	
	// 调整按钮事件
	$("button[name='monthReductionDayss']").click(function(){
		
		$.ajax({  
			   type : "POST",
			   data:{
				   "id":$(this).val()
			   },
				url : ctx+"/borrow/payback/overduemanage/queryOverdueManage",
				datatype : "json",
				success : function(data){
					var jsonData = eval("("+data+")");
					$("#monthOverdueDayss").val(jsonData.monthOverdueDays);
					$("#id").val(jsonData.id);
					$("#rPaybackId").val(jsonData.rPaybackId);
					$("#monthReductionDay").val(jsonData.monthReductionDay);
					$("#bankName").val(jsonData.bankName);
					$("#bankAccount").val(jsonData.bankAccount);
					$("#bankBranchs").val(jsonData.bankBranch);
					$("#customerCertNum").val(jsonData.customerCertNum);
					$("#customerName").val(jsonData.customerName);
					$("#loanCode").val(jsonData.loanCode);
					$("#dateString").val(jsonData.dateString);
					$("#contractMonthRepayAmountLates").val(jsonData.contractMonthRepayAmountLate);
					$("#penaltyAndShould").val(jsonData.penaltyAndShould.toFixed(2));
					$('#myModal').modal('toggle').css({
						width : '100%',
						"margin-left" : (($(document).width() -  $("#myModal").css("width").replace("px", "")) / 2),
						"margin-top" : (($(document).height() -  $("#myModal").css("height").replace("px", "")) / 2)
					});
				},
				error: function(){
						artDialog.alert('服务器没有返回数据，可能服务器忙，请重试');
						return;
					}
			});
	});
	// 绑定减免天数确定按钮事件
	$("#confirm").click(function(){
		var rPaybackId=$("#rPaybackId").val();
		var id=$("#id").val();
		var monthReductionDay=$("#monthReductionDay").val();// 减免天数
		var monthOverdueDays=$("#monthOverdueDayss").val();// 逾期天数
		var reductionDay=$("#reductionDay").val();// 页面输入的减免天数
		if(!(/^\d+$/.test(reductionDay))){
			artDialog.alert('输入有误，只能输入正整数');
			return;
		}
		if(monthOverdueDays == null || monthOverdueDays == "" || monthOverdueDays == '0' ){
			artDialog.alert('没有逾期，不能减免天数');
			return;
		}
		if(eval(reductionDay) > eval(monthOverdueDays)){
			artDialog.alert('减免天数不能大于逾期天数');
			return;
		}
		if(reductionDay == "" || reductionDay == null || reductionDay == "0"){
			artDialog.alert('请填写需要调整几天');
			return;
		}else{
		 $.ajax({  
			   type : "POST",
			   data:{
				   "rPaybackId":rPaybackId,
				   "id":id,
				   "monthReductionDay":monthReductionDay,
				   "reductionDay":reductionDay
			   },
				url : ctx+"/borrow/payback/overduemanage/updateOverdueDay",
				datatype : "json",
				success : function(data){
					if(data == "1"){
						artDialog.alert('修改成功');
					}else if(data == "2"){
						artDialog.alert('修改成功，添加修改记录失败');
					}else{
						artDialog.alert('修改失败');
					}
					window.location.href =ctx + '/borrow/payback/overduemanage/allOverdueManageList';
				},
				error: function(){
						artDialog.alert('服务器没有返回数据，可能服务器忙，请重试');
						window.location.reload();
					}
			});
		}
	});
	
	
	// 清除按钮
	$("#clearBtn").click(function(){
		$(":input").val('');
		$("#auditForm").submit();	
	});
	
	/**
	 * @function 搜索条件显示隐藏事件
	 */
	$('#showMore').click(function() {
		if($("#T1").css('display')=='none'){
			$("#showMore").attr('src','../../../../static/images/down.png');
			$("#T1").show();
		}else{
			$("#showMore").attr("src",'../../../../static/images/up.png');
			$("#T1").hide();
		}
	});
	
	

	//导出Excel数据表
	$("button[name='exportExcel']").click(function() {
		var idVal = "";
		 $("input:checkbox[name='checkBox']:checked").each(function(){
			 idVal+=","+$(this).val();
			});
		$("#ids").val(idVal);
		$("#auditForm").attr("action",ctx+"/borrow/payback/overduemanage/excelList");
		$("#auditForm").submit();
		$("#auditForm").attr("action",ctx+"/borrow/payback/overduemanage/allOverdueManageList");
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
	$("#auditForm").attr("action",  ctx + "/borrow/payback/overduemanage/allOverdueManageList");
	$("#auditForm").submit();
	return false;
}