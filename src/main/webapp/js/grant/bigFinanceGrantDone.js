
$(document).ready(function(){
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
	 $(':input','#grantForm')
	  .not(':button, :reset,:hidden,:submit')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
		
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});
	
	// 导出excel,默认导出全部
	$("#daoBtn").click(function(){
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="") {
        			idVal+=","+$(this).attr("loanCode");
        		}else{
        			idVal=$(this).attr("loanCode");
        		}
        	});
		}
		window.location.href=ctx+"/channel/bigfinance/grantDone/grantDoneExl?"+$("#grantForm").serialize()+"&idVal="+idVal;
	});
	var $checkBoxItem = $(":input[name='checkBoxItem']");
	var num = 0,money = 0.00;
	$checkBoxItem.each(function(){
		var totalGrantMoney = parseFloat($(this).attr("contractMoney"));
			money += totalGrantMoney;
			num ++;
		$("#totalGrantMoney").text(fmoney(money,2));
		$("#totalNum").text(num);
	});
	// 点击全选
	$("#checkAll").click(function () {
		var cumulativeLoan = 0.00,
		totalNum = 0;
		$checkBoxItem.prop("checked",this.checked);
		//全选按钮选中
		if ($(this).is(":checked")) {
			cumulativeLoan = money;
			totalNum = num;
		} 
		$("#hiddenGrant").val(cumulativeLoan);
		$("#hiddenNum").val(totalNum);
		$("#totalGrantMoney").text(fmoney(money,2));
		$("#totalNum").text(num);
	});
	$checkBoxItem.click(function(){
		var cumulativeLoan  = parseFloat($("#hiddenGrant").val());
		var totalNum = parseFloat($("#hiddenNum").val(),10);
		var hiddeNum = 0,hiddenMoney = 0.00;
		if ($(this).is(":checked")) {
			hiddenMoney = cumulativeLoan += parseFloat($(this).attr("contractMoney"));
			hiddeNum = totalNum += 1;
		} else {
			if ($("input[name='checkBoxItem']:checked").length == 0){
				cumulativeLoan = money;
				totalNum = num;
			} else {
				hiddenMoney = cumulativeLoan -= parseFloat($(this).attr("contractMoney"));
				hiddeNum = totalNum -= 1 ;
			}
		}
		$("#checkAll").prop("checked",($("input[name='checkBoxItem']").length == $("input[name='checkBoxItem']:checked").length ? true : false));
		
		$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
		$("#hiddenGrant").val(hiddenMoney);
		$("#totalNum").text(totalNum);
		$("#hiddenNum").val(hiddeNum);
	});
	// 格式化，保留两个小数点
    function fmoney(s, n) {  
        n = n > 0 && n <= 20 ? n : 2;  
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
        var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
        t = "";  
        for (i = 0; i < l.length; i++) {  
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
        }  
        return t.split("").reverse().join("") + "." + r;  
    }  
})

	
