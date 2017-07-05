<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/poscard/posBacktage.js" type="text/javascript"></script>
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
				"${ctx}/borrow/poscard/posBacktageList/posBacktageInfo");
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
		   <form:form  action="${ctx }/borrow/restrictedInlet/stroreList" method="post" id="backForm" modelAttribute="PosBacktage">
   			<input id="pageNo" name="pageNo" type="hidden" value="${restrictedInletListpage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${restrictedInletListpage.pageSize}" />	
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
          <tr>
			   <td><label class="lab">门店名称：</label>${res.orgName}</td>
			   <td><label class="lab">M1逾期高危线：</label>${res.storeNumLable}%</td>
			   <td><label class="lab">当前M1逾期率：</label>${res.m1YqlCurrentLable}%</td>
          </tr> 
          
           <tr>
                <td><label class="lab">岗位：</label><select class="select180" name="zkbjType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_person_type')}" var="matchingState">
                                   <option value="${matchingState.value }" <c:if test="${restrictedInlet.zkbjType == matchingState.value }">selected</c:if>>${matchingState.label }</option>
                              </c:forEach>
                	</select>
               </td>
			   <td><label class="lab">可进件</label>&nbsp;&nbsp;&nbsp;&nbsp;<span class="red">限制进件</span></td>
         
         
           </tr> 
            
           
        </table>
        <div class="tright pr30 pb5">
          		<input type="button" id="searchBtn" class="btn btn-primary"
						value="搜索"></input> <input type="button" id="clearBtn"
						class="btn btn-primary" value="清除"></input>
        </div>
      </form:form>
	</div>
	<table id="tableList" class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th>姓名</th>
				<th>岗位</th>
				<th>M1逾期率高危线</th>
				<th>当前M1逾期率</th>
				<th>是否限制进件</th>
			</tr>
		</thead>
		<tbody>
			<c:if
				test="${restrictedInletListpage.list!=null && fn:length(restrictedInletListpage.list)>0}">
				<c:set var="index" value="0" />
				<c:forEach items="${restrictedInletListpage.list}" var="item">
					<c:set var="index" value="${index+1}" />
					<tr>
						<td>${item.teamName}</td>            <!-- 姓名 --> 
						<td>${item.zkbjTypeLable}</td>       <!-- 岗位-->
						<td>${item.m1YqlLable}%</td>     <!-- M1逾期率高危线-->
						<td>${item.m1YqlCurrentLable}%</td>    <!-- 当前M1逾期率-->
						<td>${item.sfJjLable}</td>    <!-- 是否被限制进件-->
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

	<div class="pagination">${restrictedInletListpage }</div>
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