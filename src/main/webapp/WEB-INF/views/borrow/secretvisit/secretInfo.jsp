<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<title>暗访办理</title>
<head>
<meta name="decorator" content="default" />
<script src='${context}/js/secret/secret.js' type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script> 
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" /> 	
<script type="text/javascript">
function page(n, s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#supplyForm").submit();
	return false;
}
</script>
</head>
<body>
    <div style="height:100%">
    <div style="float:right;width:39.8%;height:100%;position:relative;">
    <div class="tright pr10 pt5 pb5">
				<c:if test="${secretInfo.visitFlag != '1' }">
					<button class="btn btn-small" id="${secretInfo.loanCode }" onclick="secretOk(this)">暗访完成</button>				
				</c:if>
				<button class="btn btn-small" id="${secretInfo.applyId }"  onclick="showLoanHis(this.id)">历史</button>
				<button class="btn btn-small" onclick="location.href='javascript:history.go(-1);'">返回</button>	
	</div>
	<h2 class="f14 pl10">暗访信息</h2>
	<div class="control-group">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab">门店名称:</label>
						${secretInfo.orgName }
					</td>
					<td>
						<label class="lab">合同编号:</label>
						${secretInfo.contractCode }						
					</td>
					</tr>
		        <tr>
					<td>
						<label class="lab">客户姓名:</label>
						${secretInfo.customerName }	
					</td>
					<td>
						<label class="lab">共借人:</label>
						${secretInfo.coboName }	
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">借款产品:</label> 
						${secretInfo.productName }	
					</td>
					<td>
						<label class="lab">借款金额:</label>
						<fmt:formatNumber value="${secretInfo.auditAmount }" pattern="#,#00.00" />
					</td>
			   </tr>
			   <tr>
					<td>
						<label class="lab">借款期限:</label>
						${secretInfo.contractMonths }	
					</td>
				</tr>				
			</table>
		</div>
		<div class="box5" style="height:250px">			
			<form action="${ctx}/borrow/secret/getInfoByCode?contractCode=${secretInfo.contractCode}" id="supplyForm">
				<input id="pageNo" name="pageNo" type="hidden"	value="${page.pageNo}" />
				<input id="contractCode" name="contractCode" type="hidden"	value="${secretInfo.contractCode}" />  
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<table class="table table-hover table-bordered table-condensed">
					<thead>
						<th>期数</th>
						<th>月还款金额</th>
						<th>还款日</th>
						<th>实际还款日</th>
						<th>是否逾期</th>
					</thead>
					<c:forEach var="sec" items="${supplyPage.list }" varStatus="sta">
						<tbody>
							<td>${sec.months }</td>
							<td><fmt:formatNumber value="${sec.monthRepayAmount }" pattern="#,#00.00" /></td>
							<td><fmt:formatDate value="${sec.monthPayDay }" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatDate value="${sec.monthPayActualday }" pattern="yyyy-MM-dd"/></td>
							<td>${secret.isOverdue}</td>
						</tbody>				
					</c:forEach>
					<c:if test="${supplyPage.list==null || fn:length(supplyPage.list)==0}">
						<tr>
							<td colspan="6" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if> 	
				</table>
			</form>
			
			<div class="pagination" >
			${supplyPage }
		    </div>
		   
		</div>
	</div>
	<!-- 影像 -->
	 <div style="float:left;width:60%;height:100%;position:absolute;top:0;">
		<iframe id="ckfinder_iframe" scrolling="yes" frameborder="0" width="100%" height=99%
				src="${secretInfo.imageUrl }">
		</iframe>
	</div> 
	</div>
</body>
</html>
