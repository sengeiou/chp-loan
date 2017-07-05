<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<!-- 工作信息 -->
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript" src="${context}/js/car/apply/reviewMeetApply.js"></script>
<script type="text/javascript">
jQuery.validator.addMethod("telOrPhone", function(value,element) {
	var length = value.length;
	var mobile =/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
	var tel = /^\d{3,4}-?\d{7,9}$/; 
	return this.optional(element) || (tel.test(value) || mobile.test(value));
	}, "格式为：0XX(X)-XXXXXXX(X)或有效的11位手机号码");
$(document).ready(function(){
	
	loan.initCity("compProvince", "compCity","compArer");
	areaselect.initCity("compProvince", "compCity",
			 "compArer", $("#compCity").attr("value"),$("#compArer").attr("value"));
	
	//验证JS
	$("#nextBtn").bind('click',function(){
		  
		
	
	$("#carCompany").validate({
				onclick: true, 
				rules:{
					monthlyPay:{
						maxlength:15
					},
					incomMoney:{
						max : 9999999
					}
			},
			messages:{
				monthlyPay:{
					maxlength:'月均薪资输入的长度不能超过15'
				}
				
			}
	});
	$("#carCompany").submit();
	
	});
	// 客户放弃		
	$("#giveUpBtn").bind('click',function(){
		
		var url = ctx+"/car/carApplyTask/giveUp?loanCode="+ $("#loanCode").val();					
		
		art.dialog.confirm("是否客户放弃?",function(){
			$("#frameFlowForm").attr('action',url);
			$("#frameFlowForm").submit();
			});
	});
	
	//验证是否有其他收入，如果有显示收入金额输入框
	$('input:radio[name="isOtherRevenue"]').change(function() {
		var isOtherRevenue = $('input:radio[name="isOtherRevenue"]:checked ').val();
		if(isOtherRevenue){
			if(isOtherRevenue == '0'){
				$("#incomMoneyM").val("");
				$("#incomMoney").hide();
			}else{
				$("#incomMoney").show();
			}
		}
	});
	$('input:radio[name="isOtherRevenue"]').trigger("change");
});


</script>
<body>
<ul class="nav nav-tabs">
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCustomer?loanCode=${loanCode}');">个人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/carLoanFlowCarVehicleInfo?loanCode=${loanCode}');">车辆信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowInfo?loanCode=${loanCode}');">借款信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanCoborrower?loanCode=${loanCode}');">共同借款人</a></li>
		<li class="active"><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowCompany?loanCode=${loanCode}');">工作信息</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowContact?loanCode=${loanCode}');">联系人资料</a></li>
		<li><a href="javascript:reviewMeetApply.changeTabs('${ctx}/car/carApplyTask/toCarLoanFlowBank?loanCode=${loanCode}');">客户开户及管辖信息</a></li>
		<input type="button"  onclick="showCarLoanHis('${loanCode}')" class="btn btn-small r mr10" value="历史">
		<input type="button" id="giveUpBtn" class="btn btn-small r mr10"  value="客户放弃"></input></div>
	</ul>

