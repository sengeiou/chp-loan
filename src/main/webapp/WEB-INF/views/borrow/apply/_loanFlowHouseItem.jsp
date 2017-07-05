<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<table class="table1" id="loanHouseArea_${parentIndex}" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td colspan="3"> <input type="button" name="delHouseItem" class="btn btn-small ml10" value="删除" dbId="-1" currId="loanHouseArea_${parentIndex}"/>
                </td>
             </tr>
            <tr>
                <td><label class="lab">购买方式：</label>
                  <select id="cardType"
									name="customerLoanHouseList[${parentIndex}].houseBuyway" class="select180">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_house_buywayg')}"
											var="item">
											<option value="${item.value}"
												<c:if test="${curLoanHouse.houseBuyway == item.value}">
					      selected = true
					     </c:if>>${item.label}</option>
										</c:forEach>
				 </select>
                </td>
                <td><label class="lab">购买日期：</label>
                <input id="d4311" name="customerLoanHouseList[${parentIndex}].houseBuyday" type="text" class="input_text178 Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
                <td><label class="lab">房屋面积：</label>
                  <input type="text" name="customerLoanHouseList[${parentIndex}].houseBuilingArea" class="input_text178 number houseAreaCheck"/>
                </td>
            </tr> 
			 <tr>
                <td><label class="lab">房屋共有情况：</label>
                 <select class="select180" name="customerLoanHouseList[${parentIndex}].dictHouseCommon">
                   <option value="">请选择</option>
					<c:choose>
					  <c:when test="${dictMarry=='0'}">
						<c:forEach items="${fns:getDictList('jk_house_common_type')}" var="item">
						  <c:if test="${item.label!='夫妻共有'}">
							<option value="${item.value}"
							  <c:if test="${curLoanHouse.dictHouseCommon==item.value}">
					           selected='true' 
					          </c:if>>${item.label}
					        </option>
					     </c:if>
					   </c:forEach>
					</c:when>
					<c:otherwise>
					  <c:forEach items="${fns:getDictList('jk_house_common_type')}" var="item">
						<option value="${item.value}"
						<c:if test="${curLoanHouse.dictHouseCommon==item.value}">
					        selected='true' 
					    </c:if>>${item.label}
					    </option>
					  </c:forEach>
				    </c:otherwise>
					</c:choose>
				  </select>
                </td>
                <td>
                  <label class="lab">规划用途/设计用途：</label>
                   <select id="cardType" name="customerLoanHouseList[${parentIndex}].dictHouseType"
					 class="select180">
					 <option value="">请选择</option>
					 <c:forEach items="${fns:getDictList('jk_design_use')}" var="item">
						 <option value="${item.value}"
							 <c:if test="${curLoanHouse.dictHouseType==item.value}">
					            selected='true' 
					          </c:if>>${item.label}
					      </option>
					 </c:forEach>
					 </select>
                </td>
                <td>
                  <label class="lab">是否抵押：</label>
                  <c:forEach items="${fns:getDictList('jk_pledge_flag')}" var="item">
					<input type="radio" class="ml10 mr6" name="customerLoanHouseList[${parentIndex}].housePledgeFlag" value="${item.value}">
					 ${item.label}
					</input>
				  </c:forEach>
               </td>
            </tr> 
            <tr><td colspan="2"><label class="lab"><span class="red">*</span>房产具体信息：</label>
                    <select class="select78  houseProvince required" id="houseProvince_${parentIndex}" name="customerLoanHouseList[${parentIndex}].houseProvince">
                        <option value="">请选择</option>
                         <c:forEach var="item" items="${provinceList}" varStatus="status">
		                 <option value="${item.areaCode}">${item.areaName}</option>
	                  </c:forEach>
                    </select>
                    <select class="select78 houseCity required" id="houseCity_${parentIndex}" name="customerLoanHouseList[${parentIndex}].houseCity">
                        <option value="">请选择</option>
                    </select>
                    <select class="select78 houseArea required" id="houseArea_${parentIndex}" name="customerLoanHouseList[${parentIndex}].houseArea">
                        <option value="">请选择</option>
                    </select>
                    <input type="hidden" class="houseListId" name="customerLoanHouseList[${parentIndex}].id"/>
                    <input name="customerLoanHouseList[${parentIndex}].houseAddress" type="text" class="input_text178 required" style="width:250px" />
                </td>
                <td><label class="lab">建筑年份：</label>
                 <input id="d4311" name="customerLoanHouseList[${parentIndex}].houseCreateDay" type="text" class="input_text178 Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
              
            </tr>
           </tbody>
        </table>