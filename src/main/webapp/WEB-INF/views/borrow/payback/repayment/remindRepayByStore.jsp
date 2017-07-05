<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>提醒还款待办列表</title>
<script type="text/javascript" src="${context}/js/payback/remindRepaymentByStore.js">
</script>
</head>
<body>
	<div class="control-group">
		<form id="auditForm" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">客户姓名：</label>
						<input name="customerName" value="${repay.customerName }" type="text" class="input_text178" />
					</td>
					<td>
						<label class="lab">合同编号：</label>
						<input name="contractCode" value="${repay.contractCode }" type="text" class="input_text178" />
					</td>
					<td>
						<label>期供还款日期：</label>
						<input id="paybackMonthMoneyDate" name="paybackMonthMoneyDate" type="text" readonly="readonly" maxlength="20" class="input_text178 Wdate"
							value="<fmt:formatDate value="${repay.paybackMonthMoneyDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"></input>
					</td>
				</tr>            
				<tr>
					<td>
						<label class="lab">划扣平台：</label>
						<select class="select180" name="dictDealType">
							<option value="" selected>请选择</option>
								<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="dictDeType">
									<option value="${dictDeType.value }"
										<c:if test="${repay.dictDealType == dictDeType.value }">selected</c:if>>${dictDeType.label }
									</option>
								</c:forEach>
						</select>
					</td>
					<td>
						<label class="lab">还款日：</label>
						<input id="repaymentDate" value="${repay.repaymentDate }" type="number" name="repaymentDate" class="input_text178">
			
					</td>
				</tr>
			</table>		
			<div class="tright pr30 mb5" >
				<button class="btn btn-primary" id="searchBtn">搜索</button>
				<button class="btn btn-primary" id="clearBtn">清除</button>
			</div>
		</form>
	</div>
	<div>	
	<sys:columnCtrl pageToken="remin"></sys:columnCtrl>
		<div class="box5">
			<table id="tableList" class="table table-hover table-bordered table-condensed" style="margin-bottom:100px">
				<thead>
					<tr>
						<th>序号</th>
						<th>合同编号</th>
						<th>客户姓名</th>
						<th>门店名称</th>
						<th>开户行名称</th>
						<th>手机号码</th>
						<th>合同到期日</th>
						<th>期数</th>
						<th>还款日</th>
						<th>月还期供金额</th>
						<th>已还金额</th>
						<th>蓝补金额</th>
						<th>借款状态</th>
						<th>期供状态</th>
						<th>渠道</th>
						<th>划扣平台</th>
						<th>门店备注</th>
						<th>操作</th>
					</tr>
				</thead>		
				<tbody id="repaymentReminder">
					<c:forEach items="${waitPage.list}" var="item" varStatus="num">
						<tr>
							<td style="padding-left: 12px;">${num.index +1 }</td> 
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td>${item.stroeName}</td>
							<td>${item.applyBankName}</td>
							<td>${item.offendTel}</td>
							<td><fmt:formatDate value="${item.contractEndTimestamp}" type="date"/></td>
							<td>${item.months}</td>
							<td><fmt:formatDate value="${item.monthPayDay}" type="date"/></td>
							<td><fmt:formatNumber value='${item.payMoney}' pattern="0.00"/></td>
							<td><fmt:formatNumber value='${item.completeMoney}' pattern="0.00"/></td>
							<td><fmt:formatNumber value='${item.buleAmont}' pattern="0.00"/></td>
							<td>${item.dictLoanStatusLabel}</td>
							<td>${item.dictMonthStatusLabel}</td>
							<td>${item.logoLabel}</td>
							<td>${item.dictDealTypeLabel}</td>
							<td>
								<p style="width:50px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;" title="${item.storeRemark}">               					                		
                				${item.storeRemark }
								</p>
							</td>
							<td class="tcenter">
								<input type="button" class="btn_edit" id="${item.storeRemark }" name="${item.id }" onclick="addRemark(this) " value="添加备注">
							</td>
						</tr>
					</c:forEach>
					<c:if test="${waitPage.list==null || fn:length(waitPage.list)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>	
<div class="pagination">${waitPage}</div>
<div id="remarkByStore" style="width:60%;height:80%;display:none">    
	<form id="remarkForm" method="post" >	 
		<textarea name="storeRemark" id="reamrkVal" style="width:500px;height:70px" ></textarea>		
		<div class="tright mr10 mb5">
			<input type="button" value="确定" id="remarkSubId" class="btn btn-primary" onclick="remarkSub(this)"/>
			<input type="button" value="取消" class="btn btn-primary" onclick="remarkBack()"/>
		</div>
	</form>
</div>
</body> 
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
</html>