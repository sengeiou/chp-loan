<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
		<input type="hidden" name="customerContactList.relationType" value="1" />
		<input id="workProveContact_contactName_${parentIndex}" type="text" name="customerContactList.contactName" class="input_text180 required stringCheck contactCheck" />
	</td>
	<td>
		<input id="workProveContact_department_${parentIndex}" type="text" name="customerContactList.department" class="input_text180 required" maxlength="20"/>
	</td>
	<td>
		<input id="workProveContact_contactMobile_${parentIndex}" type="text" name="customerContactList.contactMobile" class="input_text180 isMobile required" />
	</td>
	<td>
		<input id="workProveContact_post_${parentIndex}" type="text" name="customerContactList.post"  class="input_text180 required" maxlength="20"/>
	</td>
	<td>
		<select id="workProveContact_contactRelation_${parentIndex}" name="customerContactList.contactRelation" class="select180 required">
			<option value="">请选择</option>
			<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
				<option value="${item.value}">${item.label}</option>
			</c:forEach>
		</select>
	</td>
	<td>
		<input id="workProveContact_remarks_${parentIndex}" type="text" name="customerContactList.remarks" class="input_text180" maxlength="100"/>
	</td>
	<td class="tcenter"><input type="button" onclick="contact.delRow(this,'table_workProvePersonArea','CONTACT');" class="btn_edit" value="删除" /></td>
</tr>