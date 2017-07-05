<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
		<input type="hidden" name="customerContactList.relationType" value="0" />
		<input id="relaticontact_contactName_${parentIndex}" type="text" name="customerContactList.contactName" class="input_text180 required stringCheck contactCheck" />
	</td>
	<td>
		<select id="relaticontact_contactRelation_${parentIndex}" name="customerContactList.contactRelation" index="${parentIndex}" class="select180 required">
			<option value="">请选择</option>
			<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
				<c:if test="${item.value != '2' }">
					<option value="${item.value}">${item.label}</option>
				</c:if>	
			</c:forEach>
		</select>
	</td>
	<td>
		<input id="relaticontact_certNum_${parentIndex}" type="text" name="customerContactList.certNum" class="input_text180 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
	</td>
	<td>
		<input id="relaticontact_contactSex_${parentIndex}" type="text" name="customerContactList.contactSex" class="input_text180" />
	</td>
	<td>
		<input id="relaticontact_homeTel_${parentIndex}" type="text" name="customerContactList.homeTel" class="input_text180 isTel mobileAndTelNeedOne" />
	</td>
	<td>
		<input id="relaticontact_contactMobile_${parentIndex}" type="text" name="customerContactList.contactMobile" class="input_text180 isMobile mobileAndTelNeedOne" />
	</td>
	<td class="tcenter"><input type="button" onclick="contact.delRow(this,'table_contactRelationArea','CONTACT');" class="btn_edit" value="删除" /></td>
</tr>