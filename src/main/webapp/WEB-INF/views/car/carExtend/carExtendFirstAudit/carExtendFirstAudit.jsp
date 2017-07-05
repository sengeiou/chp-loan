<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title>展期初审</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/validate.js'></script>
<script src="${context}/js/pageinit/areaselect.js" type="text/javascript"></script>
<script src="${context}/js/car/carExtend/carExtendDetailsCheck.js" type="text/javascript"></script>
<style>
   .lab {
    display: inline-block;
    height: 26px;
    line-height: 27px;
    text-align: right;
}
</style>
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
			 if(suggestLoanAmount != null && suggestLoanAmount != '' &&
					auditAmount != null && auditAmount != ''){
				if(parseFloat(auditAmount) > parseFloat(suggestLoanAmount)){
					check=false;
				}
			} 
			return this.optional(a)||check
		},"审批金额不能大于借款金额");
$(document).ready(function(){
	var dictProductType =  $("#dictProductType").val();
	if(dictProductType == "CJ01"){
		$(".hidProcess").hide();
	} else {
		$(".hidProcess").show();
	}
	//初审通过提交
	$("#handleContractCheck").bind('click',function(){
		//将金额中的","去除
		var parkingFee = $("#parkingFee").val();
		var facilityCharge = $("#facilityCharge").val();
		var auditAmount = $("#auditAmount").val();
		if(parkingFee != null){
			$("#parkingFee").val(parkingFee.replace(",", ""));
			$("#parkingFee2").val(parkingFee);
		}
		if(facilityCharge != null){
			$("#facilityCharge").val(facilityCharge.replace(",", ""));
			$("#facilityCharge2").val(facilityCharge);
		}
		if(auditAmount != null){
			$("#auditAmount").val(auditAmount.replace(",", ""));
			$("#auditAmount2").val(auditAmount);
		}
		var flag1 = false;
		$("#remark,#backNode,#dictIdIstrue,#queryResult,#queryResultPhone,#customerJobReview,#creditReport,#auditAmount,#auditCheckExamine").removeClass("required");
		if($("#sendBack").attr("checked")){
			$("#remark,#backNode").addClass("required");
			//退回时的必填验证
			flag1 = $("#trialForm").validate({
				onclick: true, 
				rules:{
					auditResult: {required:true},
					auditCheckExamine:{
						maxlength:200
						}
				}
			}).form();
		}else if($("#refused").attr("checked")){
			$("#auditCheckExamine").addClass("required");
			//拒绝时的必填验证
			flag1 = $("#trialForm").validate({
				onclick: true, 
				rules:{
					auditResult: {required:true},
					auditCheckExamine:{
						maxlength:200
						}
				}
			}).form();
		}else{
			//验证必填js
			$("#dictIdIstrue,#queryResult,#queryResultPhone,#customerJobReview,#creditReport,#auditAmount,#auditCheckExamine").addClass("required");
			flag1 = $("#trialForm").validate({
				onclick: true, 
				rules:{
					parkingFee:{
						number:true,
						maxlength:19
					},
					facilityCharge:{
						number:true,
						maxlength:19
					},
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
						maxlength:'8',
						remote:{
							type : "POST",
							url :  ctx + "/car/appraiserEntry/notRepeatSubmit",
							data:{
								auditAmount :$("#auditAmount").val(),
								loanCode :$("#loanCode").val()
							}
						}
						
					},
					auditResult: {required:true},
					auditCheckExamine: {
						maxlength:200
						}
				},
				messages: {
					auditAmount: {
						number:"请输入合法的数字",
						moreZero:'输入的数字要大于零',
						checkAmount:"审批金额不能大于借款金额",
						maxlength:'输入审批金额长度不能大于8位'
						},
					queryResult:{
						maxlength:"200字以内"
						},
					queryResultPhone:{
						maxlength:"200字以内"
						},
					customerJobReview:{
						maxlength:"200字以内"
						},
						creditReport:{
						maxlength:"200字以内"
						},
					auditCheckExamine:{
						maxlength:"200字以内"
						}
				}
			}).form();
		};
		if(flag1 == true){
			var url = ctx + "/car/carExtend/carExtendFirstAudit/firstAuditSubmit";	
			art.dialog.confirm("是否确认提交?",function(){
				$("#trialForm").attr('action',url);
				$("#trialForm").submit();
			});
		}else{
			if(parkingFee != null){
				$("#parkingFee").val($("#parkingFee2").val());
			}
			if(facilityCharge != null){
				$("#facilityCharge").val($("#facilityCharge2").val());
			}
			if(auditAmount != null){
				$("#auditAmount").val($("#auditAmount2").val());
			}
		}
	});

	// 当选择退回的的js
	$("input:radio[name='auditResult']").change(function() {
		var radioVal = $("input:radio[name='auditResult']:checked").val();
		if (radioVal == '4') { // 退回
			$(".process").show();
			$("#auditAmount, #parkingFee, #facilityCharge").attr("disabled", true);
			$(".hidProcess").hide();
		} else if (radioVal == '1') { // 通过
			$(".process").hide();
			$(".hidProcess").show();
			$("#auditAmount, #parkingFee, #facilityCharge").attr("disabled", false);
		} else if (radioVal == '3') { // 拒绝
			$(".process").hide();
			$("#auditAmount, #parkingFee, #facilityCharge").attr("disabled", true);
			$(".hidProcess").hide();
		}
	});
	$("input:radio[name='auditResult']").trigger("change");
	
	//客户放弃的js
	$("#btnGiveUp").bind('click',function(){
		var url = ctx + "/car/carExtend/carExtendFirstAudit/firstAuditGiveUp";		
		art.dialog.confirm("是否确认客户放弃?",function(){
			$("#workParam").attr('action',url);
			$("#workParam").submit();
		});
	});
	//审批金额的验证
	
	
	//省市级联js
	loan.initCity("liveProvinceId", "liveCityId","liveDistrictId");
	areaselect.initCity("liveProvinceId", "liveCityId","liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden").val());
});
/**
*根据产品类型和批复期限   获取总费率
*/
function getGross(){
	$.ajax({
		type : "POST",
		 url : ctx+"/car/apply/reviewMeetFirstAudit/isExtendGiveUp",
		data : {
			loanCode: '${workItem.bv.carLoanInfo.loanCode }'  
		},
	 	success : function(data){
			 $.ajax({
					type : "POST",
					 url : ctx+"/car/apply/reviewMeetFirstAudit/asynLoadGrossRate",
					data : {
						productTypeName : $("#productTypeID").val(),    //产品类型
						productTypeMonths : $("#productMonthsID").val(),	//产品期限
						loanCode: data  
					},
				 success : function(data){
					 //console.info(data);
					 if(data){
						$("#grossRateID").val(data);
						}else{
							$("#grossRateID").val("");
						}
					
				 }
				}); 
	 	}
	});

}
window.onload=getGross;
</script>
</head>
<body >
	<form id="workParam" class="hide">
		<input type="text" value="${workItem.flowName}" name="flowName"></input>
		<input type="text" value="${workItem.flowId}" name="flowId"></input>
		<input type="text" value="${workItem.flowCode}" name="flowCode"></input>
		 <input name="menuId" type="hidden" value="${param.menuId}" />
		<input type="text" value="${workItem.stepName}" name="stepName"></input>
		<input type="text" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="text" value="${workItem.token}" name="token"></input>
		<input type="text" name="loanCode" id="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
		<input type="text" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
	</form>
	<div
		style="height: 100%; position: absolute; top: 0px; bottom: 2px; width: 100%; overflow: hidden">
		<div class="pr30">
			<span class="f14">初审审核</span>
			<div class="r pt5 mb5">
				<input onclick="showExtendLoanInfo('${workItem.bv.carLoanInfo.applyId}')" type="button" class="btn btn-small jkcj_carExtendFirstAudit_view" value="查看" />
				<input type="button" class="btn btn-small jkcj_carExtendFirstAudit_giveUp" id="btnGiveUp" value="展期放弃">
				<input  class="btn btn-small jkcj_carExtendFirstAudit_history" onclick="showCarLoanHis('${workItem.bv.carLoanInfo.loanCode}')"  type="button" value="历史" />
			</div>
		</div>
		<div style="width: 60%; height: 97%; float: left;">
			<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0"
				width="100%" height="100%"
				src="${workItem.bv.imageUrl}">
			</iframe>
		</div>
		<div style="width: 39.9%; height: 100%; float: right; overflow: auto">
		<form:form id="trialForm" class="form-horizontal" method="post">
				<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
				<input name="menuId" type="hidden" value="${param.menuId}" />
				<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
				<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
				<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
				<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
				<input type="hidden" value="${workItem.token}" name="token"></input>
				<input type="hidden" name="loanCode" value="${workItem.bv.carLoanInfo.loanCode }" />
				<input type="hidden" name="applyId" value="${workItem.bv.carLoanInfo.applyId }" />
				<input type="hidden" id="dictProductType" value="${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',workItem.bv.carLoanInfo.dictProductType)}" />
				
				<div class="box2">
					<h2 class="f14 pl10 mt20">借款人信息</h2>
					<table class="table01" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
							<td><label class="labl">身份证号：</label>${workItem.bv.carCustomer.customerCertNum}</td>
						</tr>
						<c:forEach items="${workItem.bv.carLoanCoborrowers}" var="c" >
							<tr>
								<td><label class="labl">共借人姓名：</label>${c.coboName }</td>
								<td><label class="labl">共借人身份证号：</label>${c.certNum}</td>
							</tr>
						</c:forEach>
						<tr>
							<td><label class="labl">团队经理：</label>${workItem.bv.consTeamManagerName}</td>
							<td><label class="labl">客户经理：</label>${workItem.bv.managerName}</td>
						</tr>
						<tr>
							<td><label class="labl">产品类型：</label>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',workItem.bv.carLoanInfo.dictProductType)}</td>
							<td><label class="labl">借款期限：</label>${workItem.bv.carLoanInfo.loanMonths}天</td>
						</tr>
						<tr>
							<td><label class="labl">申请金额：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.loanApplyAmount == null ? 0 : workItem.bv.carLoanInfo.loanApplyAmount }" pattern="#,##0.00" />
							元</td>
						</tr>
						<tr>
							<td><label class="labl">门店名称： </label>${workItem.bv.carLoanInfo.storeName}</td>
							<td><!-- <label class="labl">管辖城市： </label>${workItem.bv.cityName } --></td>
						</tr>
						</table></div>
						<div class="box2">
						<h2 class="f14 pl10 mt20">车辆信息</h2>
						<table class="table01" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="labl">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
							<td><label class="labl">评估师姓名：</label>${workItem.bv.carVehicleInfo.appraiserName}</td>
						</tr>
						<tr>
							<td><label class="labl">展期建议借款金额：</label>
							<fmt:formatNumber value="${workItem.bv.carVehicleInfo.extensionSuggestAmount == null ? 0 : workItem.bv.carVehicleInfo.extensionSuggestAmount }" pattern="#,##0.00" />
							元
							<input type="hidden" id="suggestLoanAmount" value="${workItem.bv.carVehicleInfo.extensionSuggestAmount }" />
							</td>
							<td><label class="labl">展期评估金额：</label><fmt:formatNumber value="${workItem.bv.carVehicleInfo.extensionAssessAmount == null ? 0 : workItem.bv.carVehicleInfo.extensionAssessAmount }" pattern="#,##0.00" />
							元</td>
						</tr>
						<tr>
							 <c:if test="${workItem.bv.carLoanInfo.dictProductType != null && workItem.bv.carLoanInfo.dictProductType=='CJ01'}">
							<td><label class="labl">商业险到期日： </label><fmt:formatDate value="${workItem.bv.carVehicleInfo.commercialMaturityDate}" pattern="yyyy-MM-dd"/></td>
							</c:if>
							<td><label class="labl">同类市场价格：</label><fmt:formatNumber value="${workItem.bv.carVehicleInfo.similarMarketPrice == null ? 0 : workItem.bv.carVehicleInfo.similarMarketPrice }" pattern="#,##0.00" />
							元</td>
						</tr>
						<tr>
							<td><label class="labl">出厂日期： </label><fmt:formatDate value="${workItem.bv.carVehicleInfo.factoryDate}" pattern="yyyy-MM-dd"/></td>
							<td><label class="labl">交强险到期日：</label><fmt:formatDate value="${workItem.bv.carVehicleInfo.strongRiskMaturityDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td><label class="labl">车年检到期日：</label><fmt:formatDate value="${workItem.bv.carVehicleInfo.annualCheckDate}" pattern="yyyy-MM-dd"/></td>
							<td><label class="labl">首次登记日期：</label><fmt:formatDate value="${workItem.bv.carVehicleInfo.firstRegistrationDate}" pattern="yyyy-MM-dd"/></td>
						</tr>
						<tr>
							<td><label class="labl">车架号： </label>${workItem.bv.carVehicleInfo.frameNumber}</td>
							<td><label class="labl">车辆厂牌型号： </label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
						</tr>
						<tr>
							<td><label class="labl">表征里程：</label>
							<fmt:formatNumber value="${workItem.bv.carVehicleInfo.mileage == null ? 0 : workItem.bv.carVehicleInfo.mileage }" pattern="#,##0.00" />
							</td>
							<td><label class="labl">排气量： </label>${workItem.bv.carVehicleInfo.displacemint}
								<c:if test="${workItem.bv.carVehicleInfo.displacemint != null}">升</c:if>
							</td>
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
						<h2 class="f14 pl10 mt20">初审具体情况</h2></td>
						<div class="box2">
						<table class="table01" cellpadding="0" cellspacing="0" border="0" width="100%">
	 					<tr>
							<td colspan="2"><label class="labl">户籍地址：</label>
								<sys:carAddressShow detailAddress="${workItem.bv.carCustomer.customerRegisterAddressView}"></sys:carAddressShow>
							</td>
						</tr>
						<tr>
							<td colspan="2">
 							<label class="labl"><span style="color: #ff0000"></span>现住址：</label>
  							<sys:carAddressShow detailAddress="${workItem.bv.carCustomer.customerAddressView}"></sys:carAddressShow>
						</td></tr>
						<tr class="hidProcess">
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>身份证真伪：</label>
								<select name="dictIdIstrue" id="dictIdIstrue">
									<option value="">请选择</option>
									  <c:forEach items="${fns:getDictList('jk_car_idcard_fact')}" var="idcard_fact">
	                                     <option value="${idcard_fact.value}">${idcard_fact.label}</option>
	                    			</c:forEach> 
								</select>
							</td>
						</tr>
						<tr class="hidProcess">
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>客户人法查询结果：</label>
								<textarea rows="5" cols="30" name="queryResult" id="queryResult"></textarea>
							</td>
						</tr>
						<tr class="hidProcess">
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>114电话查询情况：</label>
								<textarea rows="5" cols="30" name="queryResultPhone" id="queryResultPhone"></textarea>
							</td>
						</tr>
						<tr class="hidProcess">
							<td colspan="2"><label class="labl"><span style="color: #ff0000">*</span>客户工作审核情况：</label>
								<textarea rows="5" cols="30" name="customerJobReview" id="customerJobReview"></textarea>
							</td>
						</tr>
						<tr class="hidProcess">
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
						<tr>
							<td><label class="labl">合同编号： </label>${workItem.bv.contractNo}</td>
						</tr>
						<tr class="hidProcess">
							<td>
								<label class="labl">产品类型： </label>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',workItem.bv.carLoanInfo.dictProductType)}
								<input type="hidden" id="productTypeID" name="auditBorrowProductCode" value="${workItem.bv.carLoanInfo.dictProductType }"  />
								<input type="hidden" id="productMonthsID" name="auditLoanMonths" value="30" >
							</td>
						</tr>
						<tr class="hidProcess">
							<td><label class="labl">借款期限： </label>30天</td>
						</tr>
						<tr class="hidProcess">
							<td><label class="labl"><span style="color: #ff0000">*</span>审批金额（元）：</label>
								<input id="auditAmount" type="text" name="auditAmount"/>元
								<input type="hidden" id="auditAmount2" />
							</td>
						</tr>
						<tr class="hidProcess">
							<td><label class="labl">总费率：</label>
								<input id="grossRateID" type="text" name="grossRate" readonly/>%
							</td>
						</tr>
						<c:if test="${workItem.bv.carLoanInfo.dictProductType != 'CJ01' }">
						<tr class="hidProcess">
							<td><label class="labl">停车费：</label>
								<input id="parkingFee" class="input_text70" readonly="readonly" type="text" name="parkingFee" maxLength="5" value="<fmt:formatNumber value="${workItem.bv.carLoanInfo.parkingFee == null ? 0 : workItem.bv.carLoanInfo.parkingFee}" pattern="###" />" />
								<input id="parkingFee2" type="hidden"/>
								元</td>
						</tr></c:if>
						<c:if test="${workItem.bv.carLoanInfo.dictProductType != 'CJ02' && workItem.bv.carLoanInfo.dictProductType != 'CJ03'}">
						<tr class="hidProcess">
							<td><label class="labl">平台及流量费：</label>
								<input id="facilityCharge" class="input_text70" readonly="readonly" type="text" name="facilityCharge" value="<fmt:formatNumber value="${workItem.bv.carLoanInfo.flowFee == null ? 0 : workItem.bv.carLoanInfo.flowFee}" pattern="###" />"/>
								<input id="facilityCharge2" type="hidden"/>
							元</td>
						</tr></c:if>
						<c:if test="${workItem.bv.carLoanInfo.dictProductType == 'CJ01' && workItem.bv.carLoanInfo.deviceUsedFee>0}">
						<tr class="hidProcess">
							<td><label class="labl">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee}" pattern="#,##0" />元</td>
						</tr></c:if>
