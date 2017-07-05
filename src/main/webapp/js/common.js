var loan = {};
var loanCard = {};
/**
 * 省市区联动  银行卡 专用省市区联动
 */
loanCard.initCity = function(provinceId,cityId,districtId){
	$("#"+provinceId).change(function() {
		 var provinceCode = $("#"+provinceId+" option:selected").val();
		 
		 var cityCode = $("#"+cityId+" option:selected").val();
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
          		url : ctx + "/common/cityinfo/asynLoadCityCmb",
          		data: {provinceId: provinceCode},
          		async: false,
          		success : function(data) {
          			
          			var resultObj = eval("("+data+")");
          		    $("#"+cityId).empty();
          		    $("#"+cityId).select2().empty();
          		    $("#"+cityId).append("<option value=''>请选择</option>");
	                $.each(resultObj,function(i,item){
	                	if(cityCode==item.areaCode){
	                        $("#"+cityId).append("<option selected='selected' value="+item.areaCode+">"+item.areaName+"</option>");
	                	}else{
	                		$("#"+cityId).append("<option value="+item.areaCode+">"+item.areaName+"</option>");	
	                	}
	                });
	                $("#"+cityId).trigger("change");
	                $("#"+cityId).attr("disabled", false);
          		}
          	});
		 }
	});
};
/**
 * 金账户省市区联动  
 */
loan.initKingBankCity = function(provinceId,cityId,areaType){
	$("#"+provinceId).change(function() {
		 var provinceCode = $("#"+provinceId+" option:selected").val();
		 var cityCode = $("#"+cityId+" option:selected").val();
		 if(provinceCode=="-1" || provinceCode==""){
			 $("#"+cityId).empty();
			 $("#"+cityId).select2().empty();
			 $("#"+cityId).append("<option value=''>请选择</option>");
			 $("#"+cityId).trigger("change");
		 }else{
		     $.ajax({
          		type : "POST",
          		url : ctx + "/common/fyAreaCode/asynGetFyAreaCode",
    			data : {
    				'parentId':provinceCode,
    				'areaType':areaType 
    			},
          		async: false,
          		success : function(data) {
          			var resultObj = eval("("+data+")");
          		    $("#"+cityId).empty();
          		    $("#"+cityId).select2().empty();
          		    $("#"+cityId).append("<option value=''>请选择</option>");
	                $.each(resultObj,function(i,item){
	                	if(cityCode==item.areaCode){
	                        $("#"+cityId).append("<option selected='selected' value="+item.areaCode+">"+item.areaName+"</option>");
	                	}else{
	                		$("#"+cityId).append("<option value="+item.areaCode+">"+item.areaName+"</option>");	
	                	}
	                });
	                $("#"+cityId).trigger("change");
	                $("#"+cityId).attr("disabled", false);
          		}
          	});
		 }
	});
};

/**
 * 省市区联动  
 */
