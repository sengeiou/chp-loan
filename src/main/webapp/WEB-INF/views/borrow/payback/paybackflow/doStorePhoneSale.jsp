<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/doStorePhoneSale.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div class="control-group">
		<div>
			<form id="applyPayBackUseForm" action="${ctx}/borrow/payback/doStore/phoneSaleList" method="post">
			<input type="hidden" id="msg" value="${message}">
			<input type="hidden" name="id" />
				<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">客户姓名：</label> 
							<input type="text" name="loanCustomer.customerName" class="input-medium" value="${paybackApply.loanCustomer.customerName }"/>
						</td>
						<td><label class="lab">合同编号：</label> 
							<input type="text" name="contractCode" class="input-medium" value="${paybackApply.contractCode }"/>
						</td>
						<td><label class="lab">还款申请日期：</label> 
						<input type="text" name="applyPayDay" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${paybackApply.applyPayDay }" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</td>
					</tr>
					<tr>
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
						<td><label class="lab">是否电销 ：</label>
							<select class="select180" name="loanCustomer.customerTelesalesFlag">
		                			  <option value="">请选择</option>
		                              <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="loanIsPhone">
		                                   <option value="${loanIsPhone.value }" <c:if test="${paybackApply.loanCustomer.customerTelesalesFlag== loanIsPhone.value }">selected</c:if>>${loanIsPhone.label }</option>
		                              </c:forEach>
		                	</select>
						</td>
					</tr>
					<tr id="T1" style="display:none">
						<td><label class="lab">还款方式：</label> 
							<select class="select180" name="dictRepayMethod">
		                			  <option value="">请选择</option>
		                              <c:forEach items="${fns:getDictList('jk_repay_way')}" var="dictRepayMethod">
		                                   <option value="${dictRepayMethod.value }" <c:if test="${paybackApply.dictRepayMethod== dictRepayMethod.value }">selected</c:if>>${dictRepayMethod.label }</option>
		                              </c:forEach>
		                	</select>
						 </td>
						 <td><label class="lab">模式：</label> 
							<select name="loanInfo.model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${paybackApply.loanInfo.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                       </select>
	                    </td>
					</tr>
				</table>
			</form>
		</div>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" id="applyPayBackUseClearBtn" value="清除">
			<div style="float:left;margin-left:45%;padding-top:10px">
					  <img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
			</div>
		</div>
	</div>
	<div>
		<form id="deductInfoForm" action="${ctx}/borrow/payback/doStore/phoneSaleForm" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<input type="hidden" id="deductContractCode" name="contractCode">
			<input type="hidden" id="matchingId" name="id">
			<input type="hidden" id="chargeId" name="paybackCharge.id">
			<table id="doStoreTab">
				<thead>
					<tr>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>合同到期日</th>
						<th>还款申请日</th>
						<th>门店名称</th>
						<th>借款状态</th>
						<th>还款日</th>
						<th>申请还款金额</th>
						<th>实际还款金额</th>
						<th>还款类型</th>
						<th>还款状态</th>
						<th>还款方式</th>
						<th>回盘结果</th>
						<th>渠道</th>
						<th>模式</th>
						<th>是否电销</th>
						<th>退回原因</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${paybackApplyList.list}" var="item">
						<tr>
							<td>${item.contractCode}</td>
							<td>${item.loanCustomer.customerName}</td>
							<td><fmt:formatDate value="${item.contract.contractEndDay }" type="date" /><%-- <fmt:formatDate value="${item.contract.contractEndDay }" type="date" /> --%></td>
							<td><fmt:formatDate value="${item.applyPayDay }" type="date" /><%-- <fmt:formatDate value="${item.applyPayDay }" type="date" /> --%></td>
							<td>${item.loanInfo.loanStoreOrgName }</td>
							<td>${item.loanInfo.dictLoanStatusLabel}</td>
							<td>${item.payback.paybackDay}</td>
							<td><fmt:formatNumber value='${item.applyAmount}' pattern="#,##0.00"/><%-- <fmt:formatNumber value='${item.applyAmount}' pattern="#,##0.00"/> --%></td>
							<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="#,##0.00"/><%-- <fmt:formatNumber value='${item.applyReallyAmount}' pattern="#,##0.00"/> --%></td>
							<td>${item.dictPayUseLabel}<%-- ${fns:getDictLists(item.dictPayUse,'jk_repay_type','-')} --%></td>
							<td>${item.payback.dictPayStatusLabel}<%-- ${fns:getDictLabel(item.payback.dictPayStatus,'jk_repay_status','-')} --%></td>
							<td>${item.dictRepayMethodLabel}</td>
							<td>${item.splitBackResultLabel}<%-- ${fns:getDictLabel(item.dictPayResult,'jk_counteroffer_result','-')} --%></td>
							<td>${item.loanInfo.loanFlagLabel}<%-- ${fns:getDictLabel(item.loanInfo.loanFlag,'jk_channel_flag','-')} --%></td>
                   			<td>${item.loanInfo.modelLabel}</td>
							<td>${item.loanCustomer.customerTelesalesFlagLabel}<%-- ${fns:getDictLabel(item.loanCustomer.customerTelesalesFlag,'jk_telemarketing','-')} --%></td>
							<td>${item.applyBackMes }</td>
							<td><input type="button" class="btn_edit" value="办理" applyPaybackId="${item.id }" chargeId ="${item.paybackCharge.id}"
							 name="deductInfoBtn" deductContractCode="${item.contractCode}">
							 <input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('${item.payback.id}','${item.id}','');" value="历史" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${paybackApplyList}</div>
</body>
</html>