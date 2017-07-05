<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditExecutiveInfoTab" >
		<form id="creditExecutiveInfoForm">
		<table id="creditExecutiveInfoTable"  class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th>职务</th>
					<th>姓名</th>
					<th>证件类型</th>
					<th>证件号码</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="creditExecutiveInfo" items="${creditExecutiveInfoList}" varStatus="sta">
				<tr id="infoTr${sta.index + 1}">
					<input name="id" type="hidden" value="${creditExecutiveInfo.id}" />
					<input name="loanCode" type="hidden" value="${creditExecutiveInfo.loanCode}" />
					<td>
						<select name="dictCompPost" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_comp_post')}" var="item">
								<option value="${item.value}" <c:if test="${creditExecutiveInfo.dictCompPost == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td><input maxlength="20" name="name" value="${creditExecutiveInfo.name}" class="input_text70" type="text" /></td>
					<td>
						<select name="dictCertType" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_cart_type')}" var="item">
								<option value="${item.value}" <c:if test="${creditExecutiveInfo.dictCertType == item.value}">selected</c:if> >${item.label}</option>
							</c:forEach>
						</select>
					</td> 
					<td><input maxlength="20" name="customerCertNum" value="${creditExecutiveInfo.customerCertNum}" onblur="javascript:checkIdNum(this);" class="input_text178 card" type="text" /></td>
					<td><a name="aRemoveInfo" onClick="removeTr('creditExecutiveInfo', '${sta.index + 1}');" href="javascript:void(0);" >删除</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		</form>
		
		<table id="creditExecutiveInfoTableHide" class="hide">
			<tbody>
				<tr id="infoTr">
					<input name="id" type="hidden" value="" />
					<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
					<td>
						<select name="dictCompPost" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_comp_post')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>					
					<td><input maxlength="20"  name="name" class="input_text70 " type="text" /></td>
					<td>
						<select name="dictCertType" class="select78 "  >
							<option value="" >请选择</option>
							<c:forEach items="${fns:getDictList('jk_enterprise_cart_type')}" var="item">
								<option value="${item.value}" >${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input maxlength="20"  name="customerCertNum" class="input_text178 card" type="text" /></td>
					<td><a name="aRemoveInfo" href="javascript:void(0);">删除</a></td>
				</tr>
			</tbody>
		</table>		
	</div>
	