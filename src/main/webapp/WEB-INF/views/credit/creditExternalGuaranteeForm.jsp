<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="externalGuaranteeTab" >
		<form id="externalGuaranteeForm">
		<table id="externalGuaranteeTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>类型</th>
					<th>被担保人</th>
					<th>证件类型</th>
					<th>证件号码</th>
					<th>担保币种</th>
					<th>担保金额</th>
					<th>担保形式</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="externalGuarantee" items="${externalGuaranteeList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${externalGuarantee.id}" />
					<input name="loanCode" type="hidden" value="${externalGuarantee.loanCode}" />
					<td>
						<select name="dictGuaranteeType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee_type')}" var="item">
								<option value="${item.value}" <c:if test="${externalGuarantee.dictGuaranteeType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input maxlength="100" name="warrantee" value="${externalGuarantee.warrantee}" class="input_text70 " type="text"/></td>
					<td>
						<select name="dictCertType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee_card_type')}" var="item">
								<option value="${item.value}" <c:if test="${externalGuarantee.dictCertType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input maxlength="20" name="customerCertNum" value="${externalGuarantee.customerCertNum}" class="input_text70 required integer" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${externalGuarantee.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>						
					<td>
						<input  type="hidden" name="guaranteeAmount" value="${externalGuarantee.guaranteeAmount}" class="input_text70 " money="1" maxlength="12" />
						<input  name="guaranteeAmountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${externalGuarantee.guaranteeAmount}" pattern="#,###.##"/>' onblur="formatMoney('externalGuarantee', '${sta.index}', 'guaranteeAmount');" />
					</td>
					<td>
						<select name="dictGuaranteeForm" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee_form')}" var="item">
								<option value="${item.value}" <c:if test="${externalGuarantee.dictGuaranteeForm == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('externalGuarantee', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="externalGuaranteeTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />					
					<td>
						<select name="dictGuaranteeType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee_type')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input maxlength="100" name="warrantee" class="input_text70 " type="text"/></td>
					<td>
						<select name="dictCertType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee_card_type')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input maxlength="20" name="customerCertNum" class="input_text70 required integer" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<input  type="hidden" name="guaranteeAmount" class="input_text70 " money="1" maxlength="12" />
						<input  name="guaranteeAmountName" class="input_text70 " type="text" money="1" maxlength="12"/>
					</td>
					<td>
						<select name="dictGuaranteeForm" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee_form')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</tbody>
		</table>		
	</div>
	