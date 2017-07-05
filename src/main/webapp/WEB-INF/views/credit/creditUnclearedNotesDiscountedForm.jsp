<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditUnclearedNotesDiscountedTab" >
		<form id="creditUnclearedNotesDiscountedForm">
		<table id="creditUnclearedNotesDiscountedTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>笔数</th>
					<th>余额</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditUnclearedNotesDiscounted" items="${creditUnclearedNotesDiscountedList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditUnclearedNotesDiscounted.id}" />
					<input name="loanCode" type="hidden" value="${creditUnclearedNotesDiscounted.loanCode}" />
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value="${creditUnclearedNotesDiscounted.loanOrg}"  />
					</td>
					<td><input  maxlength="2" name="transactionCount" value="${creditUnclearedNotesDiscounted.transactionCount}"  class="input_text70 integer " type="text"  /></td>
					<td>
						<input  type="hidden" name="balance" value="${creditUnclearedNotesDiscounted.balance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="balanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedNotesDiscounted.balance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedNotesDiscounted', '${sta.index}', 'balance');" />
					</td>
					<td><a name="aRemoveInfo" onClick="removeTr('creditUnclearedNotesDiscounted', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditUnclearedNotesDiscountedTableHide" class="hide">
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
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	