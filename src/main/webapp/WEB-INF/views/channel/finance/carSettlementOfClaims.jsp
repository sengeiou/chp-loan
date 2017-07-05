<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="default"/>
<title>车借债权结清列表</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src = "${context}/js/channel/finance/carSettlementOfClaims.js">
</script>
</head>
<body>
	<div class="control-group">
   <form:form  method="post" modelAttribute="params" id = "searchForm" action = "${ctx}/channel/financial/carSettlement/init">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">借款ID：</label><form:input type="text" class="input_text178" path="loanCode" id = "loanCode"></form:input></td>
                <td>
                	<label class="lab">提前结清日期：</label>
                	<input type="text" class="Wdate input_text170" id ="settleStartDate" name = "settleStartDate" 
                	value="<fmt:formatDate value='${params.settleStartDate}' pattern='yyyy-MM-dd'/>"  
                	onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'settleEndDate\')}'})" 
                	style="cursor: pointer"/>-<input type="text" class=" Wdate input_text170" id = "settleEndDate" 
                	name = "settleEndDate" value="<fmt:formatDate value='${params.settleEndDate}' pattern='yyyy-MM-dd'/>"  
                	onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'settleStartDate\')}'})" style="cursor: pointer"/>
                </td>
            </tr>
        </table>
         
        <div class="tright pr30 pb5">
        	<input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"/> 
            <button class="btn btn-primary" id="clearBtn">清除</button>
	    </div>
	</form:form>
		</div>
		<p class="mb5">
    	<button class="btn btn-small" id="dao">导出excel</button>
    	<button class="btn btn-small" id = "confirm">确认</button>
    	&nbsp;&nbsp;<label class="lab1"><span class="red">债权合计金额：</span></label><label class="red" id = "deductAmount">
    	<fmt:formatNumber value='${contractAmount}' pattern="#,##0.00"/></label><label class="red">元</label>
    	<input type="hidden"  id="hiddenAmount" value="0.00"/>
    	<input type="hidden" id="deduct" value="${contractAmount}">
		&nbsp;&nbsp;<label class="lab1"><span class="red">总笔数：</span></label>
		<label class="red" id = "totalNum">${settlementCount }</label><label class="red">&nbsp;笔</label>
		<input type="hidden" id="num" value="${settlementCount }">
		<input type="hidden" id="hiddenNum" value="0"/>
		</p>
		<div class="box5">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id = "checkAll"/></th>
            <th>借款ID</th>
            <th>债务人</th>
			<th>原始借款开始日期</th>
			<th>债权月利率（%）</th>
			<th>债权转入金额（元）</th>
			<th>提前结清日期</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ settlementList!=null && fn:length(settlementList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${settlementList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" cid = "${item.id }" creditorAmount='${item.contractAmount}'/>
             </td>
             <td>${item.loanCode}</td>
             <td>${item.loanCustomerName}</td>
             <td><fmt:formatDate value="${item.loanStartDate}"
							type="date" pattern='yyyy-MM-dd'/></td>
             <td><fmt:formatNumber value='${item.feeMonthRate}' pattern="#,#0.00"/></td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatDate value='${item.settleDate}' pattern='yyyy-MM-dd'/></td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ settlementList==null || fn:length(settlementList.list)==0}">
           <tr><td colspan="7" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
      
    </table>
	</div>
	</div>
<div class="pagination">${settlementList}</div> 
<script type="text/javascript">
	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-290,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>