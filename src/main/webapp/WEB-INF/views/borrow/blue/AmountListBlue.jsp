<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackBlueList.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
</head>
<body>
	<!-- <div class="control-group"> -->
			<%-- <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" name="customerName" value="${customer.customername}" class="input_text178" value="" readonly="readonly"/>
					</td>
					<td><label class="lab">合同编号：</label> 
						<input type="text" name="contractCode" value="${contractcode}" class="input_text178" readonly="readonly"/>
					</td>
					<td><label class="lab">蓝补余额：</label> 
						<input type="text" name="paybackBuleAmount" value="<fmt:formatNumber value='${customer.surplusbuleamount}' pattern='0.00'/>" class="input_text178" readonly="readonly"/>
					</td>
				</tr>
			    </table> --%>
			    <form style="overflow-y: hidden;" id="applyPayBackUseForm" action="${ctx}/borrow/blue/PaybackBlue/listBlue" method="post">
					<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		<input type="hidden" id="msg" name="msg" value="${message}">
		<input type="hidden" name="contractCode" value="${contractcode}"/>
			   <%--  <table>
				<tr>
					<td cols='3'><label class="lab" style="text-align: right">查询时间：</label>
								<input id="startDate"
						name="startDate" type="text"
						class="input_text70 required Wdate" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDate\')}'})"
						style="cursor: pointer" value="${search.startDate}" readonly /> -
						<input id="endDate"
						name="endDate" type="text" class="input_text70 Wdate"
						size="10"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDate\')}'})"
						style="cursor: pointer" value="${search.endDate}" readonly /> 
					</td>
					<td>
					
					</td>
				</tr>
				
			</table> --%>
		</form>
		<!-- <div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
				<input type="button" class="btn btn-primary" id="applyPayBackUseClearBtn" value="清除">
				<input type="button" class="btn btn-primary"  id="backBtn" value="返回">
		</div> -->
	<!-- </div> -->
	<p class="mb5">
    	<button class="btn btn-small" id="dao">导出excel</button>
    			</p>
	<div>
		<table  class=" table  table-bordered table-condensed table-hover " >
			<thead>
				<tr>
					<th width="38px">序号</th>
					<th width="88px">交易时间</th>
					<th width="71">交易动作</th>
				
					<th width="71">操作人</th>
					<th width="171">交易用途</th>
					<th width="71">冲抵期数</th>
					<th width="105">交易金额</th>
					
					<th width="98">蓝补余额</th>
				</tr>
			</thead>
			<c:forEach items="${paybackList.list}" var="item" varStatus="sta">
				<tr>
					<td width="38px">${sta.index + 1}</td>
					<td width="88"><fmt:formatDate value="${item.dealTime}" type="date" /></td>
					<td width="71">${item.tradeType}</td>
					
					<td width="71">${item.operator}</td>
					<td width="171">${item.dictDealUse}</td>
					<td width="171">${item.offsetRepaymentDate}</td>

					<td width="105"><fmt:formatNumber value="${item.tradeAmount }" pattern='0.00'/></td>
					<td width="98"><fmt:formatNumber value="${item.surplusBuleAmount }" pattern='0.00'/></td>
					
				
				</tr>
			</c:forEach>
		</table>
	</div>
	<br><br>
	<div class="pagination">${paybackList}</div>
	
</body>
</html>