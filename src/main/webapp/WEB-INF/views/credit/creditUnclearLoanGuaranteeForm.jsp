<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="unclearLoanGuaranteeTab" >
		<form id="unclearLoanGuaranteeForm">
		
		<table id="unclearLoanGuaranteeTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>					
					<th>授信机构</th>
					<th>币种</th>
					<th>开证金额</th>
					<th>保证金比例 单位：%</th>
					<th>可用余额</th>
					<th>到期日期</th>
					<th>五级分类</th>
					<th>担保</th>
					<th>垫款</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="unclearLoan" items="${unclearedImproperLoanList}" varStatus="sta">
				<c:if test="${unclearLoan.businessType eq 6}">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${unclearLoan.id}" />
					<input name="loanCode" type="hidden" value="${unclearLoan.loanCode}" />
					<input name="businessType" type="hidden" value="${unclearLoan.businessType}" />					
					<td><input maxlength="20" name="loanOrg" value="${unclearLoan.loanOrg}" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select180 ">
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
							onblur="formatMoney('unclearLoanGuarantee', '${sta.index}', 'businessAmount');" />
					</td>
					<td>
						<input  name="marginLevel" value="${unclearLoan.marginLevel}" money="1" maxlength="12" class="input_text70 percent " type="text"/>
					</td>	
					<td>
						<input  type="hidden" name="businessBalance" value="${unclearLoan.businessBalance}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" 
							value='<fmt:formatNumber value="${unclearLoan.businessBalance}" pattern="#,###.##"/>' 
							onblur="formatMoney('unclearLoanGuarantee', '${sta.index}', 'businessBalance');" />
					</td>				
					<td><input  name="actualDay" value='<fmt:formatDate value="${unclearLoan.actualDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLevelClass" class="select180 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictLevelClass == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<select name="dictGuarantee" class="select180 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.dictGuarantee == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td>
						<select name="makeAdvances" class="select180 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}" <c:if test="${unclearLoan.makeAdvances == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('unclearLoanGuarantee', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
					<input name="dictExhibition" type="hidden" value="" />		
					<input  name="dictLoanType" value="${unclearLoan.dictLoanType}"  type="hidden"/>
					<input name="businessDay" type="hidden" value="" />							
				</tr>
				</c:if>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="unclearLoanGuaranteeTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<input name="businessType" type="hidden" value="6" />				
					<td><input maxlength="20" name="loanOrg" class="input_text70 required" type="text"/></td>
					<td>
						<select name="dictCurrency" class="select180 ">
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
					<td>
					<input  name="marginLevel" value="" money="1" maxlength="12" class="input_text70 percent " type="text"/>
					</td>	
					<td>
						<input  type="hidden" name="businessBalance" value="" class="input_text70 number " money="1" maxlength="12" />
						<input  name="businessBalanceName" class="input_text70 " type="text" money="1" maxlength="12" />
					</td>				
					<td><input  name="actualDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLevelClass" class="select180 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_level_class')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>	
					<td>
						<select name="dictGuarantee" class="select180 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>	
					<td>
						<select name="makeAdvances" class="select180 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_guarantee')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>															
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>
					<input name="dictExhibition" type="hidden" value="" />
					<input  name="dictLoanType"   type="hidden"/>	
					<input name="businessDay" type="hidden" value="" />					
				</tr>
			</tbody>
		</table>		
	</div>
	