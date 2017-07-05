<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/doStorePos.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />

<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet"
	href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script language="javascript">
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#applyPayBackUseForm").attr("action",
				"${ctx}/borrow/poscard/posBacktageList/posAlreadyMatchInfo");
		$("#applyPayBackUseForm").submit();
		return false;
	}
</script>


</head>
<body>
	<div class="control-group">
		<form id="applyPayBackUseForm" action="${ctx}/borrow/poscard/posBacktageList/posAlreadyMatchInfo" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${paybackApplyPage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${paybackApplyPage.pageSize}" />
			<input  name="id" type="hidden" />
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input value="${posBacktage.customerName}" type="text" name="customerName" class="input_text178"></input>
					</td>
					<td><label class="lab">合同编号：</label> 
						<input value="${posBacktage.contractCode}" type="text" name="contractCode" class="input_text178"></input>
					</td>
					<td><label class="lab">订单编号：</label> 
					    <input value="${posBacktage.posBillCode}" type="text" name="posBillCode" class="input_text178"></input>
					</td>
				</tr>
				
				<tr>
					<td><label class="lab">门店：</label> 
						<input value="${posBacktage.loanTeamOrgId}" type="text" name="loanTeamOrgId" class="input_text178"></input>
					</td>
					<td><label class="lab">实还金额：</label> 
						<input value="${posBacktage.applyReallyAmount}" type="text" name="applyReallyAmount" class="input_text178"></input>
					</td>
					<td><label class="lab">还款状态：</label> 
						<select name="posBacktage.dictPayStatus" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_repay_status')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${posBacktage.dictPayStatus == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                            </c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td><label class="lab" style="text-align: right">来源系统：</label>
						<select name="loanInfo.dictSourceType" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${posBacktage.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                            </c:forEach>
						</select>
					</td>
					<td><label class="lab">渠道：</label> 
							<select class="select180" name="loanMark">
	                			<option value="">请选择</option>
	                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
	                                   <option value="${loanMark.value }" <c:if test="${posBacktage.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
	                              </c:forEach>
                			</select>
						</td>
					</td>
					<td><label class="lab">还款日 ：</label>
						 <input value="${posBacktage.paybackDay}" type="text" name="paybackDay" class="input_text178"></input>
					</td>
				</tr>
				<tr>  
				<td>
				<label class="lab">支付成功日期：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${posBacktage.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${posBacktage.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               </td>
			   </tr>
			</table>
		</form>
		<div class="tright pr30 pb5">
			<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
			<input type="button" class="btn btn-primary" id="applyPayBackUseClearBtn" value="清除">
		</div>
	</div>
	
	<div id="auditList">
			<p class="mb5">
				<button class="btn btn-small" id="daoBtnListPosMacth">导出excel</button>
	    <span class="red">实还总金额:</span>
		<span class="red" id="total_money">${numTotal.total}</span>&nbsp;
		<span class="red">实还总笔数:</span>
		<span class="red" id="total_num">${numTotal.num}</span>
			</p>
			<form id="deductInfoFormPos" action="${ctx}/borrow/poscard/posBacktageList/seeStoresAlreadyDo" method="post">
			<input type="hidden" id="deductContractCode" name="contractCode">
			<input type="hidden" id="matchingId" name="id">
			<input type="hidden" id="chargeId" name="paybackCharge.id">
			<input id="ids" name="search_id" type="hidden"	value="${posBacktage.id}" /> 
			<div class="box5">
			<table id="tableList" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
					    <th><input type="checkbox"   class="checkbox" id="checkAll" /></th>
					    <th>POS机订单编号</th>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>合同到期日</th>
						<th>门店名称</th>
						<th>借款状态</th>
						<th>还款日</th>
						<th>支付成功日期</th>
						<th>申请还款金额</th>
						<th>实际还款金额</th>
						<th>还款类型</th>
						<th>还款状态</th>
						<th>回盘结果</th>
						<th>失败原因</th>
						<th>渠道</th>
						<th>是否电销</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="applyPayMatchingListBody">
					<c:forEach items="${paybackApplyPage.list}" var="item">
						<tr>
							 	<td><input type="checkbox" name="checkBox" value="${item.id}" />
	        	           <input type="hidden"  value="${item.applyReallyAmount}" />
	        	                   </td>
						    <td>${item.posBillCode}</td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td><fmt:formatDate value="${item.contractEndDay }" type="date" /></td>
							<td>${item.loanTeamOrgId}</td>
							<td>${item.dictLoanStatusLabel}</td>
							<td>${item.paybackDay}</td>
							<td><fmt:formatDate value="${item.paybackDate }" type="date" /></td>
							<td><fmt:formatNumber value='${item.applyAmount}' pattern="0.00"/></td>
							<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="0.00"/></td>
							<td>${item.dictPayUseLabel}</td>
							<td>${item.dictPayStatusLabel}</td>
							<td>${item.dictPayResultLabel}</td>
							<td>${item.applyBackMes }</td>
							<td>${item.loanFlagLabel}</td>
							<td>${item.customerTelesalesFlagLabel}</td>
							<td><input type="button" class="btn_edit" value="查看" applyPaybackId="${item.id }" chargeId ="${item.id}"
							 name="deductInfoBtnPos" deductContractCode="${item.contractCode}">
						</tr>
					</c:forEach>
					<c:if
						test="${ paybackApplyPage.list==null || fn:length(paybackApplyPage.list)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有查看的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			</div>
			</form>
			</div>

	<div class="pagination">${paybackApplyPage}</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-300,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>