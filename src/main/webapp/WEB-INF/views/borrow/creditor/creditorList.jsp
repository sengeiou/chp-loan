<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>债权录入</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/creditor/creditor.js" type="text/javascript"></script>
<script>
var ctx = '${ctx}';
	$(document).ready(function() {
		var msg = '${message}';
		if (msg != "" && msg != null) {
			art.dialog.alert(msg);
		}
	});
</script>
<meta name="decorator" content="default"/>
</head>
<body>


    
	<div class="control-group">
   <form:form  method="post" modelAttribute="UrgeServicesMoneyEx" id="creditorForm" name="creditorForm">
   <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }">
   <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab">借款编号：</label><input type="text" class="input_text178" id="loanCode" name="loanCode" maxlength="16" value="${search.loanCode }"></input></td>
                <td><label class="lab">借款人姓名：</label><input type="text" class="input_text178" id="loanName" name="loanName" maxlength="10" value="${search.loanName }"></input></td>
                <td><label class="lab">证件号码：</label><input type="text" class="input_text78" id="cerNum" name="cerNum" maxlength="20" value="${search.cerNum }"></input></td>
            </tr>
            <tr>
				<td><label class="lab">产品类型：</label><select class="select180" id="type" name="type" >
            		<option value="">请选择</option>
            		<c:forEach items="${typeList}" var="item">
            		<c:if test="${item.value ne 'A007' and item.value ne 'A008' }">
						<option value="${item.value}" <c:if test="${item.value eq search.type}">selected</c:if>>${item.label}</option>
					</c:if>
					</c:forEach>
            	</select></td>
				<td></td>
                <td></td>
            </tr>
		
        </table>
         
        <div class="tright pr30 pb5"><input type="button" class="btn btn-primary" onclick="document.forms[0].submit();" value="搜索"> 
               <input type="button" class="btn btn-primary" onclick="clear1();" value="清除">
      
        <div style="float: left; margin-left: 45%; padding-top: 10px">
	       <!-- <img src="../../../static/images/up.png" id="showMore"></img> -->
	    </div>
	    </div>
	    </form:form>
		</div>
		<p class="mb5">
    	&nbsp;&nbsp;
    	<button class="btn btn-small" onclick="addPage();">录入</button>
		</p>
		<div class="box5">
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <!-- <th><input type="checkbox" onclick="checkAll(this);" /></th> -->
            <th>借款编号</th>
            <th>借款人状态</th>
            <th>借款人姓名</th>
            <th>借款用途</th>
            <th>初始借款金额</th>
            <th>月账户管理费</th>
			<th>初始借款时间</th>
			<th>还款期限</th>
			<th>剩余期限</th>
			<th>起始还款日期</th>
			<th>借款利率%</th>
            <th>还款截止日期</th>
			<th>产品类型</th>
            <th>借款人职业情况</th>
            <th>证件号码</th>
            <th>操作人</th>
            <th>操作时间</th>
            <th>状态</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ items!=null && fn:length(items.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${items.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <%-- <td><input type="checkbox" name="checkBoxItem" value='${item.id }' onclick="chekbox_click();" />
             </td> --%><%-- <c:if test="${item.dealMoney eq null}">0.00</c:if> --%>
             <td>${item.loanCode}</td>
             <td>${item.loanStatus}</td>
             <td>${item.loanName}</td>
             <td>${item.loanPurpose}</td>
             <td>${item.initMoney}<c:if test="${item.initMoney eq null}">0.00</c:if></td>
             <td>${item.manageMoney}<c:if test="${item.manageMoney eq null}">0.00</c:if></td>
             <td>${item.initLoanDateStr}</td>
             <td>${item.repaymentDate}月</td>
             <td>${item.surplusDate}期</td>
             <td>${item.startDateStr}</td>
             <td>${item.interestRate}<c:if test="${item.interestRate eq null}">0</c:if></td>
             <td>${item.endDateStr}</td>
             <td>${item.type}</td>  
             <td>${item.occupation}</td>  
             <td>${item.cerNum}</td>  
             <td>${item.operationUser}</td>  
             <td>${item.operationDate}</td>  
             <td>${item.status}</td>          
         </tr> 
         </c:forEach>  
         </c:if> 
         <c:if test="${ items==null || fn:length(items.list)==0}">
           <tr><td colspan="24" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
      
    </table>
	<c:if test="${ items!=null || fn:length(items)>0}">
			  <div class="page">${items}</div>
			</c:if>
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
