<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
		<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" value="2" />
		<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.id"></input>
		<input type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
	</td>
	<td>
		<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" value="3">
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.remarks" class="input_text178 required chineseCheck2" maxlength="100"/>
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.homeTel" class="input_text178 isTel" />
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2" />
	</td>
	<td class="tcenter">
		<input type="button" class="btn_edit" value="删除" onclick="contact.delRow(this,'other_contact_table_${parentIndex}','CONTACT')" style="width: 50px;" />
	</td>
</tr>