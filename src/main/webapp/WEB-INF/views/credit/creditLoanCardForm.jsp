<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditLoanCardTab" >
		<form id="creditLoanCardForm">
		<table id="creditLoanCardTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>通过年度</th>
					<th>机构名称</th>
					<th>添加日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditLoanCard" items="${creditLoanCardList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditLoanCard.id}" />
					<input name="loanCode" type="hidden" value="${creditLoanCard.loanCode}" />
					<td><input maxlength="20" name="throughYear" value="${creditLoanCard.throughYear}" class="input_text70 " type="text"/></td>
					<td><input maxlength="50" name="orgName" value="${creditLoanCard.orgName}" class="input_text70 " type="text"/></td>					
					<td><input  name="addDay" value='<fmt:formatDate value="${creditLoanCard.addDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" onClick="removeTr('creditLoanCard', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditLoanCardTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />					
					<td><input maxlength="20" name="throughYear" class="input_text70 " type="text"/></td>
					<td><input maxlength="50" name="orgName" class="input_text70 " type="text"/></td>
					<td><input  name="addDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</tbody>
		</table>		
	</div>
	