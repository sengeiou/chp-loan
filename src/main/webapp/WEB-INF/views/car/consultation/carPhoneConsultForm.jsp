<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" /><meta name="author" content="")">
<meta name="renderer" content="webkit"><meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0"><meta http-equiv="Cache-Control" content="no-cache"><meta http-equiv="Cache-Control" content="no-store">
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/font-awesome.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_cerulean/ace.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/bootstrap/3.3.5/js/ace-extra.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>

<script src="/loan/static/bootstrap/3.3.5/js/bootstrap.min.js" type="text/javascript"></script>
<script src="/loan/static/bootstrap/3.3.5/js/ace.min.js" type="text/javascript"></script>
<script src="/loan/static/bootstrap/3.3.5/js/bootstrap-table.js" type="text/javascript"></script>
<link href="/loan/static/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
<link href="/loan/static/bootstrap/3.3.5/css_default/bootstrap-table.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
<link href="/loan/static/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<script src="/loan/static/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="/loan/static/jquery/jquery-migrate-1.1.1.js" type="text/javascript"></script>
<script src="/loan/static/common/mustache.min.js" type="text/javascript"></script>
<script type=text/javascript src="/loan/static/artDialog4.1.7/artDialog.source.js?skin=blue"></script>
<script type=text/javascript src="/loan/static/artDialog4.1.7/plugins/iframeTools.source.js"></script>
<link href="/loan/static/common/jeesite.min.css" type="text/css" rel="stylesheet" />
<script src="/loan/static/common/jeesite.js" type="text/javascript"></script>
<script type="text/javascript">var basePath='/loan/a';</script>
<script type="text/javascript">var ctx = '/loan/a', ctxStatic='/loan/static',context='/loan';</script>
<link href="/loan/static/jquery-select2/3.4/select2.min.css" rel="stylesheet" />

<script src="/loan/static/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
<!-- 1 -->
<script type="text/javascript" src="${context}/js/car/consult/loanLaunch.js"></script>

<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/huijing.js"></script>
<meta name="decorator" content="default" />

<script type="text/javascript">
	$(document).ready(
					function() {
 
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

					});
</script>
</head>
<body>
			
 <form id="carCustomer" class="form-horizontal" action="${ctx}/car/carConsult/saveCustomerConsult" method="post">
 <h2 class=" f14 pl10 ">基本信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
  	<tr>
		<td style="width:32%">
			<label class="lab"><span style="color: #ff0000">*</span>客户姓名：</label>
		  		<input id="customerName" name="carCustomerBase.customerName" class="required input_text178" type="text" value=""/>
		  		<input id="customerCode" name="carCustomerBase.customerCode" type="hidden" value=""/>
		  		<input type="hidden" id="flag" />
		  		<input type="hidden" id="message"/>
		</td>
		
    	<td><label class="lab"><span style="color: #ff0000"></span>性别：</label>
						<c:choose>
							<c:when test="${loanCustomer.customerSex!=null}">
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" name="carCustomerBase.customerSex" value="${item.value}"
										<c:if test="${carCustomerBase.customerSex==item.value}">
                                  checked
                                </c:if>>
                           ${item.label}
                         </input>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<c:forEach items="${fns:getDictList('jk_sex')}" var="item">
									<input type="radio" class=""
										name="carCustomerBase.customerSex" value="${item.value}"
										<c:if test="${carCustomerBase.customerSex==item.value}">
                                  checked
                                </c:if>>
                             ${item.label}
                           </input>
								</c:forEach>
							</c:otherwise>
						</c:choose></td>
   	</tr>
	<tr>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>手机号码：</label>
   				<input id="mateCertNum" name="carCustomerBase.customerMobilePhone" class="required input_text178" type="text" value=""/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
		<td style="width:32%">
  			<label class="lab"><span style="color: #ff0000"></span>证件类型：</label>
  				<select name="carCustomerBase.dictCertTypee"
						value="${carCustomerBase.dictCertTypee}" class="select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('com_certificate_type')}"
								var="item">
							 <option value="${item.value}"
									<c:if test="${carCustomerBase.dictCertTypee==item.value}">
					      selected = true 
					     </c:if>>${item.label}</option>
							</c:forEach>
					</select>
		</td>
   </tr>
   <tr>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000"></span>证件号码：</label>
   			<input id="mateCertNum" name="carCustomerBase.customerCertNum" class="input_text178" type="text" value=""/>
   			<span id="blackTip" style="color: red;"></span>
		</td>
		<td style="width:32%">
    		 <label class="lab"><span style="color: #ff0000"></span>证件有效期：</label>
		    <input id="idStartDay" name="carCustomerBase.idStartDay" type="text" class="input_text70" size="10"  
		      onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay\')}'})" style="cursor: pointer" readonly/>
		      -<input id="idEndDay" name="carCustomerBase.idEndDay" type="text" class="input_text70" size="10"
			  onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay\')}'})" style="cursor: pointer" readonly/>
			   <input type="checkbox" path="longTerm" id="longTerm" value="1" />长期
     	</td>
 	</tr>
 	<tr>
 		<td colspan="2">
 			<label class="lab"><span style="color: #ff0000"></span>现住址：</label>
  				
  				<select id="registerProvince" name="liveProvince" class="select78">
							<option value="">请选择</option>
							<c:forEach var="item" items="${provinceList}"
								varStatus="status">
								<option value="${item.areaCode}"
									<c:if test="${liveProvince==item.areaCode}">
							selected = true 
		                 </c:if>>
									${item.areaName}</option>
							</c:forEach>
					</select>-<select id="customerRegisterCity"
						name="liveCity" class="select78"
						value="${liveCity}">
							<option value="">请选择</option>
					</select>-<select id="customerRegisterArea"
						name="liveArea" class="select78" value="${liveArea}">
							<option value="">请选择</option>
					</select>
					<input type="text"
						name="customerAddress" class="input_text178"
						style="width: 250px"
						value="${customerAddress}" />
   		</td>	
 	</tr>
 	<tr>
   		<td>
    		<label class="lab"><span style="color: #ff0000"></span>学历：</label>
  				<select id="dictEducation" name="dictEducation"
						value="${dictEducation}" class="select2-container select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}"
									<c:if test="${dictEducation==curEdu.value}">
					      selected = true
					     </c:if>>${curEdu.label}</option>
							</c:forEach>
					</select>
		</td>
		<td>
    		<label class="lab"><span style="color: #ff0000"></span>婚姻状态：</label>
  				<select id="dictMarry" name="dictMarry" value="${dictMarry}" class="select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
					  <option value="${item.value}"
					   <c:if test="${dictMarry==item.value}"> 
					    selected=true 
					   </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
		</td>
      </tr>
 </table>
