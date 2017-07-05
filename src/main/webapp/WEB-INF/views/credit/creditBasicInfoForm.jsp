<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
	<div id="creditBasicInfoTab" class="control-group pb10 form-search ">
		<form id="creditBasicInfoForm">
			<table class="table1" width="100%" cellspacing="0" cellpadding="0" border="0">
				<tbody>
					<tr>
						<input name="id" type="hidden" value="${creditBasicInfo.id}" />
						<input name="loanCode" type="hidden" value="${enterpriseCredit.loanCode}" />
						<td>
							<lable class="lab" >名称：</lable>
							<input name="creditName" value="${creditBasicInfo.creditName}" class="input_text178 " type="text" maxlength="100"/></td>
						</td>
						<td>
							<lable class="lab" >登记注册号：</lable>
							<input maxlength="20" name="registrationNumber" value="${creditBasicInfo.registrationNumber}" class="input_text178 " type="text" /></td>
						</td>
						<td>
							<lable class="lab" >登记注册类型：</lable>
							<input maxlength="20" name="registrationType" value="${creditBasicInfo.registrationType}" class="input_text178 " type="text" /></td>
						</td>
					</tr>
					<tr>
						<td>
							<lable class="lab" >国税登记号：</lable>
							<input digits=”1” maxlength="20" name="taxRegistrationNumber" value="${creditBasicInfo.taxRegistrationNumber}" class="input_text178 " type="text" /></td>
						</td>
						<td>
							<lable class="lab" >贷款卡状态：</lable>
							<select name="dictLoanCardState" class="select180"  >
								<option value="" >请选择</option>
								<c:forEach items="${fns:getDictList('jk_enterprise_card_status')}" var="item">
									<option value="${item.value}" <c:if test="${creditBasicInfo.dictLoanCardState == item.value}">selected</c:if> >${item.label}</option>
								</c:forEach>
							</select>	
						</td>
						<td>
							<lable class="lab" >地税登记号：</lable>
							<input digits=”1” maxlength="20" name="landTaxRegistrationNumber" value="${creditBasicInfo.landTaxRegistrationNumber}" class="input_text178 " type="text" /></td>
						</td>
					</tr>
						<td>
							<lable class="lab" >登记注册日期：</lable>
							<input name="registrationDate" value='<fmt:formatDate value="${creditBasicInfo.registrationDate}" pattern="yyyy-MM-dd"/>'  
								type="text" class="input_text178 Wdate" onclick="WdatePicker({maxDate:'%y-%M-%d'})" />
						</td>
						<td>
							<lable class="lab" >有效截至日期：</lable>
							<input name="expireDate" value='<fmt:formatDate value="${creditBasicInfo.expireDate}" pattern="yyyy-MM-dd"/>'  
								type="text" class="input_text178 Wdate" onclick="WdatePicker()" />
						</td>
					</tr>
					<tr>
						<td>
							<lable class="lab" >省：</lable>
							<input  id="provinceHid" value="${creditBasicInfo.province}"  type="hidden"  />
							<select str="provinceTD" name="province" class="select180";padding-left:0px">
								<option value="" >请选择</option>
								<c:forEach var="item" items="${provinceList}" varStatus="status">
										<option value="${item.areaCode}">${item.areaName}</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<lable class="lab" >市：</lable>
							<input  id="cityHid" value="${creditBasicInfo.city}"  type="hidden"  />
							<select str="cityTD" name="city" class="select180";padding-left:0px">
								<option value="" >请选择</option>
							</select>
						</td>
						<td>
							<lable class="lab" >区：</lable>
							<input  id="areaHid" value="${creditBasicInfo.area}"  type="hidden"  />
							<select name="area" class="select180";padding-left:0px">
								<option value="" >请选择</option>
							</select>
						</td>
					<tr>	
						<td>
							<lable class="lab" >注册地址：</lable>
							<input maxlength="100" name="address" type="text" class="input_text178" value="${creditBasicInfo.address}"/>
						</td>
					</tr>
					</tr>
					
				</tbody>
			</table>
		</form>
		
	</div>
