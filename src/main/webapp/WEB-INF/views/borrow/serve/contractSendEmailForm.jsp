<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/serve/contractSendEmailValidate.js"></script>
<script type="text/javascript">
	$(document).ready(
		 function() {
				$('#submitTelesalesBtn').bind('click',function(){					
						contract.datavalidate();  //验证
				});  
		 });
	
</script>
<meta name="decorator" content="default" />
</head>
<body>
	<form:form id="inputForm" modelAttribute="contractFileSendEmailView"
		action="" method='post'
		class="form-horizontal">
		<h2 class=" f14 pl10 ">客户信息</h2>
         <input type="hidden" name="fileType" value="${contractFileSendEmailView.fileType}"/>
         <input type="hidden" name="loanFlag" value="${loanFlag}"/>
		<div class="pb5 pt10 pr30 title ohide">
		<form:hidden path="loanCode"/> 
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">
						             客户姓名：</label> 
							<form:input path="receiverName" value="${loanMinute.customerName}" readonly="true" class="required input_text178" style="width:100px"/> 						
							<input type="hidden" id="contractCode" name="contractCode" value="${contractCode}" />
						<label class="lab">
						             性别：</label>
						    <form:input path="receiverSex" value="${loanMinute.customerSexLabel}" readonly="true" class="required input_text178 isChineseChar" style="width:100px"/>	             				
					</td>
				</tr>
				<tr>
					<td><label class="lab">
							证件号码：</label> 
							<form:input path="coboCertNum" value="${loanMinute.customerCertNum}" readonly="true" id='coboCertNum' class="required input_text178" style="width:300px"/>
					</td>				 
				</tr>
				<tr>
					<td><label class="lab">
						            邮箱：</label> 
						    <form:input path="receiverEmail" value="${loanMinute.customerEmail}" readonly="true" class="required input_text178" style="width:300px"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="pull-right mt10 pr30">
			<input type="submit" id="submitTelesalesBtn" class="btn btn-primary"
				value="提交"></input>
		</div>
		<div><font color="red">提示：如果修改接收邮箱，请在系统中完成申请邮箱变更流程后，再申请<c:if test="${contractFileSendEmailView.fileType=='0'}" >电子协议</c:if><c:if test="${contractFileSendEmailView.fileType=='1'}" >结清通知书</c:if></font></div>
	</form:form>
</body>
</html>