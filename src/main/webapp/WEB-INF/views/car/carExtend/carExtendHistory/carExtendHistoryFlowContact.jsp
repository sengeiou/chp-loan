<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 联系人资料 -->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/addContact.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/contactFormat.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carExtendConsultForm.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript">
  CONTACT_NUM = 3;
  $(document).ready(function(){
		//验证JS
			$("#qnm").validate({
						onclick: true, 
						rules:{
							
					},
						messages : {
						
						}
			});
	 });  

</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/queryHistoryExtend?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendInfo?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowCustomer?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarExtendCompany?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">工作信息</a></li>
		<li class="active"><a>联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toExtendCoborrower?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtendHistory/toCarLoanFlowBank?loanCode=${loanCode}&newLoanCode=${newLoanCode}');">客户开户信息</a></li>
	</ul>
	<jsp:include page="frameFlowFormExtend.jsp"></jsp:include>
    <jsp:include page="carExtendHistoryFlowContactItem.jsp"></jsp:include>
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
            	<c:when test="${carCustomerContactPerson != null && fn:length(carCustomerContactPerson) > 0}">
            		<c:forEach items="${carCustomerContactPerson}" var="per" varStatus="conStatus">
		               <tr>
		                <td>
		                <input type="hidden" mark="loanCode" value="${per.loanCode}" name="loanCode"></input>
		                <input type="hidden" mark="id" name="id" value="${per.id}">
			  			<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType">
		                 <input type="text" mark="contactName"  name="contactName${conStatus.index }" value="${per.contactName}" class="input_text70 required stringCheck contactCheck"/>
		                </td>
		             
		                <td>
		                <select mark="dictContactRelation" name="dictContactRelation" class="select180 required" disabled="disabled">
		                  <option value="">请选择</option>
		                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
							  <option value="${item.value}"<c:if test="${per.dictContactRelation==item.value}">
							      selected
							     </c:if>>${item.label}</option>
						     </c:forEach>
						</select>
		                </td>
		                <td><input type="text" mark="contactUint" readonly="readonly" name="contactUint${conStatus.index }" value="${per.contactUint}" class="input_text178 companyName"/></td>
		                <td>
		                   <input type="text" mark="dictContactNowAddress" readonly="readonly" name="dictContactNowAddress${conStatus.index }" value="${per.dictContactNowAddress}" class="input_text150 detailAddress" style="width:250px">
		                </td>
		                <td><input type="text" mark="compTel" readonly="readonly" name="compTel" value="${per.compTel}" class="input_text178  isTel"/></td>
		                <td><input type="text" mark="contactUnitTel" readonly="readonly" name="contactUnitTel" value="${per.contactUnitTel}" class="input_text178 isMobile required"/></td>
		                <td class="tcenter"><input type="button" onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
		              </tr>
		              </c:forEach>
            	</c:when>
            	<c:otherwise>
            		<c:forEach begin="0" end="2" varStatus="status">
	            		<tr>
			                <td>
			                <input type="hidden" mark="loanCode" name="loanCode"></input>
			                <input type="hidden" mark="id" name="id">
				  			<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType">
			                 <input type="text" mark="contactName" name="contactName${status.index}" class="input_text70 required realName"/>
			                </td>
			                <td>
			                <select mark="dictContactRelation" name="dictContactRelation" class="select180 required">
			                  <option value="">请选择</option>
			                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
								  <option value="${item.value}">${item.label}</option>
							     </c:forEach>
							</select>
			                </td>
			                <td><input type="text" mark="contactUint" name="contactUint${status.index}" class="input_text178 companyName" /></td>
			                <td>
			                   <input type="text" mark="dictContactNowAddress" name="dictContactNowAddress${status.index}" class="input_text150  detailAddress" style="width:250px">
			                </td>
			                <td><input type="text" mark="compTel" name="compTel${status.index}" class="input_text178 isTel"/></td>
			                <td><input type="text" mark="contactUnitTel" name="contactUnitTel${status.index}" class="input_text178 isMobile required"/></td>
			                <td class="tcenter"><input type="button" onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
			             </tr>
			         </c:forEach>
            	</c:otherwise>
            </c:choose>
              </tbody>
        </table>
        <div class="tright mb5 mr10" ><input type="button" id="contactExtendHisNextBtn" class="btn btn-primary" value="下一步"/></div>
        </form>
    	</div>
    </body>
     
</html>