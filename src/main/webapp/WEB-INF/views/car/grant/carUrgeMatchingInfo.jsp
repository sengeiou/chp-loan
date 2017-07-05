<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/car/grant/carUrgeMatchingInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<style type="text/css">
#file {
	border-radius: 8px;
	width:70%;
	height:70%;
	margin：auto;
}
</style>
</head>
<body>
	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}">
		<button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>
	<form id="paybackInfoForm" action="${ctx}/car/grant/urgeCheckMatch/save" method="post" class="form-horizontal">
		<input type="hidden" name="urgeServicesCheckApply.id" value="${paybackApply.urgeServicesCheckApply.id}" /> 
		<input type="hidden" name="urgeId" value="${paybackApply.urgeId}" /> 
		<input type="hidden" name="id" value="${paybackApply.id}" /> 
		<input type="hidden" id="infoId" />
		<h2 class="f14 pl10">基本信息&nbsp;&nbsp;&nbsp;</h2>
		<div class="box2 mb10" style='border-top: none'>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> 
						<input type="text" class="input_text178" name="customerName" value="${paybackApply.customerName }" readonly></td>
					<td><label class="lab">证件号码：</label> 
						<input type="text" class="input_text178" name="customerCertNum" value="${paybackApply.customerCertNum }" readonly></td>
					<td><label class="lab">合同编号：</label> 
						<input type="text" class="input_text178" name="contractCode" value="${paybackApply.contractCode }" readonly /></td>
				</tr>
				<tr>
					<td><label class="lab">产品类型：</label> 
						<input type="text" class="input_text178" name="productName" value="${paybackApply.productName}" readonly /></td>
					<td><label class="lab">合同金额（元）：</label> <input type="text"
						class="input_text178"
						value="<fmt:formatNumber value='${paybackApply.contractAmount }' pattern="#,##0.00"/>"
						readonly> <input type="hidden" class="input_text178"
						name="contractAmount"
						value="<fmt:formatNumber value='${paybackApply.contractAmount }' pattern="0.00"/>"
						readonly></td>
					<td><label class="lab">放款金额（元）：</label> <input type="text"
						class="input_text178"
						value="<fmt:formatNumber value='${paybackApply.grantAmount}' pattern="#,##0.00"/>"
						readonly> <input type="hidden" class="input_text178"
						name="grantAmount"
						value="<fmt:formatNumber value='${paybackApply.grantAmount}' pattern="0.00"/>"
						readonly></td>
				</tr>
				<tr>
					<td><label class="lab">借款期限：</label> <input type="text"
						class="input_text178" name="contractMonths"
						value="${paybackApply.contractMonths }" readonly></td>
					<td><label class="lab">借款状态：</label> <input type="text"
						class="input_text178" name="dictLoanStatus"
						value="${paybackApply.dictLoanStatus}"
						readonly></td>
					<td><label class="lab">渠道：</label> <input type="text"
						class="input_text178" name="loanFlag"
						value="${paybackApply.loanFlag}"
						readonly></td>
				</tr>
				<tr>
					<td><label class="lab">门店名称：</label> <input type="text"
						class="input_text178" name="storeName" value="${paybackApply.storeName }"
						readonly></td>
					<td><label class="lab">待催收金额：</label> <input type="text"
						class="input_text178" name="waitUrgeMoeny"
						value="${paybackApply.waitUrgeMoeny}" readonly></td>
				</tr>
			</table>
		</div>
		<h2 class="f14 pl10">汇款账户</h2>
		<div class="box2 mb10" id="qishu_div1">
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" id="applyReallyAmountFormat" class="input_text178" value="<fmt:formatNumber value='${paybackApply.urgeServicesCheckApply.urgeReallyAmount }' pattern="#,##0.00"/>" readonly/></td>
						<input type="hidden" id="applyReallyAmount" name="urgeServicesCheckApply.urgeReallyAmount" value="<fmt:formatNumber value='${paybackApply.urgeServicesCheckApply.urgeReallyAmount }' pattern="0.00"/>"/></td>
						<input type="hidden" id="applyAmount" name="urgeServicesCheckApply.urgeApplyAmount" value="<fmt:formatNumber value='${paybackApply.urgeServicesCheckApply.urgeApplyAmount }' pattern="0.00"/>" />
					</td>
					<td><label class="lab">存入银行：</label> <select
						class="select180" id="storesInAccount"
						name="paybackTransferInfo.storesInAccount" disabled>
							<option>请选择</option>
							<c:forEach var="item" items="${middlePersonList }">
								<option value="${item.bankCardNo }"
									<c:if test="${paybackApply.urgeServicesCheckApply.dictDepositAccount==item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
							</c:forEach>
					</select></td>
					<td><label class="lab">还款申请日期：</label> <input type="text"
						name="urgeServicesCheckApply.urgeApplyDate" class="input_text178"
						value="<fmt:formatDate value='${paybackApply.urgeServicesCheckApply.urgeApplyDate}' type='date' pattern="yyyy-MM-dd" />"
						readonly /></td>
				</tr>
			</table>
		</div>
		<div id="paybackTransferInfo">
			<table id='appendTab' class="table table-bordered table-condensed"
				cellpadding=" 0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>存款方式</td>
					<td>存款时间</td>
					<td>实际到账金额</td>
					<td>实际存入人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${paybackTransferInfoList}" var="item"
					varStatus="status">
					<tr>
						<td>
							<input type="hidden" class="input-mini" name="paybackTransferInfo.dictDeposit" />
							${item.dictDeposit }
						</td>
						<td><input type="hidden" name="paybackTransferInfo.tranDepositTime" /> 
							<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />
						</td>
						<td><input type="hidden" class="input-mini" />
							<fmt:formatNumber value='${item.reallyAmount }' pattern="#,##0.00" />
						</td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.depositName" />
							${item.depositName }
						</td>
						<td><input type="button" class="btn_edit" name="previewPngBtn" 
							value="${item.uploadFilename }" docId=${item.uploadPath }>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadFilename" />&nbsp;
							${item.uploadName }
						</td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadDate" readOnly /> 
							<fmt:formatDate value='${item.uploadDate }' type="both"/>
						</td>
						<td><c:choose>
								<c:when test="${item.auditStatus==2}">
									<input type="button" class="btn_edit" value="已匹配" />
								</c:when>
								<c:otherwise>
									<input type="button" class="btn_edit" data-toggle="modal" data-target="#matchingPayMal"
										id="matchingPayback_${status.index}" name="matchingPayback" value="匹配" infoId="${item.id }"
										tranDepositTime="<fmt:formatDate value='${item.tranDepositTime}' type='date' pattern="yyyy-MM-dd" />" reallyAmount="${item.reallyAmount }">
								</c:otherwise>
							</c:choose> 
							<a type="button" class="btn_edit" id="downPng" name="downPng" docId=${item.uploadPath } fileName="${item.uploadFilename }">下载凭证</a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
		<div class="box2 mb10" id="qishu_div1">
			<tr>
				<td><label class="lab">匹配结果：</label>&nbsp; 
				<input type="radio" id="urgeDictPayResultGo"  name="dictPayResult" value="0" required> 匹配成功 
				<input type="radio" id = "urgeDictPayResultBack" name="dictPayResult" value="1" required> 匹配退回
				</td>
			</tr>
		</div>
		<div id="shhy" class="box2 mb10"  style="display: none">
			<label class="lab">审核意见：</label>
			<textarea class="form-control" id="applyBackMes" name="urgeServicesCheckApply.urgeBackReason"></textarea>
		</div>
				
		<div class="tright mt10 pr30" id="qishu_div4">
			<input class="btn btn-primary" type="submit" id="applyPaySaveBtn"
				value="确认" /> <input class="btn btn-primary" type="button"
				id="btnCancel" value="返 回" onclick="history.go(-1)" />
		</div>
	</form>

	<div class="modal fade" id="matchingPayMal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width: 900px;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h4 class="modal-title">手动匹配</h4>
				</div>
				<table class="table" id="urgeMatchingTable" data-toggle="table"
					data-pagination="true">
					<thead>
						<tr>
							<th data-field="orderNumber">序号</th>
							<th data-field="outDepositTime" data-formatter="dataFormatter">存款日期</th>
							<th data-field="outReallyAmount" data-formatter="numberFormatter">实际到账金额</th>
							<th data-field="midBankName">存入银行</th>
							<th data-field="outDepositName">存款人</th>
							<th data-field="outRemark">备注</th>
							<th data-radio="true">操作</th>
						</tr>
					</thead>
				</table>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal"
						id="matchingSemiautomatic">提交更改</a>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
