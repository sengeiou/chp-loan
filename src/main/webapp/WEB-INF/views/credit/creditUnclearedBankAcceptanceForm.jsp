<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditUnclearedBankAcceptanceTab" >
		<form id="creditUnclearedBankAcceptanceForm">
		<table id="creditUnclearedBankAcceptanceTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<td rowspan="2">授信机构</td>
					<td rowspan="2">笔数</td>
					<td colspan="6">余额</td>
				</tr>
				<tr>
					<th>到期日 <30</th>
					<th>到期日 <60</th>
					<th>到期日 ≤90</th>
					<th>到期日 >90</th>
					<th>合计</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditUnclearedBankAcceptance" items="${creditUnclearedBankAcceptanceList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditUnclearedBankAcceptance.id}" />
					<input name="loanCode" type="hidden" value="${creditUnclearedBankAcceptance.loanCode}" />
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value="${creditUnclearedBankAcceptance.loanOrg}"  />
					</td>
					<td><input  name="transactionCount" maxlength="2" value="${creditUnclearedBankAcceptance.transactionCount}"  class="input_text70 integer " type="text"  /></td>
					<td>
						<input  type="hidden" name="actual30dayBalance" value="${creditUnclearedBankAcceptance.actual30dayBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual30dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedBankAcceptance.actual30dayBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedBankAcceptance', '${sta.index}', 'actual30dayBalance');" />
					</td>
					<td>
						<input  type="hidden" name="actual60dayBalance" value="${creditUnclearedBankAcceptance.actual60dayBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual60dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedBankAcceptance.actual60dayBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedBankAcceptance', '${sta.index}', 'actual60dayBalance');" />
					</td>					
					<td>
						<input  type="hidden" name="actual90dayBalance" value="${creditUnclearedBankAcceptance.actual90dayBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual90dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedBankAcceptance.actual90dayBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedBankAcceptance', '${sta.index}', 'actual90dayBalance');" />
					</td>					
					<td>
						<input  type="hidden" name="actual91dayBalance" value="${creditUnclearedBankAcceptance.actual91dayBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual91dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedBankAcceptance.actual91dayBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedBankAcceptance', '${sta.index}', 'actual91dayBalance');" />
					</td>					
					<td>
						<input  type="hidden" name="totalBalance" value="${creditUnclearedBankAcceptance.totalBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="totalBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedBankAcceptance.totalBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedBankAcceptance', '${sta.index}', 'totalBalance');" />
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('creditUnclearedBankAcceptance', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditUnclearedBankAcceptanceTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden"  value="${enterpriseCredit.loanCode}"/>
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value=""  />
					</td>
					<td><input  name="transactionCount" value=""  class="input_text70 integer " type="text"  /></td>
					<td>
						<input  type="hidden" name="actual30dayBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual30dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>
					<td>
						<input  type="hidden" name="actual60dayBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual60dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>					
					<td>
						<input  type="hidden" name="actual90dayBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual90dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>					
					<td>
						<input  type="hidden" name="actual91dayBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="actual91dayBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>					
					<td>
						<input  type="hidden" name="totalBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="totalBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>					
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	