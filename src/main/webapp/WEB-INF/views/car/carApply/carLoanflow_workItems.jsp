<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<!-- 车借申请待办 -->
<head>
<title>车借申请待办-面审</title>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("select").trigger("change");
		 
	 });
	 $('#showMore').bind('click',function(){
		show(); 
		 
	 });
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/car/carApplyTask/fetchTaskItems");
		$("#inputForm").submit();
		return false;
  } 
  function checkDealUser(applyId,wobNum,token) {
	  $.ajax({
			url:  "${ctx}/car/carApplyTask/checkDealUser",
			data:"applyId="+applyId,
			type: "post",
			dataType:'text',
			success:function(data){
				if(data=="true"){
					windowLocationHref('${ctx}/bpm/flowController/openForm?applyId='+applyId+'&wobNum='+wobNum+'&dealType=0&token='+token);
				}else if(data=="false"){
					alert("该数据有误！");
				}else{
					alert("该数据已由用户  "+data+" 处理！");
				}
			}
			});
  } 
</script>
<style type="text/css">
.trRed {
	color:red;
}
</style>
<meta name="decorator" content="default" />
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/car/carApplyTask/fetchTaskItems">申请待办列表</a></li>
		<li ><a href="${ctx}/car/carContract/firstDefer/selectDeferList">展期待办列表</a></li>
	</ul>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="loanFlowQueryParam" method="post" class="form-horizontal">
       <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
       <input name="menuId" type="hidden" value="${param.menuId}" />
	   <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table id="searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input name="customerName" value="${param.customerName}" type="text" class="input_text178">
                 </td>
                <td><label class="lab">产品类型：</label>
                <form:select  id="productType" path="borrowProductCode" class="select180">
                   <option value="">请选择</option>
					 <c:forEach items="${fncjk:getPrd('products_type_car_credit')}" var="product_type">
                           <form:option value="${product_type.productCode}">${product_type.productName}</form:option>
                     </c:forEach>  
		         </form:select>
                </td>
                <td><label class="lab">借款状态：</label>
                <form:select id="cardType" path="dictStatus" class="select180">
                    <option value="">请选择</option>
					<form:options items="${fns:getDictList('jk_car_loan_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
					</td>
            </tr>
            <tr>
                <td>
                  <label class="lab">团队经理：</label>
                  <input type="text" id="loanTeamEmpName" name="loanTeamEmpName" value="${param.loanTeamEmpName}" class="input_text178"/>
                </td>

                <td><label class="lab">客户经理：</label>
                	<input type="text" id="offendSalesName" name="offendSalesName" value="${param.offendSalesName}" class="input_text178"/>
                </td>
                <td>
                  <label class="lab">门店：</label>
                  <form:input id="txtStore" path="storeName" class="txt date input_text178"  readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore">
                </td>
               

            </tr>
			<tr id="T1" style="display:none">
					<td >
                  <label class="lab">是否电销：</label>
                  <c:forEach items="${fns:getDictList('jk_yes_or_no')}" var="item"><input type="radio" name="loanIsPhone"
								value="${item.value}"
								<c:if test="${item.value==param.loanIsPhone}">
                                checked='true'
                             	</c:if> />
                             	${item.label}
               	  </c:forEach>
                </td>
					<td><label class="lab">渠道：</label>
					<form:select id="cardType" path="conditionalThroughFlag" class="select180">
                    <option value="">请选择</option>
					<form:options items="${fns:getDictList('jk_car_throuth_flag')}" itemLabel="label" itemValue="label" htmlEscape="false"/>
				</form:select>
					</td>
			 </tr>
			 
        </table>
        <div  class="tright pr30 pb5">
                                       <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                                       <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        <div class="xiala" style="text-align:center;">
				  <img src="${context}/static/images/up.png" id="showMore"></img>
       </div>
    </div>
    </div>
   </form:form>
   <sys:columnCtrl pageToken="carLoanFlowList"></sys:columnCtrl> 
   <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px"> 
       	 
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;" >
      <thead>
         <tr>
          <th>序号</th>
          <th>合同编号</th>
          <th>客户姓名</th>
          <th>申请金额（元）</th>
          <th>借款限期（天）</th>
          <th>产品类型</th>
          <th>评估金额</th>
          <th>批借金额</th>
          <th>申请日期</th>
          <th>团队经理</th>
          <th>客户经理</th>
          <th>面审人员</th>
          <th>借款状态</th>
          <th>合同到期提醒</th>
          <th>车牌号码</th>
          <th>是否电销</th>
          <th>渠道</th>
          <th>操作</th>
         </tr>
         </thead>
         	<c:if test="${ itemList!=null && fn:length(itemList)>0}">
         	<c:set var="index" value="0"/>
          	<c:forEach items="${itemList}" var="item"> 
 
			<tr <c:if test="${fn:contains(item.orderField,'0,') }">class='trRed'</c:if>>
             <td><c:set var="index" value="${index+1}"/>${index}</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName}</td>
             <td><fmt:formatNumber value="${item.loanApplyAmount}" type="number" pattern="0.00"/></td>
             <td><c:choose>
             	<c:when test="${item.auditLoanMonths !=null&&item.auditLoanMonths !=0}">
             		${item.auditLoanMonths}
             	</c:when>
             	<c:otherwise>
             		<c:choose>
		             	<c:when test="${item.loanMonths == 0}">
		             		
		             	</c:when>
		             	<c:otherwise>
		             		${item.loanMonths}
		             	</c:otherwise>
		             </c:choose>
             	</c:otherwise>
             </c:choose></td>
             <td>
             <c:choose>
             	<c:when test="${item.auditBorrowProductCode !=null&&item.auditBorrowProductCode !=''}">
             		${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.auditBorrowProductCode)}
             	</c:when>
             	<c:otherwise>
             		${fncjk:getPrdLabelByTypeAndCode('products_type_car_credit',item.borrowProductCode)}
             	</c:otherwise>
             </c:choose>
             </td>
             <td><fmt:formatNumber value="${item.storeAssessAmount}" type="number" pattern="0.00"/></td>
             <td><fmt:formatNumber value="${item.auditAmount}" type="number" pattern="0.00"/></td>
             <td><fmt:formatDate value='${item.loanApplyTime}' pattern="yyyy-MM-dd"/></td>
             <td>${item.loanTeamEmpName}</td>
             <td>${item.offendSalesName}</td>
             <td>${item.firstCheckName}</td>
             <td>${item.dictStatus}</td>
             <td><fmt:formatDate value="${item.contractExpirationDate}" pattern="yyyy-MM-dd"/></td>
             <td>${item.plateNumbers}</td>
             <td>${item.loanIsPhone}</td>
             <td>${item.loanFlag}</td>
             <td>
              <%--   <input type="button" value="办理" class="btn_edit"  
              onclick="window.location='${ctx}/car/carApplyTask/carLoanFlowCustomer?
              applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"></input> --%>
              <c:if test="${!((item.dictStatus == '待确认签署' || item.dictStatus == '待签订合同') && (item.backReason == '风险客户' || item.backReason == '客户主动放弃')) }">
                 <input type="button" value="办理" class="btn_edit"  onclick="checkDealUser('${item.applyId}','${item.wobNum}','${item.token}')"></input>
            </c:if>
             </td>     
        	 </tr>
            
   
     
         </c:forEach>       
         </c:if>
         <c:if test="${ itemList==null || fn:length(itemList)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
      </table>
    </div>
 <div class="pagination">${page}</div>
 <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-280,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>