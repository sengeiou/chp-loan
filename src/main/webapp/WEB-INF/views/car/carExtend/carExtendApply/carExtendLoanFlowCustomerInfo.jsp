<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!-- 个人资料 -->
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewExtendMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript"
	src="${context}/js/consult/consultValidate.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
					function() {
						jQuery.validator.addMethod("telOrPhone", function(value,element) {
							var length = value.length;
							var mobile =/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
							var tel = /^\d{3,4}-?\d{7,9}$/; 
							return this.optional(element) || (tel.test(value) || mobile.test(value));
							}, "格式为：0XX(X)-XXXXXXX(X)或有效的11位手机号码");
						
					 	function changeHouse(houseValue) {  
							if( houseValue != null){
								if(houseValue == '0' || houseValue == '2'){
									$("#rentLabelName").html("<span style='color: red'>*</span> 月供金额：");
									$("#isHiddenRent").show();
								}else if(houseValue == '4'){
									$("#rentLabelName").html("<span style='color: red'>*</span> 月租金：");
									$("#isHiddenRent").show();
								}else{
									$("#jydzzmRentMonth").val("0");
									$("#isHiddenRent").hide();
								}
							}
						} 
					 	
						var customerHouseHoldProperty = $("#customerHouseHoldProperty").val();
						if(customerHouseHoldProperty == "4"){
							$("#isHiddenRent").show();
						}else {
							$("#isHiddenRent").hide();
						}

						loan.initCity("liveProvinceId", "liveCityId",
								"liveDistrictId");
						loan.initCity("registerProvince",
								"customerRegisterCity", "customerRegisterArea");
						areaselect.initCity("liveProvinceId", "liveCityId",
								"liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden")
										.val());
						$("#registerProvince,#customerRegisterCity,#customerRegisterArea").attr("disabled", true);
						$("#customerHouseHoldProperty").trigger("change");
						$('#longTerm')
								.bind(
										'click',
										function() {
											if ($(this).attr('checked') == true
													|| $(this).attr('checked') == 'checked') {
												$('#idEndDay').val('');
												$('#idEndDay').attr('disabled',
														true);
											} else {
												$('#idEndDay').attr('disabled',
														false);
											}
										});
					 	//验证JS
						$("#customerForm").validate({
									onclick: true, 
									rules:{
										customerEmail:{
											email:"/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/"
										},
									},
										messages : {
											customerEmail:{
												email:"请输入正确邮箱格式"
											},
									},
						}); 
						
					 	$('#isRareword')
						.bind('click',
								function() {
									if ($(this).prop('checked') == true
											|| $(this).prop('checked') == 'checked') {
										$('#bankAccountName').removeAttr("readonly");
										$('#bankAccountName').val("");
										
									} else {
										var phone = $("#oldcustomerMobilePhone").val();
										$('#bankAccountName').attr("readonly","readonly");
										$('#bankAccountName').val(phone);
										
									}
								});
						//$('#isRareword').trigger("click");
					 	$('#isEmailModify')
						.bind('click',
								function() {
									if ($(this).prop('checked') == true
											|| $(this).prop('checked') == 'checked') {
										$('#customerEmail').removeAttr("readonly");
										$('#customerEmail').val("");
									} else {
										var email = $("#oldcustomerEmail").val();
										$('#customerEmail').attr("readonly","readonly");
										$('#customerEmail').val(email);
									}
								});
					 // 住房性质变化时  控制月租金输入框是否显示 
 						 $('#customerHouseHoldProperty').bind('change',function(){
								  changeHouse($("#customerHouseHoldProperty").attr("selected","selected").val());
						 });  
 						$('#customerHouseHoldProperty').trigger('change');
					 	$('#customerNextBtn').bind('click',function () {
					 		$("#registerProvince,#customerRegisterCity,#customerRegisterArea").attr("disabled", false);
					 		$("#customerForm").submit();
					 	});
					});
	

</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarContract?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendInfo?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowCustomer?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendCompany?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendContact?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toExtendCoborrower?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowBank?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
</ul>
	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
	 
