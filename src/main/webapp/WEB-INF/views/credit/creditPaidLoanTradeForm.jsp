<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="paidLoanTradeTab" >
		<form id="paidLoanTradeForm">
		<table id="paidLoanTradeTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>授信机构</th>
					<th>币种</th>
					<th>融资金额</th>
					<th>放款日期</th>
					<th>到期日期</th>
					<th>结清日期</th>
					<th>还款方式</th>
					<th>五级分类</th>
					<!-- <th>贷款形式</th> -->
					<th>担保</th>
					<th>展期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="paidLoan" items="${paidLoanList}" varStatus="sta">
				<c:if test="${paidLoan.businessType eq '2'}" >
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${paidLoan.id}" />
					<input name="loanCode" type="hidden" value="${paidLoan.loanCode}" />
					<input name="businessType" type="hidden" value="${paidLoan.businessType}" />
					<td><input maxlength="20" name="loanOrg" value="${paidLoan.loanOrg}" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_currency')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoan.dictCurrency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<input  type="hidden" name="businessAmount" value="${paidLoan.businessAmount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessAmountName" class="input_text70 " type="text" money="1" maxlength="12" 
							value='<fmt:formatNumber value="${paidLoan.businessAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney('paidLoanTrade', '${sta.index}', 'businessAmount');" />
					</td>					
					<td><input  name="businessDay" value='<fmt:formatDate value="${paidLoan.businessDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay" value='<fmt:formatDate value="${paidLoan.actualDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="paidDay" value='<fmt:formatDate value="${paidLoan.paidDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>					
					<td><input maxlength="20" name="dictRepayMethod" value="${paidLoan.dictRepayMethod}" class="input_text70 " type="text"/></td>				
					<td>
						<select name="dictLevelClass" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoan.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>
					<%-- <td>
						<select name="dictLoanType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_loan_type')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoan.dictLoanType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td> --%>
					<td>
						<select name="dictGuarantee" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoan.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>						
					<td>
						<select name="dictExhibition" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${paidLoan.dictExhibition == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('paidLoanTrade', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
					<input name="makeAdvances" type="hidden" value="" />	
				</tr>
				</c:if>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="paidLoanTradeTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<input name="businessType" type="hidden" value="2" />					
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
						<input  type="hidden" name="businessAmount" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessAmountName" class="input_text70 " type="text" money="1" maxlength="12"/>
					</td>					
					<td><input  name="businessDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="actualDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="paidDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>					
					<td><input maxlength="20" name="dictRepayMethod" class="input_text70 " type="text"/></td>
					<td>
						<select name="dictLevelClass" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<%-- <td>
						<select name="dictLoanType" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_loan_type')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td> --%>
					<td>
						<select name="dictGuarantee" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td>
						<select name="dictExhibition" class="select78">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>								
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>
					<input name="makeAdvances" type="hidden" value="" />										
				</tr>
			</tbody>
		</table>		
	</div>
	