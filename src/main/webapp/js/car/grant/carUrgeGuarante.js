
$(document).ready(function(){
	
	// 点击清除，清除搜索页面中的数据
	$("#clearBtn").click(function(){	
		$(':input','#backForm')
		  .not(':button,:submit, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected'); 
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		var deductAmount=$("#deduct").val();
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
			});
			$("#deductAmount").text(fmoney(deductAmount,2));
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			$("#deductAmount").text(fmoney(deductAmount,2));
		}
		
	});
	
	// 计算金额,
	$(":input[name='checkBoxItem']").click(function(){
		// 记录总金额，当length为0时，进行总金额的处理
		var totalDeduct=$("#deduct").val();
		// 获得单个单子的金额
		var deductAmount=parseFloat($(this).attr("deductAmount"));
		if($(this).attr('checked')=='checked'|| $(this).attr('checked')){
			var hiddenDeduct=parseFloat($("#hiddenDeduct").val())+deductAmount;
			$("#hiddenDeduct").val(hiddenDeduct);
			$('#deductAmount').text(fmoney(hiddenDeduct,2));
		}else{
		 if($(":input[name='checkBoxItem']:checked").length==0){
				$("#deductAmount").text(fmoney(totalDeduct,2));
				$("#hiddenDeduct").val(0.00);
		       }else{
		    	var hiddenDeduct=parseFloat($("#hiddenDeduct").val())-deductAmount;
		   		$("#deductAmount").text(fmoney(hiddenDeduct,2));
		   		$("#hiddenDeduct").val(hiddenDeduct);
		       }
	 }
	});
	
	//点击查询历史
	$("#btHistory").click(function(){
		var rPaybackId=$(this).attr("sid");
		var url=ctx+'/common/history/showFeekHis?payBackApplyId='+rPaybackId;
		art.dialog.open(url, {  
			   title: '查看历史',
			   lock:true,
			   width:700,
			   height:350
			},false); 
	});
	
	//提交划扣,只有状态为失败的单子才可以进行提交划扣操作，同时，同一条单子不能被提交划扣两次  
	$("#btDeduct").click(function(){
		var rPaybackId=$(this).attr("sid");
	$.ajax({
		type : 'post',
		url : ctx + '/car/grant/deductCost/submitDeducts',
		data : {
			'rPaybackId':rPaybackId
		},
		success : function(result) {
				if(result=="success"){
					alert("提交划扣成功");
					window.location.href = ctx + '/car/grant/deductCost/deductCostStayRecoverList';
				}else if(result=="deal"){
					art.dialog.alert("该单子正在处理中，不能进行提交划扣操作!");
				}else{
					art.dialog.alert(result);
				}
		},
		error : function() {
			art.dialog.alert('提交划扣异常！');
		}
	});
	});
	/**
	 * @function 催收服务费查账显示和隐藏 by zhangfeng
	 */
	$('#btCheck').click(function() {
		var splitBackResult = $("#splitBackResult").val();
		if(splitBackResult=="划扣失败"||splitBackResult=="查账退回"||splitBackResult=="处理中(导出)"){
			if($("#urgeAudite").css('display')=='none'){
				$("#urgeAudite").show();
				$("#urgeSaveApply").show();
			}else{
				$("#urgeAudite").hide();
				$("#urgeSaveApply").hide();
			}
		}else{
			art.dialog.alert('该单子正在进行处理，不能进行提交查账！');
		}
		
	});
	
	/**
	 * 提交按钮校验
	 */
	$("#urgeSaveApply").click(function(){
		var storesInAccount = $('#urgeStoresInAccount').val();
		var urgeApplyAmount = $("#urgeApplyAmountCopy").val();
		var urgeApplyAmountSum = $("#urgeApplyAmount").val();
		var waitUrgeMoney = $("#waitUrgeMoney").val();
		var flag = $("#flag").val();
		var regex1=/^\d{1,10}\.\d{1,2}$/;
		var regex2=/^\d{1,10}$/;
		if(storesInAccount=="请选择" || storesInAccount == '' || storesInAccount== null){
			top.$.jBox.tip('请选择存入银行!');
        	return false;
        }
		var dictDepositTrue = true;
		$('#urgeAppendTab').find("[name='paybackTransferInfo.dictDeposit']").each(function(d,e){
			if (e.value.length == 0) {
				top.$.jBox.tip('第 '+(d+1)+' 行存款方式必填!')
				dictDepositTrue=false;
				return dictDepositTrue;
				}
	    });	
		if(dictDepositTrue == false){
			return false;
		}
		var tranDepositTimeTrue = true;
		$('#urgeAppendTab').find("[name='paybackTransferInfo.tranDepositTimeStr']").each(function(d,e){
			if (e.value.length == 0) {
				top.$.jBox.tip('第 '+(d+1)+' 行存款时间必填!')
				tranDepositTimeTrue=false;
				return tranDepositTimeTrue;
				}
	    });	
		if(tranDepositTimeTrue == false){
			return false;
		}
		var reallyAmountTrue = true;
		$('#urgeAppendTab').find("[name='paybackTransferInfo.reallyAmountStr']").each(function(d,e){
			if (e.value.length == 0) {
				top.$.jBox.tip('第 '+(d+1)+' 行实际到账金额必填!')
				reallyAmountTrue=false;
				return reallyAmountTrue;
				}else{
					var isOK = regex1.test(e.value)||regex2.test(e.value);
					if (!isOK) {
						top.$.jBox.tip('实际到账金额只能为数字且保留2位小数!');
						 reallyAmountTrue=false;
						 return reallyAmountTrue;
					}
				}
	    });	
		if(reallyAmountTrue == false){
			return false;
		}
		var depositNameTrue = true;
		$('#urgeAppendTab').find("[name='paybackTransferInfo.depositName']").each(function(d,e){
			if (e.value.length == 0) {
				top.$.jBox.tip('第 '+(d+1)+' 行实际存入人必填!')
				depositNameTrue=false;
				return depositNameTrue;
				}
	    });	
		if(depositNameTrue == false){
			return false;
		}
		var urgetrue = true,files;
		$("#urgeAppendTab").find("[name='files']").each(function(i,e){
			if (e.value.length > 0) {
				files = e.value.substring(e.value.lastIndexOf("."));
				if (files != ".jpg" && files != ".png" && files != ".jpeg") {
					top.$.jBox.tip('第 '+(i+1)+' 行请上传图片格式的的凭条!');
					urgetrue=false;
					return urgetrue;
				}
			} else {
				top.$.jBox.tip('第 '+(i+1)+' 行请上传凭条!');
				urgetrue=false;
				return urgetrue;
			}
	    });	
		if(urgetrue==false){
			return false;
		}
    	$("#applyPayLaunchForm").submit();
	});
	/**
	 * @function 实际到账金额累加 by zhangfeng
	 */
	$('#urgeAppendTab').on('blur','tr', function(event) {
		var amountCount = 0;
		$('input[id="urgeApplyAmountCopy"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			if(amountCount.toFixed(2) < 0){
				top.$.jBox.tip('实际到账金额不能为负数!','warning');
			}else{
				$("#urgeApplyAmount").val(amountCount.toFixed(2));
			}
		});
	});
	
	/**
	 * 图片预览
	 */
	$("[name='doPreviewPngBtn']").each(function(){
		$(this).bind('click',function(){
			var url=ctx + "//borrow/payback/dealPayback/previewPng?docId="+$(this).attr('docId');
			art.dialog.open(url, {  
		         id: 'previewPngBtn',
		         title: '图片预览',
		         lock:true,
		         width:800,
		     	 height:500
		     },  
		     false);
		});
	});
	
	/**
	 * 
	 */
	$('#urgeStoresInAccount').change(function(){
		$('#storesInAccountName').val($("#urgeStoresInAccount").find("option:selected").text());
	})
	
});
/**
 * @function 新增一条银行账款数据  by zhangfeng
 */
function appendRow(){ 
	var tr = $('#urgeAppendId').clone();
	tr.find('input[name="files"]').val('');
	tr.find('input[name="doPreviewPngBtn"]').hide();
	$('#urgeAppendTab tr:last').after(tr);
	$('input[name="paybackTransferInfo.tranDepositTimeStr"]').each(function(idx) {
		$(this).daterangepicker({ 
			 singleDatePicker: true,
			 startDate: moment().subtract('days')
		});
	});
}

/**
 * @function 删除一条银行账款数据 by zhangfeng
 */
function deleteRow(){
	if($("#urgeAppendTab tr").length >= 4){
		$("#urgeAppendTab tr:last").remove();
	}
	else 
		top.$.jBox.tip('最后一条数据不允许删除','warning');
}

//跳转办理页面
function urgeGuarantDeal(sid){
	windowLocationHref(ctx+'/car/grant/deductCost/deductCostStayRecoverForm?sid='+sid);
}
//格式化，保留两个小数点
function fmoney(s, n) {  
    n = n > 0 && n <= 20 ? n : 2;  
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
    t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
} 
