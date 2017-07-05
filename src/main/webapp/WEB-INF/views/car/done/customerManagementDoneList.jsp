<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>客户管理列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script>
$(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		// 预计到店时间验证触发事件,点击搜索进行验证
		var startDate = $("#planArrivalTimeStart").val();
		var endDate = $("#planArrivalTimeend").val();
		if(endDate!=""&& endDate!=null && startDate!="" &&startDate!=null){
			var arrStart = startDate.split("-");
			var arrEnd = endDate.split("-");
			var sd=new Date(arrStart[0],arrStart[1],arrStart[2]);  
			var ed=new Date(arrEnd[0],arrEnd[1],arrEnd[2]);  
			if(sd.getTime()>ed.getTime()){   
				art.dialog.alert("预计到店时间开始日期不能大于预计到店时间结束日期!",function(){
					$("#planArrivalTimeStart").val("");
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
		 $(':input','#managementList_form')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	//敲回车执行input条件查询
	 $(function(){
	 document.onkeydown = function(e){ 
	 var ev = document.all ? window.event : e;
	 		if(ev.keyCode== 13) {
	 			page(1,${page.pageSize});
	 		}
	 	}
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
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#managementList_form").attr("action", "${ctx}/common/carHistory/customerManagementDoneList");
	$("#managementList_form").submit();
	return false;
}
</script>
</head>
<body>
	<div class="control-group">
	<form:form id="managementList_form" modelAttribute="carLoanStatusHisEx"   class="form-horizontal" method="post">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input name="menuId" type="hidden" value="${param.menuId}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <form:input id="loanCustomerName" path="loanCustomerName" class="input_text178" /></td>
                <td><label class="lab">客户经理：</label>
                <form:input id="costumerManagerName" path="costumerManagerName" class="input_text178" /></td>
                <td><label class="lab">咨询状态：</label>
                <form:select id="dictOperStatus" path="dictOperStatus" class="select180">
                <option value="">请选择</option>
                	<c:forEach items="${fns:getDictLists('0,1,2,3,5,','jk_next_step')}" var="item">
		      				<form:option value="${item.value}">${item.label}</form:option>
		  			</c:forEach>
				</form:select></td>
            </tr>
            <tr>
                <td><label class="lab">预计到店时间：</label>
                	<input id="planArrivalTimeStart" name="planArrivalTimeStart"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanStatusHisEx.planArrivalTimeStart}' type='date' pattern="yyyy-MM-dd" />" />-
            		<input id="planArrivalTimeend" name="planArrivalTimeend"  class="input_text70 Wdate" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})" style="cursor: pointer" 
            		value="<fmt:formatDate value='${carLoanStatusHisEx.planArrivalTimeend}' type='date' pattern="yyyy-MM-dd" />" />
                </td>
				 <td><label class="lab">是否电销：</label>
				 	<form:radiobuttons path="telesalesFlag" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		         </td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
        			<input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            		<input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
		</div>
		</form:form>
		</div>
		<sys:columnCtrl pageToken="customerManagementDoneList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">	
	
    <table id="tableList" class="table  table-bordered table-condensed table-hover "  style="margin-bottom: 200px;">
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
	             <tr>
	             <td><input type="checkbox" class="checkBoxItem" /></td>
	             <td>${be.index+1}</td>
	             <td>${b.loanCustomerName}</td>
	             <td>${b.storeName}</td>
	             <td><fmt:formatDate value='${b.planArrivalTime}' pattern="yyyy-MM-dd"/></td>
	             <td>${b.vehicleBrandModel}</td>
	             <td>${b.costumerManagerName}</td>
	             <td>${b.teamManagerName}</td>
	             <td>${b.dictOperStatus}
	             </td>
	             <td>${b.telesalesFlag}
	             </td>
	             	<c:if test="${b.operateStep == 4}">
					 <td class="tcenter"><input class="btn_edit" type="button" onclick="carConsultDone('${b.loanCode}')" value="查看" /></td>
	             	</c:if>
	             	<c:if test="${b.operateStep == 5}">
					 <td class="tcenter"><input class="btn_edit" type="button" onclick="appraiserEntryDone('${b.loanCode}')"  value="查看" /></td>
	             	</c:if>
	             	<c:if test="${b.operateStep == 6}">
					 <td class="tcenter"><input class="btn_edit" type="button" onclick="showCarLoanInfo('${b.loanCode}')" value="查看"  /></td>
	             	</c:if>
	             </tr>
             </c:forEach>
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