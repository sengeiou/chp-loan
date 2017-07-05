<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td align="center"><input type="hidden" class="contactId" name="customerContactList.id"/>
	   <input type="text" name="customerContactList.contactName" class="input_text70 required stringCheck contactCheck"/></td>
	 <td align="center">
     	<select name="customerContactList.relationType" index="${parentIndex}" id="relationType_${parentIndex}" class="required select180">
       	  <option value="">请选择</option>
          <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
		  	  <option value="${item.value}" 
		      <c:if test="${ccItem.relationType==item.value}">
		         selected=true 
		      </c:if>
		  	   >${item.label}
			   </option>
		  	</c:forEach>
	       </select>
      </td>
	<td align="center"><select  id="contactRelation_${parentIndex}" 
		name="customerContactList.contactRelation"
		  class="select180 required">
			<option value="">请选择</option>
	    </select>
	</td>
	<td align="center"><input type="text" name="customerContactList.contactSex"
		 class="input_text178"  maxlength="180" /></td>
	<td align="center"><input type="text"
		name="customerContactList.contactNowAddress"
		 class="input_text178"  maxlength="80"/></td>
	<td align="center"><input type="text" name="customerContactList.contactUnitTel"
		 class="input_text178 isTel" /></td>
	<td align="center"><input type="text" name="customerContactList.contactMobile"
		 class="input_text178 required isMobile contactMobileDiff1 contactMobileDiff2" /></td>
	<td class="tcenter"><input type="button"
		onclick="contact.delRow(this,'contactArea','CONTACT');" class="btn_edit" 
		value="删除"></input></td>
</tr>