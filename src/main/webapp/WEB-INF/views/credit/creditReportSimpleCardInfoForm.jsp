<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="cardInfoTab" >
		<form id="cardInfoForm" >
		<table id="cardTable" cellpadding="0" cellspacing="0" border="0"  width="100%"  class="table  table-bordered table-condensed ">
			<thead>
				<tr>
					<th>编号</th>
					<th>账户状态</th>
					<th>币种</th>
					<th>是否发生过逾期</th>
					<th>发放日期</th>
					<th>截至年月</th>
					<th>额度</th>
					<th>已使用额度</th>
					<th>逾期金额</th>
					<th>最近5年逾期次数</th>
					<th >最近五年90天以上逾期次数</th>
					<th>销户年月</th>
					<th>删除</th>
					<th>保存</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditCardInfo" items="${creditCardInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditCardInfo.id}" />
					<input name="relationId" type="hidden" value="${creditCardInfo.relationId}" />
					<td><input name="num" type="text" class="input_text50" value="${sta.index + 1}" /></td>
					<td>
						<select name="accountStatus" class="select78 required" onchange="changeCardStatus(this)" style="width:65px">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_cardinfo_accountstatus')}" var="item">
								<option value="${item.value}" <c:if test="${creditCardInfo.accountStatus == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="currency" class="select78 required"  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_currency')}" var="item">
								<option value="${item.value}" <c:if test="${creditCardInfo.currency == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="isOverdue" class="select78 required" style="width:65px" onchange="clearOverDueCount(this);">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_isoverdue')}" var="item">
								<option value="${item.value}" <c:if test="${creditCardInfo.isOverdue == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input  name="issueDay" value='<fmt:formatDate value="${creditCardInfo.issueDay}" pattern="yyyy-MM-dd"/>' class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="abortDay" value='<fmt:formatDate value="${creditCardInfo.abortDay}" pattern="yyyy-MM"/>' class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" /></td>
					<td>
						<input  type="hidden" name="limit" value="${creditCardInfo.limit}" class="input_text70_2 required" money="1" maxlength="12" />
						<input  name="limitName" class="input_text70_2 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCardInfo.limit}" pattern="#,###.##"/>' 
							onblur="formatMoney1('card', '${sta.index}', 'limit')" />
					</td>
					<td>
						<input  type="hidden" name="usedLimit" value="${creditCardInfo.usedLimit}" class="input_text70_2 required" money="1" maxlength="12" />
						<input  name="usedLimitName" class="input_text70_2 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCardInfo.usedLimit}" pattern="#,###.##"/>'
							onblur="formatMoney1('card', '${sta.index}', 'usedLimit')" />
					</td>
					<td>
						<input  type="hidden" name="overdueAmount" value="${creditCardInfo.overdueAmount}" class="input_text70_2 required" money="1" maxlength="12" />
						<input  name="overdueAmountName" class="input_text70_2 required" type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${creditCardInfo.overdueAmount}" pattern="#,###.##"/>' 
							onblur="formatMoney1('card', '${sta.index}', 'overdueAmount')" />
					</td>										
					<td><input  name="overdueNo" value="${creditCardInfo.overdueNo}" class="input_text70_2 required integer" type="text"/></td>
					<td><input  name="overdueForNo" value="${creditCardInfo.overdueForNo}" class="input_text70_2 required integer" type="text"/></td>
					<td><input  name="cancellationDay" 
							<c:if test="${creditCardInfo.accountStatus == '2'}">class="select75 Wdate required"</c:if>
							<c:if test="${creditCardInfo.accountStatus != '2'}">class="select75 Wdate"</c:if>
							value='<fmt:formatDate value="${creditCardInfo.cancellationDay}" pattern="yyyy-MM"/>' class="input_text70_2" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" /></td>
					<td><a name="aRemoveInfo" onClick="removeTr('card', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
					<td><a name="aSaveInfo" onClick="saveTr('card',this);" href="javascript:void(0);" >保存</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="cardTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="relationId" type="hidden" value="${creditReportSimple.id}" />
					<td><input name="num" type="text" class="input_text50" value="1" /></td>
					<td>
						<select name="accountStatus" onchange="changeCarSta(this);" class="select78 required" style="width:65px" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_cardinfo_accountstatus')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="currency" class="select78 required"  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_currency')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<select name="isOverdue" class="select78 required" style="width:65px" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_isoverdue')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input  name="issueDay" class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td><input  name="abortDay" class="input_text70_2 required" type="text" calsss="select75 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" /></td>
					<td>
						<input  type="hidden" name="limit"  class="input_text70_2 required" money="1" maxlength="12" />
						<input  name="limitName" class="input_text70_2 required" type="text" money="1" maxlength="12" />
					</td>
					<td>
						<input  type="hidden" name="usedLimit"  class="input_text70_2 required" money="1" maxlength="12" />
						<input  name="usedLimitName" class="input_text70_2 required" type="text" money="1" maxlength="12" />
					</td>	
					<td>
						<input  type="hidden" name="overdueAmount"  class="input_text70_2 required" money="1" maxlength="12" />
						<input  name="overdueAmountName" class="input_text70_2 required" type="text" money="1" maxlength="12" />
					</td>									
					<td><input name="overdueNo" class="input_text70_2 required integer" type="text"/></td>
					<td><input name="overdueForNo" class="input_text70_2 required integer" type="text"/></td>
					<td><input required name="cancellationDay" type="text" class="select75 Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false})" /></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
					<td><a name="aSaveInfo" onClick="saveTr('card',this)" href="javascript:void(0);">保存</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	