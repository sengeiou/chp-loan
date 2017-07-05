/**
 * 配置js
 */
jQuery(document).ready(function($){
	//var iframe = getCurrentIframeContents();
	$("#submitButton").click(function(){
		$form = $('#addOrEdit');
		var notEnoughProportionId = $("#notEnoughProportionId").val(); // 余额不足比例
		var notEnoughBaseId = $("#notEnoughBaseId").val(); // 余额不足基数
		var failureRateId = $("#failureRateId").val(); // 失败率
		var failureBaseId = $("#failureBaseId").val(); // 失败基数
		var failureNumberId = $("#failureNumberId").val(); // 失败笔数
		
		var plantCode = $("#plantCodeId").val();
		
		if(!plantCode){
			art.dialog.alert("平台不能为空！");
			return false;
		}
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
		
		if(notEnoughBaseId){
			if(!isInteger(notEnoughBaseId)){
				art.dialog.alert(" 余额不足基数为整数，请输入整数！");
				return false;
			}
			if(notEnoughBaseId>999999999){
				art.dialog.alert(" 余额不足基数不能大于999999999！");
				return false;
			}
			
			if(notEnoughBaseId < 0){
				art.dialog.alert(" 余额不足基数不能小于0！");
				return false;
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
					art.dialog.alert("失败率不能只能输入俩位小数！");
					return false;
				}
			}
		}
		
		if(failureBaseId){
			
			if(!isInteger(failureBaseId)){
				art.dialog.alert("失败基数为整数，请输入整数！");
				return false;
			}
			
			if(failureBaseId>999999999){
				art.dialog.alert(" 失败基数不能大于999999999！");
				return false;
			}
			
			if(failureBaseId < 0){
				art.dialog.alert(" 失败基数不能小于0！");
				return false;
			}
		}
		
		if(failureNumberId){
			
			if(!isInteger(failureNumberId)){
				art.dialog.alert("失败笔数为整数，请输入整数！");
				return false;
			}
			
			if(failureNumberId>999999999){
				art.dialog.alert(" 失败笔数不能大于999999999！");
				return false;
			}
			
			if(failureNumberId < 0){
				art.dialog.alert(" 失败笔数不能小于0！");
				return false;
			}
		}

		//通联平台的时候进行判断
		if($("#plantCodeId").val() == 3){
			var moneySymbol1Id = $("#moneySymbol1Id").val();
			var deductMoney1Id = $("#deductMoney1Id").val();
			var deductType1Id = $("#deductType1Id").val();
			
			var moneySymbol2Id = $("#moneySymbol2Id").val();
			var deductMoney2Id = $("#deductMoney2Id").val();
			var deductType2Id = $("#deductType2Id").val();
			
			if(!moneySymbol1Id){
				art.dialog.alert("金额条件1大于小于不能为空！");
				return false;
			}
			if(!deductMoney1Id){
				art.dialog.alert("金额条件1划扣金额不能为空！");
				return false;
			}

			if(deductMoney1Id){
				if(deductMoney1Id > 999999999 ){
					art.dialog.alert("金额条件1划扣金额不能大于999999999！");
					return false;
				}
				if(deductMoney1Id < 0 ){
					art.dialog.alert("金额条件1划扣金额不能小于小于0！");
					return false;
				}
				
				var deductMoney1s  = deductMoney1Id.split(".");
				if(deductMoney1s[1]){
					if(deductMoney1s[1].length>2){
						art.dialog.alert("金额条件1划扣金额只能输入俩位小数！");
						return false;
					}
				}
			}
			
			if(!deductType1Id){
				art.dialog.alert("金额条件1接口不能为空！");
				return false;
			}
			
			if(!moneySymbol2Id){
				art.dialog.alert("金额条件2大于小于不能为空！");
				return false;
			}
			if(!deductMoney2Id){
				art.dialog.alert("金额条件2划扣金额不能为空！");
				return false;
			}
			
			if(deductMoney2Id){
				if(deductMoney2Id > 999999999 ){
					art.dialog.alert("金额条件2划扣金额不能大于999999999！");
					return false;
				}
				if(deductMoney2Id < 0 ){
					art.dialog.alert("金额条件2划扣金额不能小于小于0！");
					return false;
				}
				var deductMoney2s  = deductMoney2Id.split(".");
				if(deductMoney2s[1]){
					if(deductMoney2s[1].length>2){
						art.dialog.alert("金额条件2划扣金额只能输入俩位小数！");
						return false;
					}
				}
			}
			
			if(!deductType2Id){
				art.dialog.alert("金额条件2接口不能为空！");
				return false;
			}
	        
		    if(deductMoney1Id != deductMoney2Id){
				art.dialog.alert("条件1与条件2划扣金额应该相等！");
				return false; 
			 }
		    
		     if(moneySymbol2Id == moneySymbol1Id){
				art.dialog.alert("条件1与条件2大于小于不能相同！");
				return false; 
			 }
		     if(deductType2Id == deductType1Id){
					art.dialog.alert("条件1与条件2接口不能相同！");
					return false; 
			 }
		}
		
		     $form.submit();
	})
	
	if($("#plantCodeId").val() == 3){
		$("#template").show();
	}
	
});

function go(){
	window.location.href = ctx + "/borrow/payback/deductPlantLimit/queryPage";
}


function checkPlatform(){
	var bankCode = $("select[name='bankCode']").val();
	if(bankCode){
		var isConcentrate =  $("input[name='isConcentrate']:checked").val()
		if(isConcentrate){
			$.ajax({  
				type : "POST",
			    data:{"bankCode":bankCode,
			    	   "isConcentrate":isConcentrate},
				url :ctx+"/borrow/payback/plantskipordernew/checkPlatform",
				datatype : "json",
				success : function(data){
					if(data == 'false'){
						art.dialog.alert("你选中的银行,已经存在");	
						$("select[name='bankCode']").val("");
						$("select[name='bankCode']").change();
						 return;
					}					 
				}
			});
		}
	}
}

function plantChage(obj){
	var oldplantcode = $("#plantId").val();
	if(obj.value){
		if(obj.value != oldplantcode ){
			$.ajax({  
				type : "POST",
				async: false,
			    data:{"plantCode":obj.value},
				url :ctx+"/borrow/payback/deductPlantLimit/querydataByPlant",
				datatype : "json",
				success : function(data){
					if(data == 'false'){
						art.dialog.alert("你选中平台,已经存在");	
						$(obj).val("");
						$(obj).change();
						 return;
					}					 
				}
			});
		}
	 }
	
	if(obj.value == 3){
		$("#template").show();
	}else{
		$("#template").find(":input").val("");
		$("#template").hide();
	}
}

function isInteger(obj) {
	if(obj.indexOf(".") != -1){
		  return false;
		}else{
		  return true;
		}
}