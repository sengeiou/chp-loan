<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/launch/coborrowerFormat.js"></script>
<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	 $('#coborrowNextBtn').bind('click',function(){
		 $(this).attr('disabled',true);
	     coborrower.format();
		 launch.saveApplyInfo('4','coborrowForm','coborrowNextBtn');
	 }); 
	$('#addCoborrowBtn').bind('click',function(){
		 var indexStr = $('#parentPanelIndex').val();
		 var index = parseInt(indexStr)+1;
		launch.additionItem('contactPanel','_loanFlowCoborrowerItem',index,'0',null);
		$('#parentPanelIndex').val(index);
	});
	$(":input[name='addCobContactBtn']").each(function(){
		$(this).bind('click',function(){
			var taObj = $(this).attr('parentIndex');
			var tabLengthStr=$('#table_'+taObj).attr("currIndex");
			var tabLength = parseInt(tabLengthStr)+1;
		  launch.additionItem($(this).attr('tableId'),'_loanFlowCoborrowerContactItem',taObj,tabLength,null);
		  $('#table_'+taObj).attr("currIndex",tabLength);
		});
	});
	loan.batchInitCity("coboHouseholdProvince", "coboHouseholdCity", "coboHouseholdArea");
	areaselect.batchInitCity("coboHouseholdProvince", "coboHouseholdCity", "coboHouseholdArea");
	
	loan.batchInitCity("coboLiveingProvince", "coboLiveingCity", "coboLiveingArea");
	areaselect.batchInitCity("coboLiveingProvince", "coboLiveingCity", "coboLiveingArea");
	
	loan.batchInitCity("coboCompProvince", "coboCompCity", "coboCompArer");
	areaselect.batchInitCity("coboCompProvince", "coboCompCity", "coboCompArer");
	
	$("select[name$='relationType']").each(function(){
		var id = $(this).attr("id");
		var index = $(this).attr("index");
		var targetId = "contactRelation_"+index;
		var targetHidId = "contactRelationHidden_"+index;
		var parentValue = $(this).val();
		var targetValue = $("#"+targetHidId).val();
		$(this).bind('change',function(){
			launch.getRelationDict($('#'+id+" option:selected").val(),targetId);
		});
		if(parentValue!='' && targetValue!=''){
			launch.initRelationDict(parentValue,targetId,targetValue);
		}
	});
	$("input[name='delCoborrower']").each(function(){
		$(this).bind('click',function(){
			var index = $(this).attr("index");
			var coboId = $(this).attr("coboId");
			coborrower.del("contactPanel_"+index,"COBORROWER",coboId);
		});
		
	});
});
var msg = "${message}";
if (msg != "" && msg != null) {
	top.$.jBox.tip(msg);
};
</script>
</head>
<body>
<ul class="nav nav-tabs">
	  <li><a href="javascript:launch.getCustomerInfo('1');">个人资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('2');">配偶资料</a></li>
	  <li><a href="javascript:launch.getCustomerInfo('3');">申请信息</a></li>
	   <c:if test="${workItem.bv.oneedition==-1}"> 
			<li class="active"><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li class="active"><a href="javascript:launch.getCustomerInfo('4');">自然人保证人</a></li>	
        </c:if>
	  <li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">
	<!-- 区分 借款人 和 自然人/保证人的 -->
   <input type="hidden" value="${workItem.bv.oneedition}" name="oneedition"  id="oneedition"/>
	   
	   <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	   <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
	   <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}"  name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	</form>
