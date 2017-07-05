<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>批量交割列表</title>
<script type="text/javascript" src="${context}/js/delivery/deliveryNew.js"></script>
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#singleForm").attr("action", "${ctx}/borrow/delivery/deliveryWaitBatchList");
	$("#singleForm").submit();
}
$(document).ready(function(){
	var msg = "${message}";
	if (msg != "" && msg != null) {
		art.dialog.alert(msg);
	}
});
</script>
<script type="text/javascript">
$(document).ready(function(){  
	$("#subBtnOne").on('click',function(){
		var typeRoleP = $.trim($("#typeRoleP").val());
        var custName = $.trim($("#custNameOne").val());//员工姓名
        var custCode = $.trim($("#custCodeOne").val());//员工编号
        var radio = document.getElementsByName("typeRoleOne"); //角色
        var typeRole=""; //角色
        for (i=0; i<radio.length; i++) {  
            if (radio[i].checked) {  
                typeRole=radio[i].value;
            }  
        } 
        var backData=checkParams(custName,custCode,typeRole);
        if(backData.type){
    		art.dialog.alert(backData.message);
     		return false;
    	}
   		if(typeRoleP!=typeRole){
   			art.dialog.alert("请勿跨角色交割!");
        	return false;
   		}else{
   			$("#singleForm").submit();
   		}
    }) 
});

function checkParams(custName,custCode,typeRole){
	var obj={};
	if(0!=custName.length && 0!=custCode.length && 0!=typeRole.length){
		return false;
	}else{
 		obj.type=true;
 		var custNameMessage=custName.length==0?"请输入员工姓名！":"";
 		var custCodeMessage=custCode.length==0?"请输入员工编号！":"";
 		var typeRoleMessage=typeRole.length==0?"请选择角色!":"";
 		var messageStr="";
 		if(""!=custNameMessage){
 			messageStr=custNameMessage;
 		}
 		if(""!=custCodeMessage){
 			messageStr=custCodeMessage;
 		}
 		if(""!=typeRoleMessage){
 			messageStr=typeRoleMessage;
 		}
 		obj.message=messageStr;
 		return obj;
	}
}
</script>
</head>
<body>
	<div class="control-group">
		<form id="singleForm" method="post" action="${ctx}/borrow/delivery/deliveryWaitBatchList">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="typeRoleP" name="typeRoleP" type="hidden" value="${paramsOne.typeRoleP}" />
			<input id="custNameP" name="custNameP" type="hidden" value="${paramsOne.custNameP}" />
			<input id="custCodeP" name="custCodeP" type="hidden" value="${paramsOne.custCodeP}" />
			<input id="deliveryResultP" name="deliveryResultP" type="hidden" value="${paramsOne.deliveryResultP}" />
			<input id="loanCodes" name="loanCodes"  type="hidden" value="${paramsOne.loanCodes}" />
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">员工姓名：</label>
						<input type="text" class="input_text178" id="custNameOne" name="custNameOne" value="${paramsOne.custNameOne }" />
					</td>
					<td>
						<label class="lab">员工编号：</label>
					 	<input type="text" class="input_text178" id="custCodeOne" name="custCodeOne" value="${paramsOne.custCodeOne }" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">角色：</label>
						<input type="radio" name="typeRoleOne" value="1" <c:if test="${'1' eq paramsOne.typeRoleOne}">checked</c:if> />客服
						<input type="radio" name="typeRoleOne" value="2" <c:if test="${'2' eq paramsOne.typeRoleOne}">checked</c:if> />客户经理
						<input type="radio" name="typeRoleOne" value="3" <c:if test="${'3' eq paramsOne.typeRoleOne}">checked</c:if>/>团队经理
					</td>
					<td>
					</td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="submit" value="搜索" class="btn btn-primary" id="subBtnOne"/> 
				<input type="button" value="清除" class="btn btn-primary" id="delRemoveBtnOne" />
			</div>
		</form>
	</div>
	<table id="tableListOne" class="table table-hover table-bordered table-condensed">
		<thead> 
				<th>序号</th>
				<th>员工姓名</th>
				<th>员工编号</th>
				<th>合同编号</th>
				<th>还款状态</th>
				<th>门店名称</th>
				<!-- <th hidden>门店名称编号</th>  -->
				<th>团队名称</th>
				<th>团队经理</th>
				<!-- <th hidden>团队经理工号</th>
				<th hidden>团队机构</th> -->
				<th>客户经理</th>
				<!-- <th hidden>客户经理工号</th> -->
				<th>客服专员</th>
				<!-- <th hidden>客服专员工号</th> -->
				<th>外访人员</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dl" items="${delPageOne.list }" varStatus="st">
				<tr>
					<td><c:out value="${st.count }" /></td>
					<td>${paramsOne.custNameOne }</td>
					<td>${paramsOne.custCodeOne }</td>
					<td>${dl.contractCode }</td>
					<td>${dl.dictLoanStatusLabel}</td>
					<td>${dl.orgName }</td>
					<%-- <td hidden>${dl.loanStoreOrgId }</td> --%>
					<td>${dl.teamName }</td> 
					<td>${dl.teamManagerName }</td>
					<%-- <td hidden>${dl.teamManagerCode }</td>
					<td hidden>${dl.loanTeamOrgId }</td> --%>
					<td>${dl.managerName }</td>
					<%-- <td hidden>${dl.managerCode }</td> --%>
					<td>${dl.customerServicesName }</td>
					<%-- <td hidden>${dl.customerServicesCode }</td> --%>
					<td>${dl.outbountByName}</td>
				</tr>
			</c:forEach>
			<c:if test="${delPageOne.list==null || fn:length(delPageOne.list)==0}">
				<tr>
					<td colspan="11" style="text-align: center;">没有符合条件的数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<div class="pagination">${delPageOne}</div>
	<table cellspacing="0" cellpadding="0" border="0" width="100%">
		<thead>
			<tr>
				<th><input class="btn btn-primary r" type="button" value="办理" 
				onclick="blBtnBatch('${delPageOne.list[0].loanStoreOrgId}','${delPageOne.list[0].teamManagerCode}',
				'${delPageOne.list[0].loanTeamOrgId}','${delPageOne.list[0].managerCode}',
				'${delPageOne.list[0].customerServicesCode}','${paramsOne.typeRoleOne }','${paramsOne.loanCodes }',
				'${paramsOne.deliveryResultP }','${paramsOne.custCodeP }','${paramsOne.custNameP }','${paramsOne.typeRoleP }')"></th>
			</tr>
		</thead>
	</table>
<script type="text/javascript">
    $("#tableListOne").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
	$('#tableListOne').bootstrapTable({
		method: 'get',
		cache: false,
		height: $(window).height()-260,
		
		pageSize: 20,
		pageNumber:1
	});
	$(window).resize(function () {
		$('#tableListOne').bootstrapTable('resetView');
	});
</script>
</body>
</html>