loan.initCity = function(provinceId,cityId,districtId){
	$("#"+provinceId).change(function() {
		 var provinceCode = $("#"+provinceId+" option:selected").val();
		 
		 var cityCode = $("#"+cityId+" option:selected").val();
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
	                	if(cityCode==item.areaCode){
	                        $("#"+cityId).append("<option selected='selected' value="+item.areaCode+">"+item.areaName+"</option>");
	                	}else{
	                		$("#"+cityId).append("<option value="+item.areaCode+">"+item.areaName+"</option>");	
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
function show(){
	if(document.getElementById("T1").style.display=='none'){
		document.getElementById("showMore").src=ctxStatic+'/images/down.png';
		document.getElementById("T1").style.display='';
		if(document.getElementById("T2")){
		  document.getElementById("T2").style.display='';
		}
		if(document.getElementById("T3")){
		  document.getElementById("T3").style.display='';
		}
		if(document.getElementById("T4")){
			  document.getElementById("T4").style.display='';
			}
		if($("input[type='radio']").length>0){
			$("input[type='radio']").addClass("ml10 mr6");
		}
	}else{
		document.getElementById("showMore").src=ctxStatic+'/images/up.png';
		document.getElementById("T1").style.display='none';
		if(document.getElementById("T2")){
		  document.getElementById("T2").style.display='none';
		}
		if(document.getElementById("T3")){
		  document.getElementById("T3").style.display='none';
		}
		if(document.getElementById("T4")){
			  document.getElementById("T4").style.display='none';
			}
	}
}


/**
 * 弹出历史界面
 * applyId ：借款申请表主键
 */
function showLoanHis(applyId){
 		var url=ctx + "/common/history/showLoanHisByApplyId?applyId="+applyId;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '信借信息历史',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 * 弹出历史界面
 * applyId ：借款申请表主键
 */
function showEmailOpe(emailId){
 		var url=ctx + "/common/history/showEmailOpe?emailId="+emailId;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '历史列表',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 * 弹出历史界面
 * applyId ：借款申请表主键
 */
function showEmailOpeByContractCode(contractCode){
 		var url=ctx + "/common/history/showEmailOpe?contractCode="+contractCode;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '历史列表',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 * 弹出历史界面
 * applyId ：借款申请表主键
 */
function showHisByLoanCode(loanCode){
 		var url=ctx + "/common/history/showLoanHisByLoanCode?loanCode="+loanCode;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '信借信息历史',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 * 弹出历史界面
 * applyId ：借款申请表主键
 */
function showAllHisByLoanCode(loanCode){
	 	var url = ctx + "/common/history/showLoanHisByLoanCode?showAll=true&loanCode=" + loanCode;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '信借信息历史',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}


/**
 * 弹出车借历史界面
 * loanCode：借款申请表loanCode
 */
function showCarLoanHis(loanCode){
 		var url=ctx + "/common/carHistory/showLoanHisByLoanCode?loanCode="+loanCode;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '车借信息历史',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 * 弹出车借历史界面
 * applyId：借款申请表applyId
 */
function showCarLoanHisByApplyId(applyId){
 		var url=ctx + "/common/carHistory/showLoanHisByApplyId?applyId="+applyId;
	    art.dialog.open(url, {  
		   id: 'his',
		   title: '车借信息历史',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}


/**
 * 弹出历史界面(集中划扣)
 * applyId ：借款申请表主键
 */
function showPaybackHis(rPaybackId, payBackApplyId,lisi) {
	if(!lisi){
		lisi='';	
	}
	var url = ctx + "/common/history/showPayBackHis?rPaybackId=" + rPaybackId
			+ "&payBackApplyId=" + payBackApplyId+"&lisi="+lisi;
	art.dialog.open(url, {
		id : 'his',
		title : '还款操作流水',
		lock : true,
		width : 700,
		height : 350
	}, false);
}

/**
 * 弹出历史界面(非集中划扣)
 * applyId ：借款申请表主键
 */
function showNoDeductPaybackHistory(rPaybackId, payBackApplyId,dictLoanStatus) {
	var url = ctx + "/common/history/showNoDeductPaybackHistory?rPaybackId=" + rPaybackId
			+ "&payBackApplyId=" + payBackApplyId+"&dictLoanStatus="+dictLoanStatus;
	art.dialog.open(url, {
		id : 'his',
		title : '还款操作流水',
		lock : true,
		width : 700,
		height : 350
	}, false);
}

/**
 * 弹出车借借款信息界面 loanCode 
 */
function showCarLoanInfo(loanCode){
 		var url=ctx + "/car/uploadDataController/showCarLoanInfo?loanCode="+loanCode;
	    art.dialog.open(url, {  
		   id: 'information',
		   title: '车借信息查看',
		   lock:true,
		   width:1000,
		   height:450,
		   cancelVal:'返回',
		   cancel: true
		},false);  
}


/**
 * 弹出车借已咨询查看 loanCode
 */
function carConsultDone(loanCode){
 		var url=ctx + "/car/carConsult/carConsultDone?loanCode="+loanCode;
	    art.dialog.open(url, {  
		   id: 'information',
		   title: '车借咨询查看',
		   lock:true,
		   width:800,
		   height:450
		},false);  
}

/**
 * 弹出车借已录入查看 loanCode
 */
function appraiserEntryDone(loanCode){
 		var url=ctx + "/car/appraiserEntry/appraiserEntryDone?loanCode="+loanCode;
	    art.dialog.open(url, {  
		   id: 'information',
		   title: '车借录入查看',
		   lock:true,
		   width:800,
		   height:450
		},false);  
}

/**
 * 弹出车借合同已制作查看 applyId
 */
function contractDone(applyId){
 		var url=ctx + "/car/carContract/checkRate/contractDone?applyId="+applyId;
	    art.dialog.open(url, {  
		   id: 'contractDone',
		   title: '车借合同制作查看',
		   lock:true,
		   width:1000,
		   height:450
		},false);  
}

/**
 * 弹出车借合同协议预览和下载 loanCode flag
 */
function contractDownload(loanCode,flag){
 		var url=ctx + "/car/carContract/checkRate/contractDownload?loanCode=" + loanCode + "&flag=" + flag;
	    art.dialog.open(url, {  
		   id: 'Download',
		   title: '车借合同协议查看和下载',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 * 弹出车借合同协议预览和下载 loanCode flag
 */
function contractDownloadbyId(applyId,flag){
 		var url=ctx + "/car/carContract/checkRate/contractDownloadByApplyId?applyId=" + applyId + "&flag=" + flag;
	    art.dialog.open(url, {  
		   id: 'Download',
		   title: '车借合同协议查看和下载',
		   lock:true,
		   width:700,
		   height:350
		},false);  
}

/**
 *批量初始化省、市、联动 
 * 
 */
loan.batchInitCity=function(provinceName,CityName,AreaName){
	if(AreaName!=null){
	  var houseProvs = $("select[name$='"+provinceName+"']");
	  var houseCities = $("select[name$='"+CityName+"']"); 
	  var houseAreas = $("select[name$='"+AreaName+"']");
	  
	  for(var i = 0;i<houseProvs.length;i++){
		  
		 loan.initCity(houseProvs[i].id,houseCities[i].id,houseAreas[i].id); 
	  }
	}else{
		 var houseProvs = $("select[name$='"+provinceName+"']");
		 var houseCities = $("select[name$='"+CityName+"']"); 
		 for(var i = 0;i<houseProvs.length;i++){
			 loan.initCity(houseProvs[i].id,houseCities[i].id,null); 
		  }
	}
}
loan.queryFormClear=function(tarForm) {
	 $(':input','#'+tarForm)
	  .not(':button, :reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	 if($("select").length>0){
			$("select").each(function(){
				$(this).trigger("change");
			});
		}
}

/**
 * 获取选中复选框的值
 * checkBoxName ：复选框Name
 */
function getCheckedCheckBoxValue(checkBoxName){
	var ls = document.getElementsByName(checkBoxName);
	var str='';
	for ( var i = 0; i < ls.length; i++) {
		if(ls[i].checked ){
		 	if( str!=''){
		 		str += ',';
		 	}
		 	str += ls[i].value;
		}
	}
	return str;
}


/**
 * 全选
 * checkBoxName ：复选框Name
 */
function checkedAll(checkBoxName,obj){
	var ls = document.getElementsByName(checkBoxName);
	for ( var i = 0; i < ls.length; i++) {
		ls[i].checked = obj.checked;
	}
}

loan.initProduct=function (productId,monthId)
{
	$("#"+productId).change(function() { 
		var productCode = $("#"+productId+" option:selected").val();
	    if(productCode=="-1" || productCode==""){
			 $("#"+monthId).empty();
			 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
			 $("#"+monthId).trigger("change");
		 }else{
		     $.ajax({
         		type : "POST",
         		url : ctx + "/common/prdmng/asynLoadPrdMonths",
         		data: {productCode: productCode},	
         		success : function(data) {
         			var resultObj = eval("("+data+")");
         		    $("#"+monthId).empty();
         		    $("#"+monthId).append("<option value=''>请选择</option>");
	                $.each(resultObj,function(i,item){
	                  $("#"+monthId).append("<option value="+item+">"+item+"</option>"); 
	                });
	                $("#"+monthId).trigger("change");
	                $("#"+monthId).attr("disabled", false);
         		}
         	});
		 }
	});
};
loan.initProductRisk=function (productId,monthId,createTime)
{
	$("#"+productId).change(function() { 
		var productCode = $("#"+productId+" option:selected").val();
	    if(productCode=="-1" || productCode==""){
			 $("#"+monthId).empty();
			 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
			 $("#"+monthId).trigger("change");
		 }else{
		     $.ajax({
         		type : "POST",
         		url : ctx + "/common/prdmng/asynLoadPrdMonthsRisk",
         		data: {productCode: productCode,createTime: createTime},	
         		success : function(data) {
         			var resultObj = eval("("+data+")");
         		    $("#"+monthId).empty();
         		    $("#"+monthId).append("<option value=''>请选择</option>");
	                $.each(resultObj,function(i,item){
	                  $("#"+monthId).append("<option value="+item+">"+item+"</option>"); 
	                });
	                $("#"+monthId).trigger("change");
	                $("#"+monthId).attr("disabled", false);
         		}
         	});
		 }
	});
};
loan.selectedProduct=function (productId,monthId,monthCode){
	 var productCode = $("#"+productId+" option:selected").val();
	 if(productCode=="-1" || productCode==""){
		 $("#"+monthId).empty();
		 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
	 }else{
	     $.ajax({
     		type : "POST",
     		url : ctx + "/common/prdmng/asynLoadPrdMonths",
     		data: {productCode: productCode},	
     		success : function(data) {
     			var resultObj = eval("("+data+")");
     		    $("#"+monthId).empty();
     		    $("#"+monthId).append("<option value=''>请选择</option>");
                $.each(resultObj,function(i,item){
                	if(monthCode==item){
                	  $("#"+monthId).append("<option selected=true value="+item+">"+item+"</option>");	
                	}else{
                      $("#"+monthId).append("<option value="+item+">"+item+"</option>");
                	}
                });
                $("#"+monthId).trigger("change");
                $("#"+monthId).attr("disabled", false);
     		}
     	});
	 }
};
loan.selectedProductRisk=function (productId,monthId,monthCode,createTime){
	 var productCode = $("#"+productId+" option:selected").val();
	 if(productCode=="-1" || productCode==""){
		 $("#"+monthId).empty();
		 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
	 }else{
	     $.ajax({
    		type : "POST",
    		url : ctx + "/common/prdmng/asynLoadPrdMonthsRisk",
    		data: {productCode: productCode,createTime: createTime},	
    		success : function(data) {
    			var resultObj = eval("("+data+")");
    		    $("#"+monthId).empty();
    		    $("#"+monthId).append("<option value=''>请选择</option>");
               $.each(resultObj,function(i,item){
               	if(monthCode==item){
               	  $("#"+monthId).append("<option selected=true value="+item+">"+item+"</option>");	
               	}else{
                     $("#"+monthId).append("<option value="+item+">"+item+"</option>");
               	}
               });
               $("#"+monthId).trigger("change");
               $("#"+monthId).attr("disabled", false);
    		}
    	});
	 }
};
/**
 *初始化产品期限信息并查询额度上下限信息 
 *@Author zhanghao
 *@Create In 2016-03-10
 *@param productId  产品ID
 *@param monthId    期限ID
 *@param monthCode  初始期限
 *@param limitLowerId  额度下限ID
 *@param limitUppderId 额度上限ID
 * 
 * 
 */
loan.selectedProductWithLimit=function (productId,monthId,monthCode,limitLowerId,limitUppderId,createTime,loanCustomerSource){
	 var productCode = $("#"+productId+" option:selected").val();
	 var loanCustomerSourceCode = $("#"+loanCustomerSource+" option:selected").val();
	 if(productCode=="-1" || productCode==""){
		 $("#"+monthId).empty();
		 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
	 }else{
	     $.ajax({
    		type : "POST",
    		url : ctx + "/common/prdmng/asynLoadPrdInfoRisk",
    		dataType:'json',
    		data: {productCode: productCode,createTime:createTime,loanCustomerSourceCode:loanCustomerSourceCode},	
    		success : function(data) {
    		    $("#"+monthId).empty();
    		    $("#"+monthId).append("<option value=''>请选择</option>");
    		    var listMonths = data.listMonths;
                var i = 0;
                for(;i<listMonths.length;i++){
                	 if(monthCode==listMonths[i]){
                     	  $("#"+monthId).append("<option selected=true value="+listMonths[i]+">"+listMonths[i]+"</option>");	
                     	}else{
                           $("#"+monthId).append("<option value="+listMonths[i]+">"+listMonths[i]+"</option>");
                     	}
                }
               $('#'+limitLowerId).val(data.limitLower);
               $('#'+limitUppderId).val(data.limitUpper);
               $("#"+monthId).trigger("change");
               $("#"+monthId).attr("disabled", false);
               //如果是借么的单子则自然人保证人页签的申请期限设置成disabled
               var isBorrow=$("#isBorrow").val();
               if(isBorrow == 1){
            	   $("select[name^='loanCoborrower'][name$='loanInfoCoborrower.loanMonths']").attr('disabled',true); //申请期限
               }
    		}
    	});
	 }
};

/**
 *初始化产品期限信息并查询额度上下限信息,此方法适用于旧版申请表，新版申请表请见initProductWithLimitNew方法 
 *@Author zhanghao
 *@Create In 2016-03-10
 *@param productId  产品ID
 *@param monthId    期限ID
 *@param limitLowerId  额度下限ID
 *@param limitUppderId 额度上限ID
 * 
 * 
 */
loan.initProductWithLimit = function(productId,monthId,limitLowerId,limitUppderId,createTime){
	 $("#"+productId).change(function() {
			var productCode = $("#"+productId+" option:selected").val();
		    if(productCode=="-1" || productCode==""){
				 $("#"+monthId).empty();
				 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
				 $("#"+monthId).trigger("change");
			 }else{
			     $.ajax({
	         		type : "POST",
	         		dataType:'json',
	         		url : ctx + "/common/prdmng/asynLoadPrdInfoRisk",
	         		data: {productCode: productCode,createTime: createTime},	
	         		success : function(data) {
		                $("#"+monthId).empty();
		    		    $("#"+monthId).append("<option value=''>请选择</option>");
		                var listMonths = data.listMonths;
		                var i = 0;
		                for(;i<listMonths.length;i++){
		                	 $("#"+monthId).append("<option value="+listMonths[i]+">"+listMonths[i]+"</option>"); 	
		                }
		               $('#'+limitLowerId).val(data.limitLower);
		               $('#'+limitUppderId).val(data.limitUpper);
		               $("#"+monthId).trigger("change");
		               $("#"+monthId).attr("disabled", false);
		               //如果是借么的单子则自然人保证人页签的申请期限设置成disabled
		               var isBorrow=$("#isBorrow").val();
		               if(isBorrow == 1){
		            	   $("select[name^='loanCoborrower'][name$='loanInfoCoborrower.loanMonths']").attr('disabled',true); //申请期限
		               }
	         		}
	         	});
			 }
		});
};
/**
 * 适用于新版申请表
 */
loan.initProductWithLimitNew = function(productId,monthId,limitLowerId,limitUppderId,createTime){
	 $("#"+productId).change(function() {
			var productCode = $("#"+productId+" option:selected").val();
			/**根据选择的产品决定字段是否必填 start*/
			if(productCode!=null && productCode!=''){
				var productCodeArr = ['A007','A008','A014','A003']; //优卡借、优房借、精英借、薪水借
				if(productCodeArr.indexOf(productCode)!=-1){
					$(".productTypeSpan").show();
					$(".productTypeRe").addClass("required");
				}else{
					$(".productTypeSpan").hide();
					$(".productTypeRe").removeClass("required");
				}
			}
			/**根据选择的产品决定字段是否必填 start*/
			//如果是老板借则客户来源字段显示
			if(productCode!=null && productCode!=''){
				if(productCode == "A005"){
					$("#loanCustomerSourceSpan").show(); 
				}else{
					$("#loanCustomerSourceSpan").hide(); 
					$("#loanCustomerSource").val("").trigger("change");
				}
			}
		    if(productCode=="-1" || productCode==""){
				 $("#"+monthId).empty();
				 $("#"+monthId).append("<option value='' selected=true>请选择</option>");
				 $("#"+monthId).trigger("change");
			 }else{
			     $.ajax({
	         		type : "POST",
	         		dataType:'json',
	         		url : ctx + "/common/prdmng/asynLoadPrdInfoRisk",
	         		data: {productCode: productCode,createTime: createTime},	
	         		success : function(data) {
		                $("#"+monthId).empty();
		    		    $("#"+monthId).append("<option value=''>请选择</option>");
		                var listMonths = data.listMonths;
		                var i = 0;
		                for(;i<listMonths.length;i++){
		                	 $("#"+monthId).append("<option value="+listMonths[i]+">"+listMonths[i]+"</option>"); 	
		                }
		               $('#'+limitLowerId).val(data.limitLower);
		               $('#'+limitUppderId).val(data.limitUpper);
		               $("#"+monthId).trigger("change");
		               $("#"+monthId).attr("disabled", false);
		               
	         		}
	         	});
			 }
		});
}; 
loan.initCustomerSourceWithLimit = function(productId,monthId,createTime,loanCustomerSource){
	 $("#"+loanCustomerSource).change(function() {
		 var productCode = $("#"+productId+" option:selected").val();
		 var loanCustomerSourceCode = $("#"+loanCustomerSource+" option:selected").val();
		 $.ajax({
      		type : "POST",
      		dataType:'json',
      		url : ctx + "/common/prdmng/asynLoadPrdInfoRisk",
      		data: {productCode: productCode,createTime: createTime,loanCustomerSourceCode:loanCustomerSourceCode},	
      		success : function(data) {
                $("#"+monthId).empty();
    		    $("#"+monthId).append("<option value=''>请选择</option>");
                var listMonths = data.listMonths;
                var i = 0;
                for(;i<listMonths.length;i++){
                	 $("#"+monthId).append("<option value="+listMonths[i]+">"+listMonths[i]+"</option>"); 	
                }
                $("#"+monthId).trigger("change");
                $("#"+monthId).attr("disabled", false);
                loan.selectedProductWithLimit("productId", "monthId",$('#loanMonths').val(),"limitLower","limitUpper",createTime,"loanCustomerSource");
      		}
      	});
	 });
}
loan.getstorelsit = function(inputText,hiddenText,isSingle){
	 var url="/sys/org/selectOrgList";
	 if(isSingle!=""&isSingle!="undefined"&isSingle!=undefined){
		 url=url+"?isSingle=1"
	 }
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
			        	
			        	str = str.replace(/,$/g,"");
			        	strname = strname.replace(/,$/g,"");
			        	
			        	$("#"+inputText).val(strname);
			        	$("#"+hiddenText).val(str);
			 }
	},false);
})
};
//登录用户查询框
loan.getuserlist = function(inputText,hiddenText){
	 var url="/sys/user/userList";
	$("#selectUserBtn").click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"选择申请人", 
					width:660, 
					height:450, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var iframe = this.iframe.contentWindow;
			        	var str="";
			        	var strname="";
			        	$("input[name='userIds']:checked",iframe.document).each(function(){ 
			        	    if($(this).attr("checked")){
			        	        str += $(this).attr("id")+",";
			        	        strname += $(this).attr("sname")+",";
			        	    }
			        	});
			        	
			        	str = str.replace(/,$/g,"");
			        	strname = strname.replace(/,$/g,"");
			        	
			        	$("#"+inputText).val(strname);
			        	$("#"+hiddenText).val(str);
			 }
	},false);
})
};

// 渠道标识的查询框，
loan.getChannelFlag = function(inputText,hiddenText,isSingle){
	 var url="/common/dict/getChannelList?typeName=jk_channel_flag";
	 if(isSingle!=""&isSingle!="undefined"&isSingle!=undefined){
		 url=url+"?isSingle=1"
	 }
	$("#selectChannelBtn").click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"选择渠道", 
					width:360, 
					height:250, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var iframe = this.iframe.contentWindow;
			        	var str="";
			        	var strname="";
			        	$("input[name='dict']:checked",iframe.document).each(function(){ 
			        	    if($(this).attr("checked")){
			        	        str += $(this).val()+",";
			        	        strname += $(this).attr("sname")+",";
			        	    }
			        	});
			        	
			        	str = str.replace(/,$/g,"");
			        	strname = strname.replace(/,$/g,"");
			        	
			        	$("#"+inputText).val(strname);
			        	$("#"+hiddenText).val(str);
			 }
	},false);
})
};

