<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${context}/js/car/contract/confirmSign.js" type="text/javascript"></script>
<script src="${context}/js/car/carExtend/carExtendSigning.js" type="text/javascript"></script>
<script src="${context}/js/car/carExtend/carExtendDetailsCheck.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script type="text/javascript">
/**
 * 校验页面元素属性
 */
var confirmSign_Form = {};
confirmSign_Form.loanddd = function(){
	return $("#confirmSignForm").validate({
		rules : {
			'bankProvince':{
				required : true
			},
			'bankCardNo' :{
				required : true,
				equalTo  : "#bankCardNo_1",
				digits	 : true,
				min		 : 0,
				maxlength: 19,
				minlength: 14
			},
			'bankCardNo1':{
				required : true,
				digits	 : true,
				min		 : 0,
				maxlength: 19,
				minlength: 14
			}
		},
		messages : {
			'bankProvince':{
				required : "必须输入"
			},
			'bankCardNo' :{
				required : "必须输入",
				digits   :  "银行卡号必须为数字类型",
				equalTo  : "输入银行卡号不一致",
				min      :"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
				minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
			},
			'bankCardNo1':{
				required : "必须输入",
				digits   :  "银行卡号必须为数字类型",
				min      :"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
				minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
			}
		}
	}).form();
};

REDIRECT_URL="/car/carApplyTask/fetchTaskItems";

$(document).ready(function(){
	$('#retBtn').bind('click',function(){
		window.location=ctx+REDIRECT_URL;
	});
	
	loan.initCity("bankProvince", "bankCity",null);
	
	areaselect.initCity("bankProvince", "bankCity", null, $("#bankCityHidden").val(), null);
	
	$('#isRareword').bind('change', function() {
		if ($(this).prop('checked') == true || $(this).prop('checked') == 'checked') {
			$('#bankAccountName').removeAttr('readonly');
			$('#bankAccountName').val($("#bankAccountNameHidden").val());
			$('#isRareword').val("1");
		} else {
			$('#bankAccountName').attr("readonly","readonly");
			$('#bankAccountName').val($('#customerName').val());
			$('#isRareword').val("0");
		}
	});
	$('#isRareword').trigger("change");
});
</script>
</head>
<body>
<div class=" pt5 pr10 pb5">
        <div  class="tright">
         <input type="button" class="btn btn-small"  id="custGiveUp" value="客户放弃"/>
         <input type="button" class="btn btn-small"  id="historyBtn" onclick="showCarLoanHis('${workItem.bv.carLoanInfo.loanCode}')" value="历史"/>
         <input type="button" class="btn btn-small"  onclick="showExtendLoanInfo('${workItem.bv.applyId}')" value="查看">
         <button  class="btn btn-small"  data-target="#back_div" data-toggle="modal" >退回</button>
        </div>
