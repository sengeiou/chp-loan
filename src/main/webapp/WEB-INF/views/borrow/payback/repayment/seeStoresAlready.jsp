<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>门店已办 查看页面  (提前结清)</title>
</head>
<body>
<div class="body_r">
    <div class="box2 mb10">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>${stores.customerName }</td>
                <td><label class="lab">证件号码：</label>${stores.customerCertNum }</td>
                <td><label class="lab">合同编号：</label>${stores.contractCode }</td>
            </tr>
            <tr>
                <td><label class="lab">产品类型：</label>${stores.productType }</td>
                <td><label class="lab">合同金额（元）：</label><fmt:formatNumber value='${stores.contractAmount }' pattern="0.00"/></td>
                <td><label class="lab">期供金额（元）：</label><fmt:formatNumber value='${stores.paybackMonthAmount }' pattern="0.00"/></td>
            </tr>
            <tr>
                <td><label class="lab">借款期限：</label>${stores.contractMonths }</td>
                <td><label class="lab">借款状态：</label>${stores.dictLoanStatusLabel}</td>
            </tr>
        </table>	
    </div>
       <h2 class="f14 pl10 mt20">还款信息</h2>
    </div>
    <div class="box2  ">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab" style="width:200px">未还违约金(滞纳金)及罚息总金额：</label><fmt:formatNumber value='${stores.notPunishViolate }' pattern="0.00"/></td>
                <td><label class="lab" style="width:150px">提前结清金额：</label><fmt:formatNumber value='${stores.monthBeforeFinishAmount }' pattern="0.00"/></td>
				 <td><label class="lab" style="width:150px">提前结清应还总金额：</label><fmt:formatNumber value='${stores.interestforeFinishAmount }' pattern="0.00"/></td>
            </tr>
            <tr>
               <td><label class="lab" style="width:200px">蓝补金额：</label><fmt:formatNumber value='${stores.paybackBuleAmount }' pattern="0.00"/></td>
                <td><label class="lab" style="width:150px">提前结清申请资料：</label><input type="text" value="${stores.uploadFilename }" readOnly="true"></td>
                <td><label  class="lab" style="width:150px">减免金额：</label><fmt:formatNumber value='${stores.reductionAmount }' pattern="0.00"/></td>
            </tr>
        </table>
    </div>
</body>
</html>