<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditCurrentLiabilityInfoTab" >
		<form id="creditCurrentLiabilityInfoForm">
		<table id="creditCurrentLiabilityInfoTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th class="listbg01"></th>
					<th class="listbg01">笔数 单位：笔</th>
					<th class="listbg01">余额</th>
					<th class="listbg01">最近一次处置完成日期</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditCurrentLiabilityInfo" items="${creditCurrentLiabilityInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditCurrentLiabilityInfo.id}" />
					<input name="loanCode" type="hidden" value="${creditCurrentLiabilityInfo.loanCode}" />
					<td class="listbg01">
						${creditCurrentLiabilityInfo.infoSummary}
					</td>
					<td>
						<input name="transactionCount" type="text" value="${creditCurrentLiabilityInfo.transactionCount}" class="input_text70 integer" maxlength="2" />
					</td>
					<td>
						<input  type="hidden" name="balance" value="${creditCurrentLiabilityInfo.balance}" class="input_text70  integer" money="1" maxlength="12" />
						<input  name="balanceName" class="input_text70  " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCurrentLiabilityInfo.balance}" pattern="#,###.##"/>' onblur="formatMoney('creditCurrentLiabilityInfo', '${sta.index}', 'balance');" />
					</td>
										
					<td>
						<c:if test="${creditCurrentLiabilityInfo.infoSummary == '由资产管理公司处置的债务'}">
							<input  name="completionDate" value='<fmt:formatDate value="${creditCurrentLiabilityInfo.completionDate}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" />
						</c:if>
						<c:if test="${creditCurrentLiabilityInfo.infoSummary != '由资产管理公司处置的债务'}">
							<input  name="completionDate" value='<fmt:formatDate value="${creditCurrentLiabilityInfo.completionDate}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="hidden" calsss="select75 Wdate" onclick="WdatePicker()" />
						</c:if>
					</td>
				
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
	