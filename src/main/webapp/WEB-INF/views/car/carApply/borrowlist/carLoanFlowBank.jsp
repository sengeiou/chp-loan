<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!--银行信息-->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/checkAllTab.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
$(function(){
	var text = $("#textarea").val();
	var counter = text.length;
	$(".textareP").find("var").text(50-counter);
	
	$("#textarea").keyup(function() {
		var text = $("#textarea").val();
		var counter = text.length;
		$(".textareP").find("var").text(50-counter);
	});
});
$(document).ready(function(){

 loan.initCity("bankProvince", "bankCity",null);
 $('#bankSigningPlatform').bind('change',function(){
	$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
 });
});
$(document).ready(
		function() {
			areaselect.initCity("bankProvince", "bankCity",
					  null, $("#bankCityHidden")
							.val(), null);
			// 客户放弃		
			$("#giveUpBtn").bind('click',function(){
				
				var url = ctx+"/car/carApplyTask/giveUp?loanCode="+ $("#loanCode").val();					
				
				art.dialog.confirm("是否客户放弃?",function(){
					$("#frameFlowForm").attr('action',url);
					$("#frameFlowForm").submit();
					});
			});
		
			$('#isRareword').bind('change',
					function() {
						if ($(this).prop('checked') == true
								|| $(this).prop('checked') == 'checked') {
							//$('#bankAccountName').val("");
							$('#bankAccountName').removeAttr("readonly");
						} else {
							$("#bankAccountName").val($("#hiddenBankAccountName").val());
							$('#bankAccountName').attr("readonly","readonly");
						}
					});
			$('#isRareword').trigger("change");
			if("${carCustomerBankInfo.bankAccountName}"==""){
				$("#bankAccountName").val($("#hiddenBankAccountName").val());
			}
			
			function commonBank(){
				return $("#carBank").validate({
					onclick: true,
					rules : {
						bankCardNo : {
							equalTo : "#firstInputBankAccount",
							digits : true,
							min:0,
							maxlength:19,
							minlength:16
						}
					},
					messages : {
						bankCardNo:{
							equalTo: "输入银行卡号不一致",
							digits:  "银行卡号必须为数字类型",
							min:"输入的数据不合法",
							maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
							minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
						}
					}
				}).form();
			};
			
			$('#firstInputBankAccount').blur(function () {
				commonBank();
			});
			$('#confirmBankAccount').blur(function () {
				commonBank();
			});
 });
</script>
<body>
 <ul class="nav nav-tabs">
  		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${loanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${loanCode}');">车辆信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${loanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${loanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${loanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${loanCode}');">联系人资料</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${loanCode}');">客户开户及管辖信息</a></li>
		<input type="button"  class="btn btn-small r mr10" onclick="showCarLoanHis('${loanCode}')" class="btn btn-small" value="历史">
		<input type="button"  class="btn btn-small r mr10" id="giveUpBtn" class="btn btn-small"  value="客户放弃"></input></div>
	</ul>
