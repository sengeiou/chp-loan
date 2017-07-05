<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<!-- 借款信息 -->
<head>
<meta name="decorator" content="default" />
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewExtendMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendTermCustomer.js"></script>
<script type="text/javascript">
	jQuery.validator.addMethod("checkAmount", function(b, a) {
		var check = true;
		var lastLastAmount = '${contractAmount}';
		var loanApplyAmount = $('#loanApplyAmount').val();
		if (lastLastAmount != null && lastLastAmount != '' && loanApplyAmount != null && loanApplyAmount != '') {
			if (parseFloat(loanApplyAmount) > parseFloat(lastLastAmount)) {
				check = false;
			}
		}
		return this.optional(a) || check
	}, "申请额度应该小于等于前次合同金额");
	jQuery.validator.addMethod("checkParkingAmount", function(b, a) {
		var check = true;
		var parkingAmount = $("#parkingAmount").val();
		
		if (parkingAmount != null && parkingAmount != '') {
			if (parkingAmount > 1000) {
				check = false;
			}
		}
		return this.optional(a) || check
	}, "停车费应小于1000元");
	$(document).ready(function() {
		/* loan.initProduct("productId", "monthId");
		loan.selectedProduct("productId", "monthId");
		areaselect.initCity("storeProvice", "storeCity",
				  null, $("#storeCityHidden").val(), null); */
		$("#applyInfoNextBtn").bind('click', function() {
			//验证JS
			$("#carLoanFlowInfo").validate({
				onclick : true,
				rules : {
					queryResult : {
						maxlength : 200
					},
					loanApplyAmount : {
						number : true,
						checkAmount : true
					},
					parkingFee :{
						checkParkingAmount : true
					}
				},
				message : {
					queryResult : {
						maxlength : '200以内'
					},
					loanApplyAmount : {
						number : "请输入合法的数字",
						checkAmount : "申请额度应该小于等于前次合同金额"
					},
					parkingFee :{
						checkParkingAmount :"停车费应小于1000元"
					}
				}
			});
			
			$("#carLoanFlowInfo").submit();
		});
		$('input:radio[name="dictIsGatherFlowFee"]').change(function() {
			if($(this).val()=='1'){
				$("#flowFee").show();
			}else{
				$("#flowFee").hide();
			}
		}); 
	});
</script>
<body>
	<ul class="nav nav-tabs">
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarContract?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li class="active"><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendInfo?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowCustomer?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendCompany?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendContact?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toExtendCoborrower?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowBank?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
	</ul>

	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
	<div id="div3" class="content control-group">
		<form id="carLoanFlowInfo" method="post"
			action="${ctx}/car/carExtendApply/carExtendLoanFlowInfo">
			<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
			<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
			<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
			<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
			<input type="hidden" value="${workItem.token}" name="token"></input>
			<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
			<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
			<input type="hidden" value="${carLoanInfo.customerCode}"
				name="customerCode">
			<input type="hidden" value="${loanCode}" name="loanCode"> <input
				type="hidden" value="${newLoanCode}" name="newLoanCode"> <input
				type="hidden" value="${carLoanInfo.loanApplyTime }"
				name="loanApplyTime"></input>
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td colspan="3"><label class="lab">合同编号：</label>${contractNo}</td>
				</tr>
				<tr>
					<td><label class="lab">借款人姓名：</label>${carLoanInfo.loanCustomerName}<input type="hidden" value="${carLoanInfo.loanCustomerName}"
								name="loanCustomerName">
					</td>
					<td><label class="lab"><span style="color: red">*</span>申请额度（元）：</label> <input type="text" class="input_text178 required" maxlength="11" id="loanApplyAmount"
						value="<fmt:formatNumber value='${lastLastAmount}' pattern="##0.00"/>"
						name="loanApplyAmount"><input type="hidden" value="${carLoanInfo.loanApplyAmount}" id="lastLastAmount">
					</td>
					
					<td><label class="lab">申请借款期限(天数)：</label>
							30 <input type="hidden"
							value="30" name="loanMonths"></td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',carLoanInfo.dictProductType)}</td>
					<td>
					<c:choose>
						<c:when test="${carLoanInfo.dictProductType=='CJ01'}">
						<label class="lab"><span style="color: red">*</span>是否收取平台流量费：</label>
						<input type="radio" name="dictIsGatherFlowFee" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '1'}">
						checked </c:if> value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="dictIsGatherFlowFee" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '2'}">
						checked </c:if> value="2"/>否&nbsp;&nbsp;
						<input id="flowFee" name="flowFee" type="text" value="200.00" readonly="true" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '2'}">
						style="display:none;" </c:if>/>
						</c:when>
						<c:otherwise><label class="lab"><span style="color: red">*</span>停车费：</label>
						<input name="parkingFee" maxLength="10" class="input_text178 required" id="parkingAmount" value="<fmt:formatNumber value='${carLoanInfo.parkingFee}' pattern="##0.00"/>"/>
						</c:otherwise>
					</c:choose>
					 <input type="hidden" name="dictProductType" value="${carLoanInfo.dictProductType}"/>
					 <input type="hidden" name="dictGpsRemaining" value="${carLoanInfo.dictGpsRemaining}"/>
					 <!--  
					 <input type="hidden" name="facilityCharge" value="${carLoanInfo.facilityCharge}"/>
					 -->
					</td>
					<td><c:if test="${carLoanInfo.dictProductType=='CJ01' && carLoanInfo.deviceUsedFee!=null && carLoanInfo.deviceUsedFee!=0 && ((sourceLoanInfoVersion=='1.5' && contractVersion =='1.5')||(sourceLoanInfoVersion=='1.5' && contractVersion =='1.6')||(sourceLoanInfoVersion=='1.6' && contractVersion =='1.6'))}">
						<label class="lab">设备使用费：</label><span>100</span>
						<input type="hidden" name="deviceUsedFee" value="100"></input>
						</c:if>
					</td>
				</tr>
				<tr>
					<td><label class="lab"><span style="color: red">*</span>展期原因：</label>
						<select id="cardType" name="dictLoanUse"
						value="${carLoanInfo.dictLoanUse}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
								<option value="${item.value}"
									<c:if test="${carLoanInfo.dictLoanUse==item.value}"> 
						    selected=true 
						  </c:if>>${item.label}</option>
							</c:forEach>
					</select>
