<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/electricApplyPaybackUse.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div class="control-group">
		<form style="overflow-y: hidden;" id="applyPayBackUseForm" action="${ctx}/borrow/payback/applyPaybackUse/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input type="hidden" id="msg" name="msg" value="${message}">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" name="loanCustomer.customerName" class="input_text178" value="${payback.loanCustomer.customerName }" />
					</td>
					<td><label class="lab">合同编号：</label> 
						<input type="text" name="contractCode" class="input_text178" value="${payback.contractCode }" />
					</td>
					<td><label class="lab" style="text-align: right">还款日：</label>
						<sys:multirepaymentDate dateClick="dateClick" dateId="repaymentDate"></sys:multirepaymentDate>
				        <input id="repaymentDate"  name="paybackDayNum" class="input_text178" readonly ="readonly"   value="${payback.paybackDayNum }">
				        <i id="dateClick" class="icon-search" style="cursor: pointer;"></i>
					</td>
				</tr>
				<tr>
					<td><label class="lab">合同到期日：</label> 
						<input type="text" name="contract.contractEndDay" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${payback.contract.contractEndDay }" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
					</td>
					<td><label class="lab" style="text-align: right">借款状态：</label>
						<select name="loanInfo.dictLoanStatus" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_status')}" var="card_type">
								 <option value="${card_type.value }" <c:if test="${payback.loanInfo.dictLoanStatus == card_type.value }">selected</c:if>>${card_type.label }</option>
							</c:forEach>
						</select>
					<td><label class="lab" style="text-align: right">来源系统：</label>
						<select name="loanInfo.dictSourceType" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${payback.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                            </c:forEach>
						</select>
					</td>
				</tr>
				<tr id="searchApplyUse" style="display: none">
					<td><label class="lab">渠道：</label> 
						<select class="select180" name="loanInfo.loanFlag">
                			<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${payback.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                		</select>
					</td>
					<td><label class="lab">开户行名称：</label> 
							<select name="loanBank.bankName" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_open_bank')}" var="applyBankName">
	                                   <option value="${applyBankName.value }" <c:if test="${payback.loanBank.bankName == applyBankName.value }">selected</c:if>>${applyBankName.label }</option>
	                            </c:forEach>
	                       </select>
						</td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
				<input type="button" class="btn btn-primary" id="applyPayBackUseClearBtn" value="清除">
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="${context }/static/images/up.png" id="showSearchApplyUseBtn" style="text-align: center;"></img>
			</div>
		</div>
	</div>
	<div>
		<table  id="tableList" class="table  table-bordered table-condensed table-hover">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>开户行名称</th>
					<th>门店名称</th>
					<th>合同到期日</th>
					<th>批复期限</th>
					<th>还款日</th>
					<th>月还期供金额</th>
					<th>借款状态</th>
					<th>期供状态</th>
					<th>还款状态</th>
					<th>蓝补金额</th>
					<th>渠道</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:forEach items="${paybackList.list}" var="item" varStatus="status">
				<tr>
					<td width="38px">${status.count }</td>
					<td width="88px">${item.contractCode}</td>
					<td width="71">${item.loanCustomer.customerName}</td>
					<td width="105">${item.loanBank.bankNameLabel}</td>
					<td width="71">${item.loanInfo.loanStoreOrgName}</td>
<%-- <fmt:formatDate value="${item.contract.contractEndDay}" type="date" />	 --%><td width="88"> <fmt:formatDate value="${item.contract.contractEndDay}" type="date" /></td>
					<td width="71">${item.contract.contractMonths }</td>
					<td width="99">${item.paybackDay}</td>
<%-- <fmt:formatNumber value="${item.paybackMonthAmount }" pattern='0.00'/>		 --%><td width="105"><fmt:formatNumber value="${item.paybackMonthAmount }" pattern='0.00'/></td>
					<td width="71">${item.loanInfo.dictLoanStatusLabel}</td>
					<td width="71">${item.paybackMonth.dictMonthStatusLabel}</td>
					<td width="90">${item.dictPayStatusLabel}</td>
<%-- <fmt:formatNumber value="${item.paybackBuleAmount }" pattern='0.00'/>	 --%><td width="98"><fmt:formatNumber value="${item.paybackBuleAmount }" pattern='0.00'/></td>
					<td width="41">${item.loanInfo.loanFlagLabel}</td>
					<td width="52"><input type="button" class="btn_edit" data-toggle="modal" data-target="#appplyPayBackUseModal"
						id="applyPayBackUseMal" applyPaybackId="${item.id }" 
						name="applyPayBackUse" dictMonthStatus="${item.dictPayStatus}"
						payBackUseContractCode="${item.contractCode}" value="办理"/></td>
				</tr>
			</c:forEach>
			<c:if
				test="${ paybackList.list==null || fn:length(paybackList.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有待办数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${paybackList}</div>
	<div class="modal fade" id="appplyPayBackUseModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">冲抵方式</h4>
				</div>
				<form id="appplyPayBackUseForm" action="${ctx}/borrow/payback/applyPaybackUse/form" method="post">
					<input type="hidden" id="id" name="id" /> 
					<input type="hidden" id="applyPayBackContractCode" name="contractCode">
					<div class="modal-body">
						<table class="table1" id="customerTab">
								<tr>
									<td><label class="lab"> 冲抵方式：</label> 
										<input type="radio" style="margin-left:5px;margin-top:4px" name="paybackApply.dictPayUse" value="5" checked="checked" />还款冲抵
									</td>
									<td id="monthBeforefinish">
										<input type="radio" style="margin-left:10px" name="paybackApply.dictPayUse" value="3" />提前结清申请
									</td>
								</tr>
						</table>
					</div>
					<div class="modal-footer">
						<a type="button" class="btn btn-primary" data-dismiss="modal" id="applyPayBackUsebtn">确定</a>
						<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					</div>
				</form>
			</div>
		</div>
	</div> 
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-230,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>