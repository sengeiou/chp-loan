<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>待确认签署</title>
<meta name="decorator" content="default" />
<script src="${context}/js/car/contract/contractAudit.js" type="text/javascript"></script>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src="${context}/js/pageinit/areaselect.js"></script>
<script src="${context}/js/car/contract/confirmSign.js" type="text/javascript"></script>
<script type="text/javascript">
/**
 * 校验页面元素属性
 */
var confirmSign_Form = {};
confirmSign_Form.loanddd = function(){
	
	  return $("#confirmSignForm").validate({
			rules : {
				'carCustomerBankInfo.bankProvince':{
					required : true
				},
				'carCustomerBankInfo.bankCardNo' :{
					required : true,
					digits	 : true,
					min		 : 0,
					maxlength: 19,
					minlength: 14
				},
				'carCustomerBankInfo.bankCardNo1':{
					required : true,
					equalTo  : "#bankCardNo_1",
					digits	 : true,
					min		 : 0,
					maxlength: 19,
					minlength: 14
				},
				'carContract.customerPin':{
					required : true
				}
			},
			messages : {
				'carCustomerBankInfo.bankProvince':{
					required : "必须输入"
				},
				'carCustomerBankInfo.bankCardNo' :{
					required : "必须输入",
					digits   :  "银行卡号必须为数字类型",
					min      :"输入的数据不合法",
					maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
					minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
				},
				'carCustomerBankInfo.bankCardNo1':{
					required : "必须输入",
					equalTo  : "输入银行卡号不一致",
					digits   :  "银行卡号必须为数字类型",
					min      :"输入的数据不合法",
					maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串"),
					minlength:$.validator.format("请输入一个长度最少是 {0} 的数字字符串")
				},
				'carContract.customerPin':{
					required : "必须输入"
				}
			} 
	  }).form();
};




   REDIRECT_URL="/car/carApplyTask/fetchTaskItems";
  $(document).ready(function(){
	  
	  $('#backSure').bind('click',function(){
		  
		  dispatchFlow('','TO_FRIST_AUDIT',REDIRECT_URL); 
		}); 
	  $('#custGiveUp').bind('click',function(){
		  art.dialog.confirm('是否确定执行放弃操作', function () {
			  $('#dictOperateResult').val("3");
			  dispatchFlow('','SIGN_GIVE_UP',REDIRECT_URL); 
			  	var dialog = art.dialog({
			  	    title: '请等待..',
			  	    lock:true
			  	});
			}, function () {
			});
		}); 
      $('#confirmSignBtn').bind('click',function(){
    	  if(hasValid){
    		  alert("请验证手机！");
    		  return false;
    		  
    	  }
    	   if(!confirmSign_Form.loanddd()){
    		  return;
    	  }
    	  
    	   var contractDueDay = $('#contractDueDay').val();
    	  if(contractDueDay==''){
    		 $('#messageBox').html("合同签订日期不能为空");  
    		 return false;
    	  }else{
    		 $('#messageBox').html(""); 
    	     
    	  }
    	  $('#applyBankName').val($('#bankName option:selected').text());
    	  $('#signingPlatformName').val($("#bankSigningPlatform option:selected").text()); 
       	  $('#applyBankBranch').val($('#bankBranch').val());
    	  $('#applyBankAccount').val($('#bankAccount').val());
     	  dispatchFlow('','TO_MAKE_CONTRACT',REDIRECT_URL); 
    	  var dialog = art.dialog({
		  	    title: '请等待..',
		  	    lock:true
		  	});  
	  });
	  $('#historyBtn').bind('click',function(){
		  showCarLoanHis($(this).attr('loanCode'));
	  });  
	  //查看详细信息
	  $('#showView').bind('click',function(){
		  showCarLoanInfo($(this).attr('loanCode'));
	  });
	  $('#retBtn').bind('click',function(){
			window.location=ctx+REDIRECT_URL;
		}); 
	  loan.initCity("bankProvince", "bankCity",null);
  });
  $(document).ready(
  		function() {
  			areaselect.initCity("bankProvince", "bankCity",
  					  null, $("#bankCityHidden")
  							.val(), null);
  			
 
   });
  

  
	   
	
  //停车费和设备费根据批借产品互斥显示；平台流量费只针对GPS类产品，移交与质押不收取，不可默认带出
  $(document).ready(function(){
		// 账户姓名 生僻字change事件
	 	  $('#isRareword').bind('change',function(){
				if($(this).attr('checked') == 'checked'){
					$('#bankAccountName').removeAttr("readonly");
					$('#isRareword').val("1");
				} else {
					$("#bankAccountName").val($("#hiddenBankAccountName").val());
					$('#bankAccountName').attr("readonly","readonly");
					$('#isRareword').val("0");
				}
		 });
	 	 if("${workItem.bv.carCustomerBankInfo.bankAccountName}"==""){
				$("#bankAccountName").val($("#hiddenBankAccountName").val());
		}
		var productTypeName = $("#productTypeName").val();
		if(productTypeName=='GPS'){
			$("#parkingFee").hide();
			$("#facilityCharge").show();
			if ($("#GPSchai").val() == '2' && $("#getFee").val() == '1' ) {
				$("#flowFee").show();
			} else {
				$("#flowFee").hide();
			}
		}else {
			$("#parkingFee").show();
			$("#facilityCharge").hide();
			$("#flowFee").hide();
		}
	});

  
