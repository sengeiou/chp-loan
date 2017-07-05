<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/serve/contractSendValidate.js?ver=2"></script>
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
	<form:form id="inputForm" modelAttribute="contractFileSendView"
		action="" method='post'
		class="form-horizontal">
		<h2 class=" f14 pl10 ">邮寄信息</h2>
         <input type="hidden" name="fileType" value="${contractFileSendView.fileType}"/>
		<div class="pb5 pt10 pr30 title ohide">
		<form:hidden path="loanCode"/> 
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">
						<span style="color: #ff0000">*</span>收件人姓名：</label> 
							<form:input path="receiverName" class="required input_text178 isChineseChar" style="width:300px"/> 						
							<form:hidden path="contractCode" />
							<form:hidden path="flag"/>				
					</td>
				</tr>
				<tr>
					<td><label class="lab">
						<span style="color: #ff0000">*</span>收件人电话：</label> 
						<form:input path="receiverPhone"  id='receiverPhone' class="required input_text178" style="width:300px"/>
					</td>				 
				</tr>
				<tr>
					<td><label class="lab">
						<span style="color: #ff0000;">*</span>收件人地址：</label> <!-- isChineseChar -->
							<form:input path="receiverAddress" class="required input_text178" style="width:300px"/>
					</td>
				</tr>
				<tr>
					<td><label class="lab">
						<span style="color: #ff0000">*</span>紧急程度：</label> 
							<form:select id="emergentLevel" path="emergentLevel" class="required select180"  >
								<option value="">请选择</option>
								<form:options items="${fns:getDictList('jk_cm_admin_urgent_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
					</td>
				</tr>
			</table>
		</div>
		<div class="pull-right mt10 pr30">
			<input type="submit" id="submitTelesalesBtn" class="btn btn-primary"
				value="提交"></input>
		</div>
	</form:form>
</body>
</html>