//查询字典
function getDict(typeName,titleName,inputText,hiddenText){
	 var url="/common/dict/getChannelList?typeName="+typeName;
	 
		art.dialog.open(ctx + url, 
		{
					title:titleName, 
					width:360, 
					height:250, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var iframe = this.iframe.contentWindow;
			        	var str="";
			        	var strname="";
			        	$("input[name='dict']:checked",iframe.document).each(function(){ 
			        	    if($(this).attr("checked")){
			        	        str += $(this).val()+",";
			        	        strname += $(this).attr("sname")+",";
			        	    }
			        	});
			        	
			        	str = str.replace(/,$/g,"");
			        	strname = strname.replace(/,$/g,"");
			        	
			        	$("#"+inputText).val(strname);
			        	$("#"+hiddenText).val(str);
			 }
	},false);
};

loan.getBankList = function(inputText,hiddenText,isSingle,selectid){
	var url = "/common/bank/list";
	var obj;
	if(selectid){
		obj = $("#"+selectid);
	}else{
		obj = $("#selectStoresBtn");
	}
	    obj.click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"选择支行", 
					width:660, 
					height:450, 
					lock:true,
					opacity: .1,
			        okVal: '确定', 
			        ok:function(){
			        	var iframe = this.iframe.contentWindow;
			        	var str="";
			        	var strname="";
			        	$("input[name='bankCode']:checked",iframe.document).each(function(){ 
			        	    if($(this).attr("checked")){
			        	        str += $(this).attr("id")+",";
			        	        strname += $(this).attr("sname")+",";
			        	    }
			        	});
			        	str = str.replace(/,$/g,"");
			        	strname = strname.replace(/,$/g,"");
			        	
			        	$("#"+inputText).val(strname);
			        	$("#"+hiddenText).val(str);
			 }
	},false);
})
};



