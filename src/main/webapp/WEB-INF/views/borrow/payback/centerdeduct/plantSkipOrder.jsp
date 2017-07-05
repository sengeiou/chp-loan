<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>平台跳转顺序列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${context}/js/payback/platformSkip.js"></script>
	
	<script type="text/javascript">
	function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#centerapplyPayForm").attr("action", "${ctx}/borrow/payback/plantskipordernew/queryPage");
		$("#centerapplyPayForm").submit();
		return false;
	}
	
	/**
	 * 新增
	 */
	 
	 $(document).ready(function() {
			
		$("#addDue").bind('click', function() {
			window.location.href = ctx + "/borrow/payback/plantskipordernew/goAdd";
		})
	 });
	
	//点击清除按钮调用的的方法
	  function resets(){
	  	// 清除text	
	    $(":input").val("");
	  	$("#centerapplyPayForm").submit();
	  }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/borrow/payback/deductPlantLimit/queryPage">划扣限制配置列表</a></li>
		<li class="active"><a href="${ctx}/borrow/payback/plantskipordernew/queryPage">平台跳转顺序列表</a></li>
	</ul>
	<div class="control-group">
	<form method="post" id="centerapplyPayForm" action="${ctx}/borrow/payback/plantskipordernew/queryPage">
       <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
                <td><label class="lab">银行：</label>
                   <select class="select180" name="bankCode">
				    <option value="">请选择</option>
			         <c:forEach var="bank" items="${fns:getDictList('jk_open_bank')}">
						<option value="${bank.value }" <c:if test="${record.bankCode==bank.value }">selected</c:if> >${bank.label}</option>
					 </c:forEach>
				  </select>
                </td>
                <td><label class="lab">集中/非集中:</label>
                  <select class="select180" name="isConcentrate">
				    <option value="">请选择</option>
			        <c:forEach var="isConcentrate" items="${fns:getDictList('yes_no')}">
						<option value="${isConcentrate.value }"   <c:if test="${record.isConcentrate==isConcentrate.value }">selected</c:if> >${isConcentrate.label}</option>
					</c:forEach>
				  </select>
               </td>
            </tr>
        </table>
        <div class="tright pr30 pb5">
        <button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        <button class="btn btn-primary" onclick ='resets()'>清除</button></div>
   </form>
   <sys:message content="${message}"/>
   </div>
	<p class="mb5"> 
    <button id="addDue" role="button" class="btn btn-small">新增</button>
	<div class="box5"> 
        <table  class="table  table-bordered table-condensed" >
        <thead>
            <tr>
               	<th>序号</th>
                <th>银行</th>
                <th>跳转顺序</th>
                <th>是否启用</th>
                <th>集中/非集中</th>
                <th>操作</th>
            </tr>
         </thead>   
        <c:if test="${page.list!=null && fn:length(page.list)>0}">
         <c:forEach items="${page.list}" var="plantskiporder" varStatus="status">
            <tr>
                 <td>${(page.pageNo-1)*page.pageSize+status.count}</td>
                 <td>${plantskiporder.bankCode}</td>
                 <td>
                    ${plantskiporder.platformRuleName}
                 </td>
                  <td>
                    ${plantskiporder.status}
                 </td>
                 <td>
                    ${plantskiporder.isConcentrate}
                 </td>
                 <td>
		           	<a href="${ctx}/borrow/payback/plantskipordernew/get?id=${plantskiporder.id }">
					    <button class="cf_btn_edit" onclick="">修改</button>
			        </a>
					<input id="delete" type="button" url="${ctx}/borrow/payback/plantskipordernew/delete?id=${plantskiporder.id }&status=3" value="删除" class="cf_btn_edit" style="cursor:pointer;">
                 </td>
            </tr>
			</c:forEach>
		</c:if>
        <c:if test="${ page.list==null || fn:length(page.list)==0}">
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