<div id="div1" class="content control-group ">
	<form id="customerForm"  action="${ctx}/car/carExtendApply/addCarExtendCustomer" method="post">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>		
        <input type="hidden" value="${loanCode}" name="loanCode"/>
		<input type="hidden" value="${carCustomer.customerCode}" name="customerCode">
		<input type="hidden" value="${newLoanCode}" name="newLoanCode">
		<input type="hidden" value="${carCustomer.dictCertTypeCode }" name="dictCertType" ></input>
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>

					<td><label class="lab">客户姓名：</label>${carCustomer.customerName}
						<input type="hidden" name="customerName" readonly="readonly"
						value="${carCustomer.customerName}"/></td>
					<td><label class="lab">性别：</label>
						${carCustomer.customerSex}
						<input type="hidden" value="${carCustomer.customerSexCode}" name="customerSex"/>
						</td>
					<td><label class="lab"><span style="color: red">*</span>婚姻状况：</label>
						<select id="dictMarry" name="dictMarryStatus" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
								<option value="${item.value}"
									<c:if test="${carCustomer.dictMarryStatusCode==item.value}">
					      selected = true
					     </c:if>>${item.label}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
				  <td><label class="lab"><span style="color: red">*</span>手机号码：</label>
								<input type="text" id="bankAccountName" name="customerMobilePhone"
									value="${carCustomerBase.customerMobilePhone}" class="required isMobile" <c:if test="${carCustomerBase.isTelephoneModify ne '1'}"> readonly="readonly" </c:if>/>
									 <input type="checkbox" id="isRareword" name= "isTelephoneModify" 
				 <c:if test="${carCustomerBase.isTelephoneModify == '1'}"> checked='checked' </c:if>
				 />修改  <input type="hidden" id="oldcustomerMobilePhone" value="${carCustomerBase.customerMobilePhone}"/> </td>
			    	<td><label class="lab">有无子女：</label> <c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="customerHaveChildren" 
								value="${item.value}"
								<c:if test="${item.value==carCustomer.customerHaveChildrenCode}">
                     checked='true'
                  </c:if>/>${item.label}
                </c:forEach></td>
                
                <td><label class="lab">最高学历：</label>
                ${carCustomer.dictEducation}
                <input type="hidden" value="${carCustomer.dictEducationCode}" name="dictEducation"/>
						</td>
				</tr>

				<tr>
					
					<td><label class="lab">证件号码：</label>
					<input type="hidden" name="customerCertNum" value="${carCustomerBase.customerCertNum}"/>
						${carCustomerBase.customerCertNum}
						</td>
					<td><label class="lab"><span style="color: red">*</span>证件有效期：</label>
						<input id="idStartDay" name="idStartDay" readonly="readonly"
						value="<fmt:formatDate value='${carCustomerBase.idStartDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text70 required" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idStartDay\')}'})"
						style="cursor: pointer" />-<input id="idEndDay"
						name="idEndDay" readonly="readonly"
						value="<fmt:formatDate value='${carCustomerBase.idEndDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text70" size="10"
						<c:if test="${carCustomerBase.idEndDay==null}"> 
						  disabled=true
						</c:if>
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idEndDay\')}'})"
						style="cursor: pointer" /> <input type="checkbox" id="longTerm" readonly="readonly"
						name="isLongTerm" value="1"
						<c:if test="${carCustomerBase.idEndDay==null}"> 
						     checked=true 
						</c:if>  />
						长期</td>
						
						<td><label class="lab">出生日期：</label> 
						<fmt:formatDate value='${carCustomer.customerBirthday}' pattern="yyyy-MM-dd"/>
						<input type="hidden" value="<fmt:formatDate value='${carCustomer.customerBirthday}' pattern="yyyy-MM-dd"/>" name="customerBirthday"/>
					</td>
				</tr>
		
				<tr>
					<td><label class="lab">暂住证：</label> 
					${carCustomer.customerTempPermit}
					<input type="hidden" value="${carCustomer.customerTempPermitCode}" name="customerTempPermit"/>
					</td>
                
					<td><label class="lab"><span style="color: red">*</span>住房性质：</label>
						<select name="customerHouseHoldProperty" id="customerHouseHoldProperty"
							value="${carCustomer.customerHouseHoldProperty}"
							class="select180 required">
								<option value="">请选择</option>
								<c:forEach items="${fns:getDictList('jk_house_nature')}"
									var="item">
									<option value="${item.value}"
										<c:if test="${item.value==carCustomer.customerHouseHoldPropertyCode}">
						      selected=true 
						    </c:if>>${item.label}</option>
								</c:forEach>
						</select>	
						<%-- <input type="hidden" value="${carCustomer.customerHouseHoldPropertyCode}" name="customerHouseHoldProperty" id="customerHouseProperty"/> --%>
						</td><td id="isHiddenRent" ><label class="lab" id="rentLabelName"><span style="color: red">*</span>月租金：</label>
						<input type="text" name="jydzzmRentMonth" id="jydzzmRentMonth"
						value="<fmt:formatNumber value='${carCustomer.jydzzmRentMonth}' pattern="0.00"/>"
						class="input_text178 required number" maxlength="9"/>
						</td>
				</tr>
				<tr>
					<td><label class="lab">初到本市时间：</label>
					<fmt:formatDate value='${carCustomer.customerFirtArriveYear}' pattern="yyyy-MM-dd"/>
						<input id="customerFirtArriveYear"
						name="customerFirtArriveYear"
						value="<fmt:formatDate value='${carCustomer.customerFirtArriveYear}' pattern="yyyy-MM-dd"/>" type="hidden" />
					</td>
					<td><label class="lab">起始居住时间：</label>
	<%-- 				<fmt:formatDate value='${carCustomer.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>
					 <input type="hidden" value="<fmt:formatDate value='${carCustomer.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>" name="customerFirstLivingDay" > --%>
					<input
						id=""
						name="customerFirstLivingDay"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" value="<fmt:formatDate value='${carCustomer.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>"/>
					</td>
					<td><label class="lab">供养亲属人数：</label>
					${carCustomer.customerFamilySupport}
						<input type="hidden" name="customerFamilySupport" maxlength="2"
						value="${carCustomer.customerFamilySupport}" />
						</td>
				</tr>
				
				<tr>
					<td><label class="lab"><span style="color: red">*</span>电子邮箱：</label> <input name="customerEmail"
						type="text" id="customerEmail"
						value="${carCustomer.customerEmail}" class="input_text178 required" <c:if test="${carCustomerBase.isEmailModify ne '1'}"> readonly="readonly" </c:if> />
						 <input type="checkbox" id="isEmailModify" name= "isEmailModify"
				 <c:if test="${carCustomerBase.isEmailModify == '1'}"> checked='checked' </c:if>
				 />修改 <input type="hidden" id="oldcustomerEmail" value="${carCustomer.customerEmail}"/></td>
                      <td><label class="lab">信用额度：</label>
						<fmt:formatNumber value='${carCustomer.creditLine}' pattern="0.00"/>
						<input type="hidden" name="creditLine" maxlength="11"
						value="${carCustomer.creditLine}" />
					 </td>
					 
					<td><label class="lab"><span style="color: red">*</span>客户来源：</label>
                		<select id="dictCustomerSource2" name="dictCustomerSource2" value="${carCustomer.dictCustomerSource2}" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_cm_src')}" var="item">
					  	<option value="${item.value}"
					  	<c:if test="${carCustomer.dictCustomerSource2==item.value}">
                              selected = true 
                         </c:if>>${item.label}</option>
				     </c:forEach>
				</select></td>
			
				</tr>
				<tr>
					<td colspan="2"><label class="lab"><span class="red">*</span>现住址：</label>
						<select class="select78 required" id="liveProvinceId"
						name="customerLiveProvince">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${carCustomer.customerLiveProvince==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>-<select class="select78 required" id="liveCityId"
						name="customerLiveCity"
						value="${carCustomer.customerLiveCity}">
							<option value="">请选择</option>
							<c:forEach var="item" items="${cityList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${carCustomer.customerLiveCity==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>-<select id="liveDistrictId" class="select78 required"
						name="customerLiveArea"
						value="${carCustomer.customerLiveArea}">
							<option value="">请选择</option>
							<c:forEach var="item" items="${districtList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${carCustomer.customerLiveArea==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>
				<input type="hidden" id="liveCityIdHidden" value="${carCustomer.customerLiveCity}"/>
					<input type="hidden" id="liveDistrictIdHidden" value="${carCustomer.customerLiveArea}"/> 
					<input type="text"
						name="customerAddress" maxlength="50"
						value="${carCustomer.customerAddress}" style="width: 250px"
						class="required" /></td>
					<td><label class="lab">本市电话：</label> <input
						name="cityPhone" type="text" id="cityPhone"
						value="${carCustomer.cityPhone}" class="input_text178 telOrPhone" /></td>
				</tr>
				<tr>
					<td colspan="3" ><label class="lab"><span class="red">*</span>户籍地址：</label>
					   <sys:carAddressShow detailAddress="${carCustomer.customerRegisterAddressView}"></sys:carAddressShow>
					   <input type="hidden" name="customerRegisterProvince" value="${carCustomer.customerRegisterProvince}"/>
					   <input type="hidden" name="customerRegisterCity" value="${carCustomer.customerRegisterCity}"/>
					   <input type="hidden" name="customerRegisterArea" value="${carCustomer.customerRegisterArea}"/>
					   <input type="hidden" name="customerRegisterAddress" value="${carCustomer.customerRegisterAddress}"/>
					</td>
				</tr>
			</table>

			<div class="tright mr10 mb5">
				<input type="button" class="btn btn-primary" id="customerNextBtn"
					value="下一步">
			</div>
		</form>
	</div>
</body>
</html>