<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html >
<head>
<title>评估师已录入</title>
<meta name="decorator" content="default" />
</head>
<body>
 <h2 class=" f14 pl10 ">客户基本信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
<td style="width:32%">
  <label class="lab">客户姓名：</label>${carCustomer.customerName}
</td>
<td style="width:32%">
  <label class="lab">证件类型：</label>
  ${carCustomer.dictCertType}
</td>
<td style="width:32%">
   <label class="lab">证件号码：</label>${carCustomer.customerCertNum}
</td>
   </tr>
   <tr>
<td style="width:32%">
   <label class="lab">手机号码：</label>${carCustomerBase.customerMobilePhone}
   <span id="blackTip" style="color: red;"></span>
</td>
<td style="width:32%">
  <label class="lab">学历：</label>
  ${carCustomer.dictEducation}
</td>
<td style="width:32%">
  <label class="lab">婚姻状况：</label>
  ${carCustomer.dictMarryStatus}
</td>
 </tr>
 <tr>
 	<td style="width:32%">
    		 <label class="lab"><span style="color: #ff0000"></span>证件有效期：</label>
			<fmt:formatDate value='${carCustomer.idStartDay}' pattern="yyyy-MM-dd"/>~
		    <c:if test="${carCustomer.idEndDay==null}"><input type="checkbox" checked="checked" disabled="disabled" />长期</c:if>
			<c:if test="${carCustomer.idEndDay!=null}">
			<fmt:formatDate value='${carCustomer.idEndDay}' pattern="yyyy-MM-dd"/></c:if>
      
     	</td>
      </tr>
      <tr>
      	<td style="width:32%">
   			<label class="lab">预计借款金额：</label>
   			<fmt:formatNumber value="${carCustomerConsultation.consLoanAmount == null ? 0 : carCustomerConsultation.consLoanAmount }" pattern="#,##0.00" />
   			<c:if test="${carCustomerConsultation.consLoanAmount != null}">元</c:if>
		</td>
      </tr>
 </table>
</div>
 <h2 class=" f14 pl10 mt20">车辆信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
 <tr>
   <td style="width:32%">
    <label class="lab">车辆品牌与型号：</label>${carVehicleInfo.vehicleBrandModel}
   </td>
 </tr>
 <tr>
   <td style="width:32%">
    <label class="lab">车牌号码：</label>${carVehicleInfo.plateNumbers}
   </td>
   <td style="width:32%">
   			<label class="lab">建议借款金额：</label>
   			<fmt:formatNumber value="${carVehicleInfo.suggestLoanAmount == null ? 0 : carVehicleInfo.suggestLoanAmount }" pattern="#,##0.00" />
   			<c:if test="${carVehicleInfo.suggestLoanAmount != null}">元</c:if>
   </td>
   <td style="width:32%">
    <label class="lab">评估师姓名：</label>${carVehicleInfo.appraiserName}
   </td>
</tr>
<tr>
   <td style="width:32%">
   			<label class="lab">评估金额：</label>
   			<fmt:formatNumber value="${carVehicleInfo.storeAssessAmount == null ? 0 : carVehicleInfo.storeAssessAmount }" pattern="#,##0.00" />
   			<c:if test="${carVehicleInfo.storeAssessAmount != null}">元</c:if>
   </td>
  <td style="width:32%">
    <label class="lab">商业险到期日：</label>
    <fmt:formatDate value='${carVehicleInfo.commercialMaturityDate}' pattern="yyyy-MM-dd"/>
   </td>
   <td style="width:32%">
   			<label class="lab">同类市场价格：</label>
   			<fmt:formatNumber value="${carVehicleInfo.similarMarketPrice == null ? 0 : carVehicleInfo.similarMarketPrice }" pattern="#,##0.00" />
   			<c:if test="${carVehicleInfo.similarMarketPrice != null}">元</c:if>
   </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab">出厂日期：</label><fmt:formatDate value='${carVehicleInfo.factoryDate}' pattern="yyyy-MM-dd"/>
    </td>
	<td style="width:32%">
    <label class="lab">交强险到期日：</label><fmt:formatDate value='${carVehicleInfo.strongRiskMaturityDate}' pattern="yyyy-MM-dd"/>
    </td>
	<td style="width:32%">
    <label class="lab">车年到期日：</label><fmt:formatDate value='${carVehicleInfo.annualCheckDate}' pattern="yyyy-MM-dd"/>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab">车架号：</label>${carVehicleInfo.frameNumber}
    </td>
    <td style="width:32%">
    <!-- <label class="lab">车辆厂牌型号：</label>${carVehicleInfo.vehiclePlantModel} -->
    </td>
    <td style="width:32%">
    <label class="lab">首次登记日期：</label><fmt:formatDate value='${carVehicleInfo.firstRegistrationDate}' pattern="yyyy-MM-dd"/>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab">表征里程：</label>${carVehicleInfo.mileage}
    <c:if test="${carVehicleInfo.mileage != null}"><label>公里</label></c:if>
    </td>
    <td style="width:32%">
    <label class="lab">排气量：</label>${carVehicleInfo.displacemint}
    <c:if test="${carVehicleInfo.displacemint != null}"><label>升</label></c:if>
    </td>
    <td style="width:32%">
    <label class="lab">车身颜色：</label>${carVehicleInfo.carBodyColor}
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab">变速器：</label>${carVehicleInfo.variator}
    </td>
    <td style="width:32%">
    <label class="lab">发动机号：</label>${carVehicleInfo.engineNumber}
    </td>
    <td style="width:32%">
    <label class="lab">过户次数：</label>${carVehicleInfo.changeNum}
    <c:if test="${carVehicleInfo.changeNum != null}"><label>次</label></c:if>
    </td>
</tr>
<tr>
	<td style="width:32%">
    <label class="lab">权属证书编号：</label>${carVehicleInfo.ownershipCertificateNumber}
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab">改装情况：</label> ${carVehicleInfo.modifiedSituation}
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab">外观检测：</label>${carVehicleInfo.outerInspection}
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab">违章及事故情况：</label>${carVehicleInfo.illegalAccident}
    </td>
</tr>
<tr>
	<td style="width:32%" colspan="2">
    <label class="lab">车辆评估意见：</label>${carVehicleInfo.vehicleAssessment}
    </td>
</tr>
 </table>
</div>
</body>
</html>