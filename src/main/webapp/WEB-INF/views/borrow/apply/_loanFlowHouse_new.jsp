<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<jsp:include page="./applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/launch/houseNew.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#houseNextBtn').bind('click', function() {
			house.format();
			$(this).attr('disabled',true);
			launch.saveApplyInfo('6', 'houseForm','houseNextBtn');
		});
		$('#addHouseBtn').bind('click', function() {
			var tabLength = parseInt($('#currIndex').val())+1;
			launch.additionItem('loanHouseArea','_loanFlowHouseItem_new', tabLength, '','');
			$('#currIndex').val(tabLength);
			$('#houseCount').val(tabLength);
		});
		//初始化房产信息
		house.init();
	});
	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
	  	<li><a href="javascript:launch.getCustomerInfo('1');">个人基本信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('2');">借款意愿</a></li>
		<li><a href="javascript:launch.getCustomerInfo('3');">工作信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('4');">联系人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('5');">自然人保证人信息</a></li>
		<li class="active"><a href="javascript:launch.getCustomerInfo('6');">房产信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('8');">证件信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo('9');">银行卡信息</a></li>
	</ul>
	<form id="custInfoForm" method="post">
		<input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId" />
		<input type="hidden" value="${workItem.bv.defToken}" name="defToken" />
		<input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanInfo.loanCode" id="loanCode" />
		<input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanCode" />
		<input type="hidden" value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id" id="custId" />
		<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" />
		<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode" />
		<input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}" name="loanInfo.loanCustomerName" id="loanCustomerName" />
		<input type="hidden" value="${workItem.bv.loanCustomer.customerName}" name="loanCustomer.customerName" />
		<input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId" />
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${dictMarry}" id="dictMarry" />
	</form>
	<c:choose>
		<c:when test="${applyInfoEx.customerLoanHouseList.get(0).id eq null }">
			<div class="mt10 mb10 f14 ml10">资料数量&nbsp;&nbsp;共提供&nbsp;&nbsp;<input type="text" id="houseCount" name="houseCount" value="${fn:length(applyInfoEx.customerLoanHouseList)-1}" class="border:none" disabled="disabled"/>套房产</div>
		</c:when>
		<c:otherwise>
			<div class="mt10 mb10 f14 ml10">资料数量&nbsp;&nbsp;共提供&nbsp;&nbsp;<input type="text" id="houseCount" name="houseCount" value="${fn:length(applyInfoEx.customerLoanHouseList)}" class="border:none" disabled="disabled"/>套房产</div>
		</c:otherwise>
	</c:choose>
	<div id="div7" class="content control-group">
		<div class=" ml10">
			<input type="button" class="btn btn-small" value="添加房产" id="addHouseBtn"></input>
		</div>
		<form id="houseForm" method="post">
			<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
			<input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId" />
			<input type="hidden" value="${workItem.bv.defToken}" name="defToken" />
			<input type="hidden" value="${fn:length(applyInfoEx.customerLoanHouseList)}" name="currIndex" id="currIndex" />
			<input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanInfo.loanCode" id="loanCode" />
			<input type="hidden" value="${workItem.bv.loanInfo.loanCode}" name="loanCode" />
			<input type="hidden" value="${workItem.bv.loanCustomer.id}" name="loanCustomer.id" id="custId" />
			<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="loanCustomer.customerCode" id="customerCode" />
			<input type="hidden" value="${workItem.bv.loanCustomer.customerCode}" name="customerCode" />
			<input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}" name="loanInfo.loanCustomerName" id="loanCustomerName" />
			<input type="hidden" value="${workItem.bv.loanCustomer.customerName}" name="loanCustomer.customerName" />
			<input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId" />
			<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
			<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
			<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
			<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
			<div id="loanHouseArea">
				<c:forEach items="${applyInfoEx.customerLoanHouseList}" var="curLoanHouse" varStatus="loanHouseStatus">
					<table class=" table1" id="loanHouseArea_${loanHouseStatus.index}" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tbody>
							<c:if test="${loanHouseStatus.index!=0}">
								<tr>
									<td colspan="3">
										<input type="button" name="delHouseItem" class="btn btn-small" value="删除" dbId="${curLoanHouse.id}" currId="loanHouseArea_${loanHouseStatus.index}" />
									</td>
								</tr>
							</c:if>
							<tr>
								<td colspan="3">
									<label class="lab">
										<span class="red">*</span>
										房产地址：
									</label>
									<select class="select78 mr10 houseProvince required" id="houseProvince_${loanHouseStatus.index}" name="customerLoanHouseList[${loanHouseStatus.index}].houseProvince" value="${curLoanHouse.houseProvince}">
										<option value="">请选择</option>
										<c:forEach var="item" items="${provinceList}" varStatus="status">
											<option value="${item.areaCode}" <c:if test="${curLoanHouse.houseProvince==item.areaCode}"> selected=true </c:if>>
												${item.areaName}
											</option>
										</c:forEach>
									</select>
									-
									<select class="select78 mr10 houseCity required" id="houseCity_${loanHouseStatus.index}" name="customerLoanHouseList[${loanHouseStatus.index}].houseCity" value="${curLoanHouse.houseCity}">
										<option value="">请选择</option>
									</select>
									-
									<select class="select78 houseArea required" id="houseArea_${loanHouseStatus.index}" name="customerLoanHouseList[${loanHouseStatus.index}].houseArea" value="${curLoanHouse.houseArea}">
										<option value="">请选择</option>
									</select>
									<input name="customerLoanHouseList[${loanHouseStatus.index}].houseAddress" type="text" value="${curLoanHouse.houseAddress}" class="input_text178 required" style="width: 250px" maxlength="100">
									<input type="hidden" id="houseCityHidden_${loanHouseStatus.index}" value="${curLoanHouse.houseCity}" />
									<input type="hidden" id="houseAreaHidden_${loanHouseStatus.index}" value="${curLoanHouse.houseArea}" />
									<input type="hidden" name="customerLoanHouseList[${loanHouseStatus.index}].id" class="houseListId" value="${curLoanHouse.id}" />
								</td>
							</tr>
							<tr>
								<td>
									<label class="lab"><span class="red">*</span>房产类型：</label>
									<select name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseType" class="select180 required">
										<option value="">请选择</option>
										<c:forEach items="${fns:getNewDictList('jk_design_use')}" var="item">
											<option value="${item.value}" <c:if test="${curLoanHouse.dictHouseType==item.value}"> selected='true' </c:if>>${item.label}</option>
										</c:forEach>
									</select>
									<input type="text" value="${curLoanHouse.dictHouseTypeRemark}" name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseTypeRemark" class="input_text178 chineseCheck2 <c:if test="${empty curLoanHouse.dictHouseTypeRemark}"> collapse </c:if>" maxlength='50' />
								</td>
								<td>
									<label class="lab"><span class="red">*</span>购买方式：</label>
									<select name="customerLoanHouseList[${loanHouseStatus.index}].houseBuyway" class="select180 required">
										<option value="">请选择</option>
										<c:forEach items="${fns:getNewDictList('jk_house_buywayg')}" var="item">
											<option value="${item.value}" <c:if test="${curLoanHouse.houseBuyway == item.value}"> selected = true </c:if>>${item.label}</option>
										</c:forEach>
									</select>
								</td>
								<td>
									<label class="lab">贷款总额(万元)：</label>
									<input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseLoanAmount" value="<fmt:formatNumber value='${curLoanHouse.houseLoanAmount}' pattern="0.00"/>" class="input_text178 numCheck" min="0" max="999999999.00"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="lab">贷款余额(元)：</label>
									<input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseLessAmount" value="<fmt:formatNumber value='${curLoanHouse.houseLessAmount}' pattern="0.00"/>" class="input_text178 numCheck" min="0" max="999999999.00"/>
								</td>
								<td>
									<label class="lab">月还款(元)：</label>
									<input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseMonthRepayAmount" value="<fmt:formatNumber value='${curLoanHouse.houseMonthRepayAmount}' pattern="0.00"/>" class="input_text178 numCheck" min="0" max="999999999.00"/>
								</td>
								<td>
									<label class="lab">贷款年限(年)：</label>
									<input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseLoanYear" value='<fmt:formatNumber value='${curLoanHouse.houseLoanYear}' pattern="0.0"/>' class="input_text178 numCheck2 max50"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="lab"><span class="red">*</span>建筑面积(平方米)：</label>
									<input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseBuilingArea" value="<fmt:formatNumber value='${curLoanHouse.houseBuilingArea}' pattern="0.00"/>" class="input_text178 numCheck required" min="0" max="999999999.00"/>
								</td>
								<td>
									<label class="lab"><span class="red">*</span>购买时间：</label>
									<input class="input_text178 Wdate required" name="customerLoanHouseList[${loanHouseStatus.index}].houseBuyday" value="<fmt:formatDate value='${curLoanHouse.houseBuyday}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
								</td>
								<td>
									<label class="lab">购买价格(万元)：</label>
									<input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseAmount" value="<fmt:formatNumber value='${curLoanHouse.houseAmount}' pattern="0.00"/>" class="input_text178 numCheck" min="0" max="999999999.00"/>
								</td>
							</tr>
							<tr>
								<td>
									<label class="lab"><span class="red">*</span>房屋所有权：</label>
									<select class="select180 required" name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseCommon" value="${curLoanHouse.dictHouseCommon}">
										<option value="">请选择</option>
										<c:choose>
											<c:when test="${dictMarry=='0'}">
												<c:forEach items="${fns:getNewDictList('jk_house_common_type')}" var="item">
													<c:if test="${item.label!='夫妻共有'}">
														<option value="${item.value}" <c:if test="${curLoanHouse.dictHouseCommon==item.value}">selected='true'</c:if>>
															${item.label}
														</option>
													</c:if>
												</c:forEach>
											</c:when>
											<c:otherwise>
												<c:forEach items="${fns:getNewDictList('jk_house_common_type')}" var="item">
													<option value="${item.value}" <c:if test="${curLoanHouse.dictHouseCommon==item.value}">
					                                 	selected='true' 
					                              	 </c:if>>${item.label}</option>
												</c:forEach>
											</c:otherwise>
										</c:choose>
									</select>
								</td>
							</tr>
						</tbody>
					</table>
				</c:forEach>
			</div>
			<!-- <input type="button" class="btn btn-small" onclick="house.format();" value="序列化"/> -->
			<div class="tright mr10 mb5">
				<input type="submit" id="houseNextBtn" class="btn btn-primary" value="下一步" />
			</div>
		</form>
	</div>
</body>
</html>