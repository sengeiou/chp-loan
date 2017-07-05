<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>展期评估报告录入待办列表</title>
<meta name="decorator" content="default" />

<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
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
		 $("select").trigger("change");
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/car/carExtendWorkItems/fetchTaskItems/extendAppraiser");
		$("#inputForm").submit();
		return false;
}   
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carExtendFlowQueryView" action="${ctx}/car/carExtendWorkItems/fetchTaskItems/extendAppraiser" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
     <input name="menuId" type="hidden" value="${param.menuId}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
             <td><label class="lab">合同编号：</label>
                <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
             <td><label class="lab">产品类型：</label>
             	<form:select id="productType" path="borrowProductCode" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
                           <form:option value="${product_type.productCode}">${product_type.productName}</form:option>
                     </c:forEach> 
		         </form:select>
             </td>
         </tr>
         <tr>
         	<td><label class="lab">借款期限：</label>
					<form:select
							id="loanMonths" path="loanMonths" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.key}">${product_type.value}</form:option>
							</c:forEach>
						</form:select>
				</td>
			<td><label class="lab">门店名称：</label> 
					<input id="txtStore" name="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore">
               </td>	
               <td><label class="lab">申请状态：</label>
	           		<form:select path="applyStatusCode" class="select180">
	                	<option value="">请选择</option>
						<form:options items="${fns:getDictList('jk_car_loan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</form:select>
				</td>
         </tr>
     </table>
     <div  class="tright pr30 pb5">
            <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
 </div>
 </div>
</form:form>
<sys:columnCtrl pageToken="carAppraiserList"></sys:columnCtrl> 
<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
	
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th>序号</th>
        <th>展期合同编号</th>
		<th>原合同编号</th>
		<th>客户姓名</th>
		<th>门店名称</th>
		<th>申请金额（元）</th>
		<th>借款期限</th>
		<th>产品类型</th>
		<th>申请状态</th>
		<th>展期申请</th>
		<th>已展期次数</th>
		<th>车牌号码</th>
		<th>批借金额</th>
		<th>降额</th>
		<th>合同到期提醒</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
        <tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.originalContractCode}</td>
          <td>${item.customerName}</td>
          <td>${item.storeName}</td>
          <td><fmt:formatNumber value='${item.loanApplyAmount}' pattern='##0.00' /></td>
          <td>${item.loanMonths}</td>
          <td>${item.borrowProductName}</td>
          <td>${item.applyStatusCode }</td>
          <td>${item.extensionFlag }</td>
          <td>${item.extendNumber}</td>
          <td>${item.plateNumbers}</td>
          <td><fmt:formatNumber value='${item.originalAuditAmount}' pattern='##0.00' /></td>
          <td><fmt:formatNumber value='${item.derate}' pattern='##0.00' /></td>
          <td><fmt:formatDate value='${item.contractExpirationDate}' pattern="yyyy-MM-dd"/></td>
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