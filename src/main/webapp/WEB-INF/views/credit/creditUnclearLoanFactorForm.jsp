<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="unclearLoanFactorTab" >
		<form id="unclearLoanFactorForm">
		
		<table id="unclearLoanFactorTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>					
					<th>授信机构</th>
					<th>币种</th>
					<th>叙做金额</th>
					<th>叙做余额</th>
					<th>叙做日期</th>
					<th>到期日期</th>
					<th>五级分类</th>
					<!-- <th>贷款形式</th> -->
					<th>担保</th>
					<th>垫款</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="unclearLoan" items="${unclearedImproperLoanList}" varStatus="sta">
				<c:if test="${unclearLoan.businessType eq 3}">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${unclearLoan.id}" />
					<input name="loanCode" type="hidden" value="${unclearLoan.loanCode}" />
					<input name="businessType" type="hidden" value="${unclearLoan.businessType}" />					
					<td><input maxlength="20" name="loanOrg" value="${unclearLoan.loanOrg}" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<input  type="hidden" name="businessAmount" value="${unclearLoan.businessAmount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessAmountName" class="input_text70 " type="text" money="1" maxlength="12" 
							value='<fmt:formatNumber value="${unclearLoan.businessAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney('unclearLoanFactor', '${sta.index}', 'businessAmount');" />
					</td>	
					<td>
						<input  type="hidden" name="businessBalance" value="${unclearLoan.businessBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" 
							value='<fmt:formatNumber value="${unclearLoan.businessBalance}" pattern="#,###.##"/>' 
							onblur="formatMoney('unclearLoanFactor', '${sta.index}', 'businessBalance');" />
					</td>				
					<td><input  name="businessDay" value='<fmt:formatDate value="${unclearLoan.businessDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay" value='<fmt:formatDate value="${unclearLoan.actualDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLevelClass" class="select78">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>
									<%-- 	 <td>
						<select name="dictLoanType" class="select78">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_loan_type')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictLoanType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					 --%>
					<td>
						<select name="dictGuarantee" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<%-- <select name="dictExhibition" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_exhibition')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictExhibition == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select> --%>
						<select name="makeAdvances" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.makeAdvances == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>						
					</td>
					<td><a name="aRemoveInfo" onClick="removeTr('unclearLoanFactor', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
					
					<input  name="marginLevel" type="hidden"/>	
					<input  name="makeAdvances" type="hidden"/>						
				</tr>
				</c:if>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="unclearLoanFactorTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<input name="businessType" type="hidden" value="3" />				
					<td><input maxlength="20" name="loanOrg" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>
					</td>										
					<td>
						<input  type="hidden" name="businessAmount" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessAmountName" class="input_text70 " type="text" money="1" maxlength="12"/>
					</td>	
					<td>
						<input  type="hidden" name="businessBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" />
					</td>				
					<td><input  name="businessDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLevelClass" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>						
					</td>
					<%-- <td>
						<select name="dictLoanType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_loan_type')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>	 --%>				
					<td>
						<select name="dictGuarantee" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<%-- <select name="dictExhibition" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_exhibition')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select> --%>		
						<select name="makeAdvances" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.makeAdvances == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>				
					</td>										
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>
					
					<input  name="marginLevel" type="hidden"/>	
					<input  name="makeAdvances" type="hidden"/>				
				</tr>
			</tbody>
		</table>		
	</div>
	