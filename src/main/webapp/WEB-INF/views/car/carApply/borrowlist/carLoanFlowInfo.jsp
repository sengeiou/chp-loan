<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<!-- 借款信息 -->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">

		/***
		* 根据产品类型获取 审批期限
		**/
		$(document).ready(function() {
			var contractVersion = '${contractVersion}';

			$("select[name='dictIsGatherFlowFee']").change(function() { //平台及流量费
				var flowFee = $(this).val();
				if (flowFee == '2') {	
					$("#flowFeeTDId").css("display", "none");
				} else {
					$("#flowFeeTDId").css("display", "inline");
				}
			});
			$("select[name='dictIsGatherFlowFee']").trigger("change");//平台及流量费
		$("#dictSettleRelend").change(function(){  //结清再借
			var dictProductType = $("#dictProductType option:selected").val();
			var dictSettleRelend = $("#dictSettleRelend option:selected").val();
			var dictGpsRemaining = $("#dictGpsRemaining option:selected").val();
			var addContractVersion = '${addContractVersion}'; //结清再借时，判断最近的新增借款是哪个合同版本,1.4版本设备使用费为0
			if (dictProductType == "CJ01" && dictSettleRelend == "1") {//gps结清再借
				$("#flowFeeId").css("display", "inline"); //gps是否拆除显示
				if(dictGpsRemaining=="2"){//gps未拆除
					//$("#facilityCharge").show();
					if(addContractVersion=='1.4'){
						$("#deviceUsedFee").val("0"); //设置设备使用费为0
					}
					$("#facility").val("0"); //设备费
					$("#GatherflowFeeId").show(); //平台流量费显示 
				}else if(contractVersion=="1.5" || contractVersion=="1.6"){
					//$("#facilityCharge").show();
					$("#facility").val("600");
					$("#GatherflowFeeId").hide();
				}else{
					$("#facility").val("1300");
					$("#GatherflowFeeId").hide();
				}
			} else if(contractVersion=="1.5" || contractVersion=="1.6"){
				$("#flowFeeId").css("display", "none");
				$("#facility").val("600");//设备费
				$("#GatherflowFeeId").hide();//平台流量费
			}else{
				$("#flowFeeId").css("display", "none");
				$("#facility").val("1300");//设备费
				$("#GatherflowFeeId").hide();//平台流量费
			}
		});
		$("#dictGpsRemaining").change(function() { //gps是否拆除
			var dictProductType = $("#dictProductType option:selected").val();
			var dictSettleRelend = $("#dictSettleRelend option:selected").val();
			var dictGpsRemaining = $("#dictGpsRemaining option:selected").val();
			var addContractVersion = '${addContractVersion}';
			if (dictProductType == "CJ01" && dictSettleRelend == "1") {//gps结清再借
				$("#flowFeeId").css("display", "inline");//gps是否拆除显示
				if(dictGpsRemaining=="2"){//gps未拆除
					if(addContractVersion=='1.4'){
						$("#deviceUsedFee").val("0"); //设置设备使用费为0
					}
					$("#facility").val("0");//设备费
					$("#GatherflowFeeId").show();//平台流量费显示 
				}else if(contractVersion=="1.5" || contractVersion=="1.6"){
					var month = $("#monthId option:selected").val();
					var deviceFee = 100*(month/30);
					$("#deviceUsedFee").val(deviceFee);
					//--------------------------------
					$("#facilityCharge").show();
					$("#facility").removeAttr("disabled");
					$("#deviceUsedFee").removeAttr("disabled");
					$("#facility").val("600");
					$("#GatherflowFeeId").hide();//平台流量费
				}else{
					$("#facilityCharge").show();
					//$("#deviceUsedFee").hide(); //1.4合同隐藏设备使用费
					$("#facilityCharge").children().eq(1).hide();
					$("#deviceUsedFee").attr("disabled","disabled");
					$("#facility").removeAttr("disabled");
					$("#facility").val("1300");
					$("#GatherflowFeeId").hide();//平台流量费
				}
			}else {
				$("#flowFeeId").css("display", "none");//gps是否拆除
				$("#GatherflowFeeId").hide();
			}
		});
		$("#dictProductType").change(function() {	
			var dictProductType = $("#dictProductType option:selected").val();
			var dictSettleRelend = $("#dictSettleRelend option:selected").val();
			var dictGpsRemaining = $("#dictGpsRemaining option:selected").val();
			if (dictProductType == "CJ01" && dictSettleRelend == "1") { //gps结清再借
				$("#flowFeeId").css("display", "inline");  //gps是否拆除显示
				if(dictGpsRemaining=="2"){ //gps未拆除
					//$("#facilityCharge").hide();
					$("#facility").val("0");  //设备费
					$("#GatherflowFeeId").show(); //平台流量费显示
				}else if(contractVersion=="1.5" || contractVersion=="1.6"){
					//$("#facilityCharge").show();
					$("#facility").val("600");
					$("#GatherflowFeeId").hide(); //平台流量费隐藏
				}else{
					$("#facility").val("1300");
					$("#GatherflowFeeId").hide(); //平台流量费隐藏
				}
			} else { //再借为否的情况
				$("#flowFeeId").css("display", "none");
				$("#GatherflowFeeId").hide();
			}
			if(dictProductType == "CJ01"){
				$("#parkingFee").hide();  //停车费
				$("input[name='commericialCompany'], input[name='commericialNum'], input[name='commercialMaturityDate']").addClass("required");
				$("#commericialShowOrHide").show(); //商业保险显示
			} else {
				$("#parkingFee").show();
				$("input[name='commericialCompany'], input[name='commericialNum'], input[name='commercialMaturityDate']").removeClass("required");
				$("#commericialShowOrHide").hide();
			}
			if(dictProductType != "CJ01"){ //不为gps时 
				$("#facilityCharge").hide();  //设备费 隐藏
				$("#facility").attr("disabled","disabled");
				$("#deviceUsedFee").attr("disabled","disabled");
			} else if(contractVersion=="1.5" || contractVersion=="1.6"){
				$("#facilityCharge").show();
				$("#facility").removeAttr("disabled");
				$("#deviceUsedFee").removeAttr("disabled");
			}else{//1.4合同隐藏设备使用费
				$("#facilityCharge").show();
				$("#facility").val("1300");
				$("#facility").removeAttr("disabled");
				$("#facilityCharge").children().eq(1).hide();
				//$("#deviceUsedFee").hide();
				$("#deviceUsedFee").attr("disabled","disabled");
			}
			var auditBorrowProductNameId = $("#auditBorrowProductNameId").val($("#dictProductType option:selected").html());
			if (dictProductType == "") {
				$("#monthId").empty();  //借款期限(天数)
				$("#monthId").append("<option value='' selected=true>请选择</option>");
				$("#monthId").trigger("change");
			} else {
				$.ajax({
				type : "POST",
				url : ctx + "/common/productInfo/asynLoadProductMonths",
				data : {
					productCode : dictProductType,
					productType:"products_type_car_credit"
				},
				success : function(data) {
						 var resultObj = data.split(",");
						$("#monthId").empty();
						$("#monthId").append("<option value=''>请选择</option>");
						var month = $("#auditMonthId").val(); //借款期数（天数）
						$.each(resultObj, function(i, item) {
							if (month == item) {
								$("#monthId").append("<option selected=true value=" + item + ">" + item + "</option>");
							} else {
								$("#monthId").append("<option value=" + item + ">" + item + "</option>");
							}
						});
						$("#monthId").trigger("change"); 
					}
				});
			}
		});
		$("#monthId").change(function() { //借款期限(天数)
			var dictProductType = $("#dictProductType option:selected").val();
			var dictSettleRelend = $("#dictSettleRelend option:selected").val();
			var dictGpsRemaining = $("#dictGpsRemaining option:selected").val();
			
			$("#firstServiceTariffingRate").empty(); //首期服务费率
			$("#firstServiceTariffingRate").append("<option value='' selected=true>请选择</option>");
			$("#firstServiceTariffingRate").trigger("change");
			var month = $("#monthId option:selected").val();
			$("#firstServiceTariffingRate").attr("disabled",false); 
			var addContractVersion = '${addContractVersion}'; //最近一次新增的借款版本
			var firstRate = $("#firstRate").val();//保存的首期服务费率
			firstRate = firstRate.substr(0,1);
			//获取首期服务费率列表
			var dictProductType = $("#dictProductType option:selected").val();
			var firstServiceCharge = '${firstServiceList}';
			var chargeArray = JSON.parse(firstServiceCharge);
			//修改设备使用费
			if(dictSettleRelend == "1" && dictGpsRemaining=="2" && addContractVersion=="1.4"){
				$("#deviceUsedFee").val("0");
			}else if(contractVersion=="1.5" || contractVersion=="1.6"){
				var deviceFee = 100*(month/30);
				$("#deviceUsedFee").val(deviceFee);
			}
			//firstServiceChargeId
			if(month==30||month==90){
				$("#firstServiceTariffingRate").empty();
				$("#firstServiceTariffingRate").append("<option value=''>请选择</option>");
				//$("#firstServiceTariffingRate").append("<option value='0.00000'>0%</option>");
				//$("#firstServiceTariffingRate").append("<option value='2.00000'>2%</option>");
				$.each(chargeArray,function(i,item){
					if(firstRate == item.ninetyBelowRate){
						$("#firstServiceTariffingRate").append("<option sid="+item.id+" selected=true value='"+item.ninetyBelowRate+".00000'>"+item.ninetyBelowRate+"%</option>");
						$("#firstServiceChargeId").val(item.id);
					}else{
						$("#firstServiceTariffingRate").append("<option sid="+item.id+" value='"+item.ninetyBelowRate+".00000'>"+item.ninetyBelowRate+"%</option>");
					}
				});
			}else if(month==180||month==270||month==360){
				$("#firstServiceTariffingRate").empty();
				$("#firstServiceTariffingRate").append("<option value=''>请选择</option>");
				//$("#firstServiceTariffingRate").append("<option value='2.00000'>2%</option>");
				//$("#firstServiceTariffingRate").append("<option value='4.00000'>4%</option>");
				$.each(chargeArray,function(i,item){
					if(firstRate == item.ninetyAboveRate){
						$("#firstServiceTariffingRate").append("<option sid="+item.id+" selected=true value='"+item.ninetyAboveRate+".00000'>"+item.ninetyAboveRate+"%</option>");
						$("#firstServiceChargeId").val(item.id);
					}else{
						$("#firstServiceTariffingRate").append("<option sid="+item.id+" value='"+item.ninetyAboveRate+".00000'>"+item.ninetyAboveRate+"%</option>");
					}
				});
			}else{
				$("#firstServiceTariffingRate").empty();
				$("#firstServiceTariffingRate").append("<option value='' selected=true>请选择</option>");
				$("#firstServiceTariffingRate").attr("disabled",true); 
			}
			$("#firstServiceTariffingRate").trigger("change");
		});
		$("#dictProductType").trigger("change");
		
		$("#firstServiceTariffingRate").change(function(){
			var sid = $("#firstServiceTariffingRate").find("option:selected").attr("sid");
			$("#firstServiceChargeId").val(sid);
		});
		//验证JS
		$("#applyInfoNextBtn").bind('click', function() {
			//验证JS
			$("#carLoanFlowInfo").validate({
				onclick : true,
				rules : {
					loanApplyAmount : {
						max : 999999999
					},
					parkingFee : {
						max : 1000
					}

				},
			});
			var a=$("#parkingFee1").val();
			if(a>1000){
				art.dialog.alert('停车费用不能大于1000');
				return false;
			}
			var firstServiceTariffingRate = $("#firstServiceTariffingRate").val();
			firstServiceTariffingRate = firstServiceTariffingRate.substr(0,1);
			$("#firstRate").val(firstServiceTariffingRate);
			$("#dictProductType").removeAttr("disabled");
			$("#firstServiceTariffingRate").removeAttr("disabled");
			$("#carLoanFlowInfo").submit();
		});
		// 客户放弃		
		$("#giveUpBtn").bind('click',function(){
			
			var url = ctx+"/car/carApplyTask/giveUp?loanCode="+ $("#loanCode").val();						
			
			art.dialog.confirm("是否客户放弃?",function(){
				$("#frameFlowForm").attr('action',url);
				$("#frameFlowForm").submit();
				});
			});
		
		$('input:radio[name="dictLoanCommonRepaymentFlag"]').change(function() {
			if($(this).val()=='0'){
				$("#coborrowerA").attr("href","#");
			}else{
				
			}
			
		}); 
	});
	</script>
