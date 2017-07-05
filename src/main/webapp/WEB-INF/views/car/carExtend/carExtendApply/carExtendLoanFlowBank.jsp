<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!--管辖及开户信息-->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewExtendMeetApply.js"></script>
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
jQuery.validator.addMethod("stringCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5 .·]{1,20}$/;
		isOK = regex.test(value);
    }
	return isOK;
}, "只能输入中文、英文、空格、点号");
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
			$("#extendSubmitBtn").click(function() {
				if(!confirmSign_Form.loanddd()){
		    		  return;
		    	 }
				var flag = true;
		    	if (!checkForm($(this).parents("form"))) {
		    		flag = false;
		    	}
		    	if($("#applyBankName").hasClass("error") || $("#bankAccountName").hasClass("error")){
		    		flag = false;
		    	}
				var param = $("#carBank").serialize();
				if(flag){
				$.ajax({		
		    		url:ctx + "/car/carExtendApply/carLoanFlowBank",
		    		type:'post',
		    		data: param,
		    		dataType:'json',
		    		success:function(data){
		    			if(data == true){
		    				windowLocationHref(ctx + "/car/carContract/firstDefer/selectDeferList");
		    				//window.parent.closeAndReLocate();
		    			//	window.frames['mainFrame'].document.location = ctx+"/car/carExtendWorkItems/fetchTaskItems/extendAppraiser";
		    			}else{//parent.window.frames['frame_content'].document.location="http://www.baidu.com";
		    				art.dialog.alert("其它页签填写的数据不全，提交失败!");
		    			}
		    		},
		    		error:function(){
		    			art.dialog.alert('服务器异常！');
		    			return false;
		    		}
		    	});
				}
			});
			$('#isRareword')
			.bind('click',
					function() {
						if ($(this).prop('checked') == true
								|| $(this).prop('checked') == 'checked') {
							$('#bankAccountName').removeAttr("readonly");
							$('#isRareword').val("1");
						} else {
							$('#bankAccountName').attr("readonly","readonly");
							$("#bankAccountName").val($("#loanCustomerName").val());
							$('#isRareword').val("0");
						}
					});
			if("${carCustomerBankInfo.bankAccountName}"==""){
				$("#bankAccountName").val("${carLoanInfo.loanCustomerName}");
			}
 });
var confirmSign_Form = {};
confirmSign_Form.loanddd = function(){
	return $("#carBank").validate({
		rules : {
			'bankCardNo' :{
				required : true,
				equalTo  : "#firstInputBankAccount",
				digits	 : true,
				min		 : 0,
				maxlength: 19,
				minlength: 14
			},
			'bankCardNo1':{
				required : true,
				digits	 : true,
				min		 : 0,
				maxlength: 19,
				minlength: 14
			},
			'applyBankName' :{
				 stringCheck : true 
			}
		},
		messages : {
			'bankCardNo' :{
				required : "必须输入",
				digits   :  "银行卡号必须为数字类型",
				equalTo  : "输入银行卡号不一致",
				min      :"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
				minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
			},
			'bankCardNo1':{
				required : "必须输入",
				digits   :  "银行卡号必须为数字类型",
				min      :"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
				minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
			}
		}
	}).form();
};
</script>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarContract?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendInfo?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowCustomer?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendCompany?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendContact?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toExtendCoborrower?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowBank?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
</ul>
<jsp:include page="_frameFlowForm.jsp"></jsp:include>
<div id="div9" class="content control-group">
	  <form id="carBank">
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
            	<td colspan="3"><label class="lab"><span class="red">*</span>授权人：</label>
                ${carLoanInfo.loanCustomerName}
                <input id="loanAuthorizer" type="hidden" value="${carLoanInfo.loanCustomerName}"/>
                </td>
                
        </tr>
		<tr>
				<td>
 				<label class="lab"><span class="red">*</span>银行卡户名：</label>
 				<input id="loanCustomerName" type="hidden" value="${carLoanInfo.loanCustomerName}"/>
				<input type="text" id="bankAccountName" name="bankAccountName" value="${carCustomerBankInfo.bankAccountName}" class="input_text178 required stringCheck" <c:if test="${carCustomerBankInfo.israre ne '1'}"> readonly="readonly" </c:if>/>
				 <input type="checkbox" id="isRareword" name="israre" value="${carCustomerBankInfo.israre}" 
				 <c:if test="${carCustomerBankInfo.israre == '1'}">  checked='checked'</c:if>
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
                <input type="text" id="applyBankName" name="applyBankName" value="${carCustomerBankInfo.applyBankName}" class="input_text178 required stringCheck"/></td>
                <td><label class="lab"><span class="red">*</span>银行卡号：</label>
                 <input type="text" id="firstInputBankAccount" name="bankCardNo1" value="${carCustomerBankInfo.bankCardNo}" class="input_text178 required"/></td>
                 <td><label class="lab"><span class="red">*</span>确认银行卡号：</label>
                <input type="text" id="confirmBankAccount" value="${carCustomerBankInfo.bankCardNo}" name="bankCardNo" class="input_text178 required"></td>
               
            </tr>
        </table>
        
         <!--流程参数-->
        <div class="tright mr10 mb5" >
           <input type="button" class="btn btn-primary" id="extendSubmitBtn" value="提交"></input>
        </div>
         </form>
    </div>
</body>
</html>