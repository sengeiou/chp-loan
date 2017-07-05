<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditLiabilityHisTab" >
		<form id="creditLiabilityHisForm">
		<table id="creditLiabilityHisTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>负债历史变化时间</th>
					<th>全部负债余额</th>
					<th>不良负债余额</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditLiabilityHis" items="${creditLiabilityHisList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditLiabilityHis.id}" />
					<input name="loanCode" type="hidden" value="${creditLiabilityHis.loanCode}" />
					<td><input  name="liabilityHisTime" value='<fmt:formatDate value="${creditLiabilityHis.liabilityHisTime}" pattern="yyyy-MM"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<input  type="hidden" name="allBalance" value="${creditLiabilityHis.allBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="allBalanceName" class="input_text70  " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditLiabilityHis.allBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditLiabilityHis', '${sta.index}', 'allBalance');" />
					</td>
					<td>
						<input  type="hidden" name="badnessBalance" value="${creditLiabilityHis.badnessBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="badnessBalanceName" class="input_text70  " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditLiabilityHis.badnessBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditLiabilityHis', '${sta.index}', 'badnessBalance');" />
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('creditLiabilityHis', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditLiabilityHisTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden"  value="${enterpriseCredit.loanCode}"/>
					<td><input  name="liabilityHisTime"  class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<input  type="hidden" name="allBalance"  class="input_text70 number " money="1" maxlength="12" />
						<input  name="allBalanceName" class="input_text70 " type="text" money="1" maxlength="12"   />
					</td>
					<td>
						<input  type="hidden" name="badnessBalance"  class="input_text70 number " money="1" maxlength="12" />
						<input  name="badnessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" />
					</td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>	
	</div>
	