loan.getOpenBank=function(typeName,textId,valueId){
	var url = ctx+"/common/dict/list?typeName="+typeName;
	$('#chooseOpenBankBtn').click(function(){
		art.dialog.open(url,
			{
				title:"选择开户行", 
				width:460, 
				height:350, 
				lock:true,
				opacity: .1,
				okVal: '确定', 
				ok:function(){
					var iframe = this.iframe.contentWindow;
					var svalue="";
					$("input[name='dict']:checked",iframe.document).each(function(){ 
						if($(this).attr("checked")){
							svalue += $(this).attr("svalue")+",";
						}
					});
					
					svalue = svalue.replace(/,$/g,"");
	        	
					$("#"+textId).val(svalue);
					if(valueId!=null && valueId!=''){
						$("#"+valueId).val(svalue);
					}
				}
				},false);
	});
};

function autoMatch(inputId,hiddenId){
	$('#' + inputId).autocomplete({
		source : function(query, process) {
			var matchCount = this.options.items;// 返回结果集最大数量
			$.post(ctx + "/common/userinfo/userdata", {
				"userName" : query,
				"matchCount" : matchCount
			}, function(respData) {
				return process(respData);
			});
		},
		formatItem : function(item) {
			return item["name"] + "(" + item["userCode"] + ")";
		},
		setValue : function(item) {
			$('#' + hiddenId).val(item["id"]);
			return {
				'data-value' : item["name"],
				'real-value' : item["userCode"]
			};
		}
	});
}

