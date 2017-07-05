
function batchRejectApply(formId,attrName){
  var hasNoFrozenFlag = false;
  var param = "";
  if($("input[name='checkBoxItem']:checked").length==0){
	  art.dialog.alert("请选择需要驳回的数据!");
	  return false;
  }
  $("input[name='checkBoxItem']:checked").each(function(){
     frozenFlag = $(this).attr("frozenFlag");
	 if(frozenFlag!="1"){
	 	hasNoFrozenFlag = true;
	 }
	 if(param!=""){
	    param+=";"+$(this).attr("flowParam");
	 }else{
		param=$(this).attr("flowParam");	
	 }
  });
if(hasNoFrozenFlag){
	art.dialog.alert("选中的数据中存在非冻结数据，请重新选择!");
	return false;
 }else{
	 var cancelFlagRetVal = null;
	 art.dialog({
			content: document.getElementById("rejectFrozen"),
			title:'驳回申请',
			fixed: true,
			lock:true,
			okVal: '确认',
			ok: function () {
				var remark = $("#rejectReason").val();
				if (remark == null || remark=='') {
					art.dialog.alert("请输入驳回原因!");
				}else{					
					art.dialog.confirm('是否确定执行驳回冻结申请操作', function (){
						$.ajax({
							type : 'post',
							url : ctx + '/borrow/grant/disCard/backFrozen',
							data : {
							'checkVal':param,
							'remark':remark
							},
							cache : false,
							async : false,
							success : function(result) {
					        cancelFlagRetVal = result;
							},
							error : function() {
							art.dialog.alert('请求异常！', '提示信息');
							}
						});
						if(cancelFlagRetVal=='修改成功'){
							$('#'+formId).submit();  
						}else {
						   art.dialog.alert(result);
						}
					});
				}
			return false;
			},
			cancel: true
		});
  }
}
// 金额精确到小数点后2位
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
/**
 * 分配卡号JS处理
 */
