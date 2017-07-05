<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>风控手动冲抵查看页面</title>
<script type="text/javascript" src="${context}/js/payback/seeManualOperation.js"></script>
</head>
<body>
   <p style="padding-left: 15px; color: #191970;height: 30px;">
   		<button class="btn btn-small" name="exportExcel" value="${manualOperation.contractCode }">导出Excel</button>&nbsp;&nbsp;
   		身份证号： <label>${manualOperation.customerCertNum }</label>&nbsp;&nbsp;
   		放款金额： <fmt:formatNumber value='${manualOperation.grantAmount }' pattern="#,##0.00"/>&nbsp;&nbsp;
		已还期供金额： <fmt:formatNumber value='${manualOperation.sunAmount }' pattern="#,##0.00"/>&nbsp;&nbsp;
		借款状态：${manualOperation.dictPayStatusLabel}&nbsp;&nbsp;
		未还违约金及罚息总额：<fmt:formatNumber value='${manualOperation.notPenaltyPunishShouldSum }' pattern="#,##0.00"/>
   	</p>
	<form id="auditForm" method="post">
		<input id="contractCode" name="contractCode" type="hidden" value="${manualOperation.contractCode}" />
	</form>
     <div>
		<table id="tableList" class="table  table-bordered table-condensed table-hover">
		<thead>
		<tr>
		    <th>还款分期序号</th>
		    <th>合同编号</th>
		    <th>产品类型</th>
		    <th>客户姓名</th>
		    <th>账单日</th>
		    <th>账单金额（期供）</th>
		    <th>当期期供已还</th>
		    <th>当期期供未还</th>
		    <th>提前结清一次性金额</th>
		    <th>应还罚息及违约金(滞纳金)</th>
		    <th>实还罚息及违约金(滞纳金)</th>
		    <th>减免罚息及违约金(滞纳金)</th>
		    <th>未还罚息及违约金(滞纳金)</th>
		    <th>是否逾期</th>
		    <th>当期逾期天数</th>
		    <th>期供状态</th>
		    <th>渠道</th>
		</tr>
		</thead>
		<c:forEach items="${waitPage}" var="item" varStatus="num">
	        <tr>
	        	 <td>${num.index + 1 }</td>
	        	<td>${item.contractCode}</td>
	        	<td>${item.productType}</td>
	        	<td>${item.customerName }</td>
	        	<td><fmt:formatDate value="${item.payBack}" type="date"/></td>
	        	<td><fmt:formatNumber value='${item.contractMonthRepayAmount}' pattern="#,##0.00"/></td>
	        	<td><fmt:formatNumber value='${item.hisOverpaybackMonthMoney}' pattern="#,##0.00"/></td>
	        	<td>
	        		<c:if test="${item.notOverpaybackMonthMoney==0}">
	        			0
	        		</c:if>
	        		<c:if test="${item.notOverpaybackMonthMoney!=0}">
	        			<fmt:formatNumber value='${item.notOverpaybackMonthMoney}' pattern="#,##0.00"/>
	        		</c:if>
	        	</td>
	        	<td><fmt:formatNumber value='${item.monthBeforeFinishAmount}' pattern="#,##0.00"/></td>
	        	<td><fmt:formatNumber value='${item.interestPenaltyPunishShould}' pattern="#,##0.00"/></td>
	        	<td><fmt:formatNumber value='${item.penaltyPunishActualSum}' pattern="#,##0.00"/></td>
	        	<td><fmt:formatNumber value='${item.penaltyPunishReductionSum}' pattern="#,##0.00"/></td>
	        	<td><fmt:formatNumber value='${item.interestPenaltyPunishShould-item.penaltyPunishActualSum-item.penaltyPunishReductionSum}' pattern="#,##0.00"/></td>
	        	<td>${item.isOverdue }</td>
	        	<td>${item.monthOverdueDays}</td>
	        	<td>${item.dictMonthStatusLabel}</td>
	        	<td>${item.loanMarkLabel}</td>
	        </tr>
        </c:forEach>
		</table>
     </div>
</body>
</html>