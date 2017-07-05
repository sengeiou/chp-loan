
//全选和反选复选框   划扣金额和划扣笔数的显示
$(function(){
	
	 var url="/borrow/payback/deductPlantLimit/selectOrgList";
	$("#selectStoresBtn").click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"选择门店", 
					width:660, 
					height:450, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var iframe = this.iframe.contentWindow;
			        	var str="";
			        	var strname="";
			        	$("input[name='orgIds']:checked",iframe.document).each(function(){ 
			        	    if($(this).attr("checked")){
			        	        str += $(this).attr("id")+",";
			        	        strname += $(this).attr("sname")+",";
			        	    }
			        	});
			        	
			        /*	if(str.indexOf("all") > 0 ){
			        		
			        		alert("222222")
			        		art.dialog.confirm("包含所有门店，确认操作 ！",function(){
				        		str = str.replace(/,$/g,"");
					        	strname = strname.replace(/,$/g,"");
					        	
					        	$("#txtStore").val(strname);
					        	$("#hdStore").val(str);
				       		});
			        	}else{*/
			        		str = str.replace(/,$/g,"");
				        	strname = strname.replace(/,$/g,"");
				        	
				        	$("#txtStore").val(strname);
				        	$("#hdStore").val(str);
			    /*    }*/
			        
			   }
	   },false);
    })
    
    $("#submitButton").click(function(){
		$form = $('#addOrEdit');
		var notEnoughProportionId = $("#notEnoughProportionId").val(); // 余额不足比例
		var failureRateId = $("#failureRateId").val(); // 失败率
		var failureBaseId = $("#baseNumberId").val(); // 基数
        var  orgid = $("#hdStore").val(); //门店
		if(notEnoughProportionId){
			if(notEnoughProportionId > 100 ){
				art.dialog.alert("余额不足比例不能大于100！");
				return false;
			}
			if(notEnoughProportionId < 0 ){
				art.dialog.alert("余额不足比例不能小于0！");
				return false;
			}
			
			var notEnoughProportionIds  = notEnoughProportionId.split(".");
			if(notEnoughProportionIds[1]){
				if(notEnoughProportionIds[1].length>2){
					art.dialog.alert("余额不足比例只能输入俩位小数！");
					return false;
				}
			}
		}
		
		if(failureRateId){
			if(failureRateId > 100 ){
				art.dialog.alert("失败率不能大于100！");
				return false;
			}
			if(failureRateId < 0 ){
				art.dialog.alert("失败率不能小于0！");
				return false;
			}
			var failureRateIds  = failureRateId.split(".");
			if(failureRateIds[1]){
				if(failureRateIds[1].length>2){
					art.dialog.alert("失败率只能输入俩位小数！");
					return false;
				}
			}
		}
		
		
		
		if(failureBaseId){
			
			if(!isInteger(failureBaseId)){
				art.dialog.alert("基数为整数，请输入整数！");
				return false;
			}
			
			if(failureBaseId>999999999){
				art.dialog.alert(" 基数不能大于999999999！");
				return false;
			}
			
			if(failureBaseId < 0){
				art.dialog.alert("基数不能小于0！");
				return false;
			}
		}
		if(!orgid){
			art.dialog.alert("适用门店不能为空！");
			return false;
		}
	   $form.submit();
	})
	
	
	$("#delete").live("click",function(){
		var url = $(this).attr("url");
		art.dialog.confirm("您确定要删除该条数据吗？",function() {
				window.location.href = url;
		})
	});
	
	
	
	 var queryUrl="/borrow/payback/deductPlantLimit/selectOrgList";
		$("#querySelectStoresBtn").click(function(){
			art.dialog.open(ctx + queryUrl, 
			{
						title:"选择门店", 
						width:660, 
						height:450, 
						lock:true,
						opacity: .1,
				        okVal: '确定', 
				        ok:function(){
				        	var iframe = this.iframe.contentWindow;
				        	var str="";
				        	var strname="";
				        	$("input[name='orgIds']:checked",iframe.document).each(function(){ 
				        	    if($(this).attr("checked")){
				        	        str += $(this).attr("id")+",";
				        	        strname += $(this).attr("sname")+",";
				        	    }
				        	});
				        	
				        /*	if(str.indexOf("all") > 0 ){
				        		
				        		alert("222222")
				        		art.dialog.confirm("包含所有门店，确认操作 ！",function(){
					        		str = str.replace(/,$/g,"");
						        	strname = strname.replace(/,$/g,"");
						        	
						        	$("#txtStore").val(strname);
						        	$("#hdStore").val(str);
					       		});
				        	}else{*/
				        		str = str.replace(/,$/g,"");
					        	strname = strname.replace(/,$/g,"");
					        	
					        	$("#queryTxtStore").val(strname);
					        	$("#queryHdStore").val(str);
				    /*    }*/
				        
				   }
		   },false);
	    })
	    
	    
	  //清除按钮绑定事件
		$("#cleBtn").click(function(){
			$(":input").val("");
			$("#CenterapplyPayForm").submit();
		});
	 

});

function isInteger(obj) {
	if(obj.indexOf(".") != -1){
		  return false;
		}else{
		  return true;
		}
}
	
