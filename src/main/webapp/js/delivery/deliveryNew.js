// 待交割列表回显数据清除
$(document).ready(function() {	
	$("#delRemoveBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio	
		$(":radio").attr("checked", false);
		// 清除select	
		$("select").val("");
		$("#waitForm").submit();
	});	
});

//单条被交割按钮
function traBtn(contractCode,loanCode,custName,custCode,deliveryResult,typeRole){
	if(""==contractCode || null==contractCode){
		art.dialog.alert("合同编号不能为空！");
		return false;
	}
	var url=ctx + "/borrow/delivery/deliveryWaitOneList?typeRoleP="+typeRole+"&loanCode="+loanCode+"&custNameP="+custName+"&custCodeP="+custCode+"&deliveryResultP="+deliveryResult;
	art.dialog.open(url,{
		title:"交割办理",
		width:1000,
		height:450,
		lock:true
		/*button:[{
			name: '办理',
//			okVal: '确定',
			callback: function(deliveryData) {
				console.log("办理数据参数"+deliveryData);
//				var custName = $("#custNameP").val();
				var custName=document.getElementById('custNameP').value;
				alert(111);
				alert(custName);
			}
		}]*/
	},false);
};

//单条办理按钮
function blBtnOne(loanStoreOrgId,teamManagerCode,loanTeamOrgId,managerCode,customerServicesCode,typeRoleOne,loanCode,deliveryResultP,custCodeP,custNameP,typeRoleP){
	//typeRoleOne 1 客服，2 客户经理 3 团队经理    根据不同角色update不同的值
	
	if(typeRoleOne==null || ""==typeRoleOne){
		//art.dialog.alert("角色不能为空！");
		art.dialog.alert("页面无交割数据不能进行交割办理。");
		return false;
	}
	if(loanCode==null || ""==loanCode){
		art.dialog.alert("借款编码不能为空！");
		return false;
	}
	var parent = art.dialog.open.origin;//来源页面
	if(typeRoleOne==1){
		if(customerServicesCode!=null && ""!=customerServicesCode){
			var url=ctx + "/borrow/delivery/updateLoanInfoDelivery?customerServicesCode="+customerServicesCode+"&loanCode="+loanCode+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
			parent.location.href = url;
			art.dialog.close();
		}else{
			art.dialog.alert("客服编号不能为空！");
			return false;
		}
	}
	if(typeRoleOne==2){
		if(managerCode!=null && ""!=managerCode){
			var url=ctx + "/borrow/delivery/updateLoanInfoDelivery?managerCode="+managerCode+"&loanCode="+loanCode+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
			parent.location.href = url;
			art.dialog.close();
		}else{
			art.dialog.alert("客户经理编号不能为空！");
			return false;
		}
	}
	if(typeRoleOne==3){
		/*if(loanStoreOrgId==null || ""==loanStoreOrgId){
			art.dialog.alert("门店不能为空！");
			return false;
		}*/
		if(teamManagerCode==null || ""==teamManagerCode){
			art.dialog.alert("团队经理不能为空！");
			return false;
		}
		if(loanTeamOrgId==null || ""==loanTeamOrgId){
			art.dialog.alert("团队不能为空！");
			return false;
		}
		//var url=ctx + "/borrow/delivery/updateLoanInfoDelivery?loanStoreOrgId="+loanStoreOrgId+"&loanTeamOrgId="+loanTeamOrgId+"&teamManagerCode="+teamManagerCode+"&loanCode="+loanCode+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
		var url=ctx + "/borrow/delivery/updateLoanInfoDelivery?loanTeamOrgId="+loanTeamOrgId+"&teamManagerCode="+teamManagerCode+"&loanCode="+loanCode+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
		parent.location.href = url;
		art.dialog.close();
	}
	
};

//单条被交割列表回显数据清除
$(document).ready(function() {	
	$("#delRemoveBtnOne").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio	
		$(":radio").attr("checked", false);
		// 清除select	
		$("select").val("");
		$("#singleForm").submit();
	});	
});

//批量交割弹出层
$(document).ready(function() {	
	$("#batchDelivery").click(function(){
		var valArr = new Array;
		$(":input[name='loanCodes']:checked").each(function(i) {
			valArr[i] = $(this).val();	
		});	
		//alert(valArr);
		if (valArr != null && "" != valArr) {
			var vals = valArr.join(',');
			var radio = document.getElementsByName("typeRole"); //角色
	        var typeRole=""; //角色
	        for (i=0; i<radio.length; i++) {  
	            if (radio[i].checked) {  
	                typeRole=radio[i].value;
	            }  
	        } 
	        var custName = $.trim($("#custName").val());//员工姓名
	        var custCode = $.trim($("#custCode").val());//员工编号
	        var deliveryResult = $.trim($("#deliveryResult").val());
			var url=ctx + "/borrow/delivery/deliveryWaitBatchList?typeRoleP="+typeRole+"&loanCodes="+vals+"&custNameP="+custName+"&custCodeP="+custCode+"&deliveryResultP="+deliveryResult;
			art.dialog.open(url,{
				title:"交割办理",
				width:1000,
				height:450,
				lock:true
				/*button:[{
					name: '办理',
					callback: function() {
						
					}
				}]*/
			},false);
		} else {
			art.dialog.alert('请选择办理项');
		}	
	});
});