<label class="lab" ><span class="red">*</span>详细用途：<input type="text" class="input_text178 required" value="${carLoanInfo.loanUseDetail}" maxlength="50" name="loanUseDetail"></label>
					</td>
					<td><label class="lab">共借人：</label>
					<c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
						<input type="radio" onclick="return false" name="dictLoanCommonRepaymentFlag" class="required"
								value="${item.value}"
								<c:if test="${item.value==carLoanInfo.dictLoanCommonRepaymentFlag}">
                    					 checked='true'
                 				 </c:if> />${item.label}
                	</c:forEach>
				</td>
				<td><label class="lab"><span class="red">*</span>中间人：</label>
                 <select name="mortgagee" class="select180 required">
						<option value="">请选择</option>
							<c:forEach items="${middlePersons}" var="item">
								<option value="${item.id}"
									<c:if test="${ item.id == carLoanInfo.mortgagee}">selected</c:if>>${item.middleName}</option>
							</c:forEach>
					</select>
                </td>
				</tr>
				<tr>
					<td><label class="lab"><span style="color: red">*</span>团队经理：</label>
						<select id="termManagerId" name="consTeamManagercode" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${termManagers}" var="item">
								<option value="${item.id}"
								<c:if test="${carLoanInfo.consTeamManagercode == item.id}">selected</c:if>
								>${item.name}</option>
							</c:forEach>
						</select></td>
					<td><label class="lab"><span style="color: red">*</span>客户经理：</label>
						<select id="custManagerId" name="managerCode" class="select180 required">
							<option value="">请选择</option>
					</select><input id="customerManagerHidden" type="hidden" value="${carLoanInfo.managerCode }" /></td>
					<td><label class="lab">门店名称：</label>${storeName}</td>
					<input type="hidden" value="${storeName}" name="storeName">
					<input type="hidden" value="${storeCode}" name="storeCode">
				</tr>
				<tr>
					<td colspan="2"><label class="lab">管辖城市：</label>${cityName}</td>
					<td><label class="lab"><span style="color: red">*</span>客户人法查询结果：</label>
						<input type="text" class="input_text178 required"
						name="queryResult" maxlength="200" value="${carApplicationInterviewInfo.queryResult}"></td>
				</tr>

			</table>

			<div class="tright mb5 mr10">
				<input type="button" id="applyInfoNextBtn" class="btn btn-primary"
					value="下一步" />
			</div>
		</form>
	</div>
</body>
</html>