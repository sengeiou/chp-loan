<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackDone.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src='${context}/js/bootstrap.autocomplete.js' type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
</head>
<body>
	<div class="control-group">
		<form id="paybackDoneForm" action="${ctx}/borrow/payback/paybackDone/list" method="post">
			<input id="ids" name="ids" type="hidden" value=""></input>
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<div>
					<tr>
						<td><label class="lab">客户姓名：</label> <input type="text" name="loanCustomer.customerName" class="input-medium" value="${payback.loanCustomer.customerName }"/></td>
						<td><label class="lab">合同编号：</label> <input type="text" name="contractCode" class="input-medium" value="${payback.contractCode }"/></td>
						<td><label class="lab">门店：</label> 
							<input id="txtStore" name="loanInfo.loanStoreOrgName" type="text" maxlength="20" class="txt date input_text178" value="${payback.loanInfo.loanStoreOrgName }"/> 
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" id="hdStore" name="loanInfo.loanStoreOrgId" value="${payback.loanInfo.loanStoreOrgId }">
						</td>
					</tr>
					<tr>
						<td><label class="lab">来源系统：</label>
							<select name="loanInfo.dictSourceType" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${payback.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                            </c:forEach>
						</select>
                      </td>
						<td><label class="lab">结清时间：</label>
						<input name="beginDate"  type="text" readonly="readonly" maxlength="40" class="input-mini Wdate"
					     value="<fmt:formatDate value="${payback.beginDate}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-<input name="endDate"  type="text" readonly="readonly" maxlength="40" class="input-mini Wdate"
					     value="<fmt:formatDate value="${payback.endDate}" pattern="yyyy-MM-dd"/>"
					     onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</td>
						<td><label class="lab">渠道：</label> 
							<select class="select180" name="loanInfo.loanFlag">
               				<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${payback.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                			</select>
						</td>
					</tr>
					<tr>
						<td><label class="lab">模式：</label> 
							<select name="loanInfo.model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${payback.loanInfo.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                       </select>
	                     </td>
					</tr>
				</div>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" id="clearDeductBtn" value="清除">
	</div>
	</div>
	<p class="mb5">
		<button class="btn btn-small" onclick="selectAll(this)">全选</button>
		<button id="exportExcel" class="btn btn-small" >导出Excel</button>
	</p>
	<div id="confirmList" class="box5">
		<form id="confirmForm" method="post">
			<input type="hidden" id="confirmContractCode" name="contractCode">
			<table id="paybackDoneTab">
				<thead>
					<tr>
						<th></th>
						<th>序号</th>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>门店名称</th>
						<th>合同金额</th>
						<th>已收催收服务费金额</th>
						<th>放款金额</th>
						<th>批借期数</th>
						<th>首期还款日</th>
						<th>最长逾期天数</th>
						<th>未还违约金(滞纳金)及罚息总额</th>
						<th>结清日期</th>
						<th>还款状态</th>
						<!-- <th>借款状态</th> -->
						<th>减免金额</th>
						<th>渠道</th>
						<th>模式</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${paybackList.list}" var="item" varStatus="status">
						<tr>
							<td>
				            	<input type="checkbox" name="checkBox" value="${item.id}" />
				         	</td>
							<td>${status.count}</td>
							<td>${item.contractCode}</td>
							<td>${item.loanCustomer.customerName}</td>
							<td>${item.loanInfo.loanStoreOrgName}</td>
							<td>
							 <fmt:formatNumber value='${item.contract.contractAmount }' pattern="#,##0.00" /> 
							</td>
							<td>
							 <fmt:formatNumber value='${item.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00" />
							</td>
							<td>
							<fmt:formatNumber value='${item.loanGrant.grantAmount }' pattern="#,##0.00" />
							</td>
							<td>${item.contract.contractMonths }</td>
							<td><fmt:formatDate
									value="${item.contract.contractReplayDay}" type="date" /></td>
							<td>${item.paybackMaxOverduedays }</td>
							<td>
							<fmt:formatNumber value='${item.paybackMonth.penaltyInterest }' pattern="#,##0.00" />
							</td>
							<td><fmt:formatDate value="${item.modifyTime}" type="date" /></td>
							<td>${item.dictPayStatusLabel}</td>
							<%-- <td>${item.loanInfo.dictLoanStatus}</td> --%>
							<td>
							 <fmt:formatNumber value='${item.paybackMonth.creditAmount }' pattern="#,##0.00" />
							</td>
							<td>${item.loanInfo.loanFlagLabel}</td>
							<td>${item.loanInfo.modelLabel}</td>
							<td><input type="button" class="btn_edit"  value="查看"
								id="confirmInfoBtn" name="confirmInfoBtn"
								data-toggle="modal" data-target="#confirmModal"
								confirmContractCode="${item.contractCode}">
								<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('${item.id}','','3');" value="历史" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${paybackList}</div>
</body>
</html>