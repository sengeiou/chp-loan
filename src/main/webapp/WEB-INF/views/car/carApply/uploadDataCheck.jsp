<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>上传资料</title>
<meta name="decorator" content="default" />
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<link href="/loan/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="/loan/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-migrate-1.1.1.js" type="text/javascript"></script>
</head>

<body>
	<div class="body_r"  style="overflow: auto;">
   <h2 class="f14 pl10">客户信息</h2>
	  <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		     <tr>
                <td><label class="labl">客户姓名：</label>${carCustomer.customerName}</td>
               <td><label class="labl">性别：</label>
               	${carCustomer.customerSex}
            </tr>
			<tr>
			 <td><label class="labl">身份证号：</label>${carCustomer.customerCertNum }</td>
			 <td><label class="labl">证件有效期：</label>
			 			<fmt:formatDate value='${carCustomer.idStartDay}' pattern="yyyy-MM-dd"/>~
			 			<c:if test="${carCustomer.idEndDay==null}">长期</c:if>
						<c:if test="${carCustomer.idEndDay!=null}">
						<fmt:formatDate value='${carCustomer.idEndDay}' pattern="yyyy-MM-dd"/></c:if>
			 </td>
			</tr>
            <tr>
				<td><label class="labl">手机号码：</label>${carCustomerBase.customerMobilePhone }</td>
                <td><label class="labl">出生日期：</label><fmt:formatDate value='${carCustomer.customerBirthday}' pattern="yyyy-MM-dd"/></td>
            </tr>
			<tr>
			 <td><label class="labl">暂住证：</label>${carCustomer.customerTempPermit}</td>
			 <td><label class="labl">住房性质：</label>${carCustomer.customerHouseHoldProperty}</td> 
			 <c:if test="${carCustomer.customerHouseHoldProperty == '商业按揭房' || carCustomer.customerHouseHoldProperty == '公积金按揭购房'}">
			 	<td><label class="labl">月供金额：</label>
			 	<fmt:formatNumber value="${carCustomer.jydzzmRentMonth == null ? 0 : carCustomer.jydzzmRentMonth }" pattern="0.00" />元
			 	</td> 
			 </c:if>	
			 <c:if test="${carCustomer.customerHouseHoldProperty == '租用'}">
			 	<td><label class="labl">月租金：</label><fmt:formatNumber value="${carCustomer.jydzzmRentMonth == null ? 0 : carCustomer.jydzzmRentMonth }" pattern="0.00" />元</td> 
			 </c:if>		
			</tr>
			 <tr>
                <td colspan="2"><label class="labl">户籍地址：</label>
                <sys:carAddressShow detailAddress="${carCustomer.customerRegisterAddressView }"></sys:carAddressShow>
                </td>
            </tr>
			<tr>
                <td colspan="2"><label class="labl">本市地址：</label>
                 <sys:carAddressShow detailAddress="${carCustomer.customerAddressView }"></sys:carAddressShow></td>
				<td><label class="labl">本市电话：</label>${carCustomer.cityPhone }</td>							
			</tr>
			<tr>
				<td><label class="labl">信用额度：</label>
				<fmt:formatNumber value="${carCustomer.creditLine == null ? 0 : carCustomer.creditLine }" pattern="0.00" />元
				</td>
                <td><label class="labl">起始居住时间：</label><fmt:formatDate value='${carCustomer.customerFirstLivingDay}' pattern="yyyy-MM-dd"/></td>
                <td><label class="labl">初来本市年份：</label><fmt:formatDate value='${carCustomer.customerFirtArriveYear}' pattern="yyyy-MM-dd"/></td> 
            </tr>
			<tr>
			<td><label class="labl">供养亲属人数：</label>${carCustomer.customerFamilySupport }</td>
                <td><label class="labl">婚姻状况：</label>${carCustomer.dictMarryStatus}</td>
		    <td><label class="labl">最高学历：</label>${carCustomer.dictEducation}</td>
            </tr>
			<tr>
			<td><label class="labl">邮箱：</label>${carCustomer.customerEmail }</td>
			<td><label class="labl">有无子女：</label>${carCustomer.customerHaveChildren}</td>
			</tr>
        </table>
    </div>
       <h2 class="f14 pl10 mt20">车辆信息</h2>
    <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td width="32%"><label class="labl">车牌号码：</label>${carVehicleInfo.plateNumbers }</td>
                <td style="width:32%">
   					<label class="labl">建议借款金额：</label>
   					<fmt:formatNumber value="${carVehicleInfo.suggestLoanAmount == null ? 0 : carVehicleInfo.suggestLoanAmount }" pattern="#,##0.00" />
   					<c:if test="${carVehicleInfo.suggestLoanAmount != null}">元</c:if>
  				</td>
                <td><label class="labl">评估师姓名：</label>${carVehicleInfo.appraiserName }</td>
            </tr>
            <tr>
                <td style="width:32%">
   					<label class="labl">评估金额：</label>
   					<fmt:formatNumber value="${carVehicleInfo.storeAssessAmount == null ? 0 : carVehicleInfo.storeAssessAmount }" pattern="#,##0.00" />
   					<c:if test="${carVehicleInfo.storeAssessAmount != null}">元</c:if>
   				</td>
                <td><label class="labl">商业险到期日：</label><fmt:formatDate value='${carVehicleInfo.commercialMaturityDate}' pattern="yyyy-MM-dd"/></td>
                <td style="width:32%">
   					<label class="labl">同类市场价格：</label>
   					<fmt:formatNumber value="${carVehicleInfo.similarMarketPrice == null ? 0 : carVehicleInfo.similarMarketPrice }" pattern="#,##0.00" />
   					<c:if test="${carVehicleInfo.similarMarketPrice != null}">元</c:if>
   				</td>
            </tr>
			<tr>
                <td width="32%"><label class="labl">出厂日期：</label><fmt:formatDate value='${carVehicleInfo.factoryDate}' pattern="yyyy-MM-dd"/></td>
                <td width="32%"><label class="labl">交强险到期日：</label><fmt:formatDate value='${carVehicleInfo.strongRiskMaturityDate}' pattern="yyyy-MM-dd"/></td>
                <td><label class="labl">车年到期日：</label><fmt:formatDate value='${carVehicleInfo.annualCheckDate}' pattern="yyyy-MM-dd"/></td>
            </tr>
			<tr>
                <td width="32%"><label class="labl">车架号：</label>${carVehicleInfo.frameNumber }</td>
                <td width="32%"><label class="labl">车辆厂牌型号：</label>${carVehicleInfo.vehiclePlantModel }</td>
                <td><label class="labl">首次登记日期：</label><fmt:formatDate value='${carVehicleInfo.firstRegistrationDate}' pattern="yyyy-MM-dd"/></td>
            </tr>
			<tr>
                <td style="width:32%">
    				<label class="labl">表征里程：</label>${carVehicleInfo.mileage}
    				<c:if test="${carVehicleInfo.mileage != null}"><label>公里</label></c:if>
    			</td>
                <td style="width:32%">
    				<label class="labl">排气量：</label>${carVehicleInfo.displacemint}
   	 				<c:if test="${carVehicleInfo.displacemint != null}"><label>升</label></c:if>
    			</td>
                <td><label class="labl">车身颜色：</label>${carVehicleInfo.carBodyColor }</td>
            </tr>
			<tr>
                <td width="32%"><label class="labl">变速器：</label>${carVehicleInfo.variator }</td>
                <td width="32%"><label class="labl">发动机号：</label>${carVehicleInfo.engineNumber }</td>
                <td style="width:32%">
    				<label class="labl">过户次数：</label>${carVehicleInfo.changeNum}
    				<c:if test="${carVehicleInfo.changeNum != null}"><label>次</label></c:if>
    			</td>
            </tr>
			<tr>
			    <td><label class="labl">权属证书编号：</label>${carVehicleInfo.ownershipCertificateNumber }</td>
			</tr>
			<tr>
                <td width="32%" colspan="2"><label class="labl">改装情况：</label>${carVehicleInfo.modifiedSituation }</td>
                
            </tr>
			<tr>
                <td width="32%" colspan="2"><label class="labl">外观检测：</label>${carVehicleInfo.outerInspection }</td>
                
            </tr>
			<tr>
                <td width="32%" colspan="2"><label class="labl">违章及事故情况：</label>${carVehicleInfo.illegalAccident }</td>
                
            </tr>
			<tr>
                <td width="32%" colspan="2"><label class="labl">车辆评估意见：</label>${carVehicleInfo.vehicleAssessment }</td>
            </tr>
        </table>
    </div>
    <h2 class="f14 pl10 mt20">工作信息</h2>
	<div class="box1 mb10">
         	<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                <td><label class="labl">单位名称：</label>${carCustomerCompany.companyName }</td>
                <td><label class="labl">所属部门：</label>${carCustomerCompany.dictDepartment }</td>
                <td><label class="labl">成立时间：</label><fmt:formatDate value='${carCustomerCompany.establishedTime}' pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td colspan="3"><label class="labl">单位地址：</label><sys:carAddressShow detailAddress="${addressCompany }${carCustomerCompany.companyAddress}"></sys:carAddressShow>  
            </tr>
            <tr>
			<td><label class="labl">单位电话：</label>${carCustomerCompany.workTelephone }</td>
                <td><label class="labl">职位级别：</label>${carCustomerCompany.dictPositionLevel}</td>
                <td style="width:32%">
   					<label class="labl">月均薪资(元)：</label>
   					<fmt:formatNumber value="${carCustomerCompany.monthlyPay == null ? 0 : carCustomerCompany.monthlyPay }" pattern="#,##0.00" />
   					<c:if test="${carCustomerCompany.monthlyPay != null}">元</c:if>
   				</td>
            </tr>
            <tr>
			<td><label class="labl">其他收入：</label>${carCustomerCompany.isOtherRevenue}</td>
                <td><label class="labl">起始服务时间：</label><fmt:formatDate value='${carCustomerCompany.firstServiceDate}' pattern="yyyy-MM-dd"/></td>
                <td><label class="labl">单位性质：</label>
				${carCustomerCompany.dictUnitNature}</td>
            </tr>
			<tr>
			 <td><label class="labl">企业类型：</label>${carCustomerCompany.dictEnterpriseNature}</td>
               
            </tr>
              </table>   
        </div>
        <h2 class="f14 pl10 mt20">联系人资料</h2>
    <div class="box2 mb10">
       <table class="table2" border="1" width="100%">
		<tr>
            <th width="8%">姓名</th>
            <th width="8%">和本人关系</th>
            <th width="32%">工作单位</th>
            <th width="32%">工作单位或家庭详细地址</th>
            <th width="10%">单位电话</th>
            <th width="10%">联系方式</th>
		</tr>
		<c:forEach items="${carCustomerContactPersons}" var="c">
        <tr style="word-break: break-all;">
            <td>${c.contactName }</td>
            <td>${c.dictContactRelationName }</td>
            <td>${c.contactUint }</td>
            <td>${c.dictContactNowAddress }</td>
            <td>${c.compTel }</td>
            <td>${c.contactUnitTel }</td>
         </tr>
		 </c:forEach>  
        </table>
    </div>
       <h2 class="f14 pl10 mt20">借款信息</h2>
     <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		   <tr>
                      <td style="width:32%">
   						<label class="labl">申请借款额度：</label>
   						<fmt:formatNumber value="${carLoanInfo.loanApplyAmount == null ? 0 : carLoanInfo.loanApplyAmount }" pattern="#,##0.00" />
   						<c:if test="${carLoanInfo.loanApplyAmount != null}">元</c:if>
   					  </td>
                      <td><label class="labl">借款用途：</label>${carLoanInfo.dictLoanUse}</td>
					 <td><label class="labl">申请借款期限：</label>${carLoanInfo.loanMonths }
						<c:if test="${carLoanInfo.loanMonths != null}">天</c:if>
					 </td>
                  </tr>
           <tr>
                <td><label class="labl">申请日期：</label><fmt:formatDate value='${carLoanInfo.loanApplyTime}' pattern="yyyy-MM-dd"/></td>
                <td><label class="labl">共同借款人：</label>${carLoanInfo.dictLoanCommonRepaymentFlag}</td>
                <td><label class="labl">抵押权人：</label>${carLoanInfo.mortgagee}</td>
            </tr>
			<tr>
                <td><label class="labl">授权人：</label>${CarCustomerBankInfo.bankAccountName }</td>
            </tr>
            <sys:carProductShow facilityCharge="${carLoanInfo.facilityCharge }" parkingFee="${carLoanInfo.parkingFee }" 
                         flowFee="${carLoanInfo.flowFee }"
                         dictGpsRemaining="${carLoanInfo.dictGpsRemaining }"
                         dictIsGatherFlowFee="${carLoanInfo.dictIsGatherFlowFee }" 
                         dictSettleRelend="${carLoanInfo.dictSettleRelend }" productType="${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',carLoanInfo.dictProductType)}"></sys:carProductShow>
        </table>
    </div>
    <c:if test="${carLoanCoborrowers != null }">
        <h2 class="f14 pl10 mt20">共借人信息</h2>
    <c:forEach items="${CoborrowerMap}" var="c">
    <div class="box2"><input type="hidden" value="${c.key.createTime}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td  width="31%" ><label class="labl">共借人姓名：</label>${c.key.coboName }</td>
                <td  width="31%" ><label class="labl">性别：</label>${c.key.dictSex}</td>
            </tr>
            <tr>
                <td><label class="labl">身份证号：</label>${c.key.certNum }</td>
                <td><label class="labl">手机号码：</label>${c.key.mobile }</td>
                <td><label class="labl">婚姻状况：</label>${c.key.dictMarryStatus}</td>
            </tr>
            <tr>
                <td><label class="labl">户籍地址：</label><sys:carAddressShow detailAddress="${c.key.dictHouseholdView}"></sys:carAddressShow></td>
                <td><label class="labl">现住址：</label><sys:carAddressShow detailAddress="${c.key.dictLiveView }"></sys:carAddressShow></td>
                <td><label class="labl">固定电话：</label>${c.key.familyTel }</td>
            </tr>
            <tr>
                <td><label class="labl">有无子女：</label>${c.key.haveChildFlag}</td>
				<td><label class="labl">邮箱：</label>${c.key.email }</td>
            </tr>
            <tr>
                <td colspan="2"><label class="labl">其他所得：</label>
                	<fmt:formatNumber value="${c.key.otherIncome == null ? 0 : c.key.otherIncome }" pattern="#,##0.00" />
   					<c:if test="${c.key.otherIncome != null}">元/每月</c:if>
                </td>
                <td style="width:40%"><label class="labl">房屋出租：</label>
                	<fmt:formatNumber value="${c.key.houseRent == null ? 0 : c.key.houseRent }" pattern="#,##0.00" />
   					<c:if test="${c.key.houseRent != null}">元/每月</c:if>
                </td>
            </tr>

        </table>
		 <table class="table2" border="1" width="100%">
         <c:if test="${c.value != null }">
            <tr>
            <th width="6%">姓名</th>
            <th width="8%">和本人关系</th>
            <th width="22%">工作单位</th>
            <th width="22%">家庭详细地址</th>
            <th width="22%">工作单位详细地址</th>
            <th width="10%">单位电话</th>
            <th width="10%">联系方式</th>
        </tr>
        </c:if>
        <c:forEach items="${c.value}" var="p" >
         <tr style="word-break: break-all;">
            <td>${p.contactName }</td>
            <td>${p.dictContactRelationName }</td>
            <td>${p.contactUint }</td>
            <td>${p.dictContactNowAddress }</td>
            <td>${p.dictContactNowAddress }</td>
            <td>${p.compTel }</td>
            <td>${p.contactUnitTel }</td>
           </tr></c:forEach>
			</table>
			
    </div></c:forEach>
    </c:if>
        <h2 class="f14 pl10 mt20">客户开户及管辖信息</h2>
    <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
				<td><label class="labl">银行卡户名：</label>${CarCustomerBankInfo.bankAccountName }
				<span>
				<input type="checkbox" id="isRareword" <c:if test="${CarCustomerBankInfo.israre == '1'}">checked='checked'</c:if> disabled="disabled" value="${carCustomerBankInfo.israre}" /><lable>是否生僻字</lable>
				</span></td>
                <td><label class="labl">开卡省市：</label>${CarCustomerBankInfo.bankProvinceCity }</td>
                <td><label class="labl">开卡行：</label>${CarCustomerBankInfo.cardBank }</td>
            </tr>
			<tr>
                <td><label class="labl">开户支行：</label>${CarCustomerBankInfo.applyBankName }</td>
                <td><label class="labl">银行账号：</label>${CarCustomerBankInfo.bankCardNo }</td>
                <td><label class="labl">确认银行账号：</label>${CarCustomerBankInfo.bankCardNo }</td>
            </tr>
            <tr>
                <td><label class="labl">客户经理：</label>${managerName}</td>
                <td><label class="labl">团队经理：</label>${teamManagerName}</td>
                <td><label class="labl">面审：</label>${carLoanInfo.reviewMeet }</td>
            </tr>
            <tr>
                <td><label class="labl">门店名称：</label>${carLoanInfo.storeName }</td>
                 <td colspan="2"><label class="labl">管辖城市：</label>${cityName }</td>
            </tr>
            <tr>
                <td><label class="labl">客户来源：</label>${carCustomer.dictCustomerSource2}</td>
                <td colspan="2" style="width:100px;"><label class="labl">客户人法查询结果：</label>${carApplicationInterviewInfo.queryResult }</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>