</div>
<h2 class="f14 pl10">历史展期信息</h2>

     <form id="loanapplyForm">
      <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
      <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
      <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
      <input type="hidden" value="${workItem.token}" name="token"></input>
      <input type="hidden" value="${workItem.bv.applyId}" name="flowId"></input>
      <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
     
     </form>
    <c:set var="bv" value="${workItem.bv}"/>
    <form id="confirmSignForm"> 
     <input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
     <input type="hidden" value="${bv.carLoanInfo.loanCode}" name="loanCode" id="loanCode"/>
		<div class="box2 mb10">
			 <table class="table3" cellpadding="0" cellspacing="0" border="0" width="100%">
				<c:if test='${workItem.bv.carAuditResult.auditResult eq "附条件通过"}'>
					<tr>
						<td><span style="color: red;"><label class="lab">汇诚审批结果：</label>
								${workItem.bv.carAuditResult.auditResult}</span></td>
						<td><span style="color: red;"><label class="lab">汇城审批意见：
							</label>${workItem.bv.carAuditResult.auditCheckExamine}</span></td>
						<td></td>
					</tr>
				</c:if>
				<tr>
					<th>合同编号</th>
					<th>合同金额（元）</th>
					<th>借款期限（天）</th>
					<th>合同期限</th>
					<th>降额（元）</th>
				</tr>
				<c:if test="${workItem.bv.carContracts!=null && fn:length(workItem.bv.carContracts)>0}">
				<c:forEach items="${workItem.bv.carContracts}" var="carContracts">
					<tr>
						<td>${carContracts.contractCode}</td>
						<td><fmt:formatNumber value="${carContracts.contractAmount}" pattern="0.00" /></td>
						<td>${carContracts.contractMonths}</td>
						<td><fmt:formatDate value='${carContracts.contractFactDay}' pattern="yyyy-MM-dd"/>~<fmt:formatDate value='${carContracts.contractEndDay}' pattern="yyyy-MM-dd"/></td>
							<td>
								 <c:choose>
									<c:when test="${carContracts.derate == null}">
										0.00
									</c:when>
									<c:otherwise>
										<fmt:formatNumber value="${carContracts.derate}"
											pattern="0.00" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
				</c:forEach>
				</c:if>
			</table>
		</div>
		<h2 class=" f14 pl10 mt20">客户信息</h2>
    <div class="box2 ">      
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
 			
            <tr>
                <td><label class="lab">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
                <input type="hidden"  value="${workItem.bv.carCustomer.customerName}" id="customerName">
                <td width="500px"><input class="btn btn-small messageCheck" type="button"
						 stype="0" id="btnPin" 
						 <c:if test="${workItem.bv.carCustomer.captchaIfConfirm=='1'}">
								   disabled=true value="客户已验证"
						 </c:if>
						 <c:if test="${workItem.bv.carCustomer.captchaIfConfirm!='1'}">
						     value="发送验证码"
						 </c:if>
						onclick="sendPin(this,'${workItem.bv.carCustomer.customerPhoneFirst}','${workItem.bv.carCustomer.customerCode}','${workItem.bv.carCustomer.customerName}');" />&nbsp;
						<span><input type="text" class="input-medium" name="txt_pin" 
						<c:if test="${workItem.bv.carCustomer.captchaIfConfirm=='1'}">
								   disabled=true 
								   value="${workItem.bv.carCustomer.customerPin}" 
						 </c:if>
						id="txt_pin" onchange="confirmPin(this,'${workItem.bv.carCustomer.loanCode}','0');"/> 
						<label id="lab_co_pin_0" for="txt_co_pin_0"></label>
						</span>
						<input type="hidden" name="pin" id="pin" value="${workItem.bv.carCustomer.customerPin}"> 
				</td>
                <td><label class="lab">证件号码：</label>${workItem.bv.carCustomer.customerCertNum}</td>
                <td></td>
            </tr>
           <c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
					<td><input class="btn btn-small messageCheck" type="button"
						stype="${status.index + 1}"  id="btn_co_pin_${status.index + 1}" 
						<c:if test="${cobos.captchaIfConfirm=='1'}">
						   disabled=true value="客户已验证"
						</c:if> 
						<c:if test="${cobos.captchaIfConfirm!='1'}">
						  value="发送验证码" 
						</c:if>
						onclick="sendPin(this,'${cobos.mobile}','${cobos.id}','${cobos.coboName}');"
						/>&nbsp;<span><input  type="text" class="input-medium"
						name="txt_co_pin_${status.index + 1}" onchange="confirmPin(this,'${cobos.id}','${status.index + 1}');"
						id="txt_co_pin_${status.index + 1}"
						<c:if test="${cobos.captchaIfConfirm=='1'}">
						   disabled=true value="${cobos.customerPin}" 
						</c:if>
						 /><label id="lab_co_pin_${status.index + 1}", for="txt_co_pin_${status.index + 1}"></label></span>
						 <input type="hidden" name="co_pin_${status.index + 1}" id="co_pin_${status.index + 1}" value="${cobos.customerPin}">
					</td>
					<td><label class="lab">共借人证件号码：</label>${cobos.certNum}</td>
					<td></td>
				</tr>
			</c:forEach>
	</table>
	</div>
	<h2 class=" f14 pl10 mt20">借款信息</h2>
	 <div class="box2 "> 
		<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		 	
			<tr>
			<td><label class="lab">展期合同编号：</label>${workItem.bv.carContract.contractCode}</td>
			<td><label class="lab">合同金额：</label><fmt:formatNumber value="${workItem.bv.carContract.contractAmount}" pattern="0.00" />元</td>
			<td><label class="lab">展期降额金额：</label><fmt:formatNumber value="${workItem.bv.carContract.derate}" pattern="0.00" />元</td>
			</tr>
            <tr>
                <td><label class="lab">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
				<td><label class="lab">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
				<td><label class="lab">批借产品：</label>${workItem.bv.carContract.productTypeName}</td>
            </tr>
            <tr>
				<td><label class="lab">批借期限：</label>${workItem.bv.carContract.contractMonths}天</td>
				<td><label class="lab">总费率：</label><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.00" />%</td>
				<td><label class="lab">展期费用：</label><fmt:formatNumber value="${workItem.bv.carContract.extensionFee}" pattern="0.00" />元</td>
            </tr>

            <tr>
            	<td><label class="lab">展期应还总金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.extendPayAmount}" pattern="0.00" />元</td>
            	<sys:carExtendProductShow parkingFee="${workItem.bv.carLoanInfo.parkingFee}" flowFee="${workItem.bv.carLoanInfo.flowFee}" 
					dictGpsRemaining="${workItem.bv.carLoanInfo.dictGpsRemaining}" facilityCharge="${workItem.bv.carLoanInfo.facilityCharge}" 
					dictIsGatherFlowFee="${workItem.bv.carLoanInfo.dictIsGatherFlowFee}" dictSettleRelend="${workItem.bv.carLoanInfo.dictSettleRelend}" productType="${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',workItem.bv.carLoanInfo.dictProductType)}"></sys:carExtendProductShow>
            </tr>
            <c:if test="${workItem.bv.carLoanInfo.dictProductType=='CJ01' && workItem.bv.carLoanInfo.deviceUsedFee != null}">
            <tr>
            	<td>
            		<label class="lab">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee == null ? 0 : workItem.bv.carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元
            	</td>
            </tr>
            </c:if>
			</table>
	</div>
	<h2 class=" f14 pl10 mt20">客户银行卡信息</h2>
	<div class="box2 ">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
			  <td><label class="lab"></span>授权人：</label>${workItem.bv.carCustomer.customerName}</td>
			  <td><label class="lab"><span style="color:red">*</span>账户姓名：</label>
			      <input type="text" id="bankAccountName"  maxLength="20" name="bankAccountName" value="${workItem.bv.carCustomerBankInfo.bankAccountName}"
			      <c:if test="${workItem.bv.carCustomerBankInfo.israre != '1'}">
			     		 readonly="readonly"
			      </c:if> class="input-medium required"/>
			      
			      <input type="checkbox" id="isRareword" name="israre" value="${workItem.bv.carCustomerBankInfo.israre}" 
					 <c:if test="${workItem.bv.carCustomerBankInfo.israre == '1'}"> checked='checked'</c:if> />
					 <lable>是否生僻字</lable>
				 <input type="hidden" id="bankAccountNameHidden" value="${workItem.bv.carCustomerBankInfo.bankAccountName}"/>
			    </td>
			    <td><label class="lab"><span style="color:red">*</span>签约平台：</label>
			    	<select id="bankSigningPlatform" name="bankSigningPlatform"  class="select180 required">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="curItem">
                       <c:if test="${curItem.label!='中金' && curItem.value!='6'}">
					  <option value="${curItem.value}"
					    <c:if test="${workItem.bv.carCustomerBankInfo.bankSigningPlatform==curItem.value}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
					  </c:if>
				    </c:forEach>
				   </select>
			    </td>

			</tr>
			<tr>

               <td><label class="lab"><span style="color:red">*</span>开户省市：</label>
				    <select class="select78 mr34 required" name="bankProvince"  id="bankProvince">
				        <option value="">请选择</option>
				      <c:forEach items="${bv.provinceList}" var="item">
				         <option value="${item.areaCode}" 
				          <c:if test="${bv.carCustomerBankInfo.bankProvince==item.areaCode}">
		                    selected=true 
		                  </c:if> 
		               >${item.areaName}</option>
				        </c:forEach> 
				    </select>
				    <input type="hidden" id="bankCityHidden" value="${workItem.bv.carCustomerBankInfo.bankCity}"/>
				    <select class="select78 required" name="bankCity"  id="bankCity">
				      <option value="">请选择</option>
				    </select>
				   
				</td>
				<td><label class="lab"><span style="color:red">*</span>开卡行：</label>
			      <select id="bankName" name="cardBank" class="select180 required">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${workItem.bv.carCustomerBankInfo.cardBankCode==curItem.value}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach> 
				  </select>
			    </td>
                <td><label class="lab"><span style="color:red">*</span>开户支行：</label>
		              <input type="text" id="bankBranch" name="applyBankName" maxLength="20" value="${workItem.bv.carCustomerBankInfo.applyBankName}" class="input-medium required">
		  
		        </td>
            </tr>
            
			 <tr>

		        <td><label class="lab"><span style="color:red">*</span>银行账号：</label>
		          <input type="text" id="bankCardNo_1" name="bankCardNo1" value="${workItem.bv.carCustomerBankInfo.bankCardNo}" class="input-medium required"/>

	            </td>
				<td><label class="lab"><span style="color:red">*</span>确认银行账号：</label>
		          <input type="text" id="bankCardNo_2" name="bankCardNo" value="${workItem.bv.carCustomerBankInfo.bankCardNo}" class="input-medium required">
	            </td>
	            <td><label class="lab"><span style="color: red">*</span>合同签订日期：</label><fmt:formatDate value='${workItem.bv.lastContractEndDateSecond}' pattern="yyyy-MM-dd"/>
                	<input id="contractDueDay" name="contractDueDay" value="<fmt:formatDate value='${workItem.bv.lastContractEndDateSecond}' pattern="yyyy-MM-dd"/>"
						type="hidden"/>
                <%-- <input id="contractDueDay" name="contractDueDay" readonly="readonly"
						value="<fmt:formatDate value='${workItem.bv.lastContractEndDateSecond}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178 required" size="10"
						<c:choose>
							<c:when test="${workItem.bv.lastContractEndDateSecond == null}">
								onClick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})" 
							</c:when>
							<c:otherwise>
								onClick="WdatePicker({skin:'whyGreen',minDate:'${workItem.bv.lastContractEndDateSecond}'})"						
							</c:otherwise>
						</c:choose>
						style="cursor: pointer" class="required"/>
						<span id="messageBox" style="color:red;"></span> --%>
               </td>
               
            </tr>

        </table>
    </div>
     <div class="tright mt10 pr30" style="text-align:right;">
     <input type="button" class="btn btn-primary" value="提交" id="confirmSignBtn"></input>
     <input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="取消"
		onclick="JavaScript:history.go(-1);"></input> 
     </div>
    </form>
    
    	<!--退回弹框  -->
    <div  id="back_div" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
		<div class="modal-dialog role="document"">
				<div class="modal-content">
				<div class="modal-header">
					   <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						
						<div class="l">
         					<h4 class="pop_title">退回</h4>
       					</div>
					 </div>
				 <div class="modal-body">
			
				    <table class="table4" cellpadding="0" cellspacing="0" border="0" width="100%" id="tpTable">
				    <tr>
				    	<td><lable class="lab"><span style="color: red;">*</span>退回流程节点：</lable>
								<select
									id="backNodeId" name="checkResult" class="required">
						
										<c:forEach
											items="${fns:getDictLists('7', 'jk_car_loan_status')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select>
				 		</td>
				    </tr>
          			 <tr>
						<td><lable class="lab"><span style="color: red;">*</span>退回原因：</lable>
								<textarea rows="" cols="" class="textarea_refuse required" id="remark" maxlength="200"></textarea>
						</td>
					</tr>
        </table>
				 </div>
				 <div class="modal-footer">
				 <button id="backSure" class="btn btn-primary" data-dismiss="modal">确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>
				
	</div>
</body>
</html>