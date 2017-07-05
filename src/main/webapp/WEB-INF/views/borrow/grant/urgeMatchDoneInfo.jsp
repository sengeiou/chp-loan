<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<style type="text/css">
.pagination {
	background: none;
	position: static;
	width: auto
}
</style>

</head>
<body>
	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}"><button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>
	<form id="paybackInfoForm" action="${ctx}/borrow/payback/dealPayback/save" method="post" class="form-horizontal">
		<input type="hidden" name="id" value="${paybackApply.id}" />
		<input type="hidden" id="infoId"/>
		<div class="box2 mb10" style='border-top:none'>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178"  name="customerName" value="${paybackApply.customerName }" readonly>
					</td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178"  name="customerCertNum" value="${paybackApply.customerCertNum }" readonly>
					</td>
					<td><label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${paybackApply.contractCode }" readonly/>
					</td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="text" class="input_text178" name="productName" value="${paybackApply.productName}" readonly/>
					</td>
					<td><label class="lab">合同金额（元）：</label> 
						<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackApply.contractAmount }' pattern="#,##0.00"/>" readonly>
						<input type="hidden"class="input_text178" name="contractAmount" value="<fmt:formatNumber value='${paybackApply.contractAmount }' pattern="0.00"/>" readonly>
					</td>
					<td><label class="lab">放款金额（元）：</label> 
						<input type="text"class="input_text178" value="<fmt:formatNumber value='${paybackApply.grantAmount}' pattern="#,##0.00"/>" readonly>
						<input type="hidden"class="input_text178" name="grantAmount" value="<fmt:formatNumber value='${paybackApply.grantAmount}' pattern="0.00"/>" readonly>
					</td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> 
						<input type="text" class="input_text178" name="contractMonths" value="${paybackApply.contractMonths }" readonly>
					</td>
					<td><label class="lab">借款状态：</label> 
						<input type="text" class="input_text178" name="dictLoanStatus" value="${paybackApply.dictLoanStatusLabel}" readonly>
					</td>
					<td><label class="lab">渠道：</label> 
						<input type="text" class="input_text178" name="loanFlag" value="${paybackApply.loanFlagLabel}" readonly>
					</td>
				</tr>
				<tr>
				<td><label class="lab">门店名称：</label> 
					<input type="text" class="input_text178" name="name" value="${paybackApply.storeName }" readonly>
				</td>
				<td><label class="lab">待催收金额：</label> 
					<input type="text" class="input_text178" name="waitUrgeMoeny" value="${paybackApply.waitUrgeMoeny}" readonly>
				</td>
				</tr>
			</table>
		</div>
				 <h2 class="f14 pl10 mt20">汇款账户</h2>
				<div class="box2 " id="qishu_div1">
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab">实际到账总额：</label> 
							<input type="text" class="input_text178" value="<fmt:formatNumber value='${paybackApply.urgeReallyAmount }' pattern="#,##0.00"/>" readonly/></td>
							<input type="hidden" name="urgeReallyAmount" value="<fmt:formatNumber value='${paybackApply.urgeReallyAmount }' pattern="0.00"/>"/></td>
							<input type="hidden" name="urgeApplyAmount" value="<fmt:formatNumber value='${paybackApply.urgeApplyAmount }' pattern="0.00"/>"/>
							<td><label class="lab">存入银行：</label> 
								<select class="select180" id="midBankName" name="midBankName" disabled>
									<option>${paybackApply.midBankName}</option>
									<%-- <c:forEach var="item" items="${middlePersonList }">
										<option value="${item.bankCardNo }" <c:if test="${paybackApply.storesInAccount==item.bankCardNo }">selected</c:if>>${item.middleName }</option>
									</c:forEach> --%>
								</select>
							</td>
							<td><label class="lab">还款申请日期：</label> 
								<input type="text" name="urgeApplyDate" class="input_text178"
								value="<fmt:formatDate value='${paybackApply.urgeApplyDate}' type='date' pattern="yyyy-MM-dd" />" readonly/>
							</td>
						</tr>
					</table>
				</div>
				<div id="paybackTransferInfo">
					<table id='appendTab' class="table table-bordered table-condensed" cellpadding=" 0" cellspacing="0" border="0" width="100%">
						<tr>
							<td>存款方式</td>
							<td>存款时间</td>
							<td>实际到账金额</td>
							<td>实际存入人</td>
							<td>存款凭条</td>
							<td>上传人</td>
							<td>上传时间</td>
						</tr>
						<c:forEach items="${paybackTransferInfoList}" var="item" varStatus="status">
							<tr>
								<td>
									<input type="hidden" class="input-mini"name="paybackTransferInfo.dictDeposit" />${item.dictDeposit }
								</td>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.tranDepositTime" />
									<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />
								</td>
								<td>
									<input type="hidden" class="input-mini" /><fmt:formatNumber value='${item.reallyAmount }' pattern="#,##0.00"/>
								</td>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.depositName" />${item.depositName }
								</td>
								<td>
									<input type="button" class="btn_edit" name = "previewPngBtn" value="${item.uploadFilename }" docId = ${item.uploadPath }>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.uploadFilename" />&nbsp;${item.uploadName }
								</td>
								<td>
									<input type="hidden" class="input-mini" name="paybackTransferInfo.uploadDate" readOnly />
									<fmt:formatDate value='${item.uploadDate }' type='date' pattern="yyyy-MM-dd" />
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="box2 mt20" id="qishu_div1">
					<tr>
						<td><label class="lab">匹配结果：</label>&nbsp; 
						${paybackApply.urgeApplyStatusLabel}
						</td>
					</tr>
				</div>
				<div class="tright mt10 pr30" id="qishu_div4">
					<input  class="btn btn-primary" type="button" value="返 回" onclick="history.go(-1)" />
				</div>
	</form>
</body>
</html>
