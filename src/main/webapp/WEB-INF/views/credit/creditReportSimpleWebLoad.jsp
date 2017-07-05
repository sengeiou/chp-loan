<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html ><head>
<title>个人征信网络登录</title>
<script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
<script type="text/javascript" src="${ctxjs}/common.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(function(){
	if($("#hidCodeUrl").val()==""){
		$("#creditSubmit").attr("disabled","disabled");
	}

	$("#look").bind("click",function(){
		loadImg();
	});
});
function loadImg(){
	$("#look").unbind();
	$.ajax({
        type: "GET",
        url: ctx + "/credit/creditReportSimple/laodCodeImageUrl",
        dataType: "json",
        timeout: 10000,
        success: function (data) {
        	if(data!=null){
        		if(data.retCode=="0000"){
        			$("#codeImageUrl").attr("src",data.codeImageUrl);
        			$("#cookies").val(data.cookies);
        			$("#hidCodeUrl").val(data.codeImageUrl);
        			$("#creditSubmit").removeAttr("disabled");
        		}else{
        			$("#creditSubmit").attr("disabled","disabled");
        		}
        	}
        	$("#look").bind("click",function(){
        		loadImg();
        	});
        }
    });
}
</script>
</head>
<body>
	<div class="content_bar" style="height:100%;width:100%; position:absolute; background:-moz-radial-gradient(center 80px 45deg, circle farthest-corner, #fff 0%, #f3f3f3 100%);">
		<form id="loginForm" action="${ctx}/credit/creditReportSimple/login" method="post"  style="width:480px;height:70%; padding-top:8%; margin:auto;"> 
           	<h1 style="width:160px;float:left;height:200px;line-height:315px;">用户登录</h1>
           	<div style="width:29px;height:400px; float:left;">
           		<img src="../../../static/images/line.png">
           	</div>
            <div style="margin-top:80px;"><div>
            	<c:if test="creditReportSimple.pbcGetLoginPageOutInfo.retCode != '' && creditReportSimple.pbcGetLoginPageOutInfo.retCode != '0000'">
					<font color="red">${creditReportSimple.pbcGetLoginPageOutInfo.retMsg}</font>
				</c:if>
				<font color="red">${creditReportSimple.pbcLoginOutInfo.retMsg}</font>
			</div>
            <div class="login-box">
            	<div class="login-user login-input margintop0" style="height:50px;line-height:50px">
            	<p style="float:left">用户名：</p>
                	<input required="required" type="text" name="pbcLoginInfo.userName" id="userName" value="${creditReportSimple.pbcLoginInfo.userName}" placeholder="用户名"/><br>
             	</div>
                 
             	<div class="login-password login-input" style="height:50px;line-height:50px">
             	<p style="float:left">密 &nbsp; 码：</p>
             		<input required="required" type="password" name="pbcLoginInfo.password" id="password" value="${creditReportSimple.pbcLoginInfo.password}" placeholder="密码"/><br>
             	</div>
             	<div class="login-code login-input" style="height:50px;line-height:50px"><div>
             		<p style="float:left">验证码：</p>
             		<input required="required" type="text" name="pbcLoginInfo.verifycode" value="${creditReportSimple.pbcLoginInfo.verifycode}" placeholder="验证码" style="width:80px;"/>
					<img id="codeImageUrl" src="${creditReportSimple.pbcGetLoginPageOutInfo.codeImageUrl}"/>&nbsp;&nbsp;<a id="look" href="#">看不清</a>
               	</div></div>
               	<input type="hidden" name="pbcLoginInfo.cookies" id="cookies" value='${creditReportSimple.pbcLoginInfo.cookies}' /> 
               	<input type="hidden" name="id" id="id" value='${creditReportSimple.id}' />
               	<input type="hidden" name="pbcGetLoginPageOutInfo.codeImageUrl" id="hidCodeUrl" value='${creditReportSimple.pbcGetLoginPageOutInfo.codeImageUrl}' /> 
               	<input type="hidden" name="loanCode" value="${creditReportSimple.loanCode}" />
				<input type="hidden" name="rCustomerCoborrowerId" value="${creditReportSimple.rCustomerCoborrowerId}" />
				<input type="hidden" name="dictCustomerType" value="${creditReportSimple.dictCustomerType}"/>
                <input type="hidden" name="applyCertNum" value="${applyCertNum}"/>
            </div>
            <div class="login-button" style="position: relative;margin-left:50%;margin-top:20px">
            	<span class="submit_btn" ><input id="creditSubmit" class="btn btn-primary" type="submit" value="登录" /></span>
            </div></div>
		</form>
    </div>
	
</body>
</html>
