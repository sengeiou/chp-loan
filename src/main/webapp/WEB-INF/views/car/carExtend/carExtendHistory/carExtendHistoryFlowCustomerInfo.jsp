<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!-- 个人资料 -->
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
					function() {
						
						var carLoanCustomer = {};
						
						carLoanCustomer.initBirthday = function(customerCertNum){
							var birthdayNum = '';
							if(customerCertNum !='' && customerCertNum != null){
								birthdayNum = customerCertNum.substring(6,10) + "-"+customerCertNum.substring(10,12) + "-" + customerCertNum.substring(12,14)
							}
							$("#customerBirthday").val(birthdayNum);
						}
						var idStartDay = $("#idStartDay").val();
						var idEndDay = $("#idEndDay").val();
						if(idStartDay == null || idStartDay == ''){
							$('#longTerm').attr("checked",false);
							$('#idEndDay').attr('disabled',false);
						}else if (idStartDay != null || idStartDay != '') {
							if( idEndDay == null || idEndDay == ''){
							$('#longTerm').attr("checked",true);
							$('#idEndDay').attr('disabled',true);
							}
						} else if(idStartDay != null && idStartDay != ''){
							$("#idEndDay").addClass("required");
						} else {
							$("#idEndDay").removeClass("required");
						}
						// 初始化出生日期
						carLoanCustomer.initBirthday($("#customerCertNum").val());
						 // 当身份证号改变时改变出生日期
						  $('#customerCertNum').bind('change',function(){
							  carLoanCustomer.initBirthday($("#customerCertNum").val());
						 }); 
						 
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
							
							 // 住房性质变化时  控制月租金输入框是否显示 
	 						 $('#customerHouseHoldProperty').bind('change',function(){
									  changeHouse($("#customerHouseHoldProperty").attr("selected","selected").val());
							 });  
	 						$('#customerHouseHoldProperty').trigger('change');
	 						
						 	$('#isRareword')
							.bind('click',
									function() {
										if ($(this).prop('checked') == true
												|| $(this).prop('checked') == 'checked') {
											$('#bankAccountName').removeAttr("readonly");
											$('#bankAccountName').val("");
											$('#isRareword').val("1");
											
										} else {
											var phone = $("#oldcustomerMobilePhone").val();
											$('#bankAccountName').attr("readonly","readonly");
											$('#bankAccountName').val(phone);
											$('#isRareword').val("0");
											
										}
									});
							$('#isEmailModify')
							.bind('click',
									function() {
										if ($(this).prop('checked') == true
												|| $(this).prop('checked') == 'checked') {
											$('#customerEmail').val('');
											$('#isEmailModify').val("1");
											$('#customerEmail').removeAttr("readonly");
										} else {
											var email = $("#oldcustomerEmail").val();
											$('#customerEmail').attr("readonly","readonly");
											$('#customerEmail').val(email);
											$('#isEmailModify').val("0");
										}
									});
						 	
						//级联联动
						loan.initCity("liveProvinceId", "liveCityId",
								"liveDistrictId");
						loan.initCity("registerProvince",
								"customerRegisterCity", "customerRegisterArea");
						//回显
						areaselect.initCity("liveProvinceId", "liveCityId",
								"liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden")
										.val());
						areaselect.initCity("registerProvince",
								"customerRegisterCity", "customerRegisterArea",
								$("#customerRegisterCityHidden").val(), $(
										"#customerRegisterAreaHidden").val());
										$('#longTerm')
										.bind(
												'click',
												function() {
													if ($(this).attr('checked') == true
															|| $(this).attr('checked') == 'checked') {
														$('#idEndDay').val('');
														$('#idEndDay').attr('disabled',
																true);
													}else {
														$('#idEndDay').attr('disabled',false);
														$("#idEndDay").addClass("required");
													}
												});
						//点击下一步
						$("#customerNextBtn").bind('click',function(){
							//验证JS
							var f=$("#customerForm").validate({
									onclick: true, 
									rules:{
													customerEmail:{
														email:"/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/"
													},
													
													customerAddress:{
														maxlength:100
													},
													customerRegisterAddress:{
														maxlength:100
													},
													customerCertNum:{
														card:true
													},
													customerFamilySupport:{
														maxlength:2
													}
										},
									messages: {
												customerEmail:{
													email:"请输入正确邮箱格式"
												},
												customerAddress:{
													maxlength:'输入现地址长度不能超过100'
												},
												customerRegisterAddress:{
													maxlength:'输入户籍地址长度不能超过100'
												},
												customerFamilySupport:{
													maxlength:"输入供养亲属不能超过2个"
												}
										}
							}).form(); 
							if(f){
								var dictSex = $("#customerForm").find("input:radio[name='customerSex']:checked").val();
								var dictCertType = $("#customerForm").find("input[name='dictCertType']")[0];
								if (dictCertType == undefined) {
									dictCertType = $("#customerForm").find("input[name='dictCertType']")[0].value;
								} else {
									dictCertType = dictCertType.value;
								}
								var certNum = $("#customerForm").find("input[name='customerCertNum']")[0].value;
								var flag = false;
								if (dictCertType == '0') { // 若为身份证
									var tSex = certNum.substr(certNum.length - 2, 1);
									if ((((tSex % 2 == 0) ? false : true) && dictSex == '0') || (((tSex % 2 == 0) ? true : false) && dictSex == '1')) {
										flag = true;
									}
								}
								if (flag) {
									$("#registerProvince,#customerRegisterCity,#customerRegisterArea").attr("disabled", false);
									$("#customerForm").submit();
								} else {
									alert("身份证和性别不匹配，请重新填写！");
								}
							}
							
						});
				
					});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/queryHistoryExtend?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendInfo?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li class="active"><a>个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendCompany?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendContact?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toExtendCoborrower?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowBank?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
	</ul>
	<jsp:include page="frameFlowFormExtend.jsp"></jsp:include>
	<div id="div1" class="content control-group ">
		<form id="customerForm"  action="${ctx}/car/carExtendHistory/addCarExtendCustomer" method="post">
        <input type="hidden" value="${loanCode}" name="loanCode"/>
        <input type="hidden" value="${newLoanCode}" name="newLoanCode"/>
		<input type="hidden" value="${carCustomer.customerCode}" name="customerCode">
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab"><span class="red">*</span>客户姓名：</label>
						<input type="text" name="customerName"
						value="${carCustomer.customerName == null ? carLoanInfo.loanCustomerName : carCustomer.customerName}" class="input_text178 required realName"  maxlength="10"/></td>
					<td>
					<label class="lab"><span class="red">*</span>性别：</label>
					<c:choose>
							<c:when test="${carCustomer.customerSex != null}">
							  <span>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" 
										name="customerSex" value="${item.value}"
										<c:if test="${carCustomer.customerSexCode == item.value}">
                                  checked
                                </c:if> onclick="return false" class="required">
                           ${item.label}
                         </input>
								</c:forEach>
								</span>
							</c:when>
							<c:otherwise>
							  <span>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" name="customerSex" value="${item.value}" class="required">
                             ${item.label}
                           </input>
								</c:forEach>
								</span>
							</c:otherwise>
						</c:choose>
						</td>
					<td>
						<label class="lab"><span class="red">*</span>婚姻状况：</label>
	                     <select id="dictMarryStatus" name="dictMarryStatus" class="select180 required">
		                   <option value="">请选择</option>
			               <c:forEach items="${fns:getDictList('jk_marriage')}" var="aitem">
								  <option value="${aitem.value}"
									   <c:if test="${carCustomer.dictMarryStatusCode==aitem.value}"> 
									    selected 
									   </c:if>>${aitem.label}
								  </option>
						     </c:forEach>
						</select>
				</td>			
				</tr>
				<tr>
				  <td><label class="lab"><span class="red">*</span>手机号码：</label>
								<input type="text" id="bankAccountName" name="customerPhoneFirst" class="required isMobile input_text178" 
									value="${carCustomer.customerPhoneFirst}"/>
								 <input type="checkbox" id="isRareword" name= "isTelephoneModify" 
				 <c:if test="${carCustomerBase.isTelephoneModify == '1'}"> checked='checked' </c:if>
				  value="${carCustomerBase.isTelephoneModify =='1'?'1':'0'}"
				 />修改  <input type="hidden" id="oldcustomerMobilePhone" value="${carCustomer.customerPhoneFirst}"/>
							</td>
			    	<td><label class="lab">有无子女：</label> 
			    	<c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="customerHaveChildren"
								value="${item.value}"
								<c:if test="${item.value==carCustomer.customerHaveChildren}">
                     			checked
                 		 </c:if> />${item.label}
               	 </c:forEach>
                </td>
                
                <td><label class="lab"><span class="red">*</span>最高学历：</label>
                <select id="dictEducation" name="dictEducation"
						value="${carCustomer.dictEducationCode}"
						class="select2-container select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}"
									<c:if test="${carCustomer.dictEducationCode==curEdu.value}">
					      		selected
					     </c:if>>${curEdu.label}</option>
							</c:forEach>
					</select>
				</td>
				</tr>
				<tr>
					<td style="width:32%">
			  			<label class="lab"><span class="red">*</span>证件类型：</label>