<!-- <script type="text/javascript">
$(document).ready(function() {
			loan.initProduct("productId", "monthId");
			loan.selectedProduct("productId", "monthId");
			areaselect.initCity("storeProvice", "storeCity",
					  null, $("#storeCityHidden").val(), null);
		});
</script> -->
<body>
 <ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${loanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${loanCode}');">车辆信息</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${loanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${loanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${loanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${loanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${loanCode}');">客户开户及管辖信息</a></li>
		<input type="button"  onclick="showCarLoanHis('${loanCode}')" class="btn btn-small r mr10" value="历史">
		<input type="button"  id="giveUpBtn" class="btn btn-small r mr10"  value="客户放弃"></input></div>
	</ul>

	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
<div id="div3" class="content control-group">
<form id="carLoanFlowInfo" method="post" action="${ctx}/car/carApplyTask/carLoanFlowInfo">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
	  <input type="hidden" value="${carLoanInfo.customerCode}" name="customerCode">
	  <input type="hidden" value="${carLoanInfo.id}" name="id">
	  <input type="hidden" value="${carLoanInfo.applyId}" name="applyId">
      <input type="hidden" value="${carLoanInfo.loanCustomerName}" name="loanCustomerName">
      <input type="hidden" value="${carLoanInfo.loanCustomerService}" name="loanCustomerService">
      <input type="hidden" value="${carLoanInfo.managerCode}" name="managerCode">
      <input type="hidden" value="${carLoanInfo.consTeamManagercode}" name="consTeamManagercode">
      <input type="hidden" id="loanCode" value="${loanCode}" name="loanCode">
      <input type="hidden" id="auditMonthId" value="${carLoanInfo.loanMonths}">
      <input type="hidden" id="firstServiceChargeId" name="firstServiceChargeId"></input>
      <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
                      <td ><label class="lab" ><span class="red">*</span>申请额度（元）：</label>
				<input type="text" name="loanApplyAmount"
				value="<fmt:formatNumber value='${carLoanInfo.loanApplyAmount}' pattern="##0.00"/>"  maxlength="13"   class="input_text178 required number"/></td>
                      
                      <td ><label class="lab" ><span class="red">*</span>产品类型：</label>
				<select name="dictProductType" id="dictProductType" class="select180 required" <c:if test="${productTypeEditable == '0' }">disabled="disabled"</c:if> >
						    <option value="">请选择</option>
							 <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="pro">
							    <option value="${pro.productCode}"
							    <c:if test="${carLoanInfo.dictProductType==pro.productCode}">
							     selected = true
							   </c:if>>${pro.productName}</option>
							 </c:forEach>
						</select>
     		</td>
                      <td><label class="lab" ><span class="red">*</span>申请借款期限(天数)：</label><select
					id="monthId" name="loanMonths" class="select180 required">
				</select>
			</td>
                  </tr>
           <tr>
                <td><label class="lab" ><span class="red">*</span>申请日期：</label> <input class="input_text178 required"
				id="d4311" name="loanApplyTime" value="<fmt:formatDate value='${carLoanInfo.loanApplyTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate"
				size="10" onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
				style="cursor: pointer" /></td>
                
                
                
                <td><label class="lab"><span class="red">*</span>共同借款人：</label><c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="dictLoanCommonRepaymentFlag" class="required"
								value="${item.value}"
								<c:if test="${item.value==carLoanInfo.dictLoanCommonRepaymentFlag}">
                     checked='true'
                  </c:if> />${item.label}
                </c:forEach>
                <span id="dictLoanCommonRepaymentFlagSpan"></span>
                </td>
                
                
                <td><label class="lab"><span class="red">*</span>中间人：</label>
                 <select name="mortgagee"
						class="select180 required">
						<option value="">请选择</option>
							<c:forEach items="${middlePersons}" var="item">
								<option value="${item.id}"
									<c:if test="${ item.id == carLoanInfo.mortgagee}">selected</c:if>>${item.middleName}</option>
							</c:forEach>
					</select>
                </td>
            </tr>
			<tr>
                <td><label class="lab"><span class="red">*</span>借款人姓名：</label>
                ${carLoanInfo.loanCustomerName}
                <input type="hidden" class="input_text178 required" value="${carLoanInfo.loanCustomerName}" name="loanAuthorizer"></td>
                <td></td>
                <td><label class="lab"><span class="red">*</span>首期服务费率：</label>
                	<select disabled="disabled"
					id="firstServiceTariffingRate" class="select180 required"></select>
					<input type="hidden" value="${carLoanInfo.firstServiceTariffingRate}" id="firstRate" name="firstServiceTariffingRate"/>
                </td>
            </tr>
            <tr id="commericialShowOrHide">
                <td><label class="lab"><span class="red">*</span>商业险保险公司：</label><input type="text" class="input_text178 required" name="commericialCompany" maxlength="50" value="${carVehicleInfo.commericialCompany}"></td>
                <td><label class="lab"><span class="red">*</span>商业险单号：</label><input type="text" class="input_text178 required" name="commericialNum" maxlength="50" value="${carVehicleInfo.commericialNum}"></td>
                <td><label class="lab"><span class="red">*</span>商业险到期日：</label><input id="commercialMaturityDate" name="commercialMaturityDate" type="text" class="input_text178 Wdate required" size="10"  
				      onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer"  value="<fmt:formatDate value='${carVehicleInfo.commercialMaturityDate}' pattern="yyyy-MM-dd"/>"/>
				   </td>
            </tr>
			<tr id="parkingFee">
                <td><label class="lab"><span class="red">*</span>停车费：</label><input type="text" class="input_text178 number required" name="parkingFee" maxlength="10" value="<fmt:formatNumber value="${carLoanInfo.parkingFee}" pattern="0.00"/>">元</td>
				<td><span style="color:red;">（备注：此处只填写一个月的停车费）</td>
            </tr>
            <tr id="facilityCharge">
                <td><label class="lab"><span class="red">*</span>设备费：</label><input type="text" class="input_text178 number required" value="600" name="facilityCharge" id="facility" readonly="true">元</td>
                <td><label class="lab"><span class="red">*</span>设备使用费：</label><input type="text" class="input_text178 number required" name="deviceUsedFee" id="deviceUsedFee" readonly="true">元</td>
            </tr>
			<tr>
                <td colspan="3"><label class="lab"><span class="red">*</span>结清再借：</label>
                <select name="dictSettleRelend" id="dictSettleRelend" class="select180 required">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_circle_loan_flag')}" var="yesNo">
						<option value="${yesNo.value}" <c:if test="${carLoanInfo.dictSettleRelend==yesNo.value}">
						selected=true
						</c:if>>${yesNo.label}</option>
					 </c:forEach>
			     </select>
				<span id="flowFeeId"><select id="dictGpsRemaining" class="select180 required"
							name="dictGpsRemaining"><option value="2" <c:if test="${carLoanInfo.dictGpsRemaining eq '2'}">
						selected=true </c:if>>GPS未拆除</option>
								<option value="1" <c:if test="${carLoanInfo.dictGpsRemaining eq '1'}">
						selected=true </c:if>>GPS已拆除</option></select></span><span id="GatherflowFeeId"> <select id="dictIsGatherFlowFee"
							class="select180 required" name="dictIsGatherFlowFee"><option <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '2'}">
						selected=true </c:if>
									value="2">不收取平台流量费</option>
								<option <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '1'}">
						selected=true </c:if> value="1">收取平台流量费</option></select> <span id="flowFeeTDId"><label
								class="lab">平台及流量费：</label><input type="text" readonly="true"
								class="input_text178" name="flowFee" value="200">元</span> </span></td>
            </tr>
			<tr>
				 <td><label class="lab" ><span class="red">*</span>借款用途：</label> <select id="cardType"
				name="dictLoanUse" value="${carLoanInfo.dictLoanUse}"
				class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_loan_use')}" var="item">
						<option value="${item.value}" 
						<c:if test="${carLoanInfo.dictLoanUse==item.value}"> 
						    selected=true 
						  </c:if> 
						>${item.label}</option>
					</c:forEach>
			</select>
			</td>
			<td>
			<label class="lab" ><span class="red">*</span>详细用途：<input type="text" class="input_text178 required" value="${carLoanInfo.loanUseDetail}" maxlength="50" name="loanUseDetail"></label>
			</td>
			<td></td>
			</tr> 
        </table>
		<%-- <tr>
			<td colspan="2"><label class="lab" >管辖城市：</label> 
			 <select class="select78 mr34" disabled="disabled" id="storeProvice">
				  <option value="">请选择</option>
				  <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode }"
		             <c:if test="${applyInfoEx.loanInfo.storeProviceCode==item.areaCode}">
		               selected=true 
		             </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
			  </select>
			  <select class="select78" disabled="disabled" id="storeCity"><option value="">请选择</option>
			  </select>
			  <input type="hidden" value="${applyInfoEx.loanInfo.storeCityCode}" id="storeCityHidden"/>
		   </td> 
		   <td>
               <input type="hidden" name="loanInfo.loanStoreOrgId" value="${applyInfoEx.loanInfo.loanStoreOrgId}"/>
            </td>
		</tr>--%>
	<div class="tright mb5 mr10" >
		<input type="submit" id="applyInfoNextBtn" class="btn btn-primary" value="下一步" />
	</div>
	</form>
</div>
</body>
</html>