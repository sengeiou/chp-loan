<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<head>
<title>合同审核</title>
<meta name="decorator" content="default" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/car/contract/carContractHandle.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src='${context}/js/car/contract/contractAudit.js'></script>
<script type="text/javascript" src="${ctxStatic}/lightbox/jquery.lightbox.js"></script>
<link rel="stylesheet" href="${ctxStatic}/lightbox/css/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript">
	$(function(){
		var text = $("#textarea").val();
    	var counter = text.length;
    	$(".textareP").find("var").text(200-counter);
    	
    	$("#textarea").keyup(function() {
    		var text = $("#textarea").val();
    		var counter = text.length;
    		$(".textareP").find("var").text(200-counter);
    	});
    	
	});
	
	$(function(){
		$(".lightbox-2").lightbox({
		    fitToScreen: true,
		    scaleImages: true,
		    xScale: 1.2,
		    yScale: 1.2,
		    displayDownloadLink: false,
			imageClickClose: false
	    });
		
		$("input:radio[name='checkResult']").change(function() {
			var changeFlag = $("input:radio[name='checkResult']:checked").val();
			if (changeFlag == 'TO_GRANT_CONFIRM') {
				$("#backNode").removeClass("required");
				$("#textarea").val("");
				$(this).parents("tr").next("tr").hide();
				//$(this).parents("tr").next("tr").next("tr").show();
			} else {
				$(this).parents("tr").next("tr").show();
				//$(this).parents("tr").next("tr").next("tr").hide();
				$("#backNode").removeClass("required");
			}
		});
		$("input:radio[name='checkResult']").trigger("change");
		
		$("#xyckBtn").click(function(){
		  var loanCode=$('#loanCode').val();
		  var contractCode=$('#contractCode').val();
		  var url='${ctx}/car/carContract/checkRate/xieyiList?loanCode='+loanCode+'&contractCode='+contractCode;
		  art.dialog.open(url, {  
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1500,
			   height:600
			},false); 
		});
	})
	
