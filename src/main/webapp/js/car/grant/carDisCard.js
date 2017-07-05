var disCard = {};
var disCardArea = {};
//备注校验
jQuery.validator.addMethod("remarkValidate", function() {
	var isOK = true;
	// 获得选中的退回原因
	var dictBackMestype=$("#reason").attr("selected","selected").val();
	//备注
	var remark=$("#remark").val();
	if(dictBackMestype == '9' && remark == ''){
		isOK = false;
	}
    return isOK;
}, "退回原因为 其他 时请填写备注！");
/**
 * 分配卡号JS处理
 */
$(document).ready(function(){
	var checkVal="";
	var CarLoanFlowQueryView = $("#disCardForm").serialize();
	// 批量处理，批量进行分配卡号
	$("#disCardBtn").click(function(){
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			 var flag=confirm("确认对所有待分配卡号客户进行分配？");
	           if(flag){
	        	   CarLoanFlowQueryView+="&checkVal="+checkVal;
	        	   window.location.href=ctx +"/car/grant/disCard/disCardJump?"+CarLoanFlowQueryView;
	           }else{
	        	   return;
	           }
	          				
			}else{
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
            		if(checkVal!="")
            		{
            			checkVal+=","+$(this).val();
            		}else{
            			checkVal=$(this).val();
            		}
            	});
				CarLoanFlowQueryView+="&checkVal="+checkVal;
				 window.location.href=ctx +"/car/grant/disCard/disCardJump?"+CarLoanFlowQueryView;
				
				
			}
	});
	
	//伸缩
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
	
	// 金额精确到小数点后2位
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
	
	//点击全选反选选择框
	$("#checkAll").click(function(){
		var totalGrantMoney=0.00;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			totalGrantMoney+=parseFloat($("#hiddenTotalGrant").val());
		}
		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
	});
	
	//计算金额
	$(":input[name='checkBoxItem']").click(function(){
		var totalGrantMoney=0.00;
			$(":input[name='checkBoxItem']:checked").each(function(){
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
        	});
			if ($(":input[name='checkBoxItem']:checked").length==0) {
				totalGrantMoney+=parseFloat($("#hiddenTotalGrant").val());
			}
			$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));

	});
	
	//点击分配
	$(":input[name='dealBtn']").each(function(){
		$(this).click(function(){
			var checkVal=$(this).val();
			window.location.href=ctx +"/car/grant/disCard/disCardJump?checkVal="+checkVal;
		
	});
	});
	
	// 分配卡号导出excel
	$("#expDisCardBtn").click(function(){
		
		var idVal = "";
		var CarLoanFlowQueryView = $("#disCardForm").serialize();
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			 var flag=confirm("确认对所有待分配卡号数据导出？");
	           if(flag){
	        	   CarLoanFlowQueryView+="&idVal="+idVal;
	        	   window.location.href=ctx+"/car/grant/grantSure/expDisCardExl?"+CarLoanFlowQueryView;
	           }else{
	        	   return;
	           }
	          				
			}else{
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).val();
        		}else{
        			idVal=$(this).val();
        		}
        	});
			CarLoanFlowQueryView+="&idVal="+idVal;
    		window.location.href=ctx+"/car/grant/grantSure/expDisCardExl?"+CarLoanFlowQueryView;
		}
		
	});
	
    // 退回确定 分配卡号退回
	$("#backSure").click(function(){
		
		// 获得选中的退回原因，退回到合同审核，获得的为name
		var dictBackMestype=$("#reason").attr("selected","selected").val();
		//备注
		var remark=$("#remark").val();
		var applyId = $("#applyId").val();
		var loanCode = $("#loanCode").val();
		var contractCode = $("#contractCode").val();
		var carLoanWorkQueues = 'HJ_CAR_LOAN_BALANCE_COMMISSIONER';
		
		var Tflag =$("#reasonValidate").validate({
			onclick: true, 
			rules:{
			
				consLoanRemarks:{
					maxlength:200,
					remarkValidate:true
				},
	
		},
			messages : {

				consLoanRemarks:{
					maxlength:"备注200字以内"
				},
		
			}
}).form();
		
		if(Tflag){
			$("#back_div").hide();
			$.ajax({
				type : 'post',
				url : ctx + '/car/grant/grantSure/backTo',
				data : {
					'applyId':applyId,
					'loanCode':loanCode,
					'contractCode':contractCode,
					'dictBackMestype':dictBackMestype,
					'remark':remark,
					'carLoanWorkQueues':carLoanWorkQueues
					
				
				},
				success : function(data) {
					if(data!=null){
						alert('退回成功!');
						 windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/balanceCommissioner');
					}else{
						alert('退回失败!');
						 windowLocationHref(ctx+'/car/carLoanWorkItems/fetchTaskItems/balanceCommissioner');
					}
				}
			});
		}
	
	});
	
	// 每次点击退回，将流程信息放到form表单中
	$(":input[name='back']").each(function(){
		$(this).bind('click',function(){
			$('#applyId').val($(this).attr('applyId'));
			$('#contractCode').val($(this).attr('contractCode'));
			$('#loanCode').val($(this).attr('loanCode'));

		});
	});
	
	
});

/**
 * 省市区联动  分配卡号专用
 */
