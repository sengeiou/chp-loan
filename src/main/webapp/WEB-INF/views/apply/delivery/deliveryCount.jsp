<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>交割统计</title>
<script type="text/javascript" src="${context}/js/delivery/deliveryNew.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
});
$(document).ready(function() {
	$("#countBtn").on('click',function(){
		$("#countForm").attr("action", "${ctx}/borrow/delivery/deliveryCountQuery");
		$("#countForm").submit();
	});
});
</script>
</head>
<body>
	<div class="control-group">
		<form id="countForm" method="post">
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td align="right">业务部：</td>
					<td>
						<select id="orgCode" name="orgCode" class="select180" value="${params.orgCode }">
							<option value="">请选择</option>
							<c:forEach var="org" items="${orgs }">
								<option value="${org.orgCode }" <c:if test="${org.orgCode eq params.orgCode}">selected</c:if>>${org.orgName }</option>
							</c:forEach>
						</select> 
					</td>
				</tr>
				<tr>
					<td align="right">日期：</td>
					<td>
					   <input id="startTime" onchange="checkDate(this)" class="input_text70 Wdate" name="startDate" type="text"
						readonly="readonly" value="${params.startTime }" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
						- <input id="endTime" class="input_text70 Wdate" name="endDate" type="text" readonly="readonly" value="${params.endTime }"
						 onchange="checkDate(this)" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
					</td>
					</td>
				</tr>
					
			</table>
			<div class="tright pr30 mb5">
				<input type="submit" value="搜索" class="btn btn-primary" id="countBtn"/> 
				<input type="button" value="清除" class="btn btn-primary" id="countRemoveBtn" />
			</div>
		</form>
	</div>
    <div>
	     <table cellpadding="0" cellspacing="0" border="0" width="100%">
	     	<tr>
	     	   <td>
				<span>${count }</span>
	     	   </td>
	     	</tr>
	     </table>
    </div>
</body>
</html>