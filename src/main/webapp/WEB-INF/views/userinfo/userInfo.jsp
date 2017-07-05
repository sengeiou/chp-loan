<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人信息</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
		<li class="active"><a href="${ctx}/sys/user/info">个人信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">修改密码</a></li>
	</ul><br/>
	<div class="container">
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal"><%--
		<form:hidden path="email" htmlEscape="false" maxlength="255" class="input-xlarge"/>
		<sys:ckfinder input="email" type="files" uploadPath="/mytask" selectMultiple="false"/> --%>
		<sys:message content="${message}"/>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right" for="form-field-1">归属部门:</label>
			<div class="col-sm-9">
				<label class="lbl">${user.department.name}</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right"> 姓名: </label>
			<div class="col-sm-9">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required col-xs-10 col-sm-5" readonly="true"/>
			</div>
		</div>
		
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">邮箱:</label>
			<div class="col-sm-9">
				<form:input path="email" htmlEscape="false" maxlength="50" class="email col-xs-10 col-sm-5"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">电话:</label>
			<div class="col-sm-9">
				<form:input path="phone" htmlEscape="false" maxlength="50" class="col-xs-10 col-sm-5"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">手机:</label>
			<div class="col-sm-9">
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="col-xs-10 col-sm-5"/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">备注:</label>
			<div class="col-sm-9">
				<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">用户类型:</label>
			<div class="col-sm-9">
				<label class="lbl">${user.userType}</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">用户角色:</label>
			<div class="col-sm-9">
				<label class="lbl">${user.roleNames}</label>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">上次登录:</label>
			<div class="col-sm-9">
				<label class="lbl">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</div>
		</div>
	</form:form>
	</div>
</body>
</html>