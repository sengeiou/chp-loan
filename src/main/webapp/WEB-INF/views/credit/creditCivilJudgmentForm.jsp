<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="civilJudgmentTab" >
		<form id="civilJudgmentForm">
		<table id="civilJudgmentTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>立案法院</th>
					<th>案由</th>
					<th>案号</th>
					<th>诉讼标的</th>
					<th>结案方式</th>
					<th>立案日期</th>
					<th>诉讼地位</th>
					<th>审判程序</th>
					<th>诉讼标的金额（元）</th>
					<th>判决/调解生效日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="civilJudgment" items="${civilJudgmentList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${civilJudgment.id}" />
					<input name="loanCode" type="hidden" value="${civilJudgment.loanCode}" />
					<td><input maxlength="100" name="filingCourt" value="${civilJudgment.filingCourt}" class="input_text70 " type="text"/></td>
					<td><input maxlength="100" name="matterCase" value="${civilJudgment.matterCase}" class="input_text70 " type="text"/></td>
					<td><input maxlength="20" name="caseReference" value="${civilJudgment.caseReference}" class="input_text70 " type="text"/></td>
					<td><input maxlength="20" name="objectLitigation" value="${civilJudgment.objectLitigation}" class="input_text70 number " type="text"/></td>
					<td>
						<select name="dictClosedManner" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_closed_manner')}" var="item">
								<option value="${item.value}" <c:if test="${civilJudgment.dictClosedManner == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input  name="filingDay" value='<fmt:formatDate value="${civilJudgment.filingDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>					
					<td>
						<select name="dictLawsuitPosition" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_lawsuit_position')}" var="item">
								<option value="${item.value}" <c:if test="${civilJudgment.dictLawsuitPosition == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input maxlength="100" name="trialProcedure" value="${civilJudgment.trialProcedure}" class="input_text70 " type="text"/></td>
					<td>
						<input  type="hidden" name="objectLitigationAmount" value="${civilJudgment.objectLitigationAmount}" class="input_text70 number " money="1" maxlength="12" />
						<input  name="objectLitigationAmountName" class="input_text70 " type="text" money="1" maxlength="12" value='<fmt:formatNumber value="${civilJudgment.objectLitigationAmount}" pattern="#,###.##"/>' onblur="formatMoney('civilJudgment', '${sta.index}', 'objectLitigationAmount');" />
					</td>					
					<td><input  name="effectiveDay" value='<fmt:formatDate value="${civilJudgment.effectiveDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" onClick="removeTr('civilJudgment', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="civilJudgmentTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />					
					<td><input maxlength="100" name="filingCourt" class="input_text70 " type="text"/></td>
					<td><input maxlength="100" name="matterCase" class="input_text70 " type="text"/></td>
					<td><input maxlength="20" name="caseReference" class="input_text70 " type="text"/></td>
					<td><input maxlength="20" name="objectLitigation" class="input_text70 number " type="text"/></td>
					<td>
						<select name="dictClosedManner" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_closed_manner')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input  name="filingDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictLawsuitPosition" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_lawsuit_position')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>	
					<td><input maxlength="100" name="trialProcedure" class="input_text70 " type="text"/></td>
					<td>
						<input  type="hidden" name="objectLitigationAmount" class="input_text70 number " money="1" maxlength="12" />
						<input  name="objectLitigationAmountName" class="input_text70 " type="text" money="1" maxlength="12"/>
					</td>
					<td><input  name="effectiveDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</tbody>
		</table>		
	</div>
	