<%-- 						<c:if test="${workItem.bv.carLoanInfo.dictProductType != 'CJ01' }">
						<tr class="hidProcess">	
							<td><label class="labl">停车费：</label>
								<input id="parkingFee" class="input_text70" type="text"  readonly="readonly" name="parkingFee" maxLength="5" value="<fmt:formatNumber value="${workItem.bv.carLoanInfo.parkingFee == null ? 0 : workItem.bv.carLoanInfo.parkingFee}" pattern="###" />" />
								<input id="parkingFee2" type="hidden"/>
								元</td>
						</tr></c:if> --%>
						<tr>
							<td><label class="labl"><span style="color: #ff0000">*</span>审批结果：</label>
							<span>
								<input type="radio" id="sendBack" name="auditResult" value="4" />${workItem.bv.fourLabel}
								<input type="radio" id="approved"  name="auditResult" value="1" checked="checked"/>${workItem.bv.oneLabel}
								<input type="radio" id="refused"  name="auditResult" value="3" />${workItem.bv.threeLabel}
							</span>
							</td>
						</tr>
						<tr style="display: none;" class="process" >
							<td><lable class="labl"><span style="color: red;">*</span>退回至流程节点：</lable>
    							<select id="backNode" name="backNode">
									<option value="">请选择退回节点</option>
 		 							<option value="0">评估师录入</option>
 									 <option value="1">上传资料</option>
 		 					</select>
 		 					<p>&nbsp;&nbsp;&nbsp;&nbsp;
								<span style="color: #ff0000">*</span>退回原因：
							<textarea style="width:250px;" class="textarea_refuse" id="remark" name="remark"></textarea>
							</p>
 		 					</td>
						</tr>
						<tr>
							<td><label class="labl"><span style="color: #ff0000">*</span>审批意见 ：</label>
								<textarea rows="5" cols="30" name="auditCheckExamine" id="auditCheckExamine"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="tright mt10 pr10" style="margin-bottom: 50px">
					<input type="button" id="handleContractCheck"
						class="btn btn-primary" value="提交"></input>
					<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
					onclick="JavaScript:history.go(-1);"></input>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>