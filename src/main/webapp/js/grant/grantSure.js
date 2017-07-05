function batchRejectApply(formId,attrName){
  var hasNoFrozenFlag = false;
  var param = "";
  if($("input[name='checkBoxItem']:checked").length==0){
	  art.dialog.alert("请选择需要驳回的数据!");
	  return false;
  }
  $("input[name='checkBoxItem']:checked").each(function(){
     frozenFlag = $(this).attr("frozenFlag");
	 if(frozenFlag!="1"){
	 	hasNoFrozenFlag = true;
	 }
	 if(param!=""){
	    param+=";"+$(this).val();
	 }else{
		param=$(this).val();	
	 }
  });
if(hasNoFrozenFlag){
	art.dialog.alert("选中的数据中存在非冻结数据，请重新选择!");
	return false;
 }else{
	 var cancelFlagRetVal = null;
	$.ajax({
		type : 'post',
		url : ctx + '/borrow/grant/grantSure/updateLoanFlag',
		data : {
		'checkVal':param,
		'attrName':attrName,
		},
		cache : false,
		dataType : 'json',
		async : false,
		success : function(result) {
        cancelFlagRetVal = result;
		},
		error : function() {
		art.dialog.error('请求异常！', '提示信息');
		}
	});
	if(cancelFlagRetVal=='1'){
		$('#'+formId).submit();  
	}else if('0'==cancelFlagRetVal){
	   art.dialog.alert("请求的数据异常!");
	}
  }
}

