var checkVal="";
$(document).ready(function(){
	// 点击清除，清除搜索页面中的数据
	$("#clearBtn").click(function(){		
		$(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$("select").trigger("change");
	});
	$('#searchBtn').bind('click',function(){
		
		var urgeMoenyStart=$("#urgeMoenyStart").val();
		var urgeMoenyEnd=$("#urgeMoenyEnd").val();

		if(urgeMoenyStart > urgeMoenyEnd ){
			art.dialog.alert("划扣开始金额不能大于结束金额！");
		}else{
			$('#deductsForm').submit(); 
		}
		
	 });

	// 查看划扣历史,未做
	$("#deductHistory").click(function(){
		var urgeId = $(this).attr("sid");
	});
	

	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
		
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
		
		}
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		var totalNum= 0 ;
		var totalGrantMoney=0.00;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalNum++;
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
			});

		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			totalNum = $("#num").val();
			totalGrantMoney+=parseFloat($("#hiddenTotalGrant").val());
		}
		$('#totalNum').text(totalNum);
		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
		
	});
	
	// 计算金额,
	$(":input[name='checkBoxItem']").click(function(){
		var totalGrantMoney=0.00;
		var totalNum= 0 ;
		$(":input[name='checkBoxItem']:checked").each(function(){
			totalNum++;
			totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
    	});
		if ($(":input[name='checkBoxItem']:checked").length==0) {
			totalGrantMoney+=parseFloat($("#hiddenTotalGrant").val());
			totalNum = $("#num").val();
		}
		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
		$('#totalNum').text(totalNum);
	});
	

	
	// 点击导出是记录催收服务费id
	$("#daoHBtn").click(function(){
		
		// 催收id
		var cid = "";
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
		 }else{
			 
				// 对选中的单子进行导出
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(cid!="")
         		{
         			cid+=","+$(this).attr("cid");
         		}else{
         			cid=$(this).attr("cid");
         		}
         	});
			 
		 }
		
		// 当日划扣标识
		var result = $("#result").val();
		// 搜索表单序列化
		var urgeServicesMoneyEx = $("#deductsForm").serialize();
		
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
			
			art.dialog.confirm("导出所有划扣失败、处理中(导出)数据?",function(){
				
				 $.ajax({
			 			type : 'post',
			 			url : ctx + '/car/grant/grantDeducts/isExportDeducts',
			 			
			 			data : {
			 				'cid':cid,
			 				'result':result,
			 				'urgeServicesMoneyEx':urgeServicesMoneyEx
			 			},
			 			dataType:'json',
			 			cache : false,
			 			success : function(result) {
			 				if (result == true) {
			 					$("#offLineDao").modal('show');
					
							}else{
								art.dialog.alert("请选择划扣失败、处理中(导出)数据！");
							}
			 			},
			 			error : function() {
			 				art.dialog.alert("请求异常！");
			 				
			 			}
			 		});
	    		
				});
		 }else{
			 
			 $.ajax({
		 			type : 'post',
		 			url : ctx + '/car/grant/grantDeducts/isExportDeducts',
		 			
		 			data : {
		 				'cid':cid,
		 				'result':result,
		 				'urgeServicesMoneyEx':urgeServicesMoneyEx
		 			},
		 			dataType:'json',
		 			cache : false,
		 			success : function(result) {
		 				if (result == true) {
		 					$("#offLineDao").modal('show');
				
						}else{
							art.dialog.alert("请选择划扣失败、处理中(导出)数据！");
						}
		 			},
		 			error : function() {
		 				art.dialog.alert("请求异常！");
		 				
		 			}
		 		});
			 		
		 }
		
	});
	
	// 导出  确定按钮
	$("#offLineBtn").click(function(){
		
		// 催收id
		var cid = "";
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
		 }else{
			 
				// 对选中的单子进行导出
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(cid!="")
         		{
         			cid+=","+$(this).attr("cid");
         		}else{
         			cid=$(this).attr("cid");
         		}
         	});
			 
		 }


		// 获得选中的平台
		var deductsType=$("#exportDeducts").attr("selected","selected").val();
		
		// 当日划扣标识
		var result = $("#result").val();
		// 搜索表单序列化
		var urgeMoney = $("#deductsForm").serialize();
		
		urgeMoney+="&cid="+cid+"&resultString="+result+"&deductsType="+deductsType;
		
		var Tflag =$("#exportDeductPlat").validate({
			onclick: true, 
			rules:{
		},
			messages : {
		
			}
}).form();
		if(Tflag){
			$('#offLineDao').modal('hide');
			$.ajax({
	 			type : 'post',
	 			url : ctx + '/car/grant/grantDeducts/isConditions',
	 			
	 			data : {
	 				'cid':cid,
	 				'result':result,
	 				'deductsType':deductsType
	 			},
	 			dataType:'json',
	 			cache : false,
	 			success : function(result) {
	 				if (result == true) {
	 					window.location.href=ctx+"/car/grant/grantDeducts/deductsExl?"+urgeMoney;
					}else{
						art.dialog.alert("没有符合条件数据");
					}
	 			},
	 			error : function() {
	 				art.dialog.alert("请求异常！");
	 				
	 			}
	 		});
		}
		

		
	});
	
	
	// 上传回执，控制弹出框的
	$("#uploadBoxBtn").click(function(){
		
		var returnUrl=$("#returnUrl").val();
		var result = $("#result").val();
		
		// 获得选中的平台
		var deductsType=$("#importDeducts").attr("selected","selected").val();
		// 文件对象
		var file=$("#fileid").val();
		if(deductsType == ""){
			showTip("请选择平台！");
			return false;
	    }
	   if(!validateXlsFileImport('fileid','file')){
		   return false;
	   }
		// 好易联
		if ("1" == deductsType) {
			//$("#uploadAuditForm").attr("action",  ctx + "/car/grant/grantDeducts/hylImportResult?returnUrl="+returnUrl+"&result="+result);
			//$("#uploadAuditForm").submit();
			var formData = new FormData($("#uploadAuditForm")[0]);
			if(validateXlsFileImport('fileid','file')){
				$.ajax({
					type : 'post',
					url : ctx + "/car/grant/grantDeducts/hylImportResult?returnUrl="+returnUrl+"&result="+result,
					data : formData,
					cache: false,
					processData: false,
					contentType: false,
					dataType : 'text',
					success : function(data) {
						if (data == 'true') {
							result = "bef";
							window.location.href = ctx + "/car/grant/grantDeducts/deductsInfo?result="+result+"&returnUrl="+"carPastGrantDeductsList";
						} else if (data == 'false') {
							art.dialog.alert("导入excel格式有误，请导入正确格式的数据");
						} else {
							
						}
					}
				});
			}
			return false;
		}
		// 中金
		if ("2" == deductsType) {
			//$("#uploadAuditForm").attr("action",  ctx + "/car/grant/grantDeducts/zjImportResult?returnUrl="+returnUrl+"&result="+result);
			//$("#uploadAuditForm").submit();
			var formData = new FormData($("#uploadAuditForm")[0]);
			if(validateXlsFileImport('fileid','file')){
				$.ajax({
					type : 'post',
					url : ctx + "/car/grant/grantDeducts/zjImportResult?returnUrl="+returnUrl+"&result="+result,
					data : formData,
					cache: false,
					processData: false,
					contentType: false,
					dataType : 'text',
					success : function(data) {
						if (data == 'true') {
							result = "bef";
							window.location.href = ctx + "/car/grant/grantDeducts/deductsInfo?result="+result+"&returnUrl="+"carPastGrantDeductsList";
						} else if (data == 'false') {
							art.dialog.alert("导入excel格式有误，请导入正确格式的数据");
						} else {
							
						}
					}
				});
			}
			return false;
		}
		// 通联
		if ("3" == deductsType) {
			//$("#uploadAuditForm").attr("action",  ctx + "/car/grant/grantDeducts/tlImportResult?returnUrl="+returnUrl+"&result="+result);
			//$("#uploadAuditForm").submit();
			var formData = new FormData($("#uploadAuditForm")[0]);
			if(validateXlsFileImport('fileid','file')){
				$.ajax({
					type : 'post',
					url : ctx + "/car/grant/grantDeducts/tlImportResult?returnUrl="+returnUrl+"&result="+result,
					data : formData,
					cache: false,
					processData: false,
					contentType: false,
					dataType : 'text',
					success : function(data) {
						if (data == 'true') {
							result = "bef";
							window.location.href = ctx + "/car/grant/grantDeducts/deductsInfo?result="+result+"&returnUrl="+"carPastGrantDeductsList";
						} else if (data == 'false') {
							art.dialog.alert("导入excel格式有误，请导入正确格式的数据");
						} else {
							
						}
					}
				});
			}
			return false;
		}
	});
	
	//手动确认弹框
	$("#sendZ").click(function(){
		$("#s2id_manualDeducts .select2-chosen").text("请选择");
		$("#manualDeducts").val("");
		var contractCode = ""; 
		if($(":input[name='checkBoxItem']:checked").length==0){
			showTip("请先请选择划扣平台！");
		}else{
			$("#manualBtn").modal('show');
		}
	
	});
	
	// 手动确认   确定按钮
	
	$("#manualSureBtn").click(function(){

		// 获得选中的平台
		var deductsType=$("#manualDeducts").attr("selected","selected").val();
		// 获得划扣结果
		var splitBackResult=$(":input[name='sort']:checked").val();
		// 获得失败原因
		var splitFailResult=$("#remark").val();
		// 当日或以往划扣标识
		var result = $("#result").val();
		
		// 催收id
		var cid = "";
		
		if($(":input[name='checkBoxItem']:checked").length==0){
			
		 }else{
				// 对选中的单子进行导出
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(cid!="")
         		{
         			cid+=","+$(this).attr("cid");
         		}else{
         			cid=$(this).attr("cid");
         		}
         	});
			 
		 }
		
		var Tflag =$("#reasonValidate").validate({
			onclick: true, 
			rules:{
			
				consLoanRemarks:{
					maxlength:200
					
				},
	
		},
			messages : {

				consLoanRemarks:{
					maxlength:"失败原因200字以内"
				},
		
			}
}).form();
		
		if(Tflag){
			 $.ajax({
		 			type : 'post',
		 			url : ctx + '/car/grant/grantDeducts/manualSure',
		 			data : {
		 				'cid':cid,
		 				'deductsType':deductsType,
		 				'splitBackResult':splitBackResult,
		 				'splitFailResult':splitFailResult,
		 				'result':result
		 			},
		 			dataType:'json',
		 			cache : false,
		 			success : function(result) {
		 					if (result == true) {
		 					
		 					art.dialog.alert("操作成功！",function(){
		 						window.location.reload();
							});
		 					
						}else{
							art.dialog.alert("操作失败！",function(){
		 						window.location.reload();
							});
							 
						}
		 
		 		    	
		 			},
		 			error : function() {
		 				art.dialog.alert("请求异常！",function(){
	 						window.location.reload();
						});
		 			}	
		 		});
		}
		

		
	
		
	});
	// 发送平台，线上划扣
	$("#sendF").click(function(){
	var checkVal = "";
	$("#s2id_onlineDeducts .select2-chosen").text("请选择");
	$("#onlineDeducts").val("");
	if($(":input[name='checkBoxItem']:checked").length==0){
			 var flag=confirm("确认对所有划扣失败数据划扣？");
	           if(flag){
	        		$(this).attr("data-target","#online");
	           }else{
	        	   return;
	           }
	          				
		 }else{
				$(this).attr("data-target","#online");	 
		 }
	});
	
	// 线上划扣按钮确认,获得选择的平台，
	$("#onlineBtn").click(function(){
		// 获得选中的平台
		var deductsType=$("#onlineDeducts").val();
		if(deductsType==""){
			showTip("请先请选择划扣平台！");
			return false;
		}
		// 当日，以往划扣标识
		var result = $("#result").val();
		// 搜索表单序列化
		var urgeServicesMoneyEx = $("#deductsForm").serialize();
		// 催收id
		var cid = "";
	
		
		if($(":input[name='checkBoxItem']:checked").length>0){
				// 对选中的单子进行导出
				$(":input[name='checkBoxItem']:checked").each(function(){	                		
         		if(cid!="")
         		{
         			cid+=","+$(this).attr("cid");
         		}else{
         			cid=$(this).attr("cid");
         		}
         	});
		 }
		urgeServicesMoneyEx+="&cid="+cid+"&deductsType="+deductsType+"&result="+result;
		 $.ajax({
	 			type : 'post',
	 			url : ctx + '/car/grant/grantDeducts/onlineGrantDeducts',
	 			data : urgeServicesMoneyEx ,
	 			dataType:'json',
	 			cache : false,
	 			success : function(result) {
	 				if (result == true) {
	 					art.dialog.alert("发送划扣成功！",function(){
	 						window.location.reload();
					  	});
	 					
					}else{
						art.dialog.alert("发送划扣失败！",function(){
	 						window.location.reload();
					  	});
						 
					}
	 
	 		    	
	 			},
	 			error : function() {
	 				art.dialog.alert("请求异常！",function(){
 						window.location.reload();
				  	});
	 			}
	 		});
		
		
		

	});




	
})	

	


    // 关闭模式框
 function closeBtn(){
	 $("#backModal").modal('hide');
 }


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
	    



