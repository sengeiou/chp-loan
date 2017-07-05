<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 联系人资料 -->
<head>
<title>展期信息-联系人资料</title>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/addContact.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/contactFormat.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewExtendMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carExtendConsultForm.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript">
  CONTACT_NUM = 3;
//中英文校验
  jQuery.validator.addMethod("stringCheck", function(value, element) {
  	var isOK = true;
  	if(value!=''){
  		var regex= /^[a-zA-Z\u4e00-\u9fa5 ]{1,20}$/;
  		isOK = regex.test(value);
      }
  	return this.optional(element) || (isOK);
  }, "只能输入中文、英文、空格");
  jQuery.validator.addMethod("telOrPhone", function(value,element) {
		var length = value.length;
		var mobile =/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
		var tel = /^\d{3,4}-?\d{7,9}$/; 
		return this.optional(element) || (tel.test(value) || mobile.test(value));
		}, "格式为：0XX(X)-XXXXXXX(X)或有效的11位手机号码");
   $(document).ready(function(){
	$('#contactNextBtn').bind('click',function(){
		 contactFormat.format();
		 var tabLength=$('#contactArea tbody>tr').length;
		 if(tabLength<3){
			 art.dialog.alert("联系人至少要3个");
			 return false;
		 }
	});  
		//验证JS
			$("#qnm").validate({
						onclick: true, 
						rules:{
							customerCertNum:{
								card:true,
							},
					},
						messages : {
							customerName:{
								realName:"姓名只能为2-30个汉字"
							},
						
						}
			});
	 }); 

</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarContract?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendInfo?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowCustomer?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendCompany?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarExtendContact?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toExtendCoborrower?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendApply/toCarLoanFlowBank?loanCode=${loanCode}'+'&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
</ul>
    <jsp:include page="carExtendLoanFlowContactItem.jsp"></jsp:include>
	<jsp:include page="_frameFlowForm.jsp"></jsp:include>
   <div id="div8" class="content control-group">
	 <div class=" ml10">
	    <input type="button" class="btn btn-small" id="contactAdd" value="增加联系人"></input>
      </div>
      <form class="hide" id="workItemParam">
      	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
      </form>
      <form class="hide" id="loanCodeParam">
      <input type="hidden" value="${loanCode}" name="loanCode"></input>
      <input type="hidden" value="${newLoanCode}" name="newLoanCode"></input>
      </form>
      <form id="qnm">
        <table border="1" id="contactArea" cellspacing="0" cellpadding="0" border="0"  class="table3" width="100%">
            <thead>
                <th><span class="red">*</span>姓名</th>
                <th><span class="red">*</span>和本人关系</th>
                <th>工作单位</th>
                <th>家庭或工作单位详细地址</th>
                <th>单位电话</th>
                <th><span class="red">*</span>手机号</th>
                <th>操作</th>
            </thead>
            <tbody>
            <c:choose>
				<c:when
					test="${carCustomerContactPerson != null && fn:length(carCustomerContactPerson) > 0}">
               <c:forEach items="${carCustomerContactPerson}" var="per" varStatus="status">
               <tr>
                <td>
                <input type="hidden" mark="loanCode" value="${loanCode}" name="loanCode"></input>
		<input type="hidden" mark="newLoanCode" value="${newLoanCode}" name="newLoanCode">
                <input type="hidden" mark="id" name="id" value="${per.id}">
	  			<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType">
                 <input type="text" mark="contactName" name="contactName${status.index}" value="${per.contactName}" class="input_text70 required stringCheck"/>
                </td>
             
                <td>
                <select mark="dictContactRelation" name="dictContactRelation${status.index}" class="select180 required">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  <option value="${item.value}"<c:if test="${per.dictContactRelation==item.value}">
					      selected
					     </c:if>>${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" maxlength="20" mark="contactUint" name="contactUint${status.index}" value="${per.contactUint}" class="input_text178"/></td>
                <td>
                   <input type="text" maxlength="50" mark="dictContactNowAddress" name="dictContactNowAddress${status.index}" value="${per.dictContactNowAddress}" class="input_text150" style="width:250px">
                </td>
                <td><input type="text" mark="compTel" name="compTel${status.index}" value="${per.compTel}" class="input_text178 telOrPhone"/></td>
                <td><input type="text" mark="contactUnitTel" name="contactUnitTel${status.index}" value="${per.contactUnitTel}" class="input_text178 isMobile "/></td>
                <td class="tcenter"><input type="button" onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
              </tr>
             
              </c:forEach>
              </c:when>
              <c:otherwise>
              	<c:forEach  begin="0" end="2" varStatus="status">
					<tr>
					<td><input type="hidden" mark="loanCustomterType"
							value="1" name="loanCustomterType${status.index}"> <input
							type="text" mark="contactName" name="contactNameo${status.index}"
							class="input_text70 required stringCheck" /></td>
						<td><select mark="dictContactRelation"
								name="dictContactRelationo${status.index}" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_relation_type')}"
									var="item">
							<option value="${item.value}">${item.label}</option>
								</c:forEach>
									</select></td>
										<td><input type="text" mark="contactUint" maxlength="20"
												name="contactUinto${status.index}" class="input_text178" /></td>
										<td><input type="text" mark="dictContactNowAddress"
												name="dictContactNowAddresso${status.index}" class="input_text150"
											maxlength="50"	style="width: 250px"></td>
										<td><input type="text" mark="compTel" name="compTelo${status.index}"
												class="input_text178 simplePhone" /></td>
										<td><input type="text" mark="contactUnitTel"
												name="contactUnitTelo${status.index}" class="input_text178 isMobile required" /></td>
										<td class="tcenter"><input type="button"
												onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
										</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
              </tbody>
        </table>

        <div class="tright mb5 mr10" ><input type="button" id="contactExtendNextBtn" class="btn btn-primary" value="下一步"/></div>
        </form>
    	</div>
    </body>
     
</html>