<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
     <!-- 新添加的的联系人 -->
	<div  class="hide" >
			<table id="myConfirmCopy" cellpadding="0" cellspacing="0" border="0"  width="100%" class="table03" >
				<tbody>
				<tr>
                <td>
                <input type="hidden" mark="loanCode"  name="loanCode"></input>
                <input type="hidden" mark="id" name="id">
	  			<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType">
                 <input type="text" mark="contactName" name="contactName" maxlength="10" class="input_text70 required realName2"/>
                </td>
             
                <td>
                <select mark="dictContactRelation" name="dictContactRelation" class="select180 required">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" mark="contactUint"  maxlength="20" name="contactUint" class="input_text178"/></td>
                <td>
                   <input type="text" mark="dictContactNowAddress" maxlength="50" name="dictContactNowAddress"  class="input_text150" style="width:250px">
                </td>
                <td><input type="text" mark="compTel" name="compTel" class="input_text178 isTel "/></td>
                <td><input type="text" mark="contactUnitTel" name="contactUnitTel" class="input_text178 isMobile required"/>
                </td>
                <td class="tcenter"><input type="button" onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
              </tr>
              </tbody>
            </table>
	</div>