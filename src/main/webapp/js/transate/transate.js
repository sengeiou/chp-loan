$(function(){		
	/*//表头固定
	$("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#tableList').bootstrapTable({
	method: 'get',
	cache: false,
	height: $(window).height()-330,

	pageSize: 30,
	pageNumber:1
	});
	$(window).resize(function () {
	$('#tableList').bootstrapTable('resetView');
	});*/
   //----------------------------------------
	var url = $("#url").val();
	var protoColUrl = $('#protoColUrl').val();
	// 影像弹出层
	$("#iconBtn").click(function(){
		art.dialog.open(url, {
			title: "客户影像资料",	
			top: 80,
			lock: true,
		    width: 1000,
		    height: 450,
		},false);	
	});
	
	// 协议查看弹出层
	if($("#agreementBtn").length>0){
		$("#agreementBtn").click(function(){
			art.dialog.open(protoColUrl, {
				title: "协议查看",	
				top: 80,
				lock: true,
				width: 1000,
				height: 450,
			},false);		
		});	
	}
	
	// 委托提现/取消委托提现
	$("#trustCash").click(function(){
		var btn = this;
		var trustCash = "0";
		if($(btn).text().trim()=="委托提现"){
			trustCash = "1";
		}
		$.ajax({
			type : 'post',
			url : ctx + '/borrow/transate/updateTrustCash',
			data : {
					'loanCode':$(this).attr('loanCode'),
					'applyId':$(this).attr('applyId'),
					'dictLoanStatusCode':$('#dictLoanStatus').val(),
					'trustCash':trustCash
				},
			dataType:'json',
			success : function(data) {
				art.dialog.alert("更新完成");
				$(btn).text($(btn).text().trim()=="委托提现"?"取消委托提现":"委托提现");
			}
		});
	});	
	
	// 委托充值/取消委托充值
	$("#trustRecharge").click(function(){
		var btn = this;
		var trustRecharge = "0";
		if($(btn).text().trim()=="委托充值"){
			trustRecharge = "1";
		}
		$.ajax({
			type : 'post',
			url : ctx + '/borrow/transate/updateTrustRecharge',
			data : {
					'loanCode':$(this).attr('loanCode'),
					'applyId':$(this).attr('applyId'),
					'dictLoanStatusCode':$('#dictLoanStatus').val(),
					'trustRecharge':trustRecharge
				},
			dataType:'json',
			success : function(data) {
				art.dialog.alert("更新完成");
				$(btn).text($(btn).text().trim()=="委托充值"?"取消委托充值":"委托充值");
			}
		});
	});	
});

//关闭模态框
function closeBtn() {
	$('#icon').modal('toggle');
};
// 数据信息列表回显数据清除
$(document).ready(function() {	
	$("#lRemoveBtn").click(function(){		
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio			
		$(":radio").attr("checked", false);
		// 清除select			
		$("select").val("");
		$("select").trigger("change");	
		$("#loanForm").submit();
	});	
});

// 信借已办列表回显数据清除
$(document).ready(function() {	
	$("#tRemoveBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		//$(":checkbox").attr("checked", false);
		// 清除radio			
		$(":radio").attr("checked", false);
		// 清除select			
		$("select").val("");
		$("select").trigger("change");	
		$("#orgName").val("");
		$("#orgCode").val("");
		$("#traForm").submit();
	});	
});

