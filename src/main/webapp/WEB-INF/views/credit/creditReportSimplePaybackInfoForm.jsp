<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="paybackInfoTab" >
		<form id="paybackInfoForm">
		<table id="paybackTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table">
			<thead>
				<tr>
					<th>编号<font color="red"></font></th>
					<th>最近一次代偿时间<font color="red"></font></th>
					<th>代偿机构<font color="red"></font></th>
					<th>累计代偿金额<font color="red"></font></th>
					<th>最后一次还款日期<font color="red"></font></th>
					<th>余额<font color="red"></font></th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditPaybackInfo" items="${creditPaybackInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditPaybackInfo.id}" />
					<input name="relationId" type="hidden" value="${creditPaybackInfo.relationId}" />
					<td><input name="num" type="text" class="input_text50" value="${sta.index + 1}" /></td>
					
					<td><input  name="recentlyPaybackTime" value='<fmt:formatDate value="${creditPaybackInfo.recentlyPaybackTime}" pattern="yyyy-MM-dd"/>' class="input_text70 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input maxlength="30" name="paybackOrg" value="${creditPaybackInfo.paybackOrg}" class="input_text70 required " type="text"/></td>
					<td>
						<input  type="hidden" name="totalPaybackAmount" value="${creditPaybackInfo.totalPaybackAmount}" class="input_text70 required" money="1" maxlength="12" />
						<input  name="totalPaybackAmountName" class="input_text70 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditPaybackInfo.totalPaybackAmount}" pattern="#,###.##"/>' onblur="formatMoney1('payback', '${sta.index}', 'totalPaybackAmount');" />
					</td>
					<td><input  name="lastPaybackDate" value='<fmt:formatDate value="${creditPaybackInfo.lastPaybackDate}" pattern="yyyy-MM-dd"/>' class="input_text70 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<input  type="hidden" name="residualAmount" value="${creditPaybackInfo.residualAmount}" class="input_text70 required" money="1" maxlength="12" />
						<input  name="residualAmountName" class="input_text70 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditPaybackInfo.residualAmount}" pattern="#,###.##"/>' onblur="formatMoney1('payback', '${sta.index}', 'residualAmount');" />
					</td>
					
					<td><a name="aRemoveInfo" onClick="removeTr('payback', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="paybackTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="relationId" type="hidden" value="${creditReportSimple.id}" />
					<td><input name="num" type="text" class="input_text50" value="1" /></td>
					
					<td><input  name="recentlyPaybackTime" class="input_text70 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input maxlength="30" name="paybackOrg"  class="input_text70 required " type="text"/></td>
					<td>
						<input  type="hidden" name="totalPaybackAmount"  class="input_text70 required" money="1" maxlength="12" />
						<input  name="totalPaybackAmountName" class="input_text70 required" type="text" money="1" maxlength="12" />
					</td>
					<td><input  name="lastPaybackDate" class="input_text70 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<input  type="hidden" name="residualAmount"  class="input_text70 required" money="1" maxlength="12" />
						<input  name="residualAmountName" class="input_text70 required" type="text" money="1" maxlength="12" />
					</td>
					
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	