<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title>车借初审办理</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="")">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<meta name="decorator" content="default" />
<script type="text/javascript" src='${context}/js/validate.js'></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">

jQuery.validator.addMethod(
		"moreZero",function(value,element,params){
			if(value > 0){
				return true;
			}else{
				return false;
			}
		},"输入的数字要大于零");
		
		


jQuery.validator.addMethod(
		"checkAmount",function(b,a){
			var check = true;
			var suggestLoanAmount = $('#suggestLoanAmount').val();
			var auditAmount = $('#auditAmount').val();
			/* var loanApplyAmount = $('#loanApplyAmount').val(); */
			 if(suggestLoanAmount != null && suggestLoanAmount != '' &&
					auditAmount != null && auditAmount != ''/*  && loanApplyAmount != null && loanApplyAmount != '' */){
				 if(parseFloat(auditAmount) > parseFloat(suggestLoanAmount)/* || parseFloat(auditAmount) > parseFloat(loanApplyAmount) */){
					check=false;
				 }
			} 
			return this.optional(a)||check
		},"审批金额不能大于建议金额");
$(document).ready(function(){
	//初审通过提交
	$("#handleContractCheck").bind('click',function(){
		$("#dictIdIstrue,#queryResult,#queryResultPhone,#backNode,#remark,#customerJobReview,#creditReport,#productTypeID,#productMonthsID,#auditAmount,#auditCheckExamine").removeClass("required");
		var flag = false;
		if($("#sendBack").attr("checked")){
			$("#backNode,#remark").addClass("required");
			//如果选择退回流程节点
			flag = $("#trialForm").validate({
				onclick: true, 
				rules:{
					auditResult: {required:true},
					auditCheckExamine: {
						maxlength:200
					}
				}
			}).form();
		}else if($("#refuse").attr("checked")){
			$("#auditCheckExamine").addClass("required");
			flag = $("#trialForm").validate({
				onclick: true, 
				rules:{
					auditResult: {required:true},
					auditCheckExamine: {
						maxlength:200
					}
				}
			}).form();
		}else{
			//如果选择通过
			//验证必填js
			$("#dictIdIstrue,#queryResult,#queryResultPhone,#customerJobReview,#creditReport,#productTypeID,#productMonthsID,#auditAmount,#auditCheckExamine").addClass("required");
			flag = $("#trialForm").validate({
				onclick: true, 
				rules:{
					queryResult: {
						maxlength:200
					},
					queryResultPhone: {
						maxlength:200
					},
					customerJobReview: {
						maxlength:200
					},
					creditReport: {
						maxlength:200
					},
					auditAmount: {
						number:true,
						moreZero:true,
						checkAmount:true,
						maxlength:8
					},
					grossRate: {
						number:true
					},
					firstServiceTariffing: {
						number:true
					},
					auditResult: {required:true},
					auditCheckExamine: {
						maxlength:200
					}
					
				},
				messages:{
					auditAmount:{
						number:"请输入合法的数字",
						moreZero:'输入的数字要大于零',
						checkAmount:"审批金额不能大于建议金额",
						maxlength:'输入审批金额长度不能大于8位'		
					}
				},
				errorPlacement: function(error, element) {  
				    error.appendTo(element.parent());
				}
			}).form();
		}
		if (flag) {
			var url = ctx + "/car/apply/reviewMeetFirstAudit/reviewMeetCommit";
			art.dialog.confirm("是否确认提交?",function(){
				$("#trialForm").attr('action',url);
				$("#trialForm").submit();
			});
		}
	});
	
	// 当选择退回的的js
	$("input:radio[name='auditResult']").change(function() {
		var radioVal = $("input:radio[name='auditResult']:checked").val();
		if (radioVal == '4') { // 退回
			$(".process").show();
			$("#auditAmount").attr("disabled", true);
			$(".hidProcess").hide();
		} else if (radioVal == '1') { // 通过
			$(".process").hide();
			$(".hidProcess").show();
			$("#auditAmount").attr("disabled", false);
		} else if (radioVal == '3') { // 拒绝
			$(".process").hide();
			$("#auditAmount").attr("disabled", true);
			$(".hidProcess").hide();
		}
	});
	$("input:radio[name='auditResult']").trigger("change");
	
	$("#backSure").bind('click',function(){
		if(checkForm($("#backForm"))){
			var url = ctx + "/car/apply/reviewMeetFirstAudit/reviewMeetCommit";
			art.dialog.confirm("是否确认退回?",function(){
				$("#backForm").attr('action',url);
				$("#backForm").submit();
			});	
		} 
	});
	//客户放弃的js
	$("#btnGiveUp").bind('click',function(){
		var url = ctx + "/car/apply/reviewMeetFirstAudit/customerGiveUp";		
		art.dialog.confirm("是否确认客户放弃?",function(){
			$("#workParam").attr('action',url);
			$("#workParam").submit();
		});
	});
});


