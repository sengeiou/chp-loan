function getName(t){
	if($('#cerType').val()=='0'){
		var c = checkIdcard(t.value);
		if(!c){
			return;
		}
	}
	$.ajax({
		   type: "POST",
		   dataType: "json",
		   data : {cerNum:t.value},
		   url: ctx + "/borrow/creditor/getName",	
		   success: function(msg){
			   if(msg.length!=0){
				   $('#loanName').val(msg[0].loanname);
			   }
		   }
	})
}

function getOccupation(t){
	$.ajax({
		   type: "POST",
		   dataType: "json",
		   data : {type:t.value},
		   url: ctx + "/borrow/creditor/getOccupation",	
		   success: function(msg){
			   if(msg.length!=0){
				   $('#occupationTmp').val(msg[0].label);
				   $('#occupation').val(msg[0].value);
			   }
		   }
	})
}

function getLastsMonths(){
	var debtTimes = document.getElementById("repaymentDate").value;
	var payDateFri = document.getElementById("startDate").value;
	if(debtTimes == ''){
		return ;
	}
	if(payDateFri == ''){
		return ;
	}
	
	$.ajax({			
    	cache:false,
    	async:false,
		type: "POST",
		dataType: "json",
		url: ctx + "/borrow/creditor/getLastsMonths",
		data : {payDateFri : payDateFri,debtTimes : debtTimes},//传递参数 
		success: function(data){
			document.getElementById('surplusDate').value = data.value;
		}
	});
}

function sub(){
	if($('#loanName').val()==''){
		art.dialog.alert('借款人姓名不能为空!');
		return;
	}
	if ($('#loanName').val().match(/[^\u4e00-\u9fa5]/g))   {  
		art.dialog.alert('借款人姓名只能为中文!');
		return;
	}
	if($('#cerNum').val()==''){
		art.dialog.alert('证件号码不能为空!');
		return;
	}
	//var  re=/^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
	if($('#cerType').val()=='0'){
		var t = checkIdcard($('#cerNum').val());
		if(!t){
			return;
		}
		/*if (!re.test($('#cerNum').val()))   {  
			   alert("身份证号格式不正确！");  
			return false;  
		}*/
	}
	  
	if($('#loanCode').val()==''){
		art.dialog.alert('借款编号不能为空!');
		return;
	}
	var je = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
	if($('#initMoney').val()==''){
		art.dialog.alert('初始借款金额不能为空!');
		return;
	}
	if (!je.test($('#initMoney').val()))   {  
		art.dialog.alert("初始借款金额不正确！");  
		return false;  
	}
	if($('#manageMoney').val()==''){
		art.dialog.alert('月账户管理费不能为空!');
		return;
	}
	if (!je.test($('#manageMoney').val()))   {  
		art.dialog.alert("月账户管理费金额不正确！");  
		return false;  
	}
	if($('#repaymentDate').val()==''){
		art.dialog.alert('还款期限不能为空!');
		return;
	}
	if($('#initLoanDate').val()==''){
		art.dialog.alert('初始借款时间不能为空!');
		return;
	}
	if($('#startDate').val()==''){
		art.dialog.alert('起始还款日期不能为空!');
		return;
	}
	if($('#initLoanDate').val()>=$('#startDate').val()){
		art.dialog.alert('起始还款日期不能在初始借款时间之前!');
		return;
	}
	/*if($('#interestRate').val()!=''){
		var re = /(^1?\d$|^2[0-4]$)|(^1?\d\.\d$)|(^2[0-3]\.\d$)|(^-\d+(\.\d)?$)/;  
		if(!re.test($('#interestRate').val())){
			art.dialog.alert('借款利率为10以内的整数或小数!');
			return;
		}
	}*/
	if($('#interestRate').val()==''){
		art.dialog.alert('借款利率不能为空!');
		return;
	}
	var ra = parseFloat($('#interestRate').val());
	if(isNaN(ra)){
		art.dialog.alert('借款利率不能大于10!');
		return;
	}
	if(ra>10){
		art.dialog.alert('借款利率不能大于10!');
		return;
	}
	$('#interestRate').val(ra)
	$('#inputForm').submit();
}