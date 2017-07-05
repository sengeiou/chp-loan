	/**
	清除查询表单中的数据项
	 一定要加[0]
	 */
	function queryFormClear(tarForm) {
		 $(':input','#'+tarForm)
		  .not(':button, :reset')
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
	function viewAuditHistory(loanCode) {
		showAllHisByLoanCode(loanCode);
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
	function dealTaskNew(applyId, wobNum, dealType, token,stepName,menuId) {
		/**添加状态判断，如果不是待审核合同不让输。防止退回后点击浏览器后退按钮重新办理*/
		/*$.ajax({
			url:ctx+"/apply/contractAudit/getStatus",
			type:'post',
			data:{applyId:applyId},
			dataType : 'json',
			async : false,
			success:function(result){
				if(result.msg=="true"){
					var url = ctx + "/bpm/flowController/openForm?applyId=" + applyId
					+ "&wobNum=" + wobNum + "&dealType=" + dealType + "&token="
					+ token+"&stepName="+stepName+"&menuId="+menuId;
					window.location = url;
				}else{
					art.dialog.alert('合同已办理');
				}
			}
		})*/
		var url = ctx + "/bpm/flowController/openForm?applyId=" + applyId
				+ "&wobNum=" + wobNum + "&dealType=" + dealType + "&token="
				+ token+"&stepName="+stepName+"&menuId="+menuId;
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
	function reckonFee(curRate,loanCode){
		if(curRate!=''){
			var srcFormParam = $('#rateAuditForm').serialize();
			srcFormParam+="&ctrFee.feeMonthRate="+curRate+"&contract.loanCode="+loanCode;
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
					$('#feeUrgedService').val(result.feeInfo.feeUrgedService);
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
					$('#monthFeeService2').val(result.feeInfo.monthFeeService);
					$('#month_fee_service1').val(result.ctrFee.monthFeeService);
					$('#monthFeeService1').val(result.feeInfo.monthFeeService);
					$('#month_fee_service2').val(result.ctrFee.monthFeeService);
					$('#monthFeeConsult').val(result.feeInfo.monthFeeConsult);
					$('#month_fee_consult').val(result.ctrFee.monthFeeConsult);
					$('#monthMidFeeService').val(result.feeInfo.monthMidFeeService);
					$('#month_mid_fee_service').val(result.ctrFee.monthMidFeeService);
					$('#monthPayTotalAmount').val(result.feeInfo.monthPayTotalAmount);
					$('#month_pay_total_amount').val(result.contract.monthPayTotalAmount);
					$('#feeExpedited').val(result.feeInfo.feeExpedited);
					$('#fee_expedited').val(result.ctrFee.feeExpedited);
					$('#monthRateService').val(result.ctrFee.monthRateService);
					$('#comprehensiveServiceRate').val(result.ctrFee.comprehensiveServiceRate);
				},
				error:function(){
					art.dialog.alert('【计算费率】异常');
				}
			
			});
		}
	}
	function batchAddTGFlag(tgParam,reloadParam){
		var param="";
		var hasFlag = false;
		var indexFlag = null;
		$("input[name='prepareCheckEle']:checked").each(function(){
			indexFlag = $(this).attr("flag");
			if(indexFlag!=null && indexFlag.trim()!='' && indexFlag!='TG'){
				hasFlag = true;
			}
			if(param!=""){
			    param+=";"+$(this).val();
			}else{
				param=$(this).val();	
			}
		});
		if(param==''){
			art.dialog.alert("请先选择需要添加标识的数据!");
			return false;
		}
		if(hasFlag){
			art.dialog.alert("选中的数据中存在其它类型的标识，请重新选择!");
			return false;
		}else{
			ajaxHandleFlag(param,tgParam.key,tgParam.value,tgParam.label);
			if(cancelFlagRetVal=='1'){
				fetchTaskItems(reloadParam.curForm,
						reloadParam.flagForm);  
			  }else if('0'==cancelFlagRetVal){
				  art.dialog.alert("请求的数据异常!");
			  }
		}
	}
	
	function batchRejectApply(attrName){
		  var hasNoFrozenFlag = false;
		  var param = "";
		  var statusList = "";
		  if($("input[name='prepareCheckEle']:checked").length==0){
			 
			  art.dialog.alert("请选择需要驳回的数据!");
			  return false;
		  }
		$("input[name='prepareCheckEle']:checked").each(function(){
			
			frozenFlag = $(this).attr("frozenFlag");
			if(frozenFlag!="1"){
				hasNoFrozenFlag = true;
			}
			if(param!=""){
			    param+=";"+$(this).val();
			}else{
				param=$(this).val();	
			}
			if(statusList!=''){
				statusList += ";"+$(this).attr("loanStatus");
			}else{
				statusList = $(this).attr("loanStatus");
			}
		});
		if(hasNoFrozenFlag){
			art.dialog.alert("选中的数据中存在非冻结数据，请重新选择!");
			return false;
		}else{
			art.dialog({
				content: document.getElementById("rejectFrozen"),
				title:'驳回申请',
				fixed: true,
				lock:true,
				okVal: '确认',
				ok: function () {
					var remark = $("#rejectReason").val();
					if (remark == null || remark=='') {
						art.dialog.alert("请输入驳回原因!");
					}else{					
						art.dialog.confirm('是否确定执行驳回冻结申请操作', function (){
							 rejectApply(param,attrName,statusList,remark);
							 if(cancelFlagRetVal=='1'){
									fetchTaskItems(reloadParam.curForm,
											reloadParam.flagForm);  
							 }else if('0'==cancelFlagRetVal){
								  art.dialog.alert("请求的数据异常!");
							}
						});
					}
				return false;
				},
				cancel: true
			});
			
		}
	}
	
	/**
	 *驳回申请 
	 *@param param 
	 *@param attrName
	 *@param statusList 
	 * 
	 */
	function rejectApply(param,attrName,statusList,remark){
		$.ajax({
			type : 'post',
			url : ctx + '/apply/contractAudit/asynChangeFlag',
			data : {
				'batchColl':param,
				'attributName':attrName,
				'statusList':statusList,
				'remark':remark
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
		      cancelFlagRetVal = result;
			},
			error : function() {
				art.dialog.alert('请求异常！', '提示信息');
			}
		});
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
				art.dialog.alert('请求异常！', '提示信息');
			}
		});
	}
