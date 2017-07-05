<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default"/>
<script type="text/javascript" src="${context}/js/payback/platform.js"></script>
<script type="text/javascript" src="${context}/static/bootstrap/3.3.5/js/bootstrap-dialog.min.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
     <ul class="nav nav-tabs">
		<li ><a href="${ctx}/borrow/payback/bankplantport/queryPage">银行平台接口列表</a></li>
		<li class="active"><a href="${ctx}/borrow/payback/plantskiporder/queryPage">平台跳转顺序列表</a></li>
	</ul>
<div class="control-group">
	<form id="searchForm" method="post" action="${ctx}/borrow/payback/plantskiporder/queryPage">
	        	    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	            <tr> 
				    <td>
				    	<label class="lab">第一平台：</label>
				    	<select name="platformId"  class="select78"  style ="width:135px;" value="${platformGotoRule.platformId}">
								<option value="">请选择</option>
		                		<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="d">
		                			<option value="${d.value}" 
		                				<c:if test="${d.value eq platformGotoRule.platformId}">
		                					selected
		                				</c:if> 
		                			>${d.label}</option>
		                		</c:forEach>
		                	</select>
				    </td>
		             <td><label class="lab">集中：</label>
		                  <select class="select180" name="isConcentrate">
						    <option value="">请选择</option>
					        <c:forEach var="concentrate" items="${fns:getDictList('yes_no')}">
								<option value="${concentrate.value }"   <c:if test="${platformGotoRule.isConcentrate==concentrate.value }">selected</c:if> >${concentrate.label}</option>
							</c:forEach>
						  </select>
	                 </td>
				   	<td>
				   	  <lable></lable>
				   	</td>
	            </tr>
	        </table>
	         <div class="tright pr30 pb5">
	            <input class="btn btn-primary" type="submit" value="搜索"/>
	            <input type="reset" class="btn btn-primary" id='reset' value="清除">
	        </div>
    </form>
    </div>
    <sys:message content="${message}"/>
   	<p class="mb5"> 
    <button id="add"  class="btn btn-small">新增</button></p>
    <sys:columnCtrl pageToken="trusteeship_account_list"></sys:columnCtrl>
   <div class="box5"> 
    <table id="tableList" class="table table-striped table-bordered table-condensed data-list-table" width="100%">
        <thead>
        <tr>
            <th>序号</th>
            <th>平台ID</th>
            <th>第一平台</th>
            <th>平台规则</th>
            <th>状态</th>
            <th>是否集中</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${page.list }" var="platformEntity" varStatus="status">
	        <tr>
	           <td>${(page.pageNo-1)*(page.pageSize)+status.count}</td>
	           <td>${platformEntity.platformId}</td>
	           <td>${platformEntity.platformRuleName}</td>
	           <td>${platformEntity.platformRuleTitle}</td>
	           <td>
		          		<c:if test="${platformEntity.status eq '0'}">停用</c:if>
		          		<c:if test="${platformEntity.status eq '1'}">启用</c:if>
	           </td>
	             <td>
		          		<c:if test="${platformEntity.isConcentrate eq '1'}">是</c:if>
		          		<c:if test="${platformEntity.isConcentrate eq '0'}">否</c:if>
	           </td>
	           <td>
<%-- 	           		<auth:hasPermission key="cf:platformgorule:edit"> --%>
			           	<a href="${ctx}/borrow/payback/plantskiporder/get?id=${platformEntity.id }">
						    <button class="cf_btn_edit" onclick="">修改</button>
				        </a>
<%-- 				    </auth:hasPermission> --%>
<%-- 				    <auth:hasPermission key="cf:platformgorule:del"> --%>
						<input id="delete" type="button" url="${ctx}/borrow/payback/plantskiporder/delete?id=${platformEntity.id }&status=3" value="删除" class="cf_btn_edit" style="cursor:pointer;">
<%-- 					</auth:hasPermission> --%>
			       </td>
		        </tr>
	        </c:forEach>
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
