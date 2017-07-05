<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html ><head>
<title>个人征信网络登录</title>
<script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
<script type="text/javascript" src="${ctxjs}/credit/creditReportSimpleForm.js"></script>
<script type="text/javascript" src="${ctxjs}/common.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">


</script>
</head>
<body>
	<div class="content_bar" style="height:100%;width:100%; position:absolute; background:-moz-radial-gradient(center 80px 45deg, circle farthest-corner, #fff 0%, #f3f3f3 100%);">
		<form id="loginForm" action="${ctx}/credit/creditReportSimple/getReport" method="post"  style="width:480px;height:70%; padding-top:8%; margin:auto;"> 
           	<h1 style="width:160px;float:left;height:200px;line-height:315px;">登录</h1>
           	<div style="width:29px;height:400px; float:left;">
           		<img src="../../../static/images/line.png">
           	</div>
            <div style="margin-top:80px;"><div >
				<font color="red">${creditReportSimple.pbcGetReportOutInfo.retMsg}</font>
			</div>
            <div class="login-box">
            	<div class="login-user login-input margintop0" style="height:50px;line-height:50px">
            	<p style="float:left">授权码：</p>
                	<input required="required" type="text" name="pbcGetReportInfo.tradeCode" id="tradeCode" value="" placeholder="授权码"/>
             	</div>
               	
               	<input type="hidden" name="pbcGetReportInfo.cookies" id="cookies" value='${creditReportSimple.pbcLoginOutInfo.cookies}' />
                <input type="hidden" name="id" id="creditReportSimpleId" value='${creditReportSimple.id}' />
                <input type="hidden" name="loanCode" value="${creditReportSimple.loanCode}" />
				<input type="hidden" name="rCustomerCoborrowerId" value="${creditReportSimple.rCustomerCoborrowerId}" />
				<input type="hidden" name="dictCustomerType" value="${creditReportSimple.dictCustomerType}"/>
                <input type="hidden" name="applyCertNum" value="${applyCertNum}"/>
            </div>
            <div class="login-button" style="position: relative;margin-left:50%;margin-top:20px">
            	<span class="submit_btn"><input class="btn btn-primary" type="submit" value="登录" /></span>
            </div></div>
		</form>
    </div>
	
</body>
</html>
