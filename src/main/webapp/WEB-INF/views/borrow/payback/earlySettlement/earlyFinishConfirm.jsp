<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/earlyFinishConfirm.js?time=2211"></script>
<script type="text/javascript" src="${context}/js/payback/historicalRecords.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
</head>
<body>
	<div class="control-group">
		<form id="confirmPaybackForm" method="post" action="${ctx}/borrow/payback/earlyFinishConfirm/list">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input id="idVals" name="idVals" type="hidden" value="" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<div>
					<tr>
						<td><label class="lab">客户姓名：</label> 
						<input type="text" name="loanCustomer.customerName" class="input-medium"
							value="${paybackCharge.loanCustomer.customerName }" /></td>
						<td><label class="lab">合同编号：</label> <input type="text"
							name="contractCode" class="input-medium"
							value="${paybackCharge.contractCode }" /></td>
						<td><label class="lab">门店：</label> <input id="txtStore" name="store" type="text" maxlength="20"
							class="txt date input_text178" value="${paybackCharge.store}" />
							<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i> 
							<input type="hidden" id="hdStore" name="storeId" value="${paybackCharge.storeId}"></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">还款账单日：</label> <select name="payback.paybackDay"
							class="select180">
								<option value="">请选择</option>
								<c:forEach var="day" items="${dayList}">
									<option value="${day.billDay}"
										<c:if test="${paybackCharge.payback.paybackDay ==day.billDay }">selected</c:if>>${day.billDay}</option>
								</c:forEach>
						</select></td>
						<td><label class="lab">渠道：</label> <select class="select180"
							name="loanInfo.loanFlag">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
									<option value="${loanMark.value }"
										<c:if test="${paybackCharge.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
								</c:forEach>
						</select></td>
						<td><label class="lab">来源系统：</label> <select
							class="select180" name="loanInfo.dictSourceType">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
									<option value="${dictSourceType.value }"
										<c:if test="${paybackCharge.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
								</c:forEach>
						</select></td>
					</tr>
					<div>
						<tr id="searchConfirmPayback" style="display: none">
							<td>
						      <label class="lab">模式：</label>
						      	<select class="select180" name="loanInfo.model">
				                     <option value="">请选择</option>
						              <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
									    <option value="${flag.value }"
										<c:if test="${paybackCharge.loanInfo.model==flag.value }">selected</c:if>>
										<c:if test="${flag.value=='1' }">${flag.label}</c:if>
										<c:if test="${flag.value!='1' }">非TG</c:if>
										</option>
							   		</c:forEach>
			                    </select>
			                </td>
							<td><label class="lab">提前结清申请时间：</label> <input
								name="beginDate" type="text" readonly="readonly" maxlength="40"
								class="input-mini Wdate"
								value="<fmt:formatDate value="${paybackCharge.beginDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" />-<input
								name="endDate" type="text" readonly="readonly" maxlength="40"
								class="input-mini Wdate"
								value="<fmt:formatDate value="${paybackCharge.endDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});" /></td>
							<td><label class="lab">是否电销：</label>
								<select class="select180" name="loanCustomer.customerTelesalesFlag">
									<option value="">请选择</option>
									<c:forEach items="${fns:getDictList('jk_telemarketing')}"
										var="loanIsPhone">
										<option value="${loanIsPhone.value }"
											<c:if test="${paybackCharge.loanCustomer.customerTelesalesFlag== loanIsPhone.value }">selected</c:if>>${loanIsPhone.label }</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td><label class="lab">未还违约金罚息：</label> <input
								name="publishStart" type="text" maxlength="40" 
								class="input_text70" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" value="${paybackCharge.publishStart}" />-<input
								name="publishEnd" type="text" maxlength="40"
								class="input_text70" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" value="${paybackCharge.publishEnd}" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
						</tr>
					</div>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" id="btn-submit" value="搜索"> <input
				type="button" class="btn btn-primary" id="clearBtn" value="清除" />
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="${context }/static/images/up.png" id="showSearchConfirmPaybackBtn"></img>
			</div>
		</div>
	</div>
	<p class="mb5">
			<button class="btn btn-small" onclick="selectAll(this)">全选</button>
			<button class="btn btn-small" id="exportExcel">导出Excel</button>
	</p>
	<div id="confirmList">
		<form id="confirmForm" method="post">
			<input type="hidden" id="confirmContractCode" name="contractCode">
			<table id="earlyFinishTab">
				<thead>
					<tr height="37px">
						<th></th>
						<th>序号</th>
						<th>门店名称</th>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>合同金额</th>
						<th>已收催收服务费金额</th>
						<th>放款金额</th>
						<th>批借期数</th>
						<th>首期还款日</th>
						<th>最长逾期天数</th>
						<th>未还违约金罚息总额</th>
						<th>提前结清应还款总额</th>
						<th>申请还款金额</th>
						<th>还款账单日</th>
						<th>申请提前结清日期</th>
						<th>借款状态</th>
						<th>减免金额</th>
						<th>渠道</th>
						<th>模式</th>
						<th>是否电销</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${waitPage.list}" var="item" varStatus="status">
					<tr>
						<td>
				           <input type="checkbox" name="checkBox"   value="${item.id}" />
				        </td>
						<td>${status.count}</td>
						<td>${item.loanInfo.loanStoreOrgName}</td>
						<td>${item.contractCode}</td>
						<td>${item.loanCustomer.customerName}</td>
						<td><fmt:formatNumber value='${item.contract.contractAmount}' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00" />
							<c:if test="${item.urgeServicesMoney.urgeDecuteMoeny == null }">0.00</c:if>
						</td>
						<td><fmt:formatNumber value='${item.loanGrant.grantAmount }' pattern="#,##0.00" /></td>
						<td>${item.contract.contractMonths }</td>
						<td><fmt:formatDate value="${item.contract.contractReplayDay}" type="date" /></td>
						<td>${item.payback.paybackMaxOverduedays }</td>
						<td><fmt:formatNumber value='${item.penaltyTotalAmount }' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.settleTotalAmount+item.penaltyTotalAmount }' pattern="#,##0.00" /></td>
						<td><fmt:formatNumber value='${item.applyBuleAmount }' pattern="#,##0.00" /></td>
						<td>${item.payback.paybackDay}</td>
						<td><fmt:formatDate value="${item.createTime}" type="date" /></td>
						<td>${item.loanInfo.dictLoanStatusLabel}</td>
						<td><fmt:formatNumber value='${(item.settleTotalAmount+item.penaltyTotalAmount-item.payback.paybackBackAmount) lt 0?0:(item.settleTotalAmount+item.penaltyTotalAmount-item.payback.paybackBackAmount)}' pattern="#,##0.00"/></td>
						<td>${item.loanInfo.loanFlagLabel}</td>
						<td>${item.loanInfo.modelLabel}</td>
						<td>${item.loanCustomer.customerTelesalesFlagLabel}</td>
						<td><input type="button" class="btn_edit" value="办理" name="earlyFinishBtn" pmContractCode="${item.contractCode}" confirmContractCode="${item.id}"> 
							<input type="button" name="seeDate" class="btn_edit" value="查看还款明细" pmContractCode="${item.contractCode}"/> 
							<%-- <input type="hidden" name="paybackMonthId" value="${item.paybackMonth.id}" />  
							<input type="hidden" name="contractCode" value="${item.contractCode}" />--%>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${waitPage}</div>
</body>
</html>