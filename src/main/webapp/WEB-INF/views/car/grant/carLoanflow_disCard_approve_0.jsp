<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>分配卡号办理</title>
<script type="text/javascript" src='${context}/js/common.js'></script>
<script src="${context}/js/car/grant/carDisCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
</head>
<body>
<div class="wrap bg_gray">
        <h4 class=" f14 pl10">已选客户</h4>
        <table class="table  table-bordered table-condensed table-hover ">
        <thead>
            <tr>
                <th>合同编号</th>
                <th>客户姓名</th>
                <th>合同金额</th>
                <th>开户行</th>
                <th>渠道</th>
            </tr>
          </thead>
          <tbody>
          <form id="param">
          		<input type="hidden" value="${workItem.flowProperties.firstBackSourceStep}" name="firstBackSourceStep" ></input>
	            <c:forEach items="${list}" var="item" varStatus="stat">
	            <tr>
	                <td>${item.bv.contractCode}</td>
	                <td>${item.bv.customerName}</td>
	                <td><fmt:formatNumber value='${item.bv.grantAmount}' pattern="#,#00.00"/></td>
	                <td>${item.bv.cardBank}</td>
	                <td>${item.bv.dictLoanFlag}</td>
	                <input type="hidden" name="list[${stat.index}].applyId" value='${item.bv.applyId}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.flowId" value='${item.bv.applyId}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.wobNum" value='${item.wobNum}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.flowName" value='${item.flowName}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.token" value='${item.token}'></input>
	                <input type="hidden" name="list[${stat.index}].workItemView.stepName" value='${item.stepName}'></input>
	                <input type="hidden" name="list[${stat.index}].contractCode" value='${item.bv.contractCode}'></input>
	                <input type="hidden" name="list[${stat.index}].grantAmount" value='${item.bv.grantAmount}'></input>
	                <input type="hidden" name="list[${stat.index}].loanCode" value='${item.bv.loanCode}'></input>
                    <input type="hidden" name="list[${stat.index}].contractVersion" value='${item.bv.contractVersion}'></input>
	                
	            </tr>
	            </c:forEach>
	            
          </form>  
            </tbody>
        </table>
         <h4 class="  pl10 mt20 f14">输入放款信息</h4>
    <div class="content control-group  ">
     <input type="hidden" id="userCode" ></input>
     <input type="hidden" id="middleId" ></input>
        <table cellspacing="0" cellpadding="0" border="0"  class="table3 mb5" width="100%">
            <tr>
                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;放款账户：<input type="text" class="input_text178" readonly="readonly" id="middleName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="midBankName"/>&nbsp;
				<input type="button" class="btn btn-small" value="选择账户" id="selectMiddleBtn"></input>
				</td>
            </tr> 
            <tr> <td id="001"></td></tr>
        </table>
       </div>
       <div class="tright   pr30 pt10"><button class="btn btn-primary" id="disCommitBtn">提交</button>&nbsp;&nbsp;<input type="button" class="btn btn-primary" id="handleCheckRateInfoCancel" value="返回"
		onclick="JavaScript:history.go(-1);"></input> </div>
            
</div>
</body>
</html>