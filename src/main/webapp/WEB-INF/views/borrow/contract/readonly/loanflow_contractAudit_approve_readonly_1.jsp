<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />

<script src="${context}/js/contract/readonly/contractAuditReadOnly.js" type="text/javascript"></script>
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
	 redirectUrl:'/apply/contractAudit/fetchTaskItems4ReadOnly'
  };
  $(document).ready(function(){
   if($('#channelName').val()=='财富'){
	   $('#cancelFlagBtn').attr('disabled',true);  
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
   }
	if($('#largeAmountBtn').length>0){
		$('#largeAmountBtn').bind('click',function(){
			largeAmountShow($(this).attr('applyId'),$(this).attr('loanCode'),imgURL);
		}); 
	}
	$('#protclBtn').bind('click',function(){
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
	
	$('#dispatchFlowBtn').bind('click',function(){
		var msg = "是否确定执行提交操作";
		var auditResultChecked = $("input[type='radio']:checked").length;
		if(auditResultChecked==0){
			$('#dictOperateResultMessage').html("请选择审核结果");
			return false;
		}else{
			$('#dictOperateResultMessage').html("");
		}
		var auditResult = $("input[type='radio']:checked").val();
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
		art.dialog.confirm(msg,function (){
			var upLimit = $('#upLimit').val(); 
		    if(upLimit=='1'){
		    	$('#channelCode').val('2');
		    }
		    dispatchFlowWithPermission(dispatchParam.flowFlag,dispatchParam.response,dispatchParam.redirectUrl,'contractAudit','${param.menuId}');
			$('#dispatchFlowBtn').attr('disabled',true);
		},function(){
			var upLimit = $('#upLimit').val(); 
		    if(upLimit=='1'){
		    	retAuditList('CTR_AUDIT');
		    	$('#dispatchFlowBtn').attr('disabled',true);
		    }
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
  });
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
       <!--  <button class="btn btn-small" id="cancelFlagBtn">取消渠道</button> -->
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
         <input type="hidden" value="${bview.contract.loanCode}" name="contract.loanCode"></input>
         <input type="hidden" value="${bview.contract.contractAmount}" name="contractAmount"/>
         <input type="hidden" value="${bview.ctrFee.feePaymentAmount}" name="feePaymentAmount"/>
         <input type="hidden" value="${bview.applyId}" name="contract.applyId"></input>
         <input type="hidden" value="${bview.ctrFee.feePaymentAmount}" name="ctrFee.feePaymentAmount"></input>
         <input type="hidden" value="${bview.contract.midId}" name="contract.midId"></input>
         <input type="hidden" value="${bview.ctrFee.feeUrgedService}" name="feeUrgedService"></input>
         <input type="hidden" value="${bview.ctrFee.feeUrgedService}" name="unUrgeService"></input>
         <input type="hidden" value="${bview.loanFlagCode}" name="contract.channelFlag" id="channelCode"/>
         <input type="hidden" value="${bview.loanFlag}" id="channelName"/>
         <input type="hidden" value="${bview.trusteeshipFlag}" id="trusteeshipFlag"/>
         <input type="hidden" value="${bview.modelName}" id="modelName"/>
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
			    <td><label class="lab">共借人姓名：</label>${currItem.coboName}
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
         <td><label class="lab">保证人：</label><label class="enlarge">${bview.auditEnsureName}</label></td>
		 <td><label class="lab">法定代表人：</label><label class="enlarge">${bview.auditLegalMan}</label></td>
	  </tr>
	  <tr>
         <td><label class="lab">保证人实际经营场所：</label><label class="enlarge">${bview.ensuremanBusinessPlace}</label></td>
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
		  <td><label class="lab">前期咨询费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeConsult}' pattern="#,##0.00"/></label></td>
       </tr>
       <tr>
		 <td><label class="lab">合同金额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.contractAmount}' pattern="#,##0.00"/></label></td>
         <td><label class="lab">前期审核费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeAuditAmount}' pattern="#,##0.00"/></label></td>
       </tr>
       <tr>
       	 <td><label class="lab">应还本息：</label><fmt:formatNumber value='${bview.contract.contractMonthRepayAmount}' pattern="#,##0.00"/></td>
		 <td><label class="lab">前期居间服务费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeService}' pattern="#,##0.00"/></label></td>
       </tr>
       <tr>
        <td><label class="lab">分期服务费：</label><fmt:formatNumber value='${bview.ctrFee.monthFeeService}' pattern="#,##0.00"/></td>
		<td><label class="lab">前期信息服务费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeInfoService}' pattern="#,##0.00"/></label></td>
       </tr>
	   <tr>
    	 <td><label class="lab">月还金额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.monthPayTotalAmount}' pattern="#,##0.00"/></label></td>
		 <td><label class="lab">前期综合服务费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeCount}' pattern="#,##0.00"/></label></td>
	   </tr>
	   <tr>
	    <td><label class="lab">预计还款总额：</label><label class="enlarge"><fmt:formatNumber value='${bview.contract.contractExpectAmount}' pattern="#,##0.00"/></label></td>
	    <td><label class="lab">催收服务费：</label><label class="enlarge"><fmt:formatNumber value='${bview.ctrFee.feeUrgedService}' pattern="#,##0.00"/></label></td>
	   </tr>
	   <tr>
	     <td><label class="lab">分期咨询费：</label><fmt:formatNumber value='${bview.ctrFee.monthFeeConsult}' pattern="#,##0.00"/></td>
	     <td><label class="lab">分期居间服务费：</label><fmt:formatNumber value='${bview.ctrFee.monthMidFeeService}' pattern="#,##0.00"/>
         </td> 
	   </tr>
	   <tr>
	     <td><label class="lab">分期服务费：</label><fmt:formatNumber value='${bview.ctrFee.monthFeeService}' pattern="#,##0.00"/></td>
	     <td></td>
	   </tr>
	   <tr>
         <td><label class="lab">合同签订日期：</label><label class="enlarge"><fmt:formatDate value="${bview.contract.contractDueDay}" pattern="yyyy-MM-dd"/></label></td>
         <td><label class="lab">起始还款日期：</label><label class="enlarge"><fmt:formatDate value="${bview.contract.contractReplayDay}" pattern="yyyy-MM-dd"/></label></td>
       </tr>
	   <tr>
         <td><label class="lab">账单日：</label><label class="enlarge">${bview.billDay}</label></td>
		 <td></td>
        </tr>
        <tr>
		  <td><label class="lab">中间人：</label><label class="enlarge">${bview.contract.midName}</label></td>
          <td><label class="lab">还款付息方式：</label>
            <label class="enlarge">
             ${bview.contract.dictRepayMethodName}
            </label>
          </td> 
        </tr>
		<tr>
          <td colspan="2" class="bar"></td>
        </tr>
	    <tr>
          <td colspan="2">
            <label class="lab">审核结果：</label>
             <input  disabled="disabled" type="radio" name="dictOperateResult" value="2" targetFlag='2' class="ml10 mr6"/>通过 
             <input  disabled="disabled" type="radio" targetFlag="0" value="0" name="dictOperateResult"  class="ml10 mr6"/>不通过退回
             <span id="dictOperateResultMessage" style="color:red"></span>
  		   </td>
  		
         </tr>
		 <tr id="T1" style="display:none">
            <td><label class="lab"><span class="red">*</span>退回至流程节点：</label>
                   <select class="select180" onchange="javascript:switchBackNode();" id="backFlowNote">
					     <option value="">请选择退回节点</option>
						 <option value="TO_CONTRACT_SIGN">待合同签订</option>
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
                <textarea id="remark" name="remarks" disabled="disabled" class="textarea_refuse"></textarea>
           </td>
         </tr>
         <tr>
            <td colspan="2"><span id="remarkMessage" style="color:red"></span></td>
          </tr>
        </table>
       </form> 
       <div class="tright pr30 mb10"><input  type="hidden" class="btn btn-primary" value="提交" id="dispatchFlowBtn"></input></div>
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