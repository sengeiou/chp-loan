<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta name="decorator" content="default" />	
	<script type="text/javascript" src="${context}/js/storereview/loanFlowStoreReview.js"></script>
</head>
<body>
	 <h2 class="f14 pl10">客户信息</h2>
	<div class="box2">
		<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td><label class="lab">客户姓名：</label>${bview.loanCustomer.customerName}</td>	
				<td></td>
				<td></td>					
			</tr>
			<tr>
				<td><label class="lab">客户编号：</label>${bview.loanCustomer.customerCode}</td>				
				<td><label class="lab">性别：</label>${bview.loanCustomer.customerSexLabel}</td>
				<td>
					<label class="lab">婚姻状况：</label>
					<span id="dictMarrySpan">${bview.loanCustomer.dictMarryLabel}</span>
				</td>
			</tr>
			<tr>					
				<td><label class="lab">证件类型：</label>${bview.loanCustomer.dictCertTypeLabel}</td>
				<td><label class="lab">证件号码：</label>${bview.loanCustomer.customerCertNum}</td>
				<td>
					<label class="lab">证件有效期：</label>
					<c:if test="${bview.loanCustomer.idEndDay == null || bview.loanCustomer.idEndDay == ''}">
						长期
					</c:if>
					<c:if test="${bview.loanCustomer.idStartDay !=null && bview.loanCustomer.idEndDay != null}">
						<fmt:formatDate value="${bview.loanCustomer.idStartDay}" pattern="yyyy-MM-dd"/>至
						<fmt:formatDate value="${bview.loanCustomer.idEndDay}" pattern="yyyy-MM-dd"/>					
					</c:if>
				</td>
			</tr>								
			<tr>
				<td><label class="lab">学历：</label>${bview.loanCustomer.dictEducationLabel}</td>
				<td><label class="lab">电话：</label>${bview.loanCustomer.customerTel}</td>
				<td><label class="lab">手机：</label>${bview.loanCustomer.customerPhoneFirst}&nbsp;&nbsp;${bview.loanCustomer.customerPhoneSecond}</td>
			</tr>
		</table>
	</div>	
	<c:if test="${bview.loanCustomer.dictMarry == '1'}">
		<h2 class="f14 pl10 mt20">配偶资料</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">姓名：</label>${bview.loanMate.mateName}</td>					
					<td><label class="lab">手机：</label>${bview.loanMate.mateTel}</td>	
					<td><label class="lab">证件号码：</label>${bview.loanMate.mateCertNum}</td>
				</tr>
				<tr>
					<td><label class="lab">单位名称：</label>${bview.loanMate.mateLoanCompany.compName}</td>
					<td><label class="lab">单位电话：</label>${bview.loanMate.mateLoanCompany.compTel}</td>
					<td><label class="lab">单位地址：</label>${bview.loanMate.mateLoanCompany.compAddress}</td>
				</tr>
			</table>			
		</div>						
	</c:if>
	<h2 class="f14 pl10 mt20">信借申请</h2>
	<div class="box2">
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td><label class="lab">产品类型：</label>${bview.productName}</td>
				<td><label class="lab">申请额度(元)：</label><fmt:formatNumber value="${bview.loanInfo.loanApplyAmount == null ? 0 : bview.loanInfo.loanApplyAmount }" pattern="#,##0.00" /></td>
				<td><label class="lab">借款期限(月)：</label>${bview.loanInfo.loanMonths}</td>
			</tr>
			<tr>
				<td><label class="lab">申请日期：</label><fmt:formatDate value="${bview.loanInfo.loanApplyTime}" pattern="yyyy-MM-dd"/></td>
				<td><label class="lab">借款用途：</label>${bview.loanInfo.dictLoanUserLabel}</td>
				<td><label class="lab">具体用途：</label>${bview.loanInfo.realyUse}</td>
			</tr>
			<tr>					
				<td><label class="lab">由何处得知我公司：</label>${bview.loanCustomer.dictCustomerSourceLabel}</td>
				<td><label class="lab">管辖城市：</label>${bview.loanInfo.storeProviceName}${bview.loanInfo.storeCityName}</td>
				<td><label class="lab">是否加急：</label>${bview.loanInfo.loanUrgentFlagLabel}</td>
			</tr>
			<tr>					
				<td><label class="lab">录入人：</label>${bview.loanInfo.loanCustomerService}</td>
				<td><label class="lab">客户经理：</label>${bview.loanInfo.loanManagerName}</td>
				<td><label class="lab">备注：</label>${bview.loanRemark.remark}</td>
			</tr>				
		</table>
	</div>	
	<c:if test="${bview.loanCoborrower != null && fn:length(bview.loanCoborrower)>0}">
		<h4 class="f14 pl10 mt10">共同借款人</h4>
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
					<c:forEach var="coboList" items="${bview.loanCoborrower }" varStatus="stu">
						<c:if test="${coboList.coboCertNum != null && coboList.coboCertNum != ''}">
							<tr>
								<td>${stu.count }</td>
								<td>${coboList.coboName }</td>
								<td>${coboList.coboCertNum }</td>
								<td>${coboList.coboMobile }</td>												
							</tr>								
						</c:if>
					</c:forEach>
					<c:if test="${bview.loanCoborrower==null || fn:length(bview.loanCoborrower)==''}">
						<tr>
							<td colspan="4" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
	</c:if>
	<c:if test="${bview.loanCreditInfoList[0].creditMortgageGoods != '' && bview.loanCreditInfoList[0].creditMortgageGoods != null}">	
	   	<h4 class="f14 pl10 mt10">信用资料</h4>
			<table class="table table-hover table-bordered table-condensed">
				<thead>
					<tr>
						<th>序号</th>
						<th>抵押类型</th>
						<th>抵押物品</th>
						<th>机构名称</th>					
						<th>贷款额度(¥)</th>
						<th>每月供额度(¥)</th>
						<th>贷款余额(¥)</th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach var="credit" items="${bview.loanCreditInfoList }" varStatus="str">
						<tr>
							<td>${str.count }</td>
							<td>${credit.dictMortgageTypeLabel}</td>
							<td>${credit.creditMortgageGoods }</td>
							<td>${credit.orgCode }</td>
							<td><fmt:formatNumber value="${credit.creditLoanLimit == null ? 0 : credit.creditLoanLimit }" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${credit.creditMonthsAmount == null ? 0 : credit.creditMonthsAmount }" pattern="#,##0.00" /></td>
							<td><fmt:formatNumber value="${credit.creditLoanBlance == null ? 0 : credit.creditLoanBlance }" pattern="#,##0.00" /></td>							
						</tr>								
					</c:forEach>
					<c:if test="${bview.loanCreditInfoList==null || fn:length(bview.loanCreditInfoList)==0}">
						<tr>
							<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
	</c:if>				
	<c:if test="${bview.customerLoanCompany.compName != null}">
		<h2 class="f14 pl10 mt20">职业信息/公司资料</h2>
		<div class="box2">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">名称：</label>${bview.customerLoanCompany.compName }</td>
					<td>
						<label class="lab">地址：</label>
						${bview.customerLoanCompany.comProvinceName }
						${bview.customerLoanCompany.comCityName }
						${bview.customerLoanCompany.comArerName }
						${bview.customerLoanCompany.compAddress }
					</td>					
					<td><label class="lab">职务：</label>${bview.customerLoanCompany.compPostLabel}</td>		
				</tr>
				<tr>
					<td><label class="lab">入职时间：</label><fmt:formatDate value="${bview.customerLoanCompany.compEntryDay }" pattern="yyyy-MM-dd"/></td>
					<td><label class="lab">单位类型：</label>${bview.customerLoanCompany.dictCompTypeLabel}</td>
					<td><label class="lab">单位电话：</label>${bview.customerLoanCompany.compTel }</td>				
				</tr>
				<tr>
					<td><label class="lab">月收入(元)：</label><fmt:formatNumber value="${bview.customerLoanCompany.compSalary == null ? 0 : bview.customerLoanCompany.compSalary}" pattern="#,##0.00" /></td>
					<td><label class="lab">每月支薪日：</label>${bview.customerLoanCompany.compSalaryDay }</td>
					<td><label class="lab">累积工作年限：</label>${bview.customerLoanCompany.compWorkExperience }</td>															
				</tr>				
			</table>
		</div>		
	</c:if>	
	<c:if test="${bview.customerLoanHouseList[0].id != null && bview.customerLoanHouseList[0].id != ''}">
		<h4 class="f14 pl10 mt10">房产资料</h4>
			<table class="table table-hover table-bordered table-condensed">
				<thead >
					<tr>
						<th>序号</th>
						<th>房产具体信息</th>					
						<th>建筑年份</th>								
						<th>购买方式</th>
						<th>购买日期</th>
						<th>房屋面积</th>	
						<th>是否抵押</th>								
					</tr>
				</thead>
				<tbody>
					<c:forEach var="hose" items="${bview.customerLoanHouseList }" varStatus="stu">
						<tr>
							<td>${stu.count }</td>
							<td>${hose.houseProvinceName}${hose.houseCityName}${hose.houseAreaName}${hose.houseAddress}</td>
							<td><fmt:formatDate value="${hose.houseCreateDay }" pattern="yyyy-MM-dd"/></td>
							<td>${hose.houseBuywayLabel}</td>
							<td><fmt:formatDate value="${hose.houseBuyday }" pattern="yyyy-MM-dd"/></td>
							<td><fmt:formatNumber value="${hose.houseBuilingArea == null ? 0 : hose.houseBuilingArea}" pattern="#,##0.00" ></fmt:formatNumber></td>
							<td>${hose.housePledgeFlagLabel}</td>
						</tr>
					</c:forEach>
				</tbody>							
			</table>		
	</c:if>
	<h4 class="f14 pl10 mt10">联系人资料</h4>
		<table class="table table-hover table-bordered table-condensed">
			<thead>
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>关系类型</th>
					<th>和本人关系</th>
					<th>工作单位</th>
					<th>家庭或工作单位详细地址</th>
					<th>单位电话</th>
					<th>手机号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="cust" items="${bview.customerContactList }" varStatus="str">
					<tr>
						<td>${str.count}</td>
						<td>${cust.contactName }</td>
						<td>${cust.relationTypeLabel}</td>
						<td>${cust.contactRelationLabel}</td>
						<td>${cust.contactSex }</td>
						<td>${cust.contactNowAddress }</td>
						<td>${cust.contactUnitTel }</td>
						<td>${cust.contactMobile }</td>
					</tr>								
				</c:forEach>
				<c:if test="${bview.customerContactList==null || fn:length(bview.customerContactList)==0}">
					<tr>
						<td colspan="7" style="text-align: center;">没有符合条件的数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	<h2 class="f14 pl10 mt20">银行卡资料</h2>
	<div class="box2">
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td><label class="lab">银行卡/折 户名：</label>${bview.loanBank.bankAccountName}</td>
				<td><label class="lab">授权人：</label>${bview.loanBank.bankAuthorizer}</td>
				<td><label class="lab">签约平台：</label>${bview.loanBank.bankSigningPlatformName}</td>					
			</tr>
			<tr>
				<td><label class="lab">开户行名称：</label>${bview.loanBank.bankNameLabel}</td>
				<td><label class="lab">开户支行：</label>${bview.loanBank.bankBranch}</td>
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