<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>提醒还款待办列表</title>
<script type="text/javascript" src="${context}/js/payback/remindRepaymentAgencyPhoneSale.js">
</script>
</head>
<body>
	<div class="control-group">
	<form id="auditForm" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><input name="customerName" type="text" class="input_text178" value="${RepaymentReminderEx.customerName }"></input></td>
                <td><label class="lab">合同编号：</label><input name="contractCode" type="text" class="input_text178" value="${RepaymentReminderEx.contractCode }"></input></td>
                <td><label class="lab">期供还款日期：</label><input id="paybackMonthMoneyDate" name="paybackMonthMoneyDate" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate"
							value="<fmt:formatDate value="${RepaymentReminderEx.paybackMonthMoneyDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></input>
				</td>
            </tr>
            
            <tr>
                 <td><label class="lab">划扣平台：</label><select class="select180" name="dictDealType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="dictDeType">
                                   <option value="${dictDeType.value }" <c:if test="${RepaymentReminderEx.dictDealType == dictDeType.value }">selected</c:if>>
                                   		${dictDeType.label }
                                   </option>
                              </c:forEach>
                	</select>
                </td>
                <td>
                	<label class="lab">还款日：</label><select class="select180" id="repaymentDate" name="repaymentDate">
                		<option value="">全部</option>
                		<c:forEach items="${billDayList}" var="version">
								<option value="${version.billday}"
									<c:if test="${version.billday == RepaymentReminderEx.repaymentDate}">
										selected = true
									</c:if>>
									${version.billday}
								</option>
							</c:forEach>
                	</select>
                	<%-- <input id="repaymentDate" value="${RepaymentReminderEx.repaymentDate }" type="number" name="repaymentDate" class="input_text178"> --%>
                </td>
            </tr>
        </table>
		
		 <div class="tright pr30 mb5" >
		    <button class="btn btn-primary" id="searchBtn">搜索</button>
			<input type="button" class="btn btn-primary" id="clearBtn" value="清除" />
		 </div>
		</div>
		</form>
	<div class="box5">
    <table id="#tableList" class="table table-hover table-bordered table-condensed">
       <thead>
						<tr>
							<th>序号</th>
							<th >合同编号</th>
							<th >客户姓名</th>
							<th >门店名称</th>
							<th >开户行名称</th>
							<th >手机号码</th>
							<th >合同到期日</th>
							<th >期数</th>
							<th >还款日</th>
							<th >月还期供金额</th>
							<th >已还金额</th>
							<th >蓝补金额</th>
							<th >借款状态</th>
							<th >期供状态</th>
							<th >渠道</th>
							<th >划扣平台</th>
							<th>团队经理</th>
				            <th>客户经理</th>
				            <th>外访</th>
				            <th>客服</th>
						</tr>
						</thead>
					<tbody id="repaymentReminder">
						<c:forEach items="${waitPage.list}" var="item" varStatus="num">
							<tr style="text-align: center;">
								<td style="padding-left: 12px;">${num.index +1 }</td> 
								<td>${item.contractCode}</td>
								<td>${item.customerName}</td>
								<td>${item.stroeName}</td>
								<td>${item.applyBankNameLabel}</td>
								<td>${item.offendTel}</td>
								<td><fmt:formatDate value="${item.contractEndTimestamp}" type="date"/></td>
								<td>${item.months}</td>
								<td><fmt:formatDate value="${item.monthPayDay}" pattern="dd"/></td>
								<td><fmt:formatNumber value='${item.payMoney}' pattern="0.00"/></td>
								<td><fmt:formatNumber value='${item.completeMoney}' pattern="0.00"/></td>
								<td><fmt:formatNumber value='${item.buleAmont}' pattern="0.00"/></td>
								<td>${item.dictLoanStatusLabel}</td>
								<td>${item.dictMonthStatusLabel}</td>
								<td>${item.logoLabel}</td>
								<td>${item.dictDealTypeLabel}</td>
								<td>${item.loanTeamManagerName}</td>
					        	<td>${item.loanManagerName}</td>
					        	<td>${item.loanSurveyEmpName}</td>
					        	<td>${item.loanCustomerService}</td>
							</tr>
						</c:forEach>
						<c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
							<tr>
								<td colspan="22" style="text-align: center;">没有符合条件的数据</td>
							</tr>
						</c:if>
					</tbody>
         
    </table>
	</div>

</div>
	
<div class="pagination">${waitPage}</div>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>