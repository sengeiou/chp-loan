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
		url : ctx + '/borrow/grant/grantSure/asycUpdateFlowAttr',
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
	});
	
	// 点击确定，合同金额上限
	$("#contractSure").click(function(){
		var totalNum=0;
		var totalGrantMoney=0.00;
		var maxMoney = $("#maxMoney").val();
		var sumContractMoney = 0.00;
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
			// 获得每一笔单子的合同金额，求和，对符合条件的单子进行选择
			$(":input[name='checkBoxItem']").each(function(){
        		var contractMoney =parseFloat($(this).attr("contractMoney"));
        		sumContractMoney += contractMoney;
        		if(sumContractMoney <= maxMoney){
        			$(this).attr("checked",'true');
        			// 之后对放款笔数，放款累计金额进行处理
        			totalNum++;
    				totalGrantMoney+=parseFloat($(this).attr("grantAmount"));
        		}
        		$('#totalNum').text(totalNum);
        		$("#totalContractMoney").text(fmoney(sumContractMoney,2));
        		$("#totalGrantMoney").text(fmoney(totalGrantMoney,2));
        	});
		}
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src=ctxStatic + '/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			document.getElementById("T3").style.display='';
			document.getElementById("T4").style.display='';
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			document.getElementById("T3").style.display='none';
			document.getElementById("T4").style.display='none';
		}
	});
	var num = 0,money = 0.00,contractMoney = 0.00;
	// 点击全选
	$("#checkAll").click(function(){
		var cumulativeLoan = 0.00,
		totalNum = 0,cmoney  = 0.00;
		$(":input[name='checkBoxItem']").prop("checked",this.checked);
		//全选按钮选中
		if ($(this).is(":checked")) {
			$(":input[name='checkBoxItem']").each(function(){
				    cumulativeLoan += parseFloat($(this).attr("grantAmount"));
				    cmoney += parseFloat($(this).attr("contractMoney"));
					totalNum ++;
				$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
				$("#totalNum").text(totalNum);
				$("#totalContractMoney").text(fmoney(cmoney,2));
				
				//合同金额
				$("#hiddenContract").val(cmoney);
				$("#hiddenGrant").val(cumulativeLoan);
				$("#totalNumber").val(totalNum);
			});
		}else{
			cumulativeLoan = 0.00;
			totalNum = 0;
			cmoney  = 0.00;
			$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
			$("#totalNum").text(totalNum);
			$("#totalContractMoney").text(fmoney(cmoney,2));
			
			//合同金额
			$("#hiddenContract").val(cmoney);
			$("#hiddenGrant").val(cumulativeLoan);
			$("#totalNumber").val(totalNum);
		} 
		
	});
	
	// 计算金额
	var $subBox = $(":input[name='checkBoxItem']");
	$subBox.click(function() {
		// 获得单个单子的金额
		var deductAmount = parseFloat($(this).attr("grantAmount")),
		grant = parseFloat($(this).attr("contractMoney"));
		
		//放款总额
		var hiddenDeduct = parseFloat($("#hiddenGrant").val()),
		totalNumm = parseFloat($("#totalNumber").val()),
		hiddenGrant = parseFloat($("#hiddenContract").val());//合同金额
		
		var total  = 0,contractM = 0.00,lendM = 0.00;
		if ($(this).is(':checked')) {
			lendM = hiddenDeduct += deductAmount;
			total = totalNumm += 1 ;
			contractM = hiddenGrant += grant;
		} else {
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				hiddenDeduct = money;
				totalNumm = num;
				hiddenGrant = contractMoney;
			} else {
				lendM = hiddenDeduct -= deductAmount;
				total = totalNumm -= 1 ;
				contractM = hiddenGrant -= grant;
			}
		}
		$("#checkAll").prop("checked",($subBox.length == $(":input[name='checkBoxItem']:checked").length) ? true : false);
		
		$("#totalGrantMoney").text(fmoney(hiddenDeduct,2));
		$("#totalNum").text(totalNumm);
		$("#totalContractMoney").text(fmoney(hiddenGrant,2));
		//合同金额
		$("#hiddenContract").val(contractM);
		$("#hiddenGrant").val(lendM);
		$("#totalNumber").val(total);
		
	});
	
	/*$subBox.each(function(){
		var totalGrantMoney = parseFloat($(this).attr("grantAmount"));
			contractMoney += parseFloat($(this).attr("contractMoney"));
			money += totalGrantMoney;
			num ++;
		$("#totalGrantMoney").text(fmoney(money,2));
		$("#totalNum").text(num);
		$("#totalContractMoney").text(fmoney(contractMoney,2));
	});*/
	
	
	// 批量点击添加P2P标识
	$("#addP2P").click(function(){
		var checkVal=null;
 		var borrowFlag=$(this).val();
 		// 遍历页面中的选中框，
		if($(":input[name='checkBoxItem']:checked").length==0){
           alert("请选择要进行操作的数据!");
           return;					
		}else{
            var r=confirm("确定将选中单子添加P2P标识么？");
            if(r){
            	$(":input[name='checkBoxItem']:checked").each(function(){	                		
            		if(checkVal!=null)
            		{
            			checkVal+=";"+$(this).val();
            		}else{
            			checkVal=$(this).val();
            		}
            	});
            	updFlag('channelCode',borrowFlag,checkVal);
	      }
            else{
            	return;}
            }
	});
	
	// 批量取消金信标识
	$("#cancleJx").click(function(){
		var borrowFlag=$(this).val();
		flagCancle(borrowFlag);
	});
	
})	
	
	
	//修改单子的标识，同时返回列表
	function updFlag(attrName,borrowFlag,checkVal){
        	$.ajax({
    			type : 'post',
    			url : ctx + '/borrow/grant/grantSure/asycUpdateFlowAttr',
    			data : {
    				'checkVal':checkVal,    //传递流程需要的参数
    				'borrowFlag':borrowFlag, //单子标识
    				'attrName':attrName
    			},
    			cache : false,
    			dataType : 'json',
    			async : false,
    			success : function(result) {
    		      if(result){
    		    	 alert("修改成功");
    		    	 window.location.href=ctx+'/borrow/grant/grantSure/grantSureFetchTaskItems?flowFlag=GRANT_DETAIL_SURE';
    		      }
    			},
    			error : function() {
    				alert('请求异常！');
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
    (function($){
    	  $.isBlank = function(obj){
    	    return(!obj || $.trim(obj) === "") ? 0 : obj;
    	  };
    })(jQuery);