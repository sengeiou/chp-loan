<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
</head>
<body>
	<table class="table table-bordered table-condensed  ">
		<tbody>
			<tr>
				<td>
					<label class="lab">合同编号：</label>
					<input type="text" class="input-medium" value="${infoView.contractCode}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">维护类型：</label>
					<input type="text" class="input-medium" value="${infoView.dictMaintainTypeName}" readonly="readonly"/>
				</td>
				<c:if test="${infoView.dictMaintainType eq '1' && infoView.updateType eq '1'}">
					<td>
						<label class="lab">新手机号：</label>
						<input type="text"  value="${infoView.newCustomerPhone}"
						class="input-medium" readonly="readonly"/>
					</td>
				</c:if>
			</tr>
			<tr>
				<td>
					<label class="lab">客户姓名：</label>
					<input type="text" class="input-medium" value="${infoView.customerName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">客户身份证号：</label>
					<input type="text" class="input-medium" value="${infoView.customerCertNum}"  readonly="readonly"/>
				</td>
				<td>
					<label class="lab">客户手机号：</label>
					<input type="text" class="input-medium" value="${infoView.customerPhoneFirst}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">账号姓名：</label>
					<input type="text" class="input-medium" value="${infoView.bankAccountName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">划扣账号：</label>
					<input type="text" class="input-medium" value="${infoView.bankCardNo}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">划扣平台：</label>
					<input type="text" class="input-medium" value="${infoView.bankSigningPlatform}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">开户行：</label>
					<input type="text" class="input-medium" value="${infoView.cardBankName}" readonly="readonly"/>
				</td>
				<td>
					<label class="lab">开户行支行：</label>
					<input type="text" class="input-medium" value="${infoView.applyBankName}" readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">上传附件：</label>
					<a class="input-medium" href="${ctx}/car/carBankInfo/carCustomerBankInfo/downloadFile?id=${infoView.fileId}
							&name=${infoView.fileName}">${infoView.fileName}</a>
				</td>
			</tr>
			<tr>
				<td colspan="3"><h4>审核结果 </h4></td>
			</tr>
			<tr>
				<td>
					<label class="lab">审核结果：</label>
				</td>
				<td>
					<label >
						<c:if test="${infoView.bankCheckResult eq '1'}">
							拒绝
						</c:if>
						<c:if test="${infoView.bankCheckResult eq '2'}">
							通过
						</c:if>
					</label>
				</td>
			</tr>
			<tr>
				<td>
					<label class="lab">审核意见：</label>
				</td>
				<td>
					<label>${infoView.bankCheckDesc}</label>
				</td>
			</tr>
		</tbody>
	</table>
</body>
</html>