var waitingDialog = waitingDialog || (function ($) {
	var $dialog = $(
		'<div class="modal fade" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true" style="overflow-y:visible;">' +
		'<div class="modal-dialog modal-m">' +
		'<div class="modal-content">' +
			'<div class="modal-header"><h3 style="margin:0;"></h3></div>' +
			'<div class="modal-body">' +
				'<div class="progress progress-striped active" style="margin-bottom:0;"><div class="progress-bar" style="width: 100%"></div></div>' +
			'</div>' +
		'</div></div></div>');

	return {
		show: function (message, options) {
			// Assigning defaults
			if (typeof options === 'undefined') {
				options = {};
			}
			if (typeof message === 'undefined') {
				message = '正在拼命加载中...';
			}
			var settings = $.extend({
				dialogSize: 'm',
				progressType: '',
				onHide: null // This callback runs after the dialog was hidden
			}, options);

			// Configuring dialog
			$dialog.find('.modal-dialog').attr('class', 'modal-dialog').addClass('modal-' + settings.dialogSize);
			$dialog.find('.progress-bar').attr('class', 'progress-bar');
			if (settings.progressType) {
				$dialog.find('.progress-bar').addClass('progress-bar-' + settings.progressType);
			}
			$dialog.find('h3').text(message);
			// Adding callbacks
			if (typeof settings.onHide === 'function') {
				$dialog.off('hidden.bs.modal').on('hidden.bs.modal', function (e) {
					settings.onHide.call($dialog);
				});
			}
			// Opening dialog
			$dialog.modal();
		},
		/**
		 * Closes dialog
		 */
		hide: function () {
			$dialog.modal('hide');
		}
	};

})(jQuery);
function textareaNum(obj, length){
	var maxCount = length;  // 最高字数，这个值可以自己配置
	 var len = getStrLength($(obj).val()); // 当前输入的自数
	 var nowLen = eval( maxCount - len );
	 if(nowLen <= 0){
		 art.dialog.tips("最多输入" + length + "个字");
	 }
}


