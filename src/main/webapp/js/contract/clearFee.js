var fee={};
fee.inputPropId= new Array("feePaymentAmount","fee_payment_amount","bv_feePaymentAmount","feeConsult"
		,"fee_consult","fee_urged_service","contractAmount","contract_amount","bv_contractAmount","feeAuditAmount",
		"fee_audit_amount","contractMonthRepayAmount","contract_back_month_money","feeService",
		"fee_service","contractExpectAmount","contract_expect_amount","feeInfoService","fee_info_service",
		"feeCount","fee_count","feeUrgedService","fee_urged_service",
		"monthFeeService2","month_fee_service2","month_fee_service1","monthFeeService1",
		"monthFeeConsult","month_fee_consult","monthMidFeeService","month_mid_fee_service",
		"monthPayTotalAmount","month_pay_total_amount","monthRateService","comprehensiveServiceRate");
fee.spanPropId = new Array("feeUrgedService");
fee.clear=function(){
	var feeProp;
	var prop = fee.inputPropId;
	for(m in prop){
		feeProp =prop[m];
		$('#'+feeProp).val('');
	}
	prop = fee.spanPropId;
	for(m in prop){
		feeProp = prop[m];
		$('#'+feeProp).html('');
	}
};