//合同寄送及结算通知书申请
function showContract(contractcode,flag){
	   var titleStr="";
	   var fileType ='';
		   
	   if(flag=='1'){
		   fileType='0';
		   titleStr="合同寄送申请";
	   }else{
		   fileType='1';
		   titleStr='纸质结清通知书申请';
	   }
	   //判断服务部门那里是否有已经邮寄的合同编号 如果有则 不允许再次提交
	    var code =contractcode;
		//查看改合同编号在门店待办中是否有待还款的POS信息
			  $.ajax({  
				   type : "POST",
				   data:{
					   contractCode:contractcode,
					   flag:flag,
					   fileType:fileType,
					   loanCode:$("#loanCode").val()
				   },
					url : ctx+"/borrow/serve/customerServe/findApplyByDealt",
					success : function(data){
						if(data=='true'){
							var url=ctx+"/borrow/serve/customerServe/openContractSendInfo?flag="+flag+"&contractcode="+contractcode + "&loanCode=" + $("#loanCode").val()+"&fileType="+fileType;
							   art.dialog.open(url, {
									title: titleStr,	
									top: 80,
									lock: true,
								    width: 650,
								    height: 350,
								},false);
						}else {
								 top.$.jBox.tip('合同 '+code+data,'warning');
								  return false;
							 }
					},
					//其他异常错误
					error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
	   

	   
	
}

function checkBtn(obj){
	var isManager =$('#isManager').val();
	if(isManager == undefined){
		isManager = 'false';
	}
	var loanCode = $('#loanCodeHid'+obj.id).val();
	var applyId = $('#applyIdHid'+obj.id).val();
	var status = $('#status'+obj.id).val();
	var coboNames = $('#coboNames'+obj.id).val();
	var query = $('#query'+obj.id).val();
	var loanInfoOldOrNewFlag = $('#loanInfoOldOrNewFlag'+obj.id).val();
	var url = ctx+"/borrow/transate/transateDetails?loanCode="+loanCode+"&applyId="+applyId+"&status="+status+"&coboNames="+coboNames+"&query="+query+"&isManager="+isManager+"&loanInfoOldOrNewFlag="+loanInfoOldOrNewFlag;				
     window.location = url;
/*	$.ajax({
		type : 'post',
		url : ctx+'/borrow/transate/checkUrl',
		data : {'loanCode':loanCode},
		async: false,
		success : function(data) {
			if (data) {
				url = ctx+"/borrow/transate/loanMinute?loanCode="+loanCode+"&status="+status+"&query="+query+"&isManager="+isManager;
			}else{
				url = ctx+"/borrow/transate/transateDetails?loanCode="+loanCode+"&applyId="+applyId+"&status="+status+"&coboNames="+coboNames+"&query="+query+"&isManager="+isManager;				
			}
			window.location = url;
		}
	});	*/
}

//电子协议及结清证明申请
function showContractEmail(contractcode,fileType,loanFlag){
	   var titleStr="";
		   
	   if(fileType=='0'){
		   titleStr="电子协议申请";
	   }else{
		   titleStr='电子结清通知书申请';
	   }
	   //判断服务部门那里是否有已经电子协议发送的合同编号 如果有则 不允许再次提交
	    var code =contractcode;
		//查看改合同编号在门店待办中是否有待还款的POS信息
			  $.ajax({  
				   type : "POST",
				   data:{
					   contractCode:contractcode,
					   fileType:fileType,
					   loanCode:$("#loanCode").val()
				   },
					url : ctx+"/borrow/serve/customerServe/findEmailApplyByDealt",
					success : function(data){
						if(data=='true'){
							var url=ctx+"/borrow/serve/customerServe/openContractSendEmailInfo?loanCode="+$("#loanCode").val()+"&fileType="+fileType+"&contractcode="+contractcode+"&loanFlag="+loanFlag;
							   //var url=ctx+"/borrow/serve/customerServe/openContractSendEmailInfo?flag="+flag+"&contractcode="+contractcode + "&loanCode=" + $("#loanCode").val()+"&fileType="+fileType+"&receiverName="+$('#name').text()+"&receiverSex="+$('#sex').text()+"&coboCertNum="+$('#card').text()+"&receiverEmail="+$('#email').text();
							   art.dialog.open(url, {
									title: titleStr,	
									top: 80,
									lock: true,
								    width: 650,
								    height: 350,
								},false);
								 
						}else{
							top.$.jBox.tip('合同 '+code+data,'warning');
							  return false;
						}
					},
					//其他异常错误
					error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});

}