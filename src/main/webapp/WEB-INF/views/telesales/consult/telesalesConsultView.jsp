<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
	$(document).ready(
			function(){
				$('#cancleTelesalesBtn').bind('click',function(){					
					//if(confirm('确定要取消发送订单?')){
				  top.$.jBox.confirm("确定要取消发送订单吗？","系统提示", function(v, h, f){
						 if(v =='ok'){	
						    var id=$('#id').val();
							var name=$('#customerName').val();
							var url="${ctx}/telesales/consult/cancaleTelesaleConsultSend?id="+id+"&name="+name;  
							$("#inputForm").attr("action",url);
							$("#inputForm").submit();
						 }
					});
				});
				$('#submitTelesalesBtn').bind('click',function(){
					    self.location=document.referrer;
						return false;
				});
				var dd=$('#idEndDay').val();
				if(dd==""){
					$('#idEndDay').attr('disabled', true);
					$('#longTerm').attr('checked',true);
				}
				var status=$('#consOperStatus').val();
				if(status=="6"){
					$('#cancleTelesalesBtn').removeAttr("style");
				}else{
					$('#cancleTelesalesBtn').css("display","none");
				}
			}  
	);
</script>
<meta name="decorator" content="default" />
</head>
<body>
      <form:form id="inputForm" modelAttribute="consult" action=""
		class="form-horizontal">
		<h2 class=" f14 pl10 ">基本信息</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td style="width: 32%"><label class="lab">客户姓名：</label> <form:input
							path="customerBaseInfo.customerName" id="customerName" maxlength="30"
							disabled="true" class="input_text178" /> 
							<input type="hidden" id="id" value="${consult.id}"> 
					</td>
					<td><label class="lab">行业类型：</label> <form:select
							id="dictCompIndustry" path="customerBaseInfo.dictCompIndustry"
							class="select180" disabled="true">				
							<form:options items="${fns:getDictList('jk_industry_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<%-- <td style="width: 32%"><label class="lab">手机号码：</label> <form:input
							path="customerBaseInfo.customerMobilePhone"
							class="input_text178" id="customerMobilePhone" disabled="true"  maxlength="11"/></td> --%>
					<td style="width: 32%"><label class="lab">固定电话：</label> <form:input
							path="customerBaseInfo.areaNo" id="areaNo" disabled="true"  class="input_text50" maxlength="4"/>-
						<form:input path="customerBaseInfo.telephoneNo" disabled="true"  id="telephoneNo"
							class="input_text178" maxlength="8"/></td>
				</tr>
				<tr>
					<td style="width: 32%"><label class="lab">证件类型：</label> <form:select id="cardType"
							path="customerBaseInfo.dictCertType" disabled="true"  class="select180">
							<form:options items="${fns:getDictList('jk_certificate_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<td style="width: 32%"><label class="lab">证件号码：</label> <form:input
							disabled="true"  path="customerBaseInfo.mateCertNum"
							class="input_text178" id="mateCertNum" /> <span
						id="blackTip" style="color: red;"></span></td>
					<td style="width: 32%"><label class="lab">证件有效期：</label> 
						<input id="idStartDay"
							name="customerBaseInfo.idStartDay" type="text" class="input_text70 Wdate"
							value="<fmt:formatDate value="${consult.customerBaseInfo.idStartDay}" pattern="yyyy-MM-dd"/>"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})"
							style="cursor: pointer" disabled="true" /> 
						-
						<input id="idEndDay" 
							name="customerBaseInfo.idEndDay" type="text" class="input_text70 Wdate"
							value="<fmt:formatDate value="${consult.customerBaseInfo.idEndDay}" pattern="yyyy-MM-dd"/>"
							onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})"
							style="cursor: pointer" disabled="true" /> <form:checkbox path="longTerm"
							id="longTerm"  disabled="true" />长期</td>
				</tr>
				<tr>
					<%-- <td rowspan="2"><label class="lab">行业类型：</label> <form:select
							id="dictCompIndustry" path="customerBaseInfo.dictCompIndustry"
							class="select180" disabled="true">				
							<form:options items="${fns:getDictList('jk_industry_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td> --%>
					<td style="width: 32%"><label class="lab">性别：</label> <form:radiobuttons
							path="customerBaseInfo.customerSex" disabled="true"
							cssClass="customerSex"
							items="${fns:getDictList('jk_sex')}" itemLabel="label"
							itemValue="value" delimiter="&nbsp;" /></td>
				</tr>
			</table>
		</div>
		<h2 class=" f14 pl10">信借信息</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td style="width: 32%"><label class="lab"><span
							style="color: #ff0000">*</span>电销专员：</label> <form:input
							path="managerName" disabled="true" class="input_text178 required" />
					</td>
					<td style="width: 32%"><label class="lab">门店：</label>
					    <form:input path="storeName"  id="storeName" class="input_text178"  disabled="true"/>
							<form:hidden path="storeCode" id="storeCode" /></td>
					<td style="width: 32%"><label class="lab">信借类型：</label> <form:radiobutton
							path="dictLoanType" value="1" disabled="true" />个人信用借款</td>
				</tr>
				<tr>
				   <td style="width: 32%"><label class="lab">电销来源：</label> <form:select
							id="consTelesalesSource" path="consTelesalesSource" class="select180" disabled="true">							
							<form:options items="${fns:getDictList('jk_rs_src')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<td><label class="lab">计划借款金额：</label> <form:input
							path="loanApplyMoney" id="loanApplyMoney" htmlEscape="false" disabled="true"
							maxlength="50" class="input_text178" /></td>
					<td style="width: 32%"><label class="lab">借款用途：</label> <form:select
							id="dictLoanUse" path="dictLoanUse" class="select180" disabled="true">
							<form:options items="${fns:getDictList('jk_loan_use')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
				</tr>
			</table>
		</div>
		<h2 class=" f14 pl10">沟通记录及咨询状态</h2>
		<div class="box2 ">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td style="width: 32%">
						<label class="lab">沟通记录：</label>
						<form:textarea id="consLoanRecord" path="consultRecord.consLoanRecord" class="textarea_refuse" maxlength="50" rows="10" cols="50" />
					</td>
					<td style="width: 32%"><label class="lab">咨询状态：</label> <form:select
							id="consOperStatus" path="consultRecord.consOperStatus" disabled="true"
							class="select180">
							<form:options items="${fns:getDictList('jk_rs_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false"  />
						</form:select></td>
					<td style="width: 32%"></td>
				</tr>
			</table>
		</div>
		<div class="tright mt20" style="margin-top: 100px">
		   <input type="button" id="cancleTelesalesBtn" class="btn btn-primary" style="display:none;"
				value="取消发送"></input>
			<input type="button" id="submitTelesalesBtn" class="btn btn-primary"
				value="返回"></input>
		</div>
	</form:form>
</body>
</html>