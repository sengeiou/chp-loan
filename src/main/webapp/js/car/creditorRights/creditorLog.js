/**
 * 弹出债权历史 loanCode
 */
function showCalLog(loanCode){
 		var url=ctx + "/car/creditorRight/getCalCreLog?loanCode="+loanCode;
	    art.dialog.open(url, {  
		   id: 'information',
		   title: '债权历史',
		   lock:true,
		   width:600,
		   height:400
		},false);  
}