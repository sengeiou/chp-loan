<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退款终审列表</title>
<script src="${context}/js/refund/endList.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#refundForm").attr("action", "${ctx}/borrow/refund/longRefund/endList");
	$("#refundForm").submit();
	return false;
}
loan.getstorelsit("txtStore","hdStore");
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post"  id="refundForm" modelAttribute="GrantUrgeBackEx" >
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="id" name="id" type="hidden">
            <tr>
               <td><label class="lab">客户姓名：</label>
               	<input type="text" class="input_text178" id="customerName" name="customerName" value="${search.customerName}"></input></td>
               <td><label class="lab">合同编号：</label>
               	<input type="text" class="input_text178" id="contractCode" name="contractCode" value="${search.contractCode}"></input></td>
               <td>
                <label class="lab">门店：</label>
                			<input id="mendianName" name="mendianName" type="text" maxlength="20" class="txt date input_text178" value="${search.mendianName }" readonly/> 
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" id="mendianId" name="mendianId" value="${search.mendianId }">
				</td>
            </tr>
            <tr>
                <td>
                <label class="lab">借款状态：</label>
                	<select class="select180" id="loanStatus" name="loanStatus"><option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_loan_status')}" var="status">
										<option value="${status.value}" <c:if test="${status.value eq search.loanStatus}">selected</c:if>>${status.label}</option>
						 </c:forEach>
			    	</select>
                </td>
				<td>
                <label class="lab">申请状态：</label>
                	<select class="select180" id="appStatus" name="appStatus"><option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_app_status')}" var="status">
										<option value="${status.value}" <c:if test="${status.value eq search.appStatus}">selected</c:if>>${status.label}</option>
						 </c:forEach>
			   	 	</select>
                </td>
                <td><label class="lab">申请日期：</label>
                	<input id="startTime" name="startTime"  value="${search.startTime}" type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>-<input id="endTime" name="endTime" 
                 value="${search.endTime}"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input></td>
            </tr>
			<tr id="T1" style="display:none">
				
				<td><label class="lab">退款类别：</label>
					<select class="select180" id="appType" name="appType">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_app_type')}" var="type">
								<option value="${type.value}" <c:if test="${type.value eq search.appType}">selected</c:if>>${type.label}</option>
				 		</c:forEach>
				</select></td>
				<td>
                <label class="lab">渠道：</label>
                	<select class="select180" id="loanFlag" name="loanFlag"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="status">
								<option value="${status.value}" <c:if test="${status.value eq search.loanFlag}">selected</c:if>>${status.label}</option>
				 </c:forEach>
			    </select>
                </td>
                <td>
                <label class="lab">模式：</label>
                	<select class="select180" id="model" name="model"><option value="">请选择</option>
			    <c:forEach items="${fns:getDictList('jk_loan_model')}" var="status">
								<option value="${status.value}" <c:if test="${status.value eq search.model}">selected</c:if>>${status.label}</option>
				 </c:forEach>
			    </select>
                </td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input class="btn btn-primary" type="submit" value="搜索"></input>
              <button class="btn btn-primary" id="clearBtn" onclick="clear1();">清除</button>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	      <img src="../../../../static/images/up.png" id="showMore"></img>
	     </div>
		</div>
	</form:form>
	   </div>
		<p class="mb5">
		<button class="btn btn-small" id="daoBtn" onclick="operate('1');">批量通过</button>
		<button class="btn btn-small" id="daoBtn" onclick="operate('0');">批量退回</button>
		</p>
		<div class="box5" style="overflow: auto; width: 100%;">
        <table id="tableList" >
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th data-field="realLoanStatus" data-visible="false" data-switchable="false" >借款实际状态</th>
            <th>门店</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>借款金额</th>
            <th>借款状态</th>
            <th>申请退款金额</th>
			<th>申请日期</th>
			<th>退款类别</th>
			<th>开户行</th>
			<th>退款银行</th>
            <th>申请状态</th>
			<th>渠道</th>
			<th>模式</th>
			<th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${items!=null && fn:length(items.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${items.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td>
             <input type="checkbox" name="checkBoxItem" value='${item.id}_${item.mt}_${item.loanStatus}'/>
             </td>
             <td>${item.realLoanStatus}</td>
             <td>${item.mendianName}</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td><fmt:formatNumber value='${item.loanMoney}' pattern="#,##0.00"/></td>
             <td>${item.loanStatusName}</td>
             <td><fmt:formatNumber value='${item.refundMoney}' pattern="#,##0.00"/></td>
             <td><fmt:formatDate value="${item.createTimes}" type="date" /></td>
             <td>${item.appTypeName}</td>
             <td>${item.bankName}</td>
             <td>${item.refundBank}</td>
             <td>${item.appStatusName}<c:if test="${item.appStatus==null }">待退款</c:if></td>
			 <td>${item.loanFlagLabel}</td>
        	 <td>${item.modelLabel}</td>
             <td>
             <c:if test="${item.appStatus=='2'}">
             <button class="btn_edit" onclick="refundExamine('${item.id}','${item.contractCode}','${item.appType}','${item.mt}','${item.realLoanStatus }');" id="backBtn">审核</button>
             </c:if>
             <c:if test="${item.appStatus!='2'}">
             <button class="btn_edit" onclick="openView('${item.id}','${item.contractCode}','${item.appType}');" id="backBtn">查看</button>
             </c:if>
             <input type="button" class="btn_edit" onclick="showPaybackHistory('${item.contractCode}');" name="historyBtn" value="历史" />
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
     </tbody>
    </table>
	</div>
     <div class="pagination">${items}</div> 
     <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			striped: true,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>