//批量办理按钮
function blBtnBatch(loanStoreOrgId,teamManagerCode,loanTeamOrgId,managerCode,customerServicesCode,typeRoleOne,loanCodes,deliveryResultP,custCodeP,custNameP,typeRoleP){
	//typeRoleOne 1 客服，2 客户经理 3 团队经理    根据不同角色update不同的值
	if(typeRoleOne==null || ""==typeRoleOne){
		//art.dialog.alert("角色不能为空！");
		art.dialog.alert("页面无交割数据不能进行交割办理。");
		return false;
	}
	if(loanCodes==null || ""==loanCodes){
		art.dialog.alert("借款编码不能为空！");
		return false;
	}
	var parent = art.dialog.open.origin;//来源页面
	if(typeRoleOne==1){
		if(customerServicesCode!=null && ""!=customerServicesCode){
			var url=ctx + "/borrow/delivery/updateLoanInfoDelBatch?customerServicesCode="+customerServicesCode+"&loanCodes="+loanCodes+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
			parent.location.href = url;
			art.dialog.close();
		}else{
			art.dialog.alert("客服编号不能为空！");
			return false;
		}
	}
	if(typeRoleOne==2){
		if(managerCode!=null && ""!=managerCode){
			var url=ctx + "/borrow/delivery/updateLoanInfoDelBatch?managerCode="+managerCode+"&loanCodes="+loanCodes+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
			parent.location.href = url;
			art.dialog.close();
		}else{
			art.dialog.alert("客户经理编号不能为空！");
			return false;
		}
	}
	if(typeRoleOne==3){
		/*if(loanStoreOrgId==null || ""==loanStoreOrgId){
			art.dialog.alert("门店不能为空！");
			return false;
		}*/
		if(teamManagerCode==null || ""==teamManagerCode){
			art.dialog.alert("团队经理编号不能为空！");
			return false;
		}
		if(loanTeamOrgId==null || ""==loanTeamOrgId){
			art.dialog.alert("团队不能为空！");
			return false;
		}
		//var url=ctx + "/borrow/delivery/updateLoanInfoDelBatch?loanStoreOrgId="+loanStoreOrgId+"&loanTeamOrgId="+loanTeamOrgId+"&teamManagerCode="+teamManagerCode+"&loanCodes="+loanCodes+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
		var url=ctx + "/borrow/delivery/updateLoanInfoDelBatch?&loanTeamOrgId="+loanTeamOrgId+"&teamManagerCode="+teamManagerCode+"&loanCodes="+loanCodes+"&deliveryResultP="+deliveryResultP+"&custCodeP="+custCodeP+"&custNameP="+custNameP+"&typeRoleP="+typeRoleP;
		parent.location.href = url;
		art.dialog.close();
	}
	
};

//交割统计按钮
$(document).ready(function() {
	$("#deliveryCount").click(function(){
		var url=ctx + "/borrow/delivery/deliveryCountView";
		art.dialog.open(url,{
			title:"交割统计",
			width:500,
			height:300,
			lock:true
		},false);
	});
});

//时间搜索起止时间校验
function checkDate() {
	var startTime = $("#startTime").val();
	var start = new Date(startTime.replace("-", "/").replace("-", "/"));  
	var endTime = $("#endTime").val();
	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	if (start > end) {
		art.dialog.alert("起始时间不能大于终止时间!");
	}
};

//交割统计回显数据清除
$(document).ready(function() {	
	$("#countRemoveBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除select	
		$("select").val("");
		$("#countForm").submit();
	});	
});

//交割已办回显数据清除
$(document).ready(function() {	
	$("#removeBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除select	
		$("select").val("");
		$("#delForm").submit();
	});	
});

//导出交割数据
$(document).ready(function() {
	$("#exportExcel").click(function(){
		var valArr = new Array;
		$(":input[name='ids']:checked").each(function(i) {
			valArr[i] = $(this).val();	
		});	
		if (valArr != null && "" != valArr) {
			var vals = valArr.join(',');
			window.location.href=ctx+"/borrow/taskDelivery/exportExcel?checkIds="+vals;
			//$('#delForm').submit();
		}else{
			//var url=ctx+"/borrow/taskDelivery/exportExcel";
			//window.location.href = url;
			window.location.href=ctx+"/borrow/taskDelivery/exportExcel";
			//$('#delForm').submit();
		}
	});	
});