function openAddFlagDialog(attributeName,flowFlag,redirectUrl){
	var param="";
	var hasFlag = false;
	$("input[name='prepareCheckEle']:checked").each(function(){
		if(param!=""){
		    param+=";"+$(this).val();
		}else{
			param=$(this).val();	
		}
		if($(this).attr('flag')!='' && $(this).attr('flag').trim()!='' && $(this).attr('flag')!=null){
			hasFlag = true;
		}
	});
	if(param==""){
	    art.dialog.alert("请先选择需要添加标识的数据!");
		return false;
	}
	if(hasFlag){
		art.dialog.alert("选中的数据中存在标识不为空的数据,请先取消!");
		return false;
	}
	 var url=ctx + '/apply/contractUtil/openAddFlagDialog?batchColl='+param+"&attributeName="+attributeName+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl;
	 art.dialog.open(url, {  
         id: 'flag',
         title: '渠道选择',
         lock:true,
         width:700,
     	 height:200
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
function backFlowMenuId(viewName,flowFlag,redirectUrl,targetForm,menuId){
	var param = $('#'+targetForm).serialize();
	 var url=ctx + '/apply/contractUtil/openGrantDialog?viewName='+viewName+"&"+param+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl+"&menuId="+menuId;
	 art.dialog.open(url, {  
         id: 'his',
         title: '退回!',
         lock:true,
         width:700,
     	 height:350
     },  
     false);
	}
/**
 *批量退回 
 * 
 */
function backListFlow(viewName,flowFlag,redirectUrl,menuId,param,fromSubmit){
	var url=ctx + '/apply/contractUtil/openBackList?viewName='+viewName+"&flowFlag="+flowFlag+"&redirectUrl="+redirectUrl+"&menuId1="+menuId+"&param="+param+"&fromSubmit="+fromSubmit;
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
		history.go(-1);
		
	}
	function retAuditListNew(flowFlag,menuId){
		history.go(-1);
		
	}
	/**
	 * 流程提交、退回
	 * @param queue 队列集合的名称；viewName 返回的视图；response 节点路由；redirectUrl 重定向路径 
	 */
	function dispatchFlow(flowFlag,response,redirectUrl,type){
		var loanapplyForm = $('#loanapplyForm').serialize()+"&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl;
	    var url =  ctx+"/apply/contractAudit/dispatchFlow";
	    
		if('rateAudit'==type){
	    	loanapplyForm+="&"+$('#rateAuditForm').serialize();
	    	$('#rateForm').attr('action',url+"?"+loanapplyForm);
	    	$('#rateForm').submit();
	    }else if('confirmSign'==type){
	    	$('#confirmSignForm').attr('action',url);
	    	$('#confirmSignForm').submit();
	    }else if('contractCreate'==type){
	    	$('#contractCreateForm').attr('action',url+"?"+loanapplyForm);
	    	$('#contractCreateForm').submit();
	    }else if('contractAudit'==type){
	    	$('#contractAuditForm').attr('action',url+"?"+loanapplyForm);
	    	$('#contractAuditForm').submit();
	    }
/*
		if(finalForm!=null){
			finalForm+="&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl;
		}else{
			finalForm ="&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl; 
		}
		window.location = ctx+"/apply/contractAudit/dispatchFlow?"+finalForm;*/
	}
	
	/**
	 * 流程提交、退回
	 * @param queue 队列集合的名称；viewName 返回的视图；response 节点路由；redirectUrl 重定向路径 
	 */
	function dispatchFlowWithPermission(flowFlag,response,redirectUrl,type,menuId){
		var loanapplyForm = $('#loanapplyForm').serialize()+"&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl+"&menuId="+menuId;
	    var url =  ctx+"/apply/contractAudit/dispatchFlow";
	   
		if('rateAudit'==type){
	    	loanapplyForm+="&"+$('#rateAuditForm').serialize();
	    	$('#rateForm').attr('action',url+"?"+loanapplyForm);
	    	$('#rateForm').submit();
	    }else if('confirmSign'==type){
	    	$('#confirmSignForm').attr('action',url);
	    	$('#confirmSignForm').submit();
	    }else if('contractCreate'==type){
	    	$('#contractCreateForm').attr('action',url+"?"+loanapplyForm);
	    	$('#contractCreateForm').submit();
	    }else if('contractAudit'==type){
	    	$('#contractAuditForm').attr('action',url+"?"+loanapplyForm);
	    	$('#contractAuditForm').submit();
	    }
	}
	
	 //合同审核流程提交、退回
	function dispatchFlowWithPermissionContractAudit(flowFlag,response,redirectUrl,menuId){
		var loanapplyForm = $('#loanapplyForm').serialize()+"&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl+"&menuId="+menuId;
	    var url =  ctx+"/contractAuditModule/commitContractAudit";
	    $('#contractAuditForm').attr('action',url+"?"+loanapplyForm);
	    $('#contractAuditForm').submit();
	}
	
	/**
	 * 手动验证提交、退回
	 * @param queue 队列集合的名称；viewName 返回的视图；response 节点路由；redirectUrl 重定向路径 
	 */
	function dispatchFlowWithPermissionNew(flowFlag,response,redirectUrl,type,menuId){
		
		var loanapplyForm = $("#contractAuditForm").serialize()+$('#loanapplyForm').serialize()+"&flowFlag="+flowFlag+"&response="+response+"&redirectUrl="+redirectUrl+"&menuId="+menuId;
	    /*var url =  ctx+"/apply/contractAudit/dispatchFlowVer";
	    $('#contractAuditForm').attr('action',url+"?"+loanapplyForm);
    	$('#contractAuditForm').submit();*/
    	
    	$.ajax({
    		type : 'post',
    		url : ctx+'/apply/contractAudit/dispatchFlowVer',
    		data :loanapplyForm,
    		dataType:'json',
    		cache : false,
    		success : function(result) {
    			if(result.message=='手动验证通过'){
    				//art.dialog.alert(result.message);
    				$('#dispatchFlowBtnNew').attr('disabled',true);
    				$(":radio[name='verification']").attr("disabled","disabled");
    			}else if(result.message=='手动验证不通过操作成功'){
    				/*art.dialog.alert(result.message,function(){
        				window.location.href=ctx+"/apply/contractAudit/fetchTaskItems?flowFlag=CTR_AUDIT";
        		  	});*/
    				window.location.href=ctx+"/apply/contractAudit/fetchTaskItems?flowFlag=CTR_AUDIT";
    			}
    		},
    		error : function(result) {
    			art.dialog.alert('请求异常！');
    		}
    	});
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
		var tgFlag = true;
		var hasFlag = true;
		var kingCount = 0;
		var successCount=0;
		var errorCount=0;
		var message="是否确认取消标识？";
		var replyProductName = false;
		var NXD =false;
		if($("input:checkbox[name='prepareCheckEle']:checked").length==0){
			art.dialog.alert("请先选择需要取消标识的数据!");
			return;
		}else{
		   var checkedList =null;
		   $("input:checkbox[name='prepareCheckEle']:checked").each(function(){
			   
			    if($(this).attr("replyProductName")=="农信借"){
					NXD = true;
					return false;
				}
				if(kingFlag!=null && $(this).attr("replyProductName")=="信易借"){
					replyProductName=true;
					return false;
				}
				if($(this).attr("flag")=="财富"){
					 errorCount++;
					return true;
				}
		    
			    if(checkedList==null){
			    	checkedList = $(this).val();
			    	if($(this).attr("flag")=='' || $(this).attr("flag").trim()==''){
			    		hasFlag = false	
			    	}
			    	if(kingFlag=='金信'){
			    	   if($(this).attr("flag")!='金信'){
			    		KingFlag = false;
					  }else{
						  kingCount++; 
					  }
			    	}
			    	if(kingFlag=='TG'){
			    		if($(this).attr("flag")!='TG'){
			    			tgFlag = false;
						  }
			    	}
			    }else{
			    	checkedList+=";"+ $(this).val();
			    	if($(this).attr("flag")=='' || $(this).attr("flag").trim()==''){
			    		hasFlag = false	
			    	}
			    	if(kingFlag=='金信'){
			    	   if($(this).attr("flag")!='金信'){
			    		KingFlag = false;
					  }else{
						  kingCount++; 
					  }
			    	}
			    	if(kingFlag=='TG'){
			    		if($(this).attr("flag")!='TG'){
			    			tgFlag = false;
						  }
			    	}
			    }
			    successCount++;
			    
			});
		   if(NXD){
				art.dialog.alert('农信借的产品不能修改渠道,请重新选择!');
				return false;
			}
			if(replyProductName){
				art.dialog.alert('信易借的产品不能修改渠道,请重新选择!');
				return false;
			}
		   if(kingFlag=='金信'){
		    	if(!KingFlag){
		    	  art.dialog.alert("当前只能选择标识为金信的数据!");
				  return;  
		    	}
		    }else if(kingFlag=='TG'){
	    		if(!tgFlag){
	    			art.dialog.alert("当前只能选择标识为TG的数据!");
					  return;
				  }
	    	}else{
		    	if(!hasFlag){
		    	  art.dialog.alert("当前存在没有标识的数据,请重新选择!");
				  return;  
		    	}
		    }
		   if(kingFlag=='金信'){
			  message="共"+kingCount+"条金信数据，"+message;
		   }
		   if(successCount==0){
			   art.dialog.alert("符合条件取消"+successCount+"条数据,不符合条件取消"+errorCount+"条数据");
			   return false;
		   }
		   art.dialog.confirm(message,function(){
		   	ajaxHandleFlag(checkedList,kvParam.key,kvParam.value,"");
		   	if(cancelFlagRetVal=='1'){
		   		fetchTaskItems(reloadParam.curForm,
					reloadParam.flagForm); 
		   		art.dialog.alert("符合条件取消"+successCount+"条数据,不符合条件取消"+errorCount+"条数据");
		   	}else if('0'==cancelFlagRetVal){
		   		art.dialog.alert("请求的数据异常!");
		   	}
		   });
		}
	 }
	 function largeAmountShow(applyId,loanCode,imageUrl){
		 var url = largeAmountImgUrl
		 //var url = 'http://123.124.20.50:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1;RIGHT:1111111';
		/* window.open('http://123.124.20.50:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', "window",
			"width=1000,height=500,status=no,scrollbars=yes");*/
		 art.dialog.open(url, {  
			   id: 'largeAmount',
			   title: '大额查看',
			   lock:false,
			   width:700,
			   height:350,
			   close:function(){
				   	var url=$('#ckfinder_iframe').attr('src');
					document.getElementById('ckfinder_iframe').src=url;
			   }
			},false); 
	 }
	 function protclShow(applyId,loanCode,contractCode){
		 var url =ctx+"/apply/contractAudit/initPreviewContract?contractCode="+contractCode;
	     art.dialog.open(url, {  
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:800,
			   height:'95%',
			   left:'0%',
			   top:'0%',
			   resize:true
			},false); 
	 }
	 function signDocShow(applyId,loanCode,contractCode){
		 var url =ctx+"/apply/contractAudit/initPreviewSignContract?contractCode="+contractCode;
	     art.dialog.open(url, {  
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:'95%',
			   height:'95%',
			   left:'0%',
			   top:'0%',
			   resize:true
			},false); 
	 }
	 function auditOption(option){
		 var  obj = $('#T1');
		 var remark = $('#remark');
		if(option=='2'){
		   obj.css('display','none');
		   $('#backFlowNote').val("");
		   $('#backFlowNote').trigger("change");
		   remark.attr('disabled',true);
		   $('#remark').val("");
		}else{
		   obj.css('display','block');
		   remark.attr('disabled',false);
		   $('#remark').select2();
		} 
    }
	 //手动验证的js
	 function auditOptionVer(option){
		 var  obj = $('#T2');
		 var returnReason = $('#returnReason');
		if(option=='2'){
		   obj.css('display','none');
		   $('#backFlowNoteNew').val("");
		   $('#backFlowNoteNew').trigger("change");
		   returnReason.attr('disabled',true);
		   returnReason.val("");
		   showVerification();
		}else{
		   obj.css('display','block');
		   returnReason.attr('disabled',false);
		   $("#dispatchFlowBtnNew").attr('disabled',false);
		} 
    } 
// 有效时间判断
jQuery.validator.addMethod("effectiveDay", function(value, element) {
	var isOK = true;
	var dueDaySrc = $('#signEndDay').val();
	var startDaySrc = $('#signStartDay').val();
	dueDay = dueDaySrc.replace(new RegExp(/-/g),'');
	startDay = startDaySrc.replace(new RegExp(/-/g),'');
	var selectDay = value.replace(new RegExp(/-/g),'');
	if(parseInt(startDay)>parseInt(selectDay)){
		jQuery.validator.messages.effectiveDay = "合同签订预约日期不能早于"+startDaySrc;
		isOK = false;
	}
	if(isOK && parseInt(dueDay)<parseInt(selectDay)){
		jQuery.validator.messages.effectiveDay = "合同签订的截止日期为"+dueDaySrc;
		isOK = false;
	}
	
     return isOK;
 }, ""); 
function openContractSummary(){
	var url = ctx+"/apply/contractAudit/initSummary";
	 art.dialog.open(url, {  
		   id: 'protcl',
		   title: '占比查看',
		   lock:false,
		   width:700,
		   height:350
		},false); 
}
function switchBackNode(){
	var backNodeMessage = $('#backFlowNote option:selected').text();
	if(backNodeMessage=='待合同签订'){
	    $("#chooseItem").css('display','block'); 
	}else{
		$("#chooseItem").css('display','none'); 
	}
}

function switchBackNodeNew(){
	var backNodeMessage = $('#backFlowNote option:selected').text();
	if(backNodeMessage=='待合同签订'){
	    $("#chooseItem").css('display','block'); 
	}else{
		$("#chooseItem").css('display','none'); 
	}
}

function switchVerfication(){
	//$("#dispatchFlowBtnNew").removeAttr('disabled');
}

function fetchTaskItemsSubmit(srcForm,flagForm) {//queue='HJ_CONTRACT_COMMISSIONER' ，viewName = _rateAudit_workItems
	
	var srcFormParam = $('#' + srcForm).serialize();
	srcFormParam +="&"+$('#' + flagForm).serialize();
	return srcFormParam;
}

function splitView(){
	var param="";
	 $("input:checkbox[name='prepareCheckEle']:checked").each(function(){
		if(param!=""){
		    param+=";"+$(this).val();
		}else{
			param=$(this).val();	
		}
	 });
	 var url=ctx + '/apply/contractAudit/splitView?param='+ encodeURI(encodeURI(param));
	 art.dialog.open(url, {  
         id: 'flag',
         title: '信息平台占比分配',
         lock:true,
         width:400,
     	 height:300
     },  
     false);
}
function splitViewHis(){
	 var url=ctx + '/apply/contractAudit/splitViewHis';
	 art.dialog.open(url, {  
        id: 'flag',
        title: '占比历史',
        lock:true,
        width:600,
    	 height:300
    },  
    false);
}
/**
 *保存占比分配 
 *@param param 
 *@param attrName
 *@param statusList 
 * 
 */
function saveSplit(param,zcj,jinxin){
	if(zcj==null || zcj==''){
		art.dialog.alert('大金融占比不能为空！', '提示信息');
		return false;
	}else if(jinxin==null || jinxin==''){
		art.dialog.alert('金信占比不能为空！', '提示信息');
		return false;
	}else if(parseFloat(zcj)+parseFloat(jinxin)!=100){
		art.dialog.alert('占比之和必须为100！', '提示信息');
		return false;
	}else{
		//art.dialog.close();
		$("#saveBtn").attr({"disabled":"disabled"});
		$("#splitFormClrBtn").attr({"disabled":"disabled"});
		var win = art.dialog.open.origin;  
		win.uiclick();
		$.ajax({
			type : 'post',
			url : ctx + '/apply/contractAudit/SaveSplit',
			data : {
				'param':param,
				'zcj':zcj,
				'jinxin':jinxin
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result==true){
					var win = art.dialog.open.origin;  
					art.dialog.alert("请求成功！", '提示信息');				   
				    win.location.reload();
				}else{
					 win.location.reload();
					art.dialog.alert('请求异常！', '提示信息');
				}
				
			   
			},
			error : function() {
				 win.location.reload();
				art.dialog.alert('请求异常！', '提示信息');
			}
		});
	}	
}

