<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document)
			.ready(
					function() {
			//获得当前时间
						
						$('#customerNextBtn').bind('click',function(){
							$(this).attr('disabled',true);
							launch.saveApplyInfo('1', 'customerForm','customerNextBtn');	
						});
				    	areaselect.initCity("liveProvinceId", "liveCityId",
										"liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden")
												.val());
						loan.initCity("liveProvinceId", "liveCityId",
						"liveDistrictId"); 
						areaselect.initCity("registerProvince",
										"customerRegisterCity", "customerRegisterArea",
										$("#customerRegisterCityHidden").val(), $(
												"#customerRegisterAreaHidden").val()); 
					    loan.initCity("registerProvince",
								"customerRegisterCity", "customerRegisterArea");
	   					 $('#longTerm')
								.bind(
										'click',
										function() {
											if ($(this).attr('checked') == true
													|| $(this).attr('checked') == 'checked') {
												$('#idEndDay').val('');
												$('#idEndDay').attr('readOnly',true);
												$('#idEndDay').hide();
											} else {
												$('#idEndDay').removeAttr('readOnly');
												$('#idEndDay').show();
											}
		});
	   	$('#inputLoanCustomer').bind('change',function(){
	   		var customerName = $(this).val();
	   		$('#loanCustomerName1').val(customerName);
	   		$('#cuCustomerName1').val(customerName);
	   		$('#loanCustomerName2').val(customerName);
	   		$('#cuCustomerName2').val(customerName);
	   	});
	});
	var msg = "${workItem.bv.message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="javascript:launch.getCustomerInfo('1');">个人资料</a></li>
		<li><a href="javascript:launch.getCustomerInfo('2');">配偶资料</a></li>
		<li><a href="javascript:launch.getCustomerInfo('3');">申请信息</a></li>

		<c:if test="${workItem.bv.oneedition==-1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">自然人保证人</a></li>	
        </c:if>
		<li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
		<li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
		<li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
		<li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
		<li><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">

	    <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
		<input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
		<input type="hidden" value="${workItem.bv.loanCustomer.loanCode}"
			name="loanInfo.loanCode" id="loanCode" /> <input type="hidden"
			value="${workItem.bv.loanCustomer.loanCode}" name="loanCode" /> <input
			type="hidden" value="${workItem.bv.loanCustomer.id}"
			name="loanCustomer.id" id="custId" /> <input type="hidden"
			value="${workItem.bv.loanCustomer.customerCode}"
			name="loanCustomer.customerCode" id="customerCode" /> <input
			type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
			name="customerCode" /> <input type="hidden"
			value="${workItem.bv.customerName}" name="loanInfo.loanCustomerName"
			id="loanCustomerName1" /> <input type="hidden"
			value="${workItem.bv.customerName}" id="cuCustomerName1" name="loanCustomer.customerName" />
		<input type="hidden" value="${workItem.bv.consultId}" name="consultId"
			id="consultId" /> <input type="hidden" value="${workItem.flowCode}"
			name="flowCode"></input> <input type="hidden"
			value="${workItem.flowName}" name="flowName"></input> <input
			type="hidden" value="${workItem.stepName}" name="stepName"></input> <input
			type="hidden" value="${workItem.flowType}" name="flowType"></input>
	</form>
	<c:set var="bv" value="${workItem.bv}" />
	<div id="div1" class="content control-group ">
		<form id="customerForm" method="post">
		    <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
		    <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
			<input type="hidden" value="${workItem.bv.loanCustomer.loanCode}"
				name="loanInfo.loanCode" id="loanCode" /> <input type="hidden"
				value="${workItem.bv.loanCustomer.loanCode}" name="loanCode" /> <input
				type="hidden" value="${workItem.bv.loanCustomer.id}"
				name="loanCustomer.id" id="custId" /> <input type="hidden"
				value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> <input
				type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode" /> <input type="hidden"
				value="${workItem.bv.customerName}" name="loanInfo.loanCustomerName"
				id="loanCustomerName2" /> <input type="hidden"
				value="${workItem.bv.customerName}" id="cuCustomerName2" name="loanCustomer.customerName" />
			<input type="hidden" value="${workItem.bv.consultId}"
				name="consultId" id="consultId" /> <input type="hidden"
				value="${workItem.flowCode}" name="flowCode"></input> <input
				type="hidden" value="${workItem.flowName}" name="flowName"></input>
			<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
			<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
			<input type="hidden" value="${bv.customerLivings.id}"
				name="customerLivings.id" id="custLivingId" />
			<input type="hidden" value="${bv.loanCustomer.customerTelesalesFlag}" name="loanCustomer.customerTelesalesFlag"/>	
			<input type="hidden" value="${bv.loanCustomer.customerTeleSalesSource}" name="loanCustomer.customerTeleSalesSource"/>	
			<input type="hidden" id="liveCityIdHidden" value="${bv.loanCustomer.customerLiveCity}"/>
			<input type="hidden" id="liveDistrictIdHidden" value="${bv.loanCustomer.customerLiveArea}"/>
			<input type="hidden" id="customerRegisterCityHidden" value="${bv.loanCustomer.customerRegisterCity}"/>
			<input type="hidden" id="customerRegisterAreaHidden" value="${bv.loanCustomer.customerRegisterArea}"/>
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td>
					<label class="lab"><span style="color: red">*</span>客户姓名：</label>
						<input type="text"
						value="${bv.customerName}" id="inputLoanCustomer" class="input_text178 required"
						 /></td>
					<td><label class="lab"><span style="color: red">*</span>性别：</label>
						<c:choose>
							<c:when test="${bv.loanCustomer.customerSex!=null}">
							  <span>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" class="required"
										name="loanCustomer.customerSex" value="${item.value}"
										<c:if test="${bv.loanCustomer.customerSex==item.value}">
                                  checked
                                </c:if>>
                           ${item.label}
                         </input>
								</c:forEach>
								</span>
							</c:when>
							<c:otherwise>
							  <span>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" class="required"
										name="loanCustomer.customerSex" value="${item.value}"
										<c:if test="${bv.customerSex==item.value}">
                                  checked
                                </c:if>>
                             ${item.label}
                           </input>
								</c:forEach>
								</span>
							</c:otherwise>
						</c:choose></td>
					<td><label class="lab"><span style="color: red">*</span>婚姻状况：</label>
						<select id="dictMarry" name="loanCustomer.dictMarry"
						value="${bv.loanCustomer.dictMarry}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
							  <c:if test="${item.label!='空'}">
								<option value="${item.value}"
									<c:if test="${bv.loanCustomer.dictMarry==item.value}">
					       			 selected = true
					    			</c:if>>${item.label}
					    		</option>
					    	   </c:if>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
				  <td><label class="lab"><span style="color: red">*</span>手机号码：</label>
						<c:choose>
							<c:when test="${bv.loanCustomer.customerPhoneFirst!=null}">
							<c:if test="${bv.loanCustomer.customerTelesalesFlag!=1}">
								<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst"
									value="${bv.loanCustomer.customerPhoneFirst}"
									disabled="disabled" class="input_text178 required" />
								<input type="hidden" name="loanCustomer.customerPhoneFirst"
									value="${bv.loanCustomer.customerPhoneFirst}" />
							</c:if>		
									<!-- 如果是电销则手机号码在此处可以修改 -->
							<c:if test="${bv.loanCustomer.customerTelesalesFlag==1}">
								<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst"
									value="${bv.loanCustomer.customerPhoneFirst}"
								     class="input_text178 required isMobile mainBorrowMobileDiff2" />
							</c:if>
							</c:when>
							<c:otherwise>
							<!-- 如果是电销则手机号码在此处可以修改 -->
							<c:if test="${bv.loanCustomer.customerTelesalesFlag==1}">
								<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst"
									 value="${bv.loanCustomer.customerPhoneFirst}" 
									 class="required isMobile mainBorrowMobileDiff2"/>
							</c:if>
							<c:if test="${bv.loanCustomer.customerTelesalesFlag!=1}">
								<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst"
									value="${bv.customerMobilePhone}" disabled="disabled"
									class="input_text178 required" />
								<input type="hidden" name="loanCustomer.customerPhoneFirst"
									value="${bv.customerMobilePhone}" />
							</c:if>
							
							</c:otherwise>
						</c:choose>
				  </td>
				  
				  
				  
					<td><label class="lab">手机号码2：</label> <input
						name="loanCustomer.customerPhoneSecond" type="text"
						value="${bv.loanCustomer.customerPhoneSecond}"
						class="input_text178 mainBorrowMobileDiff1 mainBorrowMobileDiff2"  readonly ="readonly"/></td>
                   <td>
			    	<label class="lab">固定电话：</label> <c:choose>
							<c:when test="${bv.loanCustomer.customerTel!=null}">
								<input name="loanCustomer.customerTel" type="text"
									value='${bv.loanCustomer.customerTel}' class="input_text178 isTel" />
							</c:when>
							<c:when test="${bv.areaNo!=null && bv.areaNo!='' && bv.telephoneNo!=null && bv.telephoneNo!=''}">
								<input name="loanCustomer.customerTel" type="text"
									value='${bv.areaNo}-${bv.telephoneNo}' class="input_text178 isTel" />
							</c:when>
							<c:otherwise>
							    <input name="loanCustomer.customerTel" type="text" class="input_text178 isTel"/>
							</c:otherwise>
						</c:choose>
				
				    </td>
				</tr>

				<tr>
					<td><label class="lab"><span style="color: red">*</span>证件类型：</label>
						<select id="cardType" name="loanCustomer.dictCertType"
						value="${bv.dictCertType}" class="select180 required"
						disabled="disabled">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('com_certificate_type')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${bv.dictCertType==item.value}">
					      selected = true
					     </c:if>>${item.label}</option>
							</c:forEach>
					</select> <input type="hidden" name="loanCustomer.dictCertType"
						value="${bv.dictCertType}" /></td>
					<td><label class="lab"><span style="color: red">*</span>证件号码：</label>
						<c:choose>
							<c:when test="${bv.loanCustomer.customerCertNum!=null}">
								<input name="loanCustomer.customerCertNum" disabled="disabled"
									id="customerCertNum" type="text"
									value="${bv.loanCustomer.customerCertNum}"
									class="input_text178 required" />
								<input name="loanCustomer.customerCertNum" type="hidden"
									value="${bv.loanCustomer.customerCertNum}" />
							</c:when>
							<c:otherwise>
								<input name="loanCustomer.customerCertNum" id="customerCertNum"
									disabled="disabled" type="text" value="${bv.mateCertNum}"
									class="input_text178 required" />
								<input name="loanCustomer.customerCertNum" type="hidden"
									value="${bv.mateCertNum}" />
							</c:otherwise>
						</c:choose> <span id="blackTip" style="color: red;"></span></td>
					<td><label class="lab"><span style="color: red">*</span>证件有效期：</label>
					  <span>
					   <c:choose>
					     <c:when test="${bv.loanCustomer.idStartDay!=null}">
					       <input id="idStartDay" name="loanCustomer.idStartDay"
							value="<fmt:formatDate value='${bv.loanCustomer.idStartDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text70" size="10"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})"
							style="cursor: pointer" /> -<input id="idEndDay"
							name="loanCustomer.idEndDay"
							value="<fmt:formatDate value='${bv.loanCustomer.idEndDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text70" size="10"
							<c:if test="${bv.loanCustomer.idStartDay!=null && bv.loanCustomer.idEndDay==null}"> 
								  readOnly=true style='display:none'
						    </c:if>
						     onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})"
						     style="cursor: pointer" /> <input type="checkbox" id="longTerm"
						     name="longTerm" value="1"
						     <c:if test="${bv.loanCustomer.idStartDay!=null && bv.loanCustomer.idEndDay==null}"> 
						         checked=true 
						     </c:if>  />
						          长期
					   </c:when>
					   <c:otherwise>
					        <input id="idStartDay" name="loanCustomer.idStartDay"
								value="<fmt:formatDate value='${bv.idStartDay}' pattern="yyyy-MM-dd"/>"
								type="text" class="Wdate input_text70" size="10"
								onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})"
								style="cursor: pointer" /> -<input id="idEndDay"
									name="loanCustomer.idEndDay"
									value="<fmt:formatDate value='${bv.idEndDay}' pattern="yyyy-MM-dd"/>"
									type="text" class="Wdate input_text70" size="10"
									<c:if test="${bv.idStartDay!=null && bv.idEndDay==null}"> 
									  readOnly=true style='display:none' 
									</c:if>
									onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})"
						style="cursor: pointer" /> <input type="checkbox" id="longTerm"
						name="longTerm" value="1"
						<c:if test="${bv.idStartDay!=null && bv.idEndDay==null}"> 
						     checked=true 
						</c:if>  />
						长期
					   </c:otherwise>
					</c:choose>
					</span>
				  </td>
				</tr>
	     		<tr>
					<td><label class="lab"><span style="color: red">*</span>学历：</label>
						<select id="dictEducation" name="loanCustomer.dictEducation"
						value="${bv.loanCustomer.dictEducation}"
						class="select2-container select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}"
									<c:if test="${bv.loanCustomer.dictEducation==curEdu.value}">
					      selected = true
					     </c:if>>${curEdu.label}</option>
							</c:forEach>
					</select></td>
					<td><label class="lab"><span style="color: red">*</span>住房性质：</label>
						<select name="customerLivings.customerHouseHoldProperty"
						value="${bv.customerLivings.customerHouseHoldProperty}"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_house_nature')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${item.value==bv.customerLivings.customerHouseHoldProperty}">
					      selected=true 
					    </c:if>>${item.label}</option>
							</c:forEach>
					</select></td>
					<td><label class="lab">家人是否知悉：</label> <c:forEach
							items="${fns:getDictList('jk_yes_or_no')}" var="item">
							<input type="radio" name="loanCustomer.contactIsKnow"
								value="${item.value}"
								<c:if test="${item.value==bv.loanCustomer.contactIsKnow}">
                     checked='true'
                  	</c:if> />${item.label}
               	 </c:forEach></td>
				</tr>
				<tr>

					<td><label class="lab">有无子女：</label> <c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="loanCustomer.customerHaveChildren"
								value="${item.value}"
								<c:if test="${item.value==bv.loanCustomer.customerHaveChildren}">
                     checked='true'
                  </c:if> />${item.label}
                </c:forEach></td>
                <td><label class="lab">毕业日期：</label> <input
						id="customerGraduationDay"
						name="loanCustomer.customerGraduationDay"
						value="<fmt:formatDate value='${bv.loanCustomer.customerGraduationDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab">初到本市时间：</label>
						<input id="firstArriveYear"
						name="customerLivings.customerFirtArriveYear"
						value="${bv.customerLivings.customerFirtArriveYear}" type="text"
						class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
					</td>

				</tr>
				<tr>
        			<td><label class="lab">起始居住时间：</label> <input
						id="customerFirtLivingDay"
						name="customerLivings.customerFirtLivingDay"
						value="<fmt:formatDate value='${bv.customerLivings.customerFirtLivingDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab"><span style="color: red">*</span>客户类型：</label>
						<select id="dictCustomerDiff" name="loanCustomer.dictCustomerDiff"
						class="select180 required"
						value="${bv.loanCustomer.dictCustomerDiff}">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_customer_diff')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${bv.loanCustomer.dictCustomerDiff==item.value}">
					       selected=true 
					    </c:if>>${item.label}</option>
							</c:forEach>
					</select></td>
					<td>
					<label class="lab" >由何处得知我公司：</label> <select
						id="dictCustomerSource" class="select180"
						name="loanCustomer.dictCustomerSource"
						value="${bv.loanCustomer.dictCustomerSource}">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_cm_src')}" var="item">
								<option value="${item.value}"
									<c:if test="${item.value==bv.loanCustomer.dictCustomerSource}">
                        selected=true 
                         </c:if>>${item.label}</option>
							</c:forEach>
					</select>						
					</td>
				</tr>
				<tr>
					<td><label class="lab"><span style="color: red">*</span>电子邮箱：</label> <input
						name="loanCustomer.customerEmail" type="text"
						value="${bv.loanCustomer.customerEmail}" class="input_text178 required" /></td>
					<td><%-- <label class="lab">暂住证：</label> <!--暂住证没有  --> <c:forEach
							items="${fns:getDictList('sex')}" var="item">
							<input type="radio"
								name="customerLivings.customertmpResidentialPermit"
								 <c:if test="${item.value==applyInfoEx.customerLivings.customertmpResidentialPermit}"> checked='true' </c:if>
                      value="${item.value}" />${item.label}
                      </c:forEach> --%></td>
                      <td>
				      </td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span style="color: red">*</span>户籍地址：</label>
				    	<select id="registerProvince"
						name="loanCustomer.customerRegisterProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${bv.provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${bv.loanCustomer.customerRegisterProvince==item.areaCode}">
							selected = true 
		                 </c:if>>
									${item.areaName}</option>
							</c:forEach>
					</select>-<select id="customerRegisterCity"
						name="loanCustomer.customerRegisterCity" class="select78 required"
						value="${bv.loanCustomer.customerRegisterCity}">
							<option value="">请选择</option>
					</select>-<select id="customerRegisterArea"
						name="loanCustomer.customerRegisterArea" class="select78 required
						"
						value="${bv.loanCustomer.customerRegisterArea}">
							<option value="">请选择</option>
					</select>
					<input type="text"
						name="loanCustomer.customerRegisterAddress" class="required"
						style="width: 250px"
						value="${bv.loanCustomer.customerRegisterAddress}" maxlength="100"/></td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span style="color: red">*</span>现住址：</label>
					   <select class="select78 required" id="liveProvinceId"
						name="loanCustomer.customerLiveProvince">
							<option value="">请选择</option>
							<c:forEach var="item" items="${bv.provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${bv.loanCustomer.customerLiveProvince==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>-<select class="select78 required" id="liveCityId"
						name="loanCustomer.customerLiveCity"
						value="${bv.loanCustomer.customerLiveCity}">
							<option value="">请选择</option>
					</select>-<select id="liveDistrictId" class="select78 required"
						name="loanCustomer.customerLiveArea"
						value="${bv.loanCustomer.customerLiveArea}">
							<option value="">请选择</option>
					</select>
					<input type="text"
						name="loanCustomer.customerAddress"
						value="${bv.loanCustomer.customerAddress}" style="width: 250px"
						class="required" maxlength="100"/></td>
				</tr>
			</table>
			<div class="tright mr10 mb5">
				<input type="submit" class="btn btn-primary" id="customerNextBtn"
					value="下一步">
			</div>
		</form>
	</div>
</body>
</html>