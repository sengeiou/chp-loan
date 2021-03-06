<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="paidLoanCreditTab" >
		<form id="paidLoanCreditForm">
		<table id="paidLoanCreditTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>币种</th>
					<th>开证/开立金额</th>
					<th>保证金比例 单位：%</th>
					<th>到期日期</th>
					<th>注销日期/结清日期</th>
					<th>五级分类</th>
					<th>担保</th>
					<th>垫款</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="paidLoanCredit" items="${paidLoanList}" varStatus="sta">
				<c:if test="${paidLoanCredit.businessType eq '6'}" >
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${paidLoanCredit.id}" />
					<input name="loanCode" type="hidden" value="${paidLoanCredit.loanCode}" />
					<input name="businessType" type="hidden" value="${paidLoanCredit.businessType}" />
					<td><input maxlength="20" name="loanOrg" value="${paidLoanCredit.loanOrg}" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoanCredit.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<input  type="hidden" name="businessAmount" value="${paidLoanCredit.businessAmount}" class="input_text70 " money="1" maxlength="12" />
						<input  name="businessAmountName" class="input_text70 " type="text" money="1" maxlength="12" 
							value='<fmt:formatNumber value="${paidLoanCredit.businessAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney('paidLoanCredit', '${sta.index}', 'businessAmount');" />
					</td>
					<td>
						<input  type="text" name="marginLevel" value="${paidLoanCredit.marginLevel}" class="input_text70 number " money="1" maxlength="12" />
					</td>					
					<td><input  name="actualDay" value='<fmt:formatDate value="${paidLoanCredit.actualDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="paidDay" value='<fmt:formatDate value="${paidLoanCredit.paidDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>					
					<td>
						<select name="dictLevelClass" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoanCredit.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<select name="dictGuarantee" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoanCredit.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<select name="makeAdvances" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoanCredit.makeAdvances == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('paidLoanCredit', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>					
					<input name="businessDay" type="hidden" value="" />
					<input name="dictRepayMethod" type="hidden" value="" />
					<input name="dictLoanType" type="hidden" value="" />
					<input name="dictExhibition" type="hidden" value="" />										
				</tr>
				</c:if>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="paidLoanCreditTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<input name="businessType" type="hidden" value="6" />					
					<td><input maxlength="20" name="loanOrg" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>										
					<td>
						<input  type="hidden" name="businessAmount" class="input_text70 " money="1" maxlength="12" />
						<input  name="businessAmountName" class="input_text70 " type="text" money="1" maxlength="12"/>
					</td>
					<td>
						<input  type="text" name="marginLevel" class="input_text70 number " money="1" maxlength="12" />
					</td>					
					<td><input  name="actualDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="paidDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>					
					<td>
						<select name="dictLevelClass" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td>
						<select name="dictGuarantee" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td>
						<select name="makeAdvances" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>										
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>
					<input name="businessDay" type="hidden" value="" />
					<input name="dictRepayMethod" type="hidden" value="" />
					<input name="dictLoanType" type="hidden" value="" />
					<input name="dictExhibition" type="hidden" value="" />					
				</tr>
			</tbody>
		</table>		
	</div>
	