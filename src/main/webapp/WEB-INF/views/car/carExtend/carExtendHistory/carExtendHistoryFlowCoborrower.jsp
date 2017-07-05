<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>共同借款人</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/car/carExtend/carHistoryExtendAdd.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	loan.initCity("liveProvinceId", "liveCityId",
			"liveDistrictId");
	loan.initCity("registerProvince",
			"customerRegisterCity", "customerRegisterArea");
	loan.initCity("registerProvinceNew",
			"customerRegisterCityNew","customerRegisterAreaNew");
	loan.initCity("liveProvinceIdNew",
			"liveCityIdNew","liveDistrictIdNew");
	
	$('.isRareword')
	.bind('click',
			function() {
				if ($(this).prop('checked') == true
						|| $(this).prop('checked') == 'checked') {
					$(this).prev().removeAttr("readonly");
					$(this).prev().val("");
					$(this).val("1");
				} else {
					var phone = $(this).next().val();
					$(this).prev().attr("readonly","readonly");
					$(this).prev().val(phone);
					$(this).val("0");
				}
			});
 	$('.isEmailModify')
	.bind('click',
			function() {
				if ($(this).prop('checked') == true
						|| $(this).prop('checked') == 'checked') {
					$(this).prev().removeAttr("readonly");
					$(this).prev().val("");
					$(this).val("1");
				} else {
					var phone = $(this).next().val();
					$(this).prev().attr("readonly","readonly");
					$(this).prev().val(phone);
					$(this).val("0");
				}
			});
	
	});
