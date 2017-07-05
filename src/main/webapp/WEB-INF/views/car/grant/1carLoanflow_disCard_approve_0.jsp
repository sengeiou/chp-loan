<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>分配卡号</title>
<script src="${context}/js/car/grant/carDisCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
</head>
<body>
<div class="wrap bg_gray">
        <h2 class=" f14 pl10">已选客户</h2>
        <table class="table  table-bordered table-condensed table-hover ">
        <thead>
            <tr>
                <th>合同编号</th>
                <th>客户姓名</th>
                <th>放款金额</th>
                <th>标识</th>
            </tr>
          </thead>
          <tbody>
          <form id="param">
	            <c:forEach items="${list}" var="item" varStatus="stat">
	            <tr>
	                <td>${item.bv.contractCode}</td>
	                <td>${item.bv.customerName}</td>
	                <td><fmt:formatNumber value='${item.bv.grantAmount}' pattern="#,#00.00"/></td>
	                <td>${item.bv.dictLoanFlag}</td>
	                <input type="hidden" name="list[${stat.index}].applyId" value='${item.bv.applyId}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.wobNum" value='${item.wobNum}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.flowName" value='${item.flowName}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.token" value='${item.token}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.stepName" value='${item.stepName}'></input>
	                <input type="hidden" name="list[${stat.index}].contractCode" value='${item.bv.contractCode}'></input>
	            </tr>
	            </c:forEach>
          </form>  
            </tbody>
        </table>
    <div class="content control-group mt10">
     <input type="hidden" id="userCode" name="bv.lendingUserId"></input>
     <input type="hidden" id="middleId" name="bv.midId" ></input>
     
        <h3 class="pt5 pb5 pl10 ">输入放款信息</h3>
        <table cellspacing="0" cellpadding="0" border="0"  class="table3" width="100%">
            <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;放款账户：<input type="text" class="input_text178" readonly="readonly" id="middleName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="midBankName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="bankCardNo"/>
				<input type="button" class="btn btn-small" value="选择账户" id="selectMiddleBtn"></input>
				</td>
            </tr> 
            <tr> <td id="001"></td></tr>
        </table>
    
            <div class="tright mr10 mb5"><button class="btn btn-primary" id="disCommitBtn">提交</button>&nbsp;&nbsp;<button class="btn btn-primary" id="bankBtn">返回</button></div>
            </div>
</div>
</body>
</html>