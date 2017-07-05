<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript"
	src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript"
	src="${context}/js/car/apply/carConsultForm.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript"
	src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						
						loan.initCity("liveProvinceId", "liveCityId",
								"liveDistrictId");
						loan.initCity("registerProvince",
								"customerRegisterCity", "customerRegisterArea");
						loan.initCity("registerProvinceNew",
								"customerRegisterCityNew",
								"customerRegisterAreaNew");
						loan.initCity("liveProvinceIdNew", "liveCityIdNew",
								"liveDistrictIdNew");

						// 客户放弃		
						$("#giveUpBtn")
								.bind(
										'click',
										function() {

											var url = ctx
													+ "/car/carApplyTask/giveUp?loanCode="
													+ $("#ipt_loanCode").val();

											art.dialog.confirm("是否客户放弃?",
													function() {
														$("#frameFlowForm")
																.attr('action',
																		url);
														$("#frameFlowForm")
																.submit();
													});
										});
						$("input[mark='dictSex']").click(function() {
							$("span[title='sex']").hide();
						});
						jQuery.validator.addMethod("houseRentCheck", function(value, element) {
							var length = value.length;
						    var fremeNum = /^[0-9]+([.]{1}[0-9]+){0,1}$/;
						    return this.optional(element) || (length == 9 && fremeNum.test(value));
						}, "只能输入9位的数字！");
						jQuery.validator.addMethod("realName2", function(value, element) {
						    return this.optional(element) || /^[\u4e00-\u9fa5\·]{2,30}$/.test(value);
						}, "姓名只能为2-10个汉字或·号");
					});
</script>
<style>
.coborrowerTable tr td, th {
	border-color: #c3c3c3;
}

