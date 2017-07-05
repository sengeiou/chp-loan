<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
		<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" value="0" />
		<input type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
	</td>
	<td>
		<select id="relativesContact_${parentIndex}_${currIndex}" index="1${parentIndex}${currIndex}" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" class="required select180">
			<option value="">请选择</option>
			<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
				<option value="${item.value}" class="${item.id}">${item.label}</option>
			</c:forEach>
		</select>
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.certNum" class="input_text178 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactSex" class="input_text178" />
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.homeTel" class="input_text178 isTel mobileAndTelNeedOne" />
	</td>
	<td>
		<input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 isMobile coboMobileDiff1 coboMobileDiff2 mobileAndTelNeedOne" />
	</td>
	<td class="tcenter">
		<input type="button" class="btn_edit" value="删除" onclick="contact.delRow(this,'relatives_contact_table_${parentIndex}','CONTACT')" style="width: 50px;" />
	</td>
</tr>