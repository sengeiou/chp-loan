var credit={};

credit.format=function(){
	var i = 0;
	var j=0;
	$(":input[class='creditId']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].id');
		i++;
	});
	i=0;
	$("input[name$='.dictMortgageType']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].dictMortgageType');
		j++;
		if(j%2==0){
		  i++;
		}
	});
	i=0;
	$(":input[name$='.creditMortgageGoods']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].creditMortgageGoods');
		i++;
	});
	i=0;
	$(":input[name$='.orgCode']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].orgCode');
		i++;
	});
	i=0;
	$(":input[name$='.creditLoanLimit']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].creditLoanLimit');
		i++;
	});
	i=0;
	$(":input[name$='.creditMonthsAmount']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].creditMonthsAmount');
		i++;
	});
	i=0;
	$(":input[name$='.creditLoanBlance']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].creditLoanBlance');
		i++;
	});
	i=0;
	$(":input[name$='.creditCardNum']").each(function(){
		$(this).attr('name','loanCreditInfoList['+i+'].creditCardNum');
		i++;
	});
};
// 动态设置抵押物品的必填性。如果 抵押(0)则必填 否则(未抵押【1】)非必填
credit.required = function(){
	var tabLength=$('#loanCreditArea tr').length;
	var total = parseInt(tabLength)-1;
	var i = 0;
	var j = 0;
	for(var m = 0;m<total;m++){
		var radioVal = $("input[name='loanCreditInfoList["+m+"].dictMortgageType']:checked").val();
	    if(radioVal=='0'){
			$(":input[name='loanCreditInfoList["+m+"].creditMortgageGoods']").addClass("required");
	    }else if(radioVal=='1'){
	    	$(":input[name='loanCreditInfoList["+m+"].creditMortgageGoods']").removeClass("required");
	    }
	}
};