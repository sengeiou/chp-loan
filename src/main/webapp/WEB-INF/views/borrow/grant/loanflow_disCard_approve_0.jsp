<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>分配卡号</title>
<script src="${context}/js/grant/disCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default"/>
</head>
<body>
        <h4 class=" pl10  f14">已选客户</h4>
        <table class="table  table-bordered table-condensed table-hover ">
        <input type="hidden" id="flag" value='${flag}'>
        <thead>
            <tr>
                <th>合同编号</th>
                <th>客户姓名</th>
                <th>放款金额</th>
                <th>渠道</th>
            </tr>
          </thead>
          <tbody>
          <form id="param">
	            <c:forEach items="${list}" var="item" varStatus="stat">
	            <tr>
	                <td>${item.contractCode}</td>
	                <td>${item.customerName}</td>
	                <td><fmt:formatNumber value='${item.lendingMoney}' pattern="#,#00.00"/></td>
	                <td>${item.channelName}</td>
	                <input type="hidden" name="list[${stat.index}].applyId" value='${item.applyId}'></input>
	                <input type="hidden" name="list[${stat.index}].contractCode" value='${item.contractCode}'></input>
	                <input type="hidden" name="list[${stat.index}].loanCode" value='${item.loanCode}'></input>
	                <input type="hidden" name="list[${stat.index}].loanInfoOldOrNewFlag" value="${item.loanInfoOldOrNewFlag}"/>
	            </tr>
	            </c:forEach>
          </form>  
            </tbody>
        </table>
        <h4 class="f14 pl10 mt20">输入放款信息</h4>
    <div class="control-group pb10">
     <input type="hidden" id="userCode" ></input>
     <input type="hidden" id="middleId" ></input>
     <input type="hidden" id="deftokenId" value='${deftokenId}'></input>
     <input type="hidden" id="deftoken" value='${deftoken}'></input>
        <table cellspacing="0" cellpadding="0" border="0"  class="table3" width="100%">
            <tr>
                <td>&nbsp;&nbsp;&nbsp;放款账户：<input type="text" class="input_text178" readonly="readonly" id="middleName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="midBankName"/>&nbsp;
                <input type="text" class="input_text178" readonly="readonly" id="bankCardNo"/>
				<input type="button" class="btn btn-small" style="margin-bottom:4px" value="选择账户" id="selectMiddleBtn"></input>
				</td>
            </tr> 
            <tr> <td id="001"></td></tr>
        </table>
      </div>
            <div class="tright mt10 mr34 ">
            <button class="btn btn-primary" id="disCommitBtn">提交</button>
             <button class="btn btn-primary" id="bankBtn">返回</button>
     </div>
</body>
</html>