<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/payback/doAdvanceSettled.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src='${context}/js/bootstrap.autocomplete.js' type="text/javascript"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
</head>
<body>
	<div class="control-group">
		<form id="confirmPaybackForm"  method="post" action="${ctx}/borrow/payback/doAdvanceSettled/list">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<div>
					<tr>
						<td><label class="lab">客户姓名：</label> 
							<input type="text" name="loanCustomer.customerName" class="input-medium" value="${paybackCharge.loanCustomer.customerName }" />
						</td>
						<td><label class="lab">合同编号：</label> 
							<input type="text" name="payback.contractCode" class="input-medium" value="${paybackCharge.payback.contractCode }" /></td>
						
				<c:if test="${isManager==false}">	
						<td><label class="lab">门店：</label> 
							<input id="txtStore" name="loanInfo.loanStoreOrgName" type="text" maxlength="20" class="txt date input_text178" value="${paybackCharge.loanInfo.loanStoreOrgName }" /> 
								<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
							<input type="hidden" id="hdStore" name = "loanInfo.loanStoreOrgId" value="${paybackCharge.loanInfo.loanStoreOrgId }">
						</td>
				</c:if>			
						
				<c:if test="${isManager==true}">
						<td><label class="lab">门店：</label> 
							<input id="txtStore"  name="loanInfo.loanStoreOrgName"  readonly="readonly"  type="text" maxlength="20" class="txt date input_text178" value="${paybackCharge.loanInfo.loanStoreOrgName }" /> 
							<input type="hidden" id="hdStore" name = "loanInfo.loanStoreOrgId" value="${paybackCharge.loanInfo.loanStoreOrgId }">
						</td>
				</c:if>	
						
					</tr>
					<tr>
						<td><label class="lab">还款日：</label>
							<input type="number" class="input-medium" id="paybackDayNum" name="payback.paybackDayNum" value="${paybackCharge.payback.paybackDayNum }"/> 
						</td>
						<td><label class="lab">渠道：</label> 
							<select class="select180" name="loanInfo.loanFlag">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
									<option value="${loanMark.value }"
										<c:if test="${paybackCharge.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
								</c:forEach>
							</select>
						</td>
						<td><label class="lab">来源系统：</label> 
							<select class="select180" name="loanInfo.dictSourceType">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}"
									var="dictSourceType">
									<option value="${dictSourceType.value }"
										<c:if test="${paybackCharge.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<div>
						<tr id="searchConfirmPayback" style="display: none">
							<td><label class="lab">是否电销 ：</label>
									<select class="select180" name="loanCustomer.customerTelesalesFlag">
		                			  <option value="">请选择</option>
		                              <c:forEach items="${fns:getDictList('jk_telemarketing')}" var="loanIsPhone">
		                                   <option value="${loanIsPhone.value }" <c:if test="${paybackCharge.loanCustomer.customerTelesalesFlag== loanIsPhone.value }">selected</c:if>>${loanIsPhone.label }</option>
		                              </c:forEach>
		                			</select>
							</td>
						</tr>
					</div>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary " onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
		<div style="float: left; margin-left: 45%; padding-top: 10px">
			<img src="${context }/static/images/up.png" id="showSearchConfirmPaybackBtn"></img>
		</div>
	</div>
    </div>
	<div class="box5">
		<form id="doAdvanceForm" action="${ctx}/borrow/payback/doAdvanceSettled/form" method="post">
			<input type="hidden" id="doAdvanceId" name="id">
			<input type="hidden" id="doAdvanceContractCode" name="contractCode">
			<input type="hidden" id="doAdvanceDictOffsetType" name="dictOffsetType">
			<input type="hidden" id="doAdvanceChargeStatus" name="chargeStatus">
		<table id="doAdvanceTab">
				<thead>
					<tr height="37px" >
						<th>序号</th>
						<th>合同编号</th> 
						<th>门店名称</th>
						<th>客户姓名</th>
						<!-- <th>合同金额</th>
						<th>放款金额</th>
						<th>批借期数</th>
						<th>首期还款日</th>
						<th>最长逾期天数</th>
						<th>提前结清应还款总额</th>
						 -->
						 <th>借款状态</th>
						<th>申请还款总额</th>
						<th>还款日</th>
						<th>还款类型</th>
						<th>还款状态</th>
						<th>申请提前结清日期</th>
					    <!-- 
						<th>减免人</th>
						<th>减免金额</th>
						 -->
						<th>渠道</th>
						<th>是否电销</th>
						<th>操作</th>
					</tr>
				</thead>
					<c:forEach items="${waitPage.list}" var="item" varStatus="status">
						<tr>
							<td>${status.count}</td>
							<td>${item.contractCode}</td>
							<td>${item.loanInfo.loanStoreOrgName}</td>
							<td>${item.loanCustomer.customerName}</td>
							<!--  <td><fmt:formatNumber value='${item.contract.contractAmount}' pattern="#,##0.00"/></td>
							<td><fmt:formatNumber value='${item.loanGrant.grantAmount }' pattern="#,##0.00"/></td>
							<td>${item.contract.contractMonths }</td>
							<td><fmt:formatDate value="${item.contract.contractReplayDay}" type="date" /></td>
							<td>${item.payback.paybackMaxOverduedays }</td>
							<td><fmt:formatNumber value='${item.settleTotalAmount }' pattern="#,##0.00"/></td>
							-->
							<td>${item.loanInfo.dictLoanStatusLabel}</td>
							<td><fmt:formatNumber value='${item.applyBuleAmount }' pattern="#,##0.00"/></td>
							<td>${item.payback.paybackDay}</td>
							<td>${item.dictOffsetTypeLabel}</td>
							<td>${item.payback.dictPayStatusLabel}</td>
							<td><fmt:formatDate value="${item.createTime}" type="date" /></td>
							 <!-- 
							<td>${item.payback.remissionBy }</td>
							<td><fmt:formatNumber value='${item.creditAmount }' pattern="#,##0.00"/></td>
							-->
							<td>${item.loanInfo.loanFlagLabel}</td>
							<td>${item.loanCustomer.customerTelesalesFlagLabel}</td>
							<td><input type="button" class="btn_edit" value="办理" name="doAdvanceBtn" 
								doAdvanceId="${item.id }" doAdvanceContractCode="${item.contractCode }" 
								doAdvanceDictOffsetType="${item.dictOffsetType }" doAdvanceChargeStatus="${item.chargeStatus }">
						  </td>
						</tr>
					</c:forEach>
					<c:if test="${waitPage.list ==null || fn:length(waitPage.list)==0}">
						<tr>
							<td colspan="22" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
			</table>
		</form>
		<div class="pagination">${waitPage}</div>
		</div>
</body>
</html>