.coborrowerTable {
	border-color: #c3c3c3
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${loanCode}');">个人资料</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${loanCode}');">车辆信息</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${loanCode}');">借款信息</a></li>
		<li class="active"><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${loanCode}');">共同借款人</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${loanCode}');">工作信息</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${loanCode}');">联系人资料</a></li>
		<li><a
			href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${loanCode}');">客户开户及管辖信息</a></li>
		<input type="button" style="float: right"
			onclick="showCarLoanHis('${loanCode}')" class="btn btn-small"
			value="历史">
		<input type="button" style="float: right" id="giveUpBtn"
			class="btn btn-small" value="客户放弃"></input>
		</div>
	</ul>
	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
	<form class="hide" id="workItemForm">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input> <input
			type="hidden" value="${workItem.flowId}" name="flowId"></input> <input
			type="hidden" value="${workItem.wobNum}" name="wobNum"></input>


	</form>
	<input type="hidden" value="${loanCode}" id="ipt_loanCode"
		name="loanCode"></input>
	<input type="hidden" value="${fn:length(carLoanCoborrowers) }" id="coboLength"></input>
	<form id="carCoborrow">
		<div style="text-align: right;">
			<input type="button" id="addCoborrowBtn" class="btn btn-small"
				value="增加共借人" />
		</div>
		<div id="coborroArea">
			<c:choose>
				<c:when
					test="${carLoanCoborrowers != null && fn:length(carLoanCoborrowers) > 0}">
					<c:forEach items="${carLoanCoborrowers}" var="cobs"
						varStatus="status">
						<div class="contactPanel">
							<table class="table1 coborrowerTable" cellpadding="0"
								cellspacing="0" id="t1" border="0" width="100%">
								<tr>
									<td width="31%"><label class="lab"><span
											class="red">*</span>共借人姓名：</label> <input type="text" mark="coboName"
										name="coboName${status.index}" value="${cobs.coboName}"
										maxlength="10" class="input_text178 required realName2" />
										<input type="hidden" mark="id" name="id${status.index}" value="${cobs.id}">
									</td>
									<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
									<input mark="dictCertType" value="0" type="hidden"/>
				<%-- 						<select mark="dictCertType" name="dictCertType${status.index}"
										 class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('com_certificate_type')}"
												var="item">
												<option value="${item.value}"
													<c:if test="${cobs.dictCertType==item.value}">
										   selected
										  </c:if>>${item.label}</option>
											</c:forEach>
									</select> --%>
									身份证
									</td>
									<td><label class="lab"><span class="red">*</span>证件号码：</label>
										<input mark="certNum"  name="certNum${status.index}" value="${cobs.certNum}"
										type="text" class="input_text178 required card" /></td>
								</tr>
								<tr>
									<td width="31%"><label class="lab"><span
											class="red">*</span>性别：</label> <span> <c:forEach
												items="${fns:getDictList('jk_sex')}" var="item">
												<input type="radio" mark="dictSex"
													name="dictSex${status.index}" id="sex_one"
													<c:if test="${cobs.dictSex==item.value}"> 
						                 	checked="true"
						               </c:if>
													value="${item.value}">${item.label}</input>
											</c:forEach>
									</span><span id="sexSpan" style="display: none" mark="sexone"
										title="sex">&nbsp;&nbsp;&nbsp;<font color="red">请选择性别
												！</font></span></td>
									<td><label class="lab"><span class="red">*</span>手机号码：</label>
										<input name="mobile${status.index}" value="${cobs.mobile}"
										mark="mobile" type="text"
										class="input_text178 required isMobile" /></td>
									<td><label class="lab"><span class="red">*</span>婚姻状况：</label>
										<select id="dictMarryStatus" name="dictMarryStatus${status.index}"
										mark="dictMarryStatus" class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('jk_marriage')}"
												var="item">
												<option value="${item.value}"
													<c:if test="${cobs.dictMarryStatus==item.value}"> 
											  selected 
											 </c:if>>${item.label}</option>
											</c:forEach>
									</select></td>
								</tr>
								<tr>
									<td><label class="lab">有无子女：</label> <c:forEach
											items="${fns:getDictList('jk_have_or_nohave')}" var="item">
											<input type="radio" name="haveChildFlag${status.index }"
												mark="haveChildFlag" title="haveNo_one"
												<c:if test="${cobs.haveChildFlag==item.value}"> 
					                 				 checked
					               				</c:if>
												value="${item.value}">${item.label}</input>
										</c:forEach></td>
									<td><label class="lab">固定电话：</label> <input
										name="familyTel${status.index}" value="${cobs.familyTel}"
										mark="familyTel" type="text" class="input_text178 isTel" /></td>
									<td><label class="lab"><span class="red">*</span>邮箱：</label>
										<input value="${cobs.email}" mark="email"
										name="email${status.index}" type="text"
										class="input_text178 required isEmail" /></td>
								</tr>
								<tr>
									<td><label class="lab">其他收入：</label> 
											<c:forEach items="${fns:getDictList('jk_have_or_nohave')}"
													var="item">
													<input type="radio" name="haveOtherIncome${status.index}" mark="haveOtherIncome" 
														value="${item.value}"
														<c:if test="${item.value == cobs.haveOtherIncome}">
                    								 checked='true'
                 								       </c:if> />${item.label}
               								 </c:forEach>
										<span style="display:none;"><label>其他所得：
										<input name="otherIncome${status.index}"
										value="<fmt:formatNumber value="${cobs.otherIncome}" pattern="##0.00"/>" maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月
										</span></td>
										
										
