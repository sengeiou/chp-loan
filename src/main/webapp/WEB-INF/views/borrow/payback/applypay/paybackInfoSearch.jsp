<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context }/js/payback/paybackInfoSearch.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
<meta name="decorator" content="default" />
<script type="text/javascript">
function getqueryManualOperation(contractCode,monthId) {
	var url=ctx + "/borrow/payback/manualOperation/queryManualOperation?contractCode="+contractCode+"&id="+monthId;
	art.dialog.open(url, {  
         id: 'his',
         title: '还款明细',
         lock:true,
         width:1200,
     	 height:500
     } ,  
     false);
}
$(document).ready(function(){
	$(".operation").bind("click", function(event) {
		event.stopPropagation(); // 阻止事件冒泡
		var $currdiv = $(this).next(); // div
		
		$(".operation").each(function(){
			var $other = $(this).next();
			if (!$other.is($currdiv) && !$other.is(":hidden")){
				$other.hide();
			}
				
		}); // 控制是否隐藏
		$(this).next().css("float", "left");
		$(this).next().css("position", "absolute");
		$(this).next().css("top","auto");
		if ($currdiv.is(":hidden"))
			$currdiv.show();
		else{
			$currdiv.hide();
		}
	});
	
	$(document).click(function() {
		$(".operation").next().hide();
	});
	
});

