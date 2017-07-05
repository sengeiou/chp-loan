<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 联系人资料 -->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/addContact.js"></script>
<script type="text/javascript" src="${context}/js/launch/contactFormat.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carConsultForm.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript">
  CONTACT_NUM = 3;
  $(document).ready(function(){
	  
		//验证JS
			$("#qnm").validate({
						onclick: true, 
						rules:{
							contactUnitTel:{
								mobile:true,
							},
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
		// 客户放弃		
		$("#giveUpBtn").bind('click',function(){
			
			var url = ctx+"/car/carApplyTask/giveUp?loanCode="+ $("#loanCode").val();				
			
			art.dialog.confirm("是否客户放弃?",function(){
				$("#workItemParam").attr('action',url);
				$("#workItemParam").submit();
			});
		});
		
	    
		jQuery.validator.addMethod("realName2", function(value, element) {
		    return this.optional(element) || /^[\u4e00-\u9fa5\·]{2,30}$/.test(value);
		}, "姓名只能为2-10个汉字或·号");
	 });  

</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${loanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${loanCode}');">车辆信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${loanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${loanCode}');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${loanCode}');">工作信息</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${loanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${loanCode}');">客户开户及管辖信息</a></li>
		<input type="button"  onclick="showCarLoanHis('${loanCode}')" class="btn btn-small r mr10" value="历史">
		<input type="button"  id="giveUpBtn" class="btn btn-small r mr10"  value="客户放弃"></input></div>
	</ul>
    <jsp:include page="carLoanFlowContactItem.jsp"></jsp:include>
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
      <input type="hidden" id="loanCode" value="${loanCode}" name="loanCode"></input>
      </form>
      <form id="qnm">
        <table  id="contactArea" class="table  table-bordered table-condensed table-hover" >
            <thead>
            <tr>
                <th><span class="red">*</span>姓名</th>
                <th><span class="red">*</span>和本人关系</th>
                <th>工作单位</th>
                <th>家庭或工作单位详细地址</th>
                <th>单位电话</th>
                <th><span class="red">*</span>手机号</th>
                <th>操作</th>
                </tr>
            </thead>
            <tbody>
            <c:choose>
				<c:when
					test="${carCustomerContactPerson != null && fn:length(carCustomerContactPerson) > 0}">
               <c:forEach items="${carCustomerContactPerson}" var="per" varStatus="status">
               <tr>
                <td>
                <input type="hidden" mark="loanCode" value="${per.loanCode}" name="loanCode"></input>
                <input type="hidden" mark="id" name="id" value="${per.id}">
	  			<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType">
                <input type="text" mark="contactName" maxlength="10" name="contactName${status.index}" value="${per.contactName}" class="input_text70 required realName2"/>
                </td>
             
                <td>
                <select id="dictContactRelation${status.index }" mark="dictContactRelation" name="dictContactRelation" class="select180 required">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  <option value="${item.value}"<c:if test="${per.dictContactRelation==item.value}">
					      selected
					     </c:if>>${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" maxlength="20" mark="contactUint" name="contactUint" value="${per.contactUint}" class="input_text178 companyName"/></td>
                <td>
                   <input type="text" maxlength="50" mark="dictContactNowAddress" name="dictContactNowAddress" value="${per.dictContactNowAddress}" class="input_text150 detailAddress" style="width:250px">
                </td>
                <td><input type="text" id="compTel${status.index}" mark="compTel" name="compTel" value="${per.compTel}" class="input_text178  isTel"/></td>
                <td><input type="text" id="contactUnitTel${status.index}" mark="contactUnitTel" name="contactUnitTel" value="${per.contactUnitTel}" class="input_text178 isMobile required"/></td>
                <td class="tcenter"><input type="button" onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
              </tr>
             
              </c:forEach>
              </c:when>
              <c:otherwise>
              	<c:forEach  begin="0" end="2" varStatus="status">
					<tr>
					<td>
						<input type="hidden" mark="id" name="id" />
						<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType"> 
						<input type="text" mark="contactName" name="contactName${status.index}" maxlength="10" class="input_text70 required realName2" />
					</td>
					<td>
						<select mark="dictContactRelation" name="dictContactRelation" class="select180 required">
							<option value="">请选择</option>
							<c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
								<option value="${item.value}">${item.label}</option>
							</c:forEach>
						</select>
					</td>
					<td><input type="text" mark="contactUint" maxlength="20"
							name="contactUint" class="input_text178 companyName" /></td>
					<td><input type="text" mark="dictContactNowAddress" maxlength="50"
							name="dictContactNowAddress" class="input_text150 detailAddress"
							style="width: 250px"></td>
					<td><input type="text" mark="compTel" name="compTel"
							class="input_text178 isTel" /></td>
					<td><input type="text" mark="contactUnitTel"
							name="contactUnitTel${status.index}" class="input_text178 required isMobile" />  
							</td>
					<td class="tcenter"></td>
					</tr>
							</c:forEach>
						</c:otherwise>
					</c:choose>
              </tbody>
        </table>

        <div class="tright mb5 mr10" ><input type="button" id="contactNextBtn" class="btn btn-primary" value="下一步"/></div>
        </form>
    	</div>
    </body>
     
</html>