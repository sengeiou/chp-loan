<%@page import="com.creditharmony.bpm.frame.view.WorkItemView"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%
	request.getSession().setAttribute("flowProperties", ((WorkItemView)request.getAttribute("workItem")).getFlowProperties());
%>
<html>
<head>
<!-- 个人资料 -->
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<script type="text/javascript">
//主借人和共借人的身份证
$(function(){
		var message='${message}';
	       if(message!=null && message!=''){
	    	   art.dialog.alert(message);
	       }
	       var sex='${sex}';
	       if(sex!=null && sex!=''){
	    	   art.dialog.alert(sex);
	       }
	   <c:if test="${not empty workItem.bv.isLongTerm}">
			$('#longTerm').prop('checked',true);
			$('#idEndDay').val('');
		$('#idEndDay').prop('disabled',true);
	    </c:if>
	});

var carLoanCustomer = {};

jQuery.validator.addMethod("realName2", function(value, element) {
    return this.optional(element) || /^[\u4e00-\u9fa5\·]{2,30}$/.test(value);
}, "姓名只能为2-10个汉字或·号");


//手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号有误");
//只能输入中文
jQuery.validator.addMethod(
		"chinese",function(b,a){
			return this.optional(a)||/^[\u4e00-\u9fa5]*$/.test(b)
		},"只能输入汉字");
//电话校验
jQuery.validator.addMethod("isTel", function(value, element) {
	var telRegx =/^(\d{4}|\d{3})-(\d{7,8})$/;
    var result=true;
 	if(value!=null && value!=''){
 		 if(!telRegx.test(value)){
 			result = false;
         }
	}
    return this.optional(element) || (result);
}, "号码格式：XXX(X)-XXXXXXX(X)");

carLoanCustomer.initBirthday = function(customerCertNum){
	var birthdayNum = '';
	if(customerCertNum !='' && customerCertNum != null){
		birthdayNum = customerCertNum.substring(6,10) + "-"+customerCertNum.substring(10,12) + "-" + customerCertNum.substring(12,14)
	}
	$("#customerBirthday").val(birthdayNum);
}
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
carLoanCustomer.initJydzzmRentMonth = function(houseValue){
	changeHouse(houseValue);
}
	$(document).ready(
					function() {
						
						if($('#customerEmail').val() == null || $('#customerEmail').val() == ""){
							$('#customerEmail').removeAttr("readonly");
						}
						$('#isRareword')
						.bind('click',
								function() {
									if ($(this).prop('checked') == true
											|| $(this).prop('checked') == 'checked') {
										$('#bankAccountName').removeAttr("readonly");
										$('#bankAccountName').val("");
										$("#isRareword").val("1");
									} else {
										var phone = $("#oldcustomerMobilePhone").val();
										$('#bankAccountName').attr("readonly","readonly");
										$('#bankAccountName').val(phone);
										$("#isRareword").val("0");
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
										$("#isEmailModify").val("1");
									} else {
										var email = $("#oldcustomerEmail").val();
										$('#customerEmail').attr("readonly","readonly");
										$('#customerEmail').val(email);
										$("#isEmailModify").val("0");
									}
								});
					 	//$('#isEmailModify').trigger("click");
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
						 // 初始化月租金输入框是否显示 
						  carLoanCustomer.initJydzzmRentMonth($("#customerHouseHoldProperty").attr("selected","selected").val());
						 
						 // 住房性质变化时  控制月租金输入框是否显示 
						 $('#customerHouseHoldProperty').bind('change',function(){
								  changeHouse($("#customerHouseHoldProperty").attr("selected","selected").val());
						 }); 
						loan.initCity("liveProvinceId", "liveCityId",
								"liveDistrictId");
						loan.initCity("registerProvince",
								"customerRegisterCity", "customerRegisterArea");
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
						//验证JS
						$("#carCustomer").validate({
							onclick: true, 
							rules:{
								customerEmail:{
									email:"/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/"
								},
								customerMobilePhone:{
									isMobile:true
								},
								customerCertNum:{
									card:true
								},
							},
							messages : {
								customerEmail:{
									email:"请输入正确邮箱格式"
								},
								customerMobilePhone:{
									isMobile:"请正确填写您的手机号码"
								}
							},
							errorPlacement : function(error, element) {
								if(element.attr("type")=="radio")
								{
									error.appendTo($("#"+element.attr("name")+"Span"));
								}else{
									error.insertAfter(element);
								}
							}
						});
						// 客户放弃		
						$("#giveUpBtn").bind('click',function(){
							var url = ctx+"/car/carApplyTask/giveUp?loanCode="+ $("#loanCode").val();					
							art.dialog.confirm("是否客户放弃?",function(){
								$("#frameFlowForm").attr('action',url);
								$("#frameFlowForm").submit();
							});
						});
					});
