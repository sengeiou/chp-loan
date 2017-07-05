<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/credit/creditReportCheck.js?version=1"></script>
<script type="text/javascript">
	$(function(){
		getRiskList();
	});
</script>
<meta name="decorator" content="default" />

	<!-- 简版选择主借人、共借人 -->
	<div id="creditReportSimple" style="width:100%;height:100%;overflow:auto;" >
		<input type="hidden" name="loanCode" value="${loanCode}">
		<form id="creditReportSimpleForm" action="" method="post">
			<input type="hidden" id="creditReportSimpleLoanCode" name="creditReportSimple.loanCode" value="${creditReportSimple.loanCode }">
			<input type="hidden" id="creditReportSimpleCustomerId" name="creditReportSimple.customerId" value="${creditReportSimple.customerId }">
			<br>
			<br>
			<table class="table1" cellpadding="0" cellspacing="0" border="0px" width="100%">
				<tr>	
					<td>
						<label class="lab">主借人：</label>
						<a href="javascript:void(0);">
							${loanCustomer.customerName }
						</a>
					</td>
					<td>
						<input name="versionHID" str="${loanCustomer.id}" type="hidden" value=""/>
						<label><input type="radio" onclick="changeVersion(this);" mark="${loanCustomer.id}" name="creditVersion" value="1"/>详版</label>
						<label><input type="radio" onclick="changeVersion(this);" mark="${loanCustomer.id}" name="creditVersion" value="2"/>简版</label>
					</td>
					<td>
						<input type="button" class="btn btn-small" onClick="readyParam('${creditReportSimple.customerId}','0','${loanCustomer.loanCode}',this)" value="录入"/>
					</td>
				</tr>
				<tr>
					<td><hr></td>
					<td><hr></td>
					<td><hr></td>
				</tr>
				<c:forEach items="${LoanCoborrowerList}" var="item" varStatus="var">
				<tr>
					<td>
						<label class="lab">
							<c:choose>
								<c:when test="${ loanInfoOldOrNewFlag == '1'}">自然人保证人：</c:when>
								<c:otherwise>共借人：</c:otherwise>
							</c:choose>
						</label>
						<a href="javascript:void(0);">${item.coboName}</a>
					</td>
					<td>
						<input name="versionHID" str="${item.id}" type="hidden" value=""/>
						<label><input type="radio" onclick="changeVersion(this);" mark="${item.id}" name="creditVersion${var.index}" value="1"/>详版</label>
						<label><input type="radio" onclick="changeVersion(this);" mark="${item.id}" name="creditVersion${var.index}" value="2"/>简版</label>
						
					</td>
					<td>
						<input type="button" class="btn btn-small" onClick="readyParam('${item.id}','1','${loanCustomer.loanCode}',this)" value="录入"/>
					</td>
				</tr>
				</c:forEach>
			</table>
		</form>	 
	</div> 