</div>
 <h2 class=" f14 pl10">车借信息</h2>
  <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
   		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>车辆品牌与型号：</label>
   				<input id="vehicleBrandModel" name="carVehicleInfo.vehicleBrandModel" class="required input_text178" type="text" value=""/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
   		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>车牌号码：</label>
   				<input id="plateNumbers" name="carVehicleInfo.plateNumbers" class="required input_text178" type="text" value=""/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
	</tr>
	<tr>		
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>预计借款金额：</label>
   				<input id="consLoanAmount" name="carCustomerConsultation.consLoanAmount" class="required input_text178" type="text" value=""/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
		<td style="width:32%">
   			<label class="lab"><span style="color: #ff0000">*</span>预计到店时间：</label>
   				 <input id="planArrivalTime" name="carCustomerConsultation.planArrivalTime" type="text" class="input_text178" size="10"  
		      onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'planArrivalTime\')}'})" style="cursor: pointer" readonly/>
   				<span id="blackTip" style="color: red;"></span>
		</td>
	</tr>
	<tr>
  		<td style="width:32%">
	    <label class="lab"><span style="color: #ff0000"></span>团队经理：</label>
	    <input path="consTeamEmpName" disabled="true" value="${consult}" class="input_text178" />
	   </td>
  		<td colspan="2">
     		<label class="lab"><span style="color: #ff0000">*</span>客户经理：</label>
  				<select id="customerManager" path="managerCode" class="select180">
		    <option value="">请选择</option>
		    <c:forEach items="${customerManagers}" var="item">
		      <option value="${item.userCode}">${item.name}</option>
		  </c:forEach>
		<select>
   		</td>
	</tr>
 </table>
</div>
<h2 class=" f14 pl10">沟通记录及下一步操作</h2>
 <div class="box2 ">
<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
  		<td style="width:32%">
    		<label class="lab"><span style="color: #ff0000">*</span>下一步：</label>
    			<select id="dictOperStatus" path="carCustomerConsultation.dictOperStatus" class="required select180">
		  <option value="">请选择</option>
		  <c:forEach items="${fns:getDictList('jk_next_step')}" var="item">
		     <c:if test="${item.label!='已进件'}">
		      <option value="${item.value}">${item.label}</option>
		     </c:if>
		  </c:forEach>
		</select>
		</td>
  		<td style="width:32%">
    		<label class="lab">备注：</label>
    			<textarea cols="20" rows="2" name="carCustomerConsultation.consLoanRemarks">200字以内</textarea>
  		</td>
  		<td style="width:32%"></td>
   	</tr>
</table>
</div>
<div class="tright" style="margin-top: 100px"><input type="submit" class="btn btn-primary" value="提交"></input></div>
</form> 
</body>
</html>