<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				    'posNewPassword':{
				 	   max:999999999,
                	   number:true   
	                   }
				},
				messages: {
					'posNewPassword':{
						max:"POS机登录密码只能是6位数字",
						number :"POS机登录密码只能是6位数字"	
					},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
	<form:form id="inputForm" modelAttribute="user" action="${ctx}/borrow/poscard/posBacktageList/posModifyPwd" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<br/>
		<br/>
		<br/>
		<br/>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">旧密码:</label>
			<div class="col-sm-9">
				<input id="posOldPassword" name="posOldPassword" type="password" value="" maxlength="6" minlength="6"/>
				<span class="help-inline"></span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">新密码:</label>
			<div class="col-sm-9">
				<input id="posNewPassword" name="posNewPassword" type="password" value=""  maxlength="6" minlength="6" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label no-padding-right">确认新密码:</label>
			<div class="col-sm-9">
				<input id="confirmposNewPassword" name="confirmposNewPassword" type="password" value="" maxlength="6" minlength="6" class="required" equalTo="#posNewPassword"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="clearfix form-actions">
			<div class="col-md-offset-3 col-md-9">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
			</div>
		</div>
	</form:form>
</body>
</html>