</script>
<style>
.coborrowerTable tr td ,th{border-color:#c3c3c3;}
.coborrowerTable{ border-color:#c3c3c3}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/queryHistoryExtend?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendInfo?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowCustomer?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendCompany?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendContact?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li class="active"><a>共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowBank?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
	</ul>
	<jsp:include page="frameFlowFormExtend.jsp"></jsp:include>
	<form class="hide" id="workItemForm">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${carCustomer.customerCertNum}" id="customerCertNum">
		<input type="hidden" value="${workItem.token}" name="token"></input> <input
			type="hidden" value="${workItem.flowId}" name="flowId"></input> <input
			type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
	</form>
	<input type="hidden" value="${loanCode}" id="ipt_loanCode" name="loanCode"></input>
	<input type="hidden" value="${newLoanCode}" id="ipt_newLoanCode" name="newLoanCode"></input>
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
					<c:forEach items="${carLoanCoborrowers}" var="cobs" varStatus="status">
						<div class="contactPanel">
<!-- 							<input type="button" onclick="delHisCoborrower(this);" 
									class="btn_edit" value="删除"> -->
							<table class="table1 coborrowerTable" cellpadding="0" cellspacing="0"
								border="0" width="100%">
								<tr>
									<td width="31%"><label class="lab"><span
											class="red">*</span>共借人姓名：</label> <input type="text" mark="coboName"
										name="coboName" value="${cobs.coboName}"  maxlength="10"
										class="input_text178 required" /> <input id="coboId"
										type="hidden" mark="id" name="id" value="${cobs.id}">
									</td>
									<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
<%-- 										<select mark="dictCertType" name="dictCertType" id="dictCertType"
										class="select180 required">
											<option value="">请选择</option>
											<c:forEach items="${fns:getDictList('com_certificate_type')}"
												var="item">
												<option value="${item.value}"
													<c:if test="${cobs.dictCertType==item.value}">
										   selected
										  </c:if>>${item.label}</option>
											</c:forEach>
									</select> --%>身份证<input mark="dictCertType" value="0" type="hidden"/>
									</td>
									<td><label class="lab"><span class="red">*</span>证件号码：</label>
										<input mark="certNum" name="certNum" value="${cobs.certNum}"
										type="text" class="input_text178 required" maxLength="20"/></td>
								</tr>
								<tr>
									<td width="31%"><label class="lab"><span
											class="red">*</span>性别：</label> <span> <c:forEach
												items="${fns:getDictList('jk_sex')}" var="item">
												<input type="radio" mark="dictSex" name="dictSex${status.index }"
													class="required" 
													<c:if test="${cobs.dictSex==item.value}"> 
						                 	checked
						               </c:if>
													value="${item.value}">${item.label}</input>
											</c:forEach>
									</span>
									<span id="sexSpan" style="display: none" mark="sexthree" title="sex">&nbsp;&nbsp;&nbsp;<font color="red">请选择性别 ！ </font></span>
									</td>
									<td><label class="lab"><span class="red">*</span>手机号码：</label>
										<input name="mobile" value="${cobs.mobile}" mark="mobile"
										type="text" class="input_text178 required isMobile" />
										 <input type="checkbox" id="isRareword" name="istelephonemodify" mark ="istelephonemodify" class="isRareword"
											 <c:if test="${cobs.istelephonemodify == '1'}" > checked </c:if> value="${cobs.istelephonemodify=='1'?'1':'0'}"
											 />修改  <input type="hidden" id="oldcustomerMobilePhone" value="${cobs.mobile}"/>
										</td>
									<td><label class="lab"><span class="red">*</span>婚姻状况：</label>
										<select id="dictMarryStatus" name="dictMarryStatus"
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
											<input type="radio" name="haveChildFlag${status.index }" mark="haveChildFlag"
												<c:if test="${cobs.haveChildFlag==item.value}"> 
					                 				 checked
					               				</c:if>
												value="${item.value}" />${item.label}
         									  </c:forEach></td>
									<td><label class="lab">固定电话：</label> <input
										name="familyTel" value="${cobs.familyTel}" mark="familyTel"
										type="text" class="input_text178 isTel" /></td>
											<td><label class="lab"><span class="red">*</span>邮箱：</label> <input
										id="email" name="email" value="${cobs.email}" mark="email"
										type="text" class="input_text178 required" />
										<input type="checkbox" id="isEmailModify" name="isemailmodify" mark="isemailmodify" class="isEmailModify"
									 <c:if test="${cobs.isemailmodify == '1'}"> checked </c:if> value="${cobs.isemailmodify=='1'?'1':'0'}"
									 />修改 <input type="hidden"  value="${cobs.email}"/>
										</td>
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
										<span style="display:none;"><label>其他所得：</label>
										<input name="otherIncome${status.index}"
										value="<fmt:formatNumber value="${cobs.otherIncome}" pattern="##0.00"/>" maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月
										</span></td>
			<td><label class="lab"><span class="red">*</span>住房性质：</label>
                        <select name="coboHouseHoldProperty${status.index}" mark="coboHouseHoldProperty"
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
                        <input type="text" name="houseRent${status.index}" mark="houseRent"
                        value="<fmt:formatNumber value='${cobs.houseRent}' pattern="0.00"/>"
                        class="input_text178 required number" maxlength="9"/>
                        </span></td>
								</tr>

								<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>现住址：</label> <select id="liveProvinceId"
										name="dictLiveProvince" mark="dictLiveProvince"
										class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}"
												varStatus="status">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveProvince==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="liveCityId" name="dictLiveCity"
										mark="dictLiveCity" class="select78 required">
											<option value="">请选择</option>
												<c:forEach var="item" items="${cityList}"
												varStatus="status">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveCity==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="liveDistrictId" name="dictLiveArea"
										mark="dictLiveArea" class="select78 required">
											<option value="">请选择</option>
												<c:forEach var="item" items="${districtList}"
												varStatus="status">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveArea==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select> <input type="text" name="nowAddress" mark="nowAddress"
										class="input_text178 required" style="width: 250px" maxLength="20"
										value="${cobs.nowAddress}" /></td>
								</tr>
																<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>户籍地址：</label> 	
									<c:choose>
										<c:when test="${cobs.dictHouseholdProvince != null && cobs.dictHouseholdProvince != ''}">
											<sys:carAddressShow detailAddress="${cobs.dictHouseholdView}"></sys:carAddressShow>
										   <input type="hidden" mark="dictHouseholdProvince" value="${cobs.dictHouseholdProvince}"/>
										   <input type="hidden" mark="dictHouseholdCity" value="${cobs.dictHouseholdCity}"/>
										   <input type="hidden" mark="dictHouseholdArea" value="${cobs.dictHouseholdArea}"/>
										   <input type="hidden" mark="householdAddress" value="${cobs.householdAddress}"/>
										</c:when>
										<c:otherwise>								
									<select id="registerProvince" name="dictHouseholdProvince" 
										mark="dictHouseholdProvince" class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}"
												varStatus="status">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictHouseholdProvince==item.areaCode}">
											selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="customerRegisterCity" name="dictHouseholdCity"
										mark="dictHouseholdCity" class="select78 required">
											<option value="">请选择</option>
												<c:forEach var="item" items="${cityList}"
												varStatus="status">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictHouseholdCity==item.areaCode}">
											selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="customerRegisterArea" mark="dictHouseholdArea"
										name="dictHouseholdArea" class="select78 required">
											<option value="">请选择</option>
												<c:forEach var="item" items="${districtList}"
												varStatus="status">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictHouseholdArea==item.areaCode}">
											selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>  <input type="text" name="householdAddress"
										mark="householdAddress" class="input_text178 required"
										style="width: 250px" maxLength="20" value="${cobs.householdAddress}" />
										</c:otherwise>
									</c:choose></td>
								</tr>
								<input type="hidden" value="${newLoanCode}" mark="loanCode"
									name="loanCode"></input>

							</table>
							<div class="box1 mb5">
								<input type="button" class="btn btn-small"
									value="增加联系人" name="addCobContactBtn" onclick="addContractPersonClick(this)"/>
							</div>
							<table border="1" cellspacing="0"
								cellpadding="0" border="0" class="coborrowerTable" width="100%">
								<thead>
									<th><span class="red">*</span>姓名</th>
									<th>和本人关系</th>
									<th>工作单位</th>
									<th>家庭或工作单位详细地址</th>
									<th>单位电话</th>
									<th>手机号</th>
									<th>操作</th>
								</thead>
								<tbody>
									<c:forEach
										items="${cobs.carCustomerContactPerson}"
										var="cper">
										<tr>
											<td><input type="hidden" mark="loanCustomterType"
												value="1" name="loanCustomterType"> <input
												type="text" mark="contactName" name="contactName"
												value="${cper.contactName}"
												class="input_text70 required stringCheck" /></td>
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
												name="contactUint" value="${cper.contactUint}"
												class="input_text178"  maxlength="50" /></td>
											<td><input type="text" mark="dictContactNowAddress"
												name="dictContactNowAddress"
												value="${cper.dictContactNowAddress}"
												class="input_text150" style="width: 250px" maxlength="50"></td>
											<td><input type="text" mark="compTel" name="compTel"
												value="${cper.compTel}" class="input_text178 isTel" /></td>
											<td><input type="text" mark="contactUnitTel"
												value="${cper.contactUnitTel}" name="contactUnitTel" mark="contactUnitTel"
												class="input_text178 isMobile" /></td>
											<td class="tcenter"><input type="button"
												onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</c:forEach>
				</c:when>
				<c:otherwise>
					<div class="contactPanel">
						<table class="table1 coborrowerTable" cellpadding="0" cellspacing="0"
							border="0" width="100%">
							<tr>
								<td width="31%"><label class="lab"><span
										class="red">*</span>共借人姓名：</label> <input type="text" mark="coboName"
									name="coboName" class="input_text178 required" />
									<input type="hidden" mark="id" name="id"></td>
								<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
