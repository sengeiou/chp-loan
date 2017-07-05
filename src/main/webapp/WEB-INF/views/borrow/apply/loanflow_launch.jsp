<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
   $(document).ready(function(){
	  $('#mateCertNum').bind('blur',function(){
		  queryBlackLog($('#cardType option:selected').val(),$(this).val());
	  }); 
	  $('#cardType').bind('change',function(){
		  queryBlackLog($('#cardType option:selected').val(),$(mateCertNum).val());
	  });
	  $('#launchFlow').bind('click',function(){
		  launch.launchFlow();
	  });
   });
</script>
</head>
<body>
<div>
  <c:set var="bv" value="${workItem.bv}"/>
   <h3 class="tab_1" style="height:55px;">
        <span id="sp1" class="click" status="2">个人资料</span>
		<span id="sp2" status="0" viewName="_loanFlowMate">配偶资料</span>
        <span id="sp3" status="0" viewName="">申请信息</span>
        <span id="sp4" status="0">共同借款人</span> 
        <span id="sp5" status="0">信用资料</span>
        <span id="sp6" status="0">职业信息/公司资料</span>
        <span id="sp7" status="0">房产资料</span>
        <span id="sp8" status="0">联系人资料</span>
        <span id="sp9" status="0">银行卡资料</span>
    </h3>
  <form id="inputForm" action="#">
      <input type="hidden" value="${bv.loanCustomer.loanCode}" name="loanInfo.loanCode" id="loanCode"/>
      <input type="hidden" value="${bv.loanCustomer.customerCode}" name="loanCustomer.customerCode"/>
      <input type="hidden" value="${bv.customerName}" name="loanInfo.loanCustomerName" id="loanCustomerName"/>
    <div id="div1" class="content control-group">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%" >
		<tr>
               <td><label class="lab">&nbsp;&nbsp;&nbsp;</label><img src="images/CUST_NAME.png" height="25px;" width="40px;"/></td>
			   <td></td>
			   <td></td>
			</tr>
            <tr>
                <td width="31%" ><label class="lab"><span class="red">*</span>客户姓名：</label><input  type="text" name="loanCustomer.customerName" value="${bv.customerName}" class="input_text178"/></td>
                <td width="31%" ><label class="lab">性别：</label>
                  <c:forEach items="${fns:getDictList('sex')}" var="item">
                     <input type="radio" name="loanCustomer.customerSex" value="${item.value}">${item.label}</input>
                  </c:forEach>
                 </td>
				 <td><label class="lab"><span class="red">*</span>手机号码：</label>
				  <input  type="text" name="loanCustomer.customerPhoneFirst" value="${bv.customerMobilePhone}" class="input_text178"/></td>
            </tr>
			<tr>
               <td><label class="lab">手机号码2：</label>
                 <input name="loanCustomer.customerPhoneSecond" type="text" value="${bv.loanCustomer.customerPhoneSecond}" class="input_text178"/></td>
			   <td><label class="lab">&nbsp;&nbsp;&nbsp;</label><img src="images/ID_NUM.png" height="25px;" width="80px;"/></td>
			   <td></td>
			</tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>证件类型：</label>
                 <select id="cardType" name="loanCustomer.dictCertType" value="${bv.dictCertType}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					<option value="${item.value}">${item.label}</option>
				    </c:forEach>
				</select>
                </td>
                <td><label class="lab"><span class="red">*</span>证件号码：</label>
                   <input name="loanCustomer.customerCertNum" id="customerCertNum" type="text" value="${bv.mateCertNum}" class="input_text178"/>
                   <span id="blackTip" style="color:red;"></span>
                </td>
                <td><label class="lab"><span class="red">*</span>证件有效期：</label>
                <input id="d4311" name="loanCustomer.idStartDay" value="<fmt:formatDate value='${bv.idStartDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'d4312\')}'})" style="cursor: pointer" />
                <input id="d4312" name="loanCustomer.idEndDay" value="<fmt:formatDate value='${bv.idEndDay}' pattern="yyyy-MM-dd"/>"  type="text"  class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'d4311\')}'})" style="cursor: pointer" />
                <input type="checkbox" name="" value="1"/> 长期
                </td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>出生日期：</label>
                <input id="d4311" name="loanCustomer.customerBirthday" value="<fmt:formatDate value='${bv.customerBirthday}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
                <td><label class="lab"><span class="red">*</span>婚姻状况：</label>
                 <select id="dictMarry" name="loanCustomer.dictMarry" value="${bv.loanCustomer.dictMarry}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					<option value="${item.value}">${item.label}</option>
				    </c:forEach>
				</select>
				</td>
               <td><label class="lab">电子邮箱：</label>
                 <input name="loanCustomer.customerEmail" type="text" value="${bv.loanCustomer.customerEmail}" class="input_text178"/></td>
            </tr>
            <tr>
                <td><%-- <label class="lab"><span class="red">*</span>户口：</label>
               <select id="customerHouseHoldHold" name="customerLivings.customerHouseHoldHold" value="${bv.customerLivings.customerHouseHoldHold}" class="select180">
                  	 <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select> --%>
                </td>
                <td><label class="lab"><span class="red">*</span>现住址：</label>
                    <select class="select55 mr10" name="loanCustomer.customerLiveProvince"><option value="1">省份</option></select>
                    <select class="select55 mr10" name="loanCustomer.customerLiveCity"><option value="2">市</option></select>
                    <select class="select55 " name="loanCustomer.customerLiveArea"><option value="3">区县</option></select>
                    <input type="text" name="loanCustomer.customerAddress" class="input_text178" value="详细地址">
                </td>
            </tr>
            <tr>
			    <td><label class="lab"><span class="red">*</span>学历：</label>
			    <select id="dictEducation" name="loanCustomer.dictEducation" value="${bv.loanCustomer.dictEducation}" class="select180">
                    <option></option>
                    <c:forEach items="${fns:getDictList('card_type')}" var="curEdu">
					<option value="${curEdu.value}">${curEdu.label}</option>
					</c:forEach>
				</select>
			    </td>
                <td><label class="lab"><span class="red">*</span>户籍地址：</label>
                <select id="registerProvince" name="loanCustomer.customerRegisterProvince" value="${bv.loanCustomer.customerRegisterProvince}">
                   <option value="">省</option>
                </select>
                <select id="customerRegisterCity" name="loanCustomer.customerRegisterCity" value="${bv.loanCustomer.customerRegisterCity}">
                   <option value="">市</option>
                </select>
                <select id="customerRegisterArea" name="loanCustomer.customerRegisterArea" value="${bv.loanCustomer.customerRegisterArea}">
                   <option value="">区</option>
                </select>
                <input type="text" name="loanCustomer.customerRegisterAddress" value="${bv.loanCustomer.customerRegisterAddress}" class="input_text178"/></td>
                <td><label class="lab"><span class="red">*</span>住房性质：</label>
               <%--  <select id="cardType" name="loanCustomer.customerHouseProperty" value="${bv.loanCustomer.customerHouseProperty}"   class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select> --%>
                </td>
            </tr>
            <tr> 
                <td><label class="lab">家人是否知悉此借款：</label>
                <c:forEach items="${fns:getDictList('sex')}" var="item">
                  <input type="radio" name="loanCustomer.contactIsKnow" value="${item.value}" 
                  <c:if test="${item.value==bv.loanCustomer.contactIsKnow}">
                     checked='true'
                  </c:if>
                  />${item.label}
                </c:forEach>
                </td>
                <td><label class="lab">有无子女：</label>
                <c:forEach items="${fns:getDictList('sex')}" var="item">
                  <input type="radio" name="loanCustomer.customerHaveChildren" value="${item.value}"
                   <c:if test="${item.value==bv.loanCustomer.customerHaveChildren}">
                     checked='true'
                  </c:if>
                  />${item.label}
                </c:forEach>
                </td>
				<td><label class="lab">毕业日期：</label>
				 <input id="d4311" name="loanCustomer.customerGraduationDay"  value="<fmt:formatDate value='${bv.loanCustomer.customerGraduationDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
				</td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>初到本市时间：</label>
                <input id="d4311" name="customerLivings.customerFirtArriveYear" value="<fmt:formatDate value='${bv.customerLivings.customerFirtArriveYear}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
                <td><label class="lab">起始居住时间：</label>
                <input id="d4311" name="customerLivings.customerFirtLivingDay" value="<fmt:formatDate value='${bv.customerLivings.customerFirtLivingDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
                </td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>行业类型：</label>
                <select id="cardType" name="customerLoanCompany.dictCompIndustry" value="${bv.customerLoanCompany.dictCompIndustry}" class="select180">
                     <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><label class="lab">暂住证：</label>
                   <c:forEach items="${fns:getDictList('sex')}" var="item">
                     <input type="radio" name="customerLivings.customertmpResidentialPermit" value="${item.value}"/>${item.label}
                   </c:forEach>
                </td>
                <td><label class="lab">固定电话：</label>
                  <input name="loanCustomer.customerTel" type="text" ${bv.loanCustomer.customerTel} class="input_text178"/></td>
            </tr>

        </table>
          <div class="btn_next" style="text-align:right;"><input type="button" id="s1" class="btn btn-primary"  onclick="saveApplyInfo('1')" value="下一步"/></div>
    </div>
	 <div id="div2" class="content hide control-group" >
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
           <tbody id="loanMateTbody"> 
            <tr>
              <td width="31%" >
                 <label class="lab"><span class="red">*</span>姓名：</label>
                 <input name="loanMate.mateName" value="${bv.loanMate.mateName}" type="text" class="input_text178"/>
              </td>
              <td width="31%" >
                 <label class="lab"><span class="red">*</span>年龄：</label>
                 <input type="text" value="${bv.loanMate.mateAge}" name="loanMate.mateAge" class="input_text178"/>
              </td>
              <td>
                  <label class="lab"><span class="red">*</span>出生日期：</label>
                  <input id="d4311" name="loanMate.mateBirthday" value="<fmt:formatDate value='${bv.loanMate.mateBirthday}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
              </td>
            </tr>
            <tr>
              <td width="31%" >
                 <label class="lab"><span class="red">*</span>证件类型：</label>
                 <select id="mateCardType" name="loanMate.dictCertType" value="${bv.loanMate.dictCertType}" class="select180">
                     <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
              </td>
              <td width="31%" >
                 <label class="lab"><span class="red">*</span>证件号码：</label>
                 <input type="text" value="${bv.loanMate.mateCertNum}" name="loanMate.mateCertNum" class="input_text178"/>
              </td>
              <td>
                  <label class="lab"><span class="red">*</span>手机：</label>
                  <input type="text" name="loanMate.mateTel" value="${bv.loanMate.mateTel}" class="input_text178">
              </td>
            </tr>
            <tr>
                <td><label class="lab">单位名称：</label><input name="loanMate.mateLoanCompany.compName" value="${bv.loanMate.mateLoanCompany.compName}" type="text" class="input_text178"/></td>
                <td><label class="lab">单位电话：</label><input name="loanMate.mateLoanCompany.compTel" value="${bv.loanMate.mateLoanCompany.compTel}" type="text" class="input_text178"/></td>
                <td></td>
            </tr>
            <tr>
              <td colspan="3"><label class="lab">单位地址：</label>
                <select id="compProvince" name="loanMate.mateLoanCompany.compProvince" value="${bv.loanMate.mateLoanCompany.compProvince}">
                   <option value="">省</option>
                </select>
                <select id="compCity" name="loanMate.mateLoanCompany.compCity" value="${bv.loanMate.mateLoanCompany.compCity}">
                   <option value="">市</option>
                </select>
                <select id="compArer" name="loanMate.mateLoanCompany.compArer" value="${bv.loanMate.mateLoanCompany.compArer}">
                   <option value="">区</option>
                </select>
                  <input name="loanMate.mateLoanCompany.compAddress" value="${bv.loanMate.mateLoanCompany.compAddress}" type="text" class="input_text178"/>
              </td>
            </tr>
            </tbody>
        </table>
        <div class="btn_next" style="text-align:right;"><input type="button" id="s2" class="btn btn-primary"  onclick="saveApplyInfo('2')" value="下一步"/></div>
    </div>
    <div id="div3" class="content hide control-group">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td  width="31%" ><label class="lab"><span class="red">*</span>产品类型：</label>
                <select id="cardType" name="loanInfo.productType" value="${bv.loanInfo.productType}" class="select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td  width="31%" >
                   <label class="lab"><span class="red">*</span>申请额度（元）：</label>
                   <input type="text" name="loanInfo.loanApplyAmount" value="${bv.loanInfo.loanApplyAmount}" class="input_text178"/></td>
                <td><label class="lab"><span class="red">*</span>借款期限：</label>
                <select id="cardType" name="loanInfo.loanMonths" value="${bv.loanInfo.loanMonths}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>申请日期：</label>
                <input id="d4311" name="loanInfo.loanApplyTime" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
                <td><label class="lab">借款用途：</label>
                <select id="cardType" name="loanInfo.dictLoanUse" value="${bv.loanInfo.dictLoanUse}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><label class="lab">具体用途：</label><input type="text"  value="${bv.loanInfo.realyUse}"  name="loanInfo.realyUse" class="input_text178"/></td>
            </tr>
            <tr>
                <td><label class="lab">是否加急：</label>
                   <c:forEach items="${fns:getDictList('sex')}" var="item">
                     <input type="radio" name="loanInfo.loanUrgentFlag" value="${item.value}"
                      <c:if test="${item.value==bv.loanInfo.loanUrgentFlag}">
                        checked='true'
                      </c:if> 
                     />${item.label}
                   </c:forEach>
                 </td>
            </tr>
            <tr>
                <td><label class="lab">由何处得知我公司：</label>
                 <select id="cardType" name="loanCustomer.dictCustomerSource" value="${bv.loanCustomer.dictCustomerSource}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td colspan="2"><label class="lab">管辖城市：</label>
                   <select class="select78 mr34"><option value="1">省</option></select>
                   <select class="select78"><option value="2">市</option></select></td>
            </tr>
        </table>
        <div class="btn_next" style="text-align:right;"><input type="button" id="s3" class="btn btn-primary"  onclick="saveApplyInfo('3')" value="下一步"/></div>
    </div>
    <div id="div4" class="content hide control-group">
     <div  class="box1 mb10" style="text-align:right;"><input type="button" id="s3" class="btn btn-primary"  onclick="" value="增加共借人"/></div>
     <c:if test="${bv.loanCoborrower!=null && bv.loanCoborrower.size()>0}">
     <c:forEach items="${bv.loanCoborrower}" var="currCoborro" varStatus="coboStatus">
	  <div class="contactPanel" index='${coboStatus.index}'> 
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td  width="31%" ><label class="lab"><span class="red">*</span>共借人姓名：</label><input type="text" value="${currCoborro.coboName}" name="loanCoborrower[${coboStatus.index}].coboName" class="input_text178"/></td>
                <td  width="31%" ><label class="lab"><span class="red">*</span>性别：</label>
                  <c:forEach items="${fns:getDictList('sex')}" var="item">
                     <input type="radio" name="loanCoborrower[${coboStatus.index}].coboSex" value="${item.value}"/>${item.label}
                   </c:forEach>
               </td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>身份证号：</label>
                   <input name="loanCoborrower[${coboStatus.index}].coboCertNum" value="${currCoborro.coboCertNum}" type="text" class="input_text178"/>
                </td>
                <td><label class="lab"><span class="red">*</span>手机号码：</label>
                   <input name="loanCoborrower[${coboStatus.index}].coboMobile" value="${currCoborro.coboMobile}"  type="text" class="input_text178"/>
                </td>
                <td><label class="lab"><span class="red">*</span>婚姻状况：</label>
                 <select id="cardType" name="loanCoborrower[${coboStatus.index}].dictMarry" value="${currCoborro.dictMarry}" class="select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
            </tr>
            <tr>
                <td><label class="lab"><span class="red">*</span>户籍地址：</label>
                  <input name="loanCoborrower[${coboStatus.index}].coboAddress" type="text" class="input_text178"/>
                </td>
                <td><label class="lab"><span class="red">*</span>现住址：</label>
                  <input name="loanCoborrower[${coboStatus.index}].coboNowAddress" type="text" class="input_text178"/>
                </td>
                <td><label class="lab"><span class="red">*</span>固定电话：</label>
                   <input name="loanCoborrower[${coboStatus.index}].coboNowTel" type="text" class="input_text178"/>
                </td>
            </tr>
            <tr>
                <td><label class="lab">有无子女：</label>
                  <c:forEach items="${fns:getDictList('sex')}" var="item">
                     <input type="radio" name="loanCoborrower[${coboStatus.index}].coboHaveChild" value="${item.value}"/>${item.label}
                   </c:forEach>
               	</td>
            </tr>
            <tr>
                <td><label class="lab">其他所得：</label>
                     <input name="loanCoborrower[${coboStatus.index}].coboOtherMoney" type="text" class="input_text178"/>元/每月
                </td>
                <td><label class="lab">房屋出租：</label>
                     <input name="loanCoborrower[${coboStatus.index}].coboGetrent" type="text" class="input_text178"/>元/每月
                </td>
            </tr>

        </table>
      <div class="box1 mb10">
	    <input type="button"  value="增加联系人" onclick="addRow('table_${coboStatus.index}','${coboStatus.index}')"   style=" width:50px;"/>	
      </div>
        <table cellspacing="0" cellpadding="0" border="0" id="table_${coboStatus.index}"  class="table2" width="100%">
              <tr>
                <th>序号</th>
                <th>姓名</th>
                <th>和本人关系</th>
                <th>工作单位</th>
                <th>家庭或工作单位详细地址</th>
                <th>单位电话</th>
                <th>手机号</th>
                <th>操作</th>
            </tr>
         <c:forEach items="${currCoborro.coborrowerContactList}" var="item" varStatus="st" >
            <tr>
                <input type="hidden" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].id" value="${item.id}"></input>
                <td>st.index</td>
                <td><input type="text" type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].contactName" value="${item.contactName}" class="input_text70"/></td>
                <td>
                <select id="contactRelation" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].contactRelation" class="select180">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].contactSex" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].contactNowAddress" value="${item.contactNowAddress}" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].contactUnitTel" value="${item.contactUnitTel}" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${coboStatus.index}].coborrowerContactList[${st.index}].contactMobile" value="${item.contactMobile}" class="input_text178"/></td>
                <td class="tcenter"><input type="button" value="删除"  onclick="DelRow(${st.index})" style="width:50px;"/></td>
            </tr>
            </c:forEach>
        </table>
        </div>
       </c:forEach>
       </c:if>
        <c:if test="${bv.loanCoborrower==null || bv.loanCoborrower.size()==0}">
          <%@ include file="/WEB-INF/views/borrow/apply/_loanFlowContact.jsp"%>
        </c:if>
        <div class="btn_next" style="text-align:right;">
           <input type="button" id="s4" class="btn btn-primary"  onclick="saveApplyInfo('4')" value="下一步"/>
         </div>
    </div>
    <div id="div5" class="content hide control-group">
	    <div class="box1 mb10">
	    <input type="button" class="btn_sc" value="增加"></input>
      </div>
        <table cellspacing="0" cellpadding="0" border="1"  class="table2" width="100%">
            <tr>
                <th>抵押类型</th>
                <th>抵押物品</th>
                <th>机购名称</th>
                <th>贷款额度(¥)</th>
                <th>每月供额度(¥)</th>
                <th>贷款余额(¥)</th>
                <th>信用卡总数</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${bv.loanCreditInfoList}" var="curLoanCredit" varStatus="creditStatus">
             <tr>
                <td>
                <c:forEach items="${fns:getDictList('sex')}" var="curItem">
                <input type="radio" name="loanCreditInfoList[0].dictMortgageType"
                     value="${curItem.value}"
                     <c:if test="${curItem.value==curLoanCredit.dictMortgageType}">
                        checked='true'
                      </c:if> 
                     />${curItem.label}
                </c:forEach>
                </td>
                <td><input name="loanCreditInfoList[${creditStatus.index}].creditMortgageGoods" value="${curLoanCredit.creditMortgageGoods}" type="text" class="input_text178"/></td>
                <td><input name="loanCreditInfoList[${creditStatus.index}].orgCode" value="${curLoanCredit.orgCode}" type="text" class="input_text178"/></td>
                <td><input name="loanCreditInfoList[${creditStatus.index}].creditLoanLimit" value="${curLoanCredit.creditLoanLimit}" type="text" class="input_text70"/></td>
                <td><input name="loanCreditInfoList[${creditStatus.index}].creditMonthsAmount" value="${curLoanCredit.creditMonthsAmount}" type="text" class="input_text70"/></td>
                <td><input name="loanCreditInfoList[${creditStatus.index}].creditLoanBlance" value="${curLoanCredit.creditLoanBlance}" type="text" class="input_text70"/></td>
                <td><input name="loanCreditInfoList[${creditStatus.index}].creditCardNum" value="${curLoanCredit.creditCardNum}" type="text" class="input_text70"/></td>
               <td class="tcenter"><input type="button" class="btn_delete" value="删除"/></td>
             </tr>
            </c:forEach>
        </table>
        <div class="btn_next" style="text-align:right;"><input type="button" id="s5" class="btn btn-primary"  onclick="saveApplyInfo('5')" value="下一步"/></div>
    </div>
    <div id="div6" class="content hide control-group">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td  width="31%" >
                  <label class="lab"><span class="red">*</span>名称：</label>
                  <input type="text" name="customerLoanCompany.compName" value="${bv.customerLoanCompany.compName}" class="input_text178"/></td>
                <td  width="31%" ><label class="lab"><span class="red">*</span>单位类型：</label>
                <select id="cardType" name="customerLoanCompany.dictCompType" value="${bv.customerLoanCompany.dictCompType}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
				<td><label class="lab">单位电话：</label>
				   <input type="text" name="customerLoanCompany.compTel" value="${bv.customerLoanCompany.compTel}" class="input_text178"/>
				 </td>
            </tr>
            <tr>
                <td colspan="3"><label class="lab">地址：</label>
                   <select class="select78 mr10" name="customerLoanCompany.compProvince" value="${bv.customerLoanCompany.compProvince}">
                      <option value="1">省</option>
                   </select>
                   <select class="select78 mr10" name="customerLoanCompany.compCity" value="${bv.customerLoanCompany.compCity}">
                      <option value="2">市</option>
                   </select>
                   <select class="select78 mr10" name="customerLoanCompany.compArer" value="${bv.customerLoanCompany.compArer}">
                      <option value="3">区/县</option>
                   </select>
                   <input type="text" name="customerLoanCompany.compAddress" value="${bv.customerLoanCompany.compAddress}" class="input_text178">
                </td>
            </tr>
            <tr>
                <td><label class="lab">所在部门：</label>
                     <input type="text" name="customerLoanCompany.compDepartment" value="${bv.customerLoanCompany.compDepartment}" class="input_text178"/>
                </td>
                <td><label class="lab">月收入：</label>
                     <input type="text" name="customerLoanCompany.compSalary" value="${bv.customerLoanCompany.compSalary}"  class="input_text178"/>元
                </td>
                <td><label class="lab">每月支薪日：</label>
                     <input type="text" name="customerLoanCompany.compSalaryDay" value="${bv.customerLoanCompany.compSalaryDay}" class="input_text178"/>
                </td>
            </tr>
            <tr>
                <td><label class="lab">入职时间：</label>
                <input id="d4311" name="customerLoanCompany.compEntryDay" value="<fmt:formatDate value='${bv.customerLoanCompany.compEntryDay}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td> 
				<td><label class="lab">职务：</label>
				    <input type="text" name="customerLoanCompany.compPost" value="${bv.customerLoanCompany.compPost}" class="input_text178"/>
				</td> 
				<td><label class="lab">累积工作年限：</label>
				    <input type="text" name="customerLoanCompany.compWorkExperience"  value="${bv.customerLoanCompany.compWorkExperience}" class="input_text178"/>
				</td> 
            </tr>
        </table>
        <div class="btn_next" style="text-align:right;"><input type="button" id="s6" class="btn btn-primary"  onclick="saveApplyInfo('6')" value="下一步"/></div>
    </div>
    <div id="div7" class="content hide control-group">
	  <div class="box1 mb10">
	    <input type="button" class="btn_sc" value="增加"></input>
      </div>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
          <tbody id="loanHouseArea">
           <c:forEach items="${bv.customerLoanHouseList}" var="curLoanHouse" varStatus="loanHouseStatus">
            <tr><td><label class="lab"><span class="red">*</span>房产具体信息：</label>
                    <select class="select55 mr10" name="customerLoanHouseList[${loanHouseStatus.index}].houseProvince"
                      value="${curLoanHouse.houseProvince}">
                        <option value="">省份</option>
                    </select>
                    <select class="select55 mr10" name="customerLoanHouseList[${loanHouseStatus.index}].houseCity"
                      value="${curLoanHouse.houseCity}">
                        <option value="">市</option>
                    </select>
                    <select class="select55" name="customerLoanHouseList[${loanHouseStatus.index}].houseArea"
                      value="${curLoanHouse.houseArea}">
                        <option value="">区县</option>
                    </select>
                    <input name="customerLoanHouseList[${loanHouseStatus.index}].houseAddress" value="${curLoanHouse.houseAddress}" class="input_text178" />
                </td>
                <td></td>
                <td><label class="lab">建筑年份：</label>
                 <input id="d4311" name="customerLoanHouseList[${loanHouseStatus.index}].houseCreateDay" value="${curLoanHouse.houseCreateDay}" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
            </tr>
            <tr>
                <td><label class="lab">购买方式：</label>
                 <select id="cardType" name="customerLoanHouseList[${loanHouseStatus.index}].houseBuyway" value="${curLoanHouse.houseBuyway}" class="select180">
                     <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><label class="lab">购买日期：</label>
                <input id="d4311" name="customerLoanHouseList[${loanHouseStatus.index}].houseBuyday" value="${curLoanHouse.houseBuyday}" type="text" class="Wdate" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td>
                <td><label class="lab">房屋面积：</label>
                  <input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].houseBuilingArea" value="${curLoanHouse.houseBuilingArea}" class="input_text178"/>
                </td>
            </tr> 
			 <tr>
                <td><label class="lab">房屋共有情况：</label>
                  <select class="select180" name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseCommon" value="${curLoanHouse.dictHouseCommon}">
				    <option value="">请选择</option>
				    <option value="1">自有</option>
				    <option value="2">夫妻共有</option>
				    <option value="3">父母共有</option>
				    <option value="4">其他</option>
				  </select>
                </td>
                <td><label class="lab">与共有人关系：</label>
                    <input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].housePropertyRelation" value="${curLoanHouse.housePropertyRelation}" class="input_text178">
                </td>
                <td><label class="lab">抵押情况：</label>
                    <input type="text" name="customerLoanHouseList[${loanHouseStatus.index}].housePledgeMark" value="${curLoanHouse.housePledgeMark}" class="input_text178">
                </td>
            </tr> 
			 <tr>
                <td width="31%" ><label class="lab">是否抵押：</label>
                   <input type="radio" class="ml10 mr6" name="customerLoanHouseList[${loanHouseStatus.index}].housePledgeFlag" value="1"
                     <c:if test="${curLoanHouse.housePledgeFlag=='1'}"> checked='true' </c:if>/>是
                   <input type="radio" class="ml10 mr6" name="customerLoanHouseList[${loanHouseStatus.index}].housePledgeFlag" value="0"
                     <c:if test="${curLoanHouse.housePledgeFlag=='0'}"> checked='true' </c:if> />否
                </td>
               
                 <td><label class="lab">规划用途/设计用途：</label>
                  <select id="cardType" name="customerLoanHouseList[${loanHouseStatus.index}].dictHouseType"  class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				  </select>
                 </td>
                 <td></td>
            </tr> 
            </c:forEach>
           </tbody>
        </table>
        <div class="btn_next" style="text-align:right;"><input type="button" id="s7" class="btn btn-primary"  onclick="saveApplyInfo('7')" value="下一步"/></div>
    </div>
    <div id="div8" class="content hide control-group">
	 <div class="box1 mb10">
	    <button class="btn_sc">增加</button>
      </div>
        <table cellspacing="0" cellpadding="0" border="0"  class="table2" width="100%">
            <tr>
                <th>姓名</th>
                <th>和本人关系</th>
                <th>工作单位</th>
                <th>家庭或工作单位详细地址</th>
                <th>单位电话</th>
                <th>手机号</th>
                <th>操作</th>
            </tr>
            <c:forEach items="${bv.customerContactList}" var="ccItem" varStatus="ccStatus">
             <tr>
                <td><input type="text" name="customerContactList[${ccStatus.index}].contactName" value="${ccItem.contactName}" class="input_text70"/></td>
                <td>
                <select id="cardType" name="customerContactList[${ccStatus.index}].contactRelation" value="${ccItem.contactRelation}" class="select180">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" name="customerContactList[${ccStatus.index}].contactSex" value="${ccItem.contactSex}" class="input_text178"/></td>
                <td><input type="text" name="customerContactList[${ccStatus.index}].contactNowAddress"  value="${ccItem.contactNowAddress}" class="input_text178"/></td>
                <td><input type="text" name="customerContactList[${ccStatus.index}].contactUnitTel" value="${ccItem.contactUnitTel}" class="input_text178"/></td>
                <td><input type="text" name="customerContactList[${ccStatus.index}].contactMobile" value="${ccItem.contactMobile}" class="input_text178"/></td>
                <td class="tcenter"><input type="button" class="btn_delete" value="删除"></input></td>
              </tr>
            </c:forEach>
        </table>
        <div class="btn_next" style="text-align:right;"><input type="button" id="s8" class="btn btn-primary"  onclick="saveApplyInfo('8')" value="下一步"/></div>
    </div>
    <div id="div9" class="content hide control-group">
        <c:set var="bank" value="${bv.loanBank}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                
                <td  width="31%"><label class="lab">录入人：</label><input type="text" class="input_text178"></td>
				<td  width="31%"><label class="lab">客户经理：</label><input type="text" class="input_text178"></td>
				<td ><label class="lab"></label></td>
            </tr>
            <tr>
				<td  width="31%"><label class="lab">银行卡/折 户名：</label><input type="text" name="loanBank.bankAccountName" value="${bank.bankAccountName}" class="input_text178"/></td>
				<td  width="31%"><label class="lab">授权人：</label><input type="text" name="loanBank.bankAuthorizer" value="${bank.bankAuthorizer}" class="input_text178"/></td>
				 <td><label class="lab">签约平台：</label>
				 <select id="cardType" name="loanBank.bankSigningPlatform" value="${bank.bankSigningPlatform}" class="select180">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
				 </td>
                
            </tr>
            <tr>
                <td><label class="lab">开户行名称：</label>
                <select id="cardType" name="loanBank.bankName" value="${bank.bankName}" class="select180">
                    <option></option>
                    <c:forEach items="${fns:getDictList('card_type')}" var="curItem">
					  <option value="${curItem.value}">${curItem.label}</option>
				    </c:forEach>
				</select>
                </td>
                <td><label class="lab">开户支行：</label><input type="text" name="loanBank.bankBranch" value="${bank.bankBranch}" class="input_text178"/></td>
                <td><label class="lab">开户省市：</label>
                    <select class="select180" name="loanBank.bankProvince" value="${bank.bankProvince}">
                      <option value="">请选择</option>
                    </select>
                    &nbsp;
                    <select class="select180" name="loanBank.bankCity" value="${bank.bankCity}">
                       <option value="">请选择</option>
                    </select>
                </td>
            </tr>
			<tr>
               <td><label class="lab">&nbsp;&nbsp;&nbsp;</label><img src="images/bank_no.png" height="25px;" width="100px;"/></td>
			   <td><label class="lab">&nbsp;&nbsp;&nbsp;</label><img src="images/bank_no.png" height="25px;" width="100px;"/></td>
			   <td></td>
			</tr>
            <tr>
                <td><label class="lab">银行卡号：</label><input type="text" id="firstInputBankAccount" class="input_text178"/></td>
                <td><label class="lab">确认银行卡号：</label><input type="text" id="confirmBankAccount" value="${bank.bankAccount}" name="loanBank.bankAccount" class="input_text178"></td>
                <td><label class="lab">备注：</label><input type="text" name="loanBank.bankCheckDesc" value="${bank.bankCheckDesc}" class="input_text178"></td>
            </tr>
        </table>
         <!--流程参数-->
         <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
         <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
         <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
         <input type="hidden" value="${workItem.flowType}" name="flowType"></input>
        <div class="btn_next" style="text-align:right;"><input type="button" class="btn btn-primary" id="launchFlow" value="提交"></input></div>
    </div>
 </form>
</div>
function expand(el){
		var list = document.getElementsByName("child");
		for(var i =0;i<list.length;i++){
			list[i].style.display = 'none';
		}
        document.getElementById("child" + el).style.display = 'block';
	 }	
</body>
</html>