</script>
</head>
<body>
<div class="tright pt10 pr30 mb10">
         <input type="button" class="btn btn-small"  id="custGiveUp" value="客户放弃"/>
         <input type="button" class="btn btn-small"  id="historyBtn" loanCode="${workItem.bv.loanCode}"  value="历史"/>
         <input type="button" class="btn btn-small"  id="showView" loanCode="${workItem.bv.loanCode}" value="查看">
         
        <%--  <c:if test="${workItem.bv.isConCheckBack == '1'}"> --%>
         <button  class="btn btn-small"  data-target="#back_div" data-toggle="modal">退回</button>
       <%--   </c:if> --%>
         
</div>

     <form id="loanapplyForm">
      <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
      <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
      <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
      <input type="hidden" value="${workItem.token}" name="token"></input>
      <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
      <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
      <input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
      <input type="hidden" value="${workItem.bv.loanCode}" name="loanCode" id="loanCode"></input>
      <input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
     </form>
    <c:set var="bv" value="${workItem.bv}"/>
    <form id="confirmSignForm"> 
     <input type="hidden" value="${bv.applyId}" name="carContract.applyId"/>
     <input type="hidden" value="${bv.carContract.loanCode}" name="carContract.loanCode"/>
     <input type="hidden" value="${bv.carContract.contractCode}" name="carContract.contractCode"/>
     <input type="hidden" value="${bv.carContract.contractAmount}" name="carContract.contractAmount"/>
     <input type="hidden" value="2" id="dictOperateResult" name="dictOperateResult"/>
     <input type="hidden" value="${workItem.bv.carCustomerBankInfo.israre}" name="israre" ></input>
     <h2 class=" f14 pl10 mt20">客户信息</h2>
    <div class="box2 ">      
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        <c:if test='${workItem.bv.carAuditResult.auditResult eq "附条件通过"}'>
        	<tr>
                <td><span style="color:red;"><label class="lab">汇诚审批结果：</label> ${workItem.bv.carAuditResult.auditResult}</span></td>
                <td><span style="color:red;"><label class="lab">汇城审批意见： </label>${workItem.bv.carAuditResult.auditCheckExamine}</span></td>
                <td></td>
            </tr>
         </c:if>
            <tr>
                <td><label class="lab">客户姓名：</label>${workItem.bv.carCustomer.customerName}</td>
                <td width="500px"><input class="btn btn-small" type="button"
						 stype="0" id="btnPin" 
						 <c:if test="${workItem.bv.carCustomer.captchaIfConfirm=='1'}">
								   disabled=true value="客户已验证"
						 </c:if>
						 <c:if test="${workItem.bv.carCustomer.captchaIfConfirm!='1'}">
						     value="发送验证码"
						 </c:if>
						onclick="sendPin(this,'${workItem.bv.carCustomer.customerPhoneFirst}','${workItem.bv.carCustomer.customerCode}','${workItem.bv.carCustomer.customerName}');" />&nbsp;
						<span><input type="text" class="input-medium required" name="txt_pin" 
						<c:if test="${workItem.bv.carCustomer.captchaIfConfirm=='1'}">
								   disabled=true 
								   value="${workItem.bv.carCustomer.customerPin}" 
						 </c:if>
						id="txt_pin" onchange="confirmPin(this,'${workItem.bv.carCustomer.loanCode}','0');" name="carContract.customerPin"/> 
						<label id="lab_co_pin_0"  for="txt_co_pin_0"></label>
						</span>
						<input type="hidden" name="pin" id="pin" value="${workItem.bv.carCustomer.customerPin}"> 
				</td>
                <td><label class="lab">证件号码：</label>${workItem.bv.carCustomer.customerCertNum}</td>
                <td></td>
            </tr>
           <c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos" varStatus="status">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.coboName}</td>
					<td><input class="btn btn-small" type="button"
						stype="${status.index + 1}"  id="btn_co_pin_${status.index + 1}" 
						<c:if test="${cobos.captchaIfConfirm=='1'}">
						   disabled=true value="客户已验证"
						</c:if> 
						<c:if test="${cobos.captchaIfConfirm!='1'}">
						  value="发送验证码" 
						</c:if>
						onclick="sendPin(this,'${cobos.mobile}','${cobos.id}','${cobos.coboName}');"
						/>&nbsp;<span><input  type="text" class="input-medium required" 
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
	 <div class="box2"> 
	 	<input type="hidden" id="productTypeName" value="${workItem.bv.carContract.productTypeName}"/>
		<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
				<td><!-- <label class="lab">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel} --></td>
				<td></td>
            </tr>
            <tr>
                <td><label class="lab">总费率：</label><fmt:formatNumber value="${workItem.bv.carContract.grossRate}" pattern="0.00" />%</td>
				<td><label class="lab">批借期限：</label>${workItem.bv.carContract.contractMonths}天</td>
            </tr>
            <sys:carProductShow parkingFee="${workItem.bv.carLoanInfo.parkingFee}" flowFee="${workItem.bv.carLoanInfo.flowFee}" 
            dictGpsRemaining="${workItem.bv.carLoanInfo.dictGpsRemaining}" facilityCharge="${workItem.bv.carLoanInfo.facilityCharge}" 
            dictIsGatherFlowFee="${workItem.bv.carLoanInfo.dictIsGatherFlowFee}" 
            dictSettleRelend="${workItem.bv.carLoanInfo.dictSettleRelendName}" productType="${workItem.bv.carContract.productTypeName}"></sys:carProductShow>
            <tr>
                <td><label class="lab">合同编号：</label>${workItem.bv.carContract.contractCode}</td>
                <td><input type="hidden" id="GPSchai" value="${workItem.bv.carLoanInfo.dictGpsRemaining }"/>
                    <input type="hidden" id="getFee" value="${workItem.bv.carLoanInfo.dictIsGatherFlowFee }"/>
                    <c:if test="${workItem.bv.carContract.contractVersion ne '1.3'}">
                	<label class="lab">首期服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.firstServiceTariffing}" pattern="0.00" />元
                	</c:if>
                </td>
                <td><c:if test="${workItem.bv.carContract.contractVersion ne '1.3'}"><label class="lab">综合服务费：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.comprehensiveServiceFee}" pattern="0.00" />元</c:if></td>
            </tr>
            <c:if test="${workItem.bv.carAuditResult.outVisitDistance != null and workItem.bv.carAuditResult.outVisitDistance > 0}">
	            <tr>
					<td><label class="lab" >外访距离：</label><fmt:formatNumber  value="${workItem.bv.carAuditResult.outVisitDistance}"  pattern="0.00"  />km</td>
					<td><label class="lab" >外访费：</label><fmt:formatNumber  value="${workItem.bv.carCheckRate.outVisitFee}"  pattern="0.00"  />元</td>
					<td></td>
				</tr>
			</c:if>
            <tr>
                <td><label class="lab">合同金额：</label><fmt:formatNumber value="${workItem.bv.carContract.contractAmount}" pattern="0.00" />元</td>
                <td><label class="lab">实放金额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.feePaymentAmount}" pattern="0.00" />元</td>
                <td><label class="lab">每月还款额：</label><fmt:formatNumber value="${workItem.bv.carCheckRate.monthRepayAmount}" pattern="0.00" />元</td>
            </tr>
            <c:if test="${workItem.bv.carContract.productTypeName=='GPS'}">
	            <tr>
	            	<td><label class="lab">设备使用费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.deviceUsedFee == '' ? 0 : workItem.bv.carLoanInfo.deviceUsedFee }" pattern="#,##0.00" />元</td>
	            	<td></td>
	            	<td></td>
	            </tr>
	        </c:if>
			</table>
	</div>
	<h2 class=" f14 pl10 mt20">客户银行卡信息</h2>
	<div class="box2">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			
			<tr>
				<td><label class="lab"></span>授权人：</label>${workItem.bv.carCustomer.customerName}&nbsp;
				</td>
               <td><label class="lab"><span style="color:red">*</span>账户姓名：</label>
			      <input type="text" id="bankAccountName" name="carCustomerBankInfo.bankAccountName" value="${workItem.bv.carCustomerBankInfo.bankAccountName}" <c:if test="${workItem.bv.carCustomerBankInfo.israre ne '1'}">readonly="readonly"</c:if> class="input-medium required"/>
			      <input type="hidden" id="hiddenBankAccountName" value="${workItem.bv.carLoanInfo.loanCustomerName}" />
			      <input type="checkbox" id="isRareword" name="carCustomerBankInfo.israre" value="${workItem.bv.carCustomerBankInfo.israre}"
			       <c:if test="${workItem.bv.carCustomerBankInfo.israre == '1'}">checked="checked"</c:if> /><lable>是否生僻字</lable>
			      <input type="hidden" name="carCustomerBankInfo.id" value="${workItem.bv.carCustomerBankInfo.id}"></input>
			    </td>
               <td><label class="lab"><span style="color:red">*</span>开户省市：</label>
				    <select class="select78 mr34 required" name="carCustomerBankInfo.bankProvince"  id="bankProvince">
				        <option value="">请选择</option>
				      <c:forEach items="${workItem.bv.provinceList}" var="item">
				         <option value="${item.areaCode}" 
				          <c:if test="${workItem.bv.carCustomerBankInfo.bankProvince==item.areaCode}">
		                    selected=true 
		                  </c:if> 
		               >${item.areaName}
				        </c:forEach> 
				    </select>
				     <input type="hidden" id="bankProvinceHidden" value="${workItem.bv.carCustomerBankInfo.bankCity}"/>
				    <select class="select78 required" name="carCustomerBankInfo.bankCity"  id="bankCity">
				      <option value="">请选择</option>
				    </select>
				    <input type="hidden" id="bankCityHidden" value="${workItem.bv.carCustomerBankInfo.bankCity}"/>
				</td>
         
            </tr>
            
			 <tr>
			  	<td><label class="lab"><span style="color:red">*</span>开卡行：</label>
			      <select id="bankName" name="carCustomerBankInfo.cardBank" value="${workItem.bv.carCustomerBankInfo.cardBank}" class="select180 required">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${workItem.bv.carCustomerBankInfo.cardBank==curItem.label}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach> 
				  </select>
				  <input type="hidden" name="applyBankName" id="applyBankName"/>
			    </td>
		        <td><label class="lab"><span style="color:red">*</span>银行账号：</label>
		          <input type="text" id="bankCardNo_1" name="carCustomerBankInfo.bankCardNo" value="${workItem.bv.carCustomerBankInfo.bankCardNo}" class="input_text178">
		          <input type="hidden" name="applyBankAccount" id="applyBankAccount"/>
	            </td>
				<td><label class="lab"><span style="color:red">*</span>确认银行账号：</label>
		          <input type="text" id="bankCardNo_2" name="carCustomerBankInfo.bankCardNo1" value="${workItem.bv.carCustomerBankInfo.bankCardNo}" class="input-medium required">
		          <input type="hidden" name="applyBankAccount" id="applyBankAccount"/>
	            </td>
               
            </tr>
			<tr>
				<td><label class="lab"><span style="color:red">*</span>开户支行：</label>
		              <input type="text" id="bankBranch" name="carCustomerBankInfo.applyBankName" maxlength="50" value="${workItem.bv.carCustomerBankInfo.applyBankName}" class="input-medium required">
		              <input type="hidden" name="applyBankBranch" id="applyBankBranch"/>
		        </td>

                <td><label class="lab"><span style="color: red">*</span>合同签订日期：</label>
                <input type="hidden" name="carContract.contractMonths" value="${workItem.bv.carContract.contractMonths}"/>
                <input id="contractDueDay" name="carContract.contractDueDay"
						value="<fmt:formatDate value='${workItem.bv.carContract.contractDueDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178 required" size="10"
						onClick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd'})"
						style="cursor: pointer" class="required"/>
						<span id="messageBox" style="color:red;"></span>
               </td>
			   	<td><label class="lab"><span style="color: red">*</span>签约平台：</label>
                <select id="bankSigningPlatform" name="carCustomerBankInfo.bankSigningPlatform" value="${workItem.bv.carCustomerBankInfo.cardBank}" class="select180 required">
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
				<span id="messageBox" style="color:red;"></span>
               </td></td>
			</tr>
            
        </table>
    </div>
     <div class="tright pt10 pr30" ><input type="button" class="btn btn-primary" value="提交" id="confirmSignBtn"></input>
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
									id="backNodeId"  name="checkResult" class="required">
						
										<c:forEach
											items="${fns:getDictLists('7', 'jk_car_loan_status')}"
											var="item">
											<option value="${item.value}">${item.label}</option>
										</c:forEach>
								</select>
				 		</td>
				    </tr>
          			 <tr>
						<td><lable class="lab">退回原因：</lable>
								<textarea rows="" cols="" class="textarea_refuse"
									id="remark" class="required"></textarea>
						</td>
					</tr>
        </table>
				 </div>
				 <div class="modal-footer">
				 <button id="backSure" class="btn btn-primary" aria-hidden="true" >确定</button>
				 <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">取消</button>
				 </div>
				</div>
				</div>
				
	</div>
</body>
</html>