<%-- 									<td><label class="lab">房屋出租：</label> <input maxlength="11"
										name="houseRent${status.index}"
										value="<fmt:formatNumber value="${cobs.houseRent}" pattern="##0.00"/>"
										mark="houseRent" type="text" class="input_text178 number houseRentCheck"  maxlength="13"/>元/每月</td> --%>
										
                        <td><label class="lab"><span style="color: red">*</span>住房性质：</label>
                        <select name="coboHouseHoldProperty${status.index}" id="customerHouseHoldProperty"
                            value="${cobs.coboHouseHoldProperty}" mark="coboHouseHoldProperty"
                            class="select180 required">
                                <option value="">请选择</option>
                                <c:forEach items="${fns:getDictList('jk_house_nature')}"
                                    var="item">
                                    <option value="${item.value}"
                                        <c:if test="${item.value==cobs.coboHouseHoldProperty}">
                              selected=true 
                            </c:if>>${item.label}</option>
                                </c:forEach>
                        </select>
                        <span style="display:none;"><label>月租金：</label>
                        <input type="text" name="houseRent${status.index}" mark="jydzzmRentMonth"
                        value="<fmt:formatNumber value='${cobs.houseRent}' pattern="0.00"/>"
                        class="input_text178 required number" maxlength="9"/>
                        </span>
                        </td>
								</tr>
								<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>现住址：</label> <select id="liveProvinceId"
										name="dictLiveProvince" mark="dictLiveProvince"
										class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveProvince==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="liveCityId" name="dictLiveCity"
										mark="dictLiveCity" class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${cityList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveCity==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="liveDistrictId" name="dictLiveArea"
										mark="dictLiveArea" class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${districtList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveArea==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select> <input type="text" name="nowAddress" mark="nowAddress"
										class="input_text178 required detailAddress"
										style="width: 250px" value="${cobs.nowAddress}" maxlength="50" /></td>
								</tr>
								<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>户籍地址：</label> <select id="registerProvince"
										name="dictHouseholdProvince" mark="dictHouseholdProvince"
										class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictHouseholdProvince==item.areaCode}">
											selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>- <select id="customerRegisterCity" name="dictHouseholdCity"
										mark="dictHouseholdCity" class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${cityList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictHouseholdCity==item.areaCode}">
											selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>- <select id="customerRegisterArea" mark="dictHouseholdArea"
										name="dictHouseholdArea" class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${districtList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictHouseholdArea==item.areaCode}">
											selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select> <input type="text" name="householdAddress" maxlength="50"
										mark="householdAddress"
										class="input_text178 required detailAddress"
										style="width: 250px" value="${cobs.householdAddress}" /></td>
								</tr>
								<tr>
									<td><label class="lab"><span style="color: red">*</span>单位名称：</label>
										<input type="text" mark="companyName"
										name="companyName${status.index}" value="${cobs.companyName}"
										maxlength="50" class="input_text178 required" /></td>
									<td><label class="lab"><span style="color: red">*</span>单位地址：</label>
										<input type="text" mark="companyAddress"
										name="companyAddress${status.index}"
										value="${cobs.companyAddress}" maxlength="100"
										class="input_text178 required " /></td>
									<td><label class="lab"><span style="color: red">*</span>职务
											：</label> <input type="text" mark="companyPosition"
										name="companyPosition${status.index}"
										value="${cobs.companyPosition}" maxlength="50"
										class="input_text178 required " /></td>
								</tr>
								<input type="hidden" value="${cobs.loanCode}" mark="loanCode"
									name="loanCode"></input>

							</table>
							<div class="box1 mb5">
								<input type="button" class="btn btn-small" value="增加联系人"
									name="addCobContactBtn" onclick="addContractPersonClick(this)" />
							</div>
							<table id="contractTable"
								<%-- <c:if test="${fn:length(cobs.carCustomerContactPerson)==0}"> style="display:none;" </c:if> --%>
								border="1" cellspacing="0" cellpadding="0" border="0"
								class="coborrowerTable" width="100%">
								<thead>
									<th><span style="color: red">*</span>姓名</th>
									<th>和本人关系</th>
									<th>工作单位</th>
									<th>家庭或工作单位详细地址</th>
									<th>单位电话</th>
									<th><span style="color: red">*</span>手机号</th>
									<th>操作</th>
								</thead>
								<tbody>
									<c:choose>
										<c:when
											test="${cobs.carCustomerContactPerson!= null && fn:length(cobs.carCustomerContactPerson) > 0}">
											<c:forEach items="${cobs.carCustomerContactPerson}"
												var="cper" varStatus="status">
												<tr>
													<td><input type="hidden" mark="loanCustomterType"
														value="1" name="loanCustomterType"> <input
														type="text" mark="contactName"
														name="contactName${status.index}"
														value="${cper.contactName}" maxlength="10"
														class="input_text70 required realName2" /></td>
													<td><select mark="dictContactRelation"
														name="dictContactRelation" class="select180">
															<option value="">请选择</option>
															<c:forEach items="${fns:getDictList('jk_relation_type')}"
																var="item">
																<option value="${item.value}"
																	<c:if test="${cper.dictContactRelation==item.value}">
					     			 selected
					     			</c:if>>${item.label}</option>
															</c:forEach>
													</select></td>
													<td><input type="text" mark="contactUint"
														maxlength="50" name="contactUint"
														value="${cper.contactUint}" class="input_text178" /></td>
													<td><input type="text" mark="dictContactNowAddress"
														name="dictContactNowAddress" maxlength="50"
														value="${cper.dictContactNowAddress}"
														class="input_text150" style="width: 250px"></td>
													<td><input type="text" mark="compTel"
														name="compTel${status.index}" value="${cper.compTel}"
														class="input_text178 isTel" /></td>
													<td><input type="text" mark="contactUnitTel"
														value="${cper.contactUnitTel}"
														name="contactUnitTel${status.index}"
														class="input_text178 required isMobile" /></td>
													<td class="tcenter"><input type="button"
														onclick="delCoboContact(this);" class="btn_edit" value="删除"></input></td>
												</tr>
											</c:forEach>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
								</tbody>
							</table>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="contactPanel">
						<table class="table1 coborrowerTable" cellpadding="0"
							cellspacing="0" id="t2" border="0" width="100%">
							<tr>
								<td width="31%"><label class="lab"><span
										class="red">*</span>共借人姓名：</label> <input type="text" mark="coboName"
									maxlength="10" name="coboName1"
									class="input_text178 required realName2" /> <input
									type="hidden" mark="id" name="id" id="t2Id"></td>
								<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
									<input mark="dictCertType" value="0" type="hidden"/>
<%-- 									<select mark="dictCertType" name="dictCertType"
									 class="select180 required">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('com_certificate_type')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select> --%>
								身份证
								</td>
								<td><label class="lab"><span class="red">*</span>证件号码：</label>
									<input mark="certNum" name="certNum" type="text"
									class="input_text178 required card isCertificateCobo" /></td>
							</tr>
							<tr>
								<td width="31%"><label class="lab"><span
										class="red">*</span>性别：</label><span><c:forEach
											items="${fns:getDictList('jk_sex')}" var="item">
											<input type="radio" mark="dictSex" name="dictSexe"
												id="sex_two" value="${item.value}">${item.label}</input>
										</c:forEach> </span> <span id="sex_twoSpan" style="display: none" mark="sextwo"
									title="sex">&nbsp;&nbsp;&nbsp;<font color="red">请选择性别
											！</font></span></td>
								<td><label class="lab"><span class="red">*</span>手机号码：</label>
									<input name="mobile2" mark="mobile" type="text"
									class="input_text178 required isMobile" /></td>
								<td><label class="lab"><span class="red">*</span>婚姻状况：</label>
									<select id="dictMarryStatus" name="dictMarryStatus"
									mark="dictMarryStatus" class="select180 required">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_marriage')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select></td>
							</tr>

							<tr>
								<td><label class="lab">有无子女：</label> <c:forEach
										items="${fns:getDictList('jk_have_or_nohave')}" var="item">
										<input type="radio" name="haveChildFlag" mark="haveChildFlag"
											title="haveNo_two" value="${item.value}">${item.label}</input>
									</c:forEach></td>
								<td><label class="lab">固定电话：</label> <input
									name="familyTel1" mark="familyTel" type="text"
									class="input_text178 isTel" /></td>
								<td><label class="lab"><span class="red">*</span>邮箱：</label>
									<input name="email2" mark="email" type="text"
									class="input_text178 required isEmail" /></td>

							</tr>
							<tr>
								<td><label class="lab">其他收入：</label> 
												<c:forEach items="${fns:getDictList('jk_have_or_nohave')}"
													var="item">
													<input type="radio" name="haveOtherIncome" mark="haveOtherIncome"  
														value="${item.value}"
														<c:if test="${item.value=='0'}">
                    								 checked
                 								       </c:if> />${item.label}
               								 </c:forEach>
										<span style="display:none;"><label>其他所得：
										<input name="otherIncome"
										 maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月
										</span></td>
										
<!-- 								<td><label class="lab">房屋出租：</label> <input
									name="houseRent1" mark="houseRent" type="text" maxlength="11"
									class="input_text178 number" />元/每月</td> -->
						<td><label class="lab"><span style="color: red">*</span>住房性质：</label>
                        <select name="coboHouseHoldProperty" id="customerHouseHoldProperty"
                            value="${cobs.coboHouseHoldProperty}" mark="coboHouseHoldProperty"
                            class="select180 required">
                                <option value="">请选择</option>
                                <c:forEach items="${fns:getDictList('jk_house_nature')}"
                                    var="item">
                                    <option value="${item.value}"
                                        <c:if test="${item.value==cobs.coboHouseHoldProperty}">
                              selected=true 
                            </c:if>>${item.label}</option>
                                </c:forEach>
                        </select>
                        <span style="display:none;"><label>月租金：</label>
                        <input type="text" name="houseRent" mark="jydzzmRentMonth"
                        class="input_text178 required number" maxlength="9"/>
                        </span>
                        </td>
							</tr>
							<tr>
								<td colspan="3"><label class="lab"><span
										class="red">*</span>现住址：</label> <select id="liveProvinceId"
									name="dictLiveProvince" mark="dictLiveProvince"
									class="select78 required">
										<option value="">请选择</option>
										<c:forEach var="item" items="${provinceList}">
											<option value="${item.areaCode}">${item.areaName}</option>
										</c:forEach>
								</select>-<select id="liveCityId" name="dictLiveCity" mark="dictLiveCity"
									class="select78 required">
										<option value="">请选择</option>
								</select>-<select id="liveDistrictId" name="dictLiveArea"
									mark="dictLiveArea" class="select78 required">
										<option value="">请选择</option>
								</select> <input type="text" name="nowAddress" mark="nowAddress"
									class="input_text178 required detailAddress"
									style="width: 250px" maxlength="50" /></td>
							</tr>
							<tr>
								<td colspan="3"><label class="lab"><span
										class="red">*</span>户籍地址：</label> <select id="registerProvince"
									name="dictHouseholdProvince" mark="dictHouseholdProvince"
									class="select78 required">
										<option value="">请选择</option>
										<c:forEach var="item" items="${provinceList}">
											<option value="${item.areaCode}">${item.areaName}</option>
										</c:forEach>
								</select>-<select id="customerRegisterCity" name="dictHouseholdCity"
									mark="dictHouseholdCity" class="select78 required">
										<option value="">请选择</option>
								</select>-<select id="customerRegisterArea" mark="dictHouseholdArea"
									name="dictHouseholdArea" class="select78 required">
										<option value="">请选择</option>
								</select> <input type="text" name="householdAddress" maxlength="50"
									mark="householdAddress"
									class="input_text178 required detailAddress"
									style="width: 250px" /></td>
							</tr>
							<tr>
								<td><label class="lab"><span style="color: red">*</span>单位名称：</label>
									<input type="text" mark="companyName" name="companyNameo"
									maxlength="50" class="input_text178 required " /></td>
								<td><label class="lab"><span style="color: red">*</span>单位地址：</label>
									<input type="text" mark="companyAddress" name="companyAddresso"
									maxlength="100" class="input_text178 required " /></td>
								<td><label class="lab"><span style="color: red">*</span>职务
										：</label> <input type="text" mark="companyPosition"
									name="companyPositiono" maxlength="50"
									class="input_text178 required " /></td>
							</tr>
							<input type="hidden" mark="loanCode" value="${loanCode}"
								name="loanCode"></input>

						</table>
						<div class="box1 mb5">
							<input type="button" class="btn btn-small" value="增加联系人"
								name="addCobContactBtn" onclick="addContractPersonClick(this)" />
						</div>
						<table border="1" cellspacing="0" cellpadding="0" border="0"
							class="coborrowerTable" width="100%">
							<thead>
								<th><span style="color: red">*</span>姓名</th>
								<th>和本人关系</th>
								<th>工作单位</th>
								<th>家庭或工作单位详细地址</th>
								<th>单位电话</th>
								<th><span style="color: red">*</span>手机号</th>
								<th>操作</th>
							</thead>
							<tbody>
							</tbody>
						</table>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
		<div class="tright mr10 mb5">
			<input type="button" id="coborrowNextBtn" class="btn btn-primary"
				value="下一步">
		</div>
	</form>
	<!-- 新添加的共借人 -->
	<div class="hide" id="myCoborroCopy">
		<div class="contactPanel">
			<input type="button" onclick="delCoborrower(this);" class="btn_edit"
				value="删除">
			<table cellpadding="0" cellspacing="0" border="0" width="100%"
				id="t3" class="coborrowerTable">
				<tr>
					<td width="31%"><label class="lab"><span class="red">*</span>共借人姓名：</label>
						<input type="text" mark="coboName" name="coboNameo" maxlength="10"
						class="input_text178 required realName2" /> <input
						type="hidden" mark="id" name="id" id="t3Id"></td>
					<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
									<input mark="dictCertType" value="0" type="hidden"/>
<%--  						<select  name="dictCertType" mark="dictCertType" 
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('com_certificate_type')}"
								var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select>  --%>
					身份证
					</td>
					<td><label class="lab"><span class="red">*</span>证件号码：</label>
						<input name="certNumo" mark="certNum" type="text"
						class="input_text178 required card" /></td>
				</tr>
				<tr>
					<td width="31%"><label class="lab"><span class="red">*</span>性别：</label>
						<span> <c:forEach items="${fns:getDictList('jk_sex')}"
								var="item">
								<input type="radio" name="dictSexo" mark="dictSex"
									id="sex_three" value="${item.value}">
									${item.label}</input>
							</c:forEach>
					</span> <span id="sex_threeSpan" style="display: none" mark="sexthree"
						title="sex">&nbsp;&nbsp;&nbsp;<font color="red">请选择性别
								！ </font></span></td>
					<td><label class="lab"><span class="red">*</span>手机号码：</label>
						<input name="mobileo" mark="mobile" type="text"
						class="input_text178 required isMobile" /></td>
					<td><label class="lab"><span class="red">*</span>婚姻状况：</label>
						<select id="dictMarryStatus" name="dictMarryStatus"
						mark="dictMarryStatus" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select></td>
				</tr>

				<tr>
					<td><label class="lab">有无子女：</label> <c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="haveChildFlago" mark="haveChildFlag"
								title="haveNo_three" value="${item.value}">${item.label}</input>
						</c:forEach></td>
					<td><label class="lab">固定电话：</label> <input name="familyTelo"
						mark="familyTel" type="text" class="input_text178 isTel" /></td>
					<td><label class="lab"><span class="red">*</span>邮箱：</label> <input
						mark="email" name="emailo" type="text"
						class="input_text178 required isEmail" /></td>

				</tr>
				<tr>
					<td><label class="lab">其他收入：</label> 
												<c:forEach items="${fns:getDictList('jk_have_or_nohave')}"
													var="item">
													<input type="radio" name="haveOtherIncomeo" mark="haveOtherIncome" 
														value="${item.value}"
														<c:if test="${item.value=='0'}">
                    								 checked
                 								       </c:if> />${item.label}
               								 </c:forEach>
										<span style="display:none;"><label>其他所得：</label>
										<input name="otherIncome"
										maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月
										</span></td>
