<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退款审核已办列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 page(1,${page.pageSize});
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
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/carRefundCheckDoneList");
      $("#inputForm").submit();
      return false;
  }  
</script>
</head>
<body>
 <div class="control-group">
 <form:form id="inputForm" modelAttribute="carLoanStatusHisEx" method="post" class="form-horizontal">
     <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
      <input name="menuId" type="hidden" value="${param.menuId}" />
	 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
     <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
         <tr>
             <td><label class="lab">客户姓名：</label>
             	<form:input id="customerName" path="loanCustomerName" class="input_text178"/>
             </td>
             <td>			
				<label class="lab">门店：</label> 
				<form:input id="txtStore" path="storeName"
					type="text" maxlength="20" class="txt date input_text178"
					 readonly="true"/> 
				<i id="selectStoresBtn"
				class="icon-search" style="cursor: pointer;"></i>
				<input type="hidden" id="hdStore" />
		     </td>
		     <td>
		 		<label class="lab">回盘结果：</label>
		 		<form:select id="dictDealStatus" path="dictDealStatus" class="select180">
                   <option value="">请选择</option>
					<c:forEach items="${fns:getDictList('jk_counteroffer_result')}" var="dictDealStatus">
		                             <form:option value="${dictDealStatus.value}">${dictDealStatus.label}</form:option>
		            </c:forEach>  
		         </form:select>
		 	</td>
		 </tr>
		 <tr>
		 	<td><label class="lab">合同编号：</label>
             	<form:input id="contractCode" path="contractCode" class="input_text178"/>
             </td>
             <td><sys:multipleBank bankClick="selectBankBtn"
							bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
						<label class="lab">开户行：</label><form:input type="text"
							id="search_applyBankName" name="search_applyBankName"
							class="input_text178" path="midBankName"></form:input> <i
						id="selectBankBtn" class="icon-search" style="cursor: pointer;"></i>
			</td>
             <td colspan="2"><label class="lab">是否电销：</label>
				 	<form:radiobuttons path="telesalesFlag" items="${fns:getDictList('jk_telemarketing')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
		     </td>
		 </tr>
     </table>
     <div  class="tright pr30 pb5">
                                    <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                                    <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
    
 </div>
 </div>
</form:form> 
<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
	<sys:columnCtrl pageToken="carDisCardList"></sys:columnCtrl>
   <table id="tableList" class="table table-bordered table-condensed table-hover " style="margin-bottom:100px;">
   <thead>
      <tr>
        <th></th>
        <th>序号</th>
		<th>合同编号</th>
		<th>门店名称</th>
		<th>客户姓名</th>
		<th>借款产品</th>
		<th>合同金额</th>
		<th>应划扣金额</th>
		<th>实划扣金额</th>
		<th>批借期限(天)</th>
		<th>开户行</th>
		<th>借款状态</th>
		<th>回盘结果</th>
		<th>回盘原因</th>
		<th>是否电销</th>
		<th>操作</th>
      </tr>
      </thead>
      	<c:if test="${ page.list != null && fn:length(page.list)>0}">
       	<c:forEach items="${page.list}" var="item" varStatus="status"> 
        <tr>
          <td><input type="checkbox" name="selectCheckBox" /></td>
          <td>${status.count}</td>
          <td>${item.contractCode}</td>
          <td>${item.storeName}</td>
          <td>${item.loanCustomerName}</td>
          <td>${item.finalProductType}</td>
          <td><fmt:formatNumber value="${item.finalAuditAmount}" type="number" pattern="0.00"/></td>
          <td><fmt:formatNumber value="${item.urgeMoeny}" type="number" pattern="0.00"/></td>
          <td><fmt:formatNumber value="${item.urgeDecuteMoeny}" type="number" pattern="0.00"/></td>
          <td>${item.contractMonths}</td>
          <td>${item.midBankName}</td>
          <td>${item.loanStatusCode}</td>
          <td>${item.auditStatus}</td>
          <td>${item.auditRefuseReason}</td>
          <td>${item.telesalesFlag}</td>
          <td>
              <input type="button" value="历史" class="btn_edit"  onclick="showCarLoanHis('${item.loanCode}')"></input>
          </td>           
      </tr>  
      </c:forEach>       
      </c:if>
      <c:if test="${ page.list==null || fn:length(page.list)==0}">
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