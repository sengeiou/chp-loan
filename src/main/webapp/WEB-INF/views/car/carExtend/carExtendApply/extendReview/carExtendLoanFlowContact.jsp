<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 联系人资料 -->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/car/carExtend/uploadReview.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/carExtendConsultForm.js"></script>
<script type="text/javascript" src="${context}/js/consult/consultValidate.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/validate.js"></script>
<script type="text/javascript">
CONTACT_NUM = 3;
//中英文校验
	$(document).ready(function(){
		jQuery.validator.addMethod("realName", function(value, element) {
		    return this.optional(element) || /^[\u4e00-\u9fa5\·]{2,30}$/.test(value);
		}, "姓名只能为2-10个汉字或·号");
		jQuery.validator.addMethod("telOrPhone", function(value,element) {
			var length = value.length;
			var mobile =/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			var tel = /^\d{3,4}-?\d{7,9}$/; 
			return this.optional(element) || (tel.test(value) || mobile.test(value));
		}, "格式为：0XX(X)-XXXXXXX(X)或有效的11位手机号码");
 
  });  
  function backCarLoanHis(){
	  location.href="${ctx}/bpm/flowController/openForm?applyId=${param.applyId}&loanCode=${loanCode}&wobNum=${param.wobNum}&dealType=${param.dealType}&token=${param.token}";
  }
</script>
</head>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarContract');">历史展期信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendInfo');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowCustomer');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendCompany');">工作信息</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarExtendContact');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toExtendCoborrower');">共同借款人</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carExtend/carExtendUpload/toCarLoanFlowBank');">客户开户信息</a></li>
		<input type="button" style="float: right"
			onclick="backCarLoanHis()" class="btn btn-small"
			value="返回合同审核主页">
	</ul>
    <jsp:include page="carExtendLoanFlowContactItem.jsp"></jsp:include>
<c:set var="param" value="${workItem.bv}" />
   <div id="div8" class="content control-group">
	 <div class=" ml10">
	    <input type="button" class="btn btn-small" id="contactAdd" value="增加联系人"></input>
      </div>
      <form class="hide" id="loanCodeParam">
      		<input type="hidden" value="${loanCode}" name="loanCode"></input>
     		<input type="hidden" name="applyId" value="${param.applyId}" />
			<input type="hidden" name="wobNum" value="${param.wobNum}" />
			<input type="hidden" name="dealType" value="${param.dealType}" />
			<input type="hidden" name="token" value="${param.token}" />
      </form>
      <form id="frameFlowForm" method="post">
			<input type="hidden" name="loanCode" value="${loanCode }" />
			<input type="hidden" name="newLoanCode" value="${newLoanCode }" />
			<input type="hidden" name="applyId" value="${param.applyId}" />
			<input type="hidden" name="wobNum" value="${param.wobNum}" />
			<input type="hidden" name="dealType" value="${param.dealType}" />
			<input type="hidden" name="token" value="${param.token}" />
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
               <c:forEach items="${carCustomerContactPerson}" var="per" varStatus="vaSt">
               <tr>
                <td>
                <input type="hidden" mark="loanCode" value="${per.loanCode}" name="loanCode"></input>
                <input type="hidden" mark="id" name="id" value="${per.id}">
	  			<input type="hidden" mark="loanCustomterType" value="0" name="loanCustomterType">
                 <input type="text" mark="contactName" name="contactName${vaSt.index }" value="${per.contactName}" class="input_text70 required realName"/>
                </td>
             
                <td>
                <select mark="dictContactRelation" name="dictContactRelation${vaSt.index }" class="select180 required">
                  <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_relation_type')}" var="item">
					  <option value="${item.value}"<c:if test="${per.dictContactRelation==item.value}">
					      selected
					     </c:if>>${item.label}</option>
				     </c:forEach>
				</select>
                </td>
                <td><input type="text" mark="contactUint" name="contactUint" value="${per.contactUint}" class="input_text178"/></td>
                <td>
                   <input type="text" mark="dictContactNowAddress" name="dictContactNowAddress" value="${per.dictContactNowAddress}" class="input_text150" style="width:250px">
                </td>
                <td><input type="text" mark="compTel" name="compTel" value="${per.compTel}" class="input_text178 isTel"/></td>
                <td><input type="text" mark="contactUnitTel" name="contactUnitTel" value="${per.contactUnitTel}" class="input_text178 required"/></td>
                <td class="tcenter"><input type="button" onclick="delContact(this);" class="btn_edit" value="删除"></input></td>
              </tr>
             
              </c:forEach>
              </tbody>
        </table>

        <div class="tright mb5 mr10" ><input type="button" class="btn btn-primary" id="contactSave" value="保存"/></div>
        </form>
    	</div>
    </body>
     
</html>