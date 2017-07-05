<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script src="${context}/js/car/refund/refundAudit.js" type="text/javascript"></script>	
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#inputForm").attr("action", "${ctx}/car/refund/refundAudit/refundAuditJump");
	$("#inputForm").submit();
	return false;
}
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post" id="inputForm" modelAttribute="carRefundInfo">
       	 <input id="deductsAmountHide" type="hidden" name="deductsAmount" value="${deductsAmount}">
       	 <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
               <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="loanCustomerName"></form:input></td>
               <td><label class="lab">门店：</label> 
					<input id="txtStore" name="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore">
               </td>
               <td><label class="lab">回盘结果：</label><form:select class="select180" path="auditStatus">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('car_refund_status')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
						</form:select></td>
            </tr>
            <tr>
				<td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>		
				<td><label class="lab">开户行：</label>
						<form:select class="select180" path="cardBank">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_open_bank')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 		</c:forEach>
						</form:select>
				</td>
				<td><label class="lab">是否电销：</label><form:select class="select180" path="customerTelesalesFlag"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
				 </c:forEach>
			    </form:select></td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        
		</div>
	</form:form>
	   </div>
		<p class="mb5">
    	<span class="red">应划扣累计金额：</span><span class="red" id="deductAmount"><fmt:formatNumber value='${deductsAmount}' pattern="#,##0.00"/>
    	</span>元
		</p>
		<sys:columnCtrl pageToken="carReAuditList"></sys:columnCtrl>
		<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"">
        <table id="tableList" class="table  table-bordered table-condensed table-hover "  style="margin-bottom: 200px;">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
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
        <tbody>
         <c:if test="${ refundList!=null && fn:length(refundList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${refundList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" urgeMoeny="${item.urgeMoeny}"/>
             </td>
             <td>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.storeName}</td>
             <td>${item.loanCustomerName}</td>
             <td>${item.productType}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeDecuteMoeny}' pattern="#,#00.00"/></td>
             <td>${item.contractMonths}</td>
             <td>${item.cardBank}</td>
             <td>${item.dictLoanStatus}</td>
             <td>${item.auditStatus}</td>
             <td>${item.auditRefuseReason}</td>
             <td>${item.customerTelesalesFlag}</td>      
             <td>
             	<button class="btn_edit" name="historyBtn" value='${item.applyId}' onclick="showCarLoanHis('${item.loanCode}')">历史</button>
             	<c:if test="${item.auditStatus=='待审批'}">
             	<button class="btn_edit" value='${item.id}' name="jumpTo">审批</button>
             	</c:if>
             	<input type="hidden" id="cardNo${item.id}" value="${item.bankCardNo}">
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ refundList==null || fn:length(refundList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
	</div>
     <div class="pagination">${refundList}</div> 
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