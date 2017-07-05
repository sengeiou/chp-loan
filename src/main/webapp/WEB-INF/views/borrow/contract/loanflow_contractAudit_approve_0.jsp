<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />

<script src="${context}/js/contract/contractAuditFor.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/lightbox/jquery.lightbox.js"></script>
<link rel="stylesheet" href="${ctxStatic}/lightbox/css/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript">
var imgURL ="${workItem.bv.imageUrl}";
var largeAmountImgUrl = "${workItem.bv.largeAmountImageUrl}";
var protocolImgUrl = "${workItem.bv.protocolImgUrl}";
  cancelFlagRetVal = '0';
  kvParam={
	key:'loanFlag',
	value:' ',
	label:' '
  };
  dispatchParam={
	 flowFlag:'CTR_AUDIT',
	 response:'TO_PAY_CONFIRM',
	 redirectUrl:'/contractAuditModule/searchContractAuditDatas'
  };
  $(document).ready(function(){
  /*  if($('#channelName').val()=='财富' || $("#productName").val()=='信易借'){
	   $('#cancelFlagBtn').attr('disabled',true);  
   }else if($('#channelName').val()=='ZCJ'){
	   $('#cancelFlagBtn').hide();  
   }else{	  
   
		$('#cancelFlagBtn').bind('click',function(){
			art.dialog.confirm('是否确定执行渠道取消操作',function (){
		  		ajaxHandleFlag($('#flagCancelParam').val(),kvParam.key,kvParam.value,kvParam.label);
	 	 		if(cancelFlagRetVal=='1'){
		 			art.dialog.alert('渠道更新成功'); 
		 			$('#upLimit').val(""); 
		 			$('#channelCode').val('2');
		 			$('#channelName').val('财富');
		 			$('#loanFlag').val('财富');
		 	 		$('#cancelFlagBtn').attr('disabled',true);
	 	 		 }else if(cancelFlagRetVal=='0'){
		  			art.dialog.alert('渠道更新失败'); 
	  			}
			});
		});
   }*/
  	if($('#largeAmountBtn').length>0){
		$('#largeAmountBtn').bind('click',function(){
			largeAmountShow($(this).attr('applyId'),$(this).attr('loanCode'),imgURL);
		}); 
	}  
	$("#protclBtn").bind('click',function(){
  		protclShow($(this).attr('applyId'),$(this).attr('loanCode'),$(this).attr('contractCode'));
	
	});
	$('#history').bind('click',function(){
		viewAuditHistory($(this).attr('loanCode'));
	});
	$(":radio[name='dictOperateResult']").each(function(){
		$(this).bind('click',function(){
			auditOption($(this).attr('targetFlag'));
		});
	});
	
	//手动验证的js
	$(":radio[name='verification']").each(function(){
		$(this).bind('click',function(){
			 auditOptionVer($(this).attr('targetFlag')); 
		});
	});
	
	$('#dispatchFlowBtn').bind('click',function(){
		var loanCode=$("#loanCodeTwo").val();
		var isExistVerification=$("#isExistVerification").val();
		if(isExistVerification=='0'){
			//验证手动验证是否完成
			 $.ajax({
					type : 'post',
					url : ctx + '/apply/contractAudit/verification',
					data : {
						'loanCode': loanCode
					},
					cache : false,
					dataType : 'text',
					async : false,
					success : function(result) {
				      if(result!='2'){
				    	  art.dialog.alert('请先进行手动验证！', function(){
				    	  });
				    	  return ;
				      }else{
				    	  commitContract();
				      }
					},
					error : function() {
						art.dialog.alert('手动验证异常！', '提示信息');
					}
				});
		}else{
			//没有手动验证的合同审核提交
			commitContract();
		}
		
		
	});
	
	
	//点击手动验证确定按钮
	$('#dispatchFlowBtnNew').bind('click',function(){
		var msg = "是否确定执行提交操作";
		var response="";
		var verificationChecked = $("input[name='verification']:checked").length;
		if(verificationChecked==0){
			$('#dictOperateResultMessageNew').html("请选择手动验证结果");
			return false;
		}else{
			$('#dictOperateResultMessageNew').html('');
		}
		var verification = $("input[name='verification']:checked").val();
		if(verification=='0'){
			 var backFlowNoteNew = $('#backFlowNoteNew option:selected').val();
			 var backFlowNoteNewText = $('#backFlowNoteNew option:selected').text();
			 if(backFlowNoteNew==""){
				 $('#backFlowNoteMessageNew').html("请选择退回节点");
				 return false;
			 }else{
				 response = backFlowNoteNew; 
				 if($('#returnReason').val()==''){
				      $('#returnReasonMessage').html('请输入退回原因');
				      return false;
				    }else{
				      $('#returnReasonMessage').html(''); 
				    } 
					if($('#returnReason').val().length>500){
						  $('#returnReasonMessage').html("退回原因不能超过500字");
						  return false;
					  }else{
						  $('#returnReasonMessage').html('');
					  }
				 $('#backFlowNoteMessageNew').html('');
			 }
		}else{
			$('#backFlowNoteMessageNew').html('');
			$('#returnReasonMessage').html('');
		}
		art.dialog.confirm(msg,function (){
			dispatchFlowWithPermissionNew(dispatchParam.flowFlag,response,dispatchParam.redirectUrl,'contractAudit','${param.menuId}');
			
		},function(){
			//retAuditList('CTR_AUDIT');
	    	//$('#dispatchFlowBtnNew').attr('disabled',true);
		});
	});
	
	$('.enlarge').each(function(){
		$(this).bind({
			mouseover:function(){
				if(document.getElementById("FDJ").checked){ 
				   var layer1 = $('#fdj');
				   layer1.css('left',(window.event.clientX)+'px'); 
				   layer1.css('top',window.event.clientY + 'px'); 
				   layer1.html("<font size=8 color='red'>" +$(this).html()+"</font>"); 
				   layer1.css('display','block'); 
				}	
			},  
		    mouseout:function(){
		    	var layer1 = $('#fdj');
		    	layer1.html('');
		    layer1.css('display','none'); 
		    }	
			
		});
		
	});  
	$(".lightbox").lightbox({
	    fitToScreen: true,
	    imageClickClose: false
    });
	$(".lightbox-2").lightbox({
	    fitToScreen: true,
	    scaleImages: true,
	    xScale: 1.2,
	    yScale: 1.2,
	    displayDownloadLink: false,
		imageClickClose: false
    });
	$('#retBtn').bind('click',function(){
		retAuditListNew('CTR_AUDIT','${param.menuId}');
	});
	  $('#refresh').bind('click',function(){
		  var url=$('#ckfinder_iframe').attr('src');
		  document.getElementById('ckfinder_iframe').src=url;
	  });
	//验证是否手动验证过
	checkVerification();
	//手动验证原因是验证身份信息不在查询范围内  添加标示注意检查户籍证明
	getVerification();
	//已经手动验证通过，就需要回显
	showVerification();
  });
  
  function getVerification(){
	  $.ajax({
			type : 'post',
			url : ctx + '/paperless/confirminfo/getVerification',
			data : {
				'loanCode':$('#loanCodeTwo').val()
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result.flag=='0'){
					if(result.failCode=='2014'){
						$('#verificateInfo').html('*主借人：注意申请邮件，注意检查户籍证明');
					}else if((result.failCode=='2012')||(result.failCode=='0000')){
						$('#verificateInfo').html('*主借人：注意申请邮件');
					}
				}
				if(result.flaggongjie=='0'){
					if(result.failCodeGongjie=='2014'){
						$('#verificateInfogongjie').html('*自然人保证人：注意申请邮件，注意检查户籍证明');
					}else if((result.failCodeGongjie=='2012')||(result.failCodeGongjie=='0000')){
						$('#verificateInfogongjie').html('*自然人保证人：注意申请邮件');
					}
				}
			},
			error : function() {
				art.dialog.error('请求异常！', '提示信息');
			}
		});
  }
  //手动验证通过，需要回显
  function showVerification(){
	  $.ajax({
			type : 'post',
			url : ctx + '/apply/contractAudit/verification',
			data : {
				'loanCode': $('#loanCodeTwo').val()
			},
			cache : false,
			dataType : 'text',
			async : false,
			success : function(result) {
		      if(result=='2'){
		    	 $("#verificateTrue").attr('checked','checked');
		    	 $("#returnReason").attr('disabled',true);
		    	 $('#dispatchFlowBtnNew').attr('disabled',true);
		    	 $(":radio[name='verification']").attr("disabled","disabled");
		      }
			},
			error : function() {
				art.dialog.alert('手动验证异常！', '提示信息');
			}
		});
  }
  //检查是否进行手动验证
  function checkVerification(){
	  $.ajax({
			type : 'post',
			url : ctx + '/paperless/confirminfo/getVerificationByIdCard',
			data : {
				'loanCode':$('#loanCodeTwo').val()
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if(result.flag=='0'){
					$('#isExistVerification').val("0");
					$("#verqq").show();
					
				}else{
					$('#isExistVerification').val("1");
					$("#verqq").hide();
				}
			},
			error : function() {
				art.dialog.error('请求异常！', '提示信息');
			}
		});
  }
  //合同审核提交操作
  function commitContract(){
	  var msg = "是否确定执行提交操作";
		var auditResultChecked = $("input[name='dictOperateResult']:checked").length;
		if(auditResultChecked==0){
			$('#dictOperateResultMessage').html("请选择审核结果");
			return false;
		}else{
			$('#dictOperateResultMessage').html("");
		}
		var auditResult = $("input[name='dictOperateResult']:checked").val();
		if(auditResult=='0'){
			 var backFlowNote = $('#backFlowNote option:selected').val();
			 var backFlowNoteText = $('#backFlowNote option:selected').text();
			 if(backFlowNote==""){
				 $('#backFlowNoteMessage').html("请选择退回节点");
				 return false;
			 }else{
				 dispatchParam.response = backFlowNote; 
				 if(backFlowNoteText=='待合同签订'){
					if($('#chooseRemark option:selected').val()==''){
						$('#chooseItemMessage').html('请选择退回原因');
						return false;
					}else{
						$('#chooseItemMessage').html('');
					} 
				 }else{
					if($('#remark').val()==''){
				      $('#remarkMessage').html('请输入退回原因');
				      return false;
				    }else{
				      $('#remarkMessage').html(''); 
				    } 
					if($('#remark').val().length>500){
						  $('#remarkMessage').html("退回原因不能超过500字");
						  return false;
					  }else{
						  $('#remarkMessage').html("");
					  }
				 }
				 $('#backFlowNoteMessage').html("");
			 }
		}else{
			$('#backFlowNoteMessage').html("");
			$('#remarkMessage').html('');
			if($('#frozenCode').val()!=null  && $('#frozenCode').val().trim()!=''){
				msg = "合同编号为"+$('#contractCode').val()+"的客户门店已申请冻结";
			}
			var channelName = $('#channelName').val();
			var trusteeshipFlag = $('#trusteeshipFlag').val();
			var modelName = $('#modelName').val();
			if( modelName=='TG' && trusteeshipFlag=='0'){
				dispatchParam.response = 'TO_KING_OPEN';
			}else{
			    var upLimit = $('#upLimit').val(); 
			    var limitId=$('#limitId').val();//额度id
			    var feePaymentAmount=$('#feePaymentAmount').val();//实放金额
			    if(limitId!="" && limitId!=null){
				    //ajax 校验金信额度
				    $.ajax({
						type : 'post',
						url : ctx + '/apply/contractAudit/checkLimit',
						data : {
							'jxId':limitId,
							'feePaymentAmount':feePaymentAmount
						},
						cache : false,
						dataType : 'text',
						async : false,
						success : function(result) {
					      upLimit=result;
					      $('#upLimit').val(result);
						},
						error : function() {
							art.dialog.alert('金信额度验证异常！', '提示信息');
						}
					});
			    }
			    if(upLimit=='1'){
			    	if($('#frozenCode').val()!=null  && $('#frozenCode').val().trim()!=''){
						msg += "，已超金信限额，是否取消金信渠道标识？";
					}else{
			    		msg="已超金信限额，是否取消金信渠道标识？";
					}
			    }
				dispatchParam.response = 'TO_PAY_CONFIRM';
			}
		}
		
		art.dialog({
			title:'合同审核',
		    content: msg,
		    button:[
		            {
		            	name:'确认',
		            	focus:true,
		            	callback:function(){
		            		var upLimit = $('#upLimit').val(); 
		    			    if(upLimit=='1'){
		    			    	$('#channelCode').val('2');
		    			    }
		    			    if(auditResult=='0'){
		    			  		dispatchFlowWithPermission(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'contractAudit','${param.menuId}');
		    			    }else{
		    				    dispatchFlowWithPermissionContractAudit(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'${param.menuId}');
		    			    }
		    				$('#dispatchFlowBtn').attr('disabled',true);
		            	}
		            },
		            	{
		            		name:'取消',
		            		callback:function(){
		            			history.go(-1);
		            		}
		            	},
		            	{
		            		name:'关闭',
		            		callback:function(){}
		            	}
		            ]
		    /* okVal:'确定',
		    lock:true,
		    esc:true,
		    ok: function () {
		    	esc:false;
		    	var upLimit = $('#upLimit').val(); 
			    if(upLimit=='1'){
			    	$('#channelCode').val('2');
			    }
			    if(auditResult=='0'){
			  		dispatchFlowWithPermission(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'contractAudit','${param.menuId}');
			    }else{
				    dispatchFlowWithPermissionContractAudit(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'${param.menuId}');
			    }
				$('#dispatchFlowBtn').attr('disabled',true);
		    },
		    cancelVal: '取消',
		    cancel: function(){
		    	history.go(-1);
		    } */
		}); 
		
		/* art.dialog.confirm(msg,function (){
			var upLimit = $('#upLimit').val(); 
		    if(upLimit=='1'){
		    	$('#channelCode').val('2');
		    }
		    if(auditResult=='0'){
		  		dispatchFlowWithPermission(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'contractAudit','${param.menuId}');
		    }else{
			    dispatchFlowWithPermissionContractAudit(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'${param.menuId}');
		    }
			$('#dispatchFlowBtn').attr('disabled',true);
		},function(){
			retAuditList('CTR_AUDIT');
	    	$('#dispatchFlowBtn').attr('disabled',true);
		}); */
  }
