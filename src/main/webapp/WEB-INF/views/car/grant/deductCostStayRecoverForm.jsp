<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>放款确认</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script src="${context}/js/car/grant/carUrgeGuarante.js" type="text/javascript"></script>
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
	<div class="tright pr30 pt5 mb5">
		<input type="button" data-target="#back_div" data-toggle="modal" name="back" onclick="showCarLoanHis('${UrgeServicesMoneyEx.loanCode}')" value="历史"  class="btn btn-small" ></input>
		<button class="btn btn-small" id="btCheck" sid="${UrgeServicesMoneyEx.urgeId}">提交查账</button>
		<button class="btn btn-small" id="btDeduct" sid="${UrgeServicesMoneyEx.urgeId}">提交划扣</button>
	</div>
	<h2 class="f14 pl10">催收保证金</h2>
	<form id="applyPayLaunchForm" enctype="multipart/form-data" action="${ctx}/car/grant/deductCost/save" method="post">
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">合同编号：</label>${UrgeServicesMoneyEx.contractCode}
				<input type="hidden" name="loanCode" value="${UrgeServicesMoneyEx.loanCode}"/>
				<input type="hidden" name="urgeServicesCheckApply.contractCode" value="${UrgeServicesMoneyEx.contractCode}"/></td>
				<input type="hidden" name="urgeServicesCheckApply.rServiceChargeId" value="${UrgeServicesMoneyEx.urgeId}"/></td>
				<input type="hidden" name="urgeId" value="${UrgeServicesMoneyEx.urgeId}">
				<input type="hidden" name="urgeServicesCheckApply.id" value="${UrgeServicesMoneyEx.urgeServicesCheckApply.id}">
				<input type="hidden" name="flag" value="${UrgeServicesMoneyEx.flag}"/></td>
				<td><label class="lab">客户姓名：</label>${UrgeServicesMoneyEx.customerName}</td>
				<td><label class="lab">证件号码：</label>${UrgeServicesMoneyEx.customerCertNum}</td>
				<td><label class="lab">借款产品：</label>${UrgeServicesMoneyEx.productName}
				<td>
			</tr>

			<tr>
				<td><label class="lab">划扣费用：</label>${UrgeServicesMoneyEx.urgeMoeny}</td>
				<td><label class="lab">未划金额：</label>${UrgeServicesMoneyEx.waitUrgeMoeny}</td>
				<input type="hidden" id="waitUrgeMoney" value="${UrgeServicesMoneyEx.waitUrgeMoeny}"/>
				<td><label class="lab">实际划扣金额：</label>
				<fmt:formatNumber value='${UrgeServicesMoneyEx.splitAmount}' pattern="#,#00.00" /></td>
			</tr>
			<tr>
				<td><label class="lab ">批借期限：</label>${UrgeServicesMoneyEx.contractMonths}</td>
				<td><label class="lab ">开户行：</label>${UrgeServicesMoneyEx.bankNameLabel}</td>
				<td><label class="lab ">是否电销：</label>${UrgeServicesMoneyEx.customerTelesalesFlagLabel}</td>
				<td><label class="lab">标识：</label>${UrgeServicesMoneyEx.loanFlagLabel}</td>
			</tr>
			<tr>
				<td><label class="lab">放款时间：</label>
				<fmt:formatDate value="${UrgeServicesMoneyEx.lendingTime }"
						type="date" pattern="yyyy-MM-dd" /></td>
				<td><label class="lab">合同金额：</label>
				<fmt:formatNumber value='${UrgeServicesMoneyEx.contractAmount}' pattern="#,#00.00" /></td>
				<td><label class="lab">划扣状态：</label>${UrgeServicesMoneyEx.splitBackResultLabel}</td>
				<input id="splitBackResult" type="hidden"  value="${UrgeServicesMoneyEx.splitBackResultLabel}"/></td>
			</tr>
		</table>
	</div>
	
	<!--查账 by 张峰 -->
	<div class="mt20" id="urgeAudite" style="display: none;">
		<h2 class="f14 pl10">催收服务费汇款账户</h2>
		<div id="qishu_div1" class="box2" style="border-top:0;border-bottom:0">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">实际到账总额：</label> 
						<input type="text" class="input_text178" id="urgeApplyAmount" value="<fmt:formatNumber value='${UrgeServicesMoneyEx.urgeServicesCheckApply.urgeApplyAmount}' pattern="0.00"/>" name="urgeServicesCheckApply.urgeApplyAmount" readonly/>
					</td>
					<td><label class="lab">存入账户：</label> 
					<input type="hidden" id="storesInAccountName" name="paybackTransferInfo.storesInAccountname" />
						<select class="select180" id="urgeStoresInAccount" name="paybackTransferInfo.storesInAccount">
							<option>请选择</option>
							<c:if test="${ paybackTransferInfoList!=null && fn:length(paybackTransferInfoList)>0}">
								<c:forEach var="item" items="${middlePersonList}">
										<option value="${item.bankCardNo }"
											<c:if test="${UrgeServicesMoneyEx.urgeServicesCheckApply.dictDepositAccount == item.bankCardNo }">selected</c:if>>${item.midBankName }</option>
									</c:forEach>
							</c:if>
							<c:if test="${ paybackTransferInfoList==null || fn:length(paybackTransferInfoList)==0}">
								<c:forEach var="item" items="${middlePersonList }">
									<option value="${item.bankCardNo }">${item.midBankName}</option>
								</c:forEach>
							</c:if>
						</select>
					</td>
					<td><label class="lab">还款申请日期：</label> 
						<input type="text" class="input_text178" name="urgeServicesCheckApply.urgeApplyDate" value="<fmt:formatDate value="${UrgeServicesMoneyEx.urgeServicesCheckApply.urgeApplyDate }" type="date" pattern="yyyy-MM-dd"/>" readonly>
					</td>
				</tr>
			</table>
		</div>
		<div  id="qishu_div0">
			<table id='urgeAppendTab' class="table table-bordered">
				<tr>
					<th colspan="8">
							<a  onclick="appendRow()" style="background-size:100% ;height:14px;width:14px" ><img  src="${context }/static/images/jiahao.png"></a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a  onclick="deleteRow()" style="background-size:100%;height:14px;width:14px"><img alt="" src="${context }/static/images/jianhao.png"></a>
						</h1>
					</th>
				</tr>
				<tr>
					<td>存款方式</td>
					<td>到账日期</td>
					<td>实际到账金额</td>
					<td>实际存款人</td>
					<td>存款凭条</td>
					<td>上传人</td>
					<td>上传时间</td>
				</tr>
				<c:if test="${ paybackTransferInfoList!=null && fn:length(paybackTransferInfoList)>0}">
				 <c:forEach items="${paybackTransferInfoList}" var="item">
					<tr id='urgeAppendId'>
						<td><input name="paybackTransferInfo.id" value="${item.id }" type="hidden"/>
							<input type="hidden" name="paybackTransferInfo.uploadPath" value="${item.uploadPath}">
							<input type="hidden" name="paybackTransferInfo.uploadFilename"  value="${item.uploadFilename }">
							<input type="text" id="dictDeposit" class="input-mini" name="paybackTransferInfo.dictDeposit" value="${item.dictDeposit }" /></td>
						<td><input type="text" name="paybackTransferInfo.tranDepositTimeStr" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${item.tranDepositTime}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
						<td><input type="text" id="urgeApplyAmountCopy" name="paybackTransferInfo.reallyAmountStr" value="${item.reallyAmount}" class="input_text178" /></td>
						<td><input type="text" class="input-mini" id="depositName" name="paybackTransferInfo.depositName" value="${item.depositName }" /></td>
						<td><input name="files" type="file">
							<input type="button" class="btn_edit" name = "doPreviewPngBtn" data-toggle="modal" value="${item.uploadFilename }" docId ="${item.uploadPath}"></td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadName" />&nbsp;${item.uploadName }</td>
						<td><input type="hidden" class="input-mini" name="paybackTransferInfo.uploadDate" readOnly /> <fmt:formatDate value='${item.uploadDate }' type='date' pattern="yyyy-MM-dd"/>
						</td>
					</tr>
				</c:forEach>
				</c:if>
				<c:if test="${ paybackTransferInfoList==null || fn:length(paybackTransferInfoList)==0}">
          	 	<tr id='urgeAppendId'>
					<td><input type="text" class="input_text178" id="dictDeposit" name="paybackTransferInfo.dictDeposit" /></td>
					<td><input type="text" name="paybackTransferInfo.tranDepositTimeStr" maxlength="20" class="input-medium Wdate" style="cursor: pointer"
						 value="<fmt:formatDate value="${item.tranDepositTime}" pattern="yyyy-MM-dd"/>" 
						 onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/></td>
					<td><input type="text" class="input_text178" id="urgeApplyAmountCopy" name="paybackTransferInfo.reallyAmountStr" /></td>
					<td><input type="text" class="input_text178" id="depositName" name="paybackTransferInfo.depositName" /></td>
					<td><input name="files" type="file"></td>
					<td><input type="text" class="input_text178" id="uploadName" name="paybackTransferInfo.uploadName" value="${UrgeServicesMoneyEx.paybackTransferInfo.uploadName }"  readonly/>&nbsp;</td>
					<td><input type="text" class="input_text178" id="uploadDate" name="paybackTransferInfo.uploadDate" value="<fmt:formatDate value="${UrgeServicesMoneyEx.paybackTransferInfo.uploadDate }" type="date"/>" readOnly/></td>
				</tr>
         		</c:if>
			</table>
		</div>
	</div>
	<div class="tright mt10 pr30 mb5">
		<input type="button" class="btn btn-primary" value="确认" id="urgeSaveApply" style="display: none;"/>
		<input type="button" class="btn btn-primary" onclick="history.go(-1);" value="返回"/>
	</div>
	</form>
</body>
</html>