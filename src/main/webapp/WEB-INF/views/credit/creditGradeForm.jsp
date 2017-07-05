<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditGradeTab" >
		<form id="creditGradeForm">
		<table id="creditGradeTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table2">
			<thead>
				<tr>
					<th>时间</th>
					<th>机构</th>
					<th>等级</th>
					<th>添加日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditGrade" items="${creditGradeList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditGrade.id}" />
					<input name="loanCode" type="hidden" value="${creditGrade.loanCode}" />
					<td><input  name="gradeTime" value='<fmt:formatDate value="${creditGrade.gradeTime}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="dictOrgName" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_org_name')}" var="item">
								<option value="${item.value}" <c:if test="${creditGrade.dictOrgName == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>
					<td>
						<select name="dictRank" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_rank')}" var="item">
								<option value="${item.value}" <c:if test="${creditGrade.dictRank == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>					
					</td>										
					<td><input  name="addDay" value='<fmt:formatDate value="${creditGrade.addDay}" pattern="yyyy-MM-dd"/>' class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" onClick="removeTr('creditGrade', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditGradeTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />					
					<td><input  name="gradeTime" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td>
						<select name="dictOrgName" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_org_name')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>
					<td>
						<select name="dictRank" class="select78 ">
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_rank')}" var="item">
								<option value="${item.value}"  >${item.label}</option>
							</c:forEach>
						</select>					
					</td>					
					<td><input  name="addDay" class="input_text70 " type="text" calsss="select75 Wdate" onclick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" /></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);" >删除</a></td>					
				</tr>
			</tbody>
		</table>		
	</div>
	