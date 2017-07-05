<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditCreditClearedDetailTab" >
		<form id="creditCreditClearedDetailForm">
		<table id="creditCreditClearedDetailTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<td class="listbg01"></td>
					<td class="listbg01">贷款</td>
					<td class="listbg01">贸易融资</td>
					<td class="listbg01">保理</td>
					<td class="listbg01">票据贴现</td>
					<td class="listbg01">银行承兑汇票</td>
					<td class="listbg01">信用证</td>
					<td class="listbg01">保函</td>
				</tr>

			</thead>
			<tbody>
			<c:forEach var="creditCreditClearedDetail" items="${creditCreditClearedDetailList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditCreditClearedDetail.id}" />
					<input name="loanCode" type="hidden" value="${creditCreditClearedDetail.loanCode}" />
					<td class="listbg01">
						${creditCreditClearedDetail.infoSummary}
					</td>
					<td>
						<input  type="hidden" name="loan" value="${creditCreditClearedDetail.loan}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="loanName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.loan}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'loan');" />
					</td>
					<td>
						<input  type="hidden" name="tradeFinancing" value="${creditCreditClearedDetail.tradeFinancing}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="tradeFinancingName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.tradeFinancing}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'tradeFinancing');" />
					</td>					
					<td>
						<input  type="hidden" name="factoring" value="${creditCreditClearedDetail.factoring}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="factoringName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.factoring}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'factoring');" />
					</td>					
					<td>
						<input  type="hidden" name="notesDiscounted" value="${creditCreditClearedDetail.notesDiscounted}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="notesDiscountedName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.notesDiscounted}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'notesDiscounted');" />
					</td>					
					<td>
						<input  type="hidden" name="bankAcceptance" value="${creditCreditClearedDetail.bankAcceptance}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="bankAcceptanceName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.bankAcceptance}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'bankAcceptance');" />
					</td>
					<td>
						<input  type="hidden" name="letterCredit" value="${creditCreditClearedDetail.letterCredit}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="letterCreditName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.letterCredit}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'letterCredit');" />
					</td>					
					<td>
						<input  type="hidden" name="letterGuarantee" value="${creditCreditClearedDetail.letterGuarantee}" class="input_text70 number" money="1" maxlength="12" />
						<input  name="letterGuaranteeName" class="input_text70 number" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCreditClearedDetail.letterGuarantee}" pattern="#,###.##"/>' onblur="formatMoney('creditCreditClearedDetail', '${sta.index}', 'letterGuarantee');" />
					</td>					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
	