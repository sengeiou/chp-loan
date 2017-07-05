<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>利率审核只读详情</title>
<script src="${context}/js/contract/numberFormat.js" type="text/javascript"></script>
<script src="${context}/js/contract/clearFee.js" type="text/javascript"></script>
<script src="${context}/js/contract/contractAudit.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<!--   模态弹窗 文件引入 Statr -->
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<!--   模态弹窗 文件引入 End -->
</head> 
<body>
<script language=javascript>
  cancelFlagRetVal="";
  REDIRECT_URL="/apply/contractAudit/fetchTaskItems";
  flowFlag = 'RATE_AUDIT';
     function cancelFlag(){
    	 var loanFlag =$('#loanFlag').html(); 
    	 if(loanFlag!=''){
		  	ajaxHandleFlag($('#flagCancelParam').val(),'loanFlag',' ');
		  	if(cancelFlagRetVal=='1'){
			 	 $('#loanFlag').html('');
		  	}else if('0'==cancelFlagRetVal){
		  		art.dialog.tip("请求的数据异常!");
		  	}
    	 }else{
    		 art.dialog.alert("托管标识已经取消，不允许重复操作!",'提示');
    	 }
	 }
	 $(document).ready(function(){
		 $('#auditRate').bind('click',function(){
			 var curRate = $(this).val();
			 if(curRate==""){
				 $('#messageBox').html("请选择借款利率");
				 fee.clear();
			 }else{
				 $('#messageBox').html("");
			     reckonFee(curRate,$('#loanCode').val());
			 }
		 });
		 $('#cancelTGBtn').bind('click',function(){
			 cancelFlag();
		 });
		 $(":input[name='history']").each(function() {
				$(this).bind('click', function() {
					showHisByLoanCode($(this).attr('loanCode'));
				});
			});
		 $('#retAuditListBtn').bind('click',function(){
			 window.location=ctx+"/apply/contractAudit/fetchTaskItems4ReadOnly?flowFlag=RATE_AUDIT";
		 });
// 		$('#auditRateBackFlow').bind('click',function(){
// 			backFlow('_grantBackDialog',flowFlag,REDIRECT_URL,'loanapplyForm');  //传递退回窗口的视图名称
// 		});
// 		$('#auditRateDispatch').bind('click',function(){
// 			if($('#auditRate').val()==''){
// 				$('#messageBox').html("请选择借款利率");		
// 			}else{
// 				if($('#curRate').val()==$('#auditRate').val() && $('#rateEffectFlag').val()=='0'){
// 					art.dialog.alert("当前利率不在设置的有效期间内，不允许提交",'提示');
// 					return false;
// 				}
// 				  dispatchFlow(flowFlag,"TO_CONFIRM_SIGN",REDIRECT_URL,'rateAudit');
// 	              $('#auditRateDispatch').attr('disabled',true);
// 	              $('#auditRateBackFlow').attr('disabled',true);
// 			}
// 		});
		$('#maxAmountBtn').bind('click',function(){
			var url="${workItem.bv.largeAmountImageUrl}";
		    art.dialog.open(url, {  
			   id: 'information',
			   title: '大额审批协议',
			   lock:true,
			   width:800,
			   height:550
			},false); 
		});
	 });
