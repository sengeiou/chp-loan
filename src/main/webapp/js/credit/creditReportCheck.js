
//跳转简版选择共借人、主借人界面
function checkLoanManSimple(applyId){
	
	var url = ctx+"/credit/creditReportSimple/checkLoanMan?applyId="+applyId;
    art.dialog.open(url, {  
	   id: 'creditReportSimple',
	   title: '选择主借人、共借人',
	   lock:true,
	   width:700,
	   height:150
	},false);
	
}

//跳转到共借人征信简版
function intoCreditReportSimple(rCustomerCoborrowerId) {

	// 如果父页面重载或者关闭其子对话框全部会关闭
	var win = art.dialog.open.origin;//来源页面
	art.dialog.close();
	win.location.href = ctx + "/credit/creditReportSimple/form.do?loanCode=" + $("#creditReportSimpleLoanCode").val() + "&rCustomerCoborrowerId=" + rCustomerCoborrowerId + "&customerId=" + $("#creditReportSimpleCustomerId").val();
	
}

//跳转详版选择共借人、主借人界面
function checkLoanMan(applyId, id){
	
	var loanInfoOldOrNewFlag = $(id).attr("loanInfoOldOrNewFlag");
	var title;
	var url;
	if(loanInfoOldOrNewFlag == "1"){
		title = "选择主借人、自然人保证人";
		url = ctx+"/creditdetailed/info/checkLoanMan?applyId="+applyId +"&loanInfoOldOrNewFlag=1";
	}else{
		title = "选择主借人、共借人";
		url = ctx+"/creditdetailed/info/checkLoanMan?applyId="+applyId +"&loanInfoOldOrNewFlag=0";
	}
	
    art.dialog.open(url, {  
	   id: 'creditReportSimple',
	   title: title,
	   lock:true,
	   width:700,
	   height:300
	},false);
	
}

// 点击录入
function readyParam( rId , customerType , loanCode , t ){
	var param = "";
	var version = $(t).parent("td").parent("tr").find("input[type='radio']:checked").val();
	if (version == "2") {
		var win = art.dialog.open.origin;//来源页面
		art.dialog.close();
		win.location.href = ctx + "/credit/creditReportSimple/form?loanCode=" + loanCode + "&rCustomerCoborrowerId=" + rId + "&customerId=" + $("#creditReportSimpleCustomerId").val() + "&dictCustomerType=" + customerType + "&version=" + version;

	} 
	if(version == "1"){
		param = "rId="+ rId + "&customerType=" + customerType + "&loanCode=" + loanCode + "&version=" + version;
		var win = art.dialog.open.origin;//来源页面
		art.dialog.close();
		win.location.href  = ctx + "/creditdetailed/info/detail?"+param;
	}
	
	if(version != "1" && version != "2"){
		alert("请选择征信版本");
	}
}

function getRiskList(){
	var loanCode = $("input[name='loanCode']").val();
	$.ajax({
		url: ctx+"/creditdetailed/info/getRiskList",
		data:"loanCode="+loanCode,
		type: "post",
		dataType:'json',
		success:function(data){
			if(data != null){
				for(var i = 0; i < data.length; i++ ){
					
					$("input[str='"+data[i].rId+"']").val(data[i].riskCreditVersion);
					
					$("input[mark='"+data[i].rId+"']").each(function(){
						$(this).attr("checked",false);
					});
					if(data[i].riskCreditVersion == "1"){
						$("input[mark='"+data[i].rId+"']").eq(0).attr('checked', 'checked');
					}
					if(data[i].riskCreditVersion == "2"){
						$("input[mark='"+data[i].rId+"']").eq(1).attr('checked', 'checked');
					}
				}
			}
		}
	});
}

// 切换提示
function changeVersion(t){
	
	var version = $(t).parents("td").find("input[name='versionHID']").val();
	var tValue = $(t).val();
	if(version != tValue && version == "1"){
		if(confirm("您已录过详版征信，确定切换简版吗？")){
			$(t).parents("td").find("input[type='radio']").eq(0).removeAttr("checked");
			$(t).parents("td").find("input[type='radio']").eq(1).attr("checked","checked");
		}else{
			$(t).parents("td").find("input[type='radio']").eq(0).attr("checked","checked");
			$(t).parents("td").find("input[type='radio']").eq(1).removeAttr("checked");
		}
	}
	
	if(version != tValue && version == "2"){
		if(confirm("您已录过简版征信，确定切换详版吗？")){
			$(t).parents("td").find("input[type='radio']").eq(0).attr("checked","checked");
			$(t).parent("td").find("input[type='radio']").eq(1).removeAttr("checked");
		}else{
			$(t).parents("td").find("input[type='radio']").eq(0).removeAttr("checked");
			$(t).parents("td").find("input[type='radio']").eq(1).attr("checked","checked");
		}
	}
	
	
}

