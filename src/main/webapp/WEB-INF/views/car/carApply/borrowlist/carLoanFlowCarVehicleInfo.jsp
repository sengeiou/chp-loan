<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!-- 车辆资料 -->
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function(){
	var commericialCompany = $("#commericialCompany").val();
	var commericialNum = $("#commericialNum").val();
	if(commericialCompany == "" || commericialCompany == null){
		$("#commericialShowOrHide").hide();
	}  
	if(commericialNum == "" || commericialNum ==  null){
		$("#commericialNumShowOrHide").hide();
	}
// 客户放弃		
$("#giveUpBtn").bind('click',function(){
	
	var url = ctx+"/car/carApplyTask/giveUp?loanCode="+ $("#loanCode").val();				
	
	art.dialog.confirm("是否客户放弃?",function(){
		$("#frameFlowForm").attr('action',url);
		$("#frameFlowForm").submit();
		});
	});
});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${loanCode}');">个人资料</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${loanCode}');">车辆信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${loanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${loanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${loanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${loanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${loanCode}');">客户开户及管辖信息</a></li>
		<input type="button"  onclick="showCarLoanHis('${loanCode}')" class="btn btn-small r mr10" value="历史">
		<input type="button" id="giveUpBtn" class="btn btn-small r mr10"  value="客户放弃"></input></div>
	</ul>
	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
	
	<div id="div1" class="content control-group" style="overflow: auto;">
		<form id="customerForm" action="${ctx}/car/carApplyTask/toCarLoanFlowInfo" method="post">
				<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
				<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
				<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
				<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
				<input type="hidden" value="${workItem.token}" name="token"></input>
				<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
				<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
				<input type="hidden" value="${carVehicleInfo.commericialCompany}" id="commericialCompany"></input>
				<input type="hidden" value="${carVehicleInfo.commericialNum}"  id="commericialNum"></input>
				<input type="hidden" id="loanCode" name="loanCode" value="${loanCode}">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
           <tr>
                <td width="32%"><label class="lab">车牌号码：</label>${carVehicleInfo.plateNumbers}</td>
                <td width="32%"><label class="lab">建议借款金额：</label>
                <fmt:formatNumber value="${carVehicleInfo.suggestLoanAmount}" pattern="#,##0.00" />
                	元</td>
                <td><label class="lab">评估师姓名：</label>${carVehicleInfo.appraiserName}</td>
            </tr>
            <tr>
                <td><label class="lab">评估金额：</label>
                <fmt:formatNumber value="${carVehicleInfo.storeAssessAmount}" pattern="#,##0.00" />
                	元</td>
                <td><label class="lab">商业险到期日：</label>
                <fmt:formatDate value='${carVehicleInfo.commercialMaturityDate}' pattern="yyyy-MM-dd"/>
                </td>
                <td><label class="lab">同类市场价格：</label>
                <fmt:formatNumber value="${carVehicleInfo.similarMarketPrice}" pattern="#,##0.00" />
          		      元</td>
                
            </tr>
			<tr>
                <td width="32%"><label class="lab">出厂日期：</label>
                <fmt:formatDate value='${carVehicleInfo.factoryDate}' pattern="yyyy-MM-dd"/>
                </td>
                <td width="32%"><label class="lab">交强险到期日：</label>
                <fmt:formatDate value='${carVehicleInfo.strongRiskMaturityDate}' pattern="yyyy-MM-dd"/>
                </td>
                <td><label class="lab">车年到期日：</label>
                <fmt:formatDate value='${carVehicleInfo.annualCheckDate}' pattern="yyyy-MM-dd"/>
                </td>
            </tr>
			<tr>
                <td width="32%"><label class="lab">车架号：</label>${carVehicleInfo.frameNumber}</td>
                <td width="32%"><label class="lab">车辆厂牌型号：</label>${carVehicleInfo.vehiclePlantModel}</td>
                <td><label class="lab">首次登记日期：</label>
                <fmt:formatDate value='${carVehicleInfo.firstRegistrationDate}' pattern="yyyy-MM-dd"/>
                </td>
            </tr>
			<tr>
                <td width="32%"><label class="lab">表征里程：</label>${carVehicleInfo.mileage}公里</td>
                <td width="32%"><label class="lab">排气量：</label>${carVehicleInfo.displacemint}L</td>
                <td><label class="lab">车身颜色：</label>${carVehicleInfo.carBodyColor}</td>
            </tr>
			<tr>
                <td width="32%"><label class="lab">变速器：</label>${carVehicleInfo.variator}</td>
                <td width="32%"><label class="lab">发动机号：</label>${carVehicleInfo.engineNumber}</td>
                <td><label class="lab">过户次数：</label>${carVehicleInfo.changeNum}次</td>
            </tr>
			<tr>
                <td width="32%"><label class="lab">改装情况：</label>${carVehicleInfo.modifiedSituation}</td>
                <td width="32%"><label class="lab">交强险保险公司：</label>${carVehicleInfo.clivtaCompany}</td>
                <td width="32%"><label class="lab">交强险单号：</label>${carVehicleInfo.clivtaNum}</td>
            </tr>
			<tr>
			    <td width="32%"><label class="lab">权属证书编号：</label>${carVehicleInfo.ownershipCertificateNumber}</td>
			    <td width="32%" id="commericialShowOrHide"><label class="lab">商业险保险公司：</label>${carVehicleInfo.commericialCompany}</td>
			</tr>
			<tr>
                <td width="32%"><label class="lab">外观检测：</label>${carVehicleInfo.outerInspection}</td>
			    <td width="32%" id="commericialNumShowOrHide"><label class="lab">商业险单号：</label>${carVehicleInfo.commericialNum}</td>
            </tr>
			<tr>
                <td width="32%" colspan="2"><label class="lab">违章及事故情况：</label>${carVehicleInfo.illegalAccident}</td>
                
            </tr>
			<tr>
                <td width="32%" colspan="2"><label class="lab">车辆评估意见：</label>${carVehicleInfo.vehicleAssessment}</td>
                
            </tr>

			</table>

			<div class="tright mr10 mb5">
				<input type="submit" class="btn btn-primary" id="customerNextBtn"
					value="下一步">
			</div>
		</form>
	</div>
</body>
</html>