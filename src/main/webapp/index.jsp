<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>${fns:getConfig('productName')} 登录</title>
	  <meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="http://jeesite.com/"/>
	<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
	<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
	<link href="${ctxStatic}/bootstrap/3.3.5/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/3.3.5/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/font-awesome.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/bootstrap/3.3.5/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/ace.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/common/jeesite.min.css" type="text/css" rel="stylesheet" />
	<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>
	<script type="text/javascript">var basePath='${ctx}';</script>
	<link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
	<style type="text/css">
html, table {
	width: 100%;
	height:100%;
	text-align: center;
}
input:-webkit-autofill {
	-webkit-box-shadow: 0 0 0px 1000px white inset !important; 
}

body{
	background:url(static/ckeditor/images/body.png) center top no-repeat ;
	background-color:#c4d2ea;
		height:auto;
}
.form-signin-heading {
	font-family: Helvetica, Georgia, Arial, sans-serif, 黑体;
	font-size: 36px;
	margin-bottom: 40px;
	color: #0663a2;
}

.form-signin {
 background:url(static/ckeditor/images/box-bg.png) no-repeat ;
		width: 400px;
	height:238px;
	padding:62px 29px 29px 20px;
	margin: 0 auto ;
	
}

.form-signin .checkbox {
	margin-bottom: 10px;
	color: #0663a2;	
}

.form-signin .input-label {
	line-height: 23px;
	color: #333;
	font:16px normal '宋体';	
	
}

.form-signin .input-block-level {
   width:260px;
   vertical-align:middle;
  	outline:none;
	font-size: 16px;
     height: auto; 
     margin-top:10px;
	 margin-bottom: 12px; 
	padding: 11px;
	*padding-bottom: 0;
	_padding: 7px 7px 9px 7px;
      border-color:#66738a;
}

.form-signin .btn.btn-large {
	font-size: 16px;
	
	
}

.form-signin #themeSwitch {
	
}

.form-signin div.validateCode {
	padding-bottom: 15px;
}

.mid {
	vertical-align: middle;
}

.header {
	height: 80px;
	padding-top: 20px;
}

.alert {
	position: relative;
	width: 300px;
	margin: 0 auto;
	*padding-bottom: 0px;
}

label.error {
	background: none;
	width: 270px;
	font-weight: normal;
	color: inherit;
	margin: 0;
}

.login-in{
    padding-left:20px;
    padding-top:20px;
    margin-left:220px;
}
.footer{
      width:100%;
       background:rgba(147, 161, 185, 0.5);
     color:#fff;
     padding-top:19px;
    position:fixed;
    bottom:0;
}
</style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#loginForm").validate({
				rules: {
				},
				messages: {
					username: {required: "请填写用户名."},password: {required: "请填写密码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架或在对话框中，则弹出提示并跳转到首页
		if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
			alert('未登录或登录超时。请重新登录，谢谢！');
			top.location = "${ctx}";
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header">
		<c:if test="${not empty message}">
		<div class="alert alert-danger alert-dismissible" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		   ${message}
		</div>
		</c:if>
	</div> 
	<h1 class="form-signin-heading" style='margin-left:-45px;'>　<img src="static/ckeditor/images/logo2.png"/> </h1>
	<form id="loginForm" class="form-signin" action="j_security_check" >
	   <table>
	   	  <tr>
	   	  	  <td><label class="input-label" for="username">用户名：</label></td>
	   	  	  <td><input type="text" id="username" name="j_username" class="input-block-level required" value="${username}"></td>
	   	  </tr>
	   	  <tr>
	   	  	  <td><label class="input-label" for="password">密<span style="padding-right:14px"></span>码：</label></td>
	   	  	  <td><input type="password" id="password" name="j_password" class="input-block-level required"><span class="help-inline"> </span></td>
	   	  </tr>
	   </table>
		<%--s
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
        <%-- <label for="rememberMe" title="下次不需要再登录">
		<input type="checkbox" id="rememberMe" name="rememberMe" ${rememberMe ? 'checked' : ''}/> 记住密码</label> --%>
		<p class='login-in'>
		     <input class="btn btn-large xt-btn-primary" type="submit" value="登 录"/></p>
		<div id="themeSwitch" class="dropdown" style='right:121px;bottom:39px;'>
			<%-- <a class="dropdown-toggle" data-toggle="dropdown" href="#">${fns:getDictLabel(cookie.theme.value,'theme','默认主题')}<b class="caret"></b></a> --%>
			<ul class="dropdown-menu">
			  <c:forEach items="${fns:getDictList('theme')}" var="dict"><li><a href="#" onclick="location='${pageContext.request.contextPath}/theme/${dict.value}?url='+location.href">${dict.label}</a></li></c:forEach>
			</ul>
			<!--[if lte IE 6]><script type="text/javascript">$('#themeSwitch').hide();</script><![endif]-->
		</div>
	</form>
	<br/><br/><br/>
	<div class="footer"> 
		Copyright &copy; 2015信和财富　<%-- <a href="${pageContext.request.contextPath}${fns:getFrontPath()}">${fns:getConfig('productName')} --%></a> 
	</div>
	<%-- <script src="${ctxStatic}/flash/zoom.min.js" type="text/javascript"></script> --%>
</body>
</html>