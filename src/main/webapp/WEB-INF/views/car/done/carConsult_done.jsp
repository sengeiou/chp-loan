<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
</head>
<body>	
 <h2 class=" f14 pl10 ">基本信息</h2>
  <div class="content box2">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
  	<tr>
		<td style="width:32%">
			<label class="lab">客户姓名：</label>${carCustomerBase.customerName}
		</td>
		
    	<td><label class="lab">性别：</label>${carCustomerBase.customerSex}
   	</tr>
	<tr>
		<td style="width:32%">
   			<label class="lab">手机号码：</label>${carCustomerBase.customerMobilePhone}
		</td>
		
		<td style="width:32%">
  			<label class="lab"><span style="color: #ff0000">
  				</span>证件类型：</label>${carCustomerBase.dictCertType}
		</td>
   </tr>
   <tr>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">
   				</span>证件号码：</label>${carCustomerBase.customerCertNum}
		</td>
		<td style="width:32%">
    		 <label class="lab"><span style="color: #ff0000"></span>证件有效期：</label>
			<fmt:formatDate value='${carCustomer.idStartDay}' pattern="yyyy-MM-dd"/>~
		    <c:if test="${carCustomer.idEndDay==null}">长期</c:if>
			<c:if test="${carCustomer.idEndDay!=null}">
			<fmt:formatDate value='${carCustomer.idEndDay}' pattern="yyyy-MM-dd"/></c:if>
      
     	</td>
     	
 	</tr>
 	<tr>
 		<td colspan="2">
 			<label class="lab">现住址：</label>
  				${carCustomer.customerAddress}
   		</td>	
 	</tr>
 	<tr>
   		<td>
    		<label class="lab"><span style="color: #ff0000">
    		</span>学历：</label>
    		${carCustomer.dictEducation}
		</td>
		<td>
    		<label class="lab"><span style="color: #ff0000"></span>婚姻状态：</label>
    		${carCustomer.dictMarryStatus}
		</td>
      </tr>
 </table>
</div>
 <h2 class=" f14 pl10">车借信息</h2>
  <div class="content box2">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
   		<td style="width:32%">
   			<label class="lab">车辆品牌与型号：</label>${carVehicleInfo.vehicleBrandModel}
		</td>
   		<td style="width:32%">
   			<label class="lab">车牌号码：</label>${carVehicleInfo.plateNumbers}
		</td>
	</tr>
	<tr>		
		<td style="width:32%">
   			<label class="lab">预计借款金额：</label>
   			<fmt:formatNumber value="${carCustomerConsultation.consLoanAmount == null ? 0 : carCustomerConsultation.consLoanAmount }" pattern="#,##0.00" />
   			<c:if test="${carCustomerConsultation.consLoanAmount != null}">元</c:if>
		</td>
		<td style="width:32%">
   			<label class="lab">预计到店时间：</label><fmt:formatDate value='${carCustomerConsultation.planArrivalTime}' pattern="yyyy-MM-dd"/>
		</td>
	</tr>
	<tr>
  		<td style="width:32%">
	    <label class="lab"><span style="color: #ff0000"></span>团队经理：</label>${teamManagerName}
	   </td>
  		<td colspan="2">
     		<label class="lab">客户经理：</label>${managerName}
   		</td>
	</tr>
 </table>
</div>
</body>
</html>