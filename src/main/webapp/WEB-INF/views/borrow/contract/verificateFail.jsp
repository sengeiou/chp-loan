<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript">
	function checkVer(val){
		if(val=='0'){
			$("#dinamicDiv").show();
			$("#verifiateReason").css('display',''); 
			$("#verifiateCodeOne").css('display',''); 
			$("#verifiateCodeTwo").css('display','');
			
		}else{
			//$("#dinamicDiv").hide();
			$("#verifiateReason").css('display','none'); 
			$("#verifiateCodeOne").css('display','none'); 
			$("#verifiateCodeTwo").css('display','none');
		}
	}
</script>
<title>验证失败</title>
</head>
<body>
<form  action='${ctx}/paperless/confirminfo/savePhoto' method='post' id="verForm">
 <table style="margin-top: 10px; border-collapse:separate;border-spacing:10px;" >
  	<tr >
  		<th  style="width: 40%;text-align: right;">
  	    	失败原因：
  	    </th>
  	    <th align="left" style="width: 70%;text-align: left;">
  	    	${message }
  	    </th>
  	</tr>
  	<tr >
  		<th  style="width: 50%;text-align: right;">
  	    	是否进行手动验证：
  	    </th>
  	    <th align="left" style="width: 60%;text-align: left;">
  	    	<input type="radio" name="verificationRadio" value="0"  onchange="checkVer('0')"/>是
  	    	<input type="radio" name="verificationRadio" value="1" onchange="checkVer('1')"/>否
  	    </th>
  	</tr>
  
  	<tr>
  		<th><input type='hidden' id='customerId' name='customerId' value='${customerId}'/>
    		<input type='hidden' id='customerType' name='customerType' value='${customerType}'/>
    	</th>
    </tr>
   </table>
   <div id="dinamicDiv" hidden="hide">
   		<table style="margin-top: 10px; border-collapse:separate;border-spacing:10px;" >
   				
			  	<c:if test="${code=='2014'}">
			  	<tr id="verifiateCodeOne">
			  		<th  colspan="2">
			  	    	<span style="color:red;">*请进行特殊事项邮件申请并将该客户户籍证明与签约资料一同上传影像平台</span>
			  	    </th>
			  	   
			  	</tr>
			  	</c:if>
			  	<c:if test="${code=='2012'|| code=='2030' || code==SYS_CERT_VERIFY_FAILCODE_PER}">
			  	<tr id="verifiateCodeTwo">
			  		<th  colspan="2">
			  	    	<span style="color:red;">*请进行特殊事项邮件申请</span>
			  	    </th>
			  	   
			  	</tr>
			  	</c:if>
			  	
			  	<tr id="verifiateReason" style="display:'';">
			  		<th  style="width: 30%;text-align: right;">
			  	    	手动验证原因：
			  	    </th>
			  	    <th  style="width: 70%;text-align: left;">
			  	    	<textarea name="verificateReason" id="verificateReason" cols="20" rows="10" style="height: 80px;width:230px;"></textarea>
			  	    </th>
			  	</tr>
   		</table>
   </div>
 </form>
</body>
</html>