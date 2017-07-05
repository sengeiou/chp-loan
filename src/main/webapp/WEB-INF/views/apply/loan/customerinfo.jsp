<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#customerName").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/apply/consult/list">客户咨询列表</a></li>
		<li class="active">
		  <a href="${ctx}/apply/consult/openConsultForm?id=${consult.id}">客户咨询<shiro:hasPermission name="apply:consult:edit">${not empty consult.id?'修改':'添加'}</shiro:hasPermission>
			<shiro:lacksPermission name="apply:consult:edit">查看</shiro:lacksPermission>
		  </a>
		</li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="consult" action="${ctx}/apply/consult/saveConsult" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">客户姓名:</label>
			<div class="controls">
				<form:input path="customerName" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
				<form:input path="mobileNo" htmlEscape="false" maxlength="50" class="required mobile"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件类型:</label>
			<div class="controls">
				<form:select id="cardType" path="cardType" class="input-medium">
					<form:options items="${fns:getDictList('card_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">证件号码:</label>
			<div class="controls">
				<form:input path="cardNo" htmlEscape="false" maxlength="100" class="required idCard"/>
				<span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">计划借款金额:</label>
			<div class="controls">
			    <form:input path="planLoanMoney" htmlEscape="false" maxlength="50" class="required number"/>
			    <span class="help-inline"><font color="red">*</font></span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="apply:consult:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="提交"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>