<jsp:include page="_frameFlowForm.jsp"></jsp:include>
<div id="div9" class="content control-group">
	  <form id="carBank" method="post" action="${ctx}/car/carApplyTask/carLoanFlowBank" onSubmit="disabledSubmitBtn('checkAllTab')">
       <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
       	<input type="hidden" id="loanCode" value="${loanCode}" name="loanCode">
        <input type="hidden" value="${carCustomerBankInfo.id}" name="id">
       	<input type="hidden" value="3" name="dictOperStatus">
       	<input type="hidden" value="4" name="dictLoanStatus">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
				<td>
				<label class="lab"></span>授权人：</label>
				${carLoanInfo.loanCustomerName}
		</tr><tr><td>
 				<label class="lab"><span class="red">*</span>银行卡户名：</label>
 				<input type="hidden" value="${carLoanInfo.loanCustomerName}" id="hiddenBankAccountName">
				<input type="text" id="bankAccountName" name="bankAccountName" maxlength="20" value="${carCustomerBankInfo.bankAccountName}"  <c:if test="${carCustomerBankInfo.israre ne '1'}">readonly</c:if>  class="input_text178 required stringCheck"/>
				 <input type="checkbox" id="isRareword" name= "israre" <c:if test="${carCustomerBankInfo.israre eq '1'}">checked</c:if> />是否生僻字</td>

                <td><label class="lab"><span class="red">*</span>开卡省市：</label>
                <select class="select78 required" id="bankProvince" name="bankProvince" >
                      <option value="">请选择</option>
                      <c:forEach var="item" items="${provinceList}" varStatus="status">
		               <option value="${item.areaCode}" 
		                <c:if test="${carCustomerBankInfo.bankProvince==item.areaCode}">
		                   selected=true 
		                </c:if>
		               >${item.areaName}</option>
	                  </c:forEach>
                    </select>-<select class="select78 required" id="bankCity" name="bankCity" >
                       <option value="">请选择</option>
                	   <c:forEach var="item" items="${cityList}" >
		               <option value="${item.areaCode}" 
		                <c:if test="${carCustomerBankInfo.bankCity==item.areaCode}">
		                   selected=true 
		                </c:if>
		               >${item.areaName}</option>
	                  </c:forEach>
                    </select>
                    <input id="bankCityHidden" type="hidden" value="${carCustomerBankInfo.bankCity}">
                </td>
                
                <td><label class="lab"><span class="red">*</span>开户行：</label>
			 <select name="cardBank"  class="select180 required">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_open_bank')}" var="item">
					  <option value="${item.value}"
					  <c:if test="${carCustomerBankInfo.cardBankCode==item.value}">
                             selected=true 
                         </c:if>>${item.label}</option>
				    </c:forEach>
				</select>
				
				
                </td>
                
            </tr>
			<tr>
                 <td><label class="lab"><span class="red">*</span>开户支行：</label>
                <input type="text" name="applyBankName"  maxlength="20" value="${carCustomerBankInfo.applyBankName}" class="input_text178 required stringCheck"/></td>
                
                <td><label class="lab"><span class="red">*</span>银行卡号：</label>
                 <input type="text" name="firstInputBankAccount" id="firstInputBankAccount" value="${carCustomerBankInfo.bankCardNo}" class="input_text178 required"/></td>
                 <td><label class="lab"><span class="red">*</span>确认银行卡号：</label>
                <input type="text" id="confirmBankAccount" value="${carCustomerBankInfo.bankCardNo}" name="bankCardNo" class="input_text178 required"></td>
               
            </tr>
			
            <tr>
                <td><label class="lab">客户经理：</label>${managerName}</td>
                <td><label class="lab">团队经理：</label>${teamManagerName}</td>
                <td><label class="lab">面审：</label>${loanInfo.reviewMeet}
                <input type="hidden" name="reviewMeet" value="${loanInfo.reviewMeet}">
                </td>
               
            </tr>
            <tr>
                <td><label class="lab">门店名称：</label>${carLoanInfo.storeName}</td>
                
                 <td colspan="2"><label class="lab">管辖城市：</label>${cityName}</td>

            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>客户来源：</label>
                <select id="dictCustomerSource2" name="dictCustomerSource2" value="${carCustomer.dictCustomerSource2}" class="select180 required">
				<option value="">请选择</option>
				<c:forEach items="${fns:getDictList('jk_cm_src')}" var="item">
					  <option value="${item.value}"
					  <c:if test="${carCustomer.dictCustomerSource2==item.value}">
                             selected=true 
                         </c:if>>${item.label}</option>
				     </c:forEach>
				</select></td>
                <td><label class="lab"><span class="red">*</span>客户人法查询结果：</label><input type="text"  maxlength="50" class="input_text178 required" name="queryResult" id="textarea"  value="${carApplicationInterviewInfo.queryResult}">
               		<span class="textareP">剩余<var style="color:#C00">--</var>字符</span>
                </td>
                
            </tr>
        </table>
        
         <!--流程参数-->
        <div class="tright mr10 mb5" >
            <input type="button" id="checkAllTab" class="btn btn-primary" value="提交"></input>
        </div>
         </form>
    </div>
</body>
</html>