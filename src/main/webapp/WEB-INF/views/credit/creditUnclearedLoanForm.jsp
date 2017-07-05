<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditUnclearedLoanTab" >
		<form id="creditUnclearedLoanForm">
		<table id="creditUnclearedLoanTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>币种</th>
					<th>借据金额</th>
					<th>借据余额</th>
					<th>放款日期</th>
					<th>到期日期</th>
				<!-- 	<th>五级分类</th> -->
					<th>贷款形式</th>
					<th>担保</th>
					<th>展期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditUnclearedLoan" items="${creditUnclearedLoanList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditUnclearedLoan.id}" />
					<input name="loanCode" type="hidden" value="${creditUnclearedLoan.loanCode}" />
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  value="${creditUnclearedLoan.loanOrg}"  />
					</td>
					<td>
						<select name="dictCurrency" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedLoan.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input  type="hidden" name="iousAmount" value="${creditUnclearedLoan.iousAmount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="iousAmountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedLoan.iousAmount}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedLoan', '${sta.index}', 'iousAmount');" />
					</td>
					<td>
						<input  type="hidden" name="iousBalance" value="${creditUnclearedLoan.iousBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="iousBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedLoan.iousBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedLoan', '${sta.index}', 'iousBalance');" />
					</td>
					<td><input  name="lendingDay" value='<fmt:formatDate value="${creditUnclearedLoan.lendingDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay" value='<fmt:formatDate value="${creditUnclearedLoan.actualDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<%-- <td>
						<select name="dictLevelClass" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedLoan.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						<select name="dictLoanType" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_loan_type')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedLoan.dictLoanType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="dictGuarantee" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedLoan.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="dictExhibition" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedLoan.dictExhibition == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>					
															
					<td><a name="aRemoveInfo" onClick="removeTr('creditUnclearedLoan', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditUnclearedLoanTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden"  value="${enterpriseCredit.loanCode}"/>
					
					<td>
						<input maxlength="20" name="loanOrg" class="input_text70 required" type="text"  />
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
						<input  type="hidden" name="iousAmount"  class="input_text70 number " money="1" maxlength="12" />
						<input  name="iousAmountName" class="input_text70 " type="text" money="1" maxlength="12"  onblur="formatMoney('creditUnclearedLoan', '${sta.index}', 'iousAmount');" />
					</td>
					<td>
						<input  type="hidden" name="iousBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="iousBalanceName" class="input_text70 " type="text" money="1" maxlength="12"  onblur="formatMoney('creditUnclearedLoan', '${sta.index}', 'iousBalance');" />
					</td>
					<td><input  name="lendingDay"  class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay"  class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<%-- <td>
						<select name="dictLevelClass" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						<select name="dictLoanType" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_loan_type')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="dictGuarantee" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="dictExhibition" class="select78 " >
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
	