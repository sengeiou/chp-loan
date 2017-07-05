<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="loanInfoTab" >
		<form id="loanInfoForm" >
		<table id="loanTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table  table-bordered table-condensed">
			<thead>
				<tr>
					<th>编号</th>
					<th>账户状态</th>
					<th>贷款种类</th>
					<th>是否发生过逾期</th>
					<th>发放日期</th>
					<th>到期日期</th>
					<th>截至年月</th>
					<th>贷款合同金额</th>
					<th>贷款余额</th>
					<th>逾期金额</th>
					<th>最近五年逾期次数</th>
					<th>最近五年90天以上逾期次数</th>
					<th>结清年月</th>
					<th>删除</th>
					<th>保存</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditLoanInfo" items="${creditLoanInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditLoanInfo.id}" />
					<input name="relationId" type="hidden" value="${creditLoanInfo.relationId}" />
					<td><input name="num" type="text" class="input_text50" value="${sta.index + 1}" /></td>
					<td>
						<select name="accountStatus" onchange="changeLoanStatus(this)" class="select78 required" style="width:65px" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_loaninfo_accountstatus')}" var="item">
								<option value="${item.value}" <c:if test="${creditLoanInfo.accountStatus == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="currency" class="select78 required" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_loan_type_flag')}" var="item">
								<option value="${item.value}" <c:if test="${creditLoanInfo.currency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="isOverdue" class="select78 required" style="width:65px" onchange="clearOverDueCount(this);">

							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_isoverdue')}" var="item">
								<option value="${item.value}" <c:if test="${creditLoanInfo.isOverdue == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input name="issueDay" value='<fmt:formatDate value="${creditLoanInfo.issueDay}" pattern="yyyy-MM-dd"/>' class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><input name="abortDay" value='<fmt:formatDate value="${creditLoanInfo.abortDay}" pattern="yyyy-MM-dd"/>' class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input name="actualDay" value='<fmt:formatDate value="${creditLoanInfo.actualDay}" pattern="yyyy-MM"/>' class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" style="width:58px"/></td>
					<td>
						<input type="hidden" name="conteactAmount" value="${creditLoanInfo.conteactAmount}" class="input_text70_2 required" money="1" maxlength="12" />
						<input name="conteactAmountName" class="input_text70_2 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditLoanInfo.conteactAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney1('loan', '${sta.index}', 'conteactAmount');" />
					</td>	
					<td>
						<input type="hidden" name="loanBalance" value="${creditLoanInfo.loanBalance}" class="input_text70_2 required" money="1" maxlength="12" />
						<input name="loanBalanceName" class="input_text70_2 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditLoanInfo.loanBalance}" pattern="#,###.##"/>' 
							onblur="formatMoney1('loan', '${sta.index}', 'loanBalance');" />
					</td>					
					<td>
						<input type="hidden" name="overdueAmount" value="${creditLoanInfo.overdueAmount}" class="input_text70_2 required" money="1" maxlength="12" />
						<input name="overdueAmountName" class="input_text70_2 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditLoanInfo.overdueAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney1('loan', '${sta.index}', 'overdueAmount');" />
					</td>									
					<td><input name="overdueNo" value="${creditLoanInfo.overdueNo}" class="input_text70_2 required integer" type="text"/></td>
					<td><input name="overdueForNo" value="${creditLoanInfo.overdueForNo}" class="input_text70_2 required integer" type="text"/></td>
					<td><input name="settleDay"
						<c:if test="${creditLoanInfo.accountStatus == '1'}">class="select75 Wdate required"</c:if>
						<c:if test="${creditLoanInfo.accountStatus != '1'}">class="select75 Wdate"</c:if>
						value='<fmt:formatDate value="${creditLoanInfo.settleDay}" pattern="yyyy-MM"/>' type="text"  onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" style="width:58px"/></td>
					<td><a name="aRemoveInfo" onClick="removeTr('loan', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
					<td><a name="aSaveInfo" onClick="saveTr('loan',this);" href="javascript:void(0);" >保存</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="loanTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="relationId" type="hidden" value="${creditReportSimple.id}" />
					<td><input name="num" type="text" class="input_text50" value="" /></td>
					<td>
						<select name="accountStatus" onchange="changeLoaSta(this)" class="select78 required" style="width:65px">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_loaninfo_accountstatus')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="currency" class="select78 required" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_loan_type_flag')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="isOverdue" class="select78 required" style="width:65px">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_isoverdue')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input name="issueDay" value="" class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><input name="abortDay" value="" class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input name="actualDay" value="" class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" style="width:58px"/></td>
					<td>
						<input type="hidden" name="conteactAmount"  class="input_text70_2 required" money="1" maxlength="12" />
						<input name="conteactAmountName" class="input_text70_2 required" type="text" money="1" maxlength="12" />
					</td>
					<td>
						<input type="hidden" name="loanBalance"  class="input_text70_2 required" money="1" maxlength="12" />
						<input name="loanBalanceName" class="input_text70_2 required" type="text" money="1" maxlength="12" />
					</td>						
					<td>
						<input type="hidden" name="overdueAmount"  class="input_text70_2 required" money="1" maxlength="12" />
						<input name="overdueAmountName" class="input_text70_2 required" type="text" money="1" maxlength="12" />
					</td>						
					<td><input name="overdueNo" value="" class="input_text70_2 required integer" type="text"/></td>
					<td><input name="overdueForNo" value="" class="input_text70_2 required integer" type="text"/></td>
					<td><input name="settleDay" value="" class="input_text70_2" type="text" class="select75 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" style="width:58px"/></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
					<td><a name="aSaveInfo" onClick="saveTr('loan',this)" href="javascript:void(0);">保存</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	