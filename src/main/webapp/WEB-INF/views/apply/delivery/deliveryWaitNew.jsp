<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>交割列表</title>
<script type="text/javascript" src="${context}/js/delivery/deliveryNew.js"></script>
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#waitForm").attr("action", "${ctx}/borrow/delivery/deliveryWaitList");
	$("#waitForm").submit();
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
	$("#subBtn").on('click',function(){
        var custName = $.trim($("#custName").val());//员工姓名
        var custCode = $.trim($("#custCode").val());//员工编号
        var deliveryResult = $.trim($("#deliveryResult").val());//交割状态
        var radio = document.getElementsByName("typeRole"); //角色
        var typeRole=""; //角色
        for (i=0; i<radio.length; i++) {  
            if (radio[i].checked) {  
                typeRole=radio[i].value;
            }  
        } 
        //1、全部查询2、只查询交割状态   	3.员工姓名和员工编号和角色一起，没有状态也可以查
        var backData=checkParams(custName,custCode,typeRole,deliveryResult);
        if(backData.type){
    		art.dialog.alert(backData.message);
     		return false;
    	}else{
    		$("#waitForm").submit();
    	}
    }) 
});

function checkParams(custName,custCode,typeRole,deliveryResult){
	var obj={};
	if(0!=custName.length && 0!=custCode.length && 0!=typeRole.length && 0!=deliveryResult.length){
		return false;
	}else if(0!=custName.length && 0!=custCode.length && 0!=typeRole.length){
		return false;
	}else if(0!=deliveryResult.length && 0==custName.length && 0==custCode.length && 0==typeRole.length){
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
		<input id="message" name="message" type="hidden" value="${message}" />
		<form id="waitForm" method="post" action="${ctx}/borrow/delivery/deliveryWaitList">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">员工姓名：</label>
						<input type="text" class="input_text178" id="custName" name="custName" value="${params.custName }" />
					</td>
					<td>
						<label class="lab">员工编号：</label>
					 	<input type="text" class="input_text178" id="custCode" name="custCode" value="${params.custCode }" />
					</td>
					<td> 
						<label class="lab">交割状态：</label>
	               		<select id="deliveryResult" name="deliveryResult" class="select180" value="${params.deliveryResult }">
							<option value=""  <c:if test="${'' eq params.deliveryResult}">selected</c:if> >请选择</option>
							<option value="1" <c:if test="${'1' eq params.deliveryResult}">selected</c:if> >成功</option>
							<option value="2" <c:if test="${'2' eq params.deliveryResult}">selected</c:if> >失败</option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">角色：</label>
						<input type="radio" name="typeRole" value="1" <c:if test="${'1' eq params.typeRole}">checked</c:if> />客服
						<input type="radio" name="typeRole" value="2" <c:if test="${'2' eq params.typeRole}">checked</c:if> />客户经理
						<input type="radio" name="typeRole" value="3" <c:if test="${'3' eq params.typeRole}">checked</c:if>/>团队经理
					</td>
					<td>
					</td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="submit" value="搜索" class="btn btn-primary" id="subBtn"/> 
				<input type="button" value="清除" class="btn btn-primary" id="delRemoveBtn" />
			</div>
		</form>
	</div>
	<p class="mb5">
		<button id="batchDelivery" class="btn btn-small">批量交割</button>
		<button id="reportExcel" role="button" class="btn btn-small jkhj_grantsure_upload" data-target="#uploadDeliveryModal" data-toggle="modal">导入Excel</button>
		<button id="deliveryCount" class="btn btn-small">交割统计</button>
	</p>
	<table id="tableList" class="table table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th></th>
				<th>序号</th>
				<th>员工姓名</th>
				<th>员工编号</th>
				<th>合同编号</th>
				<th>客户姓名</th>
				<th>当前状态</th>
				<th>门店名称</th>
				<th>团队名称</th>
				<th>团队经理</th>
				<th>客户经理</th>
				<th>客服专员</th>
				<th>交割状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="dl" items="${delPage.list }" varStatus="st">
				<tr>
					<td>
						<input type="checkbox" name="loanCodes" value="${dl.loanCode }" >
					</td>
					<td><c:out value="${st.count }" /></td>
					<td>${params.custName }</td>
					<td>${params.custCode }</td>
					<td>${dl.contractCode }</td>
					<td>${dl.loanCustomerName }</td>
					<td>${dl.dictLoanStatusLabel}</td>
					<td>${dl.orgName }</td>
					<td>${dl.teamName }</td> 
					<td>${dl.teamManagerName }</td>
					<td>${dl.managerName }</td>
					<td>${dl.customerServicesName }</td>
            		<td>
            			<c:if test="${dl.deliveryResult=='1'}">
                   			 <span>成功</span>
               	 		</c:if>
              			<c:if test="${dl.deliveryResult=='2'}">
                  			 <span>失败</span>
              			</c:if>
              			<c:if test="${dl.deliveryResult == '' || dl.deliveryResult == null }">
                   			 ${dl.deliveryResult}
               	 		</c:if>
             	    </td>
					<td>
						<button class="btn_edit" id="${dl.loanCode}" onclick="traBtn('${dl.contractCode}','${dl.loanCode}','${params.custName }','${params.custCode }','${params.deliveryResult }','${params.typeRole }')">交割</button>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${delPage.list==null || fn:length(delPage.list)==0}">
				<tr>
					<td colspan="14" style="text-align: center;">没有符合条件的数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<div class="pagination">${delPage}</div>
	<div class="modal fade" id="uploadDeliveryModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
		<div class="modal-dialog role="document">
			<div class="modal-content">
				<form id="uploadDeliveryForm" role="form" enctype= "multipart/form-data" method="post" action="${ctx}/borrow/delivery/importResult">
					 <div class="modal-header">
					     <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						 <h4 class="modal-title" id="myModalLabel">导入Excel</h4>
					 </div>
					 <div class="modal-body">
						 <%-- <input type="hidden" name="menuId" value="${param.menuId }"> --%>
						 <input type='file' name="file" id="fileid">
					 </div>
		             <div class="modal-footer">
		 	 			 <input class="btn btn-primary" type="submit" id="upLoadBtn" value="确认">
						 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
		            </div>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
	    $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>