<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>展期信息-共同借款人</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewExtendMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselectDisable.js"></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carExtendConsultForm.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	 loan.initCity("liveProvinceIded", "liveCityIded","liveDistrictIded");
	loan.initCity("registerProvinced","customerRegisterCityed", "customerRegisterAreaed");
	
	loan.initCity("registerProvinceNew","customerRegisterCityNew","customerRegisterAreaNew");
	loan.initCity("liveProvinceIdNew","liveCityIdNew","liveDistrictIdNew"); 
	//$("#dictCertType").attr("disabled", true);
	
	$(".contactPanel").each(function(i, element) {
		loan.initCity("liveProvinceIded"+i, "liveCityIded"+i,"liveDistrictIded"+i);
		areaselectCardDisabled.initCity("registerProvince"+i,"customerRegisterCity"+i, "customerRegisterArea"+i, $("#houseCityId" + i).val(), $("#houseAreaId" + i).val());
		
	});
	$(".contactPanel").find("select[mark='dictCertType']").attr("disabled", true);
	//验证JS
	$("#carCoborrow").validate({
				onclick: true, 
				rules:{
					customerMobilePhone:{
						mobile:true,
					}
			},
				messages : {
					coboName:{
						realName:"姓名只能为2-30个汉字"
					},
				
				}
	});
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
		//验证是否有其他收入，如果有显示收入金额输入框
	 	$("input[mark='haveOtherIncome']").change(function(){
			if($(this).val()=='1'){
				$(this).next("span").show();
				$(this).next("span").children("input[mark='otherIncome']").addClass("required");
			}else{
				$(this).next().next("span").hide();
				$(this).next().next("span").children("input[mark='otherIncome']").removeClass("required");
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
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarContract?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendInfo?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowCustomer?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendCompany?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendContact?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toExtendCoborrower?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowBank?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
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
	<input type="hidden" value="${loanCode}" id="ipt_loanCode" name="loanCode"></input>
	<input type="hidden" value="${provincecount}" id="coboCount"></input>
	<input type="hidden" value="${newLoanCode}" id="ipt_newLoanCode" name="newLoanCode"></input>
	<form id="carCoborrow">
		<!-- <div style="text-align: right;">
			<input type="button" id="addCoborrowBtn" class="btn btn-small"
				value="增加共借人" />
		</div> -->
		<div id="coborroArea">
			<c:choose>
				<c:when
					test="${carLoanCoborrowers != null && fn:length(carLoanCoborrowers) > 0}">
					<c:forEach items="${carLoanCoborrowers}" var="cobs"  varStatus="status">
						<div class="contactPanel">
						<input type="hidden" id="houseCityId${status.index }" value="${cobs.dictHouseholdCity}"/>
						<input type="hidden"  id="houseAreaId${status.index }" value="${cobs.dictHouseholdArea}"/>
						<input type="hidden"  id="livingCityId${status.index }" value="${cobs.dictLiveCity}"/>
						<input type="hidden"  id="livingAreaId${status.index }" value="${cobs.dictLiveArea}"/>
							<table class="table1 coborrowerTable" cellpadding="0" cellspacing="0"
								border="0" width="100%">
								<tr>
									<td width="31%"><label class="lab"><span
											class="red">*</span>共借人姓名：</label> <input type="text" mark="coboName"
										name="coboName" value="${cobs.coboName}" maxlength="10" readonly="readonly"
										class="input_text178 required stringCheck" /> <input
										type="hidden" mark="id" name="id" value="${cobs.id}">
									</td>
									<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
<%-- 										<select mark="dictCertType" name="dictCertType" id="dictCertType${status.index}"
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
										<input mark="certNum" name="certNum" value="${cobs.certNum}" readonly="readonly"
										type="text" class="input_text178 isCertificateCobo required" /></td>
								</tr>
								<tr>
									<td width="31%"><label class="lab"><span
											class="red">*</span>性别：</label> <span> <c:forEach
												items="${fns:getDictList('jk_sex')}" var="item">
												<input type="radio" mark="dictSex" name="dictSex${status.index}"
													class="required"  onclick="return false"
													<c:if test="${cobs.dictSex==item.value}"> 
						                 	checked
						               </c:if>
													value="${item.value}">${item.label}</input>
											</c:forEach>
									</span><span id="sexSpan" style="display: none" mark="sexone" title="sex">&nbsp;&nbsp;&nbsp;<font color="red">请选择性别 ！</font></span></td>
									<td><label class="lab"><span class="red">*</span>手机号码：</label>
										<input name="mobile" id="cobomobile" value="${cobs.mobile}" mark="mobile" 
										type="text" class="input_text178 required isMobile " 
										<c:if test="${cobs.istelephonemodify ne '1'}"> readonly="readonly" </c:if>/>
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
											id="customerEmail" name="email" value="${cobs.email}" mark="email"
											type="text" class="input_text178 required"  
											<c:if test="${cobs.isemailmodify ne '1'}"> readonly="readonly" </c:if> />
											 <input type="checkbox" id="isEmailModify" name="isemailmodify" mark="isemailmodify" class="isEmailModify"
									 <c:if test="${cobs.isemailmodify == '1'}"> checked </c:if> value="${cobs.isemailmodify=='1'?'1':'0'}"
									 />修改 <input type="hidden" id="oldcustomerEmail" value="${cobs.email}"/>
									</td>

								</tr>
								<tr>
									<td colspan="1"><label class="lab">其他收入：</label> 
											<c:forEach items="${fns:getDictList('jk_have_or_nohave')}"
													var="item">
													<input type="radio" name="haveOtherIncome${status.index}"  mark="haveOtherIncome" 
														value="${item.value}"
														<c:if test="${item.value == cobs.haveOtherIncome}">
                    								 checked="checked"
                 								       </c:if> />${item.label}
               								 </c:forEach>
               							<c:choose>
               								<c:when test="${cobs.haveOtherIncome=='1'}"><span><label><span style="color: red">*</span>其他所得：</label></c:when>
               								<c:otherwise><span style="display:none;"><label><span style="color: red">*</span>其他所得：</label></c:otherwise>
               							</c:choose>
										<input name="otherIncome${status.index}"
										value="<fmt:formatNumber value="${cobs.otherIncome}" pattern="##0.00"/>" maxlength="11" mark="otherIncome" type="text" class="input_text178 number" />元/每月
										</span></td>
<%-- 									<td><label class="lab">房屋出租：</label> <input maxlength="11"
										name="houseRent" value="<fmt:formatNumber value="${cobs.houseRent}" pattern="##.00"/>" mark="houseRent"
										type="text" class="input_text178  number" />元/每月</td> --%>
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
                        <input type="text" name="houseRent${status.index}" mark="houseRent"
                        value="<fmt:formatNumber value='${cobs.houseRent}' pattern="0.00"/>"
                        class="input_text178 required number" maxlength="9"/>
                        </span>
                        </td>
								</tr>
								<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>户籍地址：</label> 
											<sys:carAddressShow detailAddress="${cobs.dictHouseholdView}"></sys:carAddressShow>
											 <input type="hidden" name="dictHouseholdProvince" mark="dictHouseholdProvince" value="${cobs.dictHouseholdProvince}"/>
                                             <input type="hidden" name="dictHouseholdCity"  mark="dictHouseholdCity" value="${cobs.dictHouseholdCity}"/>
                                             <input type="hidden" name="dictHouseholdArea" mark="dictHouseholdArea" value="${cobs.dictHouseholdArea}"/>
                                             <input type="hidden" name="householdAddress" mark="householdAddress" value="${cobs.householdAddress}"/>
									</td>
								</tr>
								<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>现住址：</label> <select id="liveProvinceId${status.index }"
										name="dictLiveProvince" mark="dictLiveProvince"
										class="select78 required">
											<option value="">请选择</option>
											<c:forEach var="item" items="${provinceList}">
												<option value="${item.areaCode}"
													<c:if test="${cobs.dictLiveProvince==item.areaCode}">
										selected</c:if>>
													${item.areaName}</option>
											</c:forEach>
									</select>-<select id="liveCityId${status.index }" name="dictLiveCity"
										mark="dictLiveCity" class="select78 required">
											<option value="">请选择</option>
									</select>-<select id="liveDistrictId${status.index }" name="dictLiveArea"
										mark="dictLiveArea" class="select78 required">
											<option value="">请选择</option>
									</select> <input type="text" name="nowAddress" mark="nowAddress"
										class="input_text178 required" style="width: 250px" maxlength="50"
										value="${cobs.nowAddress}" /></td>
								</tr>
								<input type="hidden" value="${cobs.loanCode}" mark="loanCode"
									name="loanCode"></input>
									<input type="hidden" value="${newLoanCode}" mark="newLoanCode"
									name="newLoanCode"></input>

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
									<th><span class="red">*</span>手机号</th>
									<th>操作</th>
								</thead>
								<tbody>
									<c:choose>
										<c:when
											test="${cobs.carCustomerContactPerson!= null && fn:length(cobs.carCustomerContactPerson) > 0}">
											<c:forEach
												items="${cobs.carCustomerContactPerson}"
												var="cper">
												<tr>
													<td>
													<input type="hidden" mark="loanCustomterType"
														value="1" name="loanCustomterType" value="${cper.loanCustomterType}"> <input
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
														class="input_text178" /></td>
													<td><input type="text" mark="dictContactNowAddress"
														name="dictContactNowAddress"
														value="${cper.dictContactNowAddress}"
														class="input_text150" style="width: 250px"></td>
													<td><input type="text" mark="compTel" name="compTel"
														value="${cper.compTel}" class="input_text178 isTel" /></td>
													<td><input type="text" mark="contactUnitTel"
														value="${cper.contactUnitTel}" name="contactUnitTel"
														class="input_text178 required" /></td>
													<td class="tcenter"><input type="button"
														onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
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
				</c:otherwise>
			</c:choose>
		</div>
		<div class="tright mr10 mb5">
			<input type="button" id="coborrowExtendNextBtn" class="btn btn-primary" value="下一步">
		</div>
	</form>
	<div class="hide">
		<table id="myConfirmCopyg" cellpadding="0" cellspacing="0" border="0"
			width="100%" class="table03">
			<tbody>
				<tr>
					<td><input type="hidden" mark="loanCode" name="loanCode"></input>
					<input type="hidden" mark="newLoanCode" name="newLoanCode"></input>
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
						class="input_text178" /></td>
					<td><input type="text" mark="dictContactNowAddress"
						name="dictContactNowAddress" class="input_text150"
						style="width: 250px"></td>
					<td><input type="text" mark="compTel" name="compTel"
						class="input_text178 isTel" /></td>
					<td><input type="text" mark="contactUnitTel"
						name="contactUnitTel" class="input_text178 required isMobile" /></td>
					<td class="tcenter"><input type="button"
						onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>