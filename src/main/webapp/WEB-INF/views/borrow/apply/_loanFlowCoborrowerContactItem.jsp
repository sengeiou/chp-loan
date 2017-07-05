<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
  <tr>
    <td>
    <input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.id"/>
    <input type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck"/>
    </td>
     <td>
       <select id="relationType_${parentIndex}_${currIndex}"  index="${parentIndex}${currIndex}" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" class="required select180">
         <option value="">请选择</option>
         <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
		   <option value="${item.value}">${item.label}</option>
		  </c:forEach>
	    </select>
      </td>
    <td>
     <select id="contactRelation_${parentIndex}${currIndex}" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" class="select180 required">
       <option value="">请选择</option>
       </select>
    </td>
    <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactSex" class="input_text178"/></td>
    <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactNowAddress" class="input_text178"/></td>
    <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactUnitTel" class="input_text178 isTel"/></td>
    <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/></td>
    <td class="tcenter"><input type="button" class="btn_edit"  value="删除"  onclick="contact.delRow(this,'table_${parentIndex}','CONTACT')" style="width:50px;"/></td>
 </tr>