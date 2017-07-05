<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>展期客户资料</title>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script> 
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" /> 
<script type="text/javascript">
	function carExtendInfo(loanCode){
		$.ajax({
    		url : ctx + "/car/carExtendApply/isAgainExtend?loanCode=" + loanCode, // 判断除去开始展期的数据，金额是否小于3w
    		type : 'post',
    		success : function(data) {
    			if (data == "true") {
    				window.location.href = ctx + "/car/carExtendApply/toCarContract?loanCode=" + loanCode + "&" + $("#workFlowItemParam").serialize();
    			} else {
    				art.dialog.alert("展期汇诚审批金额低于3万，不可再次展期！");
    			}
    		},
    		error:function(){
    			art.dialog.alert('服务器异常！');
    			return false;
    		}
    	});
	}
function backCarExtendInfo(){
		location.href= ctx + "/car/carContract/firstDefer/selectDeferList";
}
</script>
<meta name="decorator" content="default" />
</head>

<body>
	<div style=" height:100%" >
	<form id="workFlowItemParam" modelAttribute="workItemView">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
	</form>
		<div class="tright pt5 mb5 pr30">	
			<input  class="btn btn-small" onclick="carExtendInfo('${workItem.bv.loanCode}')"  type="button" value="展期" />		
			<input  class="btn btn-small" onclick="showCarLoanHis('${workItem.bv.loanCode}')"  type="button" value="历史" />
			<input onclick="showCarLoanInfo('${workItem.bv.loanCode}')" type="button" class="btn btn-small" value="查看" />
			<input onclick="backCarExtendInfo()" type="button" class="btn btn-small" value="返回" />
		</div>
		<h2 class="pl10 f14">客户信息:</h2>
		<div class="box2">
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				
				<tr>
					<td><label class="lab">客户姓名：</label>${workItem.bv.customerName}</td>
					<td><label class="lab">性别：</label>
					${workItem.bv.customerSex}
					</td>
					<td><label class="lab">证件类型：</label>
					${workItem.bv.dictCertType}
					</td>
			    </tr>	
			    <tr>	
					<td><label class="lab">证件号码：</label>${workItem.bv.customerCertNum}</td>
					<td><label class="lab">证件有效期：</label>
						<c:if test="${workItem.bv.idStartDay!=null && workItem.bv.idEndDay==null}">长期</c:if>
						<c:if test="${workItem.bv.idStartDay!=null && workItem.bv.idEndDay!=null}">
						<fmt:formatDate value='${workItem.bv.idStartDay}' pattern="yyyy-MM-dd"/>~<fmt:formatDate value='${workItem.bv.idEndDay}' pattern="yyyy-MM-dd"/></c:if></td>
			   		<td><label class="lab">户籍地址：</label>${workItem.bv.customerRegisterAddressView}</td>	
			   	
			   	</tr>
			   	<tr>	
					<td><label class="lab">学历：</label>${workItem.bv.dictEducation}</td>
					<td><label class="lab">婚姻状况：</label>${workItem.bv.dictMarryStatus}</td>		
					<td><label class="lab">现住址：</label>${workItem.bv.customerAddressView}</td>	
				</tr>
				<tr>
					<td><label class="lab">手机号码：</label>${workItem.bv.customerMobilePhone}</td>
					<td><label class="lab">电子邮箱：</label>${workItem.bv.customerEmail}</td>
				</tr>
			</table>
	</div>

</body>
</html>