<!-- 					<td><label class="lab">房屋出租：</label> <input name="houseRento"
						maxlength="11" mark="houseRent" type="text"
						class="input_text178 number" />元/每月</td> -->
						<td><label class="lab"><span style="color: red">*</span>住房性质：</label>
                        <select name="coboHouseHoldPropertyo" mark="coboHouseHoldProperty"
                            class="select180 required">
                                <option value="">请选择</option>
                                <c:forEach items="${fns:getDictList('jk_house_nature')}"
                                    var="item">
                                    <option value="${item.value}">${item.label}</option>
                                </c:forEach>
                        </select>    
                        <span style="display:none;"><label>月租金：</label>
                        <input type="text" name="houseRentp" mark="jydzzmRentMonth"
                        class="input_text178 number" maxlength="9"/>
                        </span>
                        </td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span class="red">*</span>现住址：</label>
						<select id="liveProvinceIdNew" name="dictLiveProvince"
						mark="dictLiveProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}">
								<option value="${item.areaCode}">${item.areaName}</option>
							</c:forEach>
					</select>-<select id="liveCityIdNew" name="dictLiveCity" mark="dictLiveCity"
						class="select78 required">
							<option value="">请选择</option>
					</select>-<select id="liveDistrictIdNew" mark="dictLiveArea"
						name="dictLiveArea" class="select78 required">
							<option value="">请选择</option>
					</select> <input type="text" mark="nowAddress" name="nowAddress"
						maxlength="50" class="input_text178 required detailAddress"
						style="width: 250px" /></td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span class="red">*</span>户籍地址：</label>
						<select id="registerProvinceNew" name="dictHouseholdProvince"
						mark="dictHouseholdProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}">
								<option value="${item.areaCode}">${item.areaName}</option>
							</c:forEach>
					</select>-<select id="customerRegisterCityNew" name="dictHouseholdCity"
						mark="dictHouseholdCity" class="select78 required">
							<option value="">请选择</option>
					</select>-<select id="customerRegisterAreaNew" mark="dictHouseholdArea"
						name="dictHouseholdArea" class="select78 required">
							<option value="">请选择</option>
					</select> <input type="text" name="householdAddress" mark="householdAddress"
						class="input_text178 required detailAddress" style="width: 250px"
						maxlength="50" /></td>
				</tr>

				<tr>
					<td><label class="lab"><span style="color: red">*</span>单位名称：</label>
						<input type="text" mark="companyName" name="companyNameo"
						maxlength="50" class="input_text178 required " /></td>
					<td><label class="lab"><span style="color: red">*</span>单位地址：</label>
						<input type="text" mark="companyAddress" name="companyAddresso"
						maxlength="100" class="input_text178 required " /></td>
					<td><label class="lab"><span style="color: red">*</span>职务
							：</label> <input type="text" mark="companyPosition"
						name="companyPositiono" maxlength="50"
						class="input_text178 required " /></td>
				</tr>
				<input type="hidden" mark="loanCode" value="${loanCode}"
					name="loanCode"></input>
			</table>
			<div class="box1 mb5">
				<input type="button" class="btn btn-small" value="增加联系人"
					name="addCobContactBtn" onclick="addContractPersonClick(this)" />
			</div>
			<table border="1" cellspacing="0" cellpadding="0" border="0"
				class="coborrowerTable" width="100%">
				<tr>
					<th><span style="color: red">*</span>姓名</th>
					<th>和本人关系</th>
					<th>工作单位</th>
					<th>家庭或工作单位详细地址</th>
					<th>单位电话</th>
					<th><span style="color: red">*</span>手机号</th>
					<th>操作</th>
				</tr>
			</table>
		</div>
	</div>
	<div class="hide">
		<table id="myConfirmCopyg" cellpadding="0" cellspacing="0" border="0"
			width="100%" class="table03">
			<tbody>
				<tr>
					<td><input type="hidden" mark="loanCode" name="loanCode"></input>
						<input type="hidden" mark="id" name="id"> <input
						type="hidden" mark="loanCustomterType" value="1"
						name="loanCustomterType"> <input type="text"
						mark="contactName" name="contactName" maxlength="10"
						class="input_text70 required realName2" /></td>

					<td><select mark="dictContactRelation"
						name="dictContactRelation" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_relation_type')}"
								var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select></td>
					<td><input type="text" mark="contactUint" name="contactUint"
						class="input_text178" maxlength="50" /></td>
					<td><input type="text" mark="dictContactNowAddress"
						maxlength="50" name="dictContactNowAddress" class="input_text150"
						style="width: 250px"></td>
					<td><input type="text" mark="compTel" name="compTel"
						class="input_text178 isTel" /></td>
					<td><input type="text" mark="contactUnitTel"
						name="contactUnitTel" class="input_text178 required isMobile" /></td>
					<td class="tcenter"><input type="button"
						onclick="delCoboContact(this);" class="btn_edit" value="删除"></input></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>