<%-- 									<select mark="dictCertType" name="dictCertType"
									class="select180 required">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('com_certificate_type')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select> --%>
								身份证<input mark="dictCertType" value="0" type="hidden"/></td>
								<td><label class="lab"><span class="red">*</span>证件号码：</label>
									<input mark="certNum" name="certNum" type="text"
									class="input_text178 required" maxLength="20"/></td>
							</tr>
							<tr>
								<td width="31%"><label class="lab"><span
										class="red">*</span>性别：</label> <span> <c:forEach
											items="${fns:getDictList('jk_sex')}" var="item">
											<input type="radio" mark="dictSex" name="dictSexe"
												class="required" value="${item.value}">${item.label}</input>
										</c:forEach>
								</span>
								<span id="sex_twoSpan" style="display: none" mark="sextwo" title="sex" >&nbsp;&nbsp;&nbsp;<font color="red">请选择性别 ！</font></span>
								</td>
								<td><label class="lab"><span class="red">*</span>手机号码：</label>
									<input name="mobile" mark="mobile" type="text"
									class="input_text178 required isMobile" />
									 <span style="display: none"><input type="checkbox"  name="istelephonemodify" mark ="istelephonemodify" class="isRareword"
									 <c:if test="${cobs.istelephonemodify == '1'}" > checked </c:if> value="${cobs.istelephonemodify=='1'?'1':'0'}"
									 />修改  <input type="hidden"  value="${cobs.mobile}"/>
									 </span>
									</td>
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
											value="${item.value}" />${item.label}
           </c:forEach></td>
								<td><label class="lab">固定电话：</label> <input
									name="familyTel" mark="familyTel" type="text"
									class="input_text178 isTel" /></td>
										<td><label class="lab"><span class="red">*</span>邮箱：</label> <input
										name="email" mark="email"
										type="text" class="input_text178 required" />
									<span style="display: none"><input type="checkbox" name="isemailmodify" mark="isemailmodify" class="isEmailModify"
									 <c:if test="${cobs.isemailmodify == '1'}"> checked </c:if> value="${cobs.isemailmodify=='1'?'1':'0'}"
									 />修改 <input type="hidden"  value="${cobs.email}"/></span>
										</td>
							</tr>
							<tr>
								<td><label class="lab">其他收入：</label>
									<c:forEach items="${fns:getDictList('jk_have_or_nohave')}"
											var="item">
											<input type="radio" name="haveOtherIncome" mark="haveOtherIncome" 
												value="${item.value}" />${item.label}
             						</c:forEach>
										<span style="display:none;"><label>其他所得：</label>
										<input name="otherIncome" maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月
										</span></td>
								<td><label class="lab"><span class="red">*</span>住房性质：</label> <select name="coboHouseHoldProperty" mark="coboHouseHoldProperty"
                            class="select180 required">
                                <option value="">请选择</option>
                                <c:forEach items="${fns:getDictList('jk_house_nature')}"
                                    var="item">
                                    <option value="${item.value}">${item.label}</option>
                                </c:forEach>
                        </select>
                        <span style="display:none;"><label>月租金：</label>
                        <input type="text" name="houseRent" mark="houseRent" class="input_text178 required number" maxlength="9"/>
                        </span></td>
							</tr>
							<tr>
								<td colspan="3"><label class="lab"><span
										class="red">*</span>现住址：</label> <select id="liveProvinceId"
									name="dictLiveProvince" mark="dictLiveProvince"
									class="select78 required">
										<option value="">请选择</option>
										<c:forEach var="item" items="${provinceList}"
											varStatus="status">
											<option value="${item.areaCode}">${item.areaName}</option>
										</c:forEach>
								</select>-<select id="liveCityId" name="dictLiveCity" mark="dictLiveCity"
									class="select78 required">
										<option value="">请选择</option>
								</select>-<select id="liveDistrictId" name="dictLiveArea"
									mark="dictLiveArea" class="select78 required">
										<option value="">请选择</option>
								</select> <input type="text" name="nowAddress" mark="nowAddress"
									class="input_text178 required" style="width: 250px" maxLength="20"/></td>
							</tr>
														<tr>
								<td colspan="3"><label class="lab"><span
										class="red">*</span>户籍地址：</label> <select id="registerProvince"
									name="dictHouseholdProvince" mark="dictHouseholdProvince"
									class="select78 required">
										<option value="">请选择</option>
										<c:forEach var="item" items="${provinceList}"
											varStatus="status">
											<option value="${item.areaCode}">${item.areaName}</option>
										</c:forEach>
								</select>-<select id="customerRegisterCity" name="dictHouseholdCity"
									mark="dictHouseholdCity" class="select78 required">
										<option value="">请选择</option>
								</select>-<select id="customerRegisterArea" mark="dictHouseholdArea"
									name="dictHouseholdArea" class="select78 required">
										<option value="">请选择</option>
								</select> <input type="text" name="householdAddress"
									mark="householdAddress" class="input_text178 required"
									style="width: 250px" maxLength="20"/></td>
							<input type="hidden" mark="loanCode" value="${newLoanCode}" name="loanCode"></input>

						</table>
						<div class="box1 mb5">
							<input type="button" class="btn btn-small"
								value="增加联系人" name="addCobContactBtn" onclick="addContractPersonClick(this)" />
						</div>
						<table border="1" cellspacing="0"
							cellpadding="0" border="0" class="coborrowerTable" width="100%">
							<thead>
								<th><span class="red">*</span>姓名</th>
								<th>和本人关系</th>
								<th>工作单位</th>
								<th>家庭或工作单位详细地址</th>
								<th>单位电话</th>
								<th>手机号</th>
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
			<input type="button" id="coborrowExtendNextBtn" class="btn btn-primary" value="下一步">
		</div>
	</form>
	<!-- 新添加的共借人 -->
	<div class="hide" id="myCoborroCopy">
		<div class="contactPanel">
			<input type="button" onclick="delHisCoborrower(this);" class="btn_edit"
				value="删除">
			<table cellpadding="0" cellspacing="0" border="0" width="100%"
				class="table1 coborrowerTable">
				<tr>
					<td width="31%"><label class="lab"><span class="red">*</span>共借人姓名：</label>
						<input type="text" mark="coboName" name="coboNameo"
						class="input_text178 required" /> <input
						type="hidden" mark="id" name="id"></td>
					<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
