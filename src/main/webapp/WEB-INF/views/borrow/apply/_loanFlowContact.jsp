<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
<script type="text/javascript" src="${context}/js/launch/contactFormat.js"></script>
<script type="text/javascript">
 CONTACT_NUM = 3;
 $(document).ready(function(){
	$('#contactNextBtn').bind('click',function(){
		 contactFormat.format();
		 var tabLength=$('#contactArea tr').length;
		 if(tabLength<4){
			 art.dialog.alert("联系人至少要3个");
			 return false;
		 }else{
		   $(this).attr('disabled',true);
		   launch.saveApplyInfo('8','contactForm','contactNextBtn');
		 }
	 });
	$('#contactAdd').bind('click',function(){
		var tabLength=parseInt($('#contactIndex').val())+1;
		launch.additionItem('contactArea','_loanFlowContactItem',tabLength,'',null);
		$('#contactIndex').val(tabLength);
	});
	$("select[name$='relationType']").each(function(){
		var id = $(this).attr("id");
		var index = $(this).attr("index");
		var targetId = "contactRelation_"+index;
		var targetValueId = "contactRelationHidden_"+index;
		$(this).bind('change',function(){
			launch.getRelationDict($('#'+id+" option:selected").val(),targetId);
		});
		launch.initRelationDict($('#'+id+" option:selected").val(),targetId,$("#"+targetValueId).val());
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
			<li><a href="javascript:launch.getCustomerInfo('4');">共同借款人</a></li>
        </c:if>
        <c:if test="${workItem.bv.oneedition==1}"> 
			<li><a href="javascript:launch.getCustomerInfo('4');">自然人/保证人</a></li>	
        </c:if>
      
	  <li><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li class="active"><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('9');">银行卡资料</a></li>
	</ul>

	<form id="custInfoForm" method="post">
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
   <div id="div8" class="content control-group">
	 <div class=" ml10 mb5">
	    <input type="button" class="btn btn-small" id="contactAdd" value="增加联系人"></input>
      </div>
      <form id="contactForm" method="post">
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
	  	 <c:if test="${fn:length(applyInfoEx.customerContactList)>0}">
	  	  <input type="hidden" id="contactIndex" value="${fn:length(applyInfoEx.customerContactList)}" />
	  	 </c:if>
	  	 <c:if test="${fn:length(applyInfoEx.customerContactList)==0}">
	  	  <input type="hidden" id="contactIndex" value="3" />
	  	 </c:if>
        <table id="contactArea" class="table  table-bordered table-condensed  ">
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
            <c:if test="${applyInfoEx.customerContactList!=null && fn:length(applyInfoEx.customerContactList)>0}">
            <c:forEach items="${applyInfoEx.customerContactList}" var="ccItem" varStatus="ccStatus">
             <tr>
                <td>
                 <input type="hidden" name="customerContactList.id" class="contactId" value="${ccItem.id}" />
                 <input type="text" name="customerContactList.contactName" value="${ccItem.contactName}" class="input_text70 required stringCheck contactCheck"/>
                </td>
                <td>
     			  <select name="customerContactList.relationType" id="relationType_${ccStatus.index}" index="${ccStatus.index}" class="required select180">
       				<option value="">请选择</option>
         			<c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
		  			  <option value="${item.value}" 
		  			    <c:if test="${ccItem.relationType==item.value}">
		  			       selected=true 
		  			    </c:if>
		  			   >${item.label}
			 	      </option>
		  			</c:forEach>
	            </select>
      			</td>
                <td>
                <select name="customerContactList.contactRelation" id="contactRelation_${ccStatus.index}" value="${ccItem.contactRelation}" class="required select180">
                  <option value="">请选择</option>
    			</select>
    			 <input type="hidden" id="contactRelationHidden_${ccStatus.index}" value="${ccItem.contactRelation}"/>
                </td>
                <td><input type="text" name="customerContactList.contactSex" value="${ccItem.contactSex}" class="input_text178" maxlength="180" /></td>
                <td><input type="text" name="customerContactList.contactNowAddress"  value="${ccItem.contactNowAddress}" class="input_text178" maxlength="80" /></td>
                <td><input type="text" name="customerContactList.contactUnitTel" value="${ccItem.contactUnitTel}" class="input_text178 isTel"/></td>
                <td><input type="text" name="customerContactList.contactMobile" value="${ccItem.contactMobile}" class="input_text178 required isMobile contactMobileDiff1 contactMobileDiff2"/></td>
                <td class="tcenter">
                  <c:if test="${ccStatus.index>2}">
                   <input type="button" onclick="contact.delRow(this,'contactArea','CONTACT');" class="btn_edit" value="删除"/>
                  </c:if>
                </td>
              </tr>
            </c:forEach>
            </c:if>
            <c:if test="${applyInfoEx.customerContactList==null || fn:length(applyInfoEx.customerContactList)==0}">
               <c:forEach var="x" begin="0" end="2">
                 <tr>
                <td>
                 <input type="hidden" name="customerContactList.id" class="contactId"/>
                 <input type="text" name="customerContactList.contactName" class="input_text70 required stringCheck contactCheck"/>
                </td>
                <td>
     			  <select name="customerContactList.relationType"  id="relationType_${x}" index="${x}" class="required select180">
       				<option value="">请选择</option>
         			<c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
		  			  <option value="${item.value}">${item.label}
			 	      </option>
		  			</c:forEach>
	            </select>
      			</td>
                <td>
                <select name="customerContactList.contactRelation" id="contactRelation_${x}" class="required select180">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('card_type')}" var="item">
					  <option value="${item.value}">${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" name="customerContactList.contactSex" class="input_text178"/></td>
                <td><input type="text" name="customerContactList.contactNowAddress" class="input_text178"/></td>
                <td><input type="text" name="customerContactList.contactUnitTel" class="input_text178 isTel"/></td>
                <td><input type="text" name="customerContactList.contactMobile" class="input_text178 required isMobile contactMobileDiff1 contactMobileDiff2"/></td>
                <td class="tcenter">
                 <!-- <input type="button" onclick="contact.delRow(this,'contactArea','CONTACT');" class="btn_edit" value="删除"></input> --></td>
              </tr>
               </c:forEach>
            </c:if>
        </table>
        
      <!--   <input type="button" class="btn btn-small" onclick="contactFormat.format()" value="序列化"/> -->
        <div class="tright mb5 mr10" ><input type="submit" id="contactNextBtn" class="btn btn-primary" value="下一步"/></div>
        </form>
    </div>
    </body>
</html>