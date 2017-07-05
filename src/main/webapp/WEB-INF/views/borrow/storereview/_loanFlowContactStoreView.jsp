﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<jsp:include page="../apply/applyCommon.jsp"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
<script type="text/javascript" src="${context}/js/launch/contactFormatNew.js"></script>
<script type="text/javascript">
 	$(document).ready(function(){
		//修改联系人
		$('#contactNextBtn').bind('click',function(){
			contactFormat.format();
	   	 	$(this).attr('disabled',true);
	   	 	launch.updateApplyInfo('4','contactForm','contactNextBtn');
		});
		//初始化
		contact.init();
		//判断是否选择其他
		if("${applyInfoEx.loanCustomer.whoCanKnowBorrow}".indexOf("4") != -1){
			$("#hiddenRemark").append("<input type='text' class='required chineseCheck2' name='loanCustomer.whoCanKnowTheBorrowingRemark' id='knowBorrowMoneyRemark' value='${applyInfoEx.loanCustomer.whoCanKnowTheBorrowingRemark}' maxlength='100'>");
		}
		
		//1已婚，当非已婚时配偶信息中字段均为非必填项
		if("${applyInfoEx.loanCustomer.dictMarry}" != '1'){
			$("#mateName").removeClass("required");
			$("#mateCertNum").removeClass("required");
			$("#mateLoanCompany").removeClass("required");
			$("#mateTel").removeClass("required");
			$("#mateAddressProvince").removeClass("required");
			$("#mateAddressCity").removeClass("required");
			$("#mateAddressArea").removeClass("required");
			$("#mateAddress").removeClass("required");
			$("#table1 .red").remove();
			$("#table1 :text").attr("disabled","disabled");
			$("#table1 select").attr("disabled","disabled");
		}
 	});
 
 	function knowBorrowMoneyHandle(mark){
		if(mark.checked == true && mark.value == '4'){
			$("#hiddenRemark").append("<input type='text' class='required chineseCheck2' name='loanCustomer.whoCanKnowTheBorrowingRemark' id='knowBorrowMoneyRemark' maxlength='100'>");
		}else if(mark.checked == false && mark.value == '4'){
			$("#knowBorrowMoneyRemark").remove();
		}
 	}
 	var msg = "${message}";
 	if (msg != "" && msg != null) {
 		top.$.jBox.tip(msg);
 	};
