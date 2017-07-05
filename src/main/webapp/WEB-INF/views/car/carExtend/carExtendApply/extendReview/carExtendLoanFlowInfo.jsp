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
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carExtendTermCustomer.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#applyInfoSave").bind('click',function(){
		$("#carLoanFlowInfo").validate({
	        submitHandler:function(form){
	        	var a=$("#parkingFee1").val();
	    		if(a>1000){
	    			art.dialog.alert('停车费用不能大于1000');
	    			return false;
	    		}
	    		var loanCodeParam = $("#carLoanFlowInfo").serialize();
	    		$.ajax({
	    			   type: "POST",
	    			   url: ctx+"/car/carExtend/carExtendUpload/borrowSubmit",
	    			   data: loanCodeParam,
	    			   dataType:'text',
	    			   success: function(data){
	    				    $.jBox.tip(data);
	    			   }
	    			});
	        }    
	    });
		
	});
	$('input:radio[name="dictIsGatherFlowFee"]').change(function() {
		if($(this).val()=='1'){
			$("#flowFee").show();
		}else{
			$("#flowFee").hide();
		}
	}); 
});
	function backCarLoanHis(){
		  location.href="${ctx}/bpm/flowController/openForm?applyId=${param.applyId}&loanCode=${loanCode}&wobNum=${param.wobNum}&dealType=${param.dealType}&token=${param.token}";
	  }
</script>
<body>
 <ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarContract');">历史展期信息</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendInfo');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowCustomer');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendCompany');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendContact');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toExtendCoborrower');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowBank');">客户开户信息</a></li>
		<input type="button" style="float: right"
			onclick="backCarLoanHis()" class="btn btn-small"
			value="返回合同审核主页">
</ul>
<div id="div3" class="content control-group">
<c:set var="param" value="${workItem.bv}" />
<form id="frameFlowForm" method="post">
	<input type="hidden" name="loanCode" value="${loanCode }" />
	<input type="hidden" name="applyId" value="${param.applyId}" />
	<input type="hidden" name="wobNum" value="${param.wobNum}" />
	<input type="hidden" name="dealType" value="${param.dealType}" />
	<input type="hidden" name="token" value="${param.token}" />
</form>
<form id="carLoanFlowInfo" method="post">
      <input type="hidden" value="${loanCode}" name="loanCode">
                      <input type="hidden" id="dictProductType" value="${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',carLoanInfo.dictProductType)}" />
      <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td colspan="3"><label class="lab">合同编号：</label>${contractNo}</td>
		</tr>
		<tr>
		<td><label class="lab">借款人姓名：</label>${carLoanInfo.loanCustomerName}<input type="hidden" value="${carLoanInfo.loanCustomerName}"
								name="loanCustomerName">
					</td>
                <td ><label class="lab" >申请额度（元）：</label>
				<fmt:formatNumber value='${carLoanInfo.loanApplyAmount}' pattern="##0.00"/> 
				</td>
     		</td>
                      <td><label class="lab" >申请借款期限(天数)：
					${carLoanInfo.loanMonths}
			</td>
           </tr>
			<tr>
				<td ><label class="lab" >产品类型：</label>
						${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',carLoanInfo.dictProductType)}</td>
				<td colspan="2">
				<c:choose>
						<c:when test="${carLoanInfo.dictProductType=='CJ01'}">
						<label class="lab">是否收取平台流量费：</label>
						<input type="radio" name="dictIsGatherFlowFee" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '1'}">
						checked </c:if> value="1"/>是&nbsp;&nbsp;
						<input type="radio" name="dictIsGatherFlowFee" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '2'}">
						checked </c:if> value="2"/>否&nbsp;&nbsp;
						<input id="flowFee" name="flowFee" type="text" value="200.00" readonly="true" <c:if test="${carLoanInfo.dictIsGatherFlowFee eq '2'}">
						style="display:none;" </c:if>/>
						</c:when>
						<c:otherwise><label class="lab"><span class="red">*</span>停车费：</label>
						<input id="parkingFee1" name="parkingFee" maxLength="10" class="input_text178 required" value="<fmt:formatNumber value='${carLoanInfo.parkingFee}' pattern="##0.00"/>"/>
						</c:otherwise>
					</c:choose>
					<input type="hidden" name="dictProductType" value="${carLoanInfo.dictProductType}"/>
					 <input type="hidden" name="dictGpsRemaining" value="${carLoanInfo.dictGpsRemaining}"/>
					 <input type="hidden" name="facilityCharge" value="${carLoanInfo.facilityCharge}"/>
				</td>
			</tr>
		<tr class="hidProcess">
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
					<td><label class="lab">门店名称：</label>${carLoanInfo.storeName}</td>
				</tr>
				<tr>
					<td colspan="2"><label class="lab">管辖城市：</label>${cityName}</td>
					<td><label class="lab"><span style="color: red">*</span>客户人法查询结果：</label>
						<input type="text" class="input_text178 required"
						name="queryResult" maxlength="200" value="${carApplicationInterviewInfo.queryResult}"></td>
				</tr>
        </table>
	
	<div class="tright mb5 mr10" >
		<input type="submit" id="applyInfoSave" class="btn btn-primary" value="保存" />
	</div>
	</form>
</div>
</body>
</html>