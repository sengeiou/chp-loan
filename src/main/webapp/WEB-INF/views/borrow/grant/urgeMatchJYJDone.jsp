<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/grant/urgeCheckJYJDone.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src='${context}/js/bootstrap.autocomplete.js' ></script>
<script type="text/javascript" src="${context}/js/payback/dateUtils.js"></script>
<!-- <style type="text/css">
	.pagination {background:none;position:static;width:auto}
</style> -->
</head>
<body>
<!-- 催收服务费查账已办列表 -->
	<div class="control-group">
		<form:form id="urgeCheckDoneForm" action="${ctx}/urgeCheckDoneJYJ/urgeCheckDoneInfo" method ="post" modelAttribute="urgeCheckDoneEx">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input type="text" path="customerName" class="input-medium"></form:input></td>
					<td><label class="lab">合同编号：</label> <form:input type="text" path="contractCode" class="input-medium"></form:input></td>
					<td><label class="lab">查账金额：</label> <form:input type="text" path="urgeReallyAmountMin" id="urgeReallyAmountMin" class="input_text70"></form:input>
					-<form:input type="text" path="urgeReallyAmountMax" id="urgeReallyAmountMax" class="input_text70"></form:input> </td>
				</tr>
				<tr>
					<td><label class="lab">门店：</label> 
						<form:input id="txtStore" path="storeName" type="text" maxlength="20" class="txt date input_text178"></form:input> 
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<form:input type="hidden" id="hdStore" path="orgId"></form:input>
					</td>
					<td><label class="lab">存入账户：</label>
						<form:select class="select180" path="bankCardNo">	
							<form:option value="">请选择</form:option>
							<c:forEach var="item" items="${middlePersonList }">
								<form:option value="${item.bankCardNo }">${item.midBankName }</form:option>
							</c:forEach>
						</form:select>
					</td>
					<td><label class="lab">渠道：</label> 
						<form:select class="select180" path="loanFlag">
                			<form:option value="">请选择</form:option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <form:option value="${loanMark.value }">${loanMark.label }</form:option>
                              </c:forEach>
                		</form:select>
					</td>
				</tr>
				
				<tr id="T1" Style="display:none">
				<td><label class="lab">退款标识：</label>
				    	<form:select class="select180" path="refundFlag">
					    	<option value=""> </option>
					    	<option value="1">退款</option>
					    </form:select>
				    </td>
					
					<td><label class="lab">回盘结果：</label>
						<form:select class="select180" id="urgeApplyStatus" path="urgeApplyStatus">
							<option value="">全部</option>
							<c:forEach var="item" items="${fns:getDictList('jk_urge_counteroffer_result')}">
							<c:if test="${item.label=='查账成功'||item.label=='查账退回'}">
								<form:option value="${item.value }">${item.label }</form:option>
							</c:if>
							</c:forEach>
						</form:select>
					</td>
					<td><label class="lab">查账日期：</label>
					<input  name="urgeApplyBeginDate" id="startDay" 
                 value="<fmt:formatDate value='${urgeCheckDoneEx.urgeApplyBeginDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDay\')}'})" style="cursor: pointer" ></input>-<input  name="urgeApplyEndDate" 
                 value="<fmt:formatDate value='${urgeCheckDoneEx.urgeApplyEndDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="endDay" 
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDay\')}'})" style="cursor: pointer" ></input></td>
					
				</tr>
				<tr id="T2" Style="display:none">
				 
				</tr>
			</div>
			</table>
		</form:form>
		<div style="float:left;margin-left:45%;padding-top:10px">
			<img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
		</div>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索">
			<button  class="btn btn-primary" id="clearBtn">清除</button>
		</div>
	</div>
	<div>
		<p class="mb5">
			<button class="btn btn-small" id="daoBtn">导出excel</button>
		</p>
	</div>
	<sys:columnCtrl pageToken="urgedone"></sys:columnCtrl>
	
	<div id="matchingList">
		<form id="matchingInfoForm" action="${ctx}/urgeCheckDoneJYJ/form" method="post">
		<input type="hidden" id = "matchingContractCode" name="contractCode">
		<input type="hidden" id = "applyId" name="checkApplyId">
		<div class="box5">
		<table id="tableList" class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr height="37px">
					<th><input type="checkbox" id="checkAll" onclick="selectAll();"/></th>
					<th>合同编号</th>
		            <th>客户姓名</th>
					<th>门店名称</th>
		            <th>存入账户</th>
		            <th>批借期数</th>
		            <th>合同金额</th>
		            <th>首次放款金额</th>
		            <th>费用总金额</th>
		            <th>前期综合服务费</th>
		            <th>外访费</th>
		            <th>加急费</th>
		            <th>待查账金额</th>
		            <th>申请查账金额</th>
		            <th>查账金额</th>
		            <th>查账日期</th>
		            <th>回盘结果</th>
		            <th>渠道</th>
		            <th>退款标识</th>
		            <th>操作</th>
				</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${waitPage.list}" var="item">
						<tr>
							<td><input type="checkbox" name="checkBoxItem" value='${item.checkApplyId}'/></td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td>${item.storeName}</td>
							<td>${item.midBankName}</td>
							<td>${item.contractMonths}</td>
							 
							<td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.firstGrantAmount}' pattern="#,##0.00"/> </td> <!-- 首次放款金额 -->
							<td><fmt:formatNumber value='${item.allFee}' pattern="#,##0.00"/> </td> <!-- 费用总金额 -->
							<td><fmt:formatNumber value='${item.feeCount}' pattern="#,##0.00"/> </td> <!-- 前期综合服务费 -->
							<td><fmt:formatNumber value='${item.feePetition}' pattern="#,##0.00"/> </td> <!-- 外访费 -->
							<td><fmt:formatNumber value='${item.feeExpedited}' pattern="#,##0.00"/> </td> <!-- 加急费 -->
							<td><fmt:formatNumber value='${item.urgeAmount}' pattern="#,##0.00"/></td><!-- 待查账金额 -->
							<td><fmt:formatNumber value='${item.urgeApplyAmount}' pattern="#,##0.00"/></td><!-- 申请查账金额 -->
							<td><fmt:formatNumber value='${item.urgeReallyAmount}' pattern="#,##0.00"/></td><!-- 实还金额 -->
							<td><fmt:formatDate value="${item.urgeApplyDate}" type="date"/></td>
							<td>${item.urgeApplyStatus}</td>
							<td width="41">${item.loanFlag}</td>
             				<td><c:if test="${item.refundFlag=='1'}">退款</c:if></td> 
							<td><input type="button" class="btn_edit" value="查看"  name="doneInfoBtn" matchingId="${item.checkApplyId}" contractCode = "${item.contractCode }" ">
								<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('','${item.checkApplyId}','');" value="历史" /></td>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${ waitPage.list==null || fn:length(waitPage.list)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</form>
		</div>
			</div>
	<div class="pagination">${waitPage}</div>
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