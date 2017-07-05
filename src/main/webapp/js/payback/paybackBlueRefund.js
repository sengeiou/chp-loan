var infos = {};
  $(document).ready(function(){
	  infos.datavalidate();
	  $( '#paybackBuleAmount').removeAttr('onblur').on('blur',getLastAmount);  

  });
function getLastAmount()
{
	var amount=$(this).val();
	  if(amount==undefined || amount==""){
		  alert('请输入退款金额');
		  window.setTimeout (function(){ document.getElementById ('paybackBuleAmount'). select();},0 );
		  return false;
	  }
	  var pre=$('#paybackBuleAmount1').val();
	  var last= eval(pre-amount);
	  $('#paybackBuleAmountLast').val(last);
}

//保存数据
function saveData(){
	var data = makeParamCard();
	$.ajax({
		url: ctx+"/borrow/blue/PaybackBlue/saveTransData",
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
			if(data){
				art.dialog.tips("操作成功");
			}
			else{
				art.dialog.tips("操作失败");
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

function updateRefund(formId) {
	
	$.ajax({
		url : ctx + '/borrow/blue/PaybackBlue/saveRefundData',
		type : 'post',
		data : $('#' + formId).serialize(),
		dataType : 'text',
		success : function(data) {
			if (data) {
 				// 如果父页面重载或者关闭其子对话框全部会关闭
				var win = art.dialog.open.origin;//来源页面
				art.dialog.close();
				art.dialog.alert("退款成功！");
			} else {
				art.dialog.alert("退款失败,请重试！");
			}
		},
		error : function() {
			art.dialog.alert("服务器异常,请重试！");
		},
		async : false

	});
}

infos.datavalidate = function() {
	$("#inputForm").validate(
			{
				onkeyup : false,
				submitHandler : function(form) {
					updateRefund("inputForm");
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio")
							|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
};
