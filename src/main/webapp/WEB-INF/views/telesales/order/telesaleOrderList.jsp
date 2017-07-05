<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 var cert=$("#mateCertNum").val();
		 if(cert==""){
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
	 $('#showMore').bind('click',function(){
		show();  
	 });
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/telesales/customerManagement/findTelesaleOrderList");
		$("#inputForm").submit();
		return false;
	}
  
  function dealOrder(id){
	  if(id!=""){
		  $.ajax({
				url : ctx + '/telesales/customerManagement/dealTelesaleOrder?id='+id,
				type : 'post',
				data : $('#inputForm').serialize(),
				dataType : 'text',
				success : function(data) {
					if (data) {
						art.dialog.close();
						art.dialog.alert("取单成功！");
					} else {
						art.dialog.alert("取单失败！");
					}
				},
				error : function() {
					art.dialog.alert("服务器异常！");
				},
				async : false
			});
	  }
  }
</script>
<title>信借电销客户咨询列表</title>
<meta name="decorator" content="default" />
</head>
<body>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="consultView" action="${ctx}/telesales/customerManagement/findTelesaleOrderList" method="post"  class="form-horizontal">
       	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户身份证号：</label><form:input path="mateCertNum" id="mateCertNum" class="input_text178"/></td>
                <td></td>
	            <td></td>
            </tr>
        </table>
        <div  class="tright pr30 pb5">
                 <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                 <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
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
	             <th>身份证号码</th>
	             <th>发送门店</th>                      
	             <th>电销专员</th>
	             <th>电销编号</th>             
	             <th>操作</th>
         </tr>
         </thead>
       <tbody>
         <c:if test="${page.list!=null && fn:length(page.list)>0}">
			<c:forEach items="${page.list}" var="item" varStatus="st">
				<tr>
					<td><c:out value="${st.count }" /></td>
				    <td>${item.customerName}</td>
				    <td>${item.mateCertNum}</td>
				    <td>${item.storeName}</td>
				    <td>${item.telesaleManName}</td>
				    <td>${item.telesaleManCode}</td>
				    <td class="tcenter">
				       <button class="btn_edit" onclick="dealOrder('${item.id}')">取单</button>
				    </td>
				</tr>
			</c:forEach>
		   </c:if>
		   <c:if test="${page.list==null || fn:length(page.list)==0}">
               <tr><td colspan="7" style="text-align:center;">暂无数据!</td></tr>
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