<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>还款申请明细表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/applyList.js?time=2211"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
</head>
<body>
	<div class="control-group">
		<form id="applyPaybackForm" method="post" action="${ctx}/borrow/payback/applyList/applyList">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="ids" name="ids" type="hidden" value="" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<div>
					<tr>
						<td><label class="lab">合同编号：</label> <input type="text"
							name="contractCode" class="input-medium"
							value="${paybackApply.contractCode }" /></td>
						<td><label class="lab">门店：</label> <input readonly="readonly" id="txtStore" name="orgName" type="text" maxlength="20"
							class="txt date input_text178" value="${paybackApply.orgName}" />
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i> 
							<input type="hidden" id="hdStore" name="orgCode" value="${paybackApply.orgCode}"></td>
						</td>
						<td><label class="lab">客户姓名：</label> 
						<input type="text" name="customerName" class="input-medium"
							value="${paybackApply.customerName }" /></td>
						
					</tr>
					<tr>
						<td><label class="lab">划扣日期：</label> <input
								name="enumOne" type="text" readonly="readonly" maxlength="40"
								class="input-mini Wdate"
								value="${paybackApply.enumOne}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />-<input
								name="enumTwo" type="text" readonly="readonly" maxlength="40"
								class="input-mini Wdate"
								value="${paybackApply.enumTwo}"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" /></td>
						<td><label class="lab">查账/划扣申请人：</label> <input readonly="readonly" id="txtUser" name="createName" type="text" maxlength="20"
							class="txt date input_text178" value="${paybackApply.createName}" />
							<i id="selectUserBtn" class="icon-search" style="cursor: pointer;"></i> 
							<input type="hidden" id="hdUser" name="createBy" value="${paybackApply.createBy}"></td>
						</td>
						<td><label class="lab">到账账户：</label> 
						<input type="text" name="bank" class="input-medium"
							value="${paybackApply.bank }" /></td>
					</tr>
					
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="btn-submit" value="搜索"> <input
				type="button" class="btn btn-primary" id="clearBtn" value="清除" />
		</div>
	</div>
	<p class="mb5">
			<button class="btn btn-small" id="exportExcel">导出Excel</button>
	</p>
	<div id="applyList">
		<form id="applyForm" method="post">
			<table id="applyTab">
				<thead>
					<tr height="37px">
						<th data-field="state" data-checkbox="true"></th>
						<th>序号</th>
						<th>门店名称</th>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>实还金额</th>
						<th>还款方式</th>
						<th>到账账户</th>
						<th>划扣日期</th>
						<th>账单日</th>
						<th>首期还款日</th>
						<th>借款期数</th>
						<th>查账/划扣申请人</th>
						<th>申请人员工编号</th>
						<th>渠道</th>
						
					</tr>
				</thead>
				<tbody id="dataList">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>
				           <input type="checkbox" name="checkBox"   value="${item.id}" />
				        </td>
						<td>${status.count}</td>
						<td>${item.orgName}</td>
						<td>${item.contractCode}</td>
						<td>${item.loanCustomer.customerName}</td>
						<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="#,##0.00" /></td>
						<td>${item.dictRepayMethod }</td>
						<td>${item.middlePerson.midBankName }</td>
						<td><fmt:formatDate value="${item.modifyTime }" type="date" /></td>
						<td>${item.payback.paybackDay }</td>
						<td><fmt:formatDate value="${item.contract.contractReplayDay }" type="date" /></td>
						<td>${item.contract.contractMonths }</td>
						<td>${item.createName }</td>
						<td>${item.createBy }</td>
						<td>${item.contract.channelFlag}</td>
						
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${page}</div>
</body>
</html>