</script>
</head>
<body>
	<form id="param" class="hide">
		<input type="text" id="loanCode" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
		<input type="text" id="contractCode" name="contractCode" value="${workItem.bv.carContract.contractCode }" />
	</form>
	<form id="workParam" class="hide">
		<input type="text" value="${workItem.flowName}" name="flowName"></input>
		<input type="text" value="${workItem.flowId}" name="flowId"></input>
		<input type="text" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="text" value="${workItem.stepName}" name="stepName"></input>
		<input type="text" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="text" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
		<input type="hidden" id="GPSchai" value="${workItem.bv.carLoanInfo.dictGpsRemaining }"/>
        <input type="hidden" id="getFee" value="${workItem.bv.carLoanInfo.dictIsGatherFlowFee }"/>
	</form>
	<div style="height: 100%; position: absolute; top: 0px; bottom: 2px; width: 100%; overflow: hidden">
			<div class="tright pt5 pb5 pr10">
				<c:if test="${workItem.bv.isLargeAmount == 1}">
					<input type="button" class="btn btn-small jkcj_carContractCheckForm_Large_view" applyId="${workItem.bv.carLoanInfo.applyId}" loanCode="${workItem.bv.carLoanInfo.loanCode}" id="largeAmountBtn" value="大额查看">
				</c:if>
				
			</div>
		<div style="width: 60%; height: 97%; float: left;">
			<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0"
				width="100%" height="100%" src="${workItem.bv.imageurl}"
				<!-- src="http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111"> -->
			</iframe>
		</div>
		<div style="width: 39.9%; height: 100%; float: right; overflow: auto;">
			
				<h2 class="f14 pl10">客户信息</h2>
				<div class="box2" style="overflow-y:auto">
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td colspan="2"><label class="labl" style="width:70px">门店名称：</label>${workItem.bv.carLoanInfo.storeName}</td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl" style="width:70px">合同编号：</label>${workItem.bv.carContract.contractCode}</td>
						</tr>
						<tr>
							<td><label class="labl">借款人姓名：</label>${workItem.bv.carCustomer.customerName}</td>
							<td><label class="labl">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
						</tr>
						<c:if test="${workItem.bv.carCustomer.idValidScore!=null&&workItem.bv.carCustomer.idValidScore<=40}">
						<tr>
							<td style="color: red">该用户身份的公安验证分数为30-40之间请注意！</td>
						</tr>
						</c:if>
						<tr>
					        <td>
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
					         </c:if>
					        </td>
					        <td></td>
					      </tr>
						<%-- <tr>
							<td><label class="labl">借款人手机号：</label>${workItem.bv.carCustomer.customerPhoneFirst}</td>
							<td></td>
						</tr> --%>
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
				       	     <td></td>
				      	    </tr>
							<%-- <tr>
								<td><label class="labl">共借人手机号：</label>${cobos.mobile}</td>
								<td></td>
								<td></td>
							</tr> --%>
						</c:forEach>
						</table>
						<h2 class="f14 pl10">银行信息</h2>
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl" style="width:73px">账户姓名：</label>${workItem.bv.carCustomerBankInfo.bankAccountName}
							&nbsp;&nbsp;&nbsp;<span>
				<input type="checkbox" <c:if test="${workItem.bv.carCustomerBankInfo.israre == '1'}">checked='checked' </c:if> disabled="disabled"  /><lable>是否生僻字</lable>
				</span></td>
							<td><label class="labl" style="width:73px">开户行：</label>${workItem.bv.carCustomerBankInfo.cardBank}</td>
						</tr>
						<tr>
							<td><label class="labl" style="width:73px">开卡省市：</label>
							<sys:carAddressShow detailAddress="${workItem.bv.carCustomerBankInfo.bankProvinceCity }"></sys:carAddressShow>
							</td>
							<td colspan="2"><label class="labl" style="width:110px">开卡行支行名称：</label>${workItem.bv.carCustomerBankInfo.applyBankName}</td>
						</tr>
						<tr>
							<td><label class="labl" style="width:73px">银行账号：</label>${workItem.bv.carCustomerBankInfo.bankCardNo}</td>
							<td><label class="labl">签约平台：</label>
									<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="curItem">
				                     <c:if test="${curItem.label!='中金' && curItem.value!='6'}">
									    <c:if test="${workItem.bv.carCustomerBankInfo.bankSigningPlatform==curItem.value}">
									    	${curItem.label}
									    </c:if>
									  </c:if>
								    </c:forEach> 
							</td>
						</tr>
						</table>
						<h2 class="f14 pl10">汇诚审批结果</h2>
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
							<td><label class="labl">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel} </td>
						</tr>
						<tr>
							<td><label class="labl">车架号：</label>${workItem.bv.carVehicleInfo.frameNumber}</td>
							<td><label class="labl">发动机号：</label>${workItem.bv.carVehicleInfo.engineNumber} </td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl" >权属证书编号：</label>
							${workItem.bv.carVehicleInfo.ownershipCertificateNumber}</td>
							
							</tr>
						<tr>
							<td><label class="labl">批借产品：</label>
								<c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ01'}">GPS</c:if>
								<c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ02'}">移交</c:if>
								<c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ03'}">质押</c:if>
							</td>
							<td>
							<c:choose>
								<c:when test="${workItem.bv.carLoanInfo.dictProductType=='CJ01'}"><label class="labl">设备费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.facilityCharge == '' ? 0 : workItem.bv.carLoanInfo.facilityCharge }" pattern="#,##0.00" />元</c:when>
								<c:otherwise><label class="labl">停车费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.parkingFee == '' ? 0 : workItem.bv.carLoanInfo.parkingFee }" pattern="#,##0.00" />元</c:otherwise>
							</c:choose>
							</td>
						</tr>
						<c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ01'}">
						<tr>
							<td><label class="labl">结清再借：</label>${workItem.bv.carLoanInfo.dictSettleRelend=='1' ? '是' :'否'}</td>
							<td><label class="labl">GPS是否拆除：</label>${workItem.bv.carLoanInfo.dictGpsRemaining =='1' ? '是' :'否'}</td>
							<c:if test="${workItem.bv.carContract.contractVersion =='1.5' || workItem.bv.carContract.contractVersion =='1.6'}">
							<tr><td><label class="labl">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee == null ? 0 : workItem.bv.carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元</td></tr>
							
						</c:if>	
						</tr>
						</c:if>
						<c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ01' && workItem.bv.carLoanInfo.dictGpsRemaining !='1'}">
						<tr>
							<td><label class="labl">是否收取平台流量费：</label>${workItem.bv.carLoanInfo.dictIsGatherFlowFee =='1' ? '是' :'否'}</td>
						
						<c:if test="${workItem.bv.carLoanInfo.dictGpsRemaining !='1'}">
							<td><label class="labl">平台及流量费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.flowFee == '' ? 0 : workItem.bv.carLoanInfo.flowFee }" pattern="#,##0.00" />元</td>
						</c:if>
						</tr>
						</c:if>
						<tr>
							<td><input type="hidden" id="productTypeName" value='${workItem.bv.carContract.productTypeName}'/><label class="labl">批借期限：</label>${workItem.bv.carContract.contractMonths}天</td>
							<td><label class="labl">批借金额：</label><fmt:formatNumber value="${workItem.bv.carContract.auditAmount}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">首期服务费率：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.firstServiceTariffingRate}" pattern="0.00" />%</td>
							<td>
								<c:if test="${workItem.bv.carAuditResult.outVisitDistance != null && workItem.bv.carAuditResult.outVisitDistance>0}">
									<label class="labl" >外访距离：</label><fmt:formatNumber  value="${workItem.bv.carAuditResult.outVisitDistance}"  pattern="0.00"  />公里
								</c:if>
							</td>
						</tr></table>
						<h2 class="f14 pl10">借款结果</h2>
						<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">总费率：</label><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.0000" />%</td>
							<td>
							<!--  <label class="labl">首期服务费率：</label><fmt:formatNumber value="${workItem.bv.carAuditResult.firstServiceTariffing}" pattern="0.00" />%-->
							</td>
						</tr>
						<tr>
							<td><label class="labl">利息率：</label><fmt:formatNumber value=" ${workItem.bv.carCheckRate.interestRate}" pattern="0.0000" />%</td>
							<td><label class="labl">月利息：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.monthlyInterest }" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">中间人：</label>${workItem.bv.carContract.midIdName }</td>
							<td></td>
						</tr>
						<tr>
							<td><label class="labl">合同金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.contractAmount}" pattern="0.00" />元</td>
							<td><label class="labl">实放金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.feePaymentAmount}" pattern="0.00" />元</td>
						</tr>
						<c:if test="${workItem.bv.carContract.contractVersion ne '1.3'}">
                		<tr>
							<td><label class="labl">首期服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.firstServiceTariffing}" pattern="0.00"/>元</td>
                			<td>
                				<c:if test="${workItem.bv.carCheckRate.outVisitFee != null && workItem.bv.carCheckRate.outVisitFee>0}">
									<label class="labl" >外访费：</label><fmt:formatNumber  value="${workItem.bv.carCheckRate.outVisitFee}"  pattern="0.00"/>元
								</c:if>
                			</td>
                		</tr>
                		</c:if>
						<tr>
							<td><label class="labl">月还金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.monthRepayAmount}" pattern="0.00" />元</td>
							<td><label class="labl">综合服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.comprehensiveServiceFee}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">审核费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.auditFee}" pattern="0.00" />元</td>
							<td><label class="labl">咨询费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.consultingFee}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">居间服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.intermediaryServiceFee}" pattern="0.00" />元</td>
							<td><label class="labl">信息服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.informationServiceCharge}" pattern="0.00" />元</td>
						</tr>
						<tr>
							<td><label class="labl">首期还款日：</label><fmt:formatDate value='${workItem.bv.carContract.contractReplayDay}' pattern="yyyy-MM-dd"/></td>
							<td><label class="labl">合同签订日期：</label><fmt:formatDate value='${workItem.bv.carContract.contractDueDay}' pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td><label class="labl">还款付息方式：</label>${workItem.bv.carContract.dictRepayMethod}</td>
							<td></td>
						</tr>
					</table>
				
			<tr><td colspan="2"><p class="bar"></p></td></tr>
			<form id="carContractForm">
			
					<table class=" table1" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr>
							<td><label class="labl"><span style="color: #ff0000">*</span>审核结果：</label>
							<c:choose>
								<c:when test="${workItem.bv.carLoanInfo.dictLoanStatus == '25' && (workItem.bv.carLoanInfo.dictBackMestype == '6' || workItem.bv.carLoanInfo.dictBackMestype == '7')}">
									<input name="checkResult" type="radio" class="jkcj_lendCarsigningVerify_refunded" value="CONTRACT_CHECK_BACK" checked="true"/>不通过退回</td>
								</c:when>
								<c:otherwise>
									<input name="checkResult" type="radio" value="TO_GRANT_CONFIRM" checked="true"/>通过
									<input name="checkResult" type="radio" class="jkcj_lendCarsigningVerify_refunded" value="CONTRACT_CHECK_BACK" />不通过退回</td>
								</c:otherwise>
							</c:choose>
							<td></td>
						</tr>
						<tr style="display: none;">
							<td><label class="labl"><span style="color: #ff0000">*</span>退回至流程节点：</label>
							<select name="backNode" id="backNode" style="width:140px;">
									<option value="">请选择退回节点</option>
									<c:forEach
										items="${fns:getDictLists('16,20', 'jk_car_loan_status')}"
										var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
							</select><br/>
							<label class="labl">退回原因：</label><select id="backNodeResult" name="dictBackMestype" style="width:140px;">
									<option value="">请选择</option>
									<option value="风险客户">风险客户</option>
									<option value="客户主动放弃">客户主动放弃</option>
									<option value="资料错误或少资料">资料错误或少资料</option>
									<option value="提前上传签约资料">提前上传签约资料</option>
									<option value="签字潦草">签字潦草</option>
									<option value="修改合同签署时间">修改合同签署时间</option>
									<option value="修改账户姓名">修改账户姓名</option>
									<option value="其他">其他</option>
							</select>
							</td>
							<td></td>
						</tr>
						<tr>
							<td>
							<label class="labl">审核意见：</label>
							<textarea name="backReason" class="input_text70" id="textarea" style="width: 230px;height: 120px" maxlength="200"></textarea>
							<span class="textareP">剩余<var style="color:#C00">--</var>字符</span>
							</td>
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