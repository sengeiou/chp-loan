<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title></title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src='${context}/js/validate.js'></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendContractHandle.js"></script>
<script type="text/javascript" src='${context}/js/car/contract/contractAudit.js'></script>
<link rel="stylesheet" href="${ctxStatic}/lightbox/css/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript" src="${ctxStatic}/lightbox/jquery.lightbox.js"></script>
<script type="text/javascript">
	$(function(){
		$(".lightbox-2").lightbox({
		    fitToScreen: true,
		    scaleImages: true,
		    xScale: 1.2,
		    yScale: 1.2,
		    displayDownloadLink: false,
			imageClickClose: false
	    });
	 });
	</script>
</head>
<body>
	<form id="param" class="hide">
		<input type="text" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
		<input type="text" name="contractCode" value="${workItem.bv.carContract.contractCode }" />
	</form>
	<form id="workParam" class="hide">
		<input type="text" value="${workItem.flowName}" name="flowName"></input>
		<input type="text" value="${workItem.flowId}" name="flowId"></input>
		<input type="text" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="text" value="${workItem.stepName}" name="stepName"></input>
		<input type="text" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="text" value="${workItem.token}" name="token"></input>
	</form>
	<div style="height: 100%; position: absolute; top: 0px; bottom: 2px; width: 100%; overflow: hidden">
			<div class="r pt5 pb5 pr10">
				<c:if test="${workItem.bv.isLargeAmount == '1'}">
					<input type="button" class="btn btn-small jkcj_carExtendContractCheckForm_LargeView" applyId="${workItem.bv.carLoanInfo.applyId}" loanCode="${workItem.bv.carLoanInfo.loanCode}" id="largeAmountBtn" value="大额查看">
				</c:if>
			</div>
		<div style="width: 60%; height: 97%; float: left;">
			<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0"
				width="100%" height="100%"
				src="${workItem.bv.imageurl}">
			</iframe>
		</div>
		<div style="width: 39.9%; height: 100%; float: right; overflow: auto">
			
				<h2 class="f14 pl10">历史展期信息</h2>
				<div class="box2">
				
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr><th>合同编号</th><th style="text-align:left;">合同金额</th><th>借款期限</th><th>合同期限</th><th>降额</th></tr>
					<c:forEach var="extendContract" items="${workItem.bv.carContracts }">
						<tr>
						<td style="width:230px;">${extendContract.contractCode}</td>
						<td><fmt:formatNumber value="${extendContract.auditAmount}" pattern="0.00" /> </td>
						<td style="text-align:center;">${extendContract.contractMonths}</td>
						<td><fmt:formatDate value='${extendContract.contractFactDay }' type='date' pattern="yyyy-MM-dd" /> <br/> 
								<fmt:formatDate value='${extendContract.contractEndDay }' type='date' pattern="yyyy-MM-dd" />	</td>
						<td><c:choose>
						<c:when test="${extendContract.derate == null}">
							0.00
						</c:when>
						<c:otherwise>
							<fmt:formatNumber value="${extendContract.derate}" pattern="0.00" />
						</c:otherwise>
					</c:choose>元</td>
						</tr>
						</c:forEach>
						
						</table>
						</div>
						<h2 class="f14 pl10 mt20">借款人信息</h2>
						<div class="box2">
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">借款人姓名：</label>${workItem.bv.carCustomer.customerName}</td>
							<td><label class="labl">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
						</tr>
						<c:if test="${workItem.bv.carCustomer.idValidScore!=null&&workItem.bv.carCustomer.idValidScore<=40}">
								<tr>
									<td style="color: red">该用户身份的公安验证分数为30-40之间请注意！</td>
								</tr>
						</c:if>
						<c:if test="${workItem.bv.carCustomer.composePhotoId!=null && workItem.bv.carCustomer.composePhotoId!=''}">
						</c:if>
						<tr><td>
						<c:if test="${workItem.bv.carCustomer.composePhotoId!=null && workItem.bv.carCustomer.composePhotoId!=''}">
					           <div style="height:100px;width:80px;float:left;position:relative">
					            <img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.composePhotoId}" width="80px" height="100px"/>
					              <a href="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${workItem.bv.carCustomer.composePhotoId}" style="float:right" class="lightbox-2" rel="flowers">
									 <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
							      </a> 
					          </div>
					         </c:if>
					         <c:if test="${workItem.bv.carCustomer.appSignFlag=='1' && workItem.bv.carCustomer.customerId!=null && bview.customerId!=''}">
					          <div style="height:100px;width:80px;float:left;position:relative">
					            <img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?customerId=${workItem.bv.carCustomer.customerId}" width="80px" height="100px"/>
					              <a href="${ctx}/carpaperless/confirminfo/getPreparePhoto?customerId=${workItem.bv.carCustomer.customerId}" style="float:right" class="lightbox-2" rel="flowers">
									 <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
							      </a> 
					          </div>
					         </c:if></td>
					         <c:if test="${workItem.bv.carCustomerBase.isTelephoneModify=='1'}">
					         		<td style="color: red"><label class="labl">电话号码：</label>${workItem.bv.carCustomerBase.customerMobilePhone}</td>
					         </c:if>
					         <c:if test="${workItem.bv.carCustomerBase.isEmailModify=='1'}">
					         		<td style="color: red"><label class="labl">电子邮箱：</label>${workItem.bv.carCustomer.customerEmail}</td>
					         </c:if>
					         
					         </tr>
						<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
							<tr>
								<td><label class="labl">共借人姓名：</label>${cobos.coboName}</td>
								<td><label class="labl">共借人身份证号：</label>${cobos.certNum}</td>
							</tr>
							<c:if test="${cobos.idValidScore!=null&&cobos.idValidScore<=40}">
								<tr>
									<td style="color: red">该用户身份的公安验证分数为30-40之间请注意！</td>
								</tr>
							</c:if>
							<tr>
								<td>
								<c:if test="${cobos.composePhotoId!=null && cobos.composePhotoId!=''}">
				                <div  style="height:100px;width:80px;float:left;">
				           		 <img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${cobos.composePhotoId}" width="80px" height="100px"/>
				         	     <a href="${ctx}/carpaperless/confirminfo/getPreparePhoto?docId=${cobos.composePhotoId}" style="float:right" class="lightbox-2">
								   <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
						         </a>
				         	    </div>
				         	   </c:if>
				         	   <c:if test="${cobos.appSignFlag=='1' && currItem.id!=null && cobos.id!=''}">
				         	    <div  style="height:100px;width:80px;float:left;">
				           		 <img src="${ctx}/carpaperless/confirminfo/getPreparePhoto?customerId=${cobos.id}" width="80px" height="100px"/>
				        	      <a href="${ctx}/carpaperless/confirminfo/getPreparePhoto?customerId=${cobos.id}" style="float:right" class="lightbox-2">
								   <i class="icon-search" style="cursor: pointer;position:relative;bottom:19px;right:4px"></i>
						         </a>
				        	    </div>
				        	   </c:if>
								</td>
								<c:if test="${cobos.istelephonemodify=='1'}">
					         		<td  style="color: red"><label class="labl">电话号码：</label>${cobos.mobile}</td>
						         </c:if>
						         <c:if test="${cobos.isemailmodify=='1'}">
					         		<td  style="color: red"><label class="labl">电子邮箱：</label>${cobos.email}</td>
						         </c:if>
							</tr>
						</c:forEach>
						</table>
						</div>
						<h2 class="f14 pl10 mt20">车辆信息</h2>
						<div class="box2">
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
							<td><label class="labl">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
						</tr>
						<tr>
							<td><label class="labl">车架号：</label>${workItem.bv.carVehicleInfo.frameNumber}</td>
							<td><label class="labl">发动机号：</label>${workItem.bv.carVehicleInfo.engineNumber}</td>
						</tr>
						</table>
						</div>
						<h2 class="f14 pl10 mt20">汇城审批信息</h2>
						<div class="box2">
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">批借产品：</label>${workItem.bv.carContract.productType}</td>
							<td><label class="labl">批借期限：</label><label id="auditMonthsId">${workItem.bv.carContract.contractMonths}</label>天</td>
						</tr>
						<tr>
							<td><label class="labl">批借金额 ：</label><fmt:formatNumber value="${workItem.bv.carContract.auditAmount}" pattern="0.00" />元</td>
							<td><label class="labl">总费率 ：</label><label id="totalRate"><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.0000" /></label>%</td>
						</tr>
						</table>
						</div>
						<h2 class="f14 pl10 mt20">借款信息</h2>
						<div class="box2">
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td colspan="2"><label class="labl">展期合同编号：</label>${workItem.bv.carContract.contractCode }</td>
						</tr>
						<tr>
							<td><label class="labl">合同金额：</label><fmt:formatNumber value="${workItem.bv.carContract.contractAmount}" pattern="0.00" />元</td>
							<td><label class="labl">降额：</label><fmt:formatNumber value="${workItem.bv.carContract.derate }" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">总费率：</label><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.0000" />%</td>
							<td><label class="labl">中间人：</label>${workItem.bv.carContract.midIdName }</td>
						</tr>
						<tr>
							<td><label class="labl">利息率：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.interestRate}" pattern="0.0000" />%</td>
							<td><label class="labl">月利息：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.monthlyInterest }" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">展期综合服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.comprehensiveServiceFee}" pattern="0.00" />元</td>
							<td><label class="labl">审核费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.auditFee}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">咨询费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.consultingFee}" pattern="0.00" />元</td>
							<td><label class="labl">居间服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.intermediaryServiceFee}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">信息服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.informationServiceCharge}" pattern="0.00" />元</td>
							<td><label class="labl">展期费用：</label><fmt:formatNumber value="${workItem.bv.carContract.extensionFee}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<c:choose>
									<c:when test="${workItem.bv.carContract.productTypeName == 'GPS'}">
										<td><label class="labl">平台及流量费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.flowFee == null ? 0 : workItem.bv.carLoanInfo.flowFee }" pattern="0.00" />元</td>
									</c:when>
									<c:otherwise>
										<td><label class="labl">停车费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.parkingFee }" pattern="0.00" />元</td>
									</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td><label class="labl">展期还款总金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.extendPayAmount}" pattern="0.00" />元</td>
							<td><label class="labl">合同签订日期：</label>
							<fmt:formatDate value='${workItem.bv.carContract.contractFactDay}' type='date' pattern="yyyy-MM-dd" />
							</td>
						</tr>
						 <c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ01'}">
						    <tr>
						    	<td>
						    		<label class="labl">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee == null ? 0 : workItem.bv.carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元
						    	</td>
						    	<td></td>
						    </tr>
						    
						</c:if>
						</table>
						</div>
						<h2 class="f14 pl10 mt20">客户银行信息</h2>
						<div class="box2 mt20">
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">授权人：</label>${workItem.bv.carCustomer.customerName}</td>
							<td><label class="labl">账户名称：</label>${workItem.bv.carCustomerBankInfo.bankAccountName}
							<input type="checkbox" onclick="return false;" <c:if test="${workItem.bv.carCustomerBankInfo.israre == '1'}"> checked="checked" </c:if> /><lable>是否生僻字</lable>
							</td>
						</tr>
						<tr>
							<td><label class="labl">开卡行：</label>${workItem.bv.carCustomerBankInfo.cardBank}</td>
							<td><label class="labl">开卡省市：</label><sys:carAddressShow detailAddress="${workItem.bv.carCustomerBankInfo.bankProvinceCity}"></sys:carAddressShow></td>
						</tr>
						<tr>
							<td><label class="labl">银行账号：</label>${workItem.bv.carCustomerBankInfo.bankCardNo}</td>
							<td><label class="labl">开卡行支行名称：</label>${workItem.bv.carCustomerBankInfo.applyBankName}</td>
						</tr>
						<tr>
							<td><label class="labl">签约平台：</label>
									<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="curItem">
				                     <c:if test="${curItem.label!='中金'}">
									    <c:if test="${workItem.bv.carCustomerBankInfo.bankSigningPlatform==curItem.value}">
									    	${curItem.label}
									    </c:if>
									  </c:if>
								    </c:forEach> 
							</td>
						</tr>
						
					</table>
				</div>
			<form>
				<h2 class="f14 pl10 mt20">审核结果</h2>
				<div class="box2 mt20">
					<table class=" table1" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="labl"><span style="color: #ff0000">*</span>审核结果：</label>
							<input name="checkResult" type="radio" onclick="auditClick(this)" value="TO_GRANT_CONFIRM" checked/>通过 <input
								name="checkResult" type="radio" onclick="auditClick(this)" class="jkcj_extensionsigningVerify_refunded" value="CONTRACT_CHECK_BACK" />不通过退回</td>
							<td></td>
						</tr>
						<tr style="display: none;">
							<td><label class="labl"><span style="color: #ff0000">*</span>退回至流程节点：</label>
							<select name="backNode" id="backNodeSelect">
									<option value="">请选择退回节点</option>
									<c:forEach
										items="${fns:getDictLists('16,20', 'jk_car_loan_status')}"
										var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
							</select>
							</td>
							<td>
								<span style="color: #ff0000">*</span><label class="labl">退回原因：</label>
								<select id="backNodeResult" name="dictBackMestype" style="width:140px;">
									<option value="">请选择</option>
									<option value="风险客户">风险客户</option>
									<option value="客户主动放弃">客户主动放弃</option>
									<option value="资料错误或少资料">资料错误或少资料</option>
									<option value="提前上传签约资料">提前上传签约资料</option>
									<option value="签字潦草">签字潦草</option>
								</select>
							</td>
						</tr>
						<tr>
							<td><span id="checkStar" style="color: #ff0000;display: none">*</span><label class="labl">审核意见：</label>
							 <textarea	
							 name="backReason" maxLength="100" id="checkSuggess" style="width:300px;height:60px;"></textarea></td>
							<td></td>
						</tr>
					</table>
				</div>
				<div class="tright mt10 pr10" style="margin-bottom: 50px">
					<input type="button" id="handleContractCheck"
						class="btn btn-primary" value="提交"></input>
					<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
						onclick="JavaScript:history.go(-1);"></input> 
				</div>
			</form>
		</div>
	</div>
</body>
</html>