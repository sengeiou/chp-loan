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
		$("#auditForm").attr("action",  ctx + "/borrow/payback/manualOperation/allManualOperationList");
		$("#auditForm").submit();
		return false;
	}

$(document).ready(function(){
	var totalMoney = 0.00;// 冲抵总金额
	var totalNum = 0;// 笔数
	// 复选框：全选和反选
	$("#checkAll").click(function() {
		totalMoney = 0.00;
		totalNum = 0;
		$("input:checkbox").attr("checked", this.checked);
		if (this.checked) {
			$("[name='checkBox']").each(function() {
				var value=$(this).val();
				var values=value.split(",");
				totalNum = totalNum + 1;
				totalMoney = totalMoney + Number(values[1]);
			});
			$("#total_money").text(parseFloat(totalMoney).toFixed(2));
			$("#total_num").text(totalNum);
		} else {
			$("#total_money").text("0.00");
			$("#total_num").text(0);
			totalMoney = 0.00;
			totalNum = 0;
		}
	});
	
	// 复选框：单个复选框绑定事件
	$("[name='checkBox']").each(function(){
			$(this).bind('click', function() {
				if (this.checked) {
					var value=$(this).val();
					var values=value.split(",");
					totalNum = totalNum + 1;
					totalMoney = totalMoney + Number(values[1]);
				}else{
					var value=$(this).val();
					var values=value.split(",");
					totalNum = totalNum - 1;
					totalMoney = totalMoney - Number(values[1]);
					$("#checkAll").removeAttr("checked");
				}
				$("#total_money").text(parseFloat(totalMoney).toFixed(2));
				$("#total_num").text(totalNum);
				if($("input:checkbox[name='checkBox']:checked").length==0){
					$("#checkAll").removeAttr("checked");
				}
			});
	});
	
	var id ="";// 期供id
	var chargeId="";// 冲抵ID
	// 批量冲抵按钮事件
	$("button[name='querys']").click(function() {
		var ids ="";
		var values = "";
		var chargeIds="";
		var contractMonthRepayAmount = "" ;// 期供金额
		var paybackBuleAmount = "";// 蓝补金额
		var checkValue=$("input:checkbox[name='checkBox']:checked").val();
		if(checkValue == null){
			artDialog.alert('请选择需要冲抵的数据!');
			return;
		}
		$("input:checkbox[name='checkBox']:checked").each(function(){
			 if(checkValue != null){
				 var value=$(this).val();
				 values=value.split(",");
				 ids +=";"+ values[0];// 期供ID
				 contractMonthRepayAmount +=";" + values[1]; // 期供金额
				 paybackBuleAmount +=";" + values[2]; // 蓝补金额
				 chargeIds +=";" + values[3];// 冲抵ID
			 }
			});
		var qigongAmount = contractMonthRepayAmount.split(";");// 拆分期供金额与蓝补金额对比
		var buleAmount = paybackBuleAmount.split(";");// 拆分蓝补金额与期供金额对比
		for(var i=0;i<values.length-1;i++){
			if(Number(qigongAmount[i+1]) > Number(buleAmount[i+1])){
				artDialog.alert('蓝补金额不足，不能批量冲抵！');
				return;
			}
		}
		$('#determine').modal('toggle').css({
			"margin-left" : (($(document).width() -  $("#determine").css("width").replace("px", "")) / 2),
			"margin-top" : (($(document).height() -  $("#determine").css("height").replace("px", "")) / 2)
		});
			$("#money").text(parseFloat(totalMoney).toFixed(2));
			$("#count").text(totalNum);
		id=ids;
		chargeId=chargeIds;
	});
	
	// 风控冲抵页面 确认按钮绑定事件
	$("#confirm").click(function() {
		$.ajax({  
		   type : "POST",
		   async:true,
		   data:{
			   'ids':id,
			   'chargeId':chargeId
		   },
			url : ctx+"/borrow/payback/manualOperation/queryChargeAgainst",
			datatype : "json",
			success : function(data){
				if(data == "1"){
					artDialog.alert('冲抵成功！');
				}else if(data == "2"){
					artDialog.alert('蓝补金额不足,请刷新页面后重新操作!');
				}else if(data == "0"){
					artDialog.alert('冲抵失败,请认真查询期供数据是否有误!');
				}
				window.location.reload();
			},
			error: function(){
					artDialog.alert('服务器没有返回数据，可能服务器忙，请重试');
					return;
				}
		});
	});
	
	// 绑定查看页面弹出框事件
	$("button[name='seeDate']").click(function() {
		var value = $(this).val();
		var dates = value.split(",");
		var contractCode = dates[0];
		var id = dates[1];
		var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode+"&id="+id;
		art.dialog.open(url, {  
	         id: 'his',
	         title: '还款明细',
	         lock:true,
	         width:1200,
	     	 height:500
	     },  
	     false);
	});
	
	// 导出Excel数据表
	$("button[name='exportExcel']").click(function() {
		var idVal = "";
		var checkList=$("input:checkbox[name='checkBox']:checked").val();
		$("input:checkbox[name='checkBox']:checked").each(function(){
			 if(checkList != null){
				 var value=$(this).val();
				 values=value.split(",");
				 idVal +=";"+ values[3];// 冲抵申请ID
			 }
			});
		window.location.href=ctx+"/borrow/payback/manualOperation/exportExcel?idVal="+idVal;
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
