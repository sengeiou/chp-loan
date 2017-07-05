
$(document).ready(function(){

	// 初始化
	init();
	
	/**
	 * @function 是否结清点击事件
	 */
	$('input:radio[name="isContfirm"]').click(function(){
		  var val=$('input:radio[name="isContfirm"]:checked').val();
            if(val=='0'){
				$('#isTopCashBack').show();
				$('#isSettleMoney').hide();
				$("#isTopCashBackNot").attr('checked','checked');
	          }
            else{
	            $('#isTopCashBack').hide();
				$('#isSettleMoney').hide();
            }
	})
	
	/**
	 * @function 是否还款点击事件
	 */
	$('input:radio[name="isTopCashBack"]').click(function(){
		  var val=$('input:radio[name="isTopCashBack"]:checked').val();
            if(val=='0'){
	                $('#isSettleMoney').show();
	          }
            else{
                $('#isSettleMoney').hide();
            }
	})
	
	/**
	 * 判断是否可以进行提前结清确认
	 */
	$('#submitConfirmBtn').click(function() {
		// 判断是否选中提前结清按钮
		var val=$('input:radio[name="isContfirm"]:checked').val();
		// 判断是否选中返款单选按钮
		var valBack= $('input:radio[name="isTopCashBack"]:checked').val();
		//  获取催收服务费的值
		var urgeMoney=$("#paybackBackAmount").val();
		// 判断页面返款金额输入框输入值
		var backMoney=$("#paybackBak").val();
		var regex1 = /^\d+(\.\d+)?$/;
		var regex2 = /^[0-9]+([.]\d{1,2})?$/;
		
        var contractCode = $("#contractCode").val();
		var returnReason=$("#returnReason").val();
		var chargeId=$("#chargeId").val();
		var pId=$("#id").val();
		var monthBeforeReductionAmount=$("#monthBeforeReductionAmount").val();
		var paybackBuleAmount=$("#paybackBuleAmount").val();
		var monthBeforeFinishAmount=$("#monthBeforeFinishAmount").val() ;
		// 确认提前结清
		if(val == null || val == ''){
			artDialog.alert("请选择是否进行提前结清!");
			return false;
		}
		if(val == '0'){
		    if(returnReason == null || returnReason == ""){
		    	artDialog.alert('请输入审核意见!');
				return false;
			}
		    if(valBack == null || valBack == ''){
				artDialog.alert("请选择是否返款!");
				return false;
			}else {
				 if('0' == valBack){
					if(backMoney == '' || backMoney < 0){
					  artDialog.alert("返款金额不能为空或小于0！");
					  return false;
				    }
					else if (!regex1.test(backMoney)) {
						 art.dialog.tips('返款金额只能为数字!');
							return false;
					}
					else if (!regex2.test(backMoney)) {
						 art.dialog.tips('返款金额只能保留2位小数!');
							return false;
					}
					else {
				    	if(!(urgeMoney == '' || urgeMoney < 0)){
					    	 if(parseFloat(urgeMoney-backMoney).toFixed(2) < 0){
						       artDialog.alert("返款金额不能大于催收服务费金额！");
						        return false;
					    	 }
				    	}else{
				    		artDialog.alert("后台数据获取异常，请稍后重试！");
				    		return false;
				    	}
				    }
			    }
			}
		    
		// 减免提前结清金额
	    if(monthBeforeReductionAmount == '' || monthBeforeReductionAmount == null){
	    	 artDialog.alert('减免提前结清金额不能为空!');
	    	 return false;
	    }else{
	    	monthBeforeReductionAmount=parseFloat($("#monthBeforeReductionAmount").val());
	    }
		// 蓝补金额
	    if(paybackBuleAmount == '' || paybackBuleAmount == null){
	    	 artDialog.alert('蓝补金额不能为空!');
	    	 return false;
	    }else{
	    	paybackBuleAmount=parseFloat($("#paybackBuleAmount").val());
	    }
		// 一次性提前结清应还总额
	    if(monthBeforeFinishAmount == '' || monthBeforeFinishAmount == null){
	    	artDialog.alert(' 一次性提前结清应还总额不能为空!');
	    	return false;
	    }else{
	    	monthBeforeFinishAmount=parseFloat($("#monthBeforeFinishAmount").val());
	    }
		if(!((monthBeforeReductionAmount == ''  || monthBeforeReductionAmount == null) && (paybackBuleAmount == '' || paybackBuleAmount == null))){
		    	var monthBeforeBule=monthBeforeReductionAmount+paybackBuleAmount;
			    if(parseFloat(monthBeforeBule).toFixed(2)>=monthBeforeFinishAmount){
					$.ajax({     
						   type : "POST",
						   data:$('#confirmInfoForm').serialize()+"&valBack="+valBack+"&val="+val,
							url : ctx+"/borrow/payback/earlyFinishConfirm/save?",
							datatype : "json",
							async:false,  
							success : function(data){
								if(data == '0'){
									alert('提前结清确认不通过处理完成！');
									art.dialog.close();
								}
								if(data == '1'){
									alert('冲抵成功，借款提前结清确认完成！');
									art.dialog.close();
								}
								if(data == '2'){
									alert('借款状态已发生变化，不允许进行提前结清！');
									art.dialog.close();
								}
								if(data == '3'){
									alert('还款状态已发生变化，不允许进行提前结清！');
									art.dialog.close();
								}
								if(data == '4'){
									alert('金额不足，不允许进行提前结清！');
									art.dialog.close();
								}
								if(data == '5'){
									alert('同步财富通讯接口发生异常！');
									art.dialog.close();
								}
								if(data == '6'){
									alert('同步债权数据异常！');
									art.dialog.close();
								}
								else
								{
									alert('冲抵成功，借款提前结清确认完成！');
									art.dialog.close();
								}
								art.dialog.open.origin.location.href=ctx+"/borrow/payback/earlyFinishConfirm/list";
							},
					       error: function(){
							artDialog.alert('冲抵失败！');
							return false;
					      }
					});
					
				}else{
					artDialog.alert('蓝补金额和减免提前结清金额小于一次性提前结清应还总额，不允许提交提前结清确认！');
					return false;   
				}
		}else{
			artDialog.alert('蓝补金额和减免提前结清金额为空，不允许提交提前结清确认！');
			return false;
		}
	}
	else{
		// 提前结清确认不通过
		 if(returnReason == null || returnReason == ""){
		    	artDialog.alert('请输入审核意见!');
				return false;
		 }
		 art.dialog.confirm("是否确认提前结清不通过？",function(){
			 $.ajax({  
			   type : "POST",
 			   data:{
 				   "id":chargeId,
 				   "returnReason":returnReason,
 				   "pId":pId,
 				   "contractCode":contractCode,
 				   "flag":'1'
 			   },
 			   url : ctx+"/borrow/payback/earlySettlement/updatePaybackChargeStatus",
 			   datatype : "json",
 			   success : function(data){
 					artDialog.alert("提前结清确认不通过成功！");
 					setTimeout(function(){
 						art.dialog.close();
						art.dialog.open.origin.location.href=ctx+"/borrow/payback/earlyFinishConfirm/list";
					},3000);
 				},
 				error: function(){
 					artDialog.alert("提前结清确认不通过失败！");
 				}
 			});
		 },
		function()
			{
			 	alert("取消执行");
			}
		 );
		return false;
	}	
	});
	
	$("#closeBtn").click(function(){
		art.dialog.close();
	});
});

function init(){
	// 初始化返款金额
	var val = $('input:radio[name="isTopCashBack"]:checked').val();
       if (val == '0') {
				$("#isSettleMoney").css('display','block'); 
			} else {
				$("#isSettleMoney").css('display','none');
			}
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
	$("#confirmPaybackForm").attr("action",  ctx + "/borrow/payback/earlyFinishConfirm/list");
	$("#confirmPaybackForm").submit();
	return false;
}