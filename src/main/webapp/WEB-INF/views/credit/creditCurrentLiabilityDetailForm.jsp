<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditCurrentLiabilityDetailTab" >
		<form id="creditCurrentLiabilityDetailForm">
		<table id="creditCurrentLiabilityDetailTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<td class="listbg01"></td>
					<td class="listbg01" colspan="2">正常类汇总</td>
					<td class="listbg01" colspan="2">关注类汇总</td>
					<td class="listbg01" colspan="2" width="20%">不良类汇总</td>
				</tr>
				<tr>
					<td class="listbg01"></td>
					<td class="listbg01">笔数 单位：笔</td>
					<td class="listbg01">余额</td>
					<td class="listbg01">笔数 单位：笔</td>
					<td class="listbg01">余额</td>
					<td class="listbg01">笔数 单位：笔</td>
					<td class="listbg01">余额</td>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditCurrentLiabilityDetail" items="${creditCurrentLiabilityDetailList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditCurrentLiabilityDetail.id}" />
					<input name="loanCode" type="hidden" value="${creditCurrentLiabilityDetail.loanCode}" />
					<td class="listbg01">
						${creditCurrentLiabilityDetail.infoSummary}
					</td>
					<td>
						<input maxlength="2" name="normalTransactionCount" type="text" value="${creditCurrentLiabilityDetail.normalTransactionCount}" class="input_text70 integer" />
					</td>
					<td>
						<input  type="hidden" name="normalBalance" value="${creditCurrentLiabilityDetail.normalBalance}" class="input_text70 " money="1" maxlength="12" />
						<input  name="normalBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCurrentLiabilityDetail.normalBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditCurrentLiabilityDetail', '${sta.index}', 'normalBalance');" />
					</td>
					<td>
						<input maxlength="2" name="concernTransactionCount" type="text" value="${creditCurrentLiabilityDetail.concernTransactionCount}" class="input_text70 integer" />
					</td>
					<td>
						<input  type="hidden" name="concernBalance" value="${creditCurrentLiabilityDetail.concernBalance}" class="input_text70 " money="1" maxlength="12" />
						<input  name="concernBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCurrentLiabilityDetail.concernBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditCurrentLiabilityDetail', '${sta.index}', 'concernBalance');" />
					</td>	
					<td>
						<input maxlength="2" name="badnessTransactionCount" type="text" value="${creditCurrentLiabilityDetail.badnessTransactionCount}" class="input_text70 integer" />
					</td>
					<td>
						<input  type="hidden" name="badnessBalance" value="${creditCurrentLiabilityDetail.badnessBalance}" class="input_text70 " money="1" maxlength="12" />
						<input  name="badnessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCurrentLiabilityDetail.badnessBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditCurrentLiabilityDetail', '${sta.index}', 'badnessBalance');" />
					</td>						
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
	</div>
	