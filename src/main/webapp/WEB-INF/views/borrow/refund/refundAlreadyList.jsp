<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退款已办列表</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/refund/refund.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#refundForm").attr("action", "${ctx}/borrow/refund/longRefund/refundAlreadyList");
	$("#refundForm").submit();
	return false;
}
$(document).ready(
		function() {
			loan.getstorelsit("name","storeId");
		});
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post" modelAttribute="refund" id="refundForm">
         <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
               <td><label class="lab">客户姓名：</label>
               <form:input type="text" class="input_text178" path="customerName"></form:input></td>
               <td><label class="lab">合同编号：</label>
               <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
               <td><label class="lab">门店：</label>
				    <form:input type="text" id="name" class="input_text178" path="name" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="storeId" id="storeId"/>
				</td>
            </tr>
            <tr>
				<td>
               	 	<label class="lab">借款状态：</label>
                	<select class="select180" id="loanStatus" name="loanStatus">
                	<option value="-1">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_loan_status')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq refund.loanStatus}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
                </td>
                <td>
               	 	<label class="lab">申请状态：</label>
                	<select class="select180" id="appStatus" name="appStatus">
                	<option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_app_status')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq refund.appStatus}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
				</td>
                <td>
               	 	<label class="lab">退款类别：</label>
                	<select class="select180" id="appType" name="appType">
                		<option value="-1">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_app_type')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq refund.appType}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
                </td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input class="btn btn-primary" type="submit" value="搜索"></input>
              <button class="btn btn-primary" id="clearBtn">清除</button>
		</div>
	</form:form>
	   </div>
		<sys:columnCtrl pageToken="grantdeductlist"></sys:columnCtrl>
		<div class="box5" >
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>门店</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>借款金额</th>
            <th>借款状态</th>
            <th>蓝补金额/放款金额</th>
            <th>申请退款金额</th>
            <th>申请日期</th>
			<th>退款类别</th>
			<th>开户行</th>
			<th>退款银行</th>
			<th>申请状态</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgeList!=null && fn:length(urgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" cid='${item.id }'/></td>
             <td>${item.mendianName }</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName }</td>
             <td><fmt:formatNumber value='${item.loanMoney}' pattern="#,##0.00"/></td>
             <td>${item.loanStatusName}</td>
             <td><fmt:formatNumber value='${item.money}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value='${item.refundMoney}' pattern="#,#00.00"/></td>
             <td><fmt:formatDate value="${item.createTimes}" type="date" /></td>
             <td>${item.appTypeName}</td>
             <td>${item.bankName}</td>
             <td>${item.refundBank}</td>
             <td>${item.appStatusName}</td>
             <td>
             	<c:if test="${item.appStatus eq '1' || item.appStatus eq '7'}">
           			<input class="btn_edit" onclick="openView('${item.id}','${item.contractCode}','${item.appType}','edit')" value="修改" type="button">
            	</c:if>
             	<c:if test="${item.appStatus ne '1' && item.appStatus ne '7'}">
           			<input class="btn_edit" onclick="openView('${item.id}','${item.contractCode}','${item.appType}','show')" value="查看" type="button">
            	</c:if>
             <input class="btn_edit" onclick="showPaybackHistory('${item.contractCode}');" value="历史" type="button"></td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ urgeList==null || fn:length(urgeList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
	</div> 
	<div class="pagination">${urgeList}</div> 
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