<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>利率审核</title>
<script src="${context}/js/contract/numberFormat.js"
	type="text/javascript"></script>
<script src="${context}/js/contract/clearFee.js" type="text/javascript"></script>
<script src="${context}/js/contract/contractAudit.js"
	type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<!--   模态弹窗 文件引入 Statr -->
<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet"
	href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<!--   模态弹窗 文件引入 End -->
</head>
<body>
	<script language=javascript>
		cancelFlagRetVal = "";
		REDIRECT_URL = "/apply/contractAudit/fetchTaskItems";
		flowFlag = 'RATE_AUDIT';
		function cancelFlag() {
			var loanFlag = $('#loanFlag').html();
			if (loanFlag != '') {
				ajaxHandleFlag($('#flagCancelParam').val(), 'loanFlag', ' ');
				if (cancelFlagRetVal == '1') {
					$('#loanFlag').html('');
				} else if ('0' == cancelFlagRetVal) {
					art.dialog.tip("请求的数据异常!");
				}
			} else {
				art.dialog.alert("托管标识已经取消，不允许重复操作!", '提示');
			}
		}
		$(document).ready(
				function() {
					$('#auditRate').bind('click', function() {
						var curRate = $(this).val();
						if (curRate == "") {
							$('#messageBox').html("请选择借款利率");
							fee.clear();
						} else {
							$('#messageBox').html("");
							reckonFee(curRate, $('#loanCode').val());
						}
					});
					$('#cancelTGBtn').bind('click', function() {
						cancelFlag();
					});
					$(":input[name='history']").each(function() {
						$(this).bind('click', function() {
							viewAuditHistory($(this).attr('loanCode'));
						});
					});
					$('#retAuditListBtn').bind('click', function() {

						retAuditList('RATE_AUDIT');
					});
					$('#auditRateBackFlow').bind(
							'click',
							function() {
								backFlowMenuId('rate_grantBackDialog', flowFlag,
										REDIRECT_URL, 'loanapplyForm',
										'${param.menuId}'); //传递退回窗口的视图名称
							});
					$('#auditRateDispatch').bind('click',function(){
						if($('#auditRate').val()==''){
							$('#messageBox').html("请选择借款利率");		
						}else{
							if($('#curRate').val()==$('#auditRate').val() && $('#rateEffectFlag').val()=='0'){
								art.dialog.alert("当前利率不在设置的有效期间内，不允许提交",'提示');
								return false;
							}
							dispatchFlowWithPermission(flowFlag,"TO_CONFIRM_SIGN",REDIRECT_URL,'rateAudit','${param.menuId}');
				              $('#auditRateDispatch').attr('disabled',true);
				              $('#auditRateBackFlow').attr('disabled',true);
						}
					});
					$('#maxAmountBtn').bind('click', function() {
						var url = "${workItem.bv.largeAmountImageUrl}";
						art.dialog.open(url, {
							id : 'information',
							title : '大额审批协议',
							lock : true,
							width : 800,
							height : 550
						}, false);
					});
				});
	</script>
	<div class="pt10 mb10 tright">
		<!-- <button class="btn btn-small" id="cancelTGBtn">取消TG</button> -->
		<c:if test="${workItem.bv.largeAmountFlag!='0'}">
			<button class="btn btn-small" id="maxAmountBtn">大额审批查看</button>
		</c:if>
		<button class="btn btn-small" id="retAuditListBtn">返回</button>
		<button class="btn btn-small" name="history" loanCode='${workItem.bv.contract.loanCode}'>历史</button>
	</div>
	<c:set var="bv" value="${workItem.bv}" />
	<form id="rateAuditForm">
		<input type="hidden" name="contract.auditEnsureName"value="${bv.auditEnsureName}" /> 
		<input type="hidden" name="contract.auditLegalMan" value="${bv.auditLegalMan}" /> 
		<input type="hidden" name="contract.ensuremanBusinessPlace"	value="${bv.ensuremanBusinessPlace}" /> 
		<input type="hidden" name="contract.productType" value="${bv.contract.productType}" /> 
		<input type="hidden" name="contract.contractMonths" value="${bv.contract.contractMonths}" /> 
		<input type="hidden" name="contract.auditAmount" value="${bv.contract.auditAmount}" /> 
		<input type="hidden" name="ctrFee.feePetition"	value="${bv.ctrFee.feePetition}" /> 
		<input type="hidden" name="ctrFee.monthRateService" id="monthRateService" value="${bv.ctrFee.monthRateService}" /> 
		<input type="hidden" name="ctrFee.comprehensiveServiceRate" id="comprehensiveServiceRate" value="${bv.ctrFee.comprehensiveServiceRate}" /> 
		<input type="hidden" name="ctrFee.feeAllRaio" value="${bv.ctrFee.feeAllRaio}" />
		
		<input type="hidden" name="ctrFee.monthServiceFee" value="${bv.ctrFee.monthServiceFee}" />
		<input type="hidden" name="ctrFee.monthServiceRate" value="${bv.ctrFee.monthServiceRate}" />
		<input type="hidden" name="ctrFee.monthRate" value="${bv.ctrFee.monthRate}" />
		<input type="hidden" name="ctrFee.monthFee" value="${bv.ctrFee.monthFee}" />
		<!-- zcj -->
		<input type="hidden" name="contractZCJ.auditEnsureName" value="${bv.auditEnsureName}" />
		<input type="hidden" name="contractZCJ.auditLegalMan" value="${bv.auditLegalMan}" /> 
		<input type="hidden" name="contractZCJ.ensuremanBusinessPlace" 	value="${bv.ensuremanBusinessPlace}" /> 
		<input type="hidden" name="contractZCJ.productType" value="${bv.contractZCJ.productType}" />
		<input type="hidden" name="contractZCJ.contractMonths" value="${bv.contractZCJ.contractMonths}" /> 
		<input type="hidden" name="contractZCJ.auditAmount" value="${bv.contractZCJ.auditAmount}" /> 
		<input type="hidden" name="ctrFeeZCJ.feePetition"  value="${bv.ctrFeeZCJ.feePetition}" />
		<input type="hidden" name="ctrFeeZCJ.monthRateService" id="monthRateService" value="${bv.ctrFeeZCJ.monthRateService}" />
		<input type="hidden" name="ctrFeeZCJ.comprehensiveServiceRate" id="comprehensiveServiceRate" value="${bv.ctrFeeZCJ.comprehensiveServiceRate}" />
		<input type="hidden" name="ctrFeeZCJ.feeAllRaio" value="${bv.ctrFeeZCJ.feeAllRaio}" />
		<!-- jinxin -->
		<input type="hidden" name="contractJINXIN.auditEnsureName" 	value="${bv.auditEnsureName}" /> 
		<input type="hidden" name="contractJINXIN.auditLegalMan" value="${bv.auditLegalMan}" /> 
		<input type="hidden" name="contractJINXIN.ensuremanBusinessPlace" value="${bv.ensuremanBusinessPlace}" /> 
		<input type="hidden" name="contractJINXIN.productType" value="${bv.contractJINXIN.productType}" /> 
		<input type="hidden" name="contractJINXIN.contractMonths" value="${bv.contractJINXIN.contractMonths}" /> 
		<input type="hidden" name="contractJINXIN.auditAmount" value="${bv.contractJINXIN.auditAmount}" />
		<input type="hidden" name="ctrFeeJINXIN.feePetition" value="${bv.ctrFeeJINXIN.feePetition}" /> 
		<input type="hidden" name="ctrFeeJINXIN.monthRateService" id="monthRateService" value="${bv.ctrFeeJINXIN.monthRateService}" />
		<input type="hidden" name="ctrFeeJINXIN.comprehensiveServiceRate" id="comprehensiveServiceRate" value="${bv.ctrFeeJINXIN.comprehensiveServiceRate}" /> 
		<input type="hidden" name="ctrFeeJINXIN.feeAllRaio" value="${bv.ctrFeeJINXIN.feeAllRaio}" />
		
		<input type="hidden" name="ctrFeeZCJ.feePetitionTemp" value="${bv.ctrFeeZCJ.feePetition}" />
		<input type="hidden" name="ctrFeeJINXIN.feePetitionTemp" value="${bv.ctrFeeJINXIN.feePetition}" />
		<!--测试用-->
		<input type="hidden" name="contract.coboCertNum" value="${bv.contract.coboCertNum}" /> 
		<input type="hidden" name="contract.coboName" value="${bv.contract.coboName}" /> 
		<input type="hidden" name="contract.loanName" value="${bv.mainLoaner}" /> 
		<input type="hidden" name="contract.loanCertNum" value="${bv.mainCertNum}" />
		<input type="hidden" name="contract.channelFlag" value="${bv.loanFlagCode}" />
		<input type="hidden" name="contract.model" value="${bv.model}" />
		<input type="hidden" name="contractZCJ.newFlag" value="${bv.contract.newFlag}" />
		<input type="hidden" name="contractJINXIN.newFlag" value="${bv.contract.newFlag}" /> 
		<input type="hidden" value="2" name="dictOperateResult" /> 
		<input type="hidden" name="contract.companyName" value="${bv.auditEnsureName}" />
		<input type="hidden" name="contract.legalMan" value="${bv.auditLegalMan}" />
		<input type="hidden" name="contract.maddress" value="${bv.ensuremanBusinessPlace}" />
		<input type="hidden" name="businessProveId" value="${bv.businessProveId}" />
		<!--测试用zcj-->
		<input type="hidden" name="contractZCJ.coboCertNum" value="${bv.contractZCJ.coboCertNum}" /> 
		<input type="hidden" name="contractZCJ.coboName" value="${bv.contractZCJ.coboName}" /> 
		<input type="hidden" name="contractZCJ.loanName" value="${bv.mainLoaner}" /> 
		<input type="hidden" name="contractZCJ.loanCertNum" value="${bv.mainCertNum}" />
		<input type="hidden" name="contractZCJ.channelFlag" value="${bv.contractZCJ.channelFlag}" /> 
		<input type="hidden" name="contractZCJ.model" value="${bv.model}" />  
		<input type="hidden" name="contractZCJ.companyName" value="${bv.auditEnsureName}" /> 
		<input type="hidden" name="contractZCJ.legalMan" value="${bv.auditLegalMan}" />
		<input type="hidden" name="contractZCJ.maddress" value="${bv.ensuremanBusinessPlace}" />
		<!--测试用jinxin-->
		<input type="hidden" name="contractJINXIN.coboCertNum" 	value="${bv.contractJINXIN.coboCertNum}" /> 
		<input type="hidden" name="contractJINXIN.coboName" value="${bv.contractJINXIN.coboName}" /> 
		<input type="hidden" name="contractJINXIN.loanName" value="${bv.mainLoaner}" /> 
		<input type="hidden" name="contractJINXIN.loanCertNum" value="${bv.mainCertNum}" />
		<input type="hidden" name="contractJINXIN.channelFlag" value="${bv.contractJINXIN.channelFlag}" /> 
		<input type="hidden" name="contractJINXIN.model" value="${bv.model}" /> 
		<input type="hidden" name="contractJINXIN.companyName" value="${bv.auditEnsureName}" /> 
		<input type="hidden" name="contractJINXIN.legalMan" value="${bv.auditLegalMan}" />
		<input type="hidden" name="contractJINXIN.maddress" value="${bv.ensuremanBusinessPlace}" /> 
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
		<input type="hidden" value="${bv.contract.contractCode}" name="contract.contractCode" /> 
		<input type="hidden" value="${bv.contract.loanCode}" id="loanCode" name="contract.loanCode" />
		<input type="hidden" value="${bv.contractZCJ.loanCode}" id="loanCode" name="contractZCJ.loanCode" />
		<input type="hidden" value="${bv.contractJINXIN.loanCode}" id="loanCode" name="contractJINXIN.loanCode" />
		<input type="hidden" value="${bv.contractZCJ.contractCode}" name="contractZCJ.contractCode" />
		<input type="hidden" value="${bv.contractJINXIN.contractCode}" name="contractJINXIN.contractCode" />
		<input type="hidden" value="${bv.productType}" name="productType" />
	</form>
	<input type="hidden" id="curRate" value="${bv.curRate}" />
	<input type="hidden" id="rateEffectFlag" value="${bv.rateEffectiveFlag}" />
	<input type="hidden" id="flagCancelParam" value="${workItem.bv.applyId},${workItem.wobNum},${workItem.flowName},${workItem.token},${workItem.stepName},${bv.contract.loanCode}" />
	<form id="rateForm" method="post">
		<div class="box2 mb10">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">门店名称：</label>${bv.applyOrgName}</td>
					<td><label class="lab">借款合同编号：</label>${bv.contract.contractCode}
					</td>
					<td></td>
				</tr>
			</table>
		</div>
		<div class="box2 mb10">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab"></label></td>
					<td></td>
					<td><label class="lab"></label></td>
				</tr>
				<tr>
					<td width="31%"><label class="lab">借款人姓名：</label>${bv.mainLoaner}
					</td>
					<td><label class="lab">证件类型：</label>${bv.mainCertTypeName}</td>
					<td width="31%"><label class="lab">证件号码：</label>${bv.mainCertNum}
					</td>
				</tr>


				<c:forEach items="${bv.coborrowers}" var="currItem">
					<tr>
						<td><label class="lab"> <c:if
									test="${bv.loanInfo.loanInfoOldOrNewFlag eq '' || bv.loanInfo.loanInfoOldOrNewFlag eq '0'}">共借人姓名：</c:if>
								<c:if test="${bv.loanInfo.loanInfoOldOrNewFlag eq '1'}">自然人保证人姓名：</c:if></label>${currItem.coboName}
						</td>
						<td><label class="lab">证件类型：</label>${currItem.dictCertTypeName}
						</td>
						<td><label class="lab">证件号码：</label>${currItem.coboCertNum}
						<td>
					</tr>
				</c:forEach>
				<c:if test="${bv.auditEnsureName!=null && bv.auditEnsureName!=''}">
					<tr>
						<td><label class="lab"> <c:if
									test="${bv.loanInfo.loanInfoOldOrNewFlag eq '' || bv.loanInfo.loanInfoOldOrNewFlag eq '0'}">
									保证人：
								</c:if> <c:if test="${bv.loanInfo.loanInfoOldOrNewFlag eq '1'}">
									保证公司名称：
								</c:if>
						</label>${bv.auditEnsureName}</td>
						<td><label class="lab">法定代表人：</label>${bv.auditLegalMan}</td>
						<td width="31%"><label class="lab">经营场所：</label>${bv.maddressName}
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		<c:if test="${bv.issplit!=1 }">
			<div class="box2 mb10">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td colspan="4">
							<!-- <div class="control-group"></div> -->
						</td>
					</tr>
					<tr>
						<td width="31%"><label class="lab">批复产品：</label>${bv.productName}
						</td>
						<td width="31%"><label class="lab">批复期数：</label>${bv.contract.contractMonths}
						</td>
						<td><label class="lab">风险等级：</label>${bv.riskLevel}</td>
						<td></td>
					</tr>
					<tr>
						<td width="31%"><label class="lab">产品总费率%：</label>
						<fmt:formatNumber value='${bv.ctrFee.feeAllRaio}' pattern="#,##0.000" /></td>
						<td><c:if test="${bv.hasOnlineTime eq '1'}">
								<label class="lab">前期综合费率%：</label>
								<fmt:formatNumber value='${bv.ctrFee.comprehensiveServiceRate}' pattern="####0.000" />
							</c:if></td>
						<td><c:if test="${bv.hasOnlineTime eq '1'}">
								<label class="lab">分期服务费率%：</label>
								<fmt:formatNumber value='${bv.ctrFee.monthRateService}' pattern="####0.000" />
							</c:if></td>
						<td></td>
					</tr>
					<tr>
						<td width="31%"><label class="lab">批借金额：</label>
						<fmt:formatNumber value='${bv.contract.auditAmount}' pattern="####0.00" /></td>
						<td><label class="lab">外访费：</label>
						<fmt:formatNumber value='${bv.ctrFee.feePetition}' pattern="####0.00" /></td>
						<td><label class="lab">外访距离：</label> ${bv.item_distance}</td>
						<td><c:if
								test="${bv.outside_flag eq ''  || bv.outside_flag eq '0' || bv.outside_flag ==null }">
								<span style="position: relative; color: red;"><label
									for="check3"></label>
									免外访 <input type="checkbox"	class="input_check" disabled="disabled" checked="checked" id="check3" /> </span>
							</c:if></td>
					</tr>
					<c:if test="${bv.productType eq 'A021'}">
						<tr>
							<td width="31%"><label class="lab">月利息：</label>
							<fmt:formatNumber value='${bv.ctrFee.monthFee}' pattern="####0.00" /></td>
						</tr>
					</c:if>
					
				</table>
			</div>
			<div class="box2 mb10">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr>
						<td><label class="lab"><span class="red">*</span>借款利率%：</label>
							<c:choose>
								<c:when test="${bv.hasOnlineTime eq '1'}">${bv.curRate}
							<input type="hidden" value="${bv.curRate}" 	name="ctrFee.feeMonthRate" />
							
								</c:when>
								<c:otherwise>
									<select class="select180 required" name="ctrFee.feeMonthRate"
										id="auditRate">
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
							</c:otherwise>
						</c:choose>
					<td>
							<label class="lab">模式：</label>
							<span id="loanFlag">${bv.modelName}</span>
					</td>
					
				</tr>
				<tr>
					<td>
						<label class="lab">实放金额：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.ctrFee.feePaymentAmount}' pattern="####0.00"/>" id="feePaymentAmount" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feePaymentAmount" id="fee_payment_amount" value="${bv.ctrFee.feePaymentAmount}"/>
						<input type="hidden" name="feePaymentAmount" id="bv_feePaymentAmount" value="${bv.ctrFee.feePaymentAmount}"/>
					</td>
					<td> <label class="lab">渠道：</label> <span id="loanFlag">${bv.loanFlag}</span> </td>
					 
				</tr>
				<tr>
					<td>
						<label class="lab">合同金额：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.contract.contractAmount}' pattern="####0.00"/>" id="contractAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.contractAmount" id="contract_amount" value="${bv.contract.contractAmount}"/>
						<input type="hidden" name="contractAmount" id="bv_contractAmount" value="${bv.contract.contractAmount}"/>
					</td>  
					<td>
						<label class="lab">前期综合服务费：</label>
						<input type="text" value="${bv.feeInfo.feeCount}" id="feeCount" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeCount" id="fee_count" value="${bv.ctrFee.feeCount}"/>
					</td>
				</tr> 
				<tr>  
					<td>
						<label class="lab">应还本息：</label>
						<input type="text" value="${bv.feeInfo.contractMonthRepayAmount}" id="contractMonthRepayAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.contractMonthRepayAmount" id="contract_back_month_money" value="${bv.contract.contractMonthRepayAmount}"/>
					</td>
					<td>
						<label class="lab">分期服务费总额：</label>
						<input type="text" value="${bv.feeInfo.monthFeeService*bv.contract.contractMonths}" id="monthFeeService1" class="input_text178" disabled>
					</td>
					
				</tr>
				<tr> 
					<td>
						<label class="lab">分期服务费：</label>
						<input type="text" value="${bv.feeInfo.monthFeeService}" id="monthFeeService2" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.monthFeeService" id="month_fee_service2" value="${bv.ctrFee.monthFeeService}"/>
					</td>
					<td>
						<label class="lab">催收服务费：</label>
						<input type="text" value="${bv.feeInfo.feeUrgedService}" id="feeUrgedService" class="input_text178" disabled/>
						<input type="hidden" name="ctrFee.feeUrgedService" id="fee_urged_service" value='${bv.ctrFee.feeUrgedService}'/>
					</td>
					
				</tr>
				<tr>
					<td>
						<label class="lab">月还款额：</label>
						<input type="text" value="${bv.feeInfo.monthPayTotalAmount}" id="monthPayTotalAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.monthPayTotalAmount" id="month_pay_total_amount" value="${bv.contract.monthPayTotalAmount}"/>
					</td>
					
					<td>
						<label class="lab">加急费：</label>
						<input type="text" value="${bv.feeInfo.feeExpedited}" id="feeExpedited" class="input_text178" disabled>
						<input type="hidden" name="ctrFee.feeExpedited" id="fee_expedited" value="${bv.ctrFee.feeExpedited}"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">预计还款总额：</label>
						<input type="text" value="${bv.feeInfo.contractExpectAmount}" id="contractExpectAmount" class="input_text178" disabled>
						<input type="hidden" name="contract.contractExpectAmount" id="contract_expect_amount" value="${bv.contract.contractExpectAmount}" /></td>
						<td></td>
						<td></td>
					</tr>
				</table>
			</div>
		</c:if>
			<c:if test="${bv.issplit==1 }">
			 <input type="hidden" name="ctrFeeZCJ.feeConsult" id="fee_consultZCJ" value="${bv.ctrFeeZCJ.feeConsult}" />
		     <input type="hidden" name="ctrFeeJINXIN.feeConsult" id="fee_consultJINXIN" value="${bv.ctrFeeJINXIN.feeConsult}" />
		     <input type="hidden" name="ctrFeeZCJ.monthFeeConsult" id="month_fee_consultZCJ" value="${bv.ctrFeeZCJ.monthFeeConsult}" />
			 <input type="hidden" name="ctrFeeJINXIN.monthFeeConsult" id="month_fee_consultJINXIN" value="${bv.ctrFeeJINXIN.monthFeeConsult}" />
		     <input type="hidden" name="ctrFeeZCJ.feeAuditAmount" id="fee_audit_amountZCJ" value="${bv.ctrFeeZCJ.feeAuditAmount}" />
			 <input type="hidden" name="ctrFeeJINXIN.feeAuditAmount" id="fee_audit_amountJINXIN" value="${bv.ctrFeeJINXIN.feeAuditAmount}" />
			 <input type="hidden" name="ctrFeeZCJ.monthMidFeeService" id="month_mid_fee_serviceZCJ" value="${bv.ctrFeeZCJ.monthMidFeeService}" />
			 <input type="hidden" name="ctrFeeJINXIN.monthMidFeeService" id="month_mid_fee_serviceJINXIN" value="${bv.ctrFeeJINXIN.monthMidFeeService}" />		
		    <input type="hidden" name="ctrFeeZCJ.feeService" id="fee_serviceZCJ" value="${bv.ctrFeeZCJ.feeService}" />		
			<input type="hidden" name="ctrFeeJINXIN.feeService" id="fee_serviceJINXIN" value="${bv.ctrFeeJINXIN.feeService}" />			
			<input type="hidden" name="ctrFeeZCJ.feeInfoService" id="fee_info_serviceZCJ" value="${bv.ctrFeeZCJ.feeInfoService}" />		
			<input type="hidden" name="ctrFeeJINXIN.feeInfoService" id="fee_info_serviceJINXIN" value="${bv.ctrFeeJINXIN.feeInfoService}" />		
					
					
				<div class="box2 mb10">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td colspan="4">
							<!-- <div class="control-group"></div> -->
						</td>
					</tr>
					<tr>
						<td width="31%"><label class="lab">批复产品：</label>${bv.productName}
						</td>
						<td width="31%"><label class="lab">批复期数：</label>${bv.contract.contractMonths}
						</td>
						<td><label class="lab">风险等级：</label>${bv.riskLevel}</td>
						<td></td>
					</tr>
					<tr>
						<td width="31%"><label class="lab">产品总费率%：</label>
						<fmt:formatNumber value='${bv.ctrFee.feeAllRaio}' pattern="#,##0.000" /></td>
						<td><c:if test="${bv.hasOnlineTime eq '1'}">
								<label class="lab">前期综合费率%：</label>
								<fmt:formatNumber value='${bv.ctrFee.comprehensiveServiceRate}' pattern="####0.000" />
							</c:if></td>
						<td><c:if test="${bv.hasOnlineTime eq '1'}">
								<label class="lab">分期服务费率%：</label>
								<fmt:formatNumber value='${bv.ctrFee.monthRateService}' pattern="####0.000" />
							</c:if></td>
						<td></td>
					</tr>
					<tr>
						<td width="31%"><label class="lab"><span class="red">*</span>借款利率%：</label>
							<c:choose>
								<c:when test="${bv.hasOnlineTime eq '1'}">${bv.curRate}
							<input type="hidden" value="${bv.curRate}" name="ctrFee.feeMonthRate" />
							<input type="hidden" value="${bv.curRate}" 	name="ctrFeeZCJ.feeMonthRate" />
							<input type="hidden" value="${bv.curRate}"  name="ctrFeeJINXIN.feeMonthRate" />
								</c:when>
								<c:otherwise>
									<select class="select180 required" name="ctrFee.feeMonthRate"
										id="auditRate">
										<option value=''>请选择</option>
										<c:forEach items="${bv.rateInfoList}" var="item">
											<option value='${item.rate}'
												<c:if test="${item.rate==bv.curRate}">
									       selected = true
									    </c:if>>${item.rate}</option>
										</c:forEach>
									</select>
									<span id="messageBox" style="color: red;"></span>
								</c:otherwise>
							</c:choose></td>
						<td><label class="lab">外访费：</label>
						<fmt:formatNumber value='${bv.ctrFee.feePetition}'
								pattern="####0.00" /></td>
						<td><label class="lab">外访距离：</label> ${bv.item_distance}</td>
						<td><c:if
								test="${bv.outside_flag eq ''  || bv.outside_flag eq '0' || bv.outside_flag ==null }">
								<span style="position: relative; color: red;"><label
									for="check3"></label>免外访
									<input type="checkbox" class="input_check" disabled="disabled" checked="checked" id="check3" /> </span>
							</c:if></td>
					</tr>
					 <tr>
					 	<td width="31%"><label class="lab">批借金额：</label>
						<fmt:formatNumber value='${bv.contract.auditAmount}' pattern="####0.00" /></td>
					    <td colspan="2"><label class="lab">模式：</label> <span id="loanFlag">${bv.modelName}</span>
					
					</tr>
				</table>
			</div>
			
			<div class="box2 mb10">
				<table cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td colspan="4">
							<!-- <div class="control-group"></div> -->
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">合同编号：</label>${bv.contractZCJ.contractCode}
						</td>
						<td width="25%"><label class="lab">渠道：</label>
						<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
								 <c:if test="${mark.value==bv.contractZCJ.channelFlag}">
									${mark.label}
								 </c:if>
						</c:forEach>
						</td>
						<td width="25%"><label class="lab">合同编号：</label>${bv.contractJINXIN.contractCode}
						</td>
						<td width="25%"><label class="lab">渠道：</label>
						<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
								 <c:if test="${mark.value==bv.contractJINXIN.channelFlag}">
									${mark.label}
								 </c:if>
						</c:forEach>
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">批借金额：</label>	<fmt:formatNumber value='${bv.contractZCJ.auditAmount}'
								pattern="####0.00" /></td>
						</td>
						<td width="25%"><label class="lab">外访费：</label>${bv.ctrFeeZCJ.feePetition}
						</td>
						<td width="25%"><label class="lab">批借金额：</label><fmt:formatNumber value='${bv.contractJINXIN.auditAmount}'
								pattern="####0.00" /></td>
						</td>
						<td width="25%"><label class="lab">外访费：</label>${bv.ctrFeeJINXIN.feePetition}
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">实放金额：</label>
							<input type="text" 	value="<fmt:formatNumber value='${bv.ctrFeeZCJ.feePaymentAmount}' pattern="####0.00"/>" id="feePaymentAmountZCJ" class="input_text178" disabled> 
							<input type="hidden" name="ctrFeeZCJ.feePaymentAmount" id="fee_payment_amountZCJ" value="${bv.ctrFeeZCJ.feePaymentAmount}" />
							<input type="hidden" name="feePaymentAmountZCJ" id="bv_feePaymentAmountZCJ" value="${bv.ctrFeeZCJ.feePaymentAmount}" />
						</td>
						<td width="25%"><label class="lab">前期综合服务费：</label>
							<input type="text" value="${bv.feeInfoZCJ.feeCount}" id="feeCountZCJ" class="input_text178" disabled> 
							<input type="hidden" name="ctrFeeZCJ.feeCount" id="fee_countZCJ" value="${bv.feeInfoZCJ.feeCount}" />
						</td>
						<td width="25%"><label class="lab">实放金额：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.ctrFeeJINXIN.feePaymentAmount}' pattern="####0.00"/>" id="feePaymentAmountJINXIN" class="input_text178" disabled> 
						<input type="hidden" name="ctrFeeJINXIN.feePaymentAmount" id="fee_payment_amountJINXIN" value="${bv.ctrFeeJINXIN.feePaymentAmount}" />
						<input type="hidden" name="feePaymentAmountJINXIN" 	id="bv_feePaymentAmountJINXIN" value="${bv.ctrFeeJINXIN.feePaymentAmount}" />
						</td>
						<td width="25%"><label class="lab">前期综合服务费：</label>
						<input type="text" value="${bv.feeInfoJINXIN.feeCount}" id="feeCountJINXIN"	class="input_text178" disabled> 
						<input type="hidden" name="ctrFeeJINXIN.feeCount" id="fee_countJINXIN" value="${bv.feeInfoJINXIN.feeCount}" />
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">合同金额：</label>
							<input type="text" value="<fmt:formatNumber value='${bv.contractZCJ.contractAmount}' pattern="####0.00"/>" id="contractAmountZCJ" class="input_text178" disabled> 
							<input type="hidden" name="contractZCJ.contractAmount" id="contract_amountZCJ" value="${bv.contractZCJ.contractAmount}" /> 
							<input type="hidden" name="contractAmountZCJ" id="bv_contractAmountZCJ" value="${bv.contractZCJ.contractAmount}" />
						</td>
						<td width="25%"><label class="lab">分期服务费总额：</label>
						<input type="text" value="${bv.feeInfoZCJ.monthFeeService*bv.contractZCJ.contractMonths}" id="zgZCJ" class="input_text178" disabled> 
						</td>
						<td width="25%"><label class="lab">合同金额：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.contractJINXIN.contractAmount}' pattern="####0.00"/>" id="contractAmountJINXIN" class="input_text178" disabled> 
						<input type="hidden" name="contractJINXIN.contractAmount" id="contract_amountJINXIN" value="${bv.contractJINXIN.contractAmount}" /> 
						<input type="hidden" name="contractAmountJINXIN" id="bv_contractAmountJINXIN" value="${bv.contractJINXIN.contractAmount}" />
						</td>
						<td width="25%"><label class="lab">分期服务费总额：</label>
						<input type="text" value="${bv.feeInfoJINXIN.monthFeeService*bv.contractJINXIN.contractMonths}" id="zgJINXIN" class="input_text178" disabled>
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">应还本息：</label>
							<input type="text" value="${bv.feeInfoZCJ.contractMonthRepayAmount}" id="contractMonthRepayAmountZCJ" class="input_text178" disabled>
							<input type="hidden" name="contractZCJ.contractMonthRepayAmount" id="contract_back_month_moneyZCJ" value="${bv.feeInfoZCJ.contractMonthRepayAmount}" /> 
						</td>
						<td width="25%"><label class="lab">催收服务费：</label>
							<input type="text" value="${bv.feeInfoZCJ.feeUrgedService}" id="feeUrgedServiceZCJ" class="input_text178" disabled /> 
							<input type="hidden" name="ctrFeeZCJ.feeUrgedService" id="fee_urged_serviceZCJ" value='${bv.feeInfoZCJ.feeUrgedService}' />
						</td>
						<td width="25%"><label class="lab">应还本息：</label>	
						<input type="text" value="${bv.feeInfoJINXIN.contractMonthRepayAmount}" id="contractMonthRepayAmountJINXIN" class="input_text178" disabled>
						<input type="hidden" name="contractJINXIN.contractMonthRepayAmount" id="contract_back_month_moneyJINXIN" value="${bv.feeInfoJINXIN.contractMonthRepayAmount}" /> 
						</td>
						<td width="25%"><label class="lab">催收服务费：</label>
						<input type="text" 	value="${bv.feeInfoJINXIN.feeUrgedService}" id="feeUrgedServiceJINXIN" class="input_text178" disabled /> 
						<input type="hidden" name="ctrFeeJINXIN.feeUrgedService" id="fee_urged_serviceJINXIN" value='${bv.feeInfoJINXIN.feeUrgedService}' />
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">分期服务费：</label>
							<input type="text" value="${bv.feeInfoZCJ.monthFeeService}" id="monthFeeService1ZCJ" class="input_text178" disabled> 
							<input type="hidden" name="ctrFeeZCJ.monthFeeService" id="month_fee_service1ZCJ" value="${bv.feeInfoZCJ.monthFeeService}" />
						</td>
						<td width="25%"><label class="lab">加急费：</label>
							<input type="text" value="${bv.feeInfoZCJ.feeExpedited}" id="feeExpeditedZCJ" class="input_text178" disabled> 
							<input type="hidden" name="ctrFeeZCJ.feeExpedited" id="fee_expeditedZCJ" value="${bv.feeInfoZCJ.feeExpedited}" />
						</td>
						<td width="25%"><label class="lab">分期服务费：</label>
						<input type="text" value="${bv.feeInfoJINXIN.monthFeeService}" id="monthFeeService1JINXIN" class="input_text178" disabled> 
						<input type="hidden" name="ctrFeeJINXIN.monthFeeService" id="month_fee_service1JINXIN" value="${bv.feeInfoJINXIN.monthFeeService}" />
						</td>
						<td width="25%"><label class="lab">加急费：</label>
						<input type="text" value="${bv.feeInfoJINXIN.feeExpedited}" id="feeExpeditedJINXIN" class="input_text178" disabled> 
						<input type="hidden" name="ctrFeeJINXIN.feeExpedited" id="fee_expeditedJINXIN" value="${bv.feeInfoJINXIN.feeExpedited}" />
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">月还款额：</label>
							<input type="text" value="${bv.feeInfoZCJ.monthPayTotalAmount}" id="monthPayTotalAmountZCJ" class="input_text178" disabled>
							<input type="hidden" name="contractZCJ.monthPayTotalAmount" id="month_pay_total_amountZCJ" value="${bv.feeInfoZCJ.monthPayTotalAmount}" />
						</td>
						<td width="25%">
						</td>
						<td width="25%"><label class="lab">月还款额：</label>
						<input type="text" value="${bv.feeInfoJINXIN.monthPayTotalAmount}" 	id="monthPayTotalAmountJINXIN" class="input_text178" disabled>
						<input type="hidden" name="contractJINXIN.monthPayTotalAmount" id="month_pay_total_amountJINXIN" value="${bv.feeInfoJINXIN.monthPayTotalAmount}" />
						<td width="25%">
						</td>
					</tr>
					<tr>
						<td width="25%"><label class="lab">预计还款总额：</label>
							<input type="text" value="${bv.feeInfoZCJ.contractExpectAmount}" id="contractExpectAmountZCJ" class="input_text178" disabled>
							<input type="hidden" name="contractZCJ.contractExpectAmount" id="contract_expect_amountZCJ" value="${bv.feeInfoZCJ.contractExpectAmount}" />
						</td>
						<td width="25%">
						</td>
						<td width="25%"><label class="lab">预计还款总额：</label>
						<input type="text" value="${bv.feeInfoJINXIN.contractExpectAmount}" id="contractExpectAmountJINXIN" class="input_text178" disabled>
						<input type="hidden" name="contractJINXIN.contractExpectAmount" id="contract_expect_amountJINXIN" value="${bv.feeInfoJINXIN.contractExpectAmount}" />
						</td>
						<td width="25%">
						</td>
					</tr>
				</table>
			</div>
				
			</c:if>
	</form>
	<div class="tright">
		<button class="btn btn-primary" id="auditRateDispatch">提交</button>
		<button class="btn btn-primary" id="auditRateBackFlow">退回</button>
	</div>
</body>
</html>
