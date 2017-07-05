<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title>车借OCR待办</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<meta name="decorator" content="default"/>
<script>
$(document).ready(function(){
	$('#searchBtn').bind('click',function(){
		page(1,${page.pageSize});
	});
	 $('#clearBtn').bind('click',function(){
		 $(':input','#grantForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("select").trigger("change");
	 });
});
function showCarOcr(id){
	window.location.href = ctx + "/car/carOcr/carOcrQue?id="+id;    
}
function page(n,s){
    if(n) $("#pageNo").val(n);
    if(s) $("#pageSize").val(s);
    $("#grantForm").submit();
    return false;
}  
</script>
<style type="text/css">
.trRed {
	color:red;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/car/carOcr/fetchTaskItems">OCR待办列表</a></li>
		<!-- <li ><a href="${ctx}/car/carLoanWorkItems/fetchTaskItems/appraiser">评估师待办列表</a></li> -->
	</ul>
	<div class="control-group">
	<form:form id="grantForm" class="form-horizontal" modelAttribute="carLoanAdvisoryBacklogEx"  action="${ctx}/car/carOcr/fetchTaskItems" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input name="menuId" type="hidden" value="${param.menuId}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input id="customerName" path="customerName" class="input_text178" /></td>
                <td><label class="lab">客户身份证：</label><form:input id="customerCertNum" path="customerCertNum" class="input_text178" /></td>
            </tr>
            <tr>
            	<td><label class="lab">车牌号：</label><form:input id="vehicleBrandModel" path="vehicleBrandModel" class="input_text178" /></td>
            	<td><label class="lab">客户经理：</label><form:input id="customerManager" path="customerManager" class="input_text178" /></td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
        		<input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            	<input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
		</div>
		</form:form>
		</div>
	<sys:columnCtrl pageToken="carBorrowAdvisoryList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
    <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom: 200px;">
    <thead>
        <tr>
            <th>序号</th>
            <th>客户姓名</th>
            <th>身份证号</th>
            <th>车辆型号</th>
            <th>APP咨询创建时间</th>
            <th>客户经理</th>
            <th>团队经理</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        	 <c:forEach items="${page.list}" var="b" varStatus="be">
<%--              <tr <c:if test="${b.dictLoanStatus==5||b.dictLoanStatus==46}">style='color:red'</c:if>> --%>
			<tr <c:if test="${fn:contains(b.orderField,'0,') }">class='trRed'</c:if>>
             <td>${be.index+1}</td>
             <td>${b.customerName}</td>
             <td>${b.customerCertNum}</td>
             <td>${b.vehicleBrandModel}</td>
             <td><fmt:formatDate value='${b.planArrivalTime}' pattern="yyyy-MM-dd"/></td>
             <td>${b.customerManager}</td>
             <td>${b.teamManager}</td>
			 <td class="tcenter">
	             <button class="btn_edit" onclick="javascript:showCarOcr('${b.id}')">办理</button>
			 </td>
             </tr>
             </c:forEach>
             <c:if test="${ page.list==null || fn:length(page.list)==0}">
		        <tr><td colspan="8" style="text-align:center;">没有待办数据</td></tr>
		      </c:if>
       </tbody>   
    </table>
    </div>
<div class="pagination">${page}</div>
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