</script>
<style> .input_check+label {display: inline-block;width: 13px;height: 13px;border: 1px solid #fa0505;}
         .input_check:checked+label:after {content: "";position: absolute;left: 3px;width: 9px;height: 6px;
      border: 2px solid #fa0505;border-top-color: transparent;border-right-color: transparent;-ms-transform: rotate(-60deg); 
-moz-transform: rotate(-60deg); 
-webkit-transform: rotate(-60deg); 
transform: rotate(-45deg);}
      .input_check {position: absolute;visibility: hidden;}
      
</style>
</head>
<body>
<div style="position:absolute;top:0;left:0;width:100%;height:95%">
   <div >
     <c:set var="bview" value="${workItem.bv}"/>
      <div class="tright pt5 mb5 pr10">
        <button class="btn btn-small" id="refresh">刷新影像平台</button>
      <!--   <button class="btn btn-small" id="cancelFlagBtn">取消渠道</button> -->
        <c:if test="${bview.largeAmountFlag!='0'}">
       	 <button class="btn btn-small" applyId="${bview.applyId}" loanCode="${bview.loanCode}" id="largeAmountBtn">大额查看</button>
        </c:if>
        <button class="btn btn-small" applyId="${bview.applyId}" contractCode="${bview.contract.contractCode}" loanCode="${bview.loanCode}" id="protclBtn">协议查看</button> 
		<button class="btn btn-small" applyId="${bview.applyId}" loanCode="${bview.loanCode}" id="history">历史</button> 
		<button class="btn btn-small" id="retBtn">返回</button>
      </div>
      </div>
   <div class="control-group" style="float:right;width:39.5%;height:95%;overflow-y:auto">
  <input type="hidden" id="flagCancelParam" value="${bview.applyId},${workItem.wobNum},${workItem.flowName},${workItem.token},${workItem.stepName},${bview.loanCode}"/>
    <form id="loanapplyForm">
      <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
      <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
      <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
      <input type="hidden" value="${workItem.token}" name="token"></input>
      <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
      <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
      <input type="hidden" value="${bview.applyId}" name="applyId"></input>
      <input type="hidden" value="${bview.frozenCode}" id="frozenCode"></input>
      <input type="hidden" value="${bview.upLimit}" name="upLimit" id="upLimit"></input>
      <input type="hidden" value="${bview.jxVersion}" name="jxVersion" id="jxVersion"></input>
      <input type="hidden" value="${bview.limitId}" name="limitId" id="limitId"></input>
      <input type="hidden" value="${bview.jxId}" name="jxId" id="jxId"></input>
     </form>
   <form id="contractAuditForm" method="post">
    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	  <tr>
        <td><label class="lab"><!-- <input type='checkbox' id="FDJ" /> &nbsp;放大镜 </label> --></td>
		<td><label class="lab"/></td>
	  </tr>
	  <tr>
        <td><label class="lab">合同编号：</label><label class="enlarge">${bview.contract.contractCode}</label>
         <input type="hidden" value="${bview.contract.contractCode}" id="contractCode" name="contract.contractCode"></input>
         <input type="hidden" value="${bview.contract.loanCode}" name="contract.loanCode" id="loanCodeTwo"></input>
         <input type="hidden" value="${bview.contract.contractAmount}" name="contractAmount"/>
         <input type="hidden" value="${bview.ctrFee.feePaymentAmount}" name="feePaymentAmount" id="feePaymentAmount"/>
         <input type="hidden" value="${bview.applyId}" name="contract.applyId"></input>
         <input type="hidden" value="${bview.ctrFee.feePaymentAmount}" name="ctrFee.feePaymentAmount"></input>
         <input type="hidden" value="${bview.contract.midId}" name="contract.midId"></input>
         <input type="hidden" value="${bview.ctrFee.feeUrgedService}" name="feeUrgedService"></input>
         <input type="hidden" value="${bview.ctrFee.feeUrgedService}" name="unUrgeService"></input>
         <input type="hidden" value="${bview.loanFlagCode}" name="contract.channelFlag" id="channelCode"/>
         <input type="hidden" value="${bview.loanFlag}" id="channelName"/>
         <input type="hidden" value="${bview.trusteeshipFlag}" id="trusteeshipFlag"/>
         <input type="hidden" value="${bview.modelName}" id="modelName"/>
         <input type="hidden" value="${bview.productName}" id="productName"/>
        </td>
		<td><label class="lab">门店名称：</label><label class="enlarge">${bview.applyOrgName}</label></td>
	  </tr>
	  <tr>
		<td><label class="lab">产品种类：</label>
		  <label class="enlarge">
		   ${bview.productName}
		  </label>
		</td>
		<td><label class="lab" >签约平台：</label>
		    <label class="enlarge">${bview.loanBank.bankSigningPlatformName}</label>
		    
		</td>
	  </tr>
      <tr>
        <td colspan="2" class="bar">
	  </tr>
	   <tr>
        <td>
         <c:if test="${bview.composePhotoId!=null && bview.composePhotoId!=''}">
           <div style="height:100px;width:80px;float:left;position:relative">
            <img src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${bview.composePhotoId}" width="80px" height="100px"/>
              <a href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${bview.composePhotoId}" style="float:right" class="lightbox-2" rel="flowers">
				 <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
		      </a> 
          </div>
         </c:if>
         <c:if test="${bview.appSignFlag=='1' && bview.customerId!=null && bview.customerId!=''}">
          <div style="height:100px;width:80px;float:left;position:relative">
            <img src="${ctx}/paperless/confirminfo/getPreparePhoto?customerId=${bview.customerId}" width="80px" height="100px"/>
              <a href="${ctx}/paperless/confirminfo/getPreparePhoto?customerId=${bview.customerId}" style="float:right" class="lightbox-2" rel="flowers">
				 <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
		      </a> 
          </div>
         </c:if>
        </td>
        <td></td>
      </tr>
	  <tr>
		<td><label class="lab">客户姓名：</label><label class="enlarge">${bview.mainLoaner}
		  <c:if test="${bview.idValidMessage!=null}">
		     &nbsp;&nbsp;&nbsp;<span style="color:red">${bview.idValidMessage}</span>
		  </c:if>
		</label></td>
		<td><label class="lab">证件号码：</label><label class="enlarge">${bview.mainCertNum}</label></td> 
      </tr>
      <tr> 
		<td><label class="lab">性别：</label><label class="enlarge">${bview.mainCertSexName}</label></td>
        <td></td>
      </tr>
      <c:forEach items="${bview.coborrowers}" var="currItem">
           <tr>
        	  <td>
               <c:if test="${currItem.composePhotoId!=null && currItem.composePhotoId!=''}">
                <div  style="height:100px;width:80px;float:left;">
           		 <img src="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${currItem.composePhotoId}" width="80px" height="100px"/>
         	     <a href="${ctx}/paperless/confirminfo/getPreparePhoto?docId=${currItem.composePhotoId}" style="float:right" class="lightbox-2">
				   <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
		         </a>
         	    </div>
         	   </c:if>
         	   <c:if test="${currItem.appSignFlag=='1' && currItem.id!=null && currItem.id!=''}">
         	    <div  style="height:100px;width:80px;float:left;">
           		 <img src="${ctx}/paperless/confirminfo/getPreparePhoto?customerId=${currItem.id}" width="80px" height="100px"/>
        	      <a href="${ctx}/paperless/confirminfo/getPreparePhoto?customerId=${currItem.id}" style="float:right" class="lightbox-2">
				   <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
		         </a>
        	    </div>
        	   </c:if>
        	 </td>
       	     <td></td>
      	    </tr>
			<tr>
			    <td>
			    	<label class="lab">
			    		<c:if test="${bview.loanInfo.loanInfoOldOrNewFlag eq '' || bview.loanInfo.loanInfoOldOrNewFlag eq '0'}">
							共借人姓名：
						</c:if>
						<c:if test="${bview.loanInfo.loanInfoOldOrNewFlag eq '1'}">
							自然人保证人姓名：
						</c:if>
			    	</label>
			    	${currItem.coboName}
			     <c:if test="${currItem.idValidMessage!=null}">
		    		 &nbsp;&nbsp;&nbsp;<span style="color:red">${currItem.idValidMessage}</span>
		  		</c:if>
			    </td>
	            <td><label class="lab">证件号码：</label>${currItem.coboCertNum}<td>
			</tr>
			
			<tr><td><label class="lab">性别：</label>
	          <label class="enlarge">
	            ${currItem.coboSexName}
	             </label>
	             </td>
	          <td></td>
	        </tr>
		</c:forEach>
		<c:if test="${bview.auditEnsureName!=null && bview.auditEnsureName!=''}">		
			
	  <tr>
         <td><label class="lab">
         		<c:if test="${bview.loanInfo.loanInfoOldOrNewFlag == '1'}">
         		保证公司名称：
         		</c:if>
         		<c:if test="${bview.loanInfo.loanInfoOldOrNewFlag != '1'}">
         		保证人：
         		</c:if>
         </label><label class="enlarge">${bview.auditEnsureName}</label></td>
		 <td><label class="lab">法定代表人：</label><label class="enlarge">${bview.auditLegalMan}</label></td>
	  </tr>
	  <tr>
         <td><label class="lab">保证人实际经营场所：</label><label class="enlarge">
         <c:if test="${bview.ensuremanBusinessPlace!=null && bview.ensuremanBusinessPlace!=''}">
       	  ${bview.maddressName}
         </c:if>
         </label></td>
		 <td></td>
	  </tr>
	  </c:if>
      <tr> 
		 <td><label class="lab">开户名：</label>
		     <label class="enlarge">${bview.loanBank.bankAccountName}</label>
		     <c:if test="${bview.loanBank.bankIsRareword==1}">
				<span style="position: relative;color:#fa0505"><input type="checkbox"  checked="checked" class="input_check" disabled="disabled" id="check3"/><label for="check3"></label>生僻字</span>
	    	 </c:if>
		 </td> 
		 <td></td>
	  </tr>
	  <tr>
         <td><label class="lab"></label><label class="enlarge"></label></td>
	  </tr>
	  <tr>
		 <td><label class="lab">还款银行账号：</label><label class="enlarge">${bview.loanBank.bankAccount}</label></td> 
		 <td></td>
	  </tr>
	  <tr>
         <td colspan="2">
           <label class="lab">开户行：</label>
            <label class="enlarge">${bview.loanBank.bankProvinceName} ${bview.loanBank.bankCityName}${bview.loanBank.bankNameLabel}${bview.loanBank.bankBranch}
            </label>
         </td>
	  </tr>
	  <tr>
	    <td>
		 	 <label class="lab">模式：</label>
			   <span id="loanFlag">${bview.modelName}</span>
		</td>
		<td>
    		 <label class="lab">渠道：</label>
			   <span id="loanFlag">${bview.loanFlag}</span>
       </td>
	  </tr>
	  <tr>
         <td colspan="2" class="bar"></td>
      </tr>
      <tr>
         <td><label class="lab">批借金额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.auditAmount}' pattern="#,##0.00"/></label></td>
		 <td><label class="lab">批复期数：</label><label class="enlarge">${bview.contract.contractMonths}</label></td>
	  </tr>
      <tr>
         <td><label class="lab">外访距离：</label> ${bview.item_distance}</td>
		 <td>
			<c:if test="${bview.outside_flag eq ''  || bview.outside_flag eq '0' || bview.outside_flag ==null }">
			 	<span style="position: relative;color:red;"><label for="check">&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp; 免外访</label>
					<input type="checkbox"   disabled="disabled"  checked="checked"/>
				</span>
			</c:if>
		 </td>
	  </tr>
	  <tr>
         <td><label class="lab">外访费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feePetition}' pattern="#,##0.00"/></label></td>
		 <td><label class="lab">加急费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeExpedited}' pattern="#,##0.00"/></label></td>
	  </tr>
	  <tr>
         <td><label class="lab">产品总费率：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeAllRaio}' pattern="#,##0.000"/>%</label></td>
		  <td><label class="lab">风险等级：</label>${bview.riskLevel}</td>
	  </tr>
	  <tr>
         <td colspan="2" class="bar"></td>
      </tr>
      <tr>
         <td><label class="lab">借款利率：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeMonthRate}' pattern="#,##0.000"/>%</label></td>
        
	  </tr>
	  <tr>
         <td><label class="lab">实放金额：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feePaymentAmount}' pattern="#,##0.00"/></label></td>
		 <td><label class="lab">前期综合服务费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeCount}' pattern="#,##0.00"/></label></td>
       </tr>
       <tr>
		 <td><label class="lab">合同金额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.contractAmount}' pattern="#,##0.00"/></label></td>
         <td><label class="lab">分期服务费总额：</label><fmt:formatNumber value='${bview.ctrFee.monthFeeService*bview.contract.contractMonths}' pattern="#,##0.00"/></td>
       </tr>
       <tr>
       	 <td><label class="lab">应还本息：</label><fmt:formatNumber value='${bview.contract.contractMonthRepayAmount}' pattern="#,##0.00"/></td>
		 <td><label class="lab">催收服务费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeUrgedService}' pattern="#,##0.00"/></label></td>
       </tr>
       
	   <tr>
	     <td><label class="lab">分期服务费：</label><fmt:formatNumber value='${bview.ctrFee.monthFeeService}' pattern="#,##0.00"/></td>
	      <td><label class="lab">合同签订日期：</label><label class="enlarge"><fmt:formatDate value="${bview.contract.contractDueDay}" pattern="yyyy-MM-dd"/></label></td>
	   </tr>
	   <tr>
    	 <td><label class="lab">月还金额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.monthPayTotalAmount}' pattern="#,##0.00"/></label></td>
		  <td><label class="lab">起始还款日期：</label><label class="enlarge"><fmt:formatDate value="${bview.contract.contractReplayDay}" pattern="yyyy-MM-dd"/></label></td>
   
	   </tr>
	   <tr>
	     <td><label class="lab">预计还款总额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.contractExpectAmount}' pattern="#,##0.00"/></label></td>
         <td><label class="lab">账单日：</label><label class="enlarge">${bview.billDay}</label></td>
		 
        </tr>
        <tr>
          <td><label class="lab">还款付息方式：</label>
            <label class="enlarge">
             ${bview.contract.dictRepayMethodName}
            </label>
          </td> 
          	<c:if test="${bview.productType eq 'A021'}">
			<td><label class="lab">月利息：</label>
			<fmt:formatNumber value='${bview.ctrFee.monthFee}' pattern="#,##0.00" /></td>
			</c:if>
        </tr>
         <tr>
          <td colspan="2" class="bar">
          	<input type="hidden" id="isExistVerification"/>
          </td>
        </tr>
        <tbody id="verqq">
	    <tr>
          <td colspan="2">
            <label class="lab">手动验证结果：</label>
             <input type="radio" name="verification" value="2" targetFlag='2' class="ml10 mr6" onchange="switchVerfication();" id="verificateTrue"/>通过 
             <input type="radio" targetFlag="0" value="0" name="verification"  class="ml10 mr6" onchange="switchVerfication();"/>不通过退回
             <span style="color:red" id="verificateInfo"></span>
             <span id="dictOperateResultMessageNew" style="color:red"></span> 
             <div style="color:red;margin-left: 40px;" id="verificateInfo"></div>
             <div style="color:red;margin-left: 40px;" id="verificateInfogongjie"></div>
  		   </td>
  		
         </tr>
		 <tr id="T2" style="display:none">
            <td><label class="lab"><span class="red">*</span>退回至流程节点：</label>
                   <select class="select180" onchange="javascript:switchBackNodeNew();" id="backFlowNoteNew">
					     <option value="">请选择退回节点</option>
						 <option value="TO_CONTRACT_SIGN">待上传合同</option>
                         <option value="TO_CONFIRM_SIGN">待确认签署</option>
                    </select>
                    <span id="backFlowNoteMessageNew" style="color:red"></span>
			 </td>
			<td></td>
          </tr>
           <tr >
             <td colspan="2" nowrap="nowrap"><label class="lab">退回原因：</label>
             </td>
         </tr>
         <tr >
           <td colspan="2"><label class="lab"></label>
                <textarea id="returnReason" name="returnReason" class="textarea_refuse"></textarea>
           </td>
         </tr>
         <tr>
            <td colspan="2"><span id="returnReasonMessage" style="color:red"></span></td>
          </tr>
          <tr>
	          <td colspan="2">
	          	<div  style="margin-left: 420px;"><input type="button" class="btn btn-primary" value="确定" id="dispatchFlowBtnNew"></input></div>
	          </td>
          </tr>
          </tbody>
		<!-- <tr>
          <td colspan="2" class="bar"></td>
        </tr> -->
	    <tr>
          <td colspan="2">
            <label class="lab">审核结果：</label>
             <input type="radio" name="dictOperateResult" value="2" targetFlag='2' class="ml10 mr6"/>通过 
             <input type="radio" targetFlag="0" value="0" name="dictOperateResult"  class="ml10 mr6"/>不通过退回 
             <span id="dictOperateResultMessage" style="color:red"></span>
  		   </td>
  		
         </tr>
		 <tr id="T1" style="display:none">
            <td><label class="lab"><span class="red">*</span>退回至流程节点：</label>
                   <select class="select180" onchange="javascript:switchBackNode();" id="backFlowNote">
					     <option value="">请选择退回节点</option>
						 <option value="TO_CONTRACT_SIGN">待上传合同</option>
                         <option value="TO_CONFIRM_SIGN">待确认签署</option>
                    </select>
                    <span id="backFlowNoteMessage" style="color:red"></span>
			 </td>
			<td></td>
          </tr>
           <tr>
             <td colspan="2" nowrap="nowrap"><label class="lab">审核意见：</label>
             </td>
         </tr>
         <tr id="chooseItem" style="display:none;">
             <td colspan="2" nowrap="nowrap"><label class="lab"><span class="red">*</span></label>
               <select name="remarks" id="chooseRemark" class="select180">
                <option value="">请选择</option>
                 <c:forEach items="${fns:getDictList('jk_contract_verify_return')}" var="item">
                   <option value="${item.label}">${item.label}</option>
                 </c:forEach>  
               </select>
               <span id="chooseItemMessage" style="color:red"></span>
             </td>
         </tr>
         <tr>
           <td colspan="2"><label class="lab"></label>
                <textarea id="remark" name="remarks" class="textarea_refuse"></textarea>
           </td>
         </tr>
         <tr>
            <td colspan="2"><span id="remarkMessage" style="color:red"></span></td>
          </tr>
        </table>
       </form> 
       <div class="tright pr30 mb10"><input type="button" class="btn btn-primary" value="提交" id="dispatchFlowBtn"></input></div>
   </div>
  
    <div style="float:left;width:60%;height:97%">
      <iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height="100%"
		src="${workItem.bv.imageUrl}">
	  </iframe>
    </div>
  </div>
  <div id="fdj"></div>
</body>
</html>