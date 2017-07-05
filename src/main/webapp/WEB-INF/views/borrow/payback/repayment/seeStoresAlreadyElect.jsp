<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店已办 查看页面 (非提前结清)</title>
</head>
<body>
<div class="body_r">
    <div class="box2 mb10">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
			    <td><label class="lab">合同编号：</label>${stores.contractCode }</td>
                <td><label class="lab">证件号码：</label>${stores.customerCertNum }</td>
                 <td><label class="lab">客户姓名：</label>${stores.customerName }</td>
            </tr>
            <tr>
                <td><label class="lab">产品类型：</label>${stores.productType }</td>
                <td><label class="lab">合同金额（元）：</label><fmt:formatNumber value='${stores.contractAmount }' pattern="0.00"/></td>
                <td><label class="lab">期供金额（元）：</label><fmt:formatNumber value='${stores.paybackMonthAmount }' pattern="0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">借款期限：</label>${stores.contractMonths }</td>
                <td><label class="lab">借款状态：</label>${stores.dictLoanStatusLabel}</td>
				<td><label class="lab">渠道：</label>${stores.loanMarkLabel}</td>
            </tr>
        </table>
    </div>
   
		<h2 class="f14 pl10">还款信息</h2>
	
	    <div class="box2 mb10">
	        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	            <tr >
	                <td ><label class="lab">蓝补金额:</label>&nbsp;<label class="lab"><fmt:formatNumber value='${stores.paybackBuleAmount }' pattern="0.00"/></label></td>
	               
	                <td  ><label class="lab">本次应还款金额：</label>&nbsp;<label class="lab"><fmt:formatNumber value='${stores.monthPayAmount }' pattern="0.00"/></label></td>
	            </tr>
	           <tr >
			     <td  >
			      <label class="lab"> 还款方式:</label>
			      <c:if test="${stores.dictRepayMethod == '1' }">
			     	 <label> <input type="radio" name="huan_sort" value="汇款/转账" checked="checked">汇款/转账</label>
			      </c:if>
			      <c:if test="${stores.dictRepayMethod == '0' }">
			     	<label> <input type="radio" name="huan_sort" value="划扣" checked="checked">划扣</label>
			     </c:if>
			  </td>
			</tr>
	        </table>
	    </div>
    
    <c:if test="${stores.dictRepayMethod == '1' }">
		  <div   id="qishu_div3" >
		        <table class="table  table-bordered table-condensed table-hover ">
			    <tr >
				   <td ><label class="lab">实际到账总额：</label><label class="lab"><fmt:formatNumber value='${stores.applyReallyAmount }' pattern="0.00"/></label></td>
				   <td ><label class="lab">还款申请日期：</label>&nbsp;<input type="text" class="input_text178" readonly="readonly"  value="<fmt:formatDate value='${stores.applyPayDay }' type='date'/>"> </td>
				 </tr> 
		         </table> 
				 <p style="padding-left: 600px;font-size: 16px;">还款信息</p>
		         <table class="table  table-bordered table-condensed table-hover ">  
		        <tr >
		         </tr>
		         <thead>
		         <tr>
		            <th>存款方式</th>
		            <th>存款时间</th>
		            <th>存入账户</th>
		            <th>实际到账金额</th>
		            <th>实际存入人</th>
		            <th>存款凭条</th>
		            <th>上传人</th>
		            <th>上传时间</th>
		         </tr>
		         </thead>
		        <c:forEach items="${payBackTransFif}" var="item" varStatus="num">
					<tr >
					     <td ><input type="text" readonly="readonly"  class="select78_24" value="${item.dictDeposit }"/></td>
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="<fmt:formatDate value='${item.tranDepositTime }' type='date'/>"/></td>
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="${item.storesInAccountname }"/></td>
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="<fmt:formatNumber value='${item.reallyAmount }' pattern="0.00"/>"/></td>
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="${item.depositName }"/></td>
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="2.jpg??" /></td>
			             <td >${item.uploadName }</td>
			             <td ><fmt:formatDate value="${item.uploadDate }" type="both"/></td>
					 </tr> 
				</c:forEach>   
			  </table>
		  </div>
 	</c:if>
	 <c:if test="${stores.dictRepayMethod != '1' }"> 
		  <div class="box2 mb10" id="qishu_div2">
		        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				    <tr >
					  <td ><label class="lab">划扣金额：</label><input type="text"  class="input_text178" readonly="readonly"  value="<fmt:formatNumber value='${stores.applyDeductAmount }' pattern="0.00"/>"></input></td>
					  <td ><label class="lab">划扣平台：</label><input type="text"  class="input_text178" readonly="readonly"  value="${stores.dictDealTypeLabel}"></input></td>
					  <td ><label class="lab">还款申请日期：</label><input type="text" class="input_text178" readonly="readonly"  value="<fmt:formatDate value='${stores.applyPayDay }' type='date'/>" /></td>
					</tr>	
			        <tr>
			          
			          <td><label class="lab">账号姓名：</label><input type="text" readonly="readonly"  class="input_text178" value="${stores.applyAccountName }" /></td>
			          <td><label class="lab">划扣账号:</label><input type="text" readonly="readonly"  class="input_text178" value="${stores.applyDeductAccount }" /></td>
			          <td><label class="lab">开户行全称：</label><input type="text" readonly="readonly"  class="input_text178" value="${stores.applyBankName }" /></td>
			
			        </tr>    
			 </table>		
		  </div>
	 </c:if>
</div>
</body>
</html>