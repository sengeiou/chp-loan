<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditUnclearedLetterCreditTab" >
		<form id="creditUnclearedLetterCreditForm">
		<table id="creditUnclearedLetterCreditTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>笔数</th>
					<th>金额</th>
					<th>余额</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditUnclearedLetterCredit" items="${creditUnclearedLetterCreditList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditUnclearedLetterCredit.id}" />
					<input name="loanCode" type="hidden" value="${creditUnclearedLetterCredit.loanCode}" />
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value="${creditUnclearedLetterCredit.loanOrg}"  />
					</td>
					<td><input maxlength="2" name="transactionCount" value="${creditUnclearedLetterCredit.transactionCount}"  class="input_text70 integer " type="text"  /></td>
					<td>
						<input  type="hidden" name="balance" value="${creditUnclearedLetterCredit.balance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="balanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedLetterCredit.balance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedLetterCredit', '${sta.index}', 'balance');" />
					</td>
					<td>
						<input  type="hidden" name="amount" value="${creditUnclearedLetterCredit.amount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="amountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedLetterCredit.amount}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedLetterCredit', '${sta.index}', 'amount');" />
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('creditUnclearedLetterCredit', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditUnclearedLetterCreditTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden"  value="${enterpriseCredit.loanCode}"/>
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value=""  />
					</td>
					<td><input  name="transactionCount" value=""  class="input_text70 integer " type="text"  /></td>
					<td>
						<input  type="hidden" name="balance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="balanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>
					<td>
						<input  type="hidden" name="amount" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="amountName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>					
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	