<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/launch/house.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				$('#houseNextBtn').bind('click', function() {
					house.format();
					$(this).attr('disabled',true);
					launch.saveApplyInfo('7', 'houseForm','houseNextBtn');
				});
				$('#addHouseBtn').bind(
						'click',
						function() {
							var tabLength = parseInt($('#currIndex').val())+1;
							launch.additionItem('loanHouseArea',
									'_loanFlowHouseItem', tabLength, '',"dictMarry="+$('#dictMarry').val());
							$('#currIndex').val(tabLength);
						});
                $("input[name='delHouseItem']").each(function(){
                	$(this).bind('click',function(){
                		house.delItem($(this).attr("currId"),"loanHouseArea",$(this).attr("dbId"),'HOUSE');
                   	});
                	
                });
			loan.batchInitCity('houseProvince', 'houseCity','houseArea');
			areaselect.batchInitCity("houseProvince","houseCity","houseArea");
			});
	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
</script>
</head>
<body>
<ul class="nav nav-tabs">
	  <li><a href="javascript:launch.getCustomerInfo('1');">个人资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('2');">配偶资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('3');">申请信息</a></li>
	  
	    <c:if test="${workItem.bv.oneedition==-1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">自然人/保证人</a></li>	
        </c:if>
      
	  <li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li class="active"><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">
	   <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	   <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	   <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	  <input type="hidden" value="${dictMarry}" id="dictMarry"/>
	</form>
	<div id="div7" class="content control-group">
		<div class=" ml10">
			<input type="button" class="btn btn-small" value="增加"
				id="addHouseBtn"></input> 
		</div>
		<form id="houseForm" method="post">
		   <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
		   <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
		   <input type="hidden" value="${fn:length(applyInfoEx.customerLoanHouseList)}" name="currIndex" id="currIndex"/>
		    <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  		<input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  		<input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  		<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  		<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  		<input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  		<input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  		<input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
	 		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	 		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	 		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	 		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
			<div id="loanHouseArea">
			 <c:forEach items="${applyInfoEx.customerLoanHouseList}"
				var="curLoanHouse" varStatus="loanHouseStatus">
					<table class=" table1"
						id="loanHouseArea_${loanHouseStatus.index}" cellpadding="0"
						cellspacing="0" border="0" width="100%">
						<tbody>
						  <c:if test="${loanHouseStatus.index!=0}">
							<tr>
							  <td colspan="3">
							   <input type="button" name="delHouseItem" class="btn btn-small" value="删除" dbId="${curLoanHouse.id}" currId="loanHouseArea_${loanHouseStatus.index}"/></td>
							</tr>
							</c:if>
							<tr>
								<td><label class="lab">购买方式：</label> <select id="cardType"
									name="customerLoanHouseList[${loanHouseStatus.index}].houseBuyway" class="select180">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_house_buywayg')}"
											var="item">
											<option value="${item.value}"
												<c:if test="${curLoanHouse.houseBuyway == item.value}">
					      selected = true
					     </c:if>>${item.label}</option>
										</c:forEach>
								</select></td>
								<td><label class="lab">购买日期：</label> <input id="d4311"
									class="input_text178 Wdate" name="customerLoanHouseList[${loanHouseStatus.index}].houseBuyday"
									value="<fmt:formatDate value='${curLoanHouse.houseBuyday}' pattern="yyyy-MM-dd"/>"
									type="text" class="Wdate" size="10"
									onClick="WdatePicker({skin:'whyGreen'})"
									style="cursor: pointer" /></td>
								<td><label class="lab">房屋面积：</label> <input type="text"
									name="customerLoanHouseList[${loanHouseStatus.index}].houseBuilingArea"
									value="<fmt:formatNumber value='${curLoanHouse.houseBuilingArea}' pattern="0.00"/>"
									class="input_text178 number houseAreaCheck" /></td>
							</tr>
							<tr>
								<td><label class="lab">房屋共有情况：</label> <select
									class="select180" name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseCommon"
									value="${curLoanHouse.dictHouseCommon}">
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
									</select></td>
								<td><label class="lab">规划用途/设计用途：</label> <select
									id="cardType" name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseType"
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
								<td ><label class="lab">是否抵押：</label> <c:forEach
										items="${fns:getDictList('jk_pledge_flag')}" var="item">
										<input type="radio" class="ml10 mr6"
											name="customerLoanHouseList[${loanHouseStatus.index}].housePledgeFlag"
											value="${item.value}"
											<c:if test="${curLoanHouse.housePledgeFlag==item.value}"> checked='true' </c:if>> ${item.label}</input>
									</c:forEach></td>
						    	</tr>
						    	<tr>
								<td colspan="2"><label class="lab"><span class="red">*</span>房产具体信息：</label>
									<select class="select78 mr10 houseProvince required"
									id="houseProvince_${loanHouseStatus.index}"
									name="customerLoanHouseList[${loanHouseStatus.index}].houseProvince"
									value="${curLoanHouse.houseProvince}">
										<option value="">请选择</option>
										<c:forEach var="item" items="${provinceList}"
											varStatus="status">
											<option value="${item.areaCode}"
												<c:if test="${curLoanHouse.houseProvince==item.areaCode}"> 
		                  selected=true
		                </c:if>>${item.areaName}</option>
										</c:forEach>
								</select> <select class="select78 mr10 houseCity required"
									id="houseCity_${loanHouseStatus.index}"
									name="customerLoanHouseList[${loanHouseStatus.index}].houseCity"
									value="${curLoanHouse.houseCity}">
										<option value="">请选择</option>
								</select> <select class="select78 houseArea required"
									id="houseArea_${loanHouseStatus.index}"
									name="customerLoanHouseList[${loanHouseStatus.index}].houseArea"
									value="${curLoanHouse.houseArea}">
										<option value="">请选择</option>
								</select> 
								<input name="customerLoanHouseList[${loanHouseStatus.index}].houseAddress"
									type="text" value="${curLoanHouse.houseAddress}"
									class="input_text178 required" style="width:250px">
									<input type="hidden" id="houseCityHidden_${loanHouseStatus.index}" value="${curLoanHouse.houseCity}"/>
									<input type="hidden" id="houseAreaHidden_${loanHouseStatus.index}" value="${curLoanHouse.houseArea}"/>
									
									<input type="hidden" name="customerLoanHouseList[${loanHouseStatus.index}].id"
									class="houseListId" value="${curLoanHouse.id}" />
							    </td>
								<td><label class="lab">建筑年份：</label> <input id="d4311"
									class="input_text178 Wdate"
									name="customerLoanHouseList[${loanHouseStatus.index}].houseCreateDay"
									value="<fmt:formatDate value='${curLoanHouse.houseCreateDay}' pattern="yyyy-MM-dd"/>"
									type="text" class="Wdate" size="10"
									onClick="WdatePicker({skin:'whyGreen'})"
									style="cursor: pointer" /></td>
							</tr>
						</tbody>
					</table>
			</c:forEach>
		 </div>
		<!-- <input type="button" class="btn btn-small" onclick="house.format();" value="序列化"/> -->
		<div class="tright mr10 mb5" >
			<input type="submit" id="houseNextBtn" class="btn btn-primary"
				value="下一步" />
		</div>
		</form>
	</div>
</body>
</html>