$(document).ready(function(){
	
	$("#reason").change(function() { 
		SelectChange();
		}); 
	
	$('#rejectApplyBtn').click(function(){
		batchRejectApply('disCardForm','frozenFlag');
	});
	var checkVal = "";
	$("#disCardBtn").click(function(){
		var disCard = $("#disCardForm").serialize();
		$(":input[name='checkBoxItem']:checked").each(function(){	                		
    		if(checkVal!= "")
    		{
    			checkVal+=","+$(this).val();
    		}else{
    			checkVal=$(this).val();
    		}
    	});
		window.location.href=ctx +"/borrow/grant/disCard/disCardJump?checkVal="+checkVal+"&"+disCard;
	});
	
	// 导出excel
	$("#daoBtn").click(function(){
		var idVal = "";
		var loanGrant = $("#disCardForm").serialize();
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
		window.location.href=ctx+"/borrow/grant/disCard/disCardExl?idVal="+idVal+"&"+loanGrant;
	});
	
	// 放款金额区间值的验证,失去焦点时
	$("#lendingMoneyStart").live("blur",function(){//金额输入框失去焦点时，进行格式的验证
		var maxMoney = $("#lendingMoneyStart").val();
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
		if((maxMoney!=" "||maxMoney!=null)&& maxMoney < 0){
			art.dialog.alert("放款金额下限要大于0");
			$(this).val("");
			return false;
		}
	});
	
	$("#lendingMoneyEnd").live("blur",function(){//金额输入框失去焦点时，进行格式的验证
		var maxMoney = $("#lendingMoneyEnd").val();
		var maxMoneyStart = $("#lendingMoneyStart").val();
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
			art.dialog.alert("放款金额上限要大于0");
			$(this).val("");
			return false;
		}
		if(maxMoney!="" && maxMoneyStart!="" && parseFloat(maxMoney) < parseFloat(maxMoneyStart)){
			art.dialog.alert("起始金额不大于终止金额！");
	    	return false;
		}
	});
	
	// 放款金额上限处理
	$("#lendingMoneySure").click(function(){
		var maxMoney = $("#maxMoney").val();
		var sumLendingMoney =0.00;
		// 放款金额验证
		var index = maxMoney.indexOf(".");
	    if(index>0 && maxMoney.substring(index).length>3) {
	    	art.dialog.alert("请输入有效的金额，小数位最多为两位");
	    	$(this).val("");
	        return false;
	    }
	    //(isNaN(maxMoney.replace(/[,]/g,""))) ||
	      if(isNaN(maxMoney.replace(/[,]/g,""))) {//不是数字isNaN返回true
	    	art.dialog.alert("请输入有效的金额格式！");
	    	$(this).val("");
	        return false;
	    }
		if(maxMoney <= 0){
			art.dialog.alert("放款金额上限要大于0");
			$(this).val("");
			return false;
		}else{
			$(":input[name='checkBoxItem']").each(function(){
				$(this).removeAttr("checked");
			});
			// 获得每一笔单子的合同金额，求和，对符合条件的单子进行选择
			calculate.topLimitAmount = maxMoney;
			var checkBoxItems = $(":input[name='checkBoxItem']");
			calculate.srcListLength = checkBoxItems.length;
			calculate.srcListIndex = 0;
			calculate.curMaxAmount = 0;
			calculate.maxAmountLocation = 0;
			calculate.canBreak = false;
			calculate.amountObjList = new Array();
			for(var i=0;i<calculate.srcListLength;i++){
				calculate.amountTopLimit(checkBoxItems,calculate,"grantAmount");
				if(calculate.canBreak){
					break;
				}
				if(calculate.srcListLength>calculate.srcListIndex+1){
					calculate.srcListIndex = calculate.srcListIndex+1;
				}
			}
			$("#totalGrantMoney").text(fmoney(calculate.curMaxAmount,2));
			$("#hiddenGrant").val(calculate.curMaxAmount);
			var amountObjList = calculate.amountObjList;
			var maxAmountLocation = calculate.maxAmountLocation;
			var amountBean = amountObjList[maxAmountLocation];
			var itemList = amountBean.itemList;
			var i = 0;
			$(":input[name='checkBoxItem']").each(function(){
                  for(var m = 0;m<itemList.length;m++){
                	  if(i==itemList[m]){
        			    $(this).attr("checked",'true');
                	  }
                  }
           		i++;
        	});
			$("#totalGrantCount").text(itemList.length);
			calculate.amountObjList = new Array();
		}
	});
	
	//点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
	 $(':input','#disCardForm')
	  .not(':button,:submit, :reset')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	});
	
	//伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src= ctxStatic + '/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			if(document.getElementById("T3")){
			  document.getElementById("T3").style.display='';
			}
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			if(document.getElementById("T3")){
			   document.getElementById("T3").style.display='none';
			}
		}
	});
	
 
	
	//点击全选反选选择框
	$("#checkAll").click(function(){
		var totalGrantMoney=0.00;
		var totalGrantCount = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
				totalGrantCount ++;
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
		$("#hiddenGrant").val(totalGrantMoney);
		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
		$("#totalGrantCount").text(totalGrantCount);
	});
	
	// 计算金额,使用没有格式化的金额进行计算
	$(":input[name='checkBoxItem']").click(function(){
		var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
		var sumGrantMoney = parseFloat($("#hiddenGrant").val());
		var totalNum=1;
		if($(this).attr('checked')=='checked'|| $(this).attr('checked')){
			var grant = sumGrantMoney + totalGrantMoney;
			$("#hiddenGrant").val(grant);
			$("#totalGrantMoney").text(fmoney(grant,2));
			$('#totalGrantCount').text(parseFloat($("#totalGrantCount").text())+totalNum);
	 }else{
		 $("#checkAll").removeAttr("checked");
		 if($(":input[name='checkBoxItem']:checked").length==0){
			$("#hiddenGrant").val(0.00);
			$("#totalGrantMoney").text(fmoney(0.00,2)); 
			$("#totalGrantCount").text(0);
		 }else{
			 var grant = sumGrantMoney - totalGrantMoney;
			 $("#hiddenGrant").val(grant);
			 $("#totalGrantMoney").text(fmoney(grant,2));
			 $('#totalGrantCount').text(parseFloat($("#totalGrantCount").text())-totalNum);
		 }
	 }
	  $("#checkAll")
		.attr(
				"checked",
				$subBox.length == $(":input[name='checkBoxItem']:checked").length ? true
						: false);
	});
	
	// 点击退回,退回到放款明细确认,单笔退回
	$(":input[name='backBtn']").each(function(){
		$(this).click(function(){
			var checkVal=$(this).val();
			$(this).attr("data-target","#backDiv");
			$("#checkVal").val(checkVal);
	});
	});
	
	// 批量退回，退回放款明细确认
	$("#disCardBackBtn").click(function(){
		var checkVal = "";
		if($(":input[name='checkBoxItem']:checked").length==0){
			art.dialog.alert("请选择要进行退回的数据!");
	           return;					
			}else{
	            var r=confirm("确定将选中单子退回么?");
	            if(r){
	            	$(":input[name='checkBoxItem']:checked").each(function(){	                		
	            		if(checkVal!=null && checkVal!='')
	            		{
	            			checkVal+=";"+$(this).val();
	            		}else{
	            			checkVal=$(this).val();
	            		}
	            	});
	            	$(this).attr("data-target","#backDiv");
	            	$("#checkVal").val(checkVal);
		      }
	           else{
	            	return;}
	            }
	})
	
	// 退回确认,处理
	$("#sureBtn").click(function(){
		var backReason = $("#reason").find("option:selected").text();
		var backReasonCode = $("#reason option:selected").val(); 
		if(backReason=="其他"){
			backReason = $("#remark").val();
			backReasonCode = '9'
				if(backReason==null||backReason==""){
					art.dialog.alert("退回原因不能为空！");
					return;
				}
		}
		var checkVal = $("#checkVal").val();
		backDeal(checkVal,backReason,backReasonCode);
	});
	
	//点击办理
	$(":input[name='dealBtn']").each(function(){
		$(this).click(function(){
			var checkVal=$(this).val();
			window.location.href=ctx +"/borrow/grant/disCard/disCardJump?checkVal="+checkVal;
		
	});
	});
	
	// 选择其他的时候，填写退回原因
    function SelectChange() {
        var selectText = $("#reason").find("option:selected").text();
        if (selectText != "其他") {
           $("#other").hide();
        } else {
		   $("#other").show(); 
        }
    }
	
	// 退回的详细处理
	function backDeal(checkVal,backReason,backReasonCode){
		$.ajax({
			type : 'post',
			url : ctx + '/borrow/grant/disCard/backDeal',
			data : {
				'checkVal':checkVal,
				'backReason':backReason,
				'backReasonCode':backReasonCode
				},
			success : function(data) {
				var obj = eval("("+data+")");
				alert(obj.message);
				window.location=ctx+'/borrow/grant/disCard/getDisCardList';
			}
		});
	}
});
