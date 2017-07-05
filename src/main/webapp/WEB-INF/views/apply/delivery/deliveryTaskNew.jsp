<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="default" />  
<script type="text/javascript" src="${context}/js/delivery/deliveryNew.js"></script>
<script type="text/javascript">
function page(n,s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#delForm").attr("action", "${ctx}/borrow/taskDelivery/queryDeliveryList");
	$("#delForm").submit();
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
	$("#delSubBtn").on('click',function(){
        var custName = $.trim($("#custName").val());//员工姓名
        var custCode = $.trim($("#custCode").val());//员工编号
        var deliveryResult = $.trim($("#deliveryResult").val());//交割状态
       
        //1、全部查询2、只查询交割状态   	3.员工姓名和员工编号没有状态也可以查
        var backData=checkParams(custName,custCode,deliveryResult);
        if(backData.type){
    		art.dialog.alert(backData.message);
     		return false;
    	}else{
    		$("#delForm").submit();
    	}
    }) 
});

function checkParams(custName,custCode,deliveryResult){
	var obj={};
	if(0!=custName.length && 0!=custCode.length && 0!=deliveryResult.length){
		return false;
	}else if(0!=custName.length && 0!=custCode.length){
		return false;
	}else if(0!=deliveryResult.length && 0==custName.length && 0==custCode.length){
		return false;
	}else{
 		obj.type=true;
 		var custNameMessage=custName.length==0?"请输入员工姓名！":"";
 		var custCodeMessage=custCode.length==0?"请输入员工编号！":"";
 		var messageStr="";
 		if(""!=custNameMessage){
 			messageStr=custNameMessage;
 		}
 		if(""!=custCodeMessage){
 			messageStr=custCodeMessage;
 		}
 		obj.message=messageStr;
 		return obj; 
	}
}
</script>
<title>匹配列表</title>
</head>
<body>
   	<div class="control-group">
		<form id="delForm" method="post" action="${ctx}/borrow/taskDelivery/queryDeliveryList">
			<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
       		<table cellpadding="0" cellspacing="0" border="0" width="100%">
           		<tr>
               		<td>
               			<label class="lab" >员工姓名：</label>
               			<input type="text" class="input_text178" id="custName" name="custName" value="${params.custName }"/>
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
       		</table>
       		<div class="tright pr30 pb5">
       			<input type="submit" class="btn btn-primary" value="搜索" id="delSubBtn">
       			<input type="button" class="btn btn-primary" value="清除" id="removeBtn"/>
       		</div> 
        </form>
   </div> 
      <p class="mb5">
		<button id="exportExcel" class="btn btn-small">导出Excel</button>  本次批量交割成功：${countSuccess }条,失败：${countFalse }条;
	  </p>
      <table id="tableList" class="table table-hover table-bordered table-condensed">
    	  <thead>  
    		<!--  <tr>
    		    <th></th>                
          		<th rowspan="2">序号</th>
          		<th rowspan="2">客户姓名</th>  
          		<th rowspan="2">合同编号</th>  
          		<th colspan="7">原归属信息</th> 
          		<th colspan="7">现归属信息</th>           
				<th rowspan="2">交割状态</th>
				<th rowspan="2">失败原因</th>
       		</tr> -->
       		 <tr>
       		    <th></th>
       		    <th>序号</th>
          		<th>客户姓名</th>  
          		<th>合同编号</th>  
          		<th>交割前所属门店</th>
          		<th>交割前团队经理</th>
          		<th>交割前团队经理员工号</th>
          		<th>交割前客户经理</th>
          		<th>交割前客户经理员工号</th>
				<th>交割前客服人员</th>
				<th>交割前客服人员员工号</th>
				<th>交割后所属门店</th>
          		<th>交割后团队经理</th>
          		<th>交割后团队经理员工号</th>
          		<th>交割后客户经理</th>
          		<th>交割后客户经理员工号</th>
				<th>交割后客服人员</th>
				<th>交割后客服人员员工号</th>
				<th>交割状态</th>
				<th>失败原因</th>
       		</tr>
       	</thead>
        <tbody>
          	<c:forEach var="t" items="${taskPage.list }" varStatus="sta">
   				<tr>
   				    <td>
						<input type="checkbox" name="ids" value="${t.id }">
					</td>
        			<td>${sta.count }</td>
        			<td>${t.loanCustomerName }</td> 
        			<td>${t.contractCode }</td>
        			<td>${t.storesName }</td>
        			<td>${t.teamManagerName }</td>
        			<td>${t.teamManagerCode }</td>
        			<td>${t.managerName }</td>
        			<td>${t.managerCode }</td>
        			<td>${t.customerServicesName }</td>
        			<td>${t.customerServicesCode }</td>
           			<td>${t.newStoresName }</td>
           			<td>${t.newTeamManagerName }</td>
           			<td>${t.newTeamManagerCode }</td>
           			<td>${t.newManagerName }</td>
           			<td>${t.newManagerCode }</td>
           			<td>${t.newCustomerServicesName }</td>
           			<td>${t.newCustomerServicesCode }</td>
           			<td>
            			<c:if test="${t.deliveryResult=='1'}">
                   			 <span>成功</span>
               	 		</c:if>
              			<c:if test="${t.deliveryResult=='2'}">
                  			 <span>失败</span>
              			</c:if>
              			<c:if test="${t.deliveryResult == '' || t.deliveryResult == null }">
                   			 ${t.deliveryResult}
               	 		</c:if>
             	    </td>
           			<td>${t.rejectedReason }</td>
         		</tr>
 			</c:forEach>
 			<c:if test="${taskPage.list==null || fn:length(taskPage.list)==0}">
				<tr>
					<td colspan="20" style="text-align: center;">没有符合条件的数据</td>
				</tr>
			</c:if> 
 		</tbody>	 
    </table>
 	<div class="pagination">${taskPage }</div>
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
