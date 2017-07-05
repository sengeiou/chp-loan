<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default" />	
	<script type="text/javascript" src="${context}/js/storereview/loanFlowStoreReview.js"></script>
</head>
<body>
	 <h2 class="f14 pl10">个人基本信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">客户编号：</label>${bview.loanCustomer.customerCode}</td>
				<td><label class="lab">姓名：</label>${bview.loanCustomer.customerName}</td>
				<td><label class="lab">婚姻状况：</label> <span id="dictMarrySpan">${bview.loanCustomer.dictMarryLabel}</span>
				</td>
			</tr>
			<tr>
				<td><label class="lab">身份证号码：</label>${bview.loanCustomer.customerCertNum}</td>
				<td><label class="lab">证件有效期：</label> <c:if
						test="${bview.loanCustomer.idEndDay == null || bview.loanCustomer.idEndDay == ''}">
						长期
					</c:if> <c:if
						test="${bview.loanCustomer.idStartDay !=null && bview.loanCustomer.idEndDay != null}">
						<fmt:formatDate value="${bview.loanCustomer.idStartDay}"
							pattern="yyyy-MM-dd" />至
						<fmt:formatDate value="${bview.loanCustomer.idEndDay}"
							pattern="yyyy-MM-dd" />
					</c:if></td>
				<td><label class="lab">所属行业：</label>${bview.customerLoanCompany.dictCompIndustry}</td>
			</tr>
			<tr>
				<td><label class="lab">性别：</label>${bview.loanCustomer.customerSexLabel}</td>
				<td><label class="lab">教育程度：</label>${bview.loanCustomer.dictEducationLabel}</td>
				<%-- 屏蔽掉手机号码 <td><label class="lab">手机：</label>${bview.loanCustomer.customerPhoneFirst}&nbsp;&nbsp;${bview.loanCustomer.customerPhoneSecond}</td> --%>
			</tr>
		</table>
	</div>
	<h2 class="f14 pl10 mt20">借款意愿</h2>
	<div class="box2">
		<table class=" table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">申请额度(元)：</label>
				<fmt:formatNumber
						value="${bview.loanInfo.loanApplyAmount == null ? 0 : bview.loanInfo.loanApplyAmount }"
						pattern="#,##0.00" /></td>
				<td><label class="lab">产品类型：</label>${bview.productName}</td>		
				<td><label class="lab">申请期限(月)：</label>${bview.loanInfo.loanMonths}</td>
			</tr>
			<tr>
				<td><label class="lab">主要借款用途：</label>
					<c:if test="${bview.loanInfo.dictLoanUse eq '其它'}">
						${bview.loanInfo.dictLoanUseNewOther}
					</c:if>	                	
	            	<c:if test="${bview.loanInfo.dictLoanUse ne '其它'}">
	            		${bview.loanInfo.dictLoanUse}
	            	</c:if>
				</td>
				<td><label class="lab">最高可承受月还(元)：</label><fmt:formatNumber value='${bview.loanInfo.highPaybackMonthMoney}' pattern="#,##0.00"/></td>
				
				<td><label class="lab">主要还款来源：</label>
					<c:if test="${bview.loanInfo.dictLoanSource eq '其它'}">
						${bview.loanInfo.dictLoanSourceOther}
					</c:if>	                	
	            	<c:if test="${bview.loanInfo.dictLoanSource ne '其它'}">
	            		${bview.loanInfo.dictLoanSource}
	            	</c:if>
				</td>
			</tr>
			<tr>
				<td><label class="lab">其他收入来源：</label><%-- ${bview.loanInfo.dictLoanSourceElse} loanInfo.dictLoanSourceElseOther--%>
					<c:forEach items="${fns:getNewDictList('jk_repay_source_new_else')}" var="item">
			  			<c:if test="${fn:contains(bview.loanInfo.dictLoanSourceElse,item.value)}">
				  			<c:if test="${item.label eq '其它'}">
								${bview.loanInfo.dictLoanSourceElseOther}
							</c:if>	                	
		            		<c:if test="${item.label ne '其它'}">
		            			${item.label}
		            		</c:if>
			  			</c:if>
			  		</c:forEach>
				</td>
				<td><label class="lab">其它月收入(元)：</label><fmt:formatNumber value='${bview.loanInfo.otherMonthIncome}' pattern="#,##0.00"/></td>
				<td><label class="lab">同业在还借款总笔数：</label>${bview.loanInfo.otherCompanyPaybackCount}</td>
			</tr>
			<tr>
				<td><label class="lab">月还款总额(元)：</label><fmt:formatNumber value='${bview.loanInfo.otherCompanyPaybackTotalmoney}' pattern="#,##0.00"/></td>
			    <td><label class="lab">申请日期：</label>
				<fmt:formatDate value="${bview.loanInfo.loanApplyTime}"
						pattern="yyyy-MM-dd" /></td>
				<td><label class="lab">是否加急：</label>${bview.loanInfo.loanUrgentFlagLabel}</td>
			</tr>
			<tr>
				<td colspan="3"><label class="lab">备注：</label> <textarea
						readonly="readonly" rows="" cols="" name="loanRemark.remark"
						class="textarea_refuse">${bview.loanRemark.remark}</textarea></td>
			</tr>
		</table>
	</div>
	<c:if test="${bview.customerLoanCompany.compName != null&& bview.customerLoanCompany.compName !=''}">
		<h2 class="f14 pl10 mt20">工作信息</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">单位名称：</label>${bview.customerLoanCompany.compName }</td>
					<td><label class="lab">单位电话：</label>${bview.customerLoanCompany.compTel }</td>
					<td><label class="lab">所属行业：</label>${bview.customerLoanCompany.dictCompIndustry}</td>
				</tr>
				<tr>
					<td><label class="lab">单位地址：</label>
						${bview.customerLoanCompany.comProvinceName }
						${bview.customerLoanCompany.comCityName }
						${bview.customerLoanCompany.comArerName }
						${bview.customerLoanCompany.compAddress }</td>
					<td><label class="lab">前单位名称：</label>${bview.customerLoanCompany.previousCompName }</td>
					<td><label class="lab">单位性质：</label>${bview.customerLoanCompany.dictCompTypeLabel}</td>
					
				</tr>
				<tr>
					<td><label class="lab">	职务级别：</label>
						${bview.customerLoanCompany.compPostLevel}</td>
					<td><label class="lab">入职时间：</label>
						<fmt:formatDate value='${bview.customerLoanCompany.compEntryDay}' pattern="yyyy-MM-dd"/>
					</td>
					<td><label class="lab">	员工数量：</label>${bview.customerLoanCompany.compUnitScale }</td>
				</tr>
				<tr>
					<td><label class="lab">部门：</label>${bview.customerLoanCompany.compDepartment}</td>		
					<td><label class="lab">职务：</label>${bview.customerLoanCompany.compPost}</td>		
					<td><label class="lab">月税后工资：</label><fmt:formatNumber value='${bview.customerLoanCompany.compSalary}' pattern="##0.00"/> 元</td>
				</tr>
				<tr>
					<td><label class="lab">每月发薪日期：</label>${bview.customerLoanCompany.compSalaryDay }</td>
					<td><label class="lab">主要发薪方式：</label>${bview.customerLoanCompany.dictSalaryPay }</td>
				</tr>
			</table>
		</div>
	</c:if>
	
	<c:if test="${bview.loanCustomer.dictMarry == '1'}">
		<h2 class="f14 pl10 mt20">配偶资料</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">姓名：</label>${bview.loanMate.mateName}</td>
					<td><label class="lab">身份证号码：</label>${bview.loanMate.mateCertNum}</td> 
					<td><label class="lab">单位名称：</label>${bview.loanMate.mateLoanCompany.compName}</td>
					
				</tr>
				<tr>
					<td><label class="lab">手机号码：</label>${bview.loanMate.mateTel}</td>
					<td><label class="lab">邮箱：</label>${bview.loanMate.mateEmail}</td>
					<td><label class="lab">住址：</label>
						${bview.loanMate.mateAddressProvince} 
						${bview.loanMate.mateAddressCity}
						${bview.loanMate.mateAddressArea}
						${bview.loanMate.mateAddress}
					</td>
				</tr>
			</table>
		</div>
	</c:if>
	<h4 class="f14 pl10 mt10">亲属</h4>
	<table class="table table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>关系</th>
				<th>身份证号</th>
				<th>单位名称</th>
				<th>宅电</th>
				<th>手机号</th>
			</tr>
		</thead>
		<tbody>
		    <c:if test="${contactMap['family_relation']!=null && fn:length(contactMap['family_relation'])>0}">
				<c:forEach var="cust" items="${contactMap['family_relation']}"
					varStatus="str">
					<tr>
						<td>${str.count}</td>
						<td>${cust.contactName }</td>
						<td>
							<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
				  			    <c:if test="${cust.contactRelation==item.value}">
				  			      ${item.label}
				  			    </c:if>
					  		</c:forEach>
						</td>
						<td>${cust.certNum }</td>
						<td>${cust.contactSex }</td>
						<td>${cust.homeTel }</td>
						<td>${cust.contactMobile }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${contactMap['family_relation']==null || fn:length(contactMap['family_relation'])==0}">
				<tr>
					<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<h4 class="f14 pl10 mt10">工作证明人</h4>
	<table class="table table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>部门</th>
				<th>手机号码</th>
				<th>职务</th>
				<th>关系</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		    <c:if test="${contactMap['workmate_relation']!=null && fn:length(contactMap['workmate_relation'])>0}">
				<c:forEach var="custwork" items="${contactMap['workmate_relation']}"
					varStatus="strwork">
					<tr>
						<td>${strwork.count}</td>
						<td>${custwork.contactName }</td>
						<td>${custwork.department}</td>
						<td>${custwork.contactMobile }</td>
						<td>${custwork.post }</td>
						<td>
							<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
				  			    <c:if test="${custwork.contactRelation==item.value}">
				  			       ${item.label}
				  			    </c:if>
				  			</c:forEach>
						</td>
						<td>${custwork.remarks }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${contactMap['workmate_relation']==null || fn:length(contactMap['workmate_relation'])==0}">
				<tr>
					<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	
	<h4 class="f14 pl10 mt10">其他联系人</h4>
	<table class="table table-hover table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>姓名</th>
				<th>关系</th>
				<th>宅电</th>
				<th>手机号码</th>
			</tr>
		</thead>
		<tbody>
		    <c:if test="${contactMap['other_relation']!=null && fn:length(contactMap['other_relation'])>0}">
				<c:forEach var="custother" items="${contactMap['other_relation']}"
					varStatus="strother">
					<tr>
						<td>${strother.count}</td>
						<td>${custother.contactName }</td>
						<%-- <td>
							${custother.contactRelationLabel}
						</td>  --%>
						<td>
						${custother.remarks}
						</td>
						<td>${custother.homeTel}</td>
						<td>${custother.contactMobile }</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${contactMap['other_relation']==null || fn:length(contactMap['other_relation'])==0}">
				<tr>
					<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<c:if test="${bview.loanCoborrower != null && fn:length(bview.loanCoborrower)>0}">
		<h4 class="f14 pl10 mt10">自然人保证人信息</h4>
		<table class="table table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>身份证号</th>
					<th>手机号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="coboList" items="${bview.loanCoborrower }"
					varStatus="stu">
					<c:if
						test="${coboList.coboCertNum != null && coboList.coboCertNum != ''}">
						<tr>
							<td>${stu.count }</td>
							<td>${coboList.coboName }</td>
							<td>${coboList.coboCertNum }</td>
							<td>${coboList.coboMobile }</td>
						</tr>
					</c:if>
				</c:forEach>
				<c:if
					test="${bview.loanCoborrower==null || fn:length(bview.loanCoborrower)==''}">
					<tr>
						<td colspan="4" style="text-align: center;">没有符合条件的数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</c:if>
	
	<c:if test="${bview.customerLoanHouseList != null && fn:length(bview.customerLoanHouseList)>0&&bview.customerLoanHouseList[0].houseProvinceName!=null}">
		<h2 class="f14 pl10 mt10">房产信息</h2>
		<c:forEach var="hose" items="${bview.customerLoanHouseList }" varStatus="stu">
		<h5 class="f14 pl10 mt10" style="margin-left: 20px;">房产信息${stu.count}:</h5>
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">房产地址：</label>
						${hose.houseProvinceName}
						${hose.houseCityName}
						${hose.houseAreaName}
						${hose.houseAddress}
					</td>
					<td><label class="lab">房产类型：</label>
						<c:forEach items="${fns:getNewDictList('jk_design_use')}" var="item">
							<c:if test="${hose.dictHouseType==item.value}">${item.label}</c:if>
						</c:forEach>
					</td>
					<td><label class="lab">购买方式：</label>
						<c:forEach items="${fns:getNewDictList('jk_house_buywayg')}" var="item">
							<c:if test="${hose.houseBuyway == item.value}">${item.label}</c:if>
						</c:forEach>
					</td>
				</tr>
				<tr>
					<td><label class="lab">贷款总额：</label>
						<fmt:formatNumber value='${hose.houseLoanAmount}' pattern="0.00"/>万元
					</td>
					<td><label class="lab">贷款余额：</label><fmt:formatNumber value='${hose.houseLessAmount}' pattern="0.00"/></td>
					<td><label class="lab">月还款：</label><fmt:formatNumber value='${hose.houseMonthRepayAmount}' pattern="0.00"/></td>
					
				</tr>
				<tr>
					<td><label class="lab">	贷款年限(年)：</label>
						${hose.houseLoanYear}</td>
					<td><label class="lab">建筑面积：</label>
						<fmt:formatNumber value='${hose.houseBuilingArea}' pattern="0.00"/>平方
					</td>
					<td><label class="lab">购买时间：</label> <fmt:formatDate value='${hose.houseBuyday}' pattern="yyyy-MM-dd"/>
					</td>
				</tr>
				<tr>
					<td><label class="lab">购买价格：</label><fmt:formatNumber value='${hose.houseAmount}' pattern="0.00"/>万元</td>		
					<td><label class="lab">房屋所有权：</label>
						<c:forEach items="${fns:getNewDictList('jk_house_common_type')}" var="item">
							<c:if test="${hose.dictHouseCommon==item.value}">${item.label}</c:if>
						</c:forEach>
					</td>		
					
				</tr>
			</table>
		</c:forEach>
	</c:if>
	
	<c:if test="${bview.loanCompManage.businessLicenseRegisterNum != null && bview.loanCompManage.businessLicenseRegisterNum != ''}">
		<h2 class="f14 pl10 mt10">经营信息</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">营业执照注册号：</label>${bview.loanCompManage.businessLicenseRegisterNum}</td>
					<td><label class="lab">成立日期：</label><fmt:formatDate value='${bview.loanCompManage.compCreateDate}' pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">企业类型：</label>${bview.loanCompManage.compType}</td>
				</tr>
				<tr>
					<td><label class="lab">平均月营业额：</label>
						${bview.loanCompManage.averageMonthTurnover }万元
					</td>
					<td><label class="lab">主营业务：</label>${bview.loanCompManage.managerBusiness }</td>
					<td><label class="lab">企业注册资本：</label>${bview.loanCompManage.compRegisterCapital}万元</td>
					
				</tr>
				<tr>
					<td><label class="lab">	申请人占股比例：</label>
						${bview.loanCompManage.customerRatioComp}</td>
					<td><label class="lab">法定代表人姓名：</label>
						${bview.loanCompManage.corporateRepresent}
					</td>
					<td><label class="lab">法定代表人身份证号码：</label> ${bview.loanCompManage.certNum }
					</td>
				</tr>
				<tr>
					<td><label class="lab">法定代表人手机号：</label>${bview.loanCompManage.corporateRepresentMobile}</td>		
					<td><label class="lab">企业邮箱：</label>${bview.loanCompManage.compEmail}</td>		
					<td><label class="lab">营业面积：</label>${bview.loanCompManage.businessArea}平方米</td>
				</tr>
				<tr>
					<td><label class="lab">经营场所：</label>
						<c:forEach items="${fns:getNewDictList('jk_comp_manage_area')}" var="item">
						     <c:if test="${fn:contains(bview.loanCompManage.managePlace,item.value)}">${item.label}</c:if> 
						</c:forEach>
					</td>
					<td><label class="lab">经营地址：</label>
						${bview.loanCompManage.manageAddressProvince}
						${bview.loanCompManage.manageAddressCity}
						${bview.loanCompManage.manageAddressArea}
						${bview.loanCompManage.manageAddress}
					</td>
				</tr>
			</table>
		</div>
	</c:if>
	<c:if test="${!empty bview.loanPersonalCertificate && bview.loanPersonalCertificate.masterName!=null}">
	<h2 class="f14 pl10 mt20">证件信息</h2>
	<div class="box2 mb30">
		
		<table class=" table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td colspan="3">
					<h3 style="margin-left: 20px;">户口本信息</h3>
				</td>
			</tr>
			<tr>
				<td><label class="lab">申请人与户主关系：</label>
					<c:forEach items="${fns:getNewDictList('jk_apply_rel_master')}" var="item">
						<c:if test="${bview.loanPersonalCertificate.customerRelMaster==item.value}"> ${item.label} </c:if>
		  			</c:forEach>
		  			${bview.loanPersonalCertificate.customerRelMasterRemark}
				</td>
				<td><label class="lab">户主姓名：</label>${bview.loanPersonalCertificate.masterName}</td>
				<td><label class="lab">户主身份证号码：</label>${bview.loanPersonalCertificate.masterCertNum}</td>
			</tr>
			<tr>
				<td><label class="lab">子女姓名：</label>${bview.loanPersonalCertificate.childrenName}</td>
				<td><label class="lab">子女身份证号码：</label>${bview.loanPersonalCertificate.childrenCertNum}</td>
				<td><label class="lab">户主页住址：</label>
					${bview.loanPersonalCertificate.masterAddressProvince}
					${bview.loanPersonalCertificate.masterAddressCity}
					${bview.loanPersonalCertificate.masterAddressArea}
					${bview.loanPersonalCertificate.masterAddress}
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<h3 style="margin-left: 20px;">学历信息</h3>
				</td>
			</tr>
			<tr>
				<td><label class="lab">学历证书类型：</label>
					<c:forEach items="${fns:getNewDictList('jk_academic_certificate_type')}" var="item">
						<c:if test="${bview.loanPersonalCertificate.educationalCertificateType==item.value}"> ${item.label} </c:if>
		  			</c:forEach>
				</td>
				<td><label class="lab">毕业院校：</label>${bview.loanPersonalCertificate.graduationSchool}</td>
				<td><label class="lab">学历证书编号： </label>${bview.loanPersonalCertificate.educationalCertificateNum}</td>
			</tr>
			<tr>
				<td><label class="lab">学历证书取得时间：</label>
					<fmt:formatDate value='${bview.loanPersonalCertificate.educationalCertificateTime}' pattern="yyyy-MM-dd"/>
				</td>
			</tr>
			<tr>
				<td colspan="3">
					<h3 style="margin-left: 20px;">结婚证信息</h3>
				</td>
			</tr>
			<tr>
				<td><label class="lab">结婚日期：</label>
					<fmt:formatDate value='${bview.loanPersonalCertificate.weddingTime}' pattern="yyyy-MM-dd"/>
				</td>
				<td><label class="lab">发证机构：</label>
					${bview.loanPersonalCertificate.licenseIssuingAgency}
				</td>
			</tr>
		</table>
	</div>
	</c:if>
	
	<h2 class="f14 pl10 mt20">银行卡信息</h2>
	<div class="box2 mb30">
		<table class=" table1" cellpadding="0" cellspacing="0" border="0"
			width="100%">
			<tr>
				<td><label class="lab">银行卡/折 户名：</label>${bview.loanBank.bankAccountName}</td>
				<td><label class="lab">授权人：</label>${bview.loanBank.bankAuthorizer}</td>
				<td><label class="lab">签约平台：</label>${bview.loanBank.bankSigningPlatformName}</td>
			</tr>
			<tr>
				<td><label class="lab">开户行：</label>${bview.loanBank.bankNameLabel}</td>
				<td><label class="lab">开户行支行：</label>${bview.loanBank.bankBranch}</td>
				<td><label class="lab">开户省市：</label>${bview.loanBank.bankProvinceName}${bview.loanBank.bankCityName}</td>
			</tr>
			<tr>
				<td><label class="lab">银行卡号：</label>${bview.loanBank.bankAccount}</td>
				<td></td>
			</tr>
		</table>
	</div>
 </body>
</html>