/***
* 根据产品类型获取 审批期限
**/
$(function(){
	$("#productTypeID").change(function() {
		var productTypeID = $("#productTypeID option:selected").val();
		var auditBorrowProductNameId = $("#auditBorrowProductNameId").val($("#productTypeID option:selected").html());
		$("#grossRateID").val(null); //总费率
		$("#firstServiceTariffingId").val(null);
		if (productTypeID == "") {
			$("#productMonthsID").empty();
			$("#productMonthsID").append("<option value='' selected=true>请选择</option>");
			$("#productMonthsID").trigger("change");
		} else {
			$.ajax({
				type : "POST",
				url : ctx + "/common/productInfo/asynLoadProductMonths",
				data : {
					productCode : productTypeID,
					productType:"products_type_car_credit"
					
				},
				success : function(data) {
					
					console.info(data);
					 var resultObj = data.split(",");
					$("#productMonthsID").empty();
					$("#productMonthsID").append("<option value=''>请选择</option>");
					$.each(resultObj, function(i, item) {
							$("#productMonthsID").append("<option value=" + item + ">" + item + "</option>");
					});
					$("#productMonthsID").trigger("change"); 
				}
			});
		}
	});
	$("#productTypeID").trigger("change");
	
	
	$("#productMonthsID").change(function() {  //借款期限
		/**
		*根据产品类型和批复期限   获取总费率
		*/
		var productMonthsID = $("#productMonthsID option:selected").val();  //借款期限
		var contractVersion = "${workItem.bv.contractVersion}"; //合同版本
		var productMonths = $("#productMonthsID").val();  
		var productTypeName = $("#productTypeID option:selected").val(); //产品名称
		var loanCode = '${workItem.bv.carLoanInfo.loanCode}';
		var deviceUsedFee = '${workItem.bv.carLoanInfo.deviceUsedFee}'; //设备使用费
		var dictProductType = '${workItem.bv.carLoanInfo.dictProductType}'; 
		//如果产品期限为空，则总费率置空
		if(productMonthsID == ""){
			$("#grossRateID").empty();
			$("#grossRateID").trigger();
			$("#deviceUsedFee").val('${workItem.bv.carLoanInfo.deviceUsedFee}');
			$("input[name='deviceUsedFee']").val('${workItem.bv.carLoanInfo.deviceUsedFee}');
		} else {
			$.ajax({
				type : "POST",
				 url : ctx+"/car/apply/reviewMeetFirstAudit/asynLoadGrossRate",
				data : {
					productTypeName : $("#productTypeID option:selected").val(),    //产品类型
					productTypeMonths : $("#productMonthsID option:selected").val(),	//产品期限
					loanCode : loanCode  //借款编码
				},
			 success : function(data){
				 	if(data){
					   if(contractVersion=='1.4'){
							if(productMonths != '30' && productMonths != '90' && productTypeName=='CJ01'){
								$("#grossRateID").val("2.4");
							}else{
								$("#grossRateID").val(data);
							}
							$("#deviceUsedFee").text("0");
							$("input[name='deviceUsedFee']").val("0");
						}else{ //1.5/1.6的合同
							$("#grossRateID").val(data);
							if(dictProductType == 'CJ01'){
								$("#deviceUsedFee").text((productMonths/30)*100);
								$("input[name='deviceUsedFee']").val((productMonths/30)*100);
							}else{
								$("#deviceUsedFee").text("0");
								$("input[name='deviceUsedFee']").val("0");
							}
						}
					}else{
						$("#grossRateID").val(null);
					}
				
			 }
			});
		}
		//根据批复期限得到首期服务费率
			$.ajax({
				type : "POST",
				 url : ctx+"/car/apply/reviewMeetFirstAudit/asynLoadFirstServiceRate",
				data : {
					loanCode : loanCode  //借款编码
				},
			 	success : function(data){
			 		var firstServiceCharge = JSON.parse(data);
			 		if(firstServiceCharge==0){//保留原有业务
			 			//$("#firstServiceTariffingId").val("0.000");
			 			if(productMonths == '30' || productMonths == '90'){
			 				if(firstServiceRate=='4.00000'&&loanMonthId>90){
			 					$("#firstServiceTariffingId").val(2.000);
			 				}else if(firstServiceRate=='2.00000'&&loanMonthId<=90){
			 					$("#firstServiceTariffingId").val(2.000);
			 				}else{
			 					$("#firstServiceTariffingId").val(0.000);
			 				}
			 			}else{
			 				if(firstServiceRate=='2.00000'&&loanMonthId<=90){
			 					$("#firstServiceTariffingId").val(4.000);
			 				}else if(firstServiceRate=='4.00000'&&loanMonthId>90){
			 					$("#firstServiceTariffingId").val(4.000);
			 				}else{
			 					$("#firstServiceTariffingId").val(2.000);
			 				}
			 			}
			 		}else{
						if(productMonthsID <=90){
							$("#firstServiceTariffingId").val(firstServiceCharge.ninetyBelowRate+".000");
						}else{
							$("#firstServiceTariffingId").val(firstServiceCharge.ninetyAboveRate+".000");
						}
			 		}

			 	}
			});
	});
	
});




