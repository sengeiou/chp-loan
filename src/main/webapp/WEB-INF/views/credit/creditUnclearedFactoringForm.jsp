<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditUnclearedFactoringTab" >
		<form id="creditUnclearedFactoringForm">
		<table id="creditUnclearedFactoringTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>币种</th>
					<th>叙做金额</th>
					<th>叙做余额</th>
					<th>叙做日期</th>
					<!-- <th>到期日期</th>
					<th>五级分类</th> -->
					<th>担保</th>
					<th>垫款</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditUnclearedFactoring" items="${creditUnclearedFactoringList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditUnclearedFactoring.id}" />
					<input name="loanCode" type="hidden" value="${creditUnclearedFactoring.loanCode}" />
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value="${creditUnclearedFactoring.loanOrg}"  />
					</td>
					<td>
						<select name="dictCurrency" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedFactoring.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input  type="hidden" name="factoringAmount" value="${creditUnclearedFactoring.factoringAmount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="factoringAmountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedFactoring.factoringAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney('creditUnclearedFactoring', '${sta.index}', 'factoringAmount');" />
					</td>
					<td>
						<input  type="hidden" name="factoringBalance" value="${creditUnclearedFactoring.factoringBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="factoringBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedFactoring.factoringBalance}" pattern="#,###.##"/>'
							onblur="formatMoney('creditUnclearedFactoring', '${sta.index}', 'factoringBalance');" />
					</td>
					<td><input  name="lendingDay" value='<fmt:formatDate value="${creditUnclearedFactoring.lendingDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<%-- <td><input  name="factoringDay" value='<fmt:formatDate value="${creditUnclearedFactoring.factoringDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLevelClass" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedFactoring.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						<select name="dictGuarantee" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedFactoring.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="makeAdvances" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedFactoring.makeAdvances == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('creditUnclearedFactoring', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditUnclearedFactoringTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden"  value="${enterpriseCredit.loanCode}"/>
					
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value=""  />
					</td>
					<td>
						<select name="dictCurrency" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input  type="hidden" name="factoringAmount" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="factoringAmountName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>
					<td>
						<input  type="hidden" name="factoringBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="factoringBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  />
					</td>
					<td><input  name="lendingDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<%-- <td><input  name="factoringDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLevelClass" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						<select name="dictGuarantee" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="makeAdvances" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td>						
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	