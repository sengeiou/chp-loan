<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="contactPanel" id="contactPanel_${parentIndex}" index='${parentIndex}'>
	<div  style="text-align:left;" class="mt20">
		<input type="button" name="delCoborrower" index="${parentIndex}" coboId="-1" value="删除自然人保证人" class="btn btn-small"/>
	</div>
	<input type="hidden" class="coboId" name="loanCoborrower[${parentIndex}].id" value="${currCoborro.id}" />
	<h5 class="mt20">借款意愿</h5>
	<hr class="hr"/>
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td width="33%">
				<label class="lab">
					<span class="red">*</span>
					申请额度(元)：
				</label>
				<input type="hidden" id="limitLower_${parentIndex}"/>
				<input type="hidden" id="limitUpper_${parentIndex}"/>	
				<input type="hidden" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.id"/>
				<input type="text" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.loanApplyAmount" class="input_text178 required numCheck positiveNumCheck limitCheckCobo" />
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					产品类别：
				</label>
				<select id="productType_${parentIndex}" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.productType" class="select180 required">
				</select>
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					申请期限：
				</label>
				<span>
					<select id="loanMonths_${parentIndex}" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.loanMonths" class="select180 required">
						<option value="">请选择</option>
					</select>
					<input type="hidden" id="loanMonthsRecord_${parentIndex}" />
				</span>
			</td>
		</tr>
		<tr>
			<td width="33%">
				<label class="lab">
					<span class="red">*</span>
					主要借款用途：
				</label>
				<select id="loanApplyAmount_${parentIndex}" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.borrowingPurposes" class="select2-container select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_loan_use')}" var="item">
						<option value="${item.value}">
							${item.label}
						</option>
					</c:forEach>
				</select>
				<input type="text" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.borrowingPurposesRemark" class="input_text178 collapse" maxlength="100"/>
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					主要还款来源：
				</label>
				<select id="mainPaybackResource_${parentIndex}" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.mainPaybackResource" class="select2-container select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_repay_source_new')}" var="item">
						<option value="${item.value}">
							${item.label}
						</option>
					</c:forEach>
				</select>
				<input name="loanCoborrower[${parentIndex}].loanInfoCoborrower.mainPaybackResourceRemark" type="text" class="input_text178 collapse" maxlength="100"/>
			</td>
		</tr>
		<tr >
			<td rowspan="1">
				<label class="lab">
					<span class="red">*</span>
					最高可承受月还(元)：
				</label>
				<input name="loanCoborrower[${parentIndex}].loanInfoCoborrower.highPaybackMonthMoney" type="text" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
			</td>
			<td colspan="2" rowspan="3">
				<label class="lab">
					备注：
				</label>
				<textarea class="textarea_refuse" name="loanCoborrower[${parentIndex}].loanInfoCoborrower.remarks" maxlength="450"></textarea>
			</td>
		</tr>
	</table>
	
	<h5 class="mt20">个人基本信息</h5>
	<hr class="hr"/>
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td width="33%">
				<label class="lab">
					<span class="red">*</span>
					姓名：
				</label>
				<input type="text" name="loanCoborrower[${parentIndex}].coboName" class="input_text178 required stringCheck" />
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					身份证号码：
				</label>
				<!--证件类型，默认为身份证 -->
				<input name="loanCoborrower[${parentIndex}].dictCertType" type="hidden" value="0"/>
				<input name="loanCoborrower[${parentIndex}].coboCertNum" onblur="javascript:launch.certifacteFormatByCertNum(this.value,this);" type="text" class="input_text178 required coboCertCheckCopmany coboCertNumCheck2 currentPageDuplicateCertNumCheck" />
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					证件有效期：
				</label>
				<span>
					<input id="idStartDay_${parentIndex}" name="loanCoborrower[${parentIndex}].idStartDay" type="text" class="Wdate input_text70" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay_${parentIndex}\')}'})" style="cursor: pointer" />
					-
					<input id="idEndDay_${parentIndex}" name="loanCoborrower[${parentIndex}].idEndDay" type="text" class="Wdate input_text70" size="10" onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay_${parentIndex}\')}'})" style="cursor: pointer" />
					<input type="checkbox" id="longTerm_${parentIndex}" name="longTerm_${parentIndex}" value="1" class="coboEffeCertificate" onclick="launch.idEffectiveDay(this,'idEndDay_${parentIndex}');">
					长期
					</input>
				</span>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab">
					<span class="red">*</span>
					性别：
				</label>
				<span>
					<c:forEach items="${fns:getNewDictList('jk_sex')}" var="item">
						<input type="radio" name="loanCoborrower[${parentIndex}].coboSex" class="required" value="${item.value}">${item.label}</input>
					</c:forEach>
				</span>
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					教育程度：
				</label>
				<select id="dictEducation_${parentIndex}" name="loanCoborrower[${parentIndex}].dictEducation" class="select2-container select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_degree')}" var="curEdu">
						<option value="${curEdu.value}">${curEdu.label}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					婚姻状况：
				</label>
				<select name="loanCoborrower[${parentIndex}].dictMarry" class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_marriage')}" var="item">
						<c:if test="${item.label!='空'}">
							<option value="${item.value}">${item.label}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab"><span class="red">*</span>子女人数(人)：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].childrenNum" class="input_text178 required positiveInteger" maxlength="3"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>供养人数(人)：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].supportNum" class="input_text178 required positiveInteger" maxlength="3"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>个人年收入(万元)：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].personalYearIncome" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab"><span class="red">*</span>家庭月收入(元)：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].homeMonthIncome" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>家庭月支出(元)：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].homeMonthPay" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
			</td>
			<td>
				<label class="lab"><span class="red">*</span>家庭总负债(万元)：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].homeTotalDebt" class="input_text178 required number numCheck positiveNumCheck " min="0" max="999999999.00"/>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab"><span class="red">*</span>初来本市时间：</label>
				<input id="firstArriveYear_${parentIndex}" name="loanCoborrower[${parentIndex}].customerFirtArriveYear" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
			</td>
			<td>
				<label class="lab"><span class="red">*</span>现住宅起始居住日期：</label>
				<input id="customerFirtLivingDay_${parentIndex}" name="loanCoborrower[${parentIndex}].customerFirstLivingDay" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
			</td>
			<td>
				<label class="lab"><span class="red"></span>宅电：</label>
				<input type="text" name="loanCoborrower[${parentIndex}].coboFamilyTel" class="input_text178 isTel" />
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<label class="lab">
					<span class="red">*</span>
					现居住地址：
				</label>
				<select class="select78  required coboLiveingProvince" id="coboLiveingProvince_${parentIndex}" name="loanCoborrower[${parentIndex}].coboLiveingProvince">
					<option value="">请选择</option>
					<c:forEach var="item" items="${provinceList}" varStatus="status">
						<option value="${item.areaCode}">${item.areaName}</option>
					</c:forEach>
				</select>
				-
				<select class="select78  required coboLiveingCity" id="coboLiveingCity_${parentIndex}" name="loanCoborrower[${parentIndex}].coboLiveingCity">
					<option value="">请选择</option>
				</select>
				-
				<select class="select78  required coboLiveingArea" id="coboLiveingArea_${parentIndex}" name="loanCoborrower[${parentIndex}].coboLiveingArea">
					<option value="">请选择</option>
				</select>
				<input type="hidden" id="coboLiveingCityHidden_${parentIndex}"/>
				<input type="hidden" id="coboLiveingAreaHidden_${parentIndex}"/>
				<input name="loanCoborrower[${parentIndex}].coboNowAddress" type="text" class="input_text178 required" maxlength="100" style="width: 250px" />
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					住宅类别：
				</label>
				<select name="loanCoborrower[${parentIndex}].customerHouseHoldProperty" class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_house_nature')}" var="item">
						<option value="${item.value}">${item.label}</option>
					</c:forEach>
				</select>
				<input type="text" class="input_text178 collapse" name="loanCoborrower[${parentIndex}].residentialCategoryRemark" maxlength="100"/>
			</td>
			<td>
				<label class="lab">
					社保卡号：
				</label>
				<input type="text" class="input_text178 isSocial" name="loanCoborrower[${parentIndex}].socialSecurityNumber" maxlength="20"/>
			</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					常用手机号：
				</label>
				<input name="loanCoborrower[${parentIndex}].coboMobile" type="text" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2" />
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<label class="lab">
					<span class="red">*</span>
					户籍地址：
				</label>
				<select class="select78 required coboHouseholdProvince" id="coboHouseholdProvince_${parentIndex}" name="loanCoborrower[${parentIndex}].coboHouseholdProvince">
					<option value="">请选择</option>
					<c:forEach var="item" items="${provinceList}" varStatus="status">
						<option value="${item.areaCode }">${item.areaName}</option>
					</c:forEach>
				</select>
				-
				<select class="select78  required coboHouseholdCity" id="coboHouseholdCity_${parentIndex}" name="loanCoborrower[${parentIndex}].coboHouseholdCity">
					<option value="">请选择</option>
				</select>
				-
				<select class="select78 required coboHouseholdArea" id="coboHouseholdArea_${parentIndex}" name="loanCoborrower[${parentIndex}].coboHouseholdArea">
					<option value="">请选择</option>
				</select>
				<input type="hidden" id="coboHouseholdCityHidden_${parentIndex}"/>
				<input type="hidden" id="coboHouseholdAreaHidden_${parentIndex}"/>
				<input name="loanCoborrower[${parentIndex}].coboHouseholdAddress" type="text" class="input_text178 required" style="width: 250px" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					户籍性质：
				</label>
				<select name="loanCoborrower[${parentIndex}].registerProperty" class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_register_property')}" var="item">
						<option value="${item.value}">${item.label}</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					邮箱：
				</label>
				<input type="text" class="input_text178 required isEmail" name="loanCoborrower[${parentIndex}].coboEmail"/>
			</td>
			<td class="loanCoborrower_issuingAuthority" style="display:none;">
				<label class="lab"><span style="color: red">*</span>签发机关：</label>
				<input name="loanCoborrower[${parentIndex}].issuingAuthority" type="text" value="${currCoborro.issuingAuthority}" class="input_text178 required" maxlength="100"/>
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab">微博：</label>
				<input name="loanCoborrower[${parentIndex}].coboWeibo" type="text" class="input_text178 maxLength50" />
			</td>
			<td>
				<label class="lab">QQ：</label>
				<input name="loanCoborrower[${parentIndex}].coboQq" type="text" class="input_text178 isQQ maxLength20" />
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab">
					征信用户名：
				</label>
				<input type="text" name="loanCoborrower[${parentIndex}].creditUserName" class="input_text178 maxLength20" />
			</td>
			<td>
				<label class="lab">
					密码：
				</label>
				<input type="text" name="loanCoborrower[${parentIndex}].creditPassword" class="input_text178 maxLength20" />
			</td>
			<td>
				<label class="lab">
					身份验证码：
				</label>
				<input type="text" name="loanCoborrower[${parentIndex}].creditAuthCode" class="input_text178 maxLength20" />
			</td>
		</tr>
	</table>
	
	<h5 class="mt20">工作信息</h5>
	<hr class="hr"/>	
	<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					单位名称：
				</label>
				<input name="loanCoborrower[${parentIndex}].coboCompany.id" type="hidden"/>
				<input name="loanCoborrower[${parentIndex}].coboCompany.compName" type="text" class="input_text178 required"/>
			</td>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					单位电话：
				</label>
				<input name="loanCoborrower[${parentIndex}].coboCompany.compTel" type="text" class="input_text178 required isTel" />
				-
				<input style="width: 50px" type="text" max="9999.00" name="loanCoborrower[${parentIndex}].coboCompany.compTelExtension" class="input_text178 positiveInteger" />
			</td>
		</tr>
		<tr>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					月税后工资(元)：
				</label>
				<input name="loanCoborrower[${parentIndex}].coboCompany.compSalary" type="text" class="input_text178 required number numCheck positiveNumCheck" min="0" max="999999999.00"/>
			</td>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					每月发薪日期(日)：
				</label>
				<input name="loanCoborrower[${parentIndex}].coboCompany.compSalaryDay" type="text" class="input_text178 required day" />
			</td>
			<td>
				<label class="lab">
					<span style="color: red">*</span>
					主要发薪方式：
				</label>
				<select name="loanCoborrower[${parentIndex}].coboCompany.dictSalaryPay" class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getNewDictList('jk_paysalary_way')}" var="item">
						<option value="${item.value}">
							${item.label}
						</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="3">
				<label class="lab">
					<span class="red">*</span>
					单位地址：
				</label>
				<select class="select78  required coboCompProvince" id="coboCompProvince_${parentIndex}" name="loanCoborrower[${parentIndex}].coboCompany.compProvince">
					<option value="">请选择</option>
					<c:forEach var="item" items="${provinceList}" varStatus="status">
						<option value="${item.areaCode}">${item.areaName}</option>
					</c:forEach>
				</select>
				-
				<select class="select78  required coboCompCity" id="coboCompCity_${parentIndex}" name="loanCoborrower[${parentIndex}].coboCompany.compCity">
					<option value="">请选择</option>
				</select>
				-
				<select class="select78  required coboCompArer" id="coboCompArer_${parentIndex}" name="loanCoborrower[${parentIndex}].coboCompany.compArer">
					<option value="">请选择</option>
				</select>
				<input type="hidden" id="compCityHidden_${parentIndex}" />
				<input type="hidden" id="compArerHidden_${parentIndex}" />
				<input name="loanCoborrower[${parentIndex}].coboCompany.compAddress" type="text" class="input_text178 required" maxlength="100" style="width: 250px" />
			</td>
		</tr>
	</table>
	<h5 class="mt20">联系人信息</h5>
	<hr class="hr"/>
	
	<div class="mt20 ml48">
		<h5 class="l mt5">亲属</h5>
		<input type="button" class="btn btn-small ml20" value="增加" name="addCobContactBtn" tableId='relatives_contact_table_${parentIndex}' parentIndex='${parentIndex}' />
	</div> 
	
	<table currIndex="2" id="relatives_contact_table_${parentIndex}" class="table  table-bordered table-condensed  mt20 ml48" style="width: 90%">
		<thead>
			<tr>
				<th><span class="red">*</span>姓名</th>
				<th><span class="red">*</span>关系</th>
				<th>身份证号码</th>
				<th>单位名称</th>
				<th>宅电</th>
				<th>手机号码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" value="0" />
					<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.id" />
					<input id = "relativesContact_${parentIndex}_contactName_0" type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
				</td>
				<td>
					<select id="relativesContact_${parentIndex}_contactRelation_0" index="1${parentIndex}0" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" class="required select180">
						<option value="">请选择</option>
						<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
							<option value="${item.value}" class="${item.id}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input id="relativesContact_${parentIndex}_certNum_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.certNum" class="input_text178 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
				</td>
				<td>
					<input id="relativesContact_${parentIndex}_contactSex_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactSex" class="input_text178" />
				</td>
				<td>
					<input id="relativesContact_${parentIndex}_homeTel_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.homeTel" class="input_text178 isTel mobileAndTelNeedOne" />
				</td>
				<td>
					<input id="relativesContact_${parentIndex}_contactMobile_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 isMobile coboMobileDiff1 coboMobileDiff2 mobileAndTelNeedOne" />
				</td>
				<td class="tcenter"></td>
			</tr>
		</tbody>
	</table>
	
	<div class="mt20 ml48">
		<h5 class="l mt5">工作联系人</h5>
		<input type="button" class="btn btn-small ml20" value="增加" name="addCobContactBtn" tableId='worktogether_contact_table_${parentIndex}' parentIndex='${parentIndex}' />
	</div>
	<table currIndex="2" id="worktogether_contact_table_${parentIndex}" class="table  table-bordered table-condensed  mt20 ml48" style="width: 90%">
		<thead>
			<tr>
				<th><span class="red">*</span>姓名</th>
				<th><span class="red">*</span>部门</th>
				<th><span class="red">*</span>手机号码</th>
				<th><span class="red">*</span>职务</th>
				<th><span class="red">*</span>关系</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" value="1" />
					<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.id"></input>
					<input id="workTogetherContact_${parentIndex}_contactName_0" type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
				</td>
				<td>
					<input id="workTogetherContact_${parentIndex}_department_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.department" class="input_text178 required" maxlength="20"/>
				</td>
				<td>
					<input id="workTogetherContact_${parentIndex}_contactMobile_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2" />
				</td>
				<td>
					<input id="workTogetherContact_${parentIndex}_post_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.post" class="input_text178 required" maxlength="20"/>
				</td>
				
				<td>
					<select id="workTogetherContact_${parentIndex}_contactRelation_0" index="2${parentIndex}0" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" class="required select180">
						<option value="">请选择</option>
						<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
							<option value="${item.value}" class="${item.id}">${item.label}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input id="workTogetherContact_${parentIndex}_remarks_0" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.remarks" class="input_text178" />
				</td>
				<td class="tcenter"></td>
			</tr>
		</tbody>
	</table>
	
	<div class="mt20 ml48">
		<h5 class="l mt5">其他</h5>
		<input type="button" class="btn btn-small ml20" value="增加" name="addCobContactBtn" tableId='other_contact_table_${parentIndex}' parentIndex='${parentIndex}' />
	</div>
	<table currIndex="2" id="other_contact_table_${parentIndex}" class="table  table-bordered table-condensed  mt20 ml48" style="width: 90%">
		<thead>
			<tr>
				<th><span class="red">*</span>姓名</th>
				<th><span class="red">*</span>关系</th>
				<th>宅电</th>
				<th><span class="red">*</span>手机号码</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${isBorrow ne 1}">
				<c:set var="otherContactEnd" value="1"/>
			</c:if>
			<c:if test="${isBorrow eq 1}">
				<c:set var="otherContactEnd" value="0"/>
			</c:if>
			<c:forEach  varStatus="other" begin="0" end="${otherContactEnd}">
				<tr>
					<td>
						<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" value="2" />
						<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.id"></input>
						<input id="otherContact_${parentIndex}_contactName_${other.index}" type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck" />
					</td>
					<td>
						<input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" value="1">
						<input id="otherContact_${parentIndex}_remarks_${other.index}" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.remarks" class="input_text178 required chineseCheck2" maxlength="100"/>
					</td>
					<td>
						<input id="otherContact_${parentIndex}_homeTel_${other.index}" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.homeTel" class="input_text178 isTel" />
					</td>
					<td>
						<input id="otherContact_${parentIndex}_contactMobile_${other.index}" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2" />
					</td>
					<td class="tcenter"></td>
				</tr>
			</c:forEach>	
		</tbody>	
	</table>
	
	<table class="table1 mt20 ml48" style="width: 90%">
		<tr>
			<td colspan="3">
				以上可知晓本次借款的联系人&nbsp;&nbsp;
				<span>
					<c:forEach items="${fns:getNewDictList('jk_who_can_know_the_borrowing')}" var="item">
						<c:if test="${item.value != '1'}">
							<input type="checkbox" name="loanCoborrower[${parentIndex}].whoCanKnowTheBorrowing" value="${item.value}" class="required"/>${item.label}
						</c:if>
					</c:forEach>
				</span>
				<input type="text" name="loanCoborrower[${parentIndex}].whoCanKnowTheBorrowingRemark" class="input_text178 collapse chineseCheck2" maxlength="100"/>
			</td>
		</tr>
	</table>
</div>