disCard.initCity = function(provinceId,cityId,districtId){
	$("#"+provinceId).change(function() {
		 var provinceCode = $("#"+provinceId+" option:selected").attr("areaCode");
		 var areaName =$("#cityCode").attr("value");
		 if(provinceCode=="-1" || provinceCode==""){
			 $("#"+cityId).empty();
			 $("#"+cityId).select2().empty();
			 $("#"+cityId).append("<option value=''>请选择</option>");
			 $("#"+cityId).trigger("change");
			 $("#"+districtId).empty();
			 $("#"+districtId).append("<option value=''>请选择</option>");
			 $("#"+districtId).trigger("change");
		 }else{
		     $.ajax({
          		type : "POST",
          		url : ctx + "/common/cityinfo/asynLoadCity",
          		data: {provinceId: provinceCode},
          		async: false,
          		success : function(data) {
          			
          			var resultObj = eval("("+data+")");
          		    $("#"+cityId).empty();
          		    $("#"+cityId).select2().empty();
          		    $("#"+cityId).append("<option value=''>请选择</option>");
	                $.each(resultObj,function(i,item){
	                	if(areaName==item.areaName){
	                		
	                        $("#"+cityId).append("<option selected='selected' value='"+item.areaName+"' areaCode='"+item.areaCode+ "'>"+item.areaName+"</option>");
	                	}else{
	                		$("#"+cityId).append("<option value='"+item.areaName+"' areaCode='"+item.areaCode+ "'>"+item.areaName+"</option>");	
	                	}
	                });
	                $("#"+cityId).trigger("change");
	                $("#"+cityId).attr("disabled", false);
          		}
          	});
		 }
	});
if(districtId!=null && districtId!='' && districtId!=undefined){
 	$("#"+cityId).change(function() {
		 var cityCode = $("#"+cityId+" option:selected").val();
		 var districtCode = $("#"+districtId+" option:selected").val();
		 if(cityCode=="-1" || cityCode==''){
			 $("#"+districtId).empty();
			 $("#"+districtId).select2().empty();
			 $("#"+districtId).append("<option value=''>请选择</option>");
			 $("#"+districtId).trigger("change");
		 }else{
    	     $.ajax({
          		type : "POST",
          		url: ctx + "/common/cityinfo/asynLoadDistrict",
          		data: {cityId: cityCode},
          		async: false,
          		success : function(data) {
          			var resultObj = eval("("+data+")");
          		    $("#"+districtId).empty();
          		    $("#"+districtId).select2().empty();
          		    $("#"+districtId).append("<option value=''>请选择</option>");
	                $.each(resultObj,function(i,item){
	                	if(districtCode == item.areaCode){
	                		
	                        $("#"+districtId).append("<option selected='selected' value="+item.areaCode+">"+item.areaName+"</option>");
	                	}else{
	                		$("#"+districtId).append("<option value="+item.areaCode+">"+item.areaName+"</option>");	
	                	}
	                });
	                $("#"+districtId).trigger("change");
	                $("#"+districtId).attr("disabled", false);
          		}
            });
		}
  });
 }
};

/**
 * 省市选择  分配卡号专用
 */
disCardArea.initCity = function(provinceId, cityId, districtId, areaName, distCode) {
	var provinceCode = $("#" + provinceId + " option:selected").attr("areaCode");
	if (provinceCode == "-1" || provinceCode == "") {
		$("#" + cityId).empty();
		$("#" + cityId).append("<option value=''>请选择</option>");
		$("#" + cityId).trigger("change");
		$("#" + districtId).empty();
		$("#" + districtId).append("<option value=''>请选择</option>");
		$("#" + districtId).trigger("change");
	} else {
		$.ajax({
			type : "POST",
			url : ctx + "/common/cityinfo/asynLoadCity",
			data : {
				provinceId : provinceCode
			},
			async: false,
			success : function(data) {
				var resultObj = eval("(" + data + ")");
				$("#" + cityId).empty();
				$("#" + cityId).append("<option value=''>请选择</option>");
				$.each(resultObj, function(i, item) {
					if (areaName == item.areaName) {
						$("#" + cityId).append("<option selected='selected' value='" + item.areaName+ "' areaCode='"+item.areaCode+ "'>" + item.areaName + "</option>");
					} else {
						$("#" + cityId).append("<option value='" + item.areaName + "' areaCode='"+item.areaCode+ "'>" + item.areaName+ "</option>");
					}
				});
				$("#" + cityId).trigger("change");
				$("#" + cityId).attr("disabled", false);
			}
		});
	}
	if (districtId != null && districtId != '' && districtId != undefined) {
		/*var cityCode = $("#" + cityId + " option:selected").val();*/
		if (cityCode == "-1" || cityCode == ""|| cityCode == undefined) {
			$("#" + districtId).empty();
			$("#" + districtId).append("<option value=''>请选择</option>");
			$("#" + districtId).trigger("change");
		} else {
			$("#" + districtId).empty();
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadDistrict",
				data : {
					cityId : cityCode
				},
				async: false,
				success : function(data) {
					var resultObj = eval("(" + data+ ")");
					$("#" + districtId).empty();
					$("#" + districtId).append("<option value=''>请选择</option>");
					$.each(resultObj,function(i,item) {
						       	if (distCode == item.areaCode) {
							    	$("#"+ districtId).append("<option selected='selected' value="+ item.areaCode+ ">"+ item.areaName+ "</option>");
								} else {
									$("#"+ districtId).append("<option value="+ item.areaCode+ ">"+ item.areaName+ "</option>");
								}

							});
					$("#" + districtId).trigger("change");
					$("#" + districtId).attr("disabled", false);
				}
			});
		}		 
	}
};