$(document).ready(function(){
	$('#rejectApplyBtn').click(function(){
		batchRejectApply('grantSureForm','frozenFlag');
	});
	// 点击页面跳转事件
	$(".jumpTo").click(function(){
		var url=ctx +"/borrow/grant/grantSure/grantSureDeal";
		  window.location.href=url;
	});
	
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
		 $(':input','#grantSureForm')
		  .not(':button,:submit, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#grantSureForm").submit();
	});
	
	// 点击确定，合同金额上限
	$("#contractSure").click(function(){
		$("input[name='checkBoxItem']").attr('checked',false);
		var totalGrantMoney=0.00;
		var maxMoney = $("#maxMoney").val();
		// 金额验证，只能为数字，同时第一位不能为小数点
		var index = maxMoney.indexOf(".");
	    if(index>0 && maxMoney.substring(index).length>3) {
	    	art.dialog.alert("请输入有效的金额，小数位最多为两位");
	    	$(this).val("");
	        return false;
	    }
	    if(isNaN(maxMoney)) {//不是数字isNaN返回true
	    	art.dialog.alert("请输入有效的金额格式！");
	    	$(this).val("");
	        return false;
	    }
		if(maxMoney<=0){
			art.dialog.alert("合同金额上限要大于0");
			$(this).val("");
			return false;
		}else{
			$(":input[name='checkBoxItem']").each(function(){
				$(this).removeAttr("checked");
			});
			// 获得每一笔单子的合同金额，求和，对符合条件的单子进行选择
			calculate.topLimitAmount = maxMoney;
			var checkBoxItems = $(":input[name='checkBoxItem']");
			calculate.srcListLength = checkBoxItems.length;
			calculate.srcListIndex = 0;
			calculate.curMaxAmount = 0;
			calculate.maxAmountLocation = 0;
			calculate.canBreak = false;
			calculate.amountObjList = new Array();
			for(var i=0;i<calculate.srcListLength;i++){
				calculate.amountTopLimit(checkBoxItems,calculate,"contractMoney");
				if(calculate.canBreak){
					break;
				}
				if(calculate.srcListLength>calculate.srcListIndex+1){
					calculate.srcListIndex = calculate.srcListIndex+1;
				}
			}
			$("#totalContractMoney").text(fmoney(calculate.curMaxAmount,2));
			$("#hiddenContract").val(calculate.curMaxAmount);
			var amountObjList = calculate.amountObjList;
			var maxAmountLocation = calculate.maxAmountLocation;
			var amountBean = amountObjList[maxAmountLocation];
			var itemList = amountBean.itemList;
			var i = 0;
			$(":input[name='checkBoxItem']").each(function(){
                  for(var m = 0;m<itemList.length;m++){
                	  if(i==itemList[m]){
        			    $(this).attr("checked",'true');
        			    totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
                	  }
                  }
           		i++;
        	});
			$('#hiddenGrant').val(totalGrantMoney);
			$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
			$("#totalNum").text(itemList.length);
			calculate.amountObjList = new Array();
		}
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
			if(document.getElementById("T4")){
				document.getElementById("T4").style.display='';	
			}
			if(document.getElementById("T5")){
				document.getElementById("T5").style.display='';	
			}
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			if(document.getElementById("T3")){
				document.getElementById("T3").style.display='none';	
			}
			if(document.getElementById("T4")){
				document.getElementById("T4").style.display='none';	
			}
			if(document.getElementById("T5")){
				document.getElementById("T5").style.display='none';	
			}
		}
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		var totalNum=0;
		var totalContractMoney=0.00;
		var totalGrantMoney=0.00;
		if($('#checkAll').is(':checked')){
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				totalNum++;
				totalContractMoney+=parseFloat($(this).attr("contractMoney"));
				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
		}
		$('#totalNum').text(totalNum);
		$('#hiddenContract').val(totalContractMoney);
		$('#hiddenGrant').val(totalGrantMoney);
		$("#totalContractMoney").text(fmoney(totalContractMoney,2));
		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
	});
	
	// 计算金额
	var $subBox = $(":input[name='checkBoxItem']");
	$subBox.click(function(){
		var totalGrantMoney=parseFloat($(this).attr("grantAmount"));
		var totalContractMoney=parseFloat($(this).attr("contractMoney"));
		var totalNum=1;
		if($(this).attr('checked')=='checked'|| $(this).attr('checked')){			
		$('#totalNum').text(parseFloat($("#totalNum").text())+totalNum);
		var contract = parseFloat($("#hiddenContract").val())+totalContractMoney;
		var grant = parseFloat($("#hiddenGrant").val())+totalGrantMoney;
		
		$('#hiddenContract').val(contract);
		$('#hiddenGrant').val(grant);
		
		$("#totalContractMoney").text(fmoney(contract,2));
		$("#totalGrantMoney").text(fmoney(grant,2));
	 }else{
		 $("#checkAll").removeAttr("checked");
		 if($(":input[name='checkBoxItem']:checked").length==0){
			$('#hiddenContract').val(0.00);
			$('#hiddenGrant').val(0.00);
			$("#totalContractMoney").text(fmoney(0.00,2));
			$("#totalGrantMoney").text(fmoney(0.00,2));
			$("#totalNum").text(0);
		 }else{
			$('#totalNum').text(parseFloat($("#totalNum").text())-totalNum);
			var contract =  parseFloat($("#hiddenContract").val(),10) - totalContractMoney;
			var grant =  parseFloat($("#hiddenGrant").val(),10) - totalGrantMoney;
				
			$('#hiddenContract').val(contract);
			$('#hiddenGrant').val(grant);
				
			$("#totalContractMoney").text(fmoney(contract,2));
			$("#totalGrantMoney").text(fmoney(grant,2));
		 }
	 }
		$("#checkAll")
		.attr(
				"checked",
				$subBox.length == $(":input[name='checkBoxItem']:checked").length ? true
						: false);
	});
	// 批量点击添加P2P标识
	$("#addP2P").click(function(){
		var replyProductName=false;
		var checkVal=null;
 		var borrowFlag=$(this).val();
 		// 遍历页面中的选中框，
		if($(":input[name='checkBoxItem']:checked").length==0){
           art.dialog.alert("请选择要进行操作的数据!");
           return;					
		}else{
			
			$("input[name='checkBoxItem']:checked").each(function(){
				if($(this).attr("replyProductName")=="信易借"){
					replyProductName=true;
					return false;
				}
			});
			if(replyProductName){
				art.dialog.alert('信易借的产品不能修改渠道,请重新选择!');
				return ;
			}
	         art.dialog.confirm('确定将选中单子添加P2P标识？', 
            	    function () {
	            	$(":input[name='checkBoxItem']:checked").each(function(){	                		
	            		if(checkVal!=null)
	            		{
	            			checkVal+=";"+$(this).val();
	            		}else{
	            			checkVal=$(this).val();
	            		}
	            	});
	            	updFlagPch('channelCode',borrowFlag,checkVal);
            	    }, 
            	    function () {
            	    art.dialog.tips('执行取消操作');
            	    return;
        	    });
            }
	});
	
	// 批量取消P2P标识
	$("#cancleP2P").click(function(){
		var replyProductName=false;
		$("input[name='checkBoxItem']:checked").each(function(){
			if($(this).attr("replyProductName")=="信易借"){
				replyProductName=true;
				return false;
			}
		});
		if(replyProductName){
			art.dialog.alert('信易借的产品不能修改渠道,请重新选择!');
			return ;
		}
		var borrowFlag=$(this).val();
		flagCancle(borrowFlag);
	});
	
	// 批量取消金信标识
	$("#cancleJx").click(function(){
		flagCancle(borrowFlag);
	});
	
	// 导出客户信息表
	$("#customerDao").click(function(){
		var idVal = "";
		var listFlag = $("#listFlag").val();
		var loanFlowQueryParam = $("#grantSureForm").serialize();
		if($(":input[name='checkBoxItem']").length<=0)
		{
			art.dialog.alert("无可导出的客户信息!");
			return;
		}
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("applyId");
        		}else{
        			idVal=$(this).attr("applyId");
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/grantSure/grantCustomerExl?idVal="+idVal+"&"+loanFlowQueryParam+"&listFlag="+listFlag;
	});
	
	// 导出打款表
	$("#moneyBtn").click(function(){
		if($(":input[name='checkBoxItem']").length<=0)
		{
			art.dialog.alert("无可导出的客户信息!");
			return;
		}
		var idVal = "";
		var listFlag = $("#listFlag").val();
		var loanGrant = $("#grantSureForm").serialize();
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("applyId");
        		}else{
        			idVal=$(this).attr("applyId");
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/grantSure/sendMoneyExl?idVal="+idVal+"&"+loanGrant+"&listFlag="+listFlag;
	});
	
	// 汇总表导出
	$("#sumBtn").click(function(){
		if($(":input[name='checkBoxItem']").length<=0)
		{
			art.dialog.alert("无可导出的客户信息!");
			return;
		}
		
		var idVal = "";
		var listFlag = $("#listFlag").val();
		var loanGrant = $("#grantSureForm").serialize();
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("applyId");
        		}else{
        			idVal=$(this).attr("applyId");
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/grantSure/grantSumExl?idVal="+idVal+"&"+loanGrant+"&listFlag="+listFlag;
	});
	
	
	// 点击选择借款利率
	$("#selectRateBtn").click(function(){
		art.dialog.open(ctx + url, 
		{
					title:"选择利率", 
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
})	
	// 点击批量取消P2P,金信标识处理
	function flagCancle(borrowFlag) {
	var checkVal = null;
	var flagSet = "false";
	if ($(":input[name='checkBoxItem']:checked").length == 0) {
		art.dialog.alert("请选择要进行操作的数据!");
		return;
	} else {
		art.dialog.confirm("确定取消选中单子的" + borrowFlag + "标识么？", function() {
			$(":input[name='checkBoxItem']:checked").each(function() {
				// 获取要进行处理的单子
				var flag = $(this).attr('borrowTrusteeFlag');
				if (flag == borrowFlag) {
					if (checkVal == null) {
						checkVal = $(this).val();
					} else {
						// 将checkBox的值以';'分割传送到controller中
						checkVal += ";" + $(this).val();
					}
				} else {
					flagSet = "true";
				}
			});
			if (flagSet == "true") {
				art.dialog.alert("您选中的单子中，有不为" + borrowFlag + "的单子！请重新选择");
				return;
			} else {
				updFlagPch('channelCode', '财富', checkVal);
			}

		}, function() {
			art.dialog.tips('执行取消操作');
			return;
		});
	}
}
	
	// 修改单子的标识，同时返回列表
	function updFlagPch(attrName,borrowFlag,checkVal){
        	$.ajax({
    			type : 'post',
    			url : ctx + '/borrow/grant/grantSure/updateLoanFlag',
    			data : {
    				'checkVal':checkVal,    // 传递流程需要的参数
    				'borrowFlag':borrowFlag, //单子标识
    				'attrName':attrName
    			},
    			async : false,
    			success : function(result) {
    				art.dialog.alert(result);
    				window.location.reload();
    			},
    			error : function() {
    				art.dialog.alert('请求异常！');
    			}
    		});
        }
	
	// 上传文件处理
	function ajaxFileUpload() {
		  var file = $("#fileid");
	      if($.trim(file.val())==''){
	             alert("请选择文件");
	             return false;
	     }
	     var extStart=file.lastIndexOf(".");
		 var ext=file.substring(extStart,file.length).toUpperCase();
		    if(ext!=".XLSX" && ext!=".XLS"){
		    	art.dialog.alert("仅限于上传Excel文件");
		    	$("#fileid").val('');
		    	return false;
		    }
	    $.ajaxFileUpload({
	   	    url : ctx+'/borrow/grant/grantSure/importResult',
	        type: 'post',
	        secureuri: false, //一般设置为false
	        fileElementId: "fileid", // 上传文件的id、name属性名
	        success: function(data,status){
	        	art.dialog.alert(data);
	        },
	        error: function(data, status, e){ 
	        	art.dialog.alert("请求异常");
	        }
	    });
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