<div id="div4" class="content control-group ">
    <form id="coborrowForm" method="post">
      <input type="hidden" value="${workItem.bv.defTokenId}" name="defTokenId"/>
	  <input type="hidden" value="${workItem.bv.defToken}" name="defToken"/>
   	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanInfo.loanCode" id="loanCode" />
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCode}"
				name="loanCode"/>
	  <input type="hidden" value="${workItem.bv.loanCustomer.id}" 
	            name="loanCustomer.id" id="custId" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="loanCustomer.customerCode" id="customerCode" /> 
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerCode}"
				name="customerCode"/> 
	  <input type="hidden" value="${workItem.bv.loanInfo.loanCustomerName}"
				name="loanInfo.loanCustomerName" id="loanCustomerName" />
	  <input type="hidden" value="${workItem.bv.loanCustomer.customerName}"
				name="loanCustomer.customerName" />
	  <input type="hidden" value="${workItem.bv.consultId}" name="consultId" id="consultId"/>
	  <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
	  <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
	  <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
	  <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
	  <c:if test="${applyInfoEx.loanCoborrower!=null &&fn:length(applyInfoEx.loanCoborrower)>0}">
	   <input type="hidden" value="${fn:length(applyInfoEx.loanCoborrower)}" id="parentPanelIndex"></input>
	  </c:if>
	  <c:if test="${applyInfoEx.loanCoborrower==null || fn:length(applyInfoEx.loanCoborrower)==0}">
	    <input type="hidden" value="0" id="parentPanelIndex"></input>
	  </c:if>
	   <c:if test="${workItem.bv.oneedition==-1}"> 
	  	       <div  style="text-align:left;"><input type="button" id="addCoborrowBtn" class="btn btn-small" value="增加共借人"/></div>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
            <div  style="text-align:left;"><input type="button" id="addCoborrowBtn" class="btn btn-small" value="增加自然人保证人"/></div>
        </c:if>
     
     <c:if test="${applyInfoEx.loanCoborrower!=null &&fn:length(applyInfoEx.loanCoborrower)>0}">
      <div id="contactPanel">
     <c:forEach items="${applyInfoEx.loanCoborrower}" var="currCoborro" varStatus="coboStatus">
	  <div class="contactPanel" id="contactPanel_${coboStatus.index}" index='${coboStatus.index}'> 
	   <c:if test="${coboStatus.index!=0}">
	     <div  style="text-align:left;">
	   	   <c:if test="${workItem.bv.oneedition==-1}"> 
	   	      <input type="button" name="delCoborrower" index="${coboStatus.index}" coboId="${currCoborro.id}" value="删除共借人" class="btn btn-small"></input>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
           <input type="button" name="delCoborrower" index="${coboStatus.index}" coboId="${currCoborro.id}" value="删除自然人保证人" class="btn btn-small"></input>
        </c:if>
	   
	     </div>
	   </c:if>
	   <input type="hidden" class="coboId" name="loanCoborrower[${coboStatus.index}].id" value="${currCoborro.id}"/>
	    <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td  width="31%" ><label class="lab"><span class="red">*</span>共借人姓名：</label>
                <input type="text" value="${currCoborro.coboName}" name="loanCoborrower[${coboStatus.index}].coboName" class="input_text178 required stringCheck"/></td>
                <td  width="31%" ><label class="lab"><span class="red">*</span>性别：</label>
                 <span>
                  <c:forEach items="${fns:getDictList('jk_sex')}" var="item">
                     <input type="radio"name="loanCoborrower[${coboStatus.index}].coboSex" class="required" 
                        <c:if test="${currCoborro.coboSex==item.value}"> 
                         checked=true 
                       </c:if>
                      value="${item.value}">${item.label}</input>
                   </c:forEach>
                   </span>
               </td> 
               <td><label class="lab"><span class="red">*</span>婚姻状况：</label>
                 <select name="loanCoborrower[${coboStatus.index}].dictMarry" value="${currCoborro.dictMarry}" class="select180 required">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
                      <c:if test="${item.label!='空'}">
					   <option value="${item.value}"
					    <c:if test="${currCoborro.dictMarry==item.value}"> 
					    selected=true 
					    </c:if>
					   >${item.label}</option>
					   </c:if>
				     </c:forEach>
				</select>
                </td>
            </tr>
            <tr>
              <td><label class="lab"><span style="color: red">*</span>证件类型：</label>
						<select id="dictCertType" name="loanCoborrower[${coboStatus.index}].dictCertType"
						value="${currCoborro.dictCertType}" class="select180 required"
						 onchange="javascript:launch.certifacteFormatByCertType(this.value,this);"
						>
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('com_certificate_type')}"
								var="item">
							 <option value="${item.value}"
									<c:if test="${currCoborro.dictCertType==item.value}">
					      selected = true 
					     </c:if>>${item.label}</option>
							</c:forEach>
					</select>
				</td>
				<td><label class="lab"><span class="red">*</span>证件号码：</label>
                   <input name="loanCoborrower[${coboStatus.index}].coboCertNum" onblur="javascript:launch.certifacteFormatByCertNum(this.value,this);" value="${currCoborro.coboCertNum}" type="text" class="input_text178 required coboCertCheck coboCertNumCheck1 coboCertNumCheck2"/>
                </td>
               <td>
                 <label class="lab"><span class="red">*</span>证件有效期：</label>
               		 <span>  <input id="idStartDay_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].idStartDay"
							value="<fmt:formatDate value='${currCoborro.idStartDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text70" size="10"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay_${coboStatus.index}\')}'})"
							style="cursor: pointer" /> -<input id="idEndDay_${coboStatus.index}"
							name="loanCoborrower[${coboStatus.index}].idEndDay"
							value="<fmt:formatDate value='${currCoborro.idEndDay}' pattern="yyyy-MM-dd"/>"
							type="text" class="Wdate input_text70" size="10"
							<c:if test="${currCoborro.idStartDay!=null && currCoborro.idEndDay==null}"> 
								  readOnly=true style='display:none'
						    </c:if>
						     onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay_${coboStatus.index}\')}'})"
						     style="cursor: pointer" /> <input type="checkbox" id="longTerm_${coboStatus.index}"
						     name="longTerm_${coboStatus.index}" value="1" class="coboEffeCertificate" onclick="launch.idEffectiveDay(this,'idEndDay_${coboStatus.index}');"
						     <c:if test="${currCoborro.idStartDay!=null && currCoborro.idEndDay==null}"> 
						         checked=true 
						     </c:if>  />
						          长期
					</span>
               </td>
            </tr>
            <tr>
            <td><label class="lab"><span class="red">*</span>手机号码1：</label>
                   <input name="loanCoborrower[${coboStatus.index}].coboMobile" value="${currCoborro.coboMobile}"  type="text" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/>
             </td>
             <td><label class="lab">手机号码2：</label>
                 <input type="text" name="loanCoborrower[${coboStatus.index}].coboMobile2" value="${currCoborro.coboMobile2}" class="input_text178 isMobile coboMobileDiff1 coboMobileDiff2"/>
             </td>
             <td><label class="lab">固定电话：</label>
                   <input name="loanCoborrower[${coboStatus.index}].coboNowTel" value="${currCoborro.coboNowTel}" type="text" class="input_text178 isTel"/>
             </td>
            </tr>
            <tr>
              <td><label class="lab">学历：</label>
						<select id="dictEducation_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].dictEducation"
						value="${currCoborro.dictEducation}"
						class="select2-container select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}"
									<c:if test="${currCoborro.dictEducation==curEdu.value}">
					      selected = true
					     </c:if>>${curEdu.label}</option>
							</c:forEach>
					</select>
			   </td>
			   <td><label class="lab"><span style="color: red">*</span>住房性质：</label>
						<select name="loanCoborrower[${coboStatus.index}].customerHouseHoldProperty"
						value="${currCoborro.customerHouseHoldProperty}"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_house_nature')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${item.value==currCoborro.customerHouseHoldProperty}">
					      selected=true 
					    </c:if>>${item.label}</option>
							</c:forEach>
					</select>
			    </td>
			    <td><label class="lab">家人是否知悉：</label> <c:forEach
							items="${fns:getDictList('jk_yes_or_no')}" var="item">
							<input type="radio" name="loanCoborrower[${coboStatus.index}].coboContractIsKnow"
								value="${item.value}"
								<c:if test="${item.value==currCoborro.coboContractIsKnow}">
                     checked='true'
                  	</c:if> />${item.label}
               	 </c:forEach></td>
            </tr>
            <tr>
                <td><label class="lab">有无子女：</label>
                  <c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
                     <input type="radio" name="loanCoborrower[${coboStatus.index}].coboHaveChild"
                       <c:if test="${currCoborro.coboHaveChild==item.value}"> 
                         checked=true 
                       </c:if>
                      value="${item.value}"/>${item.label}
                   </c:forEach>
               	</td>
               	<td><label class="lab">毕业日期：</label> <input
						id="coboGraduationDay_${coboStatus.index}"
						name="loanCoborrower[${coboStatus.index}].coboGraduationDay"
						value="<fmt:formatDate value='${currCoborro.coboGraduationDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
				</td>
				<td><label class="lab">初到本市时间：</label>
						<input id="firstArriveYear_${coboStatus.index}"
						name="loanCoborrower[${coboStatus.index}].customerFirtArriveYear"
						value="${currCoborro.customerFirtArriveYear}" type="text"
						class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
				</td>
            </tr>
            <tr>
              <td><label class="lab">起始居住时间：</label> <input
						id="customerFirtLivingDay_${coboStatus.index}"
						name="loanCoborrower[${coboStatus.index}].customerFirstLivingDay"
						value="<fmt:formatDate value='${currCoborro.customerFirstLivingDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab"><span style="color: red">*</span>客户类型：</label>
						<select id="dictCustomerDiff_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].dictCustomerDiff"
						class="select180 required"
						value="${currCoborro.dictCustomerDiff}">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_customer_diff')}"
								var="item">
								<option value="${item.value}"
									<c:if test="${currCoborro.dictCustomerDiff==item.value}">
					       selected=true 
					    </c:if>>${item.label}</option>
							</c:forEach>
					</select></td>
					<td>
					  <label class="lab" ><span style="color: red">*</span>电子邮箱：</label>
					   <input type="text" class="input_text178 required email"
						name="loanCoborrower[${coboStatus.index}].coboEmail"
						value="${currCoborro.coboEmail}"/>
				    </td>
            </tr>
            <tr>
                <td>
                     <label class="lab"><span style="color: red">*</span>月收入（元/每月）：</label>
                     <input type="text" name="loanCoborrower[${coboStatus.index}].coboCompany.compSalary" value="<fmt:formatNumber value='${currCoborro.coboCompany.compSalary}' pattern="##0.00"/>" class="input_text178  number amountCheck numCheck required" />
                </td>
                <td><label class="lab">其他所得（元/每月）：</label>
                     <input type="hidden" name="loanCoborrower[${coboStatus.index}].coboCompany.id" class="coboCompId" value="${currCoborro.coboCompany.id}"/>
                     <input name="loanCoborrower[${coboStatus.index}].coboCompany.compOtherAmount" value="${currCoborro.coboCompany.compOtherAmount}" type="text" class="input_text178"/>
                </td>
                <td><label class="lab">房屋出租（元/每月）：</label>
                     <input type="hidden" name="loanCoborrower[${coboStatus.index}].coboLivings.id" class="coboLivingsId" value="${currCoborro.coboLivings.id}"/>
                     <input name="loanCoborrower[${coboStatus.index}].coboLivings.customerRentMonth" value="<fmt:formatNumber value='${currCoborro.coboLivings.customerRentMonth}' pattern="##0.00"/>" type="text" class="input_text178 number amountCheck numCheck"/>
                </td>
            </tr>
            
        <c:if test="${workItem.bv.oneedition==1}"> 
         <!-- 单位名称   QQ  微博  开始  -->  
             <tr>
                <td>
                  <label class="lab"><span style="color: red">*</span>单位名称：</label>
                     <input name="loanCoborrower[${coboStatus.index}].coboCompName" value="${currCoborro.coboCompName}" type="text" class="input_text178 required"/>
                </td>
                <td><label class="lab">QQ：</label>
                    <input name="loanCoborrower[${coboStatus.index}].coboQq" value="${currCoborro.coboQq}" type="text" class="input_text178"/>
                </td>
                <td><label class="lab">微博：</label>
                    <input name="loanCoborrower[${coboStatus.index}].coboWeibo" value="${currCoborro.coboWeibo}" type="text" class="input_text178"/>
                </td>
            </tr>
         <!--     单位名称   QQ  微博 结束 -->   
          </c:if>
         
                  <tr>
                <td colspan="3"><label class="lab"><span class="red">*</span>户籍地址：</label>
                  <select  class="select78   required coboHouseholdProvince" id="coboHouseholdProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode }"
		               <c:if test="${item.areaCode==currCoborro.coboHouseholdProvince}">
		                 selected = true 
		               </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboHouseholdCity" id="coboHouseholdCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78 required coboHouseholdArea" id="coboHouseholdArea_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboHouseholdArea">
                     <option value="">请选择</option>
                  </select>
                  <input type="hidden" id="coboHouseholdCityHidden_${coboStatus.index}" value="${currCoborro.coboHouseholdCity}"/>
                   <input type="hidden" id="coboHouseholdAreaHidden_${coboStatus.index}" value="${currCoborro.coboHouseholdArea}"/>
                  <input name="loanCoborrower[${coboStatus.index}].coboHouseholdAddress" value="${currCoborro.coboHouseholdAddress}" type="text" class="input_text178 required"style="width:250px"/>
                </td>
            </tr>
            <tr>
             <td colspan="3"><label class="lab"><span class="red">*</span>现住址：</label>
                  <select class="select78  required coboLiveingProvince" id="coboLiveingProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode}"
		              <c:if test="${item.areaCode==currCoborro.coboLiveingProvince}"> 
		                 selected=true 
		              </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboLiveingCity" id="coboLiveingCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78  required coboLiveingArea" id="coboLiveingArea_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboLiveingArea">
                     <option value="">请选择</option>
                  </select>
                  <input type="hidden" id="coboLiveingCityHidden_${coboStatus.index}" value="${currCoborro.coboLiveingCity}"/>
                   <input type="hidden" id="coboLiveingAreaHidden_${coboStatus.index}" value="${currCoborro.coboLiveingArea}"/>
                  <input name="loanCoborrower[${coboStatus.index}].coboNowAddress" value="${currCoborro.coboNowAddress}" type="text" class="input_text178 required" style="width:250px"/>
                </td>
            </tr>
