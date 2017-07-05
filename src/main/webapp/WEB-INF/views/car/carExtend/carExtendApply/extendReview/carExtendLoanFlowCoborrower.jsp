<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript"
	src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carExtendConsultForm.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselectDisable.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$(".contactPanel").each(function(i, element) {
		if($("#liveCityId" + i).val()!=undefined){
			loan.initCity("liveProvinceId"+i, "liveCityId"+i,"liveDistrictId"+i);
			areaselect.initCity("liveProvinceId"+i,"liveCityId"+i, "liveDistrictId"+i, $("#liveCityIdInput" + i).val(), $("#liveDistrictIdInput" + i).val());
			
			// 其他收入显示与隐藏
			$(this).find("input:radio[name='haveOtherIncome" + i + "']").bind('change', function() {
				var haveOtherIncome = $("input:radio[name='haveOtherIncome" + i + "']:checked").val();
				if(haveOtherIncome){
					if(haveOtherIncome == '0'){
						$(this).parent().find("input[name='otherIncome" + i + "']").val("");
						$(this).parent().children("span").css("display","none");
						$(this).parent().find("input[name='otherIncome" + i + "']").removeClass("required");
					}else{
						$(this).parent().children("span").css("display","inline");
						$(this).parent().find("input[name='otherIncome" + i + "']").addClass("required");
					}
				}
			});
			$(this).find("input:radio[name='haveOtherIncome" + i + "']").trigger('change');
		}
	});
	//验证JS
	$("#coboSave").bind("click",function() {
		var flag = $("#carCoborrow").validate({
			onclick: true, 
			rules:{
				customerMobilePhone:{
					mobile:true,
				},
				customerCertNum:{
					card:true,
				}
			},
			messages : {
				customerName:{
					realName:"姓名只能为2-30个汉字"
				}
			}
		}).form();
		if (flag) {
			coborrowExtendNextBtnSave();
		}
	});
	$("#carCoborrow").validate({
				onclick: true, 
				rules:{
					customerMobilePhone:{
						mobile:true,
					},
					customerCertNum:{
						card:true,
					},
				},
				messages : {
					customerName:{
						realName:"姓名只能为2-30个汉字"
					},
				
				},
				submitHandler:function(form){ // 提交表单
					coborrowExtendNextBtnSave();
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
					$(this).prev().val(phone);s
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
function backCarLoanHis(){
	  location.href="${ctx}/bpm/flowController/openForm?applyId=${param.applyId}&loanCode=${loanCode}&wobNum=${param.wobNum}&dealType=${param.dealType}&token=${param.token}";
}
</script>
<style>
.coborrowerTable tr td ,th{border-color:#c3c3c3;}
.coborrowerTable{ border-color:#c3c3c3}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarContract');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendInfo');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowCustomer');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendCompany');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendContact');">联系人资料</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toExtendCoborrower');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowBank');">客户开户信息</a></li>
		<input type="button" style="float: right"
			onclick="backCarLoanHis()" class="btn btn-small"
			value="返回合同审核主页">
	</ul>
	<form id="frameFlowForm" method="post" >
			<input type="hidden" name="loanCode" value="${loanCode }" />
			<input type="hidden" name="applyId" value="${param.applyId}" />
			<input type="hidden" name="wobNum" value="${param.wobNum}" />
			<input type="hidden" name="dealType" value="${param.dealType}" />
			<input type="hidden" name="token" value="${param.token}" />
	</form><c:set var="param" value="${workItem.bv}" />
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
	<form id="carCoborrow">
		<div id="coborroArea">
					<c:forEach items="${carLoanCoborrowers}" var="cobs"  varStatus="status">
						<div class="contactPanel">
						<input type="hidden"  id="liveCityIdInput${status.index }" value="${cobs.dictLiveCity}"/>
						<input type="hidden"  id="liveDistrictIdInput${status.index }" value="${cobs.dictLiveArea}"/>
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
									身份证<input
										type="hidden" mark="dictCertType" value="0">
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
										 <input type="checkbox" class="isRareword" name= "istelephonemodify" mark ="istelephonemodify"class="isRareword"
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
											 <input type="checkbox" id="isEmailModify" name= "isemailmodify" mark="isemailmodify" class="isEmailModify"
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
									<td><label class="lab"><span style="color: red">*</span>住房性质：</label>
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
				                        class="input_text178 required number" maxlength="9" value="<fmt:formatNumber value="${cobs.houseRent}" pattern="##0.00"/>"/>
				                        </span>
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
										value="${cobs.nowAddress}" />
										</td>
								</tr>
								<tr>
									<td colspan="3"><label class="lab"><span
											class="red">*</span>户籍地址：</label> 
											<sys:carAddressShow detailAddress="${cobs.dictHouseholdView}"></sys:carAddressShow>
											</td>
								</tr>
								<input type="hidden" value="${cobs.loanCode}" mark="loanCode"
									name="loanCode"></input>
									<input type="hidden" value="${newLoanCode}" mark="newLoanCode"
									name="newLoanCode"></input>
							</table><br/>
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
													<td><input type="hidden" mark="id"
														name="id" value="${cper.id}"/>
													<input type="text" mark="contactUint"
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
		</div>
		<div class="tright mr10 mb5">
			<input type="button" class="btn btn-primary" id="coboSave" value="保存">
		</div>
	</form>
</body>
</html>