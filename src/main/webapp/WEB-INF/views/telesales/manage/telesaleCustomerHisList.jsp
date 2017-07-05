<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 var cert=$("#mateCertNum").val();
		 var phone=$("#customerMobilePhone").val();
		 if(cert==""&&phone==""){
			 art.dialog.alert("请输入查询条件!");
			 return false;
		 }
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input', '#inputForm').not(':button, :submit, :reset')
			.val('').removeAttr('checked').removeAttr('selected');
			$('#inputForm')[0].submit(); 		 
	 });
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/telesales/customerManagement/findTelesaleCustomerHisList");
		$("#inputForm").submit();
		return false;
	}
</script>
<title>电销客户信息表</title>
<meta name="decorator" content="default" />
</head>
<body>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="consultView" action="${ctx}/telesales/customerManagement/findTelesaleCustomerHisList" method="post"  class="form-horizontal">
       	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户身份证号：</label><form:input path="mateCertNum" id="mateCertNum" class="input_text178"/></td>
                <td><label class="lab">客户电话号码：</label><form:input path="customerMobilePhone"  id="customerMobilePhone" class="input_text178"/></td>
	            <td></td>
            </tr>
        </table>
        <div  class="tright pr30 pb5">
                 <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                 <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
       </div>
    </div>
   </form:form> 
   <sys:columnCtrl pageToken="talesales"></sys:columnCtrl>
   <div class="box5"> 
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
      <thead>
         <tr>
                <th>序号</th>
                <th>借款编号</th>
                <th>合同编号</th>
                <th>客户姓名</th>
                <th>共借人</th>
                <th>自然人保证人</th>
                <th>省份</th>
                <th>城市</th>
                <th>门店</th>
                <th>状态</th>
                <th>产品</th>
                <th>是否加急</th>
                <th>申请金额</th>
                <th>批复金额</th>
                <th>团队经理</th>
                <th>客户经理</th>
                <th>录单人员</th>
                <th>外访人员</th>
				<th>申请时间</th>
				<th>进件时间</th>
				<th>渠道</th>
				<th>是否循环借</th>
				<th>是否电销</th>
				<th>操作</th>
         </tr>
        </thead>
        <tbody>
          <c:if test="${page.list!=null && fn:length(page.list)>0}">
        	<c:forEach items="${page.list}" var="item" varStatus="st">
				<tr>
					<td><c:out value="${st.count }" /></td>
				    <td>${item.loanCode}</td>
				    <td>${item.contractCode}</td>
				    <td>${item.customerName}</td>
				     <td>
				    	<c:if test="${item.loanInfoOldOrNewFlag eq '0'}">	
							${item.coboName}
						</c:if>
				    </td>
				    <td>
				    	<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">	
							${item.bestCoborrower}
						</c:if>
				    </td>
				    <td>${item.storeProviceName}</td>
				    <td>${item.storeCityName}</td>				   
				    <td>${item.storeName}</td>	
				    <td>${item.dictLoanStatusName}</td>
				    <td>${item.productName}</td>
				    <td>${item.loanUrgentFlag}</td>
				    <td>${item.loanApplyAmount}</td>
				    <td>${item.loanAuditAmount}</td>
				    <td>${item.loanTeamManagerCode}</td>				   
				    <td>${item.loanManagerCode}</td>
				    <td>${item.creater}</td>    
				    <td>${item.loanSurveyEmpId}</td>
				    <td><fmt:formatDate value="${item.loanApplyTime}"
							pattern="yyyy/MM/dd" /></td>
				    <td><fmt:formatDate value="${item.customerIntoTime}"
							pattern="yyyy/MM/dd" /></td>
				    <td>${item.loanFlagName}</td>
				    <td>${item.dictIsCycleName}</td>				   
				    <td>${item.consTelesalesFlagName}</td>	
				    <td><input type="button" class="btn_edit" value="查看" onclick="window.location='${ctx}/borrow/transate/loanMinute?loanCode=${item.loanCode}'"></td>	
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${page.list==null || fn:length(page.list)==0}">
             <tr><td colspan="23" style="text-align:center;">暂无数据!</td></tr>
            </c:if>
		</tbody>
      </table>
     </div>
  </div>
 <div class="pagination">${page}</div>
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