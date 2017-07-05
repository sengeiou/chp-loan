<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资产家大金融业务列表</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/zcj/financeInfoList.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#zcjForm").attr("action", "${ctx}/borrow/zcj/getFinanceInfo");
	$("#zcjForm").submit();
	return false;
}
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form  method="post" id="zcjForm">
         <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
               <td><label class="lab">推介日期：</label>
               <input id="pushDate" name="pushDate"  readonly="true"
                 value="${zcj.pushDate}"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
               </td>
               <td><label class="lab">客户姓名：</label>
               <input type="text" class="input_text178" id="loanCustomerName" name="loanCustomerName" value="${zcj.loanCustomerName}"></td>
               <td><label class="lab">门店：</label>
				    <input type="text" id="orgName" class="input_text178" name="orgName" readonly="true" value="${zcj.orgName}"></input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <input type="hidden" name="storeId" id="storeId" value="${zcj.storeId}"/>
				</td>
            </tr>
            <tr>
                <td>
               	 	<label class="lab">借款状态：</label>
                	<select class="select180" id="loanStatus" name="loanStatus">
                	<option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_loan_apply_status')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq zcj.loanStatus}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
				</td>
				<td>
					<label class="lab">放款日期：</label>
                   <input id="lendingTime" name="lendingTime"  readonly="true"
                 value="${zcj.lendingTime}"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
               </td>
               <td>
					<label class="lab">是否无纸化：</label>
                   <select class="select180" id="paperlessFlag" name="paperlessFlag">
                		<option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('yes_no')}" var="yn">
							<option value="${yn.value}" <c:if test="${yn.value eq zcj.paperlessFlag}">selected</c:if>>${yn.label}</option>
					 	</c:forEach>
				    </select>
               </td>
            </tr>
			<tr >
                <td>
               	 	<label class="lab">是否有保证人：</label>
                	<select class="select180" id="legalMan" name="legalMan">
                		<option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('yes_no')}" var="yn">
							<option value="${yn.value}" <c:if test="${yn.value eq zcj.legalMan}">selected</c:if>>${yn.label}</option>
					 	</c:forEach>
				    </select>
                </td>
                <td>
					<label class="lab">合同版本号：</label>
                   <select class="select180" id="contractVersion" name="contractVersion">
                		<option value="">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="ver">
							<option value="${ver.value}" <c:if test="${ver.value eq zcj.contractVersion}">selected</c:if>>${ver.label}</option>
					 	</c:forEach>
				    </select>
               </td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input class="btn btn-primary" type="submit" value="搜索"></input>
              <input class="btn btn-primary" type="button" onClick="javaScript:clear1();" value="清除"></input>
		</div>
	</form>
	   </div>
		<p class="mb5">
		&nbsp;&nbsp;<label class="lab1">放款总笔数：</label><label id = "count"
			class="red">${urgeList.count }</label><label class="lab1">笔</label><input type="hidden" id="hiddenCount" value="${urgeList.count }" />
			&nbsp;&nbsp;<label class="lab1">放款总金额：</label><label id = "grant"
		class="red">${amountSum.grantAmountSum }</label> <label class="lab1">元</label><input type="hidden" id="hiddenGrant" value="${amountSum.grantAmountSum }" />
		&nbsp;&nbsp;<label class="lab1">批借总金额：</label><label id = "audit"
		class="red">${amountSum.loanAuditAmountSum }</label> <label class="lab1">元</label><input type="hidden" id="hiddenAudit" value="${amountSum.loanAuditAmountSum }" />
		&nbsp;&nbsp;<label class="lab1">合同总金额：</label><label id = "contract"
			class="red">${amountSum.contractAmountSum }</label><label class="lab1">元</label><input type="hidden" id="hiddenContract" value="${amountSum.contractAmountSum }" />
		</p>
		<sys:columnCtrl pageToken="grantdeductlist"></sys:columnCtrl>
		<div class="box5" >
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/>全选</th>
            <th>门店</th>
            <th>推介日期</th>
            <th>客户姓名</th>
            <th>身份证号</th>
            <th>开户行</th>
            <th>银行账号</th>
            <th>批借金额</th>
			<th>合同金额</th>
			<th>外访费</th>
			<th>加急费</th>
			<th>放款金额</th>
			<th>合同版本号</th>
			<th>期数</th>
			<th>状态</th>
			<th>放款日期</th>
			<th>渠道</th>
			<th>模式</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgeList!=null && fn:length(urgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" cid='${item.id }' contractAmount = '${item.contractAmount}'
                                       grantAmount='${item.grantAmount}' auditAmount='${item.loanAuditAmount}'/>${index}</td>
             <td>${item.orgName }</td>
             <td>${item.pushDate}</td>
             <td>${item.loanCustomerName }</td>
             <td>${item.customerCertNum}</td>
             <td>${item.bankName}</td>
             <td>${item.bankAccount}</td>
             <td><fmt:formatNumber value='${item.loanAuditAmount}' pattern="#,##0.00"/></td>
             <td><fmt:formatNumber value="${item.contractAmount}" pattern="#,##0.00" /></td>
             <td><fmt:formatNumber value="${item.feePetition}" pattern="#,##0.00" /></td>
             <td><fmt:formatNumber value="${item.feeExpedited}" pattern="#,##0.00" /></td>
             <td><fmt:formatNumber value="${item.grantAmount}" pattern="#,##0.00" /></td>
             <td>${item.contractVersion}</td>
             <td>${item.contractMonths}</td>
             <td>${item.loanStatus}<c:if
								test="${item.frozenFlag == '1' }">
	             	(门店申请冻结)
	             </c:if></td>
             <td>${item.lendingTime}</td>
             <td>${item.loanFlag}</td>
             <td>${item.model}</td>
             <td>
             <input class="btn_edit zcj_financeinfo_his" onClick="javaScript:showAllHisByLoanCode('${item.loanCode}');" value="历史" type="button"></td>
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

</body>
</html>