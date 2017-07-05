<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/applyPaybackUse.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
<script type="text/javascript">
function getqueryManualOperation(contractCode,monthId) {
	var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode+"&id="+monthId;
	art.dialog.open(url, {  
         id: 'his',
         title: '还款明细',
         lock:true,
         width:1200,
     	 height:500
     },  
     false);
}
</script>
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
					<td><label class="lab">合同到期日：</label> 
						<input type="text" name="contract.contractEndDay" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${payback.contract.contractEndDay }" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
					</td>
				</tr>
				<tr>
					<td><label class="lab" style="text-align: right">还款日：</label>
						<select name="paybackDayNum" class="select180">
							        <option value="">请选择</option>
							<c:forEach var="day" items="${dayList}">
									<option value="${day.billDay}"
									<c:if test="${payback.paybackDayNum ==day.billDay }">selected</c:if>>${day.billDay}</option>
							</c:forEach>
					   </select>
					</td>
					<td><label class="lab" style="text-align: right">借款状态：</label>
						<select name="loanInfo.dictLoanStatus" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_status')}" var="card_type">
								 <option value="${card_type.value }" <c:if test="${payback.loanInfo.dictLoanStatus == card_type.value }">selected</c:if>>${card_type.label }</option>
							</c:forEach>
						</select></td>
					
	                       <td><label class="lab">开户行名称：</label> 
							<select name="loanBank.bankName" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_open_bank')}" var="applyBankName">
	                                   <option value="${applyBankName.value }" <c:if test="${payback.loanBank.bankName == applyBankName.value }">selected</c:if>>${applyBankName.label }</option>
	                            </c:forEach>
	                       </select>
						</td>
				</tr>
				<tr id="searchApplyUse">
					<td><label class="lab">渠道：</label> 
						<select class="select180" name="loanInfo.loanFlag">
                			<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${payback.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                		</select>
					</td>
					<td><label class="lab">模式：</label> 
							<select name="model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${payback.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
	                       </select></td>
	                       <td><label class="lab" style="text-align: right">来源系统：</label>
						<select name="loanInfo.dictSourceType" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${payback.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                            </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">逾期等级：</label>
						<select name="overdueLevel" class="select180">
							<option value="">请选择</option>
							<option value="-1" <c:if test="${payback.overdueLevel==-1 }">selected</c:if>></option>
							<option value="0" <c:if test="${payback.overdueLevel==0 }">selected</c:if>>C</option>
							<option value="1" <c:if test="${payback.overdueLevel==1 }">selected</c:if>>M1</option>
							<option value="2" <c:if test="${payback.overdueLevel==2 }">selected</c:if>>M2</option>
							<option value="3" <c:if test="${payback.overdueLevel==3 }">selected</c:if>>M3</option>
							<option value="4" <c:if test="${payback.overdueLevel==4 }">selected</c:if>>M4</option>
							<option value="5" <c:if test="${payback.overdueLevel==5 }">selected</c:if>>M5</option>
							<option value="6" <c:if test="${payback.overdueLevel==6 }">selected</c:if>>M6</option>
							<option value="7" <c:if test="${payback.overdueLevel==7 }">selected</c:if>>M6+</option>
						</select>
					</td>
				</tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
				<input type="button" class="btn btn-primary" id="applyPayBackUseClearBtn" value="清除">
		</div>
	</div>
	<div>
		<table id="applypaybackUseTab">
			<thead>
				<tr>
					<th width="38px">序号</th>
					<th width="88px">合同编号</th>
					<th width="71">客户姓名</th>
					<th width="105">开户行名称</th>
					<th width="71">门店名称</th>
					<th width="70">首期还款日</th>
					<th width="88">合同到期日</th>
					<th width="71">借款期数</th>
					<th width="99">还款日</th>
					<th width="105">月还期供金额</th>
					<th width="71">借款状态</th>
					<th width="90">还款状态</th>
					<th width="90">逾期等级</th>
					<th width="90">逾期天数</th>
					<th width="90">总逾期次数</th>
					<th width="98">蓝补金额</th>
					<!-- <th width="41">管辖</th> -->
					<th width="41">渠道</th>
					<th width="41">模式</th>
					<th width="68">操作</th>
				</tr>
			</thead>
			<c:forEach items="${paybackList.list}" var="item" varStatus="status">
				<tr>
					<td width="38px">${status.count }</td>
					<td width="88px">${item.contractCode}</td>
					<td width="71">${item.loanCustomer.customerName}</td>
					<td width="105">${item.loanBank.bankNameLabel}</td>
					<td width="71">${item.loanInfo.loanStoreOrgName}</td>
					<td width="70"><fmt:formatDate value="${item.contract.contractReplayDay}" type="date" /></td>
					<td width="88"><fmt:formatDate value="${item.contract.contractEndDay}" type="date" /></td>
					<td width="71">${item.contract.contractMonths }</td>
					<td width="99">${item.paybackDay}</td>
                    <td width="105"><fmt:formatNumber value="${item.paybackMonthAmount }" pattern='0.00'/></td>
					<td width="71">${item.loanInfo.dictLoanStatusLabel}</td>
					<td width="90">${item.dictPayStatusLabel}</td>
					<td width="90">${item.overdueLevel}</td>
					<td width="90">${item.overdueDays}</td>
					<td width="90">${item.overdueCount}</td>
                    <td width="98"><fmt:formatNumber value="${item.paybackBuleAmount }" pattern='0.00'/></td>
                   <!--  <td>门店</td> -->
					<td width="41">${item.loanInfo.loanFlagLabel}</td>
                   	<td width="41">${item.loanInfo.modelLabel}</td>
					<td width="52">
						<input type="button" class="btn_edit jkhj_hkcdlb_blbtn" data-toggle="modal" data-target="#appplyPayBackUseModal"
						id="applyPayBackUseMal" applyPaybackId="${item.id }" dictPayStatus="${item.dictPayStatus }" paybackDay = "${item.paybackDay}"
						name="applyPayBackUse" dictMonthStatus="${item.dictPayStatus}" <c:if test="${isfour!='1' or checkDay!=item.paybackDay }">isuse="1"</c:if>
						<c:if test="${isfour=='1' and checkDay==item.paybackDay }">isuse="0"</c:if>
						payBackUseContractCode="${item.contractCode}" value="办理"/>
						<c:if test="${item.loanInfo.dictLoanStatus!='88' and isfour!='1'}"><input type="button" class="btn_edit jkhj_hkcdlb_tksqbtn" value="退款申请" onclick="doOpenRefund('${item.contractCode}');"/></c:if>
						<input type="button" class="btn_edit jkhj_hkcdlb_hkmxbtn" value="还款明细" onclick="getqueryManualOperation('${item.contractCode}','${item.paybackMonth.id }');"/>
					</td>
				</tr>
			</c:forEach>
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
						<table id="customerTab">
							<tr>
								<td>
									<label class="lab"> 冲抵方式：</label> 
									<input type="radio" style="margin-left:5px;margin-top:4px" name="paybackApply.dictPayUse" value="5" checked="checked" />
									<font style="">还款冲抵</font>
								</td>
								<td id="monthBeforefinish">
									<input type="radio" style="margin-left: 5px" name="paybackApply.dictPayUse" value="3" />提前结清申请
								</td>
							</tr>
							<tr>
								<td id = "dictPayUseMsg">
									<font style="margin-left:65px;margin-top:5px;font-size: 13px;color:#FF4500;"><b>11：50~13：00,17:50~19:00 系统冲抵中请勿操作！</b></font>
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
</body>
</html>