<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script src="${context}/js/car/grant/carUrgeGuarante.js" type="text/javascript"></script>
<script language="javascript"> 
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#backForm").attr("action", "${ctx}/car/grant/deductCost/deductCostStayRecoverList");
		$("#backForm").submit();
		return false;
	}
</script>
<meta name="decorator" content="default"/>
</head>
<body>

	<div class="control-group">
   <form:form action="${ctx }/car/grant/deductCost/deductCostStayRecoverList" method="post" id="backForm" modelAttribute="UrgeServicesMoneyEx">
       		 <input id="pageNo" name="pageNo" type="hidden" value="${urgePage.pageNo}" /> 
       		 <input name="menuId" type="hidden" value="${param.menuId}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${urgePage.pageSize}" />	
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input type="text" class="input_text178" path="customerName"></form:input></td>
                <td><label class="lab">合同编号：</label><form:input type="text" class="input_text178" path="contractCode"></form:input></td>
                <td><label class="lab">证件号码：</label><form:input type="text" class="input_text178" path="customerCertNum"></form:input>
				</td>
            </tr>
            <tr>
				<td><label class="lab">放款日期：</label><input  name="startDate"  id="startDay"  
                 value="<fmt:formatDate value='${UrgeServicesMoneyEx.startDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'endDay\')}'})" style="cursor: pointer" ></input>-<input  name="endDate" 
                 value="<fmt:formatDate value='${UrgeServicesMoneyEx.endDate}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="endDay"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'startDay\')}'})" style="cursor: pointer" ></input></td>
            </tr>
        </table>
        <div class="tright pr30 pb5"><input type="submit" class="btn btn-primary" value="搜索"></input>
        <button class="btn btn-primary" id="clearBtn">清除</button></div>
         </form:form>
		</div>
		
		<p class="mb5">
    	&nbsp;&nbsp;<label class="lab1"><span class="red">未划扣累计金额：</span></label>
    	<label class="red" id="deductAmount"><fmt:formatNumber value='${deductsAmount}' pattern="#,#0.00"/></label>元</p>
    	<input type="hidden"  id="hiddenDeduct" value="0.00"/>
    	<input type="hidden" id="deduct" value="${deductsAmount}">
    	<div class="box5">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>证件号码</th>
            <th>借款产品</th>
            <th>合同金额</th>
            <th>划扣费用</th>
			<th>实际划扣金额</th>
			<th>未划扣金额</th>
			<th>放款时间</th>
			<th>划扣状态</th>
			<th>划扣平台</th>
			<th>批借期限(月)</th>
            <th>开户行</th>
			<th>是否电销</th>
            <th>渠道</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgePage.list !=null && fn:length(urgePage.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgePage.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" val=${item.urgeId } deductAmount='${item.waitUrgeMoeny}'/></td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td>${item.customerCertNum}</td>
             <td>${item.productName}</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.urgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.splitAmount}' pattern="#,#00.00"/></td> <!-- 已划金额 -->
             <td><fmt:formatNumber value='${item.waitUrgeMoeny}' pattern="#,#00.00"/></td>
             <td><fmt:formatDate value="${item.lendingTime }" type="date" pattern="yyyy-MM-dd" /></td>
             <td>${item.splitBackResult} </td>
             <td>${item.dictDealType} </td>
             <td>${item.contractMonths}</td>
             <td>${item.bankName}</td>
             <td>${item.customerTelesalesFlag} </td>
             <td>${item.loanFlag} </td>
             <td><button class="btn_edit" onclick="urgeGuarantDeal('${item.urgeId }');" id="dealBtnsss" sid="${item.urgeId }">办理</button></td>
         </tr> 
         
         </c:forEach>  
            
           
         </c:if>
         
         <c:if test="${ urgePage.list==null || fn:length(urgePage.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
        
    </table>
	</div>
       

</div>
<div class="pagination">
	${urgePage }
</div>
<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>