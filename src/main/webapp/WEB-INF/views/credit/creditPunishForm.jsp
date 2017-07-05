<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditPunishTab" >
		<form id="creditPunishForm">
		<table id="creditPunishTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>时间</th>
					<th>机构</th>
					<th>项目</th>
					<th>金额</th>
					<th>添加日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditPunish" items="${creditPunishList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditPunish.id}" />
					<input name="loanCode" type="hidden" value="${creditPunish.loanCode}" />
					<td><input  name="gradeTime" value='<fmt:formatDate value="${creditPunish.gradeTime}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><input maxlength="50" name="orgName" value="${creditPunish.orgName}" class="input_text70 " type="text"/></td>
					<td>
						<select name="item" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_item')}" var="item">
								<option value="${item.value}" <c:if test="${creditPunish.item == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<input  type="hidden" name="amount" value="${creditPunish.amount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="amountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditPunish.amount}" pattern="#,###.##"/>' onblur="formatMoney('creditPunish', '${sta.index}', 'amount');" />
					</td>
					<td><input  name="addDay" value='<fmt:formatDate value="${creditPunish.addDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" onClick="removeTr('creditPunish', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditPunishTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />					
					<td><input  name="gradeTime" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><input maxlength="50" name="orgName" class="input_text70 " type="text"/></td>
					<td>
						<select name="item" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_item')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<input  type="hidden" name="amount" class="input_text70 number " money="1" maxlength="12" />
						<input  name="amountName" class="input_text70 " type="text" money="1" maxlength="12"/>
					</td>
					<td><input  name="addDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</tbody>
		</table>		
	</div>
	