</script>
</head>
<body >
	<ul class="nav nav-tabs">
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${workItem.bv.loanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${workItem.bv.loanCode}');">车辆信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${workItem.bv.loanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${workItem.bv.loanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${workItem.bv.loanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${workItem.bv.loanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${workItem.bv.loanCode}');">客户开户及管辖信息</a></li>
		<input type="button"  onclick="showCarLoanHis('${workItem.bv.loanCode}')" class="btn btn-small r mr10" value="历史">
		<input type="button"  id="giveUpBtn" class="btn btn-small r mr10"  value="客户放弃"></input></div>
	</ul>
	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
	<c:set var="bv" value="${workItem.bv}" />
	<div id="div1" class="content control-group ">
		<form id="carCustomer"  action="${ctx}/car/carApplyTask/addCarLoanFlowCustomer" method="post">
		
			<input type="hidden" value="${workItem.bv.loanCode}"
				name="loanCode" id="loanCode" /> <input type="hidden"
				value="${workItem.bv.customerCode}"
				name="customerCode" id="customerCode" /> 
			 	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
				<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
				<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
				<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
				<input type="hidden" value="${workItem.token}" name="token"></input>
				<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
				<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
				<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
			<input type="hidden" value="${workItem.bv.id}" name="id">
			<input type="hidden" value="${workItem.bv.applyId}" name="applyId">
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>

					<td><label class="lab"><span style="color: red">*</span>客户姓名：</label>
						<input type="text" name="customerName" readonly="readonly"
						value="${bv.customerName}" class="input_text178 required"/></td>
					<td><label class="lab"><span style="color: red">*</span>性别：</label>
						<c:choose>
							<c:when test="${bv.customerSex!=null}">
							  <span>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" 
										name="customerSex" value="${item.value}"
										<c:if test="${bv.customerSex==item.value}">
                                  checked
                                </c:if> class="required">
                           ${item.label}
                         </input>
								</c:forEach>
								</span>
							</c:when>
							<c:otherwise>
							  <span>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" 
										name="customerSex" value="${item.value}"
										<c:if test="${bv.customerSex==item.value}">
                                  checked
                                </c:if> class="required">
                             ${item.label}
                           </input>
								</c:forEach>
								</span>
							</c:otherwise>
						</c:choose>
						<span id="customerSexSpan"></span>
					</td>
					<td><label class="lab"><span style="color: red">*</span>婚姻状况：</label>
						<select id="dictMarry" name="dictMarryStatus" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
								<option value="${item.value}"
									<c:if test="${bv.dictMarryStatusCode==item.value}">
					      selected = true
					     </c:if>>${item.label}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><label class="lab"><span style="color: red">*</span>手机号码：</label>
						<input type="text" id="bankAccountName" name="customerMobilePhone"
						value="${bv.customerMobilePhone}" class="required isMobile"/>
				    </td>
				 
			    	<td><label class="lab">有无子女：</label> <c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="customerHaveChildren" 
								value="${item.value}"
								<c:if test="${item.value==bv.customerHaveChildren}">
                     checked='true'
                  </c:if>/>${item.label}
                </c:forEach></td>
                
                <td><label class="lab"><span style="color: red">*</span>最高学历：</label>
						<select id="dictEducation" name="dictEducation" class="select2-container select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}"
									<c:if test="${bv.dictEducationCode==curEdu.value}">
					      selected = true
					     </c:if>>${curEdu.label}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					
					<td><label class="lab"><span style="color: red">*</span>证件号码：</label>
						<c:choose>
							<c:when test="${bv.customerCertNum!=null}">
								<input name="customerCertNum"
									id="customerCertNum" type="text"
									value="${bv.customerCertNum}"
									class="input_text178" />
							</c:when>
							<c:otherwise>
								<input name="customerCertNum" id="customerCertNum"
									type="text" value="${bv.mateCertNum}"
									class="input_text178 required card" />
							</c:otherwise>
						</c:choose> <span id="blackTip" style="color: red;"></span></td>
					<td><label class="lab"><span style="color: red">*</span>证件有效期：</label>
						<input id="idStartDay" name="idStartDay"
						value="<fmt:formatDate value='${bv.idStartDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text70 required" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})"
						style="cursor: pointer" />-<input id="idEndDay"
						name="idEndDay"
						value="<fmt:formatDate value='${bv.idEndDay}' pattern="yyyy-MM-dd"/>"
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
					<td><label class="lab"><span style="color: red">*</span>暂住证：</label> <!--暂住证没有  --> <c:forEach
							items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="customerTempPermit"
								value="${item.value}" class="required"
								<c:if test="${item.value==bv.customerTempPermit}">
                     checked='true'
                  </c:if> />${item.label}
                </c:forEach>
                <span id="customerTempPermitSpan"></span>
                </td>
                
					<td><label class="lab" ><span style="color: red">*</span>住房性质：</label>
						<select name="customerHouseHoldProperty" id="customerHouseHoldProperty"
						value="${bv.customerHouseHoldProperty}"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_house_nature')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${item.value==bv.customerHouseHoldProperty}">
					      selected=true 
					    </c:if>>${item.label}</option>
							</c:forEach>
					</select></td>
					<td id="isHiddenRent" ><label class="lab" id="rentLabelName"><span style="color: red">*</span>月租金：</label>
						<input type="text" name="jydzzmRentMonth" id="jydzzmRentMonth"
						value="<fmt:formatNumber value="${bv.jydzzmRentMonth}" pattern="##0" />"
						class="input_text178 required number" maxlength="9"/>
						</td>
				   </tr>
				<tr>
					<td><label class="lab"><span style="color: red">*</span>初到本市时间：</label>
						<input id="customerFirtArriveYear"
						name="customerFirtArriveYear"
						value="<fmt:formatDate value='${bv.customerFirtArriveYear}' pattern="yyyy-MM"/>" type="text"
						class="Wdate input_text178 required" size="10"
						onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM'})" style="cursor: pointer" />
					</td>
					<td><label class="lab">起始居住时间：</label> <input
						id="customerFirstLivingDay"
						name="customerFirstLivingDay"
						value="<fmt:formatDate value='${bv.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab"><span style="color: red">*</span>供养亲属人数：</label>
						<input type="text" name="customerFamilySupport" maxlength="4"
						value="${bv.customerFamilySupport}" class="input_text178 required number"/>
						</td>
				</tr>
				
				<tr>
						<td><label class="lab"><span style="color: red">*</span>电子邮箱：</label> 
							<input name="customerEmail" type="text" id="customerEmail" value="${bv.customerEmail}" class="input_text178 required" />
				 		</td>
                      <td><label class="lab"><span style="color: red">*</span>信用额度：</label>
						<input type="text" name="creditLine"
						value="<fmt:formatNumber value="${bv.creditLine}" pattern="##0" />" class="input_text178 required number"   maxlength="10"/>
					 </td>
					 <td><label class="lab">本市电话：</label>
						<input type="text" name=cityPhone
						value="${bv.cityPhone}" class="input_text178  isTel"/>
					 </td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span style="color: red">*</span>现住址：</label>
						<select class="select78 required" id="liveProvinceId"
						name="customerLiveProvince">
							<option value="">请选择</option>
							<c:forEach var="item" items="${bv.provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${bv.customerLiveProvince==item.areaCode}">
		                    selected = true 
		                 </c:if>>${item.areaName}</option>
							</c:forEach>
					</select>-<select class="select78 required" id="liveCityId"
						name="customerLiveCity"
						value="${bv.customerLiveCity}">
							<option value="">请选择</option>
					</select>-<select id="liveDistrictId" class="select78 required"
						name="customerLiveArea"
						value="${bv.customerLiveArea}">
							<option value="">请选择</option>
					</select>
				<input type="hidden" id="liveCityIdHidden" value="${bv.customerLiveCity}"/>
					<input type="hidden" id="liveDistrictIdHidden" value="${bv.customerLiveArea}"/> 
					
					<input type="text"
						name="customerAddress" maxlength="50"
						value="${bv.customerAddress}" style="width: 250px"
						class="required" /></td>
				</tr>
				<tr>
					<td colspan="3"><label class="lab"><span style="color: red">*</span>户籍地址：</label>
						<select id="registerProvince"
						name="customerRegisterProvince" class="select78 required">
							<option value="">请选择</option>
							<c:forEach var="item" items="${bv.provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${bv.customerRegisterProvince==item.areaCode}">
							selected = true 
		                 </c:if>>
									${item.areaName}</option>
							</c:forEach>
					</select>-<select id="customerRegisterCity"
						name="customerRegisterCity" class="select78 required"
						value="${bv.customerRegisterCity}">
							<option value="">请选择</option>
					</select>-<select id="customerRegisterArea"
						name="customerRegisterArea" class="select78 required
						"
						value="${bv.customerRegisterArea}">
							<option value="">请选择</option>
					</select>
					<input type="hidden" id="customerRegisterCityHidden" value="${bv.customerRegisterCity}"/>
					<input type="hidden" id="customerRegisterAreaHidden" value="${bv.customerRegisterArea}"/>
					<input type="text" maxlength="50"
						name="customerRegisterAddress" class="required"
						style="width: 250px"
						value="${bv.customerRegisterAddress}" /></td>
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