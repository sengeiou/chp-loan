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
		window.location=ctx+'/apply/contractAudit/fetchTaskItems4ReadOnly?'+srcFormParam;
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
		window.location=ctx+"/apply/contractAudit/fetchTaskItems4ReadOnly?flowFlag="+flowFlag;
		
	}
	function retAuditListNew(flowFlag,menuId){
		window.location=ctx+"/apply/contractAudit/fetchTaskItems4ReadOnly?flowFlag="+flowFlag+"&menuId="+menuId;
		
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
		var message="是否确认取消标识？";
		if($("input:checkbox[name='prepareCheckEle']:checked").length==0){
			art.dialog.alert("请先选择需要取消标识的数据!");
			return;
		}else{
		   var checkedList =null;
		   $("input:checkbox[name='prepareCheckEle']:checked").each(function(){
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
			});
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
		   art.dialog.confirm(message,function(){
		   	ajaxHandleFlag(checkedList,kvParam.key,kvParam.value,"");
		   	if(cancelFlagRetVal=='1'){
		   		fetchTaskItems(reloadParam.curForm,
					reloadParam.flagForm);  
		   	}else if('0'==cancelFlagRetVal){
		   		art.dialog.alert("请求的数据异常!");
		   	}
		   });
		}
	 }
	 function largeAmountShow(applyId,loanCode,imageUrl){
		 var url = largeAmountImgUrl;
		 //var url = 'http://123.124.20.50:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1;RIGHT:1111111';
		/* window.open('http://123.124.20.50:9099/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:HUIJINSY1;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111', "window",
			"width=1000,height=500,status=no,scrollbars=yes");*/
		 art.dialog.open(url, {  
			   id: 'largeAmount',
			   title: '大额查看',
			   lock:false,
			   width:700,
			   height:350
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
