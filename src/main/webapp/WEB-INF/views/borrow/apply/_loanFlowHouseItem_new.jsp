<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<table class="table1" id="loanHouseArea_${parentIndex}" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td colspan="3">
			<input type="button" name="delHouseItem" class="btn btn-small ml10" value="删除" dbId="-1" currId="loanHouseArea_${parentIndex}" />
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<label class="lab">
				<span class="red">*</span>
				房产地址：
			</label>
			<select class="select78 mr10 houseProvince required" id="houseProvince_${parentIndex}" name="customerLoanHouseList[${parentIndex}].houseProvince">
				<option value="">请选择</option>
				<c:forEach var="item" items="${provinceList}" varStatus="status">
					<option value="${item.areaCode}">${item.areaName}</option>
				</c:forEach>
			</select>
			-
			<select class="select78 mr10 houseCity required" id="houseCity_${parentIndex}" name="customerLoanHouseList[${parentIndex}].houseCity">
				<option value="">请选择</option>
			</select>
			-
			<select class="select78 houseArea required" id="houseArea_${parentIndex}" name="customerLoanHouseList[${parentIndex}].houseArea">
				<option value="">请选择</option>
			</select>
			<input name="customerLoanHouseList[${parentIndex}].houseAddress" type="text" class="input_text178 required" style="width: 250px" maxlength="100">
			<input type="hidden" id="houseCityHidden_${parentIndex}"/>
			<input type="hidden" id="houseAreaHidden_${parentIndex}"/>
			<input type="hidden" name="customerLoanHouseList[${parentIndex}].id" class="houseListId"/>
		</td>
	</tr>
	<tr>
		<td>
			<label class="lab"><span class="red">*</span>房产类型：</label>
			<select name="customerLoanHouseList[${parentIndex}].dictHouseType" class="select180 required">
				<option value="">请选择</option>
				<c:forEach items="${fns:getNewDictList('jk_design_use')}" var="item">
					<option value="${item.value}" >${item.label}</option>
				</c:forEach>
			</select>
			<input type="text" name="customerLoanHouseList[${parentIndex}].dictHouseTypeRemark" class="input_text178 collapse chineseCheck2" maxlength='50' />
		</td>
		<td>
			<label class="lab"><span class="red">*</span>购买方式：</label>
			<select name="customerLoanHouseList[${parentIndex}].houseBuyway" class="select180 required">
				<option value="">请选择</option>
				<c:forEach items="${fns:getNewDictList('jk_house_buywayg')}" var="item">
					<option value="${item.value}">${item.label}</option>
				</c:forEach>
			</select>
		</td>
		<td>
			<label class="lab">贷款总额(万元)：</label>
			<input type="text" name="customerLoanHouseList[${parentIndex}].houseLoanAmount" class="input_text178 numCheck" min="0" max="999999999.00"/>
		</td>
	</tr>
	<tr>
		<td>
			<label class="lab">贷款余额(元)：</label>
			<input type="text" name="customerLoanHouseList[${parentIndex}].houseLessAmount" class="input_text178 numCheck" min="0" max="999999999.00"/>
		</td>
		<td>
			<label class="lab">月还款(元)：</label>
			<input type="text" name="customerLoanHouseList[${parentIndex}].houseMonthRepayAmount" class="input_text178 numCheck" min="0" max="999999999.00"/>
		</td>
		<td>
			<label class="lab">贷款年限(年)：</label>
			<input type="text" name="customerLoanHouseList[${parentIndex}].houseLoanYear" class="input_text178 numCheck2 max50"/>
		</td>
	</tr>
	<tr>
		<td>
			<label class="lab"><span class="red">*</span>建筑面积(平方米)：</label>
			<input type="text" name="customerLoanHouseList[${parentIndex}].houseBuilingArea" class="input_text178 numCheck required" min="0" max="999999999.00"/>
		</td>
		<td>
			<label class="lab"><span class="red">*</span>购买时间：</label>
			<input class="input_text178 Wdate required" name="customerLoanHouseList[${parentIndex}].houseBuyday" type="text" class="Wdate" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
		</td>
		<td>
			<label class="lab">购买价格(万元)：</label>
			<input type="text" name="customerLoanHouseList[${parentIndex}].houseAmount" class="input_text178 numCheck" min="0" max="999999999.00"/>
		</td>
	</tr>
	<tr>
		<td>
			<label class="lab"><span class="red">*</span>房屋所有权：</label>
			<select class="select180 required" name="customerLoanHouseList[${parentIndex}].dictHouseCommon" >
				<option value="">请选择</option>
				<c:choose>
					<c:when test="${dictMarry=='0'}">
						<c:forEach items="${fns:getNewDictList('jk_house_common_type')}" var="item">
							<c:if test="${item.label!='夫妻共有'}">
								<option value="${item.value}">
									${item.label}
								</option>
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<c:forEach items="${fns:getNewDictList('jk_house_common_type')}" var="item">
							<option value="${item.value}">
								${item.label}
							</option>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</select>
		</td>
	</tr>
	</tbody>
</table>