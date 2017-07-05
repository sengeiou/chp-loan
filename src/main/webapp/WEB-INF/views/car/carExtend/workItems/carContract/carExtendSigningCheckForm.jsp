<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="${context}/js/car/contract/contractAudit.js" type="text/javascript"></script>
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
				'carCustomerBankInfo.bankProvince':{
					required : true
				},
				'carCustomerBankInfo.bankCardNo' :{
					required : true,
					digits	 : true,
					min		 : 0,
					maxlength: 19,
					minlength: 16
				},
				'carCustomerBankInfo.bankCardNo1':{
					required : true,
					equalTo  : "#bankCardNo_1",
					digits	 : true,
					min		 : 0,
					maxlength: 19,
					minlength: 16
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
				}
			} 
	  }).form();
};




   REDIRECT_URL="/car/carApplyTask/fetchTaskItems";
  $(document).ready(function(){
	  $('#custGiveUp').bind('click',function(){
		  art.dialog.confirm('是否确定执行放弃操作', function () {
			  $('#dictOperateResult').val("3");
			  dispatchFlow('','CUSTOMER_GIVE_UP',REDIRECT_URL); 
			  	var dialog = art.dialog({
			  	    title: '请等待..',
			  	    lock:true
			  	});
			}, function () {
			});
		}); 
      $('#confirmSignBtn').bind('click',function(){
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
  

  
  
  
</script>
</head>
<body>
<div class=" pt10 pr30 ">
        <div  class="r">
         <input type="button" class="btn btn-small"  id="custGiveUp" value="客户放弃"/>
         <input type="button" class="btn btn-small"  id="historyBtn" loanCode="${workItem.bv.loanCode}"  value="历史"/>
         <input type="button" class="btn btn-small"  id="showView" loanCode="${workItem.bv.loanCode}" value="查看">
        </div>
</div>
<h2 class="f14 ml10"></h2>
     <form id="loanapplyForm">
      <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
      <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
      <input type="hidden" value="${workItem.stepName}" name="stepName"></input>
      <input type="hidden" value="${workItem.token}" name="token"></input>
      <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
      <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
      <input type="hidden" value="${workItem.bv.applyId}" name="applyId"></input>
      <input type="hidden" value="${workItem.bv.loanCode}" name="loanCode"></input>
     </form>
    <c:set var="bv" value="${workItem.bv}"/>
    <form id="confirmSignForm"> 
     <input type="hidden" value="${bv.applyId}" name="carContract.applyId"/>
     <input type="hidden" value="${bv.carContract.loanCode}" name="carContract.loanCode"/>
     <input type="hidden" value="${bv.carContract.contractCode}" name="carContract.contractCode"/>
     <input type="hidden" value="${bv.carContract.contractAmount}" name="carContract.contractAmount"/>
     <input type="hidden" value="2" id="dictOperateResult" name="dictOperateResult"/>
    <div class="control-group">      
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
                <td><label class="lab">证件号码：</label>${workItem.bv.customerCertNum}</td>
                <td></td>
            </tr>
           <c:forEach items="${workItem.bv.carLoanCoborrowers }" var="cobos">
				<tr>
					<td><label class="lab">共借人姓名：</label>${cobos.customerName}</td>
					<td><label class="lab">共借人身份证号：</label>${cobos.customerCertNum}</td>
					<td></td>
				</tr>
			</c:forEach>
	</table>
	</div>
	<h2 class=" f14 pl10 "></h2>
	 <div class="box2 mb10"> 
		<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">车牌号码：</label>${workItem.bv.carVehicleInfo.plateNumbers}</td>
				<td><label class="lab">车辆厂牌型号：</label>${workItem.bv.carVehicleInfo.vehiclePlantModel}</td>
				<td></td>
            </tr>
            <tr>
                <td><label class="lab">批借产品：</label>${workItem.bv.carAuditResult.dictProductType}</td>
				<td><label class="lab">批借期限：</label>${workItem.bv.carAuditResult.dictAuditMonths}天</td>
                <td></td>
            </tr>
            <tr>
                <td><label class="lab">总费率：</label><fmt:formatNumber value="${workItem.bv.carAuditResult.grossRate}" pattern="0.00" />%</td>
                <td><label class="lab">停车费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.parkingFee}" pattern="0.00" />元</td>
                <td><label class="lab">设备费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.facilityCharge}" pattern="0.00" />元</td>
            </tr>
            <tr>
                <td><label class="lab">平台及流量费：</label><fmt:formatNumber value="${workItem.bv.carLoanInfo.flowFee}" pattern="0.00" />元</td>
                <td><label class="lab">合同编号：</label>${workItem.bv.carContract.contractCode}</td>
                <td></td>
            </tr>
            <tr>
                <td><label class="lab">合同金额：</label><fmt:formatNumber value="${workItem.bv.carContract.contractAmount}" pattern="0.00" />元</td>
                <td><label class="lab">放款金额：</label></td>
                <td><label class="lab">每月还款额：</label><fmt:formatNumber value="${workItem.bv.carContract.contractMonthRepayAmount}" pattern="0.00" />元</td>
            </tr>
			</table>
	</div>
	<h2 class=" f14 pl10 "></h2>
	<div class="box2 mb10">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			
			<tr>
               <td><label class="lab"><span style="color:red">*</span>账户姓名：</label>
			      <input type="text" name="loanBank.bankAccountName" value="${workItem.bv.carCustomerBankInfo.bankAccountName}" readonly="readonly" class="input-medium required">
			      <input type="hidden" name="loanBank.id" value="${workItem.bv.carCustomerBankInfo.id}"></input>
			    </td>
               <td><label class="lab"><span style="color:red">*</span>开户省市：</label>
				    <select class="select78 mr34 required" name="carCustomerBankInfo.bankProvince" value="${workItem.bv.carCustomerBankInfo.bankProvince}" id="bankProvince">
				        <option value="">请选择</option>
				      <c:forEach items="${bv.provinceList}" var="item">
				         <option value="${item.areaCode}" 
				          <c:if test="${bv.carCustomerBankInfo.bankProvince==item.areaCode}">
		                    selected=true 
		                  </c:if> 
		               >${item.areaName}
				        </c:forEach> 
				    </select>
				     <input type="hidden" id="bankProvinceHidden" value="${workItem.bv.carCustomerBankInfo.bankCity}"/>
				    <select class="select78 required" name="carCustomerBankInfo.bankCity" value="${workItem.bv.carCustomerBankInfo.bankCity}" id="bankCity">
				      <option value="">请选择</option>
				    </select>
				    <input type="hidden" id="bankCityHidden" value="${workItem.bv.carCustomerBankInfo.bankCity}"/>
				</td>
                <td></td>
            </tr>
            
			 <tr>
			    <td><label class="lab"><span style="color:red">*</span>开户支行：</label>
		              <input type="text" id="bankBranch" name="carCustomerBankInfo.applyBankName" value="${workItem.bv.carCustomerBankInfo.applyBankName}" class="input-medium required">
		              <input type="hidden" name="applyBankBranch" id="applyBankBranch"/>
		        </td>
		        <td><label class="lab"><span style="color:red">*</span>银行账号：</label>
		          <input type="text" id="bankCardNo_1" name="carCustomerBankInfo.bankCardNo" value="${workItem.bv.carCustomerBankInfo.bankCardNo}" class="required">
		          <input type="hidden" name="applyBankAccount" id="applyBankAccount"/>
	            </td>
				<td><label class="lab"><span style="color:red">*</span>确认银行账号：</label>
		          <input type="text" id="bankCardNo_2" name="carCustomerBankInfo.bankCardNo1" value="${workItem.bv.carCustomerBankInfo.bankCardNo}" class="input-medium required">
		          <input type="hidden" name="applyBankAccount" id="applyBankAccount"/>
	            </td>
               
            </tr>
			<tr>
			  	<td><label class="lab"><span style="color:red">*</span>开卡行：</label>
			      <select id="bankName" name="carCustomerBankInfo.cardBank" value="${workItem.bv.carCustomerBankInfo.cardBank}" class="select180 required">
                    <option value="">请选择</option>
                     <c:forEach items="${fns:getDictList('jk_open_bank')}" var="curItem">
					  <option value="${curItem.value}"
					    <c:if test="${workItem.bv.carCustomerBankInfo.cardBank==curItem.value}">
					      selected=true 
					    </c:if>
					  >${curItem.label}</option>
				    </c:forEach> 
				  </select>
				  <input type="hidden" name="applyBankName" id="applyBankName"/>
			    </td>
                <td><label class="lab"><span style="color: red">*</span>合同签订日期：</label>
                <input id="contractDueDay" name="carContract.contractDueDay"
						value="<fmt:formatDate value='${workItem.bv.carContract.contractDueDay}' pattern="yyyy-MM-dd"/>"
						type="text" class="Wdate input_text178 required" size="10"
						onClick="WdatePicker({skin:'whyGreen',minDate:'%y-%M-%d'})"
						style="cursor: pointer" class="required"/>
						<span id="messageBox" style="color:red;"></span>
               </td>
			   	<td></td>
			</tr>
            
        </table>
    </div>
     <div class="tcenter mt20" style="text-align:right;"><input type="button" class="btn btn-primary" value="提交" id="confirmSignBtn"></input></div>
    </form>
</body>
</html>