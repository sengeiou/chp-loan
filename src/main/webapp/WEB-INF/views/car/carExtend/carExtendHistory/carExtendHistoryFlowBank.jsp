<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!--管辖及开户信息-->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	
	$('#isRareword').bind('change', function() {
		if ($(this).prop('checked') == true || $(this).prop('checked') == 'checked') {
			$('#bankAccountName').removeAttr('readonly');
			$('#isRareword').val('1');
		} else {
			$("#bankAccountName").attr("value", $("#hiddenBankAccountName").val());
			$('#bankAccountName').attr("readonly","readonly");
			$('#isRareword').val('0');
		}
	});
	$('#isRareword').trigger("change");

	loan.initCity("bankProvince", "bankCity",null);
	$('#bankSigningPlatform').bind('change',function(){
		$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
	});
});
$(document).ready(function() {
	areaselect.initCity("bankProvince", "bankCity", null, $("#bankCityHidden").val(), null);
	//验证JS
	$("#nextSubmitBtn").bind("click",function(){
		var flag=$("#carBank").validate({
			onclick: true, 
			rules : {
				'bankCardNo' : {
					equalTo:"#firstInputBankAccount",
					digits:true,
					min:0,
					maxlength:19,
					minlength:16
				}
			},
			messages : {
				'bankCardNo':{
					equalTo: "输入银行卡号不一致",
					digits:  "银行卡号必须为数字类型",
					min:"输入的数据不合法",
					maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
					minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
				}
			}
		}).form();
		if(flag==true){
			$("#carBank").submit();
			$("#nextSubmitBtn").prop('disabled',true);
		}
	})	
 });
</script>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/queryHistoryExtend?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendInfo?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowCustomer?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendCompany?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendContact?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toExtendCoborrower?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li class="active"><a>客户开户信息</a></li>
	</ul>
	<jsp:include page="frameFlowFormExtend.jsp"></jsp:include>
<div id="div9" class="content control-group">
	  <form id="carBank" action="${ctx}/car/carExtendHistory/carLoanFlowBank" method="post">
       <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
       	<input type="hidden" value="${loanCode}" name="loanCode">
       	<input type="hidden" value="${newLoanCode}" name="newLoanCode">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td><label class="lab">授权人：</label>${carCustomer.customerName}</td>
			</tr>
			<tr>
				<td>
 				<label class="lab"><span class="red">*</span>银行卡户名：</label>
				<input type="text" name="bankAccountName" id="bankAccountName" value="${carCustomerBankInfo.bankAccountName == null ? carCustomer.customerName : carCustomerBankInfo.bankAccountName}" maxlength="20" class="input_text178 required stringCheck"/>
				<input type="hidden" id="hiddenBankAccountName" value="${carCustomer.customerName}"/>
				<input type="checkbox" id="isRareword" name="israre" value="${carCustomerBankInfo.israre}" 
				 <c:if test="${carCustomerBankInfo.israre == '1'}"> checked='checked' </c:if>
				 />是否生僻字</td>
                <td><label class="lab"><span class="red">*</span>开卡省市：</label>
                <select class="select78 required" id="bankProvince" name="bankProvince" value="${carCustomerBankInfo.bankProvince}">
                      <option value="">请选择</option>
                      <c:forEach var="item" items="${provinceList}" varStatus="status">
		               <option value="${item.areaCode}" 
		                <c:if test="${carCustomerBankInfo.bankProvince==item.areaCode}">
		                   selected=true 
		                </c:if>
		               >${item.areaName}</option>
	                  </c:forEach>
                    </select>-<select class="select78 required" id="bankCity" name="bankCity" value="${carCustomerBankInfo.bankCity}">
                       <option value="">请选择</option>
                    </select>
                    <input type="hidden" id="bankCityHidden" value="${carCustomerBankInfo.bankCity}">
                </td>
                
                <td><label class="lab"><span class="red">*</span>开户行：</label>
			 <select name="cardBank"  class="select180 required">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${carCustomerBankInfo.cardBankCode==curItem.value}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach>
				</select>
                </td>
                
            </tr>
			<tr>
                 <td><label class="lab"><span class="red">*</span>开户支行：</label>
                <input type="text" name="applyBankName" value="${carCustomerBankInfo.applyBankName}"  maxlength="20"  class="input_text178 required stringCheck"/></td>
                
                <td><label class="lab"><span class="red">*</span>银行卡号：</label>
                 <input type="text" id="firstInputBankAccount" value="${carCustomerBankInfo.bankCardNo}" class="input_text178 required"/></td>
                 <td><label class="lab"><span class="red">*</span>确认银行卡号：</label>
                <input type="text" id="confirmBankAccount" value="${carCustomerBankInfo.bankCardNo}" name="bankCardNo" class="input_text178 required"></td>
               
            </tr>
        </table>
        
         <!--流程参数-->
        <div class="tright mr10 mb5" >
            <input type="button" id="nextSubmitBtn" class="btn btn-primary" value="提交"></input>
        </div>
         </form>
    </div>
</body>
</html>