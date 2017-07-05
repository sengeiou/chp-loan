<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default" />
<title>Insert title here</title>
<script type="text/javascript"
	src='${context}/js/common.js'></script>
<script type="text/javascript">
	$(function () {
		loan.initCity("addrProvice", "addrCity",null);
		$("#addrProvice").trigger("change");
		var cityId = '${info.city_id }';
		$("#addrCity option[value='"+cityId+"']").attr("selected", "selected").trigger("change");
		$("input").prop("readonly","readonly");
		$("select").prop("disabled","disabled");
	});
	function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };


	//验证身份证号并获取出生日期
	function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";

	iIdNo = trim(iIdNo);

	if ((iIdNo.length != 15) && (iIdNo.length != 18)) {
	strReturn = "输入的身份证号位数错误";
	return strReturn;
	}

	if (iIdNo.length == 15) {
	tmpStr = iIdNo.substring(6, 12);
	tmpStr = "19" + tmpStr;
	tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-" + tmpStr.substring(6)

	return tmpStr;
	}
	else {
	tmpStr = iIdNo.substring(6, 14);
	tmpStr = tmpStr.substring(0, 4) + "-" + tmpStr.substring(4, 6) + "-" + tmpStr.substring(6)

	return tmpStr;
	}
	}
</script>
</head>
<body>
<div class="body_r">
      <h2 class=" f14 pl10">客户信息</h2>
    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户编号：</label><input type="text" class="input_text178" value="${info.customer_code }"></td>
                <td><label class="lab">客户姓名：</label><input type="text" class="input_text178" value="${info.customer_name }"></td>
                <td>
               
                <label class="lab">婚姻状况：</label><select class="select180" name = "dict_marry_status">
					 <option value="">请选择</option>
					 <c:forEach items="${fns:getDictList('jk_marriage')}" var="marriage">
									<option value="${marriage.value}" <c:if test="${marriage.value == info.dict_marry_status }">selected=true</c:if>>${marriage.label}</option>
					 </c:forEach>
				 </select>
                </td>
            </tr>
            <tr>
                <td><label class="lab">证件类型：</label><select class="select180">
				 <option value="">请选择</option>
				 <c:forEach items="${fns:getDictList('jk_certificate_type')}" var="certificate_type">
								<option value="${certificate_type.value}"<c:if test="${certificate_type.value == info.dict_cert_type }">selected=true</c:if>>${certificate_type.label}</option>
				 </c:forEach>
				 </select>
                </td>
                <td><label class="lab">证件号码：</label><input type="text" class="input_text178" value="${info.customer_cert_num }"></td>
                <td>
                
                
                <label class="lab">学历：</label><select class="select180">
				 <option value="">请选择</option>
				 <c:forEach items="${fns:getDictList('jk_degree')}" var="degree">
								<option value="${degree.value}"<c:if test="${degree.value == info.dict_education }">selected=true</c:if>>${degree.label}</option>
				 </c:forEach>
				 </select>
                </td>
            </tr>
            <tr>
                <td><label class="lab">行业：</label><select class="select180">
				 <option value="">请选择</option>
				 <c:forEach items="${fns:getDictList('jk_industry_type')}"
						var="industry_type">
						<option value="${industry_type.value}"<c:if test="${industry_type.value == info.dict_comp_type }">selected=true</c:if>>${industry_type.label}</option>
				</c:forEach>
				</select>
                </td>
                <td><label class="lab">性别：</label><select class="select180">
				 <option value="">请选择</option>
				 <c:forEach items="${fns:getDictList('jk_sex')}"
						var="sex">
						<option value="${sex.value}"<c:if test="${sex.value == info.customer_sex }">selected=true</c:if>>${sex.label}</option>
				</c:forEach>
				</select>
                </td>
                <td><label class="lab">出生日期：</label><input type="text" class="input_text178" id = "no" value=""></td>
            	<script type="text/javascript">
            	document.getElementById("no").value = getBirthdatByIdNo('${info.customer_cert_num }');
            	</script>
            </tr>
        </table>
    </div>
    <c:if test = "${info.dict_marry_status == 1 }">
     <h2 class=" f14 pl10 mt20">客户配偶信息</h2>
    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td width="32%"><label class="lab">名字：</label><input type="text" class="input_text178" value="${info.mate_name }"></td>
                <td width="32%"><label class="lab">证件号码：</label><input type="text" class="input_text178"value="${info.mate_cert_num }"></td>
                <td width="32%"><label class="lab">手机：</label><input type="text" class="input_text178"value="${info.mate_tel }"></td>
            </tr>
            <tr>
                <%-- <td><label class="lab">年龄：</label><input type="text" class="input_text178" value="${info.mate_age }"></td> --%>
                <td width="32%"><label class="lab">单位名称：</label><input type="text" class="input_text178" value="${info.mate_comp_name }"></td>
                <td width="32%"><label class="lab">单位电话：</label><input type="text" class="input_text178" value="${info.mate_comp_tel }"></td>
                
           		<td width="32%"><label class="lab">单位地址：</label><input type="text" class="input_text178" value="${info.mate_comp_address }"></td>
            </tr>
             <%-- <tr>
                <td width="32%"><label class="lab">邮编：</label><input type="text" class="input_text178" value="${info.mate_comp_post_code }"></td>
                <td width="32%"><label class="lab">单位电话：</label><input type="text" class="input_text178" value="${info.mate_comp_tel }"></td>
                <td width="32%"><label class="lab">单位网址：</label><input type="text" class="input_text178" value="${info.mate_comp_website }"></td> 
            </tr>
            <tr>
                <td width="32%"><label class="lab">行业类别：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_industry_type')}"
								var="industry_type">
								<option value="${industry_type.value}"<c:if test="${industry_type.value == info.mate_dict_comp_type }">selected=true</c:if>>${industry_type.label}</option>
						</c:forEach>
					</select>
				</td>
                <td width="32%"><label class="lab">职务：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_job_type')}"
								var="job_type">
								<option value="${job_type.value}" <c:if test="${job_type.value == info.mate_comp_post }">selected=true</c:if>>${job_type.label}</option>
						</c:forEach>
					</select>
              </td>
                <td width="32%"><label class="lab">部门：</label><input type="text" class="input_text178" value="${info.mate_comp_department }"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">每月收入：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.mate_comp_salary }' pattern="#,#00.00" />"></td>
                <td width="32%"><label class="lab">	在此单位工作年数：</label><input type="text" class="input_text178" value="${info.mate_comp_work_experience }"></td>
                <td width="32%"><label class="lab">其他收入：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.mate_comp_other_amount }' pattern="#,#00.00" />"></td>
            </tr> --%>
        </table>
    </div>
    </c:if>
      <h2 class=" f14 pl10 mt20">借款申请</h2>
    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">产品类别：</label><input type="text" class="input_text178" value="${info.product_type }">
                </td>
                <td><label class="lab">申请额度(元)：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.loan_apply_amount }' pattern="#,#00.00" />"></td>
                <td><label class="lab">借款期限(月)：</label><input type="text" class="input_text178" value="${info.loan_months }"></td>
            </tr>
            <tr>
                <td><label class="lab">申请日期：</label><input type="text" class="input_text178" value="<fmt:formatDate value="${info.loan_apply_time }" pattern='yyyy-MM-dd' />"></td>
                <td><label class="lab">由何处得知我公司：</label><input type="text" class="input_text178" value="${info.dict_customer_source}"></td>
                <td><label class="lab">借款用途：</label><input type="text" class="input_text178" value="${info.dict_loan_use}"></td>
            </tr>
            <tr>
                <td><label class="lab">具体用途：</label><input type="text" class="input_text178" value="${info.realy_use }"></td>
                <td><label class="lab">是否加急：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_urgent_flag')}"
								var="urgent_flag">
								<option value="${urgent_flag.value}" <c:if test="${urgent_flag.value == info.loan_urgent_flag }">selected=true</c:if>>${urgent_flag.label}</option>
						</c:forEach>
					</select>
                </td>
                <td><label class="lab">还款方式：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_repay_way')}"
								var="repay_way">
								<option value="${repay_way.value}" <c:if test="${repay_way.value == info.dict_repay_method }">selected=true</c:if>>${repay_way.label}</option>
						</c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <td><label class="lab">共同还款人：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_repay_share_flag')}"
								var="repay_share_flag">
								<option value="${repay_share_flag.value}" <c:if test="${repay_share_flag.value == info.loan_common_repayment_flag }">selected=true</c:if>>${repay_share_flag.label}</option>
						</c:forEach>
					</select>
                </td>
                <td><label class="lab">管辖城市：</label><select class="select180" id="addrProvice">
					 <option value="">请选择</option>
						  <c:forEach var="item" items="${provinceList}" varStatus="status">
								<option value="${item.areaCode}" <c:if test="${item.areaCode == info.province_id}">selected=true</c:if>>${item.areaName}</option>
						</c:forEach>
					</select>-<select class="select180" id="addrCity">
                	<option value="">请选择</option>
                </select>
                </td>
            </tr>
        </table>
    </div>
     <h4 class=" f14 pl10 mt20">信用资料</h4>
		<div>
        <table class="table  table-bordered table-condensed table-hover " cellpadding="0" cellspacing="0" border="0" width="100%">
        <thead>  
         <tr>
            <th>抵押类型</th>
            <th>抵押物品</th>
            <th >机构名称</th>
            <th>贷款额度(￥)</th>
            <th>每月供额度(￥)</th>
            <th>贷款余额(￥)</th>
         </tr>
         </thead>
         <c:forEach items="${creditList}" var="credit">
         	<c:set var="creditNum" value="${credit.credit_card_num }"/>
         	<tr >
             <td ><input type="text" class="select78_24" value="${credit.dict_mortgage_type}"/></td>
             <td ><input type="text" class="select78_24" value="${credit.credit_mortgage_goods }"/></td>
             <td ><input type="text" class="select78_24" value="${credit.org_code }"/></td>
             <td ><input type="text" class="select78_24" value="<fmt:formatNumber value='${credit.credit_loan_limit }' pattern="#,#00.00" />"/></td>
             <td ><input type="text" class="select78_24" value="<fmt:formatNumber value='${credit.credit_months_amount }' pattern="#,#00.00" />" /></td>
             <td ><input type="text" class="select78_24" value="<fmt:formatNumber value='${credit.credit_loan_blanace }' pattern="#,#00.00" />" /></td>
		 </tr>
         </c:forEach>
		
	  </table><br>
	  <table>
		<tr>
             <td >信用卡总数：<input type="text" class="select78_24" value="${creditNum }"/>&nbsp;个</td>
		 </tr>
	</table>
	</div>
     <h2 class=" f14 pl10 mt20">职业信息/公司资料</h2>
    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
         <td>职业信息/公司资料--具体信息</td>
        </tr>
            <tr>
                <td width="32%"><label class="lab">名称：</label><input type="text" class="input_text178" value="${info.comp_name }"></td>
                <td width="32%"><label class="lab">邮编：</label><input type="text" class="input_text178" value="${info.comp_post_code }"></td>
                <td width="32%"><label class="lab">地址：</label><input type="text" class="input_text178" value="${info.comp_address }"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">单位类型：</label><input type="text" class="input_text178" value="${info.dict_comp_type }"></td>
                <td width="32%"><label class="lab">单位电话：</label><input type="text" class="input_text178" value="${info.comp_tel }"></td>
                <td width="32%"><label class="lab">单位网址：</label><input type="text" class="input_text178" value="${info.comp_website }"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">职务：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_job_type')}"
								var="job_type">
								<option value="${job_type.value}" <c:if test="${job_type.value == info.comp_post }">selected=true</c:if>>${job_type.label}</option>
						</c:forEach>
					</select>
                </td>
                <td width="32%"><label class="lab">部门：</label><input type="text" class="input_text178" value="${info.comp_department }"></td>
                <td width="32%"><label class="lab">员工数量：</label><input type="text" class="input_text178" value="${info.comp_unit_scale }"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">每月收入（元）：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.comp_salary }' pattern="#,#00.00" />"></td>
                <td width="32%"><label class="lab">每月支薪日：</label><input type="text" class="input_text178" value="${info.comp_salary_day }"></td>
                <td width="32%"><label class="lab">支付方式：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_pay_way')}"
								var="pay_way">
								<option value="${pay_way.value}" <c:if test="${pay_way.value == info.dict_salary_pay }">selected=true</c:if>>${pay_way.label}</option>
						</c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <td><label class="lab">服务年数：</label><input type="text" class="input_text178" value="${info.comp_work_experience }"></td>
                <td><label class="lab">其他收入（元）：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.comp_other_amount}' pattern="#,#00.00" />"></td>
            </tr>
            
        </table>
    </div>
    <h2 class=" f14 pl10 mt20">房产资料</h2>
    <div class="box2 ">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td width="32%"><label class="lab">房产具体信息：</label><input type="text" class="input_text178" value="${info.house_base_info }"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">房产地址：</label><input type="text" class="input_text178" value="${info.house_address }"></td>
                <td width="32%"><label class="lab">购买方式：</label><select class="select180">
					 <option value="">请选择</option>
						 <c:forEach items="${fns:getDictList('jk_house_buywayg')}"
								var="house_buywayg">
								<option value="${house_buywayg.value}" <c:if test="${house_buywayg.value == info.house_buyway }">selected=true</c:if>>${house_buywayg.label}</option>
						</c:forEach>
					</select>
                </td>
                <td width="32%"><label class="lab">购买日期：</label><input type="text" class="input_text178" value="${info.house_buyday }"></td>
            <tr>
                <td width="32%"><label class="lab">建筑年份：</label><input type="text" class="input_text178" value="${info.house_create_day }"></td>
                <td width="32%"><label class="lab">房屋面积：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.house_builing_area }' pattern="#,#00.00"/>"></td>
                <td width="32%"><label class="lab">购买价格（元）：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.house_amount }' pattern="#,#00.00" />"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">按揭银行：</label><input type="text" class="input_text178" value="${info.dict_house_bank }"></td>
                <td width="32%"><label class="lab">贷款总额（元）：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.house_loan_amount }' pattern="#,#00.00" />"></td>
                <td width="32%"><label class="lab">贷款年限：</label><input type="text" class="input_text178" value="${info.house_loan_year}"></td>
            </tr>
            <tr>
                <td width="32%"><label class="lab">尚欠余额（元）：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.house_less_amount }' pattern="#,#00.00" />"></td>
                <td width="32%"><label class="lab">月还款（元）：</label><input type="text" class="input_text178" value="<fmt:formatNumber value='${info.house_month_repay_amount }' pattern="#,#00.00" />"></td>
            </tr>
            
        </table>
    </div>

   <h4 class="f14 pl10 mt20">联系人资料(2名亲属、2名朋友、2名同事，最少1名联系人为直系亲属)</h4>
        <table class="table  table-bordered table-condensed table-hover " cellpadding="0" cellspacing="0" border="0" width="100%">
        <thead>
            <tr>
                <th>姓名</th>
                <th>和本人关系</th>
                <th>工作单位</th>
                <th>家庭或工作单位详细地址</th>
                <th>单位电话</th>
                <th>手机号</th>
            </tr>
            </thead>
            <c:forEach var = 'contact' items="${contactList }">
	             <tr>
	                <td>${contact.contact_name }</td>
	                <td>${contact.contact_relation}</td>
	                <td>${contact.contact_sex }</td>
	                <td>${contact.contact_now_address }</td>
	                <td>${contact.contact_unit_tel }</td>
	                <td>${contact.contact_mobile }</td>
	            </tr>
            </c:forEach>
           
        </table>
    <div class="box2 mt20">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户经理：</label><input type="text" class="input_text178" value="${info.name }"></td>
                <td><label class="lab">录入人：</label><input type="text" class="input_text178" value="${info.loan_customer_service }"></td>
                <td><label class="lab">复审人：</label><input type="text" class="input_text178" value="${info.check_id }"></td>
            </tr>
            <tr>
                <td><label class="lab">授权人：</label><input type="text" class="input_text178" value="${info.bank_authorizer }"></td>
            </tr>
            <tr>
                <td><label class="lab">银行卡（存折）户名：</label><input type="text" class="input_text178" value="${info.bank_account_name }">&nbsp;<input type="checkbox" <c:if test = "${info.bank_is_rareword  == 1}">checked = "checked"</c:if> id = "abc" disabled="disabled"/><label for='abc'>是否生僻字</label></td>
            </tr>
            <tr>
                <td><label class="lab">开户行名称：</label><input type="text" class="input_text178" value="${info.label }"></td>
            </tr>
            <tr>
                <td><label class="lab">开户行支行：</label><input type="text" class="input_text178" value="${info.bank_branch }"></td>
            </tr>
            <tr>
                <td><label class="lab">银行账号：</label><input type="text" class="input_text178" value="${info.bank_account }"></td>
            </tr>
            <tr>
           		<td><label class="lab">备注：</label><textarea class="textarea_refuse" >${info.remark }</textarea></td>
            </tr>
        </table>
    </div>
    <div class="tright mt20 mb30"><button class="btn btn-primary" onclick="history.go(-1);">返回</button></div>
</div>



</body>
</html>