<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackBlue.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
</head>
<body>
	<div class="control-group">
		<form style="overflow-y: hidden;" id="applyPayBackUseForm" action="${ctx}/borrow/blue/PaybackBlue/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input type="hidden" id="msg" name="msg" value="${message}"/>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" id="customerName" name="customerName" class="input_text178" value="${search.customerName }" maxlength="20"/>
					</td>
					<td><label class="lab">合同编号：</label> 
						<input type="text" id="contractCode" name="contractCode" class="input_text178" value="${search.contractCode }" maxlength="20"/>
					</td>
					<td><label class="lab">门店名称：</label>
					<input id="orgName" name="orgName" type="text" maxlength="20" class="txt date input_text178" value="${search.orgName }" readonly/> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input id="orgId" name="orgId" type="hidden" value="${search.orgId }">
				</td>
					
				</tr>
				<tr>
					<td><label class="lab" style="text-align: right">还款日：</label>
				        <select name="paybackDayNum" class="select180">
							        <option value="">请选择</option>
							<c:forEach var="day" items="${dayList}">
									<option value="${day.billDay}"
									<c:if test="${search.paybackDayNum ==day.billDay }">selected</c:if>>${day.billDay}</option>
							</c:forEach>
					   </select>
					</td>
					
					<td><label class="lab" style="text-align: right">还款状态：</label>
						<select id="loanStatus" name="loanStatus" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_status')}" var="loanStatus">
								<option value="${loanStatus.value}" <c:if test="${search.loanStatus == loanStatus.value }">selected</c:if>>${loanStatus.label}</option>
							</c:forEach>
						</select>
					<td><label class="lab">渠道：</label> 
							<select class="select180" id="loanFlag" name="loanFlag">
	                			<option value="">请选择</option>
	                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanFlag">
	                                   <option value="${loanFlag.value }" <c:if test="${search.loanFlag == loanFlag.value }">selected</c:if>>${loanFlag.label }</option>
	                              </c:forEach>
                			</select>
					
					</td>
				</tr>
				<tr>
					<td><label class="lab">模式：</label> 
						<select name="contract.model" class="select180">
							<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
                                  <option value="${loanmodel.value }" <c:if test="${search.contract.model == loanmodel.value }">selected</c:if>>
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
		
		<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary"  onclick="document.forms[0].submit();" value="搜索"> 
				<input type="button" class="btn btn-primary" id="applyPayBackUseClearBtn" value="清除">
			
		</div>
		</form>
	</div>
	<div>
		<table id="applypaybackUseTab">
			<thead>
				<tr>
					<th width="38px">序号</th>
					<th width="88px">合同编号</th>
					<th width="71">客户姓名</th>
				
					<th width="71">门店名称</th>
					<th width="71">产品类型</th>
					
					<th width="71">批复期限</th>
					<th width="99">还款日</th>
					<th width="88">首次还款日</th>
					<th width="105">合同金额</th>
					<th width="105">放款金额</th>
					
					<th width="105">期供</th>
					<th width="105">应还违约金总额</th>
					<th width="105">应还罚息总额 </th>
					<th width="98">蓝补金额</th>
					<th width="98">渠道</th>
					<th width="98">模式</th>
					<th width="98">操作</th>
				</tr>
			</thead>
			<c:forEach items="${paybackList.list}" var="item" varStatus="sta">
				<tr>
					<td width="38px">${sta.index + 1}</td>
					<td width="88px">${item.contractCode}</td>
					<td width="71">${item.loanCustomer.customerName}</td>
					
					<td width="71">${item.orgName}</td>
					<td width="71">${item.jkProducts.productName}</td>
				
					<td width="71">${item.contract.contractMonths }</td>
					<td width="99">${item.paybackDay}</td>
					<td width="88"><fmt:formatDate value="${item.contract.contractReplayDay}" type="date" /></td>
					<td width="105"><fmt:formatNumber value="${item.contract.contractAmount }" pattern='0.00'/></td>
					<td width="105"><fmt:formatNumber value="${item.loanGrant.grantAmount }" pattern='0.00'/></td>
					<td width="105"><fmt:formatNumber value="${item.paybackMonthAmount }" pattern='0.00'/></td>
					<td width="105"><fmt:formatNumber value="${item.paybackMonth.monthPenaltyShouldSum }" pattern='0.00'/></td>
					<td width="105"><fmt:formatNumber value="${item.paybackMonth.monthInterestPunishshouldSum }" pattern='0.00'/></td>
					<td width="98"><fmt:formatNumber value="${item.paybackBuleAmount }" pattern='0.00'/></td>
					<td width="98">${item.loanInfo.loanFlag}</td>
					<td width="98">${item.contract.model}</td>
					<td width="98">
					
						<%-- <input type="button" class="btn_edit jkhj_lbzhgllb_zzbtn"
								onclick="doOpenTransfer('${item.contractCode }','${item.loanCustomer.customerCertNum}');"
								value="转账" /> --%>
						<%-- <input type="button" class="btn_edit"
								onclick="doOpenRefund('${item.contractCode }','${item.loanCustomer.customerCertNum}');"
								value="退款" /> --%>
						<input type="button" class="btn_edit jkhj_lbzhgllb_lbdzdbtn"
								onclick="openView('${item.contractCode}','${item.paybackBuleAmount }');"
								value="蓝补对账单" />
						<%-- <input type="button" class="btn_edit jkhj_lbzhgllb_lbjexgbtn"
								onclick="updatePaybackBlue('${item.contractCode }','${item.loanCustomer.customerCertNum}');"
								value="蓝补金额修改" /> --%>		
						</td>
				</tr>
			</c:forEach>
			<c:if
				test="${ paybackList.list==null || fn:length(paybackList.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有蓝补数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${paybackList}</div>
	
</body>
</html>