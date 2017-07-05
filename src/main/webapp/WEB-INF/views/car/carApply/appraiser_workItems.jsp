<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>评估师录入待办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#inputForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
 });
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/car/CarLoanAdvisoryBacklog/CarLoanAdvisoryBacklog">咨询待办列表</a></li>
		<li class="active"><a href="${ctx}/car/carLoanWorkItems/fetchTaskItems/appraiser">评估师待办列表</a></li>
	</ul>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanFlowQueryParam" action="${ctx}/car/carLoanWorkItems/fetchTaskItems/faceFirstAudit" method="post" class="form-horizontal">
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
            <td><label class="lab">申请日期：</label>
            	<form:input path="loanApplyTimeStart"  class="input_text178" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: pointer"/>
            	至
            	<form:input path="loanApplyTimeEnd"  class="input_text178" 
            		onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor: pointer"/>
            </td>
             <td><label class="lab">产品类型：</label>
             	<form:select id="productType" path="borrowProductCode" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_car_loan_product_type')}" var="product_type">
		                             <form:option value="${product_type.value}">${product_type.label}</form:option>
		            </c:forEach>  
		         </form:select>
             </td>
         </tr>
		<tr >
				<td><label class="lab">是否电销：</label>
				 	<form:radiobuttons path="loanIsPhone" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		         </td>
		 </tr>
     </table>
     <div  class="tright pr30 pb5">
            <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
 </div>
 </div>
</form:form> 
   <div class="box5"> 
   <table class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
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
      	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
        <tr>
          <td>${status.count}</td>
          <td>${item.customerName}</td>
          <td>${item.storeName}</td>
          <td>${item.planArrivalTime}</td>
          <td>${item.vehicleBrandModel}</td>
          <td>${item.offendSalesName}</td>
          <td>${item.loanTeamEmpName}</td>
          <td>${item.dictOperStatus}</td>
          <td>${item.loanIsPhone}</td>
          <td>
              <input type="button" value="办理" class="btn_edit"  onclick="window.location='${ctx}/bpm/flowController/openForm?applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"></input>
          </td>           
      </tr>  
      </c:forEach>       
      </c:if>
      <c:if test="${ itemList==null || fn:length(itemList)==0}">
        <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
      </c:if>
     </table>
   </div>
<div class="pagination">${page}</div>
</body>
</html>