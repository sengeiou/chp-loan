<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/confirmPayback.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
</head>
<body>
	<div class="control-group">
		<form id="confirmPaybackForm" action="${ctx}/borrow/payback/confirmPayback/list" method="post">
			<input id="ids" name="ids" type="hidden" value=""></input>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					<tr>
						<td><label class="lab">客户姓名：</label> <input type="text" name="loanCustomer.customerName" class="input-medium" value="${payback.loanCustomer.customerName }"/></td>
						<td><label class="lab">合同编号：</label> <input type="text" name="contractCode" class="input-medium" value="${payback.contractCode }"/></td>
						<td><label class="lab">门店：</label> 
							<input id="txtStore" name="loanInfo.loanStoreOrgName" type="text" readonly="readonly"  class="txt date input_text178" value="${payback.loanInfo.loanStoreOrgName }"/> 
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
						<td><label class="lab">还款状态 ：</label> 
							<select name="queryDictPayStatus" readonly="readonly" class="select180">
									<option value=''>请选择</option>
									 <c:forEach items="${fns:getDictList('jk_repay_status')}" var="dictPayStatus">
                                   <option value="${dictPayStatus.value }" 
                                   <c:if test="${payback.dictPayStatus == dictPayStatus.value }">selected</c:if>>${dictPayStatus.label }</option>
                              </c:forEach>
							</select>
						</td>
						<td><label class="lab">是否电销 ：</label> 
							<select class="select180" name="loanCustomer.customerTelesalesFlag">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_telemarketing')}" var="loanIsPhone">
									<option value="${loanIsPhone.value }"
									<c:if test="${payback.loanCustomer.customerTelesalesFlag== loanIsPhone.value }">selected</c:if>>${loanIsPhone.label }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr id="T1" style="display: none">
						<td><label class="lab">结清待审核时间：</label>
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
					<tr>
							<td><label class="lab">未还违约金罚息：</label> <input
								name="publishStart" type="text" maxlength="40" 
								class="input_text70" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"   value="${payback.publishStart}" />-<input
								name="publishEnd" type="text" maxlength="40"
								class="input_text70" onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')"    value="${payback.publishEnd}" /></td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
					</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
				<input type="button" id="clearBtn" class="btn btn-primary" value="清除">
		  <div style="float: left; margin-left: 45%; padding-top: 10px">
			<img src="${context }/static/images/up.png" id="showMore"></img>
		</div>
		</div>
	</div>
	<p class="mb5">
		<button class="btn btn-small" onclick="selectAll(this)">全选</button>
		<button id="exportExcel" class="btn btn-small" >导出Excel</button>
	</p>
	<div class="box5">
		<form id="confirmForm" method="post">
			<input type="hidden" id="confirmContractCode" name="contractCode">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table id="confirmTab">
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
						<th>未还违约金(滞纳金)及罚息金额</th>
						<th>结清待审核日期</th>
						<th>还款状态</th>
						<th>借款状态</th>
						<th>减免金额</th>
						<th>渠道</th>
						<th>模式</th>
						<th>是否电销</th>
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
							   <fmt:formatNumber  value='${item.contract.contractAmount }' pattern="#,##0.00"  /> 
							</td>
							<td>
							   <fmt:formatNumber value='${item.urgeServicesMoney.urgeDecuteMoeny }' pattern="#,##0.00"  /> 
							</td>
							<td>
							   <fmt:formatNumber value='${item.loanGrant.grantAmount }' pattern="#,##0.00"  /> 
							</td>
							<td>${item.contract.contractMonths }</td>
							<td><fmt:formatDate value="${item.contract.contractReplayDay}" type="date" /></td>
							<td>${item.paybackMaxOverduedays }</td>
							<td>
							   <fmt:formatNumber value='${item.paybackMonth.penaltyInterest }' pattern="#,##0.00"  /> 
							</td>
							<td><fmt:formatDate value="${item.modifyTime}" type="date" /></td>
							<td>${item.dictPayStatusLabel}</td>
							<td>${item.loanInfo.dictLoanStatusLabel}</td>
							<td>
							  <fmt:formatNumber type="number" value='${item.paybackMonth.creditAmount }' pattern="#,##0.00"  />
							</td>
							<td>${item.loanInfo.loanFlagLabel}</td>
							<td>${item.loanInfo.modelLabel}</td>
							<td>${item.loanCustomer.customerTelesalesFlagLabel}</td>
							<td><input type="button" class="btn_edit" value="办理"
								id="confirmInfoBtn" name="confirmInfoBtn"
								confirmContractCode="${item.contractCode}"></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</form>
	</div>
	<div class="pagination">${paybackList}</div>
</body>
</html>