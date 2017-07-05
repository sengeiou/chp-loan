<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/launch/addContact.js"></script>
<script type="text/javascript" src="${context}/js/launch/creditFormat.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#creditNextBtn').bind('click',function(){
		 credit.format();
		 credit.required();
		 $(this).attr('disabled',true);
		 launch.saveApplyInfo('5','creditForm','creditNextBtn');
	 });
	$('#creditAddBtn').bind('click',function(){
		var tabLength=$('#loanCreditArea tr').length;
		launch.additionItem('loanCreditArea','_loanFlowCreditInfoItem',tabLength,'0',null);
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
      
	  <li class="active"><a href="javascript:launch.getCustomerInfo('5');">信用资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('6');">职业信息/公司资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('7');">房产资料</a></li>
      <li><a href="javascript:launch.getCustomerInfo('8');">联系人资料</a></li>
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
 <div id="div5" class="content control-group">
	    <div class="box1 mb10">
	    <input type="button" class="btn btn-small" id="creditAddBtn" value="增加"></input>
      </div>
      <form id="creditForm" method="post">
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
        <table  id="loanCreditArea" class="table  table-bordered table-condensed  ">
            <thead>
            <tr>
                <th><span class="red">*</span>抵押类型</th>
                <th>抵押物品</th>
                <th>机构名称</th>
                <th>贷款额度(元)</th>
                <th>每月供额度(元)</th>
                <th>贷款余额(元)</th>
                <th>信用卡总数</th>
                <th>操作</th>
            </tr>
            </thead>
            <c:forEach items="${applyInfoEx.loanCreditInfoList}" var="curLoanCredit" varStatus="creditStatus">
             <tr>
                <td>
                <span>
                <c:forEach items="${fns:getDictList('jk_pledge_flag')}" var="curItem">
                <input type="radio" class="required" name="loanCreditInfoList[${creditStatus.index}].dictMortgageType"
                     value="${curItem.value}"
                     <c:if test="${curItem.value==curLoanCredit.dictMortgageType}">
                        checked=true
                      </c:if> 
                     />${curItem.label}
                </c:forEach>
                </span>
                 <input type="hidden" name="loanCreditInfoList.id" class="creditId" value="${curLoanCredit.id}"/>
                </td>
                <td><input name="loanCreditInfoList.creditMortgageGoods" value="${curLoanCredit.creditMortgageGoods}" type="text" class="input_text178 required"/></td>
                <td><input name="loanCreditInfoList.orgCode" value="${curLoanCredit.orgCode}" type="text" class="input_text178"/></td>
                <td><input name="loanCreditInfoList.creditLoanLimit" value="<fmt:formatNumber value='${curLoanCredit.creditLoanLimit}' pattern="##0.00"/>" maxlength="13" type="text"class="input_text178 number numCheck positiveNumCheck"/></td>
                <td><input name="loanCreditInfoList.creditMonthsAmount" value="<fmt:formatNumber value='${curLoanCredit.creditMonthsAmount}' pattern="##0.00"/>" maxlength="13" type="text"class="input_text178 number numCheck positiveNumCheck monthsAmountCheck"/></td>
                <td><input name="loanCreditInfoList.creditLoanBlance" value="<fmt:formatNumber value='${curLoanCredit.creditLoanBlance}' pattern="##0.00"/>" maxlength="13" type="text" class="input_text178 number numCheck positiveNumCheck balanceAmountCheck"/></td>
                <td><input name="loanCreditInfoList.creditCardNum" value="<fmt:formatNumber value='${curLoanCredit.creditCardNum}' pattern="##0"/>"  type="text" class="input_text178 digits positiveNumCheck totalCardNumCheck"/></td>
               <td class="tcenter"><input type="button" onclick="contact.delRow(this,'loanCreditArea','CREDIT');" class="btn_edit" value="删除"></input></td>
             </tr>
            </c:forEach>
        </table>
         <!-- <input type="button" class="btn btn-small" value="序列化" onclick="credit.format();"/> -->
        <div  class="tright pr30 mb5"><input type="submit" id="creditNextBtn" class="btn btn-primary" value="下一步"/></div>
        </form>
    </div>
  </body>
</html>