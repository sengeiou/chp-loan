<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
		<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" value="1" />
		<input type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.department" class="input_text178 required" maxlength="20"/>
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2" />
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.post" class="input_text178 required" maxlength="20"/>
	</td>

	<td>
		<select id="workTogetherContact_${parentIndex}_${currIndex}" index="2${parentIndex}${currIndex}" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" class="required select180">
			<option value="">请选择</option>
			<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
				<option value="${item.value}" class="${item.id}">${item.label}</option>
			</c:forEach>
		</select>
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.remarks" class="input_text178" maxlength="100"/>
	</td>
	<td class="tcenter">
		<input type="button" class="btn_edit" value="删除" onclick="contact.delRow(this,'worktogether_contact_table_${parentIndex}','CONTACT')" style="width: 50px;" />
	</td>
</tr>