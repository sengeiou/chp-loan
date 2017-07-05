<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店已办 查看页面 (非提前结清)</title>
<script type="text/javascript" src="${context }/js/payback/doStoreInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/jquery-barcode-1.3.3.js"></script>
<script language="javascript">
$(document).ready(function(){
	 $("#bcTarget").barcode($("#src").val(), "code128");
})

</script>
<style type="text/css">
#file {
	border-radius: 8px;
	width:70%;
	height:70%;
	margin：auto;
}
</style>
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
                <td><label class="lab">产品类型：</label>${stores.productLabel }</td>
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
	            <tr>
	                <td><label class="lab">蓝补金额：</label>
	                	<label class="lbl"><fmt:formatNumber value='${stores.paybackBuleAmount }' pattern="0.00"/></label></td>
			     <td>
			      <label class="lab"> 还款方式：</label>
			      <c:if test="${stores.dictRepayMethod == '1' }">
			     	 <label> <input type="radio" name="huan_sort" value="汇款/转账" checked="checked">汇款/转账</label>
			      </c:if>
			      <c:if test="${stores.dictRepayMethod == '0' }">
			     	<label> <input type="radio" name="huan_sort" value="划扣" checked="checked">划扣</label>
			      </c:if>
			      <c:if test="${stores.dictRepayMethod == '2' }">
			     	<label> <input type="radio" name="huan_sort" value="POS机刷卡" checked="checked">POS机刷卡</label>
			     </c:if>
			      <c:if test="${stores.dictRepayMethod == '3' }">
			     	<label> <input type="radio" name="huan_sort" value="POS机刷卡查账" checked="checked">POS机刷卡查账</label>
			     </c:if>
			  </td>
			</tr>
	        </table>
	    </div>
    
    <c:if test="${stores.dictRepayMethod == '1' }">
    <h2 class="f14 pl10 mt20">汇款信息&nbsp;&nbsp;&nbsp;</h2>
		  <div id="qishu_div3" class="box2">
		        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			    <tr >
				   <td ><label class="lab">实际到账总额：</label>
				  	<label class="lbl"><fmt:formatNumber value='${stores.applyReallyAmount }' pattern="0.00"/></label></td>
				   <td ><label class="lbl"/>存入账户：</label>
				   		<label class="lbl">${stores.dictDepositAccount }</label>
				   </td>
				   <td ><label class="lab">还款申请日期：</label>
				   		<label class="lbl"><fmt:formatDate value='${stores.applyPayDay }' type='date'/></label> </td>
				</tr> 
		         </table> 
		         <table  class="table table-hover table-bordered table-condensed" cellpadding="0" cellspacing="0" border="0" width="100%"">  
		         <thead>
		         <tr>
		            <th>存款方式</th>
		            <th>存款时间</th>
		            <th>实际到账金额</th>
		            <th>实际存入人</th>
		            <th>存款凭条</th>
		            <th>上传人</th>
		            <th>上传时间</th>
		         </tr>
		         </thead>
		        <c:forEach items="${payBackTransFif}" var="item" varStatus="num">
					<tr >
					     <td >${item.dictDeposit }</td>
			             <td ><fmt:formatDate value='${item.tranDepositTime }' type='date'/></td>
			             <td ><fmt:formatNumber value='${item.reallyAmount }' pattern="0.00"/></td>
			             <td >${item.depositName }</td>
			             <td >
			             <input type="button" class="btn_edit" name = "doPreviewPngBtn" value="${item.uploadFilename }" docId = ${item.uploadPath }>
			          	 </td>
			             <td >${item.uploadName }</td>
			             <td ><fmt:formatDate value="${item.uploadDate }" type="both"/></td>
					 </tr> 
				</c:forEach>   
			  </table>
		  </div>
 	</c:if>
	 <c:if test="${stores.dictRepayMethod == '0' }"> 
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
	 <c:if test="${stores.dictRepayMethod == '2' }"> 
	<!-- POS刷卡 开始  -->
		<div class="box2 mb10" id="qishu_div5" >
			<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				    <tr >
					  <td ><label class="lab">申请刷卡金额：</label><input type="text"  class="input_text178" readonly="readonly"  value="<fmt:formatNumber value='${stores.applyDeductAmount }' pattern="0.00"/>"></input></td>
					  <td ><label class="lab">POS机平台：</label><input type="text"  class="input_text178" readonly="readonly"  value="${stores.dictPosTypeLabel}"></input></td>
					  <td ><label class="lab">还款申请日期：</label><input type="text" class="input_text178" readonly="readonly"  value="<fmt:formatDate value='${stores.applyPayDay }' type='date'/>" /></td>
			      	</tr>	
			      	<tr>
			      	  <td><label class="lab">POS机订单号：</label><input type="text" class="input_text178" id="src" value="${stores.posBillCode}" name="paybackApply.posBillCode" readonly /></td>
					<td><label class="lab">条形码：</label> <div id="bcTarget" style="margin-left:auto;margin-left:100px;"></div></td>
               	   </tr>
			</table>
		</div>
	 </c:if>
	<!-- POS刷卡 结束 --> 
	
	<!-- POS刷卡查账 开始-->
   <c:if test="${stores.dictRepayMethod == '3' }"> 
      	  <div   id="qishu_div3" >
		        <table class="table  table-bordered table-condensed table-hover ">
			    <tr >
				   <td ><label class="lab">实际到账总额：</label><label class="lab"><fmt:formatNumber value='${stores.applyReallyAmount }' pattern="0.00"/></label></td>
				 	<td><label class="lab">存入账户：</label> 
						<select class="select178" id="dictPosType" name="paybackApply.dictPosType" disabled>
							<option>请选择</option>
							<c:forEach items="${fns:getDictList('jk_account')}" var="item">
								<option value="${item.value }" <c:if test="${stores.posAccount==item.value }">selected</c:if>>${item.label }</option>
							</c:forEach>
						</select>
					</td>
				   <td ><label class="lab">还款申请日期：</label>&nbsp;<input type="text" class="input_text178" readonly="readonly"  value="<fmt:formatDate value='${stores.applyPayDay }' type='date'/>"> </td>
				</tr> 
		         </table> 
				 <p style="padding-left: 600px;font-size: 16px;">POS刷卡查账信息</p>
		         <table class="table  table-bordered table-condensed table-hover ">  
		        <tr >
		         </tr>
		         <thead>
		         <tr>
		            <td>存款方式</td>
					<td>到账日期</td>
					<td>实际到账金额</td>
					<td>参考号</td>
					<td>订单号</td>
					<td>POS小票凭证</td>
					<td>上传人</td>
					<td>上传时间</td>
		         </tr>
		         </thead>
		        <c:forEach items="${payBackTransFif}" var="item" varStatus="num">
					<tr >
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="${item.dictDepositPosCardLabel}"/></td>  <!-- 存款方式 -->
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="<fmt:formatDate value='${item.paybackDate }' type='date'/>"/></td>  <!-- 到账日期 -->
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="<fmt:formatNumber value='${item.applyReallyAmountPosCard}' pattern="0.00"/>"/></td> <!-- 实际到账金额 -->
			             <td ><input type="text" readonly="readonly"  class="select78_24"  value="${item.referCode }"/></td> <!-- 参考号-->   
			             <td ><input type="text" readonly="readonly"  class="select78_24" value="${item.posOrderNumber }"/></td>  <!-- 订单号 -->
			             <td >
			             	<input type="button" class="btn_edit" name = "doPreviewPngBtn" value="${item.uploadFilename }" docId = ${item.uploadPath }></td>
			             </td>             <!--POS小票凭证 -->
			             <td >${item.uploadNamePosCard }</td>                             <!--上传人 -->                                             
			             <td ><fmt:formatDate value="${item.uploadDatePosCard}" type="both"/></td>  <!--上传时间 -->  
					 </tr> 
				</c:forEach>   
			  </table>
		  </div>
			</table>
		</div>
	</c:if>
</div>
</body>
</html>