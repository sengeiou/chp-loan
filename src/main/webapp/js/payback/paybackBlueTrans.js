$(document).ready(function(){
	$( '#transmitBuleAmount').removeAttr('onblur').on('blur',getLastAmount);  

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
//			height: $(window).height()-260,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
});

function getLastAmount()
{
	var amount=$(this).val();
	  if(amount==undefined || amount==""){
		  art.dialog.tips('请输入转账金额');
		  window.setTimeout(function(){ document.getElementById ('transmitBuleAmount').select();},0 );
		  return false;
	  }
	  var pre=$('#paybackBuleAmounts').val();
	  var last= eval(pre-amount);
	  $('#paybackBuleAmountLast').val(last.toFixed(2));
}

//保存数据
function saveData(){
	if($('#customerName').val()==''){
		art.dialog.alert('请选择收款方信息!');
		return;
	}
	var je = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	if($('#transmitBuleAmount').val()==''){
		art.dialog.alert('转账金额不能为空!');
		return;
	}
	if (!je.test($('#transmitBuleAmount').val())){  
		art.dialog.alert("转账金额不正确！");  
		return false;  
	}
	if($('#transmitBuleAmount').val()<=0){
		art.dialog.alert('转账金额必须大于0!');
		return;
	}
	if($('#paybackBuleAmountLast').val()<0){
		art.dialog.alert('转账不能超出蓝补余额!');
		return;
	}
	if($('#transAmountFor').val()==''){
		art.dialog.alert('请添写转账用途!');
		return;
	}
	var data = $("#inputForm").serialize();//makeParamCard();
	$.ajax({
		url: ctx+"/borrow/blue/PaybackBlue/saveTransData",
		data:data,
		type: "post",
		success:function(data){
			if(data){
				var win = art.dialog.open.origin;//来源页面
				art.dialog.close();
				art.dialog.alert("操作成功");
				art.dialog.open.origin.location.href = ctx+"/borrow/blue/PaybackBlue/list";
			}
			else{
				art.dialog.alert("操作失败");
			}
		}
	});
}

// 做好保存参数
function makeParamCard(){
	var CardForm1=$("#inputForm1");
	var customerName = CardForm1.find("input[name='loanCustomer.customerName']"); 
	var customerCertNum = CardForm1.find("input[name='loanCustomer.customerCertNum']");  
	var contractCode = CardForm1.find("input[name='contractCode']");  
	var dictLoanStatus = CardForm1.find("input[name='loanInfo.dictLoanStatus']");  
	var paybackBuleAmount = CardForm1.find("input[name='paybackBuleAmount']");  
	var paybackMonthAmount = CardForm1.find("input[name='paybackMonthAmount']"); 
	var param = "";
	for(var i = 0; i < customerName.length; i++ ){
		param+="&paybackList["+i+"].loanCustomer.customerName="+$(customerName[i]).val();
		param+="&paybackList["+i+"].loanCustomer.customerCertNum="+$(customerCertNum[i]).val();
		param+="&paybackList["+i+"].contractCode="+$(contractCode[i]).val();
		param+="&paybackList["+i+"].loanInfo.dictLoanStatus="+$(dictLoanStatus[i]).val();
		param+="&paybackList["+i+"].paybackBuleAmount="+$(paybackBuleAmount[i]).val();
		param+="&paybackList["+i+"].paybackMonthAmount="+$(paybackMonthAmount[i]).val();
	
	}
	return param;
}

function changeSele(customerName,customerCertNum,contractCode,dictLoanStatus,paybackBuleAmount){
	$('#customerName').val(customerName);
	$('#customerCertNum').val(customerCertNum);
	$('#contractCode').val(contractCode);
	$('#dictLoanStatus').val(dictLoanStatus);
	$('#paybackBuleAmount').val(paybackBuleAmount);
}