<%-- 			  			<c:choose>
							<c:when test="${carCustomer.dictCertType != null}">
							  ${carCustomer.dictCertType}<input type="hidden" name="dictCertType" value="${carCustomer.dictCertTypeCode}"/>
							</c:when>
							<c:otherwise>
								<select name="dictCertType" class="select180 required">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select>
							</c:otherwise>
						</c:choose> --%>
						身份证<input type="hidden" name="dictCertType" value="0"/>
					</td>
						<td><label class="lab"><span style="color: red">*</span>证件号码：</label>
						<c:choose>
							<c:when test="${carCustomer.customerCertNum!=null}">
								<input name="customerCertNum" readonly="readonly"
									id="customerCertNum" type="text"
									value="${carCustomer.customerCertNum}"
									class="input_text178" />
							</c:when>
							<c:otherwise>
								<input name="customerCertNum" id="customerCertNum"
									type="text" value="${bv.mateCertNum}"
									class="input_text178 required card" />
							</c:otherwise>
						</c:choose> <span id="blackTip" style="color: red;"></span></td>
				</tr>
				<tr>
						<td><label class="lab"><span style="color: red">*</span>证件有效期：</label>
						<input id="idStartDay" name="idStartDay"
						value="<fmt:formatDate value='${carCustomer.idStartDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text70 required" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})"
						style="cursor: pointer" />-<input id="idEndDay"
						name="idEndDay"
						value="<fmt:formatDate value='${carCustomer.idEndDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text70" size="10"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})"
						style="cursor: pointer" /> <input type="checkbox" id="longTerm"
						name="isLongTerm" value="1"/>
						长期</td>
						<td><label class="lab">出生日期：</label> <input
						id="customerBirthday"
						name="customerBirthday"
						type="text" class=" input_text178" size="10"
						readonly="readonly"
						 />
						</td>
				</tr>
				<tr>
					<td><label class="lab"><span class="red">*</span>暂住证：</label> 
					<c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="customerTempPermit" class="required"
								value="${item.value}"
								<c:if test="${item.value==carCustomer.customerTempPermit}">
                    		 checked
                  			</c:if>/>${item.label}
               		 </c:forEach></td>
                
