<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title>车借待确认签署列表</title>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
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
		 $("#hdStore").val('');
		 $("select").trigger("change");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 
	 /**
	  * 弹出车借历史界面
	  * loanCode：借款申请表loanCode
	  */
	 function showCarLoanHis(loanCode){
	  		var url=ctx + "/common/carHistory/showLoanHisByLoanCode?loanCode="+loanCode;
	 	    art.dialog.open(url, {  
	 		   id: 'his',
	 		   title: '车借信息历史',
	 		   lock:true,
	 		   width:700,
	 		   height:350
	 		},false);  
	 }

 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/car/carContract/waitSign/contractWaitSign");
		$("#inputForm").submit();
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
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanFlowQueryParam" method="post" class="form-horizontal">
 	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
 	<input name="menuId" type="hidden" value="${param.menuId}" />
     <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="customerName" class="input_text178"/>
             </td>
             <td>			
				<label class="lab">门店名称：</label> 
				<input id="txtStore" name="storeName"
					type="text" maxlength="20" class="txt date input_text178"
					value="${secret.store }" readonly="true"/> 
				<i id="selectStoresBtn"
				class="icon-search" style="cursor: pointer;"></i>
				<input type="hidden" id="hdStore">
		     </td>
             <td><label class="lab">产品类型：</label>
             	<form:select id="productType" path="auditBorrowProductCode" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_car_loan_product_type')}" var="product_type">
		                             <form:option value="${product_type.value}">${product_type.label}</form:option>
		            </c:forEach>  
		         </form:select>
             </td>
         </tr>
         <tr>
            <td><label class="lab">合同编号：</label>
            <form:input path="contractCode"  class="input_text178"/></td>
            <td><label class="lab">借款期限：</label>
	           	<form:select id="loanMonths" path="auditLoanMonths" class="select180">
	                	<option value="">请选择</option>
						<c:forEach items="${fncjk:getPrdMonthsByType('products_type_car_credit')}" var="product_type">
								<form:option value="${product_type.key}">${product_type.value}</form:option>
							</c:forEach>
				</form:select>
			</td>
         </tr>
     </table>
     <div  class="tright pr30 pb5">
                                    <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                                    <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
     <div class="xiala" style="text-align:center;">
	  <img src="${context}/static/images/up.png" id="showMore"></img>
    </div>
 </div>
 </div>
</form:form> 
<sys:columnCtrl pageToken="carContractProductionList"></sys:columnCtrl>
<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
   <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th>序号</th>
		<th>合同编号</th>
		<th>客户姓名</th>
		<th>共借人姓名</th>
		<th>门店名称</th>
		<th>合同金额</th>
		<th>综合服务费</th>
		<th>借款期限</th>
		<th>批借金额</th>
		<th>产品类型</th>
		<th>借款状态</th>
		<th>车牌号码</th>
		<th>渠道</th>
		<th class="jkcj_lendCarPendingReview_history">操作</th>
      </tr>
      </thead>
      	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
       	<c:forEach items="${itemList}" var="item" varStatus="status"> 
        <tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.customerName}</td>
          <td>${item.coborrowerName}</td>
          <td>${item.storeName}</td>
          <td>${item.auditAmount}</td>
          <td>${item.comprehensiveServiceFee}</td>
          <td>${item.auditLoanMonths}</td>
          <td>${item.auditAmount}</td>
          <td><c:choose>
             	<c:when test="${item.auditBorrowProductCode !=null&&item.auditBorrowProductCode !=''}">
             		${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.auditBorrowProductCode)}
             	</c:when>
             	<c:otherwise>
             		${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.borrowProductCode)}
             	</c:otherwise>
             </c:choose></td>
          <td>待确认签署</td>
          <td>${item.plateNumbers}</td>
          <td>${item.loanFlag}</td>
          <td>
              &nbsp;<input type="button" value="历史" onclick="showCarLoanHis('${item.loanCode}')" class="btn_edit jkcj_lendCarPendingReview_history"></input>
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