<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head><!--  -->
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/contract/contractAudit.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
  REDIRECT_URL="/apply/contractAudit/fetchTaskItems";
  flowFlag = "CTR_CREATE";
  $(document).ready(function(){
	$('#retCtrCreateListBtn').bind('click',function(){
		 window.location=ctx+"/apply/contractAudit/fetchTaskItems4ReadOnly?flowFlag=CTR_CREATE";
	 }); 
	//历史绑定
	$(":input[name='history']").each(function() {
		$(this).bind('click', function() {
			viewAuditHistory($(this).attr('loanCode'));
		});
	});
	$('#submitBtn').bind('click',function(){
		$('#hidDictRepayMethod').val($('#selDictRepayMethod option:selected').val());
		dispatchFlow(flowFlag,'TO_CONTRACT_SIGN',REDIRECT_URL,'contractCreate');
			 $('#submitBtn').attr('disabled',true);
			 $('#backBtn').attr('disabled',true);
	});
    $('#backBtn').bind('click',function(){
    	backFlow('_grantBackDialog',flowFlag,REDIRECT_URL,'loanapplyForm');  //传递退回窗口的视图名称
    });
  });
</script>
<style> .input_check+label {display: inline-block;width: 13px;height: 13px;border: 1px solid #fa0505;}
         .input_check:checked+label:after {content: "";position: absolute;left: 3px;width: 9px;height: 6px;
      border: 2px solid #fa0505;border-top-color: transparent;border-right-color: transparent;-ms-transform: rotate(-60deg); 
-moz-transform: rotate(-60deg); 
-webkit-transform: rotate(-60deg); 
transform: rotate(-45deg);}
      .input_check {position: absolute;visibility: hidden;}
      
</style>
<title>合同制作</title>
</head>
<body>
 
   
         <div style="text-align:right;">
            <button class="btn btn-small" name="history" loanCode='${workItem.bv.contract.loanCode}'>历史</button>
            <button class="btn btn-small" id="retCtrCreateListBtn">返回</button>
        </div>
   
    <c:set var="bv" value="${workItem.bv}"/>
    
      <form id="loanapplyForm">
          <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
          <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
          <input type="hidden"  id="stepName" value="${workItem.stepName}" name="stepName"></input>
          <input type="hidden" value="${workItem.token}" name="token"></input>
          <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
          <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
           <input type="hidden" id="applyId" value="${bv.applyId}" name="applyId"></input>
          <input type="hidden" value="${bv.contract.applyId}" name="contract.applyId"></input>
          <input type="hidden" value="${bv.contract.loanCode}" name="contract.loanCode"></input>
          <input type="hidden" value="${bv.contract.contractCode}" name="contract.contractCode"></input>
          <input type="hidden" id="hidDictRepayMethod" value="${bv.contract.dictRepayMethod}" name="contract.dictRepayMethod"></input>
          <input type="hidden" value="<fmt:formatDate value="${bv.contract.contractReplayDay}" pattern="yyyy-MM-dd"/>" name="contract.contractReplayDay"/>
       </form>
      <form id="contractCreateForm" method="post">
       <input type="hidden" value="2" name="dictOperateResult"></input>
       <div class="box2 mb10">
      <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
              <td ><label class="lab">门店名称：</label>${bv.applyOrgName}</td>
			  <td ><label class="lab">借款合同编号：</label>${bv.contract.contractCode}</td>
            </tr>
            </table>
            </div>
            <div class="box2 mb10"> 
           <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">  
            <tr>
                <td ><label class="lab">借款人姓名：</label>${bv.mainLoaner}</td>
                <td><label class="lab">证件类型：</label>${bv.mainCertTypeName}</td>
                <td><label class="lab">证件号码：</label>${bv.mainCertNum}<td>
            </tr>
            <c:forEach items="${bv.coborrowers}" var="currItem">
			<tr>
			    <td><label class="lab">共借人姓名：</label>${currItem.coboName}</td>
			    <td><label class="lab">证件类型：</label>${currItem.dictCertTypeName}</td>
                <td><label class="lab">证件号码：</label>${currItem.coboCertNum}<td>
			</tr>
			</c:forEach>
			<c:if test="${bv.auditEnsureName!=null && bv.auditEnsureName!=''}">
			<tr>
			    <td><label class="lab">保证人：</label>${bv.auditEnsureName}</td>
				<td><label class="lab">法定代表人：</label>${bv.auditLegalMan}</td>
                <td ><label class="lab">经营场所：</label>${bv.ensuremanBusinessPlace}</td>
   			</tr>
			</c:if>
           </table>
           </div>
           <div class="box2 mb10"> 
           <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">起始还款日期：</label><fmt:formatDate value="${bv.contract.contractReplayDay}" pattern="yyyy-MM-dd"/></td>
                <td><label class="lab">批复产品：</label>${bv.productName}</td>
               <td><label class="lab">中间人：</label>
                  ${bv.contract.midName}
                 <input type="hidden" value="${bv.contract.midId}" name="contract.midId"/>
                 <%-- <select name="contract.midId" class="select180">
                 <c:forEach items="${bv.middlePersons}" var="middlePerson">
                     <option value="${middlePerson.id}"
                       <c:if test="${middlePerson.id==bv.contract.midId}">
                           selected=true 
                       </c:if>
                     >${middlePerson.middleName}</option>
                 </c:forEach> --%>
               </td>
            </tr>
            <tr>
                <td><label class="lab">还款付息方式：</label>
				 <select class="select180" id="selDictRepayMethod" name="contract.dictRepayMethod" disabled="disabled">
				   <c:forEach items="${fns:getDictList('jk_repay_interest_way')}" var="item">
				     <option value="${item.value}" 
				       <c:if test="${item.value=='等额本息'}">
				          selected = true  
				       </c:if>
				     >${item.label}</option>
				   </c:forEach>
				  </select>
				</td>
                <td><label class="lab">批复期数：</label>${bv.contract.contractMonths}</td>
                <td><label class="lab">产品总费率：</label><fmt:formatNumber value='${bv.ctrFee.feeAllRaio}' pattern="#,##0.000"/>%</td>
            </tr>
            <tr>
                <td><label class="lab">借款利率：</label><fmt:formatNumber value='${bv.ctrFee.feeMonthRate}' pattern="#,##0.00#"/>%</td>
				<td>
				   <label class="lab">模式：</label>${bv.modelName}
				</td>
                <td>
     			   <label class="lab">渠道：</label>${bv.loanFlag}
				</td>
		    </tr>
            <tr>
                <td><label class="lab">批借金额：</label><fmt:formatNumber value='${bv.contract.auditAmount}' pattern="####0.00"/></td>
                <td><label class="lab">外访费：</label><fmt:formatNumber value='${bv.ctrFee.feePetition}' pattern="####0.00"/></td>
                <td></td>
	        </tr>
            </table>
            </div>
            <div class="box2 mb10"> 
           <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
               <td><label class="lab">实放金额：</label><fmt:formatNumber value='${bv.ctrFee.feePaymentAmount}' pattern="####0.00"/></td>
               <td><label class="lab">前期咨询费：</label><fmt:formatNumber value='${bv.ctrFee.feeConsult}' pattern="####0.00"/></td>
               <td><label class="lab">分期咨询费：</label><fmt:formatNumber value='${bv.ctrFee.monthFeeConsult}' pattern="####0.00"/></td>
            </tr>
            <tr>
			   <td><label class="lab">合同金额：</label><fmt:formatNumber value='${bv.contract.contractAmount}' pattern="####0.00"/></td>
               <td><label class="lab">前期审核费：</label><fmt:formatNumber value='${bv.ctrFee.feeAuditAmount}' pattern="####0.00"/></td>
               <td><label class="lab">分期居间服务费：</label><fmt:formatNumber value='${bv.ctrFee.monthMidFeeService}' pattern="####0.00"/>
               </td>    
            </tr>
            <tr>
			    <td><label class="lab">应还本息：</label><fmt:formatNumber value='${bv.contract.contractMonthRepayAmount}' pattern="####0.00"/></td>
                <td><label class="lab">前期居间服务费：</label><fmt:formatNumber value='${bv.ctrFee.feeService}' pattern="####0.00"/></td>
				<td><label class="lab">分期服务费：</label><fmt:formatNumber value='${bv.ctrFee.monthFeeService}' pattern="####0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">分期服务费：</label><fmt:formatNumber value='${bv.ctrFee.monthFeeService}' pattern="####0.00"/></td>
		        <td><label class="lab">前期信息服务费：</label><fmt:formatNumber value='${bv.ctrFee.feeInfoService}' pattern="####0.00"/></td>
				<td><label class="lab">催收服务费：</label><span id="feeUrgedService"><fmt:formatNumber value='${bv.ctrFee.feeUrgedService}' pattern="####0.00"/></span></td>
            </tr>
            <tr>
                <td><label class="lab">月还款额：</label><fmt:formatNumber value='${bv.contract.monthPayTotalAmount}' pattern="####0.00"/>
                </td>
                <td><label class="lab">前期综合服务费：</label><fmt:formatNumber value='${bv.ctrFee.feeCount}' pattern="####0.00"/></td>
                <td><label class="lab">加急费：</label><fmt:formatNumber value='${bv.ctrFee.feeExpedited}' pattern="####0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">预计还款总额：</label><fmt:formatNumber value='${bv.contract.contractExpectAmount}' pattern="####0.00"/></td>
                <td><label class="lab">账户姓名：</label>
                 ${bv.loanBank.bankAccountName}
                <c:if test="${bv.loanBank.bankIsRareword==1}">
				  <span style="position: relative;color:#fa0505"><input type="checkbox"  checked="checked"  disabled="disabled" class="input_check" id="check3"/><label for="check3"></label>生僻字</span>
	    	     </c:if>
                </td>
                <td><label class="lab">签约平台：</label>
                   ${bv.loanBank.bankSigningPlatformName}
                </td>
            </tr>
			<tr>
			    <td><label class="lab">合同签订日期：</label><fmt:formatDate value="${bv.contract.contractDueDay}" pattern="yyyy-MM-dd"/></td>
				<td>
				<label class="lab">银行账号：</label><label class="enlarge">${bv.loanBank.bankAccount}</label>
				</td>
				<td></td>
            </tr>
            <tr>
                <td>
                  <label class="lab">开户行名称：</label>
            		<label class="enlarge">${bv.loanBank.bankNameLabel}
            	  </label>
                </td>
                <td>
                <label class="lab">支行名称：</label>
                   ${bv.loanBank.bankBranch}
                </td>
            </tr>
        </table>
        </div>
     </form>
</div>
</body>
</html>