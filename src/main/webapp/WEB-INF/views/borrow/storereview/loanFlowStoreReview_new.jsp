<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<jsp:include page="../apply/applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<%-- <script type="text/javascript" src="${context}/js/consult/loanLaunch_new.js?version=2"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src='${context}/js/common.js'></script> --%>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
		function() {
			
			$('#customerNextBtn').bind('click',function(){
				$(this).attr('disabled',true);
				launch.updateApplyInfo('1', 'customerForm','customerNextBtn');	
			});
			
	    	areaselect.initCity("liveProvinceId", "liveCityId","liveDistrictId", $("#liveCityIdHidden").val(), $("#liveDistrictIdHidden").val());
			loan.initCity("liveProvinceId", "liveCityId","liveDistrictId"); 
			
			areaselect.initCity("registerProvince","customerRegisterCity", "customerRegisterArea",$("#customerRegisterCityHidden").val(), $("#customerRegisterAreaHidden").val()); 
			loan.initCity("registerProvince","customerRegisterCity", "customerRegisterArea");
			
 			$('#longTerm').bind('click',function() {
				if ($(this).attr('checked') == true || $(this).attr('checked') == 'checked') {
					$('#idEndDay').val('');
					$('#idEndDay').attr('readOnly',true);
					$('#idEndDay').hide();
				} else {
					$('#idEndDay').removeAttr('readOnly');
					$('#idEndDay').show();
				}
			});
 			
 			//验证申请人分类为农户人群（邮箱和户籍地址非必填）
		   	var productType = $("#customerProductType").val();
		   	if(productType == 'A021'){
		   		$(".red").hide();
				$("#customerEmail").removeClass("required");
				$("#registerProvince").removeClass("required");
				$("#customerRegisterCity").removeClass("required");
				$("#customerRegisterArea").removeClass("required");
				$("#customerRegisterAddress").removeClass("required");
		   	}else{
		   		$(".red").show();
				$("#customerEmail").addClass("required");
				$("#registerProvince").addClass("required");
				$("#customerRegisterCity").addClass("required");
				$("#customerRegisterArea").addClass("required");
				$("#customerRegisterAddress").addClass("required");
		   	}
	});
	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	};
	//住宅类别下拉框值改变时调用
	function customerHouseHoldPropertyNewChange(obj){
		var value=obj.value;
		if(value==7){ //选择其他时显示文本框
			$("#customerHouseHoldPropertyNewOther").show();
		}else{
			$("#customerHouseHoldPropertyNewOther").hide();
		}
	}
	//由何处了解到我公司复选框改变时调用
	function dictCustomerSourceNewChange(obj){
		var value=obj.value;
		if($("input[name='loanCustomer.dictCustomerSourceNewStr'][value='6']").is(':checked')){ //选择其他时显示文本框
			$("#dictCustomerSourceNewOther").show();
		}else{
			$("#dictCustomerSourceNewOther").hide();
		}
	}
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:launch.getCustomerInfo_storeView('1');">个人基本信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('2');">借款意愿</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('3');">工作信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('4');">联系人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('5');">自然人保证人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('6');">房产信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('8');">证件信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('9');">银行卡信息</a></li>
		<li style="width:auto;float:right;">
			<jsp:include page="./tright.jsp"/>
		</li>
	</ul>
	<form id="custInfoForm" method="post">
		<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}" id="customerCode">
		<input type="hidden" name="consultId" value="${workItem.bv.consultId}" id="consultId">
		<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}" id="loanCode" >
  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
 		<input type="hidden" name="token" value="${workItem.token}">
 		<input type="hidden" name="stepName" value="${workItem.stepName }">
 		<input type="hidden" name="flowName" value="${workItem.flowName }">
		<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
 		
 		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
 		<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
 		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
	</form>
	<c:set var="bv" value="${workItem.bv}" />
	<div id="div1" class="content control-group">
		<form id="customerForm" method="post">
			<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
			<input type="hidden" name="customerCode" value="${bv.customerCode}">
			<input type="hidden" value="${workItem.bv.customerCode}" id="bvCustomerCode"/>
			<input type="hidden" name="consultId" value="${bv.consultId}">
			<input type="hidden" name="loanCode" value="${bv.loanCode}">
	  		<input type="hidden" name="applyId" value="${bv.applyId}">
  			<input type="hidden" name="preResponse" value="${bv.preResponse }">
	  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
  			<input type="hidden" name="token" value="${workItem.token}">
  			<input type="hidden" name="stepName" value="${workItem.stepName }">
  			<input type="hidden" name="flowName" value="${workItem.flowName }">
			<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
			<input type="hidden" name="loanCustomer.id" value="${bv.loanCustomer.id}">
  			<input type="hidden" name="loanCustomer.loanCode" value="${bv.loanCustomer.loanCode}"/>
			<input type="hidden" name="customerLivings.id" value="${bv.customerLivings.id}">
			<input type="hidden" name="customerLivings.loanCode" value="${bv.loanCustomer.loanCode}">
			<input type="hidden" id="liveCityIdHidden" value="${bv.loanCustomer.customerLiveCity}"/>
			<input type="hidden" id="liveDistrictIdHidden" value="${bv.loanCustomer.customerLiveArea}"/>
			<input type="hidden" id="customerRegisterCityHidden" value="${bv.loanCustomer.customerRegisterCity}"/>
			<input type="hidden" id="customerRegisterAreaHidden" value="${bv.loanCustomer.customerRegisterArea}"/>
			<input type="hidden" id="sendEmailFlag" value="${bv.loanCustomer.customerEmail}"/>
			<input type="hidden" id="oldEmailFlag" value="${bv.loanCustomer.customerEmail}"/>
			<input type="hidden" value="${workItem.bv.customerName}" id="cuCustomerName1"/>
  			<input type="hidden" name="method" value="customer">
  			<input type="hidden" value="${workItem.bv.loanInfo.productType}" id="customerProductType"/>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<label class="lab"><span style="color: red">*</span>姓名：</label>
						<input type="text" value="${bv.loanCustomer.customerName}"  name="loanCustomer.customerName" id="loanCustomerName" class="input_text178 required"/>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>身份证号码：</label>
						<c:choose>
							<c:when test="${bv.loanCustomer.customerCertNum!=null}">
								<input name="loanCustomer.customerCertNum" disabled="disabled" id="customerCertNum" type="text" value="${bv.loanCustomer.customerCertNum}" class="input_text178 required" />
								<input name="loanCustomer.customerCertNum" type="hidden" value="${bv.loanCustomer.customerCertNum}" />
							</c:when>
							<c:otherwise>
								<input name="loanCustomer.customerCertNum" id="customerCertNum" disabled="disabled" type="text" value="${bv.mateCertNum}" class="input_text178 required" />
								<input name="loanCustomer.customerCertNum" type="hidden" value="${bv.mateCertNum}" />
							</c:otherwise>
						</c:choose> 
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>证件有效期：</label>
					  	<span>
						    <c:choose>
							    <c:when test="${bv.loanCustomer.idStartDay!=null}">
							    	<input id="idStartDay" name="loanCustomer.idStartDay" value="<fmt:formatDate value='${bv.loanCustomer.idStartDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" />
							    	-
							    	<input id="idEndDay" name="loanCustomer.idEndDay" value="<fmt:formatDate value='${bv.loanCustomer.idEndDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10"
										<c:if test="${bv.loanCustomer.idStartDay!=null && bv.loanCustomer.idEndDay==null}">readOnly=true style='display:none'</c:if>
								    	onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" />
								    <input type="checkbox" id="longTerm" name="longTerm" value="1" <c:if test="${bv.loanCustomer.idStartDay!=null && bv.loanCustomer.idEndDay==null}">checked=true</c:if>  />
								        长期
							   </c:when>
							   <c:otherwise>
							   		<input id="idStartDay" name="loanCustomer.idStartDay" value="<fmt:formatDate value='${bv.idStartDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" /> 
							   		-
							   		<input id="idEndDay" name="loanCustomer.idEndDay" value="<fmt:formatDate value='${bv.idEndDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text70" size="10"
										<c:if test="${bv.idStartDay!=null && bv.idEndDay==null}">readOnly=true style='display:none'</c:if>
										onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" /> 
									<input type="checkbox" id="longTerm" name="longTerm" value="1" <c:if test="${bv.idStartDay!=null && bv.idEndDay==null}">checked=true</c:if>/>
									长期
							   </c:otherwise>
							</c:choose>
						</span>
				  </td>
			  </tr>
			  <tr>
			  	  <td>
					<label class="lab"><span style="color: red">*</span>性别：</label>
					<c:choose>
						<c:when test="${bv.loanCustomer.customerSex!=null}">
						  <span>
							<c:forEach items="${fns:getNewDictList('jk_sex')}" var="item">
								<input type="radio" class="required" name="loanCustomer.customerSex" value="${item.value}" <c:if test="${bv.loanCustomer.customerSex==item.value}">checked</c:if>>${item.label}</input>
							</c:forEach>
						  </span>
						</c:when>
						<c:otherwise>
						  <span>
							<c:forEach items="${fns:getNewDictList('jk_sex')}" var="item">
								<input type="radio" class="required" name="loanCustomer.customerSex" value="${item.value}" <c:if test="${bv.customerSex==item.value}">checked</c:if>>${item.label}</input>
							</c:forEach>
						  </span>
						</c:otherwise>
					</c:choose>
				  </td>
				  <td>
				  	<label class="lab"><span style="color: red">*</span>申请人分类：</label>
				  	<select id="dictCustomerDiff" name="loanCustomer.dictCustomerDiff" class="select180 required" value="${bv.loanCustomer.dictCustomerDiff}" >
						<option value="">请选择</option>
						<c:forEach items="${fns:getNewDictList('jk_customer_diff')}" var="item">
							<option value="${item.value}" <c:if test="${bv.loanCustomer.dictCustomerDiff==item.value}">selected=true</c:if>>${item.label}</option>
						</c:forEach>
					</select>
				  </td>
				  <td>
				  	<label class="lab"><span style="color: red">*</span>教育程度：</label>
				  	<select id="dictEducation" name="loanCustomer.dictEducation" value="${bv.loanCustomer.dictEducation}" class="select2-container select180 required">
				  		<option value="">请选择</option>
				  		<c:forEach items="${fns:getNewDictList('jk_degree')}" var="curEdu">
				  			<option value="${curEdu.value}" <c:if test="${bv.loanCustomer.dictEducation==curEdu.value}">selected = true</c:if>>${curEdu.label}</option>
				  		</c:forEach>
				  	</select>
				  </td>
				</tr>
				<tr>
					<td>
						<label class="lab"><span style="color: red">*</span>婚姻状况：</label>
					  	<select id="dictMarry" name="loanCustomer.dictMarry" value="${bv.loanCustomer.dictMarry}" class="select180 required">
					  		<option value="">请选择</option>
					  		<c:forEach items="${fns:getNewDictList('jk_marriage')}" var="item">
					  			<c:if test="${item.label!='空'}">
					  				<option value="${item.value}" <c:if test="${bv.loanCustomer.dictMarry==item.value}">selected = true</c:if>>${item.label}</option>
					  			</c:if>
					  		</c:forEach>
					  	</select>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>子女人数(人)：</label>
						<span><input type="text" value="${bv.loanCustomer.customerChildrenCount}" name="loanCustomer.customerChildrenCount" id="customerChildrenCount" class="input_text178 required digits" maxlength="3"/><span>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>供养人数(人)：</label>
						<input type="text" value="${bv.loanCustomer.customerFamilySupport}" name="loanCustomer.customerFamilySupport" id="customerFamilySupport" class="input_text178 required digits"  maxlength="3"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab"><span style="color: red">*</span>个人年收入(万元)：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.loanCustomer.personalYearIncome}' pattern="##0.00"/>" name="loanCustomer.personalYearIncome" id="personalYearIncome" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>家庭月收入(元)：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.loanCustomer.homeMonthIncome}' pattern="##0.00"/>" name="loanCustomer.homeMonthIncome" id="homeMonthIncome" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>家庭月支出(元)：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.loanCustomer.homeMonthPay}' pattern="##0.00"/>" name="loanCustomer.homeMonthPay" id="homeMonthPay" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab"><span style="color: red">*</span>家庭总负债(万元)：</label>
						<input type="text" value="<fmt:formatNumber value='${bv.loanCustomer.homeTotalDebt}' pattern="##0.00"/>" name="loanCustomer.homeTotalDebt" id="homeTotalDebt" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>初来本市时间：</label>
						<input id="firstArriveYear" name="customerLivings.customerFirtArriveYear" value="${bv.customerLivings.customerFirtArriveYear}" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>现住宅起始居住日期：</label>
						<input id="customerFirtLivingDay" name="customerLivings.customerFirtLivingDay" value="<fmt:formatDate value='${bv.customerLivings.customerFirtLivingDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab"><span style="color: red"></span>宅电：</label>
						<c:choose>
							<c:when test="${bv.loanCustomer.customerTel!=null}">
								<input name="loanCustomer.customerTel" type="text" value='${bv.loanCustomer.customerTel}' class="input_text178 isTel" />
							</c:when>
							<c:when test="${bv.areaNo!=null && bv.areaNo!='' && bv.telephoneNo!=null && bv.telephoneNo!='' && bv.isBorrow!='1'}">
								<input name="loanCustomer.customerTel" type="text" value='${bv.areaNo}-${bv.telephoneNo}' class="input_text178 isTel" />
							</c:when>
							<c:otherwise>
							    <input name="loanCustomer.customerTel" type="text" class="input_text178 isTel"/>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
					  	<label class="lab"><span style="color: red">*</span>住宅类别：</label>
					  	<select name="customerLivings.customerHouseHoldProperty" value="${bv.customerLivings.customerHouseHoldProperty}" onchange="customerHouseHoldPropertyNewChange(this);" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_house_nature')}" var="item">
								<option value="${item.value}" <c:if test="${item.value==bv.customerLivings.customerHouseHoldProperty}">selected=true </c:if>>${item.label}</option>
							</c:forEach>
						</select>
						<input type="text" value="${bv.customerLivings.customerHouseHoldPropertyNewOther}" name="customerLivings.customerHouseHoldPropertyNewOther" id="customerHouseHoldPropertyNewOther" class="input_text178 required" maxlength="100"
							 style="width:100px;<c:if test="${bv.customerLivings.customerHouseHoldProperty ne 7}">display:none</c:if>"/>
					</td>
					<td>
						<label class="lab"><span style="color: red">*</span>户籍性质：</label>
						<select name="loanCustomer.registerProperty" value="${bv.loanCustomer.registerProperty}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getNewDictList('jk_register_property')}" var="item">
								<option value="${item.value}" <c:if test="${item.value==bv.loanCustomer.registerProperty}">selected=true </c:if>>${item.label}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
					  	<label class="lab"><span style="color: red">*</span>常用手机号：</label>
					  	<c:choose>
					  		<c:when test="${bv.loanCustomer.customerPhoneFirst!=null}">
								<c:if test="${bv.loanCustomer.customerTelesalesFlag!=1}">
									<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst" value="${bv.loanCustomer.customerPhoneFirst}" class="input_text178 required isMobile mainBorrowMobileDiff2" />
								</c:if>		
								<c:if test="${bv.loanCustomer.customerTelesalesFlag==1}">
									<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst" value="${bv.loanCustomer.customerPhoneFirst}" class="input_text178 required isMobile mainBorrowMobileDiff2" />
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${bv.loanCustomer.customerTelesalesFlag==1}">
									<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst" value="${bv.loanCustomer.customerPhoneFirst}" class="input_text178 required isMobile mainBorrowMobileDiff2"/>
								</c:if>
								<c:if test="${bv.loanCustomer.customerTelesalesFlag!=1}">
									<input type="text" id="customerPhoneFirst" name="loanCustomer.customerPhoneFirst" value="${bv.customerMobilePhone}" class="input_text178 required isMobile mainBorrowMobileDiff2" />
								</c:if>
							</c:otherwise>
					  	</c:choose>
					</td>
					 <td>
						<label class="lab"><span style="color: red" class="red">*</span>邮箱：</label> 
						<input id="customerEmail" name="loanCustomer.customerEmail" type="text" value="${bv.loanCustomer.customerEmail}" class="input_text178 required" />
						<input type="button" value="发送邮箱验证" onclick="sendtoemail(this)" id="emailBtn"/>
						<input name = "emailvalidator" id="emailvalidator" width="30px" style= "background-color:transparent;border:none;"></input>
					</td> 
					<td class="loanCustomer_issuingAuthority" style="display:none;">
						<label class="lab"><span style="color: red">*</span>签发机关：</label>
						<input name="loanCustomer.issuingAuthority" type="text" value="${bv.loanCustomer.issuingAuthority}" class="input_text178 required" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">社保卡号：</label>
						<input type="text" value="${bv.loanCustomer.socialSecurityNumber}" name="loanCustomer.socialSecurityNumber" id="socialSecurityNumber" class="input_text178 isSocial" maxlength="20"/>
					</td>
					<td>
						<label class="lab">社保密码：</label>
						<input type="text" value="${bv.loanCustomer.socialSecurityPassword}" name="loanCustomer.socialSecurityPassword" id="socialSecurityPassword" class="input_text178" maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">QQ：</label> 
						<input name="loanCustomer.customerQq" type="text" value="${bv.loanCustomer.customerQq}" class="input_text178 digits" maxlength="15"/>
					</td>
					<td>
						<label class="lab">微博：</label> 
						<input name="loanCustomer.customerWeibo" type="text" value="${bv.loanCustomer.customerWeibo}" class="input_text178" maxlength="30"/>
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">征信用户名：</label> 
						<input name="loanCustomer.creditUsername" type="text" value="${bv.loanCustomer.creditUsername}" class="input_text178" maxlength="20"/>
					</td>
					<td>
						<label class="lab">密码：</label> 
						<input name="loanCustomer.creditPassword" type="text" value="${bv.loanCustomer.creditPassword}" class="input_text178" maxlength="20"/>
					</td>
					<td>
						<label class="lab">身份验证码：</label> 
						<input name="loanCustomer.creditAuthCode" type="text" value="${bv.loanCustomer.creditAuthCode}" class="input_text178" maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label class="lab"><span style="color: red">*</span>现居住地址：</label>
					  	<select class="select78 required" id="liveProvinceId" name="loanCustomer.customerLiveProvince">
							<option value="">请选择</option>
							<c:forEach var="item" items="${bv.provinceList}" varStatus="status">
								<option value="${item.areaCode}" <c:if test="${bv.loanCustomer.customerLiveProvince==item.areaCode}">selected = true</c:if>>${item.areaName}</option>
							</c:forEach>
						</select>
						-
						<select class="select78 required" id="liveCityId" name="loanCustomer.customerLiveCity" value="${bv.loanCustomer.customerLiveCity}">
							<option value="">请选择</option>
						</select>
						-
						<select id="liveDistrictId" class="select78 required" name="loanCustomer.customerLiveArea" value="${bv.loanCustomer.customerLiveArea}">
							<option value="">请选择</option>
						</select>
						<input type="text" name="loanCustomer.customerAddress" value="${bv.loanCustomer.customerAddress}" style="width: 250px" class="required" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<label class="lab"><span style="color: red" class="red">*</span>户籍地址：</label>
				    	<select id="registerProvince" name="loanCustomer.customerRegisterProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${bv.provinceList}" varStatus="status">
								<option value="${item.areaCode}" <c:if test="${bv.loanCustomer.customerRegisterProvince==item.areaCode}">selected = true </c:if>>${item.areaName}</option>
							</c:forEach>
						</select>
						-
						<select id="customerRegisterCity" name="loanCustomer.customerRegisterCity" class="select78 required" value="${bv.loanCustomer.customerRegisterCity}">
							<option value="">请选择</option>
						</select>
						-
						<select id="customerRegisterArea" name="loanCustomer.customerRegisterArea" class="select78 required" value="${bv.loanCustomer.customerRegisterArea}">
							<option value="">请选择</option>
						</select>
						<input type="text" id="customerRegisterAddress" name="loanCustomer.customerRegisterAddress" class="required" style="width: 250px" value="${bv.loanCustomer.customerRegisterAddress}" maxlength="100"/>
					</td>
				</tr>
				<tr>
					<td colspan="3">
					  	<label class="lab"><span style="color: red">*</span>从何处了解到我公司：</label> 
					  	<span>
					  		<c:forEach items="${fns:getNewDictList('jk_cm_src')}" var="item">
					  			<!-- 不显示其他选项 -->
					  			<c:if test="${item.value ne 6}">
					  				<input type="checkbox" class="required" name="loanCustomer.dictCustomerSourceNewStr" value="${item.value}" <c:if test="${fn:contains(bv.loanCustomer.dictCustomerSourceNew,item.value)}">checked</c:if> onchange="dictCustomerSourceNewChange(this);">${item.label}</input>
					  			</c:if>
					  		</c:forEach>
					  		<!-- 隐藏备注框
						  	<input type="text" value="${bv.loanCustomer.dictCustomerSourceNewOther}" name="loanCustomer.dictCustomerSourceNewOther" id="dictCustomerSourceNewOther" class="input_text178 required" maxlength="100" 
						  			style="width:100px;<c:if test="${!fn:contains(bv.loanCustomer.dictCustomerSourceNew,'6')}">display:none</c:if>"/>
						  	-->
					  	</span>
					</td>
				</tr>
			</table>
			<div class="tright mr10 mb5">
				<input type="submit" class="btn btn-primary" id="customerNextBtn" value="保存"/>
			</div>
		</form>
	</div>
</body>
</html>