<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html style="overflow-x:auto;overflow-y:auto;">
<head>
	<title>车借咨询待办</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<meta name="decorator" content="default"/>
<script>
$(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
			// 预计到店日期验证触发事件,点击搜索进行验证
			var startDate = $("#planArrivalTime").val();
			var endDate = $("#planArrivalTimeend").val();
			if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
				var arrStart = startDate.split("-");
				var arrEnd = endDate.split("-");
				var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
				var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
				if(sd.getTime()>ed.getTime()){   
					art.dialog.alert("预计到店日期开始日期不能大于预计到店时间结束日期!",function(){
						$("#planArrivalTime").val("");
						$("#planArrivalTimeend").val("");
					});
					return false;     
				}else{
					page(1,${page.pageSize}); 
				}  
			}else{
				page(1,${page.pageSize});
			} 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#grantForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("select").trigger("change");
	 });
	//全选js
	 $("#checkAll").bind('click',function(){
	 	  if(this.checked == true){
	 		  $(".checkBoxItem").attr('checked',true);
	 	  }else {
	 		  $(".checkBoxItem").attr('checked',false);
	 	  }
	 });
});
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
		<li class="active"><a href="${ctx}/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog">咨询待办列表</a></li>
		<!-- <li ><a href="${ctx}/car/carLoanWorkItems/fetchTaskItems/appraiser">评估师待办列表</a></li> -->
	</ul>
	<div class="control-group">
	<form:form id="grantForm" class="form-horizontal" modelAttribute="carLoanAdvisoryBacklogEx"  action="${ctx}/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input name="menuId" type="hidden" value="${param.menuId}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input id="customerName" path="customerName" class="input_text178" /></td>
                <td><label class="lab">客户经理：</label><form:input id="customerManager" path="customerManager" class="input_text178" /></td>
                <td><label class="lab">咨询状态：</label><form:select id="dictOperStatus" path="dictOperStatus" class="select180">
                <option value="">请选择</option>
                	<c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
		      				<form:option value="${item.value}">${item.label}</form:option>
		  			</c:forEach>
				</form:select></td>
            </tr>
            <tr>
                <td><label class="lab">预计到店时间：</label><input id="planArrivalTime" name="planArrivalTime"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanAdvisoryBacklogEx.planArrivalTime}' type='date' pattern="yyyy-MM-dd" />" />-
            		<input id="planArrivalTimeend" name="planArrivalTimeend"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanAdvisoryBacklogEx.planArrivalTimeend}' type='date' pattern="yyyy-MM-dd" />" />	
                </td>
				 <td><label class="lab">是否电销：</label>
				 	<form:radiobuttons path="consTelesalesFlag" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		         </td>
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
            <th><input type="checkbox" id="checkAll"/></th>
            <th>序号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>预计到店时间</th>
            <th>车辆型号</th>
            <th>客户经理</th>
            <th>团队经理</th>
            <th>咨询状态</th>
            <th>是否电销</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        	 <c:forEach items="${page.list}" var="b" varStatus="be">
<%--              <tr <c:if test="${b.dictLoanStatus==5||b.dictLoanStatus==46}">style='color:red'</c:if>> --%>
			<tr <c:if test="${fn:contains(b.orderField,'0,') }">class='trRed'</c:if>>
             <td><input type="checkbox" class="checkBoxItem" /></td>
             <td>${be.index+1}</td>
             <td>${b.customerName}</td>
             <td>${b.mingDiang}</td>
             <td><fmt:formatDate value='${b.planArrivalTime}' pattern="yyyy-MM-dd"/></td>
             <td>${b.vehicleBrandModel}</td>
             <td>${b.customerManager}</td>
             <td>${b.teamManager}</td>
             <td><c:choose>
             			<c:when test="${b.dictOperStatus==0}">继续跟踪</c:when>
             			
             	</c:choose>
             </td>
             <td><c:choose><c:when test="${b.consTelesalesFlag==1}">是</c:when>
             				<c:otherwise>否</c:otherwise>
             </c:choose>
             </td>
				 <td class="tcenter">
				 <c:choose>
					 <c:when test="${b.dictLoanStatus==5||b.dictLoanStatus==46}">
					 	<input type="button" value="办理" class="btn_edit"  onclick="window.location='${ctx}/bpm/flowController/openForm?applyId=${b.applyId}&wobNum=${b.wobNum}&dealType=0&token=${b.token}'"></input>
					 </c:when>
	             	 <c:otherwise>
	             		<button class="btn_edit" onclick="window.location='${ctx}/bpm/flowController/openLaunchForm?flowCode=loanCarFlow&loanCode=${b.loanCode}&customerCode=${b.customerCode}'">办理</button>
	             	 </c:otherwise>
             	</c:choose>
				 </td>
             </tr>
             </c:forEach>
             <c:if test="${ page.list==null || fn:length(page.list)==0}">
		        <tr><td colspan="11" style="text-align:center;">没有待办数据</td></tr>
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