<c:if test="${workItem.bv.oneedition==1}">   
<!--单位住址  开始 -->
            <tr>
             <td colspan="3"><label class="lab"><span class="red">*</span>单位地址：</label>
                  <select class="select78  required coboCompProvince" id="coboCompProvince_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode}"
		              <c:if test="${item.areaCode==currCoborro.coboCompProvince}"> 
		                 selected=true 
		              </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboCompCity" id="coboCompCity_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78  required coboCompArer" id="coboCompArer_${coboStatus.index}" name="loanCoborrower[${coboStatus.index}].coboCompArer">
                     <option value="">请选择</option>
                  </select>
                  <input type="hidden" id="coboCompCityHidden_${coboStatus.index}" value="${currCoborro.coboCompCity}"/>
                   <input type="hidden" id="coboCompArerHidden_${coboStatus.index}" value="${currCoborro.coboCompArer}"/>
                  <input name="loanCoborrower[${coboStatus.index}].coboCompAddress" value="${currCoborro.coboCompAddress}" type="text" class="input_text178 required" style="width:250px"/>
                </td>
            </tr>
<!--单位住址  结束-->
  </c:if>

        </table>
      <div class="box1 mb5">
	    <input type="button" class="btn btn-small"   value="增加联系人" name="addCobContactBtn" tableId='table_${coboStatus.index}'  parentIndex='${coboStatus.index}'/>	
      </div>
        <table currIndex="2" id="table_${coboStatus.index}"  class="table  table-bordered table-condensed  ">
        <thead>
              <tr>
                <th><span class="red">*</span>姓名</th>
                <th><span class="red">*</span>关系类型</th>
                <th><span class="red">*</span>和本人关系</th>
                <th>工作单位</th>
                <th>家庭或工作单位详细地址</th>
                <th>单位电话</th>
                <th><span class="red">*</span>手机号</th>
                <th>操作</th>
            </tr>
            </thead>
          <c:if test="${fn:length(currCoborro.coborrowerContactList)>0}">  
         	 <c:forEach items="${currCoborro.coborrowerContactList}" var="curContact" varStatus="st">
           	   <tr>
               	<td>
                 <input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id" value="${curContact.id}"></input>
                 <input type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" value="${curContact.contactName}" class="input_text70 required stringCheck contactCheck"/>
                </td>
                <td>
                <select id="relationType_${coboStatus.index}_${st.index}" index="${coboStatus.index}${st.index}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" class="required select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  <option value="${item.value}" 
					    <c:if test="${curContact.relationType==item.value}"> 
                         selected=true 
                       </c:if>
					   class="${item.id}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td>
                <select id="contactRelation_${coboStatus.index}${st.index}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
                   <option value="">请选择</option>
             	</select>
             	 <input type="hidden" id="contactRelationHidden_${coboStatus.index}${st.index}" value="${curContact.contactRelation}"/>
                </td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactSex" value="${curContact.contactSex}" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactNowAddress" value="${curContact.contactNowAddress}" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactUnitTel" value="${curContact.contactUnitTel}" class="input_text178 isTel"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" value="${curContact.contactMobile}" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/></td>
                <td class="tcenter">
                  <c:if test="${st.index>2}">
                    <input type="button" class="btn_edit"  value="删除"  onclick="contact.delRow(this,'table_${coboStatus.index}','CONTACT')" style="width:50px;"/>
                  </c:if>
                 </td>
            </tr>
            </c:forEach>
          </c:if>
          <c:if test="${fn:length(currCoborro.coborrowerContactList)<3}">
            <c:forEach  var="x" begin="${fn:length(currCoborro.coborrowerContactList)}" end="2">
           	   <tr>
               	<td>
                 <input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.id"></input>
                 <input type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck"/>
                </td>
                <td>
                <select id="relationType_${coboStatus.index}_${x}" index="${coboStatus.index}${x}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.relationType" class="required select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  <option value="${item.value}" 
					   class="${item.id}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td>
                <select id="contactRelation_${coboStatus.index}${x}" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactRelation" class="required select180">
                   <option value="">请选择</option>
             	</select>
                </td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactSex" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactNowAddress" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactUnitTel" class="input_text178 isTel"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/></td>
                <td class="tcenter">
                </td>
            </tr>
            </c:forEach>
          </c:if>
        </table>
        </div>
       </c:forEach>
       </div>
       </c:if>
    <!--  <input type="button" value="序列化" onclick="coborrower.format();"/>  -->
        <div class="tright mr10 mb5">
           <input type="submit" id="coborrowNextBtn" class="btn btn-primary" value="下一步"/>
         </div>
         
    </div>
    </form>
  </body>
</html>