</script>  
    <div class="pt10 mb10 tright" >
        <!-- <button class="btn btn-small" id="cancelTGBtn">取消TG</button> -->
        <c:if test="${workItem.bv.largeAmountFlag!='0'}">
        	<button class="btn btn-small" id="maxAmountBtn">大额审批查看</button>
        </c:if>
		<button class="btn btn-small" id="retAuditListBtn">返回</button>
        <button class="btn btn-small" name="history" loanCode='${workItem.bv.contract.loanCode}'>历史</button>
    </div>
    <c:set var="bv" value="${workItem.bv}"/>
    <form id="rateAuditForm">
		<input type="hidden" name="contract.auditEnsureName" value="${bv.auditEnsureName}"/>
		<input type="hidden" name="contract.auditLegalMan" value="${bv.auditLegalMan}"/>
		<input type="hidden" name="contract.ensuremanBusinessPlace" value="${bv.ensuremanBusinessPlace}"/>
		<input type="hidden" name="contract.productType" value="${bv.contract.productType}"/>
		<input type="hidden" name="contract.contractMonths" value="${bv.contract.contractMonths}"/>
		<input type="hidden" name="contract.auditAmount" value="${bv.contract.auditAmount}"/>
		<input type="hidden" name="ctrFee.feePetition" value="${bv.ctrFee.feePetition}"/>
		<input type="hidden" name="ctrFee.monthRateService" id="monthRateService" value="${bv.ctrFee.monthRateService}"/>
		<input type="hidden" name="ctrFee.comprehensiveServiceRate" id="comprehensiveServiceRate" value="${bv.ctrFee.comprehensiveServiceRate}"/>
		<input type="hidden" name="ctrFee.feeAllRaio" value="${bv.ctrFee.feeAllRaio}"/><!--测试用-->
		<input type="hidden" name="contract.coboCertNum" value="${bv.contract.coboCertNum}"/>
		<input type="hidden" name="contract.coboName" value="${bv.contract.coboName}"/>
		<input type="hidden" name="contract.loanName" value="${bv.mainLoaner}"/>
		<input type="hidden" name="contract.loanCertNum" value="${bv.mainCertNum}"/>
		<input type="hidden" name="contract.channelFlag" value="${bv.loanFlagCode}"/>
		<input type="hidden" name="contract.model" value="${bv.model}"/>
	    <input type="hidden" value="2" name="dictOperateResult"/>
	    <input type="hidden" name="contract.companyName" value="${bv.auditEnsureName}"/>
	    <input type="hidden" name="contract.legalMan" value="${bv.auditLegalMan}"/>
	    <input type="hidden" name="contract.maddress" value="${bv.ensuremanBusinessPlace}"/>
	    <input type="hidden" name="businessProveId" value="${bv.businessProveId}"/>
    </form>
    <form id="loanapplyForm">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="hidden" id="stepName" value="${workItem.stepName}" name="stepName"></input>
		<%-- <input type="hidden" value="利率审核" name="stepName"></input> --%>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" id="applyId" value="${workItem.bv.applyId}" name="applyId"></input>
		<input type="hidden" value="${bv.contract.contractCode}" name="contract.contractCode"/>
		<input type="hidden" value="${bv.contract.loanCode}" id="loanCode" name="contract.loanCode"/>
    </form>
    <input type="hidden" id="curRate" value="${bv.curRate}"/>
    <input type="hidden" id="rateEffectFlag" value="${bv.rateEffectiveFlag}"/>
    <input type="hidden" id="flagCancelParam" value="${workItem.bv.applyId},${workItem.wobNum},${workItem.flowName},${workItem.token},${workItem.stepName},${bv.contract.loanCode}"/>
    <form id="rateForm" method="post">
		<div class="box2 mb10">		
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td>
							<label class="lab">门店名称：</label>${bv.applyOrgName}
						</td>
						<td>
							<label class="lab">借款合同编号：</label>${bv.contract.contractCode}
						</td>
						<td>
						</td>
					</tr>			
				</table>
		</div>		
		<div class="box2 mb10">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">	
				<tr>
					<td>
						<label class="lab"></label>
					</td>
					<td>
					</td>
					<td>
						<label class="lab"></label>
					</td>
				</tr>
				<tr>
					<td width="31%">
						<label class="lab">借款人姓名：</label>${bv.mainLoaner}
					</td>
					<td>
						<label class="lab">证件类型：</label>${bv.mainCertTypeName}
					</td>
					<td width="31%">
						<label class="lab">证件号码：</label>${bv.mainCertNum}
					</td>               
				</tr>
				<c:forEach items="${bv.coborrowers}" var="currItem">
					<tr>
						<td>
							<label class="lab">共借人姓名：</label>${currItem.coboName}
						</td>
						<td>
							<label class="lab">证件类型：</label>${currItem.dictCertTypeName}
						</td>
						<td>
							<label class="lab">证件号码：</label>${currItem.coboCertNum}
						<td>
					</tr>
				</c:forEach>
				<c:if test="${bv.auditEnsureName!=null && bv.auditEnsureName!=''}">
					<tr>
						<td>
							<label class="lab">保证人：</label>${bv.auditEnsureName}
						</td>
						<td>
							<label class="lab">法定代表人：</label>${bv.auditLegalMan}
						</td>
						<td width="31%">
							<label class="lab">经营场所：</label>${bv.ensuremanBusinessPlace}
						</td>
					</tr>
				</c:if>
			</table>
		</div>           
		<div class="box2 mb10">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">	
				<tr>
					<td colspan="3">
						<!-- <div class="control-group"></div> -->
					</td>
				</tr>
				<tr>
					<td width="31%">
						<label class="lab">批复产品：</label>${bv.productName}
					</td>
					<td width="31%">
						<label class="lab">批复期数：</label>${bv.contract.contractMonths}
					</td>
					<td width="31%">
						<label class="lab">产品总费率：</label><fmt:formatNumber value='${bv.ctrFee.feeAllRaio}' pattern="#,##0.000"/>%
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">批借金额：</label><fmt:formatNumber value='${bv.contract.auditAmount}' pattern="####0.00"/>
					</td>
					<td>
						<label class="lab">外访费：</label><fmt:formatNumber value='${bv.ctrFee.feePetition}' pattern="####0.00"/>
					</td>
					<td><label class="lab">风险等级：</label>${bv.riskLevel}</td>
				</tr>
			</table>
		</div>
		<div class="box2 mb10">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td >
						<label class="lab"><span class="red">*</span>借款利率%：</label>
						<select class="select180 required" name="ctrFee.feeMonthRate" id="auditRate" di>
							<option value=''>请选择</option>
							<c:forEach items="${bv.rateInfoList}" var="item">
							  <option value='${item.rate}' 
							    <c:if test="${item.rate==bv.curRate}">
							       selected = true
							    </c:if>
							  >${item.rate}</option>
							</c:forEach>
						</select>
						<span id="messageBox" style="color:red;"></span>
					<td>
							<label class="lab">模式：</label>
							<span id="loanFlag">${bv.modelName}</span>
					</td>
					<td>
							<label class="lab">渠道：</label>
							<span id="loanFlag">${bv.loanFlag}</span>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">实放金额：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.feePaymentAmount}" id="feePaymentAmount" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feePaymentAmount" id="fee_payment_amount" value="${bv.ctrFee.feePaymentAmount}"/>
						<input type="hidden" name="feePaymentAmount" id="bv_feePaymentAmount" value="${bv.ctrFee.feePaymentAmount}"/>
					</td>
					<td>
						<label class="lab">前期咨询费：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.feeConsult}" id="feeConsult" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeConsult" id="fee_consult" value="${bv.ctrFee.feeConsult}"/>
					</td>
					<td>
						<label class="lab">分期咨询费：</label>
						<input type="text"  readonly="readonly"  value="${bv.feeInfo.monthFeeConsult}" id="monthFeeConsult" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.monthFeeConsult" id="month_fee_consult" value="${bv.ctrFee.monthFeeConsult}"/>
					</td> 
				</tr>
				<tr>
					<td>
						<label class="lab">合同金额：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.contractAmount}" id="contractAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.contractAmount" id="contract_amount" value="${bv.contract.contractAmount}"/>
						<input type="hidden" name="contractAmount" id="bv_contractAmount" value="${bv.contract.contractAmount}"/>
					</td>  
					<td>
						<label class="lab">前期审核费：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.feeAuditAmount}" id="feeAuditAmount" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeAuditAmount" id="fee_audit_amount" value="${bv.ctrFee.feeAuditAmount}"/>
					</td>
					<td>
						<label class="lab">分期居间服务费：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.monthMidFeeService}" id="monthMidFeeService" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.monthMidFeeService" id="month_mid_fee_service" value="${bv.ctrFee.monthMidFeeService}"/>
					</td>
				</tr> 
				<tr>  
					<td>
						<label class="lab">应还本息：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.contractMonthRepayAmount}" id="contractMonthRepayAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.contractMonthRepayAmount" id="contract_back_month_money" value="${bv.contract.contractMonthRepayAmount}"/>
					</td>
					<td>
						<label class="lab">前期居间服务费：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.feeService}" id="feeService" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeService" id="fee_service" value="${bv.ctrFee.feeService}"/>
					</td>
					<td>
						<label class="lab">分期服务费：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.monthFeeService}" id="monthFeeService1" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.monthFeeService" id="month_fee_service1" value="${bv.ctrFee.monthFeeService}"/>
					</td>
				</tr>
				<tr> 
					<td>
						<label class="lab">分期服务费：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.monthFeeService}" id="monthFeeService2" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.monthFeeService" id="month_fee_service2" value="${bv.ctrFee.monthFeeService}"/>
					</td>
					<td>
						<label class="lab">前期信息服务费：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.feeInfoService}" id="feeInfoService" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeInfoService" id="fee_info_service" value="${bv.ctrFee.feeInfoService}"/>
					</td>
					<td>
						<label class="lab">催收服务费：</label>
						<input type="text" readonly="readonly" value="${bv.feeInfo.feeUrgedService}" id="feeUrgedService" class="input_text178" disabled/>
						<input type="hidden" name="ctrFee.feeUrgedService" id="fee_urged_service" value='${bv.ctrFee.feeUrgedService}'/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">月还款额：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.monthPayTotalAmount}" id="monthPayTotalAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.monthPayTotalAmount" id="month_pay_total_amount" value="${bv.contract.monthPayTotalAmount}"/>
					</td>
					<td>
						<label class="lab">前期综合服务费：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.feeCount}" id="feeCount" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeCount" id="fee_count" value="${bv.ctrFee.feeCount}"/>
					</td>
					<td>
						<label class="lab">加急费：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.feeExpedited}" id="feeExpedited" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeExpedited" id="fee_expedited" value="${bv.ctrFee.feeExpedited}"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">预计还款总额：</label>
						<input type="text" readonly="readonly"  value="${bv.feeInfo.contractExpectAmount}" id="contractExpectAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.contractExpectAmount" id="contract_expect_amount" value="${bv.contract.contractExpectAmount}"/>
					</td>
					<td></td>
					<td></td>
				 </tr>
			</table>
		</div>	
    </form>   
</body>
</html>
