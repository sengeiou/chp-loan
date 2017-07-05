<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/matchingPayback.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src='${context}/js/bootstrap.autocomplete.js' ></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
</head>
<body>
	<div class="control-group">
		<form id="matchingForm" action="${ctx}/borrow/payback/matching/list" method ="post" >
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input type="hidden" id="msg" value="${message}">
			<input name="matchingTokenId" type="hidden" value="${paybackApply.matchingTokenId}" />
			<input name="matchingToken" type="hidden" value="${paybackApply.matchingToken}" />
			<input id="zhcz" name="zhcz" type="hidden" value="${zhcz}" />
			<input name="operateRole" type="hidden" value="${zhcz}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<div>
				<tr>
					<td><label class="lab">客户姓名：</label> <input type="text" name="loanCustomer.customerName" class="input-medium" value="${paybackApply.loanCustomer.customerName }"/></td>
					<td><label class="lab">合同编号：</label> <input type="text" name="contractCode" class="input-medium" value="${paybackApply.contractCode }"/></td>
					<td><label class="lab">申请日期：</label> 
						<input type="text" name="applyPayDay" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${paybackApply.applyPayDay}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
				</tr>
				<tr>
					<td><label class="lab">门店：</label> 
						<input id="txtStore" name="loanInfo.loanStoreOrgName" type="text" readonly="readonly" class="txt date input_text178" value="${paybackApply.loanInfo.loanStoreOrgName }"/> 
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" id="hdStore" name="loanInfo.loanStoreOrgId" value="${paybackApply.loanInfo.loanStoreOrgId }">
					</td>
					<td><label class="lab" style="text-align: right">来源系统：</label>
						<select name="loanInfo.dictSourceType" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${paybackApply.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                            </c:forEach>
						</select>
					</td>
					<td><label class="lab">渠道：</label> 
						<select class="select180" name="loanInfo.loanFlag">
                			<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${paybackApply.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                		</select>
					</td>
				</tr>
			</div>
			<div >
				<tr id="T1" Style="display:none">
					<td><label class="lab">还款日：</label> 
						<select name="payback.paybackDayNum" id="repaymentDate" class="select180">
							        <option value="">请选择</option>
							<c:forEach var="day" items="${dayList}">
									<option value="${day.billDay}"
									<c:if test="${paybackApply.payback.paybackDayNum ==day.billDay }">selected</c:if>>${day.billDay}</option>
							</c:forEach>
					   </select>
					</td>
					<td><label class="lab">存入账户：</label>
						<select class="select180" name="dictDepositAccount" id="dictDepositAccount">
							<option value="">请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
							<c:if test="${zhcz==1 }">
								<c:if test="${item.midBankName =='中和-中国工商银行' || item.midBankName =='中和-招商银行'}">
									<option value="${item.bankCardNo }" <c:if test="${paybackApply.dictDepositAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
								</c:if>
							</c:if>
							<c:if test="${zhcz!=1 }">
								<c:if test="${item.midBankName !='中和-中国工商银行' && item.midBankName !='中和-招商银行'}">
									<option value="${item.bankCardNo }" <c:if test="${paybackApply.dictDepositAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
								</c:if>
							</c:if>
							</c:forEach>
						</select>
					</td>
				</tr>
			</div>
			</table>
		</form>
		<div style="float:left;margin-left:45%;padding-top:10px">
			<img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
		</div>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索">
			<input type="button" class="btn btn-primary" id="matchingClearBtn" value="清除">
		</div>
	</div>
	<p class="mb5">
		<input id="batchMatchingBtn" type="button" class="btn btn-small" value="批量匹配" />
		<input id="batchBackMathcingBtn" type="button"  class="btn btn-small" value="批量匹配退回" />
		<c:if test="${zhcz==null || zhcz=='' }">
			<input id="startAutoMatching"  type="button" class="btn btn-small" value="开启自动匹配" />
			<input id="endAutoMatching"  type="button" class="btn btn-small" value="结束自动匹配" />
		</c:if>
		<input type="hidden" id="sysValue" value="${sys.sysValue}" />
	</p>
	<div id="matchingList">
		<form id="matchingInfoForm" action="${ctx}/borrow/payback/dealPayback/form" method="post">
		<input type="hidden" id = "matchingContractCode" name="contractCode">
		<input type="hidden" id = "applyId" name="id">
		<input name="zhcz" type="hidden" value="${zhcz}" />
		<table id="matchingTab">
			<thead>
				<tr height="37px">
					<th data-field="state" data-checkbox="true"></th>
					<th data-field="id" data-visible="false" data-switchable="false" class="hidden">ID</th>
					<th data-field="paybackId" data-visible="false" data-switchable="false" class="hidden">ID</th>
		            <th data-field="model" data-visible="false" data-switchable="false" class="hidden">模式</th>
					<th data-field="contractCode" >合同编号</th>
		            <th data-field="customerName" >客户姓名</th>
					<th data-field="loanStoreOrgId" >门店名称</th>
		            <th data-field="midBankName" >存入银行</th>
		            <th data-field="contractMonths" >批借期数</th>
		            <th data-field="contractReplayDay" >首期还款日</th>
		            <th data-field="applyPayDay" >查账申请日期</th>
		            <th data-field="dictPayStatus" >还款状态</th>
		            <th data-field="contractAmount" >合同金额</th>
		            <th data-field="paybackMonthAmount" >期供金额</th>
		            <th data-field="applyAmount" >申请还款金额</th>
		            <th data-field="paybackDay" >还款日</th>
		            <th data-field="dictLoanStatus" >借款状态</th>
		            <th data-field="paybackBuleAmount" >蓝补金额</th>
		            <th data-field="loanFlag" >渠道</th>
		            <th data-field="modelLabel" >模式</th>
		            <th>操作</th>
				</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${paybackApplyList.list}" var="item">
						<tr>
							<td></td>
							<td>${item.id}</td>
							<td>${item.payback.id}</td>
							<td>${item.loanInfo.model}</td>
							<td>${item.contractCode}</td>
							<td>${item.loanCustomer.customerName}</td>
							<td>${item.loanInfo.loanStoreOrgName}</td>
							<td>${item.middlePerson.midBankName}</td>
							<td>${item.contract.contractMonths}</td>
							<td><fmt:formatDate value="${item.contract.contractReplayDay}" type="date"/></td>
							<td><fmt:formatDate value="${item.applyPayDay}" type="date"/></td>
							<td>${item.payback.dictPayStatusLabel}</td>
							<td><fmt:formatNumber value='${item.contract.contractAmount}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.payback.paybackMonthAmount}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.applyAmount}' pattern="#,##0.00"/></td>
							<td>${item.payback.paybackDay}</td>
							<td>${item.loanInfo.dictLoanStatusLabel}</td>
							<td><fmt:formatNumber value='${item.payback.paybackBuleAmount}' pattern="#,##0.00"/></td>
							<td width="41">${item.loanInfo.loanFlagLabel}</td>
							<td width="41">${item.loanInfo.modelLabel}</td>
							<td><input type="button" class="btn_edit" value="办理"  name="matchingInfoBtn" matchingId="${item.id}" contractCode = "${item.contractCode }" ></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${paybackApplyList}</div>
</body>
</html>