</script>
</head>
<body>
	<div class="control-group">
		<form style="overflow-y: hidden;" id="applyPayBackUseForm" action="${ctx}/borrow/payback/paybackInfoSearch/list" method="post">
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
                              	<c:if test="${loanMark.label!='联合'}">
                                   <option value="${loanMark.value }" <c:if test="${payback.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              	</c:if>
                              </c:forEach>
                		</select>
					</td>
					<td><label class="lab">是否紧急诉讼：</label> 
							<select name="loanInfo.yesOrNoEmergency" class="select180">
							<option value="">请选择</option>
							<option value="1" <c:if test="${payback.loanInfo.yesOrNoEmergency == '1'}">selected</c:if>>是</option>
							<option value="0" <c:if test="${payback.loanInfo.yesOrNoEmergency == '0'}">selected</c:if>>空</option>
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
	                       <td><label class="lab">黑灰名单标记：</label>
	                       <select name="loanInfo.blackType" class="select180">
		                       <option value=''>请选择</option>
		                       <option value='0' <c:if test="${payback.loanInfo.blackType== '0'}">selected</c:if>>黑</option>
		                       <option value='1' <c:if test="${payback.loanInfo.blackType== '1'}">selected</c:if>>灰</option>
		                       <option value='2' <c:if test="${payback.loanInfo.blackType== '2'}">selected</c:if>>无</option>
	                       </select>
	                       </td>
	                       <td><label class="lab">紧急诉讼状态</label>
	                       <select name="loanInfo.emergencyStatus" class="select180">
	                       <option value=''>请选择</option>
		                       <c:forEach items="${fns:getDictList('jk_emergency_status')}" var="item">
		                       <option value="${item.value}" <c:if test="${item.value== payback.loanInfo.emergencyStatus}">selected</c:if>>
		                       		${item.label}
		                       </option>
		                       </c:forEach>
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
					<th width="98">蓝补金额</th>
					<th width="41">渠道</th>
					<th width="41">模式</th>
					<th width="41">是否紧急诉讼</th>
					<th width="71">紧急诉讼状态</th>
					<th width="41">黑灰名单标记</th>
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
                    <td width="98"><fmt:formatNumber value="${item.paybackBuleAmount }" pattern='0.00'/></td>
					<td width="41">${item.loanInfo.loanFlagLabel}</td>
                   	<td width="41">${item.loanInfo.modelLabel}</td>
                   	<td width="41"><c:if test="${item.loanInfo.yesOrNoEmergency=='1'}">是</c:if></td>
                   	<td width="71">${item.loanInfo.emergencyStatusLabel}</td>
                   	<td width="41">
                   	<c:if test="${item.loanInfo.blackType=='0'}">黑名单</c:if>
                   	<c:if test="${item.loanInfo.blackType=='1'}">灰名单</c:if>
                   	</td>
					<td>
					<input type="button" class="btn btn_edit operation" value="操作"/>
					<div class="alertset" style="display:none">
						<input type="button" name="emergencyBtn" class="btn_edit jkhj_hkchxx_embtn" data-toggle="modal" data-target="#emergencyModal"
						payBackUseContractCode="${item.contractCode}" loanCode = "${item.contract.loanCode}" value="紧急诉讼状态"/>
						<c:if test="${item.loanInfo.blackType != '0'}">
						<input type="button" name="blackBtn" class="btn_edit jkhj_hkchxx_blackbtn" value="黑灰名单标记" data-toggle="modal" data-target="#blackTypeModal"
						payBackUseContractCode="${item.contractCode}" loanCode = "${item.contract.loanCode}"/>
						</c:if></br>
						<input type="button" class="btn_edit jkhj_hkchxx_mainbtn" value="还款信息变更历史" 
						name="paybackChangeHis" contractCode = "${item.contractCode}"/>
						<input type="button" class="btn_edit jkhj_hkchxx_detailbtn" value="还款明细" 
						onclick="getqueryManualOperation('${item.contractCode}','${item.paybackMonth.id }');"/>
					</div>
					</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="pagination">${paybackList}</div>
	<!-- 紧急诉讼start -->
	<div class="modal fade" id="emergencyModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">诉讼状态</h4>
				</div>
				<form id="appplyPayBackUseForm" action="${ctx}/borrow/payback/paybackInfoSearch/updateEmergency" method="post">
					<input type="hidden" id="applyPayBackContractCode" name="contractCode">
					<input type="hidden" id="paybackLoanCode" name="contract.loanCode">
					<div class="modal-body">
						<table id="customerTab">
							<tr>
								<td>
									<label class="lab"> 诉讼状态：</label> 
									<input type="radio" style="margin-left:5px;margin-top:4px" name="loanInfo.emergencyStatus" value="0" />
									发起诉讼
									<input type="radio" style="margin-left:5px;margin-top:4px" name="loanInfo.emergencyStatus" value="1" />
									委托失败
									<input type="radio" style="margin-left:5px;margin-top:4px" name="loanInfo.emergencyStatus" value="2" />
									诉讼中
									<input type="radio" style="margin-left:5px;margin-top:4px" name="loanInfo.emergencyStatus" value="3" />
									诉讼终止
								</td>
							</tr>
							<tr>
								<td style="display:none" id="emergencyTd">
								<span>备注：</span><textarea style="width: 500px; height: 104px;" id="emergencyRemark"></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<a type="button" class="btn btn-primary" data-dismiss="modal" id="emergencySure">确定</a>
						<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 紧急诉讼end -->
	
	<!-- 黑白名单start -->
	<div class="modal fade" id="blackTypeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">黑灰标记</h4>
				</div>
				<form id="blackTypeForm" action="${ctx}/borrow/payback/paybackInfoSearch/updateEmergency" method="post">
					<input type="hidden" id="blackLoanCode" name="contract.loanCode">
					<div class="modal-body">
						<table id="customerTab">
							<tr>
								<td>
									<label class="lab"> 黑灰标记：</label> 
									<input type="radio" style="margin-left:5px;margin-top:4px" name="loanInfo.blackType" value="0" label="黑名单"/>
									黑名单
									<input type="radio" style="margin-left:5px;margin-top:4px" name="loanInfo.blackType" value="1" label="灰名单"/>
									灰名单
								</td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<a type="button" class="btn btn-primary" data-dismiss="modal" id="blackSure">确定</a>
						<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- 黑白名单end -->
</body>
</html>