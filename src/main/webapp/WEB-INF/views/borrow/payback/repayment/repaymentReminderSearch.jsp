<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<c:forEach items="${waitPages.list}" var="item">
							<tr>
								<td><input type="checkbox" name="checkBox" value="${item.contractCode}"/></td>
								<td>${item.contractCode}</td>
								<td>${item.customerName}</td>
								<td>${item.stroeName}</td>
								<td>${item.applyBankName}</td>
								<td>${item.offendTel}</td>
								<td><fmt:formatDate value="${item.contractEndTimestamp}" type="date"/></td>
								<td>${item.months}</td>
								<td><fmt:formatDate value="${item.monthPayDate}" type="date"/></td>
								<td><fmt:formatNumber value='${item.payMoney}' pattern="0.00"/></td>
								<td><fmt:formatNumber value='${item.completeMoney}' pattern="0.00"/></td>
								<td><fmt:formatNumber value='${item.buleAmont}' pattern="0.00"/></td>
								<td>${fns:getDictLabel(item.dictLoanStatus,'jk_loan_status','-')}</td>
								<td>${fns:getDictLabel(item.dictMonthStatus,'jk_period_status','-')}</td>
								<td>${fns:getDictLabel(item.logo,'jk_channel_flag','-')}</td>
								<td>${fns:getDictLabel(item.dictDealType,'jk_deduct_plat','-')}</td>
							</tr>
						</c:forEach>
						<c:if test="${waitPages.list==null || fn:length(waitPages.list)==0}">
							<tr>
								<td colspan="18" style="text-align: center;">没有符合条件的数据</td>
							</tr>
						</c:if>