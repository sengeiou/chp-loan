/**
 * 弹出还款历史界面
 * rPaybackId ：还款主表申请表主键
 * dictLoanStatus：还款流水记录的操作步骤
 */
function showLoanHis(rPaybackId,dictLoanStatus){
 			var url=ctx + "/borrow/payback/historicalRecords/historicalRecords?rPaybackId="+rPaybackId+"&dictLoanStatus="+dictLoanStatus;
 			art.dialog.open(url, {  
 		         id: 'his',
 		         title: '历史记录',
 		         lock:true,
 		         width:900,
 		     	 height:400
 		     },  
 		     false);
}

/**
 * 弹出集中划扣已拆分历史界面
 * rId ：还款申请表ID
 */
function showSplitHis(rId){
 			var url=ctx + "/borrow/payback/historicalRecords/allSplitHis?rId="+rId;
 			art.dialog.open(url, {  
		         id: 'his',
		         title: '已历史记录',
		         lock:true,
		         width:900,
		     	 height:400
		     },  
		     false);
}