</script>
</head>
<body>
	<ul class="nav nav-tabs">
	  	<li><a href="javascript:launch.getCustomerInfo_storeView('1');">个人基本信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('2');">借款意愿</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('3');">工作信息</a></li>
		<li class="active"><a href="javascript:launch.getCustomerInfo_storeView('4');">联系人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('5');">自然人保证人信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('6');">房产信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('7');">经营信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('8');">证件信息</a></li>
		<li><a href="javascript:launch.getCustomerInfo_storeView('9');">银行卡信息</a></li>
		<li style="width:auto;float:right;">
			<jsp:include page="./tright.jsp"/>
		</li>
	</ul>
	
	<form id="custInfoForm" method="post" >
    	<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}" id="customerCode">
		<input type="hidden" name="consultId" value="${workItem.bv.consultId}" id="consultId">
		<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}" id="loanCode" >
  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
 		<input type="hidden" name="token" value="${workItem.token}">
 		<input type="hidden" name="stepName" value="${workItem.stepName }">
 		<input type="hidden" name="flowName" value="${workItem.flowName }">
		<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
 		
 		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
 		<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"/>
 		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
 	</form>
 	
   <div id="div8" class="content control-group">
      	<form id="contactForm" method="post">
      		<input type="hidden" value="${workItem.bv.isBorrow}" name="isBorrow" id="isBorrow"/>
        	<input type="hidden" name="customerCode" value="${workItem.bv.customerCode}">
			<input type="hidden" name="consultId" value="${workItem.bv.consultId}">
			<input type="hidden" name="loanCode" value="${workItem.bv.loanCode}">
	  		<input type="hidden" name="applyId" value="${workItem.bv.applyId}">
	 		<input type="hidden" name="preResponse" value="${workItem.bv.preResponse }">
	  		<input type="hidden" name="wobNum" value="${workItem.wobNum }">
	 		<input type="hidden" name="token" value="${workItem.token}">
	 		<input type="hidden" name="stepName" value="${workItem.stepName }">
	 		<input type="hidden" name="flowName" value="${workItem.flowName }">
			<input type="hidden" name="lastLoanStatus" value="${workItem.bv.lastLoanStatus}"/>
	 		<input type="hidden" name="method" value="contact">
	 		<input type="hidden" name="loanCustomer.loanCode" value="${workItem.bv.loanCustomer.loanCode}"/>
	 		
	 		<input type="hidden" name="loanInfo.loanCode" value="${workItem.bv.loanCode}"/>
			<input type="hidden" name="loanMate.loanCode" value="${workItem.bv.loanCode}"/>
			<input type="hidden" name="loanCustomer.id" value="${workItem.bv.loanCustomer.id}"  id="custId" />
      <table id="table1">
      	<tr>
      		<h2 class=" pl10 f14 "><b>配偶信息</b></h2>
      	</tr>
      	<tr>
      		<td>
      			<input type="hidden" name="loanMate.id" value="${applyInfoEx.loanMate.id}"/>
      			<label class="lab"><span class="red">*</span>姓名：</label>
				<input type="text" name="loanMate.mateName" id="mateName" class="required stringCheck" value="${applyInfoEx.loanMate.mateName}" maxlength="50"/>
      		</td>
			<td>
				<label class="lab">
					<span class="red">*</span>
					证件类型：
				</label>
				<select id="mateCardType" name="loanMate.dictCertType" value="${applyInfoEx.loanMate.dictCertType}" class="select180 required">
					<option value="">请选择</option>
					<c:forEach items="${fns:getDictList('com_certificate_type')}" var="item">
						<c:if test="${item.value == 0 || item.value ==2 }">
							<option value="${item.value}" <c:if test="${applyInfoEx.loanMate.dictCertType==item.value}"> selected=true </c:if>>${item.label}</option>
						</c:if>
					</c:forEach>
				</select>
			</td>
			<td>
      			<label class="lab"><span class="red">*</span>证件号码：</label>
				<input type="text" name="loanMate.mateCertNum" class="required isCertificate mateNumCheck currentPageDuplicateCertNumCheck" id="mateCertNum" value="${applyInfoEx.loanMate.mateCertNum}"/>
      		</td>
      	</tr>
      	<tr>
      		<td>
      			<input type="hidden" name="loanMate.mateLoanCompany.id" value="${applyInfoEx.loanMate.mateLoanCompany.id}"/>
      			<label class="lab"><span class="red">*</span>单位名称：</label>
				<input type="text" name="loanMate.mateLoanCompany.compName" id="mateLoanCompany" class="required" value="${applyInfoEx.loanMate.mateLoanCompany.compName}"/>
      		</td>
      		<td>
      			<label class="lab"><span class="red">*</span>手机号码：</label>
				<input type="text" name="loanMate.mateTel" class="required isMobile mateMobileDiff" id="mateTel" value="${applyInfoEx.loanMate.mateTel}"/>
      		</td>
      		<td>
      			<label class="lab">邮箱：</label>
				<input type="text" name="loanMate.mateEmail" class="isEmail" id="mateEmail" value="${applyInfoEx.loanMate.mateEmail}"/>
      		</td>
      	</tr>
      	
      	<tr>
      		<td colspan="2"><label class="lab"><span class="red">*</span>住址：</label>
				<select class="select78 mr10 required" id="mateAddressProvince" name="loanMate.mateAddressProvince" value="${applyInfoEx.loanMate.mateAddressProvince}">
						<option value="">请选择</option>
						<c:forEach var="item" items="${provinceList}"
							varStatus="status">
							<option value="${item.areaCode}"
								<c:if test="${applyInfoEx.loanMate.mateAddressProvince==item.areaCode}">selected=true</c:if>>${item.areaName}
							</option>
						</c:forEach>
				</select>
				-
				<select class="select78 mr10 required" id="mateAddressCity" name="loanMate.mateAddressCity" value="${applyInfoEx.loanMate.mateAddressCity}">
					<option value="">请选择</option>
				</select>
				-	
				<select class="select78 required" name="loanMate.mateAddressArea" id="mateAddressArea" value="${applyInfoEx.loanMate.mateAddressArea}">
					<option value="">请选择</option>
				</select> 
				<input type="hidden" id="mateAddressCityHidden" value="${applyInfoEx.loanMate.mateAddressCity}" />
				<input type="hidden" id="mateAddressAreaHidden" value="${applyInfoEx.loanMate.mateAddressArea}" />
				<input name="loanMate.mateAddress" type="text" value="${applyInfoEx.loanMate.mateAddress}" id="mateAddress" class="input_text180 required" style="width:250px" maxlength="250">
			</td>
      	</tr>
      </table>
      <br>
      <span class=" pl10 f14 ">
				<b>亲属</b>
			</span>
			<input type="button" id="relationAdd" value="增加">
			<table id="table_contactRelationArea" class="table  table-bordered table-condensed  ">
				<c:if test="${applyInfoEx.relationContactList!=null &&fn:length(applyInfoEx.relationContactList)>0}">
					<input type="hidden" value="${fn:length(applyInfoEx.relationContactList)}" id="relationIndex" />
				</c:if>
				<c:if test="${applyInfoEx.relationContactList==null || fn:length(applyInfoEx.relationContactList)==0}">
					<input type="hidden" value="0" id="relationIndex" />
				</c:if>
				<thead>
					<tr>
						<th>
							<span class="red">*</span>
							姓名
						</th>
						<th>
							<span class="red">*</span>
							关系
						</th>
						<th>身份证号码</th>
						<th>单位名称</th>
						<th>宅电</th>
						<th>手机号码</th>
						<th>操作</th>
					</tr>
				</thead>

				<c:if test="${applyInfoEx.relationContactList != null && fn:length(applyInfoEx.relationContactList) > 0}">
					<c:forEach items="${applyInfoEx.relationContactList}" var="ccItem" varStatus="ccStatus">
						<tr>
							<td>
								<input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
								<input type="hidden" name="customerContactList.relationType" value="0" />
								<input id="relaticontact_contactName_${ccStatus.index}" type="text" name="customerContactList.contactName" value="${ccItem.contactName}" class="input_text180 required stringCheck contactCheck" />
							</td>
							<td>
								<select id="relaticontact_contactRelation_${ccStatus.index}" name="customerContactList.contactRelation"  index="${ccStatus.index}" class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
										<c:if test="${item.value != '2' }">
											<option value="${item.value}" <c:if test="${ccItem.contactRelation==item.value}"> selected=true </c:if>>
												${item.label}
											</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="relaticontact_certNum_${ccStatus.index}" type="text" name="customerContactList.certNum" value="${ccItem.certNum}" class="input_text180 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
							</td>
							<td>
								<input id="relaticontact_contactSex_${ccStatus.index}" type="text" name="customerContactList.contactSex" value="${ccItem.contactSex}" class="input_text180" />
							</td>
							<td>
								<input id="relaticontact_homeTel_${ccStatus.index}" type="text" name="customerContactList.homeTel" value="${ccItem.homeTel}" class="input_text180 isTel mobileAndTelNeedOne" />
							</td>
							<td>
								<input id="relaticontact_contactMobile_${ccStatus.index}" type="text" name="customerContactList.contactMobile" value="${ccItem.contactMobile}" class="input_text180 isMobile contactMobileDiff1 contactMobileDiff2 mobileAndTelNeedOne" />
							</td>
							<td class="tcenter">
								<c:if test="${ccStatus.index>0}">
									<input type="button" onclick="contact.delConMainByReturnNew(this,'table_contactRelationArea','CONTACT');" class="btn_edit" value="删除" />
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty applyInfoEx.relationContactList }">
					<c:forEach varStatus="ccStatus" begin="${fn:length(applyInfoEx.relationContactList)}" end="0">
						<tr>
							<td>
								<input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
								<input type="hidden" name="customerContactList.relationType" value="0" />
								<input id="relaticontact_contactName_${ccStatus.index}" type="text" name="customerContactList.contactName" class="input_text180 required stringCheck contactCheck" />
							</td>
							<td>
								<select id="relaticontact_contactRelation_${ccStatus.index}" name="customerContactList.contactRelation" index="${ccStatus.index}" class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${fns:getNewDictList('jk_loan_family_relation')}" var="item">
										<c:if test="${item.value != '2' }">
											<option value="${item.value}">
												${item.label}
											</option>
										</c:if>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="relaticontact_certNum_${ccStatus.index}" type="text" name="customerContactList.certNum" class="input_text180 coboCertCheckCopmany currentPageDuplicateCertNumCheck" />
							</td>
							<td>
								<input id="relaticontact_contactSex_${ccStatus.index}" type="text" name="customerContactList.contactSex" class="input_text180" />
							</td>
							<td>
								<input id="relaticontact_homeTel_${ccStatus.index}" type="text" name="customerContactList.homeTel" class="input_text180 isTel mobileAndTelNeedOne" />
							</td>
							<td>
								<input id="relaticontact_contactMobile_${ccStatus.index}" type="text" name="customerContactList.contactMobile" class="input_text180 isMobile contactMobileDiff1 contactMobileDiff2 mobileAndTelNeedOne" />
							</td>
							<td class="tcenter"></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>

			<br>
			<span class=" pl10 f14 ">
				<b>工作证明人</b>
				<input type="button" id="workProvePersonAdd" value="增加">
			</span>
			<table id="table_workProvePersonArea" class="table  table-bordered table-condensed  ">
				<c:if test="${applyInfoEx.workProveContactList!=null &&fn:length(applyInfoEx.workProveContactList)>0}">
					<input type="hidden" value="${fn:length(applyInfoEx.workProveContactList)}" id="workProvePersonIndex" />
				</c:if>
				<c:if test="${applyInfoEx.workProveContactList==null || fn:length(applyInfoEx.workProveContactList)==0}">
					<input type="hidden" value="0" id="workProvePersonIndex" />
				</c:if>
				<thead>
					<tr>
						<th>
							<span class="red">*</span>
							姓名
						</th>
						<th>
							<span class="red">*</span>
							部门
						</th>
						<th>
							<span class="red">*</span>
							手机号码
						</th>
						<th>
							<span class="red">*</span>
							职务
						</th>
						<th>
							<span class="red">*</span>
							关系
						</th>
						<th>
							备注
						</th>
						<th>操作</th>
					</tr>
				</thead>
				<c:if test="${applyInfoEx.workProveContactList != null && fn:length(applyInfoEx.workProveContactList) > 0}">
					<c:forEach items="${applyInfoEx.workProveContactList}" var="ccItem" varStatus="ccStatus">
						<tr>
							<td>
								<input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
								<input type="hidden" name="customerContactList.relationType" value="1" />
								<input id="workProveContact_contactName_${ccStatus.index}" type="text" name="customerContactList.contactName" value="${ccItem.contactName}" class="input_text180 required stringCheck contactCheck" />
							</td>
							<td>
								<input id="workProveContact_department_${ccStatus.index}" type="text" name="customerContactList.department" value="${ccItem.department}" class="input_text180 required" maxlength="20"/>
							</td>
							<td>
								<input id="workProveContact_contactMobile_${ccStatus.index}" type="text" name="customerContactList.contactMobile" value="${ccItem.contactMobile}" class="input_text180 isMobile required contactMobileDiff1 contactMobileDiff2" />
							</td>
							<td>
								<input id="workProveContact_post_${ccStatus.index}" type="text" name="customerContactList.post" value="${ccItem.post}" class="input_text180 required" maxlength="20"/>
							</td>
							<td>
								<select id="workProveContact_contactRelation_${ccStatus.index}" name="customerContactList.contactRelation" index="${ccStatus.index}" class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
										<option value="${item.value}" <c:if test="${ccItem.contactRelation==item.value}"> selected=true </c:if>>
											${item.label}
										</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="workProveContact_remarks_${ccStatus.index}" type="text" name="customerContactList.remarks" value="${ccItem.remarks}" class="input_text180" maxlength="100"/>
							</td>
							<td class="tcenter">
								<c:if test="${ccStatus.index>0}">
									<input type="button" onclick="contact.delConMainByReturnNew(this,'table_workProvePersonArea','CONTACT');" class="btn_edit" value="删除" />
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
				<c:if test="${empty applyInfoEx.workProveContactList }">
					<c:forEach varStatus="ccStatus" begin="${fn:length(applyInfoEx.workProveContactList)}" end="0">
						<tr>
							<td>
								<input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
								<input type="hidden" name="customerContactList.relationType" value="1" />
								<input id="workProveContact_contactName_${ccStatus.index}" type="text" name="customerContactList.contactName" class="input_text180 required stringCheck contactCheck" />
							</td>
							<td>
								<input id="workProveContact_department_${ccStatus.index}" type="text" name="customerContactList.department" class="input_text180 required maxLength20" />
							</td>
							<td>
								<input id="workProveContact_contactMobile_${ccStatus.index}" type="text" name="customerContactList.contactMobile" class="input_text180 required isMobile contactMobileDiff1 contactMobileDiff2" />
							</td>
							<td>
								<input id="workProveContact_post_${ccStatus.index}" type="text" name="customerContactList.post" class="input_text180 required maxLength20" />
							</td>
							<td>
								<select id="workProveContact_contactRelation_${ccStatus.index}"  name="customerContactList.contactRelation" index="${ccStatus.index}" class="select180 required">
									<option value="">请选择</option>
									<c:forEach items="${fns:getNewDictList('jk_loan_workmate_relation')}" var="item">
										<option value="${item.value}">
											${item.label}
										</option>
									</c:forEach>
								</select>
							</td>
							<td>
								<input id="workProveContact_remarks_${ccStatus.index}" type="text" name="customerContactList.remarks" class="input_text180"  maxlength="100"/>
							</td>
							<td class="tcenter"></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
			<br>
			<br>
			<span class=" pl10 f14 ">
				<b>其他</b>
				<input type="button" id="otherAdd" value="增加">
			</span>
			<table id="table_otherArea" class="table  table-bordered table-condensed  ">
				<c:if test="${applyInfoEx.otherContactList!=null &&fn:length(applyInfoEx.otherContactList)>0}">
					<input type="hidden" value="${fn:length(applyInfoEx.otherContactList)}" id="otherIndex" />
				</c:if>
				<c:if test="${applyInfoEx.otherContactList==null || fn:length(applyInfoEx.otherContactList)==0}">
					<input type="hidden" value="1" id="otherIndex" />
				</c:if>
				<thead>
					<tr>
						<th>
							<span class="red">*</span>
							姓名
						</th>
						<th>
							<span class="red">*</span>
							关系
						</th>
						<th>宅电</th>
						<th>
							<span class="red">*</span>
							手机号码
						</th>
						<th>操作</th>
					</tr>
				</thead>
				<c:if test="${applyInfoEx.otherContactList != null && fn:length(applyInfoEx.otherContactList) > 0}">
					<c:forEach items="${applyInfoEx.otherContactList}" var="ccItem" varStatus="ccStatus">
						<tr>
							<td>
								<input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
								<input type="hidden" name="customerContactList.relationType" value="2" />
								<input id="otherContact_contactName_${ccStatus.index}" type="text" name="customerContactList.contactName" value="${ccItem.contactName}" class="input_text180 required stringCheck contactCheck" />
							</td>
							<td>
								<input type="hidden" name="customerContactList.contactRelation" value="3">
								<input id="otherContact_remarks_${ccStatus.index}" type="text" name="customerContactList.remarks" value="${ccItem.remarks}" class="input_text180 required chineseCheck2" maxlength="100"/>
							</td>
							<td>
								<input id="otherContact_homeTel_${ccStatus.index}" type="text" name="customerContactList.homeTel" value="${ccItem.homeTel}" class="input_text180 isTel" />
							</td>
							<td>
								<input id="otherContact_contactMobile_${ccStatus.index}" type="text" name="customerContactList.contactMobile" value="${ccItem.contactMobile}" class="input_text180 isMobile required contactMobileDiff1 contactMobileDiff2" />
							</td>
							<td class="tcenter">
								<c:if test="${workItem.bv.isBorrow ne 1}">
									<c:set var="otherContactDelRow" value="1"/>
								</c:if>
								<c:if test="${workItem.bv.isBorrow eq 1}">
									<c:set var="otherContactDelRow" value="0"/>
								</c:if>
								<c:if test="${ccStatus.index>otherContactDelRow}">
									<input type="button" onclick="contact.delConMainByReturnNew(this,'table_otherArea','CONTACT');" class="btn_edit" value="删除" />
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>

				<c:if test="${empty applyInfoEx.otherContactList }">
					<c:if test="${workItem.bv.isBorrow ne 1}">
						<c:set var="otherContactEnd" value="1"/>
					</c:if>
					<c:if test="${workItem.bv.isBorrow eq 1}">
						<c:set var="otherContactEnd" value="0"/>
					</c:if>
					<c:forEach varStatus="ccStatus" begin="${fn:length(applyInfoEx.otherContactList)}" end="${otherContactEnd}">
						<tr>
							<td>
								<input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
								<input type="hidden" name="customerContactList.relationType" value="2" />
								<input id="otherContact_contactName_${ccStatus.index}" type="text" name="customerContactList.contactName" class="input_text180 required stringCheck contactCheck" />
							</td>
							<td>
								<input type="hidden" name="customerContactList.contactRelation" value="3">
								<input id="otherContact_remarks_${ccStatus.index}" type="text" name="customerContactList.remarks" class="input_text180 required chineseCheck2" maxlength="100"/>
							</td>
							<td>
								<input id="otherContact_homeTel_${ccStatus.index}" type="text" name="customerContactList.homeTel" class="input_text180 isTel" />
							</td>
							<td>
								<input id="otherContact_contactMobile_${ccStatus.index}" type="text" name="customerContactList.contactMobile" class="input_text180 required isMobile contactMobileDiff1 contactMobileDiff2" />
							</td>
							<td class="tcenter"></td>
						</tr>
					</c:forEach>
				</c:if>
			</table>
        <br>
        <table>
        	<tr>
        		<td colspan="3">
        			<span class="red">*</span><b>以上可知晓本次借款的联系人</b>
					<span>
						<c:forEach items="${fns:getNewDictList('jk_who_can_know_the_borrowing')}" var="item">
					      <input type="checkbox" class="required" name="loanCustomer.whoCanKnowBorrow" id="knowContactPerson${item.value}" onclick="knowBorrowMoneyHandle(this);"
					      <c:if test="${fn:contains(applyInfoEx.loanCustomer.whoCanKnowBorrow,item.value)}">checked</c:if> value="${item.value}">${item.label}
						</c:forEach>
					</span>
					<label id="hiddenRemark"></label>
        		</td>
        	</tr>
        </table>
        <div class="tright mb5 mr10" ><input type="submit" id="contactNextBtn" class="btn btn-primary" value="保存"/></div>
      </form>
    </div>
    </body>
</html>