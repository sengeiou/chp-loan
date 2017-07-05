<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditInvestorInfoTab" >
		<form id="creditInvestorInfoForm">
		<table id="creditInvestorInfoTable" cellpadding="0" cellspacing="0"   width="100%" class="table2">
			<thead>
				<tr>
					<th>出资金额</th>
					<th>出资方名称</th>
					<th>出资占比 单位：%</th>
					<th>证件类型</th>
					<th>证件号码</th>
					<th>币种</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditInvestorInfo" items="${creditInvestorInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditInvestorInfo.id}" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<td>
						<input  type="hidden" name="contributionAmount" value="${creditInvestorInfo.contributionAmount}" class="input_text70 integer " money="1" maxlength="12" />
						<input  name="contributionAmountName" class="input_text70  " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditInvestorInfo.contributionAmount}" pattern="#,###.##"/>' onblur="formatMoney('creditInvestorInfo', '${sta.index}', 'contributionAmount');" />
					</td>
					<td><input maxlength="100" name="investorName" value="${creditInvestorInfo.investorName}" class="input_text70 " type="text" /></td>
					<td><input min="0" max="100" name="contributionProportion" value="${creditInvestorInfo.contributionProportion}" class="input_text70  percent " type="text" /></td>
					<td>
						<select name="dictCertType" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_cart_type')}" var="item">
								<option value="${item.value}" <c:if test="${creditInvestorInfo.dictCertType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input maxlength="20"  name="customerCertNum" value="${creditInvestorInfo.customerCertNum}" onblur="javascript:checkIdNum(this);" class="input_text178 card" type="text" /></td>
					<td>
						<select name="dictCurrency" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${creditInvestorInfo.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a name="aRemoveInfo" onClick="removeTr('creditInvestorInfo', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditInvestorInfoTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<td>
						<input  type="hidden" name="contributionAmount"  class="input_text70 integer " money="1" maxlength="12" />
						<input  name="contributionAmountName" class="input_text70  " type="text" money="1" maxlength="12"  />
					</td>
					<td><input maxlength="100" name="investorName"  class="input_text70 " type="text" /></td>
					<td><input  name="contributionProportion"  class="input_text70  percent  " type="text" /></td>
					<td>
						<select name="dictCertType" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_cart_type')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input maxlength="20" name="customerCertNum"  class="input_text178 card" type="text" /></td>
					<td>
						<select name="dictCurrency" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	