<%-- 					<td><label class="lab"><span class="red">*</span>住房性质：</label>
						 <select name="customerHouseHoldProperty"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_house_nature')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${carCustomer.customerHouseHoldProperty==item.value}">
					      		selected = true
					     	</c:if>>${item.label}</option>
							</c:forEach>
					</select>
						 </td>
					<td><label class="lab">月租金：</label>
						<input type="text" name="jydzzmRentMonth"  maxlength="8"
						value="<fmt:formatNumber value="${carCustomer.jydzzmRentMonth}" pattern="##0"/>" class="numCheck positiveNumCheck"/>
						</td> --%>
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
				
					<td><label class="lab"><span class="red">*</span>初到本市时间：</label>
					<input id="customerFirtArriveYear"
						name="customerFirtArriveYear"
						value="<fmt:formatDate value="${carCustomer.customerFirtArriveYear}" pattern="yyyy-MM-dd"/>" type="text"
						class="Wdate input_text178 required" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab">起始居住时间：</label>
					<input id="customerFirstLivingDay"
						name="customerFirstLivingDay"
						value="<fmt:formatDate value='${carCustomer.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab">供养亲属人数：</label>
						<input type="text" name="customerFamilySupport" class="number" maxlength="2"
						value="${carCustomer.customerFamilySupport}" />
						</td>
				</tr>
				
				<tr>
					<td><label class="lab"><span style="color: red">*</span>电子邮箱：</label> 
					<input name="customerEmail" type="text" id="customerEmail"
						value="${carCustomer.customerEmail}" class="input_text178 required" />
					 <input type="checkbox" id="isEmailModify" name= "isEmailModify"
				 <c:if test="${carCustomerBase.isEmailModify == '1'}"> checked </c:if>
				 value="${carCustomerBase.isEmailModify =='1'?'1':'0'}"
				 />修改 <input type="hidden" id="oldcustomerEmail" value="${carCustomer.customerEmail}"/>
						</td>
					
                      <td><label class="lab"><span style="color: red">*</span>信用额度：</label>
						<input type="text" name="creditLine" class="required numCheck positiveNumCheck"
						value="<fmt:formatNumber value="${carCustomer.creditLine}" pattern="##0"/>"   maxlength="8"/>
					 </td>
					 
					<td><label class="lab"><span class="red">*</span>客户来源：</label>
                		<select id="dictCustomerSource2" name="dictCustomerSource2" class="select180 required">
						<option value="">请选择</option>
						<c:forEach items="${fns:getDictList('jk_cm_src')}" var="item">
					  	<option value="${item.value}"
					  	<c:if test="${carCustomer.dictCustomerSource2==item.value}">
                              selected
                         </c:if>>${item.label}</option>
				     </c:forEach>
				</select></td>
			
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span class="red">*</span>现住址：</label>
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
					</select>-<select id="liveDistrictId" class="select78 required"
						name="customerLiveArea"
						value="${carCustomer.customerLiveArea}">
							<option value="">请选择</option>
					</select>
					<input type="text"
						name="customerAddress"
						value="${carCustomer.customerAddress}"   maxlength="50"   style="width: 250px"
						class="required"  /></td>
					<input type="hidden" id="liveCityIdHidden" value="${carCustomer.customerLiveCity}"/>
					<input type="hidden" id="liveDistrictIdHidden" value="${carCustomer.customerLiveArea}"/> 
					</td>							
				</tr>
				<tr>
					<td colspan="3" ><label class="lab"><span class="red">*</span>户籍地址：</label>
					<c:choose>
						<c:when test="${carCustomer.customerRegisterProvince != null && carCustomer.customerRegisterProvince != ''}">
							<sys:carAddressShow detailAddress="${carCustomer.customerRegisterAddressView}"></sys:carAddressShow>
						   <input type="hidden" name="customerRegisterProvince" value="${carCustomer.customerRegisterProvince}"/>
						   <input type="hidden" name="customerRegisterCity" value="${carCustomer.customerRegisterCity}"/>
						   <input type="hidden" name="customerRegisterArea" value="${carCustomer.customerRegisterArea}"/>
						   <input type="hidden" name="customerRegisterAddress" value="${carCustomer.customerRegisterAddress}"/>
						</c:when>
						<c:otherwise>
							<select id="registerProvince"
								name="customerRegisterProvince" class="select78 required">
									<option value="">请选择</option>
									<c:forEach var="item" items="${provinceList}"
										varStatus="status">
										<option value="${item.areaCode}">
											${item.areaName}</option>
									</c:forEach>
							</select>-<select id="customerRegisterCity"
								name="customerRegisterCity" class="select78 required">
									<option value="">请选择</option>
							</select>-<select id="customerRegisterArea"
								name="customerRegisterArea" class="select78 required"
									<option value="">请选择</option>
							</select>
							<input type="text" maxlength="50" name="customerRegisterAddress" class="required" style="width: 250px"/>
						</c:otherwise>
					</c:choose>
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