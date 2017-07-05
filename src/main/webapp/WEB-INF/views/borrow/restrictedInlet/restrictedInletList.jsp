<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/poscard/restricted.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>


<%-- <script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script> --%>
<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet"
	href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />

<meta name="decorator" content="default" />
<script language="javascript">
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#backForm").attr("action",
				"${ctx}/borrow/restrictedInlet/restrictedInletList");
		$("#backForm").submit();
		return false;
	}

		function backReason() {
	 $('#diabox01').modal('toggle').css({
	 width : '90%',
	 'margin-left' : function() {
	 return -($(this).width() / 2);
	 }
	 });
	 } 
</script>
</head>
<body>
	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}">
		<button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>
	<div class="control-group">
		   <form:form  action="${ctx }/borrow/restrictedInlet/restrictedInletList" method="post" id="backForm" modelAttribute="PosBacktage">
   			<input id="pageNo" name="pageNo" type="hidden" value="${restrictedInletListpage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${restrictedInletListpage.pageSize}" />	
    </div>
      </form:form>
	</div>
	<p class="mb5">
		<button class="btn btn-small" id="checkBtnList">设置高危线</button>
		&nbsp;&nbsp;<span class="red">选择修改条数：</span><span class="red" id="totalNum">${totalNum }</span>
	</p>
	<table id="tableList" class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th><input type="checkbox"   class="checkbox" id="checkAll" /></th>
				<th>序号</th>
				<th>省分公司名称</th>
				<th>业务大区名称</th>
				<th>客户经理M1逾期率高危线</th>
				<th>团队经理M1逾期率高危线</th>
				<th>门店M1逾期率高危线</th>
			</tr>
		</thead>
		<tbody>
			<c:if
				test="${restrictedInletListpage.list!=null && fn:length(restrictedInletListpage.list)>0}">
				<c:set var="index" value="0" />
				<c:forEach items="${restrictedInletListpage.list}" var="item" varStatus="sta">
					<c:set var="index" value="${index+1}" />
					<tr>
						<td><input type="checkbox" name="checkBoxItem"  id="checkBoxItem"
							value=${item.id } /></td>
						<td>${sta.count}</td>            <!-- 省分公司名称 --> 
						<td>${item.provinceName}</td>     <!-- 省分公司名称 --> 
						<td>${item.lgareName}</td>       <!-- 业务大区名称 -->
						<td>${item.customerNumLable}%</td>     <!-- 客户经理M1逾期率 -->
						<td>${item.termNumLable}%</td>         <!-- 团队经理M1逾期率高危线-->
						<td>${item.storeNumLable}%</td>        <!-- 门店M1逾期率高危线-->
					   </tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${ restrictedInletListpage.list==null || fn:length(restrictedInletListpage.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有待办数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	</form>
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