<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context }/js/payback/paybackInfo.js"></script>
<script type="text/javascript" src="${context }/js/payback/dateUtils.js"></script>
<style type="text/css">
.pagination {background:none;position:static;width:auto}
</style>
</head>
<body>
<div class="body_r">
	  <div class="box2 mb5">
		<input type="hidden" id ="dictRepayMethod"  value="${paybackAudit.dictRepayMethod}" />
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>&nbsp;${paybackAudit.customerName}</td>
                <td><label class="lab">证件号码：</label>&nbsp;${paybackAudit.customerCertNum}</td>
                <td><label class="lab">合同编号：</label>&nbsp;${paybackAudit.contractCode}</td>
            </tr>
            <tr>
                <td><label class="lab">产品类型：</label>&nbsp;${paybackAudit.productType}</td>
                <td><label class="lab">合同金额（元）：</label>&nbsp;<fmt:formatNumber value='${paybackAudit.contractAmount}' pattern="0.00"/></td>
                <td><label class="lab">期供金额（元）：</label>&nbsp;<fmt:formatNumber value='${paybackAudit.contractMonthRepayAmount}' pattern="0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">借款期限：</label>&nbsp;${paybackAudit.contractMonths}</td>
                <td><label class="lab">借款状态：</label>&nbsp;${paybackAudit.dictLoanStatusLabel}</td>
                <td><label class="lab">渠道：</label>&nbsp;${paybackAudit.loanMarkLabel}</td>
            </tr>
        </table>
    </div>

    
    <!-- 汇款  -->
    <div id="qishu_div1" class="box2" style="display: none;" >
   		<h2 class="f14 pl10">还款信息</h2>
   		 <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	   		 <tr>
	                <td><label class="lab">蓝补金额：&nbsp;</label><fmt:formatNumber value='${paybackAudit.paybackBuleAmount}' pattern="0.00"/></td>
	                <td><label class="lab">还款方式：</label>&nbsp;${paybackAudit.dictRepayMethodLabel}</td>
            </tr>
   		 </table>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
           <!-- 汇款  -->
            <tr>
                <td><label class="lab">实际到账总额：</label>&nbsp;<fmt:formatNumber value='${paybackAudit.sunReallyAmount}' pattern="0.00"/></td>
                <td><label class="lab">存入账户：</label>&nbsp;${paybackAudit.storesInAccountname}</td>
                <td><label class="lab">还款申请日期：</label>&nbsp;<fmt:formatDate value='${paybackAudit.applyPayDay}' type='date'/></td>
            </tr>
            <!-- 汇款 列表  开始--> 
  
       <h2 class="f14 pl10">汇款账号</h2>
   
         <table  class="table  table-bordered table-condensed table-hover " >
         <thead>
        <tr>
            <th>存款方式</th>
            <th>存款时间</th>
            <th>实际到账金额</th>
            <th>实际存款人</th>
            <th>存款凭条</th>
            <th>上传人</th>
            <th>上传时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${remittanceList}" var="item" varStatus="num">
	        <tr>
	        	<td>${item.dictDeposit}</td>
	            <td><fmt:formatDate value='${item.tranDepositTime}' type='both'/></td>
	            <td><fmt:formatNumber value='${item.reallyAmount}' pattern="0.00"/></td>
	            <td>${item.depositName}</td>
	            <td><input type="button" class="btn_edit" name = "previewPngBtn" value="${item.uploadFileName }" docId = ${item.uploadPath }></td>
	            <td>${item.uploadName}</td>
	            <td><fmt:formatDate value='${item.uploadDate}' type='both'/></td>
	            <td><input type="button" class="btn_edit" id ="downPng" name="downPng" docId = ${item.uploadPath } fileName="${item.uploadFileName }" value="下载凭证" /></td>
	        </tr>
        </c:forEach>
    </table>
    <!--汇款列表结束  -->
      </div>
      
           <!--划扣  -->
		<div id="qishu_div0"  class="box2 mb10"  style="display: none;">
		<h2 class="f14 pl10 mt20">划扣信息&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					 <tr>
		                <td><label class="lab">蓝补金额：&nbsp;</label><fmt:formatNumber value='${paybackAudit.paybackBuleAmount}' pattern="0.00"/></td>
		                <td><label class="lab">还款方式：</label>&nbsp;${paybackAudit.dictRepayMethodLabel}</td>
		            </tr>
	           <tr>
	                <td><label class="lab">划扣金额：</label>&nbsp;<fmt:formatNumber value='${paybackAudit.sunReallyAmount}' pattern="0.00"/></td>
	                <td><label class="lab">划扣平台：</label>&nbsp;${paybackAudit.dictDealTypeLable}</td>
	                <td><label class="lab">还款申请日期：</label>&nbsp;<fmt:formatDate value='${paybackAudit.applyPayDay}' type='date'/></td>
	            </tr>
	            
	            <tr>
					<td><label class="lab"></span>账号姓名：</label>&nbsp;${paybackAudit.applyAccountName}</td>
					<td><label class="lab"></span>划扣账号：</label>&nbsp;${paybackAudit.applyDeductAccount}</td>
					<td><label class="lab"></span>开户行全称：</label>&nbsp;${paybackAudit.applyBankName}</td>
				</tr>
			</table>
		</div>
	        <!--POS刷卡  -->
		<div id="qishu_div2"  class="box2 mb10" style="display: none;">
		<h2 class="f14 pl10 mt20">划扣信息&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			 <tr>
                <td><label class="lab">蓝补金额：&nbsp;</label><fmt:formatNumber value='${paybackAudit.paybackBuleAmount}' pattern="0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">还款方式：</label>&nbsp;${paybackAudit.dictRepayMethodLabel}</td>
            </tr>
	        <!--POS刷卡  -->
            <tr>
                <td><label class="lab">刷卡金额：</label>&nbsp;<fmt:formatNumber value='${paybackAudit.sunReallyAmount}' pattern="0.00"/></td>
                <td><label class="lab">POS机平台：</label>&nbsp;${paybackAudit.storesInAccountname}</td>
                <td><label class="lab">还款申请日期：</label>&nbsp;<fmt:formatDate value='${paybackAudit.applyPayDay}' type='date'/></td>
            </tr>
			</table>
		</div>
	
  	          <!--POS刷卡查账  -->
		<div id="qishu_div3"  class="box2 mb10"  style="display: none;">
		<h2 class="f14 pl10 mt20">划扣信息&nbsp;&nbsp;&nbsp;</h2>
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
			 <tr>
                <td><label class="lab">蓝补金额：&nbsp;</label><fmt:formatNumber value='${paybackAudit.paybackBuleAmount}' pattern="0.00"/></td>
                <td><label class="lab">还款方式：</label>&nbsp;${paybackAudit.dictRepayMethodLabel}</td>
            </tr>
           <!--POS刷卡查账  -->
            <tr>
                <td><label class="lab">实际到账总额：</label>&nbsp;<fmt:formatNumber value='${paybackAudit.sunReallyAmount}' pattern="0.00"/></td>
                <td><label class="lab">存入账户：</label>&nbsp;${paybackAudit.posAccountLable}</td>
                <td><label class="lab">还款申请日期：</label>&nbsp;<fmt:formatDate value='${paybackAudit.applyPayDay}' type='date'/></td>
            </tr>
			</table>
         <h2 class="f14 pl10">POS还款查账列表</h2>
         <table  class="table  table-bordered table-condensed table-hover " >
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
        <c:forEach items="${remittanceListPos}" var="item" varStatus="num">
	        <tr>
	        	<td>${item.posAccountLable}</td>
	            <td><fmt:formatDate value='${item.paybackDate}' type='both'/></td>
	            <td><fmt:formatNumber value='${item.applyReallyAmountPosCard}' pattern="0.00"/></td>
	            <td>${item.referCode}</td>
	            <td>${item.posOrderNumber}</td>
	            <td><input type="button" class="btn_edit" name = "previewPngBtn" value="${item.uploadNamePosCard }" docId = ${item.uploadPath }></td>
	            <td>${item.uploadName}</td>
	            <td><fmt:formatDate value='${item.uploadDatePosCard}' type='both'/></td>
	            <td><input type="button" class="btn_edit" id ="downPng" name="downPng" docId = ${item.uploadPath } fileName="${item.uploadFileName }" value="下载凭证" /></td>
	        </tr>
        </c:forEach>
    </table>
    	</div>
    <!--POS还款查账列表结束  -->
</body>
</html>