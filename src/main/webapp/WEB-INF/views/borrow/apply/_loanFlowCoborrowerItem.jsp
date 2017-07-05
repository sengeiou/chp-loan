<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="contactPanel" id="contactPanel_${parentIndex}" index='${parentIndex}'>
         <div  style="text-align:left;">  
         
         
          <input type="hidden" value="${oneedition}" name="oneedition"  id="oneedition"/>
          
         <c:if test="${oneedition==-1}">    
	         <input type="button" name="delCoborrower" index="${parentIndex}" coboId="-1" value="删除共借人" class="btn btn-small"></input>
	     </c:if>
	       <c:if test="${oneedition==1}">    
	         <input type="button" name="delCoborrower" index="${parentIndex}" coboId="-1" value="删除自然人保证人" class="btn btn-small"></input>
	     </c:if>
	     </div>
	   <input type="hidden" class="coboId" name="loanCoborrower[${parentIndex}].id"/>
	    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td  width="31%" ><label class="lab"><span class="red">*</span>共借人姓名：</label>
                <input type="text" name="loanCoborrower[${parentIndex}].coboName" class="input_text178 required stringCheck"/></td>
                <td  width="31%" ><label class="lab"><span class="red">*</span>性别：</label>
                 <span>
                  <c:forEach items="${fns:getDictList('jk_sex')}" var="item">
                     <input type="radio"name="loanCoborrower[${parentIndex}].coboSex" class="required" 
                       value="${item.value}">${item.label}</input>
                   </c:forEach>
                   </span>
               </td> 
               <td><label class="lab"><span class="red">*</span>婚姻状况：</label>
                 <select name="loanCoborrower[${parentIndex}].dictMarry" class="select180 required">
                   <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_marriage')}" var="item">
                       <c:if test="${item.label!='空'}">
					     <option value="${item.value}">${item.label}</option>
					   </c:if>
				     </c:forEach>
				</select>
                </td>
            </tr>
            <tr>
              <td><label class="lab"><span style="color: red">*</span>证件类型：</label>
						<select name="loanCoborrower[${parentIndex}].dictCertType" class="select180 required"
						  onchange="javascript:launch.certifacteFormatByCertType(this.value,this);">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('com_certificate_type')}"
								var="item">
							 <option value="${item.value}">${item.label}</option>
							</c:forEach>
					   </select>
				</td>
				<td><label class="lab"><span class="red">*</span>证件号码：</label>
                    <input name="loanCoborrower[${parentIndex}].coboCertNum" onblur="javascript:launch.certifacteFormatByCertNum(this.value,this);" type="text" class="input_text178 required coboCertCheck coboCertNumCheck1 coboCertNumCheck2"/>
                </td>
               <td>
                 <label class="lab"><span class="red">*</span>证件有效期：</label>
                   <span> <input id="idStartDay_${parentIndex}" name="loanCoborrower[${parentIndex}].idStartDay" 
							type="text" class="Wdate input_text70" size="10"
							onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'idEndDay_${parentIndex}\')}'})"
							style="cursor: pointer" />-<input id="idEndDay_${parentIndex}"
							name="loanCoborrower[${parentIndex}].idEndDay" 
							type="text" class="Wdate input_text70" size="10"
							   onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'idStartDay_${parentIndex}\')}'})"
						     style="cursor: pointer" /> 
						     <input type="checkbox" class="coboEffeCertificate" onclick="launch.idEffectiveDay(this,'idEndDay_${parentIndex}');"
						      id="longTerm_${parentIndex}"
						     name="longTerm_${parentIndex}" value="1"/>
						          长期
					</span>
               </td>
            </tr>
            <tr>
            <td><label class="lab"><span class="red">*</span>手机号码1：</label>
                   <input name="loanCoborrower[${parentIndex}].coboMobile" type="text" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/>
             </td>
             <td><label class="lab">手机号码2：</label>
                 <input type="text" name="loanCoborrower[${parentIndex}].coboMobile2" class="input_text178 isMobile coboMobileDiff1 coboMobileDiff2"/>
             </td>
             <td><label class="lab">固定电话：</label>
                   <input name="loanCoborrower[${parentIndex}].coboNowTel" type="text" class="input_text178 isTel"/>
             </td>
            </tr>
            <tr>
              <td><label class="lab">学历：</label>
						<select id="dictEducation_${parentIndex}" name="loanCoborrower[${parentIndex}].dictEducation"
						 class="select2-container select180">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_degree')}" var="curEdu">
								<option value="${curEdu.value}">${curEdu.label}</option>
							</c:forEach>
					</select>
			   </td>
			   <td><label class="lab"><span style="color: red">*</span>住房性质：</label>
						<select name="loanCoborrower[${parentIndex}].customerHouseHoldProperty"
						 class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_house_nature')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select>
			    </td>
			    <td><label class="lab">家人是否知悉：</label> <c:forEach
							items="${fns:getDictList('jk_yes_or_no')}" var="item">
							<input type="radio" name="loanCoborrower[${parentIndex}].coboContractIsKnow"
								value="${item.value}"
							 />${item.label}
               	 </c:forEach></td>
            </tr>
            <tr>
                <td><label class="lab">有无子女：</label>
                  <c:forEach items="${fns:getDictList('jk_have_or_nohave')}" var="item">
                     <input type="radio" name="loanCoborrower[${parentIndex}].coboHaveChild"
                       value="${item.value}"/>${item.label}
                   </c:forEach>
               	</td>
               	<td><label class="lab">毕业日期：</label> <input
						id="coboGraduationDay_${parentIndex}"
						name="loanCoborrower[${parentIndex}].coboGraduationDay" 
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
				</td>
				<td><label class="lab">初到本市时间：</label>
						<input id="firstArriveYear_${parentIndex}"
						name="loanCoborrower[${parentIndex}].customerFirtArriveYear"
						 type="text"
						class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d'})" style="cursor: pointer" />
				</td>
            </tr>
            <tr>
              <td><label class="lab">起始居住时间：</label> <input
						id="customerFirtLivingDay_${parentIndex}"
						name="loanCoborrower[${parentIndex}].customerFirstLivingDay" 
						type="text" class="Wdate input_text178" size="10"
						onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
					</td>
					<td><label class="lab"><span style="color: red">*</span>客户类型：</label>
						<select id="dictCustomerDiff_${parentIndex}" name="loanCoborrower[${parentIndex}].dictCustomerDiff"
						class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_customer_diff')}"
								var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
					</select></td>
					<td>
					  <label class="lab" >电子邮箱：</label>
					   <input type="text" class="input_text178 email"
						name="loanCoborrower[${parentIndex}].coboEmail"/>
				    </td>
            </tr>
            <tr>
                 <td>
                     <label class="lab">月收入（元/每月）：</label>
                     <input type="text" name="loanCoborrower[${parentIndex}].coboCompany.compSalary" class="input_text178  number amountCheck numCheck" />
                 </td>
                 <td><label class="lab">其他所得（元/每月）：</label>
                     <input type="hidden" name="loanCoborrower[${parentIndex}].coboCompany.id" class="coboCompId"/>
          			 <input name="loanCoborrower[${parentIndex}].coboCompany.compOtherAmount" type="text" class="input_text178"/>
                </td>
                <td><label class="lab">房屋出租（元/每月）：</label>
                     <input name="loanCoborrower[${parentIndex}].coboLivings.customerRentMonth" type="text" class="input_text178 number amountCheck numCheck "/>
           			 <input type="hidden" name="loanCoborrower[${parentIndex}].coboLivings.id" class="coboLivingsId"/>
                </td>
            </tr>
            
         <c:if test="${oneedition==1}"> 
        <!-- 单位名称   QQ  微博  开始  -->  
             <tr>
                <td>
                  <label class="lab">单位名称：</label>
                     <input name="loanCoborrower[${parentIndex}].coboCompName" value="${currCoborro.coboCompName}" type="text" class="input_text178"/>
                </td>
                <td><label class="lab">QQ：</label>
                    <input name="loanCoborrower[${parentIndex}].coboQq" value="${currCoborro.coboQq}" type="text" class="input_text178"/>
                </td>
                <td><label class="lab">微博：</label>
                    <input name="loanCoborrower[${parentIndex}].coboWeibo" value="${currCoborro.coboWeibo}" type="text" class="input_text178"/>
                </td>
            </tr>
         <!--     单位名称   QQ  微博 结束 -->   
        </c:if>
            
            
            <tr>
                <td colspan="3"><label class="lab"><span class="red">*</span>户籍地址：</label>
                  <select class="select78  required coboHouseholdProvince" id="coboHouseholdProvince_${parentIndex}" name="loanCoborrower[${parentIndex}].coboHouseholdProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode}">${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboHouseholdCity" id="coboHouseholdCity_${parentIndex}" name="loanCoborrower[${parentIndex}].coboHouseholdCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78 required coboHouseholdArea" id="coboHouseholdArea_${parentIndex}" name="loanCoborrower[${parentIndex}].coboHouseholdArea">
                     <option value="">请选择</option>
                  </select>
                  <input name="loanCoborrower[${parentIndex}].coboHouseholdAddress" type="text" class="input_text178 required"style="width:250px"/>
                </td>
            </tr>
            <tr>
            	<td colspan="3"><label class="lab"><span class="red">*</span>现住址：</label>
                  <select class="select78  required coboLiveingProvince" id="coboLiveingProvince_${parentIndex}" name="loanCoborrower[${parentIndex}].coboLiveingProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode}">${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboLiveingCity" id="coboLiveingCity_${parentIndex}" name="loanCoborrower[${parentIndex}].coboLiveingCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78 required coboLiveingArea" id="coboLiveingArea_${parentIndex}" name="loanCoborrower[${parentIndex}].coboLiveingArea">
                     <option value="">请选择</option>
                  </select>
                  <input name="loanCoborrower[${parentIndex}].coboNowAddress"type="text" class="input_text178 required" style="width:250px"/>
                </td>
            </tr>
      <c:if test="${oneedition==1}">          
 <!--单位住址  开始 -->
            <tr>
             <td colspan="3"><label class="lab"><span class="red">*</span>单位地址：</label>
                  <select class="select78  required coboCompProvince" id="coboCompProvince_${parentIndex}" name="loanCoborrower[${parentIndex}].coboCompProvince">
                     <option value="">请选择</option>
                     <c:forEach var="item" items="${provinceList}" varStatus="status">
		             <option value="${item.areaCode}"
		              <c:if test="${item.areaCode==currCoborro.coboCompProvince}"> 
		                 selected=true 
		              </c:if>
		             >${item.areaName}</option>
	               </c:forEach>
                  </select>
                  <select class="select78  required coboCompCity" id="coboCompCity_${parentIndex}" name="loanCoborrower[${parentIndex}].coboCompCity">
                     <option value="">请选择</option>
                  </select>
                  <select class="select78  required coboCompArer" id="coboCompArer_${parentIndex}" name="loanCoborrower[${parentIndex}].coboCompArer">
                     <option value="">请选择</option>
                  </select>
                  <input name="loanCoborrower[${parentIndex}].coboCompAddress" value="${currCoborro.coboCompAddress}" type="text" class="input_text178 required" style="width:250px"/>
                </td>
            </tr>
<!--单位住址  结束-->
     </c:if>
        </table>
      <div class="box1 mb5">
	    <input type="button" class="btn btn-small"  value="增加联系人" name="addCobContactBtn" tableId='table_${parentIndex}'  parentIndex='${parentIndex}'/>	
      </div>
        <table  border="1" cellspacing="0" cellpadding="0" border="0" currIndex="2" id="table_${parentIndex}"  class="table3" width="100%">
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
            <c:forEach var="x" begin="0" end="2">
              <tr>
                <td>
                 <input type="hidden" name="loanCoborrower[${parentIndex}].coborrowerContactList.id"></input> 
                 <input type="text" type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactName" class="input_text70 required stringCheck contactCheck"/>
                </td>
                <td>
     			  <select id="relationType_${parentIndex}_${x}"  index="${parentIndex}${x}" name="loanCoborrower[${parentIndex}].coborrowerContactList.relationType" class="required select180">
       				<option value="">请选择</option>
         			<c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
		  			  <option value="${item.value}" class="${item.id}">${item.label}
			 	      </option>
		  			</c:forEach>
	            </select>
      			</td>
                <td>
                <select id="contactRelation_${parentIndex}${x}" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactRelation" class="select180 required">
                   <option value="">请选择</option>
				</select>
                </td>
                <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactSex" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactNowAddress" class="input_text178"/></td>
                <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactUnitTel" class="input_text178 isTel"/></td>
                <td><input type="text" name="loanCoborrower[${parentIndex}].coborrowerContactList.contactMobile" class="input_text178 required isMobile coboMobileDiff1 coboMobileDiff2"/></td>
                <td class="tcenter">
                <%-- <input type="button" class="btn_edit" value="删除"  onclick="contact.delRow(this,'table_${parentIndex}','CONTACT')" style="width:50px;"/> --%>
                </td>
              </tr>
             </c:forEach>
       </table>
</div>