	/**
	清除查询表单中的数据项
	 一定要加[0]
	 */
	function queryFormClear(tarForm) {
		 $(':input','#'+tarForm)
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
	}
	/**
	 异步加载列表数据，数据加载后将整个视图嵌入列表
	 @Parameter srcForm  查询参数表单
	 @Parameter queue    队列集合名称
	 @Parameter viewName 需要返回的视图名称
	 */
	function fetchTaskItems(srcForm,flagForm) {//queue='HJ_CONTRACT_COMMISSIONER' ，viewName = _rateAudit_workItems
		
		var srcFormParam = $('#' + srcForm).serialize();
		srcFormParam +="&"+$('#' + flagForm).serialize();
		window.location=ctx+'/apply/contractAudit/fetchTaskItems?'+srcFormParam;
	}
	/**
	 全选事件
	 @Parameter checked 全选框的check状态
	 */
	function selectAll(checked) {
		$("input[name='prepareCheckEle']").each(function() {
			$(this).attr('checked', checked);
		});
	}
	/**
	 查询历史 
	 */
	function viewAuditHistory(applyId) {
		showLoanHis(applyId);
	}
	/**
	 办理
	 viewName 测试用
	 *${ctx}/bpm/flowController/openForm?applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"
	 */
	function dealTask(applyId, wobNum, dealType, token,stepName) {
		var url = ctx + "/bpm/flowController/openForm?applyId=" + applyId
				+ "&wobNum=" + wobNum + "&dealType=" + dealType + "&token="
				+ token+"&stepName="+stepName;
		window.location = url;
	}
    function display(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
   }
	/**
	 * 费率计算
	 * @author 张灏
	 * 
	 */
	function reckonFee(curRate){
		if(curRate!=''){
			var srcFormParam = $('#rateAuditForm').serialize();
			srcFormParam+="&ctrFee.feeLoanRate="+curRate;
			$.ajax({
				url:ctx+"/apply/contractUtil/asycReckonFee",
				type:'post',
				data:srcFormParam,
				dataType : 'json',
				async : false,
				success:function(result){
					$('#feePaymentAmount').val(result.feeInfo.feePaymentAmount);
					$('#fee_payment_amount').val(result.ctrFee.feePaymentAmount);
					$('#bv_feePaymentAmount').val(result.ctrFee.feePaymentAmount);
					$('#feeConsult').val(result.feeInfo.feeConsult);
					$('#fee_consult').val(result.ctrFee.feeConsult);
					$('#feeUrgedService').html(result.feeInfo.feeUrgedService);
					$('#fee_urged_service').val(result.ctrFee.feeUrgedService);
					$('#contractAmount').val(result.feeInfo.contractAmount);
					$('#contract_amount').val(result.contract.contractAmount);
					$('#bv_contractAmount').val(result.contract.contractAmount);
					$('#feeAuditAmount').val(result.feeInfo.feeAuditAmount);
					$('#fee_audit_amount').val(result.ctrFee.feeAuditAmount);
					$('#contractMonthRepayAmount').val(result.feeInfo.contractMonthRepayAmount);
					$('#contract_back_month_money').val(result.contract.contractMonthRepayAmount);
					$('#feeService').val(result.feeInfo.feeService);
					$('#fee_service').val(result.ctrFee.feeService);
					$('#contractExpectAmount').val(result.feeInfo.contractExpectAmount);
					$('#contract_expect_amount').val(result.contract.contractExpectAmount);
					$('#feeInfoService').val(result.feeInfo.feeInfoService);
					$('#fee_info_service').val(result.ctrFee.feeInfoService);
					$('#feeCount').val(result.feeInfo.feeCount);
					$('#fee_count').val(result.ctrFee.feeCount);
				},
				error:function(){
					art.dialog.alert('【计算费率】异常');
				}
			
			});
		}
	}
	
	/**
	  标识设定
	
	 */
	function ajaxHandleFlag(param,attrName,tarVal,batchLabel){
		$.ajax({
			type : 'post',
			url : ctx + '/apply/contractAudit/asynChangeFlag',
			data : {
				'batchColl':param,
				'attributName':attrName,
				'batchValue':tarVal,
				"batchLabel":batchLabel
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
	}
function openAddFlagDialog(attributeName,flowFlag,redirectUrl){
	var param="";
	$("input[name='prepareCheckEle']:checked").each(function(){
		if(param!=""){
		    param+=";"+$(this).val();
		}else{
			param=$(this).val();	
		}
	});
	if(param==""){
	    art.dialog.alert("请先选择需要添加标识的数据!");
		return;
	}
	 var url=ctx + '/apply/contractUtil/openAddFlagDialog?batchColl='+param+"&attributeName="+attributeName+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl;
	 art.dialog.open(url, {  
         id: 'flag',
         title: '标识!',
         lock:true,
         width:700,
     	 height:350
     },  
     false);	
}
	/**
	 *退回 
	 * 
	 */
function backFlow(viewName,flowFlag,redirectUrl,targetForm){
	var param = $('#'+targetForm).serialize();
	 var url=ctx + '/apply/contractUtil/openGrantDialog?viewName='+viewName+"&"+param+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl;
	 art.dialog.open(url, {  
         id: 'his',
         title: '退回!',
         lock:true,
         width:700,
     	 height:350
     },  
     false);
	}
//$(this).attr('applyId'),$(this).attr('token')
function workItemBackFlow(viewName,flowName,stepName,dealType,applyId,token,flowFlag,redirectUrl,wobNum){
	
	var url=ctx + '/apply/contractUtil/openGrantDialog?viewName='+viewName+"&flowName="+flowName+"&dealType="+dealType+"&stepName="+stepName+"&applyId="+applyId+"&token="+token+"&wobNum="+wobNum+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl;
	 art.dialog.open(url, {  
         id: 'back',
         title: '退回!',
         lock:true,
         width:700,
     	 height:350
     },  
     false);
}
	/**
	 *返回待办列表 
	 * 
	 * 
	 */
	function retAuditList(flowFlag){
		window.location=ctx+"/apply/contractAudit/fetchTaskItems?flowFlag="+flowFlag;
		
	}
	/**
	 * 待确认签署 流程提交、退回
	 * @param queue 队列集合的名称；viewName 返回的视图；response 节点路由；redirectUrl 重定向路径 
	 */
	function dispatchFlow(flowFlag,response,redirectUrl){
		var rateForm = null;
		var loanapplyForm = null;
		var rateAuditForm = null;
		var confirmSignForm = null;
		var contractCreateForm = null;
		var contractAuditForm = null;
		var finalForm = null;
		//退回原因
  		var remark = "";
  		remark =$("#remark").val();
  		
		if($('#rateForm').length!=0){
		   rateForm = $('#rateForm').serialize();
		   if(finalForm!=null){
			   finalForm+="&"+rateForm;
		   }else{
			   finalForm = rateForm;
		   }
		}
		if($('#loanapplyForm').length!=0){
		   loanapplyForm = $('#loanapplyForm').serialize();
		   if(finalForm!=null){
			   finalForm+="&"+loanapplyForm;
		   }else{
			   finalForm = loanapplyForm;
		   }
		}
		if($('#rateAuditForm').length!=0){
		  rateAuditForm = $('#rateAuditForm').serialize();
		  if(finalForm!=null){
			   finalForm+="&"+rateAuditForm;
		   }else{
			   finalForm = rateAuditForm;
		   }
		}
		if($('#confirmSignForm').length!=0){
			confirmSignForm = $('#confirmSignForm').serialize();
			if(finalForm!=null){
				   finalForm+="&"+confirmSignForm;
			   }else{
				   finalForm =confirmSignForm;
			   }
		}
		if($('#contractCreateForm').length!=0){
			contractCreateForm = $('#contractCreateForm').serialize();
			if(finalForm!=null){
				   finalForm+="&"+contractCreateForm;
			   }else{
				   finalForm =contractCreateForm;
			   }
		}
		if($('#contractAuditForm').length!=0){
			contractAuditForm = $('#contractAuditForm').serialize();
			if(finalForm!=null){
				   finalForm+="&"+contractAuditForm;
			   }else{
				   finalForm =contractAuditForm;
			   }
		}
		if(finalForm!=null){
			finalForm+="&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl+"&remark="+remark;
		}else{
			finalForm ="&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl+"&remark="+remark; 
		}
		windowLocationHref(ctx+"/car/carContract/checkRate/carSigningCheckHandle?"+finalForm);
	}
	/**
	 * 流程提交、退回
	 * @param queue 队列集合的名称；viewName 返回的视图；response 节点路由；redirectUrl 重定向路径 
	 */
	 function dispatchFlow2(queue,viewName,response,redirectUrl){
		 var url = ctx+"/apply/contractAudit/dispatchFlow?queue="+queue+"&viewName="+viewName+"&response="+response+""
	     window.location =ctx+url;
	 }
	 /**
	  批量取消标识(或金信标识)
	 */
	 function batchCancelFlag(kvParam,reloadParam,kingFlag){
		var KingFlag = true;
		var hasFlag = true;
		if($("input:checkbox[name='prepareCheckEle']:checked").length==0){
			art.dialog.alert("请先选择需要取消标识的数据!");
			return;
		}else{
		   var checkedList =null;
		   $("input:checkbox[name='prepareCheckEle']:checked").each(function(){
			    if(checkedList==null){
			    	checkedList = $(this).val();
			    	if($(this).attr("flag")==''){
			    		hasFlag = false	
			    	}
			    	if(kingFlag=='金信'){
			    	   if($(this).attr("flag")!='金信'){
			    		KingFlag = false;
					  }
			    	}
			    }else{
			    	checkedList+=";"+ $(this).val();
			    	if($(this).attr("flag")==''){
			    		hasFlag = false	
			    	}
			    	if(kingFlag=='金信'){
			    	   if($(this).attr("flag")!='金信'){
			    		KingFlag = false;
					  }
			    	}
			    }
			});
		   if(kingFlag=='金信'){
		    	if(!KingFlag){
		    	  art.dialog.alert("当前只能选择标识为金信的数据!");
				  return;  
		    	}
		    }else{
		    	if(!hasFlag){
		    	  art.dialog.alert("当前存在没有标识的数据,请重新选择!");
				  return;  
		    	}
		    }
		  ajaxHandleFlag(checkedList,kvParam.key,kvParam.value,"");
		  if(cancelFlagRetVal=='1'){
			fetchTaskItems(reloadParam.curForm,
					reloadParam.flagForm);  
		  }else if('0'==cancelFlagRetVal){
			  art.dialog.alert("请求的数据异常!");
		  }
		}
	 }
	 function getImageUrl(loancode){
		 	$.ajax({
		  		type:'post',
		 		url :ctx + '/carpaperless/confirminfo/getLargeAmountImageUrl',
		 		data:{
		 			'loancode':loancode,
		 		},
		 		cache:false,
		 		dataType:'text',
		 		async:false,
		 		success:function(result) {
		 			return result;
		 		},
		 		error : function() {
		 			art.dialog.alert('请求异常！', '提示信息');
		 			return "";
		 		}
		    	});
		 }
	 function largeAmountShow(applyId,loanCode){
			 $.ajax({
			  		type:'post',
			 		url :ctx + '/carpaperless/confirminfo/getLargeAmountImageUrl',
			 		data:{
			 			'loancode':loanCode,
			 		},
			 		cache:false,
			 		dataType:'text',
			 		async:false,
			 		success:function(result) {
			 			 window.open(result, "window",
			 			"width=1000,height=500,status=no,scrollbars=yes");
			 		}
		 });
	 }
	 function protclShow(applyId,loanCode){
		 window.open('http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111"', "window",
			"width=1000,height=500,status=no,scrollbars=yes");
	 }
	 function auditOption(option){
		 var  obj = $('#T1');
		 var remark = $('#remark');
		if(option=='2'){
		   obj.css('display','none');
		   remark.attr('disabled',true);
		}else{
		   obj.css('display','block');
		   remark.attr('disabled',false);
		} 
    }
	 