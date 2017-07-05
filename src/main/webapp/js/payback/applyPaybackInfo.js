
$(document).ready(function(){
	
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	}
	
	/**
	 * @function 蓝补金额联动
	 */
	$('[id=monthBlusAmount]').on('blur', function() {
		var amountCount = 0;
		$('input[id="monthBlusAmount"]').each(function(idx) {
			var price = parseFloat($(this).val());
			if (price) {
				amountCount += price;
			}
			$("#offsetBlusAmount").val(amountCount.toFixed(2));
			$("#bulsbalanceAmount").val(($("#paybackBuleAmount").val()-amountCount).toFixed(2));
		});
	});
	
	/**
	 * @function 结清表单提交
	 */
	$('#applyPayInfoSaveBtn').click(function() {
		var paybackBuleAmount = $("#paybackBuleAmount").val();
		var applyAmountPayback = $("[name='paybackCharge.applyAmountPayback']").val();

		var offsetBlusAmount   = $("[name='paybackApply.offsetBlusAmount']").val(); // 本次蓝补冲抵金额
		var monthAmountSum = $("#monthAmountSum").val();
		var regex3 =/^[0-9]+([.]\d{1,2})?$/;
		var regex1=/^\d{1,20}\.\d{1,2}$/;
		var regex2=/^\d{1,20}$/;
		var isOKApplyAmount = regex1.test(applyAmountPayback)||regex2.test(applyAmountPayback);
		
		var applyAmountViolate = $("[name='paybackCharge.applyAmountViolate']").val();
		var monthPenaltySum = $("#monthPenaltySum").val();
		var isOKApplyViolate = regex1.test(applyAmountViolate)||regex2.test(applyAmountViolate);
		
		var applyAmountPunish = $("[name='paybackCharge.applyAmountPunish']").val();
		var monthInterestPunishSum = $("#monthInterestPunishSum").val();
		var isOKApplyPunish = regex1.test(applyAmountPunish)||regex2.test(applyAmountPunish);
		var dictPayUse = $('#dictPayUse').val();
		var dictLoanStatus = $('#dictLoanStatus').val();
		// 未还违约金(滞纳金)及罚息总金额
		var penaltyTotalAmount = $("[name='paybackCharge.penaltyTotalAmount']").val();
		
		if (dictPayUse != "5") {
			var files = $('#files').val();
			if (files.length > 0) {
				files = files.substring(files.lastIndexOf("."));
				if (!(".zip" == files)) {
					top.$.jBox.tip('请上传后缀为.zip的压缩文件!','warning');
					return false;
				} else{
					// 未还违约金(滞纳金)及罚息总金额 大于0，则不允许提交申请。
					if(penaltyTotalAmount > 0) {
						top.$.jBox.tip("有未还违约金(滞纳金)及罚息，不允许提前结清！",'warning');
						return false;
					}
					$('#applyPayinfoForm').submit();
				}
			} else {
				top.$.jBox.tip('请上传提前结清资料!','warning');
				return false;
			}
		}else{
			var today = new Date();
		    var i1 = today.getHours()*60*60+ today.getMinutes()*60;

			var startTime1 = new Date("January 12,2006 11:50:00");
			var i2 = startTime1.getHours()*60*60+ startTime1.getMinutes()*60;
			var endTime1 = new Date("January 12,2006 13:00:00");
			var i3 = endTime1.getHours()*60*60+ endTime1.getMinutes()*60;
			
			var startTime2 = new Date("January 12,2006 17:50:00");
			var i4 = startTime2.getHours()*60*60+ startTime2.getMinutes()*60;
			var endTime2 = new Date("January 12,2006 19:00:00");
			var i5 = endTime2.getHours()*60*60+ endTime2.getMinutes()*60;
			
			if (i2 < i1 && i1 < i3) {
				top.$.jBox.tip("系统冲抵中请勿操作！");
				return false;
			}
			if (i4 < i1 && i1 < i5) {
				top.$.jBox.tip("系统冲抵中请勿操作！");
				return false;
			}
			
			if(offsetBlusAmount - paybackBuleAmount > 0){
				top.$.jBox.tip('蓝补金额不足，不允许冲抵!','warning');
				return false;
			}
			
			// 申请还期供输入框的验证
            if(applyAmountPayback == null && applyAmountPayback == "" 
            	&& applyAmountViolate == null && applyAmountViolate == "" 
            	&&	applyAmountPunish == null && applyAmountPunish == "" ){
            	top.$.jBox.tip("申请还款金额不允许都为空或0！",'warning');
				return false;
			}
            if(applyAmountPayback != null && applyAmountPayback != ""){
				if (!regex3.test(applyAmountPayback)) {
					 top.$.jBox.tip('申请还期供金额只能保留2位小数!','warning');
						return false;
				}
				if (!isOKApplyAmount) {
					 top.$.jBox.tip('申请还期供金额只能为数字!','warning');
						return false;
				}
				if(applyAmountPayback - paybackBuleAmount > 0){
					top.$.jBox.tip('申请还期供金额不能大于蓝补金额!','warning');
					return false;	
				}
            }
			//申请还违约金(滞纳金)总额输入框验证
            if(applyAmountViolate != null && applyAmountViolate != ""){
				if (!regex3.test(applyAmountViolate)) {
					 top.$.jBox.tip('申请还违约金(滞纳金)额只能保留2位小数!','warning');
						return false;
				}
				if (!isOKApplyViolate) {
					 top.$.jBox.tip('申请还违约金(滞纳金)额只能为数字!','warning');
						return false;
				}
				if(applyAmountViolate - paybackBuleAmount > 0){
					top.$.jBox.tip('申请还违约金(滞纳金)额不能大于蓝补金额!','warning');
					return false;	
				}
				if(applyAmountViolate - monthPenaltySum > 0){
					top.$.jBox.tip('申请还违约金(滞纳金)额不能大于未还违约金(滞纳金)总额!','warning');
					return false;	
				}
            }
			//申请还罚息输入框验证
            if(applyAmountPunish != null && applyAmountPunish != ""){
				if (!regex3.test(applyAmountPunish)) {
					 top.$.jBox.tip('申请还罚息金额只能保留2位小数!','warning');
						return false;
				}
				if (!isOKApplyPunish) {
					 top.$.jBox.tip('申请还罚息金额只能为数字!','warning');
						return false;
				}
				if(applyAmountPunish - paybackBuleAmount > 0){
					top.$.jBox.tip('申请还罚息金额不能大于蓝补金额!','warning');
					return false;	
				}
				if(applyAmountPunish - monthInterestPunishSum > 0){
					top.$.jBox.tip('申请还罚息金额不能大于未还罚息总额!','warning');
					return false;	
				}
            }
            // 如果借款状态为‘结清待确认’且未还违约金(滞纳金)和未还罚息都为0，给出'期供已偿还完毕，本次冲抵失败'提示
            if(dictLoanStatus == '92' && applyAmountPayback > 0){
            	top.$.jBox.tip('期供已偿还完毕，本次冲抵失败！');
            	window.location.href=ctx+"/borrow/payback/applyPaybackUse/list";
            	return false;
            }
        	$('#applyPayinfoForm').submit();
		}
	})
	// 返回
	$('#btnCancel').click(function(){
		window.location.href=ctx+"/borrow/payback/applyPaybackUse/returnPage";
	})
	
	/**
	 * @function 绑定还款明细页面弹出框事件
	 */
	$("#applyPaybackMonthBth").click(function() {
		var contractCode = $("[name='contractCode']").val();
		var monthId = $("#paybackMonthId").val();
		
//		if(contractCode == null || contractCode == ""){
//			artDialog.alert('该笔借款不存在!');
//		}
//		if(monthId == null || monthId == ""){
//			artDialog.alert('该笔借款没有期供!');
//		}
		
		var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode+"&id="+monthId;
		art.dialog.open(url, {  
	         id: 'his',
	         title: '还款明细',
	         lock:true,
	         width:1200,
	     	 height:500
	     },  
	     false);
	});
});



