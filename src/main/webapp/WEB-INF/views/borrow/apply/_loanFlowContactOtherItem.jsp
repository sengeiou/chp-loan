<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
		<input type="hidden" name="customerContactList.relationType" value="2" />
		<input id="otherContact_contactName_${parentIndex}" type="text" name="customerContactList.contactName" value="${ccItem.contactName}" class="input_text180 required stringCheck contactCheck" />
	</td>
	<td>
		<input type="hidden" name="customerContactList.contactRelation" value="3">
		<input id="otherContact_remarks_${parentIndex}" type="text" name="customerContactList.remarks" value="${ccItem.remarks}" class="input_text180 required chineseCheck2" maxlength="100"/>
	</td>
	<td>
		<input id="otherContact_homeTel_${parentIndex}" type="text" name="customerContactList.homeTel" value="${ccItem.homeTel}" class="input_text180 isTel" />
	</td>
	<td>
		<input id="otherContact_contactMobile_${parentIndex}" type="text" name="customerContactList.contactMobile" value="${ccItem.contactMobile}" class="input_text180 isMobile required" />
	</td>
	<td class="tcenter"><input type="button" onclick="contact.delRow(this,'table_otherArea','CONTACT');" class="btn_edit" value="删除"></input></td>
</tr>