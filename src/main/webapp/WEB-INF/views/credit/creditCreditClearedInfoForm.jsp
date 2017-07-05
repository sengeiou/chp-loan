<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditCreditClearedInfoTab" >
		<form id="creditCreditClearedInfoForm">
		<table id="creditCreditClearedInfoTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<td class="listbg01"></td>
					<td class="listbg01">笔数 单位：笔</td>
					<td class="listbg01">余额 </td>
					<td class="listbg01">最近一次处置完成日期</td>
				</tr>

			</thead>
			<tbody>
			<c:forEach var="creditCreditClearedInfo" items="${creditCreditClearedInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditCreditClearedInfo.id}" />
					<input name="loanCode" type="hidden" value="${creditCreditClearedInfo.loanCode}" />
					<td class="listbg01">
						${creditCreditClearedInfo.infoSummary}
					</td>
					<td>
						<input maxlength="2" name="transactionCount" type="text" value="${creditCreditClearedInfo.transactionCount}" class="input_text70 integer" />
					</td>
					<td>
						<input  type="hidden" name="balance" value="${creditCreditClearedInfo.balance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="balanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedInfo.balance}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedInfo', '${sta.index}', 'balance');" />
					</td>
					<td>
						<input  name="completionDate" value='<fmt:formatDate value="${creditCreditClearedInfo.completionDate}" pattern="yyyy-MM"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" />
					</td>
												
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
	