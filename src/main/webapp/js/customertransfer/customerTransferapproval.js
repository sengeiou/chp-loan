
$(function(){
});

/**
 * 营业部经理初审弹出
 * @param applyId
 * @param wobNum
 * @param token
 */
function launchTool(applyId,wobNum,token){
	var url="/bpm/flowController/openForm?applyId="+applyId+"&wobNum="+wobNum+"&dealType=0&token="+token;
	var sub = SubDialogHandle.create(url,null,null,null).loadSubWindow();
	$('#subClose').off('click').on('click',function(){sub.closeSubWindow();});
	}
	
	function Verification(){
		if($("#managerCodebf").val()==$("#managerCodeaf").val()){
			BootstrapDialog.alert("该客户的理财经理已是您，不能发起抢单！");
		}else{
			$("#GrabForm").submit();
		}
		
	}
	