$(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#inputForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 
	// 点击取消，关闭模式对话框
	$('#closeBtn').bind('click',function(){
		closeBtn();
	});
	
	// 点击确认
	$('#auditSure').bind('click',function(){
		
		var checkEle=$(":input[name='sort']:checked").val();
		var reason=$("#refuseReason").val();
		if(checkEle==""){
			art.dialog.alert("请选择审核结果");
			return;
		}else{
			if(checkEle=="审核拒绝")
			{
				if(reason==""){
					art.dialog.alert("请填写审核拒绝原因");
					return;
				}else{
					if(reason.length<=50)
					{
						auditOver(reason,'2');
					}else{
						art.dialog.alert("审核拒绝原因不能超过50个字!");
						return;
					}
				}
			}else{
				auditOver(reason,'1');
			}
		}
	});
	
	 $("input[name=sort]").click(function(){
		 isReasonShow();
	 });
	 
	function isReasonShow()
	{
		var checkEle=$(":input[name='sort']:checked").val();
		if(checkEle=="审核通过")
		{
			$("#ta").hide();
		}else{
			$("#ta").show();
		}
	} 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	// 点击页面跳转事件
	$(":input[name='jumpTo']").each(function(){
		$(this).click(function(){
			var refundId=$(this).val();
			var url=ctx + "/car/refund/refundAudit/refundAuditDo?checkVal="+refundId+"&cardNo="+$("#cardNo"+refundId).val();
			jump(url);
		
	});
	});
	
	function jump(url){
	    art.dialog.open(url, {  
			   title: '退款审核',
			   lock:true,
			   width:700,
			   height:350
			},false);  
	}
	
	function auditOver(reason,result){
		var ParamEx = "id="+$("#id").val()+"&result="+result+"&reason="+reason;
		$.ajax({
			type : 'post',
			url : ctx + '/car/refund/refundAudit/refundAuditSubmit',
			data : ParamEx,
			dataType : 'text',
			success : function(result) {
				if(result){
					art.dialog.alert("审核成功！");
					art.dialog.open.origin.location.href=ctx + "/car/refund/refundAudit/refundAuditJump";
				}else{
					art.dialog.alert("审核失败！");
				}
			},
			error : function() {
				art.dialog.alert('请求失败！');
			}
		});
	}
	
	function closeBtn(){
		art.dialog.close();
	}
	
	//点击全选反选选择框
	$("#checkAll").click(function(){
		var totalMoney = 0.00;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalMoney+=parseFloat($(this).attr("urgeMoeny"));
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			totalMoney+=parseFloat($("#deductsAmountHide").val());
		}
		$("#deductAmount").text(fmoney(totalMoney,2));
	});
	
	//计算金额
	$(":input[name='checkBoxItem']").click(function(){
		var totalMoney=0.00;
			$(":input[name='checkBoxItem']:checked").each(function(){
				totalMoney+=parseFloat($(this).attr("urgeMoeny"));
	    	});
			if ($(":input[name='checkBoxItem']:checked").length==0) {
				totalMoney+=parseFloat($("#deductsAmountHide").val());
			}
			$("#deductAmount").text(fmoney(totalMoney,2));
	
	});
});	

//金额精确到小数点后2位
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