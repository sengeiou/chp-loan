<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<tr>
	<td>
	  <span>
	  <c:forEach items="${fns:getDictList('jk_pledge_flag')}" var="curItem">
			<input type="radio" name="loanCreditInfoList[${parentIndex}].dictMortgageType"
				value="${curItem.value}"  class="required" />${curItem.label}
        </c:forEach> 
        <input type="hidden" class="creditId" name="loanCreditInfoList.id" />
       </span>
    </td>
	<td><input
		name="loanCreditInfoList.creditMortgageGoods"
		type="text" class="input_text178 required" /></td>
	<td><input
		name="loanCreditInfoList.orgCode" type="text"
		class="input_text178" /></td>
	<td><input
		name="loanCreditInfoList.creditLoanLimit" maxlength="13" 
		 class="input_text178 number numCheck positiveNumCheck" /></td>
	<td><input
		name="loanCreditInfoList.creditMonthsAmount" maxlength="13" 
		  class="input_text178 number numCheck positiveNumCheck monthsAmountCheck" /></td>
	<td><input
		name="loanCreditInfoList.creditLoanBlance" maxlength="13" 
		 class="input_text178 number numCheck positiveNumCheck balanceAmountCheck" /></td>
	<td><input
		name="loanCreditInfoList.creditCardNum"
		 class="input_text178 digits positiveNumCheck totalCardNumCheck"/></td>
	<td class="tcenter"><input type="button" onclick="contact.delRow(this,'loanCreditArea','CREDIT');" class="btn_delete btn_edit"
		value="删除" /></td>
</tr>