</script>
</head>
<body>
	<form id="workParam" class="hide">
		<input type="text" value="${workItem.flowName}" name="flowName"></input>
		<input type="text" value="${workItem.flowId}" name="flowId"></input>
		<input type="text" value="${workItem.flowCode}" name="flowCode"></input>
		 <input name="menuId" type="hidden" value="${param.menuId}" />
		<input type="text" value="${workItem.stepName}" name="stepName"></input>
		<input type="text" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="text" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
		<input type="text" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
	</form>
	<div style="height: 100%; position: absolute; top: 0px; bottom: 2px; width: 100%; overflow: hidden">
		<div class="pr30">
			<span class="f14">初审审核</span>
			<div class="r pt5 mb5">
				<input onclick="showCarLoanInfo('${workItem.bv.carLoanInfo.loanCode}')" type="button" class="btn btn-small" value="查看" />
				<input type="button" class="btn btn-small" id="btnGiveUp" value="客户放弃">
				<input  class="btn btn-small" onclick="showCarLoanHis('${workItem.bv.carLoanInfo.loanCode}')"  type="button" value="历史" />
				<button  class="btn btn-small"  data-target="#back_div" data-toggle="modal">退回</button>
			</div>
		</div>
		<div style="width: 60%; height: 97%; float: left;">
			<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0"
				width="100%" height="100%" src="${workItem.bv.imageUrl}">
				<!-- src="http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111"> -->
				 
			</iframe>
		</div>
		<div style="width: 40%; height: 100%; float: right;  overflow:auto;" >
		<form:form id="trialForm" class="form-horizontal" method="post">
				<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
				 <input name="menuId" type="hidden" value="${param.menuId}" />
				<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
				<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
				<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
				<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
				<input type="hidden" value="${workItem.token}" name="token"></input>
				<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
				<input type="hidden" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
				<input type="hidden" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
				<input type="hidden" name="deviceUsedFee" />
				<h2 class="f14 pl10 mt20">客户信息</h2>
				<div class="box2">
					<table class="table02" cellpadding="0" cellspacing="0" border="0" >
						<tr>
							<td><label class="labl">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
							<td><label class="labl">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
						</tr>
						<tr>
							<td><label class="labl">门店名称：</label>${workItem.bv.carLoanInfo.storeName}</td>
							<td><label class="labl">团队经理：</label>${workItem.bv.consTeamManagerName}</td>
						</tr>
						<tr>
							<td><label class="labl">产品类型：</label><span id="productName">${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',workItem.bv.carLoanInfo.dictProductType)}</span></td>
							<td><label class="labl">借款期限：</label>${workItem.bv.carLoanInfo.loanMonths}
							<c:if test="${workItem.bv.carLoanInfo.loanMonths != null}">天</c:if>
							<input type="hidden" id ="loanMonthId" value="${workItem.bv.carLoanInfo.loanMonths}" />
							<input type="hidden" id ="firstServiceRate" value="${workItem.bv.carLoanInfo.firstServiceTariffingRate}" />
							</td>
						</tr>
						<tr>
							<td><label class="labl">申请金额：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.loanApplyAmount == null ? 0 : workItem.bv.carLoanInfo.loanApplyAmount }" pattern="#,##0.00" />
							<c:if test="${workItem.bv.carLoanInfo.loanApplyAmount != null}">元</c:if>
							<input id="loanApplyAmount" type="hidden" value="${workItem.bv.carLoanInfo.loanApplyAmount}"/></td>
							<td><label class="labl">客户经理：</label>${workItem.bv.managerName}</td>
						</tr>
						<c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
							<tr>
								<td><label class="labl">共借人姓名：</label>${cobos.coboName}</td>
								<td><label class="labl">共借人证件号码：</label>${cobos.certNum}</td>
								<td></td>
							</tr>
						</c:forEach>
						<!-- 
						<tr>
							<td><label class="lab">管辖城市：</label>${workItem.bv.jurisdictionCity}</td>
						</tr>-->
						</table></div>
						<h2 class="f14 pl10 mt20">车辆信息</h2>
						<div class="box2">
						<table class="table02" cellpadding="0" cellspacing="0" border="0" >
						<tr>
							<td><label class="labl">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
							<td><label class="labl">评估师姓名：</label>${workItem.bv.carVehicleInfo.appraiserName}</td>
						</tr>
						<tr>
							<td><label class="labl">建议借款金额：</label><fmt:formatNumber value="${workItem.bv.carVehicleInfo.suggestLoanAmount == null ? 0 : workItem.bv.carVehicleInfo.suggestLoanAmount }" pattern="#,##0.00" />
							<c:if test="${workItem.bv.carVehicleInfo.suggestLoanAmount != null}">元</c:if>
							<input type="hidden" id="suggestLoanAmount" value="${workItem.bv.carVehicleInfo.suggestLoanAmount }" />
							</td>
							<td><label class="labl">评估金额：</label><fmt:formatNumber value="${workItem.bv.carVehicleInfo.storeAssessAmount == null ? 0 : workItem.bv.carVehicleInfo.storeAssessAmount }" pattern="#,##0.00" />
							<c:if test="${workItem.bv.carVehicleInfo.storeAssessAmount != null}">元</c:if></td>
						</tr>
						<tr>
							 <c:if test="${workItem.bv.dictProductType != null&&workItem.bv.dictProductType=='CJ01'}">
							<td><label class="labl">商业险到期日： </label><fmt:formatDate value="${workItem.bv.carVehicleInfo.commercialMaturityDate}" pattern="yyyy-MM-dd"/></td>
							</c:if>
							<td><label class="labl">同类市场价格：</label><fmt:formatNumber value="${workItem.bv.carVehicleInfo.similarMarketPrice == null ? 0 : workItem.bv.carVehicleInfo.similarMarketPrice }" pattern="#,##0.00" />
							<c:if test="${workItem.bv.carVehicleInfo.similarMarketPrice != null}">元</c:if></td>
						</tr>
						<tr>
							<td><label class="labl">出厂日期： </label><fmt:formatDate value="${workItem.bv.carVehicleInfo.factoryDate}" pattern="yyyy-MM-dd"/></td>
							<td><label class="labl">交强险到期日：</label><fmt:formatDate value="${workItem.bv.carVehicleInfo.strongRiskMaturityDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td><label class="labl">车险到期日：</label><fmt:formatDate value="${workItem.bv.carVehicleInfo.annualCheckDate}" pattern="yyyy-MM-dd"/></td>
							<td><label class="labl">车架号： </label>${workItem.bv.carVehicleInfo.frameNumber}</td>
						</tr>
						<tr>
							<td><label class="labl">车辆厂牌型号： </label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
							<td><label class="labl">首次登记日期：</label><fmt:formatDate value="${workItem.bv.carVehicleInfo.firstRegistrationDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td><label class="labl">表征里程：</label>${workItem.bv.carVehicleInfo.mileage}</td>
							<td><label class="labl">排气量： </label>${workItem.bv.carVehicleInfo.displacemint}</td>
						</tr>
						<tr>
							<td><label class="labl">车身颜色：</label>${workItem.bv.carVehicleInfo.carBodyColor }</td>
							<td><label class="labl">变速器： </label>${workItem.bv.carVehicleInfo.variator }</td>
						</tr>
						<tr>
							<td><label class="labl">发动机号：</label>${workItem.bv.carVehicleInfo.engineNumber }</td>
							<td><label class="labl">过户次数：</label>${workItem.bv.carVehicleInfo.changeNum }</td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl">权属证书编号：</label>${workItem.bv.carVehicleInfo.ownershipCertificateNumber}</td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl">改装情况：</label>${workItem.bv.carVehicleInfo.modifiedSituation}</td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl">外观检测：</label>${workItem.bv.carVehicleInfo.outerInspection}</td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl">违章及事故情况：</label>${workItem.bv.carVehicleInfo.illegalAccident}</td>
						</tr>
						<tr>
							<td colspan="2"><label class="labl">车辆评估结论：</label>${workItem.bv.carVehicleInfo.vehicleAssessment}</td>
						</tr>
						
						</table>
						</div>
						<h2 class="f14 pl10 mt20">初审具体情况</h2>
						<div class="box2">
						<table style="width:100%">
						<sys:carProductShow2Col parkingFee="${workItem.bv.carLoanInfo.parkingFee}" flowFee="${workItem.bv.carLoanInfo.flowFee}" contractVersion="${workItem.bv.contractVersion}"
				            dictGpsRemaining="${workItem.bv.carLoanInfo.dictGpsRemaining}" facilityCharge="${workItem.bv.carLoanInfo.facilityCharge}" 
				            dictIsGatherFlowFee="${workItem.bv.carLoanInfo.dictIsGatherFlowFee}" deviceUsedFee="${workItem.bv.carLoanInfo.deviceUsedFee}"
				            dictSettleRelend="${workItem.bv.carLoanInfo.dictSettleRelendName}" productType="${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',workItem.bv.carLoanInfo.dictProductType)}"></sys:carProductShow2Col>
					<td colspan="2"><label class="labl">户籍地址：</label>  
					<sys:carAddressShow detailAddress="${workItem.bv.carCustomer.customerRegisterAddressView }"></sys:carAddressShow>
							</td>
						</tr>
						<tr ><td colspan="2"><label class="labl">客户现住址：</label>
						<sys:carAddressShow detailAddress="${workItem.bv.carCustomer.customerAddressView }"></sys:carAddressShow>
						</td>
						</tr>
						<tr class='hidProcess'>
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>身份证真伪：</label>
								<select id="dictIdIstrue" name="dictIdIstrue" class="input_text70">
									<option value="">请选择</option>
									  <c:forEach items="${fns:getDictList('jk_car_idcard_fact')}" var="idcard_fact">
	                                     <option value="${idcard_fact.value}">${idcard_fact.label}</option>
	                    			</c:forEach> 
								</select>
							</td>
						</tr>
						<tr class='hidProcess'>
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>客户人法查询 ：</label>
								<textarea rows="5" cols="30" name="queryResult" id="queryResult"></textarea>
							</td>
						</tr>
						<tr class='hidProcess'>
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>114电话查询情况：</label>
								<textarea rows="5" cols="30" name="queryResultPhone" id="queryResultPhone"></textarea>
							</td>
						</tr>
						<tr class='hidProcess'>
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>客户工作审核情况：</label>
								<textarea rows="5" cols="30" name="customerJobReview" id="customerJobReview"></textarea>
							</td>
						</tr>
						<tr class='hidProcess'>
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>征信报告显示情况：</label>
								<textarea rows="5" cols="30" name="creditReport" id="creditReport"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<h2 class="f14 pl10 mt20">审批</h2>
				<div class="box2 ">
					<table class=" table1" cellpadding="0" cellspacing="0" border="0"
						width="100%">
						<tr class="hidProcess">
							<td><label class="lab" style="width:100px"><span class="red">*</span>产品类型：</label>
								 <select id="productTypeID" name="dictProductType" class="input_text70">
				                   <option value="">请选择</option>
				                    <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="pro">
									    <option value="${pro.productCode}"
									    <c:if test="${workItem.bv.carLoanInfo.dictProductType==pro.productCode}">
									     selected = true
									   </c:if>>${pro.productName}</option>
									 </c:forEach> 
				                 </select> 
				                 <input type="hidden" name="" id="auditBorrowProductNameId">
							</td>
							<td><label class="lab" style="width:100px"><span class="red">*</span>借款期限：</label>
								<select id="productMonthsID" name="dictAuditMonths" class="input_text70">
				                   <option value="">请选择</option>
				                 </select> 
							</td>
						</tr>
						<tr class="hidProcess">
							<td colspan="2"><label class="lab"style="width:100px"><span class="red">*</span>审批金额（元）：</label>
								<input type="text" class="input_text70" id="auditAmount" name="auditAmount" maxLength="10"/>
							</td>
						</tr>
						<tr class="hidProcess">
							<td><label class="lab" style="width:100px">总费率：</label>
								<input id="grossRateID" type="text" class="input_text70" name="grossRate" readonly/>%
							</td>
							<td><label class="lab" style="width:100px">首期服务费率：</label>
								<input type="text" class="input_text70" id="firstServiceTariffingId" name="firstServiceTariffing" readonly/>%
							</td>
						</tr>
						<tr>
							<td colspan="2"><label class="lab" style="width:100px"><span style="color: #ff0000">*</span>审批结果：</label>
							<span>
								<input type="radio"  id="sendBack" name="auditResult" value="4" />${workItem.bv.fourLabel}
								<input type="radio"  id="pass" name="auditResult" value="1" checked="checked"/>${workItem.bv.oneLabel}
								<input type="radio"  id="refuse" name="auditResult" value="3" />${workItem.bv.threeLabel}
							</span>
							<span class="process"><p>
							&nbsp;&nbsp;&nbsp;&nbsp;
							<span style="color: #ff0000">*</span>退回至流程节点：
							<select id="backNode" name="backNode" >
 		 						<option value="">请选择退回节点</option>
 							 	<option value="0">评估师录入</option>
 		 						<option value="1">车借申请</option>
 		 					</select></p>
							<p>&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="color: #ff0000">*</span>退回原因：
							<textarea rows="" cols="" class="textarea_refuse" id="remark" name="remark" style="width: 280px;"></textarea>
							</p>
							</span>
							</td>
						</tr>
						<tr>
							<td colspan="2"><label class="lab" style="width:100px"><span style="color: #ff0000">*</span>审批意见：</label>
								<textarea rows="5" cols="30" name="auditCheckExamine" id = "auditCheckExamine"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="tright mt10 pr30" style="margin-bottom: 50px">
					<input type="button" id="handleContractCheck"
						class="btn btn-primary" value="提交"></input>
					<input type="button" onclick="history.go(-1);"
						class="btn btn-primary" value="取消"></input>
				</div>
			</form:form>
		</div>
	</div>
	        	<!--退回弹框  -->
	<div id="back_div" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>

					<div class="l">
						<h4 class="pop_title">退回</h4>
					</div>
				</div>

				<div class="modal-body">
					<form id="backForm">
						<input type="hidden" name="auditResult" value="4" /> <input
							type="hidden" value="${workItem.flowName}" name="flowName"></input>
						<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
						<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
						 <input name="menuId" type="hidden" value="${param.menuId}" />
						<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
						<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
						<input type="hidden" value="${workItem.token}" name="token"></input>
						<input type="hidden"
							value="${workItem.flowProperties.firstBackSourceStep}"
							name="firstBackSourceStep"></input> <input type="hidden"
							name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
						<input type="hidden" name="applyId"
							value="${workItem.bv.carLoanInfo.applyId }" />
						<table class="table4" cellpadding="0" cellspacing="0" border="0"
							width="100%" id="tpTable">
							<tr>
							<td>
								<p>
										&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: #ff0000">*</span>退回至流程节点：
										<select name="backNode" class="required">
											<option value="">请选择退回节点</option>
											<option value="0">评估师录入</option>
											<option value="1">车借申请</option>
										</select>
									</p></td>
							</tr>
									<tr>
							<td><p>
										&nbsp;&nbsp;&nbsp;&nbsp; <span style="color: #ff0000">*</span>退回原因：
										<textarea rows="" cols="" class="textarea_refuse required"
											name="remark"></textarea>
									</p>
							</td>
							</tr>
						</table>
					</form>
				</div>
				<div class="modal-footer">
					<button id="backSure" class="btn btn-primary" aria-hidden="true">确定</button>
					<!-- <input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
					onclick="JavaScript:history.go(-1);"></input>  -->
					<button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				</div>

			</div>
		</div>

	</div>
</body>
</html>