<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/zhrefund.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${ctxStatic}/jquery-validation/1.11.0/lib/jquery.form.js" ></script>
</head>
<body>
	<div class="control-group">
		<form id="refundForm" action="${ctx}/borrow/payback/zhrefund/list" method ="post" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input name="matchingTokenId" type="hidden" value="${paybackApply.matchingTokenId}" />
			<input name="matchingToken" type="hidden" value="${paybackApply.matchingToken}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<div>
				<tr>
					<td><label class="lab">客户姓名：</label> <input type="text" name="customerName" class="input-medium" value="${zhrefund.customerName }"/></td>
					<td><label class="lab">合同编号：</label> <input type="text" name="contractCode" class="input-medium" value="${zhrefund.contractCode }"/></td>
					<td><label class="lab">门店：</label> 
						<input id="txtStore" name="storeName" type="text" readonly="readonly" class="txt date input_text178" value="${zhrefund.storeName }"/> 
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="storeId" value="${zhrefund.storeId }">
					</td>
				</tr>
				<tr>
					<td><label class="lab" style="text-align: right">借款状态：</label>
						<select name="dictLoanStatus" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_status')}" var="card_type">
								 <option value="${card_type.value }" <c:if test="${zhrefund.dictLoanStatus == card_type.value }">selected</c:if>>${card_type.label }</option>
							</c:forEach>
						</select>
					</td>
					<td><label class="lab" style="text-align: right">状态：</label>
						<select name="zhrefundStatus" class="select180">
							<option value=''>请选择</option>
							<option value="1" <c:if test="${zhrefund.zhrefundStatus==1 }">selected</c:if>>生效</option>
							<option value="2" <c:if test="${zhrefund.zhrefundStatus==2 }">selected</c:if>>失效</option>
						</select>
					</td>
				</tr>
			</div>	
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索">
			<input type="button" class="btn btn-primary" id="clearBtn" value="清除">
		</div>
	</div>
	<p class="mb5">
		<button class="btn btn-small" id="importDate" data-target="#uploadAuditedModal" data-toggle="modal">导入excel</button>
		<button class="btn btn-small" id="invalidBtn">失效</button>   
	</p>
	<div id="refundList">
		<form id="refundForm" action="${ctx}/borrow/payback/zhrefund/list" method="post">
		<table id="refundTab">
			<thead>
				<tr height="37px">
					<th data-field="state" data-checkbox="true"></th>
					<th data-field="contractCode" >合同编号</th>
		            <th data-field="customerName" >客户姓名</th>
					<th data-field="storeName" >门店名称</th>
		            <th data-field="dictLoanStatus" >借款状态</th>
		            <th data-field="paybackBuleAmount" >蓝补金额</th>
		            <th data-field="paybackMonthAmount" >月还期供金额</th>
		            <th data-field="zhrefundStatus" >状态</th>
		            <th>操作</th>
				</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${pageList.list}" var="item"  varStatus="status">
						<tr>
							<td></td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td>${item.storeName}</td>
							<td id="loanStatus_${status.index }">${item.dictLoanStatusLabel}</td>
							<td><fmt:formatNumber value='${item.paybackBuleAmount}' pattern="#,##0.00"/></td>
							<td width="41"><fmt:formatNumber value="${item.paybackMonthAmount}" pattern='0.00'/></td>
							<td width="41">${item.zhrefundStatusLabel}</td>
							<td width="41">
								<input type="button" id="settleApplyBtn_${status.index }"
								<c:if test="${item.dictLoanStatusLabel!='逾期' }">disabled</c:if> value="结清申请"
								 onclick="settleApply('${item.contractCode}',${status.index })"/>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${pageList}</div>
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">导入中和数据</h4>
				</div>
				<div class="modal-body">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data" action="${ctx}/borrow/payback/zhrefund/importData" method="POST">
					<input id="file" name="file" type="file" style="display: none">
					<input id="photoCover" class="input_text178" type="text" /> 
					<a class="btn btn-small" onclick="$('input[id=file]').click();">选择文件</a>
					</div>
					<div class="modal-footer">
						<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
						<a type="button" class="btn btn-primary" data-dismiss="modal" id="uploadAuditedFile">确认</a>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>