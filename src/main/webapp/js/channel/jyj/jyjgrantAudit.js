$(document).ready(function(){
	$("input[name='grantAuditResult']").get(0).checked=true; 
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
			$(':input','#grantAuditForm')
			 .not(':button,:submit, :reset,:hidden')
			 .val('')
			  .removeAttr('checked')
			  .removeAttr('selected');
			$('#storeOrgId').val('');
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src=ctxStatic + '/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			if(document.getElementById("T3")){
			  document.getElementById("T3").style.display='';
		    }
			document.getElementById("T4").style.display='';
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			if(document.getElementById("T3")){
			   document.getElementById("T3").style.display='none';
			}
			document.getElementById("T4").style.display='none';
		}
	});
	
	//单独点击复选框，统计放款笔数和放款金额
	$(":input[name='checkBoxItem']").click(function() {
		// 记录总金额，当length为0时，进行总金额的处理
		var totalGrantMoney = $("#hiddenTotalGrant").val();
		var totalNum = $("#hiddenNum").val();
		// 获得单个单子的金额
		var deductAmount = 0.00;
		if($(this).attr("lendingMoney")!=''){
			deductAmount = parseFloat($(this).attr("lendingMoney"));
		}
		var num = 1;
		if ($(this).attr('checked') == 'checked' || $(this).attr('checked')) {
			var hiddenNum = parseFloat($("#numHid").val()) + num;
			var hiddenDeduct = 0.00;
			if($("#hiddenTotalGrant").val()!=''){
				hiddenDeduct = parseFloat($("#hiddenTotalGrant").val()) + deductAmount;
			}
			
			$('#totalItem').text(hiddenNum);
			$("#numHid").val(hiddenNum);
			$("#grantMoneyHid").val(hiddenDeduct);
			$('#grantMoneyText').text(fmoney(hiddenDeduct, 2));
		} else {
			$('#checkAll').removeAttr("checked");
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				$('#totalItem').text(totalNum);
				$("#grantMoneyText").text(fmoney(totalGrantMoney, 2));
				$('#grantMoneyHid').val(0.00);
				$('#numHid').val(0);
			} else {
				var hiddenDeduct = parseFloat($("#grantMoneyHid").val()) - deductAmount;
				$('#totalItem').text(parseFloat($("#numHid").val()) - num);
				$("#numHid").val(parseFloat($("#numHid").val()) - num);
				$("#grantMoneyText").text(fmoney(hiddenDeduct, 2));
				$("#grantMoneyHid").val(hiddenDeduct);
			}
		}
	});
	
	// 手动确认按钮触发
	$("#resultSure").click(function(){
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("contractCode");
        		}else{
        			idVal=$(this).attr("contractCode");
        		}
        	});
		}
		$("#contractCode").val(idVal);
	});
	
	// 退回原因显示框的显示
	$("input:radio[name='grantAuditResult']").click(function(){
		var elements =  document.getElementsByName("grantAuditResult");
		for(var i = 0 ; i < elements.length ;i++){
			var clickEle= elements[i].value;
			if(elements[i].checked){
				if("0"==clickEle){
				document.getElementById("backReason").style.display = "inline";
			    }
			   else{
				 document.getElementById("backReason").style.display = "none";
			   } 
			}
		}
	});
	
	// 点击手动确认按钮点击确定
	$("#auditSureBtn").click(function(){
		var checkType = $("input:radio[name='grantAuditResult']:checked").val();
		var checkVal = $("#backResult").val();  
		if(checkType == null){
			top.$.jBox.tip("请确认结果！");
			return false;
		}else if(checkVal=="" && checkType=='0'){
			top.$.jBox.tip("失败原因不能为空！");
			return false;
		}else{
			var payback = $("#grantAuditForm").serialize()+"&grantAuditResult="+checkType+"&emergencyRemark="+checkVal;
			$.ajax({
				type: 'post',
				url: ctx +'/channel/jyj/grantAudit/updateEmergency',
				data: payback,
				success:function(data){
					top.$.jBox.tip(data);
					window.location.href = ctx +'/channel/jyj/grantAudit/grantAuditItem';
				},
				error:function(data){
					top.$.jBox.tip(data);
				}
			});
		}
	});
	
	// 导出excel,
	$("#daoBtn").click(function(){
		var loanGrant = $("#grantAuditForm").serialize();
		var idVal = "";
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
		}
		window.location.href=ctx+"/channel/jyj/grantAudit/grantAuditExl?idVal="+idVal+"&"+loanGrant;
	});
	
});

//格式化，保留两个小数点
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