<%-- 						<select name="dictCertTypeo" mark="dictCertType"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('com_certificate_type')}"
								var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select> --%>
					身份证<input mark="dictCertType" value="0" type="hidden"/></td>
					<td><label class="lab"><span class="red">*</span>证件号码：</label>
						<input name="certNumo" mark="certNum" type="text" maxLength="20"
						class="input_text178 required" /></td>
				</tr>
				<tr>
					<td width="31%"><label class="lab"><span class="red">*</span>性别：</label>
						<span> <c:forEach items="${fns:getDictList('jk_sex')}"
								var="item">
								<input type="radio" name="dictSexo" mark="dictSex"
									class="required" value="${item.value}">
									${item.label}</input>
							</c:forEach>
					</span>
					<span id="sex_threeSpan" style="display: none" mark="sexthree" title="sex">&nbsp;&nbsp;&nbsp;<font color="red">请选择性别 ！ </font></span>
					</td>
					<td><label class="lab"><span class="red">*</span>手机号码：</label>
						<input name="mobileo" mark="mobile" type="text"
						class="input_text178 required isMobile" />
						<span style="display: none"><input type="checkbox" name="istelephonemodify" mark ="istelephonemodify" class="isRareword"
									 <c:if test="${cobs.istelephonemodify == '1'}" > checked </c:if> value="${cobs.istelephonemodify=='1'?'1':'0'}"
									 />修改  <input type="hidden"  value="${cobs.mobile}"/>
									 </span>
						</td>
					<td><label class="lab"><span class="red">*</span>婚姻状况：</label>
						<select name="dictMarryStatuso"
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
								value="${item.value}"/>${item.label}
                 </c:forEach></td>
					<td><label class="lab">固定电话：</label> <input name="familyTelo"
						mark="familyTel" type="text" class="input_text178 isTel" /></td>
							<td><label class="lab"><span class="red">*</span>邮箱：</label> <input
										 name="emailo" value="${cobs.email}" mark="email"
										type="text" class="input_text178 required" />
										<span style="display: none"><input type="checkbox" name="isemailmodify" mark="isemailmodify" class="isEmailModify"
									 <c:if test="${cobs.isemailmodify == '1'}"> checked </c:if> value="${cobs.isemailmodify=='1'?'1':'0'}"
									 />修改 <input type="hidden" value="${cobs.email}"/></span>
										</td>

				</tr>
				<tr>
					<td><label class="lab">其他收入：</label>
						<c:forEach items="${fns:getDictList('jk_have_or_nohave')}"
							var="item">
							<input type="radio" name="haveOtherIncomeo" mark="haveOtherIncome" 
							value="${item.value}"/>${item.label}</c:forEach>
					<span style="display:none;"><label>其他所得：</label>
					<input name="otherIncomeo" maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月</span></td>
					<td><label class="lab"><span class="red">*</span>住房性质：</label><select name="coboHouseHoldPropertyo" mark="coboHouseHoldProperty"
                            class="select180 required">
                                <option value="">请选择</option>
                                <c:forEach items="${fns:getDictList('jk_house_nature')}"
                                    var="item">
                                    <option value="${item.value}">${item.label}</option>
                                </c:forEach>
                        </select>
                        <span style="display:none;"><label>月租金：</label>
                        <input type="text" name="houseRento" mark="houseRent" class="input_text178 required number" maxlength="9"/>
                        </span></td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span class="red">*</span>现住址：</label>
						<select id="liveProvinceIdNew" name="dictLiveProvince"
						mark="dictLiveProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}" varStatus="status">
								<option value="${item.areaCode}">
									${item.areaName}</option>
							</c:forEach>
					</select>-<select id="liveCityIdNew" name="dictLiveCity" mark="dictLiveCity"
						class="select78 required">
							<option value="">请选择</option>
					</select>-<select id="liveDistrictIdNew" mark="dictLiveArea"
						name="dictLiveArea" class="select78 required">
							<option value="">请选择</option>
					</select> <input type="text" mark="nowAddress" name="nowAddress"
						class="input_text178 required" style="width: 250px" maxLength="20"/></td>
				</tr>
								<tr>
					<td colspan="3"><label class="lab"><span class="red">*</span>户籍地址：</label>
						<select id="registerProvinceNew" name="dictHouseholdProvince"
						mark="dictHouseholdProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}" varStatus="status">
								<option value="${item.areaCode}">${item.areaName}</option>
							</c:forEach>
					</select>-<select id="customerRegisterCityNew" name="dictHouseholdCity"
						mark="dictHouseholdCity" class="select78 required">
							<option value="">请选择</option>
					</select>-<select id="customerRegisterAreaNew" mark="dictHouseholdArea"
						name="dictHouseholdArea" class="select78 required">
							<option value="">请选择</option>
					</select> <input type="text" name="householdAddress" mark="householdAddress"
						class="input_text178 required" style="width: 250px" maxLength="20"/></td>
				</tr>
				<input type="hidden" mark="loanCode" value="${newLoanCode}" name="loanCode"></input>
			</table>
			<div class="box1 mb5">
				<input type="button" class="btn btn-small"
					value="增加联系人" name="addCobContactBtn" onclick="addContractPersonClick(this)"/>
			</div>
			<table border="1" cellspacing="0" cellpadding="0"
				border="0" class="coborrowerTable" width="100%">
				<tr>
					<th><span class="red">*</span>姓名</th>
					<th>和本人关系</th>
					<th>工作单位</th>
					<th>家庭或工作单位详细地址</th>
					<th>单位电话</th>
					<th>手机号</th>
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
						mark="contactName" name="contactName"
						class="input_text70 required stringCheck" /></td>

					<td><select mark="dictContactRelation"
						name="dictContactRelation" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_relation_type')}"
								var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select></td>
					<td><input type="text" mark="contactUint" name="contactUint"
						class="input_text178" maxlength="50"/></td>
					<td><input type="text" mark="dictContactNowAddress"
						name="dictContactNowAddress" class="input_text150"   maxlength="50"
						style="width: 250px"></td>
					<td><input type="text" mark="compTel" name="compTel" maxlength="50"
						class="input_text178 isTel" /></td>
					<td><input type="text" mark="contactUnitTel"
						name="contactUnitTel" class="input_text178" maxlength="50" /></td>
					<td class="tcenter"><input type="button"
						onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>