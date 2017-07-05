<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input', '#inputForm').not(':button, :submit, :reset')
			.val('').removeAttr('checked').removeAttr('selected');
			$('#inputForm')[0].submit();
	 });
	 $('#showMore').bind('click',function(){
		show();  
	 });
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/telesales/customerManagement/findTelesaleReconsiderList");
		$("#inputForm").submit();
		return false;
	}
</script>
<title>电销待复议筛选列表</title>
<meta name="decorator" content="default" />
</head>
<body>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="consultView" action="${ctx}/telesales/customerManagement/findTelesaleReconsiderList" method="post"  class="form-horizontal">
       	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input path="customerName" class="input_text178"/></td>
                <td><label class="lab">身份证号：</label><form:input path="mateCertNum" class="input_text178"/></td>
	            <td><label class="lab">手机号码：</label><form:input path="customerMobilePhone" class="input_text178"/></td>
            </tr>
            <tr>
                <td><label class="lab">电销专员：</label><form:input path="telesaleManName"  class="input_text178"/></td>
                <td><label class="lab">电销团队主管：</label><form:input path="telesaleTeamLeaderName"  class="input_text178"/></td>              
                <td><label class="lab">电销现场经理：</label><form:input  path="telesaleSiteManagerName" class="input_text178"/>
            </tr>
            <tr id="T1" style="display:none">
            	<td><label class="lab">电销来源：</label><form:select id="consTelesalesSource" path="consTelesalesSource" class="select180">
                    <option value="">全部</option>
					<form:options items="${fns:getDictList('jk_rs_src')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>	
				<td></td>
				<td></td>
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
   <sys:message content="${message}" />
   <div class="box5"> 
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
      <thead>
         <tr>
               <th>序号</th>
                <th>客户姓名</th>
                <th>借款编号</th>
                <th>电销来源</th>
                <th>电销专员</th>
                <th>电销专员编号</th>
                <th>电销团队主管</th>
                <th>电销现场经理</th>
                <th>产品</th>
                <th>门店</th>
                <th>申请金额</th>
                <th>申请日期</th>
         </tr>
         </thead>
        <tbody>
          <c:if test="${page.list!=null && fn:length(page.list)>0}">
	        <c:forEach items="${page.list}" var="item" varStatus="st">
					<tr>
						<td><c:out value="${st.count }" /></td>
					    <td>${item.customerName}</td>
					    <td>${item.loanCode}</td>
					    <td>${fns:getDictLists(item.consTelesalesSource,'jk_rs_src')}</td>
					    <td>${item.telesaleManName}</td>
					    <td>${item.telesaleManCode}</td>
					    <td>${item.telesaleTeamLeaderName}</td>
					    <td>${item.telesaleSiteManagerName}</td>				   
					    <td>${item.productName}</td>	
					    <td>${item.storeName}</td>
					    <td>${item.loanApplyAmount}</td>					  					 
					    <td><fmt:formatDate value="${item.loanApplyTime}"
								pattern="yyyy/MM/dd" /></td>					   
					</tr>
				</c:forEach>
			  </c:if>
			  <c:if test="${page.list==null || fn:length(page.list)==0}">
               <tr><td colspan="12" style="text-align:center;">暂无数据!</td></tr>
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