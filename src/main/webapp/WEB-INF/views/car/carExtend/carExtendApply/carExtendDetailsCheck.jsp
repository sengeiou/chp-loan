<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>展期信息查看</title>
<meta name="decorator" content="default" />
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script>
imageUrl='${imageurl}';
$(document).ready(function(){
$("#ImageData").click(function(){
	art.dialog.open(imageUrl/* 'http://10.168.230.116:8080/SunIAS/SunIASRequestServlet.do?UID=admin&PWD=111&AppID=AB&UserID=zhangsan&UserName=zhangsan&OrgID=1&OrgName=fenbu&info1=BUSI_SERIAL_NO:400000317152215;OBJECT_NAME:SRCJ;QUERY_TIME:20150611;FILELEVEL:1,3;RIGHT:1111111' */, {
	title: "客户影像资料",	
	top: 80,
	lock: true,
	    width: 1000,
	    height: 450,
	},false);	
	});
});
</script>
</head>

<body>
	<div class="pt10 pb10 tright pr30 title">
		<input type="button" class="btn btn-small" id="ImageData" value="查看影像">
		<input type="button" class="btn btn-small"  onclick="history.go(-1);" value="返回">
	</div>
	<div class="body_r">
	        <h2 class="f14 pl10 mt20">历史展期信息</h2>
    <div class="box2 mb10">
       <table class="table2" border="1" width="100%">
		<tr>
            <th>合同编号</th>
            <th>合同金额(元)</th>
            <th>借款期限(天)</th>
            <th>合同期限</th>
            <th>降额(元)</th>
		</tr>
		<c:if test="${carContracts != null}">
		<c:forEach items="${carContracts}" var="c">
        <tr>
            <td>${c.contractCode }</td>
            <td>
            <fmt:formatNumber value="${c.contractAmount == null ? 0 : c.contractAmount }" pattern="#,##0.00" />
            </td>
            <td>${c.contractMonths }</td>
            <td><fmt:formatDate value="${c.contractFactDay}" pattern="yyyy-MM-dd"/>~<fmt:formatDate value="${c.contractEndDay}" pattern="yyyy-MM-dd"/></td>
            <td><%-- <c:choose>
          			<c:when test="${c.derate == null}">
          				0.00
          			</c:when>
          			<c:otherwise>
          				<fmt:formatNumber value="${c.derate}" pattern="0.00" />
          			</c:otherwise>
          		</c:choose> --%>
          		<fmt:formatNumber value="${c.derate == null ? 0 : c.derate }" pattern="#,##0.00" />
          		</td>
         </tr>
		 </c:forEach>  
		 </c:if>
        </table>
    </div>
     		<h2 class="f14 pl10 mt20">借款信息</h2>
     <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		   <tr>
                <td><label class="lab">合同编号：</label>${contractNo}</td>
           </tr>
           <tr>
                <td><label class="lab">申请借款额度：</label>
                	<fmt:formatNumber value="${carLoanInfo.loanApplyAmount == null ? 0 : carLoanInfo.loanApplyAmount }" pattern="#,##0.00" />
                </td>
                <td><label class="lab">产品类型：</label>${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',carLoanInfo.dictProductType)}</td>
                <td><label class="lab">申请借款期限(天数)： </label>${carLoanInfo.loanMonths}
                	<c:if test="${carLoanInfo.loanMonths != null}">天</c:if>
                </td>
           </tr>
           <tr>
           		<td><label class="lab">借款人姓名：</label>${carLoanInfo.loanCustomerName}</td>
           		<td><label class="lab">共同借款人：</label>${carLoanCoborrowers }</td>
           		<td><label class="lab">中间人：</label>${middleman}</td>
           </tr>
           <tr><td>
                <c:choose>
                	<c:when test="${carLoanInfo.dictProductType == 'CJ01' }">
                	<label class="lab">平台及流量费：</label>
           			<fmt:formatNumber value="${carLoanInfo.flowFee == null ? 0 : carLoanInfo.flowFee }" pattern="#,##0.00" />
                	</c:when>
                	<c:otherwise>
                	<label class="lab">停车费：</label>
           			<fmt:formatNumber value="${carLoanInfo.parkingFee == null ? 0 : carLoanInfo.parkingFee }" pattern="#,##0.00" />
                	</c:otherwise>
                </c:choose>
                </td>
           		<td><label class="lab">展期原因：</label>${carLoanInfo.dictLoanUse}</td>
           </tr>
           <tr>
           		<td><label class="lab">团队经理：</label>${teamManagerName}</td>
           		<td><label class="lab">客户经理：</label>${managerName}</td>
           		<td><label class="lab">客户来源：</label>${carCustomer.dictCustomerSource2}</td>
           </tr>
           <tr>
           		<td><label class="lab">门店名称：</label>${carLoanInfo.storeName}</td>
           		<td><label class="lab">管辖城市：</label>${cityName}</td>
           		<td><label class="lab">客户人法查询结果：</label>${carApplicationInterviewInfo.queryResult}</td>
           </tr>
           <tr>
           		<td><label class="lab">申请日期：</label><fmt:formatDate value='${carLoanInfo.loanApplyTime}' pattern="yyyy-MM-dd"/></td>
           </tr>
        </table>
    </div>
   <h2 class="f14 pl10">客户信息</h2>
	  <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		     <tr>
                <td><label class="lab">客户姓名：</label>${carCustomer.customerName}</td>
               <td><label class="lab">性别：</label>
               	${carCustomer.customerSex}
            </tr>
			<tr>
			 <td><label class="lab">身份证号：</label>${carCustomer.customerCertNum }</td>
			 <td><label class="lab">证件有效期：</label>
			 			<c:if test="${carCustomer.idStartDay!=null && carCustomer.idEndDay==null}">长期</c:if>
						<c:if test="${carCustomer.idStartDay!=null && carCustomer.idEndDay!=null}">
						<fmt:formatDate value='${carCustomer.idStartDay}' pattern="yyyy-MM-dd"/>~<fmt:formatDate value='${carCustomer.idEndDay}' pattern="yyyy-MM-dd"/></c:if>
			 </td>
			</tr>
            <tr>
				<td><label class="lab">手机号码：</label>${carCustomerBase.customerMobilePhone }</td>
                <td><label class="lab">出生日期：</label><fmt:formatDate value='${carCustomer.customerBirthday}' pattern="yyyy-MM-dd"/></td>
            </tr>
			<tr>
			 <td><label class="lab">暂住证：</label>${carCustomer.customerTempPermit}</td>
					<td><label class="lab">住房性质：</label>${carCustomer.customerHouseHoldProperty}</td> 
			</tr>
			 <tr>
                <td colspan="2"><label class="lab">户籍地址：</label>
                <sys:carAddressShow detailAddress="${carCustomer.customerRegisterAddressView }"></sys:carAddressShow>
                </td>
            </tr>
			<tr>
                <td colspan="2"><label class="lab">本市地址：</label>
                <sys:carAddressShow detailAddress="${carCustomer.customerAddressView }"></sys:carAddressShow>
				</td>
			</tr>
			<tr>
				<td><label class="lab">信用额度：</label>
					<fmt:formatNumber value="${carCustomer.creditLine == null ? 0 : carCustomer.creditLine }" pattern="#,##0.00" />
				</td>
                <td><label class="lab">起始居住时间：</label><fmt:formatDate value='${carCustomer.customerFirstLivingDay}' pattern="yyyy-MM-dd"/></td>
                <td><label class="lab">初来本市年份：</label><fmt:formatDate value='${carCustomer.customerFirtArriveYear}' pattern="yyyy-MM-dd"/></td> 
            </tr>
			<tr>
			<td><label class="lab">供养亲属人数：</label>${carCustomer.customerFamilySupport }</td>
                <td><label class="lab">婚姻状况：</label>${carCustomer.dictMarryStatus}</td>
		    <td><label class="lab">最高学历：</label>${carCustomer.dictEducation}</td>
            </tr>
			<tr>
			<td><label class="lab">邮箱：</label>${carCustomer.customerEmail }</td>
			<td><label class="lab">有无子女：</label>${carCustomer.customerHaveChildren}</td>
			</tr>
        </table>
    </div>
    <h2 class="f14 pl10 mt20">工作信息</h2>
	<div class="box2 ">
         	<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
                <tr>
                <td><label class="lab">单位名称：</label>${carCustomerCompany.companyName }</td>
                <td><label class="lab">所属部门：</label>${carCustomerCompany.dictDepartment }</td>
                <td><label class="lab">成立时间：</label><fmt:formatDate value='${carCustomerCompany.establishedTime}' pattern="yyyy-MM-dd"/></td>
            </tr>
            <tr>
                <td colspan="3"><label class="lab">单位地址：</label>${carCustomerCompany.companyAddress }
            </tr>
            <tr>
			<td><label class="lab">单位电话：</label>${carCustomerCompany.workTelephone }</td>
                <td><label class="lab">职位级别：</label>${carCustomerCompany.dictPositionLevel }</td>
                <td><label class="lab">月均薪资(元)：</label>
                	<fmt:formatNumber value="${carCustomerCompany.monthlyPay == null ? 0 : carCustomerCompany.monthlyPay }" pattern="#,##0.00" />
                </td>
            </tr>
            <tr>
			<td><label class="lab">其他收入：</label>${carCustomerCompany.isOtherRevenue}</td>
                <td><label class="lab">起始服务时间：</label><fmt:formatDate value='${carCustomerCompany.firstServiceDate}' pattern="yyyy-MM-dd"/></td>
                <td><label class="lab">单位性质：</label>
				${carCustomerCompany.dictUnitNature}</td>
            </tr>
			<tr>
			 <td><label class="lab">企业类型：</label>${carCustomerCompany.dictEnterpriseNature}</td>
            </tr>
              </table>   
        </div>
        <h2 class="f14 pl10 mt20">联系人资料</h2>
    	<div class="box2 mb10">
       	<table class="table2" border="1" width="100%">
			<tr>
           	 <th>姓名</th>
             <th>和本人关系</th>
             <th>工作单位</th>
       		 <th>工作单位或家庭详细地址</th>
             <th>单位电话</th>
           	 <th>联系方式</th>
			</tr>
		<c:forEach items="${carCustomerContactPersons}" var="c">
        <tr>
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
        <h2 class="f14 pl10 mt20">客户开户信息</h2>
    <div class="box2">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">授权人：</label>${carCustomer.customerName }</td>
                <td><label class="lab">账户名称：</label>${CarCustomerBankInfo.bankAccountName }</td>
                <td><label class="lab">开卡省市：</label>${CarCustomerBankInfo.bankProvinceCity }</td>
            </tr>
            <tr>
            	<td><label class="lab">开卡行：</label>${CarCustomerBankInfo.cardBank }</td>
                <td><label class="lab">开户支行：</label>${CarCustomerBankInfo.applyBankName }</td>
                <td><label class="lab">银行账号：</label>${CarCustomerBankInfo.bankCardNo }</td>
            </tr>
			<tr>
                <td><label class="lab">确认银行账号：</label>${CarCustomerBankInfo.bankCardNo }</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>