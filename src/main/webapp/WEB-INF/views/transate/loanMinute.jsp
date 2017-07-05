<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信借审批信息详情</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet"
	href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/transate/transate.js"></script>
</head>
<body>

	<div class=" pb5 pt5 pr30 title ohide">
		<div class="r">
			<input type="hidden" id="url" value="${url }"><input type="hidden" id="loanCode" value="${lm.loanCode }"> <input
				type="hidden" id="isManager" value="${isManager}"> <input
				type="hidden" id="protoColUrl" value="${protoColUrl}" />
			<button class="btn btn-small" id="sendBtn"
				onclick="showContract('${lm.contractCode}',1)">合同寄送申请</button>
			<c:if test="${isManager==false}">
				<button class="btn btn-small" id="${lm.applyId }"
					onclick="showLoanHis(this.id)">历史</button>
				<button class="btn btn-small" id="iconBtn">影像</button>
				<c:if test="${procBtn=='1'}">
					<button class="btn btn-small" id="agreementBtn">协议查看</button>
				</c:if>
				<c:if test="${status == '2' || status == '3'}">
					<button class="btn btn-small" id="noticeBtn"
						onclick="showContract('${lm.contractCode}',2)">结清通知书申请</button>
				</c:if>
				<c:if test="${trustCashEnable == '1'}">
					<button class="btn btn-small" id="trustCash"
						loanCode="${lm.loanCode}" applyId="${lm.applyId }">
						<c:if test="${lm.trustCash == '1'}">取消</c:if>
						委托提现
					</button>
				</c:if>
				<c:if test="${trustRechargeEnable == '1'}">
					<button class="btn btn-small" id="trustRecharge"
						loanCode="${lm.loanCode}" applyId="${lm.applyId }">
						<c:if test="${lm.trustRecharge == '1'}">取消</c:if>
						委托充值
					</button>
				</c:if>
				<c:if test="${query == 'loan' }">
					<button class="btn btn-small"
						onclick="location.href='${ctx}/borrow/transate/loanInfo'">返回</button>
				</c:if>
			</c:if>

			<c:if test="${query == 'tra' }">
				<button class="btn btn-small"
					onclick="location.href='${ctx }/borrow/transate/transateInfo'">返回</button>
			</c:if>
		</div>



	</div>
	<div class="box2 mb10">
		<input type="hidden" id="dictLoanStatus" value="${dictLoanStatus}" />
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">借款合同编号：</label>${lm.contractCode }</td>
				<td><label class="lab">门店名称：</label> ${lm.dictLoanUse }</td>
			</tr>
			<tr>
				<td colspan="3"><p class="bar"></p></td>
			</tr>
			<tr>
				<td><label class="lab">借款人姓名：</label>${lm.customerName }</td>
				<td><label class="lab">证件类型：</label>${lm.customerCertTypeLabel}</td>
				<td><label class="lab">证件号码：</label> ${lm.customerCertNum }
				<td>
			</tr>
			<c:forEach items="${cob}" var="item">
				<tr>
					<td>
						<label class="lab">
							<c:if test="${lm.loanInfoOldOrNewFlag eq '0'}">	
								共借人姓名：
							</c:if>
							<c:if test="${lm.loanInfoOldOrNewFlag eq '1'}">	
								自然人保证人姓名：
							</c:if>
						</label>
						${item.coboname }
					</td>
					<td><label class="lab">证件类型：</label>${item.dictcerttype }</td>
					<td><label class="lab">证件号码：</label>${item.cobocertnum }
					<td>
				</tr>
			</c:forEach>
			<c:if
				test="${lm.auditEnsureName != null && lm.auditEnsureName != '' 
							&& lm.auditLegalMan != null && lm.auditLegalMan != '' 
							&& 	lm.ensuremanBusinessPlace != null && lm.ensuremanBusinessPlace != ''
							}">
				<tr>
					<c:if
						test="${lm.auditEnsureName != null && lm.auditEnsureName != '' }">
						<td><label class="lab">保证人：</label>${lm.auditEnsureName }</td>
					</c:if>
					<c:if test="${lm.auditLegalMan != null && lm.auditLegalMan != '' }">
						<td><label class="lab">法定代表人：</label>${lm.auditLegalMan }</td>
					</c:if>
					<c:if
						test="${lm.ensuremanBusinessPlace != null && lm.ensuremanBusinessPlace != '' }">
						<td><label class="lab">经营场所：</label>${lm.ensuremanBusinessPlace }
						<td>
					</c:if>
				</tr>
			</c:if>
			<c:if test="${lm.middleName != null && lm.middleName != '' }">
				<tr>
					<td><label class="lab">中间人：</label>${lm.middleName }</td>
				</tr>
			</c:if>
			<tr>
				<td colspan="3"><p class="bar"></p></td>
			</tr>
			<tr>
				<td><label class="lab">产品种类：</label>${lm.productName }</td>
				<td><label class="lab">批复分期：</label>${lm.contractMonths }</td>
			</tr>
			<tr>
				<td><label class="lab">批借金额：</label> <fmt:formatNumber
						value="${lm.money == null ? 0 : lm.money }" pattern="#,##0.00#" />
				</td>
				<td><label class="lab">产品总费率：</label> <fmt:formatNumber
						value="${lm.feeAllRaio == null ? 0 : lm.feeAllRaio }"
						pattern="#0.00#" />%</td>
			</tr>
			<tr>
				<td><label class="lab">外访费：</label> <fmt:formatNumber
						value="${lm.feePetitionFee == null ? 0 : lm.feePetitionFee }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">借款利率：</label> <fmt:formatNumber
						value="${lm.feeLoanRate == null ? 0 : lm.feeLoanRate }"
						pattern="#0.00#" />%</td>
			</tr>
			<tr>
				<td colspan="3"><p class="bar"></p></td>
			</tr>
			<tr>
				<td><label class="lab">实放金额：</label> <fmt:formatNumber
						value="${lm.feeRealput == null ? 0 : lm.feeRealput }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">前期咨询费：</label> <fmt:formatNumber
						value="${lm.feeConsult == null ? 0 : lm.feeConsult }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">分期咨询费：</label> <fmt:formatNumber
						value="${lm.monthFeeConsult == null ? 0 : lm.monthFeeConsult }"
						pattern="#,##0.00#" /></td>
			</tr>
			<tr>
				<td><label class="lab">合同金额：</label> <fmt:formatNumber
						value="${lm.contractMoney == null ? 0 : lm.contractMoney }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">前期审核费：</label> <fmt:formatNumber
						value="${lm.feeCredit == null ? 0 : lm.feeCredit }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">分期居间服务费：</label> <fmt:formatNumber
						value="${lm.monthMidFeeService == null ? 0 : lm.monthMidFeeService }"
						pattern="#,##0.00#" /></td>
			</tr>
			<tr>
				<td><label class="lab">应还本息：</label> <fmt:formatNumber
						value="${lm.contractBackMonthMoney == null ? 0 : lm.contractBackMonthMoney }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">前期居间服务费：</label> <fmt:formatNumber
						value="${lm.feeService == null ? 0 : lm.feeService }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">分期服务费：</label> <fmt:formatNumber
						value="${lm.monthFeeService == null ? 0 : lm.monthFeeService }"
						pattern="#,##0.00#" /></td>
			</tr>
			<tr>
				<td><label class="lab">分期服务费：</label> <fmt:formatNumber
						value="${lm.monthFeeService == null ? 0 : lm.monthFeeService }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">前期信息服务费：</label> <fmt:formatNumber
						value="${lm.feeInfoService == null ? 0 : lm.feeInfoService }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">催收服务费：</label> <fmt:formatNumber
						value="${lm.feeUrgedService == null ? 0 : lm.feeUrgedService }"
						pattern="#,##0.00#" /></td>
			</tr>
			<tr>
				<td><label class="lab">月还款额：</label> <fmt:formatNumber
						value="${lm.contractMonthRepayTotal == null ? 0 : lm.contractMonthRepayTotal }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">前期综合服务费：</label> <fmt:formatNumber
						value="${lm.feeCount == null ? 0 : lm.feeCount }"
						pattern="#,##0.00#" /></td>
				<td><label class="lab">加急费：</label> <fmt:formatNumber
						value="${lm.feeExpeditedFee == null ? 0 : lm.feeExpeditedFee }"
						pattern="#,##0.00#" /></td>
			</tr>
			<tr>
				<td><label class="lab">预计还款总额：</label> <fmt:formatNumber
						value="${lm.contractExpectCount == null ? 0 : lm.contractExpectCount }"
						pattern="#,##0.00#" /></td>
			</tr>
		</table>
	</div>
	</div>
</body>
</html>