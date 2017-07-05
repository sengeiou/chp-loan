var checkVal = "";
//保存系统设定
function saveSysSetting(sysFlag,sysName,sysValue,sysId){
	$.ajax({
		url:ctx+"/common/systemSetMater/save",
		type:"post",
		dataType:'json',
		data:{
		  'sysFlag':sysFlag,
		  'sysName':sysName,
		  'sysValue':sysValue,
		  'isNewRecord':false,
		  'id':sysId 
		},
		cache: false,
		async: false,
		success:function(data){
			
		},
		error:function(){
			
		}
	});
}
$(document).ready(function() {
	var canExport=$("#canExport").val();
	if(canExport!=null && canExport=='0'){
		art.dialog.alert("逾期客户，禁止退款操作！");
	}
	// 点击清除，清除搜索页面中的数据
	$("#clearBtn").click(function() {
		$('#storeId').val("");
		$(':input', '#refundForm')
		.not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
	});

	// 查看单子操作历史
	$(":input[name='history']").each(function() {
		$(this).click(function() {
			var urgeId = $(this).attr("sid");
			var url = ctx + '/common/history/showFeekHis?payBackApplyId=' + urgeId;
			art.dialog.open(url, {
				title: "操作历史",
				lock: true,
				width: 700,
				height: 350
			},
			false);
		});
	});
	
	// 点击全选
	$("#checkAll").click(function() {
		var totalMoney = 0;
		var totalNum = 0;
		if ($('#checkAll').attr('checked') == 'checked' || $('#checkAll').attr('checked')) {
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked", 'true');
				totalNum = totalNum + 1;
				totalMoney = totalMoney + parseFloat($(this).val());
			});
		} else {
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
		$("#total_money").text(totalMoney.toFixed(2));
		$("#total_num").text(totalNum);
	});
	
	$("[name='checkBoxItem']").click(function() {
		var totalMoney = 0.0;
		var totalNum = 0;
		$("[name='checkBoxItem']").each(function() {
			if (this.checked) {
				totalMoney = totalMoney + parseFloat($(this).val());
				totalNum = totalNum + 1;
			}
			$("#total_money").text(totalMoney.toFixed(2));
			$("#total_num").text(totalNum);
			
		});
		var checkBox = $("input:checkbox[name='checkBoxItem']").length;
		var checkBoxchecked = $("input:checkbox[name='checkBoxItem']:checked").length
		/*if(checkBoxchecked==0){
			$("#checkAll").attr("checked", false);
		}*/
		if(checkBox==checkBoxchecked){
			$("#checkAll").attr("checked", true);
		}else{
			$("#checkAll").attr("checked", false);
		}
	});
	
	//批量退回
	$("#batchReturn").click(function() {
		if ($(":input[name='checkBoxItem']:checked").length == 0) {
			art.dialog.alert("请选择数据！");
			return;
		}
		var ids = "";
		art.dialog({
		    content: document.getElementById("batchReturnDiv"),
		    title:'批量退回',
		    fixed: true,
		    lock:true,
		    width:300,
		    height:200,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
				if($("#remarkA").val() == ''){
					$("#showMsgA").html("请输入退回原因");
					return false;
				}
				if ($(":input[name='checkBoxItem']:checked").length > 0) {
					$(":input[name='checkBoxItem']:checked").each(function() {
						if (ids != "") {
							ids += "," + $(this).attr("cid");
						} else {
							ids = $(this).attr("cid");
						}
					});
				}
				if(ids.length<1){
    				art.dialog.alert("请选择数据！");
    				return;
    			}
				$("#ids").val(ids);
		    	var srcFormParam = $('#batchReturnForm');
				srcFormParam.submit();
				return false;
		    },
		    cancel: true
		});
	});

	//退款成功
	$("#returnSuccess").click(function() {
		var url = "selectMiddlePerson";
		$.ajax({
			type : "POST",
			url : url,
			async: false,
			success : function(data){
      			var cityStr = "<option value=''>请选择</option>";
      			if (data != null){
	     			for(var i=0; i<data.length; i++){
     					cityStr += "<option value='" + data[i] + "'>" + data[i] + "</option>";
	     			}
      			}
      			$("#refundBank").append(cityStr);
      			$("#refundBank").trigger("change");
    			var ids = "";
    			if ($(":input[name='checkBoxItem']:checked").length > 0) {
    				$(":input[name='checkBoxItem']:checked").each(function() {
    					if (ids != "") {
    						ids += "," + $(this).attr("cid");
    					} else {
    						ids = $(this).attr("cid");
    					}
    				});
    			}
    			if(ids.length<1){
    				art.dialog.alert("请选择数据！");
    				return;
    			}
    			$("#idsA").val(ids);
      			art.dialog({
      			    content: document.getElementById("returnSuccessDiv"),
      			    title:'退款成功',
      			    fixed: true,
      			    lock:true,
      			    width:300,
      			    height:200,
      			    id: 'confirm',
      			    okVal: '确认',
      			    ok: function () {
      			    	if($("#refundBank").val()==''){
      			    		art.dialog.alert("请选择退款银行！");
      			    		return false;
      			    	}
      			    	if($("#backDate").val()==''){
      			    		art.dialog.alert("请输入退款日期！");
      			    		return false;
      			    	}
      			    	
      			    	var srcFormParam = $('#returnSuccessForm');
      					srcFormParam.submit();
      					return false;
      			    },
      			    cancel: true
      			});
			}
		});
	});
	
	// 导出Excel
	$("#daoExcel").click(function() {
		var ids = "";
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (ids != "") {
					ids += "," + $(this).attr("cid");
				} else {
					ids = $(this).attr("cid");
				}
			});
		}
		document.getElementById("refundForm").action = ctx + "/borrow/refund/longRefund/refundExl?ids=" + ids;
		$('#refundForm').submit();
		document.getElementById("refundForm").action = ctx + "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList";
		//window.location.href = ctx + "/borrow/refund/longRefund/refundExl?ids=" + ids;
	});
	
	//已退款导出
	$("#alreadyRefundDataExcel").click(function(){
		var ids = "";
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (ids != "") {
					ids += "," + $(this).attr("cid");
				} else {
					ids = $(this).attr("cid");
				}
			});
		}
		document.getElementById("refundForm").action = ctx + "/borrow/refund/longRefund/alreadyRefundDataExcel?ids=" + ids;
		$('#refundForm').submit();
		document.getElementById("refundForm").action = ctx + "/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList";
	
	});

	//上传回执结果
	$("#importExcel").click(function() {
		$("#uploadAuditForm").attr("action", ctx + "/borrow/refund/longRefund/importExcel");
	});
});

 function refundTicket(id,appType,contractCode,refundMoney){
	 $("#id").val(id);
	 $("#appTypeA").val(appType);
	 $("#refundMoney").val(refundMoney);
	 $("#contractCodeA").val(contractCode);
	 art.dialog({
		    content: document.getElementById("refundTicketDiv"),
		    title:'退票原因',
		    fixed: true,
		    lock:true,
		    width:300,
		    height:200,
		    id: 'confirm',
		    okVal: '确认',
		    ok: function () {
				if($("#remark").val() == ''){
					$("#showMsg").html("请输入退票原因");
					return false;
				}
		    	var srcFormParam = $('#refundTicketForm');
				srcFormParam.submit();
				return false;
		    },
		    cancel: true
		});
 }
 
 function openView(id,contractCode,appType,mode){
	 if(mode == 'edit'){
		 if(appType == '0'){
			 var url = ctx + '/borrow/refund/longRefund/openRefundBlue?paybackId='+contractCode+'&refundId='+id;
				art.dialog.open(url, {
					id:"openRefundBlue",
					title : '蓝补退款',
					width : 800,
					lock:true,
					height : 500,
					close:function()
					{
					}
				});
		 }else if(appType == '1'){
			var url = ctx + '/borrow/refund/longRefund/openRefundServiceFee?paybackId='+contractCode+'&refundId='+id;
			art.dialog.open(url, {
				id:"openRefundServiceFee",
				title : '催收服务费退款',
				width : 800,
				lock:true,
				height : 500,
				close:function()
				{
				}
			});
		 }
	 }else{ 
		 var url = ctx + '/borrow/refund/longRefund/view?id='+id+'&contractCode='+contractCode+'&appType='+appType;
		 art.dialog.open(url, {
			 id:"view",
			 title : '退款查看',
			 width : 800,
			 lock:true,
			 height : 560,
			 close:function()
			 {
		
			 }
		 });
	 }
 }