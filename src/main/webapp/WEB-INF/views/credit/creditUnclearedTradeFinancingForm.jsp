<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditUnclearedTradeFinancingTab" >
		<form id="creditUnclearedTradeFinancingForm">
		<table id="creditUnclearedTradeFinancingTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>币种</th>
					<th>融资金额</th>
					<th>融资余额</th>
					<th>放款日期</th>
					<th>到期日期</th>
					<!-- <th>五级分类</th> -->
					<th>担保</th>
					<th>展期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditUnclearedTradeFinancing" items="${creditUnclearedTradeFinancingList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditUnclearedTradeFinancing.id}" />
					<input name="loanCode" type="hidden" value="${creditUnclearedTradeFinancing.loanCode}" />
					<td>
						<input  name="loanOrg" class="input_text70 required" type="text"  value="${creditUnclearedTradeFinancing.loanOrg}"  />
					</td>
					<td>
						<select name="dictCurrency" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedTradeFinancing.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<input  type="hidden" name="financingAmount" value="${creditUnclearedTradeFinancing.financingAmount}" class="input_text70 " money="1" maxlength="12" />
						<input  name="financingAmountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedTradeFinancing.financingAmount}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedTradeFinancing', '${sta.index}', 'financingAmount');" />
					</td>
					<td>
						<input  type="hidden" name="financingBalance" value="${creditUnclearedTradeFinancing.financingBalance}" class="input_text70 " money="1" maxlength="12" />
						<input  name="financingBalanceName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditUnclearedTradeFinancing.financingBalance}" pattern="#,###.##"/>' onblur="formatMoney('creditUnclearedTradeFinancing', '${sta.index}', 'financingBalance');" />
					</td>
					<td><input  name="lendingDay" value='<fmt:formatDate value="${creditUnclearedTradeFinancing.lendingDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay" value='<fmt:formatDate value="${creditUnclearedTradeFinancing.actualDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<%-- <td>
						<select name="dictLevelClass" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedTradeFinancing.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						<select name="dictGuarantee" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedTradeFinancing.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="dictExhibition" class="select78 " >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${creditUnclearedTradeFinancing.dictExhibition == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>					
															
					<td><a name="aRemoveInfo" onClick="removeTr('creditUnclearedTradeFinancing', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditUnclearedTradeFinancingTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden"  value="${enterpriseCredit.loanCode}"/>
					
					<td>
						<input  name="loanOrg" class="input_text70 required" type="text"  />
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
						<input  type="hidden" name="financingAmount"  class="input_text70 " money="1" maxlength="12" />
						<input  name="financingAmountName" class="input_text70 " type="text" money="1" maxlength="12"   />
					</td>
					<td>
						<input  type="hidden" name="financingBalance" value="" class="input_text70 " money="1" maxlength="12" />
						<input  name="financingBalanceName" class="input_text70 " type="text" money="1" maxlength="12" />
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
	