$(document).ready(function(){

	/**
	 * @function 是否结清点击事件
	 */
	$('input:radio[name="isConfirm"]').click(function(){
		  var val=$('input:radio[name="isConfirm"]:checked').val();
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
	// 初始化
	init();

	/**
	 * @function 是否还款点击事件
	 */
	$('input:radio[name="isTopCashBack"]').click(function(){
		  var val=$('input:radio[name="isTopCashBack"]:checked').val();
            if(val=='0'){
                $("#isSettleMoney").css('display','block');
	          }else{
                $("#isSettleMoney").css('display','none');
            }
	})
	
	// 结清确认
	$('#submitConfirmBtn').click(function() {

		// 是否确认返款按钮
		var isBack=$('input:radio[name="isTopCashBack"]:checked').val();
		if(isBack == 0){
			// 返款金额
			var backMoney=$("#paybackBackAmountBak").val();
			// 催收服务费金额
			var urgeMoney=$("#paybackBackAmount").val();
			var regex1 =/^[0-9]+([.]\d{1,2})?$/;
			var regex2= /^\d+(\.\d+)?$/;

			if (!regex2.test(backMoney)) {
				artDialog.alert('返款金额只能为数字!');
					return false;
			}
			if (!regex1.test(backMoney)) {
				artDialog.alert('返款金额只能保留两位小数!');
					return false;
			}
			if(parseFloat(urgeMoney-backMoney).toFixed(2) < 0){
			       artDialog.alert("返款金额不能大于催收服务费金额！");
			        return false;
		    	 }
		}

		var returnReason = $('#returnReason').val();
		if(returnReason.length ==0 || typeof(returnReason) == "undefinde"){
			artDialog.alert('审核意见不能为空');
			return false;
		}
		
		// 来源系统版本  chp1.0 和chp2.0 直接结清 chp3.0 判断应还是否大于实还
		var dateVersion = $('#dictSourceType').val();
		if(dateVersion == 3){
			// 实还总额
			var actualAmount=$("#actualAmount").val();
			// 应还总额
			var moneyAmount=$("#moneyAmount").val();
			// 减免金额
			var overDueAmontNoPaybacked=$("#overDueAmontNoPaybacked").val();
			
			var monthBeforeAmount=moneyAmount-overDueAmontNoPaybacked;
			
			if(monthBeforeAmount > actualAmount){
				artDialog.alert('应还款总金额大于实际还款总金额，请修改减免金额以后再进行结清!');
				return false;
			}
		}
		$.ajax({     
			   type : "POST",
			   data:$('#confirmInfoForm').serialize(),
			   url : ctx+"/borrow/payback/confirmPayback/save",
			   datatype : "json",
			   async:false,  
			   success : function(data){
					if("0"==data){
						art.dialog.close();
						artDialog.alert("结清确认成功！");
						art.dialog.open.origin.location.href=ctx+"/borrow/payback/confirmPayback/list";
					}else{
						artDialog.alert("结清失败！");
					}
				},
		       error: function(){
				artDialog.alert('服务器没有返回数据，可能服务器忙，请重试!');
				return false;
		      }
		});
	});
	
	// 关闭按钮事件
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