<jsp:include page="_frameFlowForm.jsp"></jsp:include>	  
 <div id="div6" class="content control-group">
    <form id="carCompany" method="post" action="${ctx}/car/carApplyTask/carLoanFlowCompany">
	 	<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.flowType}" name="flowType"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
	 	<input type="hidden" id="loanCode" value="${loanCode}" name="loanCode">
	 	<input type="hidden" value="${customerCompany.id}" name="id">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                 <td  width="31%" >
                  <label class="lab"><span class="red">*</span>单位名称：</label>
                  <input type="text" name="companyName" maxlength="20" value="${customerCompany.companyName}" class="input_text178 required compNameCheck"/></td>
                
                
                
                <td><label class="lab"><span class="red">*</span>所属部门：</label><input type="text" value="${customerCompany.dictDepartment}" maxlength="20" class="input_text178 required compNameCheck" name="dictDepartment"></td>
                
                
                <td><label class="lab">成立时间：</label>
                <input id="d4311" name="establishedTime" value="<fmt:formatDate value='${customerCompany.establishedTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                    <input name="dictCustomerType" value="${customerCompany.dictCustomerType}" type="hidden"/>
                </td> 
            </tr>
            <tr>
                <td colspan="3" style="text-align:left;"><label class="lab"><span class="red">*</span>单位地址：</label>
                   <select class="select78 mr10 required" id="compProvince" name="dictCompanyProvince" >
                      <option value="">请选择</option>
                      <c:forEach var="item" items="${provinceList}" varStatus="status">
		               <option value="${item.areaCode}"
		               <c:if test="${customerCompany.dictCompanyProvince==item.areaCode}">
			     	 selected = true 
			    	 </c:if>>${item.areaName}</option>
	                  </c:forEach>
                   </select>
                   <select class="select78 mr10 required" id="compCity" name="dictCompanyCity">
                      <option value="">请选择</option>
                      <c:forEach var="item" items="${cityList}">
                      	<option value="${item.areaCode}"
                      	<c:if test="${customerCompany.dictCompanyCity==item.areaCode}">
                      	selected = true
                      	</c:if>>${item.areaName}</option>
                      </c:forEach>
                   </select>
                   <select class="select78 mr10 required" id="compArer" name="dictCompanyArea">
                      <option value="">请选择</option>
                        <c:forEach var="item" items="${districtList}">
                      	<option value="${item.areaCode}"
                      	<c:if test="${customerCompany.dictCompanyArea==item.areaCode}">
                      	selected = true
                      	</c:if>>${item.areaName}</option>
                      </c:forEach>
                   </select>
                   <input type="text" name="companyAddress" maxlength="50" value="${customerCompany.companyAddress}" class="input_text178 required" style="width:250px">
                </td>		
                
            </tr>
            <tr>
			<td><label class="lab"><span class="red">*</span>单位电话：</label>
				   <input type="text" name="workTelephone" value="${customerCompany.workTelephone}" class="input_text178 telOrPhone required"/>
				 </td>  
                
                <td><label class="lab">职务级别：</label>
				  <select id="compPost" name="dictPositionLevel" value="${customerCompany.dictPositionLevel}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_job_type')}" var="item">
					  <option value="${item.value}"
					    <c:if test="${customerCompany.dictPositionLevel==item.value}">
					       selected=true 
					    </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
	    		</td> 
                
                <td><label class="lab"><span class="red">*</span>月均薪资(元)：</label>
                <input type="text" class="input_text178 required number" maxlength="10" value="<fmt:formatNumber value='${customerCompany.monthlyPay}' pattern="##0.00"/>" name="monthlyPay"></td>
            </tr>
            <tr>
                <td><label class="lab" >起始服务时间：</label>
                <input id="d4311" name="firstServiceDate" value="<fmt:formatDate value='${customerCompany.firstServiceDate}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                   onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
                </td> 
                <td><label class="lab">单位性质：</label>
                <select name="dictUnitNature" value="${customerCompany.dictUnitNature}" class="select180">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_unit_type')}" var="item">
					  <option value="${item.value}"
					    <c:if test="${customerCompany.dictUnitNature==item.value}">
					       selected=true
					    </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select>
                </td>
			 <td><label class="lab">企业类型：</label> 
			 <select class="select180" name="dictEnterpriseNature" value="${customerCompany.dictEnterpriseNature}">
                     <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_company_type')}" var="item">
					  <option value="${item.value}"
					    <c:if test="${customerCompany.dictEnterpriseNature==item.value}">
					       selected=true 
					    </c:if>
					  >${item.label}</option>
				     </c:forEach>
				</select></td>
            </tr>
			<tr>
			<td><label class="lab" ><span class="red">*</span>其他收入：</label>
				<c:forEach	items="${fns:getDictList('jk_have_or_nohave')}" var="item">
							<input type="radio" name="isOtherRevenue"
								value="${item.value}" 
								<c:choose>
									<c:when test="${customerCompany.isOtherRevenue!=null && customerCompany.isOtherRevenue!=''}">
			               	        	<c:if test="${item.value eq customerCompany.isOtherRevenue}">
                    								 checked='true'
                 						</c:if>
			               	    	</c:when>
			               	    	<c:otherwise>
				               	       <c:if test="${item.value =='0'}">
                    								 checked='true'
                 						</c:if>
				               	     </c:otherwise>
								</c:choose>
								<%-- <c:if test="${item.value eq customerCompany.isOtherRevenue}">
                    								 checked='true'
                 				</c:if> --%>
							 />${item.label}
                </c:forEach>
                <span  id="incomMoney" ><label class="lab">收入金额：                                                  
                 <input type="text" id="incomMoneyM" name="incomMoney" maxlength="10" value="<fmt:formatNumber value='${customerCompany.incomMoney}' pattern="##0.00"/>" class="input_text178 required number" pattern='##0.00'/>
                 </span>
              </td>
			 <td></td>
              <td></td>
            </tr>
        </table>
        
        <div class=" tright mr10 mb5" ><input type="button" class="btn btn-primary"  id="nextBtn" value="下一步"/></div>
     </form>
    </div>
</body>
</html>