<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditAffiliatedEnterpriseTab" >
		<form id="creditAffiliatedEnterpriseForm">
		<table id="creditAffiliatedEnterpriseTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>名称</th>
					<th>贷款卡编号</th>
					<th>关系</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditAffiliatedEnterprise" items="${creditAffiliatedEnterpriseList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditAffiliatedEnterprise.id}" />
					<input name="loanCode" type="hidden" value="${creditAffiliatedEnterprise.loanCode}" />
					<td><input maxlength="20" name="name" value="${creditAffiliatedEnterprise.name}" class="input_text70 " type="text" /></td>
					<td><input maxlength="20" name="loanCardCode" value="${creditAffiliatedEnterprise.loanCardCode}" class="input_text70 " type="text" /></td>
					<td>
						<select name="dictRepeatRelation" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_repeat_relation')}" var="item">
								<option value="${item.value}" <c:if test="${creditAffiliatedEnterprise.dictRepeatRelation == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td><a name="aRemoveInfo" onClick="removeTr('creditAffiliatedEnterprise', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditAffiliatedEnterpriseTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<td><input maxlength="20" name="name" class="input_text70 " type="text" /></td>
					<td><input maxlength="20" name="loanCardCode" class="input_text70 " type="text" /></td>
					<td>
						<select name="dictRepeatRelation" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_repeat_relation')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	