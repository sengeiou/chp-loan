<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="queryInfoTab" >
		<form id="queryInfoForm">
		<table id="queryTable" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table">
			<thead>
				<tr>
					<th>编号<font color="red"></font></th>
					<th>查询日期<font color="red"></font></th>
					<th>查询原因<font color="red"></font></th>
					<th>删除</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditQueryRecord" items="${creditQueryRecordList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditQueryRecord.id}" />
					<input name="relationId" type="hidden" value="${creditQueryRecord.relationId}" />
					<td><input name="num" type="text" class="input_text50" value="${sta.index + 1}" /></td>
					<td><input name="queryDay" value='<fmt:formatDate value="${creditQueryRecord.queryDay}" pattern="yyyy-MM-dd"/>'class="input_text70 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="queryType" class="select78 required" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_queryrecord_querytype')}" var="item">
								<option value="${item.value}" <c:if test="${creditQueryRecord.queryType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><a name="aRemoveInfo" onClick="removeTr('query', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="queryTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="relationId" type="hidden" value="${creditReportSimple.id}" />
					<td><input name="num" type="text" class="input_text50" value="" /></td>
					<td><input name="queryDay" value="" class="input_text70 required" type="text" calsss="select75 Wdate" onclick="WdatePicker()" /></td>
					<td>
						<select name="queryType" class="select78 required" >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_credit_queryrecord_querytype')}" var="item">
								<option value="${item.value}">${item.label}</option>
						
							</c:forEach>
						</select>
					</td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	