/**
 * 弹出历史界面(集中划扣)
 * applyId ：借款申请表主键
 */
function showPaybackHistory(contractCode) {
	var url = ctx + "/common/history/showPaybackHistory?contractCode=" + contractCode;
	art.dialog.open(url, {
		id : 'his',
		title : '退款操作流水',
		lock : true,
		width : 800,
		height : 450
	}, false);
}
function validateXlsFileImport(fileId,fileName){
	var filepath = $("input[name='"+fileName+"']").val();
    var extStart = filepath.lastIndexOf(".");
    var ext = filepath.substring(extStart, filepath.length).toUpperCase();
    if($.trim(filepath)==''){
    	showTip("请选择选择文件再导入！");
        return false;
    }
    if (ext != ".XLS" && ext != ".XLSX") {
    	showTip("仅限于xls,xlsx数据导入！");
        return false;
    }
    var file_size = 0;
    file_size = $("#"+fileId+"")[0].files[0].size;
    var size = file_size / 1024;
    if (size > 10240) {
        showTip("上传的xls大小不能超过10M！");
        return false;
    }
    return true;
}
$(document).ready(function() {
	$(".select2Req").change(function(){  
	     $(this).valid();  
  });
});
function showExtendLoanInfo(applyId){
		location.href=ctx + "/car/carExtend/carExtendDetails/extendCheck?applyId="+applyId;
}
function disabledSubmitBtn(submitBtnId){
	$("#"+submitBtnId).attr("disabled","disabled");
}
function removeSubmitDiv(){
	$("#submitFormDiv").remove();
}

loan.getstorelsitSub = function(inputText,hiddenText,isSingle,sub){
	 var url="/sys/org/selectOrgList";
	 if(isSingle!=""&isSingle!="undefined"&isSingle!=undefined){
		 url=url+"?isSingle=1"
	 }
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
			        	
			        	str = str.replace(/,$/g,"");
			        	strname = strname.replace(/,$/g,"");
			        	
			        	$("#"+inputText).val(strname);
			        	$("#"+hiddenText).val(str);
			        	$("#"+sub).get(0).focus();
			 },
			 cancel: function(){
				 $("#"+sub).get(0).focus();
		        }
	},false);
})
};
