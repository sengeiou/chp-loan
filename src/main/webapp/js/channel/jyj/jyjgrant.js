var id;
$(document).ready(function(){
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){		
	 $(':input','#grantForm')
	  .not(':button,:submit, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src=ctxStatic+'/images/down.png';
			document.getElementById("T1").style.display='';
		}else{
			document.getElementById("showMore").src=ctxStatic+'/images/up.png';
			document.getElementById("T1").style.display='none';
		}
	});
	
	$(":input[name='checkBoxItem']").click(function() {
		// 记录总金额，当length为0时，进行总金额的处理
		var totalGrantMoney = $("#hiddenTotalGrant").val();
		var totalNum = $("#hiddenNum").val();
		// 获得单个单子的金额
		var deductAmount = parseFloat($(this).attr("lendingMoney"));
		var num = 1;
		if ($(this).attr('checked') == 'checked' || $(this).attr('checked')) {
			var hiddenNum = parseFloat($("#numHid").val()) + num;
			var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) + deductAmount;
			$('#totalItem').text(hiddenNum);
			$("#numHid").val(hiddenNum);
			$("#grantMoneyHid").val(hiddenDeduct);
			$('#grantMoneyText').text(fmoney(hiddenDeduct, 2));
		} else {
			$('#checkAll').removeAttr("checked");
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				$('#totalItem').text(totalNum);
				$("#grantMoneyText").text(fmoney(totalGrantMoney, 2));
				$('#grantMoneyHid').val(0.00);
				$('#numHid').val(0);
			} else {
				var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) - deductAmount;
				$('#totalItem').text(parseFloat($("#numHid").val()) - num);
				$("#numHid").val(parseFloat($("#numHid").val()) - num);
				$("#grantMoneyText").text(fmoney(hiddenDeduct, 2));
				$("#grantMoneyHid").val(hiddenDeduct);
			}
		}
	});
	
	// 手动确认放款
	$("#handSureGrant").click(function(){
		var checkVal = null;
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
				if(checkVal!=null)
	        	{
	        		checkVal+=";"+$(this).attr("contractCode");
	        	}else{
	        		checkVal=$(this).attr("contractCode");
	        	}
			});
		}
		$("#contractCodeHidden").val(checkVal);
	});
	// 手动确认放款点击确定
	$("#sureGrant").click(function(){
		var grantPch = $("#handSureCon").val();
		var contractCode = $("#contractCodeHidden").val();
		var param = $("#grantForm").serialize();
		if(grantPch == null || grantPch == ""){
			art.dialog.alert("放款批次不能为空");
			return false;
		}
		var dialog = art.dialog({
			 content: '正在提交...',
		      cancel:false,
		      lock:true
		});
		$.ajax({
			type : "post",
			url : ctx + '/channel/jyj/updateGrant',
			data : {'idVal':contractCode,'grantPch':grantPch,param},
			dataType : 'text',
			success : function (data){
				dialog.close();
				art.dialog.alert(data);
				window.location=ctx+'/channel/jyj/grantItem';
			},
			error : function(){
				dialog.close();
				art.dialog.alert("手动确认放款发生异常");
			}
			
		});
	});
	
	// 批量退回的弹框
	$("#batchBackBtn").click(function(){
		var checkVal = null;
		if($(":input[name='checkBoxItem']:checked").length==0){
			   art.dialog.alert('请选择要操作的数据!');
	           return false;
			}else{
				// 对选中的单子进行线上放款，根据合同编号
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
            		if(checkVal!=null)
            		{
            			checkVal+=";"+$(this).attr("contractCode");
            		}else{
            			checkVal=$(this).attr("contractCode");
            		}
            		if($(this).attr("issplit")!='0'){
	           			 art.dialog.alert('选择的数据中含有拆分数据，不能批量退回，合同号：'+$(this).attr("contractCode"));
	   					 return false;
            		}
            	});
				$(this).attr("data-target","#backBatch_div");
				$("#contractCodeHidden").val(checkVal);
			}
	});
	
	// 每次点击退回，将合同编号放到隐藏域中
	$(":input[name='back']").each(function(){
		$(this).bind('click',function(){
			$('#contractCodeHidden').val($(this).attr('contractCode'));
		});
	});
	
	// 导出excel
	$("#offLineDao").click(function(){
		var loanGrant = $("#grantForm").serialize();
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
		}
		$(this).attr("data-target","#isBreakUp");
		$("#loanGrant").val(loanGrant);
		$("#idVal").val(idVal);
	});
	
	// 导出宝付模板
	$("#offLineBF").click(function(){
		var loanGrant = $("#grantForm").serialize();
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
		}
		window.location.href=ctx+"/channel/jyj/offLineBFDeal?idVal="+idVal+"&"+loanGrant;
	});
	
	//导出Excel点击提交按钮isBreakUpBtn
	$("#isBreakUpBtn").click(function(){
		$(this).removeAttr('data-dismiss');
		var loanGrant = $("#loanGrant").val();
		var idVal = $("#idVal").val();
		var isBreakObj = $("input[name='isBreak']:checked");
		if(isBreakObj.length==0){
			 art.dialog.alert('请选择是否拆分');
			 return;
		}else{
			var isBreakCode = isBreakObj[0].value;
			closeModal('isBreakUp');
			window.location.href=ctx+"/channel/jyj/grantExl?idVal="+idVal+"&"+loanGrant+"&isBreakCode="+isBreakCode;
		}
		
	});
	
   
	// 上传回执结果,在上传之前要进行验证，所以上传回执结果要使用Ajax进行控制
	$("#sureBtn").click(function(){
		ajaxFileUpload();
	});
	
	
	$('#uploadAuditForm2').validate({
		onkeyup: false,
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
		$("#messageBox").text("输入有误，请先更正。");
		if (element.is(":checkbox") || element.is(":radio")
				|| element.parent().is(".input-append")) {
			error.appendTo(element.parent().parent());
		} else {
			error.insertAfter(element);
		 }
		}
	});
	
});
	// 全选
	function checkAllClick(){
		var curMoney = 0.0;
		var curFailMoney = 0.0;
		var curNum = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
			  $(this).attr("checked",'true');
			  curMoney = parseFloat($(this).attr("lendingMoney"))+curMoney;
			  curNum=curNum+1;
			});
			$('#totalItem').text(curNum);
			$('#numHid').val(curNum);
			$('#grantMoneyText').text(fmoney(curMoney, 2));
			$('#grantMoneyHid').val(curMoney);
		}else{
			$(":input[name='checkBoxItem']").each(function() {
			  $(this).removeAttr("checked");
			});
			$('#grantMoneyHid').val(0.00);
			$('#numHid').val(0);
			$('#totalItem').text($("#hiddenNum").val());
			$('#grantMoneyText').text(fmoney($("#hiddenTotalGrant").val(), 2));
		}
		
	}	


	// 批量或者单笔退回处理
	function backSure(){
		var listFlag = $("#listFlag").val();
		var backBatchReason = $('#backBatchReason option:selected').text();
		var backBatchReasonCode = $('#backBatchReason option:selected').val();
		var checkVal = $("#contractCodeHidden").val();
		if (backBatchReason == '') {
		    art.dialog.alert("请选择退回原因！");
		    return false;
		}
		if (backBatchReason == '其它' || backBatchReason == '其他') {
		    var remark = $('#backBatchRemark').val();
		    if (remark == '' || remark.trim() == '') {
		        art.dialog.alert("请输入备注信息！");
		        return false;
		    }
		    backBatchReason = remark;
		}
		var dialog = art.dialog({
		    content: '正在退回...',
		    cancel: false,
		    lock: true
		});
		$.ajax({
		    type: 'post',
		    url: ctx + '/channel/jyj/batchBack',
		    data: {
		        'batchParam': checkVal,
		        'backBatchReason': backBatchReason,
		        'backBatchReasonCode': backBatchReasonCode
		    },
		    success: function(data) {
		        dialog.close();
		        art.dialog.alert(data);
		        window.location = ctx + '/channel/jyj/grantItem?listFlag=' + listFlag;
		    },
		    error: function() {
		        dialog.close();
		        art.dialog.alert('请求异常！');
		    }
		});
	}
    
	
	// 关闭窗口
	function closeModal(id){
		$("#"+id).modal('hide');
	}
	
	// 上传文件处理
	function ajaxFileUpload() {
		  var file = $("#fileid");
	      if($.trim(file.val())==''){
	             alert("请选择文件");
	             return false;
	     }
	    $.ajaxFileUpload({
	   	    url : ctx+'/channel/jyj/importResult?returnCheckFlag=0',
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "fileid", // 上传文件的id、name属性名
	        dataType:'text',
	        success: function(data){
	        	data = data.replace("<pre>","");
	        	data = data.replace("</pre>","");
	        	art.dialog.alert(data);
	        	window.location.href = ctx+'/channel/jyj/grantItem';
	        },
	        error: function(){ 
	               alert("请求异常");
	        }
	    });
	}
	
	function returnUpload(){
	    $.ajaxFileUpload({
	   	    url : ctx+'/channel/jyj/importResult?returnCheckFlag=0',
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "fileid", // 上传文件的id、name属性名
	        dataType: 'text', 
	        success: function(data){
	        	// 存在有待退款的单子
	        	data = data.replace("<pre>","");
	        	data = data.replace("</pre>","");
	        	if("grantPch"==data){
	        		art.dialog.alert("放款批次不能为空！");
	        	}else{
	        		art.dialog.alert(data);
	        		window.location.href = ctx+'/channel/jyj/grantItem';
	        	}
	        },
	        error: function(){ 
	               alert("请求异常");
	        }
	    });		
	}

function showRemark(srcId,targetId){
	var backReason = $('#'+srcId +" option:selected").text();
   if(backReason=='其它' || backReason=='其他'){
	   $('#'+targetId).css('display','block');
   }else{
	   $('#'+targetId).css('display','none');
   }
}
// 格式化，保留两个小数点
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