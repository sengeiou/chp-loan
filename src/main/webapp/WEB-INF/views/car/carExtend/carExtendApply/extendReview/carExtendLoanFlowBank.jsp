<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!--管辖及开户信息-->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
//提交表单
$(document).ready(function() {
		 loan.initCity("bankProvince", "bankCity",null);
		 $('#bankSigningPlatform').bind('change',function(){
			$('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
		 });
			areaselect.initCity("bankProvince", "bankCity",
					  null, $("#bankCityHidden")
							.val(), null);
			//验证JS
			$("#carBank").validate({
				onclick: true, 
				rules : {
					'bankCardNo' : {
						equalTo:"#firstInputBankAccount",
						digits:true,
						min:0,
						maxlength:19,
						minlength:16
					},
					'firstInputBankAccount':{
						min:0,
						maxlength:19,
						minlength:16
					},
					'applyBankName' :{
						 stringCheck : true 
					}
				},
				messages : {
					'bankCardNo':{
						equalTo: "输入银行卡号不一致",
						digits:  "银行卡号必须为数字类型",
						min:"输入的数据不合法",
						maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
						minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
					},
					'firstInputBankAccount':{
						min:"输入的数据不合法",
						maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
						minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
					}
				},submitHandler:function(form){
					var param = $("#carBank").serialize();
					$.ajax({
			    		url:ctx+"/car/carExtend/carExtendUpload/bankSave",
			    		type:'post',
			    		data: param,
			    		dataType:'text',
			    		success:function(data){
			    			$.jBox.tip(data);
			    		},
			    		error:function(){
			    			art.dialog.alert('服务器异常！');
			    			return false;
			    		}
			    	});
			    }
			});
			$('#isRareword').bind('click',function() {
						if ($(this).prop('checked') == true
								|| $(this).prop('checked') == 'checked') {
							$('#bankAccountName').removeAttr("readonly");
							$("#bankAccountName").val("");
						} else {
							$('#bankAccountName').attr("readonly","readonly");
							$("#bankAccountName").val($("#loanCustomerName").val());
						}
					});
 });
function backCarLoanHis(){
	windowLocationHref("${ctx}/bpm/flowController/openForm?applyId=${param.applyId}&loanCode=${loanCode}&wobNum=${param.wobNum}&dealType=${param.dealType}&token=${param.token}");
}
</script>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarContract');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendInfo');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowCustomer');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendCompany');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendContact');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toExtendCoborrower');">共同借款人</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowBank');">客户开户信息</a></li>
		<input type="button" style="float: right"
			onclick="backCarLoanHis()" class="btn btn-small"
			value="返回合同审核主页">
	</ul>
<c:set var="param" value="${workItem.bv}" />
	<form id="frameFlowForm" method="post" >
			<input type="hidden" name="loanCode" value="${loanCode }" />
			<input type="hidden" name="applyId" value="${param.applyId}" />
			<input type="hidden" name="wobNum" value="${param.wobNum}" />
			<input type="hidden" name="dealType" value="${param.dealType}" />
			<input type="hidden" name="token" value="${param.token}" />
	</form>
<div id="div9" class="content control-group">
	  <form id="carBank" method="post" >
       	<input type="hidden" value="${loanCode}" name="loanCode">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
            	<td colspan="3"><label class="lab"><span class="red">*</span>授权人：</label>
                ${carCustomer.customerName}</td>
        </tr>
		<tr>
				<td>
 				<label class="lab"><span class="red">*</span>银行卡户名：</label>
 				<input id="loanCustomerName" type="hidden" value="${carLoanInfo.loanCustomerName}"/>
				<input type="text" id="bankAccountName" name="bankAccountName" value="${carCustomerBankInfo.bankAccountName}" class="input_text178 required stringCheck" <c:if test="${carCustomerBankInfo.israre ne '1'}"> readonly="readonly" </c:if>/>
				 <input type="checkbox" id="isRareword" name="israre" value="${carCustomerBankInfo.israre}" 
				 <c:if test="${carCustomerBankInfo.israre == '1'}">  checked='checked' </c:if>
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
			 <select name="cardBank" value="${carCustomerBankInfo.cardBank}" class="select180 required">
                    <option value="">请选择</option>
                    <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${carCustomerBankInfo.cardBankCode==curItem.value}">
					      selected 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach>
				</select>
                </td>
                
            </tr>
			<tr>
                 <td><label class="lab"><span class="red">*</span>开户支行：</label>
                <input type="text" id="applyBankName" name="applyBankName" value="${carCustomerBankInfo.applyBankName}" class="input_text178 required"/></td>
                <td><label class="lab"><span class="red">*</span>银行卡号：</label>
                 <input type="text" id="firstInputBankAccount" value="${carCustomerBankInfo.bankCardNo}" class="input_text178 required"/></td>
                 <td><label class="lab"><span class="red">*</span>确认银行卡号：</label>
                <input type="text" id="confirmBankAccount" value="${carCustomerBankInfo.bankCardNo}" name="bankCardNo" class="input_text178 required"></td>
            </tr>
        </table>
        
         <!--流程参数-->
        <div class="tright mr10 mb5" >
            <input type="submit" class="btn btn-primary" value="保存"></input>
        </div>
         </form>
    </div>
</body>
</html>