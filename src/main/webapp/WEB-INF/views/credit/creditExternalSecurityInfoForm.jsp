<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditExternalSecurityInfoTab" >
		<form id="creditExternalSecurityInfoForm">
		<table id="creditExternalSecurityInfoTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<td rowspan="2" class="listbg01"></td>
					<td colspan="2"  class="listbg01">对外担保信息概要</td>
					<td colspan="3" class="listbg01">被担保业务余额</td>
				</tr>
				<tr>
					<td class="listbg01">笔数 单位：笔</td>
					<td class="listbg01">担保金额</td>
					<td class="listbg01">正常</td>
					<td class="listbg01">关注</td>
					<td class="listbg01">不良</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditExternalSecurityInfo" items="${creditExternalSecurityInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditExternalSecurityInfo.id}" />
					<input name="loanCode" type="hidden" value="${creditExternalSecurityInfo.loanCode}" />
					<td class="listbg01">
						${creditExternalSecurityInfo.infoSummary}
					</td>
					<td>
						<input maxlength="2" name="transactionCount" type="text" value="${creditExternalSecurityInfo.transactionCount}" class="input_text70 integer"/>
					</td>
					<td>
						<input  type="hidden" name="balance" value="${creditExternalSecurityInfo.balance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="balanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditExternalSecurityInfo.balance}" pattern="#,###.##"/>' onblur="formatMoney('creditExternalSecurityInfo', '${sta.index}', 'balance');" />
					</td>
					<td>
						<input  type="hidden" name="normalBalance" value="${creditExternalSecurityInfo.normalBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="normalBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditExternalSecurityInfo.normalBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditExternalSecurityInfo', '${sta.index}', 'normalBalance');" />
					</td>	
					<td>
						<input  type="hidden" name="concernBalance" value="${creditExternalSecurityInfo.concernBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="concernBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditExternalSecurityInfo.concernBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditExternalSecurityInfo', '${sta.index}', 'concernBalance');" />
					</td>
					<td>
						<input  type="hidden" name="badnessBalance" value="${creditExternalSecurityInfo.badnessBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="badnessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditExternalSecurityInfo.badnessBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditExternalSecurityInfo', '${sta.index}', 'badnessBalance');" />
					</td>							
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
	