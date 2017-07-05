<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
</head>
<body>
<div class="pb5 pt10 pr30 title ohide" style="margin-left:20px">
	<table class="table table-bordered table-condensed table-hover ">
		<tbody>
			<tr>
				<td>
					<label class="lab">合同编号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.contractCode}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">维护类型：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.maintainType}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">客户身份证号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.customerCard}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.customerName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">新手机号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.newCustomerPhone}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">原手机号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.customerPhone}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">账号姓名：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.accountName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">新金账户卡号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.account}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">原金账户卡号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.oldAccount}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">开户行：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.bankNames}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">开户行支行：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.bankBranch}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">划扣账号：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.oldAccount}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">划扣平台：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.deductPlatName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">省份：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.provinceName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">城市：</label>
					<input type="text" class="input-medium" value="${repayAccountApplyView.cityName}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">上传附件：</label>
					<c:if test="${repayAccountApplyView.fileId != null && repayAccountApplyView.fileName != null}">
						<a class="input-medium" href="${ctx}/borrow/account/repayaccount/downloadFile?id=${repayAccountApplyView.fileId}
								&name=${repayAccountApplyView.fileName}">${repayAccountApplyView.fileName}</a>
					</c:if>
					<%-- <c:if test="${repayAccountApplyView.phoneFileId != null && repayAccountApplyView.phoneFileName != null}">
						<a class="input-medium" href="${ctx}/borrow/account/repayaccount/downloadFile?id=${repayAccountApplyView.phoneFileId}
								&name=${repayAccountApplyView.phoneFileName}">${repayAccountApplyView.phoneFileName}</a>
					</c:if> --%>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="left"><h6>审核结果</h6></td>
			</tr>
			<tr>
				<td colspan="3" align="left">
					<label class="lab">审核结果：</label>
					<label class="lab">${repayAccountApplyView.maintainStatusName}</label>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="left">
					<label class="lab" >审核意见：</label>
					<textarea class="input-xlarge" name="remarks" rows="3" id="remarks" readonly="readonly"
									maxlength="200" style="width: 320px; height: 40px;">${repayAccountApplyView.remarks}</textarea>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</body>
</html>