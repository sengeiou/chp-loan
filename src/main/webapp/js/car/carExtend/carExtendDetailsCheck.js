/**
 * 弹出车借展期借款信息界面 applyId 
 */
function showExtendLoanInfo(applyId){
 		location.href=ctx + "/car/carExtend/carExtendDetails/extendCheck?applyId="+applyId;
}

/**
 * 弹出车借展期合同已制作查看 applyId
 */
function extendContractDone(applyId){
	window.location.href = ctx + "/car/carContract/checkRate/extendContractDone?applyId="+applyId;     
}




